package gnete.card.web.accountbiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TransAccRuleDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.TransAccRuleDef;
import gnete.card.entity.TransAccRuleDefKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.AccRegService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 转账规则定义
 * @author aps-lib
 *
 */
public class TransAccRuleDefAction extends BaseAction{

	@Autowired
	private TransAccRuleDefDAO transAccRuleDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private AccRegService accRegService;
	
	private TransAccRuleDef transAccRuleDef;
	private Paginater page;
	private List<CardTypeState> statusList;
	private List<BranchInfo> cardBranchList;
	private String branchCode;
	private String cardBin1;
	private String cardBin2;
	private boolean showCenter = false;
	private boolean showCard = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.statusList = CardTypeState.getList();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if ( transAccRuleDef != null) {
			params.put("branchCode",  transAccRuleDef.getBranchCode());
			params.put("branchName",  MatchMode.ANYWHERE.toMatchString(transAccRuleDef.getBranchName()));
			params.put("cardBin", transAccRuleDef.getCardBin1());
			params.put("status", transAccRuleDef.getStatus());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
		}
		// 发卡机构或者发卡机构部门
		else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())||
				this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())){
			cardBranchList.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
		} 
		else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		this.page = this.transAccRuleDefDAO.findTransAccRuleDef(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	//取得明细
	public String detail() throws Exception {
		TransAccRuleDefKey key = new TransAccRuleDefKey();
		key.setBranchCode(this.transAccRuleDef.getBranchCode());
		key.setCardBin1(this.transAccRuleDef.getCardBin1());
		key.setCardBin2(this.transAccRuleDef.getCardBin2());
		this.transAccRuleDef = (TransAccRuleDef) this.transAccRuleDefDAO.findByPk(key);
		
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			showCenter = true;
			showCard = false;
		} 
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			showCenter = false;
			showCard = true;
			this.transAccRuleDef = new TransAccRuleDef();
			this.transAccRuleDef.setBranchCode(this.getSessionUser().getBranchNo());
		} else{
			throw new BizException("非营运中心、营运中心部门、发卡机构及发卡机构部门不能操作。");
		}
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		this.accRegService.addTransAccRuleDef(this.transAccRuleDef, this.getSessionUser());	
		String msg = "新增发卡机构[" + this.transAccRuleDef.getBranchCode() + "]与卡BIN[" 
				+ this.transAccRuleDef.getCardBin1() + "]和卡BIN[" 
				+ this.transAccRuleDef.getCardBin2() + "]的转账规则定义成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/transAccRuleDef/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())
				&& !RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				&& !RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				&& !RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、营运中心部门、发卡机构及其部门不能操作。");
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		TransAccRuleDefKey key = new TransAccRuleDefKey();
		key.setBranchCode(this.getBranchCode());
		key.setCardBin1(this.getCardBin1());
		key.setCardBin2(this.getCardBin2());
		this.transAccRuleDef = (TransAccRuleDef) this.transAccRuleDefDAO.findByPk(key);
		
		this.accRegService.modifyTransAccRuleDef(transAccRuleDef, this.getSessionUser());
		String msg = "修改发卡机构[" + this.transAccRuleDef.getBranchCode() 
				+ "]与卡BIN[" + this.transAccRuleDef.getCardBin1() 
				+ "]和卡BIN[" + this.transAccRuleDef.getCardBin2() + "]转账规则定义成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/transAccRuleDef/list.do", msg);
		return SUCCESS;
	}
	
	// 删除转账规则定义
	public String delete() throws Exception {
		TransAccRuleDefKey key = new TransAccRuleDefKey();
		key.setBranchCode(this.getBranchCode());
		key.setCardBin1(this.getCardBin1());
		key.setCardBin2(this.getCardBin2());
		
		this.accRegService.deleteTransAccRuleDef(key);
		String msg = "删除发卡机构[" + this.getBranchCode() + "]与卡BIN["
			+ this.getCardBin1() + "]和卡BIN[" + this.getCardBin2() + "]的转账规则定义成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/transAccRuleDef/list.do", msg);
		return SUCCESS;
	}

	public TransAccRuleDef getTransAccRuleDef() {
		return transAccRuleDef;
	}

	public void setTransAccRuleDef(TransAccRuleDef transAccRuleDef) {
		this.transAccRuleDef = transAccRuleDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CardTypeState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardTypeState> statusList) {
		this.statusList = statusList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCardBin1() {
		return cardBin1;
	}

	public void setCardBin1(String cardBin1) {
		this.cardBin1 = cardBin1;
	}

	public String getCardBin2() {
		return cardBin2;
	}

	public void setCardBin2(String cardBin2) {
		this.cardBin2 = cardBin2;
	}

}
