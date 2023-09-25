/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import java.util.List;
import javax.ejb.Remote;
import util.exception.MemberExistException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Remote
public interface MemberSessionBeanRemote {

    public Member createNewMember(Member member) throws MemberExistException, UnknownPersistenceException;

    public Member retrieveMemberById(Long memberId) throws MemberNotFoundException;

    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException;

    public List<Member> retrieveAllMembers();

    public List<Member> retrieveMembersByFirstName(String firstName) throws MemberNotFoundException;

}
