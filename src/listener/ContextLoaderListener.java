package listener;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

import dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	BasicDataSource ds;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			
			memberDao.createTable();
			  
			sc.setAttribute("memberDao", memberDao);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			if(ds != null) {
				ds.close();
			}
		}
		catch(SQLException e) {}
	}

}
