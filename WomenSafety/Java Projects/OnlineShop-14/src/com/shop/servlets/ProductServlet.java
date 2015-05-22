package com.shop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.dto.ProductDTO;
import com.shop.jdbc.JDBC;
import com.shop.listener.SessionCountListener;


public class ProductServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		/*if(session == null){
			response.sendRedirect("login.html");
			return;
		}*/
		String userid = (String)session.getAttribute("user");
		
			// Read Color From Cookie
			String myColor = null;
			Cookie c [] = request.getCookies();
			if(c!=null && c.length>0){
				for(Cookie d : c){
					if(d.getName().equals("myfavcolor")){
						myColor = d.getValue();
					}
				}
			}
		if(myColor==null){
			myColor = "cyan";
		}
		String branch = request.getParameter("branch");
		String email = this.getServletConfig().getInitParameter("email");
		String tfn= this.getServletConfig().getServletContext().getInitParameter("tollfreenumber");
		String adminMsg = (String)this.getServletConfig().getServletContext().getAttribute("msg");
		if(adminMsg==null){
			adminMsg="";
		}
		out.println("<html><body bgcolor='"+myColor+"'> Welcome "+userid+" Branch is "+branch+" Support Email is "+email+" Toll Free No is "+tfn+" Admin Message is "+adminMsg+" <form action='"+response.encodeURL("brand.shop")+"' method='post'> <table border='1'>");
		try {
			List<ProductDTO> productList = JDBC.getAllProducts();
			for(ProductDTO product : productList){
				out.println("<tr><td> "+product.getId()+"</td> <td>"+product.getName()+"</td><td>"+product.getRemarks()+"</td><td><img src='images/"+product.getId()+".jpg' width='100' height='100'></td>"
						+ "<td><input type='radio' name='r1' value='"+product.getId()+"'></td>"
						+ "</tr>");
			}
			out.println("<br>");
			out.println("<input type='hidden' name='branch' value='"+branch+"'>");
			out.println("<input type='submit' value='Choose Brand'>");
			out.println("<br>");
			out.println("<h2>Total No of Login User "+SessionCountListener.counter);
			RequestDispatcher rd = request.getRequestDispatcher("Head");
			rd.include(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			out.println(e);
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println(e);
			e.printStackTrace();
		}
		out.println("</table></form></body></html>");
	}

}
