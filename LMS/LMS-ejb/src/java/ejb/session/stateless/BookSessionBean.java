/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.BookExistException;
import util.exception.BookNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class BookSessionBean implements BookSessionBeanRemote, BookSessionBeanLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public BookSessionBean() {
    }

    @Override
    public Book createNewBook(Book newBook) throws BookExistException, UnknownPersistenceException {

        try {
            em.persist(newBook);
            em.flush();
            return newBook;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new BookExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Book retrieveBookById(Long bookId) throws BookNotFoundException {

        Book book = em.find(Book.class, bookId);

        if (book != null) {
            return book;
        } else {
            throw new BookNotFoundException("BookId [" + bookId + "] not found in system!");
        }
    }

    @Override
    public Book retrieveBookByTitle(String title) throws BookNotFoundException {

        Query query = em.createQuery("SELECT b FROM Book b WHERE b.title = :inTitle");
        query.setParameter("inTitle", title);

        try {
            return (Book) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookNotFoundException("Book title [" + title + "] does not exist!");
        }
    }

    @Override
    public List<Book> retrieveAllBooks() {
        Query query = em.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }
}
