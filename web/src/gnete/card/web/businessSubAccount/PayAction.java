package gnete.card.web.businessSubAccount;

import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BankNoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PayBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BankNo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PayBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.PayState;
import gnete.card.entity.state.TransState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PayBillService;
import gnete.card.web.BaseAction;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author libaozhu
 * @date 2013-3-12
 */
public class PayAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PayBillDAO payBillDAO;
	@Autowired
	private PayBillService payBillService;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BankNoDAO bankNoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	private PayBill payBill = new PayBill();
	
	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payerAccountId", payBill.getPayerAccountId());
		params.put("payerCustId", payBill.getPayerCustId());
		params.put("payerCustName", payBill.getPayerCustName());
		params.put("startCreateTime", DateUtil.formatDate(payBill.getStartCreateDate(), "yyyyMMdd"));
		params.put("endCreateTime", DateUtil.formatDate(payBill.getEndCreateDate(), "yyyyMMdd"));
		params.put("state", payBill.getState());
		params.put("amount", payBill.getAmount());
		String loginId = getLoginBranchCode();
		params.put("loginId", loginId);
		this.page = payBillDAO.findPaginater(params, super.getPageNumber(), super.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			PayBill payBill = (PayBill)page.getList().get(i);
			payBill.setStateName(TransState.valueOf(payBill.getState()).getName());
			if (loginId.equals(payBill.getSystemCustId()) && PayState.WAIT_CHECK.getValue().equals(payBill.getState())) {
				payBill.setCanCheck(Symbol.YES);
			}
			String payeeBankType = payBill.getPayeeBankType();
			BankNo bankNo = (BankNo)bankNoDAO.findByPk(payeeBankType);
			payBill.setPayeeBankTypeName(bankNo.getBankTypeName());
		}
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		payBill = (PayBill)payBillDAO.findByPk(payBill.getId());
		payBill.setStateName(PayState.valueOf(payBill.getState()).getName());
		BankNo bankNo = (BankNo)bankNoDAO.findByPk(payBill.getPayeeBankType());
		payBill.setPayeeBankTypeName(bankNo.getBankTypeName());
		return DETAIL;
	}
	
	public PayBill getPayBill() {
		return payBill;
	}

	public void setPayBill(PayBill payBill) {
		this.payBill = payBill;
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
		request.setAttribute("payerAccts", list);
		List<BankNo> bankTypes = bankNoDAO.findAll();
		request.setAttribute("bankTypes", bankTypes);
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
			payBill.setPayerCustId(userInfo.getMerchantNo());
			payBill.setPayerCustName(merchInfo.getMerchName());
		} else {
			payBill.setPayerCustId(userInfo.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(userInfo.getBranchNo());
			payBill.setPayerCustId(userInfo.getBranchNo());
			payBill.setPayerCustName(branchInfo.getBranchName());
		}
		String id = null;
		id = payBillService.addPayBill(payBill, getSessionUser());
		String msg = LogUtils.r("新增支付记录[{0}]成功,操作人[{1}]", id, getSessionUserCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/pay/list.do", msg);
		return SUCCESS;
	}
	
	public String check() throws Exception {
		payBillService.checkPayBill(payBill, getSessionUser());
		String checkResult = "";
		if (Symbol.YES.equals(payBill.getCheckPass())) {
			checkResult = "通过";
		} else {
			checkResult = "未通过";
		}
		String msg = LogUtils.r("审核支付记录[{0}],审核结果[{1}],审核人[{2}]", ArrayUtils.toString(payBill.getIds()), checkResult, getSessionUserCode());
		this.log(msg, UserLogType.CHECK);
		this.addActionMessage("/businessSubAccount/pay/list.do", msg);
		return SUCCESS;
	}
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isBranch() {
		if(isCardRoleLogined() || isFenzhiRoleLogined()){
			return true;
		}
		return false;
	}
	public Collection getPayStates() {
		return PayState.ALL.values();
	}
	
	public String updateFee() throws Exception {
		try {
			String id = request.getParameter("id");
			BigDecimal fee = new BigDecimal(request.getParameter("fee"));
			PayBill payBill = (PayBill)payBillDAO.findByPk(id);
			payBill.setFee(fee);
			payBillDAO.update(payBill);
			respond("success");
		} catch (Exception e) {
			e.printStackTrace();
			respond("error");
		}
		return null;
	}
}
