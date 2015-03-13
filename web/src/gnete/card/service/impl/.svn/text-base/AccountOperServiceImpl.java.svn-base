package gnete.card.service.impl;

import flink.util.AmountUtil;
import gnete.card.dao.AccountChangeDetailDAO;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.entity.AccountChangeDetail;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.AccountSystemState;
import gnete.card.entity.type.AccountChangeType;
import gnete.card.service.AccountOperService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("accountOperService")
public class AccountOperServiceImpl implements AccountOperService {

	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private AccountChangeDetailDAO accountChangeDetailDAO;
	public String operAccount(AccountChangeType changeType, String accountId, BigDecimal amount, String refId, String remark) throws BizException{
		Assert.notNull(changeType, "虚账变动类型不能为空");
		Assert.notNull(accountId, "虚账编号不能为空");
		Assert.notNull(amount, "金额不能为空");
		if (AmountUtil.et(amount, new BigDecimal(0.0))) {
			return null;
		}
		Assert.notNull(refId, "关联交易id不能为空");
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPkWithLock(accountId);
		Assert.notNull(bsai, "找不到对应的虚户");
		Assert.notEquals(bsai.getState(), AccountState.CANCELED.getValue(), "账户已注销");
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
		Assert.notEquals(asi.getState(), AccountSystemState.STOP.getValue(), "账户所属体系已停用");
		AccountChangeDetail acd = new AccountChangeDetail();
		if (AccountChangeType.RECHARGE.equals(changeType)) {
			bsai.setCashAmount(AmountUtil.add(bsai.getCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.WITHDRAW.equals(changeType)) {
			Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), amount), "没有足够的余额");
			bsai.setCashAmount(AmountUtil.subtract(bsai.getCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.FREEZE.equals(changeType)){
			Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), amount), "没有足够的余额");
			bsai.setFreezeCashAmount(AmountUtil.add(bsai.getFreezeCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.UNFREEZE.equals(changeType)) {
			bsai.setFreezeCashAmount(AmountUtil.subtract(bsai.getFreezeCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.RECEIVE.equals(changeType)) {
			bsai.setCashAmount(AmountUtil.add(bsai.getCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.TRANS.equals(changeType)) {
			Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), amount), "没有足够的余额");
			bsai.setCashAmount(AmountUtil.subtract(bsai.getCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		} else if (AccountChangeType.PAY.equals(changeType)) {
			Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), amount), "没有足够的余额");
			bsai.setCashAmount(AmountUtil.subtract(bsai.getCashAmount(), amount));
			acd.setBalance(bsai.getCashAmount());
			acd.setFreezeAmt(bsai.getFreezeCashAmount());
		}
		Date now = new Date();
		bsai.setLastUpdateTime(now);
		
		acd.setAccountId(accountId);
		acd.setAmount(amount);
		acd.setChangeType(changeType.getValue());
		acd.setRemark(remark);
		acd.setSystemId(bsai.getSystemId());
		acd.setCustId(bsai.getCustId());
		acd.setCustName(bsai.getCustName());
		acd.setRefId(refId);
		acd.setCreateTime(now);
		businessSubAccountInfoDAO.update(bsai);
		return (String)accountChangeDetailDAO.insert(acd);
	}
}
