package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonFilePathCallBackImpl;
import flink.ftp.impl.CommonRemoveCallBackImpl;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.CompondWriteUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.security.CertOperateException;
import flink.util.CommonHelper;
import flink.util.DownLoadHelper;
import flink.util.FileHelper;
import gnete.card.service.ICardCertificateKeyStore;
import gnete.card.service.ICardFileTransferProcess;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 
 * @Project: Card
 * @File: CardFileTransferProcessImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Service("cardFileTranferProcess")
public class CardFileTransferProcessImpl implements ICardFileTransferProcess {
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	@Qualifier("cardCertificateKeyStore")
	private ICardCertificateKeyStore cardCertificateKeyStore;

	public boolean uploadTransferFile(File uploadFile) throws CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = new CommonUploadCallBackImpl(remoteParams[0], uploadFile);

		// 处理文件上传
		boolean flag = ftpCallBackTemplate.processFtpCallBack(uploadCallBack);

		if (flag) {
			logger.info("文件上传处理成功,上传文件名===[" + FileHelper.getFullFileName(uploadFile) + "]");
		}

		return flag;
	}

	public boolean uploadTransferFile(File uploadFile, String fileName) throws CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = new CompondWriteUploadCallBackImpl(remoteParams[0], uploadFile,
				fileName);

		// 处理文件上传
		boolean flag = ftpCallBackTemplate.processFtpCallBack(uploadCallBack);

		if (flag) {
			logger.info("文件上传处理成功,上传文件名===[" + fileName + "]");
		}

		return flag;
	}

	public boolean downloadTransferFile(String fileName, HttpServletResponse response, String contentType)
			throws CommunicationException, BizException {

		InputStream remoteFstream = getTransferFileStream(fileName);

		if (remoteFstream == null) {
			throw new BizException("文件[" + fileName + "]无法从服务端下载,请联系管理员!");
		}

		try {
			DownLoadHelper.processDownLoad(response, contentType, fileName, remoteFstream);
			logger.info("文件下载成功，文件名为[" + fileName + "]");
		} catch (IOException ex) {
			logger.error("文件下载处理失败", ex);
			throw new BizException("文件下载异常,原因[" + ex.getMessage() + "]");
		}

		return true;
	}
	
	/**
	 * 
	 * <p>根据目录、文件名以及参数进行下载处理</p>
	 * @param remotePath
	 * @param fileName
	 * @param remoteParams
	 * @return
	 * @throws CommunicationException
	 * @version: 2011-7-8 下午02:15:56
	 * @See:
	 */
	public String getRemoteFilePath(String fileName) throws CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		CommonFilePathCallBackImpl filePathCallBack = new CommonFilePathCallBackImpl(remoteParams[0], fileName);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(filePathCallBack);

		if (flag) {
			String pathRefer = filePathCallBack.getFilePathRefer();
			return CommonHelper.isNotEmpty(pathRefer) ? pathRefer : "";
		}

		return "";
	}

	public InputStream getTransferFileStream(String fileName) throws CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(remoteParams[0], fileName);

		// 处理下载
		boolean result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);

		if (result) {
			return downloadCallBack.getRemoteReferInputStream();
		}

		return null;
	}

	public Certificate getTransferCertificate(String fileName) throws DataAccessException, CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		return getTransferCertificate(remoteParams[1], remoteParams[2], remoteParams[3], remoteParams[0], fileName);
	}

	public Certificate getTransferCertificate(String remoteFilePath, String fileName) throws DataAccessException,
			CommunicationException, BizException {
		String[] remoteParams = getRemoteParams();

		return getTransferCertificate(remoteParams[1], remoteParams[2], remoteParams[3], remoteFilePath, fileName);
	}

	/**
	 * 
	 * <p>增加内部方法用于获取FTP服务器上的证书</p>
	 * @param server
	 * @param user
	 * @param pwd
	 * @param remoteFilePath
	 * @param fileName
	 * @return
	 * @throws DataAccessException
	 * @throws CommunicationException
	 * @throws BizException
	 * @version: 2011-1-10 上午11:20:23
	 * @See:
	 */
	private Certificate getTransferCertificate(String server, String user, String pwd, String remoteFilePath, String fileName)
			throws CommunicationException, BizException {
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(server, user, pwd);

		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(remoteFilePath, fileName);

		// 处理下载
		boolean result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);

		if (result) {
			InputStream remoteFstream = downloadCallBack.getRemoteReferInputStream();
			if (remoteFstream != null) {
				try {
					return this.cardCertificateKeyStore.getCertficateFromFileStream(remoteFstream);
				} catch (CertOperateException ex) {
					logger.error("证书认证提取异常,文件[" + fileName + "]", ex);
					throw new BizException("提取证书文件[" + fileName + "]认证异常,原因[" + ex.getMessage() + "]");
				}
			}
		}

		throw new BizException("从服务端获取文件[" +  fileName + "]失败,无法提取证书认证信息!");
	}

	/**
	 * <p>处理文件移除</p>
	 */
	@SuppressWarnings("unchecked")
	public boolean removeRemoteFile(String[] removeFileNames) throws CommunicationException, BizException {
		if(CommonHelper.checkIsEmpty(removeFileNames)) {
			return false;
		}
		
		String[] remoteParams = getRemoteParams();

		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(remoteParams[1], remoteParams[2], remoteParams[3]);

		// 构造移除回调
		CommonRemoveCallBackImpl removeCallBack = new CommonRemoveCallBackImpl(remoteParams[0], removeFileNames);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(removeCallBack);
		
		Object[] removeResult = removeCallBack.getFileRemoveResult();
		
		if(flag) {
			List<String> successRemoveList = (List<String>) removeResult[1];

			String successArrayStr = CommonHelper.filterArray2Str(successRemoveList.toArray(new String[successRemoveList.size()]));
			logger.info("文件移除成功,文件名" + successArrayStr);

		    return true;
		}

		List<String> failRemoveList = (List<String>) removeResult[0];
		String failArrayStr = CommonHelper.filterArray2Str(failRemoveList.toArray(new String[failRemoveList.size()]));
		throw new BizException("文件移除失败(原文件已经移除等),文件名" + failArrayStr);
	}

	/**
	 * <p>构造证书涉及FTP参数</p>
	 */
	private String[] getRemoteParams() throws BizException {
		try {
			SysparamCache paraMgr = SysparamCache.getInstance();
			return new String[] { paraMgr.getUserCertFileRemotePath(), paraMgr.getFtpServerIP(),
					paraMgr.getFtpServerUser(), paraMgr.getFtpServerPwd()

			};
		} catch (Exception ex) {
			throw new BizException("获取参数表指定参数异常,原因[" + ex.getMessage() + "]");
		}
	}

}
