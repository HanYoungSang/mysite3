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
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var page = 0;
var isEnd = false;

var render = function( vo, isAppend ) {
	var html =  "<li>" +
				"<strong>" + vo.name + 	"</strong>" +
				"<p>" + vo.content + "</p>" +
				"<strong>"+vo.regDate+"</strong>" +
				"<a href='${pageContext.request.contextPath}/guestbook/deleteform/"+ vo.no+"' title='삭제'>삭제</a>" +
				"</li>";
	if (isAppend == true) {
		$("#list").append(html);
	} else {
		$("#list").prepend(html);
	}
	//console.log(vo.name);
}

var fetchList = function() {
	if ( isEnd == true ){
		return;
	}
	++page;
	// Ajax 통신
	$.ajax( {
	    url : "/mysite3/api/guestbook/list/"+page,
	    type: "get",
	    dataType: "json",
	    data: "",
	//  contentType: "application/json",
	    success: function( response ){
	    	if( response.result != "success" ) {
	    		console.log( response.message );
	    		return;
	    	}
	    	if( response.data.length== 0 ) {
// 	    		console.log("FINISH");
	    		isEnd = true;
	    		return;
	    	}
// 	    	console.log( response );
			$( response.data ).each( function(index, vo){
// 				console.log( index + ":" + vo );
				render( vo , true);
			})
	    },
	    error: function( XHR, status, error ){
	       console.error( status + " : " + error );
	    }
   });
}

$(function(){
	$ ("#write-form").submit( function(event) {
		// 폼의 submit 기본 이벤트 처리를 막는다.
		event.preventDefault();
		
		/* ajax 입력 */
		$.ajax( {
		    url : "/mysite3/api/guestbook/add",
		    type: "post",
		    dataType: "json",
		    data: "name="+$( "input[name='name']" ).val() +"&" +
		    	  "password="+ $( "#password" ).val() +"&" +
		    	  "content="+$( "textarea" ).val()
		    	  ,
		//  contentType: "application/json",
		    success: function( response ){
		    	console.log( response );
		    	if( response.result != "success" ) {
		    		console.log( response.message );
		    		return;
		    	}
		    	if( response.data.length== 0 ) {
//	 	    		console.log("FINISH");
		    		isEnd = true;
		    		return;
		    	}
//	 	    	console.log( response );
				$( response.data ).each( function(index, vo){
//	 				console.log( index + ":" + vo );
					render( vo, false );
				})
				
		    },
		    error: function( XHR, status, error ){
		    	console.log("name="+$( "input[name='name']" ).val() +"&" +
		    			"password="+ $( "#password" ).val() +"&" +
				    	  "content="+$( "textarea" ).val())
		       console.error( status + " : " + error );
		       console.error("error"+response.data);
		    }
	   });
		return false;
	});
// 	$ ("#btn-next").click( function() {
// 		fetchList();
// 	});
	
	$( window ).scroll( function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		// 스크롤이 바닥이 되었을 때 
		if( scrollTop + windowHeight + 10 >= documentHeight ) {
			fetchList()
		}
	});
	//첫페이지 로딩
	fetchList();
	
});


</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="guestbook">
				<form id="write-form" action="" method="post">
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
							<td>비밀번호</td><td><input type="password" name="password" id="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id="list">
				</ul>
<!-- 				<button id="btn-next" title="ss">다음</button> -->
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		
	</div>
</body>
</html>