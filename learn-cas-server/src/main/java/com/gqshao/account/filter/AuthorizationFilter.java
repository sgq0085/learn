package com.gqshao.account.filter;


import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class AuthorizationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Message message = JAXRSUtils.getCurrentMessage();
        AuthorizationPolicy policy = (AuthorizationPolicy) message.get(AuthorizationPolicy.class);
        if (policy == null) {
            containerRequestContext.abortWith(Response.status(401).header("WWW-Authenticate", "Basic realm=\"PTServer\"").build());
            return;
        }
        String username = policy.getUserName();
        String password = policy.getPassword();
    }
}
