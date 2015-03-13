package gnete.card.service.impl;

import flink.util.DateUtil;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CustomerRebateRegDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.dao.SaleSignCardRegDAO;
import gnete.card.dao.SignCustDAO;
import gnete.card.dao.SignRuleRegDAO;
import gnete.card.entity.CardCustomer;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardCustomerState;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.state.CustomerRebateState;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.DerateType;
import gnete.card.entity.type.RoleType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.SaleCardRuleService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("saleCardRuleService")
public class SaleCardRuleServiceImpl implements SaleCardRuleService {

	@Autowired
	private CardCustomerDAO cardCustomerDAO;
	@Autowired
	private CustomerRebateRegDAO customerRebateRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private SignRuleRegDAO signRuleRegDAO;
	@Autowired
	private SaleSignCardRegDAO saleSignCardRegDAO;
	
	public boolean addCardCustomer(CardCustomer cardCustomer,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(cardCustomer, "添加的购卡客户对象不能为空");
		Assert.notEmpty(cardCustomer.getCardCustomerName(), "购卡客户名称不能为空。");
		Assert.notEmpty(cardCustomer.getCardBranch(), "发卡机构不能为空");

//		cardCustomer.setCardBranch(sessionUser.getBranchNo());
		cardCustomer.setInputBranch(sessionUser.getBranchNo());
		cardCustomer.setUpdateTime(new Date());
		cardCustomer.setUpdateUser(sessionUser.getUserId());
		cardCustomer.setStatus(CardCustomerState.NORMAL.getValue());

		// 购卡客户所属的发卡机构
		// cardCustomer.setCardBranch(sessionUser.getBranchNo());

		// long customerId = Long.valueOf(customerIDStr);
		// cardCustomer.setCardCustomerName(cardCustomerName)CustomerId(customerId);

		this.cardCustomerDAO.insert(cardCustomer);

		return cardCustomer.getCardCustomerId() != null;
	}

	public boolean modifyCardCustomer(CardCustomer cardCustomer,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(cardCustomer, "更新的购卡客户对象不能为空");

		CardCustomer newcustomer = (CardCustomer) cardCustomerDAO.findByPk(cardCustomer.getCardCustomerId());
		Assert.notNull(newcustomer, "该购卡客户已经不存在。");
		Assert.notTrue(CardCustomerState.USED.getValue().equals(newcustomer.getStatus()), "该购卡客户已启用");

		cardCustomer.setUpdateTime(new Date());
		cardCustomer.setUpdateUser(sessionUser.getUserId());
		cardCustomer.setStatus(newcustomer.getStatus());
//		cardCustomer.setCardBranch(newcustomer.getCardBranch());
		cardCustomer.setIsCommon(newcustomer.getIsCommon());
		cardCustomer.setInputBranch(newcustomer.getInputBranch());

		return cardCustomerDAO.update(cardCustomer) > 0;
	}

	public boolean deleteCardCustomer(long cardCustomerId) throws BizException {
		Assert.notNull(cardCustomerId, "删除的购卡客户ID不能为空");

		CardCustomer customer = (CardCustomer) cardCustomerDAO.findByPk(cardCustomerId);
		Assert.notNull(customer, "找不到要删除的购卡客户信息");
		Assert.notTrue(CardCustomerState.USED.getValue().equals(customer.getStatus()), "该购卡客户已启用，禁止删除。");
		
		return cardCustomerDAO.delete(cardCustomerId) > 0;
	}

	public boolean isCorrectRebateCard(CardCustomer cardCustomer, UserInfo sessionUser)
			throws BizException {
		boolean isCorrectRebateCard = false;
		String roleType = sessionUser.getRole().getRoleType();
		
		if (!(RoleType.CARD.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType))) {
			return false;
		}
		
		if (StringUtils.isEmpty(cardCustomer.getRebateCard())
				|| StringUtils.isEmpty(cardCustomer.getCardBranch())) {
			return false;
		}
		
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardCustomer.getRebateCard());
		if (cardInfo == null) {
			return false;
		}
		
		// 符合条件的卡为：属于选择的发卡机构，卡状态为激活（10）或已领卡待售（04）
		if (StringUtils.equals(cardInfo.getCardStatus(), CardState.FORSALE.getValue())
				|| StringUtils.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue())
				&& StringUtils.equals(cardInfo.getCardIssuer(), cardCustomer.getCardBranch())) {
			isCorrectRebateCard = true;
		}
		
		return isCorrectRebateCard;
	}

	public boolean addRebateRule(RebateRule rebateRule,
			RebateRuleDetail[] rebateRuleDetailArray, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(rebateRule, "添加的返利规则对象不能为空");
		Assert.notNull(rebateRuleDetailArray, "添加的返利规则明细对象不能为空");

		rebateRule.setUpdateTime(new Date());
		rebateRule.setUpdateUser(sessionUser.getUserId());
		// rebateRule.setCardBranch(sessionUser.getBranchNo());// 发卡机构
		rebateRule.setStatus(RebateRuleState.NORMAL.getValue());
		rebateRule.setInputBranch(sessionUser.getBranchNo());

		this.rebateRuleDAO.insert(rebateRule);

		for (short i = 0; i < rebateRuleDetailArray.length; i++) {
			rebateRuleDetailArray[i].setRebateId(rebateRule.getRebateId());
			this.rebateRuleDetailDAO.insert(rebateRuleDetailArray[i]);
		}

		return rebateRule.getRebateId() != null;
	}

	public boolean modifyRebateRule(RebateRule rebateRule,
			RebateRuleDetail[] rebateRuleDetailArray, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(rebateRule, "更新的返利规则对象不能为空");
		Assert.notNull(rebateRuleDetailArray, "更新的返利规则明细对象不能为空");

		RebateRule oldRebateRule = (RebateRule) rebateRuleDAO.findByPk(rebateRule.getRebateId());
		Assert.notNull(oldRebateRule, "要修改的返利规则已经不存在。");

		rebateRule.setCardBranch(oldRebateRule.getCardBranch());
		rebateRule.setIsCommon(oldRebateRule.getIsCommon());
		rebateRule.setInputBranch(oldRebateRule.getInputBranch());
		rebateRule.setUpdateTime(new Date());
		rebateRule.setUpdateUser(sessionUser.getUserId());
		rebateRule.setStatus(oldRebateRule.getStatus());

		// 删除返利规则原分段比例明细
		this.deleteRebateRuleDetail(rebateRule.getRebateId());
		// 插入返利规则新分段比例明细
		for (short i = 0; i < rebateRuleDetailArray.length; i++) {
			rebateRuleDetailArray[i].setRebateId(rebateRule.getRebateId());
			this.rebateRuleDetailDAO.insert(rebateRuleDetailArray[i]);
		}

		return rebateRuleDAO.update(rebateRule) > 0;
	}

	public boolean deleteRebateRule(long rebateId) throws BizException {
		Assert.notNull(rebateId, "删除的返利规则ID不能为空");
		
		int count = this.rebateRuleDAO.delete(rebateId);
		count += this.rebateRuleDetailDAO.deleteByRebateId(rebateId);

		return count > 0;
	}

	/**
	 * 删除返利规则的分段比例
	 * 
	 * @param long
	 * @return
	 * @throws BizException
	 */
	private boolean deleteRebateRuleDetail(long rebateId) throws BizException {
		Assert.notNull(rebateId, "删除的返利规则ID不能为空");
		int count = this.rebateRuleDetailDAO.deleteByRebateId(rebateId);

		return count > 0;
	}

	public CustomerRebateReg addCustomerRebateReg(
			CustomerRebateReg customerRebateReg, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(customerRebateReg, "添加的客户返利对象不能为空");

		customerRebateReg.setStatus(CustomerRebateState.WAITED.getValue());
		customerRebateReg.setUpdateTime(new Date());
		customerRebateReg.setUpdateUser(sessionUser.getUserId());
		customerRebateReg.setInputBranch(sessionUser.getBranchNo());
//		customerRebateReg.setCardBranch(sessionUser.getBranchNo());

		customerRebateRegDAO.insert(customerRebateReg);
		return customerRebateReg;
	}
	
	public void stopCustomerRebateReg(String customerRebateRegId, UserInfo user)
			throws BizException {
		Assert.notEmpty(customerRebateRegId, "客户返利登记ID不能为空");
		CustomerRebateReg reg = this.lockCustomerRebateReg(customerRebateRegId);
		
		Assert.notNull(reg, "客户返利登记ID为[" + customerRebateRegId + "]的记录已经不存在");
		reg.setStatus(CustomerRebateState.DISABLE.getValue());
		reg.setUpdateTime(new Date());
		reg.setUpdateUser(user.getUserId());
		
		this.releaseCustomerRebateReg(reg);
	}

	public void startCustomerRebateReg(String customerRebateRegId, UserInfo user)
			throws BizException {
		Assert.notEmpty(customerRebateRegId, "客户返利登记ID不能为空");
		CustomerRebateReg reg = this.lockCustomerRebateReg(customerRebateRegId);
		
		Assert.notNull(reg, "客户返利登记ID为[" + customerRebateRegId + "]的记录已经不存在");
		
		reg.setStatus(CustomerRebateState.PASSED.getValue());
		reg.setUpdateTime(new Date());
		reg.setUpdateUser(user.getUserId());
		
		this.releaseCustomerRebateReg(reg);
		
	}
	
	private CustomerRebateReg lockCustomerRebateReg(
			String customerRebateRegId) throws BizException{
		CustomerRebateReg rebateReg = new CustomerRebateReg();
		try {
			rebateReg = (CustomerRebateReg) this.customerRebateRegDAO.findByPkWithLock(NumberUtils.toLong(customerRebateRegId));
		} catch (Exception e) {
			throw new BizException("锁定客户返利登记ID为[" + customerRebateRegId + "]的记录失败");
		}
		return rebateReg;
	}
	
	private void releaseCustomerRebateReg(
			CustomerRebateReg customerRebateReg) throws BizException{
		try {
			this.customerRebateRegDAO.update(customerRebateReg);
		} catch (Exception e) {
			throw new BizException("更新客户返利登记簿，释放锁失败！");
		}
	}
	
	@Deprecated
	public boolean modifyCustomerRebateReg(CustomerRebateReg customerRebateReg,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(customerRebateReg, "编辑的客户返利对象不能为空");

		customerRebateReg.setStatus(RegisterState.WAITED.getValue());
		customerRebateReg.setUpdateTime(new Date());
		customerRebateReg.setUpdateUser(sessionUser.getUserId());
		customerRebateReg.setCardBranch(sessionUser.getBranchNo());

		return this.customerRebateRegDAO.update(customerRebateReg) > 0;
	}

	public long addSignCust(SignCust signCust, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(signCust, "添加的签单卡客户对象不能为空");
		Assert.notEmpty(signCust.getBillDate(), "账单生成日期不能为空。");

		signCust.setBillDate(StringUtils.leftPad(signCust.getBillDate(), 2, "0"));
		signCust.setCardBranch(sessionUser.getBranchNo());
		signCust.setUpdateTime(new Date());
		signCust.setUpdateUser(sessionUser.getUserId());
		signCust.setStatus(CommonState.NORMAL.getValue());

		this.signCustDAO.insert(signCust);
		return signCust.getSignCustId();
	}

	public boolean modifySignCust(SignCust signCust, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(signCust, "更新的签单卡客户对象不能为空");
		Assert.notEmpty(signCust.getBillDate(), "账单生成日期不能为空。");

		signCust.setBillDate(StringUtils.leftPad(signCust.getBillDate(), 2, "0"));
		SignCust cust = (SignCust) signCustDAO.findByPk(signCust.getSignCustId());
		Assert.notNull(cust, "要修改的签单客户对象已经不存在。");

		signCust.setCardBranch(cust.getCardBranch());
		signCust.setUpdateTime(new Date());
		signCust.setUpdateUser(sessionUser.getUserId());
		signCust.setStatus(cust.getStatus());

		return signCustDAO.update(signCust) > 0;
	}

	public boolean deleteSignCust(long signCustId) throws BizException {
		Assert.notNull(signCustId, "删除的签单卡客户ID不能为空");

		return signCustDAO.delete(signCustId) > 0;
	}

	public SignRuleReg addSignRuleReg(SignRuleReg signRuleReg,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(signRuleReg, "添加的签单规则对象不能为空");

		if (StringUtils.equals(signRuleReg.getDerateType(), DerateType.COUNT.getValue())) {
			signRuleReg.setDerateAmt(new BigDecimal(0.0));
		} else if (StringUtils.equals(signRuleReg.getDerateType(), DerateType.SUMAMT.getValue())) {
			signRuleReg.setDerateCount(0L);
		} else {
			signRuleReg.setDerateAmt(new BigDecimal(0.0));
			signRuleReg.setDerateCount(0L);
		}
		signRuleReg.setCardBranch(sessionUser.getBranchNo());
		signRuleReg.setStatus(RegisterState.WAITED.getValue());
		signRuleReg.setUpdateTime(new Date());
		signRuleReg.setUpdateUser(sessionUser.getUserId());
		signRuleRegDAO.insert(signRuleReg);

		return signRuleReg;
	}

	public boolean modifySignRuleReg(SignRuleReg signRuleReg,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(signRuleReg, "更新的签单规则对象不能为空");

		SignRuleReg reg = (SignRuleReg) signRuleRegDAO.findByPk(signRuleReg.getSignRuleId());
		Assert.notNull(reg, "要修改的签单规则已经不存在。");

		if (StringUtils.equals(signRuleReg.getDerateType(), DerateType.COUNT.getValue())) {
			signRuleReg.setDerateAmt(new BigDecimal(0.0));
		} else if (StringUtils.equals(signRuleReg.getDerateType(), DerateType.SUMAMT.getValue())) {
			signRuleReg.setDerateCount(0L);
		} else {
			signRuleReg.setDerateAmt(new BigDecimal(0.0));
			signRuleReg.setDerateCount(0L);
		}
		signRuleReg.setCardBranch(reg.getCardBranch());
		signRuleReg.setStatus(reg.getStatus());
		signRuleReg.setUpdateTime(new Date());
		signRuleReg.setUpdateUser(sessionUser.getUserId());

		return signRuleRegDAO.update(signRuleReg) > 0;
	}

	public boolean deleteSignRuleReg(long signRuleId) throws BizException {
		Assert.notNull(signRuleId, "删除的签单规则ID不能为空");
		long count = 0;
		return count > 0;
	}

	public boolean addSaleSignCardReg(SaleSignCardReg saleSignCardReg,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(saleSignCardReg, "添加的签单卡登记对象不能为空");
		Assert.notNull(saleSignCardReg.getCardId(), "售卡对象卡号ID不能为空");

		// 数据预处理
		saleSignCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		saleSignCardReg.setSaleDate(DateUtil.formatDate("yyyyMMdd"));
		saleSignCardReg.setUpdateTime(new Date());
		saleSignCardReg.setUpdateUser(sessionUser.getUserId());

		// 检查卡唯一性
		// 查卡信息表
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(saleSignCardReg.getCardId());
		Assert.notNull(cardInfo, "卡信息表里找不到该卡号的记录。");
		if (!StringUtils.equals(cardInfo.getCardStatus(), CardState.FORSALE.getValue())) {
			throw new BizException("添加售卡登记失败！卡号[" + saleSignCardReg.getCardId() + "]不是“待售卡”。");
		}
		// 签单卡类型检查
//		Assert.isTrue(cardInfo.getCardClass().equals(CardType.SIGN.getValue()),
//				"添加售卡登记失败！卡号[" + saleSignCardReg.getCardId() + "]不是“签单卡”。");
		// 查签单卡售卡登记簿
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", saleSignCardReg.getCardId());
		List<SaleSignCardReg> saleSignCardRegList = this.saleSignCardRegDAO.findSaleSignCardReg(params);
		Assert.notTrue(saleSignCardRegList.size() > 0, "添加售卡登记失败！卡号[" + saleSignCardReg.getCardId() + "]已售出”。");

		// 签单卡售卡登记簿
		this.saleSignCardRegDAO.insert(saleSignCardReg);

		// 发报文
		MsgSender.sendMsg(MsgType.SALE_SIGNBILL, saleSignCardReg
				.getSaleSignCardId(), sessionUser.getUserId());
		return true;
	}
}
