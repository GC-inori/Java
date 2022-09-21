package gc.webPro.servlets.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ManagerFilter" ,urlPatterns={"/pages/manager/*","/Manager/BookServlet"})
public class ManagerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        Object username = session.getAttribute("username");

        if(username == null || !username.equals("admin"))
            httpResponse.sendRedirect("/demo/pages/user/login.html");//这里用转发会有bug
        else
            chain.doFilter(request,response);
    }
}
