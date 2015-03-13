package gnete.card.web.transactionData;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.TerminalDAO;
import gnete.card.dao.TransLimitTermDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.Terminal;
import gnete.card.entity.TransLimitTerm;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.state.TerminalState;
import gnete.card.entity.state.TransLimitTermStatus;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * POS终端交易控制
 * 
 * @author aps-lib
 * 
 */
public class TransLimitTermAction extends BaseAction {

	@Autowired
	private TransLimitTermDAO		transLimitTermDAO;
	@Autowired
	private BranchInfoDAO			branchInfoDAO;
	@Autowired
	private CardBinDAO				cardBinDAO;
	@Autowired
	private MerchInfoDAO			merchInfoDAO;
	@Autowired
	private TerminalDAO				terminalDAO;

	private TransLimitTerm			transLimitTerm;

	private Paginater				page;

	private List<CardTypeState>		statusList;

	/** 请求参数：交易允许控制map */
	protected Map<String, String>	tranEnableMap	= new HashMap<String, String>();

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.statusList = TransLimitTermStatus.getList();
		if (transLimitTerm != null) {
			params.put("transLimitTerm", transLimitTerm);
		}

		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else {
			throw new BizException("没有权限查询。");
		}

		this.page = this.transLimitTermDAO.findTransLimitTerm(params, getPageNumber(), getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		this.transLimitTerm = checkUserAndItem();
		return DETAIL;

	}

	public String showAdd() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "非营运中心、营运中心部门、分支机构不能操作。");
		this.checkEffectiveCertUser();
		
		return ADD;
	}

	public String add() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "非营运中心、营运中心部门、分支机构不能操作。");
		this.checkEffectiveCertUser();
		
		Assert.notBlank(transLimitTerm.getCardIssuer(), "选择的发卡机构不能为空！");
		if (isFenzhiRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isDirectManagedBy(transLimitTerm.getCardIssuer(),
					getLoginBranchCode()), "该用户不能操作该发卡机构的POS终端交易控制记录");
		}

		boolean isAllCardBins = this.isFormMapFieldTrue("allCardBins");
		boolean isAllMerchs = this.isFormMapFieldTrue("allMerchs");
		boolean isAllTerminals = this.isFormMapFieldTrue("allTerminals");

		Map<String, Object> params4CardBins = new HashMap<String, Object>();
		params4CardBins.put("cardIssuer", transLimitTerm.getCardIssuer());
		params4CardBins.put("status", CardBinState.NORMAL.getValue());
		if (!isAllCardBins) { // 非 卡bin全选
			params4CardBins.put("ids", this.getFormMapValue("cardBins").split(","));
		}
		List<CardBin> cardBins = cardBinDAO.findCardBin(params4CardBins);

		Map<String, Object> params4Merch = new HashMap<String, Object>();
		params4Merch.put("card_BranchNo", transLimitTerm.getCardIssuer());
		params4Merch.put("status", MerchState.NORMAL.getValue());
		if (!isAllMerchs) {// 非 商户全选
			params4Merch.put("merchIds", this.getFormMapValue("merchNos").split(","));
		}
		List<MerchInfo> merchInfos = merchInfoDAO.find(params4Merch);
		String defaultTranEnableStr = StringUtils.repeat("0", 200);
		List<TransLimitTerm> insrtList = new ArrayList<TransLimitTerm>();
		List<TransLimitTerm> updtList = new ArrayList<TransLimitTerm>();
		for (MerchInfo merch : merchInfos) {

			Map<String, Object> params4Term = new HashMap<String, Object>();
			params4Term.put("merchId", merch.getMerchId());
			params4Term.put("status", TerminalState.NORMAL.getValue());
			if (!isAllTerminals) { // 非 终端全选
				params4Term.put("termIds", this.getFormMapValue("termNos").split(","));
			}
			List<Terminal> terms = terminalDAO.find(params4Term);

			for (Terminal term : terms) {

				for (CardBin cardBin : cardBins) {

					TransLimitTerm tlt = new TransLimitTerm(transLimitTerm.getCardIssuer(), cardBin
							.getBinNo(), merch.getMerchId(), term.getTermId());

					TransLimitTerm org = (TransLimitTerm) this.transLimitTermDAO.findByPk(tlt);
					if (org == null) { // 不存在，则插入新记录
						tlt.setTranEnable(this.generateNewTranEnableStr(defaultTranEnableStr,
								this.tranEnableMap));
						tlt.setStatus(TransLimitTermStatus.NORMAL.getValue());
						tlt.setUpdateBy(this.getSessionUserCode());
						tlt.setUpdateTime(new Date());
						insrtList.add(tlt);
					} else {// 存在，则更新原始记录
						org.setTranEnable(this.generateNewTranEnableStr(org.getTranEnable(),
								this.tranEnableMap));
						org.setUpdateBy(this.getSessionUserCode());
						org.setUpdateTime(new Date());
						updtList.add(org);
					}
				}
			}
		}
		if (insrtList.size() > 0) {
			this.transLimitTermDAO.insertBatch(insrtList);
		}
		if (updtList.size() > 0) {
			this.transLimitTermDAO.updateBatch(updtList);
		}

		String msg = String.format("批量新增/更新 POS终端交易控制 成功：插入[%s]条，更新[%s]条记录", insrtList.size(), updtList
				.size());
		this.log(msg, UserLogType.ADD);
		addActionMessage("/transLimitTerm/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		transLimitTerm = checkUserAndItem();
		for (int i = 0; i < transLimitTerm.getTranEnable().length(); i++) {
			tranEnableMap.put(String.valueOf(i), String.valueOf(transLimitTerm.getTranEnable().charAt(i)));
		}
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkEffectiveCertUser();

		TransLimitTerm org = checkUserAndItem();
		if (this.isFormMapFieldTrue("setCancel")) {// 注销
			org.setStatus(TransLimitTermStatus.CANCEL.getValue());
		} else if (this.isFormMapFieldTrue("setNormal")) {// 生效
			org.setStatus(TransLimitTermStatus.NORMAL.getValue());
		} else { // 字段修改
			org.setTranEnable(this.generateNewTranEnableStr(org.getTranEnable(), this.tranEnableMap));
		}
		org.setUpdateBy(this.getSessionUserCode());
		org.setUpdateTime(new Date());
		transLimitTermDAO.update(org);

		String msg = "修改POS终端交易控制成功！";
		this.log(msg + org.toString(), UserLogType.UPDATE);
		addActionMessage("/transLimitTerm/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		TransLimitTerm org = checkUserAndItem();

		this.transLimitTermDAO.delete(org);

		String msg = "删除POS终端交易控制 成功！";
		this.log(msg + org.toString(), UserLogType.DELETE);
		this.addActionMessage("/transLimitTerm/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/** 检查用户角色与待操作的记录 */
	private TransLimitTerm checkUserAndItem() throws BizException {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined(), "非营运中心、营运中心部门、分支机构不能操作。");

		TransLimitTerm org = (TransLimitTerm) this.transLimitTermDAO.findByPk(transLimitTerm);
		Assert.notNull(org, "没有找到指定的POS终端交易控制记录");

		if (isFenzhiRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isDirectManagedBy(org.getCardIssuer(), getLoginBranchCode()),
					"该用户不能修改指定的POS终端交易控制记录");
		}
		return org;
	}

	private String generateNewTranEnableStr(String orgTranEnable, Map<String, String> tranEnableMap) {
		char[] arr = orgTranEnable.toCharArray();
		for (String key : tranEnableMap.keySet()) {
			String value = tranEnableMap.get(key);
			if (StringUtils.isNotBlank(value)) {
				arr[Integer.parseInt(key)] = value.charAt(0);
			}
		}
		return new String(arr);
	}
	
	// ------------------------------------- getter and setter followed---------------------

	public TransLimitTerm getTransLimitTerm() {
		return transLimitTerm;
	}

	public void setTransLimitTerm(TransLimitTerm transLimitTerm) {
		this.transLimitTerm = transLimitTerm;
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

	public Map<String, String> getTranEnableMap() {
		return tranEnableMap;
	}

	public void setTranEnableMap(Map<String, String> tranEnableMap) {
		this.tranEnableMap = tranEnableMap;
	}

}
