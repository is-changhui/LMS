/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Darie
 */
public class BookOnLoanException extends Exception {

    /**
     * Creates a new instance of <code>BookOnLoanException</code> without detail
     * message.
     */
    public BookOnLoanException() {
    }

    /**
     * Constructs an instance of <code>BookOnLoanException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookOnLoanException(String msg) {
        super(msg);
    }
}
