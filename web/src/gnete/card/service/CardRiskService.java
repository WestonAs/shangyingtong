package gnete.card.service;

import gnete.card.entity.BranchSellReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.UserSellChg;
import gnete.etc.BizException;

import java.math.BigDecimal;

/**
 * @File: CardRiskService.java
 *
 * @description: 准备金相关业务处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-29
 */
public interface CardRiskService {
	
	/**
	 * 添加准备金申请登记
	 * @param cardRiskReg
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean addCardRiskReg(CardRiskReg cardRiskReg, String sessionUserCode) throws BizException;
	
	/**
	 * 修改准备金申请登记
	 * @param cardRiskReg
	 * @param sessionUserCode
	 * @return
	 * @throws BizException
	 */
	boolean modifyCardRiskReg(CardRiskReg cardRiskReg, String sessionUserCode) throws BizException ;
	
	/**
	 * 删除准备今年申请登记
	 * @param id
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardRiskReg(Long id) throws BizException;
	
	/**
	 * 添加售卡配额登记
	 * 
	 * @param branchSellReg
	 * @return
	 */
	boolean addSellAmtReg(BranchSellReg branchSellReg, String sessionUserCode) throws BizException;

	/**
	 * 设置操作
	 * @param userSellChg
	 * @param sessionUserCode
	 * @return
	 */
	boolean setUserSell(UserSellChg userSellChg, String sessionUserCode) throws BizException;
	
	/**
	 * 变更操作员配额
	 * @param userId
	 * @param branchCode
	 * @param amt
	 */
	public void deductUserAmt(String userId, String branchCode , BigDecimal amt) throws BizException;

	/**
	 * 生效售卡配额登记
	 * @param branchSellReg
	 */
	void activateSell(BranchSellReg branchSellReg) throws BizException;

	/**
	 * 风险保证金的生效
	 * @param cardRiskReg
	 */
	void activateCardRisk(CardRiskReg cardRiskReg) throws BizException;
	
	/**
	 * 处理风险准备金和操作员售卡配额。（发卡机构自己不处理配额的情况，扣减操作员额度）
	 * @param cardInfo 卡信息
	 * @param saleCardReg 售卡登记对象
	 * @param riskAmt 调整金额
	 * @param user 用户信息
	 * @throws BizException
	 */
	void dealCardRiskForSellCard(CardInfo cardInfo, SaleCardReg saleCardReg, BigDecimal riskAmt, UserInfo user) throws BizException;
	
}
