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

	public boolean insert(GuestbookVo vo) {
		return guestbookDao.insert(vo);
	}

	public boolean delete(Long no, String password) {
		return guestbookDao.delete(no, password);
		
	}

	public GuestbookVo getList(Long number) {
		return guestbookDao.getList(number);
	}

	public void modify(GuestbookVo vo) {
		guestbookDao.modify(vo);
		
	}

	public List<GuestbookVo> getAjaxList(Integer page) {
		return guestbookDao.getAjaxList(page);
	}
	public GuestbookVo getListOne() {
		return guestbookDao.getListOne();
	}
	
	
	
}
