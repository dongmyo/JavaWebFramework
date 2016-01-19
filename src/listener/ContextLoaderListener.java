package listener;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.BasicDataSource;

import context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	BasicDataSource ds;
	
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}	
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
		      
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));

			applicationContext = new ApplicationContext(propertiesPath);			
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
