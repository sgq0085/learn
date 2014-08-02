package com.gqshao.test.sys.authentication.filter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.shiro.authc.*;
import org.apache.shiro.subject.*;
import org.apache.shiro.web.filter.authc.*;
import org.apache.shiro.web.util.*;
import org.slf4j.*;

import com.gqshao.test.sys.authentication.domain.*;

public class CustomFormAuthenticationFilter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

    public static final String DEFAULT_LOGINNAME_PARAM = "loginName";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";

    private String loginNameParam = DEFAULT_LOGINNAME_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;
    private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;

    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

    public CustomFormAuthenticationFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        if (log.isTraceEnabled()) {
            log.trace("Adding login url to applied paths.");
        }
        this.appliedPaths.put(getLoginUrl(), null);
    }

    /**
     * 在访问被拒绝
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * 创建自定义的令牌
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String loginName = getLoginName(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        // if (request instanceof HttpServletRequest && StringUtils.isBlank(loginName)
        // && StringUtils.isBlank(password)) {
        // HttpServletRequest httpRequest = (HttpServletRequest) request;
        // Cookie[] cookies = httpRequest.getCookies();
        // Map<String, String> map = Maps.newHashMap();
        // for (Cookie cookie : cookies) {
        // map.put(cookie.getName(), Base64.encodeBase64String(Encodes.decodeBase64(cookie.getValue())));
        // }
        // String csn = map.get("loginName");
        // String cspw = map.get("password");
        // if (StringUtils.isNotBlank(csn) && StringUtils.isNotBlank(cspw)) {
        // loginName = csn;
        // password = cspw;
        // }
        // } else if (response instanceof HttpServletResponse && StringUtils.isNotBlank(loginName)
        // && StringUtils.isNotBlank(password) && rememberMe) {
        // HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Cookie csn = new Cookie("SSO", Encodes.encodeBase64(loginName.getBytes()));
        // // 关闭浏览器后，cookie立即失效
        // // csn.setMaxAge(-1);
        // csn.setMaxAge(3600);
        // csn.setPath("/");
        // Cookie cspw = new Cookie("SSO", Encodes.encodeBase64(password.getBytes()));
        // csn.setMaxAge(3600);
        // cspw.setPath("/");
        // httpResponse.addCookie(csn);
        // httpResponse.addCookie(cspw);
        // }
        return new CustomToken(loginName, password, rememberMe, host);
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    protected boolean isRememberMe(ServletRequest request) {
        return WebUtils.isTrue(request, getRememberMeParam());
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
            ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
            ServletRequest request, ServletResponse response) {
        setFailureAttribute(request, e);
        return true;
    }

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
    }

    protected String getLoginName(ServletRequest request) {
        return WebUtils.getCleanParam(request, getLoginNameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }

    public String getLoginNameParam() {
        return loginNameParam;
    }

    public void setLoginNameParam(String loginNameParam) {
        this.loginNameParam = loginNameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getRememberMeParam() {
        return rememberMeParam;
    }

    public void setRememberMeParam(String rememberMeParam) {
        this.rememberMeParam = rememberMeParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

}
// @Override
// protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
// throws Exception {
// return null;
// }
//
// @Override
// protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
// return false;
// }
