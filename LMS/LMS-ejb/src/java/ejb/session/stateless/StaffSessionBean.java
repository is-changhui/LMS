/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Staff;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.DeleteStaffException;
import util.exception.InvalidLoginException;
import util.exception.StaffExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Darie
 */
@Stateless
public class StaffSessionBean implements StaffSessionBeanRemote, StaffSessionBeanLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public StaffSessionBean() {
    }

    @Override
    public Staff createNewStaff(Staff newStaff) throws StaffExistException, UnknownPersistenceException {

        try {
            em.persist(newStaff);
            em.flush();
            return newStaff;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new StaffExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Staff retrieveStaffById(Long staffId) throws StaffNotFoundException {

        Staff staff = em.find(Staff.class, staffId);

        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("StaffId [" + staffId + "] not found in system!");
        }
    }

    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException {

        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Staff) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff username [" + username + "] does not exist!");
        }
    }

    @Override
    public List<Staff> retrieveAllStaffs() {
        Query query = em.createQuery("SELECT s FROM Staff s");
        return query.getResultList();
    }
    
    

    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException, DeleteStaffException {
        Staff staffToRemove = retrieveStaffById(staffId);

        if (staffToRemove != null) {
            em.remove(staffToRemove);
        } else {
            throw new DeleteStaffException("Staff ID [" + staffId + "] cannot be deleted because it does not exist in the system!");
        }
    }

    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginException {

        try {
            Staff staff = retrieveStaffByUsername(username);

            if (staff.getPassword().equals(password)) {
                return staff;
            } else {
                throw new InvalidLoginException("Staff username does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginException("Staff username does not exist or invalid password!");
        }
    }

}
