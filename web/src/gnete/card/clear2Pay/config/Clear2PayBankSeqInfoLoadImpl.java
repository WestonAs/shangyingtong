package gnete.card.clear2Pay.config;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.clear2Pay.IClear2PayBankSeqInfoFetch;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
/**
  * <p>从FTP服务目录中提取电子联行号信息</p>
  * <ul>
  * <li>1. 从远端文件目录中提取电子联行号信息</li>
  * <li>2. 注意下载的文件为压缩文件(需进行本地解压缩后再进行提炼)</li>
  * <li>3. 上述信息用于提取网银通银行id信息</li>
  * </ul>
  * @Project: Card
  * @File: Clear2PayBankSeqInfoLoadImpl.java
  * @See:  house_bank.txt
  * @description：

  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-17
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
@Service("clear2PayBankSeqInfoLoad")
public class Clear2PayBankSeqInfoLoadImpl implements IClear2PayBankSeqInfoLoad{
	 @Autowired
	 @Qualifier("clear2PayBankSeqInfoFetch")
	 private IClear2PayBankSeqInfoFetch clear2PayBankSeqInfoFetch;
	
     public Map<String,String> loadClear2PayBankSeqInfoMap () throws BizException {
    	
    	 File localOutputDir =  FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));
    	 
    	 try {
    		 InputStream clear2PayBankSeqFileStream  = getClear2PayBankSeqFileStream();
    		 
    		 if(clear2PayBankSeqFileStream == null) {
    			 return Collections.<String,String>emptyMap();
    		 }
    		 
    		 File localClear2PayBankSeqFile = getLocalClear2PayBankFile(clear2PayBankSeqFileStream,localOutputDir);
    		 
    		 return this.clear2PayBankSeqInfoFetch.getClear2PayBankSeqInfoMap(localClear2PayBankSeqFile);
    		 
    	 }catch(CommunicationException ex) {
    		 throw new BizException("提取电子联行号文件出现异常,原因[" + ex.getMessage()+"]");
    	 } finally {
    		 FileHelper.deleteFile(localOutputDir);
    	 }
     }
     
     /**
      * 
       * <p>从流中提取要进行解析的文件</p>
       * @param clear2PayBankSeqFileStream
       * @return
       * @throws BizException  
       * @version: 2011-6-17 上午10:33:57
       * @See:
      */
     private File getLocalClear2PayBankFile(InputStream clear2PayBankSeqFileStream, File localOutputDir) throws BizException {
    	 try {
    		 Collection<File> extractFiles = FileHelper.getUnZipFiles(clear2PayBankSeqFileStream, localOutputDir, FILE_SUFFIX);
    		 
    		 if(CommonHelper.checkIsEmpty(extractFiles) || extractFiles.size() > FILE_SIZE) {
    			 throw new BizException("电子联行号文件不能为空且个数只能唯一!");
    		 }
    		 
    		 return extractFiles.toArray(new File[extractFiles.size()])[0];
    	 }catch(IOException ex) {
    		 throw new BizException("提取电子联行号文件出现异常,原因[" + ex.getMessage()+"]");
    	 } 
    	
     }
     
     /**
      * 
       * <p>获得电子联行号文件输入流</p>
       * @param fileName
       * @return
       * @throws CommunicationException
       * @throws BizException  
       * @version: 2011-6-17 上午10:12:45
       * @See:
      */
     public InputStream getClear2PayBankSeqFileStream() throws CommunicationException, BizException {
 		String[] remoteParams = getRemoteParams();

 		// 构造模板处理类
 		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[2], remoteParams[3], remoteParams[4]);

 		// 构造下载回调处理类
 		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(remoteParams[0], remoteParams[1]);

 		// 处理下载
 		boolean result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);

 		if (result) {
 			return downloadCallBack.getRemoteReferInputStream();
 		}

 		return null;
 	}
     
     /**
 	 * 
 	 * <p>构造FTP查询参数</p>
 	 * @return
 	 * @throws BizException
 	 * @version: 2010-12-9 下午12:45:15
 	 * @See:
 	 */
 	private String[] getRemoteParams() throws BizException {
 		try {
 			SysparamCache paraMgr = SysparamCache.getInstance();

			return new String[] { paraMgr.getClear2PayBankSeqFilePath(),
					paraMgr.getClear2PayBankSeqFileName(), paraMgr.getFtpServerIP(),
					paraMgr.getFtpServerUser(), paraMgr.getFtpServerPwd()

			};
 		} catch (Exception ex) {
 			throw new BizException("获取参数表指定参数异常,原因[" + ex.getMessage() + "]");
 		}
 	}
}
