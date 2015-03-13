package flink.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;

/**
 * @File: Log4jServlet.java
 * 
 * @description: ServLet for Log4j
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-29
 */
public class Log4jServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		super.init();
		
		String file = getInitParameter("log4j");

		if (StringUtils.isEmpty(file)) {
			return;
		}
		
		Properties ps = new Properties();
		try {
			ps.load(getServletContext().getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PropertyConfigurator.configure(ps);
	}


}
