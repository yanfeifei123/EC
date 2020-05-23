package com.yff.core.safetysupport.xssfilter;



import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class XssFilter implements Filter {

    FilterConfig filterConfig = null;


    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {

//        System.out.println("通过过滤器访问");
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), res);
    }



}