package gnete.card.web.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.AmountUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ReleaseCardFeeDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ReleaseCardFee;
import gnete.card.entity.ReleaseCardFeeKey;
import gnete.card.entity.flag.GroupFlag;
import gnete.card.entity.type.AmtCntType;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SetModeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.ReleaseCardFeeService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * 发卡机构平台运营手续费
 * @author aps-lib
 * 
 */
public class ChlReleaseCardFeeAction extends BaseAction{

	@Autowired
	private ReleaseCardFeeDAO releaseCardFeeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private ReleaseCardFeeService releaseCardFeeService;
	
	private Paginater page;
	
	private ReleaseCardFee releaseCardFee;
	private Collection costCycleList;
	private Collection feeTypeList;
	private Collection transTypeList;
	
	private List<BranchInfo> branchList;
	private List<BranchInfo> chlList;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	
	private boolean showCenter = false;
	private boolean showCard = false;
	
	private String branchCode;
	private String updateUlmoney;
	private ReleaseCardFeeKey key;
	private Collection branchTypeList;

	@Override
	public String execute() throws Exception {
		costCycleList = CostCycleType.getList();
		feeTypeList = ChlFeeType.getList();
		transTypeList = ChlFeeContentType.getList();
		
		Map param = new HashMap();
		if(releaseCardFee!=null){
			param.put("branchName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getBranchName()));
			param.put("chlName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getChlName()));
			param.put("merchName", MatchMode.ANYWHERE.toMatchString(releaseCardFee.getMerchName()));
			param.put("feeType", releaseCardFee.getFeeType());
			param.put("transType", releaseCardFee.getTransType());
			param.put("costCycle", releaseCardFee.getCostCycle());
		}
		if(this.getLoginRoleType().equals(RoleType.CENTER.getValue())||
				this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			this.showCenter = true;
		}
		else if(this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			this.chlList = new ArrayList<BranchInfo>(); 
			this.chlList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			param.put("chlCode", this.getSessionUser().getBranchNo());
		}
		else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())||
				this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())){
			this.showCard = true;
			this.chlList = new ArrayList<BranchInfo>(); 
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
			this.chlList.add((BranchInfo) this.branchInfoDAO.findByPk(branchInfo.getParent()));
			this.branchList = new ArrayList<BranchInfo>();
			this.branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			param.put("branchCode", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询分支机构与发卡机构手续费参数！");
		}
		param.put("feeMode", SetModeType.COST.getValue());
		page = this.releaseCardFeeDAO.findReleaseCardFee(param, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("没有权限设置发卡机构手续费参数！");
		}
		//costCycleList = CostCycleType.getYear();
		feeTypeList = ChlFeeType.getList();
		transTypeList = ChlFeeContentType.getList();
		this.branchTypeList = GroupFlag.getAll();
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		// 如果是分段的
		List<ReleaseCardFee> feeList = new ArrayList<ReleaseCardFee>();
		if (this.releaseCardFee.getFeeType().equals(ChlFeeType.TRADE_MONEY.getValue())
				|| this.releaseCardFee.getFeeType().equals(ChlFeeType.TRADE_RATE.getValue())
				|| this.releaseCardFee.getFeeType().equals(ChlFeeType.ACCUMULATIVE_RATE.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				ReleaseCardFee fee = new ReleaseCardFee();
				fee.setChlCode(this.getSessionUser().getBranchNo());
				fee.setBranchCode(this.releaseCardFee.getBranchCode());
				fee.setTransType(this.releaseCardFee.getTransType());
				fee.setGroupFlag(this.releaseCardFee.getGroupFlag());
				fee.setMerchId("0");
				fee.setCardBin("*");
				fee.setFeeMode(SetModeType.COST.getValue());
				fee.setFeeType(this.releaseCardFee.getFeeType());
				fee.setCostCycle(this.releaseCardFee.getCostCycle());
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				// 成本模式为 预付资金、 通用积分和专属积分、赠券时，按金额计费
				if(ChlFeeContentType.PREPAY.getValue().equals(this.releaseCardFee.getTransType())||
						ChlFeeContentType.COUPON.getValue().equals(this.releaseCardFee.getTransType())||
						ChlFeeContentType.POINT.getValue().equals(this.releaseCardFee.getTransType())){
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
				}
				// 按笔数收费
				else{
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
				}
				feeList.add(fee);
			}
		} else {
			this.releaseCardFee.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			//this.releaseCardFee.setBranchCode(this.getSessionUser().getBranchNo());
			this.releaseCardFee.setChlCode(this.getSessionUser().getBranchNo());
			this.releaseCardFee.setMerchId("0");
			this.releaseCardFee.setCardBin("*");
			this.releaseCardFee.setFeeMode(SetModeType.COST.getValue());
			this.releaseCardFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			// 成本模式为 预付资金、 通用积分和专属积分、赠券时，按金额计费
			if(ChlFeeContentType.PREPAY.getValue().equals(this.releaseCardFee.getTransType())||
					ChlFeeContentType.COUPON.getValue().equals(this.releaseCardFee.getTransType())){
				this.releaseCardFee.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
			}
			// 按笔数收费
			else{
				this.releaseCardFee.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
			}
			feeList.add(this.releaseCardFee);
		}
		
		this.releaseCardFeeService.addChlReleaseCardFee(feeList.toArray(new ReleaseCardFee[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加分支机构与发卡机构["+ this.releaseCardFee.getMerchId() +"]运营手续费成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/chlReleaseCardFee/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("没有权限设置发卡机构手续费参数！");
		}
		releaseCardFee = (ReleaseCardFee)this.releaseCardFeeDAO.findByPk(key);
		//releaseCardFee.setMerchName(((BranchInfo)this.branchInfoDAO.findByPk(releaseCardFee.getMerchId())).getBranchName());
		this.feeRateComma = this.releaseCardFee.getFeeRate().toString();
		
		this.updateUlmoney = releaseCardFee.getUlMoney().toString();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			releaseCardFee.setNewUlMoney(new BigDecimal(updateUlmoney));
		}
		this.releaseCardFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		//releaseCardFeeService.modifyReleaseCardFee(releaseCardFee, this.getSessionUserCode());
		String msg = "修改分支机构与发卡机构手续费参数["+ releaseCardFee.getMerchId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/chlReleaseCardFee/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			throw new BizException("没有权限设置发卡机构手续费参数！");
		}
		releaseCardFee.setBranchCode(branchCode);
		releaseCardFee.setCardBin(request.getParameter("cardBin"));
		releaseCardFee.setTransType(request.getParameter("transType"));
		releaseCardFee.setMerchId(request.getParameter("merchId"));
		releaseCardFee.setUlMoney(new BigDecimal(request.getParameter("ulMoney")));
		releaseCardFee.setUpdateBy(getSessionUserCode());
		releaseCardFeeService.deleteReleaseCardFee(releaseCardFee, this.getSessionUserCode());
		String msg = "删除分支机构与发卡机构手续费参数["+ releaseCardFee.getBranchCode() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/chlReleaseCardFee/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 根据登录机构是否为分支机构，得到成本模式
	 * @return
	 */
	public String getSetMode() {
		if(super.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			return SetModeType.COST.getValue();
		} else {
			return "";
		}
	}
	
	/**
	 * 登录机构能够管理的发卡机构
	 * @return
	 */
	public String getParent() {
		return super.getSessionUser().getBranchNo();
	}

	public ReleaseCardFeeService getReleaseCardFeeService() {
		return releaseCardFeeService;
	}

	public void setReleaseCardFeeService(ReleaseCardFeeService releaseCardFeeService) {
		this.releaseCardFeeService = releaseCardFeeService;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public ReleaseCardFee getReleaseCardFee() {
		return releaseCardFee;
	}

	public void setReleaseCardFee(ReleaseCardFee releaseCardFee) {
		this.releaseCardFee = releaseCardFee;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public Collection getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(Collection feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public ReleaseCardFeeKey getKey() {
		return key;
	}

	public void setKey(ReleaseCardFeeKey key) {
		this.key = key;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public Collection getBranchTypeList() {
		return branchTypeList;
	}

	public void setBranchTypeList(Collection branchTypeList) {
		this.branchTypeList = branchTypeList;
	}

	public List<BranchInfo> getChlList() {
		return chlList;
	}

	public void setChlList(List<BranchInfo> chlList) {
		this.chlList = chlList;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}

}
