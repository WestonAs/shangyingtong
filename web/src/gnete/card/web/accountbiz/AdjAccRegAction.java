package gnete.card.web.accountbiz;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AdjAccRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TransDAO;
import gnete.card.entity.AdjAccReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.Trans;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.AdjAccFlag;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.AccRegService;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 调账退货Action
 * 
 * @author aps-lih
 * @history 2010-9-25
 */
public class AdjAccRegAction extends BaseAction {
	
	@Autowired
	private AdjAccRegDAO adjAccRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private AccRegService accRegService;
	@Autowired
	private UserCertificateRevService userCertifcateReService;
	
	@Autowired
	private TransDAO transDAO;
	
	private AdjAccReg adjAccReg;
	private Trans trans;
	private Paginater page;
	
	private List registerStateList;
	private List flagList;
	List<BranchInfo> cardBranchList;

	private String startDate;
	private String endDate;
	
	private String[] transSns;
	private boolean showAdjAccRegBat = false;
	
	private File upload;
	private String uploadFileName;
	
	private ArrayList<BranchInfo> cardIssuerList;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.registerStateList = RegisterState.getForCheck();
		this.flagList = AdjAccFlag.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if (adjAccReg != null) {
			params.put("cardId", adjAccReg.getCardId());
			params.put("acctId", adjAccReg.getAcctId());
			params.put("merNo", adjAccReg.getMerNo());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(adjAccReg.getMerchName()));
			params.put("termlId", adjAccReg.getTermlId());
			params.put("status", adjAccReg.getStatus());
			params.put("flag", adjAccReg.getFlag());
		}
		
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardOrCardDeptRoleLogined()) {
			// cardBranchList.add((BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			params.put("cardBranch", this.getLoginBranchCode());
		} else if (isMerchantRoleLogined()) {
			params.put("merNo", getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
//		if (CollectionUtils.isNotEmpty(cardBranchList)) {
//			params.put("cardIssuers", cardBranchList);
//		}
		
		this.page = this.adjAccRegDAO.findAdjAccReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception{
		Assert.notNull(this.adjAccReg, "卡调账对象不能为空");	
		Assert.notNull(this.adjAccReg.getAdjAccId(), "调账申请ID不能为空");	
		
		this.adjAccReg = (AdjAccReg)this.adjAccRegDAO.findByPk(this.adjAccReg.getAdjAccId());		
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		return baseShowAdd(false);
	}
	
	public String showAddBat() throws Exception {
		return baseShowAdd(true);
	}
	
	// 打开上传文件页面的初始化操作
	public String showFileAdjAccReg() throws Exception {
		this.checkEffectiveCertUser();
		buildSignRandom();
		
		if (!(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined())) {
			throw new BizException("非发卡机构、机构网点或者商户,不允许进行操作。");
		}
		return "addFileAdjAccReg";
	}
	
	public String findAdj() throws Exception {
		return baseFindAdj(false);
	}
	
	public String findAdjBat() throws Exception {
		return baseFindAdj(true);
	}
	
	// 新增
	public String add() throws Exception {
		
		this.checkUserSignatureSerialNo();
		if(SysparamCache.getInstance().isNeedSign()){
			// 验证签名
			boolean flag = userCertifcateReService.processUserSigVerify(this.getFormMapValue("serialNo"),
					this.getFormMapValue("signature"), this.getFormMapValue("randomSessionId"));
			Assert.isTrue(flag, "验证签名失败");
		}
		
		this.adjAccReg = this.accRegService.addAdjAccReg(adjAccReg, this.getSessionUser());
		
		String msg = LogUtils.r("调账申请[{0}]成功", this.adjAccReg.getAdjAccId());
		this.addActionMessage("/adjAccReg/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 批量新增
	public String addBat() throws Exception {
		this.checkUserSignatureSerialNo();
		if(SysparamCache.getInstance().isNeedSign()){
			// 验证签名
			boolean flag = userCertifcateReService.processUserSigVerify(this.getFormMapValue("serialNo"),
					this.getFormMapValue("signature"), this.getFormMapValue("randomSessionId"));
			Assert.isTrue(flag, "验证签名失败");
		}
		
		this.accRegService.addAdjAccRegBat(transSns, this.getSessionUser());
		
		String msg = LogUtils.r("批量调账申请成功");
		this.addActionMessage("/adjAccReg/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 文件方式批量退货操作
	public String addFileAdjAccReg() throws Exception {
		this.checkUserSignatureSerialNo();
		if(SysparamCache.getInstance().isNeedSign()){
			// 验证签名
			boolean flag = userCertifcateReService.processUserSigVerify(this.getFormMapValue("serialNo"),
					this.getFormMapValue("signature"), this.getFormMapValue("randomSessionId"));
			Assert.isTrue(flag, "验证签名失败");
		}
		
		UserInfo user = this.getSessionUser();
		try{
			List<AdjAccReg> unInsertList =  this.accRegService.addFileAdjAccReg(upload, this.getUploadFileName(), this.getSessionUserCode(), user);
			String msg = "";
			if(unInsertList.size()==0){
				msg = "文件批量退货全部成功。";
			}
			else{
				for(AdjAccReg adjAccReg : unInsertList){
					msg = msg + "交易流水" + "[" + adjAccReg.getTransSn() + "]。";
				}
				msg = msg + "以上退货记录有误，不能退货。";
			}
			this.addActionMessage("/adjAccReg/list.do", msg);
			this.log(msg, UserLogType.ADD);
		}
		catch (Exception e) {
			throw e;
		}
		
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] adjAccRegIds = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_ADJ, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(adjAccRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", adjAccRegIds);
		params.put("cardBranch", this.getLoginBranchCode());
		this.page = this.adjAccRegDAO.findAdjAccReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		Assert.notNull(adjAccReg, "卡调账对象不能为空");	
		Assert.notNull(adjAccReg.getAdjAccId(), "卡调账登记ID不能为空");	
		
		// 卡补账登记簿明细
		this.adjAccReg = (AdjAccReg)this.adjAccRegDAO.findByPk(this.adjAccReg.getAdjAccId());		
		
		return DETAIL;
	}

	private String baseShowAdd(boolean isBat) throws BizException{
		this.checkEffectiveCertUser();
		
		buildSignRandom();
		
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined()
				|| (!isBat && isCenterOrCenterDeptRoleLogined()), "非发卡机构、机构网点或者商户,不允许进行操作。");
		
		this.buildcardIssuerList();
		String workDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		this.startDate = workDate;
		this.endDate = workDate;
		this.showAdjAccRegBat = isBat;
		return isBat ? "addBat" : ADD ;
	}
	
	private String baseFindAdj(boolean isBat) throws BizException{
		this.checkEffectiveCertUser();
		buildSignRandom();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (this.trans != null) {
			params.put("transSn", trans.getTransSn());
			params.put("merNo", trans.getMerNo());
			params.put("cardId", trans.getCardId());
			params.put("settStartDate", this.startDate);
			params.put("settEndDate", this.endDate);
			//params.put("workDate", ParaMgr.getInstance().getWorkDate());
		}
		
		if (isCardOrCardDeptRoleLogined()){
			params.put("cardIssuer", trans.getCardIssuer());
		} else if (isMerchantRoleLogined()){
			params.put("merNo", this.getSessionUser().getMerchantNo());
		}
		
		// 正常消费、部分消费、次卡消费、过期消费、提现消费
		List<String> types = Arrays.asList(TransType.NORMAL_CONSUME.getValue(),
				TransType.PART_CONSUME.getValue(), TransType.TIME_CARD_CONSUME.getValue(),
				TransType.EXPIRE_CONSUME.getValue());
		
		if(isCenterRoleLogined()){
			types = Arrays.asList(TransType.WITH_DRAW.getValue()); // 重新赋值
		}
		
		params.put("types", types);
		if(isBat){
			params.put("procStatus", ProcState.DEDSUCCESS.getValue());
		}else{
			params.put("canMultiAdj", "true");
		}
		this.page = this.transDAO.findTrans(params, this.getPageNumber(), isBat ? 10 : 5 );
		this.buildcardIssuerList();
		return isBat ? "addBat" : ADD;
	}
	
	/**
	 * 构建发卡机构列表（登录机构所属的顶级发卡机构下的所有机构，包括顶级机构）
	 */
	private void buildcardIssuerList() {
		this.cardIssuerList = new ArrayList<BranchInfo>(); // 发卡机构列表
		if (isCardOrCardDeptRoleLogined()){
			BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			this.cardIssuerList.addAll(this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
		}		
	}
	
	
	private void buildSignRandom(){
		if (SysparamCache.getInstance().isNeedSign()) {
			formMap.put("needSignatureReg", Boolean.toString(SysparamCache.getInstance().isNeedSign()));
			// 随机数
			formMap.put("randomSessionId", (Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request)));
		}
	}
	
	
	// =================== getters and setters following ===================
	
	public AdjAccReg getAdjAccReg() {
		return adjAccReg;
	}

	public void setAdjAccReg(AdjAccReg adjAccReg) {
		this.adjAccReg = adjAccReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getRegisterStateList() {
		return registerStateList;
	}

	public void setRegisterStateList(List registerStateList) {
		this.registerStateList = registerStateList;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
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

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String[] getTransSns() {
		return transSns;
	}

	public void setTransSns(String[] transSns) {
		this.transSns = transSns;
	}

	public boolean isShowAdjAccRegBat() {
		return showAdjAccRegBat;
	}

	public void setShowAdjAccRegBat(boolean showAdjAccRegBat) {
		this.showAdjAccRegBat = showAdjAccRegBat;
	}

	public List getFlagList() {
		return flagList;
	}

	public void setFlagList(List flagList) {
		this.flagList = flagList;
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

	public ArrayList<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(ArrayList<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}
}
