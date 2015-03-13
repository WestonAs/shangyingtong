package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.LogUtils;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.dao.TransBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.ChannelTrade;
import gnete.card.entity.TransBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.TradeResultState;
import gnete.card.entity.state.TransState;
import gnete.card.entity.state.WithdrawState;
import gnete.card.entity.type.AccountChangeType;
import gnete.card.entity.type.AccountPropType;
import gnete.card.entity.type.TradeType;
import gnete.card.service.AccountOperService;
import gnete.card.service.ChannelTradeService;
import gnete.card.service.TradeResultHandleService;
import gnete.card.service.TransBillService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transBillService")
public class TransBillServiceImpl implements TransBillService, TradeResultHandleService {

	@Autowired
	private TransBillDAO transBillDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountOperService accountOperService;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private ChannelTradeService channelTradeService;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private ChannelTradeDAO channelTradeDAO;
	@Override
	public String addTransBill(TransBill payBill, UserInfo userInfo) throws BizException {
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo) businessSubAccountInfoDAO.findByPk(payBill.getPayerAccountId());
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
		payBill.setSystemCustId(asi.getCustId());
		BusinessSubAccountInfo recBsai = (BusinessSubAccountInfo) businessSubAccountInfoDAO.findByPk(payBill.getPayeeAccountId());
		BigDecimal freezeAmt = payBill.getAmount();
		if (bsai.getSystemId().equals(recBsai.getSystemId())) {
			payBill.setCrossSystem(Symbol.NO);
			payBill.setFee(new BigDecimal(0));
		} else {
			payBill.setCrossSystem(Symbol.YES);
			//冻结手续费
			if (asi.getUnitFee() != null) {
				payBill.setFee(asi.getUnitFee());
				//freezeAmt = AmountUtil.add(freezeAmt, asi.getUnitFee());
			}
		}
		Assert.isTrue(AmountUtil.ge(bsai.getUsableBalance(), freezeAmt), "没有足够的可用金额");
		payBill.setPayeeCustId(recBsai.getCustId());
		payBill.setPayeeCustName(recBsai.getCustName());
		payBill.setCreateTime(new Date());
		payBill.setState(TransState.WAIT_CHECK.getValue());
		String id = (String)transBillDAO.insert(payBill);
		if (Symbol.YES.equals(payBill.getCrossSystem()) && asi.getUnitFee() != null) {
			//手续费应从体系信息中取
			//accountOperService.operAccount(AccountChangeType.FREEZE, payBill.getPayerAccountId(), payBill.getFee(), id, "冻结转账手续费");
		}
		accountOperService.operAccount(AccountChangeType.FREEZE, payBill.getPayerAccountId(), payBill.getAmount(), id, "冻结转账款");
		return id;
	}

	@Override
	public void checkTransBill(TransBill transBill, UserInfo userInfo) throws BizException {
		String[] ids = transBill.getIds();
		Date now = new Date();
		for (int i = 0; i < ids.length; i++) {
			TransBill bill = (TransBill) transBillDAO.findByPkWithLock(ids[i]);
			Assert.equals(TransState.WAIT_CHECK.getValue(), bill.getState(), LogUtils.r("转账记录[{0}]不是待审核状态",
					bill.getId()));
			bill.setCheckTime(now);
			if (Symbol.YES.equals(transBill.getCheckPass())) {
				if (Symbol.YES.endsWith(bill.getCrossSystem())) {
					bill.setState(TransState.PAYING.getValue());
				} else {
					bill.setState(TransState.FINISH.getValue());
					bill.setFinishTime(now);
				}
			} else {
				//如果审核失败就解冻
				bill.setState(TransState.CHECK_FAILURE.getValue());
				bill.setFee(new BigDecimal(0));
				bill.setFinishTime(now);
				bill.setRemark(transBill.getRemark() + bill.getRemark());
				accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getAmount(), bill
						.getId(), bill.getRemark());
			}
			transBillDAO.update(bill);
			
			if (Symbol.YES.equals(transBill.getCheckPass())) {
				// 扣款
				if (Symbol.NO.equals(bill.getCrossSystem())) {
					//解冻
					accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getAmount(), bill
							.getId(), bill.getRemark());
					//扣款
					accountOperService.operAccount(AccountChangeType.TRANS, bill.getPayerAccountId(), bill.getAmount(), bill
							.getId(), bill.getRemark());
					//对方账户入账
					accountOperService.operAccount(AccountChangeType.RECEIVE, bill.getPayeeAccountId(), bill.getAmount(), bill
							.getId(), LogUtils.r("收到账户[{0}]转账[{1}]元", bill.getPayeeAccountId(), bill.getAmount()));
				} else {
					/*accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getFee(), bill
							.getId(), "斛冻已冻结手续费");
					accountOperService.operAccount(AccountChangeType.TRANS, bill.getPayerAccountId(), bill.getFee(), bill.getId(), "扣除转账交易手续费");*/
					//发起代付交易
					ChannelTrade channelTrade = new ChannelTrade();
					channelTrade.setRefId(bill.getId());
					BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(bill.getPayerAccountId());
					AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
					channelTrade.setSrcSystemId(bsai.getSystemId());
					BusinessSubAccountInfo recBsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(bill.getPayeeAccountId());
					String recSystemId = recBsai.getSystemId();
					AccountSystemInfo recAsi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(recSystemId);
					channelTrade.setAcctNo(recAsi.getAcctNo());
					channelTrade.setAcctName(recAsi.getAcctName());
					//公司
					channelTrade.setAcctProType(AccountPropType.COMPANY.getValue());
					channelTrade.setAmount(bill.getAmount());
					channelTrade.setBankCode(recAsi.getBankNo());
					channelTrade.setBankName(recAsi.getBankName());
					BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(recAsi.getBankNo());
					channelTrade.setBankType(bankInfo.getBankType());
					channelTrade.setBusiCustId(bsai.getCustId());
					channelTrade.setBusiCustName(bsai.getCustName());
					channelTrade.setSrcCustId(asi.getCustId());
					channelTrade.setSrcCustName(asi.getCustName());
					channelTrade.setBusiCustId(bsai.getCustId());
					channelTrade.setBusiCustName(bsai.getCustName());
					channelTrade.setTradeType(TradeType.TRANS.getValue());
					String id = channelTradeService.createChannelTrade(channelTrade);
					bill.setTradeNo(id);
					transBillDAO.update(bill);
				}
			}
		}
	}

	@Override
	public void handleTradeResult(String chnlTradeId) throws BizException {
		ChannelTrade channelTrade = (ChannelTrade)channelTradeDAO.findByPkWithLock(chnlTradeId);
		String id = channelTrade.getRefId();
		TransBill bill = (TransBill)transBillDAO.findByPkWithLock(id);
		Assert.isTrue(TransState.PAYING.getValue().equals(bill.getState()), LogUtils.r("转账单[{0}]状态已过期", id));
		//解冻
		accountOperService.operAccount(AccountChangeType.UNFREEZE, bill.getPayerAccountId(), bill.getAmount(), id, LogUtils.r("解冻转账金额[{0}],转账单id[{1}]", bill.getAmount(), id));
		if (TradeResultState.SUCCESS.getValue().equals(channelTrade.getResult())) {
			bill.setState(WithdrawState.FINISH.getValue());
			accountOperService.operAccount(AccountChangeType.TRANS, bill.getPayerAccountId(), bill.getAmount(), id, LogUtils.r("扣除转账金额[{0}],转账单id[{1}]", bill.getAmount(), id));
			//转账成功时，收款账户增加余额
			accountOperService.operAccount(AccountChangeType.RECEIVE, bill.getPayeeAccountId(), bill.getAmount(), id, LogUtils.r("收款账户增加金额[{0}],转账单id[{1}]", bill.getAmount(), id));
		} else {
			bill.setState(WithdrawState.FAILED.getValue());
			bill.setFee(new BigDecimal(0));
		}
		bill.setFinishTime(new Date());
		bill.setReturnCode(channelTrade.getReturnCode());
		bill.setReturnInfo(channelTrade.getReturnInfo());
		transBillDAO.update(bill);
	}
}
