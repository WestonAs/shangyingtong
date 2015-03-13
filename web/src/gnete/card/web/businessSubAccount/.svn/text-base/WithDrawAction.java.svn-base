package gnete.card.web.businessSubAccount;

import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BankAcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.SubAccountBindCardDAO;
import gnete.card.dao.WithDrawBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BankAcct;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.SubAccountBindCard;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WithDrawBill;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.WithdrawState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.WithdrawService;
import gnete.card.web.BaseAction;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author aps-lzi
 * @since JDK1.6
 * @history 2011-12-1
 */
public class WithDrawAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private WithDrawBillDAO withDrawBillDAO;
	@Autowired
	private SubAccountBindCardDAO subAccountBindCardDAO;
	@Autowired
	private BankAcctInfoDAO bankAcctInfoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	private WithDrawBill withDrawBill = new WithDrawBill();
	
	private Paginater page;
	
	public WithDrawBill getWithDrawBill() {
		return withDrawBill;
	}

	public void setWithDrawBill(WithDrawBill withDrawBill) {
		this.withDrawBill = withDrawBill;
	}

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", withDrawBill.getAccountId());
		params.put("startCreateTime", DateUtil.formatDate(withDrawBill.getStartCreateDate(), "yyyyMMdd"));
		params.put("endCreateTime", DateUtil.formatDate(withDrawBill.getEndCreateDate(), "yyyyMMdd"));
		params.put("state", withDrawBill.getState());
		params.put("amount", withDrawBill.getAmount());
		params.put("loginId", getLoginBranchCode());
		params.put("custId", withDrawBill.getCustId());
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
		}
		this.page = this.withDrawBillDAO.findPaginater(params, this.getPageNumber(), this.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			WithDrawBill bill = (WithDrawBill)page.getList().get(i);
			bill.setStateName(WithdrawState.valueOf(bill.getState()).getName());
			//发卡机构或运营分支机构
			if (isBranch() && WithdrawState.WAIT_CHECK.getValue().equals(bill.getState())) {
				if (bill.getBranchNo().equals(getSessionUser().getBranchNo())) {
					bill.setCanCheck(Symbol.YES);
				}
			}
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.withDrawBill = (WithDrawBill)withDrawBillDAO.findByPk(withDrawBill.getNo());
		withDrawBill.setStateName(WithdrawState.valueOf(withDrawBill.getState()).getName());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (isMerchantRoleLogined()) {
			params.put("custId", getSessionUser().getMerchantNo());
		} else {
			params.put("custId", getSessionUser().getBranchNo());
		}
		params.put("state", AccountState.NORMAL.getValue());
		List<BusinessSubAccountInfo> list = businessSubAccountInfoDAO.findAcctInfo(params);
		for (int i = 0; i < list.size(); i++) {
			BusinessSubAccountInfo bsai = list.get(i);
			String systemId = bsai.getSystemId();
			AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(systemId);
			bsai.setSystemName(asi.getSystemName());
			bsai.setAcctDesc(bsai.getAccountId()+" -- "+asi.getSystemName());
		}
		request.setAttribute("subAccts", list);
		return ADD;
	}

	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception { 
		UserInfo userInfo = getSessionUser();
		if(isMerchantRoleLogined()){
			MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(userInfo.getMerchantNo());
			withDrawBill.setCustId(userInfo.getMerchantNo());
			withDrawBill.setCustName(merchInfo.getMerchName());
		} else {
			withDrawBill.setCustId(userInfo.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(userInfo.getBranchNo());
			withDrawBill.setCustId(userInfo.getBranchNo());
			withDrawBill.setCustName(branchInfo.getBranchName());
		}
		String id = withdrawService.addWithdraw(withDrawBill, getSessionUser());
		String msg = LogUtils.r("新增提现单[{0}]成功,操作人[{1}]", id, getSessionUserCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/withdraw/list.do", msg);
		return SUCCESS;
	}
	public String check() throws Exception {
		withdrawService.checkWithdraw(withDrawBill, getSessionUser());
		
		String msg = LogUtils.r("审核提现单[{0}],审核结果[{1}],审核人[{2}]", ArrayUtils.toString(withDrawBill.getNos()), WithdrawState.valueOf(withDrawBill.getState()).getName(), getSessionUserCode());
		this.log(msg, UserLogType.CHECK);
		this.addActionMessage("/businessSubAccount/withdraw/list.do", msg);
		return SUCCESS;
	}
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getBankAccts() throws Exception{
		String accountId = request.getParameter("accountId");
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(accountId); 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", accountId);
		List<SubAccountBindCard> list = subAccountBindCardDAO.findBindList(params);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SubAccountBindCard sabc = list.get(i);
			String bankCardNo = sabc.getBankCardNo();
			BankAcct bankAcct = (BankAcct)bankAcctInfoDAO.findByPk(bankCardNo);
			JSONObject object = new JSONObject();
			object.put("acctNo", bankAcct.getAcctNo());
			String acctDesc = bankAcct.getAcctNo()+"--"+bankAcct.getAcctName();
			if (Symbol.YES.equals(sabc.getIsDefault())) {
				acctDesc += "(默认)";
			}
			object.put("acctDesc", acctDesc);
			jsonArray.add(object);
		}
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		jsonObject.put("usableAmount", decimalFormat.format(bsai.getUsableBalance()));
		jsonObject.put("bankAccts", jsonArray);
		respond(jsonObject.toString());
		return null;
	}
	
	public boolean isBranch() {
		if(isCardRoleLogined() || isFenzhiRoleLogined()){
			return true;
		}
		return false;
	}
	public Collection getWithdrawStates() {
		return WithdrawState.ALL.values();
	}
	
	public String updateFee() throws Exception {
		try {
			String id = request.getParameter("id");
			BigDecimal fee = new BigDecimal(request.getParameter("fee"));
			WithDrawBill withDrawBill = (WithDrawBill)withDrawBillDAO.findByPk(id);
			withDrawBill.setFee(fee);
			withDrawBillDAO.update(withDrawBill);
			respond("success");
		} catch (Exception e) {
			respond("error");
		}
		return null;
	}
}
