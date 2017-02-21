package com.bit2017.mysite.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2017.mysite.repository.GuestbookDao;
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
	
	@RequestMapping("/insert")
	public String add(@ModelAttribute GuestbookVo vo){
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
