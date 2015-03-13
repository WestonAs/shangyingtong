package flink.ibatis;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ibatis 分页sql 预处理.
 * 
 * @author aps-mhc
 *
 */
public abstract class PageSqlPredealer {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACE_LINE = LINE_SEPARATOR + LINE_SEPARATOR;
	private static final String PAGINATE_FLAG = "<!-- #paginate -->";
	private static final String PAGE_STUB_BEGIN = "\t<!-- #page stub begin -->";
	private static final String PAGE_STUB_END = "\t<!-- #page stub end -->";

	public abstract String getPageSql(String sql);

	/**
	 * 预处理分页sql.
	 * 
	 * @param configLocations
	 * @throws IOException
	 */
	public void prepare(Resource[] configLocations) throws IOException {
		for (int i = 0; i < configLocations.length; i++) {
			Resource resource = configLocations[i];
			prepareSqlMaps(resource);
		}
	}

	/**
	 * @param sqlMap
	 * @throws IOException 
	 */
	private void preparePageSql(Resource sqlMap) throws IOException {
		InputStream input = sqlMap.getInputStream();
		StringBuffer prepareSql = new StringBuffer(100);

		try {
			List lines = IOUtils.readLines(input, "UTF-8");
			
			for (int i = 0; i < lines.size(); i++) {
				String line = (String) lines.get(i);
				
				if (StringUtils.contains(line, PAGINATE_FLAG)) {
					prepareSql.append(getPageSql(lines.subList(i + 1, lines.size() - 1)));
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			
			throw e;
		}
		finally {
			IOUtils.closeQuietly(input);
		}
		
		if (prepareSql.length() > 0) {
			prepareSql.insert(0, SPACE_LINE + PAGE_STUB_BEGIN);
			prepareSql.append(LINE_SEPARATOR);
			prepareSql.append(PAGE_STUB_END);
			write(sqlMap, prepareSql);
		}
	}

	private void write(Resource sqlMap, StringBuffer prepareSql) throws IOException {
		// read
		InputStream input = null;
		List lines = null;
		
		try {
			input = sqlMap.getInputStream();
			lines = IOUtils.readLines(input, "UTF-8");
			removeOldStub(lines);
			appendNewStub(lines, prepareSql);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			IOUtils.closeQuietly(input);
		}
		
		// write
		OutputStream output = null;
		
		try {
			output = new FileOutputStream(sqlMap.getFile());
			
			IOUtils.writeLines(lines, null, output, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 添加新page stub.
	 * 
	 * @param lines
	 * @param prepareSql
	 */
	private void appendNewStub(List lines, StringBuffer prepareSql) {
		final String end = "</sqlMap>";

		for (int i = lines.size() - 1; i > 0; i--) {
			boolean isEnd = StringUtils.contains((String)lines.get(i), end);
			
			if (!isEnd) {
				lines.remove(i);
			}
			
			if (isEnd) {
				break;
			}
		}
		
		lines.add(lines.size() - 1, prepareSql.toString());
	}

	/**
	 * 删除旧page stub.
	 * 
	 * @param lines
	 */
	private void removeOldStub(List lines) {
		boolean findStubBegin = false;
		
		for (Iterator i = lines.iterator(); i.hasNext();) {
			String line = (String) i.next();
			
			if (StringUtils.contains(line, PAGE_STUB_END)) {
				i.remove();
				
				break;
			}

			if (findStubBegin) {
				i.remove();
				
				continue;
			}
			
			if (StringUtils.contains(line, PAGE_STUB_BEGIN)) {
				i.remove();
				
				findStubBegin = true;
			}
		}
	}

	/**
	 * 获取分页sql.
	 * 
	 * @param lines
	 * @return
	 */
	private StringBuffer getPageSql(List lines) throws IOException {
		StringBuffer selectSql = getSelectSql(lines);
		IbatisSqlReader reader = new IbatisSqlReader(selectSql.toString());
		
		StringBuffer countSql = getCountSql(reader);
		StringBuffer pageSql = getPageSql(reader);
		
		StringBuffer result = new StringBuffer(100);
		result.append(LINE_SEPARATOR);
		result.append(countSql);
		result.append(LINE_SEPARATOR);
		result.append(pageSql);
		
		return result;
	}

	/**
	 * 获取分页查询sql.
	 * 
	 * @param selectSql
	 * @return
	 */
	private StringBuffer getPageSql(IbatisSqlReader reader) {
		String id = reader.getId();
		String parameterClass = reader.getParameterClass();
		String parameterMap = reader.getParameterMap();
		String resultClass = reader.getResultClass();
		String resultMap = reader.getResultMap();
		String content = getPageSql(reader.getContent());
		
		SelectWriter writer = new SelectWriter();
		writer.setId(id + "_page");
		writer.setParameterClass(parameterClass);
		writer.setParameterMap(parameterMap);
		writer.setResultClass(resultClass);
		writer.setResultMap(resultMap);
		writer.setContent(content);
		
		return new StringBuffer(writer.toString());
	}

	/**
	 * 获取统计笔数sql.
	 * 
	 * @param selectSql
	 * @return
	 * @throws IOException 
	 */
	private StringBuffer getCountSql(IbatisSqlReader reader) throws IOException {
		String id = reader.getId();
		String parameterClass = reader.getParameterClass();
		String parameterMap = reader.getParameterMap();
		String content = reader.getContent();
		content = "select count(*) from ( " + content + " ) t";
		
		SelectWriter writer = new SelectWriter();
		writer.setId(id + "_count");
		writer.setParameterClass(parameterClass);
		writer.setParameterMap(parameterMap);
		writer.setResultClass(Long.class);
		writer.setContent(content);
		
		return new StringBuffer(writer.toString());
	}

	/**
	 * 获取原有的sql.
	 * 
	 * @param lines
	 * @return
	 */
	private StringBuffer getSelectSql(List lines) {
		final String start = "<select";
		final String end = "</select>";
		
		if (!StringUtils.contains((String) lines.get(0), start)) {
			throw new IllegalArgumentException("sql语法错误!");
		}
		
		StringBuffer sql = new StringBuffer();
		
		for (int i = 0; i < lines.size(); i++) {
			String line = (String) lines.get(i);
			sql.append(line);
			
			if (StringUtils.contains(line, end)) {
				return sql;
			}
		}
		
		return sql;
	}

	/**
	 * 获取所有sqlMap文件(Resource).
	 * 
	 * @param configLocations
	 * @return
	 * @throws Exception 
	 */
	private void prepareSqlMaps(Resource resource) throws IOException {
		try {
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = bf.newDocumentBuilder();
			Document doc =  db.parse(resource.getInputStream());
			NodeList nodes = doc.getElementsByTagName("sqlMap");
			int length = nodes.getLength();
			
			for (int i = 0; i < length; i++) {
				Node node = nodes.item(i);
				String url = node.getAttributes().getNamedItem("resource").getNodeValue();
				preparePageSql(new ClassPathResource(url));
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new IOException(e.getMessage());
		}
	}
	
	
}
