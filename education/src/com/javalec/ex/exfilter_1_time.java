package com.javalec.ex;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class exfilter_1_time implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        //request 필터
        long start = System.currentTimeMillis();

        //다음필터 또는 필터의 마지막이면 서블릿(JSP)실행
        chain.doFilter(req, resp); //chain.doFilter() : 다음필터를 호출한다. 없으면 servlet/jsp

        //response 필터
        long end = System.currentTimeMillis();
        String uri;
        if(req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)req;
            uri = request.getRequestURI();

            ServletContext context = config.getServletContext();
            context.log(uri + " 실행시간:" + (end-start)+"ms 시간이 소요됨");
        }
    }
    public void destroy(){}
}