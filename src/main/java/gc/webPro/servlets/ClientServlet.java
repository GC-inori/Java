package gc.webPro.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ClientServlet", value = "/Client/ClientServlet")
public class ClientServlet extends BookServlet {//继承BookServlet
//    @Override
//    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        super.page(request, response);
//    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//重写但还是调用父类
        super.page(request,response);//直接调用父类的page


        //执行顺序 先调用抽象基类的doGet->抽象基类的doPost->反射调用自己类page->父类BookServlet的page->自己类的requestDispatcher->转发到index.jsp
    }

    @Override
    protected void requestDispatcher(String path, String suffix, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(path);//如果没错应该是pages/manager/book_manager

        super.requestDispatcher("index", suffix, request, response);
       // requestDispatcher("/pages/manager/book_manager", "jsp", request, response);
    }

    @Override
    protected String getUrl() {
        return "Client/ClientServlet?action=page";
    }

//    protected void pricePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String minPrice = request.getParameter("minPrice");
//        String maxPrice = request.getParameter("maxPrice");
//
//
//    }
}
