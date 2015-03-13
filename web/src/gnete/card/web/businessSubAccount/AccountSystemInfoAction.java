package gnete.card.web.businessSubAccount;
 
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.state.AccountSystemState;
import gnete.card.entity.type.BranchType;
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

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author aps-lzi
 * @since JDK1.6
 * @history 2011-12-1
 */
public class AccountSystemInfoAction extends BaseAction {
	
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BusinessSubAccountService businessSubAccountService;
	private AccountSystemInfo accountSystemInfo;
	@Autowired
	BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	private Paginater page;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (accountSystemInfo != null) {
			params.put("custId", accountSystemInfo.getCustId()); 
			params.put("systemName", accountSystemInfo.getSystemName());
			params.put("systemId", accountSystemInfo.getSystemId());
			params.put("state", accountSystemInfo.getState());
		}
		//运营机构
		if (isFenzhiRoleLogined()) {
			params.put("parentBranch", getSessionUser().getBranchNo());
			request.setAttribute("runBranch", Symbol.YES);
		}
		//发卡机构
		if (isCardRoleLogined()) {
			params.put("custId", getSessionUser().getBranchNo());
		}
		request.setAttribute("states", AccountSystemState.ALL.values());
		this.page = this.accountSystemInfoDAO.findAccountSystemInfo(params, this.getPageNumber(), this.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			AccountSystemInfo asi = (AccountSystemInfo)page.getList().get(i);
			String systemId = asi.getSystemId();
			params.clear();
			params.put("systemId", systemId);
			List<BusinessSubAccountInfo> bsais = businessSubAccountInfoDAO.findAcctInfo(params);
			BigDecimal freezeAmt = new BigDecimal(0);
			BigDecimal balanceAmt = new BigDecimal(0);
			for (int j = 0; j < bsais.size(); j++) {
				BusinessSubAccountInfo businessSubAccountInfo = bsais.get(j);
				freezeAmt = AmountUtil.add(freezeAmt, businessSubAccountInfo.getFreezeCashAmount()); 
				balanceAmt = AmountUtil.add(balanceAmt, businessSubAccountInfo.getCashAmount());
			}
			asi.setFreezeAmt(freezeAmt);
			asi.setBalanceAmt(balanceAmt);
			asi.setUsableAmt(AmountUtil.subtract(balanceAmt, freezeAmt));
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.accountSystemInfo = (AccountSystemInfo) this.accountSystemInfoDAO.findByPk(this.accountSystemInfo.getSystemId());
		
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		Assert.isTrue(isFenzhiRoleLogined(), "非运营分支机构不能进行此操作");
		this.accountSystemInfo = (AccountSystemInfo) this.accountSystemInfoDAO.findByPk(this.accountSystemInfo.getSystemId());
		request.setAttribute("states", AccountSystemState.ALL.values());
		return MODIFY;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		accountSystemInfo.setLastUpdateTime(new Date());
		this.businessSubAccountService.modifyAccountSystemInfo(accountSystemInfo, this.getSessionUserCode());
		String msg = LogUtils.r("修改虚账户体系[{0}]成功！", accountSystemInfo.getSystemId());
		this.addActionMessage("/businessSubAccount/accountSystemInfo/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if (!isFenzhiRoleLogined()) {
			throw new BizException("非运营分支机构不能进行此操作");
		}
		request.setAttribute("states", AccountSystemState.ALL.values());
		return ADD;
	}

	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception { 
		accountSystemInfo.setParentBranch(getSessionUser().getBranchNo());
		accountSystemInfo.setCreateTime(new Date());
		accountSystemInfo.setLastUpdateTime(new Date());
		this.businessSubAccountService.addAccountSystemInfo(accountSystemInfo);
		
		String msg = LogUtils.r("新增虚账户体系[{0}]成功", accountSystemInfo.getSystemId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/accountSystemInfo/list.do", msg);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public AccountSystemInfo getAccountSystemInfo() {
		return accountSystemInfo;
	}

	public void setAccountSystemInfo(AccountSystemInfo accountSystemInfo) {
		this.accountSystemInfo = accountSystemInfo;
	}
	
	public List<BranchType> getBranchTypes() {
		List<BranchType> branchTypes = new ArrayList<BranchType>();
		branchTypes.add(BranchType.CARD);
		branchTypes.add(BranchType.FENZHI);
		return branchTypes;
	}
}
