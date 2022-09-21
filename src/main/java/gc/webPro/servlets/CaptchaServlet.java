package gc.webPro.servlets;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.annotation.WebServlet;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
@WebServlet(name = "CaptchaServlet", value = "/CaptchaServlet")
public class CaptchaServlet extends KaptchaServlet {

}
