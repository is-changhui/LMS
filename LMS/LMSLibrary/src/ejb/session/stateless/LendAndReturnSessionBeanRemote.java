/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturn;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exception.BookNotFoundException;
import util.exception.BookOnLoanException;
import util.exception.FineNotPaidException;
import util.exception.LendingNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author Darie
 */
@Remote
public interface LendAndReturnSessionBeanRemote {

    public LendAndReturn createNewLendRecord(String identityNo, String title, Date lendRecordDate) throws MemberNotFoundException, BookNotFoundException, BookOnLoanException;

    public List<LendAndReturn> retrieveLendRecordsByMemberIdentityNo(String identityNo) throws MemberNotFoundException;

    public BigDecimal calculateFineAmount(Long lendAndReturnId, Date enquiryDate) throws LendingNotFoundException;

    public LendAndReturn returnBook(Long lendAndReturnId, Date returnRecordDate, BigDecimal fineAmountPaid) throws FineNotPaidException, LendingNotFoundException;
    
    public List<LendAndReturn> retrieveAllLendingRecords();

}
