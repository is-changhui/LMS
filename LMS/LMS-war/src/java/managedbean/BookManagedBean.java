/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.BookSessionBeanLocal;
import entity.Book;
import entity.LendAndReturn;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import util.exception.BookExistException;
import util.exception.BookNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Named(value = "bookManagedBean")
@RequestScoped
public class BookManagedBean {

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    private String title;
    private String isbn;
    private String author;

    private List<LendAndReturn> lendAndReturns;

    private List<Book> books;

    /**
     * Creates a new instance of bookManagedBean
     */
    public BookManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        setBooks(bookSessionBeanLocal.retrieveAllBooks());
    }

    public void addBook(ActionEvent evt) throws BookExistException, UnknownPersistenceException {

        Book book = new Book();
        book.setTitle(getTitle());
        book.setIsbn(getIsbn());
        book.setAuthor(getAuthor());

        Book m = bookSessionBeanLocal.createNewBook(book);
    }
    
    public Book completeBook(String query) throws BookNotFoundException {
        return bookSessionBeanLocal.retrieveBookByTitle(query);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the lendAndReturns
     */
    public List<LendAndReturn> getLendAndReturns() {
        return lendAndReturns;
    }

    /**
     * @param lendAndReturns the lendAndReturns to set
     */
    public void setLendAndReturns(List<LendAndReturn> lendAndReturns) {
        this.lendAndReturns = lendAndReturns;
    }

    /**
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
