package com.gqshao.sso.authentication;

import com.google.common.collect.Maps;
import com.gqshao.sso.credential.MyCredential;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.authentication.*;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class MyAuthenticationHandler implements AuthenticationHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof MyCredential;
    }

    /**
     * 完成登录认证，封装认证结果Principal
     *
     * @param credential 认证凭证
     * @return HandlerResult
     */
    @Override
    public HandlerResult authenticate(Credential credential) throws GeneralSecurityException, PreventedException {
        MyCredential myCredential = (MyCredential) credential;
        if (credential == null) {
            throw new FailedLoginException();
        } else if (StringUtils.isBlank(credential.getId())) {
            logger.debug("{} was not found in the map.", credential);
            throw new AccountNotFoundException(credential + " not found in backing map.");
        }

        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("中文KEY", "中文value");
        attributes.put("空值key", "");
        attributes.put("custom", myCredential.getCustom());

        Principal principal = new SimplePrincipal(credential.getId(), attributes);
        return new HandlerResult(this, new BasicCredentialMetaData(credential), principal);
    }
}