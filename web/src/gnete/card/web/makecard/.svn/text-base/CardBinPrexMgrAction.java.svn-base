package gnete.card.web.makecard;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBinPreMgr;
import gnete.card.entity.CardBinPreReg;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardBinService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardBinPrexMgrAction.java
 *
 * @description: 卡BIN前三位管理Action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-10-10
 */
public class CardBinPrexMgrAction extends BaseAction {

	@Autowired
	private CardBinService cardBinService;

	private CardBinPreReg cardBinPreReg; // 卡BIN前三位申请登记薄
	private CardBinPreMgr cardBinPreMgr; // 卡BIN前三位管理表

	private Paginater page;

	// 状态列表
	private List<CheckState> statusList;
	// 一级分支机构列表
	private List<BranchInfo> branchList;
	
	private boolean showBranch = false;

	private static final String DEFUALT_LIST_URL = "/pages/cardBinMgr/cardBinPrex/list.do";
	
	@Override
	public String execute() throws Exception {
		// 加载状态列表
		this.statusList = CheckState.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardBinPreReg != null) {
			params.put("cardBinPrex", cardBinPreReg.getCardBinPrex());
			params.put("status", cardBinPreReg.getStatus());
			params.put("branchCode", cardBinPreReg.getBranchCode());
		}

		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.showBranch = true;
			this.branchList = this.sortBranchList(this.getFirstFenzhi()); // 取得一级分支机构列表
			if (CollectionUtils.isEmpty(branchList)) {
				branchList.add(new BranchInfo());
			}
		} 
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			this.showBranch = false;
			// 是否以一级分支机构的角色登录
			if (this.isLoginAsFirstFenzhi()) {
				params.put("branchCode", this.getSessionUser().getBranchNo());
			}
			// 否则没有权限查询
			else {
				throw new BizException("没有权限查询卡BIN前三位分配登记列表");
			}
		}
		else {
			throw new BizException("没有权限查询卡BIN前三位分配登记列表");
		}
		
		this.page = this.cardBinService.findCardBinPrexPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String checkList() throws Exception {
		if (!(RoleType.CENTER.getValue().equals(super.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(super.getLoginRoleType()))) {
			throw new BizException("没有权限进行卡BIN审核操作");
		}
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_CARD_BIN_PREX_REG, this.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			this.page = new Paginater(this.getPageSize(),this.getPageNumber());
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.page = this.cardBinService.findCardBinPrexPage(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 打开新增卡BIN页面的初始化操作
	public String showAdd() throws Exception {
		//  登录用户为运营中心、中心部门或分支机构时
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.branchList = this.sortBranchList(this.getFirstFenzhi()); // 取得管理的发卡机构
		} else {
			throw new BizException("没有权限分配卡BIN前三位。");
		}

		return ADD;
	}

	// 新增卡bin
	public String add() throws Exception {
		// 保存数据，同时启动流程
		this.cardBinService.addCardBinPrexReg(cardBinPreReg, this.getSessionUser());

		String msg = LogUtils.r("登记簿id为[{0}]卡BIN前三位为[{1}]的申请已经提交！", cardBinPreReg.getId(), cardBinPreReg.getCardBinPrex());

		this.addActionMessage(DEFUALT_LIST_URL, msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	// 判断卡BIN是否已经存在
	public void isExistBinNo() throws Exception {
		boolean isExist = this.cardBinService.isExistCardBinPrex(cardBinPreReg.getCardBinPrex());
		this.respond("{'isExist':" + isExist + "}");
	}

	// 取得卡BIN申请的明细
	public String detail() {
		cardBinPreReg = (CardBinPreReg) this.cardBinService.findCardBinPrexDetail(cardBinPreReg.getId());
		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public CardBinPreReg getCardBinPreReg() {
		return cardBinPreReg;
	}

	public void setCardBinPreReg(CardBinPreReg cardBinPreReg) {
		this.cardBinPreReg = cardBinPreReg;
	}

	public CardBinPreMgr getCardBinPreMgr() {
		return cardBinPreMgr;
	}

	public void setCardBinPreMgr(CardBinPreMgr cardBinPreMgr) {
		this.cardBinPreMgr = cardBinPreMgr;
	}

	public List<CheckState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CheckState> statusList) {
		this.statusList = statusList;
	}

	public boolean isShowBranch() {
		return showBranch;
	}

	public void setShowBranch(boolean showBranch) {
		this.showBranch = showBranch;
	}

}
