package com.javalec.ex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class ex20_MemberDAO {

//	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	private String uid = "scott";
//	private String upw = "tiger";
	
	private DataSource dataSource;
	
	public ex20_MemberDAO() {
		
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			Context context = new InitialContext(); //InitialContext의 lookup()메서드를 이용하면 JNDI 이름으로 등록되어 있는 서버 자원을 찾을 수 있다
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ex20_MemberDTO> memberSelect() {
		
		ArrayList<ex20_MemberDTO> dtos = new ArrayList<ex20_MemberDTO>();
		
		Connection con =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
//			con = DriverManager.getConnection(url, uid, upw);
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from bbs");
			
			while (rs.next()) {
				String seq = rs.getString("seq");
				String title = rs.getString("title");
				String content_text = rs.getString("content_text");
				String ins_dt = rs.getString("ins_dt");
				String upd_dt = rs.getString("upd_dt");
				
				ex20_MemberDTO dto = new ex20_MemberDTO(seq, title, content_text, ins_dt, upd_dt);
				dtos.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dtos;
	}
	
}
