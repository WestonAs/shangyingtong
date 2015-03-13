package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.IOUtil;
import gnete.card.dao.BDPtDtotalDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CpsParaDAO;
import gnete.card.dao.MembImportRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.BDPtDtotal;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CpsPara;
import gnete.card.entity.CpsParaKey;
import gnete.card.entity.MembImportReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CpStatus;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.entity.state.WaitsinfoState;
import gnete.card.entity.state.WebState;
import gnete.card.entity.type.IssType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CpsService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cpsService")
public class CpsServiceImpl implements CpsService {

	@Autowired
	private CpsParaDAO cpsParaDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private MembImportRegDAO membImportRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BDPtDtotalDAO bdPtDtotalDAO;
	
	static Logger logger = Logger.getLogger(CpsServiceImpl.class);
	
	/**
	 * 默认分隔符
	 */
	private static final String DEFAULT_SEQ = "\\|";
	
	public boolean addCpsPara(CpsPara cpsPara, String userId)
			throws BizException {
		Assert.notNull(cpsPara, "添加的代收付权限参数不能为空");
		
		//检查新增对象是否已经存在
		CpsParaKey key = new CpsParaKey();
		key.setCpsMerchNo(cpsPara.getCpsMerchNo());
		key.setIssCode(cpsPara.getIssCode());
		CpsPara old = (CpsPara)this.cpsParaDAO.findByPk(key);
		
		Assert.isNull(old, IssType.valueOf(cpsPara.getIssType()).getName() + 
				"["+ cpsPara.getIssCode() + "]、账户[" + cpsPara.getCpsMerchNo() + "]的代收付权限参数已经设置，不能重复定义");
				
		cpsPara.setStatus(CardTypeState.NORMAL.getValue());		
		cpsPara.setUpdateTime(new Date());
		cpsPara.setUpdateBy(userId);
				
		return this.cpsParaDAO.insert(cpsPara) != null; 
	}

	public boolean deleteCpsPara(CpsParaKey key) throws BizException {
		Assert.notNull(key, "删除的代收付权限参数不能为空");		
		return this.cpsParaDAO.delete(key) > 0;	
	}

	public boolean modifyCpsPara(CpsPara cpsPara, String userId)
			throws BizException {
		Assert.notNull(cpsPara, "更新的代收付权限参数不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(cpsPara.getStatus())){
			cpsPara.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(cpsPara.getStatus())){
			cpsPara.setStatus(CardTypeState.NORMAL.getValue());
		}
		
		cpsPara.setUpdateBy(userId);
		cpsPara.setUpdateTime(new Date());
		return this.cpsParaDAO.update(cpsPara) > 0;
	}

	public Long sendCtConfirmMsg(BDPtDtotal bdPtDtotal, String branchCode, String userCode)
			throws BizException {
		Assert.notNull(bdPtDtotal, "代收统计数据不能为空!");
		//Assert.equals(bdPtDtotal.getCardIssuer(), branchCode, "确认机构和交易中的发卡机构不符！");
		
		// 失败和文件发送中状态的交易才能手动确认
		Assert.isTrue(CpStatus.FAILURE.getValue().equals(bdPtDtotal.getCpStatus()) || 
						CpStatus.FILE_SENT.getValue().equals(bdPtDtotal.getCpStatus()), "失败和文件发送中状态的交易才能手动确认");
		
		String refId = bdPtDtotal.getCpSn();
		Assert.isTrue(refId.length()<=12, "流水号不能大于12位！");
		
		Long id = new BigDecimal(refId).longValue();
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.COLLECT_CONFIRM, id);
		
		if(waitsinfo != null){ // 如果命令已经存在，更新重新发起命令
			try {
				waitsinfo = (Waitsinfo) this.waitsinfoDAO.findByPkWithLock(waitsinfo.getId());
			} catch (Exception e) {
				throw new BizException("锁定失败！");
			}
			waitsinfo.setUserCode(userCode);
			waitsinfo.setSendTime(new Date());
			waitsinfo.setStatus(WaitsinfoState.UNDEAL.getValue());
			waitsinfo.setWebState(WebState.UNDEAL.getValue());
			this.waitsinfoDAO.update(waitsinfo);
			return waitsinfo.getId();
		}
		else {
			return MsgSender.sendMsg(MsgType.COLLECT_CONFIRM, id, userCode);
		}
	}

	public void sendCtManualMsg(BDPtDtotal bdPtDtotal, String branchCode, String userCode)
			throws BizException {
		Assert.notNull(bdPtDtotal, "代收统计数据不能为空!");
		// Assert.equals(bdPtDtotal.getCardIssuer(), branchCode, "发起机构和交易中的发卡机构不符！");

		// 失败状态的交易才能手动发起
		Assert.isTrue(CpStatus.FAILURE.getValue().equals(bdPtDtotal.getCpStatus()), "失败状态的交易才能手动发起");

		String refId = bdPtDtotal.getCpSn();
		Assert.isTrue(refId.length() <= 12, "流水号不能大于12位！");

		bdPtDtotal.setCpStatus(CpStatus.UNPAID.getValue());
		bdPtDtotal.setRemark(String.format("%s[失败重发,原清算日期:%s]", StringUtils.trimToEmpty(bdPtDtotal
				.getRemark()), bdPtDtotal.getSettDate()));
		bdPtDtotal.setSettDate(SysparamCache.getInstance().getWorkDateNotFromCache());
		bdPtDtotal.setCpDate("");
		bdPtDtotal.setPayOnTime("1");
		bdPtDtotalDAO.update(bdPtDtotal);
		
		/* 不使用waitsinfo与后台交互处理
		Long id = new BigDecimal(refId).longValue();
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.COLLECT_MANUAL, id);
		
		if(waitsinfo != null){ // 如果命令已经存在，更新重新发起命令
			try {
				waitsinfo = (Waitsinfo) this.waitsinfoDAO.findByPkWithLock(waitsinfo.getId());
			} catch (Exception e) {
				throw new BizException("锁定失败！");
			}
			waitsinfo.setUserCode(userCode);
			waitsinfo.setSendTime(new Date());
			waitsinfo.setStatus(WaitsinfoState.UNDEAL.getValue());
			waitsinfo.setWebState(WebState.UNDEAL.getValue());
			this.waitsinfoDAO.update(waitsinfo);
			return waitsinfo.getId();
		}
		else {
			return MsgSender.sendMsg(MsgType.COLLECT_MANUAL, id, userCode);
		}
		*/
	}

	public MembImportReg addMembImportReg(File file, MembImportReg importReg, UserInfo user)
			throws BizException {
		
		// 解析外部卡导入文件，检查卡号是否重复，卡号是否已经存在数据库中
		int totalRecord = calcTotalRecord(file);
		
		// 在登记簿中插入一条记录 
		importReg.setCardBranch(user.getBranchNo());
		importReg.setStatus(ExternalCardImportState.WAIT_DEAL.getValue());
		importReg.setUpdateBy(user.getUserId());
		importReg.setUpdateTime(new Date());
		importReg.setTotalCount(totalRecord);
		membImportRegDAO.insert(importReg);
		
		// 将导入文件上传到指定的ftp服务器
		boolean flag = uploadImportFile(file, importReg.getFileName());
		
		Assert.isTrue(flag, "上传文件到ftp服务器失败");
		
		// 发送命令 
		MsgSender.sendMsg(MsgType.MEMB_IMPORT, importReg.getId(), user.getUserId());
		
		return importReg;
	}

	/**
	 * 将会员注册文件导入文件上传到指定目录
	 * @param file
	 * @param fileName
	 * @return
	 * @throws BizException
	 */
	private boolean uploadImportFile(File file, String fileName) throws BizException {
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getMembFilePath();
		
		String webSavePath = SysparamCache.getInstance().getCardStyleLocalWebSavePath();
		
		// 先将卡样文件传到本地
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
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造面向uploadFile的回调处理类
		IFtpTransferCallback uploadCallBack = new CommonUploadCallBackImpl(ftpPath, locFile);
		
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
	 * @param file
	 * @param fileName
	 * @param webSavePath
	 * @throws BizException
	 */
	private void saveToWebLocalPath(File file, String fileName, String webSavePath) throws BizException {
		try {
			IOUtil.uploadFile(file, fileName, webSavePath);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}
	
	/**
	 * 检查文件格式，并返回记录总数
	 * @param file
	 * @return
	 * @throws BizException
	 */
	private int calcTotalRecord(File file) throws BizException{
		
		int totalNum = 0;
		List lines = null;
		
		try {
			lines = IOUtil.readLines(file);
		} catch (IOException e) {
			logger.debug(e, e);
			throw new BizException(e.getMessage());
		}
		
		// 获取表头字段数目
		int fieldNum = 0;
		if( lines.size()!= 0 ){
			fieldNum = ((String) lines.get(0)).split(DEFAULT_SEQ).length;
		}
		
		// 记录卡号, 用作判断是否重复.
		Set<String> cardNoSet = new HashSet<String>();
		
		// 解析充值明细， 从第二行开始解析
		for (int i = 1, n = lines.size(); i < n; i++) {
			String depositLine = (String) lines.get(i);
			
			// 空行略过.
			if (StringUtils.isEmpty(depositLine)) {
				continue;
			}
			
			totalNum ++;
			String[] arr = depositLine.split(DEFAULT_SEQ, -1);
			
			// 检查卡号
			checkCardNo(arr, i, fieldNum, cardNoSet);
		}
			
		return totalNum;
	}
	
	/**
	 * 检查会员注册导入文件卡号是否存在和重复
	 * 
	 * @param arr 单条记录的数组
	 * @param count 记录的条数的序号
	 * @param fieldNum 模板中的明细个数
	 * @param cardNoSet 卡号
	 * @throws BizException
	 */
	private void checkCardNo(String[] arr, int count, int fieldNum, 
			Set<String> cardNoSet) throws BizException {
		
		if (arr.length != fieldNum) { // 记录元素个数要和表头个数一致.
			String msg = "第" + (count+1) + "行格式错误,元素不为" + fieldNum + "个";
			logger.error(msg);
			throw new BizException(msg);
		}
		
		if (ArrayUtils.isEmpty(arr)) {
			throw new BizException("解析出的数组为空");
		}
		
		//取得卡号,为空表示有错，抛出异常，跳出循环
		String cardNo = arr[0];
		if (StringUtils.isEmpty(cardNo)) {
			throw new BizException("会员注册文件第" + (count+1) + "行格式错误，卡号不能为空。");
		}
		
		//卡号不能重复
		if (cardNoSet.contains(cardNo)) {
			throw new BizException("卡号[" + cardNo + "]的记录重复！");
		}
		
		//检查卡号是否存在
		CardInfo info = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
		Assert.notNull(info, "会员注册文件第" + (count+1) + "行的卡号[" + cardNo + "]的不存在！");
		
		cardNoSet.add(cardNo);
	}
	

}
