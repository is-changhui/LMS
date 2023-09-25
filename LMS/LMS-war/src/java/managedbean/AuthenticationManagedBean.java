/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Darie
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    private String userName = null;
    private String password = null;
    private boolean loggedIn = false;
    private int staffId = -1;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public String login() {
        //by right supposed to use a session bean to  
        //do validation here 
        //... 

        //simulate userName/password 
        if (userName.equals("eric") && password.equals("password")) {
            //login successful 
            
            loggedIn = true;

            //store the logged in user id 
            staffId = 1;

            //do redirect 
//            return "/secret/secret.xhtml?faces-redirect=true";
            return "index.xhtml?faces-redirect=true";
        } else if (userName.equals("sarah") && password.equals("password")) {
            //login successful 
            
            loggedIn = true;

            //store the logged in user id 
            staffId = 2;

            //do redirect 
            return "index.xhtml?faces-redirect=true";
        } else {
            //login failed 
            userName = null;
            password = null;
            loggedIn = false;
            staffId = -1;
            return "login.xhtml";
        }
    }

    public String logout() {
        userName = null;
        password = null;
        loggedIn = false;
        staffId = -1;

        return "default.xhtml?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
