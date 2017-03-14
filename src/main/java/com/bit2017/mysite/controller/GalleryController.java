package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2017.mysite.service.GalleryService;
import com.bit2017.mysite.vo.GalleryVo;


@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping(value={"","/list"})
	public String list(Model model) {
		
		List<GalleryVo> list = galleryService.getList();
		
		model.addAttribute("list", list);
		return "/gallery/list";
	}

	
	@RequestMapping("/upload")
	public String upload(	@RequestParam("comments")
							String comments,
							@RequestParam("file")
							MultipartFile file ) {
		
	
		String url = galleryService.insert(comments, file);
		
		
		return "redirect:/gallery/list";
	}

}
