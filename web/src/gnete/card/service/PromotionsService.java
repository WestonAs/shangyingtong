package gnete.card.service;

import java.util.List;

import gnete.card.entity.DiscntProtclDef;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.PromtScope;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**
 * @File: PromotionsService.java
 * 
 * @description: 促销活动业务处理service
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-27
 */
public interface PromotionsService {

	/**
	 * 添加新的促销活动
	 * 
	 * @param promtDef
	 * @param scopeList
	 * @param promtRuleList
	 * @param sessionUser
	 * @throws BizException
	 */
	void addPromotions(PromtDef promtDef, List<PromtScope> scopeList,
			List<PromtRuleDef> promtRuleList, UserInfo sessionUser)
			throws BizException;

	/**
	 * 注销活动
	 * 
	 * @param promtId
	 * @return
	 * @throws Exception
	 */
	void cancelPromotions(String promtId) throws BizException;

	/**
	 * 启用活动
	 * 
	 * @param promtId
	 * @return
	 * @throws BizException
	 */
	void startPromotions(String promtId) throws BizException;

	/**
	 * 删除新建状态的活动
	 * 
	 * @param promtId
	 * @return
	 * @throws BizException
	 */
	boolean deltetePromotions(String promtId) throws BizException;

	/**
	 * 将促销活动提交审核
	 * 
	 * @param promtId
	 * @throws BizException
	 */
	void commitCheckPromotions(String promtId, UserInfo sessionUser)
			throws BizException;

	/**
	 * 将协议积分活动提交审核
	 * 
	 * @param promtId
	 * @param sessionUser
	 * @throws BizException
	 */
	void commitCheckProtocol(String promtId, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改促销活动
	 * 
	 * @param promtDef
	 * @param promtScopes
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean modifyPromotionsDefine(PromtDef promtDef,
			List<PromtScope> scopeList, UserInfo sessionUser)
			throws BizException;

	/**
	 * 新增促销活动规则
	 * 
	 * @param promtRuleDef
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	PromtRuleDef addPromtRuleDef(PromtRuleDef promtRuleDef, UserInfo sessionUser)
			throws BizException;

	/**
	 * 修改促销活动规则定义表
	 * 
	 * @param promtRuleDef
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	boolean modifyPromtRuleDef(PromtRuleDef promtRuleDef, UserInfo sessionUser)
			throws BizException;

	/**
	 * 删除促销活动规则
	 * 
	 * @param promtRuleId
	 *            促销规则ID
	 * @return
	 * @throws BizException
	 */
	boolean deletePromotionsRuleDefine(String promtId, String promtRuleId)
			throws BizException;

	/**
	 * 注销活动规则
	 * 
	 * @param promtRuleId
	 * @throws BizException
	 */
	void cancelPromtRule(String promtId, String promtRuleId)
			throws BizException;

	/**
	 * 启用活动规则
	 * 
	 * @param promtRuleId
	 * @throws BizException
	 */
	void startPromtRule(String promtId, String promtRuleId) throws BizException;

	/**
	 * 新增折扣规则定义
	 * 
	 * @param discntProtclDef
	 * @return
	 * @throws BizException
	 */
	boolean addDiscntProtclDef(DiscntProtclDef discntProtclDef)
			throws BizException;

	/**
	 * 注销折扣规则定义
	 * 
	 * @param discntProtclDef
	 * @return
	 * @throws BizException
	 */
	boolean cancelDiscntProtclDef(String discntprotclNo) throws BizException;

	/**
	 * 启用折扣规则定义
	 * 
	 * @param discntprotclNo
	 * @return
	 * @throws BizException
	 */
	boolean enableDiscntProtclDef(String discntprotclNo) throws BizException;

}
