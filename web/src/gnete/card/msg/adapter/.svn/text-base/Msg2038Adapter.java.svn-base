package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.DepositPointBatRegDAO;
import gnete.card.dao.DepositPointRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.DepositPointBatReg;
import gnete.card.entity.DepositPointReg;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.SellType;
import gnete.card.msg.MsgAdapter;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @File: Msg2038Adapter.java
 *
 * @description: 积分充值命令后台返回处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-26 下午03:10:44
 */
@Repository
public class Msg2038Adapter implements MsgAdapter {
	
	@Autowired
	private DepositPointRegDAO depositPointRegDAO;
	@Autowired
	private DepositPointBatRegDAO depositPointBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		DepositPointReg depositPointReg = (DepositPointReg) this.depositPointRegDAO.findByPk(id);
		Assert.notNull(depositPointReg, "找不到积分充值登记[" + id + "]记录");
		
		//卡号不为空是单笔积分充值
		if (StringUtils.isNotEmpty(depositPointReg.getCardId())) {
			// 成功的话
			if (isSuccess) {
				logger.debug("单笔积分充值成功的处理....");
				depositPointReg.setStatus(RegisterState.NORMAL.getValue());
			} else {
				logger.debug("单笔积分充值失败的处理....");
				depositPointReg.setStatus(RegisterState.DISABLE.getValue());
				String remark = StringUtils.isEmpty(depositPointReg.getRemark()) ? "" : depositPointReg.getRemark();
				depositPointReg.setRemark(remark + getNote(id));
				
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositPointReg.getCardId());
				Assert.notNull(cardInfo, "卡号[" + depositPointReg.getCardId() + "]不存在");
				
				// 把扣掉的风险准备金补回来
				this.dealCardRisk(depositPointReg, cardInfo);
			}
		}
		// 否则是批量积分充值
		else {
			// 成功的话
			if (isSuccess) {
				logger.debug("批量积分充值成功的处理....");
				depositPointReg.setStatus(RegisterState.NORMAL.getValue());
				
				// 更新批量明细表中的状态
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("status", RegisterState.NORMAL.getValue());
				params.put("depositBatchId", id);
				
				int count = this.depositPointBatRegDAO.updateStatusByDepositBatchId(params);
				
				logger.debug("批量更新的条数为[" + count + "]");
			} else {
				logger.debug("批量积分充值失败的处理....");
				depositPointReg.setStatus(RegisterState.DISABLE.getValue());
				String remark = StringUtils.isEmpty(depositPointReg.getRemark()) ? "" : depositPointReg.getRemark();
				depositPointReg.setRemark(remark + getNote(id));
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("depositBatchId", id);
				List<DepositPointBatReg> batList = this.depositPointBatRegDAO.findDepositPointBatList(map);
				
				Assert.notEmpty(batList, "批次号为[" + id + "]的批量充值明细记录不存在");
				
				// 任取一张卡
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(batList.get(0).getCardId());
				Assert.notNull(cardInfo, "卡号[" + batList.get(0).getCardId() + "]不存在");
				
				// 把扣掉的风险准备金补回来
				this.dealCardRisk(depositPointReg, cardInfo);
				
				// 更新批量明细表中的状态
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("status", RegisterState.DISABLE.getValue());
				params.put("depositBatchId", id);
				
				int count = this.depositPointBatRegDAO.updateStatusByDepositBatchId(params);
				
				logger.debug("批量更新的条数为[" + count + "]");
			}
			
		}
		logger.debug("处理完毕，更新登记簿的状态....");
		// 更新登记簿的状态
		this.depositPointRegDAO.update(depositPointReg);
	}
	
	/**
	 * 把扣掉的风险准备金补回来
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
			branchSellReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), totalRisk));		// 清算金额
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
		this.cardRiskService.deductUserAmt(depositPointReg.getEntryUserid(), depositPointReg.getDepositBranch(), 
				AmountUtil.subtract(new BigDecimal(0.0), totalRisk));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositPointReg.getDepositBatchId());	// 积分充值登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), totalRisk)); // 积分折算金额
		cardRiskReg.setBranchCode(depositPointReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.DEPOSIT_POINT, id);
		return waitsinfo == null ? "" : ("{" + waitsinfo.getNote() + "}");
	}
}
