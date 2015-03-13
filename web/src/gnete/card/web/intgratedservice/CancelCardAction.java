package gnete.card.web.intgratedservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import flink.util.StringUtil;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CancelCardRegDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CancelCardReg;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CancelCardFlagType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CancelCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 退卡销户
 */
public class CancelCardAction extends BaseAction{

	@Autowired
	private CancelCardRegDAO cancelCardRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CancelCardService cancelCardService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	
	private CancelCardReg cancelCardReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private boolean showCardCell = false;
	private List<CancelCardFlagType> flagList;
	private Long cancelCardId;
	private Collection certTypeList;
	private String password;
	
	private File upload;
	private String uploadFileName;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.flagList = CancelCardFlagType.getAll();
		this.certTypeList = CertType.getAll();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (cancelCardReg != null) {
			params.put("cancelCardId", cancelCardReg.getCancelCardId());
			params.put("cardId", cancelCardReg.getCardId());
			params.put("acctId", cancelCardReg.getAcctId());
			params.put("flag", cancelCardReg.getFlag());
			params.put("custName", MatchMode.ANYWHERE.toMatchString(cancelCardReg.getCustName()));
		}
		
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) { // 运营分支机构
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(this.getSessionUser().getBranchNo()));
			params.put("cardIssuers", cardBranchList);

		} else if (isCardOrCardDeptRoleLogined()) { // 发卡机构或发卡机构部门
			params.put("branchCode", this.getLoginBranchCode());

		} else if (isCardSellRoleLogined()) { // 售卡代理
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
			params.put("flag", CancelCardFlagType.CANCEL_CARD.getValue());
			this.showCardCell = true;
			this.flagList = CancelCardFlagType.getCancelCard();
		} else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.cancelCardRegDAO.findCancelCardReg(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.cancelCardReg = (CancelCardReg) this.cancelCardRegDAO.findByPk(this.cancelCardReg.getCancelCardId());

		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		this.certTypeList = CertType.getAll();
		
		if (isCardOrCardDeptRoleLogined()) {
			this.showCardCell = false;
			this.flagList = CancelCardFlagType.getAll();
		} else if (isCardSellRoleLogined()) {
			this.showCardCell = true;
			this.flagList = CancelCardFlagType.getCancelCard();
		} else {
			throw new BizException("没有权限退卡销户。");
		}
		
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		/*UserInfo user = this.getSessionUser();
		if(RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			this.cancelCardReg.setBranchCode(user.getDeptId());
		}
		this.cancelCardReg.setBranchCode(user.getBranchNo());*/
		
		this.cancelCardService.addCancelCard(cancelCardReg, this.getSessionUser());

		String msg = "退卡销户登记成功！退卡销户ID为[" + this.cancelCardReg.getCancelCardId() + "]";
		this.addActionMessage("/intgratedService/cancelCard/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showFileCancelCard() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.isTrue(isCardOrCardDeptRoleLogined()||isCardSellRoleLogined(), "没有权限退卡销户。");
		
		return ADD;
	}
	
	// 新增对象操作
	public String addFileCancelCard() throws Exception {
		this.checkEffectiveCertUser();
		
		List<String> unInsertList = this.cancelCardService.addFileCancelCardReg(upload, uploadFileName, this.getSessionUser());
		
		String msg = "";
		if (unInsertList.size() == 0) {
			msg = "文件批量添加退卡销户全部成功！";
		} else {
			msg = "以下记录存在错误，不能退卡销户:" + ObjectUtils.nullSafeToString(unInsertList.toArray());
		}
		
		this.addActionMessage("/intgratedService/cancelCard/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	/**
	 * 退卡销户审核列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才有权限做退卡销户审核操作");
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_CANCEL_CARD, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getSessionUser().getBranchNo());
		params.put("ids", ids);
		this.page = this.cancelCardRegDAO.findCancelCardReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	public String checkDetail() throws Exception {
		this.cancelCardReg = (CancelCardReg) this.cancelCardRegDAO.findByPk(this.cancelCardReg.getCancelCardId());

		return DETAIL;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		this.cancelCardService.delete(this.getCancelCardId());
		String msg = "删除退卡销户记录[" + this.getCancelCardId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/intgratedService/cancelCard/list.do", msg);
		return SUCCESS;
	}
	
	// 检查卡号是否存在，返回账号
	public void checkCardId(){
		JSONObject object = new JSONObject();
		
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cancelCardReg.getCardId());
			Assert.notNull(cardInfo, "卡号不存在");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "正常(已激活)的卡才能退卡");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "退卡销户");
			
			Assert.notEmpty(cardInfo.getAcctId(), "账号不能为空");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cancelCardReg.getCardId() + "]的账号不存在");
			
//			// 退卡销户现在只返还 充值资金和返利资金账户里的钱
//			SubAcctBalKey depositkey = new SubAcctBalKey();
//			depositkey.setAcctId(cardInfo.getAcctId());
//			depositkey.setSubacctType(SubacctType.DEPOSIT.getValue());
//			SubAcctBal depositAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(depositkey);
//			Assert.notNull(depositAcctBal, "卡号[" + cancelCardReg.getCardId() + "]的充值资金账户不存在");
//			
//			SubAcctBalKey rebatekey = new SubAcctBalKey();
//			rebatekey.setAcctId(cardInfo.getAcctId());
//			rebatekey.setSubacctType(SubacctType.REBATE.getValue());
//			SubAcctBal rebateAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(depositkey);
//			Assert.notNull(rebateAcctBal, "卡号[" + cancelCardReg.getCardId() + "]的返利资金账户不存在");
			
			object.put("success", true);
//			object.put("returnAmt", depositAcctBal.getAvlbBal());
			object.put("returnAmt", cancelCardService.countAvlbBal(cardInfo));
		} catch (Exception e) {
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	// 检查账号是否存在
	public void checkAcctId(){
		try{
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(this.cancelCardReg.getAcctId());
			Assert.notNull(acctInfo, "账号不存在");
			
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true+ "}");
	}
	
	// 校验查询密码
	public void validatePassword(){
		
		List<CardInfo> cardList = new ArrayList<CardInfo>();
		
		try{
			Assert.notEmpty(this.password, "密码不能为空");
			String cardId = this.cancelCardReg.getCardId();
			String acctId = this.cancelCardReg.getAcctId();
			
			// 根据卡号验证密码
			if(StringUtils.isNotEmpty(cardId)){
				CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);
				Assert.notNull(cardExtraInfo.getPassword(), "持卡人未设查询密码，不能退卡");
				cardList.add((CardInfo)this.cardInfoDAO.findByPk(cardId));
			}
			// 根据账号验证密码
			else {
				cardList = this.cardInfoDAO.getCardListByAcctId(acctId);
			}
			
			Assert.isTrue(checkPwd(cardList, this.password), "密码不正确");
			
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true+ "}");
	}
	
	private boolean checkPwd(List<CardInfo> cardList, String password) throws Exception {
		
		String newMD5 = "";
		
		try {
			newMD5 = StringUtil.getMD5(password);
		} catch (Exception e) {
			throw new BizException("生成查询密码时发生错误！");
		}
		
		for(CardInfo cardInfo : cardList){
			CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardInfo.getCardId());
			if(cardExtraInfo!=null && cardExtraInfo.getPassword()!=null){
				if(newMD5.equals(cardExtraInfo.getPassword())){
					return true;
				}
			}
		}
		return false;
	}
	
	public CancelCardReg getCancelCardReg() {
		return cancelCardReg;
	}

	public void setCancelCardReg(CancelCardReg cancelCardReg) {
		this.cancelCardReg = cancelCardReg;
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

	public boolean isShowCardCell() {
		return showCardCell;
	}

	public void setShowCardCell(boolean showCardCell) {
		this.showCardCell = showCardCell;
	}
	
	public List<CancelCardFlagType> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<CancelCardFlagType> flagList) {
		this.flagList = flagList;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}

	public Long getCancelCardId() {
		return cancelCardId;
	}

	public void setCancelCardId(Long cancelCardId) {
		this.cancelCardId = cancelCardId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
