package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.jdbc.JDBC;


public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String branch = request.getParameter("branch");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		try{
		if(JDBC.checkLogin(userid, password))
		{
			// Fresh Session 
			HttpSession session = request.getSession(true);
			session.setAttribute("user", userid);
			String color = request.getParameter("color");
			if(color!=null && color.trim().length()>0){
				// Write Color in Cookie
				Cookie c = new Cookie("myfavcolor",color);
				
				c.setMaxAge(365*24*60*60);
				response.addCookie(c);
				
			}
			
			//out.println("Welcome "+userid);
			RequestDispatcher rd = request.getRequestDispatcher(response.encodeRedirectURL("product.shop"));
			rd.forward(request, response);
			//response.sendRedirect(response.encodeRedirectURL("product.shop?branch="+branch));
			//response.sendRedirect("product.shop");
		}
		else
		{
			out.println("Invalid Userid and Password ");
		}
		}
		catch(SQLException e){
			out.println("Some Data Base Issue "+e);
		}
		catch(Exception e){
			out.println("Some Other Exception , Kindly Contact to Your System Admin "+e);
		}
	}

}
