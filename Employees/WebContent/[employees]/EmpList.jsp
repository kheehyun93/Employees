<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <jsp:useBean id="empInfo" class="com.hb.vo.EmployeesVO" scope="session"/>
<jsp:setProperty property="*" name="empInfo"/> --%>
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Employees/css/list.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../Employees/js/jquery-3.1.1.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('td').dblclick(function() {
			var id = $(this).closest("tr").find(".td_id").text();
			location.href="/Employees/EmpController?type=empOnelist&id="+id;
		});
		
		var rank = "${requestScope.e_rank }";
		var dept = "${requestScope.e_dept }";
		
		if(rank!=""){
			$("#rank").val(rank);
		}
		if(dept!=""){
			$("#deptCategory").val(dept);
		}
		
		$("#emp_search").click(function() {
			$("#searchForm").attr("action","/Employees/EmpController?type=empSearch").attr("method", "post").submit();

		});
		
		
		
	});
	
</script>
</head>
<body>
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
		<h1>사원 목록</h1>
		<hr/>
		<div id="searchFormWrap">
			<form id="searchForm">
				<label class="f_lable">직급</label>
				<select id="rank" name="rankCategory" class="select">
					<option value="" selected="selected">전체</option>
					<option value="사장">사장</option>
					<option value="전무">전무</option>
					<option value="상무">상무</option>
					<option value="부장">부장</option>
					<option value="차장">차장</option>
					<option value="과장">과장</option>
					<option value="대리">대리</option>
					<option value="사원">사원</option>
				</select>
				 &nbsp;&nbsp;&nbsp;
				<label class="f_lable">부서</label>
				<select id="deptCategory" name="deptCategory" class="select">
					<option value="" selected="selected">전체</option>
					<option value="개발 1팀">개발 1팀</option>
					<option value="개발 2팀">개발 2팀</option>
					<option value="빅데이타팀">빅데이타팀</option>
					<option value="관리팀">관리팀</option>
					<option value="해당없음">해당없음</option>
				</select>
				&nbsp;&nbsp;&nbsp; 
				<input type="text" name="e_name" class="inputText" value="${param.e_name }" placeholder="이름 입력"/> &nbsp;&nbsp;&nbsp; 
				<input type="text" name="e_tel" class="inputText" value="${param.e_tel }" placeholder="전화번호 입력"/> &nbsp;&nbsp;&nbsp; 
				<input type="button" value="검색" id="emp_search"/>
			</form>
		</div>
	
		<div class="t_wrap">
			<table style="width: 737px;">
				<thead>
					<tr>
						<th>사번</th>
						<th>이름</th>
						<th>생년월일</th>
						<th>전화번호</th>
						<th>직급</th>
						<th>부서</th>					
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty empList }">
							<c:forEach items="${empList}" var="k">
								<tr onmouseover="this.style.background='#E1E1E1'" onmouseout="this.style.background='white'">
									<td class = "td_id">${k.e_id}</td>
									<td>${k.e_name}</td>
									<td>${k.e_jumin.substring(0,6)}</td>
									<td>${k.e_tel}</td>
									<td>${k.e_rank}</td>
									<td>${k.e_dept}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:when test="${!empty empSearchList }">
							<c:forEach items="${empSearchList}" var="k">
								<tr onmouseover="this.style.background='#E1E1E1'" onmouseout="this.style.background='white'">
									<td class = "td_id">${k.e_id}</td>
									<td>${k.e_name}</td>
									<td>${k.e_jumin.substring(0,7)}</td>
									<td>${k.e_tel}</td>
									<td>${k.e_rank}</td>
									<td>${k.e_dept}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">사원 내역이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<c:choose>
				<c:when test="${!empty empList }">
				<tfoot class="t_foot">
					<tr style="text-align: center; font-size: 14px;">
						<td colspan="6">
							<ul class="paging" style="margin-left: 218px;">
								<c:choose>
									<c:when test="${pvo.beginPage<pvo.pagePerBlock}">
										<li class="disable">이전으로</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/EmpController?type=empList&cPage=${pvo.beginPage-pvo.pagePerBlock}">이전으로</a></li>
									</c:otherwise>
								</c:choose>
								
								<c:forEach var="k" begin="${pvo.beginPage}" end="${pvo.endPage}" step="1">
									<c:choose>
										<c:when test="${k==pvo.nowPage}">
											<li class="now">${k}</li>
										</c:when>
										<c:otherwise>
											<li><a href="/Employees/EmpController?type=empList&cPage=${k}">${k}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
								<c:choose>
									<c:when test="${pvo.endPage>=pvo.totalPage}">
										<li class="disable">다음으로</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/EmpController?type=empList&cPage=${pvo.beginPage+pvo.pagePerBlock}">다음으로</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</td>
					</tr>
				</tfoot>
				</c:when>
				</c:choose>
				<c:choose>
				<c:when test="${!empty empSearchList }">
				<tfoot class="t_foot">
					<tr style="text-align: center; font-size: 14px;">
						<td colspan="6">
							<ul class="paging" style="margin-left: 218px;">
								<c:choose>
									<c:when test="${pvo.beginPage<pvo.pagePerBlock}">
										<li class="disable">이전으로</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/EmpController?type=empSearch&cPage=${pvo.beginPage-pvo.pagePerBlock}&rankCategory=${requestScope.e_rank}&deptCategory=${requestScope.e_dept}&e_name=${param.e_name}&e_tel=${param.e_tel}">이전으로</a></li>
									</c:otherwise>
								</c:choose>
								
								<c:forEach var="k" begin="${pvo.beginPage}" end="${pvo.endPage}" step="1">
									<c:choose>
										<c:when test="${k==pvo.nowPage}">
											<li class="now">${k}</li>
										</c:when>
										<c:otherwise>
											<li><a href="/Employees/EmpController?type=empSearch&cPage=${k}&rankCategory=${requestScope.e_rank}&deptCategory=${requestScope.e_dept}&e_name=${param.e_name}&e_tel=${param.e_tel}">${k}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
								<c:choose>
									<c:when test="${pvo.endPage>=pvo.totalPage}">
										<li class="disable">다음으로</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/EmpController?type=empSearch&cPage=${pvo.beginPage+pvo.pagePerBlock}&rankCategory=${requestScope.e_rank}&deptCategory=${requestScope.e_dept}&e_name=${param.e_name}&e_tel=${param.e_tel}">다음으로</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</td>
					</tr>
				</tfoot>
				</c:when>
				</c:choose>
			</table>
		</div>
	</div>
</body>
</html>