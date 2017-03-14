<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%pageContext.setAttribute( "newLine", "\n" );%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/lightbox.css" rel="stylesheet" type="text/css">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/lightbox.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(function(){
		// 업로드 다이알로그
		var dialogUpload = $( "#dialog-upload-form" ).dialog({
			autoOpen: false,
			height: 280,
			width: 300,
			modal: true,
			buttons: {
				"업로드": function() {
					$( "#dialog-upload-form form" ).submit();
					$( this ).dialog( "close" );
				},
				"취소" : function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				$( "#dialog-upload-form form" ).get(0).reset();	
			}
		});
		
		$("#upload-image").click( function(event) {
			event.preventDefault();
			dialogUpload.dialog( "open" );
		});
		
// 		var dialogView = $( "#dialog-show-form" ).dialog({
// 			autoOpen: false,
// 			height: 600,
// 			width: 800,
// 			modal: true,
// 			buttons: {
// 				"닫기" : function() {
// 					$( this ).dialog( "close" );
// 				}
// 			},
// 			close: function() {
// 				$( this ).dialog( "close" );
// 			}
// 		});
// 		$(".image").click( function(event) {
// 			event.preventDefault();
			
// 			var imgSrc = $(this).attr( "data-save" );
			
// 			$("#dialog-img").attr("src", "${pageContext.request.contextPath }/gallery/"+imgSrc);
// 			dialogView.dialog( "open" );
// 		});
		
		
	});	
	</script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="gallery">
				<div>
					<h1>갤러리</h1>
						<a href="" id="upload-image">이미지 올리기</a>
				</div>
				<ul>
					<c:forEach var="vo" items="${list}">
						<li>
							<a	href="gallery/${vo.saveFile}"
								data-lightbox="gallery"
								class="image"
								id="view-detail" 
								data-lightbox="img-${vo.no}"
<%-- 								data-save=${vo.saveFile } --%>
								style="background-image:url('${pageContext.request.contextPath }/gallery/${vo.saveFile}')">&nbsp;</a>
							<a	href="${pageContext.request.contextPath }/gallery/delete/${vo.no}"
								class="del-button"
								title="삭제">삭제</a>
						</li>
					</c:forEach>																																																					
				</ul>	
			</div>
			<div id="dialog-upload-form" title="이미지 업로드" style="display:none">
  				<p class="validateTips normal">이미지와 간단한 코멘트를 입력해 주세요.</p>
  				<form action="${pageContext.request.contextPath }/gallery/upload" 
  					  method="post"
  					  enctype="multipart/form-data">
					<label>코멘트</label>
					<input type="text" id="input-comments" name="comments" value="">
					<label>이미지</label>
					<input type="file" id="input-file" name="file">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-show-form" title="이미지 보기" style="display:none">
  				<img id="dialog-img" src ="" >
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>