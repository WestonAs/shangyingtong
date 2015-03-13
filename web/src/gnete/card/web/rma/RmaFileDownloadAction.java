package gnete.card.web.rma;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.RmaService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: RmaFileDownloadAction.java
 *
 * @description: 发卡机构角色下的划付文件下载
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-31
 */
public class RmaFileDownloadAction extends BaseAction {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private RmaService rmaService;
	
	private Paginater page;
	
	private ReportPathSave reportPathSave;
	
	private List<ReportType> reportTypeList;
	
	private List<BranchInfo> cardBranchList;
	
	private String branchName;

	/**
	 * 发卡机构的划付文件文件列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (reportPathSave != null) {
			params.put("fileDate", reportPathSave.getReportDate());
			params.put("branchCode", reportPathSave.getMerchantNo());
			params.put("fileType", reportPathSave.getReportType());
		}
		// 中心角色登录时，可查看所有发卡机构的划付文件
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		} 
		// 分支机构角色登录时，可查看自己管理的发卡机构的划付文件
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			cardBranchList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), this.getLoginBranchCode());
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
		}
		// 发卡机构登录时，只能查看自己的划付文件
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getLoginBranchCode());
		}
		// 否则，则没有权限访问
		else {
			throw new BizException("没有权限查看划付文件！");
		}
		
		this.page = this.rmaService.getRmaFileList(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	/**
	 * 从FTP服务器上下载划付文件
	 * @return
	 * @throws Exception
	 */
	public String rmaFileDownload() throws Exception {
		String merchantNo = this.getFormMapValue("merchantNo");
		String reportDate = this.getFormMapValue("reportDate");
		String reportFileName = this.getFormMapValue("reportFileName");
		String folderPath = SysparamCache.getInstance().getRmaFilePath(); 
		//ftp文件路径，如：/home/carddev/jfk/ftpDir/payfile/10056020/20121207/10056020_20121207.txt
		String path = String.format("%s%s/%s/%s", folderPath, merchantNo, reportDate, reportFileName);
		
		boolean flag = this.rmaService.downloadRmaFile(path);
		
		if (flag) {
			String name = path.substring(path.lastIndexOf("/") + 1);
			String msg = "用户[" + this.getSessionUserCode() + "]下载划付文件[" + name + "]成功！";
			this.log(msg, UserLogType.OTHER);
		}
		
		return null;
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

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public RmaService getRmaService() {
		return rmaService;
	}

	public void setRmaService(RmaService rmaService) {
		this.rmaService = rmaService;
	}

}
