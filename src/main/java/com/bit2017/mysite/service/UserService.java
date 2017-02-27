package com.bit2017.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.UserDao;
import com.bit2017.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public boolean join (UserVo uservo) {

		return userDao.insert(uservo);
	}

	public boolean exists(String email) {
		UserVo userVo = userDao.get(email);
		return (userVo != null);
	}
	
	public UserVo getUser(String email, String password) {

		UserVo userVo = userDao.get(email, password);
		return userVo;
	}

	public UserVo getUser(Long no) {
		UserVo userVo = userDao.get(no);
		return userVo;
	}

	public Boolean modify(UserVo uservo) {
		// TODO Auto-generated method stub
		return userDao.modify(uservo);
		
	}


}
