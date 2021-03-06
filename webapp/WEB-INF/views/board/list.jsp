<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board/list" method="post">
					<input type="text" id="kwd" name="kwd" value="${keyword}">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호 </th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach var="vo" items="${mapListInfo.list}">				
						<tr>
							<td>${vo.no}</td>
							<td class="left" style="padding-left:${20*vo.depth}px">
								<c:if test="${vo.depth != 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath}/board/view/${vo.no}?pageno=${mapListInfo.pageno}&kwd=${keyword}">${vo.title}</a>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td><c:if test="${authUser.no == vo.userNo}">
									<a href="${pageContext.request.contextPath}/board/delete/${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath}/board/${mapListInfo.pageno-1}?kwd=${keyword}"> ◀</a></li>
						<c:forEach var="cnt" begin="${mapListInfo.beginpage}" end="${mapListInfo.lastpage}">
							<c:choose>
								<c:when test="${mapListInfo.pageno == cnt }">
									<li class="selected">${cnt}
								</c:when>
								<c:otherwise>
									<li>
									<a href="${pageContext.request.contextPath}/board/${cnt}?kwd=${keyword}">${cnt}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath}/board/${mapListInfo.pageno+1}?kwd=${keyword}">▶</a></li>
					</ul>
				</div>				
				<div class="bottom">
					<c:if test="${! empty authUser}">
						<a href="${pageContext.request.contextPath}/board/writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>