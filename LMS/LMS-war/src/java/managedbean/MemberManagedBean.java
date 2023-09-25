/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.MemberSessionBeanLocal;
import entity.LendAndReturn;
import entity.Member;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import util.exception.MemberExistException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Named(value = "memberManagedBean")
@RequestScoped
public class MemberManagedBean {

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNo;
    private String phone;
    private String address;

    private List<LendAndReturn> lendAndReturns;

    private List<Member> members;

    /**
     * Creates a new instance of MemberManagedBean
     */
    public MemberManagedBean() {
    }

    @PostConstruct
    public void init() {
        setMembers(memberSessionBeanLocal.retrieveAllMembers());
    }

    public void addMember(ActionEvent evt) throws MemberExistException, UnknownPersistenceException {

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
    
    public Member completeMember(String query) throws MemberNotFoundException {
        return memberSessionBeanLocal.retrieveMemberByIdentityNo(query);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LendAndReturn> getLendAndReturns() {
        return lendAndReturns;
    }

    public void setLendAndReturns(List<LendAndReturn> lendAndReturns) {
        this.lendAndReturns = lendAndReturns;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
