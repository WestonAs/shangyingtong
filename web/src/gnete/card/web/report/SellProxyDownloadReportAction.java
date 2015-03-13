package gnete.card.web.report;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportPathSaveService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardDownloadReportAction.java
 *
 * @description: 售卡代理角色菜单下的报表下载
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-21
 */
public class SellProxyDownloadReportAction extends BaseAction {
	
	@Autowired
	private ReportPathSaveDAO reportPathSaveDAO;
	@Autowired
	private ReportPathSaveService reportPathSaveService;
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private Paginater page;
	
	private ReportPathSave reportPathSave;
	
	private List<ReportType> reportTypeList;
	
	private List<BranchInfo> cardBranchList;
	private boolean showCardBranch = false;
	private boolean showCardText = false;

	/* 
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
			params.put("reportDate", reportPathSave.getReportDate());
			params.put("merchantNo", reportPathSave.getMerchantNo());
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {// 中心角色登录时，可查看所有售卡代理的报表，故售卡代理号这个参数不传进去
			showCardBranch = false;
			showCardText = true;
		} else if (isFenzhiRoleLogined()) {// 分支机构角色登录时，可查看自己管理的售卡代理的报表
			showCardBranch = true;
			showCardText = false;
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(), this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", sortBranchList(cardBranchList));
			
		} else if (isCardSellRoleLogined()) {// 售卡代理登录时，只能查看自己的报表
			showCardBranch = false;
			showCardText = false;
			params.put("merchantNo", this.getLoginBranchCode());
		} else {// 否则，则没有权限访问
			throw new BizException("没有权限查看售卡代理日统计报表");
		}
		// 售卡代理菜单下看到的是售卡代理的日报表类型
		params.put("reportType", ReportType.PROXY_DAILY.getValue());
		this.page = this.reportPathSaveDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "dayList";
	}
	
	/**
	 * 下载日统计报表
	 * @return
	 * @throws Exception
	 */
	public void dayReportDownload() throws Exception {
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined() && this.branchInfoDAO.isDirectManagedBy(reportPathSave.getMerchantNo(), this.getLoginBranchCode())) {
			
		} else if (isCardSellRoleLogined() && this.getLoginBranchCode().equals(reportPathSave.getMerchantNo())) {
			
		} else {// 否则，则没有权限访问
			throw new BizException("没有权限查看售卡代理日报表");
		}
		Assert.equals(ReportType.PROXY_DAILY.getValue(), reportPathSave.getReportType(), "报表类型错误");

		reportPathSaveService.downloadReportFile(reportPathSave.getReportType(), reportPathSave.getMerchantNo(), reportPathSave.getReportDate());

		String msg = String.format("用户[%s]下载报表文件[%s][%s][%s]成功！", this.getSessionUserCode(),
				reportPathSave.getReportType(), reportPathSave.getMerchantNo(),
				reportPathSave.getReportDate());
		this.log(msg, UserLogType.OTHER);
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public ReportPathSave getReportPathSave() {
		return reportPathSave;
	}

	public void setReportPathSave(ReportPathSave reportPathSave) {
		this.reportPathSave = reportPathSave;
	}

	public List<ReportType> getReportTypeList() {
		return reportTypeList;
	}

	public void setReportTypeList(List<ReportType> reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isShowCardText() {
		return showCardText;
	}

	public void setShowCardText(boolean showCardText) {
		this.showCardText = showCardText;
	}

}
