package gnete.card.web.makecard;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CostRecordDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CostRecord;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CostRecordState;
import gnete.card.entity.type.CostRecordType;
import gnete.card.entity.type.PayType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductService;
import gnete.card.service.mgr.BranchCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @File: SingleProductChargeAction.java
 *
 * @description: 单机产品缴费
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-13
 */
public class SingleProductChargeAction extends BaseAction {

	@Autowired
	private SingleProductService singleProductService;
	@Autowired
	private CostRecordDAO costRecordDAO;

	private List<CostRecordState> statusList;
	private List<CostRecordType> typeList; // 单机产品费用记录类型
	private List<PayType> payTypeList; // 单机产品缴费方式
	/** 是否已开发票 */
	private List<YesOrNoFlag> invoiceList;

	private CostRecord costRecord; // 单机产品费用记录表
	
	/** 费用产生起始日期 */
	private String startDate;
	/** 费用产生结束日期 */
	private String endDate;
	
	/** 缴费起始日期 */
	private String chargeStartDate;
	/** 缴费结束日期 */
	private String chargeEndDate;
	
	private String ids;

	private Paginater page;
	
	private static final String DEFAULT_URL = "/pages/singleProduct/charge/list.do?goBack=goBack";
	private static final String C_START_DATE = "chargeStartDate";
	private static final String C_END_DATE = "chargeEndDate";

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = CostRecordState.getAll();
		this.typeList = CostRecordType.getList();
		this.payTypeList = PayType.getAll();
		this.invoiceList = YesOrNoFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (costRecord != null) {
			params.put("searchCardBranch", MatchMode.ANYWHERE.toMatchString(costRecord.getBranchCode()));
			params.put("type", costRecord.getType());
			params.put("status", costRecord.getStatus());
			params.put("payType", costRecord.getPayType());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
			
			DatePair chargerDatePair = new DatePair(this.chargeStartDate, this.chargeEndDate);
			chargerDatePair.setTruncatedTimeDate(params, C_START_DATE, C_END_DATE);
		}
		
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		} 
		// 发卡机构登录时
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else {
			throw new BizException("没有权限查看单机产品缴费列表");
		}

		this.page = this.singleProductService.findChargePage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		costRecord = (CostRecord) this.costRecordDAO.findByPk(costRecord.getId());
		return DETAIL;
	}

	/** 
	 * 缴费初始化页面
	 * @return
	 * @throws Exception
	 */
	public String showCharge() throws Exception {
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("没有权限操作！");
		}
		costRecord = (CostRecord) this.costRecordDAO.findByPk(costRecord.getId());
		Assert.notNull(costRecord, "费用记录ID为[" + costRecord.getId() + "]的记录不存在");
		
		BranchInfoDAO branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
		BranchInfo cardBranch = branchInfoDAO.findBranchInfo(costRecord.getBranchCode());
		Assert.notNull(cardBranch, "费用记录ID为[" + costRecord.getId() + "]归属的发卡机构不存在");
		
		
		BranchInfo branchInfo = BranchCache.getInstance().getBranchInfo(costRecord.getBranchCode());
		Assert.equals(branchInfo.getParent(), this.getLoginBranchCode(), 
				"发卡机构[" + costRecord.getBranchCode() + "]不是当前运营机构所管理的，没有权限");
		
//		List<String> list = this.getMyCardBranchNo();
//		if (!list.contains(costRecord.getBranchCode())) {
//			throw new BizException("发卡机构[" + costRecord.getBranchCode() + "]不是当前运营机构所管理的，没有权限");
//		}
		costRecord.setPaidAmt(costRecord.getAmt());
		return "charge";
	}
	
	/**
	 * 新增套餐定义
	 */
	public String charge() throws Exception {
		
		this.singleProductService.costCharge(costRecord, this.getSessionUser());
		
		String msg = LogUtils.r("用户[{0}]为发卡机构缴费[{1}]成功。", this.getSessionUserCode(), costRecord.getBranchCode());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage(DEFAULT_URL, msg);
		
		return SUCCESS;
	}
	
	public String showInvoice() throws Exception {
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
		} 
		else {
			throw new BizException("没有权限做开发票的操作");
		}
		
		// 状态列表
		this.statusList = CostRecordState.getAll();
		this.typeList = CostRecordType.getList();
		this.payTypeList = PayType.getAll();
		
		return "invoice";
	}
	
	public String findInvoice() throws Exception {
		// 状态列表
		this.statusList = CostRecordState.getAll();
		this.typeList = CostRecordType.getList();
		this.payTypeList = PayType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (costRecord != null) {
			params.put("searchCardBranch", MatchMode.ANYWHERE.toMatchString(costRecord.getBranchCode()));
			params.put("type", costRecord.getType());
			params.put("payType", costRecord.getPayType());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
			
			DatePair chargerDatePair = new DatePair(this.chargeStartDate, this.chargeEndDate);
			chargerDatePair.setTruncatedTimeDate(params, C_START_DATE, C_END_DATE);
		}
		
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		} 
		else {
			throw new BizException("没有权限做开发票的操作");
		}

		params.put("invoiceStatus", Symbol.NO); //只能查找没开发票的记录
		params.put("status", CostRecordState.PAYED.getValue());
		this.page = this.singleProductService.findChargePage(params, this.getPageNumber(), this.getPageSize());
		
		return "invoice";
	}
	
	/**
	 * 开发票
	 * @return
	 * @throws Exception
	 */
	public String invoice() throws Exception {
		this.singleProductService.singleProductCardInvoice(ids);
		
		String msg = LogUtils.r("用户[{0}]为记录[{1}]开发票成功。", this.getSessionUserCode(), ObjectUtils.nullSafeToString(ids));
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage(DEFAULT_URL, msg);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CostRecordState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CostRecordState> statusList) {
		this.statusList = statusList;
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

	public List<CostRecordType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<CostRecordType> typeList) {
		this.typeList = typeList;
	}

	public CostRecord getCostRecord() {
		return costRecord;
	}

	public void setCostRecord(CostRecord costRecord) {
		this.costRecord = costRecord;
	}

	public List<PayType> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<PayType> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public String getChargeStartDate() {
		return chargeStartDate;
	}

	public void setChargeStartDate(String chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
	}

	public String getChargeEndDate() {
		return chargeEndDate;
	}

	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<YesOrNoFlag> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<YesOrNoFlag> invoiceList) {
		this.invoiceList = invoiceList;
	}

}
