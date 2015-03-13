package gnete.card.web.intgratedservice;

import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.SMSFlag;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardExtraInfoService;
import gnete.card.service.mgr.BranchBizConfigCache;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * 持卡人信息录入
 * @author aps-lib
 *
 */
public class CardExtraInfoAction extends BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(CardExtraInfoAction.class);
	
	private static final String	IS_EXPORTING_CARD_EXTRA_INFO	= "IS_EXPORTING_CARD_EXTRA_INFO";
	
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardExtraInfoService cardExtraInfoService;
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	private CardExtraInfo cardExtraInfo;
	private BranchInfo branchInfo;
	private Paginater page;
	private Collection<CertType> certTypeList;
	private Collection<SMSFlag> smsFlagList;
	List<BranchInfo> cardBranchList;
	private String oldPass;
	private String newPass;
	
	private File upload;
	private String uploadFileName;
	
	private boolean showCenter = false;
	private boolean showFenZhi = false;
	private boolean showCard = false;
	private String parent;
	
	private ArrayList<BranchInfo> cardIssuerList;
	
	@Override
	public String execute() throws Exception {
	
		initPage();

		Map<String, Object> params = buildQueryParams();
 		this.page = this.cardExtraInfoDAO.findCardExtraInfo(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception {
		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		checkUserRoleType();
		if (BranchBizConfigCache.isCertUserCardExtroInfoIssuers(this.getLoginBranchCode())) {
			this.checkEffectiveCertUser();
		}
		
		initPage();
		return ADD;
	}
	
	public String add() throws Exception {
		checkUserRoleType();
		if (BranchBizConfigCache.isCertUserCardExtroInfoIssuers(cardExtraInfo.getCardBranch())) {
			this.checkEffectiveCertUser();
		}

		this.cardExtraInfoService.addCardExtraInfo(this.cardExtraInfo, this.getSessionUserCode());
		String msg = "新增卡号["+this.cardExtraInfo.getCardId()+"]持卡人信息成功！";
		this.addActionMessage("/intgratedService/cardExtraInfo/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		checkUserRoleType();
		CardExtraInfo orig =  (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		checkUserOprtPriv(orig);
		
		initPage();
		cardExtraInfo = orig;
		if (!this.getLoginBranchCode().equals(cardExtraInfo.getCardBranch())
				&& !this.getLoginBranchCode().equals(cardExtraInfo.getSaleOrgId())) {
			throw new BizException("非该记录的发卡机构或售卡机构,不允许进行该操作。");
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		checkUserRoleType();
		CardExtraInfo orig =  (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		checkUserOprtPriv(orig);
		
		this.cardExtraInfoService.modifyCardExtraInfo(this.cardExtraInfo, this.getSessionUserCode());
		String msg = "修改卡号["+this.cardExtraInfo.getCardId()+"]持卡人信息成功！";
		this.addActionMessage("/intgratedService/cardExtraInfo/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		checkUserRoleType();
		CardExtraInfo orig =  (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		checkUserOprtPriv(orig);
		
		this.cardExtraInfoService.deleteCardExtraInfo(this.cardExtraInfo.getCardId());
		String msg = "删除卡号[" +this.cardExtraInfo.getCardId()+ "]持卡人信息成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/intgratedService/cardExtraInfo/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage(){
		this.certTypeList = CertType.getAll();
		this.smsFlagList = SMSFlag.ALL.values();
	}
	
	/** 验证输入卡号 */
	public void cardIdCheck() throws Exception {
		CardExtraInfo cardExtraInfo = null;
		JSONObject object = new JSONObject();
		try {
			String cardId = this.cardExtraInfo.getCardId();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			
			// 检查登录机构是否有操作权限
			if (!isCenterRoleLogined()) {
				CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo,
						"持卡人信息录入");
			}
			
			cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);
			Assert.isNull(cardExtraInfo, "卡号[" + cardId + "]的持卡人信息已经存在。可以通过编辑来修改持卡人信息。");
			
			object.put("success", true);
			object.put("cardCustomerId", cardInfo.getCardCustomerId());
			object.put("saleOrgId", cardInfo.getSaleOrgId());
			object.put("cardBranch", cardInfo.getCardIssuer());
		} catch (BizException e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		this.respond(object.toString());
	}
	
	public String showChgPassword() throws Exception {
		checkUserRoleType();
		CardExtraInfo orig =  (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		checkUserOprtPriv(orig);
		
		if (!(isCardOrCardDeptRoleLogined() || isCenterRoleLogined() || isCardSellRoleLogined())) {
			throw new BizException("非营运中心、发卡机构、机构网点或者售卡代理,不允许进行操作。");
		}
		this.cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		return "chgPassword";
	}
	
	/** 修改查询密码 */
	public String chgPassword() throws Exception {
		checkUserRoleType();
		CardExtraInfo orig =  (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		checkUserOprtPriv(orig);
		
		this.cardExtraInfoService.modifyPass(this.cardExtraInfo.getCardId(), oldPass, newPass, this.getSessionUser());

		String msg = "持卡人["+this.cardExtraInfo.getCustName()+"]修改密码成功！";
		this.addActionMessage("/intgratedService/cardExtraInfo/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	/** 打开批量新增页面（文件上传方式）*/
	public String showAddBat() throws Exception {
		checkUserRoleType();
		if (BranchBizConfigCache.isCertUserCardExtroInfoIssuers(this.getLoginBranchCode())) {
			this.checkEffectiveCertUser();
		}
		
		this.showCenter = false;
		this.showCard = false;
		this.showFenZhi = false;
		// 营运中心、营运中心部门
		if (isCenterOrCenterDeptRoleLogined()) {
			this.showCenter = true;
		} else if (isFenzhiRoleLogined()) {
			this.showFenZhi = true;
			this.parent = this.getSessionUser().getBranchNo();
		} else if (isCardOrCardDeptRoleLogined()) {
			this.cardIssuerList = new ArrayList<BranchInfo>(); // 发卡机构列表
			BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			this.cardIssuerList.addAll(this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
			this.showCard = true;
		} else {
			throw new BizException("非营运中心、营运中心部门、分支机构、发卡机构不能操作。");
		}

		return "addBat";
	}
	
	public String addBat() throws Exception {
		checkUserRoleType();
		if (BranchBizConfigCache.isCertUserCardExtroInfoIssuers(branchInfo.getBranchCode())) {
			this.checkEffectiveCertUser();
		}
		
		List<CardExtraInfo> unInsertList = this.cardExtraInfoService.addCardExtraInfoBat(upload, 
				this.getUploadFileName(), this.getSessionUser(), branchInfo.getBranchCode());
		StringBuilder sb = new StringBuilder(128);
		if(unInsertList.size() == 0){
			sb.append("文件批量添加持卡人信息全部成功。");
		} else{
			for(CardExtraInfo cardExtraInfo : unInsertList){
				sb.append("“卡号[").append(cardExtraInfo.getCardId()).append("]，").append(cardExtraInfo.getAddress()).append("”");
			}
			sb.append(System.getProperty("line.separator"));
			sb.append("以上持卡人信息记录有误，不能录入。");
			throw new BizException(sb.toString());
		}
		this.addActionMessage("/intgratedService/cardExtraInfo/list.do", sb.toString());
		this.log(sb.toString(), UserLogType.ADD);
		
		return SUCCESS;
	}
	
	public void ajaxIsExporting() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_EXPORTING_CARD_EXTRA_INFO))) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}
	
	public void export() throws Exception {
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_EXPORTING_CARD_EXTRA_INFO))) {
			return;
		}
		ActionContext.getContext().getSession().put(IS_EXPORTING_CARD_EXTRA_INFO, Boolean.TRUE);
		try {
			Map<String, Object> params = buildQueryParams();

			String msg = String.format("用户%s, 机构%s导出持卡人信息...  [params: %s]", this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), params );
			logger.info(msg);
			log(msg, UserLogType.OTHER);
			TimeInterval ti = new TimeInterval();
			cardExtraInfoService.export(this.response, params);
			String msg2 = String.format("用户%s,机构%s导出持卡人信息完成！用时 %s秒", this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), ti.getIntervalOfSec() );
			logger.info(msg2);
			log(msg2, UserLogType.OTHER);
		} finally {
			IOUtils.closeQuietly(response.getWriter());
			ActionContext.getContext().getSession().remove(IS_EXPORTING_CARD_EXTRA_INFO);
		}
	}

	private Map<String, Object> buildQueryParams() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardExtraInfo", cardExtraInfo);
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardIssuer", this.getLoginBranchCode());
			List<UserInfo> users = userInfoDAO.findByBranch(this.getLoginBranchCode(), null);
			params.put("users", users);

		} else if (isCardSellRoleLogined()) {
			params.put("saleOrgId", this.getSessionUser().getBranchNo());

		} else {
			throw new BizException("没有权限查询。");
		}
		return params;
	}

	/** 检查用户角色、是否需要证书*/
	private void checkUserRoleType() throws BizException{
		if (!(isCenterRoleLogined() || isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())) {
			throw new BizException("非营运中心、发卡机构、机构网点或者售卡代理,不允许进行操作。");
		}
	}
	
	/** 检测用户是否可操作指定记录 */
	private void checkUserOprtPriv(CardExtraInfo cardExtraInfo) throws BizException {
		Assert.notNull(cardExtraInfo, "指定的记录不存在！");
		
		if (BranchBizConfigCache.isCertUserCardExtroInfoIssuers(cardExtraInfo.getCardBranch())) {
			this.checkEffectiveCertUser();
		}
		
		if (isCardOrCardDeptRoleLogined() || isCardSellRoleLogined()) {
			Assert.isTrue(this.getLoginBranchCode().equals(cardExtraInfo.getCardBranch())
					|| this.getLoginBranchCode().equals(cardExtraInfo.getSaleOrgId()), "非待操作记录的发卡机构或售卡机构，不允许该操作！");
		}
	}
	
	// =============================== getter and setter methods followed ========================
	
	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection<CertType> getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection<CertType> certTypeList) {
		this.certTypeList = certTypeList;
	}

	public Collection<SMSFlag> getSmsFlagList() {
		return smsFlagList;
	}

	public void setSmsFlagList(Collection<SMSFlag> smsFlagList) {
		this.smsFlagList = smsFlagList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
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

	public BranchInfo getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(BranchInfo branchInfo) {
		this.branchInfo = branchInfo;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowFenZhi() {
		return showFenZhi;
	}

	public void setShowFenZhi(boolean showFenZhi) {
		this.showFenZhi = showFenZhi;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public ArrayList<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(ArrayList<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}
	
}
