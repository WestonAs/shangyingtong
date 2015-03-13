package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.dao.PayBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.ChannelTrade;
import gnete.card.entity.PayBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.PayState;
import gnete.card.entity.state.TradeResultState;
import gnete.card.entity.state.TransState;
import gnete.card.entity.type.AccountChangeType;
import gnete.card.entity.type.AccountPropType;
import gnete.card.entity.type.TradeType;
import gnete.card.service.AccountOperService;
import gnete.card.service.ChannelTradeService;
import gnete.card.service.PayBillService;
import gnete.card.service.TradeResultHandleService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("payBillService")
public class PayBillServiceImpl implements PayBillService, TradeResultHandleService {

	@Autowired
	private PayBillDAO payBillDAO;
	@Autowired
	private AccountOperService accountOperService;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private ChannelTradeService channelTradeService;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private ChannelTradeDAO channelTradeDAO;
	@Override
	public String addPayBill(PayBill payBill, UserInfo userInfo) throws BizException {
		String payeeBankType = payBill.getPayeeBankType();
		String payeeBankCode = payBill.getPayeeBankCode();
		if (StringUtils.isNotEmpty(payeeBankCode)) {
			BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(payeeBankCode);
			Assert.equals(payeeBankType, bankInfo.getBankType(), "所选行别与开户行不一致");
		}
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo) businessSubAccountInfoDAO.findByPk(payBill.getPayerAccountId());
		//手续费
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
		BigDecimal freezeAmt = payBill.getAmount();
		if(asi.getUnitFee() != null){
			//freezeAmt = AmountUtil.add(payBill.getAmount(), asi.getUnitFee());
		}
		Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), freezeAmt), "没有足够的可用金额");
		payBill.setCreateTime(new Date());
		payBill.setState(TransState.WAIT_CHECK.getValue());
		
		payBill.setSystemCustId(asi.getCustId());
		payBill.setFee(asi.getUnitFee());
		String id = (String)payBillDAO.insert(payBill);
		//冻结
		accountOperService.operAccount(AccountChangeType.FREEZE, payBill.getPayerAccountId(), payBill.getAmount(), id, "冻结支付金额");
		//手续费
		BigDecimal fee = asi.getUnitFee();
		if (fee != null) {
			//accountOperService.operAccount(AccountChangeType.FREEZE, payBill.getPayerAccountId(), fee, id, "冻结支付手续费");
		}
		return id;
	}
	@Override
	public void checkPayBill(PayBill payBill, UserInfo userInfo) throws BizException {
		String[] ids = payBill.getIds();
		Date now = new Date();
		for (int i = 0; i < ids.length; i++) {
			PayBill bill = (PayBill) payBillDAO.findByPkWithLock(ids[i]);
			Assert.equals(TransState.WAIT_CHECK.getValue(), bill.getState(), LogUtils.r("支付记录[{0}]不是待审核状态",
					bill.getId()));
			bill.setCheckTime(now);
			if (Symbol.YES.equals(payBill.getCheckPass())) {
				bill.setState(TransState.PAYING.getValue());
			} else {
				bill.setState(TransState.CHECK_FAILURE.getValue());
				bill.setFee(new BigDecimal(0));
				bill.setFinishTime(now);
				// 审核未通过解冻
				accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getAmount(), bill
						.getId(), "支付款解冻");
			}
			if (TransState.FAILED.getValue().equals(bill.getState())) {
				bill.setRemark(payBill.getRemark() + bill.getRemark());
			}
			payBillDAO.update(bill);
			/*if (bill.getFee() != null) {
				accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getFee(), bill
						.getId(), "支付手续费解冻");
			}*/
			if (Symbol.YES.equals(payBill.getCheckPass())) {
				// 扣款
				/*accountOperService.operAccount(AccountChangeType.PAY, bill.getPayerAccountId(), bill.getAmount(), bill
						.getId(), "支付扣款");*/
				/*if (payBill.getFee() != null) {
					accountOperService.operAccount(AccountChangeType.PAY, bill.getPayerAccountId(), bill.getFee(), bill
							.getId(), "支付手续费扣款");
				}*/
				//发起代付交易
				ChannelTrade channelTrade = new ChannelTrade();
				BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(bill.getPayerAccountId());
				AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
				channelTrade.setSrcSystemId(bsai.getSystemId());
				channelTrade.setRefId(bill.getId());
				channelTrade.setAcctNo(bill.getPayeeAcctNo());
				channelTrade.setAcctName(bill.getPayeeAcctName());
				channelTrade.setAcctProType(AccountPropType.PERSONAL.getValue());
				channelTrade.setAmount(bill.getAmount());
				channelTrade.setBankCode(bill.getPayeeBankCode());
				channelTrade.setBankName(bill.getPayeeBankName());
				channelTrade.setBankType(bill.getPayeeBankType());
				channelTrade.setBusiCustId(bsai.getCustId());
				channelTrade.setBusiCustName(bsai.getCustName());
				channelTrade.setSrcCustId(asi.getCustId());
				channelTrade.setSrcCustName(asi.getCustName());
				channelTrade.setTradeType(TradeType.PAY.getValue());
				String id = channelTradeService.createChannelTrade(channelTrade);
				bill.setTradeNo(id);
				payBillDAO.update(bill);
			}
		}
	}
	@Override
	public void handleTradeResult(String chnlTradeId) throws BizException {
		ChannelTrade channelTrade = (ChannelTrade)channelTradeDAO.findByPkWithLock(chnlTradeId);
		String id = channelTrade.getRefId();
		PayBill bill = (PayBill)payBillDAO.findByPkWithLock(id);
		Assert.isTrue(PayState.PAYING.getValue().equals(bill.getState()), LogUtils.r("支付单[{0}]状态已过期", id));
		//解冻
		accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getAmount(), id, LogUtils.r("解冻支付金额[{0}],支付单id[{1}]", bill.getAmount(), id));
		if (TradeResultState.SUCCESS.getValue().equals(channelTrade.getResult())) {
			bill.setState(PayState.FINISH.getValue());
			accountOperService.operAccount(AccountChangeType.PAY, bill.getPayerAccountId(), bill.getAmount(), id, LogUtils.r("扣除支付金额[{0}],支付单id[{1}]", bill.getAmount(), id));
		} else {
			bill.setState(PayState.FAILED.getValue());
			bill.setFee(new BigDecimal(0));
		}
		bill.setReturnCode(channelTrade.getReturnCode());
		bill.setReturnInfo(channelTrade.getReturnInfo());
		bill.setFinishTime(new Date());
		payBillDAO.update(bill);
	}
}
