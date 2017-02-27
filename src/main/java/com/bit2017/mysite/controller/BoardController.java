package com.bit2017.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2017.mysite.security.Auth;
import com.bit2017.mysite.security.AuthUser;
import com.bit2017.mysite.service.BoardService;
import com.bit2017.mysite.vo.BoardVo;
import com.bit2017.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value={"","list"})
	public String list(	@RequestParam(value="kwd", required=false, defaultValue="")
						String keyword,
						Model model) {
		
//		System.out.println(keyword);
		
		Map<String, Object> mapListInfo = boardService.getList(1L, keyword);
		model.addAttribute( "mapListInfo", mapListInfo );
		model.addAttribute("keyword", keyword);
		// pageSet ArrayList로 페이징 처리(1:현재페이지, 2:시작 페이지, 3:마지막페이지)
//		List<Long> pageSet = boardService.getTotalByRow(1L);
//		model.addAttribute("list", boardService.getList(pageSet.get(0)));
//		model.addAttribute("pageno", pageSet.get(0));
//		model.addAttribute("beginpage", pageSet.get(1));
//		model.addAttribute("lastpage", pageSet.get(2));
		
		return "/board/list";
	}
	
	@RequestMapping(value={"/{no}","/list/{no}"})
	public String list(
						@PathVariable(value="no")
					    Long currentPage,
					    @RequestParam(value="kwd", required=false, defaultValue="")
						String keyword,
			            Model model) {
		
		Map<String, Object> mapListInfo = boardService.getList(currentPage, keyword);
		model.addAttribute( "mapListInfo", mapListInfo );
		model.addAttribute("keyword", keyword);
//		if( currentPage <=0 ) currentPage = 1L;
//		
//		// pageSet ArrayList로 페이징 처리(1:현재페이지, 2:시작 페이지, 3:마지막페이지)
//		List<Long> pageSet = boardService.getTotalByRow(currentPage);
//		
//		model.addAttribute("list", boardService.getList(pageSet.get(0)));
//		model.addAttribute("pageno", pageSet.get(0));
//		model.addAttribute("beginpage", pageSet.get(1));
//		model.addAttribute("lastpage", pageSet.get(2));
//		
		return "/board/list";
	}
	
//	@RequestMapping(value={"","list","/{no}","/list/{no}"})
//	public String listAll(
//			@PathVariable(value="no")
//			Long currentPage,
//			Model model) {
//
//		//pageSet ArrayList로 페이징 처리(1:현재페이지, 2:시작 페이지, 3:마지막페이지)
//		List<Long> pageSet = boardService.getTotalByRow(currentPage);
//
//		model.addAttribute("list", boardService.getList());
//		model.addAttribute("pageno", pageSet.get(0));
//		model.addAttribute("beginpage", pageSet.get(1));
//		model.addAttribute("lastpage", pageSet.get(2));
//		return "/board/list";
//	}
	
	@RequestMapping("/view/{no}")
	public String view( @PathVariable("no")
						Long boardNo,
						@RequestParam(value="pageno", required=false, defaultValue="1")
						Long pageNo,
						@RequestParam(value="kwd", required=false, defaultValue="")
						String keyword,
			  			Model model){
		
		model.addAttribute("vo", boardService.view(boardNo));
		model.addAttribute("pageno", pageNo);
		model.addAttribute("keyword", keyword);
		
		return "/board/view";
	}
	
	@Auth
	@RequestMapping("/writeform")
	public String writeForm(){
		return "/board/write";
	}
	
	@Auth
	@RequestMapping("/write")
	public String write(@AuthUser
						UserVo authUser,
						@ModelAttribute
						BoardVo boardVo){
		if (authUser == null) {
			return "redirect:/user/loginform";
		} else {
			boardVo.setUserNo(authUser.getNo());
		}
		
		if (boardService.write(boardVo) ) {
			return "redirect:/board/list";
		} else {
			return "/board/writeform";
		}
		
	}
	
	@Auth
	@RequestMapping("/modifyform/{no}")
	public String modifyForm(	@PathVariable("no")
								Long boardNo,
								@RequestParam(value="pageno", required=false, defaultValue="1")
								Long pageNo,
								Model model){
		
		model.addAttribute("vo", boardService.view(boardNo));
		model.addAttribute("pageno", pageNo);
		return "/board/modify";
	}
	
	@Auth
	@RequestMapping("/modify")
	public String modify(
						@ModelAttribute
						BoardVo boardVo,
						@RequestParam(value="pageno", required=false, defaultValue="1")
						Long pageNo){

		
		if (boardService.modify(boardVo) ) {
			return "redirect:/board/view/"+boardVo.getNo()+"?pageno="+pageNo;
		} else {
			return "/board/modifyform/"+boardVo.getNo();
		}
		
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(	@PathVariable("no")
							Long boardNo,
							@RequestParam(value="pageno", required=false, defaultValue="1")
							Long pageNo){

		boardService.delete(boardNo);
		
		return "redirect:/board/"+pageNo;
	}

	@Auth
	@RequestMapping("/replyform/{no}")
	public String replyForm(	@PathVariable("no")
								Long boardNo,
								@RequestParam(value="pageno", required=false, defaultValue="1")
								Long pageNo,
								Model model){

		model.addAttribute("vo", boardService.view(boardNo));
		model.addAttribute("pageno", pageNo);
		return "/board/reply";
	}
	
	@Auth
	@RequestMapping("/reply")
	public String reply(@ModelAttribute
						BoardVo boardVo,
						@RequestParam(value="pageno", required=false, defaultValue="1")
						Long pageNo,
						@AuthUser
						UserVo authUser){
		boardVo.setUserNo(authUser.getNo());
		boardService.reply(boardVo);
	
		return "redirect:/board/"+pageNo;
	}
}
