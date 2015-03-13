package gnete.card.web.makecard;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.flag.MakeFlag;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MakeCardService;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 制卡申请处理、制卡审核 、制卡记录查询
 */
public class MakeCardAppAction extends BaseAction {

	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private MakeCardRegDAO makeCardRegDAO;
	@Autowired
	private MakeCardService makeCardService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private MakeCardApp makeCardApp;
	private Date startDate;
	private Date endDate;
	
	private Collection statusList;
	private Collection makeFlagList;

	private List<MakeCardReg> regList;

	private Paginater page;

	/**
	 * 制卡申请列表页 、制卡记录查询列表页
	 */
	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = MakeCardAppState.ALL.values();
		// 制卡方式列表
		this.makeFlagList = MakeFlag.ALL.values();
		// 当前机构下的卡样状态有效的制卡登记记录（卡样定版记录）列表
		this.regList = getEffectiveMakeCardReg(this.getLoginBranchCode());

		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("makeCardApp", makeCardApp);
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		if (isCenterOrCenterDeptRoleLogined()) {//  运营中心
			
		}else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		}else if (isCardRoleLogined()) {// 发卡机构
			params.put("cardIssuerList", this.getMyCardBranch()); // 当前用户所属机构号
			
		} else {
			throw new BizException("没有权限查看制卡申请记录");
		}
		this.page = this.makeCardAppDAO.findMakeCardAppPage(params, getPageNumber(), getPageSize());
		return LIST;
	}

	// 显示明细
	public String detail() throws Exception {
		this.makeCardApp = (MakeCardApp) this.makeCardAppDAO
				.findByPk(makeCardApp.getId());
		return DETAIL;
	}

	// 新增时初始化
	public String showAdd() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能添加制卡申请！");
		Assert.notBlank(makeCardApp.getBranchCode(), "发卡机构不能为空，请先选择发卡机构！");
		
		this.regList = this.getEffectiveMakeCardReg(makeCardApp.getBranchCode());
		
		BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(makeCardApp.getBranchCode());
		makeCardApp.setDeliveryUnit(branchInfo.getBranchName()); //收货单位默认填发卡机构
		makeCardApp.setDeliveryAdd(branchInfo.getAddress()); // 交货地点默认填发卡机构地址
		return ADD;
	}

	// 新增时的相关操作
	public String add() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能添加制卡申请！");
		
		Assert.isTrue(BranchUtil.isBelong2SameTopBranch(makeCardApp.getBranchCode(), this
				.getLoginBranchCode()), "发起方与发卡机构不是属于同一顶级机构！");
		
		// 保存数据
		this.makeCardService.addMakeCardApp(makeCardApp, getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的制卡申请已经提交。", makeCardApp.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/makeCardApp/list.do", msg);
		return SUCCESS;
	}

	// 审批列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.MAKE_CARD_APP, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCardOrCardDeptRoleLogined()) {
			params.put("cardIssuerList", this.getMyCardBranch());
		}else{
			throw new BizException("非发卡机构没有权限做制卡审核");
		}
		params.put("ids", ids);
		page = makeCardAppDAO.findMakeCardAppPage(params, getPageNumber(),
				getPageSize());

		return CHECK_LIST;
	}

	public String checkDetail() throws Exception {
		makeCardApp = (MakeCardApp) makeCardAppDAO.findByPk(makeCardApp.getId());
		return DETAIL;
	}

	public String showCancel() throws Exception {
		return "cancel";
	}
	
	public String cancel() throws Exception {
		this.makeCardService.cancel(makeCardApp, getSessionUser());
		String msg = LogUtils.r("ID为[{0}]的制卡申请已经取消。", makeCardApp.getId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/makeCardApp/list.do", msg);
		return SUCCESS;
	}

	public String showRevoke() throws Exception {
		return "revoke";
	}
	
	public String revoke() throws Exception {
		this.makeCardService.revoke(makeCardApp, getSessionUser());
		String msg = LogUtils.r("ID为[{0}]的建档已经撤销。", makeCardApp.getId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/makeCardApp/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 查询当前机构下的卡样状态有效的制卡登记记录
	 */
	private List<MakeCardReg> getEffectiveMakeCardReg(String cardIssuer) {
		Map<String, Object> cardRegMap = new HashMap<String, Object>();
		cardRegMap.put("branchCode", cardIssuer);
		cardRegMap.put("picStatus", MakeCardRegState.EFFECTIVE.getValue());
		return this.makeCardRegDAO.findByBranchCode(cardRegMap);
	}

	/**
	 * 取得起始卡号
	 */
	public void getStrNo() throws Exception {
		String strCardNo = "";
		if (!StringUtils.isBlank(makeCardApp.getMakeId())) {
			strCardNo = this.makeCardService.getStrNo(makeCardApp);
		}

		this.respond("{'strCardNo':'" + strCardNo + "'}");
	}

	/**
	 * 是否是正确的起始卡号
	 */
	public void isCorrectStrNo() throws Exception {
		// 获取页面的起始卡号
		String strCardNo = makeCardApp.getStrNo();
		boolean isCorrect = false;
		if (!StringUtils.isBlank(strCardNo)) {
			isCorrect = makeCardService.isCorrectStrNo(makeCardApp);
		}
		this.respond("{'isCorrect':" + isCorrect + "}");
	}

	public MakeCardApp getMakeCardApp() {
		return makeCardApp;
	}

	public void setMakeCardApp(MakeCardApp makeCardApp) {
		this.makeCardApp = makeCardApp;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<MakeCardReg> getRegList() {
		return regList;
	}

	public void setRegList(List<MakeCardReg> regList) {
		this.regList = regList;
	}

	public Collection getMakeFlagList() {
		return makeFlagList;
	}

	public void setMakeFlagList(Collection makeFlagList) {
		this.makeFlagList = makeFlagList;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
