<%@ page contentType="text/html; charset=GB2312" %>
<html>
<head></head>
<body>

<%!
    private int initVar = 0;
    private int serviceVar = 0;
    private int destroyVar = 0;
%>

<%!
    public void jspInit() {
        initVar++;
        System.out.println("jspInit(): JSP被初始化了" + initVar + "次");
    }

    public void jspDestroy() {
        destroyVar++;
        System.out.println("jspDestroy(): JSP被销毁了" + destroyVar + "次");
    }
%>

<%
    serviceVar++;
    System.out.println("_jspService(): JSP共响应了" + serviceVar + "次请求");

    String content1 = "初始化次数 :  " + initVar;
    String content2 = "响应客户请求次数 : " + serviceVar;
    String content3 = "销毁次数 : " + destroyVar;

    // 自动刷新 Response.setHeader("Refresh","time; URL=url") 时间单位秒
    response.setHeader("Refresh", "3;URL=http://localhost/test/?forward_by_router=commons/test1_init");
%>
<!-- 注释  HTML注释，通过浏览器查看网页源代码时可以看见注释内容-->
<%-- 注释 JSP注释，注释内容不会被发送至浏览器甚至不会被编译--%>
<h1><%=content1%>
</h1>

<h1><%=content2%>
</h1>

<h1><%=content3%>
</h1>

<h1>${param["forward_by_router"]}</h1>

<h1>${param.forward_by_router}</h1>
</body>
</html>