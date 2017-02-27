package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.BoardVo;

import oracle.jdbc.pool.OracleDataSource;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private OracleDataSource dataSource;

	public List<BoardVo> getList(Long pageCount, Long maxRow, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagecount", pageCount);
		map.put("maxrow", maxRow);
		map.put("keyword", keyword);
		

		return sqlSession.selectList("board.getList", map); 

	}

	public Long getTotalByRow(Long maxRow, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maxrow", maxRow);
		map.put("keyword", keyword);

		Long ret = sqlSession.selectOne("board.getTotalByRow", map);
		
		return ret;

	}

	public boolean insert(BoardVo boardvo) {

		// 5. SQL 문 실행
		int count = sqlSession.insert("board.insert", boardvo);
		return count == 1;

	}

	public BoardVo view (Long boardNo){
		
		return sqlSession.selectOne("board.select", boardNo);
		
	}

	public BoardVo getAll(Long boardNo){
		
		return sqlSession.selectOne("board.selectall", boardNo);
		
	}

	public boolean delete(Long boardNo) {

		int count = sqlSession.delete("board.delete", boardNo);
		return count == 1;

	}

	public boolean viewAddHit(Long boardNo) {
		int count = sqlSession.update("board.addHit", boardNo);
		return count == 1;

	}

	public boolean modify(BoardVo boardvo) {

		int count = sqlSession.update("board.update", boardvo);
		return count == 1;

	}

	public boolean replyInsert(BoardVo boardvo) {
//		System.out.println(boardvo);
		int count = sqlSession.insert("board.replyinsert", boardvo);
		return count == 1;

	}

	public boolean replyUpdate(BoardVo boardvo) {
		int count = sqlSession.update("board.replyupdate", boardvo);
		return count == 1;
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try{
//			conn = dataSource.getConnection();
//			// 3. SQL문 실행
//
//			String sql1 = "select g_no, o_no"
//					+ "    from board"
//					+ "   where no = ?";
//
//			String sql2 = "update board a"
//					+ "   set o_no = o_no + 1"
//					+ " where g_no = ?"
//					+ "   and o_no >= ?"
//					+ "   and no <> (select max(no) from board b where b.g_no = ? and b.o_no = ?)";
//
//			pstmt = conn.prepareStatement(sql1);
//
//			System.out.println("boardvo.getNo():"+boardvo.getNo());
//			pstmt.setLong(1, boardvo.getNo());
//
//			rs = pstmt.executeQuery();
//			rs.next();
//			Long gNo = rs.getLong(1);
//			Long oNo = rs.getLong(2);
//
//
//			System.out.println("gNO:"+gNo+",oNo:"+oNo);
//			pstmt = conn.prepareStatement(sql2);
//
//			pstmt.setLong(1, gNo);
//			pstmt.setLong(2, oNo+1);
//			pstmt.setLong(3, gNo);
//			pstmt.setLong(4, oNo+1);
//
//
//			// 5. SQL 문 실행
//			int count = pstmt.executeUpdate();
//
//			return count == 1;
//		} catch (SQLException e) {
//			System.out.println("SQLException err : 로딩 실패 - " + e);
//		} finally {
//			// 자원 정리
//			try {
//				if(rs != null) rs.close();
//				if(pstmt != null) pstmt.close();
//
//				if(conn != null){
//					conn.close();
//				}
//			} catch (SQLException e) {
//				System.out.println("ERR:" + e);
//			}
//		}
//
//		return false;

	}


}
