/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ejb.session.stateless.MemberSessionBeanLocal;
import entity.Member;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manager.MemberManager;

/**
 *
 * @author Darie
 */
public class Controller extends HttpServlet {

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            MemberManager memberManager = new MemberManager(memberSessionBeanLocal);
            String path = request.getPathInfo();
            path = path.split("/")[1];

            switch (path) {
                case "createMember":
                    // display page - do nothing 
                    break;
                case "doCreateMember": {
                    // Handle a create member action 

                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    Character gender = request.getParameter("gender").charAt(0);
                    Integer age = Integer.parseInt(request.getParameter("age"));
                    String identityNo = request.getParameter("identityNo");
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");

                    memberManager.createMember(firstName, lastName, gender, age, identityNo, phone, address);

                    //redirect to the customer listing page 
                    //note that for sendRedirect we should include the context path 
                    response.sendRedirect(request.getContextPath()
                            + "/Controller/searchMembers");
                    return;
                }
                case "searchMembers": {
                    String field = request.getParameter("field");
                    String searchValue = request.getParameter("searchValue");
                    System.out.println("#searchValue : " + searchValue);

                    if (field == null || searchValue == null || searchValue.equals("")) {
                        List<Member> members = memberManager.searchMembers();
                        request.setAttribute("members", members);
                    } else {
                        List<Member> members = null;
                        switch (field) {
                            case "FIRSTNAME":
                                members = memberManager.searchMembersByFirstName(searchValue);
                                System.out.println("#firstName : " + searchValue);
                                break;
                            default:
                                members = memberManager.searchMembers();
                                break;
                        }

                        request.setAttribute("members", members);
                    }
                }
            }
            //forward to <path>.jsp 
            //don’t need to include context path (automatically added) 
            request.getRequestDispatcher("/" + path + ".jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
