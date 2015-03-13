package gnete.card.web.report;

import flink.util.CommonHelper;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.clear2Pay.IClear2PayBankFileProcess;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.RmaService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 网银通划付文件下载:文件由配置的定时任务生成
 * (区别于RmaFileDownloadAction,RmaFileDownloadAction的文件由后台C++生成)
 * 
 * @Project: CardWeb
 * @File: CardDownloadRmaFileAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-3-12上午11:28:37
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class CardDownloadRmaFileAction extends BaseAction {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private RmaService rmaService;
	@Autowired
	@Qualifier("clear2PayBankFileProcess")
	private IClear2PayBankFileProcess clear2PayBankFileProcess;
	
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
//			params.put("fileType", reportPathSave.getReportType());
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
		
		this.page = this.rmaService.getBranchRmaFileList(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	/**
	 * 从FTP服务器上下载划付文件
	 * @return
	 * @throws Exception
	 */
	public String rmaFileDownload() throws Exception {
		String path = request.getParameter("path");
		boolean flag = this.rmaService.downloadBranchRmaFile(path);
		
		if (flag) {
			String name = path.substring(path.lastIndexOf("/") + 1);
			String msg = "用户[" + this.getSessionUserCode() + "]下载划付文件[" + name + "]成功！";
			this.log(msg, UserLogType.OTHER);
		}
		
		return null;
	}
	
	/**
	 * 合并选中的指定日期(范围)划付文件,并进行上传
	 * @return
	 * @throws Exception
	 */
	public String rmaFileCompondAndUpload() throws Exception {
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		else {// 否则，则没有权限访问
			throw new BizException("没有权限合并划付文件！");
		}
		
		String pathStr = request.getParameter("paths");
		List<String> pathList ;
		if(CommonHelper.isEmpty(pathStr)){
			throw new BizException("划付文件路径不能为空！");
		}else{
			pathList = Arrays.asList(pathStr.split("\\,"));
			Collections.sort(pathList);//自然排序
		}
		//文件名检查 : branchCode_yyyyMMdd_bankNo.txt
		String tmpBankNo = null;
		String tmpDate = null;
		String fileName = null;
		String[] fileNameItems;
		for(String path : pathList){
			fileName  = FilenameUtils.getBaseName(path);
			fileNameItems = fileName.split("_");
			//1 不同联行号的文件不能合并
			if(CommonHelper.isEmpty(tmpBankNo)){
				tmpBankNo = fileNameItems[2];
			}else{
				if(!tmpBankNo.equals(fileNameItems[2])){
					throw new BizException("不同联行号的划付文件("+fileName+")不能合并！");
				}
			}
			
			//2 日期不连续的不能合并
			if(CommonHelper.isEmpty(tmpDate)){
				tmpDate = fileNameItems[1];
			}else{
				if(DateUtil.getDateDiffDays(tmpDate, fileNameItems[1], CommonHelper.DATE_PATTERN[0])!=1){
					throw new BizException("日期不连续的划付文件("+fileName+")不能合并！");
				}else{
					tmpDate = fileNameItems[1];
				}
			}
			
			//3 合并文件不能再合并  branchCode_yyyyMMdd_yyyyMMdd_bankNo.txt
			if(fileNameItems.length > 3){
				throw new BizException("已合并过的划付文件("+fileName+")不能再合并！");
			}
		}
		String comondFileName = clear2PayBankFileProcess.processRmaFileCompondAndUpload(pathList);
		
		if (CommonHelper.isNotEmpty(comondFileName)) {
			String msg = "用户[" + this.getSessionUserCode() + "]合并划付文件[" + FilenameUtils.getName(comondFileName) + "]成功！";
			this.log(msg, UserLogType.OTHER);
			addActionMessage("/pages/report/card/rma/list.do", msg);
			
			return SUCCESS;
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
