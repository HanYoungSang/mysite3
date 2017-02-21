<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath}/guestbook/insert" method="post">
					<table>
						<tr>
							<td>이름</td><td>
								<c:choose>
									<c:when test="${!empty authUser}">
										${authUser.name}
										<input type="hidden" name="name" value="${authUser.name}">
									</c:when>
									<c:otherwise>
										<input type="text" name="name">
									</c:otherwise>
								</c:choose>
							</td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li>
						<c:set var="countList" value="${fn:length(list) }" />
						<c:forEach var="vo" items="${list}" varStatus="status">
							<table>
								<tr>
									<td>[ ${countList - status.index } ]</td>
									<td>${vo.name}</td>
									<td>${vo.regDate}</td>
									<td><a href="${pageContext.request.contextPath}/guestbook/deleteform/${vo.no}">삭제</a>
									<a href="${pageContext.request.contextPath}/guestbook/modifyform/${vo.no}">수정</a>
									</td>
									
								</tr>
								<tr>
									<td colspan=4>
									${fn:replace(vo.content, newLine, "<br>")}
									</td>
								</tr>
							</table>
						</c:forEach>
						<br>
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		
	</div>
</body>
</html>