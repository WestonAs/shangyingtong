package gnete.card.web.accountbiz;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TransAccRegDAO;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.TransAccReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.AccRegService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: TransAccRegAction.java
 *
 * @description: 卡转账
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lih
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-9-25
 */
public class TransAccRegAction extends BaseAction {
	
	@Autowired
	private TransAccRegDAO transAccRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private AccRegService accRegService;
	
	private TransAccReg transAccReg;
	private Paginater page;
	private Paginater cardPage;
	private List<SubAcctBal> subAcctList;
	private List registerStateList;
	private List subacctTypeList;
	private String cardId;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.registerStateList = RegisterState.getForCheck();
		this.subacctTypeList = SubacctType.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if (transAccReg != null) {
			params.put("outCardId", MatchMode.ANYWHERE.toMatchString(transAccReg.getOutCardId()));
			params.put("outAcctId", MatchMode.ANYWHERE.toMatchString(transAccReg.getOutAccId()));
			params.put("inCardId", MatchMode.ANYWHERE.toMatchString(transAccReg.getInCardId()));
			params.put("inAcctId", MatchMode.ANYWHERE.toMatchString(transAccReg.getInAccId()));			
			params.put("subacctType", transAccReg.getSubacctType());
			params.put("status", transAccReg.getStatus());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("cardBranch", this.getLoginBranchCode());
		}
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
		} else{
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.transAccRegDAO.findTransAccReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception{
		Assert.notNull(this.transAccReg, "卡转账对象不能为空");	
		Assert.notNull(this.transAccReg.getTransAccId(), "转帐申请ID不能为空");	
		
		this.transAccReg = (TransAccReg)this.transAccRegDAO.findByPk(this.transAccReg.getTransAccId());		
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if (!(isCardOrCardDeptRoleLogined()||isCardSellRoleLogined())){
			throw new BizException("非发卡机构、售卡代理，不允许进行操作。");
		}
		dealIsNeedSign();
		return ADD;
	}
	
	public String findCard(){
		dealIsNeedSign();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardId", cardId);
		this.cardPage = this.cardInfoDAO.findCardInfo(params, this.getPageNumber(), 5);
		return ADD;
	}
	
	public String findSubAcct(){
		String acctId = request.getParameter("acctId");
		if (StringUtils.isEmpty(acctId)) {
			return "subacct";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acctId", acctId);
		// 只有充值和返利子账户才能转账
		params.put("subacctTypes", new String[]{SubacctType.DEPOSIT.getValue(), SubacctType.REBATE.getValue()});
		this.subAcctList = this.subAcctBalDAO.findSubAcctBal(params);
		return "subacct";
	}

	// 新增
	public String add() throws Exception {
		
		checkUserSignatureSerialNo();
		
		this.transAccReg = this.accRegService.addTransAccReg(transAccReg, this.getSessionUser());
		
		String msg = LogUtils.r("转帐申请[{0}]成功", this.transAccReg.getTransAccId());
		this.addActionMessage("/transAccReg/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] transAccRegIds = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_TRANSFER, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(transAccRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", transAccRegIds);
		this.page = this.transAccRegDAO.findTransAccReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(transAccReg, "卡转账对象不能为空");	
		Assert.notNull(transAccReg.getTransAccId(), "卡转账登记ID不能为空");	
		
		// 卡转账登记簿明细
		this.transAccReg = (TransAccReg)this.transAccRegDAO.findByPk(this.transAccReg.getTransAccId());		
		
		return DETAIL;
	}

	public TransAccReg getTransAccReg() {
		return transAccReg;
	}

	public void setTransAccReg(TransAccReg transAccReg) {
		this.transAccReg = transAccReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getCardPage() {
		return cardPage;
	}

	public void setCardPage(Paginater cardPage) {
		this.cardPage = cardPage;
	}

	public List<SubAcctBal> getSubAcctList() {
		return subAcctList;
	}

	public void setSubAcctList(List<SubAcctBal> subAcctList) {
		this.subAcctList = subAcctList;
	}

	public List getRegisterStateList() {
		return registerStateList;
	}

	public void setRegisterStateList(List registerStateList) {
		this.registerStateList = registerStateList;
	}

	public List getSubacctTypeList() {
		return subacctTypeList;
	}

	public void setSubacctTypeList(List subacctTypeList) {
		this.subacctTypeList = subacctTypeList;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
