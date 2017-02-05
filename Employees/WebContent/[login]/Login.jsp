<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Employees/css/login.css"/>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#id").keyup(function() {
			var chk;
			var id_length = id.value.length;
			
			// 아스키 코드값으로 숫자 확인
			for (var i = 0; i < id_length; i++) {
				chk = id.value.charCodeAt(i);
				if(chk>=48 && chk<=57){
					
				}else{
					alert("사원번호는 숫자만 입력해주세요.")
					id.value="";
					return;
				}
			}
		});
		$("#pwd").keyup(function() {
			var chk;
			var pwd_length = pwd.value.length;
			
			// 아스키 코드값으로 숫자,대문자,소문자 확인
			for(var i = 0; i< pwd_length; i++){
				chk = pwd.value.charCodeAt(i);
				if((chk>=65 && chk<=90) || (chk>=97 && chk<=122) || (chk>=48 && chk<=57)){
					
				}else{
					alert("비밀번호는 영문과 숫자만 입력 가능합니다.");
					pwd.value="";
					return;
				}
			}
		});
		
		// 버튼 이벤트
		$("#loginBtn").click(function() {
			if($("#id").val()== ""){
				alert("사원번호를 입력해주세요.");
			}else if($("#pwd").val()==""){
				alert("비밀번호를 입력해주세요.")
			}else{
				$(function() {
					$.ajax({
						type : "post",
						url : "/Employees/LoginController",
						data : "type=login"+"&id="+$("#id").val()+"&pwd="+$("#pwd").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4) != "fail"){
								alert("로그인 성공");
								location.href=data;
							}else{
								alert("일치하는 정보가 없습니다.");
							}
						},
						error : function(request, status, error) {
							alert("request: " + request + " status: " + status
									+ " error: " + error);
						}
					});
				});
			}
		});
		
		// 엔터키 이벤트
		$(".inputText").keydown(function(key) {
			if(key.keyCode==13){
				if($("#id").val() == ""){
					alert("사원번호를 입력해주세요.");
				}else if($("#pwd").val()==""){
					alert("비밀번호를 입력해주세요.")
				}else{
					$(function() {
						$.ajax({
							type : "post",
							url : "/Employees/LoginController",
							data : "type=login"+"&id="+$("#id").val()+"&pwd="+$("#pwd").val(),
							dataType : "text",
							success : function(data) {
								if(data.substring(0,4) != "fail"){
									alert("로그인 성공");
									location.href=data;
								}else{
									alert("일치하는 정보가 없습니다.");
								}
							},
							error : function(request, status, error) {
								alert("request: " + request + " status: " + status
										+ " error: " + error);
							}
						});
					});
				}
			}
		});
		
	});
</script>

</head>
<body>
	<div class="wrap">
		<div id="header">
			<img src="/Employees/img/ican.PNG"/><br/>
			<h1>아이캔매니지먼트 사원번호로 로그인</h1>
			<h2>사원관리</h2>
		</div>
		<div id="section">
			<div id="article">
				<form>
					<img src="/Employees/img/usersIcon.PNG"/><br>
					<input type="text" name="id" id="id" class="inputText" placeholder="사원번호 입력"/><br/>
					<input type="password" name="pwd" id="pwd" class="inputText" placeholder="비밀번호 입력"/><br/>
					<input type="button" value="로그인" class="inputBtn" id="loginBtn"/><br/>
					<div id="f_a">
						<a class="find_a" href="FindId.jsp">사원번호</a>&nbsp;<a id="find_a">/</a>&nbsp;<a class="find_a" href="../[login]/FindPwd.jsp">비밀번호 찾기</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>