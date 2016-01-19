package controller;

import java.util.Map;

import dao.MemberDao;

public class MemberDeleteController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		   
		Integer no = (Integer) model.get("no");
		memberDao.delete(no);

		return "redirect:list.do";
	}

}
