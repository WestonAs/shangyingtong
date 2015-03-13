package gnete.card.web.merch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardMerchGroupDAO;
import gnete.card.dao.CardMerchToGroupDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardMerchGroup;
import gnete.card.entity.CardMerchToGroup;
import gnete.card.entity.state.CardMerchGroupState;
import gnete.card.entity.type.FeeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardMerchGroupAction.java
 * 
 * @description: 发卡机构商户组管理
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-3
 */
public class CardMerchGroupAction extends BaseAction {

	@Autowired
	private CardMerchGroupDAO cardMerchGroupDAO;
	@Autowired
	private CardMerchToGroupDAO cardMerchToGroupDAO;
	@Autowired
	private MerchService merchService;

	private CardMerchGroup cardMerchGroup;
	private CardMerchToGroup cardMerchToGroup;

	private String merchants;
	private String merchNames;
	private String branchCode;
	private String groupId;

	private List<CardMerchToGroup> groupList;
	private List statusList;
	private List feeTypeList;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		statusList = CardMerchGroupState.getAll();
		feeTypeList = FeeType.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardMerchGroup != null) {
			params.put("groupId", cardMerchGroup.getGroupId());
			params.put("groupName", MatchMode.ANYWHERE
					.toMatchString(cardMerchGroup.getGroupName()));
			params.put("feeType", cardMerchGroup.getFeeType());
			params.put("status", cardMerchGroup.getStatus());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("branchCode", getSessionUser().getBranchNo());
		}
		// 运营中心看所有的
		else if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
		}
		// 分支机构查看其管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			List<BranchInfo> branches = this.getMyCardBranch();
			if (CollectionUtils.isEmpty(branches)) {
				branches.add(new BranchInfo());
			}
			params.put("branchList", branches);
		}
		else {
			throw new BizException("当前用户没有权限进入该菜单！");
		}
		this.page = cardMerchGroupDAO.findCardMerchGroup(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 */
	public String detail() throws Exception {
		cardMerchGroup = (CardMerchGroup) cardMerchGroupDAO
				.findByPk(cardMerchGroup);
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", cardMerchGroup.getBranchCode());
		params.put("groupId", cardMerchGroup.getGroupId());
		groupList = cardMerchToGroupDAO.findByGroupIdAndBranch(params);
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
				|| RoleType.FENZHI.getValue().equals(getLoginRoleType())
				|| RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
		} else {
			throw new BizException("只有运营中心或发卡机构才能定义商户组");
		}
		feeTypeList = FeeType.getAll();
		return ADD;
	}

	/**
	 * 新增页面
	 */
	public String add() throws Exception {
		cardMerchGroup.setBranchCode(getSessionUser().getBranchNo());

		merchService.addCardMerchGroup(cardMerchGroup, merchants,
				getSessionUserCode());
		String msg = LogUtils.r("新增发卡机构[{0}]商户组[{1}]成功", cardMerchGroup
				.getBranchCode(), cardMerchGroup.getGroupId());
		this.addActionMessage("/pages/cardMerchGroup/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	private void initPage(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", cardMerchGroup.getBranchCode());
		params.put("groupId", cardMerchGroup.getGroupId());
		List<CardMerchToGroup> list = cardMerchToGroupDAO.findByGroupIdAndBranch(params);
		this.merchants = "";
		this.merchNames = "";
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				CardMerchToGroup toGroup = list.get(i);
				this.merchants += toGroup.getMerchId();
				this.merchNames += toGroup.getMerchName();
				if (i < list.size() - 1) {
					merchants += ",";
					merchNames += ",";
				}
			}
		}
	}

	public String showModify() throws Exception {
		if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
		} else {
			throw new BizException("只有发卡机构或其部门才能修改商户组");
		}
		feeTypeList = FeeType.getAll();
		cardMerchGroup = (CardMerchGroup) cardMerchGroupDAO.findByPk(cardMerchGroup);
		initPage();
		return MODIFY;
	}

	/**
	 * 修改页面
	 */
	public String modify() throws Exception {
		merchService.modifyCardMerchGroup(cardMerchGroup, merchants,
				getSessionUserCode());
		String msg = LogUtils.r("修改发卡机构[{0}]商户组[{1}]成功", cardMerchGroup
				.getBranchCode(), cardMerchGroup.getGroupId());
		this.addActionMessage("/pages/cardMerchGroup/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}

	/**
	 * 注销发卡机构商户组
	 */
	public String cancel() throws Exception {
		merchService.cancelCardMerchGroup(branchCode, groupId);

		String msg = LogUtils.r("注销发卡机构[{0}]商户组[{1}]成功", branchCode, groupId);
		this.addActionMessage("/pages/cardMerchGroup/list.do", msg);
		this.log(msg, UserLogType.OTHER);
		return SUCCESS;
	}

	/**
	 * 启用发卡机构商户组
	 */
	public String start() throws Exception {
		merchService.startCardMerchGroup(branchCode, groupId);

		String msg = LogUtils.r("启用发卡机构[{0}]商户组[{1}]成功", branchCode, groupId);
		this.addActionMessage("/pages/cardMerchGroup/list.do", msg);
		this.log(msg, UserLogType.OTHER);
		return SUCCESS;
	}

	/**
	 * 取得当前发卡机构的机构号
	 */
	public String getBranchNo() {
		String branchNo = "";
		if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchNo = getSessionUser().getBranchNo();
		} else {
			branchNo = null;
		}
		return branchNo;
	}

	public CardMerchGroup getCardMerchGroup() {
		return cardMerchGroup;
	}

	public void setCardMerchGroup(CardMerchGroup cardMerchGroup) {
		this.cardMerchGroup = cardMerchGroup;
	}

	public CardMerchToGroup getCardMerchToGroup() {
		return cardMerchToGroup;
	}

	public void setCardMerchToGroup(CardMerchToGroup cardMerchToGroup) {
		this.cardMerchToGroup = cardMerchToGroup;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public List<CardMerchToGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<CardMerchToGroup> groupList) {
		this.groupList = groupList;
	}

	public String getMerchants() {
		return merchants;
	}

	public void setMerchants(String merchants) {
		this.merchants = merchants;
	}

	public String getMerchNames() {
		return merchNames;
	}

	public void setMerchNames(String merchNames) {
		this.merchNames = merchNames;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
