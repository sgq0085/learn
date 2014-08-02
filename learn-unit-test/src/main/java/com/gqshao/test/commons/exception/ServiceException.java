package com.gqshao.test.commons.exception;

import java.util.Map;

/**
 * <pre>
 * 功能说明： Service层公用的Exception.
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 可传入log，将错误信息写入日志
 * </pre>
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -2381250718501203811L;

    private String redirect;
    private Throwable throwable;
    private Map<String, Object> params;

    /**
     * 返回JSON
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 返回JSON，并记录Exception信息
     *
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable throwable) {
        super(message);
        this.setThrowable(throwable);
    }

    /**
     * 跳转到JSP页面
     *
     * @param redirect
     * @param model
     */
    public ServiceException(String redirect, Map<String, Object> params) {
        this.setRedirect(redirect);
        this.setParams(params);
    }

    /**
     * 跳转到JSP页面，并记录Exception信息
     *
     * @param message
     * @param cause
     */
    public ServiceException(String redirect, Map<String, Object> params, Throwable throwable) {
        this.setRedirect(redirect);
        this.setParams(params);
        this.setThrowable(throwable);
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
