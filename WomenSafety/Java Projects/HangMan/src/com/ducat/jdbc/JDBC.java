package com.ducat.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ducat.dto.ScoreDTO;

public class JDBC {
	private static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String driverName = rb.getString("drivername");
		Class.forName(driverName);
		System.out.println("Driver Loaded...");
		String url = rb.getString("dburl");
		String userid = rb.getString("userid");
		String password = rb.getString("password");
		Connection con = DriverManager.getConnection(url, userid, password);
		if (con != null) {
			System.out.println("Connection Ready...");
		}
		return con;
	}

	public static void loadResult(ScoreDTO scoreDTO)
			throws ClassNotFoundException, SQLException {

		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from highscore");
			while (rs.next()) {
				if (rs.getInt("score") < scoreDTO.getScore()) {
					scoreDTO.setSr(rs.getInt("sr"));
					break;
				}
			}
			if (scoreDTO.getSr() > 0) {

				pstmt = con.prepareStatement("update highscore set user= '"
						+ scoreDTO.getUser() + "',score= '"
						+ scoreDTO.getScore() + "' where sr = '"
						+ scoreDTO.getSr() + "'");
				pstmt.executeUpdate();
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

	public static List<ScoreDTO> foundHighScore()
			throws ClassNotFoundException, SQLException {
		List<ScoreDTO> scoreList = new ArrayList<ScoreDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScoreDTO scoreDTO = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from highscore");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				scoreDTO = new ScoreDTO();
				scoreDTO.setUser(rs.getString("user"));
				scoreDTO.setScore(rs.getInt("score"));
				scoreList.add(scoreDTO);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return scoreList;
	}

	public static String getWords(int id) throws ClassNotFoundException,
			SQLException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String score1 = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select words from words where id = '" + id
					+ "'");
			if (rs.next())
				score1 = rs.getString("words");
			System.out.println(score1);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return score1;
	}
}
