package gnete.card.service.impl;

import flink.util.IOUtil;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RetransCardRegDAO;
import gnete.card.dao.TerminalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RetransCardReg;
import gnete.card.entity.Terminal;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.PlatformType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.RetransCardRegService;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("retransCardRegService")
public class RetransCardRegServiceImpl implements RetransCardRegService {
	
	@Autowired
	private RetransCardRegDAO	retransCardRegDAO;
	@Autowired
	private CardInfoDAO			cardInfoDAO;
	@Autowired
	private AcctInfoDAO			acctInfoDAO;
	@Autowired
	private MerchInfoDAO		merchInfoDAO;
	@Autowired
	private TerminalDAO			terminalDAO;
	@Autowired
	private WorkflowService		workflowService;

	final static Logger			logger	= Logger.getLogger(RetransCardRegServiceImpl.class);
	
	public RetransCardReg addRetransCardReg(RetransCardReg retransCardReg, UserInfo userInfo) throws BizException {
		Assert.notNull(retransCardReg, "添加的卡补账对象不能为空");
		Assert.notEmpty(retransCardReg.getCardId(), "卡号不能为空");
		Assert.notEmpty(retransCardReg.getMerchId(), "商户编号不能为空");
		Assert.notEmpty(retransCardReg.getTermId(), "终端编号不能为空");
		Assert.notNull(retransCardReg.getAmt(), "补帐金额不能为空");
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(retransCardReg.getCardId());
		Assert.notNull(cardInfo, "卡号[" + cardInfo + "]不存在");
		
		retransCardReg.setCardBranch(cardInfo.getCardIssuer());
		
		String roleType = userInfo.getRole().getRoleType();
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			retransCardReg.setInitiator(userInfo.getDeptId());
		} else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			retransCardReg.setInitiator(userInfo.getMerchantNo());
		} else {
			retransCardReg.setInitiator(userInfo.getBranchNo());
		}
		
		retransCardReg.setStatus(RegisterState.WAITED.getValue());
		retransCardReg.setPlatform(PlatformType.WEB.getValue());		
		retransCardReg.setUpdateTime(new Date());
		retransCardReg.setUpdateUser(userInfo.getUserId());
				
		long retransCardId = (Long)this.retransCardRegDAO.insert(retransCardReg);
		
		retransCardReg.setRetransCardId(retransCardId);
		
		try {
			//启动单个流程（后续错误，但数据已提交）
			this.workflowService.startFlow(retransCardReg, "retransCardRegAdapter",
					Long.toString(retransCardReg.getRetransCardId()), userInfo);
		} catch (Exception e) {
			throw new BizException("启动补帐审核流程时发生异常");
		}
		
		return retransCardReg; 
	}

	@Override
	public void addRetransRegBatFile(File upload, int detailCnt, BigDecimal totalAmt, String remark,
			UserInfo userInfo) throws BizException {
		// 文件合法性检查
		List<String> detailLines = checkFileAndGetLines(upload, detailCnt, totalAmt);
		// 明细合法性检查
		List<RetransCardReg> regList = checkLinesAndGetRegs(detailLines, remark, userInfo);
		for(RetransCardReg retransCardReg : regList){
			long retransCardId = (Long)this.retransCardRegDAO.insert(retransCardReg);
			retransCardReg.setRetransCardId(retransCardId);
			try {
				//启动单个流程（后续错误，但数据已提交）
				this.workflowService.startFlow(retransCardReg, "retransCardRegAdapter",
						Long.toString(retransCardReg.getRetransCardId()), userInfo);
			} catch (Exception e) {
				throw new BizException("启动补帐审核流程时发生异常");
			}
		}
	}
	
	public boolean modifyRetransCardReg(RetransCardReg retransCardReg, String modifyUserId) throws BizException {
		Assert.notNull(retransCardReg, "更新的卡补账对象不能为空");		
		
		retransCardReg.setStatus(RegisterState.WAITED.getValue());
		retransCardReg.setUpdateTime(new Date());
		retransCardReg.setUpdateUser(modifyUserId);
		
		int count = this.retransCardRegDAO.update(retransCardReg);		
		
		return count > 0;
	}

	public boolean deleteRetransCardReg(long signRuleId) throws BizException{
		Assert.notNull(signRuleId, "删除的卡补账ID不能为空");
		long count = 0;			
		return count > 0;
	}
	
	// ----------------------------- private methods followed ------------------------------
	
	
	/**
	 * 检查批量卡文件内容：1、导入文件内容的格式；2、明细数、总金额；
	 * 返回明细行集合
	 */
	@SuppressWarnings("unchecked")
	private List<String> checkFileAndGetLines(File file, int detailCnt, BigDecimal totalAmt)
			throws BizException {
		Assert.isTrue(detailCnt >= 1 && detailCnt <= 1000, "明细笔数必须 属于[1,1000]区间");
		
		List<String> lines = null;
		try {
			lines = IOUtil.readLines(file);
		} catch (IOException e) {
			logger.warn(e, e);
			throw new BizException(e.getMessage());
		}
		Assert.isTrue(detailCnt == lines.size() - 1, String.format(
				"页面录入的总笔数[%s]与实际明细的笔数[%s]不一致，请检查文件中是否有空行", detailCnt, lines.size() - 1));

		BigDecimal calTotalAmt = new BigDecimal("0.0");// 明细总金额数（计算）
		for (int i = 1; i < lines.size(); i++) {
			String errMsgPrefix = "明细第" + i + "行错误：";
			String detailLine = (String) lines.get(i);
			Assert.isTrue(!StringUtils.isBlank(detailLine), errMsgPrefix + "导入文件不能有空行！");

			String[] lineArr = detailLine.split(",", -1);
			Assert.isTrue(!ArrayUtils.isEmpty(lineArr), errMsgPrefix + "解析出的数组不能为空");
			Assert.isTrue(lineArr.length == 5, errMsgPrefix + "字段个数必须是5个");
			
			for(int j=0; j<5; j++){
				Assert.isTrue(StringUtils.isNotBlank(lineArr[j]), errMsgPrefix + "第 " + (j + 1) + "个字段值不能为空");
			}
			
			calTotalAmt = calTotalAmt.add(NumberUtils.createBigDecimal(lineArr[1]));
		}
		Assert.isTrue(calTotalAmt.compareTo(totalAmt) == 0, String.format(
				"明细的总金额[%s] 与 页面录入的总金额[%s]不一致！", calTotalAmt, totalAmt));
		return lines;
	}
	
	/**
	 * 批量卡补账明细行，进行检查
	 * @param remark 
	 * 
	 * @param lineArr
	 *            单条记录的数组
	 * @param lineNo
	 *            记录的行数的序号（从1开始）
	 * @throws BizException
	 */
	private List<RetransCardReg> checkLinesAndGetRegs(List<String> lines, String remark, UserInfo sessionUser)
			throws BizException {
		List<RetransCardReg> regList = new ArrayList<RetransCardReg>(lines.size());
		final Map<String, MerchInfo> MerchInfoMapCache = new HashMap<String, MerchInfo>();
		final Map<String, Terminal> TerminalMapCache = new HashMap<String, Terminal>();
		for (int i = 1; i < lines.size(); i++) {
			String[] lineArr = lines.get(i).split(",", -1);
			String errMsgPrefix = "明细第" + i + "行错误：";

			RetransCardReg reg = new RetransCardReg();
			// 卡号
			String cardId = lineArr[0];
			if (StringUtils.isBlank(cardId)) {
				throw new BizException(errMsgPrefix + "卡号不能为空。");
			}
			if (cardId.length() > 19) {
				throw new BizException(errMsgPrefix + "卡号[" + cardId + "]长度不能超过19位！");
			}
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.isTrue(cardInfo != null
					&& StringUtils.equals(sessionUser.getBranchNo(), cardInfo.getCardIssuer()), errMsgPrefix
					+ "操作机构必须是卡的发卡机构！");
			reg.setCardId(cardId);
			AcctInfo acctInfo = (AcctInfo) acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, errMsgPrefix + "卡号[" + cardInfo + "]不存在账户，无法补账。");
			reg.setAcctId(cardInfo.getAcctId());
			reg.setCardBranch(cardInfo.getCardIssuer());
			reg.setInitiator(cardInfo.getCardIssuer());

			// 金额
			try {
				BigDecimal amt = NumberUtils.createBigDecimal(lineArr[1]);
				reg.setAmt(amt);
			} catch (NumberFormatException e) {
				throw new BizException(errMsgPrefix + "金额格式错误");
			}

			// 商户编号
			String merchId = lineArr[2]; 
			MerchInfo merchInfo = MerchInfoMapCache.get(merchId);
			if (merchInfo == null) {
				merchInfo = (MerchInfo) merchInfoDAO.findByPk(merchId);
				MerchInfoMapCache.put(merchId, merchInfo);
			}
			Assert.isTrue(merchInfo != null, errMsgPrefix + "编号为" + merchId + "的商户不存在！");
			reg.setMerchId(merchId);
			
			// 终端编号
			String terminalId = lineArr[3]; 
			Terminal terminal = TerminalMapCache.get(terminalId);
			if (terminal == null) {
				terminal = (Terminal) terminalDAO.findByPk(terminalId);
				TerminalMapCache.put(terminalId, terminal);
			}
			Assert.isTrue(terminal != null, errMsgPrefix + "编号为" + terminalId + "的终端不存在！");
			reg.setTermId(terminalId);

			// 不先用赠券子账户
			String coupon = lineArr[4]; 
			Assert.isTrue("1".equals(coupon) || "0".equals(coupon), errMsgPrefix + "不先用赠券子账户字段的值必须是1或0");
			reg.setCoupon(coupon);

			reg.setStatus(RegisterState.WAITED.getValue());
			reg.setPlatform(PlatformType.WEB.getValue());
			reg.setRemark(remark);
			reg.setUpdateTime(new Date());
			reg.setUpdateUser(sessionUser.getUserId());

			regList.add(reg);
		}
		return regList;
	}
}
