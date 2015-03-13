package gnete.card.service;

import gnete.card.entity.CardCustomer;
import gnete.card.entity.CustomerRebateReg;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: SaleCardRuleService.java
 *
 * @description:客户返利管理（售卡规则）
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-10-25
 */
public interface SaleCardRuleService {

	/**
	 * 添加购卡客户
	 * 
	 * @param cardCustomer
	 *            购卡客户信息
	 * @return
	 * @throws BizException
	 */
	boolean addCardCustomer(CardCustomer cardCustomer, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改购卡客户
	 * 
	 * @param cardCustomer
	 *            购卡客户信息
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean modifyCardCustomer(CardCustomer cardCustomer, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除购卡客户
	 * 
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean deleteCardCustomer(long cardCustomerId) throws BizException;
	
	/**
	 * 添加返利规则
	 * 
	 * @param rebateRule
	 *            返利规则信息
	 * @return
	 * @throws BizException
	 */
	boolean addRebateRule(RebateRule rebateRule,
			RebateRuleDetail[] rebateRuleArray, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改返利规则
	 * 
	 * @param rebateRule
	 *            返利规则信息
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean modifyRebateRule(RebateRule rebateRule,
			RebateRuleDetail[] rebateRuleDetailArray, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除返利规则
	 * 
	 * @param string
	 * @return
	 * @throws BizException
	 */
	boolean deleteRebateRule(long rebateId) throws BizException;

	/**
	 * 是否是正确的卡号
	 * 
	 * @param cardId
	 * @return
	 * @throws BizException
	 */
	boolean isCorrectRebateCard(CardCustomer cardCustomer, UserInfo sessionUser)
			throws BizException;

	/**
	 * 添加购卡客户返利规则对应登记簿信息
	 * 
	 * @param customerRebateReg
	 *            客户返利注册信息
	 * @return
	 * @throws BizException
	 */
	CustomerRebateReg addCustomerRebateReg(CustomerRebateReg customerRebateReg,
			UserInfo sessionUser) throws BizException;
	
	/**
	 * 停用购卡客户返利规则
	 * @param customerRebateRegId
	 * @throws BizException
	 */
	void stopCustomerRebateReg(String customerRebateRegId, UserInfo user) throws BizException;
	
	/**
	 * 启用购卡客户返利规则
	 * @param customerRebateRegId
	 * @throws BizException
	 */
	void startCustomerRebateReg(String customerRebateRegId, UserInfo user) throws BizException;

	/**
	 * 修改购卡客户返利规则对应登记簿信息
	 * 
	 * @param customerRebateReg
	 * @param createUserId
	 * @return
	 * @throws BizException
	 */
	boolean modifyCustomerRebateReg(CustomerRebateReg customerRebateReg,
			UserInfo sessionUser) throws BizException;
	
	/**
	 * 添加签单卡客户
	 * @param cardCustomer	签单卡客户信息
	 * @return
	 * @throws BizException
	 */
	long addSignCust(SignCust signCust, UserInfo sessionUser) throws BizException;
	
	/**
	 * 修改签单卡客户
	 * @param cardCustomer	签单卡客户信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifySignCust(SignCust signCust, UserInfo sessionUser) throws BizException ;
	
	/**
	 * 删除签单卡客户
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteSignCust(long signCustId) throws BizException;
	
	/**
	 * 添加签单规则及注册信息
	 * @param signRuleReg	签单规则注册信息
	 * @return
	 * @throws BizException
	 */
	SignRuleReg addSignRuleReg(SignRuleReg signRuleReg, UserInfo sessionUser) throws BizException;	
	
	/**
	 * 修改签单规则
	 * @param signRuleReg	签单规则信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifySignRuleReg(SignRuleReg signRuleReg, UserInfo sessionUser) throws BizException ;
	
	/**
	 * 删除签单规则
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteSignRuleReg(long signRuleId) throws BizException ;
	
	/**
	 * 添加签单卡登记
	 * @param saleSignCardReg	签单卡登记信息
	 * @return
	 * @throws BizException
	 */
	boolean addSaleSignCardReg(SaleSignCardReg saleSignCardReg, UserInfo sessionUser) throws BizException;

}
