package gnete.card.service;

import gnete.card.entity.CardInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @File: TxtService.java
 * 
 * @description: 生产txt文件
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-11-15
 */
public interface TxtService {

	/**
	 * 生成累积消费充值余额报表txt文件
	 * @param list 数据
	 * @param templatePath 文件保存目录
	 * @param head 文件第一行的题头
	 * @return
	 * @throws Exception
	 */
	public boolean geneteConsmChargeBalReportTxt(List<CardInfo> list, File templatePath, String[] head) throws Exception;
	
	/**
	 * 生成txt文件的标题行头
	 * @param head
	 * @return
	 * @throws Exception
	 */
	public StringBuilder getHead(String[] head) throws Exception;
	
	/**
	 * 
	 * <p>在web服务器目录中创建传入文件名称的文件</p>
	 * @param reportDir        文件目录
	 * @param contentHead    文件名称
	 * @return
	 * @throws IOException
	 * @version: 2011-11-16
	 * @See:
	 */
	public File getWebDirFile(File reportDir, String contentHead) throws IOException;
	
}
