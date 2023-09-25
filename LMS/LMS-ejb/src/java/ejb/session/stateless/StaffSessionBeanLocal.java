/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Staff;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteStaffException;
import util.exception.InvalidLoginException;
import util.exception.StaffExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Local
public interface StaffSessionBeanLocal {

    public Staff createNewStaff(Staff newStaff) throws StaffExistException, UnknownPersistenceException;

    public Staff retrieveStaffById(Long staffId) throws StaffNotFoundException;

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public List<Staff> retrieveAllStaffs();

    public void deleteStaff(Long staffId) throws StaffNotFoundException, DeleteStaffException;

    public Staff staffLogin(String username, String password) throws InvalidLoginException;
    
}
