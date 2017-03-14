package com.bit2017.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.security.Auth;
import com.bit2017.mysite.security.AuthUser;
import com.bit2017.mysite.service.UserService;
import com.bit2017.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(
			@RequestParam( value ="email", required=true, defaultValue="")
			String email
			){
		
		boolean isExists = userService.exists(email);
		
		return JSONResult.success(isExists? "exist":"not exist");
	}
	
	
	@RequestMapping("/joinform")
	public String joinform( @ModelAttribute UserVo userVo ){
		return "/user/joinform";
	}
	@RequestMapping("/join")
	public String join( @ModelAttribute @Valid UserVo uservo, 
						BindingResult result,
						Model model){
		
		if(result.hasErrors()) {
			// 에러 출력
//			List<ObjectError> list = result.getAllErrors();
//		       for (ObjectError e : list) {
//		            System.out.println(" ObjectError : " + e );
//		       }
				
//		       userService.join(vo);
		       model.addAllAttributes( result.getModel() );
		       return "/user/joinform";
		}
		
//		if( userService.join(uservo) )
			return "redirect:/user/joinsuccess";
//		else 
//			return "redirect:/user/joinform";
	}
	
	@RequestMapping("/joinsuccess")
	public String join(){
		return "/user/joinsuccess";
	}
	
	@RequestMapping("/loginform")
	public String loginform(){
		return "/user/loginform";
	}
	
//	@RequestMapping("/login")
//	public String login(
//						@RequestParam(value ="email", required=true, defaultValue="") String email,
//						@RequestParam(value ="password", required=true, defaultValue="") String password,
//						HttpSession session){
//		UserVo userVo = userService.getUser( email, password);
//		if( userVo == null ){
//			return "redirect:/user/loginform?result=fail";
//		} 
//		
//		//인증 처리
//		session.setAttribute("authUser",userVo);
//		return "redirect:/main";
//			
//	}
	
//	@RequestMapping("/logout")
//	public String logout( HttpSession session ) {
//
//		//인증 해제
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/main";
//	}

	/*
	@Auth		// 사용자 어노테이션을 생성하면 세선 처리가 자동으로 처리
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	if (authUser == null) {
		return "redirect:/main";
	}
*/
	@Auth
	@RequestMapping("/modifyform")
	public String modifyform(
						@AuthUser
						UserVo authUser,
						Model model){
		
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute("userVo", userVo);
		return "/user/modifyform";
	}		
	
	@Auth
	@RequestMapping("/modify")
	public String modify( 	@ModelAttribute UserVo uservo,
							@AuthUser
							UserVo authUser
							){
		
		if ( userService.modify(uservo) ){
			authUser.setName(uservo.getName());
		}
		return "redirect:/user/modifyform";
	}
}
