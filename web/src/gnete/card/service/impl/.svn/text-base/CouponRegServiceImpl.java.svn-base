package gnete.card.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponAwardRegDAO;
import gnete.card.dao.CouponBalDAO;
import gnete.card.dao.CouponBatRegDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.CouponRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponAwardReg;
import gnete.card.entity.CouponBal;
import gnete.card.entity.CouponBatReg;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.CouponReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.IssType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardRiskService;
import gnete.card.service.CouponRegService;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("CouponRegService")
public class CouponRegServiceImpl implements CouponRegService {
	
	@Autowired
	private CouponRegDAO couponRegDAO; 
	@Autowired
	private CouponBalDAO couponBalDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CouponBatRegDAO couponBatRegDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private CardRiskService cardRiskService;
	@Autowired
	private CouponAwardRegDAO couponAwardRegDAO;
	@Autowired
	protected WorkflowService workflowService;
	
	public boolean addCouponReg(CouponReg couponReg, String sessionUserCode)
	throws BizException {

		Assert.notNull(couponReg, "赠券登记对象不能为空");
		
		String cardId = couponReg.getCardId();
		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
		Assert.notNull(cardInfo, "不存在卡号[" + cardId + "]");
		
		
		couponReg.setUpdateTime(new Date());
		couponReg.setUpdateBy(sessionUserCode);
		return this.couponRegDAO.insert(couponReg) != null;
	}
	
	public boolean modifyCouponReg(CouponReg couponReg) throws BizException {
		Assert.notNull(couponReg, "更新的登记对象不能为空");
		int count = this.couponRegDAO.update(couponReg);
		return count>0;
	}
	
	public boolean deleteCouponReg(Long couponRegId) throws BizException {
		
		Assert.notNull(couponRegId, "删除的赠券登记ID不能为空");		

		int count = this.couponRegDAO.delete(couponRegId);
		
		return count > 0;
	}
	
//	public boolean updateCouponBal(BigDecimal backAmt, String acctId)
//	throws BizException {
//		Assert.notNull(backAmt, "传入的返利金额不能为空。");
//		Assert.notNull(acctId, "传入的卡号信息不能为空。");
//		
//		
//		CouponBal couponBal = (CouponBal)couponBalDAO.findByPk(acctId);
//		BigDecimal balance = couponBal.getBalance();
//		balance = balance.add(backAmt);
//		couponBal.setBalance(balance);
//		
//		return this.couponBalDAO.update(couponBal) > 0;
//	}

	public void setCouponRegDAO(CouponRegDAO couponRegDAO) {
		this.couponRegDAO = couponRegDAO;
	}

	public CouponRegDAO getCouponRegDAO() {
		return couponRegDAO;
	}

	public void setCouponBalDAO(CouponBalDAO couponBalDAO) {
		this.couponBalDAO = couponBalDAO;
	}

	public CouponBalDAO getCouponBalDAO() {
		return couponBalDAO;
	}

	public boolean addCouponBatReg(CouponBatReg couponBatReg, String sessionUser)
			throws BizException {
		Assert.notNull(couponBatReg, "批量派赠赠券卡登记对象不能为空");
		
		BigDecimal cardNum = couponBatReg.getCardNum();
		couponBatReg.setStatus(RegisterState.WAITEDEAL.getValue()); 
		couponBatReg.setUpdateTime(new Date());
		couponBatReg.setUpdateBy(sessionUser);
		
		// 通用赠券需要减去发卡机构的风险准备金
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(couponBatReg.getStartCard());
		Assert.notNull(cardInfo, "不存在卡号[" + couponBatReg.getStartCard() + "]");
		CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
		Assert.notNull(cardSubClassDef, "不存在卡子类型定义。");
		CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(cardSubClassDef.getCouponClass());
		
		if(IssType.CARD.getValue().equals(couponClassDef.getJinstType())){
			String cardssuer = couponClassDef.getJinstId();
			BigDecimal amount = cardNum.multiply(couponBatReg.getFaceValue());
			
			CardRiskReg cardRiskReg = new CardRiskReg();
			cardRiskReg.setId(couponBatReg.getCouponBatRegId());	
			cardRiskReg.setAdjType(AdjType.COUPON.getValue());
			cardRiskReg.setAmt(amount);
			cardRiskReg.setBranchCode(cardssuer);	
			cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
			this.cardRiskService.activateCardRisk(cardRiskReg);
		}
		
		if(this.couponBatRegDAO.insert(couponBatReg) != null){
			//登记成功后，组批量派赠赠券卡报文，发送后台，后台处理
			MsgSender.sendMsg(MsgType.COUPON_BAT_REG, couponBatReg.getCouponBatRegId(), sessionUser);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean deleteCouponBatReg(Long couponBatRegId) throws BizException {
		
		Assert.notNull(couponBatRegId, "删除的批量派赠赠券卡派赠登记ID不能为空");		
		int count = this.couponBatRegDAO.delete(couponBatRegId);
		return count > 0;
	}

	public boolean modifyCouponBatReg(CouponBatReg couponBatReg)
			throws BizException {
		Assert.notNull(couponBatReg, "更新的登记对象不能为空");
		couponBatReg.setUpdateTime(new Date());
		int count = this.couponBatRegDAO.update(couponBatReg);
		return count>0;
	}

	public String getEndNo(String startCard, String cardNum)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addCouponAwardReg(CouponAwardReg[] couponAwardRegList,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(couponAwardRegList, "添加的赠券赠送对象不能为空。");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		for(CouponAwardReg couponAwardReg : couponAwardRegList){
			Assert.notNull(couponAwardReg, "添加的赠券赠送对象不能为空。");
			
			params.clear();
			params.put("insId", couponAwardReg.getInsId());
			params.put("cardBin", couponAwardReg.getCardBin());
			params.put("couponClass", couponAwardReg.getCouponClass());

			Assert.isEmpty(this.couponAwardRegDAO.getCouponAwardRegList(params), 
								IssType.valueOf(couponAwardReg.getInsType()) + "[" + couponAwardReg.getInsId() + 
								"]已经设置了相关卡BIN和赠券的定义，不能重复设置。");
			
			couponAwardReg.setUpdateBy(sessionUser.getUserId());
			couponAwardReg.setUpdateTime(new Date());
			couponAwardReg.setStatus(RegisterState.WAITED.getValue());
			//couponAwardReg.setStatus(RegisterState.WAITEDEAL.getValue());
			
			this.couponAwardRegDAO.insert(couponAwardReg);

			//启动单个流程
			try {
				this.workflowService.startFlow(couponAwardReg, "couponAwardRegAdapter",
						Long.toString(couponAwardReg.getCouponAwardRegId()), sessionUser);
			} catch (Exception e) {
				throw new BizException(e.getMessage());
			}
		}
		
		return true;
	}
	
}
