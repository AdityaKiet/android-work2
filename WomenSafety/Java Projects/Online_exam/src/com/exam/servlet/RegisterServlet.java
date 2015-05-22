package com.exam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.dto.UserDTO;
import com.exam.jdbc.JDBC;

public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(request.getParameter("name"));
		userDTO.setAge(Integer.parseInt(request.getParameter("age")));
		userDTO.setEmail(request.getParameter("email"));
		userDTO.setUserid(request.getParameter("userid"));
		userDTO.setPassword(request.getParameter("password"));
		PrintWriter out = response.getWriter();
		//String result;
		try {
			if (JDBC.checkLogin(userDTO.getUserid())) {
				out.println("User ID already registered");
			} else {
				out.println(JDBC.addNewUser(userDTO));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
