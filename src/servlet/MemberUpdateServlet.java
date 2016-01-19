package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

			Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));

			request.setAttribute("member", member);

			request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
//			rd.forward(request, response);
		}
		catch(Exception e) {
			throw new ServletException(e);
//			e.printStackTrace();
//			request.setAttribute("error", e);
//
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);			
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");  

			Member member = (Member) request.getAttribute("member");
			memberDao.update(member);
//			memberDao.update(
//					new Member()
//					.setNo(Integer.parseInt(request.getParameter("no")))
//					.setName(request.getParameter("name"))
//					.setEmail(request.getParameter("email"))
//			);

			request.setAttribute("viewUrl", "redirect:list.do");
//			response.sendRedirect("list.do");			
		}
		catch(Exception e) {
			throw new ServletException(e);
//			e.printStackTrace();
//			request.setAttribute("error", e);
//
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);			
		}
	}

}
