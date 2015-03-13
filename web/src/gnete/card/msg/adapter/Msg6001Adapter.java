package gnete.card.msg.adapter;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.DepositCouponBatRegDAO;
import gnete.card.dao.DepositCouponRegDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.DepositCouponBatReg;
import gnete.card.entity.DepositCouponReg;
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
 * 赠券赠送命令后台返回处理
 * @Project: CardWeb
 * @File: Msg6001Adapter.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-3-26上午10:12:01
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
@Repository
public class Msg6001Adapter implements MsgAdapter {
	
	@Autowired
	private DepositCouponRegDAO depositCouponRegDAO;
	@Autowired
	private DepositCouponBatRegDAO depositCouponBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public void deal(Long id, boolean isSuccess) throws BizException {
		DepositCouponReg depositCouponReg = (DepositCouponReg) this.depositCouponRegDAO.findByPk(id);
		Assert.notNull(depositCouponReg, "找不到赠券赠送登记[" + id + "]记录");
		
		//卡号不为空是单笔赠券赠送
		if (StringUtils.isNotEmpty(depositCouponReg.getCardId())) {
			// 成功的话
			if (isSuccess) {
				logger.debug("单笔赠券赠送成功的处理....");
				depositCouponReg.setStatus(RegisterState.NORMAL.getValue());
			} else {
				logger.debug("单笔赠券赠送失败的处理....");
				depositCouponReg.setStatus(RegisterState.DISABLE.getValue());
				String remark = StringUtils.isEmpty(depositCouponReg.getRemark()) ? "" : depositCouponReg.getRemark();
				depositCouponReg.setRemark(remark + getNote(id));
				
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(depositCouponReg.getCardId());
				Assert.notNull(cardInfo, "卡号[" + depositCouponReg.getCardId() + "]不存在");
				
				// 把扣掉的风险准备金补回来
				this.dealCardRisk(depositCouponReg, cardInfo);
			}
		}
		// 否则是批量赠券赠送
		else {
			// 成功的话
			if (isSuccess) {
				logger.debug("批量赠券赠送成功的处理....");
				depositCouponReg.setStatus(RegisterState.NORMAL.getValue());
				
				// 更新批量明细表中的状态
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("status", RegisterState.NORMAL.getValue());
				params.put("depositBatchId", id);
				
				int count = this.depositCouponBatRegDAO.updateStatusByDepositBatchId(params);
				
				logger.debug("批量更新的条数为[" + count + "]");
			} else {
				logger.debug("批量赠券赠送失败的处理....");
				depositCouponReg.setStatus(RegisterState.DISABLE.getValue());
				String remark = StringUtils.isEmpty(depositCouponReg.getRemark()) ? "" : depositCouponReg.getRemark();
				depositCouponReg.setRemark(remark + getNote(id));
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("depositBatchId", id);
				List<DepositCouponBatReg> batList = this.depositCouponBatRegDAO.findDepositCouponBatList(map);
				
				Assert.notEmpty(batList, "批次号为[" + id + "]的批量充值明细记录不存在");
				
				// 任取一张卡
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(batList.get(0).getCardId());
				Assert.notNull(cardInfo, "卡号[" + batList.get(0).getCardId() + "]不存在");
				
				if(depositCouponReg.getDepositBranch()!=null && depositCouponReg.getDepositBranch().length() == 8){//只有发卡机构才有此操作,商户无
					// 把扣掉的风险准备金补回来
					this.dealCardRisk(depositCouponReg, cardInfo);
				}
				
				// 更新批量明细表中的状态
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("status", RegisterState.DISABLE.getValue());
				params.put("depositBatchId", id);
				
				int count = this.depositCouponBatRegDAO.updateStatusByDepositBatchId(params);
				
				logger.debug("批量更新的条数为[" + count + "]");
			}
			
		}
		logger.debug("处理完毕，更新登记簿的状态....");
		// 更新登记簿的状态
		this.depositCouponRegDAO.update(depositCouponReg);
	}
	
	/**
	 * 把扣掉的风险准备金补回来
	 * @param saleCardReg
	 * @param cardInfo
	 * @throws BizException
	 */
	private void dealCardRisk(DepositCouponReg depositCouponReg, CardInfo cardInfo) throws BizException {
		BigDecimal totalRisk = depositCouponReg.getRefAmt(); // 赠券赠送扣的是积分折算金额。
		
		// 发卡机构自己不处理配额的情况
		if (!StringUtils.equals(StringUtils.trim(depositCouponReg.getDepositBranch()), StringUtils.trim(cardInfo.getCardIssuer()))) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(depositCouponReg.getDepositBatchId());	// 充值登记薄的ID
			branchSellReg.setAdjType(AdjType.MANAGE.getValue());
			branchSellReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), totalRisk));		// 清算金额
			branchSellReg.setCardBranch(depositCouponReg.getCardBranch());		// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(depositCouponReg.getDepositBranch());	// 充值机构
			if (depositCouponReg.getDepositBranch().startsWith("D")) { // 部门充值的话
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.cardRiskService.activateSell(branchSellReg);
		}
		// 扣减充值申请人的操作员配额
		this.cardRiskService.deductUserAmt(depositCouponReg.getEntryUserid(), depositCouponReg.getDepositBranch(), 
				AmountUtil.subtract(new BigDecimal(0.0), totalRisk));
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(depositCouponReg.getDepositBatchId());	// 赠券赠送登记薄的ID
		cardRiskReg.setAdjType(AdjType.MANAGE.getValue());
		cardRiskReg.setAmt(AmountUtil.subtract(new BigDecimal(0.0), totalRisk)); // 积分折算金额
		cardRiskReg.setBranchCode(depositCouponReg.getCardBranch());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.formatDate("yyyyMMdd"));
		
		this.cardRiskService.activateCardRisk(cardRiskReg);
	}
	
	private String getNote(Long id) throws BizException {
		Waitsinfo waitsinfo = this.waitsinfoDAO.findWaitsinfo(MsgType.DEPOSIT_COUPON, id);
		return waitsinfo == null ? "" : ("{" + waitsinfo.getNote() + "}");
	}
}
