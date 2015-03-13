package flink.ibatis;

import java.util.Properties;

/**
 * 查询语句.
 * 
 * @author aps-mhc
 *
 */
public class SelectWriter {
	private Properties properties = new Properties();
	private String content;
	
	public void setId(String id) {
		setProperties("id", id);
	}
	
	public void setParameterClass(Class clazz) {
		setParameterClass(clazz.getName());
	}
	
	private void setProperties(String key, String value) {
		if (key == null || value == null) {
			return;
		}
		
		properties.put(key, value);
	}
	
	public void setParameterClass(String parameterClass) {
		setProperties("parameterClass", parameterClass);
	}
	
	public void setParameterMap(String map) {
		setProperties("parameterMap", map);
	}
	
	public void setResultClass(Class clazz) {
		setResultClass(clazz.getName());
	}
	
	public void setResultClass(String resultClass) {
		setProperties("resultClass", resultClass);
	}
	
	public void setResultMap(String map) {
		setProperties("resultMap", map);
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getProperty(String key) {
		return (String) properties.getProperty(key);
	}
	
	public String toString() {
		StringBuffer sql = new StringBuffer();
		sql.append("\t<select ");
		
		String s = null;
		s = getProperty("id");
		if (s != null) {
			sql.append("id=\"" + s + "\" ");
		}
		
		s = getProperty("parameterClass");
		if (s != null) {
			sql.append("parameterClass=\"" + s + "\" ");
		}
		
		s = getProperty("parameterMap");
		if (s != null) {
			sql.append("parameterMap=\"" + s + "\" ");
		}
		
		s = getProperty("resultClass");
		if (s != null) {
			sql.append("resultClass=\"" + s + "\" ");
		}
		
		s = getProperty("resultMap");
		if (s != null) {
			sql.append("resultMap=\"" + s + "\" ");
		}
		
		sql.append(">");
		sql.append(this.content);
		sql.append("</select>");
		
		return sql.toString();
	}
}
