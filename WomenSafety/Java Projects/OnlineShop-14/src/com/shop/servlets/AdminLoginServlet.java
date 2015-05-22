package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminLoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		if(userid.equals("admin") && password.equals("123")){
			String msg = request.getParameter("msg");
	/*		ServletConfig sc = this.getServletConfig();
			ServletContext ss = sc.getServletContext();
			ss.setAttribute("msg", msg);*/
			this.getServletConfig().getServletContext().setAttribute("msg", msg);
			out.println("Message Send SuccessFully !");
		}
	}

}
