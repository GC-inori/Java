package gc.webPro.servlets;

import gc.webPro.pojo.User;
import gc.webPro.service.UserService;
import gc.webPro.service.impl.UserServiceImpl;
import gc.webPro.utils.RequestBeanUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {

    protected static final UserService userService = UserServiceImpl.getUserServiceImpInstance();

    @Override//这个其实可以不写 写了方便理解父类的this其实是子类的实例
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.getSession().invalidate();

        response.sendRedirect("/demo/index.jsp");
        //request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = (String) request.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        String requestCode = request.getParameter("code");

//        logger.info("{},{}",code,requestCode);

        if (code == null || !code.equalsIgnoreCase(requestCode)){
            logger.info("验证码错误");
            request.setAttribute("msg", "验证码错误");
            super.requestDispatcher("pages/user/register","jsp",request,response);
           // request.getRequestDispatcher("/pages/user/register.jsp").forward(request, response);
            return;
        }

        String username = request.getParameter("username");

        if (userService.isUsernameValid(username)) {
            User user = RequestBeanUtil.SetBeanProperties(new User(), request.getParameterMap());
            if (userService.registerUser(user))
                super.requestDispatcher("pages/user/register_success","jsp",request,response);
        } else {
            logger.info("用户名已存在");
            request.setAttribute("msg", "用户名已存在");
            super.requestDispatcher("pages/user/register","jsp",request,response);
        }
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter writer = response.getWriter();
        if (!userService.isUsernameValid(username)) {

            if (userService.loginUser(username, password)) {
                request.getSession().setAttribute("username", username);
                super.requestDispatcher("pages/user/login_success","jsp",request,response);
            } else {
                logger.info("用户名或密码错误");
                writer.println("<h1>用户名或密码错误 <a href=\"/demo/pages/user/login.html\">重新登录</a></h1>");
            }
        } else {
            logger.info("用户名未注册");
            writer.println("<h1>用户名未注册 <a href=\"/demo/pages/user/register.jsp\">注册</a></h1>");
        }
    }

}
