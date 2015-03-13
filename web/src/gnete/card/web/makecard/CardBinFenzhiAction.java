package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.FenZhiCardBinMgrDAO;
import gnete.card.entity.BranchHasType;
import gnete.card.entity.BranchHasTypeKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.FenZhiCardBinMgr;
import gnete.card.entity.FenzhiCardBinReg;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.FenzhiCardBinMgrState;
import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardBinService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardBinFenzhiAction.java
 *
 * @description: 运营/分支机构卡BIN分配
 * 	<li>上级将自己拥有的卡BIN分配给自己的下级</li>
 * 	<li>分支机构的卡BIN分配不能跨级分配</li>
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-11-8
 */
public class CardBinFenzhiAction extends BaseAction {

	@Autowired
	private CardBinService cardBinService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchHasTypeDAO branchHasTypeDAO;
	@Autowired
	private FenZhiCardBinMgrDAO fenZhiCardBinMgrDAO;

	private FenzhiCardBinReg fenzhiCardBinReg; // 运营机构卡BIN申请登记薄
	private FenZhiCardBinMgr fenZhiCardBinMgr; // 运营机构卡BIN分配表

	private Paginater page;

	// 状态列表
	private List<CheckState> statusList;
	// 一级分支机构列表
	private List<BranchInfo> branchList;
	// 是否列表
	private List<YesOrNoFlag> yesOrNoFlagList;

	private static final String DEFUALT_LIST_URL = "/pages/cardBinMgr/cardBinFenzhi/list.do";
	
	@Override
	public String execute() throws Exception {
		// 加载状态列表
		this.statusList = CheckState.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (fenzhiCardBinReg != null) {
			params.put("regId", MatchMode.ANYWHERE.toMatchString(fenzhiCardBinReg.getRegId()));
			params.put("status", fenzhiCardBinReg.getStatus());
			params.put("strBinNo", fenzhiCardBinReg.getStrBinNo());
			params.put("appBranch", fenzhiCardBinReg.getAppBranch());
		}
		if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())) {
			this.branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue()); // 取得所有的分支机构
		} 
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
			
//			this.branchList = getMyFenzhiTreeList(this.getLoginBranchCode());
			this.branchList = this.getMyManageFenzhi();
//			this.branchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
		}
		else {
			throw new BizException("没有权限查询分支机构卡BIN申请登记列表");
		}
		this.page = this.cardBinService.findFenzhiCardBinRegPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 审核列表
	 */
	public String checkList() throws Exception {
		if (!(RoleType.FENZHI.getValue().equals(super.getLoginRoleType()))) {
			throw new BizException("没有权限进行分支机构卡BIN分配审核操作");
		}
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_CARD_BIN_FENZHI_REG, this.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.page = this.cardBinService.findFenzhiCardBinRegPage(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	/**
	 * 分支机构卡BIN列表
	 */
	public String fenzhiBinList() throws Exception {
		// 加载状态列表
		this.statusList = FenzhiCardBinMgrState.getAll();
		this.yesOrNoFlagList = YesOrNoFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (fenZhiCardBinMgr != null) {
			params.put("cardBin", MatchMode.ANYWHERE.toMatchString(fenZhiCardBinMgr.getCardBin()));
			params.put("cardBinPrex", fenZhiCardBinMgr.getCardBinPrex());
			params.put("status", fenZhiCardBinMgr.getStatus());
			params.put("useFlag", fenZhiCardBinMgr.getUseFlag());
			params.put("currentBranch", fenZhiCardBinMgr.getCurrentBranch());
		}
		
		// 运营中心登录，查询所有的
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())) {
			this.branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue()); // 取得所有的分支机构
		}
		// 分支机构登录时，可查询自己和自己管理的分支机构及自己的下级分支机构的下级的记录
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			this.branchList = this.getMyManageFenzhi();
//			this.branchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
			
			params.put("fenzhiList", branchList);
			
//			this.branchList = getMyFenzhiTreeList(this.getLoginBranchCode());
		}
		else {
			throw new BizException("没有权限查询分支机构卡BIN列表");
		}
		
		this.page = this.cardBinService.findFenzhiCardBinPage(params, this.getPageNumber(), this.getPageSize());
		return "fenzhiBinList";
	}
	
	/**
	 * 分支机构卡BIN明细
	 */
	public String fenzhiBinDetail() throws Exception {
		this.fenZhiCardBinMgr = (FenZhiCardBinMgr) this.fenZhiCardBinMgrDAO.findByPk(fenZhiCardBinMgr.getCardBin());
		
		return "fenzhiBinDetail";
	}
	
	/**
	 * 根据分支机构编码，取得下一级分支机构编码
	 */
	private List<BranchInfo> getMyFenzhiTreeList(String branchCode){
		List<BranchInfo> result = new ArrayList<BranchInfo>();
		BranchHasTypeKey key = new BranchHasTypeKey();
		
		key.setBranchCode(this.getLoginBranchCode());
		key.setTypeCode(this.getLoginRoleType());
		BranchHasType branchType = (BranchHasType) branchHasTypeDAO.findByPk(key);
		
		List<BranchInfo> list = this.branchInfoDAO.findByTypeAndManage(RoleType.FENZHI.getValue(), branchCode);

		result.addAll(list);
		
		// 如果是一级分支机构，则list里取得就是自己所管理的有的二级分支机构
		if (BranchLevelType.FIRST.getValue().equals(branchType.getBranchLevel())) {
			for (BranchInfo second : list) {
				List<BranchInfo> thirdList = this.branchInfoDAO.findByTypeAndManage(RoleType.FENZHI.getValue(), second.getBranchCode());
				
				result.addAll(thirdList);
			}
		}
		// 如果是二级分支机构, 则list里取得就是自己所管理的有的三级级分支机构,如果是三级分支机构，则list为空 
		
		return result;
	}
	
	// 只有分支机构能够做卡BIN分配的权利
	public String showAdd() throws Exception {
		if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			this.fenzhiCardBinReg = new FenzhiCardBinReg();
			fenzhiCardBinReg.setStrBinNo(this.fenZhiCardBinMgrDAO.findMinAbleCardBin(getLoginBranchCode()));
			
			this.branchList = getMyFenzhiTreeList(this.getLoginBranchCode());
//			this.branchList = this.getMyManageFenzhi();
		} else {
			throw new BizException("没有权限申请卡BIN。");
		}

		return ADD;
	}
	
	/**
	 * 检查起始卡BIN号是否属于当前登录机构
	 * @throws Exception
	 */
	public void checkStrCardBin() throws Exception {
		
	}
	
	/**
	 * 检查卡BIN数量是否正确
	 * @throws Exception
	 */
	public void checkBinCount() throws Exception {
		
	}

	// 新增卡bin
	public String add() throws Exception {
		// 保存数据，同时启动流程
		this.cardBinService.addFenzhiCardBinReg(fenzhiCardBinReg, this.getSessionUser());

		String msg = LogUtils.r("登记簿id为[{0}]的运营分支机构卡BIN分配申请已经提交！", fenzhiCardBinReg.getRegId());

		this.addActionMessage(DEFUALT_LIST_URL, msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	// 取得卡BIN申请的明细
	public String detail() {
		fenzhiCardBinReg = (FenzhiCardBinReg) this.cardBinService.findFenzhiCardBinRegDetail(fenzhiCardBinReg.getRegId());
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

	public List<CheckState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CheckState> statusList) {
		this.statusList = statusList;
	}

	public FenzhiCardBinReg getFenzhiCardBinReg() {
		return fenzhiCardBinReg;
	}

	public void setFenzhiCardBinReg(FenzhiCardBinReg fenzhiCardBinReg) {
		this.fenzhiCardBinReg = fenzhiCardBinReg;
	}

	public FenZhiCardBinMgr getFenZhiCardBinMgr() {
		return fenZhiCardBinMgr;
	}

	public void setFenZhiCardBinMgr(FenZhiCardBinMgr fenZhiCardBinMgr) {
		this.fenZhiCardBinMgr = fenZhiCardBinMgr;
	}

	public List<YesOrNoFlag> getYesOrNoFlagList() {
		return yesOrNoFlagList;
	}

	public void setYesOrNoFlagList(List<YesOrNoFlag> yesOrNoFlagList) {
		this.yesOrNoFlagList = yesOrNoFlagList;
	}

}
