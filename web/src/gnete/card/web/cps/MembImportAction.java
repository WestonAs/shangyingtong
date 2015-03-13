package gnete.card.web.cps;

import flink.etc.MatchMode;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MembImportRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MembImportReg;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CpsService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: MembImportAction.java
 *
 * @description: 会员注册文件导入处理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-29
 */
public class MembImportAction extends BaseAction {

	@Autowired
	private MembImportRegDAO membImportRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CpsService cpsService;

	private MembImportReg membImportReg;
	
	// 封装上传文件域的属性
	private File upload;

	// 封装上传文件名的属性
	private String uploadFileName;
	
	private Paginater page;
	
	private List<BranchInfo> cardBranchList;
	private List<ExternalCardImportState> statusList;

	@Override
	public String execute() throws Exception {
		this.statusList = ExternalCardImportState.getImportRegStates();
		this.cardBranchList = this.getMyCardBranch();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (membImportReg != null) {
			params.put("id", membImportReg.getId());
			params.put("cardBranch", membImportReg.getCardBranch());
			params.put("fileName", MatchMode.ANYWHERE.toMatchString(membImportReg.getFileName()));
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(membImportReg.getBranchName()));
			params.put("status", membImportReg.getStatus());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			params.put("cardBranch", super.getSessionUser().getBranchNo());
		} 
		// 其他
		else {
			throw new BizException("没有权限查看会员注册文件导入记录！");
		}
		
		this.page = this.membImportRegDAO.findMembImportReg(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	/**
	 * 明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		this.membImportReg = (MembImportReg) membImportRegDAO.findByPk(this.membImportReg.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (!RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("非发卡机构不能做会员注册文件导入操作");
		}
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.membImportReg = new MembImportReg();
		this.membImportReg.setBranchName(branchInfo.getBranchName());
		return ADD;
	}

	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		
		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		fileTypes.add("txt");
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, fileTypes), "会员注册文件导入文件的格式只能是文本文件");
		membImportReg.setFileName(uploadFileName);
		
		this.cpsService.addMembImportReg(upload, membImportReg, this.getSessionUser());
		
		String msg = LogUtils.r("新增会员注册文件导入登记[{0}]成功", membImportReg.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cps/membImport/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 当前登录的机构号
	 * @return
	 */
	public String getCurrentBranch() {
		return super.getLoginBranchCode();
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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

	public List<ExternalCardImportState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<ExternalCardImportState> statusList) {
		this.statusList = statusList;
	}

	public MembImportReg getMembImportReg() {
		return membImportReg;
	}

	public void setMembImportReg(MembImportReg membImportReg) {
		this.membImportReg = membImportReg;
	}

}
