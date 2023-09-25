<%-- 
    Document   : createMember
    Created on : 24 Feb, 2023, 3:03:37 AM
    Author     : Darie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>Create New Member</title> 
    </head> 
    <body> 
        <%@include file="layout/header.jsp" %> 
        <h1>New Member</h1> 
        <form action="doCreateMember" method="POST"> 
            <label for="firstName">First Name: </label><input type="text" id="firstName" 
                                                   name="firstName" /><br /> 
            <label for="lastName">Last Name: </label><input type="text" id="lastName" 
                                                   name="lastName" /><br />
            Gender: <select name="gender"> 
                <option value='M'>M</option> 
                <option value='F'>F</option> 
            </select><br> 
            <label for="age">Age: </label><input type="text" id="age" 
                                                   name="age" /><br />
            <label for="identityNo">Identity No: </label><input type="text" id="identityNo" 
                                                   name="identityNo" /><br />
            <label for="phone">Phone Number: </label><input type="text" id="phone" 
                                                   name="phone" /><br />
            <label for="address">Address: </label><input type="text" id="address" 
                                                   name="address" /><br />
            <input type="submit" value="Submit" /> 
        </form> 
    </body> 
</html> 
