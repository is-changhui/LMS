<%-- 
    Document   : searchMembers
    Created on : 24 Feb, 2023, 3:07:14 AM
    Author     : Darie
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>Search Members</title> 
    </head> 
    <body> 
        <%@include file="layout/header.jsp" %> 
        <h1>Members</h1> 
        <form action="searchMembers" method="POST"> 
            <select name="field" id="field"> 
                <option value="FIRSTNAME">First Name</option> 
                <c:forEach var="field" items="${fields}"> 
                    <option value="${field}">${field}</option> 
                </c:forEach> 
            </select> 
            <input type="text" name="searchValue" /> 
            <input type="submit" value="Search" /> 
        </form> 
        <c:forEach var="member" items="${members}"> 
            <div style="border: 1px brown solid; padding: 10px;"> 
                Id: ${member.memberId}<br /> 
                First Name: ${member.firstName}<br /> 
                Last Name: ${member.lastName}<br /> 
                Gender: ${member.gender}<br />
                Age: ${member.age}<br /> 
                Identity No: ${member.identityNo}<br /> 
                Phone: ${member.phone}<br /> 
                Address: ${member.address}<br />
            </div> 
        </c:forEach> 
    </body> 
</html> 
