/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.LendAndReturnSessionBeanLocal;
import ejb.session.stateless.MemberSessionBeanLocal;
import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import util.exception.BookNotFoundException;
import util.exception.BookOnLoanException;
import util.exception.FineNotPaidException;
import util.exception.LendingNotFoundException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Named(value = "lendAndReturnManagedBean")
@RequestScoped
public class LendAndReturnManagedBean implements Serializable {


    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal;

    private String memberIdentityNo;
    private String bookTitle;

    private Date lendDate;
    private Date returnDate;
    private BigDecimal fineAmount;

    private Book currentBook;
    private Member currentMember;

    private List<LendAndReturn> lendAndReturns;

    private Long lendId;

    private BigDecimal paidFineAmount;

    public LendAndReturnManagedBean() {
    }

    @PostConstruct
    public void init() {
        setLendAndReturns(lendAndReturnSessionBeanLocal.retrieveAllLendingRecords());
    }

    public void addLendRecord(ActionEvent evt) throws UnknownPersistenceException, MemberNotFoundException, BookNotFoundException, BookOnLoanException {

        Member member = memberSessionBeanLocal.retrieveMemberByIdentityNo(getMemberIdentityNo());
        Book book = bookSessionBeanLocal.retrieveBookByTitle(getBookTitle());

        LendAndReturn lendAndReturn = new LendAndReturn();
        lendAndReturn.setMember(member);
        lendAndReturn.setBook(book);
//        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        cal.setTimeZone(tz);
//        cal.setTime(currentDate);
//        cal.add(Calendar.MONTH, -1);
//        Date oneMonthAgo = cal.getTime();
        Date currentDate = cal.getTime();
        lendAndReturn.setLendDate(currentDate);
        lendAndReturn.setReturnDate(null);
        lendAndReturn.setFineAmount(BigDecimal.ZERO);

        lendAndReturnSessionBeanLocal.createNewLendRecord(lendAndReturn.getMember().getIdentityNo(), lendAndReturn.getBook().getTitle(), lendAndReturn.getLendDate());
    }

    public void viewFineAmount(Long lendRecordId) throws LendingNotFoundException {
        BigDecimal fine = lendAndReturnSessionBeanLocal.calculateFineAmount(lendRecordId, new Date());
        setFineAmount(fine);
    }

    public void returnBookRecord(ActionEvent evt) throws FineNotPaidException, LendingNotFoundException {
        lendAndReturnSessionBeanLocal.returnBook(lendId, new Date(), paidFineAmount);
    }
    
    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    public void setCurrentMember(Member currentMember) {
        this.currentMember = currentMember;
    }

    public String getMemberIdentityNo() {
        return memberIdentityNo;
    }

    public void setMemberIdentityNo(String memberIdentityNo) {
        this.memberIdentityNo = memberIdentityNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<LendAndReturn> getLendAndReturns() {
        return lendAndReturns;
    }

    public void setLendAndReturns(List<LendAndReturn> lendAndReturns) {
        this.lendAndReturns = lendAndReturns;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public BigDecimal getPaidFineAmount() {
        return paidFineAmount;
    }

    public void setPaidFineAmount(BigDecimal paidFineAmount) {
        this.paidFineAmount = paidFineAmount;
    }
}
