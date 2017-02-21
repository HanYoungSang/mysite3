package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bit2017.mysite.vo.BoardVo;

import oracle.jdbc.pool.OracleDataSource;

public class BoardDao {

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
	
	public List<BoardVo> getList(Long pageCount, Long maxRow) {
		List<BoardVo> list = new ArrayList<BoardVo>();	

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "select rn, no, title, hit, g_no, o_no, depth, reg_date, users_name, users_no "+
						"	from ( "+
						"		select rownum rn, c.* "+
						"			from ( "+
						"			select  a.no, a.TITLE, a.HIT, to_char(a.reg_date ,'yyyy-mm-dd hh24:mi:	ss') reg_date, "+
						"                   a.g_no, a.o_no, a.depth, "+
						"					b.NAME as users_name, "+
						"					b.no as users_no "+
						"			from board a, users b "+
						"			where a.USERS_NO = b.NO "+
						"			order by a.G_NO desc, a.O_NO asc ) c ) "+
						"  where rn >= (?-1)*?+1 "+
						"   and rn <= (?*?)" ;

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, pageCount);
			pstmt.setLong(2, maxRow);
			pstmt.setLong(3, pageCount);
			pstmt.setLong(4, maxRow);

			// 5. SQL 문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long rn = rs.getLong(1);
				Long no = rs.getLong(2);
				String title = rs.getString(3);
				Long hit = rs.getLong(4);
				Long gNo = rs.getLong(5);
				Long oNo = rs.getLong(6);
				Long depth = rs.getLong(7);
				String regDate = rs.getString(8);
				String userName = rs.getString(9);
				Long userNo = rs.getLong(10);

				//				System.out.println(firstName + " " + lastName + ":" + email + ":" +  phoneNumber + ":" + hireDate);

				BoardVo vo = new BoardVo();
				vo.setRn(rn);
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setGNo(gNo);
				vo.setONo(oNo);
				vo.setDepth(depth);
				vo.setRegDate(regDate);
				vo.setUserName(userName);
				vo.setUserNo(userNo);
				
				list.add(vo);	
			}
			return list;
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
	
	public Long getListCount(Long maxRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "select ceil(count(*)/?) as total"
					   + "  from board" ;
					   
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, maxRow);
			
			// 5. SQL 문 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getLong(1);
			}
			
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
		return (long) 0;
	}
	
	public boolean insert(BoardVo boardvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "insert into board"
					  + " values "
					  + " (seq_board.nextval, ?, ?, sysdate, 0, nvl((select max(g_no) from board), 0) + 1, 1, 0, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContent());
			pstmt.setLong(3, boardvo.getUserNo());
			

			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();
			
			return count == 1;
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

		return false;

	}
	
	public BoardVo view (Long boardNo){
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "select  a.no, a.TITLE, a.content, b.no as users_no"
					   + "  from board a, users b"
					   + " where a.USERS_NO = b.NO"
					   + "   and a.no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);
			
			// 5. SQL 문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no        = rs.getLong(1);
				String title   = rs.getString(2);
				String content = rs.getString(3);
				Long userNo = rs.getLong(4);
				
				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setUserNo(userNo);
				
				return vo;	
			}
			
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
		
		
		return vo;
	}

	public boolean delete(Long boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "delete "
					   + "  from board"
					   + " where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);
			
			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();
			
			return count == 1;
			
		} catch (SQLException e) {
			System.out.println("SQLException err : 로딩 실패 - " + e);
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
		return false;
	}
		
	public boolean viewAddHit(Long boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "update board"
					    + "  set hit = hit + 1"
				       + " where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);

			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();

			return count == 1;

		} catch (SQLException e) {
			System.out.println("SQLException err : 로딩 실패 - " + e);
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

		return false;

	}
	
	public boolean modify(BoardVo boardvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "update board"
					  + " set title = ?, content = ?"
					  + " where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContent());
			pstmt.setLong(3, boardvo.getNo());
			

			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();
			
			return count == 1;
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

		return false;

	}
	
	public boolean replyInsert(BoardVo boardvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql = "insert into board"
					  + " (select seq_board.nextval, ?, ?, sysdate, 0, g_no, o_no+1, depth+1, ? "
					  + "   from board"
					  + "  where no = ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContent());
			pstmt.setLong(3, boardvo.getUserNo());
			pstmt.setLong(4, boardvo.getNo());
			
			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();
			
			return count == 1;
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

		return false;

	}
	
	public boolean replyUpdate(BoardVo boardvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			// 3. SQL문 실행

			String sql1 = "select g_no, o_no"
					  + "    from board"
					  + "   where no = ?";
			
			String sql2 = "update board a"
					    + "   set o_no = o_no + 1"
					    + " where g_no = ?"
					    + "   and o_no >= ?"
					    + "   and no <> (select max(no) from board b where b.g_no = ? and b.o_no = ?)";

			pstmt = conn.prepareStatement(sql1);

			System.out.println("boardvo.getNo():"+boardvo.getNo());
			pstmt.setLong(1, boardvo.getNo());
			
			rs = pstmt.executeQuery();
			rs.next();
			Long gNo = rs.getLong(1);
			Long oNo = rs.getLong(2);
			
			
			System.out.println("gNO:"+gNo+",oNo:"+oNo);
			pstmt = conn.prepareStatement(sql2);
			
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo+1);
			pstmt.setLong(3, gNo);
			pstmt.setLong(4, oNo+1);
			
			
			// 5. SQL 문 실행
			int count = pstmt.executeUpdate();
			
			return count == 1;
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

		return false;

	}
	
	
}
