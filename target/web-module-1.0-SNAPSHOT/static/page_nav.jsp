<%--
  Created by IntelliJ IDEA.
  User: GuiltyCrown
  Date: 2022/8/14
  Time: 3:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="page_nav">
    <%if (currentPageNum > 1) {%>
    <a href="<%=url%>&pageNum=<%=1%>">首页</a>
    <a href="<%=url%>&pageNum=<%=currentPageNum -1%>">上一页</a>
    <%}%>

    <%
        int[] pageList = bookPage.getPageList();
        if (pageList != null)
            for (int i : pageList) {

                if (i == currentPageNum) {%>
    <a href="<%=url%>&pageNum=<%=i%>">[<%=i%>]</a>
    <%} else {%>
    <a href="<%=url%>&pageNum=<%=i%>"><%=i%>
    </a>
    <%}%>
    <%
            }
    %>
    <%if (currentPageNum < totalPage) {%>

    <a href="<%=url%>&pageNum=<%=currentPageNum + 1%>">下一页</a>
    <a href="<%=url%>&pageNum=<%=totalPage%>">末页</a>
    <%}%>
    共<%=totalPage%>页，<%=countPage%>条记录 到第<input
        value="<%=currentPageNum%>" name="pn"
        id="pn_input"/>页
    <input type="button" value="确定">
    <script>
        $("input[type = button]").click(function () {
            let val = $("#pn_input").val();
            if (val < 1)
                val = 1
            if (val ><%=totalPage%>)
                val = <%=totalPage%>
                    location.href = "<%=url%>&pageNum=" + val;
        })
    </script>
</div>