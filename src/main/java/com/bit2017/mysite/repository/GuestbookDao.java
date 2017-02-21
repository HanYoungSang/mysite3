package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.GuestbookVo;

import oracle.jdbc.pool.OracleDataSource;


@Repository
public class GuestbookDao {
	
	
	@Autowired
	private OracleDataSource dataSource;
	
//	public Connection getConnection() throws SQLException{
//		Connection conn = null;
//		
//		// 1. JDBC Driver Loading (JDBC Class Loading)
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Driver 로딩 실패: " + e);
//		}
//
//		// 2. Connection 열어오기 ( Connect to DB)
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		conn = DriverManager.getConnection(url, "webdb","webdb");
//		
//		return conn;
//
//	}
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();	

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = " select no, name, content, to_char(reg_date,'yyyy-mm-dd') from GUESTBOOK order by no desc";

			stmt = conn.createStatement();

			// 5. SQL 문 실행
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString( 2 );
				String content = rs.getString( 3 );
				String regDate = rs.getString(4);

				//				System.out.println(firstName + " " + lastName + ":" + email + ":" +  phoneNumber + ":" + hireDate);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setRegDate(regDate);

				list.add(vo);	
			}
			return list;
		} catch (SQLException e) {
			System.out.println("SQLException err : 로딩 실패 - " + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();

				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("ERR:" + e);
			}
		}

		return null;

	}
	public boolean insert (GuestbookVo guestbookVo) {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try{
			conn = dataSource.getConnection();
			
			// 3. SQL문 실행

			String sql = " insert into guestbook values (seq_guestbook.nextval, ?, ?, ?, sysdate)";

			pstmt = conn.prepareStatement(sql);


			// 4. 데이터 바인딩
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());		
			pstmt.setString(3, guestbookVo.getContent());

			//				System.out.println(sql);

			// 5. SQL 문 실행
			int count  = pstmt.executeUpdate();
			return count == 1;

		} catch (SQLException e) {
			System.out.println("err : 로딩 실패 - " + e);
			return false;
		} finally {
			// 자원 정리
			try {
				if(pstmt != null) pstmt.close();

				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("ERR:" + e);
			}
		}

	}
	public boolean delete (Long no, String password) {

		Connection conn = null;
		PreparedStatement pstmt = null;
//		ResultSet rs = null;

		try{
			
			conn = dataSource.getConnection();

			//				System.out.println("연결성공");

			// 3. SQL문 실행

			String sql = "delete from guestbook where no = ? and password = ?";

			pstmt = conn.prepareStatement(sql);


			// 4. 데이터 바인딩
			pstmt.setLong(1, no);
			pstmt.setString(2, password);		

			//	System.out.println(sql);

			// 5. SQL 문 실행
			int count  = pstmt.executeUpdate();
			return count == 1;

		} catch (SQLException e) {
			System.out.println("err : 로딩 실패 - " + e);
			return false;
		} finally {
			// 자원 정리
			try {
//				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();

				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("ERR:" + e);
			}
		}
	}
	public GuestbookVo getList(Long number) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = " select no, name, content, to_char(reg_date,'yyyy-mm-dd') from GUESTBOOK where no = ? order by no desc";

			pstmt = conn.prepareStatement(sql);
//			System.out.println(sql+":"+Integer.parseInt( number.toString()));
			
			pstmt.setLong(1, number);
			
			
			// 5. SQL 문 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString( 2 );
				String content = rs.getString( 3 );
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setRegDate(regDate);
				return vo;
			}
			return null;
		} catch (SQLException e) {
			System.out.println("SQLException err : 로딩 실패 - " + e);
		} finally {
			// 자원 정리
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();

				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("ERR:" + e);
			}
		}

		return null;

	}
	
	public boolean modify (GuestbookVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Boolean ret = false;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update GUESTBOOK " 
					   + " set name = ?, content = ?,"
					   + " reg_date = sysdate "
					   + " where no = ? "
					   + "   and password = ?";
		  
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			pstmt.setString(4, vo.getPassword());
			
			int count = pstmt.executeUpdate();
			
			return count == 1;
			
		} catch (SQLException e) {
			System.out.println("Connection Err : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null) 
					conn.close();
			} catch (SQLException e) {
				System.out.println("Connection Close Err : " + e);
			}
			
		}
		
		return ret;
	}
}