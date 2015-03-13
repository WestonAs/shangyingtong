package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.IOUtil;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.ReportPathSave;
import gnete.card.service.ReportPathSaveService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reportPathSaveService")
public class ReportPathSaveServiceImpl implements ReportPathSaveService {
	final Logger				logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReportPathSaveDAO	reportPathSaveDAO;

	@Override
	public void downloadReportFile(String reportType, String merchantNo, String reportDate)
			throws BizException, IOException {
		ReportPathSave orgRecord = this.reportPathSaveDAO.findByPk(reportType, merchantNo, reportDate);
		Assert.notNull(orgRecord, "未找到报表");

		String filePath = orgRecord.getFilePath();
		if (SysparamCache.getInstance().isLocalReportFtpServer()) {
			Assert.isTrue(IOUtil.isFileExist(filePath), "要下载的日报表文件不存在");
			IOUtil.downloadFile(filePath);
		} else {
			logger.debug("本机不是报表服务器[{}]，ftp下载报表[{}]", SysparamCache.getInstance().getReportFtpServerIP(),	filePath);
			// 构造模板处理类
			IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(SysparamCache.getInstance()
					.getReportFtpServerIP(), SysparamCache.getInstance().getReportFtpServerUser(),
					SysparamCache.getInstance().getReportFtpServerPwd());
			CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(filePath.substring(0, filePath.lastIndexOf("/")), 
					orgRecord.getReportName());
			try {
				boolean flag = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
				logger.debug("ftp下载报表结果[{}]", flag);
				if (!flag) {
					throw new BizException("ftp下载报表失败");
				}
				IOUtil.downloadFile(downloadCallBack.getRemoteReferInputStream(), orgRecord.getReportName());
			} catch (CommunicationException e) {
				String msg = "ftp下载时发生异常";
				logger.warn(msg, e);
				throw new BizException(msg, e);
			}
		}
	}

	// ----------------------------- private methods followed ------------------------------
}
