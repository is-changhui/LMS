/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.MemberExistException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class MemberSessionBean implements MemberSessionBeanRemote, MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public MemberSessionBean() {
    }

    @Override
    public Member createNewMember(Member member) throws MemberExistException, UnknownPersistenceException {

        try {
            em.persist(member);
            em.flush();
            return member;

        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new MemberExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Member retrieveMemberById(Long memberId) throws MemberNotFoundException {

        Member member = em.find(Member.class, memberId);

        if (member != null) {
            return member;
        } else {
            throw new MemberNotFoundException("MemberId [" + memberId + "] not found in system!");
        }

    }

    @Override
    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException {

        Query query = em.createQuery("SELECT m FROM Member m WHERE m.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);

        try {
            return (Member) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member identityNumber [" + identityNo + "] does not exist!");
        }
    }
    
    @Override
    public List<Member> retrieveMembersByFirstName(String firstName) throws MemberNotFoundException {

        Query query = em.createQuery("SELECT m FROM Member m WHERE m.firstName = :inFirstName");
        query.setParameter("inFirstName", firstName);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("There are no members with the firstName [" + firstName + "] !");
        }
    }

    @Override
    public List<Member> retrieveAllMembers() {
        Query query = em.createQuery("SELECT m FROM Member m");
        return query.getResultList();
    }
}
