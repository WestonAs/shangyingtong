package gnete;

import flink.util.SpringContext;
import junit.framework.TestCase;

public class BaseTest extends TestCase {

	protected void setUp() throws Exception {
		String[] configFiles = new String[] {
				"classpath:applicationContext.xml"
		};
		
        SpringContext.getInstance().initContext(configFiles);
	}
	
	protected Object getBean(String serviceName) {
		return SpringContext.getService(serviceName);
	}

	protected void print(Object o) {
		System.out.println(o);
	}
}
