package gc.webPro.servlets;

import gc.webPro.service.UserService;
import gc.webPro.service.impl.UserServiceImpl;
import gc.webPro.servlets.UserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public abstract class BaseServlet extends HttpServlet {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        //注意 protected权限在不同包内是无法调用子类独有的的方法
        try {
            //获取类中对应名字参数的方法 由于是继承关系 this是子类的对象 记住子类永远先调用自己重写的方法 没有再调用父类的
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
            //调用子类的方法并传参
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void requestDispatcher(String path, String suffix, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        StringBuilder stringBuilder = new StringBuilder("/");
        stringBuilder.append(path).append(".").append(suffix);
        request.getRequestDispatcher(stringBuilder.toString()).forward(request, response);
    }

}
