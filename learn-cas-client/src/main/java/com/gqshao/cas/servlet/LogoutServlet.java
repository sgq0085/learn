package com.gqshao.cas.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 3149392257565960001L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.getSession().invalidate();
        String path = request.getContextPath() + "/index.jsp";
        response.sendRedirect(request.getContextPath());
    }
}
