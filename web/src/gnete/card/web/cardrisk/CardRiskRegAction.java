package gnete.card.web.cardrisk;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.CardRiskBalanceDAO;
import gnete.card.dao.CardRiskRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardRiskBalance;
import gnete.card.entity.CardRiskReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.AdjType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardRiskService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 信誉额度申请
 *
 */
public class CardRiskRegAction extends BaseAction{

	@Autowired
	private CardRiskRegDAO cardRiskRegDAO;
	@Autowired
	private CardRiskBalanceDAO cardRiskBalanceDAO;
	@Autowired
	private CardRiskService cardRiskService;
	
	private CardRiskReg cardRiskReg;
	private CardRiskBalance cardRiskBalance;
	private Paginater page;
	private Collection adjTypeList;
	private Long id;

	private List<BranchInfo> branchList;
	private List<RegisterState> statusList;
	
	private boolean showAll;
	
	@Override
	public String execute() throws Exception {
//		initPage();
		this.adjTypeList = AdjType.getCardRiskRegType(); //加载调整类型
		this.statusList = RegisterState.getCheckStatus();
		
		Map<String, Object> params =new HashMap<String, Object>();
		
		if (cardRiskReg != null) {
			params.put("branchCode", cardRiskReg.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardRiskReg.getBranchName()));
			params.put("adjType", cardRiskReg.getAdjType());
			params.put("status", cardRiskReg.getStatus());
		}
		// 如果是运营中心则允许查询所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())) {
			showAll = true;
		}
		// 分支机构则允许查询其管理的机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showAll = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 发卡机构则允许查自己
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			showAll = true;
			params.put("branchCode", this.getLoginBranchCode());
		}
		// 其他机构不允许访问
		else {
			throw new BizException("该机构禁止访问该菜单！");
		}
		
		this.page = this.cardRiskRegDAO.findCardRiskReg(params, this.getPageNumber(), this.getPageSize());	
		return LIST;
	}
	
	//取得机构风险准备金调整登记的明细
	public String detail() throws Exception {
		
		this.cardRiskReg = (CardRiskReg) this.cardRiskRegDAO.findByPk(this.cardRiskReg.getId());
//		this.log("查询机构风险准备金调整["+this.cardRiskReg.getId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if ( !RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构用户禁止申请风险准备金调整！");
		}
		this.adjTypeList = AdjType.getCardRiskRegType(); //加载调整类型
		return ADD;
	}	
	
	// 新增准备金调整申请登记
	public String add() throws Exception {			
		this.cardRiskService.addCardRiskReg(cardRiskReg, this.getSessionUserCode());
		
		//启动单个流程
		this.workflowService.startFlow(cardRiskReg, "cardRiskAdapter", cardRiskReg.getId().toString(), this.getSessionUser());
		
		String msg = "机构准备金调整申请["+this.cardRiskReg.getId()+"]成功！";
		this.addActionMessage("/cardRisk/cardRiskReg/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		if ( !RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构用户禁止修改风险准备金调整！");
		}
//		initPage();
		this.adjTypeList = AdjType.getCardRiskRegType(); //加载调整类型
		this.cardRiskReg = (CardRiskReg)this.cardRiskRegDAO.findByPk(this.cardRiskReg.getId());
		return MODIFY;
	}
	
	// 修改账户透支额度调整记录
	public String modify() throws Exception {
		
		this.cardRiskService.modifyCardRiskReg(cardRiskReg, this.getSessionUserCode());
		this.addActionMessage("/cardRisk/cardRiskReg/list.do", "修改准备金调整申请成功！");	
		return SUCCESS;
	}
	
	// 删除账户透支额度调整记录
	public String delete() throws Exception {
		if (!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构用户禁止申请风险准备金调整！");
		}
		this.cardRiskService.deleteCardRiskReg(this.getId());
		String msg = "删除准备金调整调整记录[" +this.getId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/cardRisk/cardRiskReg/list.do", msg);
		return SUCCESS;
	}
	
	// 准备金调整审核列表
	public String checkList() throws Exception{
		
		// 首先调用流程引擎，得到我的待审批的工单ID
		String[] cardRiskRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_CARD_RISK_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(cardRiskRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", cardRiskRegIds);
		this.page = this.cardRiskRegDAO.findCardRiskReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}

	//取得准备金调整申请的明细，给流程审核人查看
	public String checkDetail() throws Exception {
		
		this.cardRiskReg = (CardRiskReg)this.cardRiskRegDAO.findByPk(cardRiskReg.getId());
		this.cardRiskBalance = (CardRiskBalance)this.cardRiskBalanceDAO.findByPk(this.cardRiskReg.getBranchCode());
		this.log("查询准备金调整申请["+this.cardRiskReg.getId()+"]审核明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
//	private void initPage() {
//		this.adjTypeList = AdjType.getCardRiskRegType(); //加载调整类型
//		this.branchList = this.getMyCardBranch();
//		
//		// 如果是运营中心则允许查询所有
//		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())) {
//			showAll = true;
//		}
//		// 分支机构则允许查询其管理的机构
//		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
//			showAll = true;
//		}
//		// 发卡机构则允许查自己
//		else {
//			showAll = false;
//		}
//	}
	
	/**
	 * 返回登录机构
	 * @return
	 */
	public String getParent() {
		if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			return super.getSessionUser().getBranchNo();
		}
		return "";
	}

	
	public CardRiskReg getCardRiskReg() {
		return cardRiskReg;
	}

	public void setCardRiskReg(CardRiskReg cardRiskReg) {
		this.cardRiskReg = cardRiskReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getAdjTypeList() {
		return adjTypeList;
	}

	public void setAdjTypeList(Collection adjTypeList) {
		this.adjTypeList = adjTypeList;
	}

	public void setCardRiskBalance(CardRiskBalance cardRiskBalance) {
		this.cardRiskBalance = cardRiskBalance;
	}

	public CardRiskBalance getCardRiskBalance() {
		return cardRiskBalance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}

}
