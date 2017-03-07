package com.hb.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.CareerDAO;
import com.hb.dao.EmployeesDAO;
import com.hb.dao.LicenseDAO;
import com.hb.dao.ProjectDAO;
import com.hb.dao.SchoolDAO;
import com.hb.vo.CareerVO;
import com.hb.vo.EmployeesVO;
import com.hb.vo.LicenseVO;
import com.hb.vo.ProjectJoinVO;
import com.hb.vo.SchoolVO;

public class EmpUpdategoCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 사원번호 받아옴.
		String id = request.getParameter("e_id");
		
		// 1. 기본정보 가져오기.
		EmployeesDAO e_dao = new EmployeesDAO();
		EmployeesVO firstInfo = new EmployeesVO();
		firstInfo = e_dao.getFirstInfo(id);
		request.setAttribute("firstInfo", firstInfo);
		
		// 1. 기본정보 - 학력사항 가져오기
		SchoolDAO s_dao = new SchoolDAO();
		List<SchoolVO> schoolList = new ArrayList<>();
		schoolList = s_dao.getSchoolInfo(id);
		
		request.setAttribute("schoolList", schoolList);
		
		// 2. 경력사항 가져오기.
		CareerDAO c_dao = new CareerDAO();
		List<CareerVO> careerList = new ArrayList<>();
		careerList = c_dao.getCareerInfo(id);
		request.setAttribute("careerList", careerList);
		
		// 3. 자격증 가져오기.
		LicenseDAO l_dao = new LicenseDAO();
		List<LicenseVO> licenseList = new ArrayList<>();
		licenseList = l_dao.getLicenseInfo(id);
		request.setAttribute("licenseList", licenseList);
		
		
		// 4. 프로젝트 수행경력 가져오기.
		ProjectDAO p_dao = new ProjectDAO();
		List<ProjectJoinVO> proList = new ArrayList<>();
		proList = p_dao.getProInfo(id);
		request.setAttribute("proList", proList);

		return "[employees]/EmpOnelistUpdate.jsp";
	}

}
