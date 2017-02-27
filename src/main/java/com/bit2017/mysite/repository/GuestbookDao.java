package com.bit2017.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.GuestbookVo;


@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSessions;

	public List<GuestbookVo> getList() {
		return sqlSessions.selectList("guestbook.getList");
	}
	
	public boolean insert (GuestbookVo guestbookVo) {
		
			int count  = sqlSessions.insert("guestbook.insert", guestbookVo);
			// INSERT 후에 PK 받아내기
//			System.out.println(guestbookVo);
			return count == 1;

	}
	public boolean delete (Long no, String password) {

			GuestbookVo guestbook = new GuestbookVo();
			guestbook.setNo(no);
			guestbook.setPassword(password);
			int count  = sqlSessions.delete("guestbook.delete", guestbook);
			return count == 1;
	}
	
	public List<GuestbookVo> getAjaxList(Integer page) {
		return sqlSessions.selectList("guestbook.getListByPage", page);
	}
	
	public GuestbookVo getListOne() {
		return sqlSessions.selectOne("guestbook.getListOne");
	}
	
	
	public GuestbookVo getList(Long number) {
		
		return sqlSessions.selectOne("guestbook.getListByNo", number);
	}
	
	public boolean modify (GuestbookVo vo){
		
			int count = sqlSessions.update("guestbook.update", vo);
			return count == 1;
		
	}


}