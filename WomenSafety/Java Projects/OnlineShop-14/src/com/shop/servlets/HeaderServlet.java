package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HeaderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//String headerValue = request.getHeader("");
		Enumeration r = request.getHeaderNames();
		String headerName = null;
		while(r.hasMoreElements()){
			headerName = (String)r.nextElement();
			String headerValue = request.getHeader(headerName);
			out.println(" Header Name "+headerName+" Header Value "+headerValue);
		}
		

	}

}
