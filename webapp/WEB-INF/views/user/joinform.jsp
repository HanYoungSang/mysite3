<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function(){
	$("#join-form").submit( function() {
		/* 회원 가입 폼 유효성 검증 (validation) */
		
// 		// 1. 이름
// 		var name = $( "#name ").val();
// 		if ( name == "" ) {
// 			alert( "이름이 비여 있습니다." );
// 			$( "#name ").focus();
// 			return false
// 		}
// 		// 2. 이메일
// 		var name = $( "#email ").val();
// 		if ( name == "" ) {
// 			alert( "Email이 비여 있습니다." );
// 			$( "#email ").focus();
// 			return false
// 		}
		
// 		// 3. 비밀번호
// 		var password = $("input[type='password']").val();
// 		if( password == "" ) {
// 			alert("패스워드가 비였습니다.");
// 			$( "input[type='password']").focus();
// 			return false
// 		}
		
// 		// 4. 약관동의
// 		var isChecked = $( "#agree-prov" ).is(":checked");
// 		if( isChecked == false ){
// 			alert("약관 동의를 해주세요");
// 			return false;
// 		}
		
//     	// 5. 중복 체크 확인
//     	var isVisible = $( "#emailcheck" ).is(":visible");
//     	if( isVisible == false) {
//     		alert("이메일 중복 체크를 해주세요");
//     		return false;
//     	}
		
// 		return false;
	})
	
	$( "input[type='button']" ).click ( function(){
// 		console.log("checked");
		var email = $( "#email" ).val();
		if( email == "" ) {
			alert( "Email이 비여 있습니다." );
			$( "#email ").focus();
			return;
		}
		
		// Ajax 통신
		$.ajax( {
		    url : "/mysite3/user/checkemail?email="+email,
		    type: "get",
		    dataType: "json",
		    data: "",
		//  contentType: "application/json",
		    success: function( response ){
		    	console.log	( response );
// 		       if( response.result == "failed") {
// 		    	   console.log( response );
// // 		    	   return;
// 		       }
		    	//통신 성공 (response.result == "success" )
		    	
		    	if( response.data == "exist" ) {
// 		    		alert("존재하는 이메일 입니다. 다른 이메일을 사용해 주세요");
		    		$( "#dialogMessage" ).dialog();
		    		$("#email").
// 		    		val("").
		    		focus();
		    		
		    		return;
		    	}
		    	$( "#emailcheck" ).show();
		    	$( "input[type='button']" ).hide();
		    	
		       return true;
		    },
		    error: function( XHR, status, error ){
		       console.error( status + " : " + error );
		    }

		   });

	});
	
	$( "#email" ).change ( function() {
		$( "#emailcheck" ).hide();
    	$( "input[type='button']" ).show();
	});
	
	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form 
					modelAttribute="userVo"
					id="join-form" name="joinForm" method="post"
					action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name" /> 
<!-- 					<input id="name" name="name" type="text" value=""> -->
					<spring:hasBindErrors name="userVo">
   						<c:if test="${errors.hasFieldErrors('name') }">
							<p style="text-align:left;padding:5px 0; color:red">
        						<strong>
       								<spring:message 
	     								code="${errors.getFieldError( 'name' ).codes[0] }" 
	     								text="${errors.getFieldError( 'name' ).defaultMessage }" />
        						</strong>
   							</p>
   						</c:if>
					</spring:hasBindErrors>
					<label class="block-label" for="email">이메일</label>
<!-- 					<input id="email" name="email" type="text" value="">  -->
					<form:input path="email" />
					<input type="button" value="id 중복체크">
					<img id="emailcheck" src="/mysite3/assets/images/check.png"	style="width: 25px; display: none">
					<p style="font-weight:bold; text-align:left;padding:5px 0; color:red">
						<form:errors path="email" />
					</p> 
					
					<label class="block-label">패스워드</label>
					<form:password path="password" />
<!-- 					<input name="password" type="password" value=""> -->
					<p style="font-weight:bold; text-align:left;padding:5px 0; color:red">
						<form:errors path="password" />
					</p>
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female"
							checked="checked"> <label>남</label> <input type="radio"
							name="gender" value="male">
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">


				</form:form>

			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />

	</div>
	<div id="dialogMessage" title="이메일 중복 체크" style="display: none;">
		<p>존재하는 이메일 입니다.<br> 다른 이메일을 사용해 주세요</p>
	</div>


</body>
</html>