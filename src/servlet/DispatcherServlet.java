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

import binder.DataBinding;
import binder.ServletRequestDataBinder;
import context.ApplicationContext;
import controller.Controller;
import listener.ContextLoaderListener;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String servletPath = request.getServletPath();
		
		try {
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			// 페이지 컨트롤러에게 전달할 Map 객체 
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
		    
			Controller pageController = (Controller) ctx.getBean(servletPath);
			
			if(pageController instanceof DataBinding) {
				prepareRequestData(request, model, (DataBinding) pageController);
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

	private void prepareRequestData(HttpServletRequest request, Map<String, Object> model, DataBinding dataBinding) throws Exception {
		Object[] dataBinders = dataBinding.getDataBinders();
		
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
	    for(int i = 0; i < dataBinders.length; i += 2) {
	    	dataName = (String) dataBinders[i];
	    	dataType = (Class<?>) dataBinders[i + 1];
	    	
	    	dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
	    	model.put(dataName, dataObj);
	    } 
	}

}
