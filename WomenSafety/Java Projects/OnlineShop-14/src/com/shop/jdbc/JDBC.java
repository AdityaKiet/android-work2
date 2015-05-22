package com.shop.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.shop.dto.BrandDTO;
import com.shop.dto.ProductDTO;
import com.shop.dto.UserDTO;


public class JDBC {
	
	
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
	public static boolean checkLogin(String userid, String password) throws ClassNotFoundException, SQLException{
		boolean result = false;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
		con = getConnection();
		//Step -3 Query 
		 stmt = con.createStatement();
		// Step -4 Result 
		// select userid,password from user where userid='amit' and password='123'
		rs = stmt.executeQuery("select userid,password from login where userid='"+userid+"' and password='"+password+"'");
		if(rs.next()){
			result = true;
		/*	result = "Userid "+
		rs.getString("userid")+"  Password "+
					rs.getString("password");*/
		}
		}

		finally{
		// Step -5 Close the resources
		if(rs!=null){	
		rs.close();
		}
		if(stmt!=null){
		stmt.close();
		}
		if(con!=null){
		con.close();
		}
		}
		return result;
		
	}
	public static String addNewUser(UserDTO userDTO) throws SQLException, ClassNotFoundException{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		int recordCount = 0;
		int recordCount2= 0;
		String result = "Not Register , Some Error on Server";
		try{
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("insert into user_mst (userid,password,name,address) values(?,?,?,?)");
			pstmt.setString(1,userDTO.getUserid());
			pstmt.setString(2,userDTO.getPassword());
			pstmt.setString(3,userDTO.getName());
			pstmt.setString(4,userDTO.getAddress());
			recordCount = pstmt.executeUpdate();
			
			pstmt2 = con.prepareStatement("insert into user(userid,password)values(?,?)");
			pstmt2.setString(1,userDTO.getUserid());
			pstmt2.setString(2,userDTO.getPassword());
			recordCount2 = pstmt2.executeUpdate();
			
			if(recordCount>0 && recordCount2>0){
				result = "Register SuccessFully !";
				con.commit();
			}
			else
			{
				con.rollback();
			}
		}
		finally{
			if(pstmt!=null){
				pstmt.close();
				}
				if(con!=null){
				con.close();
				}	
		}
		return result;
		
	}
	
	public static List<ProductDTO> getAllProducts() throws ClassNotFoundException, SQLException{
		List<ProductDTO> productList = new ArrayList<ProductDTO>();;
		ProductDTO productDTO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		con = getConnection();
		pstmt = con.prepareStatement("select id,name,remarks,status from product where status ='Y'");
		rs = pstmt.executeQuery();
		while(rs.next()){
			productDTO = new ProductDTO();
			productDTO.setId(rs.getInt("id"));
			productDTO.setName(rs.getString("name"));
			productDTO.setRemarks(rs.getString("remarks"));
			productDTO.setStatus(rs.getString("status"));
			productList.add(productDTO);
			
		}
		}
		finally{
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}
		
		return productList;
	}
	public static List<BrandDTO> getBrands(int productId) throws ClassNotFoundException, SQLException{
		List<BrandDTO> brandList = new ArrayList<BrandDTO>();
		BrandDTO brandDTO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		con = getConnection();
		pstmt = con.prepareStatement("select pid,name,bid from brands where pid=?");
		pstmt.setInt(1,productId);
		rs = pstmt.executeQuery();
		while(rs.next()){
			brandDTO = new BrandDTO();
			brandDTO.setPid(rs.getInt("pid"));
			brandDTO.setName(rs.getString("name"));
			brandDTO.setBid(rs.getInt("bid"));
			
			brandList.add(brandDTO);
			
		}
		}
		finally{
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}
		return brandList;
		
	}
	
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	//Class.forName("com.ducat.exams.S");
	/*S obj1 = new S();
	S obj2 = new S();*/
	/*Scanner scanner = new Scanner(System.in);
	System.out.println("Enter Userid ");
	String userid = scanner.nextLine();
	System.out.println("Enter Password ");
	String pwd = scanner.nextLine();
	String a = checkLogin(userid, pwd);
	System.out.println(a);*/
	UserDTO userDTO = new UserDTO();
	userDTO.setUserid("ram");
	userDTO.setPassword("ram123");
	userDTO.setName("ram");
	userDTO.setAddress("Raj Nagar");
	String result =addNewUser(userDTO);
	System.out.println("Result is "+result);
		
}

}
