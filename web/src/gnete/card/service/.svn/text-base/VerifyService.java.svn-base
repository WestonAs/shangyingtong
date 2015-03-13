package gnete.card.service;

import gnete.card.entity.BranchSharesMSet;
import gnete.card.entity.CardIssuerFeeMSet;
import gnete.card.entity.CenterProxySharesMSet;
import gnete.card.entity.CenterTermFeeMSet;
import gnete.card.entity.CenterTermRepFeeMSet;
import gnete.card.entity.ChlFeeMSet;
import gnete.card.entity.MerchFeeMSet;
import gnete.card.entity.MerchProxySharesMSet;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.ReleaseCardFeeMSet;
import gnete.card.entity.SaleProxyRtnMSet;
import gnete.card.entity.SignCardAccList;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: VerifyService.java
 * 
 * @description: 核销处理service
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-9
 */
public interface VerifyService {

	/**
	 * 发卡机构售卡核销
	 * 
	 * @param merchTransDSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyCardBranch(MerchTransDSet merchTransDSet,
			String verifyType, UserInfo sessionUser) throws BizException;

	/**
	 * 商户交易核销
	 * 
	 * @param merchTransDSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyMerchTrans(MerchTransDSet merchTransDSet,
			String verifyType, UserInfo sessionUser) throws BizException;

	/**
	 * 售卡代理返利核销
	 * 
	 * @param saleProxyRtnMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifySaleProxyRtn(SaleProxyRtnMSet saleProxyRtnMSet,
			String verifyType, UserInfo sessionUser) throws BizException;

	/**
	 * 运营中心发卡机构手续费核销
	 * 
	 * @param releaseCardFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyReleaseCardFee(ReleaseCardFeeMSet releaseCardFeeMSet,
			String verifyType, UserInfo sessionUser) throws BizException;

	/**
	 * 分支机构分润核销
	 * 
	 * @param branchSharesMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyBranchShares(BranchSharesMSet branchSharesMSet,
			String verifyType, UserInfo sessionUser) throws BizException;
	
	/**
	 * 分支机构发卡机构手续费核销
	 * 
	 * @param cardIssuerFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyCardIssuer(CardIssuerFeeMSet cardIssuerFeeMSet,
			 String verifyCode) throws BizException;

	/**
	 * 分支机构平台运营手续费核销
	 * 
	 * @param cardIssuerFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyChlFee(ChlFeeMSet chlFeeMSet,
			String verifyCode) throws BizException;

	/**
	 * 商户代理分润核销
	 * 
	 * @param merchProxySharesMSet
	 */
	public boolean verifyMerchProxyShares(
			MerchProxySharesMSet merchProxySharesMSet, String verifyType,
			UserInfo sessionUser) throws BizException;

	/**
	 * 商户手续费核销
	 * 
	 * @param merchFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyMerchFee(MerchFeeMSet merchFeeMSet, String verifyType,
			UserInfo sessionUser) throws BizException;

	/**
	 * 运营机构代理商分润核销
	 * 
	 * @param centerProxySharesMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyCenterProxyShares(
			CenterProxySharesMSet centerProxySharesMSet, String verifyType,
			UserInfo sessionUser) throws BizException;

	/**
	 * 机具出机方分润核销
	 * 
	 * @param centerTermFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyCenterTermFee(CenterTermFeeMSet centerTermFeeMSet,
			String verifyType, UserInfo sessionUser) throws BizException;

	/**
	 * 机具维护方分润核销
	 * 
	 * @param centerTermRepFeeMSet
	 * @return
	 * @throws BizException
	 */
	public boolean verifyCenterTermRepFee(
			CenterTermRepFeeMSet centerTermRepFeeMSet, String verifyType,
			UserInfo sessionUser) throws BizException;

	/**
	 * 签单卡账单核销
	 * 
	 * @param signCardAccList
	 * @return
	 * @throws BizException
	 */
	public boolean verifySignCardAccList(SignCardAccList signCardAccList,
			UserInfo sessionUser) throws BizException;
}
