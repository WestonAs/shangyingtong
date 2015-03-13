package gnete.card.web.transactionData;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.RmaTransTypeLimitDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RmaTransTypeLimit;
import gnete.card.entity.RmaTransTypeLimitKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.TransactionDataService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class RmaTransTypeLimitAction extends BaseAction{

	@Autowired
	private RmaTransTypeLimitDAO rmaTransTypeLimitDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransactionDataService transactionDataService;
	
	private Paginater page;
	private RmaTransTypeLimit rmaTransTypeLimit;
	private List<CardTypeState> stateList;
	private List<DSetTransType> transTypeList;
	private String parent;
	private String insCode;
	private String transType;
	private boolean showFenzhi = false;
	private boolean showCard = false;
	
	private String[] transTypes; // 交易类型
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		initPage();
		
		if (rmaTransTypeLimit != null) {
			params.put("insCode", rmaTransTypeLimit.getInsCode());
			params.put("insName", MatchMode.ANYWHERE.toMatchString(rmaTransTypeLimit.getInsName()));
			params.put("transType", rmaTransTypeLimit.getTransType());
			params.put("status", rmaTransTypeLimit.getStatus());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		} 
		// 分支机构，能查看管理的发卡机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			params.put("parent", this.getSessionUser().getBranchNo());
		} 
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())){
			params.put("insCode", this.getSessionUser().getBranchNo());
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.rmaTransTypeLimitDAO.findRmaTransTypeLimit(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		initPage();
		hasRightToDo();
		
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.transactionDataService.addRmaTransTypeLimit(rmaTransTypeLimit, this.transTypes, this.getSessionUserCode());
		StringBuffer sb = new StringBuffer(128);
		
		for (String id : this.transTypes) {
			sb.append(DSetTransType.valueOf(id).getName()).append(",");
		}
		
		String transTypeName = sb.toString();
		transTypeName = transTypeName.substring(0, transTypeName.lastIndexOf(","));
		
		String msg = "新增发卡机构[" + this.rmaTransTypeLimit.getInsCode() + "]交易类型为[" + transTypeName + "]的划付交易限制定义成功！";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/rmaTransTypeLimit/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		hasRightToDo();
		
		RmaTransTypeLimitKey key = new RmaTransTypeLimitKey();
		key.setInsCode(this.getInsCode());
		key.setTransType(this.getTransType());
		this.rmaTransTypeLimit = (RmaTransTypeLimit) this.rmaTransTypeLimitDAO.findByPk(key);
		
		this.transactionDataService.modifyRmaTransTypeLimit(rmaTransTypeLimit, this.getSessionUserCode());
		
		String msg = "修改发卡机构[" + this.rmaTransTypeLimit.getInsCode() + "]交易类型为[" + 
				DSetTransType.valueOf(rmaTransTypeLimit.getTransType()).getName() + "]的划付交易限制定义成功！";
		
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/rmaTransTypeLimit/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		hasRightToDo();
		RmaTransTypeLimitKey key = new RmaTransTypeLimitKey();
		key.setInsCode(this.getInsCode());
		key.setTransType(this.getTransType());
		
		this.transactionDataService.deleteRmaTransTypeLimit(key);
		
		String msg = "删除发卡机构[" + this.getInsCode() + "]交易类型为[" + 
		DSetTransType.valueOf(this.getTransType()).getName() + "]的划付交易限制定义成功！";
		
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/rmaTransTypeLimit/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		this.stateList = CardTypeState.getList();
		this.transTypeList = DSetTransType.getList();
	}
	
	private void hasRightToDo() throws BizException{
		// 分支机构
		if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.showFenzhi = true;
			this.showCard = false;
			this.parent = this.getSessionUser().getBranchNo();
		}
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCard = true;
			this.showFenzhi = false;
			this.rmaTransTypeLimit = new RmaTransTypeLimit();
			this.rmaTransTypeLimit.setInsCode(this.getSessionUser().getBranchNo());
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
			Assert.notNull(branchInfo, "登录机构相关信息不存在！");
			this.rmaTransTypeLimit.setInsName(branchInfo.getBranchName());
		} else{
			throw new BizException("非分支机构、发卡机构及发卡机构部门不能操作。");
		}
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public RmaTransTypeLimit getRmaTransTypeLimit() {
		return rmaTransTypeLimit;
	}

	public void setRmaTransTypeLimit(RmaTransTypeLimit rmaTransTypeLimit) {
		this.rmaTransTypeLimit = rmaTransTypeLimit;
	}

	public List<CardTypeState> getStateList() {
		return stateList;
	}

	public void setStateList(List<CardTypeState> stateList) {
		this.stateList = stateList;
	}

	public List<DSetTransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<DSetTransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean isShowFenzhi() {
		return showFenzhi;
	}

	public void setShowFenzhi(boolean showFenzhi) {
		this.showFenzhi = showFenzhi;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public String[] getTransTypes() {
		return transTypes;
	}

	public void setTransTypes(String[] transTypes) {
		this.transTypes = transTypes;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

}
