package com.javalec.ex;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


	public class exfilter_2_login implements Filter {
	    private FilterConfig config;

	    public void init(FilterConfig config) throws ServletException {
	    	 this.config = config;
	    }

	    public void doFilter(ServletRequest req, ServletResponse resp,
	            FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest)req;
	        HttpSession session = request.getSession();

	        String userId = (String)session.getAttribute("userId");

	        String[] uris = {"/sbbs/created", "/sbbs/article"};
	        boolean flag = false;
	        String uri = request.getRequestURI();
	        for(String s : uris) {
	            if(uri.indexOf(s) != -1) {
	                flag = true;
	                break;
	            }
	        }

	        if(userId == null && flag) {
	            RequestDispatcher rd = request.getRequestDispatcher("/ex7");
	            rd.forward(req, resp);
	        } else {
	            chain.doFilter(req, resp); //chain.doFilter() : 다음필터를 호출한다. 없으면 servlet/jsp
	            
	            ServletContext context = config.getServletContext();
	            context.log("로그인 filter 후속");
	        }
	    }

	    public void destroy(){
	    }
	}