package gnete.card.web.promotions;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.dao.DiscntProtclDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.DiscntProtclDef;
import gnete.card.entity.flag.DiscntExclusiveFlag;
import gnete.card.entity.state.RuleState;
import gnete.card.entity.type.DiscntOperMethod;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PromotionsService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: DiscntProtclDefAction.java
 * 
 * @description: 折扣协议规则定义处理类
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-9
 */
public class DiscntProtclDefAction extends BaseAction {

	@Autowired
	private DiscntProtclDefDAO discntProtclDefDAO;
	@Autowired
	private PromotionsService promotionsService;
	@Autowired
	private DiscntClassDefDAO discntClassDefDAO;

	/**
	 * 规则状态
	 */
	private List ruleStatusList;

	/**
	 * 机构类型
	 */
	private List jinstTypeList;

	/**
	 * 折扣类型
	 */
	private List<DiscntClassDef> discntClassList;

	/**
	 * 折扣操作方法
	 */
	private List discntOperMthdList;

	/**
	 * 折扣排他标志
	 */
	private List discntExclusiveList;

	private DiscntProtclDef discntProtclDef;
	private String discntprotclNo;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		ruleStatusList = RuleState.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if (discntProtclDef != null) {
			params.put("discntprotclNo", discntProtclDef.getDiscntProtclNo());
			params.put("discntprotclName", MatchMode.ANYWHERE.toMatchString(discntProtclDef.getDiscntProtclName()));
			params.put("ruleStatus", discntProtclDef.getRuleStatus());
		}
		
		if (isCardRoleLogined()) {// 发卡机构
			params.put("jinstId", getSessionUser().getBranchNo());
		} else if (isFenzhiRoleLogined()) {// 分支机构
			List<BranchInfo> list = this.getMyManageFenzhi();
			params.put("fenzhiList", list);
		} else if (isCenterOrCenterDeptRoleLogined()) {// 运营中心或运营中心部门
			
		} else {
			throw new BizException("没有权限查看折扣协议规则定义");
		}
		
		page = discntProtclDefDAO.findDiscntProtclDefPage(params, getPageNumber(), getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询折扣协议规则定义列表！");
		return LIST;
	}

	/**
	 * 明细
	 */
	public String detail() throws Exception {
		discntProtclDef = (DiscntProtclDef) discntProtclDefDAO.findByPk(discntProtclDef.getDiscntProtclNo());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询折扣协议规则[" + discntProtclDef.getDiscntProtclNo() + "]明细信息！");
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("只有发卡机构才能定义折扣规则。");
		}
		jinstTypeList = IssType.getAll();
		discntOperMthdList = DiscntOperMethod.getManualList();
		discntExclusiveList = DiscntExclusiveFlag.getAll();
		ruleStatusList = RuleState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", getSessionUser().getBranchNo());
		discntClassList = discntClassDefDAO.findDiscntClassList(params);
		return ADD;
	}
	
	public String add() throws Exception {
		if (isCardOrCardDeptRoleLogined()) {
			discntProtclDef.setJinstId(getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限定义折扣规则。");
		}

		promotionsService.addDiscntProtclDef(discntProtclDef);
		String msg = LogUtils.r("新增折扣协议号为[{0}]的折扣协议规则成功。", discntProtclDef.getDiscntProtclNo());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/discntProtocolDef/list.do", msg);
		
		return SUCCESS;
	}

	/**
	 * 注销折扣协议规则
	 */
	public String cancel() throws Exception {
		promotionsService.cancelDiscntProtclDef(discntprotclNo);
		String msg = LogUtils.r("注销折扣协议号为[{0}]的折扣协议规则成功。", discntprotclNo);
		log(msg, UserLogType.OTHER);
		addActionMessage("/discntProtocolDef/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 启用折扣协议规则
	 */
	public String enable() throws Exception {
		promotionsService.enableDiscntProtclDef(discntprotclNo);
		String msg = LogUtils.r("启用折扣协议号为[{0}]的折扣协议规则成功。", discntprotclNo);
		log(msg, UserLogType.OTHER);
		addActionMessage("/discntProtocolDef/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 取得当前发卡机构号
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (isCardOrCardDeptRoleLogined()) {
			cardIssuerNo = this.getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public DiscntProtclDef getDiscntProtclDef() {
		return discntProtclDef;
	}

	public void setDiscntProtclDef(DiscntProtclDef discntProtclDef) {
		this.discntProtclDef = discntProtclDef;
	}

	public List getRuleStatusList() {
		return ruleStatusList;
	}

	public void setRuleStatusList(List ruleStatusList) {
		this.ruleStatusList = ruleStatusList;
	}

	public String getDiscntprotclNo() {
		return discntprotclNo;
	}

	public void setDiscntprotclNo(String discntprotclNo) {
		this.discntprotclNo = discntprotclNo;
	}

	public List getJinstTypeList() {
		return jinstTypeList;
	}

	public void setJinstTypeList(List jinstTypeList) {
		this.jinstTypeList = jinstTypeList;
	}

	public List<DiscntClassDef> getDiscntClassList() {
		return discntClassList;
	}

	public void setDiscntClassList(List<DiscntClassDef> discntClassList) {
		this.discntClassList = discntClassList;
	}

	public List getDiscntOperMthdList() {
		return discntOperMthdList;
	}

	public void setDiscntOperMthdList(List discntOperMthdList) {
		this.discntOperMthdList = discntOperMthdList;
	}

	public List getDiscntExclusiveList() {
		return discntExclusiveList;
	}

	public void setDiscntExclusiveList(List discntExclusiveList) {
		this.discntExclusiveList = discntExclusiveList;
	}
}
