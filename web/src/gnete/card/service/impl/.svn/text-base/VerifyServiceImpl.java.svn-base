package gnete.card.service.impl;

import flink.util.AmountUtil;
import gnete.card.dao.BranchSellAmtDAO;
import gnete.card.dao.BranchSellChgDAO;
import gnete.card.dao.BranchSharesMSetDAO;
import gnete.card.dao.CardIssuerFeeMSetDAO;
import gnete.card.dao.CenterProxySharesMSetDAO;
import gnete.card.dao.CenterTermFeeMSetDAO;
import gnete.card.dao.CenterTermRepFeeMSetDAO;
import gnete.card.dao.ChlFeeMSetDAO;
import gnete.card.dao.MerchFeeMSetDAO;
import gnete.card.dao.MerchProxySharesMSetDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.dao.ReleaseCardFeeMSetDAO;
import gnete.card.dao.SaleProxyRtnMSetDAO;
import gnete.card.dao.SignCardAccListDAO;
import gnete.card.entity.BaseMSet;
import gnete.card.entity.BaseSharesMSet;
import gnete.card.entity.BranchSellAmt;
import gnete.card.entity.BranchSellAmtKey;
import gnete.card.entity.BranchSellChg;
import gnete.card.entity.BranchSharesMSet;
import gnete.card.entity.CardIssuerFeeMSet;
import gnete.card.entity.CardIssuerFeeMSetKey;
import gnete.card.entity.CenterProxySharesMSet;
import gnete.card.entity.CenterTermFeeMSet;
import gnete.card.entity.CenterTermRepFeeMSet;
import gnete.card.entity.ChlFeeMSet;
import gnete.card.entity.ChlFeeMSetKey;
import gnete.card.entity.MerchFeeMSet;
import gnete.card.entity.MerchProxySharesMSet;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.ReleaseCardFeeMSet;
import gnete.card.entity.SaleProxyRtnMSet;
import gnete.card.entity.SignCardAccList;
import gnete.card.entity.SignCardAccListKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.SignCardAccListState;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.SellType;
import gnete.card.entity.type.VerifyType;
import gnete.card.service.VerifyService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("verifyService")
public class VerifyServiceImpl implements VerifyService {

	@Autowired
	private MerchProxySharesMSetDAO merchProxySharesMSetDAO;
	@Autowired
	private MerchFeeMSetDAO merchFeeMSetDAO;
	// @Autowired
	// private CardSaleDSetDAO cardSaleDSetDAO;
	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private SaleProxyRtnMSetDAO saleProxyRtnMSetDAO;
	@Autowired
	private ReleaseCardFeeMSetDAO releaseCardFeeMSetDAO;
	@Autowired
	private BranchSharesMSetDAO branchSharesMSetDAO;
	@Autowired
	private CenterProxySharesMSetDAO centerProxySharesMSetDAO;
	@Autowired
	private CenterTermFeeMSetDAO centerTermFeeMSetDAO;
	@Autowired
	private CenterTermRepFeeMSetDAO centerTermRepFeeMSetDAO;
	@Autowired
	private SignCardAccListDAO signCardAccListDAO;
	@Autowired
	private BranchSellAmtDAO branchSellAmtDAO;
	@Autowired
	private BranchSellChgDAO branchSellChgDAO;
	@Autowired
	private CardIssuerFeeMSetDAO cardIssuerFeeMSetDAO;
	@Autowired
	private ChlFeeMSetDAO chlFeeMSetDAO;

	public boolean verifyCardBranch(MerchTransDSet merchTransDSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		// 发卡机构售卡核销：售卡代理将售卡所得到的钱给发卡机构
		Assert.notNull(merchTransDSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("payCode", merchTransDSet.getPayCode());
		keyMap.put("recvCode", merchTransDSet.getRecvCode());
		keyMap.put("setDate", merchTransDSet.getSetDate());
		keyMap.put("transType", merchTransDSet.getTransType());
		keyMap.put("curCode", merchTransDSet.getCurCode());
		MerchTransDSet mset = (MerchTransDSet) merchTransDSetDAO
				.findByPkWithLock(keyMap);
		Assert.notNull(mset, "要核销的记录已经不存在");

		this.verify(mset, merchTransDSet.getRecvAmt(), verifyType, sessionUser.getUserId(), merchTransDSet.getFeeAmount());
		
		this.resetCardBranchSellAmt(merchTransDSet);

		return this.merchTransDSetDAO.update(mset) > 0;
	}

	public boolean verifyMerchTrans(MerchTransDSet merchTransDSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		//商户交易核销：发卡机构将自己所发的卡在特约商户那发生的交易资金清算给商户
		Assert.notNull(merchTransDSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("payCode", merchTransDSet.getPayCode());
		keyMap.put("recvCode", merchTransDSet.getRecvCode());
		keyMap.put("setDate", merchTransDSet.getSetDate());
		keyMap.put("transType", merchTransDSet.getTransType());
		keyMap.put("curCode", merchTransDSet.getCurCode());
		MerchTransDSet mset = (MerchTransDSet) merchTransDSetDAO
				.findByPkWithLock(keyMap);
		Assert.notNull(mset, "要核销的记录已经不存在");

		verify(mset, merchTransDSet.getRecvAmt(), verifyType, sessionUser
				.getUserId(), mset.getFeeAmount());

		return this.merchTransDSetDAO.update(mset) > 0;
	}

	public boolean verifySaleProxyRtn(SaleProxyRtnMSet saleProxyRtnMSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		// 售卡代理返利核销：发卡机构将售卡返利资金给售卡代理
		Assert.notNull(saleProxyRtnMSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("proxyId", saleProxyRtnMSet.getProxyId());
		keyMap.put("orgId", saleProxyRtnMSet.getOrgId());
		keyMap.put("feeMonth", saleProxyRtnMSet.getFeeMonth());
		keyMap.put("curCode", saleProxyRtnMSet.getCurCode());
		SaleProxyRtnMSet mset = (SaleProxyRtnMSet) saleProxyRtnMSetDAO
				.findByPkWithLock(keyMap);
		Assert.notNull(mset, "要核销的记录已经不存在");

		verify(mset, saleProxyRtnMSet.getRecvAmt(), verifyType, sessionUser
				.getUserId(), new BigDecimal(0));

		return this.saleProxyRtnMSetDAO.update(mset) > 0;
	}

	public boolean verifyMerchFee(MerchFeeMSet merchFeeMSet, String verifyType,
			UserInfo sessionUser) throws BizException {
		// 商户手续费核销：商户给发卡机构手续费，同时会影响商户代理的分润
		Assert.notNull(merchFeeMSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("feeMonth", merchFeeMSet.getFeeMonth());
		keyMap.put("branchCode", merchFeeMSet.getBranchCode());
		keyMap.put("merchId", merchFeeMSet.getMerchId());
		MerchFeeMSet mset = (MerchFeeMSet) merchFeeMSetDAO.findByPkWithLock(keyMap);
		verify(mset, merchFeeMSet.getRecvAmt(), verifyType, sessionUser.getUserId(), new BigDecimal(0));

		// 如果商户手续核销的状态为最终状态则更改对应的商户代理分润核销的核销状态,发卡机构实际分润金额,和结转金额
		if (VerifyCheckState.EQUAL_VERIFY.getValue().equals(mset.getChkStatus())
				|| VerifyCheckState.DIFFER_VERIFY.getValue().equals(mset.getChkStatus())
				|| VerifyCheckState.DIFFER_WAITE.getValue().equals(mset.getChkStatus())) {
			Map<String, Object> sharesKeyMap = new HashMap<String, Object>();

			sharesKeyMap.put("feeMonth", mset.getFeeMonth());
			sharesKeyMap.put("branchCode", mset.getBranchCode());
			sharesKeyMap.put("merchId", mset.getMerchId());
			sharesKeyMap.put("merchProxy", mset.getMerchProxy());
			MerchProxySharesMSet sharesMSet = (MerchProxySharesMSet) merchProxySharesMSetDAO
					.findByPk(sharesKeyMap);
			Assert.notNull(sharesMSet, "对应商户代理分润核销记录不存在");
			
			BigDecimal precent = new BigDecimal("1.0000");
			// 如果本期手续费不等于0同时实收手续费小于本期手续费时,分润比例 = 实收手续费/本期手续费
			if (mset.getFeeAmt().intValue() != 0
					&& AmountUtil.lt(mset.getRecvAmt(), mset.getFeeAmt())) {
				precent = AmountUtil.divide(AmountUtil.format(mset.getRecvAmt()), mset.getFeeAmt());
			}
			modifyShares(sharesMSet, AmountUtil.format(mset.getRecvAmt()), precent);
			merchProxySharesMSetDAO.update(sharesMSet);
		}
		
		return this.merchFeeMSetDAO.update(mset) > 0;
	}

	public boolean verifyMerchProxyShares(
			MerchProxySharesMSet merchProxySharesMSet, String verifyType,
			UserInfo sessionUser) throws BizException {
		// 商户代理核销:发卡机构给商户分润
		Assert.notNull(merchProxySharesMSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("branchCode", merchProxySharesMSet.getBranchCode());
		keyMap.put("merchProxy", merchProxySharesMSet.getMerchProxy());
		keyMap.put("merchId", merchProxySharesMSet.getMerchId());
		keyMap.put("feeMonth", merchProxySharesMSet.getFeeMonth());
		MerchProxySharesMSet mset = (MerchProxySharesMSet) merchProxySharesMSetDAO
				.findByPkWithLock(keyMap);
		Assert.notNull(mset, "要核销的记录已经不存在");

		verfyShares(mset, sessionUser.getUserId());

		return this.merchProxySharesMSetDAO.update(mset) > 0;
	}

	public boolean verifyReleaseCardFee(ReleaseCardFeeMSet releaseCardFeeMSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		// 发卡机构手续费核销,运营中心收发卡机构的运营手续费，同时会影响分支机构，运营代理的分润及出机方，维护方的二次分润
		Assert.notNull(releaseCardFeeMSet, "要核销的对象不能为空");

		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("branchCode", releaseCardFeeMSet.getBranchCode());
		keyMap.put("feeMonth", releaseCardFeeMSet.getFeeMonth());
		ReleaseCardFeeMSet mset = (ReleaseCardFeeMSet) releaseCardFeeMSetDAO.findByPkWithLock(keyMap);
		Assert.notNull(mset, "要核销的记录已经不存在");
		
		verify(mset, releaseCardFeeMSet.getRecvAmt(), verifyType, sessionUser.getUserId(), new BigDecimal(0));
		
		this.updateCeneterShares(mset, releaseCardFeeMSet.getRecvAmt());

		return this.releaseCardFeeMSetDAO.update(mset) > 0;
	}

	public boolean verifyBranchShares(BranchSharesMSet branchSharesMSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		// 分支机构分润核销。运营中心给分支机构分润。
		Assert.notNull(branchSharesMSet, "要核销的对象不能为空");
		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("feeMonth", branchSharesMSet.getFeeMonth());
		keyMap.put("chlCode", branchSharesMSet.getChlCode());
		keyMap.put("branchCode", branchSharesMSet.getBranchCode());
		BranchSharesMSet mset = (BranchSharesMSet) branchSharesMSetDAO.findByPkWithLock(keyMap);

		Assert.notNull(mset, "要核销的记录已经不存在");
		verfyShares(mset, sessionUser.getUserId());

		return this.branchSharesMSetDAO.update(mset) > 0;
	}

	public boolean verifyCenterProxyShares(
			CenterProxySharesMSet centerProxySharesMSet, String verifyType,
			UserInfo sessionUser) throws BizException {
		// 运营机构代理商分润核销。运营中心给运营代理机构分润。
		Assert.notNull(centerProxySharesMSet, "要核销的对象不能为空");
		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("feeMonth", centerProxySharesMSet.getFeeMonth());
		keyMap.put("proxyId", centerProxySharesMSet.getProxyId());
		keyMap.put("branchCode", centerProxySharesMSet.getBranchCode());
		CenterProxySharesMSet mset = (CenterProxySharesMSet) centerProxySharesMSetDAO.findByPkWithLock(keyMap);

		Assert.notNull(mset, "要核销的记录已经不存在");
		verfyShares(mset, sessionUser.getUserId());

		return this.centerProxySharesMSetDAO.update(mset) > 0;
	}

	public boolean verifyCenterTermFee(CenterTermFeeMSet centerTermFeeMSet,
			String verifyType, UserInfo sessionUser) throws BizException {
		Assert.notNull(centerTermFeeMSet, "要核销的对象不能为空");
		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("termCode", centerTermFeeMSet.getTermCode());
		keyMap.put("branchCode", centerTermFeeMSet.getBranchCode());
		keyMap.put("feeMonth", centerTermFeeMSet.getFeeMonth());
		CenterTermFeeMSet mset = (CenterTermFeeMSet) centerTermFeeMSetDAO.findByPkWithLock(keyMap);

		Assert.notNull(mset, "要核销的记录已经不存在");
		verfyShares(mset, sessionUser.getUserId());

		return this.centerTermFeeMSetDAO.update(mset) > 0;
	}

	public boolean verifyCenterTermRepFee(
			CenterTermRepFeeMSet centerTermRepFeeMSet, String verifyType,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(centerTermRepFeeMSet, "要核销的对象不能为空");
		Map<String, Object> keyMap = new HashMap<String, Object>();

		keyMap.put("branchCode", centerTermRepFeeMSet.getBranchCode());
		keyMap.put("termCode", centerTermRepFeeMSet.getTermCode());
		keyMap.put("feeMonth", centerTermRepFeeMSet.getFeeMonth());
		CenterTermRepFeeMSet mset = (CenterTermRepFeeMSet) centerTermRepFeeMSetDAO.findByPkWithLock(keyMap);

		Assert.notNull(mset, "要核销的记录已经不存在");
		verfyShares(mset, sessionUser.getUserId());

		return this.centerTermRepFeeMSetDAO.update(mset) > 0;
	}

	public boolean verifySignCardAccList(SignCardAccList signCardAccList,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(signCardAccList, "要核销的对象不能为空");

		SignCardAccListKey key = new SignCardAccListKey();

		key.setAccMonth(signCardAccList.getAccMonth());
		key.setCardId(signCardAccList.getCardId());
		SignCardAccList signCard = (SignCardAccList) signCardAccListDAO
				.findByPkWithLock(key);
		Assert.notNull(signCard, "要核销的记录已经不存在");
		Assert.isTrue(SignCardAccListState.INITIAL.getValue().equals(
				signCard.getStatus()), "状态不符,不能核销");
		signCard.setPayAmt(signCardAccList.getPayAmt());
		// 应收
		BigDecimal chgFee = AmountUtil.add(signCard.getCurAmt(), signCard
				.getYearAmt());
		Assert.isTrue(chgFee.compareTo(signCardAccList.getPayAmt()) >= 0,
				"还款金额不能大于应收金额");
		// 还款标记
		String payBackFlag = chgFee.compareTo(signCardAccList.getPayAmt()) > 0 ? SignCardAccListState.PART
				.getValue()
				: SignCardAccListState.WHOLE.getValue();
		signCard.setStatus(payBackFlag);
		signCard.setUpdateUser(sessionUser.getUserId());
		signCard.setUpdateTime(new Date());

		return this.signCardAccListDAO.update(signCard) > 0;
	}

	/**
	 * 普通核销
	 * 
	 * @param mset
	 *            核销记录
	 * @param verifyAmount
	 *            实收金额
	 * @param verifyType
	 *            核销类型
	 * @param verifyUser
	 *            核销用户
	 * @param feeAmount
	 *            手续费
	 * @throws BizException
	 */
	private void verify(BaseMSet mset, BigDecimal verifyAmount,
			String verifyType, String verifyUser, BigDecimal feeAmount)
			throws BizException {

		// 应付金额 = 上期结转金额 + 本期金额 - 手续费
		BigDecimal shouldPayAmount = AmountUtil.subtract(AmountUtil.add(mset
				.getLastFee(), mset.getFeeAmt()), feeAmount);
		// 实收金额 = 本次实收 + 上次实收
		mset.setRecvAmt(AmountUtil.add(verifyAmount, mset.getRecvAmt()));

		BigDecimal nextFee = mset.getNextFee();
		// 修改后的下期结转金额 = 应付金额 - 实收金额
		nextFee = AmountUtil.subtract(shouldPayAmount, mset.getRecvAmt());

		// 如果选择的是等额核销
		if (verifyType.equals(VerifyType.EQUA.getValue())) {
			Assert.isTrue(AmountUtil.et(shouldPayAmount, mset.getRecvAmt()),
					"等额核销应付（收）金额与实付（收）金额必须相等");
//			mset.setJzFlag(VerifyJzFlag.NO.getValue());
			mset.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
			mset.setNextFee(nextFee);
			// 如果选择的是差额核销
		} else if (verifyType.equals(VerifyType.DIFF.getValue())) {
			// 应收金额 > 实收金额 差额核销
			if (AmountUtil.gt(shouldPayAmount, mset.getRecvAmt())) {
//				mset.setJzFlag(VerifyJzFlag.NO.getValue());
				mset.setChkStatus(VerifyCheckState.DIFFER_VERIFY.getValue());
				mset.setNextFee(new BigDecimal(0.0));
				// 应收金额 < 实收金额 差额待结转
			} else if (AmountUtil.lt(shouldPayAmount, mset.getRecvAmt())) {
//				mset.setJzFlag(VerifyJzFlag.YES.getValue());
				mset.setChkStatus(VerifyCheckState.DIFFER_WAITE.getValue());
				mset.setNextFee(nextFee);
				// 应收金额 = 实收金额 等额核销
			} else if (AmountUtil.et(shouldPayAmount, mset.getRecvAmt())) {
//				mset.setJzFlag(VerifyJzFlag.NO.getValue());
				mset.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
				mset.setNextFee(nextFee);
			}
			// 如果选择的是部分核销
		} else if (verifyType.equals(VerifyType.PART.getValue())) {
			// 应收金额 = 实收金额 等额核销
			if (AmountUtil.et(shouldPayAmount, mset.getRecvAmt())) {
//				mset.setJzFlag(VerifyJzFlag.NO.getValue());
				mset.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
			} else {
//				mset.setJzFlag(VerifyJzFlag.NO.getValue());
				mset.setChkStatus(VerifyCheckState.PART_VERIFY.getValue());
			}
			mset.setNextFee(nextFee);
		}

		mset.setUpdateBy(verifyUser);
		mset.setUpdateTime(new Date());
	}

	/**
	 * 分润核销
	 * 
	 * @param sharesMSet
	 * @param verifyUser
	 * @throws BizException
	 */
	private void verfyShares(BaseSharesMSet sharesMSet, String verifyUser)
			throws BizException {

		sharesMSet.setRecvAmt(sharesMSet.getPayAmt());
		sharesMSet.setUpdateBy(verifyUser);
		sharesMSet.setUpdateTime(new Date());
		// 付钱只能是等额核销
		sharesMSet.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
//		if (sharesMSet.getNextFee().intValue() == 0) {
//			sharesMSet.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
//		} else {
//			sharesMSet.setChkStatus(VerifyCheckState.DIFFER_VERIFY.getValue());
//		}
	}

	/**
	 * 商户手续费核销 更改对应的商户代理分润核销的核销状态,发卡机构实际分润金额,和结转金额
	 * 
	 * @param merchFeeMSet
	 * @param verifyAmount
	 * @param jzFlag
	 * @throws BizException
	 
	private boolean updateBranchShares(MerchFeeMSet merchFeeMSet,
			BigDecimal verifyAmount, String jzFlag) throws BizException {
		Assert.isTrue(VerifyCheckState.EQUAL_VERIFY.getValue().equals(
				merchFeeMSet.getChkStatus())
				|| VerifyCheckState.DIFFER_VERIFY.getValue().equals(
						merchFeeMSet.getChkStatus())
				|| VerifyCheckState.PART_VERIFY.getValue().equals(
						merchFeeMSet.getChkStatus())
				|| VerifyCheckState.DIFFER_WAITE.getValue().equals(
						merchFeeMSet.getChkStatus()), "核销状态不对");
		Map<String, Object> SharesKeyMap = new HashMap<String, Object>();

		SharesKeyMap.put("feeMonth", merchFeeMSet.getFeeMonth());
		SharesKeyMap.put("branchCode", merchFeeMSet.getBranchCode());
		SharesKeyMap.put("merchId", merchFeeMSet.getMerchId());
		SharesKeyMap.put("merchProxy", merchFeeMSet.getMerchProxy());
		MerchProxySharesMSet sharesMSet = (MerchProxySharesMSet) merchProxySharesMSetDAO
				.findByPk(SharesKeyMap);
		BigDecimal precent = new BigDecimal("1.0000");
		// 如果本期手续费不等于0,分润比例 = 实收手续费/本期手续费
		if (merchFeeMSet.getFeeAmt().intValue() != 0) {
			precent = AmountUtil.divide(verifyAmount, merchFeeMSet.getFeeAmt());
		}
		modifyShares(sharesMSet, verifyAmount, precent, jzFlag);

		return merchProxySharesMSetDAO.update(sharesMSet) > 0;
	}*/

	/**
	 * 如果发卡机构手续费核销的状态为最终状态则更改对应的分支机构,<br>
	 * 运营机构代理商分润及出机方，维护方的二次分润分润核销的核销状态,<br>
	 * 实际分润金额和结转金额
	 * 
	 * @param releaseCardFeeMSet
	 * @param verifyAmount 实收手续费
	 * @throws BizException
	 */
	private void updateCeneterShares(ReleaseCardFeeMSet releaseCardFeeMSet,
			BigDecimal verifyAmount) throws BizException {
		
		if (VerifyCheckState.EQUAL_VERIFY.getValue().equals(releaseCardFeeMSet.getChkStatus())
				|| VerifyCheckState.DIFFER_VERIFY.getValue().equals(releaseCardFeeMSet.getChkStatus())
				|| VerifyCheckState.DIFFER_WAITE.getValue().equals(releaseCardFeeMSet.getChkStatus())) {
			
		}
		
		BigDecimal precent = new BigDecimal("1.0000");
		// 如果本期手续费不等于0,同时实收手续费小于本期手续费时,分润比例 = 实收手续费/本期手续费
		if (releaseCardFeeMSet.getFeeAmt().intValue() != 0
				&& AmountUtil.lt(verifyAmount, releaseCardFeeMSet.getFeeAmt())) {
			precent = AmountUtil.divide(verifyAmount, releaseCardFeeMSet.getFeeAmt());
		}
		
		// 分支机构分润
		Map<String, Object> branchKeyMap = new HashMap<String, Object>();
		branchKeyMap.put("feeMonth", releaseCardFeeMSet.getFeeMonth());
		branchKeyMap.put("chlCode", releaseCardFeeMSet.getChlCode());
		branchKeyMap.put("branchCode", releaseCardFeeMSet.getBranchCode());

		BranchSharesMSet branchSharesMSet = (BranchSharesMSet) branchSharesMSetDAO.findByPk(branchKeyMap);
		Assert.notNull(branchSharesMSet, "对应分支机构的分润核销记录不存在");
		modifyShares(branchSharesMSet, verifyAmount, precent);
		branchSharesMSetDAO.update(branchSharesMSet);
		
		// 运营机构代理商分润
		Map<String, Object> proxyKeyMap = new HashMap<String, Object>();
		proxyKeyMap.put("feeMonth", releaseCardFeeMSet.getFeeMonth());
		proxyKeyMap.put("proxyId", releaseCardFeeMSet.getProxyId());
		proxyKeyMap.put("branchCode", releaseCardFeeMSet.getBranchCode());
		CenterProxySharesMSet centerProxySharesMSet = (CenterProxySharesMSet) centerProxySharesMSetDAO
				.findByPk(proxyKeyMap);
		modifyShares(centerProxySharesMSet, verifyAmount, precent);
		centerProxySharesMSetDAO.update(centerProxySharesMSet);

		Map<String, Object> termMap = new HashMap<String, Object>();
		termMap.put("feeMonth", releaseCardFeeMSet.getFeeMonth());
		termMap.put("branchCode", releaseCardFeeMSet.getBranchCode());
		
		// 机具出机方分润
		List<CenterTermFeeMSet> feeList = centerTermFeeMSetDAO.findCenterTermFeeMSet(termMap);
		for (CenterTermFeeMSet centerTermFeeMSet : feeList) {
			modifyShares(centerTermFeeMSet, verifyAmount, precent);
			centerTermFeeMSetDAO.update(centerTermFeeMSet);
		}

		// 机具维护方分润
		List<CenterTermRepFeeMSet> repList = centerTermRepFeeMSetDAO.findCenterTermRepFeeMSet(termMap);
		for (CenterTermRepFeeMSet centerTermRepFeeMSet : repList) {
			modifyShares(centerTermRepFeeMSet, verifyAmount, precent);
			centerTermRepFeeMSetDAO.update(centerTermRepFeeMSet);
		}
	}

	private void modifyShares(BaseSharesMSet sharesMSet,
			BigDecimal verifyAmount, BigDecimal precent)
			throws BizException {
		Assert.isTrue(VerifyCheckState.INITIAL.getValue().equals(sharesMSet.getChkStatus()), "对应分润核销不为初始状态");
		sharesMSet.setChkStatus(VerifyCheckState.WAIT_VERIFY.getValue());
		// 修改实际分润金额 修改后实际分润金额 = 原实际金额*(实收手续费/本期手续费);
		BigDecimal rectFeeAmount = AmountUtil.multiply(sharesMSet.getPayAmt(), precent);
//		BigDecimal nextFee = new BigDecimal("0.00");
//		// 如果是手续费是差额核销 修改分润核销的下期结转金额 = 本期金额 - 本期金额*(实收手续费/本期手续费)
//		nextFee = AmountUtil.subtract(sharesMSet.getFeeAmt(), AmountUtil.multiply(sharesMSet.getFeeAmt(), precent));
//		sharesMSet.setNextFee(nextFee);
		sharesMSet.setPayAmt(rectFeeAmount);// 可付金额
	}
	
	private void resetCardBranchSellAmt(MerchTransDSet merchTransDSet) throws BizException {
		Assert.notNull(merchTransDSet, "要核销的对象不能为空");
		Assert.notEmpty(merchTransDSet.getPayCode(), "付款方不能为空");
		Assert.notEmpty(merchTransDSet.getRecvCode(), "收款方不能为空");
		// 如果付款与收款方是同一机构，则不处理配额的情况
		if (merchTransDSet.getPayCode().equals(merchTransDSet.getRecvCode())) {
			BranchSellAmtKey key = new BranchSellAmtKey();
			key.setCardBranch(merchTransDSet.getRecvCode());
			key.setSellBranch(merchTransDSet.getPayCode());
			
			BranchSellAmt branchSellAmt = (BranchSellAmt) branchSellAmtDAO.findByPkWithLock(key);
			Assert.notNull(branchSellAmt, "没有该机构的配额数据");

			branchSellAmt.setUnsettleAmt(AmountUtil.divide(branchSellAmt.getUnsettleAmt(), merchTransDSet.getRecvAmt()));
			branchSellAmtDAO.update(branchSellAmt);
			
			// 定义变动记录
			BranchSellChg branchSellChg = new BranchSellChg();
			branchSellChg.setCardBranch(merchTransDSet.getRecvCode());
			branchSellChg.setSellBranch(merchTransDSet.getPayCode());
			branchSellChg.setSellType(SellType.BRANCH.getValue());
			branchSellChg.setAdjType(AdjType.SETTLE.getValue());
			branchSellChg.setAmt(merchTransDSet.getRecvAmt());
			branchSellChg.setRefid(merchTransDSet.getSetDate() + merchTransDSet.getTransType() + merchTransDSet.getPayCode());
			branchSellChg.setChangeDate(new Date());
			this.branchSellChgDAO.insert(branchSellChg);
		}
	}

	public boolean verifyCardIssuer(CardIssuerFeeMSet cardIssuerFeeMSet, String verifyCode) throws BizException{
		Assert.notNull(cardIssuerFeeMSet, "要核销的对象不能为空");
		CardIssuerFeeMSetKey key = new CardIssuerFeeMSetKey();
		key.setBranchCode(cardIssuerFeeMSet.getBranchCode());
		key.setChlCode(cardIssuerFeeMSet.getChlCode());
		key.setFeeMonth(cardIssuerFeeMSet.getFeeMonth());
		CardIssuerFeeMSet mset = (CardIssuerFeeMSet)this.cardIssuerFeeMSetDAO.findByPk(key);
		mset.setRecvAmt(cardIssuerFeeMSet.getRecvAmt());
		mset.setUpdateBy(verifyCode);
		mset.setUpdateTime(new Date());
		mset.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
		return this.cardIssuerFeeMSetDAO.update(mset) > 0;
	}

	public boolean verifyChlFee(ChlFeeMSet chlFeeMSet, String verifyCode)
			throws BizException {
		Assert.notNull(chlFeeMSet, "要核销的对象不能为空");
		ChlFeeMSetKey key = new ChlFeeMSetKey();
		key.setChlCode(chlFeeMSet.getChlCode());
		key.setFeeMonth(chlFeeMSet.getFeeMonth());
		ChlFeeMSet mset = (ChlFeeMSet) this.chlFeeMSetDAO.findByPk(key);
		mset.setRecvAmt(chlFeeMSet.getRecvAmt());
		mset.setUpdateBy(verifyCode);
		mset.setUpdateTime(new Date());
		// 付钱只能是等额核销
		mset.setChkStatus(VerifyCheckState.EQUAL_VERIFY.getValue());
		return this.chlFeeMSetDAO.update(mset) > 0;
	}
}
