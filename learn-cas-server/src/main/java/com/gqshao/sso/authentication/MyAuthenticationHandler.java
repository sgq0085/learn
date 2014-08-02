package com.gqshao.sso.authentication;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gqshao.sso.credential.MyCredential;
import org.jasig.cas.authentication.*;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public class MyAuthenticationHandler implements AuthenticationHandler {

    private String name;

    @Override
    public String getName() {
        return this.name != null ? this.name : getClass().getSimpleName();
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof MyCredential;
    }

    @Override
    public HandlerResult authenticate(Credential credential) throws GeneralSecurityException, PreventedException {
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("中文KEY", "中文value");

        List<String> list = Lists.newArrayList();
        list.add("value1");
        list.add("value2");
        list.add("value3");
        attributes.put("list", list);

        Principal principal = new SimplePrincipal(credential.getId(), attributes);

        return new HandlerResult(this, new BasicCredentialMetaData(credential), principal);
    }


}