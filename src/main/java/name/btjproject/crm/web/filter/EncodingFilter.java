package name.btjproject.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        过滤post请求的乱码
        request.setCharacterEncoding("utf-8");
//        过滤响应流的乱码
        response.setContentType("text/html;charset=utf-8");

        chain.doFilter(request, response);
    }
}
