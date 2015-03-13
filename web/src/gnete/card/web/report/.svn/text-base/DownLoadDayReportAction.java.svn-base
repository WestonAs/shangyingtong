package gnete.card.web.report;

import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.GenerateReportService;
import gnete.card.service.ReportPathSaveService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: DownLoadDayReportAction.java
 *
 * @description: 下载自动生成的报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-14
 */
public class DownLoadDayReportAction extends BaseAction {

	@Autowired
	private GenerateReportService	generateReportService;
	@Autowired
	private ReportPathSaveService	reportPathSaveService;
	@Autowired
	private BranchInfoDAO			branchInfoDAO;

	private Paginater				page;

	private ReportPathSave			reportPathSave;

	private List<BranchInfo>		cardBranchList;
	
	/* 
	 * 日统计报表列表
	 */
	@Override
	public String execute() throws Exception {
		return null;
	}
	
	
	/**
	 * 余额报表列表
	 * @return
	 * @throws Exception
	 */
	public String balanceReportList() throws Exception {
		cardBranchList = getMyCardBranch();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportDate", super.getFormMapValue("reportDate")); // 统计日期
		params.put("merchantNo", super.getFormMapValue("merchantNo")); //机构、商户号
			
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isCardRoleLogined()) {
			if (super.getFormMapValue("merchantNo")==null){
				super.getFormMap().put("merchantNo", super.getLoginBranchCode());
				params.put("merchantNo", super.getLoginBranchCode());
			}
		} else {
			throw new BizException("没有权限查看余额报表");
		}
		this.page = this.generateReportService.getBalanceReportList(params, 
				this.getPageNumber(), this.getPageSize());
		return "balanceList";
	}
	
	/**
	 * 余额报表下载
	 * @return
	 * @throws Exception
	 */
	public String balanceReportDownload() throws Exception {
		String merchantNo = this.getFormMapValue("merchantNo");
		String reportDate = this.getFormMapValue("reportDate");
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isCardOrCardDeptRoleLogined()
				&& branchInfoDAO.isSuperBranch(this.getLoginBranchCode(), merchantNo)) {

		} else {
			throw new BizException("没有权限查看指定发卡机构的日报表");
		}

		String path = String.format("%s/balanceReport/balanceReport_%s_%s.xlsx", SysparamCache.getInstance()
				.getReportFolderPath(), merchantNo, reportDate);

		Assert.isTrue(IOUtil.isFileExist(path), "要下载的余额报表文件不存在" + path);
		IOUtil.downloadFile(path);
		
		String name = path.substring(path.lastIndexOf("/") + 1);
		String msg = String.format("用户[%s]下载余额报表文件[%s]成功！", this.getSessionUserCode(), name);
		this.log(msg, UserLogType.OTHER);
		return null;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

}
