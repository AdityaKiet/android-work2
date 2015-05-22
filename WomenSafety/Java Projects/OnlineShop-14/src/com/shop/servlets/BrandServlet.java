package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.dto.BrandDTO;
import com.shop.jdbc.JDBC;


public class BrandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.setMaxInactiveInterval(30);
		/*if(session == null){
			response.sendRedirect("login.html");
			return;
		}*/
		String userid = (String)session.getAttribute("user");
		PrintWriter out = response.getWriter();
		int pid = Integer.parseInt(request.getParameter("r1"));
		try {
			List<BrandDTO> brandList = JDBC.getBrands(pid);
			String branch= request.getParameter("branch");
			String tfn= this.getServletConfig().getServletContext().getInitParameter("tollfreenumber");
			out.println("<html><body> Welcome "+userid+" and branch is "+branch+" Toll Free No is "+tfn+" <form action='model' method='post'> <table border='1'>");
			for(BrandDTO bd : brandList){
				out.println("<tr><td> "+bd.getName()+"</td> <td><img src='images/"+bd.getBid()+".jpg' width='100' height='100'></td>"
						+ "<td><input type='radio' name='r1' value='"+bd.getBid()+"'></td>"
						+ "</tr>");
			}
			out.println("<br>");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
