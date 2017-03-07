<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%-- <jsp:useBean id="empInfo" class="com.hb.vo.EmployeesVO" scope="session"/>
<jsp:setProperty property="*" name="empInfo"/> --%>

<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<style type="text/css">
	.sectionWrap{
		margin-left: 244px;
		margin-top: -96px;
	}
	table th,td{
		border: 1px solid black;
	}
	#pwd_go{
		color: #4d90fe;
		cursor: pointer;
		margin-right: 10px;
	}
	.pwdUpBtn{
		background-color: #4d90fe;
		border: 2px solid rgb(48, 121, 237);
		color: #fff;
		cursor: default;
		font-family: inherit;
		font-size: 11px;
    	font-weight: bold;
    	height: 37px;
    	line-height: 29px;
    	min-width: 46px;
    	text-align: center;
    	width: 50px;
	}
	.pwdDelBtn{
		background-color: #4d90fe;
		border: 2px solid rgb(48, 121, 237);
		color: #fff;
		cursor: default;
		font-family: inherit;
		font-size: 11px;
    	font-weight: bold;
    	height: 37px;
    	line-height: 29px;
    	min-width: 46px;
    	text-align: center;
    	width: 50px;
	}
	#btnWrap{
		width: 230px;
		margin-left: 442px;
    	margin-top: 17px;
	}
</style>
<script type="text/javascript">
	var e_id = "${firstInfo.e_id }";

	function pwdUpdate_go() {
		var id = $(".tr_id").closest("tr").find(".e_id").text();
		newWindow = window.open("[employees]/PwdUpdatePopup.jsp", "newWindow",
        "height=750, width=660, resizable=yes");
		
		newWindow.id=id;
	}
	$(function() {
		// 1. 기본사항 - 입사일자랑 비교하여 경력기간 구하기.
		var hire_date = document.getElementById('hire_date').childNodes[0].nodeValue.substring(0,10);
		var start = new Date(hire_date.substring(0,4),hire_date.substring(5,7)-1,hire_date.substring(8,10));
		
		var d = new Date();
		//var end = d.getFullYear()+''+((d.getMonth()+1)+'')+d.getDate()+'';
		var result = 0;
		
		if(hire_date.substring(0,4)==(d.getFullYear()+'')){
			result = (d.getMonth()+1+'')*1-hire_date.substring(5,7)*1;
		}else{
			result = Math.round((d.getTime()-start.getTime())/(1000*60*60*24*365/12));
		}
		
		var b_year = Math.floor(result/12);
		var b_month = Math.floor(result%12)-1;
		if(b_month==0||b_month==-1){
			
			if(b_year==0){
				document.getElementById('career_date').innerHTML='--';
			}else{
				document.getElementById('career_date').innerHTML=b_year+'년';
			}
		}else{
			
			if(b_year==0){
				document.getElementById('career_date').innerHTML=b_month+'개월';
			}else{
				document.getElementById('career_date').innerHTML=b_year+'년'+b_month+'개월';
			}
		}
		
		// 2. 경력사항
		var c_length = $('#c_table tr').length;
		
		for (var i = 1; i < c_length; i++) {
			var str = $('#c_table tr:eq('+i+') td:eq(0)').text();
			var c_start = str.substring(0,10);
			var c_end = str.substring(11,21);

			var csdate = new Date(c_start.substring(0,4),c_start.substring(5,7)-1,c_start.substring(8,10));
			var cedate = new Date(c_end.substring(0,4),c_end.substring(5,7)-1,c_end.substring(8,10));
			
			if(c_start.substring(0,4)==c_end.substring(0,4)){
				result = c_end.substring(5,7)*1-c_start.substring(5,7)*1;
			}else{
				result = Math.round((cedate.getTime()-csdate.getTime())/(1000*60*60*24*365/12));
			}
			
			var c_year = Math.floor(result/12);
			var c_month = Math.floor(result%12);
			if(c_month==0){
				if(c_year==0){
					$('#c_table tr:eq('+i+') td:eq(1)').html('--');
				}else{
					$('#c_table tr:eq('+i+') td:eq(1)').html(c_year+'년');
				}
			}else{
				
				if(c_year==0){
					$('#c_table tr:eq('+i+') td:eq(1)').html(c_month+'개월');
				}else{
					$('#c_table tr:eq('+i+') td:eq(1)').html(c_year+'년'+c_month+'개월');
				}
			}
		}
		
		
		$(".pwdDelBtn").click(function() {
			
			$(function() {
				$.ajax({
					type : "get",
					url : "/Employees/EmpController",
					data : "type=empDel&e_id="+e_id,
					dataType : "text",
					success : function(data) {
						if(data.substring(0,4) != "fail"){
							alert("퇴사처리 완료");
							location.href=data;
							//$("#listgo").attr("method","get").attr("action",data).submit();
						}else{
							alert("퇴사처리 실패");
						}
					},
					error : function(request, status, error) {
						alert("request: " + request + " status: " + status
								+ " error: " + error);
					}
				});
			});
		});

	});
	function empUp_go(f) {
		f.action="/Employees/EmpController?type=empUp_go&e_id="+e_id;
		f.submit();
	}
</script>
</head>
<body>
<form id="listgo"></form>
<form id="frm" method="post">
	<div>
		<c:choose>
			<c:when test="${empInfo.e_dept eq '관리팀'}">
				<div>	
					<%-- <%@ include file="/Employees/WebContent/[employees]/AdminMenu.jsp" %> --%>
					<jsp:include page="../[menu]/AdminMenu.jsp" />
				</div>
			</c:when>
			
			<c:otherwise>
				<div>
					<%-- <%@ include file="Menu.jsp" %> --%>
					<jsp:include page="../[menu]/Menu.jsp" />
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="sectionWrap">
		<h1>회원 정보 상세보기</h1>
		<hr/>
		<div class="f_wrap">
			
			<div id="btnWrap">
				<c:choose>
					<c:when test="${firstInfo.e_id eq empInfo.e_id and empInfo.e_dept eq '관리팀'}">
						<a onclick="pwdUpdate_go()" id="pwd_go">비밀번호 변경</a>
						<input type="button" value="수정" class="pwdUpBtn" onclick="empUp_go(this.form)"/>
					</c:when>
					<c:when test="${empInfo.e_dept eq '관리팀'}">
						<input type="button" value="수정" class="pwdUpBtn" onclick="empUp_go(this.form)"/>
						<input type="button" value="삭제" class="pwdDelBtn"/>
					</c:when>
					<c:when test="${firstInfo.e_id eq empInfo.e_id}">
						<a onclick="pwdUpdate_go()"  id="pwd_go">비밀번호 변경</a>
						<input type="button" value="수정" class="pwdUpBtn" onclick="empUp_go(this.form)"/>
					</c:when>
				</c:choose>
			</div>
			<h2>1. 기본사항</h2>
				<table>
					<tbody>	
						<c:if test="${!empty firstInfo}">					
							<tr><td rowspan="10"><img width="248px" height="319px" src="${firstInfo.e_img }"/></td></tr>
							<tr><th>성함</th><td>${firstInfo.e_name}</td><th>경력기간</th><td id="career_date"></td></tr>
							<tr class="tr_id"><th>사번</th><td class="e_id">${firstInfo.e_id }</td><th>직급</th><td>${firstInfo.e_rank }</td></tr>
							<tr><th>소속회사</th><td>아이캔매니지먼트(주)</td><th>부서</th><td>${firstInfo.e_dept }</td></tr>
							<tr><th>생년월일</th><td>${firstInfo.e_jumin.substring(0,6) }</td><th>입사날짜</th><td id="hire_date">${firstInfo.e_hire_date.substring(0,10)}</td></tr>
							<tr><th>전화번호</th><td>${firstInfo.e_tel }</td><th>이메일</th><td>${firstInfo.e_email }</td></tr>
							<tr><th rowspan="${schoolList.size()+1 }">학력</th><th>기간</th><th>학교명</th><th>전공</th></tr>
							<c:if test="${!empty schoolList }">
								<c:forEach items="${schoolList}" var="k">
									<tr>
										<td>${k.s_start.substring(0,10)}~${k.s_end.substring(0,10)}</td>
										<td>${k.s_name }</td>
										<td>${k.s_major}</td>
									</tr>
								</c:forEach>
							</c:if>
							<tr><th>주소</th><td colspan="5"> ${firstInfo.e_addr }</td></tr>	
						</c:if>							
					</tbody>
				</table>
			
			<h2>2. 경력사항</h2>
			<table id="c_table">
				<thead>
					<tr>
						<th>근무기간</th>
						<th>근무년수(년,개월)</th>
						<th>회사명</th>
						<th>직위</th>
						<th>담당업무</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty careerList }">
							<c:forEach items="${careerList }" var="k">
								<tr>
									<td class="c_date">${k.c_start.substring(0,10)}~${k.c_end.substring(0,10) }</td>
									<td class="c_career_date"></td>
									<td>${k.c_name}</td>
									<td>${k.c_rank}</td>
									<td>${k.c_task}</td>
								</tr>
							</c:forEach>
							
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">경력사항이 존재하지 않습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<h2>3. 자격증</h2>
			<table>
				<thead>
					<tr>
						<th>자격증</th>
						<th>취득일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty licenseList }">
							<c:forEach items="${licenseList }" var="k">
								<tr>
									<td>${k.l_name}</td>
									<td>${k.l_date.substring(0,10)}</td>
								</tr>
							</c:forEach>
							
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">자격증 내역이 존재하지 않습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			
		
			<h2>4. 프로젝트 수행 내역</h2>
			<table>
				<thead>
					<tr>
						<th>프로젝트명</th>
						<th>프로젝트 내용</th>
						<th>참여기간</th>
						<th>담당업무/역할</th>
						<th>발주처</th>
						<th>비고</th>					
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty proList }">
							<c:forEach items="${proList }" var="k">
								<tr>
									<td>${k.p_name}</td>
									<td>${k.p_content}</td>
									<td>${k.m_start.substring(0,10)}~${k.m_end.substring(0,10)}</td>
									<td>${k.m_task}</td>
									<td>${k.p_order}</td>
									<td>${k.m_etc}</td>
									<td style="display: none;"><input type="hidden" name="p_code" value="${k.p_code}"></td>
								</tr>
							</c:forEach>
							
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">프로젝트 내역이 존재하지 않습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>
</html>