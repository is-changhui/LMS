/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookNotFoundException;
import util.exception.BookOnLoanException;
import util.exception.FineNotPaidException;
import util.exception.LendingNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author Darie
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanRemote, LendAndReturnSessionBeanLocal {
    
    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;
    
    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;
    
    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public LendAndReturn createNewLendRecord(String identityNo, String title, Date lendRecordDate) throws MemberNotFoundException, BookNotFoundException, BookOnLoanException {
        
        Member currentMember = memberSessionBeanLocal.retrieveMemberByIdentityNo(identityNo);
        Book currentBook = bookSessionBeanLocal.retrieveBookByTitle(title);
        
        boolean onLoan = false;
        List<LendAndReturn> lendAndReturnRecords = currentBook.getLendAndReturns();
        LendAndReturn lendRecord;

        // Look through lend records to see if this book is on loan. If on loan, cannot lend again.
        for (LendAndReturn lendAndReturnRecord : lendAndReturnRecords) {
            if (lendAndReturnRecord.getReturnDate() == null) {
                onLoan = true;
                break;
            }
        }
        
        if (!onLoan) {
            lendRecord = new LendAndReturn(lendRecordDate, null, BigDecimal.ZERO);
            lendRecord.setMember(currentMember);
            lendRecord.setBook(currentBook);
            currentMember.getLendAndReturns().add(lendRecord);
            currentBook.getLendAndReturns().add(lendRecord);
            em.persist(lendRecord);
            em.flush();
        } else {
            throw new BookOnLoanException(" Book title [" + title + "] is already on loan and cannot be lent again! Please wait for the book to be returned!");
        }
        
        lendRecord.setMember(currentMember);
        lendRecord.setBook(currentBook);
        
        return lendRecord;
        
    }
    
    @Override
    public List<LendAndReturn> retrieveLendRecordsByMemberIdentityNo(String identityNo) throws MemberNotFoundException {
        
        Query query = em.createQuery("SELECT lar FROM LendAndReturn lar WHERE lar.member.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);
        return query.getResultList();
    }
    
    @Override
    public List<LendAndReturn> retrieveAllLendingRecords() {
        Query query = em.createQuery("SELECT lar FROM LendAndReturn lar");
        List<LendAndReturn> records = query.getResultList();
        for (LendAndReturn record : records) {
            try {
                record.setFineAmount(calculateFineAmount(record.getLendId(), new Date()));
            } catch (LendingNotFoundException ex) {
                System.out.println("LendAndReturn ID [" + record.getLendId() + "] does not exist!");
            }
        }
        return records;
    }
    
    @Override
    public BigDecimal calculateFineAmount(Long lendAndReturnId, Date enquiryDate) throws LendingNotFoundException {
        
        LendAndReturn lendAndReturn = em.find(LendAndReturn.class,
                lendAndReturnId);
        
        if (lendAndReturn != null) {
            long timeDifference = enquiryDate.getTime() - lendAndReturn.getLendDate().getTime();
            
            long dayDifference = (timeDifference / (1000 * 60 * 60 * 24));
            
            if (dayDifference > 14) {
                return BigDecimal.valueOf(dayDifference * 0.50);
            } else {
                return BigDecimal.valueOf(0);
            }
            
        } else {
            throw new LendingNotFoundException("LendAndReturn ID [" + lendAndReturnId + "] does not exist!");
        }
    }
    
    @Override
    public LendAndReturn returnBook(Long lendAndReturnId, Date returnRecordDate, BigDecimal fineAmountPaid) throws FineNotPaidException, LendingNotFoundException {
        
        LendAndReturn lendAndReturn = em.find(LendAndReturn.class,
                lendAndReturnId);
        
        if (lendAndReturn != null && lendAndReturn.getReturnDate() == null) {
            BigDecimal fineAmount = calculateFineAmount(lendAndReturnId, returnRecordDate);
            lendAndReturn.setFineAmount(fineAmount);
            if (fineAmountPaid.compareTo(fineAmount) >= 0) {
                lendAndReturn.setReturnDate(returnRecordDate);
                return lendAndReturn;
            } else {
                // FineNotPaid shall include partially-paid fines. Fines that are over-paid will be unrefundable.
                throw new FineNotPaidException("Book cannot be returned because there is an unpaid/underpaid fine amount of $" + lendAndReturn.getFineAmount().subtract(fineAmountPaid));
            }
        } else {
            throw new LendingNotFoundException("LendAndReturn ID [" + lendAndReturnId + "] does not exist/Book has been returned already!");
        }
    }
}
