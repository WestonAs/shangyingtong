package gnete.card.service.impl;

import flink.util.DateUtil;
import flink.util.LogUtils;
import gnete.card.dao.RechargeBillDAO;
import gnete.card.entity.RechargeBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RechargeState;
import gnete.card.entity.type.AccountChangeType;
import gnete.card.service.AccountOperService;
import gnete.card.service.RechargeService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {

	@Autowired
	private RechargeBillDAO rechargeBillDAO;

	@Autowired
	private AccountOperService accountOperService;
	@Override
	public String addRecharge(RechargeBill rechargeBill, UserInfo userInfo) throws BizException{
		rechargeBill.setCreateTime(new Date());
		rechargeBill.setState(RechargeState.WAIT_CHECK.getValue());
		String no = (String)rechargeBillDAO.insert(rechargeBill);
		return no;
	}

	@Override
	public void checkRecharge(RechargeBill rechargeBill, UserInfo userInfo) throws BizException{
		RechargeBill bill = (RechargeBill)rechargeBillDAO.findByPk(rechargeBill.getNo());
		Assert.equals(RechargeState.WAIT_CHECK.getValue(), bill.getState(), LogUtils.r("充值单[{0}]不是待审核状态", rechargeBill.getNo()));
		bill.setState(rechargeBill.getState());
		bill.setRemitTime(DateUtil.formatDate(rechargeBill.getRemitTimeStr(), "yyyy-MM-dd HH:mm:ss"));
		bill.setVoucherNo(rechargeBill.getVoucherNo());
		bill.setNote(bill.getNote()+rechargeBill.getCheckNote());
		rechargeBillDAO.update(bill);
		//记录账户变动信息
		if (RechargeState.RECHARGED.getValue().equals(rechargeBill.getState())) {
			accountOperService.operAccount(AccountChangeType.RECHARGE, bill.getAccountId(), bill.getAmount(), bill.getNo(), bill.getNote());
		}
	}
	
}
