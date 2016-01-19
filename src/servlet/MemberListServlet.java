package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

			request.setAttribute("members", memberDao.selectList());

			// 프론트 컨트롤러에서 공통 처리하므로 페이지 컨트롤러에서는 제거 처리
//			response.setContentType("text/html; charset=UTF-8");
//			
//			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
//			rd.include(request, response);

			// 뷰 페이지 정보를 프론트 컨트롤러에 전달하기 위해
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		}
		catch(Exception e) {
			// 프론트 컨트롤러에서 공통 처리하므로 페이지 컨트롤러에서는 제거 처리
			throw new ServletException(e);
//			e.printStackTrace();
//			request.setAttribute("error", e);
//
//			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
//			rd.forward(request, response);			
		}
	}

}
