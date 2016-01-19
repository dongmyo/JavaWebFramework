package context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.DataSource;

public class ApplicationContext {
	private Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	
	public ApplicationContext(String propertiesPath) throws Exception {
	    Properties props = new Properties();
	    props.load(new FileReader(propertiesPath));
	    
	    prepareObjects(props);
	    injectDependency();
	}

	
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	
	private void prepareObjects(Properties props) throws Exception {
	    String key = null;
	    String value = null;
	    
	    for(Object item : props.keySet()) {
	    	key = (String) item;
	    	value = props.getProperty(key);

	    	if(key.startsWith("jdbc.")) {
	    		objTable.put(key,  value);
	    	}
	    	else {
	    		objTable.put(key, Class.forName(value).newInstance());
	    	}
	    }
	}

	private void injectDependency() throws Exception {
	    for(String key : objTable.keySet()) {
	    	callSetter(objTable.get(key));
	    }
	}

	private void callSetter(Object obj) throws Exception {
	    Object dependency = null;
	    
	    for(Method m : obj.getClass().getMethods()) {
	    	if(m.getName().startsWith("set")) {
	    		if(obj instanceof DataSource) {
	    			if(m.getName().equals("setDriverClassName")) {
	    				dependency = objTable.get("jdbc.driverClassName");
	    			}
	    			else if(m.getName().equals("setUrl")) {
	    				dependency = objTable.get("jdbc.url");
	    			}
	    			else if(m.getName().equals("setUsername")) {
	    				dependency = objTable.get("jdbc.username");
	    			}
	    			else if(m.getName().equals("setPassword")) {
	    				dependency = objTable.get("jdbc.password");
	    			}
	    			else {
	    				dependency = null;
	    			}
	    		}
	    		else {
	    			dependency = findObjectByType(m.getParameterTypes()[0]);
	    		}
	    		
	    		if (dependency != null) {
	    			m.invoke(obj, dependency);
	    		}
	    	}
	    }
	}

	private Object findObjectByType(Class<?> type) {
		for (Object obj : objTable.values()) {
			if (type.isInstance(obj)) {
				return obj;
			}
		}
		
		return null;
	}
	
}
