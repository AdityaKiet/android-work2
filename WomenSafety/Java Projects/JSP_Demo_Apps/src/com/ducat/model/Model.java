package com.ducat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ducat.dto.EmployeeDTO;

public class Model {
	private static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String driverName = rb.getString("drivername");
		
		// Step -1 Load DataBase Driver
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName(driverName);
		System.out.println("Driver Loaded...");
		// Step -2 Creating Connection
		String url = rb.getString("dburl");
		String userid = rb.getString("userid");
		String password = rb.getString("password");
		//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exam","root","root");
		Connection con = DriverManager.getConnection(url,userid,password);
		if(con!=null){
			System.out.println("Connection Ready...");
			
		}	
		return con;
	}
	
	public static List<EmployeeDTO> searchEmployees(String empName) throws SQLException, ClassNotFoundException{
		List<EmployeeDTO> empList = new ArrayList<EmployeeDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeDTO empDTO = null;
		try{
			con = getConnection();
			pstmt = con.prepareStatement("select id , name,salary from employee where name=?");
			pstmt.setString(1,empName);
			rs = pstmt.executeQuery();
			while(rs.next()){
				empDTO = new EmployeeDTO();
				empDTO.setId(rs.getInt("id"));
				empDTO.setName(rs.getString("name"));
				empDTO.setSalary(rs.getDouble("salary"));
				empList.add(empDTO);
			}
			
		}
		finally
		{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
		
		
		return empList;
	}

}
