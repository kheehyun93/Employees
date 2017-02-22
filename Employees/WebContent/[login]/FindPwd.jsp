<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Employees/css/login.css"/>
<link rel="stylesheet" type="text/css" href="/Employees/css/find.css"/>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
// 뒤로가기 버튼 제어
history.pushState(null, null, location.href); 
window.onpopstate = function(event) { 
	history.go(1); 
}
	$(function() {
		
		$("#id").keyup(function() {
			var chk;
			var jumin_length = jumin.value.length;
			
			for (var i = 0; i < jumin_length; i++) {
				chk = jumin.value.charCodeAt(i);
				if(chk>=48 && chk<=57){
					
				}else{
					alert("잘못된 형식입니다.");
					jumin.value="";
					return;
				}
			}
		});
		
		$("#email").keyup(function() {
			var chk;
			var input_length = input.value.length;
			
			for (var i = 0; i < input_length; i++) {
				chk = input.value.charCodeAt(i);
				if((chk>=65 && chk<=90) || (chk>=97 && chk<=122) || (chk>=48 && chk<=57) || (chk==64) || (chk==95)){
					
				}else{
					alert(" _ , @ 외 특수문자나 한글은 입력할 수 없습니다.");
					input.value="";
					return;
				}
			}
		});
		
		$("#nextBtn").click(function() {
			if($("#id").val()==""){
				alert("사원번호를 입력해주세요~");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/LoginController",
						data : "type=pwdIdChk"+"&id="+$("#id").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4)=="find"){								
								// 해당되는 id 존재함.
								$(".wrap1").hide();
								$(".wrap2").show();
								var id = data.substring(4);
								
								$("input[name=findId]").attr("value",id);
							}else{
								alert("사원번호와 일치하는 사원이 없습니다.");
							}
						},
						error : function(request,status,error) {
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("사원번호와 일치하는 사원이 없습니다.");
						}
						
					});
				});
			}
		});
		
		$("#confirmBtn").click(function() {
			alert($("#email").val());
			if($("#email").val()==""){
				alert("이메일을 입력해주세요~");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/LoginController",
						data : "type=pwdEmailChk"+"&id="+$("input[name=findId]").val()
								+"&email="+$("#email").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4)=="find"){
								$(".wrap2").hide();
								$(".wrap3").show();
								var email = data.substring(4);
								var sendEmail = email;
								$("input[name=sendEmail]").attr("value",sendEmail);
								
								var e_length = email.length;
								var random = new Array();
								
								for (var i = 0; i < e_length/2; i++) {
									random[i] = Math.floor(Math.random()*e_length)+1;
									if((email.charAt(random[i])!='@')&&(email.charAt(random[i])!='.')&&(email.charAt(random[i])!='*')){
										var str = email.charAt(random[i]);
										email = email.replace(str,"*");
									}else{
										i=i-1;
									}
								}
								
								$("#finalId").html(email);
							}else{
								alert("이메일이 올바르지 않습니다.");
							}
						},
						error : function(request,status,error) {
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("이메일이 올바르지 않습니다.");
						}
					});
				});
			}
		});
		
		
		$("#email_go").click(function() {
			$(function() {
				$.ajax({
					type : "post",
					url : "/Employees/LoginController",
					data : "type=sendEmail&id="+$("input[name=findId]").val()+"&email="+$("input[name=sendEmail]").val(),
					dataType : "text",
					success: function(data) {
						if(data.substring(0,4) != "fail"){
							alert("이메일 발송 성공");
							location.href="Login.jsp";
						}else{
							alert("이메일 발송 실패");
						}
					},
					error : function(request,status,error) {
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				});
			});
		});
	});
	
</script>
</head>
<body>
	<!-- 첫화면 -->
	<div class="wrap1">
		<div class="wrap">
			<div id="header">
				<img src="/Employees/img/ican.PNG"/><br/>
				<h1>로그인에 문제가 있나요?</h1>
			</div>
			<div id="section">
				<div id="article">
					<form>
						<img src="/Employees/img/findIcon.png"/><br/>
						<a>사원번호를 입력하세요.</a><br/>
						<input type="text" name="input" id="id" class="inputText" placeholder="사원번호 입력"/><br/>
						<input type="button" value="다음" class="inputBtn" id="nextBtn"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="../[login]/Login.jsp">로그인</a>
			</div>
		</div>
	</div>
	<!-- 사원번호로 가져온 값이 있다면 -->
	<div class="wrap2">
		<div class="wrap">
			<div id="header">
				<img src="/Employees/img/ican.PNG"/><br/>
				<h1>로그인에 문제가 있나요?</h1>
			</div>
			<div id="section">
				<div id="article">
					<form>
						<img src="/Employees/img/findIcon.png"/><br/>
						<a>계정의 이메일을 입력하세요.</a><br/>
						<input type="hidden" name="findId" value=""/>
						<input type="text" name="email" id="email" class="inputText" placeholder="이메일 입력"/><br/>
						<input type="button" value="확인" class="inputBtn" id="confirmBtn"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="/Employees/[login]/Login.jsp">로그인</a>
			</div>
		</div>
	
	</div>
	
	<div class="wrap3">
		<div class="wrap">
			<div id="header">
				<img src="/Employees/img/ican.PNG"/><br/>
			</div>
			<div id="section">
				<div id="article">
					<form>
						<img style="width: 100px;" src="/Employees/img/confirmIcon.PNG"/><br/>
						<div id="article_a">
							<a style="font-weight: bold;">이메일 주소</a><br/>
							<a>일회용 비밀번호를 포함한 이메일을</a><br/>
							<span id="finalId"></span><br/>
							<a>으로 전송합니다.${finalId}</a><br/>
						</div>
						<input type="hidden" name="sendEmail" value=""/>
						<input type="button" value="보내기" class="inputBtn" id="email_go"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="/Employees/[login]/FindPwd.jsp">비밀번호 찾기</a>
			</div>
		</div>
	</div>
		
</body>
</html>