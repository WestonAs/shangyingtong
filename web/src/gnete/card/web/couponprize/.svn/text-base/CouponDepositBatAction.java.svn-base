package gnete.card.web.couponprize;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.DepositCouponBatRegDAO;
import gnete.card.dao.DepositCouponRegDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DepositCouponBatReg;
import gnete.card.entity.DepositCouponReg;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CouponBusService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 批量赠券赠送处理
 * @Project: CardWeb
 * @File: CouponDepositBatAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-3-25上午9:35:57
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class CouponDepositBatAction extends BaseAction {
	
	@Autowired
	private DepositCouponRegDAO depositCouponRegDAO;
	@Autowired
	private DepositCouponBatRegDAO depositCouponBatRegDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CouponBusService couponBusService;
	
	private DepositCouponReg depositCouponReg;
	private DepositCouponBatReg depositCouponBatReg;
	private List<RegisterState> statusList;

	private Paginater page;
	private Paginater batPage;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
    private List<BranchInfo> cardBranchList;
    /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
	/** 结束日期 */
    private String endDate;
    
    /** 上传的文件 */
    private File upload;
	/** 上传的文件名 */
	private String uploadFileName;
	
	/** 默认的列表页面 */
	private static final String DEFAULT_LIST_URL = "/pages/couponDepositBat/list.do";
	
	/** 返回列表页面所需要带的参数 */
	private static final String SEARCH_PARAMS = "?goBack=goBack";
	
	@Override
	public String execute() throws Exception {
		
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositCouponReg != null) {
			params.put("depositBatchId", depositCouponReg.getDepositBatchId());
//			params.put("depositCardId", MatchMode.ANYWHERE.toMatchString(depositCouponReg.getCardId()));			
			params.put("strCardId", MatchMode.ANYWHERE.toMatchString(depositCouponReg.getCardId()));			
			params.put("listCouponClass", MatchMode.ANYWHERE.toMatchString(depositCouponReg.getCouponClass()));			
			params.put("status", depositCouponReg.getStatus());
			params.put("cardBranch", depositCouponReg.getCardBranch()); //发卡机构
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			
		}
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showCardBranch = true;
		}
		// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 登录用户为售卡代理或发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", super.getSessionUser().getBranchNo());
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setBranchCode(this.getSessionUser().getMerchantNo());
			cardBranchList = new ArrayList<BranchInfo>();
			cardBranchList.add(branchInfo);
			params.put("cardBranchList", cardBranchList);
		} 
		else {
			throw new BizException("没有权限查看赠送记录");
		}
		
		params.put("isBatch", true); // 批量
		
		this.page = this.depositCouponRegDAO.findDepositCouponRegPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询批量赠券赠送列表！");
		return LIST;
	}
	
	/**
	 * 批量赠券赠送明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depositBatchId", depositCouponReg.getDepositBatchId());
		this.depositCouponReg = this.depositCouponRegDAO.findDepositCouponCheckList(params).get(0);
		
		this.batPage = this.depositCouponBatRegDAO.findDepositCouponBatPage(params, this.getPageNumber(), this.getPageSize());
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询赠券赠送批次[" + depositCouponReg.getDepositBatchId() + "]的批量赠券赠送明细！");
		return DETAIL;
	}
	
	/**
	 * 新增页面初始化
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		initAddPage();
		
		return ADD;
	}
	
	private void initAddPage() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
		}
		else {
			throw new BizException("没有权限做赠券赠送操作！");
		}

		depositCouponReg = new DepositCouponReg();
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
    		// 随机数
    		this.depositCouponReg.setRandomSessionid(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositCouponReg.setRandomSessionid("");
		}
	}
	
	/**
	 * 根据卡号，赠券类型号和赠送赠券，得到赠券折算金额
	 * @throws Exception
	 */
	public void calRealAmt() {
		JSONObject object = new JSONObject();

		String couponClass = request.getParameter("couponClass");
		String point = request.getParameter("point");
		String cardCount = request.getParameter("cardCount");
		
		try {
			Assert.isTrue(NumberUtils.isNumber(point), "单笔赠送赠券数必须是数值");
			BigDecimal depositCoupon = NumberUtils.createBigDecimal(point);
			Assert.isTrue(AmountUtil.gt(depositCoupon, BigDecimal.ZERO), "单笔赠送赠券数必须大于0");
			
			Assert.isTrue(NumberUtils.isDigits(cardCount), "卡连续张数必须为正整数");
			BigDecimal count = NumberUtils.createBigDecimal(cardCount);
			
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(couponClass);
			Assert.notNull(pointClassDef, "赠券类型[" + couponClass + "]不存在");
			
			BigDecimal refAmt = AmountUtil.multiply(depositCoupon, pointClassDef.getPtDiscntRate());
			
			BigDecimal pointSum = AmountUtil.multiply(depositCoupon, count);
			BigDecimal refAmtSum = AmountUtil.multiply(refAmt, count);
			
			// 赠券折算金额
			object.put("refAmt", refAmt);
			object.put("depositCouponSum", pointSum);
			object.put("refAmtSum", refAmtSum);
			
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 根据页面传入的赠券类型和赠送赠券，得到赠券折算金额
	 */
	public void calcCardOther() {
		JSONObject object = new JSONObject();
		
		String couponClass = request.getParameter("couponClass");
		String point = request.getParameter("point");
		
		try {
			Assert.isTrue(NumberUtils.isNumber(point), "赠送赠券数必须是数值");
			BigDecimal depositCoupon = NumberUtils.createBigDecimal(point);
			Assert.isTrue(AmountUtil.gt(depositCoupon, BigDecimal.ZERO), "赠送赠券数必须大于0");
			
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(couponClass);
			Assert.notNull(pointClassDef, "赠券类型[" + couponClass + "]不存在");
			
			BigDecimal refAmt = AmountUtil.multiply(depositCoupon, pointClassDef.getPtDiscntRate());
			
			// 赠券折算金额
			object.put("refAmt", refAmt);
			
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 批量赠券赠送新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		
		String serialNo = request.getParameter("serialNo");
		String cardCount = request.getParameter("cardCount");
		
		this.couponBusService.addDepositCouponBatReg(depositCouponReg, depositCouponBatReg, cardCount, this.getSessionUser(), serialNo);
		
		String msg = LogUtils.r("新增批次号为[{0}]的批量赠送登记成功", depositCouponReg.getDepositBatchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_URL + SEARCH_PARAMS, msg);
		
		return SUCCESS;
	}
	
	/**
	 * 文件方式批量新增赠券赠送页面初始化
	 * @return
	 * @throws Exception
	 */
	public String showAddFile() throws Exception {
		initAddPage();
		
		BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(loginBranch.getBranchCode());
			
			this.cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
		}
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.cardBranchList = new ArrayList<BranchInfo>();
			this.cardBranchList.add(loginBranch);
		}
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			List<MerchInfo> merchs = this.getMyMerch();
			this.cardBranchList = new ArrayList<BranchInfo>();
			for(MerchInfo merchInfo : merchs){
				BranchInfo branchInfo = new BranchInfo();
				branchInfo.setBranchCode(merchInfo.getMerchId());
				branchInfo.setBranchName(merchInfo.getMerchName());
				this.cardBranchList.add(branchInfo);
			}
		}
		
		return "addFile";
	}
	
	/**
	 * 文件方式批量新增赠券赠送
	 * @return
	 * @throws Exception
	 */
	public String addFile() throws Exception {
		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		fileTypes.add("txt");
		fileTypes.add("csv");
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, fileTypes), "赠送文件的格式只能是文本文件");
		
		String name = "赠送的文件名为：" + uploadFileName;
		if (name.length() >= 200) {
			depositCouponReg.setRemark(StringUtils.substring(name, 0, 190));
		} else {
			depositCouponReg.setRemark(name);
		}
		String serialNo = request.getParameter("serialNo");
		String cardCount = request.getParameter("cardCount");
		
		this.couponBusService.addDepositCouponBatRegFile(depositCouponReg, upload, cardCount, this.getSessionUser(), serialNo, "point_deposit_bat_add");
		
		String msg = LogUtils.r("添加赠送批次号为[{0}]的文件方式批量赠券赠送成功", depositCouponReg.getDepositBatchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_URL, msg);
		
		return SUCCESS;
	}
	
	/**
	 *  赠券赠送审核列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("depositBranch", this.getSessionUser().getDeptId());
		} 
		// 发卡机构或售卡代理审核时
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
			params.put("depositBranch", this.getSessionUser().getBranchNo());
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			params.put("depositBranch",  this.getSessionUser().getMerchantNo());
		} 
		else {
			throw new BizException("没有权限做赠券赠送审核");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.WORKFLOW_DEPOSIT_COUPON, this.getSessionUser());
		params.put("ids", ids);
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		this.page = this.depositCouponRegDAO.findDepositCouponCheckPage(params, this.getPageNumber(), this.getPageSize());
		
		return CHECK_LIST;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public DepositCouponReg getDepositCouponReg() {
		return depositCouponReg;
	}

	public void setDepositCouponReg(DepositCouponReg depositCouponReg) {
		this.depositCouponReg = depositCouponReg;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
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

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
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

	public Paginater getBatPage() {
		return batPage;
	}

	public void setBatPage(Paginater batPage) {
		this.batPage = batPage;
	}

	public DepositCouponBatReg getDepositCouponBatReg() {
		return depositCouponBatReg;
	}

	public void setDepositCouponBatReg(DepositCouponBatReg depositCouponBatReg) {
		this.depositCouponBatReg = depositCouponBatReg;
	}
}
