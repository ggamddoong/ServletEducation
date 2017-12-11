package com.javalec.ex;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ex6
 */
@WebServlet("/ex6")
public class ex6 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	     * @see HttpServlet#HttpServlet()
	     */
	    public ex6() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

	// @Override
	// public void service(ServletRequest arg0, ServletResponse arg1)
	// throws ServletException, IOException {
	// // TODO Auto-generated method stub
	// System.out.println("service");
	// }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
	}

	@PostConstruct
	private void initPostConstruct() {
		// TODO Auto-generated method stub
		System.out.println("initPostConstruct");
	}

	@PreDestroy
	private void destoryPreDestory() {
		// TODO Auto-generated method stub
		System.out.println("destoryPreDestory");
	}

}
