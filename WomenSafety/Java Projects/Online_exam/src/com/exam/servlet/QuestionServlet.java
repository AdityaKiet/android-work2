package com.exam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.dto.QuestionDTO;
import com.exam.dto.QuizMasterDTO;
import com.exam.dto.ResultDTO;
import com.exam.jdbc.JDBC;

public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			PrintWriter out = response.getWriter();
			String userid = (String) session.getAttribute("user");
			String quiz_id = request.getParameter("quiz_id");
			ResultDTO resultDTO = new ResultDTO();
			resultDTO.setQuiz_id(quiz_id);
			resultDTO.setUser_id(userid);
			if(!JDBC.alreadyAttempted(resultDTO)){
			List<QuestionDTO> questionList;
			questionList = JDBC.foundQuestion(quiz_id);
			session.setAttribute("totalQuestion", questionList.size());
			session.setAttribute("questionData", questionList);
			request.setAttribute("questionData", questionList);
			RequestDispatcher rd = request.getRequestDispatcher("question.jsp");
			rd.forward(request, response);
			}else{
				out.println("Already Attempted");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
