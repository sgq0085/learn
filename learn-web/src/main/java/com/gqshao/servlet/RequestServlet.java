package com.gqshao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestServlet extends HttpServlet {

    private static final long serialVersionUID = -7936817351382556277L;

    /**
     * @param accept 浏览器支持的格式
     * @return 客户端浏览器接受的文件类型
     */
    private String getAccept(String accept) {
        StringBuffer buffer = new StringBuffer();
        if (accept.contains("image/gif")) buffer.append("GIF 文件, ");
        if (accept.contains("image/x-xbitmap")) buffer.append("BMP 文件, ");
        if (accept.contains("image/jpeg")) buffer.append("JPG 文件, ");
        if (accept.contains("application/vnd.ms-excel")) buffer.append("Excel 文件, ");
        if (accept.contains("application/vnd.ms-powerpoint")) buffer.append("PPT 文件, ");
        if (accept.contains("application/msword")) buffer.append("Word 文件, ");
        return buffer.toString().replaceAll(", $", "");
    }

    /**
     * @param locale
     * @return 语言环境名称
     */
    private String getLocale(Locale locale) {
        if (Locale.SIMPLIFIED_CHINESE.equals(locale)) return "简体中文";
        if (Locale.TRADITIONAL_CHINESE.equals(locale)) return "繁体中文";
        if (Locale.ENGLISH.equals(locale)) return "英文";
        if (Locale.US.equals(locale)) return "英文";
        if (Locale.JAPANESE.equals(locale)) return "日文";
        return "未知语言环境";
    }


    /**
     * @param userAgent 浏览器信息
     * @return 客户端浏览器信息
     */
    private String getNavigator(String userAgent) {
        if (userAgent.indexOf("Chrome") > 0) return "Chrome浏览器";
        if (userAgent.indexOf("Firefox") > 0) return "Firefox浏览器";
        if (userAgent.indexOf("MSIE") > 0) return "IE 浏览器";
        return "未知浏览器";
    }

    /**
     * @param userAgent 浏览器信息
     * @return 客户端操作系统
     */
    private String getOS(String userAgent) {
        if (userAgent.indexOf("Windows NT 6.1") > 0) return "Windows 7";
        if (userAgent.indexOf("Windows NT 5.1") > 0) return "Windows XP";
        if (userAgent.indexOf("Windows 98") > 0) return "Windows 98";
        if (userAgent.indexOf("Windows NT 5.0") > 0) return "Windows 2000";
        if (userAgent.indexOf("Linux") > 0) return "Linux";
        if (userAgent.indexOf("Unix") > 0) return "Unix";
        return "未知";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html");

        String authType = request.getAuthType();
        String localAddr = request.getLocalAddr();
        Locale locale = request.getLocale();
        String localName = request.getLocalName();
        String contextPath = request.getContextPath();
        int localPort = request.getLocalPort();
        String method = request.getMethod();
        String pathInfo = request.getPathInfo();
        String pathTranslated = request.getPathTranslated();
        String protocol = request.getProtocol();
        String queryString = request.getQueryString();
        String remoteAddr = request.getRemoteAddr();
        int port = request.getRemotePort();
        String remoteUser = request.getRemoteUser();
        String requestedSessionId = request.getRequestedSessionId();
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String servletPath = request.getServletPath();
        Principal userPrincipal = request.getUserPrincipal();

        // 浏览器支持的格式
        String accept = request.getHeader("accept");
        // 指从哪个页面进入
        String referer = request.getHeader("referer");
        // 浏览器信息
        String userAgent = request.getHeader("user-agent");

        String serverInfo = this.getServletContext().getServerInfo();

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");

        // 这里<title></title>之间的信息在浏览器中显示为标题
        out.println("  <HEAD><TITLE>Request Servlet</TITLE></HEAD>");
        out.println("  <style>body, font, td, div {font-size:12px; line-height:18px; }</style>");
        out.println("  <BODY>");

        out.println("<b>您的IP为</b> " + remoteAddr + "<b>，IP</b> " + remoteAddr + "<b>；您使用</b> " + getOS(userAgent) + " <b>操作系统</b>，" + getNavigator(userAgent) + " <b>。您使用</b> " + getLocale(locale) + "。<br/>");
        out.println("<b>服务器IP为</b> " + localAddr + "<b>，IP</b> " + localAddr + "<b>；服务器使用</b> " + serverPort + " <b>端口，您的浏览器使用了</b> " + port + " <b>端口访问本网页。</b><br/>");
        out.println("<b>服务器软件为</b>：" + serverInfo + "。<b>服务器名称为</b> " + localName + "。<br/>");
        out.println("<b>您的浏览器接受</b> " + getAccept(accept) + "。<br/>");
        out.println("<b>您从</b> " + referer + " <b>访问到该页面。</b><br/>");
        out.println("<b>使用的协议为</b> " + protocol + "。<b>URL协议头</b> " + scheme + "，<b>服务器名称</b> " + serverName + "，<b>您访问的URI为</b> " + requestURI + "。<br/>");
        out.println("<b>该 Servlet 路径为</b> " + servletPath + "，<b>该 Servlet 类名为</b> " + this.getClass().getName() + "。<br/>");
        out.println("<b>本应用程序在硬盘的根目录为</b> " + this.getServletContext().getRealPath("") + "，<b>网络相对路径为</b> " + contextPath + "。 <br/>");

        out.println("<br/>");

        out.println("<br/><br/><a href=" + requestURI + "> 点击刷新本页面 </a>");

        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

}
