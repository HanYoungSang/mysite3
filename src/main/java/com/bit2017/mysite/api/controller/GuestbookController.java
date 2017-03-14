package com.bit2017.mysite.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.service.GuestbookService;
import com.bit2017.mysite.vo.GuestbookVo;

@Controller ("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list/{page}")
	public JSONResult list(	@PathVariable (value="page") 
						Integer page) {
		
		List<GuestbookVo> list = guestbookService.getAjaxList(page);
		JSONResult jsonResult = JSONResult.success( list );
		
		return jsonResult;
	}

	@ResponseBody
	@RequestMapping("/add")
	public JSONResult add(	@ModelAttribute @Valid
							GuestbookVo vo,
							BindingResult result
						    ) {
		
		if(result.hasErrors()) {
		       System.out.println("err");
		      
		       return JSONResult.success( vo );
		}
		guestbookService.insert(vo);
//		System.out.println(guestbookService.getList(vo.getNo()));
		JSONResult jsonResult = JSONResult.success( guestbookService.getList(vo.getNo()));
		
		
//		System.out.println(jsonResult);
		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(	@ModelAttribute
						    	GuestbookVo vo ) {
		
//		System.out.println("delete:" + vo);
		boolean result = guestbookService.delete(vo.getNo(), vo.getPassword());

		return JSONResult.success(result ? vo.getNo() : -1);
		
	}
	
	
}
