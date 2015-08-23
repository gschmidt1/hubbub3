package edu.acc.j2ee.hubbub3;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");   
        if (action == null) action = "timeline";
        switch (action) {
            case "timeline": action = timeline(request); break;
            case "login": action = login(request); break;
            case "post": action = post(request); break;
            case "logout": action = logout(request); break;
            default: action = "timeline";
        }
        request.getRequestDispatcher(action + ".jsp").forward(request,response);
    }
    
    private String timeline(HttpServletRequest request) {
        HubbubDAO db = (HubbubDAO) getServletContext().getAttribute("db");
        List<Post> posts = db.getSortedPosts();
        request.setAttribute("posts", posts);
        return "timeline";
    }
    
    private String login(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) return "login";
        String userName = request.getParameter("user");
        String password = request.getParameter("pass");
        LoginBean bean = new LoginBean(userName, password);
        if (LoginValidator.validate(bean)) {
            HubbubDAO db = (HubbubDAO) getServletContext().getAttribute("db");
            User user = db.authenticate(userName, password);
            if (user == null) {
                request.setAttribute("flash", "Access Denied");
                return "login";
            } else {
                request.getSession().setAttribute("user", user);
                return timeline(request);
            }
        } else {
            request.setAttribute("flash", "Invalid user name or password");
            return "login";
        }
    }
    
    private String post(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) return "post";
        String content = request.getParameter("content");
        if (content == null || content.length() < 1 || content.length() > 140) {
            request.setAttribute("flash", "Content must be between 1 and 140 characters.");
            request.setAttribute("content", content);
            return "post";
        }
        content = StringEscapeUtils.escapeHtml4(content);
        content = content.replace("'", "&#39;");
        HubbubDAO db = (HubbubDAO) this.getServletContext().getAttribute("db");
        User author = (User) request.getSession().getAttribute("user");
        db.addPost(content, author);
        return timeline(request);
    }
    
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return timeline(request);
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
