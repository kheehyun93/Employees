<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/Employees/css/login.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	var id;
	
	$(function() {
		$(".e_id").html(id);
		$("input[name=e_id]").attr("value",id);
		
		// 비밀번호 일치 확인
		$("input[name=newPwd2]").keyup(function() {
			$("#pwdChk").html("비밀번호 입력");
			if($("input[name=newPwd1]").val()!=""&&$("input[name=newPwd1]").val()==$("input[name=newPwd2]").val()){
				$("#pwdChk").html("비밀번호가 동일합니다.").css("color","blue");
			}else if($("input[name=newPwd1]").val()==""){
				$("#pwdChk").html("비밀번호를 입력해주세요.");
			}else{
				$("#pwdChk").html("비밀번호가 일치하지 않습니다.").css("color","red");
			}
		});
		
		$("input[name=newPwd1]").keyup(function() {
			
			if($("input[name=newPwd2]").val()!=""&&$("input[name=newPwd1]").val()==$("input[name=newPwd2]").val()){
				$("#pwdChk").html("비밀번호가 동일합니다.").css("color","blue");
			}else if($("input[name=newPwd2]").val()==""){
				$("#pwdChk").html("비밀번호를 입력해주세요.");
			}else{
				$("#pwdChk").html("비밀번호가 일치하지 않습니다.").css("color","red");
			}
		});
		
		$("#confirmBtn").click(function() {
			if($("input[name=pwd]").val()==""){
				alert("비밀번호를 입력해주세요.");
			}else if($("input[name=newPwd1]").val()==""){
				alert("비밀번호를 입력해주세요.");
			}else if($("input[name=newPwd2]").val()==""){
				alert("비밀번호를 입력해주세요.");
			}else if($("input[name=newPwd1]").val() != $("input[name=newPwd2]").val()){
				alert("비밀번호를 다시 한번 확인해주세요.");
			}else{
				$(function() {
					$.ajax({
						type : "post",
						url : "/Employees/EmpController",
						data : "type=pwdUpdate&e_id="+$("input[name=e_id]").val()+"&pwd="+$("input[name=pwd]").val()
								+"&newPwd="+$("input[name=newPwd2]").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4) != "fail"){
								alert("비밀번호 변경 성공");
								$("input[name=pwd]").val("");
								$("input[name=pwd]").focus();
								$("input[name=newPwd1]").val("");
								$("input[name=newPwd2]").val("");
								$("#pwdChk").html("");
							}else{
								alert("비밀번호를 확인해주세요.");
							}
						},
						error : function(request,status,error) {
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							alert("비밀번호 변경 실패.");
						}
					});
				});
			}
		});
		
		$("#cancelBtn").click(function() {
			self.close();
		});
	});
	
</script>
</head>
<body>
	<div class="wrap">
		<div id="header">
			<img src="/Employees/img/ican.PNG"/><br/>
			<h1><span class="e_id"></span>님의 비밀번호 변경</h1>
		</div>
		<div id="section">
			<div id="article">
				<form>
					<input type="hidden" name="e_id" value=""/><br/>
					<input type="password" name="pwd" class="inputText" placeholder="현재 비밀번호"/><br/>
					<input type="password" name="newPwd1" class="inputText" placeholder="새 비밀번호"/><br/>
					<input type="password" name="newPwd2" class="inputText" placeholder="새 비밀번호 확인"/><br/>
					<span id="pwdChk"></span><br/>
					<input type="button" value="확인" class="inputBtn" id="confirmBtn"/>
					<input type="button" value="취소" class="inputBtn" id="cancelBtn"/>
				</form>
			</div>
		</div>
	</div>
</body>
</html>