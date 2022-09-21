<%@ page import="gc.webPro.pojo.Cart" %>
<%@ page import="gc.webPro.pojo.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <%@include file="../../static/link.jsp" %>
    <script>
        $(function () {
            $("a.deleteItem").click(deleteFunc);

            function deleteFunc() {
                let firstName = $(this).parent().parent().find(":first").text();
                return confirm("你确定要删除[" + firstName + "]的记录吗");
            }

            $("input.updateCount").change(function (){
                let firstName = $(this).parent().parent().find(":first").text();
                if(confirm("你确定要修改["+ firstName +"]的数量吗")){
                    location.href = "/demo/CartServlet?action=updateCartItem&id="+$(this).attr("bookId") +"&count="+this.value;
                }else {
                    this.value = this.defaultValue;
                }
            })
        })
    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">购物车</span>
    <div>
        <span>欢迎<span class="um_span">${sessionScope.username}</span>光临尚硅谷书城</span>
        <a href="pages/order/order.html">我的订单</a>
        <a href="index.jsp">注销</a>&nbsp;&nbsp;
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">

    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
            <td>操作</td>
        </tr>
        <% Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                Map<Integer, CartItem> map = cart.getMap();

                for (Map.Entry<Integer, CartItem> cartEntry : map.entrySet()) {
                    CartItem cartItem = cartEntry.getValue();
        %>
        <tr>
            <td><%=cartItem.getName()%></td>
            <td><input class="updateCount" type="text" bookId = "<%=cartItem.getId()%>"  value="<%=cartItem.getCount()%>" width="25px"/></td>
            <td><%=cartItem.getPrice()%></td>
            <td><%=cartItem.getTotalPrice()%></td>
            <td><a class="deleteItem" href="CartServlet?action=deleteCartItem&id=<%=cartItem.getId()%>">删除</a></td>
        </tr>
        <%}%>

    </table>

    <%if (!cart.getMap().isEmpty()) {%>
    <div class="cart_info">
        <span class="cart_span">购物车中共有<span class="b_count"><%=cart.getTotalCount()%></span>件商品</span>
        <span class="cart_span">总金额<span
                class="b_price"><%=new DecimalFormat("0.00").format(cart.getTotalPrice())%></span>元</span>
        <span class="cart_span"><a href="CartServlet?action=deleteCart">清空购物车</a></span>
        <span class="cart_span"><a href="pages/cart/checkout.html">去结账</a></span>
    </div>
    <%}%>
    <%}%>
</div>

<%@include file="../../static/footer.jsp" %>
</body>
</html>