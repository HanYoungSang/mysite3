package com.bit2017.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2017.mysite.service.GuestbookService;
import com.bit2017.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value={"","/list"} )
	public String list(Model model){
		
		List<GuestbookVo> list = guestbookService.getList();
		model.addAttribute("list", list);
		return "/guestbook/list";
	}
	
	@RequestMapping(value={"/list-ajax"} )
	public String list(){

		return "/guestbook/list-ajax";
	}
	
	@RequestMapping("/insert")
	public String add(	@ModelAttribute @Valid GuestbookVo vo,
						BindingResult result){
		if(result.hasErrors()) {
		       
		       return "/guestbook/list";
		}
		guestbookService.insert(vo);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping("/deleteform/{no}")
	public String deleteform(@PathVariable("no") 
							 String number,
							 Model model){
		model.addAttribute("no", number);
		return "/guestbook/deleteform";
	}
	
	@RequestMapping("/delete")
	public String delete(@ModelAttribute
							GuestbookVo vo){
		
		guestbookService.delete(vo.getNo(), vo.getPassword());
		return "redirect:/guestbook/";
	}
	
	@RequestMapping("/modifyform/{no}")
	public String modify(@PathVariable (value="no")
	                     Long number,
						Model model){
		
		GuestbookVo vo = guestbookService.getList(number);
		model.addAttribute("gb", vo);
		return "/guestbook/modifyform";
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute
							GuestbookVo vo){
		
		guestbookService.modify(vo);
		return "redirect:/guestbook/";
	}
}
