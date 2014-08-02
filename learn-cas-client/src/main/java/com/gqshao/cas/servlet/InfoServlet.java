package com.gqshao.cas.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;

public class InfoServlet extends HttpServlet {
    private static final long serialVersionUID = -8390353826537804945L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession();
        AttributePrincipal principal = (AttributePrincipal) AssertionHolder.getAssertion().getPrincipal();
        if (principal != null) {
            String id = principal.getName();
            System.out.println("id ====> " + id);
            Map<String, Object> attrs = principal.getAttributes();
            for (String key : attrs.keySet()) {
                System.out.print(key + " : " + attrs.get(key));
                System.out.println(" " + attrs.get(key).getClass());
            }
        }

    }
}
