package gnete.card.web.report;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.BalanceReportSet;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.type.DateType;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BalanceReportSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: BalanceReportSetAction.java
 * 
 * @description: 余额报表生成设置处理Action
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-28
 */
public class BalanceReportSetAction extends BaseAction {

	@Autowired
	private BalanceReportSetService balanceReportSetService;

	private BalanceReportSet balanceReportSet;
	private String cardBranches; // 要添加的发卡机构数组
	
	private List<DateType> dateTypeList;
	private List<BranchInfo> cardBranchList;
	private List<DayOfMonthType> monthList;
	private List<DayOfWeekType> weekList;
	private List<YesOrNoFlag> yesOrNoFlagList;

	private Paginater page;
	
	private String branchCode;
	
	/**
	 * 列表页面
	 */
	private static final String DEFUALT_LIST_URL = "/pages/report/balanceRepostSet/list.do";

	@Override
	public String execute() throws Exception {
		this.dateTypeList = DateType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (balanceReportSet != null) {
			params.put("cardBranch", balanceReportSet.getCardBranch());
			params.put("branchName", balanceReportSet.getBranchName());
			params.put("dateType", balanceReportSet.getDateType());
		}
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) { // 分支机构登录可查看自己管理的发卡机构的
			this.cardBranchList = sortBranchList(this.getMyCardBranch());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
		}
		
		this.page = this.balanceReportSetService.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		balanceReportSet = this.balanceReportSetService.findDetail(balanceReportSet.getCardBranch());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("只有运营中心能够设置余额报表生成规则");
		}
		initPage();
		return ADD;
	}
	
	/**
	 * 页面初始化
	 */
	private void initPage() {
		this.dateTypeList = DateType.getAll();
		this.monthList = DayOfMonthType.getAll();
		this.weekList = DayOfWeekType.getAll();
		this.yesOrNoFlagList = YesOrNoFlag.getAll();
	}

	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		String[] branches = cardBranches.split(",");
		this.balanceReportSetService.add(balanceReportSet, branches, this.getSessionUser());
		
		String msg = "新增余额报表生成设置规则成功";
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFUALT_LIST_URL, msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		initPage();
		balanceReportSet = this.balanceReportSetService.showModify(this.balanceReportSet.getCardBranch());
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.balanceReportSetService.modify(balanceReportSet, this.getSessionUser());
		
		String msg = LogUtils.r("修改发卡机构[{0}]的余额报表生成设置规则成功。", this.balanceReportSet.getCardBranch());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage(DEFUALT_LIST_URL, msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		this.balanceReportSetService.delete(this.getBranchCode());
		
		String msg = LogUtils.r("删除发卡机构[{0}]的余额报表生成设置规则成功。", this.branchCode);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(DEFUALT_LIST_URL, msg);
		return SUCCESS;
	}

	public BalanceReportSet getBalanceReportSet() {
		return balanceReportSet;
	}

	public void setBalanceReportSet(BalanceReportSet balanceReportSet) {
		this.balanceReportSet = balanceReportSet;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCardBranches() {
		return cardBranches;
	}

	public void setCardBranches(String cardBranches) {
		this.cardBranches = cardBranches;
	}

	public List<DateType> getDateTypeList() {
		return dateTypeList;
	}

	public void setDateTypeList(List<DateType> dateTypeList) {
		this.dateTypeList = dateTypeList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<DayOfMonthType> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<DayOfMonthType> monthList) {
		this.monthList = monthList;
	}

	public List<DayOfWeekType> getWeekList() {
		return weekList;
	}

	public void setWeekList(List<DayOfWeekType> weekList) {
		this.weekList = weekList;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<YesOrNoFlag> getYesOrNoFlagList() {
		return yesOrNoFlagList;
	}

	public void setYesOrNoFlagList(List<YesOrNoFlag> yesOrNoFlagList) {
		this.yesOrNoFlagList = yesOrNoFlagList;
	}
}
