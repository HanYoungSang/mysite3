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

import com.bit2017.mysite.exception.UserDaoException;
import com.bit2017.mysite.vo.UserVo;

import oracle.jdbc.pool.OracleDataSource;

@Repository
public class UserDao {
	
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
	
	public boolean insert ( UserVo uservo ) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into users values (seq_users.nextVal, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString( 1, uservo.getName());
			pstmt.setString( 2, uservo.getEmail());
			pstmt.setString( 3, uservo.getPassword());
			pstmt.setString( 4, uservo.getGender());
			
			int count = pstmt.executeUpdate();
			
			return count == 1;
			
			
		} catch (SQLException e) {
			System.out.println("Connection 오류 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				 System.out.println("Connection Close 오류 : " + e);
			}
		}
		
		
		return result;
	}

	public UserVo get(Long userNo) throws UserDaoException{
		UserVo uservo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select no, name, email, gender from users where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, userNo);
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				Long no       = rs.getLong(1);
				String name   = rs.getString(2);
				String email  = rs.getString(3);
				String gender = rs.getString(4);

				uservo = new UserVo();
				uservo.setNo(no);
				uservo.setName(name);
				uservo.setEmail(email);
				uservo.setGender(gender);

			}
			
			
		} catch (SQLException e) {
//			System.out.println("error : " + e);
			throw new UserDaoException( e.getMessage() );
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {

//				System.out.println("err : " + e);
				throw new UserDaoException( e.getMessage() );
				
			}
		}
		
		return uservo;
	}
	
	public boolean modify (UserVo uservo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
//		System.out.println(uservo);
		
		try {
			String sql = "";
			boolean pass_yn = false;
			conn = dataSource.getConnection();
			
			if(uservo.getPassword().length() > 0) {
				pass_yn = true;
				sql = " update users "
					+ " set name = ?,"
					+ "     password = ?,"	
					+ "     gender = ? "
					+ " where no = ?";
			} else {
				pass_yn = false;
				sql = "update users "
					+ " set name = ?,"
					+ "     gender = ? "
					+ " where no = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if (pass_yn) {
				pstmt.setString(1, uservo.getName());
				pstmt.setString(2, uservo.getPassword());
				pstmt.setString(3, uservo.getGender());
				pstmt.setLong(4, uservo.getNo());
			} else {
				pstmt.setString(1, uservo.getName());
				pstmt.setString(2, uservo.getGender());
				pstmt.setLong(3, uservo.getNo());
			}
			
			int count = pstmt.executeUpdate();

			return count == 1;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error : " + e);
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("err : " + e);
			}
		}
		
		
		return false;
		
	}
	
	public UserVo get(String email, String password) {
		
		UserVo uservo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select no, name from users where email = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				Long no = rs.getLong(1);
				String name =rs.getString(2);

				uservo = new UserVo();
				uservo.setNo(no);
				uservo.setName(name);

			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error : " + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("err : " + e);
			}
		}
		
		return uservo;
	}
	
	public List<UserVo> getList() {
		List<UserVo> list = new ArrayList<UserVo>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			
//			System.out.println("연결성공");
			
			// 3. SQL문 실행
			
			String sql = " select no, first_name, last_name, email " + 
						 "   from emaillist order by no desc";

			stmt = conn.createStatement();
			
			// 5. SQL 문 실행
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString( 2 );
				String lastName = rs.getString( 3 );
				String email = rs.getString(4);
				
//				System.out.println(firstName + " " + lastName + ":" + email + ":" +  phoneNumber + ":" + hireDate);
				
//				EmailListVo emailListVo = new EmailListVo();
//				emailListVo.setNo(no);
//				emailListVo.setFirstName(firstName);
//				emailListVo.setLastName(lastName);
//				emailListVo.setEmail(email);
				
//				list.add(emailListVo);	
			}
			return list;
		} catch (SQLException e) {
			System.out.println("err : 로딩 실패 - " + e);
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

	
}
