<%@ page import="gc.webPro.pojo.Book" %>
<%@ page import="gc.webPro.pojo.Pages" %>
<%@ page import="java.util.List" %>
<%@ page import="gc.webPro.pojo.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if (request.getAttribute("pages") == null) {%> <!--没有找到分页对象就直接跳转servlet去找-->
<jsp:forward page="Client/ClientServlet?action=page"></jsp:forward>
<%}%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>书城首页</title>
    <%@include file="/static/link.jsp" %>
    <script>
        $(function () {
            $(".book_add button").click(function () {

                let id = $(this).attr("id");
                let name = $(this).attr("name");
                let price = $(this).attr("price");

                location.href = "CartServlet?action=addToCart&id=" + id + "&name=" + name + "&price=" + price;
            })
        })
    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">网上书城</span>
    <div>
        <% if (session.getAttribute("username") != null) { %>
        <span>欢迎<span class="um_span">${sessionScope.username}</span>光临尚硅谷书城</span>
        <a href="pages/order/order.html">我的订单</a>
        <a href="pages/cart/cart.jsp">购物车</a>
        <a href="pages/manager/manager.jsp">后台管理</a>
        <a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
        <%} else {%>
        <a href="pages/user/login.html">登录</a> |
        <a href="pages/user/register.jsp">注册</a> &nbsp;&nbsp;
        <%}%>
    </div>
</div>
<div id="main">
    <div id="book">
        <div class="book_cond">
            <form action="Client/ClientServlet?action=page" method="post">
                价格：<input id="min" type="text" name="minPrice" value="${requestScope.minPrice}"> 元 -
                <input id="max" type="text" name="maxPrice" value="${requestScope.maxPrice}"> 元
                <input type="submit" value="查询"/>
            </form>
        </div>
        <%Cart cart = (Cart) session.getAttribute("cart");
            String bookName = (String) session.getAttribute("bookName");
        %>
        <div style="text-align: center">
            <%if(cart!=null) {%>
                 <span>您的购物车中有<%=cart.getTotalCount()%>件商品</span>
            <%}%>
            <div>
                <%if(bookName!=null) {%>
                <span>您刚刚将<span style="color: red">[<%=bookName%>]</span>加入到了购物车中</span>
                <%}%>
            </div>
        </div>
        <% Pages<Book> bookPage = (Pages<Book>) request.getAttribute("pages");
            if (bookPage != null) {
                int currentPageNum = bookPage.getPageNum();
                int totalPage = bookPage.getPageRecordTotal();
                int countPage = bookPage.getPageCountTotal();
                String url = bookPage.getPageUrl();
                List<Book> list = bookPage.getPageBook();
                if (list != null)
                    for (Book book : list) {%>
        <div class="b_list">
            <div class="img_div">
                <img class="book_img" alt="" src="static/img/default.jpg"/>
            </div>
            <div class="book_info">
                <div class="book_name">
                    <span class="sp1">书名:</span>
                    <span class="sp2"><%=book.getName()%></span>
                </div>
                <div class="book_author">
                    <span class="sp1">作者:</span>
                    <span class="sp2"><%=book.getAuthor()%></span>
                </div>
                <div class="book_price">
                    <span class="sp1">价格:</span>
                    <span class="sp2">￥<%=book.getPrice()%></span>
                </div>
                <div class="book_sales">
                    <span class="sp1">销量:</span>
                    <span class="sp2"><%=book.getSales()%></span>
                </div>
                <div class="book_amount">
                    <span class="sp1">库存:</span>
                    <span class="sp2"><%=book.getStock()%></span>
                </div>
                <div class="book_add">
                    <button id="<%=book.getId()%>" name="<%=book.getName()%>" price="<%=book.getPrice()%>">加入购物车
                    </button>
                </div>
            </div>
        </div>
        <%--注意大括号 多打几个div就会让布局出问题--%>
        <%}%>
    </div>

    <%@include file="/static/page_nav.jsp" %>

    <%}%>
</div>

<%@include file="/static/footer.jsp" %>
</body>
</html>