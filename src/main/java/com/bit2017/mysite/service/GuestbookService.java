package com.bit2017.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.GuestbookDao;
import com.bit2017.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;

	public List<GuestbookVo> getList() {
		return guestbookDao.getList();
	}

	public void insert(GuestbookVo vo) {
		guestbookDao.insert(vo);
		
	}

	public void delete(Long no, String password) {
		guestbookDao.delete(no, password);
		
	}

	public GuestbookVo getList(Long number) {
		return guestbookDao.getList(number);
	}

	public void modify(GuestbookVo vo) {
		guestbookDao.modify(vo);
		
	}
	
	
	
}
