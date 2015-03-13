package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.BranchSellAmtDAO;
import gnete.card.dao.BranchSellChgDAO;
import gnete.card.dao.BranchSellRegDAO;
import gnete.card.dao.CardRiskBalanceDAO;
import gnete.card.dao.CardRiskChgDAO;
import gnete.card.dao.CardRiskRegDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.UserSellAmtDAO;
import gnete.card.dao.UserSellChgDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchSellAmt;
import gnete.card.entity.BranchSellAmtKey;
import gnete.card.entity.BranchSellChg;
import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskBalance;
import gnete.card.entity.CardRiskChg;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserSellAmt;
import gnete.card.entity.UserSellAmtKey;
import gnete.card.entity.UserSellChg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SellType;
import gnete.card.service.CardRiskService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardRiskService")
public class CardRiskServiceImpl implements CardRiskService {
	
	@Autowired
	private CardRiskRegDAO cardRiskRegDAO;
	@Autowired
	private CardRiskBalanceDAO cardRiskBalanceDAO;
	@Autowired
	private CardRiskChgDAO cardRiskChgDAO;
	@Autowired
	private BranchSellRegDAO branchSellRegDAO;
	@Autowired
	private BranchSellAmtDAO branchSellAmtDAO;
	@Autowired
	private BranchSellChgDAO branchSellChgDAO;
	@Autowired
	private UserSellAmtDAO userSellAmtDAO;
	@Autowired
	private UserSellChgDAO userSellChgDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;

	public boolean addCardRiskReg(CardRiskReg cardRiskReg, String sessionUserCode) throws BizException {
		Assert.notNull(cardRiskReg, "添加的准备金调整申请不能为空");
//		Assert.notTrue(cardRiskReg.getAmt().doubleValue() <= 0.0, "申请的金额不能为0或小于0");
		
		String branchCode = cardRiskReg.getBranchCode();
		CardRiskBalance cardRiskBalance = (CardRiskBalance) this.cardRiskBalanceDAO.findByPk(branchCode);
		if (cardRiskBalance == null) {
			cardRiskBalance = new CardRiskBalance();
			cardRiskBalance.setBranchCode(branchCode);
			cardRiskBalance.setAvailableAmt(new BigDecimal(0.0));
			cardRiskBalance.setRiskAmt(new BigDecimal(0.0));
			cardRiskBalance.setSellAmt(new BigDecimal(0.0));
			cardRiskBalance.setSettleAmt(new BigDecimal(0.0));
			cardRiskBalance.setTrustAmt(new BigDecimal(0.0));
			this.cardRiskBalanceDAO.insert(cardRiskBalance);
		}
		
		BigDecimal orgAmt = null;
		//查询原金额
		if(cardRiskReg.getAdjType().equals(AdjType.APTITUDE.getValue())){ 
			orgAmt = cardRiskBalance.getTrustAmt();
		}
		else{
			orgAmt = cardRiskBalance.getRiskAmt();
		}
		cardRiskReg.setOrgAmt(orgAmt);
		
		cardRiskReg.setStatus(RegisterState.WAITED.getValue());
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		return this.cardRiskRegDAO.insert(cardRiskReg) != null;
	}

	public boolean modifyCardRiskReg(CardRiskReg cardRiskReg, String sessionUserCode) throws BizException {
		
		Assert.notNull(cardRiskReg, "更新的准备金调整申请对象不能为空");
		int count = this.cardRiskRegDAO.update(cardRiskReg);
		return count>0;
	}

	public boolean deleteCardRiskReg(Long id) throws BizException {
		
		Assert.notNull(id, "删除的准备金调整申请对象ID不能为空");		
		int count = this.cardRiskRegDAO.delete(id);
		return count > 0;
	}

	public boolean addSellAmtReg(BranchSellReg branchSellReg,
			String sessionUserCode) throws BizException {
		Assert.notNull(branchSellReg, "配额调整申请不能为空");
		Assert.notTrue(branchSellReg.getAmt().doubleValue() <= 0.0, "申请的金额不能为0或小于0");
		
		BranchSellAmt branchSellAmt = (BranchSellAmt) this.branchSellAmtDAO.findByPk(
				new BranchSellAmtKey(branchSellReg.getCardBranch(), branchSellReg.getSellBranch()));
		// 如果没有该机构配额数据则初始化
		if (branchSellAmt == null) {
			branchSellAmt = new BranchSellAmt();
			branchSellAmt.setCardBranch(branchSellReg.getCardBranch());
			branchSellAmt.setSellBranch(branchSellReg.getSellBranch());
			branchSellAmt.setSellType(branchSellReg.getSellType());
			branchSellAmt.setAvailableAmt(new BigDecimal(0.0));
			branchSellAmt.setRiskAmt(new BigDecimal(0.0));
			branchSellAmt.setTrustAmt(new BigDecimal(0.0));
			branchSellAmt.setUnsettleAmt(new BigDecimal(0.0));
			this.branchSellAmtDAO.insert(branchSellAmt);
		}
		
		// 资质额度调整
		if (AdjType.APTITUDE.getValue().equals(branchSellReg.getAdjType())){
			branchSellReg.setOrgAmt(branchSellAmt.getTrustAmt());
		}
		// 存入保证金&保证金提现
		else {
			branchSellReg.setOrgAmt(branchSellAmt.getRiskAmt());
		}
		branchSellReg.setStatus(RegisterState.WAITED.getValue());
		branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
		return this.branchSellRegDAO.insert(branchSellReg) != null;
	}
	
	public boolean setUserSell(UserSellChg userSellChg, String sessionUserCode) throws BizException {
		Assert.notNull(userSellChg, "操作员配额申请不能为空");
		boolean isExist = false;
		
		UserSellAmt userSellAmt = (UserSellAmt) this.userSellAmtDAO.findByPk(
				new UserSellAmtKey(userSellChg.getBranchCode(), userSellChg.getUserId()));
		
		userSellChg.setUpdateTime(new Date());
		userSellChg.setUpdateUser(sessionUserCode);
		if (userSellAmt != null) {
			userSellChg.setOrgAmt(userSellAmt.getAmt());
			userSellChg.setOrgAvailableAmt(userSellAmt.getAvailableAmt());
			isExist = true;
		} else {
			userSellChg.setOrgAmt(new BigDecimal(0.0));
			userSellChg.setOrgAvailableAmt(new BigDecimal(0.0));
			
			userSellAmt = new UserSellAmt();
			userSellAmt.setUserId(userSellChg.getUserId());
			userSellAmt.setBranchCode(userSellChg.getBranchCode());
		}
		userSellAmt.setAmt(userSellChg.getAmt());
		userSellAmt.setUsedAmt(new BigDecimal(0.0));
		userSellAmt.setAvailableAmt(userSellChg.getAmt());
		
		this.userSellChgDAO.insert(userSellChg);
		if (!isExist) {
			return this.userSellAmtDAO.insert(userSellAmt) != null;
		}
		return this.userSellAmtDAO.update(userSellAmt) > 0;
	}
	
	public void deductUserAmt(String userId, String branchCode , BigDecimal amt) throws BizException {
		Assert.notEmpty(userId, "修改配额时用户编号不能为空");
		Assert.notEmpty(branchCode, "修改配额时机构编号不能为空");
		Assert.notNull(amt, "修改配额时配额不能为空");
		
		UserSellAmt userSellAmt = (UserSellAmt) this.userSellAmtDAO.findByPk(
				new UserSellAmtKey(branchCode, userId));
		
		// 没有配额或配额为0则是无限制的操作员，不修改
		if(userSellAmt == null || userSellAmt.getAmt().doubleValue() == 0.0) {
			return;
		}
		if (amt.doubleValue() > userSellAmt.getAvailableAmt().doubleValue()) {
			throw new BizException("用户[" + userId + "]配额不足");
		}
		
		userSellAmt.setUsedAmt(AmountUtil.add(userSellAmt.getUsedAmt(), amt));
		userSellAmt.setAvailableAmt(AmountUtil.subtract(userSellAmt.getAmt(), userSellAmt.getUsedAmt()));
		this.userSellAmtDAO.update(userSellAmt);
	}
	
	public void activateSell(BranchSellReg branchSellReg) throws BizException {
		Assert.notNull(branchSellReg, "配额登记对象不能为空");
		
		if (AdjType.APTITUDE.getValue().equals(branchSellReg.getAdjType())
				|| AdjType.DEPOSIT.getValue().equals(branchSellReg.getAdjType())
				|| AdjType.DRAW.getValue().equals(branchSellReg.getAdjType())) {
			branchSellReg.setStatus(RegisterState.PASSED.getValue());
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			this.branchSellRegDAO.update(branchSellReg);
		}
		
		BranchSellAmt branchSellAmt = (BranchSellAmt) this.branchSellAmtDAO.findByPkWithLock(
				new BranchSellAmtKey(branchSellReg.getCardBranch(), branchSellReg.getSellBranch()));
		Assert.notNull(branchSellAmt, "没有该机构的配额数据");
		
		// 定义变动记录
		BranchSellChg branchSellChg = new BranchSellChg();
		branchSellChg.setCardBranch(branchSellReg.getCardBranch());
		branchSellChg.setSellBranch(branchSellReg.getSellBranch());
		branchSellChg.setSellType(branchSellReg.getSellType());
		branchSellChg.setAdjType(branchSellReg.getAdjType());
		branchSellChg.setAmt(branchSellReg.getAmt());
		branchSellChg.setRefid(String.valueOf(branchSellReg.getId()));
		branchSellChg.setChangeDate(new Date());
		
		// 资质额度调整
		if (AdjType.APTITUDE.getValue().equals(branchSellReg.getAdjType())){
			branchSellAmt.setTrustAmt(branchSellReg.getAmt());
		}
		// 存入保证金
		else if (AdjType.DEPOSIT.getValue().equals(branchSellReg.getAdjType())){
			branchSellAmt.setRiskAmt(AmountUtil.add(branchSellAmt.getRiskAmt(), branchSellReg.getAmt()));
		}
		// 保证金提现
		else if (AdjType.DRAW.getValue().equals(branchSellReg.getAdjType())){
			// 计算可提现额度
			BigDecimal cashAmt = null;
			BigDecimal available = AmountUtil.subtract(branchSellAmt.getAvailableAmt(), branchSellAmt.getUnsettleAmt());
			cashAmt = AmountUtil.min(available, branchSellAmt.getRiskAmt());
			Assert.notTrue(branchSellReg.getAmt().doubleValue() > cashAmt.doubleValue(), "提现失败，可提现额度不足");
			
			branchSellAmt.setRiskAmt(AmountUtil.subtract(branchSellAmt.getRiskAmt(), branchSellReg.getAmt()));
		}
		// 售卡
		else if (AdjType.SELL.getValue().equals(branchSellReg.getAdjType())){
			if (branchSellReg.getAmt().doubleValue() > 0.0) {
				Assert.notTrue(branchSellReg.getAmt().doubleValue() > branchSellAmt.getAvailableAmt().doubleValue(), 
						"该售卡机构["+ branchSellReg.getSellBranch() +"]对应发卡机构["+ branchSellReg.getCardBranch() +"]的充值售卡额度不足");
			}
			branchSellAmt.setUnsettleAmt(AmountUtil.add(branchSellAmt.getUnsettleAmt(), branchSellReg.getAmt()));
		}
		// 管理端充值
		else if (AdjType.MANAGE.getValue().equals(branchSellReg.getAdjType())){
			if (branchSellReg.getAmt().doubleValue() > 0.0) {
				Assert.notTrue(branchSellReg.getAmt().doubleValue() > branchSellAmt.getAvailableAmt().doubleValue(), 
						"该售卡机构["+ branchSellReg.getSellBranch() +"]对应发卡机构["+ branchSellReg.getCardBranch() +"]的充值售卡额度不足");
			}
			branchSellAmt.setUnsettleAmt(AmountUtil.add(branchSellAmt.getUnsettleAmt(), branchSellReg.getAmt()));
		}
		
		// 当前可用配额＝信任额度+当前保证金－未清算资金
		branchSellAmt.setAvailableAmt(AmountUtil.subtract(
				AmountUtil.add(branchSellAmt.getTrustAmt(), branchSellAmt.getRiskAmt()), branchSellAmt.getUnsettleAmt()));
		
		this.branchSellAmtDAO.update(branchSellAmt);
		
		branchSellChg.setAvailableAmt(branchSellAmt.getAvailableAmt());
		this.branchSellChgDAO.insert(branchSellChg);
	}
	
	public void activateCardRisk(CardRiskReg cardRiskReg) throws BizException {
		Assert.notNull(cardRiskReg, "风险保证金登记对象不能为空");
		
		if (AdjType.APTITUDE.getValue().equals(cardRiskReg.getAdjType())
				|| AdjType.DEPOSIT.getValue().equals(cardRiskReg.getAdjType())
				|| AdjType.DRAW.getValue().equals(cardRiskReg.getAdjType())) {
			cardRiskReg.setStatus(RegisterState.PASSED.getValue());
			cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
			this.cardRiskRegDAO.update(cardRiskReg);
		}
		
		CardRiskBalance cardRiskBalance = (CardRiskBalance) this.cardRiskBalanceDAO.findByPkWithLock(cardRiskReg.getBranchCode());
		Assert.notNull(cardRiskBalance, "没有该机构[" + cardRiskReg.getBranchCode() + "]的风险准备金记录");
		
		// 定义变动记录
		CardRiskChg cardRiskChg = new CardRiskChg();
		cardRiskChg.setAdjType(cardRiskReg.getAdjType());
		cardRiskChg.setBranchCode(cardRiskReg.getBranchCode());
		cardRiskChg.setAmt(cardRiskReg.getAmt());
		cardRiskChg.setRefid(String.valueOf(cardRiskReg.getId()));
		cardRiskChg.setChangeDate(new Date());
		
		// 计算可用余额
		// 资质额度调整
		if (AdjType.APTITUDE.getValue().equals(cardRiskReg.getAdjType())){
			cardRiskBalance.setTrustAmt(cardRiskReg.getAmt());
		}
		// 存入保证金
		else if (AdjType.DEPOSIT.getValue().equals(cardRiskReg.getAdjType())){
			cardRiskBalance.setRiskAmt(AmountUtil.add(cardRiskBalance.getRiskAmt(), cardRiskReg.getAmt()));
		}
		// 保证金提现
		else if (AdjType.DRAW.getValue().equals(cardRiskReg.getAdjType())){
			if (cardRiskReg.getAmt().doubleValue() > 0.0) {
				Assert.notTrue(cardRiskReg.getAmt().doubleValue() > cardRiskBalance.getAvailableAmt().doubleValue(), 
						"该发卡机构["+ cardRiskReg.getBranchCode() +"]的风险准备金额度不足");
			}
			cardRiskBalance.setRiskAmt(AmountUtil.subtract(cardRiskBalance.getRiskAmt(), cardRiskReg.getAmt()));
		}
		// 售卡、管理端充值、派赠通用赠券
		else if (AdjType.SELL.getValue().equals(cardRiskReg.getAdjType()) 
				|| AdjType.MANAGE.getValue().equals(cardRiskReg.getAdjType())
				|| AdjType.COUPON.getValue().equals(cardRiskReg.getAdjType()) ){
			
			if (cardRiskReg.getAmt().doubleValue() > 0.0){
				Assert.notTrue(cardRiskReg.getAmt().doubleValue() > cardRiskBalance.getAvailableAmt().doubleValue(), 
						"该发卡机构["+ cardRiskReg.getBranchCode() +"]的风险准备金额度不足");
			}
			cardRiskBalance.setSellAmt(AmountUtil.add(cardRiskBalance.getSellAmt(), cardRiskReg.getAmt()));
		}
		
		// 可用售卡充值金额＝资质信任额度＋风险保证金－售卡充值总金额＋已清算本金总金额
		cardRiskBalance.setAvailableAmt(AmountUtil.add(AmountUtil.subtract(
				AmountUtil.add(cardRiskBalance.getTrustAmt(), cardRiskBalance.getRiskAmt()), 
					cardRiskBalance.getSellAmt()), cardRiskBalance.getSettleAmt()));
		
		this.cardRiskBalanceDAO.update(cardRiskBalance);
		
		cardRiskChg.setAvailableAmt(cardRiskBalance.getAvailableAmt());
		this.cardRiskChgDAO.insert(cardRiskChg);
	}
	
	public void dealCardRiskForSellCard(CardInfo cardInfo, SaleCardReg saleCardReg, 
			BigDecimal riskAmt, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		// 次卡需要读取清算金额
		if (DepositType.TIMES.getValue().equals(saleCardReg.getDepositType())) {
			String cardSubClass = cardInfo.getCardSubclass();
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			String freqClass = cardSubClassDef.getFrequencyClass();
			Assert.notEmpty(freqClass, "次数卡无子类型定义[" + freqClass + "]");
			
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(freqClass);
			if (accuClassDef.getSettAmt() == null || accuClassDef.getSettAmt().doubleValue() == 0.0) {
				riskAmt = new BigDecimal(0.0);
			} else {
				riskAmt = AmountUtil.multiply(accuClassDef.getSettAmt(), saleCardReg.getAmt());
			}
		}
		
		// 处理风险准备金和售卡配额
		// 发卡机构自己不处理配额的情况
		if (!RoleType.CARD.getValue().equals(roleType)) {
			BranchSellReg branchSellReg = new BranchSellReg(); 
			branchSellReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
			branchSellReg.setAdjType(AdjType.SELL.getValue());
			branchSellReg.setAmt(riskAmt); // 相关金额应该为清算金额
			branchSellReg.setCardBranch(cardInfo.getCardIssuer().trim());	// 发卡机构
			branchSellReg.setEffectiveDate(DateUtil.getCurrentDate());
			branchSellReg.setSellBranch(saleCardReg.getBranchCode());
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				branchSellReg.setSellType(SellType.DEPT.getValue());
			} else {
				branchSellReg.setSellType(SellType.BRANCH.getValue());
			}
			this.activateSell(branchSellReg);
		}
		// 扣减操作员额度
		this.deductUserAmt(user.getUserId(), saleCardReg.getBranchCode(), riskAmt);
		
		CardRiskReg cardRiskReg = new CardRiskReg();
		cardRiskReg.setId(saleCardReg.getSaleBatchId());	// 售卡登记薄的ID
		cardRiskReg.setAdjType(AdjType.SELL.getValue());
		cardRiskReg.setAmt(riskAmt);
		cardRiskReg.setBranchCode(cardInfo.getCardIssuer().trim());	// 发卡机构
		cardRiskReg.setEffectiveDate(DateUtil.getCurrentDate());
		this.activateCardRisk(cardRiskReg);
	}
	
}
