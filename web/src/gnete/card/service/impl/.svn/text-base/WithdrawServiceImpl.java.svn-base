package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BankAcctInfoDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.dao.WithDrawBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BankAcct;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.ChannelTrade;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WithDrawBill;
import gnete.card.entity.state.TradeResultState;
import gnete.card.entity.state.WithdrawState;
import gnete.card.entity.type.AccountChangeType;
import gnete.card.entity.type.AccountPropType;
import gnete.card.entity.type.TradeType;
import gnete.card.service.AccountOperService;
import gnete.card.service.ChannelTradeService;
import gnete.card.service.TradeResultHandleService;
import gnete.card.service.WithdrawService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService, TradeResultHandleService {

	@Autowired
	private WithDrawBillDAO withDrawBillDAO;
	@Autowired
	private AccountOperService accountOperService;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private BankAcctInfoDAO bankAcctInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private ChannelTradeService channelTradeService;
	@Autowired
	private ChannelTradeDAO channelTradeDAO;
	@Override
	public String addWithdraw(WithDrawBill withDrawBill, UserInfo userInfo) throws BizException {
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo) businessSubAccountInfoDAO.findByPk(withDrawBill
				.getAccountId());
		String systemId = bsai.getSystemId();
		AccountSystemInfo asi = (AccountSystemInfo) accountSystemInfoDAO.findByPk(systemId);
		BigDecimal freezeAmt = withDrawBill.getAmount();
		if (asi.getUnitFee() != null) {
			//freezeAmt = AmountUtil.add(withDrawBill.getAmount(), asi.getUnitFee());
		}
		Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), freezeAmt), "没有足够的可用金额");
		withDrawBill.setCreateTime(new Date());
		String bankCardNo = withDrawBill.getBankCardNo();
		BankAcct bankAcct = (BankAcct) bankAcctInfoDAO.findByPk(bankCardNo);
		withDrawBill.setBankCardName(bankAcct.getAcctName());
		withDrawBill.setBankCode(bankAcct.getBankNo());
		BankInfo bankInfo = (BankInfo) bankInfoDAO.findByPk(bankAcct.getBankNo());
		withDrawBill.setBankName(bankInfo.getBankName());
		
		withDrawBill.setBranchNo(asi.getCustId());
		withDrawBill.setState(WithdrawState.WAIT_CHECK.getValue());
		withDrawBill.setSystemId(systemId);
		BigDecimal fee = asi.getUnitFee();
		withDrawBill.setFee(fee);
		String no = (String) withDrawBillDAO.insert(withDrawBill);
		accountOperService.operAccount(AccountChangeType.FREEZE, withDrawBill.getAccountId(), withDrawBill.getAmount(),
				no, "提现金额冻结");
		/*if (fee != null) {
			accountOperService.operAccount(AccountChangeType.FREEZE, withDrawBill.getAccountId(), withDrawBill.getFee(),
					no, "提现手续费冻结");
		}*/
		return no;
	}

	@Override
	public void checkWithdraw(WithDrawBill withDrawBill, UserInfo userInfo) throws BizException {
		Date now = new Date();
		String[] nos = withDrawBill.getNos();
		for (int i = 0; i < nos.length; i++) {
			WithDrawBill bill = (WithDrawBill) withDrawBillDAO.findByPkWithLock(nos[i]);
			Assert.equals(WithdrawState.WAIT_CHECK.getValue(), bill.getState(), LogUtils.r("提现单[{0}]不是待审核状态",
					withDrawBill.getNo()));
			bill.setCheckTime(new Date());
			bill.setState(withDrawBill.getState());
			if (WithdrawState.FAILED.getValue().equals(withDrawBill.getState())) {
				bill.setRemark(bill.getRemark() + withDrawBill.getRemark());
			}
			//审核未通过要解冻
			if (WithdrawState.CHECK_FAILURE.getValue().equals(withDrawBill.getState())) {
				bill.setFee(new BigDecimal(0));
				bill.setFinishTime(now);
				accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getAccountId(), bill.getAmount(), bill
						.getNo(), "解冻已冻结提现金额");
			}
			withDrawBillDAO.update(bill);
/*			if (bill.getFee() != null) {
				accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getAccountId(), bill.getFee(), bill
						.getNo(), "解冻已冻结提现手续费");
			}*/
			if (WithdrawState.PAYING.getValue().equals(withDrawBill.getState())) {
				// 扣款
				/*accountOperService.operAccount(AccountChangeType.WITHDRAW, bill.getAccountId(), bill.getAmount(), bill
						.getNo(), "提现扣款");*/
				//生成支付文件，推送到
				/*if (bill.getFee() != null) {
					accountOperService.operAccount(AccountChangeType.WITHDRAW, bill.getAccountId(), bill.getFee(), bill
							.getNo(), "扣除提现手续费");
				}*/
				//
				ChannelTrade channelTrade = new ChannelTrade();
				
				BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(bill.getAccountId());
				AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
				channelTrade.setSrcSystemId(bsai.getSystemId());
				channelTrade.setRefId(bill.getNo());
				channelTrade.setAcctNo(bill.getBankCardNo());
				channelTrade.setAcctName(bill.getBankCardName());
				channelTrade.setAcctProType(AccountPropType.COMPANY.getValue());
				channelTrade.setAmount(bill.getAmount());
				channelTrade.setBankCode(bill.getBankCode());
				channelTrade.setBankName(bill.getBankName());
				BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bill.getBankCode());
				channelTrade.setBankType(bankInfo.getBankType());
				channelTrade.setBusiCustId(bsai.getCustId());
				channelTrade.setBusiCustName(bsai.getCustName());
				channelTrade.setSrcCustId(asi.getCustId());
				channelTrade.setSrcCustName(asi.getCustName());
				channelTrade.setTradeType(TradeType.WITHDRAW.getValue());
				String id = channelTradeService.createChannelTrade(channelTrade);
				bill.setTradeNo(id);
				withDrawBillDAO.update(bill);
			}
		}
	}

	@Override
	public void handleTradeResult(String chnlTradeId) throws BizException {
		ChannelTrade channelTrade = (ChannelTrade)channelTradeDAO.findByPkWithLock(chnlTradeId);
		String id = channelTrade.getRefId();
		WithDrawBill bill = (WithDrawBill)withDrawBillDAO.findByPkWithLock(id);
		Assert.isTrue(WithdrawState.PAYING.getValue().equals(bill.getState()), LogUtils.r("提现单[{0}]状态已过期", id));
		//解冻
		accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getAccountId(), bill.getAmount(), id, LogUtils.r("解冻提现金额[{0}],提现单id[{1}]", bill.getAmount(), id));
		if (TradeResultState.SUCCESS.getValue().equals(channelTrade.getResult())) {
			bill.setState(WithdrawState.FINISH.getValue());
			accountOperService.operAccount(AccountChangeType.WITHDRAW, bill.getAccountId(), bill.getAmount(), id, LogUtils.r("扣除提现金额[{0}],提现单id[{1}]", bill.getAmount(), id));
		} else {
			bill.setState(WithdrawState.FAILED.getValue());
			bill.setFee(new BigDecimal(0));
		}
		bill.setFinishTime(new Date());
		bill.setReturnCode(channelTrade.getReturnCode());
		bill.setReturnInfo(channelTrade.getReturnInfo());
		withDrawBillDAO.update(bill);
	}

}
