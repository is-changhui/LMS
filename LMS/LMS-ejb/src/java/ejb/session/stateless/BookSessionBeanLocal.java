/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookExistException;
import util.exception.BookNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface BookSessionBeanLocal {

    public Book createNewBook(Book newBook) throws BookExistException, UnknownPersistenceException;

    public Book retrieveBookById(Long bookId) throws BookNotFoundException;

    public Book retrieveBookByTitle(String title) throws BookNotFoundException;

    public List<Book> retrieveAllBooks();
    
}
