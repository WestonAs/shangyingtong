package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardGroupDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardGroup;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardGroupAction.java
 *
 * @description: 为某一集团添加发卡机构
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-5-25
 */
public class CardGroupAction extends BaseAction {
	
	@Autowired
	private CardGroupDAO cardGroupDAO;
	@Autowired
	private BranchService branchService;
	
	private CardGroup cardGroup;
	
	private Paginater page;
	
	private String groupId;	// 集团号
	private String cardBranches; // 机构号
	private String branchCode; // 发卡机构号
	private List<CommonState> statusList;
	
	private static final String lIST_URL = "/pages/cardGroup/list.do";

	@Override
	public String execute() throws Exception {
		this.statusList = CommonState.getList();
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardGroup != null) {
			params.put("branchCode", MatchMode.ANYWHERE.toMatchString(cardGroup.getBranchCode()));
			params.put("groupId", MatchMode.ANYWHERE.toMatchString(cardGroup.getGroupId()));
			params.put("status", cardGroup.getStatus());
		}
		// 运营中心看所有的
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
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
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
					|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
				params.put("branchCode", getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查看发卡机构集团关系");
		}
		this.page = this.cardGroupDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	/**
	 * 明细
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		this.cardGroup = (CardGroup) this.cardGroupDAO.findByPk(cardGroup.getBranchCode());
		return DETAIL;
	}
	
	/** 
	 * 新增集团与发卡机构关系
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
			throw new BizException("没有权限维护集团与发卡机构关系");
		}
		return ADD;
	}
	
	/** 
	 * 新增集团与发卡机构关系
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.addCardGroup(cardGroup.getGroupId(), cardBranches, this.getSessionUser());
		
		String msg = LogUtils.r("新增集团[{0}]与发卡机构[{1}]关系成功", cardGroup.getGroupId(), cardBranches);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(lIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception {
		this.checkEffectiveCertUser();
		
//		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
//				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
//				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
//			throw new BizException("没有权限维护集团与发卡机构关系");
//		}
		
		this.branchService.cancelCardGroup(branchCode, groupId, this.getSessionUser());
		
		String msg = LogUtils.r("注销集团[{0}]与发卡机构[{1}]关系成功", groupId, branchCode);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(lIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 启用
	 * @return
	 * @throws Exception
	 */
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
//		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
//				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
//				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
//			throw new BizException("没有权限维护集团与发卡机构关系");
//		}
		
		this.branchService.activateCardGroup(branchCode, groupId, this.getSessionUser());
		
		String msg = LogUtils.r("启用集团[{0}]与发卡机构[{1}]关系成功", groupId, branchCode);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(lIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
//		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
//				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
//				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
//			throw new BizException("没有权限维护集团与发卡机构关系");
//		}
		
		this.branchService.deleteCardGroup(branchCode);
		
		String msg = LogUtils.r("删除集团[{0}]与发卡机构[{1}]关系成功", groupId, branchCode);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(lIST_URL, msg);
		return SUCCESS;
	}

	public CardGroup getCardGroup() {
		return cardGroup;
	}

	public void setCardGroup(CardGroup cardGroup) {
		this.cardGroup = cardGroup;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
	}

	public String getCardBranches() {
		return cardBranches;
	}

	public void setCardBranches(String cardBranches) {
		this.cardBranches = cardBranches;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
