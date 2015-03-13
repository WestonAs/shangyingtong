package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.CommonWriteUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.DateUtil;
import flink.util.IOUtil;
import gnete.card.dao.ExternalCardImportRegDAO;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.entity.type.AdjType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.ExternalCardImportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("externalCardImportService")
public class ExternalCardImportServiceImpl implements ExternalCardImportService {
	@Autowired
	private ExternalCardImportRegDAO	externalCardImportRegDAO;
	@Autowired
	private CardRiskService				cardRiskService;
	@Autowired
	private WorkflowService				workflowService;

	static Logger						logger		= Logger.getLogger(ExternalCardImportServiceImpl.class);

	/** 默认分隔符 */
	private static final String			DEFAULT_SEQ	= "\\|";

	@Override
	public ExternalCardImportReg addExternalCardImportReg(File file, ExternalCardImportReg importReg,
			UserInfo user) throws BizException {

		String fileNamePrefix = user.getBranchNo() + "_" + DateUtil.getCurrentDate();
		if (importReg.isExternalNumMakeCard()) {// 开卡
			fileNamePrefix = "kk_" + fileNamePrefix;
		}
		Assert.isTrue(Pattern.matches(fileNamePrefix + "_\\d{3}.txt", importReg.getFileName()),
				"导入文件的文件名格式应该为：发卡机构编号_当前日期_批次.txt，如 " + fileNamePrefix + "_001.txt ");

		// 设置相关字段
		importReg.setCardBranch(user.getBranchNo());// 发卡机构
		importReg.setUpdateBy(user.getUserId());
		importReg.setUpdateTime(new Date());
		importReg
				.setBranchCode(StringUtils.isBlank(user.getDeptId()) ? user.getBranchNo() : user.getDeptId()); // 操作机构

		// 合法性检查
		checkExternalCardImport(file, importReg);

		// 将外部卡导入文件上传到指定的ftp服务器
		boolean flag = uploadImportFile(file, importReg.getFileName(), false);
		Assert.isTrue(flag, "上传文件到ftp服务器失败，可能已经存在同名的文件");

		// 在登记簿中插入一条记录

		if (importReg.isExternalNumMakeCard()) { // 开卡
			importReg.setStatus(ExternalCardImportState.WAIT_CHECK.getValue());
			externalCardImportRegDAO.insert(importReg);
			try {
				// 启动流程，审核通过后，再扣减风险保证金
				this.workflowService.startFlow(importReg, WorkflowConstants.EXTERNAL_NUM_MAKE_CARD_ADAPTER,
						Long.toString(importReg.getId()), user);
			} catch (Exception e) {
				throw new BizException("启动外部号码开卡流程时发生异常。原因：" + e.getMessage());
			}
		} else { // 导入
			importReg.setStatus(ExternalCardImportState.WAIT_DEAL.getValue());
			externalCardImportRegDAO.insert(importReg);
			// 扣减风险保证金
			this.deductCardRisk(importReg);
			// 发送命令
			MsgSender.sendMsg(MsgType.EXTERNAL_CARD_IMPORT, importReg.getId(), user.getUserId());
		}

		return importReg;
	}

	@Override
	public InputStream downloadOrigFile(Long externalCardImportRegId) throws BizException {
		ExternalCardImportReg reg = (ExternalCardImportReg) externalCardImportRegDAO
				.findByPk(externalCardImportRegId);
		Assert.notNull(reg, "没有找到对应的导入记录：id=" + externalCardImportRegId);

		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getExternalCardFileRemotePath();
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, reg
				.getFileName());

		boolean flag = false;
		try {
			flag = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			String msg = "FTP下载时发生异常：" + e.getMessage();
			logger.warn(msg);
			throw new BizException(msg);
		}
		logger.debug("downloadFlag:" + flag);
		return downloadCallBack.getRemoteReferInputStream();
	}

	@Override
	public ExternalCardImportReg reImport(File file, ExternalCardImportReg newImportReg, UserInfo user)
			throws BizException {
		ExternalCardImportReg origReg = (ExternalCardImportReg) externalCardImportRegDAO
				.findByPk(newImportReg.getId());
		Assert.notNull(origReg, "没有找到原始记录！");

		Assert.equals(newImportReg.getFileName(), origReg.getFileName(), "失败！ 重新导入的文件名必须与旧文件名相同！");
		Assert.equals(origReg.getStatus(), ExternalCardImportState.DEAL_FAILED.getValue(), "处理失败记录 才能 重新导入！");

		// 更新原始对象的相关字段。
		origReg.setTotalCount(newImportReg.getTotalCount());
		origReg.setTotalAmt(newImportReg.getTotalAmt());
		origReg.setTotalPoint(newImportReg.getTotalPoint());
		origReg.setStatus(ExternalCardImportState.RE_WAIT_DEAL.getValue());

		// 合法性检查
		checkExternalCardImport(file, origReg);

		// 将外部卡导入文件上传到指定的ftp服务器
		boolean flag = uploadImportFile(file, origReg.getFileName(), true);
		Assert.isTrue(flag, "上传文件到ftp服务器失败");

		// 在登记簿中插入一条记录
		externalCardImportRegDAO.update(origReg);

		// 发送命令
		MsgSender.reSendMsg(MsgType.EXTERNAL_CARD_IMPORT, origReg.getId(), user.getUserId());

		// 扣减风险保证金
		this.deductCardRisk(origReg);

		return origReg;
	}

	/** 扣减风险准备金 */
	@Override
	public void deductCardRisk(ExternalCardImportReg importReg) throws BizException {
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(importReg.getTotalAmt());
		cardRiskReg.setBranchCode(importReg.getCardBranch()); // 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}

	// ----------------------------- private methods followed ------------------------------

	/**
	 * 外部卡导入合法性检查：1、导入文件名/文件内容的格式；2、总比数、总金额、总积分；
	 */
	@SuppressWarnings("unchecked")
	private void checkExternalCardImport(File file, ExternalCardImportReg importReg) throws BizException {
		// 定义可上传文件的 类型
		Assert.isTrue(IOUtil.testFileFix(importReg.getFileName(), Arrays.asList("txt")), "外部卡导入文件的格式只能是文本文件");

		int totalCount = importReg.getTotalCount();
		Assert.isTrue(totalCount >= 1 && totalCount <= 50000, "总笔数必须 属于[1,50000]区间");

		List<String> lines = null;
		try {
			lines = IOUtil.readLines(file);
		} catch (IOException e) {
			logger.warn(e, e);
			throw new BizException(e.getMessage());
		}
		Assert.isTrue(totalCount == lines.size() - 1, String.format(
				"页面录入的总笔数[%s]与实际明细的笔数[%s]不一致，请检查文件中是否有空行", totalCount, lines.size() - 1));

		BigDecimal totalAmt = new BigDecimal("0.0");// 总金额数
		BigDecimal totalPoint = new BigDecimal("0.0"); // 总积分数
		boolean hasPtCls = !StringUtils.isBlank(importReg.getPtClass()); // 所选择的卡子类型定义里是否有积分类型
		for (int i = 1; i < lines.size(); i++) {
			String importLine = (String) lines.get(i);
			Assert.isTrue(!StringUtils.isBlank(importLine), "导入文件不能有空行！ 明细第" + i + "行为空行！");

			String[] lineArr = importLine.split(DEFAULT_SEQ, -1);
			// 单行检测
			checkExternalCardImportLine(lineArr, i, hasPtCls);

			BigDecimal amt = NumberUtils.createBigDecimal(lineArr[1]);
			totalAmt = totalAmt.add(amt);

			BigDecimal point = NumberUtils.createBigDecimal(lineArr[2]);
			totalPoint = totalPoint.add(point);
		}
		Assert.isTrue(totalAmt.compareTo(importReg.getTotalAmt()) == 0, String.format(
				"明细的总金额[%s] 与 页面录入的总金额[%s]不一致！", totalAmt, importReg.getTotalAmt()));
		Assert.isTrue(totalPoint.compareTo(importReg.getTotalPoint()) == 0, String.format(
				"明细总积分[%s] 与 页面录入的总积分[%s]不一致！", totalPoint, importReg.getTotalPoint()));
	}

	/**
	 * 外部卡导入明细里的卡号，进行检查
	 * 
	 * @param lineArr
	 *            单条记录的数组
	 * @param count
	 *            记录的行数的序号（从1开始）
	 * @param hasPtCls
	 *            所选择的卡子类型定义里是否有积分类型（如果没有积分类型，这通用积分必须为0）；
	 * @throws BizException
	 */
	private void checkExternalCardImportLine(String[] lineArr, int count, boolean hasPtCls)
			throws BizException {
		String errMsgPrefix = "明细第" + count + "行错误：";
		if (ArrayUtils.isEmpty(lineArr)) {
			throw new BizException(errMsgPrefix + "解析出的数组为空");
		}
		if (lineArr.length != 4) {
			String msg = errMsgPrefix + "字段个数必须是4个";
			logger.warn(msg);
			throw new BizException(msg);
		}
		String cardNo = lineArr[0];
		if (StringUtils.isBlank(cardNo)) {
			throw new BizException(errMsgPrefix + "卡号不能为空。");
		}
		if (cardNo.length() > 19) {
			throw new BizException(errMsgPrefix + "卡号[" + cardNo + "]长度超过19位！");
		}
		try {
			NumberUtils.createBigDecimal(lineArr[1]);
		} catch (NumberFormatException e) {
			throw new BizException(errMsgPrefix + "金额格式错误");
		}
		try {
			NumberUtils.createBigDecimal(lineArr[2]);
		} catch (NumberFormatException e) {
			throw new BizException(errMsgPrefix + "积分格式错误");
		}
		if (!hasPtCls) {
			Assert.isTrue(new BigDecimal("0").compareTo(NumberUtils.createBigDecimal(lineArr[2])) == 0,
					errMsgPrefix + "由于选择的卡类型没有对应的积分类型，所以通用积分字段值必须全部为0");
		}
	}

	/**
	 * 将外部卡导入文件上传到指定目录
	 * 
	 * @param file
	 * @param fileName
	 * @param couldReplaceOld
	 *            是否能替换旧文件
	 * @return
	 * @throws BizException
	 */
	private boolean uploadImportFile(File file, String fileName, boolean couldReplaceOld) throws BizException {
		String webSavePath = SysparamCache.getInstance().getCardStyleLocalWebSavePath();
		// 先将文件保存本地
		this.saveToWebLocalPath(file, fileName, webSavePath);
		logger.debug("uploadFile:" + file.getName() + "===" + file.length());
		File locFile = null;
		try {
			locFile = IOUtil.getFile(webSavePath, fileName, true);
			logger.debug("localFile:" + locFile.getName() + "===" + locFile.length());
		} catch (IOException ex) {
			logger.warn(ex.getMessage());
			throw new BizException(ex.getMessage());
		}

		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getExternalCardFileRemotePath();
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = couldReplaceOld ? new CommonWriteUploadCallBackImpl(ftpPath,
				locFile) : new CommonUploadCallBackImpl(ftpPath, locFile);

		// 处理文件上传
		boolean flag = false;
		try {
			flag = ftpCallBackTemplate.processFtpCallBack(uploadCallBack);
		} catch (CommunicationException e) {
			String msg = "FTP上传时发生异常：" + e.getMessage();
			logger.warn(msg);
			throw new BizException(msg);
		}
		logger.debug("uploadFlag:" + flag);
		return flag;
	}

	/**
	 * 将文件保存到本地
	 * 
	 * @param cardStyleFile
	 * @return
	 */
	private void saveToWebLocalPath(File file, String fileName, String webSavePath) throws BizException {
		try {
			IOUtil.uploadFile(file, fileName, webSavePath);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

}
