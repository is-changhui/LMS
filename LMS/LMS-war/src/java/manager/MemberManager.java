/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import ejb.session.stateless.MemberSessionBeanLocal;
import entity.Member;
import java.util.List;
import util.exception.MemberExistException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
public class MemberManager {

    private MemberSessionBeanLocal memberSessionBeanLocal;

    public MemberManager() {
    }

    public MemberManager(MemberSessionBeanLocal memberSessionBeanLocal) {
        this.memberSessionBeanLocal = memberSessionBeanLocal;
    }

    public Member getMember(Long memberId) throws Exception {
        return memberSessionBeanLocal.retrieveMemberById(memberId);
    }

//    public void updateCustomer(Long cId, String name, byte gender, String dob) throws Exception {
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//
//        Date dob1 = df.parse(dob);
//
//        Customer c = new Customer();
//        c.setId(cId);
//        c.setName(name);
//        c.setGender(gender);
//        c.setDob(dob1);
//
//        customerSessionLocal.updateCustomer(c);
//    }
    public void createMember(String firstName, String lastName, Character gender, Integer age, String identityNo, String phone, String address) throws MemberExistException, UnknownPersistenceException {

        Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setGender(gender);
        member.setAge(age);
        member.setIdentityNo(identityNo);
        member.setPhone(phone);
        member.setAddress(address);

        Member m = memberSessionBeanLocal.createNewMember(member);
    }

    public List<Member> searchMembers() {
        return memberSessionBeanLocal.retrieveAllMembers();
    }

    public Member searchMemberByIdentityNo(String identityNo) throws MemberNotFoundException {
        return memberSessionBeanLocal.retrieveMemberByIdentityNo(identityNo);
    }

    public List<Member> searchMembersByFirstName(String firstName) throws MemberNotFoundException {
        return memberSessionBeanLocal.retrieveMembersByFirstName(firstName);
    }
//
//    public List<Customer> searchCustomersByPhone(String phone) {
//        Contact c = new Contact();
//        c.setPhone(phone);
//        return customerSessionLocal.searchCustomersByContact(c);
//    }
//
//    public List<Customer> searchCustomersByEmail(String email) {
//        Contact c = new Contact();
//        c.setEmail(email);
//        return customerSessionLocal.searchCustomersByContact(c);
//    }
//
//    public Set<String> getAllFieldNames() {
//        return customerSessionLocal.getAllFieldNames();
//    }
//
//    public void addField(Long cId, String name, String value) throws
//            NoResultException {
//        Field f = new Field();
//        f.setName(name);
//        f.setFieldValue(value);
//
//        customerSessionLocal.addField(cId, f);
//    }
//
//    public void addPhone(Long cId, String value) throws NoResultException {
//        Contact c = new Contact();
//        c.setPhone(value);
//
//        customerSessionLocal.addContact(cId, c);
//    }
//
//    public void addEmail(Long cId, String value) throws NoResultException {
//        Contact c = new Contact();
//        c.setEmail(value);
//
//        customerSessionLocal.addContact(cId, c);
//    }
//
//    public void deleteField(Long cId, Long fId) throws NoResultException {
//        customerSessionLocal.deleteField(cId, fId);
//    }
//
//    public void deleteContact(Long cId) throws NoResultException {
//        customerSessionLocal.deleteContact(cId);
//    }
//
//    public void deleteCustomer(Long cId) throws NoResultException {
//        customerSessionLocal.deleteCustomer(cId);
//    }
}
