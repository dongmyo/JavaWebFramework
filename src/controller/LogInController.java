package controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import binder.DataBinding;
import dao.MemberDao;
import vo.Member;

public class LogInController implements Controller, DataBinding {
	MemberDao memberDao;
	
	
	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member loginInfo = (Member) model.get("loginInfo");
		
		if(loginInfo.getEmail() == null) {
			return "/auth/LogInForm.jsp";
		}
		else {
			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			if(member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				
				return "redirect:../member/list.do";
			}
			else {
				return "/auth/LogInFail.jsp";
			}
		}
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"loginInfo", Member.class
		};
	}
	
}
