package controller;

import java.util.Map;

import binder.DataBinding;
import dao.MemberDao;
import vo.Member;

public class MemberUpdateController implements Controller, DataBinding {
	MemberDao memberDao;
	
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if(member.getEmail() == null) {
			Integer no = (Integer) model.get("no");
			
			Member detailInfo = memberDao.selectOne(no);

			model.put("member", detailInfo);

			return "/member/MemberUpdateForm.jsp";
		}
		else {
			
			memberDao.update(member);
			
			return "redirect:list.do";
		}
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"member", Member.class
		};
	}
	
}
