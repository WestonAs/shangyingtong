package gnete.card.adapter;

import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.DepositPointBatRegDAO;
import gnete.card.dao.DepositPointRegDAO;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.DepositPointBatReg;
import gnete.card.entity.DepositPointReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: DepositPointAdapter.java
 *
 * @description: 积分充值流程审核处理适配器
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-26 下午04:10:10
 */
@Repository
public class DepositPointAdapter implements WorkflowAdapter {
	
	@Autowired
	private DepositPointRegDAO depositPointRegDAO;
	@Autowired
	private DepositPointBatRegDAO depositPointBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;

	private final Log logger = LogFactory.getLog(getClass());
	
	public void flowEnd(String refid, String param, String userId)
			throws BizException {
		logger.debug("积分审核通过的相关处理：1.将登记簿状态改为审核通过 2.发送命令到后台 3.扣减风险准备金和操作员配额");
		DepositPointReg depositPointReg = (DepositPointReg) this.depositPointRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		Assert.notNull(depositPointReg, "找不到积分充值登记[" + refid + "]记录");
		
		CardInfo cardInfo = null;
		
		// 1.将登记簿状态改为审核通过
		depositPointReg.setStatus(RegisterState.WAITEDEAL.getValue());
		depositPointReg.setUpdateUser(userId);
		depositPointReg.setUpdateTime(new Date());
		
		this.depositPointRegDAO.update(depositPointReg);

		//批量的话，还要更新批量积分充值明细登记簿的记录
		if (StringUtils.isEmpty(depositPointReg.getCardId())) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("depositBatchId", depositPointReg.getDepositBatchId());
			List<DepositPointBatReg> batList = this.depositPointBatRegDAO.findDepositPointBatList(map);
			
			Assert.notEmpty(batList, "批次号为[" + refid + "]的批量充值明细记录不存在");
			
			// 任取一张卡
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(batList.get(0).getCardId());
			Assert.notNull(cardInfo, "卡号[" + batList.get(0).getCardId() + "]不存在");
			
			// 更新批量明细表中的记录
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("status", RegisterState.WAITEDEAL.getValue());
			params.put("depositBatchId", depositPointReg.getDepositBatchId());
			
			int count = this.depositPointBatRegDAO.updateStatusByDepositBatchId(params);
			
			logger.debug("批量更新的条数为[" + count + "]");
		} else {
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositPointReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + depositPointReg.getCardId() + "]不存在");
		}
		
		// 2.发送命令到后台
		MsgSender.sendMsg(MsgType.DEPOSIT_POINT, NumberUtils.toLong(refid), userId);
		
		if(depositPointReg.getDepositBranch()!=null && depositPointReg.getDepositBranch().length() == 8){//只有发卡机构才有此操作,商户无
			// 3.扣减风险准备金和操作员配额
			this.dealCardRisk(depositPointReg, cardInfo);
		}
	}

	public Object getJobslip(String refid) {
		return this.depositPointRegDAO.findByPk(NumberUtils.toLong(refid));
	}

	public String getWorkflowId() {
		return WorkflowConstants.WORKFLOW_DEPOSIT_POINT;
	}

	public void postBackward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("积分充值审批不通过的相关处理....");
		DepositPointReg depositPointReg = (DepositPointReg) this.depositPointRegDAO.findByPkWithLock(NumberUtils.toLong(refid));
		Assert.notNull(depositPointReg, "找不到积分充值登记[" + refid + "]记录");
		
		// 将登记簿状态改为审核不通过
		depositPointReg.setStatus(RegisterState.FALURE.getValue());
		depositPointReg.setUpdateUser(userId);
		depositPointReg.setUpdateTime(new Date());
		
		this.depositPointRegDAO.update(depositPointReg);
		
		if (StringUtils.isEmpty(depositPointReg.getCardId())) {
			// 更新批量明细表中的记录
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("status", RegisterState.FALURE.getValue());
			params.put("depositBatchId", depositPointReg.getDepositBatchId());
			
			int count = this.depositPointBatRegDAO.updateStatusByDepositBatchId(params);
			
			logger.debug("批量更新的条数为[" + count + "]");
		}

	}

	public void postForward(String refid, Integer nodeId, String param,
			String userId) throws BizException {
		logger.debug("流程下发的相关处理。");
	}
	
	/**
	 * 扣减风险准备金和操作员的配额
	 * @param saleCardReg
	 * @param cardInfo
	 * @throws BizException
	 */
	private void dealCardRisk(DepositPointReg depositPointReg, CardInfo cardInfo) throws BizException {
		BigDecimal totalRisk = depositPointReg.getRefAmt(); // 积分充值扣的是积分折算金额。
		
		// 发卡机构自己不处理配额的情况
		if (!StringUtils.equals(StringUtils.trim(depositPointReg.getDepositBranch()), StringUtils.trim(cardInfo.getCardIssuer()))) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositPointReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(totalRisk);						// 清算金额
			branchSellReg.setCardBranch(depositPointReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositPointReg.getDepositBranch());	// 充值机构
			if (depositPointReg.getDepositBranch().startsWith("D")) { // 部门充值的话
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减充值申请人的操作员配额
		this.cardRiskService.deductUserAmt(depositPointReg.getEntryUserid(), depositPointReg.getDepositBranch(), totalRisk);
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositPointReg.getDepositBatchId());	// 积分充值登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(totalRisk); // 积分折算金额
		cardRiskReg.setBranchCode(depositPointReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}

}
