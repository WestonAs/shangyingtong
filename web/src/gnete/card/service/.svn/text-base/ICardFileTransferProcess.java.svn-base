package gnete.card.service;

import flink.ftp.CommunicationException;
import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import gnete.etc.BizException;
import java.security.cert.Certificate;

/**
 * @Project: Card
 * @File: ICardFileTransferProcess.java
 * @See:
 * @description：当前处理跟文件
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface ICardFileTransferProcess {
	
	/**
	 * 获取远程文件名
	 * @Date 2013-4-20下午4:26:48
	 * @return String
	 */
	String getRemoteFilePath(String fileName) throws CommunicationException, BizException;
	
	/**
	 * 
	 * <p>触发文件上传</p>
	 * @param uploadFile  上传文件
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2010-12-9 下午04:25:43
	 * @See:
	 */
	boolean uploadTransferFile(File uploadFile) throws CommunicationException, BizException;

	/**
	 * 
	 * <p>触发文件上传</p>
	 * @param uploadFile 上传文件
	 * @param fileName   实际上传文件名
	 * @return
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2011-4-13 上午12:28:22
	 * @See:
	 */
	boolean uploadTransferFile(File uploadFile, String fileName) throws CommunicationException, BizException;

	/**
	 * 
	 * <p>触发文件下载(用于下载链接)</p>
	 * @param fileName 文件名
	 * @param response
	 * @param contentType
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2010-12-9 下午04:26:00
	 * @See:
	 */
	boolean downloadTransferFile(String fileName, HttpServletResponse response, String contentType)
			throws CommunicationException, BizException;

	/**
	 * 
	 * <p>从远端处获得文件输入流</p>
	 * @param fileName   文件名
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2010-12-9 下午05:03:56
	 * @See:
	 */
	InputStream getTransferFileStream(String fileName) throws CommunicationException, BizException;

	/**
	 * 
	 * <p>从远程文件中读取Certificate(用于缓存或者用于签名验证使用)</p>
	 * @param fileName 远程文件名
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2010-12-9 下午04:28:33
	 * @See:
	 */
	Certificate getTransferCertificate(String fileName) throws CommunicationException, BizException;

	/**
	 * 
	 * <p>从远程文件中读取Certificate(用于缓存或者用于签名验证使用)</p>
	 * @param remoteFilePath 远程文件目录
	 * @param fileName 远程文件名
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2011-1-10 上午11:21:44
	 * @See:
	 */
	Certificate getTransferCertificate(String remoteFilePath, String fileName) throws CommunicationException, BizException;
	
	/**
	 * 
	  * <p>从远程目录中删除不需要的文件</p>
	  * @param removeFileNames
	  * @return
	  * @throws CommunicationException  
	  * @throws BizException
	  * @version: 2011-4-26 上午09:45:00
	  * @See:
	 */
	boolean removeRemoteFile(String[] removeFileNames) throws CommunicationException,BizException;

}
