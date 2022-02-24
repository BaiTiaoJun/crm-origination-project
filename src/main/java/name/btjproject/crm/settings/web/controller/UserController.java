package name.btjproject.crm.settings.web.controller;

import name.btjproject.crm.exception.LoginException;
import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.settings.service.impl.UserServiceImpl;
import name.btjproject.crm.utils.MD5Util;
import name.btjproject.crm.utils.PrintJsonUtil;
import name.btjproject.crm.utils.ServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        if ("/settings/user/login.do".equals(servletPath)) {
            loginVerify(request, response);
        }
    }

    private void loginVerify(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("pass");
        String pass = MD5Util.getMD5(password);
        String ip = request.getRemoteAddr();
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = userService.login(userName, pass, ip);
            request.getSession().setAttribute("user", user);
            PrintJsonUtil.printBoolJson(response, true);
        } catch (LoginException e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("msg", e.getMessage());
            PrintJsonUtil.printObjectJson(response, map);
        }
    }
}
