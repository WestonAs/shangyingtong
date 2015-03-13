package gnete.card.web.makecard;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.DateUtil;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardExampleDefDAO;
import gnete.card.dao.PlanPrivilegeDAO;
import gnete.card.entity.CardExampleDef;
import gnete.card.entity.PlanDef;
import gnete.card.entity.PlanPrivilege;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.InsServiceType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductStyleAction.java
 *
 * @description: 单机产品卡样定义
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public class SingleProductStyleAction extends BaseAction {

	@Autowired
	private SingleProductService singleProductService;
	@Autowired
	private CardExampleDefDAO cardExampleDefDAO;
	@Autowired
	private PlanPrivilegeDAO planPrivilegeDAO;

	private List<CommonState> statusList;
	private List<PlanDef> planList; // 有效的套餐列表

	private CardExampleDef cardExampleDef;
	private String planId;
	private String cardStyleId; //卡样编号
	private String planIdNames;
	
	private String startDate;
	private String endDate;

	// 界面选择时是否单选
	private boolean radio;
	private String hiddenBranchCode;
	private String hiddenCardBranch; // 发卡机构编号
	private String planNo; // 选择器里的套餐编号
	
	private Paginater page;
	
	// 封装上传文件域的属性
	private File upload;
	// 封装上传文件名的属性
	private String uploadFileName;

	private static final int MAX_SIZE = 50 * 1024 * 1024;
	
	private static final String DEFAULT_URL = "/pages/singleProduct/style/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = CommonState.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardExampleDef != null) {
			params.put("cardExampleId", cardExampleDef.getCardExampleId());
			params.put("cardExampleName", MatchMode.ANYWHERE.toMatchString(cardExampleDef.getCardExampleName()));
			params.put("status", cardExampleDef.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} 
		else {
			throw new BizException("没有权限查看");
		}
		this.page = this.singleProductService.findStylePage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	/**
	 * 明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		cardExampleDef = (CardExampleDef) this.cardExampleDefDAO.findByPk(cardExampleDef.getCardExampleId());
		Assert.notNull(cardExampleDef, "卡样[" + cardExampleDef.getCardExampleId() + "]不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardExampleId", cardExampleDef.getCardExampleId());
		
		List<PlanDef> defList = this.singleProductService.findPlanList(params);
		planList = new ArrayList<PlanDef>();
		for (PlanDef planDef : defList) {
			params.clear();
			params.put("planId", planDef.getPlanId());
			params.put("status", CommonState.NORMAL.getValue());
			List<PlanPrivilege> list = this.planPrivilegeDAO.findList(params);
			
			StringBuffer sb = new StringBuffer(128);
			for (PlanPrivilege planPrivilege : list) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				String serviceName = (InsServiceType.ALL.get(planPrivilege.getServiceId()) == null ? "" 
						: InsServiceType.valueOf(planPrivilege.getServiceId()).getName());
				sb.append(serviceName);
			}
			planDef.setAuthority(sb.toString());
			
			planList.add(planDef);
		}
		
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			throw new BizException("没有权限操作！");
		}
		
		//this.planList = this.singleProductService.findPlanList(this.getLoginBranchCode());
		return ADD;
	}
	
	/**
	 * 新增卡样定义
	 */
	public String add() throws Exception {
		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		fileTypes.add("zip");
		fileTypes.add("rar");
		if (!IOUtil.testFileFix(uploadFileName, fileTypes)) {
			throw new BizException("上传文件的类型只能是压缩文件，请重新选择！");
		}
		if (upload.length() > MAX_SIZE) {
			throw new BizException("上传的文件大小不能超过50M，请重新选择！");
		}
		String extention = StringUtils.lowerCase(StringUtils.substringAfterLast(uploadFileName, "."));
		uploadFileName = DateUtil.getCurrentTimeMillis() + "." + extention;
		
		this.singleProductService.addStyle(upload, uploadFileName, cardExampleDef, planId, getSessionUser());
		
		String msg = LogUtils.r("新增卡样定义[{0}]成功。", cardExampleDef.getCardExampleId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_URL, msg);
		
		return SUCCESS;
	}

	/**
	 * 卡样选择器
	 * @return
	 * @throws Exception
	 */
	public String showSelect() throws Exception {
		return "select";
	}
	
	/**
	 * 卡样选择器
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardExampleDef != null) {
			params.put("cardExampleId", cardExampleDef.getCardExampleId());
			params.put("cardExampleName", MatchMode.ANYWHERE.toMatchString(cardExampleDef.getCardExampleName()));
			params.put("status", CommonState.NORMAL.getValue());
			params.put("branchCode", hiddenBranchCode);
			params.put("hiddenCardBranch", hiddenCardBranch);
			params.put("planNo", planNo);
		}
		this.page = this.singleProductService.findStyleSelectPage(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		
		return "data";
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardExampleDef getCardExampleDef() {
		return cardExampleDef;
	}

	public void setCardExampleDef(CardExampleDef cardExampleDef) {
		this.cardExampleDef = cardExampleDef;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
	}

	public List<PlanDef> getPlanList() {
		return planList;
	}

	public void setPlanList(List<PlanDef> planList) {
		this.planList = planList;
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

	public String getCardStyleId() {
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId) {
		this.cardStyleId = cardStyleId;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getHiddenBranchCode() {
		return hiddenBranchCode;
	}

	public void setHiddenBranchCode(String hiddenBranchCode) {
		this.hiddenBranchCode = hiddenBranchCode;
	}

	public String getPlanIdNames() {
		return planIdNames;
	}

	public void setPlanIdNames(String planIdNames) {
		this.planIdNames = planIdNames;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getHiddenCardBranch() {
		return hiddenCardBranch;
	}

	public void setHiddenCardBranch(String hiddenCardBranch) {
		this.hiddenCardBranch = hiddenCardBranch;
	}
}
