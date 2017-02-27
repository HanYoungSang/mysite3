package com.bit2017.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.BoardDao;
import com.bit2017.mysite.vo.BoardVo;

@Service
public class BoardService {

	// 한 페이지당 최대 표시 게시물 수
	private final Long maxRow = 10L;

	// 하단 페이지 번호 최대 표시 개수
	private final Long maxPage = 5L;

	@Autowired
	private BoardDao boardDao;

	// 게시물 보기
	public Map<String, Object> getList(Long currentPage, String keyword) {
		// boardDao.getList(currentPage, maxRow);
		Map<String, Object> mapListInfo = new HashMap<>();
		List<BoardVo> list = null;

		// 페이지가 없거나 마이너스이면 1로 초기화
		if (currentPage <= 0 || currentPage == null)
			currentPage = 1L;

		Long beginpage = 1L;
		Long lastpage = 1L;

		// DB로 전체 페이지 구하기 (MAXROW를 넣어서 나눔)
		lastpage = boardDao.getTotalByRow(maxRow, keyword);

//		System.out.println("lastpage:"+lastpage +",keyword:"+ keyword+":lastpage:"+lastpage);
		
		// 현재페이지를 전체페이지보다 높은 숫자로 호출시 max로 고정
		if (currentPage >= lastpage)
			currentPage = lastpage;

		// 현재 페이지 기준으로 시작 페이지와 마지막 페이지 설정
		if (lastpage > maxPage) { // 전체 페이지가 최대 페이지보다 클 때 = 앞 혹은 뒤를 짤라서 뿌려줘야함
			// System.out.println("else ");
			if (currentPage >= (lastpage - Math.floor(maxPage / 2))) {
//				 System.out.println(" if( currentPage >= ( totalCount - Math.floor(maxPage/2)):" + ( lastpage -Math.floor(maxPage/2)));
				beginpage = lastpage - maxPage + 1;
			} else if (currentPage <= Math.ceil(maxPage / 2)) {
//				 System.out.println(" } else if ( currentPage <= Math.ceil(maxPage/2) ){ "+ Math.ceil(maxPage/2));
				lastpage = maxPage;
			} else {
//				 System.out.println(" else {");
				beginpage = (long) (currentPage - Math.floor(maxPage / 2));
				lastpage = maxPage - beginpage + 1; //(long) (currentPage + Math.floor(maxPage / 2));
			}
		}

		list = boardDao.getList(currentPage, maxRow, keyword);

//		System.out.println("pageno:"+currentPage+",beginpage:"+beginpage+",lastpage:"+lastpage);
		
		mapListInfo.put("list", list);
		mapListInfo.put("pageno", currentPage);
		mapListInfo.put("beginpage", beginpage);
		mapListInfo.put("lastpage", lastpage);
		
		return mapListInfo;
	}

	// 글 작성
	public boolean write(BoardVo boardVo) {
		return boardDao.insert(boardVo);

	}
	// 글 보기
	public BoardVo view(Long boardNo) {
		// 조회수 증가
		boardDao.viewAddHit(boardNo);
		return boardDao.view(boardNo);

	}

	// 글 수정
	public boolean modify(BoardVo boardVo) {
		return boardDao.modify(boardVo);
	}
	
	// 글 삭제
	public boolean delete(Long boardNo) {
		
		return boardDao.delete(boardNo);
		
	}
	// 답글 작성
	public boolean reply(BoardVo boardVo) {
		boolean ret = true;
		// 원래 게시물 번호
		Long orgBoardNo= boardVo.getNo();
		BoardVo boardVoAll = boardDao.getAll(orgBoardNo);
		
		boardDao.replyInsert(boardVo);
		boardDao.replyUpdate(boardVoAll);
		return ret; 
		
	}


}
