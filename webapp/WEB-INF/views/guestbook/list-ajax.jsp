<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var page = 0;
var isEnd = false;
var dialogDeleteform = null;

var render = function( vo, isAppend ) {
	var html =  "<li id='li-"+vo.no+"'>" +
				"<strong>" + vo.name + 	"</strong>" +
				"<p>" + vo.content + "</p>" +
				"<strong>"+vo.regDate+"</strong>" +
				"<a href='' title='삭제' data-no='" + vo.no + "'>삭제</a>" +
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
// 		       console.error( status + " : " + error );
// 		       console.error("error"+response.data);
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
	
	
	//삭제 버튼 클릭 이벤트 매핑 (Live Event Mapping)
	$( document ).on( "click", "#list li a", function(event) {
		event.preventDefault();
		var $a = $(this);
		var no = $a.attr("data-no");
		$("#no-delete").val(no);
		dialogDeleteform.dialog("open");
	});
	
	
	dialogDeleteform = $("#dialog-form").dialog({
		autoOpen : false,
		height : 200,
		width : 350,
		modal : true,
		buttons : {
		"삭제" : function(){
			var no = $("#no-delete").val();
			var password = $("#password-delete").val();
			/* ajax 통신 */
			$.ajax( {
			    url : "/mysite3/api/guestbook/delete",
			    type: "post",
			    dataType: "json",
			    data: "no="+no+"&password="+password,
			//  contentType: "application/json",
			    success: function( response ){
			    	console.log(response);
			    	if ( response.result != "success"){
			    		console.log(response.message);
			    		dialogDeleteform.dialog("close");
			    		return;
			    	}
			    	
			    	// 삭제 실패(No 번호가 없거나 비번이 틀렸을때)
			    	if ( response.data == -1 ){
			    		$("#delete-tip-normal").hide();
			    		$("#delete-tip-error").show()
			    		$("#password-delete").
			    			val( "" ).
			    			focus();
			    		return;
			    	}
			    	
			    	//삭제 성공
			    	// li- 엘리먼트 삭제
			    	$("#li-"+response.data).remove();
			    	
			    	//폼 리셋
			    	$("#delete-tip-normal").show();
		    		$("#delete-tip-error").hide();
		    		$("#password-delete").val("");
		    		//다이얼로그 닫기
			    	dialogDeleteform.dialog("close");
			    	
			    },
			    error: function( XHR, status, error ){
			       console.error( status + " : " + error );
			    }
		   	});
			
// 			console.log("삭제:"+no+":"+password);
		},
		"취소" : function() {
			$(this).dialog("close");
			}
		},
		close : function() {

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
				<form:form 
					modelAttribute="guestbookVo" 
					id="write-form" action="" method="post">
					<table>
						<tr>
							<td>이름</td>
							<td><c:choose>
									<c:when test="${!empty authUser}">
										${authUser.name}
										<input type="hidden" name="name" value="${authUser.name}">
									</c:when>
									<c:otherwise>
										<input type="text" name="name">
									</c:otherwise>
								</c:choose>
								<p style="font-weight:bold; text-align:left;padding:5px 0; color:red">
									<form:errors path="name" />
								</p> 
								</td>
							<td>비밀번호</td>
							<td><input type="password" name="password" id="password">
								<p style="font-weight:bold; text-align:left;padding:5px 0; color:red">
									<form:errors path="password" />
								</p>
							</td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea>
							<p style="font-weight:bold; text-align:left;padding:5px 0; color:red">
									<form:errors path="content" />
							</p></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form:form>
				<ul id="list">
				</ul>
				<!-- 				<button id="btn-next" title="ss">다음</button> -->
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<div id="dialog-form" title="삭제하기">
		<p id="delete-tip-normal" class="validateTips" style="padding:20px 0; font-weight: bold; font-size: 14px">삭제하기 위해 비밀번호를 입력하세요</p>
		<p id="delete-tip-error" class="validateTips" style="padding:20px 0; font-weight: bold; font-size: 14px; color: #ff0000; display: none;">비밀번호가 틀렸습니다.</p>
		<form>
			<input type="hidden" id="no-delete" value=""/>
			<label for="password">비밀번호</label>
   			<input type="password" name="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
	    	<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
		</form>
	</div>


</body>
</html>