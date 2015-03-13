package gnete.card.web.log;

import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.LogService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: BackLogFileDownloadAction.java
 *
 * @description: 营运中心角色下的后台日志文件下载
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-9-7
 */
public class BackLogFileDownloadAction extends BaseAction {
	
	@Autowired
	private LogService logService;
	
	private Paginater page;
	
	private ReportPathSave reportPathSave;
	
	private List<ReportType> reportTypeList;
	
	private List<BranchInfo> cardBranchList;
	
	private String branchName;

	private int bgType = 1;// 页面传入参数：1表示后台1，2表示后台2 。默认为1

	/**
	 * 后台日志文件列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		if (reportPathSave == null) { // 表示从菜单进入，直接进入；
			return LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileDate", reportPathSave.getReportDate());
		params.put("fileName", reportPathSave.getReportName());
		if (!isCenterOrCenterDeptRoleLogined()) {
			throw new BizException("没有权限查看后台日志文件！");
		}
		this.page = this.logService.getBackLogFileList(params, this.getPageNumber(), this.getPageSize(), bgType);
		return LIST;
	}

	/**
	 * 从FTP服务器上下载后台日志文件
	 * @return
	 * @throws Exception
	 */
	public String backLogFileDownload() throws Exception {
		String path = request.getParameter("path");
		boolean flag = this.logService.downloadBackLogFile(path, bgType);
		
		if (flag) {
			String name = path.substring(path.lastIndexOf("/") + 1);
			String msg = "用户[" + this.getSessionUserCode() + "]下载后台日志文件[" + name + "]成功！";
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

	public int getBgType() {
		return bgType;
	}

	public void setBgType(int bgType) {
		this.bgType = bgType;
	}
}
