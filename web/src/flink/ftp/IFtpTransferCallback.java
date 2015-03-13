package flink.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTP;
/**
 *  <p>包装Apache下FTPClient作为模板接口</li>
  * @Project: MyCard
  * @File: FtpTransferCallback.java
  * @See:
  * @description：使用FTP回调
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface IFtpTransferCallback {
	/**
	 * <p>FTP文件目录路径分隔符</p>
	 */
	String PATH_SEPRATOR = "/";
	
	/**
	 * <p>文件传输类型</p>
	 */
	Integer[] CHECKED_FILETYPES = new Integer[] {FTP.ASCII_FILE_TYPE,FTP.EBCDIC_FILE_TYPE,FTP.BINARY_FILE_TYPE,FTP.LOCAL_FILE_TYPE};
	/**
	 * 
	  * <p>FTPClient的包装回调接口</p>
	  * @param ftpClient FTP接口类
	  * @return
	  * @throws CommunicationException  
	  * @version: 2010-12-23 上午09:45:47
	  * @See:
	 */
	boolean doTransfer(FTPClient ftpClient) throws CommunicationException;
}
