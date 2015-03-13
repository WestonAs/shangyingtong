package gnete.card.web.accountbiz;

import flink.etc.MatchMode;
import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.CouponBalDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RetransCardRegDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TerminalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RetransCardReg;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.Terminal;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CouponUseState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RetransCardRegService;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 卡补账
 * @author aps-lib
 *
 */
public class RetransCardRegAction extends BaseAction {
	
	@Autowired
	private RetransCardRegDAO		retransCardRegDAO;
	@Autowired
	private CardInfoDAO				cardInfoDAO;
	@Autowired
	private SubAcctBalDAO			subAcctBalDAO;
	@Autowired
	private MerchInfoDAO			merchInfoDAO;
	@Autowired
	private TerminalDAO				terminalDAO;
	@Autowired
	private CardToMerchDAO			cardToMerchDAO;
	@Autowired
	private AcctInfoDAO				acctInfoDAO;
	// @Autowired
	// private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CouponBalDAO			couponBalDAO;
	@Autowired
	private RetransCardRegService	retransCardRegService;

	private RetransCardReg			retransCardReg;
	private Paginater				page;
	private MerchInfo				merchInfo;
	private List<MerchInfo>			merchInfoList;
	private String[]				terminal;
	private List<Terminal>			terminalList;
	private CardInfo				cardInfo;
	private SubAcctBal				subAcctBal;
	private String					depositAmt;
	private String					couponAmt;
	private List<BranchInfo>		cardBranchList;
	private Collection				platformTypeList;
	private Collection				couponUseStateList;
	// 封装上传文件域的属性
	private File					upload;
	// 封装上传文件名的属性
	private String					uploadFileName;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (retransCardReg != null) {
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(retransCardReg.getCardId()));
			params.put("acctId", retransCardReg.getAcctId());
			params.put("merchId", MatchMode.ANYWHERE.toMatchString(retransCardReg.getMerchId()));			
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(retransCardReg.getMerchName()));
			params.put("termId", retransCardReg.getTermId());
			params.put("status", retransCardReg.getStatus());
		}
		
//		// 当前登录用户所属或所管理的发卡机构列表
//		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			// cardBranchList.addAll(branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			// if(CollectionUtils.isEmpty(cardBranchList)){
			// cardBranchList.add(new BranchInfo());
			// }
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或发卡机构部门
		// cardBranchList.add((BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			params.put("cardBranch", this.getLoginBranchCode());
		} else if (isMerchantRoleLogined()) {
			params.put("merchId", getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
//		if (CollectionUtils.isNotEmpty(cardBranchList)) {
//			params.put("cardIssuers", cardBranchList);
//		}
		this.page = this.retransCardRegDAO.findRetransCardReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception{
		Assert.notNull(this.retransCardReg, "卡补账对象不能为空");	
		Assert.notNull(this.retransCardReg.getRetransCardId(), "购卡客户ID不能为空");	
		
		// 卡补账明细
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("retransCardId", this.retransCardReg.getRetransCardId());
		this.retransCardReg = (RetransCardReg)(this.retransCardRegDAO.findRetransCardReg(params)).get(0);		
		
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterRoleLogined() || isCardOrCardDeptRoleLogined() || isMerchantRoleLogined())) {
			throw new BizException("非营运中心、发卡机构、机构网点或者商户,不允许进行操作。");
		}
		this.couponUseStateList = CouponUseState.ALL.values();
		
		return ADD;
	}

	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		//插入卡补账记录及登记簿记录
		RetransCardReg retransCardReg = this.retransCardRegService.addRetransCardReg(this.retransCardReg, this.getSessionUser());
		
		this.addActionMessage("/retransCardReg/list.do", "添加卡补账，卡号[" + retransCardReg.getCardId() 
				+ "]，补账金额[" + retransCardReg.getAmt() + "]的申请已经提交成功！");
		this.log("卡补账，卡号[" + retransCardReg.getCardId() + "]，补账金额[" 
				+ retransCardReg.getAmt() + "]的申请已经提交", UserLogType.ADD);
		
		return SUCCESS;
	}	
	
	public String showAddBatFile() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("非发卡机构、机构网点,不允许进行操作。");
		}
		return "addBatFile";
	}
	
	public String addBatFile() throws Exception {
		this.checkEffectiveCertUser();
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("非发卡机构、机构网点,不允许进行操作。");
		}
		Assert.isTrue(IOUtil.testFileFix(uploadFileName, Arrays.asList("txt", "csv")), "文件的格式只能是文本文件");
		
		int detailCnt = Integer.parseInt(this.getFormMapValue("detailCnt"));
		BigDecimal totalAmt = new BigDecimal(getFormMapValue("totalAmt"));
		String remark = this.getFormMapValue("remark");
		
		this.retransCardRegService.addRetransRegBatFile(upload, detailCnt, totalAmt, remark, this.getSessionUser());

		String msgPattern = "文件方式批量新增卡补账成功，共[%s]条记录，金额[%s]元";
		String msg = String.format(msgPattern, detailCnt, totalAmt);
		String retUrl = "/retransCardReg/list.do?goBack=goBack";
		this.addActionMessage(retUrl, msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterRoleLogined() || isCardOrCardDeptRoleLogined() || isMerchantRoleLogined())) {
			throw new BizException("非营运中心、发卡机构、机构网点或者商户,不允许进行操作。");
		}
		this.retransCardReg = (RetransCardReg)this.retransCardRegDAO.findByPk(this.retransCardReg.getRetransCardId());
		Assert.notNull(this.retransCardReg, "修改卡补账对象不能为空");
		
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.retransCardRegService.modifyRetransCardReg(this.retransCardReg, this.getSessionUserCode());
		this.addActionMessage("/retransCardReg/list.do", "修改购卡客户成功！");
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		//this.retransCardRegService.deleteRetransCardReg(this.retransCardReg.getCardCustomerId(), this.retransCardReg.getBinNo());
		this.addActionMessage("/retransCardReg/list.do", "删除卡补账成功！");
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] retransCardRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_RETRANS_CARD_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(retransCardRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", retransCardRegIds);
		params.put("cardBranch", this.getLoginBranchCode());
		
		this.page = this.retransCardRegDAO.findRetransCardReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(retransCardReg, "卡补账对象不能为空");	
		Assert.notNull(retransCardReg.getRetransCardId(), "卡补账登记ID不能为空");	
		
		// 卡补账登记簿明细
		this.retransCardReg = (RetransCardReg)this.retransCardRegDAO.findByPk(retransCardReg.getRetransCardId());		
		
		return DETAIL;	
	}
	
	/** 根据卡号和查询登录用户返回商户列表，服务端查询，返回到前端 */
	public void getMerchList() throws BizException{
		
		String cardId = this.retransCardReg.getCardId();
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			return;
		}
		
		UserInfo user = this.getSessionUser();
		if(RoleType.CENTER.getValue().equals(user.getRole().getRoleType())
				|| RoleType.CARD.getValue().equals(user.getRole().getRoleType())){
			String cardIssuer = cardInfo.getCardIssuer();
			this.merchInfoList = this.merchInfoDAO.findFranchMerchList(cardIssuer);
		}
		else if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){
			this.merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(user.getMerchantNo()));
		}
		
		if (CollectionUtils.isEmpty(merchInfoList)) {
			return;
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (MerchInfo merchInfo : merchInfoList){
			sb.append("<option value=\"").append(merchInfo.getMerchId())
						.append("\">").append(merchInfo.getMerchName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	// 根据商户返回终端列表，服务端查询，返回到前端
	public void getTermList() throws BizException{
		
		if("".equals(this.retransCardReg.getMerchId())){
			return ;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("merchId", this.retransCardReg.getMerchId());
		
		List<Terminal> list = this.terminalDAO.find(params);
		
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (Terminal terminal : list){
			sb.append("<option value=\"").append(terminal.getTermId())
						.append("\">").append(terminal.getTermId()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	// 服务端查询终端号列表，返回给前端
	public void queryTerminalList(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params = new HashMap<String, Object>();
		params.put("merchId", this.retransCardReg.getMerchId());
		this.terminalList = this.terminalDAO.find(params);
		int count = this.terminalList.size();
		if(count > 0){
			this.terminal = new String[count];
			for(int i = 0; i < count; i++){
				this.terminal[i] = this.terminalList.get(i).getTermId();
			}
		}else{
			this.terminal = new String[1];
			this.terminal[0] = "";
		}		
	}
	
	// 服务端查询充值子账户余额、赠券子账户余额
	public void querySubAcctBal(){
		
		CardInfo cardInfo = null;
		AcctInfo acctInfo = null;
		String resultAcctId = "";
		String resultDepositAmt = "";   // 充值
		String resultCouponAmt = "";    // 赠券
		String resultRebateAmt = "";    // 返利
		//String resultAccuAmt = "";    // 次卡
		String resultOverDraftAmt = ""; // 签单卡透支额度
		boolean couponFlag = false;
		//boolean accuFlag = false;
		boolean signFlag = false;
		
		UserInfo user = this.getSessionUser();
		try{
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.retransCardReg.getCardId());
			Assert.notNull(cardInfo, "不存在该卡号。");
			
			if(RoleType.CENTER.getValue().equals(user.getRole().getRoleType())){
				
			}else if(RoleType.CARD.getValue().equals(user.getRole().getRoleType())){
				boolean flag = BranchUtil.isBelong2SameTopBranch(user.getBranchNo(), cardInfo.getCardIssuer());
				Assert.isTrue(flag, "登录机构 与 该卡的发行机构 不属于同一顶级机构，不能做卡补账处理。");
			}else if(RoleType.MERCHANT.getValue().equals(user.getRole().getRoleType())){
				CardToMerchKey key = new CardToMerchKey();
				key.setBranchCode(cardInfo.getCardIssuer());
				key.setMerchId(user.getMerchantNo());
				Assert.notNull((CardToMerch) this.cardToMerchDAO.findByPk(key), "该商户不是该卡发卡机构的特约商户。");
			}else{
				throw new BizException("非营运中心、发卡机构或者商户不能补账。");
			}
			
			acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "该卡不存在账户，无法补账。");
			
			SubAcctBal subAcctBalCoupon = null;
			SubAcctBal subAcctBalDeposit = null;
			SubAcctBal subAcctBalRebate = null;
			//SubAcctBal subAcctBalAccu = null;
			
			resultAcctId = acctInfo.getAcctId();
			SubAcctBalKey key = new SubAcctBalKey();
			key.setAcctId(resultAcctId);
			
			String cardClass = cardInfo.getCardClass();
			// 赠券卡，查找子账户中的赠券子账户
			if (CardType.COUPON.getValue().equals(cardClass)){
				key.setSubacctType(SubacctType.COUPON.getValue());
				subAcctBalCoupon = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
				if(subAcctBalCoupon!=null){
					resultCouponAmt = subAcctBalCoupon.getAvlbBal().toString();
					resultCouponAmt = (resultCouponAmt == null)? "0" : resultCouponAmt;
				}
				couponFlag = true;
			} 
			// 次卡
			/*else if (CardType.ACCU.getValue().equals(cardClass)){
				key.setSubacctType(SubacctType.ACCU.getValue());
				subAcctBalAccu = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
				if(subAcctBalAccu!=null){
					resultAccuAmt = subAcctBalAccu.getAvlbBal().toString();
					resultAccuAmt = (resultAccuAmt == null)? "" : resultAccuAmt;
				}
				accuFlag = true;
			}*/
			// 其他卡, 查询充值子账户、返利子账户(子账户余额表)、赠券子账户余额表(赠券帐户余额表)
			else{
				// 签单卡
//				if(CardType.SIGN.getValue().equals(cardClass)){
//					resultOverDraftAmt = acctInfo.getSigningOverdraftLimit().toString();
//					signFlag = true;
//				}
				// 充值子账户
				key.setSubacctType(SubacctType.DEPOSIT.getValue());
				subAcctBalDeposit = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
				if(subAcctBalDeposit!=null){
					resultDepositAmt= subAcctBalDeposit.getAvlbBal().toString();
					resultDepositAmt = (resultDepositAmt == null)? "0" : resultDepositAmt;
				}
				// 返利子账户
				key.setSubacctType(SubacctType.REBATE.getValue());
				subAcctBalRebate = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
				if(subAcctBalRebate!=null){
					resultRebateAmt = subAcctBalRebate.getAvlbBal().toString();
					resultRebateAmt = (resultRebateAmt == null)? "0" : resultRebateAmt;
				}
				// 赠券子账户余额表
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("acctId", resultAcctId);
				resultCouponAmt = (this.couponBalDAO.getBalTotal(params)!=null) ? this.couponBalDAO.getBalTotal(params).toString() : "0" ;
			}
			//Assert.isTrue(subAcctBalDep!=null||subAcctBalCou!=null, "充值子账户和赠券子账户均不存在。");
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true + ", 'couponFlag':"+ couponFlag + ", 'signFlag':" + signFlag 
				+ ", 'resultAcctId':'" + resultAcctId + "', 'resultDepositAmt':'" + resultDepositAmt  
				+ "', 'resultCouponAmt':'" + resultCouponAmt + "', 'resultRebateAmt':'" + resultRebateAmt +
				"', 'resultOverDraftAmt':'" + resultOverDraftAmt + "', 'cardIssuer':'"+cardInfo.getCardIssuer()+"'}" );
		
	}
	
	/*
	 * ===================  getters and setters following  ===================
	 */
	
	public RetransCardReg getRetransCardReg() {
		return retransCardReg;
	}

	public void setRetransCardReg(RetransCardReg retransCardReg) {
		this.retransCardReg = retransCardReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getPlatformTypeList() {
		return platformTypeList;
	}

	public void setPlatformTypeList(Collection platformTypeList) {
		this.platformTypeList = platformTypeList;
	}

	public MerchInfo getMerchInfo() {
		return merchInfo;
	}

	public void setMerchInfo(MerchInfo merchInfo) {
		this.merchInfo = merchInfo;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public String[] getTerminal() {
		return terminal;
	}

	public void setTerminal(String[] terminal) {
		this.terminal = terminal;
	}

	public List<Terminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<Terminal> terminalList) {
		this.terminalList = terminalList;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public SubAcctBal getSubAcctBal() {
		return subAcctBal;
	}

	public void setSubAcctBal(SubAcctBal subAcctBal) {
		this.subAcctBal = subAcctBal;
	}

	public String getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getCouponAmt() {
		return couponAmt;
	}

	public void setCouponAmt(String couponAmt) {
		this.couponAmt = couponAmt;
	}

	public Collection getCouponUseStateList() {
		return couponUseStateList;
	}

	public void setCouponUseStateList(Collection couponUseStateList) {
		this.couponUseStateList = couponUseStateList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
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
	
}
