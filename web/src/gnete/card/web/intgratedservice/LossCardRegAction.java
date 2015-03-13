package gnete.card.web.intgratedservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.LossCardRegDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.LossCardReg;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LossCardService;
import gnete.card.tag.NameTag;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
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
 * 卡挂失
 * @author aps-lib
 *
 */
public class LossCardRegAction extends BaseAction {

	@Autowired
	private LossCardRegDAO lossCardRegDAO;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private LossCardService losscardService;
	
	private String cardId;
	private Long lossBatchId;
	private List statusList;
	private Collection certTypeList;
	private LossCardReg lossCardReg;
	private Paginater page;
	private String custName;

	@Override
	public String execute() throws Exception {
		this.statusList = CardState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (lossCardReg != null){
			params.put("lossBatchId", lossCardReg.getLossBatchId());
			params.put("cardId", lossCardReg.getCardId());
			params.put("custName", MatchMode.ANYWHERE.toMatchString(lossCardReg.getCustName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (isCenterOrCenterDeptRoleLogined()) {
		}
		// 运营分支机构
		else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", super.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门或售卡代理时
		else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardBranchList", this.getMyCardBranch());
		}
		// 售卡代理
		else if (isCardSellRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		params.put("isBatch", false);
		this.page = this.lossCardRegDAO.findLossCard(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.lossCardReg = (LossCardReg) this.lossCardRegDAO.findByPk(this.lossCardReg.getLossBatchId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()){
			throw new BizException("非发卡机构、机构网点、售卡代理，不允许进行操作。");
		}
		initPage();
		return ADD;
	}
	
	// 新增磁卡挂失信息
	public String add() throws Exception {		
		this.checkEffectiveCertUser();
		
		/*if(!validateCardExtraInfo(lossCardReg.getCardId(), lossCardReg.getCertType(), lossCardReg.getCertNo())){
			throw new BizException("卡号与持卡人姓名、证件类型和证件号码不一致,请输入持卡人正确信息。");
		}*/
		
		if (isCardDeptRoleLogined()) {
			lossCardReg.setBranchCode(this.getSessionUser().getDeptId());
			lossCardReg.setBranchName(NameTag.getDeptName(this.getSessionUser().getDeptId()));
		} else {
			lossCardReg.setBranchCode(this.getSessionUser().getBranchNo());
			lossCardReg.setBranchName(NameTag.getBranchName(this.getSessionUser().getBranchNo()));
		}
		
		//保存数据
		this.losscardService.addLossCard(lossCardReg, this.getSessionUser());
		
		String msg = "新增磁卡[" + this.lossCardReg.getCardId() + "]挂失信息成功！";
		this.addActionMessage("/intgratedService/losscard/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	
	private void initPage() {
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();
	}

	 
	// 根据操作机构是否有权限操作该卡
	public void cardIdCheck() throws Exception {
		JSONObject object = new JSONObject();
		CardInfo cardInfo = null;
		try {
			String cardId = this.lossCardReg.getCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在,请重新输入。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡挂失");
			
			// 判断输入卡号的卡状态是否有效
			Assert.notEquals(cardInfo.getCardStatus(), CardState.LOSSREGISTE.getValue(), "该卡[" + cardInfo.getCardId() + "]已挂失。请不要重复操作。");
			Assert.isTrue(CardState.PRESELLED.getValue().equals(cardInfo.getCardStatus())
					|| CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus()), 
					"该卡状态为["+ cardInfo.getCardStatusName() + "]。只有预售出或者正常状态的卡才能挂失。");
			
			// 卡状态
			object.put("cardStatus", cardInfo.getCardStatusName());
			
			if (CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus())) {
				
				// 卡账户
//				AcctInfo acctInfo = this.acctInfoDAO.findAcctInfoByCardId(cardId);
				AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
				Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在，不能挂失");
				
				// 充值资金账户
				SubAcctBalKey deposit = new SubAcctBalKey();
				
				deposit.setAcctId(cardInfo.getAcctId());
				deposit.setSubacctType(SubacctType.DEPOSIT.getValue()); 
				
				SubAcctBal depositBal = (SubAcctBal) this.subAcctBalDAO.findByPk(deposit);
				Assert.notNull(depositBal, "卡号[" + cardId + "]的充值资金子账户不存在。");
				object.put("depositAmt", depositBal.getAvlbBal());
				
				// 返利资金账户
				SubAcctBalKey rebate = new SubAcctBalKey();
				
				rebate.setAcctId(cardInfo.getAcctId());
				rebate.setSubacctType(SubacctType.REBATE.getValue()); 
				SubAcctBal rebateBal = (SubAcctBal) this.subAcctBalDAO.findByPk(rebate);
				
				Assert.notNull(rebateBal, "卡号[" + cardId + "]的返利资金子账户不存在。");
				object.put("rebateAmt", rebateBal.getAvlbBal());
//				object.put("banlanceAmt", AmountUtil.add(depositBal.getAvlbBal(), rebateBal.getAvlbBal()));
				
				if (CardType.ACCU.getValue().equals(cardInfo.getCardClass())) {
					object.put("isAccu", true);
				} else {
					object.put("isAccu", false);
				}
			}
			
			CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.lossCardReg.getCardId());
			if (cardExtraInfo != null) {
				object.put("custName_hidden", getStr(cardExtraInfo.getCustName()));
				object.put("certType_hidden", getStr(cardExtraInfo.getCredType()));
				object.put("certNo_hidden", getStr(cardExtraInfo.getCredNo()));
			}
			
			object.put("success", true);
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		this.respond(object.toString());
	}
	
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 发卡机构可以审核自己提交的和自己发的卡的记录
		if (isCardRoleLogined()) {
			params.put("cardBranchCode", this.getSessionUser().getBranchNo());
		}
		// 发卡机构部门可审核自己部门的
		else if (isCardDeptRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getDeptId());
		}
		// 售卡代理可以审核自己提交的
		else if (isCardSellRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getDeptId());
		} else {
			throw new BizException("非发卡机构、机构网点、售卡代理，不允许进行挂失审核操作。");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_LOSS_CARD, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		params.put("ids", ids);
		
		this.page = this.lossCardRegDAO.findLossCard(params, this.getPageNumber(), this.getPageSize());

		return CHECK_LIST;
	}
	
	private String getStr(String str){
		return StringUtils.isNotEmpty(str) ? str : "";
	}

	public String getCardId() {
		return cardId;
	}


	public void setCardId(String lossCardId) {
		this.cardId = lossCardId;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}


	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}


	public LossCardReg getLossCardReg() {
		return lossCardReg;
	}


	public void setLossCardReg(LossCardReg lossCardReg) {
		this.lossCardReg = lossCardReg;
	}


	public Paginater getPage() {
		return page;
	}


	public void setPage(Paginater page) {
		this.page = page;
	}


	public List getStatusList() {
		return statusList;
	}


	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public Long getLossBatchId() {
		return lossBatchId;
	}

	public void setLossBatchId(Long lossBatchId) {
		this.lossBatchId = lossBatchId;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

}
