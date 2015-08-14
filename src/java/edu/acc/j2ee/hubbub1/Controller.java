package edu.acc.j2ee.hubbub1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HubbubDAO db = (HubbubDAO)getServletContext().getAttribute("db");
        List<Post> posts = db.getSortedPosts();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("timeline.jsp").forward(request, response);
    }
}
