package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CounterServlet
 */
public class CounterServlet extends HttpServlet {
	private int counter = 0;	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		counter++;
		response.setHeader("refresh", "1");
		PrintWriter out = response.getWriter();
		out.println("Count is "+counter);
	}

	

}
