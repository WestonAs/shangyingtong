package gnete.card.web.businessSubAccount;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankAcctInfoDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.SubAccountBindCardDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.Area;
import gnete.card.entity.BankAcct;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.SubAccountBindCard;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.AccountSystemState;
import gnete.card.entity.type.CustType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BusinessSubAccountService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * LoginAction.java.
 * 
 * @author aps-lzi
 * @since JDK1.6
 * @history 2011-12-1
 */
public class BusinessSubAccountInfoAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private BusinessSubAccountService businessSubAccountService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BankAcctInfoDAO bankAcctInfoDAO;
	@Autowired
	private SubAccountBindCardDAO subAccountBindCardDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private AreaDAO areaDAO;
	private BusinessSubAccountInfo businessSubAccountInfo;
	private Paginater page;
	public static final String BIND_ACCT_LIST = "bind_acct_list";
	public static final String CHECK = "check";
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (businessSubAccountInfo != null) {
			params.put("accountId", businessSubAccountInfo.getAccountId()); 
			params.put("cancelTime", businessSubAccountInfo.getCancelTime());
			params.put("cashAmount", businessSubAccountInfo.getCashAmount());
			params.put("createTime", businessSubAccountInfo.getCreateTime());
			params.put("custId", businessSubAccountInfo.getCustId());
			params.put("custName", businessSubAccountInfo.getCustName());
			params.put("freezeCashAmount", businessSubAccountInfo.getFreezeCashAmount());
			params.put("lastUpdateTime", businessSubAccountInfo.getLastUpdateTime());
			params.put("state", businessSubAccountInfo.getState());
			params.put("subAccountType", businessSubAccountInfo.getSubAccountType());
			params.put("systemId", businessSubAccountInfo.getSystemId()); 
		}
		//只能查询自已的或自已虚户体系的账户
		List<AccountSystemInfo> asiList = this.getAvailAcctSystem(false);
		params.put("filter", Symbol.YES);
		UserInfo userInfo = getSessionUser();
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
			params.put("filterCustId", userInfo.getMerchantNo());
		} else {
			params.put("filterCustId", userInfo.getBranchNo());
		}
		this.page = this.businessSubAccountInfoDAO.findBusinessSubAccountInfo(params, this.getPageNumber(), this.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			BusinessSubAccountInfo bsi = (BusinessSubAccountInfo)page.getList().get(i);
			AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsi.getSystemId());
			bsi.setSystemName(asi.getSystemName());
			bsi.setStateName(AccountState.valueOf(bsi.getState()).getName());
			//本机构/商户账户
			if (bsi.getCustId().equals(userInfo.getBranchNo()) || bsi.getCustId().equals(userInfo.getMerchantNo())) {
				bsi.setCanBind(Symbol.YES);
			}
			//体系内账户，可进行审核，销户，冻结
			if (asi.getCustId().equals(userInfo.getBranchNo())) {
				bsi.setMySystem(Symbol.YES);
			}
			if (bsi.getCustId().equals(getLoginBranchCode())) {
				bsi.setMyAccount(Symbol.YES);
			}
		}
		request.setAttribute("asiList", asiList);
		request.setAttribute("acctStates", AccountState.ALL.values());
		return LIST;
	}
	public String showCheck() throws Exception {
		this.businessSubAccountInfo = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(businessSubAccountInfo.getAccountId());
		
		businessSubAccountInfo.setStateName(AccountState.valueOf(businessSubAccountInfo.getState()).getName());
		return CHECK_LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.businessSubAccountInfo = (BusinessSubAccountInfo) this.businessSubAccountInfoDAO.findByPk(this.businessSubAccountInfo.getAccountId());
		businessSubAccountInfo.setStateName(AccountState.valueOf(businessSubAccountInfo.getState()).getName());
		String systemId = businessSubAccountInfo.getSystemId();
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(systemId);
		request.setAttribute("asi", asi);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", businessSubAccountInfo.getAccountId());
		List<SubAccountBindCard> list = subAccountBindCardDAO.findBindList(params);
		for (int i = 0; i < list.size(); i++) {
			SubAccountBindCard sabc = list.get(i);
			BankAcct bankAcct = (BankAcct)bankAcctInfoDAO.findByPk(sabc.getBankCardNo());
			String bankNo = bankAcct.getBankNo();
			BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankNo);
			bankAcct.setBankName(bankInfo.getBankName());
			sabc.setBankAcct(bankAcct);
		}
		request.setAttribute("list", list);
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.businessSubAccountInfo = (BusinessSubAccountInfo) this.businessSubAccountInfoDAO.findByPk(this.businessSubAccountInfo.getAccountId());
		checkSystem(businessSubAccountInfo);
		return MODIFY;
	}
	public String changeState() throws Exception {
		checkSystem(businessSubAccountInfo);
		String toState = businessSubAccountInfo.getState();
		this.businessSubAccountInfo = (BusinessSubAccountInfo) this.businessSubAccountInfoDAO.findByPk(this.businessSubAccountInfo.getAccountId());
		businessSubAccountInfo.setState(toState);
		businessSubAccountInfo.setLastUpdateTime(new Date());
		businessSubAccountInfoDAO.update(businessSubAccountInfo);
		String msg = "变更虚账户["+ businessSubAccountInfo.getAccountId() +"]状态成功！";
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/businessSubAccount/businessSubAccountInfo/list.do", msg);
		return SUCCESS;
	}
	// 修改对象操作
	public String modify() throws Exception {
		checkSystem(businessSubAccountInfo);
		businessSubAccountInfo.setLastUpdateTime(new Date());
		this.businessSubAccountService.modifyBusinessSubAccountInfo(businessSubAccountInfo, this.getSessionUserCode());
		this.addActionMessage("/businessSubAccount/accountSystemInfo/list.do", "修改成功！");
		this.log("修改虚账户["+ businessSubAccountInfo.getSystemId() +"]成功！", UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		List<AccountSystemInfo> asiList = this.getAvailAcctSystem(true);
		request.setAttribute("asiList", asiList);
		return ADD;
	}

	private List<AccountSystemInfo> getAvailAcctSystem(boolean excludeExist) {
		List<String> list = new ArrayList<String>();
		//商户
		String loginCust = getLoginBranchCode();
		if (isMerchantRoleLogined()) {
			//运营中心
			list.add(loginCust);
			//发卡机构
			List<BranchInfo> branchInfos = branchInfoDAO.findByMerch(loginCust);
			for (int i = 0; i < branchInfos.size(); i++) {
				list.add(branchInfos.get(i).getBranchCode());
			}
		} 
		//发卡机构
		else if(isCardRoleLogined()){
			//本机构
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(loginCust);
			String parentBranch = branchInfo.getParent();
			List<BranchInfo> branchInfos = branchInfoDAO.findCardByManange(parentBranch);
			for (int i = 0; i < branchInfos.size(); i++) {
				list.add(branchInfos.get(i).getBranchCode());
			}
		} 
		//运营机构
		else if (isFenzhiRoleLogined()) {
			List<BranchInfo> branchInfos = branchInfoDAO.findAll();
			for (int i = 0; i < branchInfos.size(); i++) {
				list.add(branchInfos.get(i).getBranchCode());
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("custIds", list.toArray());
		if (excludeExist) {
			params.put("loginCust", loginCust);
		}
		//仅查询状态为正常的虚账体系
		params.put("state", AccountSystemState.NORMAL.getValue());
		List<AccountSystemInfo> asiList = accountSystemInfoDAO.findByInfos(params);
		return asiList;
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception { 
		checkSystem(businessSubAccountInfo);
		BigDecimal initAmount = new BigDecimal(0.0d);
		businessSubAccountInfo.setCashAmount(initAmount);
		if(isMerchantRoleLogined()){
			businessSubAccountInfo.setCustId(getSessionUser().getMerchantNo());
			MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(getSessionUser().getMerchantNo());
			businessSubAccountInfo.setCustName(merchInfo.getMerchName());
			businessSubAccountInfo.setSubAccountType(CustType.TYPE_MERCHANT.getValue());
		} 
		//发卡机构
		else {
			businessSubAccountInfo.setCustId(getSessionUser().getBranchNo());
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(getSessionUser().getBranchNo());
			businessSubAccountInfo.setCustName(branchInfo.getBranchName());
			businessSubAccountInfo.setSubAccountType(CustType.TYPE_BRANCH.getValue());
		}
		businessSubAccountInfo.setState(AccountState.WAIT_CHECK.getValue());
		businessSubAccountInfo.setFreezeCashAmount(initAmount);
		businessSubAccountInfo.setLastUpdateTime(new Date());
		businessSubAccountInfo.setCreateTime(new Date());
		this.businessSubAccountService.addBusinessSubAccountInfo(businessSubAccountInfo);
		
		String msg = LogUtils.r("申请虚账户[{0}]成功", businessSubAccountInfo.getSystemId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/businessSubAccountInfo/showApply.do", msg);
		return SUCCESS;
	}
	public String showBankAcct() throws Exception { 
		checkSystem(businessSubAccountInfo);
		Map<String, Object> params = new HashMap<String, Object>();
		String custId = "";
		if (isMerchantRoleLogined()) {
			custId = getSessionUser().getMerchantNo();
		} else {
			custId = getSessionUser().getBranchNo();
		}
		params.put("custId", custId);
		List<BankAcct> list = bankAcctInfoDAO.findBankAccts(params);
		for (int i = 0; i < list.size(); i++) {
			BankAcct bankAcct = (BankAcct)list.get(i);
			BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankAcct.getBankNo());
			if (bankInfo != null) {
				bankAcct.setBankName(bankInfo.getBankName());
				String bankAdd = bankInfo.getAddrNo();
				Area area = (Area)areaDAO.findByPk(bankAdd);
				bankAcct.setBankAdd(area.getAreaName());
			}
		}
		request.setAttribute("bankAccts", list);
		params.put("accountId", businessSubAccountInfo.getAccountId());
		List<SubAccountBindCard> sabcList = subAccountBindCardDAO.findBindList(params);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sabcList.size(); i++) {
			SubAccountBindCard sabc = sabcList.get(i);
			sb.append(sabc.getBankCardNo());
			if (i != sabcList.size()-1) {
				sb.append(",");
			}
			if (Symbol.YES.equals(sabc.getIsDefault())) {
				request.setAttribute("defaultAcct", sabc.getBankCardNo());
			}
		}
		request.setAttribute("bindAccts", sb.toString());
		return BIND_ACCT_LIST;
	}
	
	public String bindBankAcct() throws Exception { 
		checkSystem(businessSubAccountInfo);
		businessSubAccountService.modifyBindAcct(businessSubAccountInfo);
		String msg = LogUtils.r("修改账户[{0}]绑定银行账户成功", businessSubAccountInfo.getAccountId());
		log(msg, UserLogType.UPDATE);
		this.addActionMessage("/businessSubAccount/businessSubAccountInfo/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		String id = businessSubAccountInfo.getAccountId();
		businessSubAccountInfoDAO.delete(id);
		String msg = LogUtils.r("删除账户[{0}]成功,操作人[{1}]", businessSubAccountInfo.getAccountId(), getSessionUserCode());
		log(msg, UserLogType.DELETE);
		this.addActionMessage("/businessSubAccount/businessSubAccountInfo/list.do", msg);
		return SUCCESS;
	}
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BusinessSubAccountInfo getBusinessSubAccountInfo() {
		return businessSubAccountInfo;
	}

	public void setBusinessSubAccountInfo(BusinessSubAccountInfo businessSubAccountInfo) {
		this.businessSubAccountInfo = businessSubAccountInfo;
	}
	public void checkSystem(BusinessSubAccountInfo bsi) throws BizException{
		String systemId = bsi.getSystemId();
		if (StringUtils.isEmpty(systemId)) {
			BusinessSubAccountInfo bsInfo = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(bsi.getAccountId());
			if (bsInfo != null) {
				systemId = bsInfo.getSystemId();
			}
		}
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(systemId);
		Assert.isTrue(!AccountSystemState.STOP.getValue().equals(asi.getState()), "虚户体系已停用，操作无法进行");
	}
	
}
