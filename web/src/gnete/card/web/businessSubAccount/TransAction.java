package gnete.card.web.businessSubAccount;

import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.TransBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.TransBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.AccountSystemState;
import gnete.card.entity.state.TransState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.TransBillService;
import gnete.card.web.BaseAction;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author libaozhu
 * @date 2013-3-12
 */
public class TransAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TransBillDAO transBillDAO;
	@Autowired
	private TransBillService transBillService;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	private TransBill transBill = new TransBill();
	
	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		String loginId = getLoginBranchCode();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payerAccountId", transBill.getPayerAccountId());
		params.put("payeeAccountId", transBill.getPayeeAccountId());
		params.put("startCreateTime", DateUtil.formatDate(transBill.getStartCreateDate(), "yyyyMMdd"));
		params.put("endCreateTime", DateUtil.formatDate(transBill.getEndCreateDate(), "yyyyMMdd"));
		params.put("state", transBill.getState());
		params.put("amount", transBill.getAmount());
		params.put("loginId", getLoginBranchCode());
		params.put("payerCustId", transBill.getPayerCustId());
		params.put("payerCustName", transBill.getPayerCustName());
		params.put("payeeCustId", transBill.getPayeeCustId());
		params.put("payeeCustName", transBill.getPayeeCustName());
		this.page = transBillDAO.findPaginater(params, super.getPageNumber(), super.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			TransBill transBill = (TransBill)page.getList().get(i);
			transBill.setStateName(TransState.valueOf(transBill.getState()).getName());
			if (loginId.equals(transBill.getSystemCustId()) && TransState.WAIT_CHECK.getValue().equals(transBill.getState())) {
				transBill.setCanCheck(Symbol.YES);
			}
		}
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		transBill = (TransBill)transBillDAO.findByPk(transBill.getId());
		transBill.setStateName(TransState.valueOf(transBill.getState()).getName());
		return DETAIL;
	}
	
	public TransBill getTransBill() {
		return transBill;
	}

	public void settTansBill(TransBill transBill) {
		this.transBill = transBill;
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
		params.clear();
		params.put("state", AccountSystemState.NORMAL.getValue());
		List<AccountSystemInfo> asis = accountSystemInfoDAO.findByInfos(params);
		request.setAttribute("payeeSystems", asis);
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
			transBill.setPayerCustId(userInfo.getMerchantNo());
			transBill.setPayerCustName(merchInfo.getMerchName());
		} else {
			transBill.setPayerCustId(userInfo.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(userInfo.getBranchNo());
			transBill.setPayerCustId(userInfo.getBranchNo());
			transBill.setPayerCustName(branchInfo.getBranchName());
		}
		String id = transBillService.addTransBill(transBill, getSessionUser());
		String msg = LogUtils.r("新增转账记录[{0}]成功,操作人[{1}]", id, getSessionUserCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/transfer/list.do", msg);
		return SUCCESS;
	}
	
	public String check() throws Exception {
		try {
			transBillService.checkTransBill(transBill, getSessionUser());
			String checkResult = "";
			if (Symbol.YES.equals(transBill.getCheckPass())) {
				checkResult = "通过";
			} else {
				checkResult = "不通过";
			}
			String msg = LogUtils.r("审核转账记录[{0}],审核结果[{1}],审核人[{2}]", ArrayUtils.toString(transBill.getIds()), checkResult, getSessionUserCode());
			this.log(msg, UserLogType.CHECK);
			this.addActionMessage("/businessSubAccount/transfer/list.do", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Collection getTransStates() {
		return TransState.ALL.values();
	}
	public String getPayeeSystems() {
		String crossSystem = request.getParameter("crossSystem");
		String payerAccountId = request.getParameter("payerAccountId");
		BusinessSubAccountInfo bsi = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(payerAccountId);
		String systemId = bsi.getSystemId();
		
		List<AccountSystemInfo> list = new ArrayList<AccountSystemInfo>();
		JSONArray jsonArray = new JSONArray();
		try {
			if (Symbol.YES.equals(crossSystem)) {
				list = accountSystemInfoDAO.findByInfos(null);
				for (int i = 0; i < list.size(); i++) {
					AccountSystemInfo asi = (AccountSystemInfo)list.get(i);
					if (!systemId.equals(asi.getSystemId())) {
						JSONObject object = new JSONObject();
						object.put("systemId", asi.getSystemId());
						object.put("systemName", asi.getSystemName());
						jsonArray.add(object);
					}
				}
			} else {
				AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(systemId);
				JSONObject object = new JSONObject();
				object.put("systemId", asi.getSystemId());
				object.put("systemName", asi.getSystemName());
				jsonArray.add(object);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		respond(jsonArray.toString());
		return null;
	}
	public String getPayeeAccts() {
		String loginCustId = getLoginBranchCode();
		String payeeSystemId = request.getParameter("payeeSystemId");
		String payerAccountId = request.getParameter("payerAccountId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemId", payeeSystemId);
		params.put("state", AccountState.NORMAL.getValue());
		List<BusinessSubAccountInfo> list = businessSubAccountInfoDAO.findAcctInfo(params);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			BusinessSubAccountInfo bsi = list.get(i);
			//去除付款账户
			if (payerAccountId.equals(bsi.getAccountId())) {
				continue;
			}
			JSONObject object = new JSONObject();
			object.put("accountId", bsi.getAccountId());
			object.put("custName", bsi.getCustName());
			jsonArray.add(object);
		}
		respond(jsonArray.toString());
		return null;
	}
	public String getUsableAmount() {
		String payerAccountId = request.getParameter("payerAccountId");
		BusinessSubAccountInfo payerAccountInfo = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(payerAccountId);
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		JSONObject object = new JSONObject();
		object.put("usableAmount", decimalFormat.format(payerAccountInfo.getUsableBalance()));
		respond(object.toString());
		return null;
	}
	public String updateFee() throws Exception {
		try {
			String id = request.getParameter("id");
			BigDecimal fee = new BigDecimal(request.getParameter("fee"));
			TransBill transBill = (TransBill)transBillDAO.findByPk(id);
			transBill.setFee(fee);
			transBillDAO.update(transBill);
			respond("success");
		} catch (Exception e) {
			respond("error");
		}
		return null;
	}
}
