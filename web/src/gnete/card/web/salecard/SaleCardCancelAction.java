package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RebateRule;
import gnete.card.entity.SaleCardReg;
import gnete.card.entity.flag.SaleCancelFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRegService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: SaleCardCancelAction.java
 *
 * @description: 售卡撤销处理action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-11-23
 */
public class SaleCardCancelAction extends BaseAction {
	
	@Autowired
	private SaleCardRegService saleCardRegService;
	
	private SaleCardReg saleCardReg;
	private SaleCardReg saleCardCancel;
	
	private Paginater page;
	
	// 是否需要在登记簿中记录签名信息
	private boolean signatureReg;

	private List<RegisterState> statusList;
	/** 卡类型列表 */
	private List<CardType> cardTypeList;
	
	// 是否按次充值
	private boolean depositTypeIsTimes;
	// 客户返利
	private RebateRule saleRebateRule;
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
	private List<BranchInfo> cardBranchList;
	 /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
    /** 结束日期 */
    private String endDate;
    
	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getForSaleCancelList();
		this.cardTypeList = CardType.getCardTypeWithoutPoint();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (saleCardReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(saleCardReg.getCardId()));
			params.put("saleBatchId", saleCardReg.getSaleBatchId());
			params.put("cardCustomer", saleCardReg.getCardCustomerName());
			params.put("cardBranch", saleCardReg.getCardBranch());
			params.put("status", saleCardReg.getStatus());		
			params.put("cardClass", saleCardReg.getCardClass());	
			params.put("startDate", this.startDate);
			params.put("endDate", this.endDate);
		}
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门
			showCardBranch = true;
		} else if (isFenzhiRoleLogined()) { // 运营机构
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardDeptRoleLogined() || isCardSellRoleLogined()) {// 售卡代理或发卡机构部门时
			this.cardBranchList = this.getMyCardBranch();
			params.put("branchCode", super.getSessionUser().getBranchNo());
		} else if (isCardRoleLogined()) { // 发卡机构
		// params.put("cardBranch", super.getSessionUser().getBranchNo());
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看售卡撤销记录");
		}
		
		//params.put("cancelFlag", Symbol.YES);
		params.put("saleCancel", SaleCancelFlag.CANCEL.getValue());// 售卡撤销
		
		this.page = this.saleCardRegService.findSaleCardCancelPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	/**
	 * 售卡撤销明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Assert.notNull(this.saleCardReg, "售卡对象不能为空");	
		this.saleCardReg = this.saleCardRegService.findSaleCardCancelDetail(saleCardReg.getSaleBatchId());
		
		this.saleRebateRule = this.saleCardRegService.findRebateRule(saleCardReg.getRebateId());
		return DETAIL;
	}
	
	/**
	 * 新建售卡撤销的页面
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		if (isCardOrCardDeptRoleLogined() || isCardSellRoleLogined()) {
		} else {
			throw new BizException("非发卡机构、机构网点或者售卡代理,不允许进行售卡撤销操作。");
		}
		this.statusList = RegisterState.getForSaleCancelDeal();
		
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		this.startDate = workDate;
		this.endDate = workDate;
		
		// 是否需要验签
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
			saleCardCancel = new SaleCardReg();
			// 随机数
			saleCardCancel.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		}
		return ADD;
	}
	
	/**
	 * 查找符合条件的售卡记录
	 * @return
	 * @throws Exception
	 */
	public String findSale() throws Exception {
		this.statusList = RegisterState.getForSaleCancelDeal();
		
		// 是否需要验签
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
			saleCardCancel = new SaleCardReg();
			// 随机数
			saleCardCancel.setRandomSessionId(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		}

		Map<String, Object> params = new HashMap<String, Object>();
		if (saleCardReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(saleCardReg.getCardId()));
			params.put("saleBatchId", saleCardReg.getSaleBatchId());
			params.put("status", saleCardReg.getStatus());
			params.put("startDate", this.startDate);
			params.put("endDate", this.endDate);
		}
		
		if (isCardRoleLogined() || isCardSellRoleLogined()) { // 发卡机构 或 售卡代理
			params.put("branchCode", super.getSessionUser().getBranchNo());
		} else if (isCardDeptRoleLogined()) { // 发卡机构部门
			params.put("branchCode", super.getSessionUser().getDeptId());
		} else {
			throw new BizException("没有权限做售卡撤销操作");
		}
		
		params.put("statusList", statusList);
		params.put("saleCancel", SaleCancelFlag.NORMAL.getValue());// 普通售卡
		
		this.page = this.saleCardRegService.findSaleCardCancelPage(params, this.getPageNumber(), this.getPageSize());
		return ADD;
	}
	
	/**
	 *  新增售卡撤销处理
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		String serialNo = request.getParameter("serialNo");
		
		this.saleCardRegService.addSaleCardCancel(saleCardCancel, this.getSessionUser(), serialNo);
		
		String msg = LogUtils.r("新增售卡撤销申请[{0}]成功", this.saleCardCancel.getSaleBatchId());
		this.addActionMessage("/saleCardCancel/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	/**
	 * 要审核的充值撤销列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCardRoleLogined()) { // 发卡机构可以审批自己所发的卡和自己提交的售卡撤销记录
//			this.cardBranchList = this.getMyCardBranch();
//			params.put("cardBranchList", cardBranchList);
			params.put("cardBranchCheck", getSessionUser().getBranchNo());
			
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门只能审核自己提交的
			params.put("branchCode", getSessionUser().getDeptId());
			
		} else if (isCardSellRoleLogined()) {// 售卡代理只能审核自己提交的
			params.put("branchCode", getSessionUser().getBranchNo());
			
		} else {
			throw new BizException("没有权限进行售卡撤销审核操作");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_SALECARD_CANCEL, this.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		params.put("ids", ids);
		
		this.page = this.saleCardRegService.findSaleCardCancelPage(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	/**
	 * 要审核的充值撤销的明细
	 * @return
	 * @throws Exception
	 */
	public String checkDetail() throws Exception {
		Assert.notNull(this.saleCardReg, "售卡对象不能为空");	
		this.saleCardReg = this.saleCardRegService.findSaleCardCancelDetail(saleCardReg.getSaleBatchId());
		
		this.saleRebateRule = this.saleCardRegService.findRebateRule(saleCardReg.getRebateId());
		return "checkDetail";
	}
	
	public SaleCardReg getSaleCardReg() {
		return saleCardReg;
	}

	public void setSaleCardReg(SaleCardReg saleCardReg) {
		this.saleCardReg = saleCardReg;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public boolean isDepositTypeIsTimes() {
		return depositTypeIsTimes;
	}

	public void setDepositTypeIsTimes(boolean depositTypeIsTimes) {
		this.depositTypeIsTimes = depositTypeIsTimes;
	}

	public RebateRule getSaleRebateRule() {
		return saleRebateRule;
	}

	public void setSaleRebateRule(RebateRule saleRebateRule) {
		this.saleRebateRule = saleRebateRule;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public SaleCardReg getSaleCardCancel() {
		return saleCardCancel;
	}

	public void setSaleCardCancel(SaleCardReg saleCardCancel) {
		this.saleCardCancel = saleCardCancel;
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

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}
	
}
