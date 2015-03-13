package flink.export;

import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>缺省视图结果输出定义接口</p>
 * @Project: Card
 * @File: IViewBuilder.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-5-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IViewBuilder {
	String DEFAULT_FILE_SUFFIX = ".txt";
	
	String DEFAULT_ZIP_SUFFIX = ".zip";

	String DEFAULT_CONTENT_SPLIT = "|";

	String DEFAULT_LINE_SPLIT = "\n";

	String DEFAULT_EXPORT_ENCODING = "utf-8";

	int DEFAULT_BUFFER = 4096;

	/**
	 * 
	 * <p>将传入的List集合(查询返回结果)进行文件导出(下载)处理</p>
	 * @param list      查询返回结果集
	 * @param request   
	 * @param response
	 * @return
	 * @throws IOException
	 * @version: 2011-5-19 下午04:04:18
	 * @See:
	 */
	void viewExport(List list, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
