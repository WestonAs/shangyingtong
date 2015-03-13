package gnete.card.web.businessSubAccount;

import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BusinessSubAccountInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RechargeBillDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusinessSubAccountInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RechargeBill;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.AccountState;
import gnete.card.entity.state.RechargeState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RechargeService;
import gnete.card.web.BaseAction;
import gnete.etc.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author aps-lzi
 * @since JDK1.6
 * @history 2011-12-1
 */
public class RechargeAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	RechargeBillDAO rechargeBillDAO;
	@Autowired
	private RechargeService rechargeService; 
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BusinessSubAccountInfoDAO businessSubAccountInfoDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	public final String CHECK = "check";
	private RechargeBill rechargeBill = new RechargeBill();
	public RechargeBill getRechargeBill() {
		return rechargeBill;
	}

	public void setRechargeBill(RechargeBill rechargeBill) {
		this.rechargeBill = rechargeBill;
	}

	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startCreateTime", DateUtil.formatDate(rechargeBill.getStartCreateDate(), "yyyyMMdd"));
		params.put("endCreateTime", DateUtil.formatDate(rechargeBill.getEndCreateDate(), "yyyyMMdd"));
		params.put("amount", rechargeBill.getAmount());
		params.put("state", rechargeBill.getState());
		params.put("accountId", rechargeBill.getAccountId());
		params.put("loginId", getLoginId());
		params.put("custId", rechargeBill.getCustId());
		//查询自身体系下或自己创建的充值单
		this.page = rechargeBillDAO.findRechargeInfo(params, this.getPageNumber(), this.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			RechargeBill bill = (RechargeBill)page.getList().get(i);
			bill.setStateName(RechargeState.valueOf(bill.getState()).getName());
			//发卡机构或运营分支机构
			if (isBranch() && RechargeState.WAIT_CHECK.getValue().equals(bill.getState())) {
				//当前体系下的
				if (bill.getBranchNo().equals(getSessionUser().getBranchNo())) {
					bill.setCanCheck(Symbol.YES);
				}
			}
		}
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		rechargeBill = (RechargeBill)rechargeBillDAO.findByPk(rechargeBill.getNo());
		rechargeBill.setStateName(RechargeState.valueOf(rechargeBill.getState()).getName());
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
			rechargeBill.setCustId(userInfo.getMerchantNo());
			rechargeBill.setCustName(merchInfo.getMerchName());
		} else {
			rechargeBill.setCustId(userInfo.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)branchInfoDAO.findByPk(userInfo.getBranchNo());
			rechargeBill.setCustId(userInfo.getBranchNo());
			rechargeBill.setCustName(branchInfo.getBranchName());
		}
		String accountId = rechargeBill.getAccountId();
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(accountId);
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(bsai.getSystemId());
		rechargeBill.setBranchNo(asi.getCustId());
		String no = rechargeService.addRecharge(rechargeBill, getSessionUser());
		String msg = LogUtils.r("新增充值单成功,充值单号[{0}],操作人[{1}]", no, getSessionUserCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/businessSubAccount/recharge/list.do", msg);
		return SUCCESS;
	}
	public String showCheck() throws Exception {
		rechargeBill = (RechargeBill)rechargeBillDAO.findByPk(rechargeBill.getNo());
		request.setAttribute("checkResults", getCheckResults());
		return CHECK;
	}
	public String check() throws Exception {
		rechargeService.checkRecharge(rechargeBill, getSessionUser());
		String	msg = LogUtils.r("审核充值单[{0}],审核结果[{1}],审核人[{2}]", rechargeBill.getNo(), RechargeState.valueOf(rechargeBill.getState()).getName(), getSessionUserCode());
		this.log(msg, UserLogType.CHECK);
		this.addActionMessage("/businessSubAccount/recharge/list.do", msg);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Map<String, String> getCheckResults() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(RechargeState.RECHARGED.getValue(), "通过");
		map.put(RechargeState.CHECK_FAILURE.getValue(), "不通过");
		return map;
	}

	public String getSystemInfo() {
		String accountId = request.getParameter("accountId");
		BusinessSubAccountInfo bsai = (BusinessSubAccountInfo)businessSubAccountInfoDAO.findByPk(accountId);
		String systemId = bsai.getSystemId();
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(systemId);
		JSONObject object = new JSONObject();
		object.put("acctNo", asi.getAcctNo());
		object.put("acctName", asi.getAcctName());
		respond(object.toString());
		return null;
	}
	public Collection getRechargeStates() {
		return RechargeState.ALL.values();
	}
	public boolean isBranch() {
		if(isCardRoleLogined() || isFenzhiRoleLogined()){
			return true;
		}
		return false;
	}
	public String getLoginId() {
		if (isBranch()) {
			return getSessionUser().getBranchNo();
		}else {
			return getSessionUser().getMerchantNo();
		}
	}
}
