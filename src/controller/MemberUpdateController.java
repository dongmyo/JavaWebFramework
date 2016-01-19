package controller;

import java.util.Map;

import dao.MemberDao;
import vo.Member;

public class MemberUpdateController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		if(model.get("member") == null) {
			Integer no = (Integer) model.get("no");
			
			Member member = memberDao.selectOne(no);

			model.put("member", member);

			return "/member/MemberUpdateForm.jsp";
		}
		else {
			Member member = (Member) model.get("member");
			memberDao.update(member);
			
			return "redirect:list.do";
		}
	}

}
