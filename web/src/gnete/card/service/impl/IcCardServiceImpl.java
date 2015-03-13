package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.IcCancelCardRegDAO;
import gnete.card.dao.IcCardActiveDAO;
import gnete.card.dao.IcCardExtroInfoDAO;
import gnete.card.dao.IcCardParaModifyRegDAO;
import gnete.card.dao.IcCardReversalDAO;
import gnete.card.dao.IcEcashDepositRegDAO;
import gnete.card.dao.IcEcashReversalDAO;
import gnete.card.dao.IcRenewCardRegDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.WaitsinfoDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.IcCancelCardReg;
import gnete.card.entity.IcCardActive;
import gnete.card.entity.IcCardExtroInfo;
import gnete.card.entity.IcCardParaModifyReg;
import gnete.card.entity.IcCardReversal;
import gnete.card.entity.IcEcashDepositReg;
import gnete.card.entity.IcEcashReversal;
import gnete.card.entity.IcRenewCardReg;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.Waitsinfo;
import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.state.WaitsinfoState;
import gnete.card.entity.state.WebState;
import gnete.card.entity.type.IcCancelCardType;
import gnete.card.entity.type.IcRenewCardType;
import gnete.card.entity.type.IcReversalType;
import gnete.card.entity.type.ReversalType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.BaseDataService;
import gnete.card.service.IcCardService;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.UserService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.tag.NameTag;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("icCardService")
public class IcCardServiceImpl implements IcCardService {
	
	@Autowired
	private IcEcashDepositRegDAO icEcashDepositRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private IcTempParaDAO icTempParaDAO;
	@Autowired
	private WaitsinfoDAO waitsinfoDAO;
	@Autowired
	private IcEcashReversalDAO icEcashReversalDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private IcCardActiveDAO icCardActiveDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private BaseDataService baseDataService;
	@Autowired
	private IcCardParaModifyRegDAO icCardParaModifyRegDAO;
	@Autowired
	private UserCertificateRevService userCertificateRevService;
	@Autowired
	private IcCardExtroInfoDAO icCardExtroInfoDAO;
	@Autowired
	private IcRenewCardRegDAO icRenewCardRegDAO;
	@Autowired
	private IcCardReversalDAO icCardReversalDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private IcCancelCardRegDAO icCancelCardRegDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	
	/**
	 * 默认的轮询时间，单位：秒
	 */
	private static final int DEFAULT_INTERVAL = 1; 
	
	private static final Logger logger = Logger.getLogger(IcCardServiceImpl.class);

	public Paginater findIcEcashDepositPage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		return this.icEcashDepositRegDAO.findPage(params, pageNumber, pageSize);
	}

	public IcEcashDepositReg getIcEcashDepositDetail(
			IcEcashDepositReg icEcashDepositReg) throws BizException {
		return (IcEcashDepositReg) this.icEcashDepositRegDAO.findByPk(icEcashDepositReg.getDepositBatchId());
	}
	
	public JSONObject searchCardId(String cardId) {

		JSONObject object = new JSONObject();
		
		try {
			Assert.notEmpty(cardId, "卡号不能为空");
			cardId = StringUtils.trim(cardId);
			if (cardId.length() > 19) {
				cardId = cardId.substring(0, 19);
			}
			
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "系统中没有卡号[" + cardId + "]的记录");
			
			CardSubClassDef subClass= (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(subClass, "卡号[" + cardId + "]所属的卡类型号[" + cardInfo.getCardSubclass() + "]已经不存在");
			
			IcTempPara para = (IcTempPara) this.icTempParaDAO.findByPk(subClass.getCardSubclass());
			Assert.notNull(para, "没有为卡号[" + cardId + "]所属的卡类型号[" + cardInfo.getCardSubclass() + "]配置IC个人化参数");
//			Assert.notNull(para.getBalanceLimit(), "卡类型号[" + cardInfo.getCardSubclass() + "]配置的电子现金余额上限不能为空");
			
			IcCardExtroInfo icCardExtroInfo = (IcCardExtroInfo) this.icCardExtroInfoDAO.findByPk(cardId);
			Assert.notNull(icCardExtroInfo, "IC卡附加信息表中不存在卡号[" + cardId + "]的记录");
			
			// 查询卡附加信息表的记录
			CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);
			if (cardExtraInfo != null) {
				object.put("custName", cardExtraInfo.getCustName());
				object.put("credType", cardExtraInfo.getCredType());
				object.put("credNo", cardExtraInfo.getCredNo());
			} else {
				object.put("custName", StringUtils.EMPTY);
				object.put("credType", StringUtils.EMPTY);
				object.put("credNo", StringUtils.EMPTY);
			}
			
			// 查询卡的电子现金账户
			SubAcctBalKey eCashKey = new SubAcctBalKey(cardInfo.getAcctId(), SubacctType.E_CASH.getValue());
			SubAcctBal eCashAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(eCashKey);
			Assert.notNull(eCashAcctBal, "卡号[" + cardId +"]的电子现金账户不存在");
			object.put("lastBalance", eCashAcctBal.getAvlbBal());
			
			// 查询补登账户
			SubAcctBalKey fillUpKey = new SubAcctBalKey(cardInfo.getAcctId(), SubacctType.FILL_UP.getValue());
			SubAcctBal fillUpKeyAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(fillUpKey);
			// 如果该批次的卡需要做新卡检查的话，则要检查是否有补登账户
			Assert.notNull(fillUpKeyAcctBal, "卡号[" + cardId +"]的补登账户不存在");
			object.put("avlBal", fillUpKeyAcctBal.getAvlbBal());
			
			object.put("cardId", cardId);
			object.put("cardClass", cardInfo.getCardClass());
			object.put("cardBin", cardInfo.getCardBin());
			object.put("cardClassName", cardInfo.getCardClassName());
			object.put("cardSubClass", cardInfo.getCardSubclass());
			object.put("cardSubClassName", subClass.getCardSubclassName());
			object.put("cardBranch", cardInfo.getCardIssuer());
			object.put("cardBranchName", NameTag.getBranchName(cardInfo.getCardIssuer()));
			object.put("balanceLimit", icCardExtroInfo.getBalanceLimit());
			
			object.put("cardCustomerId", cardInfo.getCardCustomerId());
			
			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		return object;
	}
	
	public String addIcEcashDepositReg(IcEcashDepositReg icEcashDepositReg,
			UserInfo user, String serialNo) throws BizException {
			
		Assert.notNull(icEcashDepositReg, "IC卡电子现金充值登记对象不能为空");
		Assert.notEmpty(icEcashDepositReg.getRandomSessionid(), "随机数不能为空");
		Assert.notEmpty(icEcashDepositReg.getCardId(), "卡号不能为空");
		
		Assert.isNull(icEcashDepositRegDAO.findByRandomSessionid(icEcashDepositReg.getRandomSessionid()), "请不要重复提交请求！");
		
		Assert.notNull(icEcashDepositReg.getDepositAmt(), "充值金额不能为空");
		Assert.notEmpty(icEcashDepositReg.getCardSn(), "卡序列号不能为空");
		Assert.notEmpty(icEcashDepositReg.getArqc(), "交易密文ARQ不能为空");
		Assert.notEmpty(icEcashDepositReg.getAqdt(), "arqc源数据ARQT不能为空");
		
		Assert.notNull(icEcashDepositReg.getLastBalance(), "卡余额不能为空");
		Assert.notNull(icEcashDepositReg.getBalanceLimit(), "IC卡余额上限不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(icEcashDepositReg.getCardId());
		Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), 
				"卡号[" + icEcashDepositReg.getCardId() + "]不是“正常（已激活）”状态不能做电子现金充值！");
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
			throw new BizException("没有给该卡["+ icEcashDepositReg.getCardId() +"]做电子现金充值的权限");
		}
		
		// 1. 在登记薄中插入记录
		icEcashDepositReg.setStatus(RegisterState.WAITEDEAL.getValue());
		icEcashDepositReg.setUpdateUser(user.getUserId());
		icEcashDepositReg.setUpdateTime(new Date());
		icEcashDepositReg.setWriteCardFlag(Symbol.NO);
		icEcashDepositReg.setReversalFlag(ReversalFlag.WAITE_REVERSAL.getValue());
		String roleType = user.getRole().getRoleType();
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			icEcashDepositReg.setDepositBranch(user.getDeptId());
		} else {
			icEcashDepositReg.setDepositBranch(user.getBranchNo());
		}
		icEcashDepositReg.setDepositDate(SysparamCache.getInstance().getWorkDateNotFromCache()); //充值时当前系统
		
		this.icEcashDepositRegDAO.insert(icEcashDepositReg);
		
		// 2. 发送报文
		MsgSender.sendMsg(MsgType.IC_ECASH_DEPOSIT, NumberUtils.toLong(icEcashDepositReg.getDepositBatchId()), user.getUserId());
			
		return icEcashDepositReg.getDepositBatchId();
		
	}
	
	public IcEcashDepositReg dealIcEcashDeposit(String refId) throws BizException {
		IcEcashDepositReg depositReg = (IcEcashDepositReg) this.icEcashDepositRegDAO.findByPk(refId);
		Assert.notNull(depositReg, "登记薄中找不到该笔充值记录");
		
		int overTime = NumberUtils.toInt(SysparamCache.getInstance().getIcDepositOverTime()); //超时时间
		overTime = overTime < 10 ? 10 : overTime; // 超时时间至少是10秒
		
		BigDecimal countFloat = AmountUtil.divide(new BigDecimal(overTime), new BigDecimal(DEFAULT_INTERVAL));
		int count = countFloat.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 循环的总次数
		
		Waitsinfo waitsinfo = null;
		for (int i = 0; i < count; i++) {
			waitsinfo = getWaitsinfo(refId, MsgType.IC_ECASH_DEPOSIT);
			
			Assert.notNull(waitsinfo, "命令表中命令类型为[" + MsgType.IC_ECASH_DEPOSIT + "]ID为[" + refId + "]的记录不存在");
			//  查看该命令是否已经被处理
			Assert.notEquals(waitsinfo.getWebState(), WebState.DONE.getValue(), "IC卡电子现金充值批次ID为[" + refId + "]的记录已被处理");
			
			// 如果处理成功的话则进行处理成功的相关操作
			if (WaitsinfoState.SUCCESS.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForSuccess(depositReg, waitsinfo);
			}
			// 如果处理失败的话则进行处理失败的相关操作
			else if (WaitsinfoState.DEALFAILED.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForFailure(depositReg, waitsinfo);
			} else {
				// 否则休息1秒
				this.waiteInterval();
			}
		}
		
		// 超时时的处理。要将原记录冲正掉,向后台发冲正
		return this.reversal(depositReg, waitsinfo);
	}
	
	public String addIcEcashReversal(IcEcashReversal icEcashReversal,
			UserInfo user, ReversalType type) throws BizException {
		Assert.notNull(icEcashReversal, "IC卡冲正登记对象不能为空");
		
		Assert.notEmpty(icEcashReversal.getDepositBatchId(), "要冲正的IC卡电子现金充值批次号或激活批次号不能为空");
		Assert.notEmpty(icEcashReversal.getCardId(), "要冲正的对象卡号不能为空");
		Assert.notNull(icEcashReversal.getAmt(), "要冲正的金额不能为空");
		Assert.notNull(type, "冲正类型不能为空");
		
		icEcashReversal.setUpdateUser(user.getUserId());
		icEcashReversal.setReversalBranch(user.getBranchNo());
		
		if (ReversalType.DEPOSIT.equals(type)) {
			// 修改原充值记录
			IcEcashDepositReg reg = new IcEcashDepositReg();
			reg.setDepositBatchId(icEcashReversal.getDepositBatchId());
			IcEcashDepositReg depositReg = this.lockDepsosit(reg);
			
			Assert.notNull(depositReg, "充值ID为[" + icEcashReversal.getDepositBatchId() + "]的记录不存在");
			
			depositReg.setStatus(RegisterState.DISABLE.getValue());
			depositReg.setWriteCardFlag(Symbol.NO); //写卡没成功
			depositReg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
			
			this.releaseDepositLock(depositReg);
		} else {
			// 修改原激活或余额补登记录
			IcCardActive active = new IcCardActive();
			active.setActiveBatchId(icEcashReversal.getDepositBatchId());
			IcCardActive cardActive = this.lockIcCardActive(active);
			
			Assert.notNull(cardActive, "批次号为[" + icEcashReversal.getDepositBatchId() + "]的激活或余额补登记录不存在");
			cardActive.setStatus(RegisterState.DISABLE.getValue());
			cardActive.setWriteCardFlag(Symbol.NO);
			cardActive.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
			
			this.releaseIcCardActive(cardActive);
		}
		
		icEcashReversal.setReversalType(type.getValue()); //冲正类型,充值冲正01, 补登冲正02
		// 在冲正登记簿中写记录，同时在命令表中写记录
		icEcashReversal = this.addReversal(icEcashReversal);
		
		return icEcashReversal.getBatchId();
	}
	
	public void noticeSuccess(String depositId) throws BizException {
		// 修改原充值记录
		IcEcashDepositReg depositReg = new IcEcashDepositReg();
		depositReg.setDepositBatchId(depositId);
		depositReg = this.lockDepsosit(depositReg);
		Assert.notNull(depositReg, "充值ID为[" + depositId + "]的记录不存在");
		
		depositReg.setWriteCardFlag(Symbol.YES); //写卡成功
		
		this.releaseDepositLock(depositReg);
	}
	
	//================================== IC卡激活或余额补登 ==========================================================
	public Paginater findIcCardActivePage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		return this.icCardActiveDAO.findPage(params, pageNumber, pageSize);
	}
	
	public IcCardActive findIcCardActiveDetail(String pk) throws BizException {
		return (IcCardActive) this.icCardActiveDAO.findByPk(pk);
	}
	
	public String addIcCardActive(IcCardActive icCardActive, UserInfo user,
			String serialNo) throws BizException {
		Assert.notNull(icCardActive, "IC卡激活或余额补登登记对象不能为空");
		Assert.notEmpty(icCardActive.getRandomSessionid(), "随机数不能为空");
		Assert.notEmpty(icCardActive.getCardId(), "卡号不能为空");
		
		Assert.isNull(icCardActiveDAO.findByRandomSessionid(icCardActive.getRandomSessionid()), "请不要重复提交请求！");
		
		Assert.notNull(icCardActive.getLastBalance(), "卡余额不能为空");
		Assert.notEmpty(icCardActive.getCardSn(), "卡序列号不能为空");
		Assert.notEmpty(icCardActive.getArqc(), "交易密文ARQ不能为空");
		Assert.notEmpty(icCardActive.getAqdt(), "arqc源数据ARQT不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
		}
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(icCardActive.getCardId());
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
			throw new BizException("没有给该卡["+ icCardActive.getCardId() +"]做激活或余额补登的权限");
		}
		
		// 1. 在登记薄中插入记录
		icCardActive.setStatus(RegisterState.WAITEDEAL.getValue());
		icCardActive.setWriteCardFlag(Symbol.NO);
		icCardActive.setReversalFlag(ReversalFlag.WAITE_REVERSAL.getValue());
		icCardActive.setUpdateUser(user.getUserId());
		icCardActive.setUpdateTime(new Date());
		String roleTpe = user.getRole().getRoleType();
		if (RoleType.CARD_DEPT.getValue().equals(roleTpe)) {
			icCardActive.setActiveBranch(user.getDeptId());
		} else {
			icCardActive.setActiveBranch(user.getBranchNo());
		}
		
		this.icCardActiveDAO.insert(icCardActive);
		
		// 2. 发送报文
		MsgSender.sendMsg(MsgType.IC_CARD_ACTIVE, NumberUtils.toLong(icCardActive.getActiveBatchId()), user.getUserId());
			
		return icCardActive.getActiveBatchId();
	}
	
	public IcCardActive dealIcCardActive(String refId) throws BizException {
		IcCardActive icCardActive = (IcCardActive) this.icCardActiveDAO.findByPk(refId);
		Assert.notNull(icCardActive, "登记薄中找不到该批次号的记录");
		
		int overTime = NumberUtils.toInt(SysparamCache.getInstance().getIcDepositOverTime()); //超时时间
		overTime = overTime < 10 ? 10 : overTime; // 超时时间至少是10秒
		
		BigDecimal countFloat = AmountUtil.divide(new BigDecimal(overTime), new BigDecimal(DEFAULT_INTERVAL));
		int count = countFloat.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 循环的总次数
		
		Waitsinfo waitsinfo = null;
		for (int i = 0; i < count; i++) {
			waitsinfo = getWaitsinfo(refId, MsgType.IC_CARD_ACTIVE);
			
			Assert.notNull(waitsinfo, "命令表中没有命令类型为[" + MsgType.IC_CARD_ACTIVE + "]批次号[" + refId + "]的记录");
			//  查看该命令是否已经被处理
			Assert.notEquals(waitsinfo.getWebState(), WebState.DONE.getValue(), "IC卡激活登记簿中激活批次号[" + refId + "]的记录已被处理");
			
			// 如果处理成功的话则进行处理成功的相关操作
			if (WaitsinfoState.SUCCESS.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForActiveSuccess(icCardActive, waitsinfo);
			}
			// 如果处理失败的话则进行处理失败的相关操作
			else if (WaitsinfoState.DEALFAILED.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForActiveFailure(icCardActive, waitsinfo);
			} else {
				// 否则休息1秒
				this.waiteInterval();
			}
		}
		
		// 超时时的处理。要将原记录冲正掉,向后台发冲正
		return this.reversalActive(icCardActive, waitsinfo);
	}
	
	public IcCardActive noticeActiveSuccess(String activeId) throws BizException {
		Assert.notEmpty(activeId, "激活批次号不能为空");
		// 修改原激活记录
		IcCardActive active = new IcCardActive();
		active.setActiveBatchId(activeId);
		IcCardActive icCardActive= (IcCardActive) this.icCardActiveDAO.findByPk(activeId);
		Assert.notNull(icCardActive, "激活批次ID为[" + activeId + "]的记录不存在");
		
		icCardActive.setWriteCardFlag(Symbol.YES); //写卡成功
		
		this.releaseIcCardActive(icCardActive);
		
		return icCardActive;
	}
	
	//========================================= IC卡卡片参数修改 ===========================================================
	public Paginater findParaModifyPage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		return this.icCardParaModifyRegDAO.findPage(params, pageNumber, pageSize);
	}
	
	public IcCardParaModifyReg findParaModifyDetail(String id) {
		return (IcCardParaModifyReg) this.icCardParaModifyRegDAO.findByPk(id);
	}
	
	public JSONObject searchCardInfoForModify(String cardId) {
		
		JSONObject object = new JSONObject();
		
		try {
			Assert.notEmpty(cardId, "卡号不能为空");
			cardId = StringUtils.trim(cardId);
			if (cardId.length() > 19) {
				cardId = cardId.substring(0, 19);
			}
			
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "系统中没有卡号[" + cardId + "]的记录");
			
			CardSubClassDef subClass= (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(subClass, "卡号[" + cardId + "]所属的卡类型号[" + cardInfo.getCardSubclass() + "]已经不存在");
			
			IcTempPara para = (IcTempPara) this.icTempParaDAO.findByPk(subClass.getCardSubclass());
			Assert.notNull(para, "没有为卡号[" + cardId + "]所属的卡类型号[" + cardInfo.getCardSubclass() + "]配置IC卡个人化参数");
			
			IcCardExtroInfo icCardExtroInfo = (IcCardExtroInfo) this.icCardExtroInfoDAO.findByPk(cardId);
			Assert.notNull(icCardExtroInfo, "IC卡附加信息表中不存在卡号[" + cardId + "]的记录");
			
			object.put("cardId", cardId);
			object.put("cardClass", cardInfo.getCardClass());
			object.put("cardBin", cardInfo.getCardBin());
			object.put("cardClassName", cardInfo.getCardClassName());
			object.put("cardSubClass", cardInfo.getCardSubclass());
			object.put("cardSubClassName", subClass.getCardSubclassName());
			object.put("cardBranch", cardInfo.getCardIssuer());
			object.put("cardBranchName", NameTag.getBranchName(cardInfo.getCardIssuer()));
			
			object.put("autoDepositFlag", icCardExtroInfo.getAutoDepositFlag());
			object.put("autoDepositFlagName", icCardExtroInfo.getAutoDepositFlagName());
			object.put("autoDepositAmt", icCardExtroInfo.getAutoDepositAmt());
			object.put("oldBalanceLimit", icCardExtroInfo.getBalanceLimit());
			object.put("oldAmountLimit", icCardExtroInfo.getAmountLimit());
			
			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		return object;
	}
	
	public String addParaModifyReg(IcCardParaModifyReg icCardParaModifyReg,
			UserInfo user, String serialNo, String signature) throws BizException {
		Assert.notNull(icCardParaModifyReg, "IC卡卡片参数修改登记对象不能为空");
		Assert.notEmpty(icCardParaModifyReg.getRandomSessionId(), "随机数不能为空");
		Assert.notEmpty(icCardParaModifyReg.getCardId(), "卡号不能为空");
		
		Assert.isNull(icCardParaModifyRegDAO.findByRandomSessionId(icCardParaModifyReg.getRandomSessionId()), "请不要重复提交请求！");
		
		Assert.notEmpty(icCardParaModifyReg.getCardSn(), "卡序列号不能为空");
		Assert.notEmpty(icCardParaModifyReg.getArqc(), "交易密文ARQ不能为空");
		Assert.notEmpty(icCardParaModifyReg.getAqdt(), "arqc源数据ARQT不能为空");
		
		Assert.notNull(icCardParaModifyReg.getBalanceLimit(), "电子现金余额上限不能为空");
		Assert.notNull(icCardParaModifyReg.getAmountLimit(), "电子现金交易限额不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			
			// 待验签的数据 卡号 + 交易限额 + 随机数
			String info = icCardParaModifyReg.getCardId() + icCardParaModifyReg.getAmountLimit().setScale(2).toString() + icCardParaModifyReg.getRandomSessionId();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, signature, info), "验签失败");
		}
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(icCardParaModifyReg.getCardId());
		
		// 如果没有权限充值则报错
		if (!baseDataService.hasRightsToDeposit(user, cardInfo)){
			throw new BizException("没有权限修改卡["+ icCardParaModifyReg.getCardId() +"的卡片参数");
		}
		
		// 1. 在登记薄中插入记录
		icCardParaModifyReg.setCardBranch(cardInfo.getCardIssuer());
		icCardParaModifyReg.setAppOrgId(cardInfo.getAppOrgId());
		icCardParaModifyReg.setStatus(RegisterState.WAITEDEAL.getValue());
		icCardParaModifyReg.setUpdateUser(user.getUserId());
		icCardParaModifyReg.setUpdateTime(new Date());
		icCardParaModifyReg.setWriteCardFlag(Symbol.NO);
		String roleType = user.getRole().getRoleType();
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			icCardParaModifyReg.setBranchCode(user.getDeptId());
		} else {
			icCardParaModifyReg.setBranchCode(user.getBranchNo());
		}
		
		this.icCardParaModifyRegDAO.insert(icCardParaModifyReg);
		
		// 2. 发送报文
		MsgSender.sendMsg(MsgType.IC_CHANGE_PARA, NumberUtils.toLong(icCardParaModifyReg.getId()), user.getUserId());
		
		return icCardParaModifyReg.getId();
	}
	
	public IcCardParaModifyReg dealModifyPara(String refId) throws BizException {
		IcCardParaModifyReg paraModifyReg = (IcCardParaModifyReg) this.icCardParaModifyRegDAO.findByPk(refId);
		Assert.notNull(paraModifyReg, "ID为[" + refId + "]的IC卡卡片参数修改登记记录不存在");
		
		int overTime = NumberUtils.toInt(SysparamCache.getInstance().getIcDepositOverTime()); //超时时间
		overTime = overTime < 10 ? 10 : overTime; // 超时时间至少是10秒
		
		BigDecimal countFloat = AmountUtil.divide(new BigDecimal(overTime), new BigDecimal(DEFAULT_INTERVAL));
		int count = countFloat.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 循环的总次数
		
		Waitsinfo waitsinfo = null;
		for (int i = 0; i < count; i++) {
			waitsinfo = getWaitsinfo(refId, MsgType.IC_CHANGE_PARA);
			
			Assert.notNull(waitsinfo, "命令表中命令类型为[" + MsgType.IC_CHANGE_PARA + "]ID为[" + refId + "]的记录不存在");
			//  查看该命令是否已经被处理
			Assert.notEquals(waitsinfo.getWebState(), WebState.DONE.getValue(), "IC卡电子现金充值批次ID为[" + refId + "]的记录已被处理");
			
			// 如果处理成功的话则进行处理成功的相关操作
			if (WaitsinfoState.SUCCESS.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForParaModifySuccess(paraModifyReg, waitsinfo);
			}
			// 如果处理失败的话则进行处理失败的相关操作
			else if (WaitsinfoState.DEALFAILED.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForParaModifyFailure(paraModifyReg, waitsinfo);
			} else {
				// 否则休息1秒
				this.waiteInterval();
			}
		}
		
		return this.dealForParaModifyFailure(paraModifyReg, waitsinfo);
	}
	
	public IcCardParaModifyReg noticeModifyParaSuccess(String id) throws BizException {
		
		IcCardParaModifyReg paraModifyReg = (IcCardParaModifyReg) this.icCardParaModifyRegDAO.findByPk(id);
		Assert.notNull(paraModifyReg, "ID为[" + id + "]的IC卡卡片参数修改登记记录不存在");
		Assert.notEmpty(paraModifyReg.getCardId(), "登记ID为[" + id + "]的IC卡卡片参数修改记录中的卡号字段不能为空");
		
		paraModifyReg.setWriteCardFlag(Symbol.YES);
		paraModifyReg.setStatus(RegisterState.NORMAL.getValue());
		this.icCardParaModifyRegDAO.update(paraModifyReg);
		
		IcCardExtroInfo icCardExtroInfo = (IcCardExtroInfo) this.icCardExtroInfoDAO.findByPk(paraModifyReg.getCardId());
		Assert.notNull(icCardExtroInfo, "IC卡附加信息表中不存在卡号[" + paraModifyReg.getCardId() + "]的记录");
		
		icCardExtroInfo.setCardSubclass(paraModifyReg.getCardSubClass());
		icCardExtroInfo.setBalanceLimit(paraModifyReg.getBalanceLimit());
		icCardExtroInfo.setAmountLimit(paraModifyReg.getAmountLimit());
		icCardExtroInfo.setUpdateBy(paraModifyReg.getUpdateUser());
		icCardExtroInfo.setUpdateTime(new Date());
		
		this.icCardExtroInfoDAO.update(icCardExtroInfo);
		
		return paraModifyReg;
	}
	
	//========================================= IC卡换卡 =================================================================
	public Paginater findIcRenewCardRegPage(Map<String, Object> params,
			int pageNumber, int pageSize) throws BizException {
		return this.icRenewCardRegDAO.findPage(params, pageNumber, pageSize);
	}
	
	public IcRenewCardReg findIcRenewCardRegDetail(String id) {
		return (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(id);
	}
	
	public String addIcRenewCardReg(IcRenewCardReg icRenewCardReg,
			UserInfo user, String serialNo, String signature) throws BizException {
		Assert.notNull(icRenewCardReg, "IC卡换卡登记对象不能为空");
		Assert.notEmpty(icRenewCardReg.getRandomSessionId(), "随机数不能为空");
		Assert.notEmpty(icRenewCardReg.getOldCardId(), "旧卡卡号不能为空");
		Assert.notEmpty(icRenewCardReg.getNewCardId(), "新卡卡号不能为空");
		
		Assert.notEquals(icRenewCardReg.getOldCardId(), icRenewCardReg.getNewCardId(), "新旧卡号不能相同");
		
		Assert.isNull(icRenewCardRegDAO.findByRandomSessionId(icRenewCardReg.getRandomSessionId()), "请不要重复提交请求！");
		
		if (IcRenewCardType.CAN_READ_CARD.getValue().equals(icRenewCardReg.getRenewType())) {
			Assert.notEmpty(icRenewCardReg.getCardSn(), "卡序列号不能为空");
			Assert.notEmpty(icRenewCardReg.getArqc(), "交易密文ARQ不能为空");
			Assert.notEmpty(icRenewCardReg.getAqdt(), "arqc源数据ARQT不能为空");
			
			icRenewCardReg.setWriteCardFlag(Symbol.NO);
		}
		
		Assert.notNull(icRenewCardReg.getOldBalanceAmt(), "旧卡的电子现金余额不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致");
			Assert.notEmpty(signature, "签名后的数据不能为空");
			
			// 待验签的数据 旧卡卡号 + 旧卡电子现金余额 + 随机数
			String info = icRenewCardReg.getOldCardId() + icRenewCardReg.getOldBalanceAmt().toString() + icRenewCardReg.getRandomSessionId();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, signature, info), "验签失败");
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 检查卡号是否存在
		CardInfo oldCardInfo = (CardInfo) cardInfoDAO.findByPk(icRenewCardReg.getOldCardId());
		Assert.notNull(oldCardInfo, "旧卡号[" + icRenewCardReg.getOldCardId() + "]不存在。");
		
		CardInfo newCardInfo = (CardInfo) cardInfoDAO.findByPk(icRenewCardReg.getNewCardId());
		Assert.notNull(newCardInfo, "新卡号[" + icRenewCardReg.getNewCardId() + "]不存在。");
		
		//检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(roleType, user.getBranchNo(), oldCardInfo, "IC卡换卡");
		
		Assert.equals(newCardInfo.getAppOrgId(), user.getBranchNo(), "登录机构不是新卡的领卡机构，请输入正确的新卡号。");
		
		// 判断新旧卡号的卡BIN是否一致
		Assert.equals(oldCardInfo.getCardBin(), newCardInfo.getCardBin(), "新旧卡号卡BIN不一致，不能换卡。");
		// 判断新旧卡号卡种是否一致
		Assert.equals(oldCardInfo.getCardClass(), newCardInfo.getCardClass(),
				"旧卡[" + oldCardInfo.getCardClassName() + "]和新卡["+ newCardInfo.getCardClassName() + "]卡种类不同。");
		Assert.equals(oldCardInfo.getCardSubclass(), newCardInfo.getCardSubclass(), 
				"旧卡[" + oldCardInfo.getCardClassName() + "]和新卡["+ newCardInfo.getCardClassName() + "]卡类型不同。");
		
		// 判断新旧卡号的状态是否正确
		/*boolean stateFlag = false;
		if (CardState.ACTIVE.getValue().equals(oldCardInfo.getCardStatus())
				|| CardState.LOSSREGISTE.getValue().equals(oldCardInfo.getCardStatus())) {
			stateFlag = true;
		}
		Assert.isTrue(stateFlag, "旧卡[" + icRenewCardReg.getOldCardId() + "]目前不是挂失或正常状态，不能换卡。");*/
		
		// 挂失换卡只能是挂失状态的
		if (IcRenewCardType.LOSS_CARD.getValue().equals(icRenewCardReg.getRenewType())) {
			Assert.equals(CardState.LOSSREGISTE.getValue(), oldCardInfo.getCardStatus(), 
					"旧卡[" + icRenewCardReg.getOldCardId() + "]目前不是挂失状态，不能做挂失换卡");
		} else {
			Assert.equals(CardState.ACTIVE.getValue(), oldCardInfo.getCardStatus(), 
					"旧卡[" + icRenewCardReg.getOldCardId() + "]目前不是“正常（已激活）”状态，不能换卡");
		}
		
		Assert.notEmpty(newCardInfo.getAppOrgId(), "新卡领卡机构为空，请选择其他新卡。");
		Assert.equals(newCardInfo.getCardStatus(), CardState.FORSALE.getValue(), "新卡不是领卡待售状态，请选择请他新卡。");
		
		// 1. 在登记薄中插入记录
		icRenewCardReg.setCardBranch(oldCardInfo.getCardIssuer());
		icRenewCardReg.setAcctId(oldCardInfo.getAcctId());
		icRenewCardReg.setAppOrgId(oldCardInfo.getAppOrgId());
		icRenewCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		icRenewCardReg.setUpdateUser(user.getUserId());
		icRenewCardReg.setUpdateTime(new Date());
		icRenewCardReg.setReversalFlag(ReversalFlag.WAITE_REVERSAL.getValue());
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			icRenewCardReg.setBranchCode(user.getDeptId());
		} else {
			icRenewCardReg.setBranchCode(user.getBranchNo());
		}
		
		this.icRenewCardRegDAO.insert(icRenewCardReg);
		
		// 2. 发送报文
		MsgSender.sendMsg(MsgType.IC_RENEW_CARD, NumberUtils.toLong(icRenewCardReg.getId()), user.getUserId());
		
		return icRenewCardReg.getId();
	}
	
	public IcRenewCardReg dealIcRenewCardReg(String refId) throws BizException {
		IcRenewCardReg icRenewCardReg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(refId);
		Assert.notNull(icRenewCardReg, "登记薄中找不到批次号为[" + refId + "]的换卡记录");
		
		int overTime = NumberUtils.toInt(SysparamCache.getInstance().getIcDepositOverTime()); //超时时间
		overTime = overTime < 10 ? 10 : overTime; // 超时时间至少是10秒
		
		BigDecimal countFloat = AmountUtil.divide(new BigDecimal(overTime), new BigDecimal(DEFAULT_INTERVAL));
		int count = countFloat.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 循环的总次数
		
		Waitsinfo waitsinfo = null;
		for (int i = 0; i < count; i++) {
			waitsinfo = getWaitsinfo(refId, MsgType.IC_RENEW_CARD);
			
			Assert.notNull(waitsinfo, "命令表中命令类型为[" + MsgType.IC_RENEW_CARD + "]ID为[" + refId + "]的记录不存在");
			//  查看该命令是否已经被处理
			Assert.notEquals(waitsinfo.getWebState(), WebState.DONE.getValue(), "IC卡换卡批次ID为[" + refId + "]的记录已被处理");
			
			// 如果处理成功的话则进行处理成功的相关操作
			if (WaitsinfoState.SUCCESS.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForRenewCardSuccess(icRenewCardReg, waitsinfo);
			}
			// 如果处理失败的话则进行处理失败的相关操作
			else if (WaitsinfoState.DEALFAILED.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForRenewCardFailure(icRenewCardReg, waitsinfo);
			} else {
				// 否则休息1秒
				this.waiteInterval();
			}
		}
		
		// 超时时的处理。要将原记录冲正掉,向后台发冲正
		IcCardReversal icCardReversal = new IcCardReversal();
		
		icCardReversal.setRefId(icRenewCardReg.getId());
		icCardReversal.setReversalType(IcReversalType.SWAP_CARD.getValue());
		icCardReversal.setReversalBranch(icRenewCardReg.getBranchCode());
		icCardReversal.setUpdateUser(icRenewCardReg.getUpdateUser());
		icCardReversal.setRemark("后台处理超时，发起冲正");
		IcRenewCardReg reg = (IcRenewCardReg) this.addIcCardReversal(icCardReversal, waitsinfo);
		
		return reg;
	}
	
	public void noticeIcRenewCardSuccess(String refId) throws BizException {
		IcRenewCardReg icRenewCardReg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(refId);
		Assert.notNull(icRenewCardReg, "ID为[" + refId + "]的IC卡换卡登记记录不存在");
		Assert.notEmpty(icRenewCardReg.getNewCardId(), "登记ID为[" + refId + "]的IC卡换卡登记簿录中的新卡卡号字段不能为空");
		Assert.notEmpty(icRenewCardReg.getOldCardId(), "登记ID为[" + refId + "]的IC卡换卡登记簿录中的旧卡卡号字段不能为空");
		
		if (IcRenewCardType.CAN_READ_CARD.getValue().equals(icRenewCardReg.getRenewType())) {
			icRenewCardReg.setWriteCardFlag(Symbol.YES);
		}
		
		this.icRenewCardRegDAO.update(icRenewCardReg);
		
		// 更改新卡的卡库存表状态
		CardStockInfo cardStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(icRenewCardReg.getNewCardId());
		Assert.notNull(cardStockInfo, "卡库存表中没有新卡[" + icRenewCardReg.getNewCardId() + "]的记录");
		
		cardStockInfo.setCardStatus(CardStockState.SOLD_OUT.getValue());
		this.cardStockInfoDAO.update(cardStockInfo);
	}
	
	public String addIcCardReversal(IcCardReversal icCardReversal, 
			UserInfo user, IcReversalType icReversalType) throws BizException {
		Assert.notNull(icCardReversal, "IC卡冲正登记对象不能为空");
		
		Assert.notEmpty(icCardReversal.getRefId(), "要冲正的登记簿的相关ID不能为空");
		Assert.notNull(icReversalType, "冲正类型不能为空");
		
		// 换卡冲正
		if (IcReversalType.SWAP_CARD.equals(icReversalType)) {
			// 修改原换卡记录
			IcRenewCardReg reg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(icCardReversal.getRefId());
			Assert.notNull(reg, "批次号为[" + icCardReversal.getRefId() + "]的换卡登记记录不存在");
			
			reg.setStatus(RegisterState.DISABLE.getValue());
			reg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());

			if (IcRenewCardType.CAN_READ_CARD.getValue().equals(reg.getRenewType())) {
				reg.setWriteCardFlag(Symbol.NO); //写卡没成功
			}
			
			this.icRenewCardRegDAO.update(reg);
			
			icCardReversal.setReversalBranch(reg.getBranchCode());
		}
		// 销卡冲正
		else if (IcReversalType.CANCEL_CARD.equals(icReversalType)) {
			// 修改原销卡记录
			IcCancelCardReg reg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(icCardReversal.getRefId());
			Assert.notNull(reg, "批次号为[" + icCardReversal.getRefId() + "]的销卡登记记录不存在");
			
			reg.setStatus(RegisterState.DISABLE.getValue());
			reg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());

			if (IcCancelCardType.CAN_READ_CARD.getValue().equals(reg.getCancelType())) {
				reg.setWriteCardFlag(Symbol.NO);
			}
			
			this.icCancelCardRegDAO.update(reg);
			
			icCardReversal.setReversalBranch(reg.getBranchCode());
		}
		
		icCardReversal.setReversalType(icReversalType.getValue()); //冲正类型,换卡冲正01, 退卡冲正02
		icCardReversal.setUpdateUser(user.getUserId());
		icCardReversal.setReversalBranch(user.getBranchNo());
		icCardReversal.setRemark("Web端发生异常，发起冲正！原因可能是：进行发卡行认证或写卡失败。");
		
		icCardReversal = this.addIcCardReversalResult(icCardReversal);
		
		return icCardReversal.getBatchId();
	}
	
	//========================================= IC卡销卡 =================================================================
	public Paginater findIcCancelCardRegPage(Map<String, Object> params, 
			int pageNumber, int pageSize) throws BizException {
		return this.icCancelCardRegDAO.findPage(params, pageNumber, pageSize);
	}
	
	public IcCancelCardReg findIcCancelCardRegDetail(String id) {
		return (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(id);
	}
	
	public String addIcCancelCardReg(IcCancelCardReg icCancelCardReg,
			UserInfo user, String serialNo, String signature) throws BizException {
		Assert.notNull(icCancelCardReg, "IC卡销卡登记对象不能为空");
		Assert.notEmpty(icCancelCardReg.getRandomSessionId(), "随机数不能为空");
		Assert.notEmpty(icCancelCardReg.getCardId(), "卡号不能为空");
		
		Assert.isNull(icCancelCardRegDAO.findByRandomSessionId(icCancelCardReg.getRandomSessionId()), "请不要重复提交请求！");
		
		if (IcCancelCardType.CAN_READ_CARD.getValue().equals(icCancelCardReg.getCancelType())) {
			Assert.notEmpty(icCancelCardReg.getCardSn(), "卡序列号不能为空");
			Assert.notEmpty(icCancelCardReg.getArqc(), "交易密文ARQ不能为空");
			Assert.notEmpty(icCancelCardReg.getAqdt(), "arqc源数据ARQT不能为空");
			
			icCancelCardReg.setWriteCardFlag(Symbol.NO);
		}
		
		Assert.notNull(icCancelCardReg.getBalanceAmt(), "旧卡的电子现金余额不能为空");
		
		// 若需要验证签名，则证书绑定的用户必须和提交的用户一致
		if (StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES)) {
			Assert.isTrue(this.userService.checkCertUser(serialNo, user), "请确保证书绑定的用户与当前登录用户一致！");
			Assert.notEmpty(signature, "签名后的数据不能为空");
			
			// 待验签的数据 卡号 + 电子现金余额 + 随机数
			String info = icCancelCardReg.getCardId() + icCancelCardReg.getBalanceAmt().toString() + icCancelCardReg.getRandomSessionId();
			
			Assert.isTrue(this.userCertificateRevService.processUserSigVerify(serialNo, signature, info), "验签失败");
		}
		
		String roleType = user.getRole().getRoleType();
		
		// 检查卡号是否存在
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(icCancelCardReg.getCardId());
		Assert.notNull(cardInfo, "卡号[" + icCancelCardReg.getCardId() + "]不存在！");
		
		if (IcCancelCardType.LOSS_CARD.getValue().equals(icCancelCardReg.getCancelType())) {
			Assert.equals(cardInfo.getCardStatus(), CardState.LOSSREGISTE.getValue(), "卡号[" + icCancelCardReg.getCardId() + "]不是“挂失”状态，不能做挂失销卡！");
		} else {
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + icCancelCardReg.getCardId() + "]不是“正常(已激活)”状态，不能销卡！");
		}
		
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
		Assert.notNull(acctInfo, "卡号[" + icCancelCardReg.getCardId() + "]的账号不存在！");
		
		//检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(roleType, user.getBranchNo(), cardInfo, "IC卡销卡");
		
		// 1. 在登记薄中插入记录
		icCancelCardReg.setCardBranch(cardInfo.getCardIssuer());
		icCancelCardReg.setAcctId(cardInfo.getAcctId());
		icCancelCardReg.setAppOrgId(cardInfo.getAppOrgId());
		icCancelCardReg.setStatus(RegisterState.WAITEDEAL.getValue());
		icCancelCardReg.setUpdateUser(user.getUserId());
		icCancelCardReg.setUpdateTime(new Date());
		icCancelCardReg.setReversalFlag(ReversalFlag.WAITE_REVERSAL.getValue());
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			icCancelCardReg.setBranchCode(user.getDeptId());
		} else {
			icCancelCardReg.setBranchCode(user.getBranchNo());
		}
		
		this.icCancelCardRegDAO.insert(icCancelCardReg);
		
		// 2. 发送报文
		MsgSender.sendMsg(MsgType.IC_CANCEL_CARD, NumberUtils.toLong(icCancelCardReg.getId()), user.getUserId());
		
		return icCancelCardReg.getId();
	}
	
	public IcCancelCardReg dealIcCancelCardReg(String refId) throws BizException {
		IcCancelCardReg icCancelCardReg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(refId);
		Assert.notNull(icCancelCardReg, "登记薄中找不到批次号为[" + refId + "]的销卡记录");
		
		int overTime = NumberUtils.toInt(SysparamCache.getInstance().getIcDepositOverTime()); //超时时间
		overTime = overTime < 10 ? 10 : overTime; // 超时时间至少是10秒
		
		BigDecimal countFloat = AmountUtil.divide(new BigDecimal(overTime), new BigDecimal(DEFAULT_INTERVAL));
		int count = countFloat.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 循环的总次数
		
		Waitsinfo waitsinfo = null;
		for (int i = 0; i < count; i++) {
			waitsinfo = getWaitsinfo(refId, MsgType.IC_CANCEL_CARD);
			
			Assert.notNull(waitsinfo, "命令表中命令类型为[" + MsgType.IC_RENEW_CARD + "]ID为[" + refId + "]的记录不存在");
			//  查看该命令是否已经被处理
			Assert.notEquals(waitsinfo.getWebState(), WebState.DONE.getValue(), "IC卡销卡批次ID为[" + refId + "]的记录已被处理");
			
			// 如果处理成功的话则进行处理成功的相关操作
			if (WaitsinfoState.SUCCESS.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForCancelCardSuccess(icCancelCardReg, waitsinfo);
			}
			// 如果处理失败的话则进行处理失败的相关操作
			else if (WaitsinfoState.DEALFAILED.getValue().equals(waitsinfo.getStatus())) {
				return this.dealForCancelCardFailure(icCancelCardReg, waitsinfo);
			} else {
				// 否则休息1秒
				this.waiteInterval();
			}
		}
		
		// 超时时的处理。要将原记录冲正掉,向后台发冲正
		IcCardReversal icCardReversal = new IcCardReversal();
		
		icCardReversal.setRefId(icCancelCardReg.getId());
		icCardReversal.setReversalType(IcReversalType.SWAP_CARD.getValue());
		icCardReversal.setReversalBranch(icCancelCardReg.getBranchCode());
		icCardReversal.setUpdateUser(icCancelCardReg.getUpdateUser());
		icCardReversal.setRemark("后台处理超时，发起冲正");
		
		IcCancelCardReg reg = (IcCancelCardReg) this.addIcCardReversal(icCardReversal, waitsinfo);
		
		return reg;
	}
	
	public void noticeIcCancelCardSuccess(String refId) throws BizException {
		IcCancelCardReg icCancelCardReg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(refId);
		Assert.notNull(icCancelCardReg, "ID为[" + refId + "]的IC卡销卡登记记录不存在");
		Assert.notEmpty(icCancelCardReg.getCardId(), "登记ID为[" + refId + "]的IC卡换卡登记簿录中的卡号字段不能为空");
		
		if (IcCancelCardType.CAN_READ_CARD.getValue().equals(icCancelCardReg.getCancelType())) {
			icCancelCardReg.setWriteCardFlag(Symbol.YES);
		}
		
		this.icCancelCardRegDAO.update(icCancelCardReg);
	}
	
	//========================================= IC卡充值处理开始 ===========================================================
	/**
	 *  后台处理成功时将IC卡电子现金充值登记薄的状态改为处理成功
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashDepositReg dealForSuccess(IcEcashDepositReg depositReg, Waitsinfo waitsinfo) throws BizException {
		IcEcashDepositReg reg = this.lockDepsosit(depositReg);
		
		Assert.notNull(reg, "充值ID为[" + depositReg.getDepositBatchId() + "]的记录不存在");
		Assert.notEmpty(reg.getChkRespCode(), "校验响应码不能为空");
		Assert.notEmpty(reg.getArpc(), "授权响应密文ARPC不能为空");
		Assert.notEmpty(reg.getWriteRespCode(), "写卡响应码不能为空");
		Assert.notEmpty(reg.getWriteScript(), "写卡脚本不能为空");
		
		reg.setStatus(RegisterState.NORMAL.getValue());
//		reg.setRemark(waitsinfo.getNote());
		
		this.releaseDepositLock(reg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return reg;
	}
	
	/**
	 * 处理失败时修改登记薄的状态为失败
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashDepositReg dealForFailure(IcEcashDepositReg depositReg, Waitsinfo waitsinfo) throws BizException {
		IcEcashDepositReg reg = this.lockDepsosit(depositReg);
		
		Assert.notNull(reg, "充值ID为[" + depositReg.getDepositBatchId() + "]的记录不存在");
		
		reg.setStatus(RegisterState.DISABLE.getValue());
		reg.setRemark(waitsinfo.getNote());
		this.releaseDepositLock(reg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return reg;
	}
	
	/**
	 *  锁定IC卡电子现金充值登记薄
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashDepositReg lockDepsosit(IcEcashDepositReg depositReg) throws BizException {
		try {
			depositReg = (IcEcashDepositReg) this.icEcashDepositRegDAO.findByPkWithLock(depositReg.getDepositBatchId());
		} catch (Exception e) {
			String msg = "锁定IC卡电子现金充值登记薄充值批次ID为[" + depositReg.getDepositBatchId() + "]的记录失败，原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
		return depositReg;
	}
	
	/**
	 * 将IC卡电子现金充值登记簿记录提交
	 * @param depositReg
	 * @throws BizException
	 */
	private void releaseDepositLock(IcEcashDepositReg depositReg) throws BizException {
		try {
			this.icEcashDepositRegDAO.update(depositReg);
		} catch (Exception e) {
			String msg = "更新IC卡电子现金充值登记簿，释放锁失败。原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
	}
	
	/**
	 * 冲正掉该笔充值记录
	 * 
	 * @throws BizException
	 */
	private IcEcashDepositReg reversal(IcEcashDepositReg reg, Waitsinfo waitsinfo) throws BizException {
		// 1.修改命令表的状态为失败,web处理状态为已处理
		this.lockWaitsinfo(waitsinfo);
		
		waitsinfo.setStatus(WaitsinfoState.DEALFAILED.getValue());
		waitsinfo.setWebState(WebState.DONE.getValue());
		
		this.releaseLock(waitsinfo);
		
		// 2.修改原充值登记薄的记录 
		IcEcashDepositReg depositReg = this.lockDepsosit(reg);
		Assert.notNull(depositReg, "充值ID为[" + reg.getDepositBatchId() + "]的记录不存在");
		
		depositReg.setWriteCardFlag(Symbol.NO); //写卡没成功
		depositReg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
		depositReg.setStatus(RegisterState.DISABLE.getValue());
		this.releaseDepositLock(depositReg);
		
		// 3.在冲正登记簿中插入一条记录，同时向后台发送命令
		IcEcashReversal ecashReversal = new IcEcashReversal();
		
		ecashReversal.setDepositBatchId(depositReg.getDepositBatchId());
		ecashReversal.setCardId(depositReg.getCardId());
		ecashReversal.setAmt(depositReg.getDepositAmt());
		ecashReversal.setUpdateUser(depositReg.getUpdateUser());
		ecashReversal.setReversalBranch(depositReg.getDepositBranch());
		ecashReversal.setReversalType(ReversalType.DEPOSIT.getValue()); //充值冲正
		
		this.addReversal(ecashReversal);
		
		return depositReg;
	}
	
	/**
	 * 在冲正登记簿中插入记录，同时向后台发送命令
	 * 
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcEcashReversal addReversal(IcEcashReversal ecashReversal) throws BizException {

		ecashReversal.setStatus(RegisterState.WAITEDEAL.getValue());
		ecashReversal.setUpdateTime(new Date());
		ecashReversal.setAutoFlag(Symbol.YES);
		
		this.icEcashReversalDAO.insert(ecashReversal);
		
		MsgSender.sendMsg(MsgType.IC_ECASH_REVERSAL, NumberUtils.toLong(ecashReversal.getBatchId()), ecashReversal.getUpdateUser());
		
		return ecashReversal;
	}
	//==================================================IC卡充值处理结束===================================================

	//==================================================IC卡激活处理开始===================================================
	/**
	 *  后台处理成功时将IC卡激活登记薄的状态改为处理成功
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcCardActive dealForActiveSuccess(IcCardActive icCardActive, Waitsinfo waitsinfo) throws BizException {
		IcCardActive active = this.lockIcCardActive(icCardActive);
		
		Assert.notNull(active, "激活批次号为[" + icCardActive.getActiveBatchId() + "]的记录不存在");
		Assert.notEmpty(active.getChkRespCode(), "校验响应码不能为空");
		Assert.notEmpty(active.getArpc(), "授权响应密文ARPC不能为空");
		
		active.setStatus(RegisterState.NORMAL.getValue());
//		reg.setRemark(waitsinfo.getNote());
		
		this.releaseIcCardActive(active);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return active;
	}
	
	/**
	 * 处理失败时修改登记薄的状态为失败
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcCardActive dealForActiveFailure(IcCardActive icCardActive, Waitsinfo waitsinfo) throws BizException {
		IcCardActive reg = this.lockIcCardActive(icCardActive);
		
		Assert.notNull(reg, "批次号为[" + icCardActive.getActiveBatchId() + "]的记录不存在");
		
		reg.setStatus(RegisterState.DISABLE.getValue());
		reg.setRemark(waitsinfo.getNote());
		this.releaseIcCardActive(reg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return reg;
	}
	
	/**
	 *  锁定IC卡激活登记薄
	 * @param depositReg
	 * @return
	 * @throws BizException
	 */
	private IcCardActive lockIcCardActive(IcCardActive icCardActive) throws BizException {
		try {
			icCardActive = (IcCardActive) this.icCardActiveDAO.findByPkWithLock(icCardActive.getActiveBatchId());
		} catch (Exception e) {
			String msg = "锁定IC卡激活或余额补登登记薄中批次号为[" + icCardActive.getActiveBatchId() + "]的记录失败，原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
		return icCardActive;
	}
	
	/**
	 * 将IC卡激活登记簿记录提交
	 * @param depositReg
	 * @throws BizException
	 */
	private void releaseIcCardActive(IcCardActive icCardActive) throws BizException {
		try {
			this.icCardActiveDAO.update(icCardActive);
		} catch (Exception e) {
			String msg = "更新IC卡激活或余额补登登记簿，释放锁失败。原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
	}
	
	/**
	 * 冲正掉该笔充值记录
	 * 
	 * @throws BizException
	 */
	private IcCardActive reversalActive(IcCardActive active, Waitsinfo waitsinfo) throws BizException {
		// 1.修改命令表的状态为失败,web处理状态为已处理
		this.lockWaitsinfo(waitsinfo);
		
		waitsinfo.setStatus(WaitsinfoState.DEALFAILED.getValue());
		waitsinfo.setWebState(WebState.DONE.getValue());
		
		this.releaseLock(waitsinfo);
		
		// 2.修改原激活登记薄的记录 
		IcCardActive icCardActive = this.lockIcCardActive(active);
		Assert.notNull(icCardActive, "激活批次号为[" + active.getActiveBatchId() + "]的记录不存在");
		
		icCardActive.setWriteCardFlag(Symbol.NO); //写卡没成功
		icCardActive.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
		icCardActive.setStatus(RegisterState.DISABLE.getValue());
		this.releaseIcCardActive(icCardActive);
		
		// 3.在冲正登记簿中插入一条记录，同时向后台发送命令
		IcEcashReversal ecashReversal = new IcEcashReversal();
		
		ecashReversal.setDepositBatchId(icCardActive.getActiveBatchId());
		ecashReversal.setCardId(icCardActive.getCardId());
		ecashReversal.setAmt(icCardActive.getLastBalance());
		ecashReversal.setUpdateUser(icCardActive.getUpdateUser());
		ecashReversal.setReversalBranch(icCardActive.getActiveBranch());
		ecashReversal.setReversalType(ReversalType.FILL_UP.getValue()); //充值冲正
		
		this.addReversal(ecashReversal);
		
		return icCardActive;
	}
	//==================================================IC卡激活处理结束 =======================================================
	
	//================================================= IC卡卡片参数修改开始 ====================================================
	private IcCardParaModifyReg dealForParaModifySuccess(IcCardParaModifyReg paraModifyReg, Waitsinfo waitsinfo) throws BizException {
		paraModifyReg = (IcCardParaModifyReg) this.icCardParaModifyRegDAO.findByPk(paraModifyReg.getId());
		Assert.notNull(paraModifyReg, "ID为[" + paraModifyReg.getId() + "]的IC卡卡片参数修改登记记录不存在");
		
		Assert.notEmpty(paraModifyReg.getChkRespCode(), "校验响应码不能为空");
		Assert.notEmpty(paraModifyReg.getArpc(), "授权响应密文ARPC不能为空");
		Assert.notEmpty(paraModifyReg.getWriteRespCode(), "写卡响应码不能为空");
		Assert.notEmpty(paraModifyReg.getWriteScript(), "写卡脚本不能为空");
		
		paraModifyReg.setStatus(RegisterState.NORMAL.getValue());
		
		this.icCardParaModifyRegDAO.update(paraModifyReg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return paraModifyReg;
	}

	private IcCardParaModifyReg dealForParaModifyFailure(IcCardParaModifyReg paraModifyReg, Waitsinfo waitsinfo) throws BizException {
		paraModifyReg = (IcCardParaModifyReg) this.icCardParaModifyRegDAO.findByPk(paraModifyReg.getId());
		Assert.notNull(paraModifyReg, "ID为[" + paraModifyReg.getId() + "]的IC卡卡片参数修改登记记录不存在");
		
		paraModifyReg.setStatus(RegisterState.DISABLE.getValue());
		// 不为空，则是处理失败
		if (waitsinfo != null) {
			paraModifyReg.setRemark(waitsinfo.getNote());
			
			waitsinfo = this.lockWaitsinfo(waitsinfo);
			waitsinfo.setWebState(WebState.DONE.getValue());
			this.releaseLock(waitsinfo);
		} else {
			paraModifyReg.setRemark("处理超时！");
		}
		this.icCardParaModifyRegDAO.update(paraModifyReg);
		
		return paraModifyReg;
	}
	//================================================= IC卡卡片参数修改结束 ====================================================
	
	//================================================= IC卡换卡操作开始 =======================================================
	private IcRenewCardReg dealForRenewCardSuccess(IcRenewCardReg icRenewCardReg, Waitsinfo waitsinfo) throws BizException {
		icRenewCardReg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(icRenewCardReg.getId());
		Assert.notNull(icRenewCardReg, "ID为[" + icRenewCardReg.getId() + "]的IC卡换卡登记记录不存在");
		
		if (IcRenewCardType.CAN_READ_CARD.getValue().equals(icRenewCardReg.getRenewType())) {
			Assert.notEmpty(icRenewCardReg.getChkRespCode(), "校验响应码不能为空");
			Assert.notEmpty(icRenewCardReg.getArpc(), "授权响应密文ARPC不能为空");
			Assert.notEmpty(icRenewCardReg.getWriteRespCode(), "写卡响应码不能为空");
			Assert.notEmpty(icRenewCardReg.getWriteScript(), "写卡脚本不能为空");
		}
		
		icRenewCardReg.setStatus(RegisterState.NORMAL.getValue());
		
		this.icRenewCardRegDAO.update(icRenewCardReg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return icRenewCardReg;
	}

	private IcRenewCardReg dealForRenewCardFailure(IcRenewCardReg icRenewCardReg, Waitsinfo waitsinfo) throws BizException {
		icRenewCardReg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(icRenewCardReg.getId());
		Assert.notNull(icRenewCardReg, "ID为[" + icRenewCardReg.getId() + "]的IC卡换卡登记记录不存在");
		
		icRenewCardReg.setStatus(RegisterState.DISABLE.getValue());
		// 不为空，则是处理失败
		if (waitsinfo != null) {
			icRenewCardReg.setRemark(waitsinfo.getNote());
			
			waitsinfo = this.lockWaitsinfo(waitsinfo);
			waitsinfo.setWebState(WebState.DONE.getValue());
			this.releaseLock(waitsinfo);
		} else {
			icRenewCardReg.setRemark("处理超时！");
		}
		this.icRenewCardRegDAO.update(icRenewCardReg);
		
		return icRenewCardReg;
	}
	//================================================= IC卡换卡操作结束 =======================================================

	//================================================= IC卡销卡操作结束 =======================================================
	private IcCancelCardReg dealForCancelCardSuccess(IcCancelCardReg icCancelCardReg, Waitsinfo waitsinfo) throws BizException {
		icCancelCardReg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(icCancelCardReg.getId());
		Assert.notNull(icCancelCardReg, "ID为[" + icCancelCardReg.getId() + "]的IC卡销卡登记记录不存在");
		
		if (IcCancelCardType.CAN_READ_CARD.getValue().equals(icCancelCardReg.getCancelType())) {
			Assert.notEmpty(icCancelCardReg.getChkRespCode(), "校验响应码不能为空");
			Assert.notEmpty(icCancelCardReg.getArpc(), "授权响应密文ARPC不能为空");
			Assert.notEmpty(icCancelCardReg.getWriteRespCode(), "写卡响应码不能为空");
			Assert.notEmpty(icCancelCardReg.getWriteScript(), "写卡脚本不能为空");
		}
		
		icCancelCardReg.setStatus(RegisterState.NORMAL.getValue());
			
		
		this.icCancelCardRegDAO.update(icCancelCardReg);
		
		waitsinfo = this.lockWaitsinfo(waitsinfo);
		waitsinfo.setWebState(WebState.DONE.getValue());
		this.releaseLock(waitsinfo);
		
		return icCancelCardReg;
	}

	private IcCancelCardReg dealForCancelCardFailure(IcCancelCardReg icCancelCardReg, Waitsinfo waitsinfo) throws BizException {
		icCancelCardReg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(icCancelCardReg.getId());
		Assert.notNull(icCancelCardReg, "ID为[" + icCancelCardReg.getId() + "]的IC卡销卡登记记录不存在");
		
		icCancelCardReg.setStatus(RegisterState.DISABLE.getValue());
		// 不为空，则是处理失败
		if (waitsinfo != null) {
			icCancelCardReg.setRemark(waitsinfo.getNote());
			
			waitsinfo = this.lockWaitsinfo(waitsinfo);
			waitsinfo.setWebState(WebState.DONE.getValue());
			this.releaseLock(waitsinfo);
		} else {
			icCancelCardReg.setRemark("处理超时！");
		}
		this.icCancelCardRegDAO.update(icCancelCardReg);
		
		return icCancelCardReg;
	}
	//================================================= IC卡销卡操作结束 =======================================================
	
	//==================================================冲正处理===============================================================
	private Object addIcCardReversal(IcCardReversal icCardReversal, Waitsinfo waitsinfo) throws BizException {
		Object object = null;
		
		// 1.修改命令表的状态为失败,web处理状态为已处理
		this.lockWaitsinfo(waitsinfo);
		
		waitsinfo.setStatus(WaitsinfoState.DEALFAILED.getValue());
		waitsinfo.setWebState(WebState.DONE.getValue());
		
		this.releaseLock(waitsinfo);
		
		// 2.修改登记薄的记录 
		if (IcReversalType.SWAP_CARD.getValue().equals(icCardReversal.getReversalType())) {
			// 换卡  修改原换卡记录
			IcRenewCardReg icRenewCardReg = (IcRenewCardReg) this.icRenewCardRegDAO.findByPk(icCardReversal.getRefId());
			Assert.notNull(icRenewCardReg, "换卡登记簿中批次号为[" + icCardReversal.getRefId() + "]的记录不存在");
			
			if (IcRenewCardType.CAN_READ_CARD.getValue().equals(icRenewCardReg.getRenewType())) {
				icRenewCardReg.setWriteCardFlag(Symbol.NO); //写卡没成功
			}
			
			icRenewCardReg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
			icRenewCardReg.setStatus(RegisterState.DISABLE.getValue());
			
			this.icRenewCardRegDAO.update(icRenewCardReg);
			
			object = icRenewCardReg;
		}
		else if (IcReversalType.CANCEL_CARD.getValue().equals(icCardReversal.getReversalType())) {
			// 销卡  修改原销卡记录
			IcCancelCardReg icCancelCardReg = (IcCancelCardReg) this.icCancelCardRegDAO.findByPk(icCardReversal.getRefId());
			Assert.notNull(icCancelCardReg, "销卡登记薄中批次号为[" + icCardReversal.getRefId() + "]的记录不存在");
			
			if (IcCancelCardType.CAN_READ_CARD.getValue().equals(icCancelCardReg.getCancelType())) {
				icCancelCardReg.setWriteCardFlag(Symbol.NO);
			}
			
			icCancelCardReg.setReversalFlag(ReversalFlag.WAITE_DEAL.getValue());
			icCancelCardReg.setStatus(RegisterState.DISABLE.getValue());
			
			this.icCancelCardRegDAO.update(icCancelCardReg);
			
			object = icCancelCardReg;
		}
		
		this.addIcCardReversalResult(icCardReversal);
		
		return object;
	}
	
	/**
	 * 在冲正登记簿中插入一条记录，同时向后台发送命令
	 * @param icCardReversal
	 * @return
	 * @throws BizException
	 */
	private IcCardReversal addIcCardReversalResult(IcCardReversal icCardReversal) throws BizException {
		icCardReversal.setStatus(RegisterState.WAITEDEAL.getValue());
		icCardReversal.setUpdateTime(new Date());
		icCardReversal.setAutoFlag(Symbol.YES);
		
		this.icCardReversalDAO.insert(icCardReversal);
		
		MsgSender.sendMsg(MsgType.IC_CARD_REVERSAL, NumberUtils.toLong(icCardReversal.getBatchId()), icCardReversal.getUpdateUser());
		
		return icCardReversal;
	}
	
	//==================================================命令表的操作开始 ========================================================
	/**
	 * 锁定命令表
	 * @param waitsinfo
	 * @return
	 * @throws BizException
	 */
	private Waitsinfo lockWaitsinfo(Waitsinfo waitsinfo) throws BizException {
		try {
			waitsinfo = (Waitsinfo) this.waitsinfoDAO.findByPkWithLock(waitsinfo.getId());
		} catch (Exception e) {
			String msg = "锁定命令表中ID为[" + waitsinfo.getId() + "]的命令失败，原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
		return waitsinfo;
	}
	
	/**
	 *  释放锁
	 * @param waitsinfo
	 * @throws BizException
	 */
	private void releaseLock(Waitsinfo waitsinfo) throws BizException {
		try {
			this.waitsinfoDAO.update(waitsinfo);
		} catch (Exception e) {
			String msg = "更新命令表，释放锁失败。原因：" + e.getMessage();
			logger.error(msg);
			throw new BizException(msg);
		}
	}
	
	/**
	 * 取得命令表的信息
	 * @param refId
	 * @return
	 */
	private Waitsinfo getWaitsinfo(String refId, String msgType) {
		return this.waitsinfoDAO.findWaitsinfo(msgType, Long.parseLong(refId));
	}

	/**
	 * 休息一下
	 * @throws BizException
	 */
	private void waiteInterval() throws BizException {
		// 否则则继续轮询
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			String msg = "调用sleep方法时发生InterruptedException异常";
			logger.error(msg, e);
			throw new BizException(msg);
		}
	}
	//==================================================命令表的操作结束 ====================================================
}
