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

import com.exam.dto.QuestionDTO;
import com.exam.dto.ResultDTO;
import com.exam.jdbc.JDBC;

public class ResultGeneratorServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int score = 0;
		String userid = (String) session.getAttribute("user");
		int totalQuestion = (int) session.getAttribute("totalQuestion");
		List<QuestionDTO> questionList = (List<QuestionDTO>) session
				.getAttribute("questionData");
		session.removeAttribute("totalQuestion");
		session.removeAttribute("questionData");
		PrintWriter out = response.getWriter();
		for (int counter = 1; counter <= totalQuestion; counter++) {
			if (request.getParameter(Integer.toString(counter)) != null) {
				if (request.getParameter(Integer.toString(counter)).equals(
						questionList.get(counter-1).getAnswer())) {
					score++;
				}
			}

		}
		out.println(score);
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setQuiz_id(questionList.get(0).getQuiz_id());
		resultDTO.setUser_id(userid);
		resultDTO.setScore(score);
		try {
			JDBC.loadResult(resultDTO);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
