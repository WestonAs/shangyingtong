package gnete.card.web.promotions;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PromtDefDAO;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.dao.PromtScopeDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.PromtScope;
import gnete.card.entity.flag.BirthdayFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PromotionsRuleType;
import gnete.card.entity.type.PromtType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.ScopeType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PromotionsService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: ProtocolDefAction.java
 *
 * @description: 协议积分活动定义
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-10-13
 */
public class ProtocolDefAction extends BaseAction {

	@Autowired
	private PromtDefDAO promtDefDAO;
	@Autowired
	private PromtScopeDAO promtScopeDAO;
	@Autowired
	private PromtRuleDefDAO promtRuleDefDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private PromotionsService promotionsService;

	// 发行机构类型列表
	private List issTypeList;
	// 参与机构类型列表
	private List pInstTypeList;
	// 范围类型
	private List scopeTypeList;
	private List transTypeList;

	private List amtTypeList;
	private List birthdayFlagList;
	private List promtRuleTypeList;

	private PromtDef promtDef;
	private PromtRuleDef promtRuleDef;

	private Paginater page;

	// 范围类型
	private String scopeType;
	// 商户号
	private String[] merNo;
	// 卡bin范围
	private String[] cardBinScope;
	// 卡子类型
	private String[] cardSubclass;
	// 积分下限
	private String[] ptLlimit;
	// 积分上限
	private String[] ptUlimit;
	// 会员级别
	private String[] membLevel;

	private String promtId;

	private List<PromtScope> promtScopeList;

	private List<PromtRuleDef> promtRuleList;
	
	private String cardBinScope_sel;
	private String pinstId_sel;
	private String addScope;

	private String promtRuleId;
	
	/** 状态 */
	private List<PromotionsRuleState> statusList;

	@Override
	public String execute() throws Exception {
		transTypeList = TransType.getPromtType();
		issTypeList = IssType.getAll();
		statusList = PromotionsRuleState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (promtDef != null) {
			params.put("promtName", MatchMode.ANYWHERE.toMatchString(promtDef.getPromtName()));
			params.put("pinstId", MatchMode.ANYWHERE.toMatchString(promtDef.getPinstId()));
			params.put("transType", promtDef.getTransType());
			params.put("pinstType", promtDef.getPinstType());
			params.put("status", promtDef.getStatus());
		}
		params.put("promtType", PromtType.PROTOCOL.getValue());
		// 活动发起机构
//		params.put("issIdList", this.getMyPromtIssIdList());
		// 运营中心查看所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 分支机构可查看自己管理的发卡机构和商户的记录
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			List<BranchInfo> list = this.getMyManageFenzhi();
			params.put("fenzhiList", list);
		}
		// 发卡机构可查看自己发起的活动
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("issId", this.getLoginBranchCode());
		}
		// 商户可查看自己发起的活动
		else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())) {
			params.put("issId", this.getLoginBranchCode());
		} else {
			throw new BizException("没有查询权限");
		}
		this.page = promtDefDAO.findPromtDef(params, getPageNumber(), getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询消费积分协议定义列表！");
		return LIST;
	}

	public String detail() throws Exception {
		this.promtDef = (PromtDef) this.promtDefDAO.findByPk(promtDef.getPromtId());
		promtScopeList = promtScopeDAO.findByPromtId(promtDef.getPromtId());
		promtRuleList = promtRuleDefDAO.findByPromtId(promtDef.getPromtId());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询消费积分协议[" + promtDef.getPromtId() + "]明细信息！");
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (!(RoleType.CARD.getValue().equals(getLoginRoleType()) 
				|| RoleType.MERCHANT.getValue().equals(getLoginRoleType()))) {
			throw new BizException("非商户或发卡机构禁止定义一个新的协议积分活动。");
		}
		scopeTypeList = ScopeType.getAll();
		issTypeList = IssType.getAll();
		transTypeList = TransType.getPromtType();
		return ADD;
	}
	
	public String nextStep() throws Exception {
		//活动类型为协议
		promtDef.setPromtType(PromtType.PROTOCOL.getValue());
		// 保存活动信息和活动范围
		promtDef.setAddScope(addScope);

		if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			// 当前用户为发卡机构
			promtDef.setIssType(IssType.CARD.getValue());
			promtDef.setIssId(getSessionUser().getBranchNo());
			if (IssType.CARD.getValue().equals(promtDef.getPinstType())) {
				promtDef.setPinstId(getSessionUser().getBranchNo());
			}
		} else if (RoleType.MERCHANT.getValue().equals(getLoginRoleType())) {
			// 当前用户为商户，参与机构为自己本身
			promtDef.setIssType(IssType.MERCHANT.getValue());
			promtDef.setIssId(getSessionUser().getMerchantNo());
			promtDef.setPinstType(IssType.MERCHANT.getValue());
			promtDef.setPinstId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的活动。");
		}

		if (TrueOrFalseFlag.TRUE.getValue().equals(addScope)) {
			List<PromtScope> list = new ArrayList<PromtScope>();
			for (int i = 0; i < merNo.length; i++) {
				PromtScope promtScope = new PromtScope();

				promtScope.setScopeType(scopeType);
				promtScope.setMerNo(merNo[i]);
				promtScope.setCardBinScope(cardBinScope[i]);
				promtScope.setCardSubclass(cardSubclass[i]);
				BigDecimal ptlimit = null;
				ptlimit = StringUtils.isBlank(ptLlimit[i]) ? null : new BigDecimal(ptLlimit[i]);
				promtScope.setPtLlimit(ptlimit);
				BigDecimal ulimit = StringUtils.isBlank(ptUlimit[i]) ? null : new BigDecimal(ptUlimit[i]);
				promtScope.setPtUlimit(ulimit);
				promtScope.setMembLevel(membLevel[i]);

				list.add(promtScope);
			}
			WebUtils.setSessionAttribute(request, Constants.PROMTSCOPE_LIST, list);
		} else {
			WebUtils.setSessionAttribute(request, Constants.PROMTSCOPE_LIST, null);
		}
		WebUtils.setSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO, promtDef);
		promtRuleList = new ArrayList<PromtRuleDef>();
		WebUtils.setSessionAttribute(request, Constants.PROMTRULE_LIST, promtRuleList);

		initData();
		return "nextStep";
	}

	public void initData() {
		amtTypeList = AmtType.getAll();
		birthdayFlagList = BirthdayFlag.getAll();
		promtRuleTypeList = PromotionsRuleType.getProtocolRuleList();
	}
	
	public String secondStep() throws Exception {
		// 点击"再添加一条规则时"
		promtRuleList = (List<PromtRuleDef>) WebUtils.getSessionAttribute(request, Constants.PROMTRULE_LIST);
		promtRuleList.add(promtRuleDef);
		promtRuleDef = new PromtRuleDef();
		WebUtils.setSessionAttribute(request, Constants.PROMTRULE_LIST, promtRuleList);
		initData();
		return "nextStep";
	}

	/**
	 * 保存数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		// 取出session中的协议积分活动规则对象
		promtRuleList = (List<PromtRuleDef>) WebUtils.getSessionAttribute(request, Constants.PROMTRULE_LIST);
		promtRuleList.add(promtRuleDef);

		// 取出Session中的协议积分活动和附加活动范围对象
		promtDef = (PromtDef) WebUtils.getSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO);
		List<PromtScope> scopeList = (List<PromtScope>) WebUtils.getSessionAttribute(request, Constants.PROMTSCOPE_LIST);

		// 保存所有数据
		promotionsService.addPromotions(promtDef, scopeList, promtRuleList, getSessionUser());

		WebUtils.setSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO, null);
		WebUtils.setSessionAttribute(request, Constants.PROMTSCOPE_LIST, null);
		WebUtils.setSessionAttribute(request, Constants.PROMTRULE_LIST, null);

//		// 启动单个流程
//		this.workflowService.startFlow(promtDef, "promotionsAdapter", promtDef
//				.getPromtId(), getSessionUser());

		String msg = LogUtils.r("新增协议积分活动[{0}]成功", promtDef.getPromtId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/promotions/protocolDef/list.do", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		scopeTypeList = ScopeType.getAll();
		issTypeList = IssType.getAll();
		transTypeList = TransType.getPromtType();
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止修改协议积分活动。");
		}

		promtDef = (PromtDef) promtDefDAO.findByPk(promtDef.getPromtId());
		if (!promtDef.getPinstType().equals(IssType.MERCHANT.getValue())) {
			promtDef.setPinstId("");
		}
		if (promtDef.getAddScope().equals(TrueOrFalseFlag.TRUE.getValue())) {
			promtScopeList = new ArrayList<PromtScope>();
			List<PromtScope> list = promtScopeDAO.findByPromtId(promtDef.getPromtId());
			if (CollectionUtils.isNotEmpty(list)) {
				for(PromtScope scope : list){
					// 商户
					MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(scope.getMerNo());
					if (info != null) {
						scope.setMerName(info.getMerchName());
					}
					
					// 卡子类型
					CardSubClassDef sub  = (CardSubClassDef) cardSubClassDefDAO.findByPk(scope.getCardSubclass());
					if (sub!=null) {
						scope.setSubClassName(sub.getCardSubclassName());
					}
					
					// 卡BIN
					if (StringUtils.isNotBlank(scope.getCardBinScope())) {
						String[] binNos = scope.getCardBinScope().split(",");
						String binNames = "";
						for (int i = 0; i < binNos.length; i++) {
							CardBin bin = (CardBin) cardBinDAO.findByPk(binNos[i]);
							binNames += bin.getBinName();
							if (i<binNos.length - 1) {
								binNames += ",";
							}
						}
						scope.setBinName(binNames);
					}
					promtScopeList.add(scope);
				}
			}
		}
		
		cardBinScope_sel = "";
		
		if (StringUtils.isNotEmpty(promtDef.getCardBinScope())) {
			String[] binscopes = promtDef.getCardBinScope().split(",");
			for (int i = 0; i < binscopes.length; i++) {
				CardBin cardBin = (CardBin) cardBinDAO.findByPk(binscopes[i]);
				cardBinScope_sel += cardBin.getBinName();
				if (i < binscopes.length - 1) {
					cardBinScope_sel += ",";
				}
			}
		}

		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(promtDef.getPinstId());
		if (merchInfo != null) {
			pinstId_sel = merchInfo.getMerchName();
		}
		addScope = promtDef.getAddScope();
		return MODIFY;
	}

	/**
	 * 修改协议积分活动和附加活动范围
	 */
	public String modify() throws Exception {
		promtDef.setAddScope(addScope);
		if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			// 当前用户为发卡机构
			promtDef.setIssType(IssType.CARD.getValue());
			promtDef.setIssId(getSessionUser().getBranchNo());
			if (IssType.CARD.getValue().equals(promtDef.getPinstType())) {
				promtDef.setPinstId(getSessionUser().getBranchNo());
			}
		} else if (RoleType.MERCHANT.getValue().equals(getLoginRoleType())) {
			// 当前用户为商户，参与机构为自己本身
			promtDef.setIssType(IssType.MERCHANT.getValue());
			promtDef.setIssId(getSessionUser().getMerchantNo());
			promtDef.setPinstType(IssType.MERCHANT.getValue());
			promtDef.setPinstId(getSessionUser().getMerchantNo());
		}
		if (addScope.equals(TrueOrFalseFlag.TRUE.getValue())) {
			List<PromtScope> list = new ArrayList<PromtScope>();
			for (int i = 0; i < merNo.length; i++) {
				PromtScope promtScope = new PromtScope();

				promtScope.setScopeType(scopeType);
				promtScope.setMerNo(merNo[i]);
				promtScope.setCardBinScope(cardBinScope[i]);
				promtScope.setCardSubclass(cardSubclass[i]);
				BigDecimal ptlimit = null;
				ptlimit = StringUtils.isBlank(ptLlimit[i]) ? null : new BigDecimal(ptLlimit[i]);
				promtScope.setPtLlimit(ptlimit);
				BigDecimal ulimit = StringUtils.isBlank(ptUlimit[i]) ? null : new BigDecimal(ptUlimit[i]);
				promtScope.setPtUlimit(ulimit);
				promtScope.setMembLevel(membLevel[i]);

				list.add(promtScope);
			}
			promotionsService.modifyPromotionsDefine(promtDef, list, getSessionUser());
		} else {
			promotionsService.modifyPromotionsDefine(promtDef, null, getSessionUser());
		}
		String msg = LogUtils.r("修改协议积分活动[{0}]成功。", promtDef.getPromtId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/promotions/protocolDef/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 提交审核
	 */
	public String commitCheck() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		promtDef = (PromtDef) promtDefDAO.findByPk(promtId);

		promotionsService.commitCheckProtocol(promtId, getSessionUser());
		String msg = LogUtils.r("协议积分活动[{0}]已经提交审核", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/protocolDef/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 待审核的协议积分活动列表
	 */
	public String checkList() throws Exception {
		String[] ids = workflowService.getMyJob(WorkflowConstants.PROTOCOL_DEFINE, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		page = promtDefDAO.findPromtDef(params, getPageNumber(), getPageSize());

		return CHECK_LIST;
	}

	/**
	 * 审核明细
	 */
	public String checkDetail() throws Exception {
		this.promtDef = (PromtDef) this.promtDefDAO.findByPk(promtDef.getPromtId());

		promtScopeList = promtScopeDAO.findByPromtId(promtDef.getPromtId());
		promtRuleList = promtRuleDefDAO.findByPromtId(promtDef.getPromtId());
		return DETAIL;
	}

	/**
	 * 删除活动
	 */
	public String delete() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		boolean isSuccess = promotionsService.deltetePromotions(promtId);
		if (isSuccess) {
			String msg = LogUtils.r("删除协议积分活动[{0}]成功。", promtId);
			log(msg, UserLogType.DELETE);
			addActionMessage("/promotions/protocolDef/list.do", msg);
		}
		return SUCCESS;
	}

	/**
	 * 根据传进来的规则类型，得到相应的子类参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getSubClassList() throws Exception {
		String ruleType = request.getParameter("ruleType");
		StringBuffer sb = new StringBuffer(128);
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			params.put("jinstId", getSessionUser().getBranchNo());
		} else if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			params.put("jinstId", getSessionUser().getMerchantNo());
		}
		if (StringUtils.equals(ruleType, "01")
				|| StringUtils.equals(ruleType, "02")
				|| StringUtils.equals(ruleType, "04")) {
			// 使用积分子类型
			List<PointClassDef> pointClassList = pointClassDefDAO.findPtClassByJinst(params);
			for (PointClassDef pointClassDef : pointClassList) {
				sb.append("<option value=\"")
						.append(pointClassDef.getPtClass()).append("\">")
						.append(pointClassDef.getClassName()).append(
								"</option>");
			}
		} else if (StringUtils.equals(ruleType, "21")
				|| StringUtils.equals(ruleType, "22")
				|| StringUtils.equals(ruleType, "23")) {
			// 使用赠券子类型
			List<CouponClassDef> couponClassList = couponClassDefDAO.findCouponClassByJinst(params);
			for (CouponClassDef couponClassDef : couponClassList) {
				sb.append("<option value=\"").append(
						couponClassDef.getCoupnClass()).append("\">").append(
						couponClassDef.getClassName()).append("</option>");
			}
		}
		this.respond(sb.toString());
	}

	/**
	 * 注销活动
	 */
	public String cancel() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		promotionsService.cancelPromotions(promtId);

		String msg = LogUtils.r("注销活动[{0}]成功", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/protocolDef/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 启用活动
	 */
	public String start() throws Exception {
		promotionsService.startPromotions(promtId);

		String msg = LogUtils.r("启用活动[{0}]成功", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/protocolDef/list.do", msg);
		return SUCCESS;
	}

	// === start =========根据协议积分活动ID对协议积分活动规则进行增删改查===================

	/**
	 * 某一协议积分活动ID的协议积分活动规则列表
	 */
	public String ruleList() throws Exception {
		if (promtRuleDef == null
				|| StringUtils.isBlank(promtRuleDef.getPromtId())) {
			throw new BizException("必须选定一个协议积分活动");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		// 协议
		params.put("promtType", PromtType.PROTOCOL.getValue());
		params.put("promtId", promtRuleDef.getPromtId());
		page = promtRuleDefDAO.findPromtRuleDef(params, getPageNumber(),
				getPageSize());
		return LIST;
	}

	/**
	 * 规则明细
	 */
	public String ruleDetail() throws Exception {
		promtRuleDef = (PromtRuleDef) promtRuleDefDAO.findByPk(promtRuleDef.getPromtRuleId());
		return DETAIL;
	}

	/**
	 * 为某一协议积分活动ID新增规则
	 */
	public String showRuleAdd() throws Exception {
		initData();
		return ADD;
	}

	/**
	 * 为某一协议积分活动ID新增规则
	 */
	public String ruleAdd() throws Exception {
		promotionsService.addPromtRuleDef(promtRuleDef, getSessionUser());

		String msg = LogUtils.r("新增协议积分活动[{0}]规则ID为[{1}]的协议积分活动规则成功。", promtRuleDef
				.getPromtId(), promtRuleDef.getPromtRuleId());
		log(msg, UserLogType.ADD);
		addActionMessage(
				"/promotions/protocolDef/ruleList.do?promtRuleDef.promtId="
						+ promtRuleDef.getPromtId(), msg);
		return SUCCESS;
	}

	/**
	 * 修改某一协议积分活动ID的规则
	 */
	public String showRuleModify() throws Exception {
		initData();

		promtRuleDef = (PromtRuleDef) promtRuleDefDAO.findByPk(promtRuleDef
				.getPromtRuleId());
		return MODIFY;
	}

	/**
	 * 修改某一协议积分活动ID的规则
	 */
	public String ruleModify() throws Exception {
		promotionsService.modifyPromtRuleDef(promtRuleDef, getSessionUser());

		String msg = LogUtils.r("修改协议积分活动ID为[{0}]促销规则ID为[{1}]的协议积分活动规则成功。",
				promtRuleDef.getPromtId(), promtRuleDef.getPromtRuleId());
		log(msg, UserLogType.UPDATE);
		this.addActionMessage(
				"/promotions/protocolDef/ruleList.do?promtRuleDef.promtId="
						+ promtRuleDef.getPromtId(), msg);
		return SUCCESS;
	}

	/**
	 * 注销某一协议积分活动ID的规则
	 */
	public String cancelRule() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		promotionsService.cancelPromtRule(promtId, promtRuleId);

		String msg = LogUtils.r("注销协议积分活动[{0}]的促销规则[{1}]成功", promtId, promtRuleId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage(
				"/promotions/protocolDef/ruleList.do?promtRuleDef.promtId="
						+ promtId, msg);
		return SUCCESS;
	}

	/**
	 * 启用某一协议积分活动ID的规则
	 */
	public String startRule() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		promotionsService.startPromtRule(promtId, promtRuleId);

		String msg = LogUtils
				.r("启用协议积分活动[{0}]的促销规则[{1}]成功", promtId, promtRuleId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage(
				"/promotions/protocolDef/ruleList.do?promtRuleDef.promtId="
						+ promtId, msg);
		return SUCCESS;
	}

	/**
	 * 删除某一协议积分活动ID的规则
	 */
	public String ruleDelete() throws Exception {
		boolean isSuccess = promotionsService.deletePromotionsRuleDefine(
				promtId, promtRuleId);
		if (isSuccess) {
			String msg = LogUtils.r("删除协议积分活动ID为[{0}]的协议积分活动成功。", promtRuleId);
			this.log(msg, UserLogType.DELETE);
			this.addActionMessage(
					"/promotions/protocolDef/ruleList.do?promtRuleDef.promtId="
							+ promtId, msg);
		}
		return SUCCESS;
	}

	// ========================== end ========================================

	public String getCardIssuerBin() {
		String cardIssuerNoBin = "";
		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			cardIssuerNoBin = getSessionUser().getBranchNo();
		}
		if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			cardIssuerNoBin = getSessionUser().getMerchantNo();
		}
		return cardIssuerNoBin;
	}

	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

	public List getIssTypeList() {
		return issTypeList;
	}

	public void setIssTypeList(List issTypeList) {
		this.issTypeList = issTypeList;
	}

	public List getPInstTypeList() {
		return pInstTypeList;
	}

	public void setPInstTypeList(List instTypeList) {
		pInstTypeList = instTypeList;
	}

	public List getScopeTypeList() {
		return scopeTypeList;
	}

	public void setScopeTypeList(List scopeTypeList) {
		this.scopeTypeList = scopeTypeList;
	}

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List getAmtTypeList() {
		return amtTypeList;
	}

	public void setAmtTypeList(List amtTypeList) {
		this.amtTypeList = amtTypeList;
	}

	public List getBirthdayFlagList() {
		return birthdayFlagList;
	}

	public void setBirthdayFlagList(List birthdayFlagList) {
		this.birthdayFlagList = birthdayFlagList;
	}

	public List getPromtRuleTypeList() {
		return promtRuleTypeList;
	}

	public void setPromtRuleTypeList(List promtRuleTypeList) {
		this.promtRuleTypeList = promtRuleTypeList;
	}

	public List<PromtRuleDef> getPromtRuleList() {
		return promtRuleList;
	}

	public void setPromtRuleList(List<PromtRuleDef> promtRuleList) {
		this.promtRuleList = promtRuleList;
	}

	public PromtDef getPromtDef() {
		return promtDef;
	}

	public void setPromtDef(PromtDef promtDef) {
		this.promtDef = promtDef;
	}

	public PromtRuleDef getPromtRuleDef() {
		return promtRuleDef;
	}

	public void setPromtRuleDef(PromtRuleDef promtRuleDef) {
		this.promtRuleDef = promtRuleDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String[] getMerNo() {
		return merNo;
	}

	public void setMerNo(String[] merNo) {
		this.merNo = merNo;
	}

	public String[] getCardBinScope() {
		return cardBinScope;
	}

	public void setCardBinScope(String[] cardBinScope) {
		this.cardBinScope = cardBinScope;
	}

	public String[] getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String[] cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public String[] getPtLlimit() {
		return ptLlimit;
	}

	public void setPtLlimit(String[] ptLlimit) {
		this.ptLlimit = ptLlimit;
	}

	public String[] getPtUlimit() {
		return ptUlimit;
	}

	public void setPtUlimit(String[] ptUlimit) {
		this.ptUlimit = ptUlimit;
	}

	public String[] getMembLevel() {
		return membLevel;
	}

	public void setMembLevel(String[] membLevel) {
		this.membLevel = membLevel;
	}

	public String getPromtId() {
		return promtId;
	}

	public void setPromtId(String promtId) {
		this.promtId = promtId;
	}

	public List<PromtScope> getPromtScopeList() {
		return promtScopeList;
	}

	public void setPromtScopeList(List<PromtScope> promtScopeList) {
		this.promtScopeList = promtScopeList;
	}

	public void setCardBinScope_sel(String cardBinScope_sel) {
		this.cardBinScope_sel = cardBinScope_sel;
	}

	public String getPinstId_sel() {
		return pinstId_sel;
	}

	public void setPinstId_sel(String pinstId_sel) {
		this.pinstId_sel = pinstId_sel;
	}

	public String getAddScope() {
		return addScope;
	}

	public void setAddScope(String addScope) {
		this.addScope = addScope;
	}

	public String getPromtRuleId() {
		return promtRuleId;
	}

	public void setPromtRuleId(String promtRuleId) {
		this.promtRuleId = promtRuleId;
	}

	public List<PromotionsRuleState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<PromotionsRuleState> statusList) {
		this.statusList = statusList;
	}

}
