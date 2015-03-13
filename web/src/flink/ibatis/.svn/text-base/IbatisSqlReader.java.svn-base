package flink.ibatis;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author aps-mhc
 *
 */
public class IbatisSqlReader {
	private String sql;
	private Node node;
	
	public IbatisSqlReader(String sql) throws IOException {
		this.sql = sql;
		init();
	}
	
	public String getId() {
		return getAttribute("id");
	}
	
	public String getParameterClass() {
		return getAttribute("parameterClass");
	}
	
	public String getParameterMap() {
		return getAttribute("parameterMap");
	}
	
	public String getResultClass() {
		return getAttribute("resultClass");
	}
	
	public String getResultMap() {
		return getAttribute("resultMap");
	}
	
	public String getContent() {
		return sql.substring(sql.indexOf(">") + 1, sql.indexOf("</select>"));
	}

	private String getAttribute(String attr) {
		Node attribute = node.getAttributes().getNamedItem(attr);
		
		return attribute == null ? null : attribute.getNodeValue();
	}
	
	private void init() throws IOException {
		try {
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = bf.newDocumentBuilder();
			Document doc =  db.parse(new ByteArrayInputStream(sql.getBytes("UTF-8")));
			node = doc.getFirstChild();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new IOException(e.getMessage());
		}
	}
}
