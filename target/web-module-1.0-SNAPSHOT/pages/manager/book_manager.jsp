<%@ page import="gc.webPro.pojo.Book" %>
<%@ page import="gc.webPro.pojo.Pages" %>
<%@ page import="java.util.List" %>
<%@ page import="gc.webPro.pojo.Pages" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%@include file="../../static/link.jsp" %>
    <script>
        $(function () {
            $(".delete").click(deleteFunc);

            function deleteFunc() {
                let firstName = $(this).parent().parent().find(":first").text();
                return confirm("你确定要删除[" + firstName + "]的记录吗");
            }
        })

    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>
    <%@ include file="common/header.jsp" %>
</div>
<% %>
<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>
        <% Pages<Book> bookPage = (Pages<Book>) request.getAttribute("pages");
            if (bookPage != null) {
                int currentPageNum = bookPage.getPageNum();
                int totalPage = bookPage.getPageRecordTotal();
                int countPage = bookPage.getPageCountTotal();
                String url = bookPage.getPageUrl();
                List<Book> list = bookPage.getPageBook();
        %>
        <%
            if (list != null)
                for (Book book : list) {%>
        <tr>
            <td><%= book.getName()%></td>
            <td><%= book.getPrice()%></td>
            <td><%= book.getAuthor()%></td>
            <td><%= book.getSales()%></td>
            <td><%= book.getStock()%></td>
            <td><a href="Manager/BookServlet?action=getBook&id=<%=book.getId()%>&pageNum=<%=currentPageNum%>">修改</a></td>
            <td><a class="delete" href="Manager/BookServlet?action=delete&id=<%=book.getId()%>&pageNum=<%=currentPageNum%>">删除</a></td>
        </tr>
        <%}%>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_edit.jsp?action=add&pageNum=<%=totalPage%>">添加图书</a></td>
        </tr>
    </table>

    <%@include file="/static/page_nav.jsp" %>

    <%}%>

</div>
<%@include file="../../static/footer.jsp" %>
</body>
</html>
