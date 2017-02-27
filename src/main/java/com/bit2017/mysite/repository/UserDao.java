package com.bit2017.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.UserVo;

//import oracle.jdbc.pool.OracleDataSource;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
//	@Autowired
//	private OracleDataSource dataSource;
	
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
		
			
			int count = sqlSession.insert("user.insert", uservo);
			
			return count == 1;

	}

	public UserVo get(Long userNo) {
		UserVo uservo = sqlSession.selectOne("user.getByNo", userNo);
		return uservo;
	}
	
	public boolean modify (UserVo uservo) {
		
			int count = sqlSession.update("user.update", uservo);

			return count == 1;
			
	}
	
	public UserVo get(String email) {
		UserVo userVo = sqlSession.selectOne("user.getByEmail", email);
		return userVo;
	}
	
	public UserVo get(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password",password);
		UserVo uservo = sqlSession.selectOne("user.getByEmailAndPassword", map);		
		return uservo;
	}

	
}
