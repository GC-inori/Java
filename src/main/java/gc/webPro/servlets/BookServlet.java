package gc.webPro.servlets;

import gc.webPro.pojo.Book;
import gc.webPro.pojo.Pages;
import gc.webPro.service.impl.BookServiceImpl;
import gc.webPro.utils.CommonUtil;
import gc.webPro.utils.RequestBeanUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static gc.webPro.pojo.Pages.PAGE_RANGE;
import static gc.webPro.pojo.Pages.PAGE_SIZE;

@WebServlet(name = "BookServlet", value = "/Manager/BookServlet")
public class BookServlet extends BaseServlet {

    private static final BookServiceImpl bookServiceImpl = BookServiceImpl.getBookServiceImplInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);//这个方法可以直接省略 因为没重写默认调父类的
    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pageNum = request.getParameter("pageNum");
        int pageNumInt = CommonUtil.StringToInt(pageNum, 1);
        Book book = RequestBeanUtil.SetBeanProperties(new Book(), request.getParameterMap());
        bookServiceImpl.addBook(book);

        response.sendRedirect("/demo/Manager/BookServlet?action=page&pageNum=" + ++pageNumInt);//重定向跳转到 避免F5刷新 重复上次请求
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pageNum = request.getParameter("pageNum");

        String id = request.getParameter("id");

        bookServiceImpl.deleteBook(Integer.parseInt(id));

        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String id = request.getParameter("id");

        String pageNum = request.getParameter("pageNum");

        Book book = RequestBeanUtil.SetBeanProperties(new Book(), request.getParameterMap());
        bookServiceImpl.updateBook(book);

        response.sendRedirect("/demo/Manager/BookServlet?action=page&pageNum=" + pageNum);
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int pageNum = CommonUtil.StringToInt(request.getParameter("pageNum"), 1);
        int pageSize = CommonUtil.StringToInt(request.getParameter("pageSize"), PAGE_SIZE);
        int pageRange = CommonUtil.StringToInt(request.getParameter("pageSize"), PAGE_RANGE);

        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String url = getUrl();//注意 如果子类重写了这个那么就会直接去调用子类的重写而不会执行父类的 这样就可以实现跳转多样性

        Pages<Book> bookPage;

        if (minPrice != null && maxPrice != null) {
            double minPriceDouble = CommonUtil.StringToDouble(minPrice, 0.0);
            double maxPriceDouble = CommonUtil.StringToDouble(maxPrice, 0.0);
            if (minPriceDouble < 0)
                minPriceDouble = 0.0;
            if (maxPriceDouble < 0)
                maxPriceDouble = 0.0;

            if (minPriceDouble > maxPriceDouble) {
                double temp = minPriceDouble;
                minPriceDouble = maxPriceDouble;
                maxPriceDouble = temp;
            }

            request.setAttribute("minPrice", minPriceDouble);
            request.setAttribute("maxPrice", maxPriceDouble);

            bookPage = bookServiceImpl.getBookPage(pageNum, pageSize, pageRange, url, minPriceDouble, maxPriceDouble);
        } else
            bookPage = bookServiceImpl.getBookPage(pageNum, pageSize, pageRange, url, null, null);

        request.setAttribute("pages", bookPage);

        requestDispatcher("pages/manager/book_manager", "jsp", request, response);//注意 如果子类重写了这个那么就会直接去调用子类的重写而不会执行父类的 这样就可以实现跳转多样性
        //如果子类重写调用 参数还是父类 需要在子类重新修改
    }

    @Override
    protected void requestDispatcher(String path, String suffix, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.requestDispatcher(path, suffix, request, response);
    }

    //    protected void requestDispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        request.getRequestDispatcher()
//    }

    protected String getUrl() {
        return "Manager/BookServlet?action=page";
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Book> list = bookServiceImpl.listAllBook();
        request.setAttribute("list", list);

        super.requestDispatcher("pages/manager/book_manager", "jsp", request, response);
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        Book book = bookServiceImpl.getSingleBookById(Integer.parseInt(id));

        request.setAttribute("book", book);

        request.getRequestDispatcher("/pages/manager/book_edit.jsp?action=update").forward(request, response);
    }


}
