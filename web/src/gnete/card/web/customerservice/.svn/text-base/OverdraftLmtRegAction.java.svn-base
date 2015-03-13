package gnete.card.web.customerservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.OverdraftLmtRegDAO;
import gnete.card.dao.SignCustDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.OverdraftLmtReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.OverdraftService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * 账户透支额度调整
 * @author aps-lib
 *
 */
public class OverdraftLmtRegAction extends BaseAction{

	@Autowired
	private OverdraftLmtRegDAO overdraftLmtRegDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private OverdraftService overdraftService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private OverdraftLmtReg overdraftLmtReg;
	private SignCust signCust;
	private Collection statusList; 
	private Paginater page;
	private Long overdraftLmtId;
	private List<BranchInfo> cardBranchList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				cardBranchList.add(new BranchInfo());
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		this.statusList = RegisterState.getForCheck();	
		
		if(overdraftLmtReg != null){
			params.put("cardId", overdraftLmtReg.getCardId());
			params.put("signCustId", overdraftLmtReg.getSignCustId());
		}
		this.page = this.overdraftLmtRegDAO.findOverdraftLmtReg(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得账户透支额度申请登记的明细
	public String detail() throws Exception {
		
		this.overdraftLmtReg = (OverdraftLmtReg) this.overdraftLmtRegDAO.findByPk(this.overdraftLmtReg.getOverdraftLmtId());
		this.log("查询账户透支额度申请["+this.overdraftLmtReg.getOverdraftLmtId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.dealIsNeedSign();
		
		this.operatePrivilege();
		initPage();
		return ADD;
	}	
	
	// 新增账户额度申请登记
	public String add() throws Exception {			
		this.checkUserSignatureSerialNo();
		
		String cardId = this.overdraftLmtReg.getCardId();
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
		AcctInfo acctInfo = (AcctInfo)this.acctInfoDAO.findByPk(cardInfo.getAcctId());
		Assert.notNull(acctInfo, "账户不存在,卡号尚未绑定到账号。");
		this.overdraftLmtReg.setAcctId(acctInfo.getAcctId());
		String signCustId = acctInfo.getSigningCustomerId();
		Assert.notNull(signCustId, "账户签单号为空");
		this.overdraftLmtReg.setSignCustId(Long.valueOf(signCustId));
		BigDecimal overdraft = acctInfo.getSigningOverdraftLimit();
		this.overdraftLmtReg.setOverdraft(overdraft);
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		this.overdraftLmtReg.setBranchCode(branch.getBranchCode());
		this.overdraftLmtReg.setBranchName(branch.getBranchName());
		
		this.overdraftService.addOverdraftLmtReg(overdraftLmtReg, this.getSessionUserCode());
		
		//启动单个流程
		this.workflowService.startFlow(overdraftLmtReg, "overdraftAdapter", overdraftLmtReg.getOverdraftLmtId().toString(), this.getSessionUser());
		
		String msg = "账户额度申请["+this.overdraftLmtReg.getOverdraftLmtId()+"]成功！";
		this.addActionMessage("/overdraftLmtReg/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.operatePrivilege();
		initPage();
		this.overdraftLmtReg = (OverdraftLmtReg)this.overdraftLmtRegDAO.findByPk(this.overdraftLmtReg.getOverdraftLmtId());
		return MODIFY;
	}
	
	// 修改账户透支额度调整记录
	public String modify() throws Exception {
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		this.overdraftLmtReg.setBranchCode(branch.getBranchCode());
		this.overdraftLmtReg.setBranchName(branch.getBranchName());
		
		this.overdraftService.modifyOverdraftLmtReg(this.overdraftLmtReg, this.getSessionUserCode());
		this.addActionMessage("/overdraftLmtReg/list.do", "修改账户透支额度调整成功！");	
		return SUCCESS;
	}
	
	// 删除账户透支额度调整记录
	public String delete() throws Exception {
		this.operatePrivilege();
		this.overdraftService.delete(this.getOverdraftLmtId());
		String msg = "删除账户透支额度调整记录[" +this.getOverdraftLmtId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/overdraftLmtReg/list.do", msg);
		return SUCCESS;
	}
	
	// 账户透支额度调整审核列表
	public String checkList() throws Exception{
		
		// 首先调用流程引擎，得到我的待审批的工单ID
		String[] overdraftLmtRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_OVERDRAFT_LMT_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(overdraftLmtRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", overdraftLmtRegIds);
		this.page = this.overdraftLmtRegDAO.findOverdraftLmtReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	//取得账户透支额度调整申请的明细，给流程审核人查看
	public String checkDetail() throws Exception {
		
		this.overdraftLmtReg = (OverdraftLmtReg)this.overdraftLmtRegDAO.findByPk(overdraftLmtReg.getOverdraftLmtId());
		this.signCust = (SignCust)this.signCustDAO.findByPk(this.overdraftLmtReg.getSignCustId());
		this.log("查询账户透支额度调整申请["+this.overdraftLmtReg.getOverdraftLmtId()+"]审核明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 根据卡号找到原签单额度
	public void getOverdraft() throws Exception {
		
		CardInfo cardInfo = null;
		AcctInfo acctInfo = null;
		
		try {
			String cardId = this.overdraftLmtReg.getCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
			String acctId = cardInfo.getAcctId();
			acctInfo = (AcctInfo)this.acctInfoDAO.findByPk(acctId);
			Assert.notNull(acctInfo, "卡号账户不存在,不能操作。");
			
			UserInfo user = this.getSessionUser();
			if(user.getRole().getRoleType().equals(RoleType.CARD_SELL.getValue())){
				boolean flag = this.isCardSellPrivilege(cardInfo, user, Constants.OVER_DRAFT_LMT_PRIVILEGE_CODE);
				Assert.isTrue(flag, "该售卡代理没有权限为该卡调整额度。");
			}
			else if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){
				String cardIssuer = cardInfo.getCardIssuer();
				Assert.equals(cardIssuer, user.getBranchNo(), "该发卡机构不是该卡的发行机构,不能调整额度。");
			}
			else{
				throw new BizException("非发卡机构或者售卡代理不能调整额度。");
			}
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		BigDecimal overdraft = acctInfo.getSigningOverdraftLimit();
		this.respond("{'success':"+ true +", 'overdraft':'" + overdraft.toString() + "'}");
		
	}
	
	private void initPage() {
		
	}

	public OverdraftLmtReg getOverdraftLmtReg() {
		return overdraftLmtReg;
	}

	public void setOverdraftLmtReg(OverdraftLmtReg overdraftLmtReg) {
		this.overdraftLmtReg = overdraftLmtReg;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Long getOverdraftLmtId() {
		return overdraftLmtId;
	}

	public void setOverdraftLmtId(Long overdraftLmtId) {
		this.overdraftLmtId = overdraftLmtId;
	}

	public void setSignCust(SignCust signCust) {
		this.signCust = signCust;
	}

	public SignCust getSignCust() {
		return signCust;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

}
