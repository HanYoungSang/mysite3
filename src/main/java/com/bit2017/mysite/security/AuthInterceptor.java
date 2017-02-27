package com.bit2017.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bit2017.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod == false) {
			return true;
		}
		
		
		// 1. @Auth 가 달고 있는 핸들러인가?
		Auth auth = ((HandlerMethod) handler).getMethodAnnotation( Auth.class );
		if ( auth == null ) {
			return true;
		}
		
		// 2. @Auth가 달려 있는 경우, Session에 authUser 가 있는 지 확인
		HttpSession session = request.getSession(false);
		if ( session == null ){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		// 세션에 authUser가 있는지, 없으면 로그인
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if ( authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		return true;
	}

}
