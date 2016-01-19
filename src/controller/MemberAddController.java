package controller;

import java.util.Map;

import binder.DataBinding;
import dao.MemberDao;
import vo.Member;

public class MemberAddController implements Controller, DataBinding {
	MemberDao memberDao;
	
	
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if(member.getEmail() == null) {
			return "/member/MemberForm.jsp";
		}
		else {
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"member", Member.class
		};
	}
	
}
