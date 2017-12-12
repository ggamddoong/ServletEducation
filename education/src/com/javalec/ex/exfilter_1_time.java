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
        //request ����
        long start = System.currentTimeMillis();

        //�������� �Ǵ� ������ �������̸� ����(JSP)����
        chain.doFilter(req, resp); //chain.doFilter() : �������͸� ȣ���Ѵ�. ������ servlet/jsp

        //response ����
        long end = System.currentTimeMillis();
        String uri;
        if(req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)req;
            uri = request.getRequestURI();

            ServletContext context = config.getServletContext();
            context.log(uri + " ����ð�:" + (end-start)+"ms �ð��� �ҿ��");
        }
    }
    public void destroy(){}
}