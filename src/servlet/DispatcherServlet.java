package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.*;
import vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String servletPath = request.getServletPath();
		
		try {
			ServletContext sc = this.getServletContext();
			
			// 페이지 컨트롤러에게 전달할 Map 객체 
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
		    
			Controller pageController = (Controller) sc.getAttribute(servletPath);
			
			/* 분기 처리 */
			// 회원 등록
			if ("/member/add.do".equals(servletPath)) {
				if(request.getParameter("email") != null) {
					Member member = new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password"))
							.setName(request.getParameter("name"));
					
					model.put("member", member);
				}
			}
			// 회원 수정
			else if ("/member/update.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					Member member = new Member()
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setEmail(request.getParameter("email"))
							.setName(request.getParameter("name"));
					
					model.put("member", member);
				}
				else {
					model.put("no", new Integer(request.getParameter("no")));
				}
			}
			// 회원 삭제
			else if ("/member/delete.do".equals(servletPath)) {
				model.put("no", new Integer(request.getParameter("no")));
			}
			// 로그인
			else if ("/auth/login.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					Member loginInfo = new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password"));
					
					model.put("loginInfo", loginInfo);
				}				
			}
			
			/* 페이지 컨트롤러를 실행 */
			String viewUrl = pageController.execute(model);
		      
			// Map 객체에 저장된 값을 ServletRequest에 복사한다
			for(String key : model.keySet()) {
			  request.setAttribute(key, model.get(key));
			}
			
			/* 뷰 페이지로 위임 */
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}
			else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
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
