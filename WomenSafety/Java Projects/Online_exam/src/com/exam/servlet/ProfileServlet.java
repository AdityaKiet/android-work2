package com.exam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.dto.UserDTO;
import com.exam.jdbc.JDBC;

public class ProfileServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDTO userDto = new UserDTO();

		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("user");
		String adminMsg = (String) this.getServletConfig().getServletContext()
				.getAttribute("message");
		if (adminMsg == null) {
			adminMsg = "";
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(adminMsg);
			userDto = JDBC.createProfile(userid);
			RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
			request.setAttribute("userDTO", userDto);
			rd.forward(request, response);

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}
	}

}
