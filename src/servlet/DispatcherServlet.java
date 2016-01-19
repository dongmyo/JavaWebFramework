package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String servletPath = request.getServletPath();
		
		try {
			String pageControllerPath = null;

			/* 분기 처리 */
			// 회원 목록      
			if ("/member/list.do".equals(servletPath)) {
				pageControllerPath = "/member/list";
			}
			// 회원 등록
			else if ("/member/add.do".equals(servletPath)) {
				// TODO
			}
			// 회원 수정
			else if ("/member/update.do".equals(servletPath)) {
				// TODO
			}
			// 회원 삭제
			else if ("/member/delete.do".equals(servletPath)) {
				// TODO
			}
			// 로그인
			else if ("/auth/login.do".equals(servletPath)) {
				// TODO
			}
			// 로그아웃
			else if ("/auth/logout.do".equals(servletPath)) {
				// TODO
			}
			 
			/* 페이지 컨트롤러로 위임 */
			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);

			/* 뷰 페이지로 위임 */
			String viewUrl = (String) request.getAttribute("viewUrl");
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}
			else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}			
		}
		catch (Exception e) {
			/* 에러 처리 */
			e.printStackTrace();
			request.setAttribute("error", e);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}		
	}

}
