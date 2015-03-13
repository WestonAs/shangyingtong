package gnete.card.service.impl;

import flink.util.AmountUtil;
import gnete.card.dao.DiscntProtclDefDAO;
import gnete.card.dao.PromtDefDAO;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.dao.PromtScopeDAO;
import gnete.card.entity.DiscntProtclDef;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.PromtScope;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.state.RuleState;
import gnete.card.service.PromotionsService;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("promotionsService")
public class PromotionsServiceImpl implements PromotionsService {

	@Autowired
	private PromtDefDAO promtDefDAO;
	@Autowired
	private PromtScopeDAO promtScopeDAO;
	@Autowired
	private PromtRuleDefDAO promtRuleDefDAO;
	@Autowired
	private DiscntProtclDefDAO discntProtclDefDAO;
	@Autowired
	private WorkflowService workflowService;
	
	private static final BigDecimal PERCENT = new BigDecimal(100);

	public void addPromotions(PromtDef promtDef, List<PromtScope> scopeList,
			List<PromtRuleDef> promtRuleList, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(promtDef, "要新增的活动对象不能为空。");
		Assert.notEmpty(promtDef.getTransType(), "交易类型不能为空。");
		Assert.notEmpty(promtDef.getEffDate(), "生效日期不能为空。");
		Assert.notEmpty(promtDef.getExpirDate(), "失效日期不能为空。");
		Assert.notNull(sessionUser, "登录用户的用户信息对象不能为空。");
		Assert.isTrue(CollectionUtils.isNotEmpty(promtRuleList), "必须添加一条规则");

		promtDef.setStatus(PromotionsRuleState.CREATE.getValue());
		promtDef.setUpdateBy(sessionUser.getUserId());
		promtDef.setUpdateTime(new Date());

		// 插入记录到促销活动表
		promtDefDAO.insert(promtDef);

		// 插入记录到促销活动附加范围表
		if (CollectionUtils.isNotEmpty(scopeList)) {
			for (PromtScope scope : scopeList) {
				scope.setPromtId(promtDef.getPromtId());
				scope.setEffDate(promtDef.getEffDate());
				scope.setExpirDate(promtDef.getExpirDate());
				scope.setPinstType(promtDef.getPinstType());
				scope.setPinstId(promtDef.getPinstId());
				scope.setTransType(promtDef.getTransType());
				scope.setPromtType(promtDef.getPromtType());
				scope.setStatus(promtDef.getStatus());

				promtScopeDAO.insert(scope);
			}
		}

		// 插入记录促销活动规则表
		for (PromtRuleDef rule : promtRuleList) {
			rule.setPromtId(promtDef.getPromtId());
			rule.setRuleStatus(promtDef.getStatus());

			promtRuleDefDAO.insert(rule);
		}
	}

	public void cancelPromotions(String promtId) throws BizException {
		Assert.notEmpty(promtId, "要注销的活动ID不能为空。");
		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		promtDef.setStatus(PromotionsRuleState.INVALID.getValue());
		promtDefDAO.update(promtDef);

		List<PromtRuleDef> ruleList = promtRuleDefDAO.findByPromtId(promtId);
		for (PromtRuleDef rule : ruleList) {
			rule.setRuleStatus(PromotionsRuleState.INVALID.getValue());

			promtRuleDefDAO.update(rule);
		}
	}

	public void startPromotions(String promtId) throws BizException {
		Assert.notEmpty(promtId, "要启用的活动ID不能为空。");
		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		promtDef.setStatus(PromotionsRuleState.EFFECT.getValue());
		promtDefDAO.update(promtDef);

		List<PromtRuleDef> ruleList = promtRuleDefDAO.findByPromtId(promtId);
		for (PromtRuleDef rule : ruleList) {
			rule.setRuleStatus(PromotionsRuleState.EFFECT.getValue());

			promtRuleDefDAO.update(rule);
		}
	}

	public boolean deltetePromotions(String promtId) throws BizException {
		Assert.notEmpty(promtId, "要删除的活动ID不能为空。");

		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		Assert.isTrue(StringUtils.equals(promtDef.getStatus(),
				PromotionsRuleState.CREATE.getValue()), "只有新建状态的活动才能删除。");

		boolean flag1 = promtDefDAO.delete(promtId) > 0;
		boolean flag2 = promtScopeDAO.deleteByPromtId(promtId);
		boolean flag3 = promtRuleDefDAO.deleteByPromtId(promtId);
		return flag1 & flag2 & flag3;
	}

	/**
	 * 为提交审核的活动或协议积分活动更新数据
	 * 
	 * @param promtId
	 * @param sessionUser
	 * @return
	 * @throws BizException
	 */
	private PromtDef updateForCommitCheck(String promtId, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(promtId, "要提交审核的活动ID不能为空");
		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		Assert.notNull(promtDef, "要审核的活动已经不存在");
		promtDef.setStatus(PromotionsRuleState.WAITED.getValue());
		promtDefDAO.update(promtDef);

		List<PromtScope> scopeList = promtScopeDAO.findByPromtId(promtId);
		if (CollectionUtils.isNotEmpty(scopeList)) {
			for (PromtScope scope : scopeList) {
				scope.setStatus(promtDef.getStatus());

				promtScopeDAO.update(scope);
			}
		}

		List<PromtRuleDef> ruleList = promtRuleDefDAO.findByPromtId(promtId);
		Assert.isTrue(CollectionUtils.isNotEmpty(ruleList),
				"该活动的规则为空，请至少添加一条规则");
		for (PromtRuleDef rule : ruleList) {
			rule.setRuleStatus(promtDef.getStatus());

			promtRuleDefDAO.update(rule);
		}

		return promtDef;
	}

	public void commitCheckPromotions(String promtId, UserInfo sessionUser)
			throws BizException {
		// 启动流程
		try {
			PromtDef promtDef = updateForCommitCheck(promtId, sessionUser);
			workflowService.startFlow(promtDef,
					WorkflowConstants.PROMOTIONS_ADAPTER, promtId, sessionUser);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}

	public void commitCheckProtocol(String promtId, UserInfo sessionUser)
			throws BizException {

		// 启动流程
		try {
			workflowService.deleteFlow(WorkflowConstants.PROTOCOL_DEFINE,
					promtId);
			PromtDef promtDef = updateForCommitCheck(promtId, sessionUser);
			workflowService.startFlow(promtDef,
					WorkflowConstants.PROTOCOL_ADAPTER, promtId, sessionUser);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}

	}

	public boolean modifyPromotionsDefine(PromtDef promtDef,
			List<PromtScope> scopeList, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(promtDef, "要修改的活动对象不能为空。");
		Assert.notEmpty(promtDef.getPromtId(), "活动ID不能为空。");
		Assert.notEmpty(promtDef.getTransType(), "交易类型不能为空。");
		Assert.notEmpty(promtDef.getEffDate(), "生效日期不能为空。");
		Assert.notEmpty(promtDef.getExpirDate(), "失效日期不能为空。");
		Assert.notNull(sessionUser, "登录用户的用户信息对象不能为空。");

		PromtDef def = (PromtDef) promtDefDAO.findByPk(promtDef.getPromtId());
		Assert.notNull(def, "要修改的活动对象已经不存在");
		promtDef.setIssType(def.getIssType());
		promtDef.setIssId(def.getIssId());
		promtDef.setReserved4(def.getReserved4());
		promtDef.setReserved5(def.getReserved5());
		promtDef.setPromtType(def.getPromtType());
		promtDef.setStatus(def.getStatus());
		promtDef.setUpdateBy(sessionUser.getUserId());
		promtDef.setUpdateTime(new Date());

		// 先删除再新增
		promtScopeDAO.deleteByPromtId(promtDef.getPromtId());

		if (CollectionUtils.isNotEmpty(scopeList)) {
			for (PromtScope scope : scopeList) {
				scope.setPromtId(promtDef.getPromtId());
				scope.setEffDate(promtDef.getEffDate());
				scope.setExpirDate(promtDef.getExpirDate());
				scope.setPinstType(promtDef.getPinstType());
				scope.setPinstId(promtDef.getPinstId());
				scope.setTransType(promtDef.getTransType());
				scope.setPromtType(promtDef.getPromtType());
				scope.setStatus(promtDef.getStatus());

				promtScopeDAO.insert(scope);
			}
		}
		return promtDefDAO.update(promtDef) > 0;
	}

	public PromtRuleDef addPromtRuleDef(PromtRuleDef promtRuleDef,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(promtRuleDef, "要添加的活动规则对象不能为空。");
		Assert.notNull(sessionUser, "登录用户信息对象不能为空。");
		Assert.notEmpty(promtRuleDef.getPromtId(), "活动ID不能为空。");
		Assert.notEmpty(promtRuleDef.getAmtType(), "金额类型不能为空。");
		Assert.notEmpty(promtRuleDef.getPromtRuleType(), "规则类型不能为空。");

		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtRuleDef
				.getPromtId());
		Assert.notNull(promtDef, "新增的活动规则对应的活动不存在");

		promtRuleDef.setRuleStatus(promtDef.getStatus());
		promtRuleDefDAO.insert(promtRuleDef);
		return promtRuleDef;
	}

	public boolean modifyPromtRuleDef(PromtRuleDef promtRuleDef,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(promtRuleDef, "要修改的活动规则对象不能为空。");
		Assert.notNull(sessionUser, "登录用户信息对象不能为空。");
		Assert.notEmpty(promtRuleDef.getPromtId(), "活动ID不能为空。");
		Assert.notEmpty(promtRuleDef.getAmtType(), "金额类型不能为空。");
		Assert.notEmpty(promtRuleDef.getPromtRuleType(), "规则类型不能为空。");

		PromtRuleDef rule = (PromtRuleDef) promtRuleDefDAO
				.findByPk(promtRuleDef.getPromtRuleId());
		Assert.notNull(rule, "要修改的活动规则已经不存在");
		promtRuleDef.setRuleStatus(rule.getRuleStatus());

		return promtRuleDefDAO.update(promtRuleDef) > 0;
	}

	public boolean deletePromotionsRuleDefine(String promtId, String promtRuleId)
			throws BizException {
		Assert.notEmpty(promtRuleId, "要删除的活动规则id不能为空。");

		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		Assert.notNull(promtDef, "该活动规则对应的活动已经不存在");

		PromtRuleDef rule = (PromtRuleDef) promtRuleDefDAO
				.findByPk(promtRuleId);
		Assert.isTrue(StringUtils.equals(rule.getRuleStatus(),
				PromotionsRuleState.CREATE.getValue()), "只有新建状态的活动规则才能删除。");

		return promtRuleDefDAO.delete(promtRuleId) > 0;
	}

	public void cancelPromtRule(String promtId, String promtRuleId)
			throws BizException {
		Assert.notEmpty(promtRuleId, "要注销的活动规则Id不能为空");

		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		Assert.notNull(promtDef, "该活动规则对应的活动已经不存在");

		PromtRuleDef rule = (PromtRuleDef) promtRuleDefDAO
				.findByPk(promtRuleId);
		rule.setRuleStatus(PromotionsRuleState.INVALID.getValue());
		promtRuleDefDAO.update(rule);
	}

	public void startPromtRule(String promtId, String promtRuleId)
			throws BizException {
		Assert.notEmpty(promtRuleId, "要启用的活动规则Id不能为空");

		PromtDef promtDef = (PromtDef) promtDefDAO.findByPk(promtId);
		Assert.notNull(promtDef, "该活动规则对应的活动已经不存在");

		PromtRuleDef rule = (PromtRuleDef) promtRuleDefDAO
				.findByPk(promtRuleId);
		rule.setRuleStatus(PromotionsRuleState.EFFECT.getValue());
		promtRuleDefDAO.update(rule);
	}

	public boolean addDiscntProtclDef(DiscntProtclDef discntProtclDef)
			throws BizException {
		// 对于同一个联名机构类型，联名机构号和同一个折扣子类型只能有一个有效的折扣协议
		Assert.notNull(discntProtclDef, "折扣规则定义对象不能为空。");
		Assert.notEmpty(discntProtclDef.getDiscntProtclName(), "折扣协议名不能为空。");
		
		// 折扣率跟暗折折扣率除以100再存入数据库
		discntProtclDef.setDiscntRate(AmountUtil.divide(discntProtclDef.getDiscntRate(), PERCENT));
		discntProtclDef.setSettDiscntRate(AmountUtil.divide(discntProtclDef.getSettDiscntRate(), PERCENT));
		discntProtclDef.setRuleStatus(RuleState.EFFECT.getValue());
		
		updateOtherDiscntProtcl(discntProtclDef, RuleState.INVALID);
		
		return discntProtclDefDAO.insert(discntProtclDef) != null;
	}

	public boolean cancelDiscntProtclDef(String discntprotclNo)
			throws BizException {
		Assert.notEmpty(discntprotclNo, "要注销的折扣协议号不能为空。");
		DiscntProtclDef def = (DiscntProtclDef) discntProtclDefDAO.findByPkWithLock(discntprotclNo);
		Assert.notNull(def, "要注销的折扣协议规则已经不存在");
		def.setRuleStatus(RuleState.INVALID.getValue());
		
		return discntProtclDefDAO.update(def) > 0;
	}
	
	public boolean enableDiscntProtclDef(String discntprotclNo)
			throws BizException {
		Assert.notEmpty(discntprotclNo, "要生效的折扣协议号不能为空。");
		DiscntProtclDef def = (DiscntProtclDef) discntProtclDefDAO.findByPkWithLock(discntprotclNo);
		Assert.notNull(def, "要生效的折扣协议规则已经不存在");
		updateOtherDiscntProtcl(def, RuleState.INVALID);
		def.setRuleStatus(RuleState.EFFECT.getValue());
		
		return discntProtclDefDAO.update(def) > 0;
	}
	
	/**
	 * 更新折扣协议规则状态
	 * @param discntProtclDef
	 * @param ruleState
	 * @return
	 */
	private boolean updateOtherDiscntProtcl(DiscntProtclDef discntProtclDef, RuleState ruleState){
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("ruleStatus", ruleState.getValue());
		params.put("discntClass", discntProtclDef.getDiscntClass());
		params.put("jinstType", discntProtclDef.getJinstType());
		params.put("jinstId", discntProtclDef.getJinstId());
		
		return this.discntProtclDefDAO.updateStatus(params) > 0;
	}

}
