package gnete.card.web.intgratedservice;

import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RenewType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RenewCardRegService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.WorkflowConstants;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 换卡
 * @author aps-lib
 *
 */
public class RenewCardRegAction extends BaseAction {

	@Autowired
	private RenewCardRegDAO renewCardRegDAO;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private RenewCardRegService renewCardRegService;

	private RenewCardReg renewCardReg;
	private Long renewCardId;
	private String newCardId;
	private String cardId;
	private List statusList;
	private List<RenewType> renewTypeList;
	private Paginater page;
	private String custName;
	private Collection certTypeList;
	private List<BranchInfo> cardBranchList;
	
	@Override
	public String execute() throws Exception {
		this.certTypeList = CertType.getAll();
		this.renewTypeList = RenewType.getLossBrokeCard();
		this.statusList = RegisterState.getForCheck();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (renewCardReg != null) {
			params.put("renewCardId", renewCardReg.getRenewCardId());
			params.put("newCardId", renewCardReg.getNewCardId());
			params.put("cardId", renewCardReg.getCardId());
			params.put("custName", renewCardReg.getCustName());
			params.put("acctId", renewCardReg.getAcctId());
			params.put("renewType", renewCardReg.getRenewType());
			params.put("status", renewCardReg.getStatus());
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardIssuers", this.getMyCardBranch());

		} else if (isCardSellRoleLogined()) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());

		} else {
			throw new BizException("没有权限查询。");
		}
		// 挂失换卡和损坏换卡类型
		params.put("types", renewTypeList);

		this.page = this.renewCardRegDAO.findRenewCard(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(this.renewCardReg.getRenewCardId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()) {
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
		initPage();
		return ADD;
	}

	// 新增信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo user = this.getSessionUser();
		if(isCardSellRoleLogined()){
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.renewCardReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + renewCardReg.getCardId() + "]不存在");
			boolean flag = hasCardSellPrivilege(user.getBranchNo(), cardInfo.getCardIssuer(), Constants.RENEW_CARD_PRIVILEGE_CODE);
			
			Assert.isTrue(flag, "旧卡的发行机构没有为该售卡代理分配换卡权限。");
		}
		
		this.renewCardRegService.addRenewCard(renewCardReg, this.getSessionUser());

		String msg = "换卡登记成功！新卡ID为[" + this.renewCardReg.getNewCardId() + "]，旧卡ID为[" + this.renewCardReg.getCardId() + "]";
		this.addActionMessage("/intgratedService/renewcard/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}

	// 打开修改页面的初始化操作
	public String showModify() throws BizException {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()) {
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
		initPage();

		this.renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(this.renewCardReg.getRenewCardId());

		return MODIFY;
	}

	// 修改
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.renewCardRegService.modifyRenewCard(this.renewCardReg, this.getSessionUserCode());
		String msg = "修改换卡信息成功，换卡ID[" + this.getRenewCardId() + "]！";
		this.addActionMessage("/intgratedService/renewcard/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 审核列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 发卡机构可以审核自己发的卡和自己领的卡的记录
		if (isCardOrCardDeptRoleLogined()) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		}
		// 售卡代理
		else if (isCardSellRoleLogined()) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限做换卡审核");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		TimeInterval t = new TimeInterval();
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_RENEW_CARD, this.getSessionUser());
		logger.info("[{}]查询换卡待审核id数组，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		params.put("ids", ids);
		t.reset();
		this.page = this.renewCardRegDAO.findRenewCard(params, this.getPageNumber(), this.getPageSize());
		logger.info("[{}]查询换卡审核列表，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		return CHECK_LIST;
	}
	
	// 删除挂失信息
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		this.renewCardRegService.delete(this.getRenewCardId());

		String msg = "删除换卡信息成功，换卡ID[" + this.getRenewCardId() + "]！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/intgratedService/renewcard/list.do", msg);
		return SUCCESS;
	}

	private void initPage() {
		this.certTypeList = CertType.getAll();
		// this.renewTypeList = RenewType.getAll();
	}

	/** 校验输入旧卡号的正确性 */
	public void cardIdCheck() throws Exception {
		JSONObject object = new JSONObject();
		try {
			String cardId = this.renewCardReg.getCardId();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在,请重新输入。");

			boolean stateFlag = false;
			if (CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus())
					|| CardState.LOSSREGISTE.getValue().equals(cardInfo.getCardStatus())) {
				stateFlag = true;
			}
			Assert.isTrue(stateFlag, "原卡[" + cardId + "]目前不是挂失或正常状态，不能换卡。");

			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(),  this.getLoginBranchCode() , cardInfo, "换卡");
			
		} catch (Exception e) {
			object.put("success", false);
			object.put("error", e.getMessage());
//			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			this.respond(object.toString());
			return;
		}

		CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.renewCardReg.getCardId());

		if (cardExtraInfo != null) {
			object.put("custName_hidden", getStr(cardExtraInfo.getCustName()));
			object.put("certType_hidden", getStr(cardExtraInfo.getCredType()));
			object.put("certNo_hidden", getStr(cardExtraInfo.getCredNo()));
			
//			this.respond("{'success':" + true + ", 'custName_hidden':'"
//					+ getStr(cardExtraInfo.getCustName())
//					+ "','certType_hidden':'"
//					+ getStr(cardExtraInfo.getCredType())
//					+ "','certNo_hidden':'" + getStr(cardExtraInfo.getCredNo())
//					+ "'}");
//			return;
		}
		object.put("success", true);
		this.respond(object.toString());
//		this.respond("{'success':" + true + "}");
	}

	private String getStr(String str) {
		return StringUtils.isNotEmpty(str) ? str : "";
	}

	// 校验输入新卡号的正确性
	public void newCardIdCheck() throws Exception {
		JSONObject object = new JSONObject();
		try {
			String cardId = this.renewCardReg.getNewCardId();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在，请重新输入。");
			
			CardInfo oldCardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.renewCardReg.getCardId());

			// 判断新旧卡号的卡BIN是否一致
			Assert.equals(cardInfo.getCardBin(), oldCardInfo.getCardBin(), "新旧卡号卡BIN不一致，不能换卡。");

			Assert.equals(cardInfo.getCardClass(), oldCardInfo.getCardClass(),
					"旧卡（" + oldCardInfo.getCardClassName() + "）和新卡（" + cardInfo.getCardClass() + "）卡种类不同。");
			Assert.equals(cardInfo.getCardStatus(), CardState.FORSALE.getValue(), "新卡不是领卡待售状态，请选择请他新卡。");
			
			UserInfo user = this.getSessionUser();
			Assert.notNull(cardInfo.getAppOrgId(), "新卡领卡机构为空，请选择其他新卡。");

			String branchCode = user.getBranchNo();
			if (RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())) {
				branchCode = user.getDeptId();
			}
			Assert.equals(cardInfo.getAppOrgId(), branchCode, "本机构不是新卡的领卡机构，请选择其他新卡。");
			
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("error", e.getMessage());
//			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
//			return;
		}
//		this.respond("{'success':" + true + "}");
		this.respond(object.toString());
	}

	public String getReTypeList() throws Exception {

		String cardId = this.renewCardReg.getCardId();
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		if (CardState.LOSSREGISTE.getValue().equals(cardInfo.getCardStatus())) {
			this.renewTypeList = RenewType.getLossCard();
		} else {
			this.renewTypeList = RenewType.getBrokeCard();
		}
		return "renewTypeList";
	}

	public String getNewCardId() {
		return newCardId;
	}

	public void setNewCardId(String newCardId) {
		this.newCardId = newCardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public RenewCardReg getRenewCardReg() {
		return renewCardReg;
	}

	public void setRenewCardReg(RenewCardReg renewCardReg) {
		this.renewCardReg = renewCardReg;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}

	public Long getRenewCardId() {
		return renewCardId;
	}

	public void setRenewCardId(Long renewCardId) {
		this.renewCardId = renewCardId;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<RenewType> getRenewTypeList() {
		return renewTypeList;
	}

	public void setRenewTypeList(List<RenewType> renewTypeList) {
		this.renewTypeList = renewTypeList;
	}

}
