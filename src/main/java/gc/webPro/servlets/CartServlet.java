package gc.webPro.servlets;

import gc.webPro.pojo.Cart;
import gc.webPro.pojo.CartItem;
import gc.webPro.utils.CommonUtil;
import gc.webPro.utils.RequestBeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {


    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("username") == null) {
            logger.info("你还没有登录");
            response.sendRedirect("/demo/pages/user/login.html");
            return;
        }

        CartItem cartItem = RequestBeanUtil.SetBeanProperties(new CartItem(), request.getParameterMap());
        Cart cart;

        if (request.getSession().getAttribute("cart") == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart = (Cart) request.getSession().getAttribute("cart");
        cart.addItem(cartItem);
        request.getSession().setAttribute("bookName", cartItem.getName());

        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        Cart cart = (Cart) request.getSession().getAttribute("cart");

        cart.deleteItem(CommonUtil.StringToInt(id,0));

        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String count = request.getParameter("count");
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        cart.updateCount(CommonUtil.StringToInt(id,0),CommonUtil.StringToInt(count,0));

        response.sendRedirect(request.getHeader("Referer"));
    }


    protected void deleteCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cart cart = (Cart) request.getSession().getAttribute("cart");

        cart.clearCart();

        response.sendRedirect(request.getHeader("Referer"));
    }
}
