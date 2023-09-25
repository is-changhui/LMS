/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.MemberSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Book;
import entity.Member;
import entity.Staff;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.BookExistException;
import util.exception.BookNotFoundException;
import util.exception.MemberExistException;
import util.exception.MemberNotFoundException;
import util.exception.StaffExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    @EJB
    private StaffSessionBeanLocal staffSessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            staffSessionBeanLocal.retrieveStaffByUsername("eric");
            staffSessionBeanLocal.retrieveStaffByUsername("sarah");
            bookSessionBeanLocal.retrieveBookByTitle("Anna Karenina");
            bookSessionBeanLocal.retrieveBookByTitle("Madame Bovary");
            bookSessionBeanLocal.retrieveBookByTitle("Hamlet");
            bookSessionBeanLocal.retrieveBookByTitle("The Hobbit");
            bookSessionBeanLocal.retrieveBookByTitle("Great Expectations");
            bookSessionBeanLocal.retrieveBookByTitle("Pride and Prejudice");
            bookSessionBeanLocal.retrieveBookByTitle("Wuthering Heights");
            memberSessionBeanLocal.retrieveMemberByIdentityNo("S8900678A");
            memberSessionBeanLocal.retrieveMemberByIdentityNo("S8581028X");
        } catch (StaffNotFoundException | BookNotFoundException | MemberNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            staffSessionBeanLocal.createNewStaff(new Staff("Eric", "Some", "eric", "password"));
            staffSessionBeanLocal.createNewStaff(new Staff("Sarah", "Brightman", "sarah", "password"));
            bookSessionBeanLocal.createNewBook(new Book("Anna Karenina", "0451528611", "Leo Tolstoy"));
            bookSessionBeanLocal.createNewBook(new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert"));
            bookSessionBeanLocal.createNewBook(new Book("Hamlet", "1980625026", "William Shakespeare"));
            bookSessionBeanLocal.createNewBook(new Book("The Hobbit", "9780007458424", "J R R Tolkien"));
            bookSessionBeanLocal.createNewBook(new Book("Great Expectations", "1521853592", "Charles Dickens"));
            bookSessionBeanLocal.createNewBook(new Book("Pride and Prejudice", "979-8653642272", "Jane Austen"));
            bookSessionBeanLocal.createNewBook(new Book("Wuthering Heights", "3961300224", "Emily Brontë"));
            memberSessionBeanLocal.createNewMember(new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3"));
            memberSessionBeanLocal.createNewMember(new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr"));
        } catch (StaffExistException | BookExistException | MemberExistException | UnknownPersistenceException ex) {
            ex.printStackTrace();
        }
    }
}
