package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.dao.SaleProxyRtnDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.SaleProxyRtn;
import gnete.card.entity.SaleProxyRtnKey;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SaleProxyRtnFeeType;
import gnete.card.entity.type.SaleProxyRtnTransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleProxyRtnService;
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
 * 售卡代理商返利
 *
 */
public class SaleProxyRtnAction extends BaseAction {
	
	@Autowired
	private SaleProxyRtnDAO saleProxyRtnDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private SaleProxyRtnService saleProxyRtnService;
	
	private Paginater page;
	
	private SaleProxyRtn saleProxyRtn;
	private SaleProxyRtnKey key;
	private String branchCode;
	private Collection transTypeList;
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
	
	@Override
	public String execute() throws Exception {
		feeTypeList = SaleProxyRtnFeeType.getList();
		transTypeList = SaleProxyRtnTransType.getList();
		costCycleList = CostCycleType.getList();
		
		Map param = new HashMap();
		if(saleProxyRtn!=null){
			param.put("branchCode", saleProxyRtn.getBranchCode());
			param.put("branchName", MatchMode.ANYWHERE.toMatchString(saleProxyRtn.getBranchName()));
			param.put("proxyId", saleProxyRtn.getProxyId());
			param.put("proxyName", MatchMode.ANYWHERE.toMatchString(saleProxyRtn.getProxyName()));
			param.put("feeType", saleProxyRtn.getFeeType());
			param.put("transType", saleProxyRtn.getTransType());
			param.put("costCycle", saleProxyRtn.getCostCycle());
			param.put("cardBin", saleProxyRtn.getCardBin());
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
					ProxyType.SELL);
			param.put("branchCode", branchList.get(0).getBranchCode());
		} 
		// 售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			showProxy = true;
			showCard = true;
			proxyList = new ArrayList<BranchInfo>();
			proxyList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			branchList = this.getMyCardBranch();
			param.put("proxyId", this.getSessionUser().getBranchNo());
		} 
		else {
			throw new BizException("没有权限查询售卡代理商返利参数！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			param.put("cardIssuers", branchList);
		}
		
		page = this.saleProxyRtnDAO.findSaleProxyRtn(param, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置售卡代理商返利参数！");
		}
		feeTypeList = SaleProxyRtnFeeType.getList();
		transTypeList = SaleProxyRtnTransType.getList();
		costCycleList = CostCycleType.getMonth();
		proxyList = this.branchInfoDAO.findCardProxy(this.getSessionUser().getBranchNo(),
				ProxyType.SELL);
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());
		
		saleProxyRtn = new SaleProxyRtn();
		this.saleProxyRtn.setBranchCode(this.getSessionUser().getBranchNo());
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.saleProxyRtn.setBranchName(branchInfo.getBranchName());
		return ADD;
	}
	
	public String add() throws Exception {

		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.saleProxyRtn.getBranchCode());
		this.saleProxyRtn.setCurCode(branchInfo.getCurCode());
		
		// 如果是分段的
		List<SaleProxyRtn> saleFee = new ArrayList<SaleProxyRtn>();
		if (saleProxyRtn.getFeeType().equals(SaleProxyRtnFeeType.TRADEMONEY.getValue())) {
//				|| saleProxyRtn.getFeeType().equals(SaleProxyRtnFeeType.SSUM.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				SaleProxyRtn fee = new SaleProxyRtn();
				fee = new SaleProxyRtn();
				formToBo(fee, this.saleProxyRtn);
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				saleFee.add(fee);
			}
		} else {
			SaleProxyRtn fee = new SaleProxyRtn();
			fee = new SaleProxyRtn();
			formToBo(fee, this.saleProxyRtn);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			saleFee.add(fee);
		}
		
		saleProxyRtnService.addSaleProxyRtn(saleFee, this.getSessionUserCode());
		String msg = "添加售卡代理商["+ saleProxyRtn.getBranchCode() +"]返利参数成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/saleProxyRtn/list.do", msg);
		return SUCCESS;
	}
	
	private void formToBo(SaleProxyRtn dest, SaleProxyRtn src){
		dest.setBranchCode(src.getBranchCode());
		dest.setTransType(src.getTransType());
		dest.setCardBin(src.getCardBin());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
		dest.setCurCode(src.getCurCode());
		dest.setProxyId(src.getProxyId());
	}
	
	public String showModify() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置售卡代理商返利参数！");
		}
		saleProxyRtn = (SaleProxyRtn)this.saleProxyRtnDAO.findByPk(key);
		saleProxyRtn.setBranchName(((BranchInfo)branchInfoDAO.findByPk(saleProxyRtn.getBranchCode())).getBranchName());
		saleProxyRtn.setProxyName(((BranchInfo)branchInfoDAO.findByPk(saleProxyRtn.getProxyId())).getBranchName());
		
		this.updateUlmoney = saleProxyRtn.getUlMoney().toString();
		this.feeRateComma = saleProxyRtn.getFeeRate().toString();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			saleProxyRtn.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		saleProxyRtn.setUpdateBy(getSessionUserCode());
		saleProxyRtn.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		saleProxyRtnService.modifySaleProxyRtn(saleProxyRtn);
		String msg = "修改售卡代理商返利参数["+ saleProxyRtn.getBranchCode() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/saleProxyRtn/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置售卡代理商返利参数！");
		}
		saleProxyRtn.setBranchCode(branchCode);
		saleProxyRtn.setProxyId(request.getParameter("proxyId"));
		saleProxyRtn.setTransType(request.getParameter("transType"));
		saleProxyRtn.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		saleProxyRtn.setCardBin(request.getParameter("cardBin"));
		saleProxyRtn.setUpdateBy(getSessionUserCode());
		saleProxyRtnService.deleteSaleProxyRtn(saleProxyRtn);
		String msg = "删除售卡代理商返利参数["+ saleProxyRtn.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/saleProxyRtn/list.do", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public SaleProxyRtn getSaleProxyRtn() {
		return saleProxyRtn;
	}

	public void setSaleProxyRtn(SaleProxyRtn saleProxyRtn) {
		this.saleProxyRtn = saleProxyRtn;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public SaleProxyRtnKey getKey() {
		return key;
	}

	public void setKey(SaleProxyRtnKey key) {
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

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}
	
}
