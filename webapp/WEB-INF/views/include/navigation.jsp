<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="navigation">
			<ul>
				<li><a href="${pageContext.request.contextPath}/main">한영상</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook/list-ajax">방명록(AJAX)</a></li>
				<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
			</ul>
		</div>