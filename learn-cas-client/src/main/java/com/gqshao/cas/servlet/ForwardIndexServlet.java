package com.gqshao.cas.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Tomcat web.xml中welcome-file不能指向WEB-INF下的JSP，只能通过Servlet映射过去
 */
public class ForwardIndexServlet extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}