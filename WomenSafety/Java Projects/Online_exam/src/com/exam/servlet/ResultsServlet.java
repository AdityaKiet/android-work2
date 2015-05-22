package com.exam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.dto.ResultDTO;
import com.exam.jdbc.JDBC;

public class ResultsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			PrintWriter out = response.getWriter();
			String userid = (String) session.getAttribute("user");
			List<ResultDTO> resultList;
			resultList = JDBC.showResult(userid);
			for (ResultDTO temp : resultList) {
				out.println(temp);
			}
			// request.setAttribute("resultData", resultList);

			// RequestDispatcher rd =
			// request.getRequestDispatcher("result.jsp");
			// rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
