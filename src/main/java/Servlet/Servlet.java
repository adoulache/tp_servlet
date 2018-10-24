/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Liste des client dans un State aux States</h1>");
            try {   // Trouver la valeur du paramètre HTTP customerID
                 String val = request.getParameter("state");
                 if (val == null) {
                     throw new Exception("La paramètre state n'a pas été transmis");
                 }
                 // on doit convertir cette valeur en entier (attention aux exceptions !)
                 String state = String.valueOf(val);

                 DAO dao = new DAO(DataSourceFactory.getDataSource());
                 List<CustomerEntity> customers = new LinkedList<>();
                 if (state == null) {
                     throw new Exception("Etat inconnu");
                 }
                 
                 try{
                  customers = dao.customersInState(state);
                  out.printf("<table style=\"border: 1px solid black\";>"
                                + "<tr>"
                                    + "<th>ID</th>"
                                    + "<th>Name</th>"
                                    + "<th>Address</th>"
                                + "</tr>" );
                  for(CustomerEntity customer : customers){
                        out.printf("<tr>"
                                    + "<th style=\"border: 1px solid black\";>" + customer.getCustomerId() + "</th>"
                                    + "<th style=\"border: 1px solid black\";>" + customer.getName() + "</th>"
                                    + "<th style=\"border: 1px solid black\";>" + customer.getAddressLine1() + "</th>"
                                + "</tr>");
                  }
                  out.printf("</table>");
                 }catch(DAOException ex){
                    throw new Exception("Probleme avec la base de données : ", ex);
                 }
             } catch (Exception e) {
                 out.printf("Erreur : %s", e.getMessage());
             }
            out.println("</body>");
            out.println("</html>");
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
