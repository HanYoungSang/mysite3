package com.bit2017.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getList() {
		
		return sqlSession.selectList("gallery.getList"); 
	}

	public boolean insert(GalleryVo vo) {
				
		return sqlSession.insert("gallery.insert", vo) == 1;
	}
	
}
