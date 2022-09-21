<%--
  Created by IntelliJ IDEA.
  User: GuiltyCrown
  Date: 2022/8/9
  Time: 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%request.setAttribute("AA","AA");
    request.setAttribute("username","BB");%>
${param.username}
${requestScope.username}
${requestScope.AA}
<a href="/demo/Test2.jsp?method=list&username=<%=request.getAttribute("username")%>">跳转</a>
<form action="Test2.jsp?action=page" method="post">
    <input type="submit" value="提交">
</form>
<%--<jsp:forward page="/ServletLogin"></jsp:forward>--%>
</body>
</html>
