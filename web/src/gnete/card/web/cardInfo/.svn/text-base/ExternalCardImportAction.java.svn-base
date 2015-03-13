package gnete.card.web.cardInfo;

import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.ExternalCardImportRegDAO;
import gnete.card.dao.ExternalCardImportResultDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.ExternalCardImportReg;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.state.ExternalCardImportState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.ExternalCardImportService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 外部卡导入/外部卡开卡 处理
 */
public class ExternalCardImportAction extends BaseAction {
	@Autowired
	private ExternalCardImportService		externalCardImportService;
	@Autowired
	private ExternalCardImportRegDAO		externalCardImportRegDAO;
	@Autowired
	private ExternalCardImportResultDAO		externalCardImportResultDAO;
	@Autowired
	private CardSubClassDefDAO				cardSubClassDefDAO;
	@Autowired
	private PointClassDefDAO				pointClassDefDao;

	
	private ExternalCardImportReg			externalCardImportReg;

	// 封装上传文件域的属性
	private File							upload;

	// 封装上传文件名的属性
	private String							uploadFileName;

	private Paginater						page;

	private List<BranchInfo>				cardBranchList;
	private List<ExternalCardImportState>	statusList;

	@Override
	public String execute() throws Exception {
		if (externalCardImportReg == null) {
			externalCardImportReg = new ExternalCardImportReg();
			externalCardImportReg.setUptype("00");// 外部卡导入
		}

		if (externalCardImportReg.isExternalNumMakeCard()) {
			this.statusList = ExternalCardImportState.getMakeCardRegStates();
		} else {
			this.statusList = ExternalCardImportState.getImportRegStates();
		}
		this.cardBranchList = this.getMyCardBranch();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("externalCardImportReg", externalCardImportReg);

		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) { // 分支机构
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			params.put("cardBranchList", cardBranchList);

		} else if (isCardRoleLogined()) { // 发卡机构时
			params.put("cardBranchList", cardBranchList);

		} else {
			throw new BizException("没有权限查看外部卡导入记录");
		}
		this.page = this.externalCardImportRegDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String listExternalNumMakeCard() throws Exception {
		if (externalCardImportReg == null) {
			externalCardImportReg = new ExternalCardImportReg();
			externalCardImportReg.setUptype("01");// 外部号码开卡
		}
		return execute();
	}

	/**
	 * 明细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Long impId = externalCardImportReg.getId();
		this.externalCardImportReg = (ExternalCardImportReg) externalCardImportRegDAO.findByPk(impId);
		
		this.page = externalCardImportResultDAO.findPage(impId, this.getPageNumber(), this.getPageSize());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做外部卡导入操作");
		this.checkEffectiveCertUser();

		dealIsNeedSign();

		return ADD;
	}

	/** 显示重新导入页面 */
	public String showReImport() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做外部卡导入操作");
		this.checkEffectiveCertUser();
		
		dealIsNeedSign();

		externalCardImportReg = (ExternalCardImportReg) externalCardImportRegDAO
				.findByPk(externalCardImportReg.getId());
		return "reImport";
	}

	public String add() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做外部卡导入操作");

		checkUserSignatureSerialNo();

		externalCardImportReg.setFileName(uploadFileName);

		this.externalCardImportService.addExternalCardImportReg(upload, externalCardImportReg, this
				.getSessionUser());

		String msgPattern = "新增外部卡导入登记[%s]成功";
		String retUrl = "/pages/externalCardImport/list.do?goBack=goBack";
		if (externalCardImportReg.isExternalNumMakeCard()) {
			msgPattern = "新增外部号码开卡登记[%s]成功";
			retUrl = "/pages/externalCardImport/listExternalNumMakeCard.do?goBack=goBack";
		}
		String msg = String.format(msgPattern, externalCardImportReg.getId());

		this.log(msg, UserLogType.ADD);
		this.addActionMessage(retUrl, msg);
		return SUCCESS;
	}

	/**
	 * 重新导入文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String reImport() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做外部卡导入操作");
		
		ExternalCardImportReg orig = (ExternalCardImportReg)externalCardImportRegDAO.findByPk(externalCardImportReg.getId());
		Assert.notNull(orig, "没有找到对应的登记记录！");
		Assert.equals(this.getLoginBranchCode(), orig.getCardBranch(), "发卡机构不匹配，不能下载原文件");
		
		checkUserSignatureSerialNo();

		externalCardImportReg.setFileName(uploadFileName);
		externalCardImportReg = this.externalCardImportService.reImport(upload, externalCardImportReg, this
				.getSessionUser());

		String msgPattern = "重新导入外部卡导入登记[%s]成功";
		String retUrl = "/pages/externalCardImport/list.do?goBack=goBack";
		if (externalCardImportReg.isExternalNumMakeCard()) {
			msgPattern = "重新导入外部号码开卡登记[%s]成功";
			retUrl = "/pages/externalCardImport/listExternalNumMakeCard.do?goBack=goBack";
		}
		String msg = String.format(msgPattern, externalCardImportReg.getId());
		this.log(msg, UserLogType.ADD);

		this.addActionMessage(retUrl, msg);
		return SUCCESS;
	}

	/**
	 * 外部号码开卡 审核列表页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkListExternalNumMakeCard() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才有权限做外部号码开卡审核操作");

		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_EXTERNAL_NUM_MAKE_CARD, this
				.getSessionUser());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getLoginBranchCode());
		params.put("ids", ids);
		this.page = this.externalCardImportRegDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "checkListExternalNumMakeCard";
	}

	/**
	 * 取得卡子类型列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getCardSubClassList() {
		String binNo = request.getParameter("binNo");
		if (StringUtils.isEmpty(binNo)) {
			return;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("binNo", binNo);
		List<CardSubClassDef> list = cardSubClassDefDAO.findCardSubClass(params);

		StringBuffer sb = new StringBuffer(128);
		for (CardSubClassDef subClass : list) {
			sb.append("<option value=\"").append(subClass.getCardSubclass()).append("\">").append(
					subClass.getCardSubclassName()).append("</option>");
		}
		this.respond(sb.toString());
	}

	/**
	 * 查找卡子类型对应的积分类型
	 * 
	 * @return
	 */
	public void findPointClassByCardSubclass() throws Exception {
		CardSubClassDef cardSubClassDef = (CardSubClassDef) cardSubClassDefDAO.findByPk(externalCardImportReg
				.getCardSubclass());
		PointClassDef pointClassDef = (PointClassDef) pointClassDefDao.findByPk(cardSubClassDef.getPtClass());

		JSONObject json = new JSONObject();
		if (pointClassDef != null) {
			json.put("success", true);
			json.put("ptClass", pointClassDef.getPtClass());
			json.put("className", pointClassDef.getClassName());
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}

	/** 下载源文件 */
	public void downloadOrigFile() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做外部卡导入操作");
		this.checkEffectiveCertUser();
		
		ExternalCardImportReg orig = (ExternalCardImportReg)externalCardImportRegDAO.findByPk(externalCardImportReg.getId());
		Assert.notNull(orig, "没有找到对应的登记记录！");
		Assert.equals(this.getLoginBranchCode(), orig.getCardBranch(), "发卡机构不匹配，不能下载原文件");
		
		
		InputStream in = externalCardImportService.downloadOrigFile(externalCardImportReg.getId());
		IOUtil.downloadFile(in, externalCardImportReg.getFileName());
	}

	// ------------------------------- getter and setter followed------------------------
	
	public ExternalCardImportReg getExternalCardImportReg() {
		return externalCardImportReg;
	}

	public void setExternalCardImportReg(ExternalCardImportReg externalCardImportReg) {
		this.externalCardImportReg = externalCardImportReg;
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

	public List<ExternalCardImportState> getStatusList() {
		return statusList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

}
