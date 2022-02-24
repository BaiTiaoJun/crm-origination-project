package name.btjproject.crm.web.filter;

import name.btjproject.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath)) {
            chain.doFilter(request, response);
        } else {
            User user = (User) httpServletRequest.getSession().getAttribute("user");
            if (user != null) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
            }
        }
    }
}
