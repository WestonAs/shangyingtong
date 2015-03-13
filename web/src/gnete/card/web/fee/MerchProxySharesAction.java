package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.MerchProxySharesDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchProxyShares;
import gnete.card.entity.MerchProxySharesKey;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.MerchProxySharesFeeType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchProxySharesService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户代理商分润
 *
 */
public class MerchProxySharesAction extends BaseAction {
	
	@Autowired
	private MerchProxySharesDAO merchProxySharesDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchProxySharesService merchProxySharesService;
	
	private Paginater page;
	
	private MerchProxyShares merchProxyShares;
	private MerchProxySharesKey key;
	private String branchCode;
	private Collection costCycleList;
	
	private boolean showCard = false;
	private boolean showProxy = false;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	
	private List currCodeList;
	private List<BranchInfo> branchList;
	private List<BranchInfo> proxyList;
	private List<Type> feeTypeList;

	@Autowired
	private CurrCodeDAO currCodeDAO;
	private String updateUlmoney;
	
	private String merchs;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = MerchProxySharesFeeType.getFeeAndTradeMoneyType();
		costCycleList = CostCycleType.getList();
		
		Map param = new HashMap();
		if(merchProxyShares!=null) {
			param.put("branchCode", merchProxyShares.getBranchCode());
			param.put("branchName", MatchMode.ANYWHERE.toMatchString(merchProxyShares.getBranchName()));
			param.put("proxyId", merchProxyShares.getProxyId());
			param.put("proxyName", MatchMode.ANYWHERE.toMatchString(merchProxyShares.getProxyName()));
			param.put("merchId", merchProxyShares.getMerchId());
			param.put("merchName", MatchMode.ANYWHERE.toMatchString(merchProxyShares.getMerchName()));
			param.put("feeType", merchProxyShares.getFeeType());
			param.put("costCycle", merchProxyShares.getCostCycle());
		}
		
		// 营运中心、中心部门
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(branchList)){
				param.put("cardIssuers", " ");
			}
		}
		// 发卡机构、机构部门
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			showCard = true;
			showProxy = true;
			branchList = this.getMyCardBranch();
			proxyList = this.branchInfoDAO.findCardProxy(this.getSessionUser().getBranchNo(),
					ProxyType.MERCHANT);
			param.put("branchCode", this.getSessionUser().getBranchNo());
		} 
		// 商户代理
		else if (RoleType.CARD_MERCHANT.getValue().equals(this.getLoginRoleType())){
			showProxy = true;
			showCard = true;
			proxyList = new ArrayList<BranchInfo>();
			proxyList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getRole().getBranchNo()));
			branchList = this.getMyCardBranch();
			param.put("proxyId", this.getSessionUser().getRole().getBranchNo());
		} else {
			throw new BizException("没有权限查询商户代理商分润参数！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			param.put("cardIssuers", branchList);
		}
		
		page = this.merchProxySharesDAO.findMerchProxyShares(param, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户代理商分润参数！");
		}
		feeTypeList = MerchProxySharesFeeType.getFeeAndTradeMoneyType();
		costCycleList = CostCycleType.getMonth();
		proxyList = this.branchInfoDAO.findCardProxy(this.getSessionUser().getBranchNo(),
				ProxyType.MERCHANT);
		
		merchProxyShares = new MerchProxyShares();
		this.merchProxyShares.setBranchCode(this.getSessionUser().getBranchNo());
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.merchProxyShares.setBranchName(branchInfo.getBranchName());
		
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		return ADD;
	}
	
	public String add() throws Exception {
		// 如果是分段的
		List<MerchProxyShares> merchProxyList = new ArrayList<MerchProxyShares>();
		if (merchProxyShares.getFeeType().equals(MerchProxySharesFeeType.TRADEMONEY.getValue())
				|| merchProxyShares.getFeeType().equals(MerchProxySharesFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				// 选择了多个商户的情况
				if (StringUtils.isNotEmpty(merchs)) {
					String[] merchArray = merchs.split(",");
					for (int j = 0; j < merchArray.length; j++) {
						MerchProxyShares fee = new MerchProxyShares();
						fee = new MerchProxyShares();
						fee.setMerchId(merchArray[j]);
						fee.setBranchCode(this.merchProxyShares.getBranchCode());
						fee.setCostCycle(this.merchProxyShares.getCostCycle());
						fee.setFeeType(this.merchProxyShares.getFeeType());
						fee.setCurCode(this.merchProxyShares.getCurCode());
						fee.setProxyId(this.merchProxyShares.getProxyId());
						fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
						fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
						
						merchProxyList.add(fee);
					}
				}
				// 没有选择商户
				else {
					MerchProxyShares fee = new MerchProxyShares();
					fee = new MerchProxyShares();
					fee.setMerchId("0");
					fee.setBranchCode(this.merchProxyShares.getBranchCode());
					fee.setCostCycle(this.merchProxyShares.getCostCycle());
					fee.setFeeType(this.merchProxyShares.getFeeType());
					fee.setCurCode(this.merchProxyShares.getCurCode());
					fee.setProxyId(this.merchProxyShares.getProxyId());
					fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
					fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
					
					merchProxyList.add(fee);
				}
			}
		} else {
			// 选择了商户
			if (StringUtils.isNotEmpty(merchs)) {
				String[] merchArray = merchs.split(",");
				for (int j = 0; j < merchArray.length; j++) {
					MerchProxyShares fee = new MerchProxyShares();
					fee = new MerchProxyShares();
					fee.setMerchId(merchArray[j]);
					fee.setBranchCode(this.merchProxyShares.getBranchCode());
					fee.setCostCycle(this.merchProxyShares.getCostCycle());
					fee.setFeeType(this.merchProxyShares.getFeeType());
					fee.setCurCode(this.merchProxyShares.getCurCode());
					fee.setProxyId(this.merchProxyShares.getProxyId());
					fee.setUlMoney(Constants.FEE_MAXACCOUNT);
					fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
					merchProxyList.add(fee);
				}
			}
			// 没有选择商户
			else {
				this.merchProxyShares.setMerchId("0");
				this.merchProxyShares.setUlMoney(Constants.FEE_MAXACCOUNT);
				this.merchProxyShares.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
				merchProxyList.add(this.merchProxyShares);
			}
		}
		merchProxySharesService.addMerchProxyShares(merchProxyList.toArray(new MerchProxyShares[merchProxyList.size()]), 
				this.getSessionUserCode());
		String msg = "添加商户代理商分润参数["+ merchProxyShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/merchProxyShares/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户代理商分润参数！");
		}
		merchProxyShares = (MerchProxyShares)this.merchProxySharesDAO.findByPk(key);
		/*
		merchProxyShares.setBranchName(((BranchInfo)branchInfoDAO.findByPk(merchProxyShares.getBranchCode())).getBranchName());
		merchProxyShares.setProxyName(((BranchInfo)branchInfoDAO.findByPk(merchProxyShares.getProxyId())).getBranchName());
		merchProxyShares.setMerchName(((MerchInfo)merchInfoDAO.findByPk(merchProxyShares.getMerchId())).getMerchName());
		 */
		this.updateUlmoney = merchProxyShares.getUlMoney().toString();
		this.feeRateComma = merchProxyShares.getFeeRate().toString();
		
		return MODIFY;
	}
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			merchProxyShares.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		merchProxyShares.setUpdateBy(getSessionUserCode());
		merchProxyShares.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		merchProxySharesService.modifyMerchProxyShares(merchProxyShares);
		String msg = "修改商户代理商分润参数["+ merchProxyShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/merchProxyShares/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户代理商分润参数！");
		}
		merchProxyShares.setBranchCode(branchCode);
		merchProxyShares.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		merchProxyShares.setMerchId(request.getParameter("merchId"));
		merchProxyShares.setProxyId(request.getParameter("proxyId"));
		merchProxyShares.setUpdateBy(getSessionUserCode());
		merchProxySharesService.deleteMerchProxyShares(merchProxyShares);
		String msg = "删除商户代理商分润参数["+ merchProxyShares.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/merchProxyShares/list.do", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public MerchProxyShares getMerchProxyShares() {
		return merchProxyShares;
	}

	public void setMerchProxyShares(MerchProxyShares merchProxyShares) {
		this.merchProxyShares = merchProxyShares;
	}

	public MerchProxySharesKey getKey() {
		return key;
	}

	public void setKey(MerchProxySharesKey key) {
		this.key = key;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowProxy() {
		return showProxy;
	}

	public void setShowProxy(boolean showProxy) {
		this.showProxy = showProxy;
	}

	public String[] getUlimit() {
		return ulimit;
	}

	public void setUlimit(String[] ulimit) {
		this.ulimit = ulimit;
	}

	public String[] getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String[] feeRate) {
		this.feeRate = feeRate;
	}

	public List getCurrCodeList() {
		return currCodeList;
	}

	public void setCurrCodeList(List currCodeList) {
		this.currCodeList = currCodeList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<BranchInfo> getProxyList() {
		return proxyList;
	}

	public void setProxyList(List<BranchInfo> proxyList) {
		this.proxyList = proxyList;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public String getMerchs() {
		return merchs;
	}

	public void setMerchs(String merchs) {
		this.merchs = merchs;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}
}
