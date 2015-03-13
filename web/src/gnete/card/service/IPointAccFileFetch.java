package gnete.card.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @Project: Card
 * @File: IPointAccFileFetch.java
 * @See:
 * @description： 
 *        <li>根据回调中的FTPCLIENT以及传入的日期来获得该日期下所有交易的文件流</li>
 *        <li>注意返回的是一个多值MAP</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IPointAccFileFetch {

	/**
	 * 
	  * @description： key: trade, value:List<Object[]> ——Object[] {fileName,InputStream}
	  * @param ftpClient
	  * @param date
	  * @return
	  * @throws IOException
	  * @throws CommunicationException
	  * @throws RuntimeBizException  
	  * @version: 2011-4-14 下午01:35:57
	  * @See:
	 */
	Map getPointAccFileStream(FTPClient ftpClient, String date) throws IOException,RuntimeBizException;

}
