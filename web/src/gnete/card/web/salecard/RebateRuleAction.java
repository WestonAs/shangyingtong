package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RebateRule;
import gnete.card.entity.RebateRuleDetail;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.type.RebateRuleCalType;
import gnete.card.entity.type.RebateRuleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: RebateRuleAction.java
 * 
 * @description: 客户返利规则设置
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class RebateRuleAction extends BaseAction {

	@Autowired
	private RebateRuleDAO rebateRuleDAO;
	@Autowired
	private RebateRuleDetailDAO rebateRuleDetailDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;

	private RebateRule rebateRule;

	private Paginater page;

	private List rebateRuleDetailList;
	private List statusList;
	private List calTypeList;
	private List<BranchInfo> cardBranchList;

	private Long rebateId;

	private String[] rebateUlimit;
	private String[] rebateRate;

	/** 发卡机构名称 */
	private String cardBranchName;
	private List<YesOrNoFlag> isCommonList;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.calTypeList = RebateRuleCalType.getAll();
		this.statusList = RebateRuleState.getAll();
		this.isCommonList = RebateRuleType.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (rebateRule != null) {
			params.put("rebateId", rebateRule.getRebateId());
			params.put("rebateName", MatchMode.ANYWHERE.toMatchString(rebateRule.getRebateName()));
			params.put("calType", rebateRule.getCalType());
			params.put("status", rebateRule.getStatus());
			params.put("cardBranch", rebateRule.getCardBranch());
			params.put("isCommon", rebateRule.getIsCommon());
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			// params.put("fenzhiCode", this.getLoginBranchCode());
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardRoleLogined()) {
			// params.put("cardBranch", getSessionUser().getBranchNo());
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
			params.put("cardBranchList", cardBranchList);
			
		} else {
			throw new BizException("没有权限查询返利规则");
		}
		
		this.page = this.rebateRuleDAO.findRebateRule(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 */
	public String detail() throws Exception {
		this.calTypeList = RebateRuleCalType.getAll();

		// 返利规则明细
		this.rebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.rebateRule.getRebateId());

		// 返利规则分段比例明细
		Map<String, Object> params = new HashMap<String, Object>();
		if (rebateRule != null){
			params.put("rebateId", rebateRule.getRebateId());
		}
		this.page = this.rebateRuleDetailDAO.findRebateRuleDetail(params, this.getPageNumber(), this.getPageSize());

		return DETAIL;
	}

	/**
	 * 打开新增页面的初始化操作
	 */
	public String showAdd() throws Exception {
		super.checkEffectiveCertUser();
		
		// 当前登录用户角色只能是发卡机构
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能新增客户返利规则。");
		}
		this.calTypeList = RebateRuleCalType.getAll();
		this.isCommonList = RebateRuleType.getNotCommon();
		BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
		
		this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
		return ADD;
	}

	/**
	 * 新增
	 */
	public String add() throws Exception {
		super.checkEffectiveCertUser();
		
		RebateRuleDetail[] rebateRuleDetailArray = new RebateRuleDetail[rebateUlimit.length];
		for (int i = 0; i < rebateUlimit.length; i++) {
			RebateRuleDetail rebateRuleDetail = new RebateRuleDetail();

			rebateRuleDetail.setRebateSect((short) (i + 1));
			rebateRuleDetail.setRebateRate(new BigDecimal(this.rebateRate[i]));
			rebateRuleDetail.setRebateUlimit(new BigDecimal(this.rebateUlimit[i]));

			rebateRuleDetailArray[i] = rebateRuleDetail;
		}

		if(RebateRuleType.TOTAL.getValue().equals(rebateRule.getIsCommon())){//POS返利规则只能有一条
			Map params = new HashMap();
			params.put("cardBranch", rebateRule.getCardBranch());
			params.put("isCommon", RebateRuleType.TOTAL.getValue());
			List<RebateRule> rebateRules = rebateRuleDAO.findRebateRule(params);
			if(rebateRules.size() > 0){
				throw new BizException("机构["+rebateRule.getCardBranch()+"]POS返利规则已存在!");
			}
		}
//		rebateRule.setIsCommon(Symbol.NO);// 非通用客户返利规则
//		rebateRule.setCardBranch(getSessionUser().getBranchNo());
		this.saleCardRuleService.addRebateRule(this.rebateRule, rebateRuleDetailArray, getSessionUser());

		String msg = LogUtils.r("新增返利规则ID为[{0}]的返利规则成功！", rebateRule.getRebateId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/customerRebateMgr/rebateRule/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/**
	 * 打开修改页面的初始化操作
	 */
	public String showModify() throws Exception {
		super.checkEffectiveCertUser();
		
		// 当前登录用户角色只能是发卡机构
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能修改客户返利规则。");
		}
		
		this.calTypeList = RebateRuleCalType.getAll();
		// 返利规则明细
		this.rebateRule = (RebateRule) this.rebateRuleDAO.findByPk(this.rebateRule.getRebateId());
		// 返利规则分段比例明细
		Map<String, Object> params = new HashMap<String, Object>();
		if (rebateRule != null)
			params.put("rebateId", rebateRule.getRebateId());
		rebateRuleDetailList = rebateRuleDetailDAO.findRebateRuleDetail(params);

		return MODIFY;
	}

	/**
	 * 修改
	 */
	public String modify() throws Exception {
		super.checkEffectiveCertUser();
		
		RebateRuleDetail[] rebateRuleDetailArray = new RebateRuleDetail[rebateUlimit.length];
		for (int i = 0; i < rebateUlimit.length; i++) {
			RebateRuleDetail rebateRuleDetail = new RebateRuleDetail();

			rebateRuleDetail.setRebateId(this.rebateRule.getRebateId());
			rebateRuleDetail.setRebateSect((short) (i + 1));
			rebateRuleDetail.setRebateUlimit(new BigDecimal(
					this.rebateUlimit[i]));
			rebateRuleDetail.setRebateRate(new BigDecimal(this.rebateRate[i]));

			rebateRuleDetailArray[i] = rebateRuleDetail;
		}

		// 修改返利规则表
		this.saleCardRuleService.modifyRebateRule(this.rebateRule,
				rebateRuleDetailArray, this.getSessionUser());
		String msg = LogUtils.r("修改返利规则ID为[{0}]的返利规则成功！", rebateRule
				.getRebateId());

		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/customerRebateMgr/rebateRule/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/**
	 * 删除
	 */
	public String delete() throws Exception {
		super.checkEffectiveCertUser();
		
		// 当前登录用户角色只能是发卡机构
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能删除客户返利规则。");
		}
		this.saleCardRuleService.deleteRebateRule(this.getRebateId());

		String msg = LogUtils.r("删除返利规则ID为[{0}]的返利规则成功！", rebateId);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/customerRebateMgr/rebateRule/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public RebateRule getRebateRule() {
		return rebateRule;
	}

	public void setRebateRule(RebateRule rebateRule) {
		this.rebateRule = rebateRule;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public RebateRuleDAO getRebateRuleDAO() {
		return rebateRuleDAO;
	}

	public void setRebateRuleDAO(RebateRuleDAO rebateRuleDAO) {
		this.rebateRuleDAO = rebateRuleDAO;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getCalTypeList() {
		return calTypeList;
	}

	public void setCalTypeList(List calTypeList) {
		this.calTypeList = calTypeList;
	}

	public Long getRebateId() {
		return rebateId;
	}

	public void setRebateId(Long rebateId) {
		this.rebateId = rebateId;
	}

	public List getRebateRuleDetailList() {
		return rebateRuleDetailList;
	}

	public void setRebateRuleDetailList(List rebateRuleDetailList) {
		this.rebateRuleDetailList = rebateRuleDetailList;
	}

	public String[] getRebateUlimit() {
		return rebateUlimit;
	}

	public void setRebateUlimit(String[] rebateUlimit) {
		this.rebateUlimit = rebateUlimit;
	}

	public String[] getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(String[] rebateRate) {
		this.rebateRate = rebateRate;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public List<YesOrNoFlag> getIsCommonList() {
		return isCommonList;
	}

	public void setIsCommonList(List<YesOrNoFlag> isCommonList) {
		this.isCommonList = isCommonList;
	}

}
