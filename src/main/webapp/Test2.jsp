<%--
  Created by IntelliJ IDEA.
  User: GuiltyCrown
  Date: 2022/8/12
  Time: 3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${requestScope.AA}
${requestScope.username}
${param.method}
<%=request.getAttribute("username")%>
<%=request.getParameter("action")%>
</body>
</html>
