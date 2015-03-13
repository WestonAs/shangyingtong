package gnete.card.web.promotions;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PromtDefDAO;
import gnete.card.dao.PromtRuleDefDAO;
import gnete.card.dao.PromtScopeDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PromtDef;
import gnete.card.entity.PromtRuleDef;
import gnete.card.entity.PromtScope;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.BirthdayFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.state.CommonState;
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
 * @File: PromotionsDefineAction.java
 * 
 * @description: 促销活动定义的相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public class PromotionsDefineAction extends BaseAction {

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
	@Autowired
	private MerchGroupDAO merchGroupDAO;

	// 发起机构类型列表
	private List issTypeList;
	// 参与机构类型列表
	private List pinstTypeList;
	// 赠送机构类型列表
	private List sendTypeList;


	// 范围类型
	private List scopeTypeList;
	private List transTypeList;

	private List amtTypeList;
	private List birthdayFlagList;
	private List promtRuleTypeList;
	
	private List<MerchGroup> merchGroupList;

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
	
	boolean showCard;
	

	@Override
	public String execute() throws Exception {
		transTypeList = TransType.getPromtType();
		issTypeList = IssType.getAll();
		statusList = PromotionsRuleState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		// 活动类型为活动
		params.put("promtType", PromtType.ACTIVITY.getValue());
		if (promtDef != null) {
			params.put("promtName", MatchMode.ANYWHERE.toMatchString(promtDef.getPromtName()));
			params.put("pinstId", MatchMode.ANYWHERE.toMatchString(promtDef.getPinstId()));
			params.put("transType", promtDef.getTransType());
			params.put("pinstType", promtDef.getPinstType());
			params.put("status", promtDef.getStatus());
		}
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
//			params.put("issId", this.getLoginBranchCode());
			params.put("cardBranchList", this.getMyCardBranch()); 
		}
		// 商户可查看自己发起的活动
		else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())) {
			params.put("issId", this.getLoginBranchCode());
		} else {
			throw new BizException("没有查询权限");
		}
		
		//商圈
		Map<String, Object> tmpparams = new HashMap<String, Object>();
		tmpparams.put("cardIssuer", getLoginBranchCode());
		tmpparams.put("status", CommonState.NORMAL.getValue());
		List<MerchGroup> merchGroupList = merchGroupDAO.findList(tmpparams);
		Object object = params.get("cardBranchList"); 
		List<BranchInfo> branchInfoList;
		if(null != object){
			branchInfoList = (List<BranchInfo>)object;
		}else{
			branchInfoList = new ArrayList<BranchInfo>();
		}
		BranchInfo branchInfo;
		for(MerchGroup merchGroup : merchGroupList){
			branchInfo = new BranchInfo();
			branchInfo.setBranchCode(merchGroup.getGroupId());
			branchInfoList.add(branchInfo);
		}
		params.put("cardBranchList", branchInfoList); 
		
		this.page = promtDefDAO.findPromtDef(params, getPageNumber(), getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		this.promtDef = (PromtDef) this.promtDefDAO.findByPk(promtDef.getPromtId());
		promtScopeList = promtScopeDAO.findByPromtId(promtDef.getPromtId());
		promtRuleList = promtRuleDefDAO.findByPromtId(promtDef.getPromtId());
		
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			showCard = true;
			sendTypeList = IssType.getAll();
		}else if(getLoginRoleType().equals(RoleType.MERCHANT.getValue())){
			showCard = false;
			sendTypeList = IssType.getCardMerchType();
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的活动。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", getSessionUser().getBranchNo());
		params.put("status", CommonState.NORMAL.getValue());
		merchGroupList = merchGroupDAO.findList(params);
		scopeTypeList = ScopeType.getAll();
		issTypeList = IssType.getCircleDefineType();
		pinstTypeList = IssType.getCardMerchType();
		transTypeList = TransType.getPromtType();
		logger.debug("用户[" + this.getSessionUserCode() + "]进入促销活动定义添加页");
		return ADD;
	}

	public String nextStep() throws Exception {
		// 保存活动信息和活动范围
		promtDef.setPromtType(PromtType.ACTIVITY.getValue());
		promtDef.setAddScope(addScope);

		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			String pinstType = promtDef.getPinstType();
			if(CommonHelper.isNotEmpty(pinstType) && pinstType.equals(IssType.CARD.getValue())){//发卡机构
				promtDef.setIssType(IssType.CARD.getValue());
				promtDef.setIssId(getSessionUser().getBranchNo());
				
				promtDef.setPinstType(IssType.CARD.getValue());
				promtDef.setPinstId(getSessionUser().getBranchNo());
			}else if(CommonHelper.isNotEmpty(pinstType) && pinstType.equals(IssType.MERCHANT.getValue())){//商户
				promtDef.setIssType(IssType.CARD.getValue());
				promtDef.setIssId(getSessionUser().getBranchNo());
			}else if(CommonHelper.isNotEmpty(pinstType) && pinstType.equals(IssType.CIRCLE.getValue())){//商圈
				promtDef.setIssType(promtDef.getPinstType());
				promtDef.setIssId(promtDef.getPinstId());
			}
		} else if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			promtDef.setIssType(IssType.MERCHANT.getValue());
			promtDef.setIssId(getSessionUser().getMerchantNo());
			promtDef.setPinstType(IssType.MERCHANT.getValue());
			promtDef.setPinstId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的活动。");
		}
		
		//赠送方为发卡机构
		if(IssType.CARD.getValue().equals(promtDef.getReserved4())){
			promtDef.setReserved5(getSessionUser().getBranchNo());
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
		promtRuleTypeList = PromotionsRuleType.getAll();
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
		// 取出session中的促销活动规则对象
		promtRuleList = (List<PromtRuleDef>) WebUtils.getSessionAttribute(request, Constants.PROMTRULE_LIST);
		promtRuleList.add(promtRuleDef);

		// 取出Session中的促销活动和附加活动范围对象
		promtDef = (PromtDef) WebUtils.getSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO);
		List<PromtScope> scopeList = (List<PromtScope>) WebUtils.getSessionAttribute(request, Constants.PROMTSCOPE_LIST);

		// 保存所有数据
		promotionsService.addPromotions(promtDef, scopeList, promtRuleList, getSessionUser());

		WebUtils.setSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO, null);
		WebUtils.setSessionAttribute(request, Constants.PROMTSCOPE_LIST, null);
		WebUtils.setSessionAttribute(request, Constants.PROMTRULE_LIST, null);

//		// 启动单个流程
//		this.workflowService.startFlow(promtDef, "promotionsAdapter", promtDef	.getPromtId(), getSessionUser());

		String msg = LogUtils.r("新增活动[{0}]成功", promtDef.getPromtId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/promotions/promtDef/list.do", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		scopeTypeList = ScopeType.getAll();
		issTypeList = IssType.getAll();
		transTypeList = TransType.getPromtType();
		
		if (!(isCardRoleLogined() || isMerchantRoleLogined())) {
			throw new BizException("非商户或发卡机构禁止修改活动。");
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", getSessionUser().getBranchNo());
		params.put("status", CommonState.NORMAL.getValue());
		merchGroupList = merchGroupDAO.findList(params);
		addScope = promtDef.getAddScope();
		logger.debug("用户[{}]进入促销活动定义[{}]修改页", this.getSessionUserCode(), promtDef.getPromtId());
		return MODIFY;
	}

	/**
	 * 修改促销活动和附加活动范围
	 */
	public String modify() throws Exception {
		promtDef.setAddScope(addScope);
		if (isCardRoleLogined()) { // 当前用户为发卡机构
			String pinstType = promtDef.getPinstType();
			if(CommonHelper.isNotEmpty(pinstType) && pinstType.equals(IssType.CARD.getValue())){//通用
				promtDef.setPinstType(IssType.CARD.getValue());
				promtDef.setPinstId(getSessionUser().getBranchNo());
			}
		} else if (isMerchantRoleLogined()) { // 当前用户为商户
			// 参与机构为商户本身
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
		String msg = LogUtils.r("修改促销活动ID为[{0}]的促销活动成功。", promtDef.getPromtId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/promotions/promtDef/list.do", msg);
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

		promotionsService.commitCheckPromotions(promtId, getSessionUser());
		String msg = LogUtils.r("促销活动[{0}]已经提交审核", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/promtDef/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 待审核的活动列表
	 */
	public String checkList() throws Exception {
		String[] ids = workflowService.getMyJob(WorkflowConstants.PROMOTIONS_DEFINE, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		//根据赠送机构获取审核列表
		String cardIssuer;
		if(getLoginRoleType().equals(RoleType.CARD.getValue())){//发卡机构
			cardIssuer = getSessionUser().getBranchNo();
		}else if(getLoginRoleType().equals(RoleType.MERCHANT.getValue())){//商户
			cardIssuer = getSessionUser().getMerchantNo();
		}else{
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		//发卡机构或商户定义的商圈
		Map<String, Object> tmpparams = new HashMap<String, Object>();
		tmpparams.put("cardIssuer", cardIssuer);
		merchGroupList =merchGroupDAO.findList(tmpparams);
		int i =0;
		String[] sendIds = new String[merchGroupList.size()+1];
		for(MerchGroup merchGroup : merchGroupList){
			sendIds[i++] = merchGroup.getGroupId();
		}
		sendIds[i] = cardIssuer;
		params.put("sendIds", sendIds);
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
			String msg = LogUtils.r("删除促销活动[{0}]成功。", promtId);
			log(msg, UserLogType.DELETE);
			addActionMessage("/promotions/promtDef/list.do", msg);
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
		// 取出Session中的促销活动和附加活动范围对象
		promtDef = (PromtDef) WebUtils.getSessionAttribute(request, Constants.PROMTDEF_OBJECT_INFO);
		params.put("jinstId", promtDef.getIssId());//发起方Id:发卡机构,商户,商圈
//		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
//			params.put("jinstId", getSessionUser().getBranchNo());
//		} else if (getLoginRoleType().equals(RoleType.MERCHANT.getValue())) {
//			params.put("jinstId", getSessionUser().getMerchantNo());
//		}
		if (StringUtils.equals(ruleType, PromotionsRuleType.SEND_POINTS_RATE.getValue())
				|| StringUtils.equals(ruleType, PromotionsRuleType.SEND_POINTS_FIXED.getValue())
				|| StringUtils.equals(ruleType, PromotionsRuleType.SEND_POINTS_OVER.getValue())
				|| StringUtils.equals(ruleType, PromotionsRuleType.PACT_POINTS_RATE.getValue())) {
			// 使用积分子类型
			List<PointClassDef> pointClassList = pointClassDefDAO.findPtClassByJinst(params);
			for (PointClassDef pointClassDef : pointClassList) {
				sb.append("<option value=\"")
						.append(pointClassDef.getPtClass()).append("\">")
						.append(pointClassDef.getClassName()).append("</option>");
			}
		} else if (StringUtils.equals(ruleType, PromotionsRuleType.DELIVERY_ADDITIONAL.getValue())
				|| StringUtils.equals(ruleType, PromotionsRuleType.DELIVERY_COUPON.getValue())
				|| StringUtils.equals(ruleType, PromotionsRuleType.DELIVERY_AMOUNT.getValue())) {
			// 使用赠券子类型
			List<CouponClassDef> couponClassList = couponClassDefDAO.findCouponClassByJinst(params);
			for (CouponClassDef couponClassDef : couponClassList) {
				sb.append("<option value=\"")
						.append(couponClassDef.getCoupnClass()).append("\">")
						.append(couponClassDef.getClassName()).append("</option>");
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
		
		this.promotionsService.cancelPromotions(promtId);

		String msg = LogUtils.r("注销活动[{0}]成功", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/promtDef/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 启用活动
	 */
	public String start() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		this.promotionsService.startPromotions(promtId);

		String msg = LogUtils.r("启用活动[{0}]成功", promtId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/promtDef/list.do", msg);
		return SUCCESS;
	}

	// === start =========根据促销活动ID对促销活动规则进行增删改查===================

	/**
	 * 某一促销活动ID的促销活动规则列表
	 */
	public String ruleList() throws Exception {
		if (promtRuleDef == null
				|| StringUtils.isBlank(promtRuleDef.getPromtId())) {
			throw new BizException("必须选定一个促销活动");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		// 活动
		params.put("promtType", PromtType.ACTIVITY.getValue());
		params.put("promtId", promtRuleDef.getPromtId());
		this.page = this.promtRuleDefDAO.findPromtRuleDef(params, getPageNumber(), getPageSize());
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
	 * 为某一促销活动ID新增规则
	 */
	public String showRuleAdd() throws Exception {
		initData();
		return ADD;
	}

	/**
	 * 为某一促销活动ID新增规则
	 */
	public String ruleAdd() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		this.promotionsService.addPromtRuleDef(promtRuleDef, getSessionUser());

		String msg = LogUtils.r("新增促销活动[{0}]规则ID为[{1}]的促销活动规则成功。", promtRuleDef.getPromtId(), promtRuleDef.getPromtRuleId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/promotions/promtDef/ruleList.do?promtRuleDef.promtId=" + promtRuleDef.getPromtId(), msg);
		return SUCCESS;
	}

	/**
	 * 修改某一促销活动ID的规则
	 */
	public String showRuleModify() throws Exception {
		initData();

		promtRuleDef = (PromtRuleDef) promtRuleDefDAO.findByPk(promtRuleDef.getPromtRuleId());
		return MODIFY;
	}

	/**
	 * 修改某一促销活动ID的规则
	 */
	public String ruleModify() throws Exception {
		promotionsService.modifyPromtRuleDef(promtRuleDef, getSessionUser());

		String msg = LogUtils.r("修改促销活动ID为[{0}]促销规则ID为[{1}]的促销活动规则成功。", promtRuleDef.getPromtId(), promtRuleDef.getPromtRuleId());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/promotions/promtDef/ruleList.do?promtRuleDef.promtId=" + promtRuleDef.getPromtId(), msg);
		return SUCCESS;
	}

	/**
	 * 注销某一促销活动ID的规则
	 */
	public String cancelRule() throws Exception {
		promotionsService.cancelPromtRule(promtId, promtRuleId);

		String msg = LogUtils.r("注销促销活动[{0}]的促销规则[{1}]成功", promtId, promtRuleId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/promtDef/ruleList.do?promtRuleDef.promtId=" + promtId, msg);
		return SUCCESS;
	}

	/**
	 * 启用某一促销活动ID的规则
	 */
	public String startRule() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		promotionsService.startPromtRule(promtId, promtRuleId);

		String msg = LogUtils.r("启用促销活动[{0}]的促销规则[{1}]成功", promtId, promtRuleId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/promotions/promtDef/ruleList.do?promtRuleDef.promtId=" + promtId, msg);
		return SUCCESS;
	}

	/**
	 * 删除某一促销活动ID的规则
	 */
	public String ruleDelete() throws Exception {
		if (!(getLoginRoleType().equals(RoleType.CARD.getValue()) 
				|| getLoginRoleType().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止进行此操作。");
		}
		
		boolean isSuccess = promotionsService.deletePromotionsRuleDefine(promtId, promtRuleId);
		if (isSuccess) {
			String msg = LogUtils.r("删除促销活动ID为[{0}]的促销活动成功。", promtRuleId);
			this.log(msg, UserLogType.DELETE);
			this.addActionMessage("/promotions/promtDef/ruleList.do?promtRuleDef.promtId=" + promtId, msg);
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
	
	public PromtDef getPromtDef() {
		return promtDef;
	}

	public void setPromtDef(PromtDef promtDef) {
		this.promtDef = promtDef;
	}

	public List<MerchGroup> getMerchGroupList() {
		return merchGroupList;
	}

	public void setMerchGroupList(List<MerchGroup> merchGroupList) {
		this.merchGroupList = merchGroupList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getIssTypeList() {
		return issTypeList;
	}

	public void setIssTypeList(List issTypeList) {
		this.issTypeList = issTypeList;
	}

	public List getPinstTypeList() {
		return pinstTypeList;
	}

	public void setPinstTypeList(List pinstTypeList) {
		this.pinstTypeList = pinstTypeList;
	}
	
	public List getSendTypeList() {
		return sendTypeList;
	}

	public void setSendTypeList(List sendTypeList) {
		this.sendTypeList = sendTypeList;
	}

	public List getScopeTypeList() {
		return scopeTypeList;
	}

	public void setScopeTypeList(List scopeTypeList) {
		this.scopeTypeList = scopeTypeList;
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

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
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

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
	}

	public String getCardBinScope_sel() {
		return cardBinScope_sel;
	}

	public void setCardBinScope_sel(String cardBinScope_sel) {
		this.cardBinScope_sel = cardBinScope_sel;
	}

	public String getAddScope() {
		return addScope;
	}

	public void setAddScope(String addScope) {
		this.addScope = addScope;
	}

	public String getPinstId_sel() {
		return pinstId_sel;
	}

	public void setPinstId_sel(String pinstId_sel) {
		this.pinstId_sel = pinstId_sel;
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

	public List<PromtRuleDef> getPromtRuleList() {
		return promtRuleList;
	}

	public void setPromtRuleList(List<PromtRuleDef> promtRuleList) {
		this.promtRuleList = promtRuleList;
	}

	public List getPromtRuleTypeList() {
		return promtRuleTypeList;
	}

	public void setPromtRuleTypeList(List promtRuleTypeList) {
		this.promtRuleTypeList = promtRuleTypeList;
	}

	public PromtRuleDef getPromtRuleDef() {
		return promtRuleDef;
	}

	public void setPromtRuleDef(PromtRuleDef promtRuleDef) {
		this.promtRuleDef = promtRuleDef;
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
	
	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

}
