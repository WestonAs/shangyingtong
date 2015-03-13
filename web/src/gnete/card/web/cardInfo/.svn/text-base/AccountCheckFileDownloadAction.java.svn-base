package gnete.card.web.cardInfo;

import flink.util.Paginater;
import gnete.card.entity.AccountCheckFile;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.GenerateReportService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: AccountCheckFileDownloadAction.java
 *
 * @description: 对账文件下载处理Action,针对潮州移动的
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-4-19
 */
public class AccountCheckFileDownloadAction extends BaseAction {
	
	@Autowired
	private GenerateReportService generateReportService;
	
	private AccountCheckFile accountCheckFile;
	private Paginater page;
	
	private List<BranchInfo> cardBranchList;
	private String cardBranchName; //机构号
	
	@Override
	public String execute() throws Exception {
//		cardBranchList = this.getMyCardBranch();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isCardRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else if (isFenzhiRoleLogined()) {

		} else {
			throw new BizException("没有权限查看对账文件下载列表");
		}
		if (accountCheckFile != null) {
			params.put("checkDate", accountCheckFile.getCheckDate());
			params.put("branchCode", accountCheckFile.getBranchCode());

			this.page = this.generateReportService.getAccountCheckFileList(params, this.getPageNumber(), this.getPageSize());
		} else {
			this.page = Paginater.EMPTY;
		}
		return LIST;
	}

	/**
	 * 从FTP服务器上下载对账文件
	 * @return
	 * @throws Exception
	 */
	public void download() throws Exception {
		String branchCode = this.getFormMapValue("branchCode");
		String checkDate = this.getFormMapValue("checkDate");
		String fileName = this.getFormMapValue("fileName");
		String folderPath = SysparamCache.getInstance().getAccountCheckFileSavePath();
		// 路径为：对账文件保存根路径/发卡机构编号/对账日期/对账文件名.txt
		String path = String.format("%s/%s/%s/%s", folderPath, branchCode, checkDate, fileName);
		
		boolean flag = this.generateReportService.downloadAccountCheckFile(path);
		
		if (flag) {
			String name = path.substring(path.lastIndexOf("/") + 1);
			String msg = "用户[" + this.getSessionUserCode() + "]下载对账文件[" + name + "]成功！";
			this.log(msg, UserLogType.OTHER);
		}
	}
	
	public AccountCheckFile getAccountCheckFile() {
		return accountCheckFile;
	}

	public void setAccountCheckFile(AccountCheckFile accountCheckFile) {
		this.accountCheckFile = accountCheckFile;
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

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

}
