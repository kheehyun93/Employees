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
// 뒤로가기버튼 제어
history.pushState(null, null, location.href); 
window.onpopstate = function(event) { 
	history.go(1); 
}
	$(function() {

		$("#input").keyup(function() {
			var chk;
			var input_length = input.value.length;
			
			for (var i = 0; i < input_length; i++) {
				chk = input.value.charCodeAt(i);
				if((chk>=65 && chk<=90) || (chk>=97 && chk<=122) || (chk>=48 && chk<=57) || (chk==64)|| (chk==46)|| (chk==95)){
					
				}else{
					alert(" '.' , '@' , '_' 외 특수문자나 한글은 입력할 수 없습니다.");
					input.value="";
					return;
				}
			}
		});
		
		$("#jumin").keyup(function() {
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
				if(jumin_length>13){
					alert("더이상 입력할 수 없습니다.");
					jumin.value="";
					return;
				}
			}
		});
		
		$("#nextBtn").click(function() {
			if($("#input").val()==""){
				alert("이메일이나 전화번호를 입력해주세요~");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/LoginController",
						data : "type=inputCheck"+"&input="+$("#input").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4)=="find"){
								
								// 해당되는 id 존재함.
								$(".wrap1").hide();
								$(".wrap2").show();
								var id = data.substring(4);
								$("input[name=findId]").attr("value",id);
							}else{
								alert("email이나 전화번호와 일치하는 사원이 없습니다.");
							}
						},
						error : function(request,status,error) {
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("email이나 전화번호와 일치하는 사원이 없습니다.");
						}
						
					});
				});
			}
		});
		
		$("#confirmBtn").click(function() {
			if($("#name").val()==""){
				alert("이름을 입력해주세요~");
			}else if($("#jumin").val()==""){
				alert("주민번호를 입력해주세요~");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/LoginController",
						data : "type=idFinalChk"+"&id="+$("input[name=findId]").val()
								+"&name="+$("#name").val()+"&jumin="+$("#jumin").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4)=="find"){
								$(".wrap2").hide();
								$(".wrap3").show();
								var id = data.substring(4);
								$("#finalId").html(id);
							}else{
								alert("이름과 주민번호가 올바르지 않습니다.");
							}
						},
						error : function(request,status,error) {
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("이름과 주민번호가 올바르지 않습니다.");
						}
					});
				});
			}
		});
	});
	
</script>
</head>
<body>
	<!-- 첫화면. 가져온 값이 null이 라면 -->
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
						<a>계정과 연결된 이메일이나</a><br/>
						<a>전화번호를 입력하세요.</a><br/>
						<input type="text" name="input" id="input" class="inputText" placeholder="이메일 또는 전화번호 입력"/><br/>
						<input type="button" value="다음" class="inputBtn" id="nextBtn"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="../[login]/Login.jsp">로그인</a>
			</div>
		</div>
	</div>
	<!-- 이메일이나 전화번호로 가져온 값이 있다면 -->
	<div class="wrap2">
		<div class="wrap">
			<div id="header">
				<img src="/Employees/img/ican.PNG"/><br/>
				<h1>로그인에 문제가 있나요?</h1>
			</div>
			<div id="section">
				<div id="article">
					<form>
						<img style="width: 100px;" src="/Employees/img/findIcon.png"/><br/>
						<a>계정의 이름과</a><br/>
						<a>주민번호를 입력하세요.</a><br/>
						<input type="hidden" name="findId" value=""/>
						<input type="text" name="name" id="name" class="inputText" placeholder="이름 입력"/><br/>
						<input type="text" name="jumin" id="jumin" class="inputText" placeholder="주민번호 입력"/><br/>
						<input type="button" value="확인" class="inputBtn" id="confirmBtn"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="../[login]/Login.jsp">로그인</a>
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
						<img src="/Employees/img/confirmIcon.PNG"/><br/>
						<div id="article_a">
							<a>당신의 사원번호는</a><br/>
							<span id="finalId"></span><a>입니다.</a><br/>
						</div>
						<input type="button" value="로그인 페이지로 이동" class="inputBtn" onclick="location.href='../[login]/Login.jsp'"/>
					</form>
				</div>
			</div>
			<div id="footer">
				<a href="../[login]/FindPwd.jsp">비밀번호 찾기</a>
			</div>
		</div>
	</div>
		
</body>
</html>