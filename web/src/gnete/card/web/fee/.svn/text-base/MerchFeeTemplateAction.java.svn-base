package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchFeeTemplateDAO;
import gnete.card.dao.MerchFeeTemplateDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchFeeTemplate;
import gnete.card.entity.MerchFeeTemplateDetail;
import gnete.card.entity.MerchFeeTemplateDetailKey;
import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.CardMerchFeeTransType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardMerchFeeService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户手续费模板设置
 * @author aps-lib
 * since 20110919
 */
public class MerchFeeTemplateAction extends BaseAction {
	
	@Autowired
	private MerchFeeTemplateDAO merchFeeTemplateDAO;
	@Autowired
	private MerchFeeTemplateDetailDAO merchFeeTemplateDetailDAO;
	@Autowired
	private CardMerchFeeService cardMerchFeeService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private Paginater page;
	private MerchFeeTemplate merchFeeTemplate;
	private MerchFeeTemplateDetail merchFeeTemplateDetail;
	private List<Type> feeTypeList;
	private Collection transTypeList;
	private Collection costCycleList;
	private String branchCode;
	private String templateId;
	private String cardBin;
	private String transType;
	private BigDecimal ulMoney;
	
	private boolean showCard = false;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	private String maxAmtComma;
	private String minAmtComma;
	
	private List currCodeList;
	private List<BranchInfo> branchList;
	private List selectMerchTypeList;

	private String updateUlmoney;
	
	@Override
	public String execute() throws Exception {
		feeTypeList = CardMerchFeeFeeType.getList();
		transTypeList = CardMerchFeeTransType.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(merchFeeTemplateDetail!=null){
			params.put("branchCode", merchFeeTemplateDetail.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(merchFeeTemplateDetail.getBranchName()));
			params.put("templateName", MatchMode.ANYWHERE.toMatchString(merchFeeTemplateDetail.getTemplateName()));
			params.put("feeType", merchFeeTemplateDetail.getFeeType());
			params.put("transType", merchFeeTemplateDetail.getTransType());
			params.put("cardBin", merchFeeTemplateDetail.getCardBin());
		}
		
		// 营运中心、中心部门
		if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				 || RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCard = true;
		}
		// 分支机构, 可以查看其管理的发卡机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			params.put("parent", this.getSessionUser().getBranchNo());
			this.showCard = true;
		}
 		// 发卡机构、机构部门	
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
			this.showCard = false;
			this.branchList = new ArrayList<BranchInfo>();
			this.branchList.add((BranchInfo)branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
		} 
		else {
			throw new BizException("没有权限查询商户手续费模板！");
		}
		
		page = this.merchFeeTemplateDetailDAO.findMerchFeeTemplateDetail(params, this.getPageNumber(), this.getPageSize() );
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费模板！");
		}
		
		initPage();
		
		merchFeeTemplateDetail = new MerchFeeTemplateDetail();
		this.merchFeeTemplateDetail.setBranchCode(this.getSessionUser().getBranchNo());
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.merchFeeTemplateDetail.setBranchName(branchInfo.getBranchName());
		
		return ADD;
	}
	
	public String add() throws Exception {

		// 如果卡BIN为空，则填写'*'表示通用
		if(StringUtils.isEmpty(this.merchFeeTemplateDetail.getCardBin())){
			this.merchFeeTemplateDetail.setCardBin("*");
		}
		
		// 如果是分段的
		List<MerchFeeTemplateDetail> merchFeeList = new ArrayList<MerchFeeTemplateDetail>();
		
		if (merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.TRADEMONEY.getValue())
				|| merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.SSUM.getValue())) {
			for (int i = 0; i < ulimit.length; i++) {
				MerchFeeTemplateDetail fee = new MerchFeeTemplateDetail();
				formToBo(fee, this.merchFeeTemplateDetail);
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				fee.setMinAmt(new BigDecimal(0.0));
				fee.setMaxAmt(new BigDecimal(0.0));
				merchFeeList.add(fee);
			}
			
		} else { // 按交易笔数, 金额固定比例
			MerchFeeTemplateDetail fee = new MerchFeeTemplateDetail();
			formToBo(fee, this.merchFeeTemplateDetail);
			fee.setUlMoney(Constants.FEE_MAXACCOUNT);
			fee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			if (!merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.FEE.getValue())){  // 非金额固定比例
				fee.setMinAmt(new BigDecimal(0.0));
				fee.setMaxAmt(new BigDecimal(0.0));
			} 
			else {
				fee.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
				fee.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
			}
			
			merchFeeList.add(fee);
		}
		
		this.merchFeeTemplate.setBranchCode(this.merchFeeTemplateDetail.getBranchCode());
		
		cardMerchFeeService.addMerchFeeTemplate(this.merchFeeTemplate, merchFeeList.toArray(new MerchFeeTemplateDetail[merchFeeList.size()]), 
													this.getSessionUserCode());
		
		String msg = "添加商户手续费模板["+ merchFeeTemplate.getTemplateName() +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/merchFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	private void formToBo(MerchFeeTemplateDetail dest, MerchFeeTemplateDetail src){
		dest.setTransType(src.getTransType());
		dest.setCardBin(src.getCardBin());
		dest.setCostCycle(src.getCostCycle());
		dest.setFeeType(src.getFeeType());
	}
	
	public String showModify() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费模板！");
		}
		
		MerchFeeTemplate key = new MerchFeeTemplate();
		key.setBranchCode(merchFeeTemplateDetail.getBranchCode());
		key.setTemplateId(merchFeeTemplateDetail.getTemplateId());
		
		this.merchFeeTemplate = (MerchFeeTemplate)merchFeeTemplateDAO.findByPk(key);
		this.merchFeeTemplate.setBranchName(((BranchInfo)branchInfoDAO.findByPk(merchFeeTemplate.getBranchCode())).getBranchName());
		
		MerchFeeTemplateDetailKey detailKey = new MerchFeeTemplateDetailKey();
		detailKey.setCardBin(merchFeeTemplateDetail.getCardBin());
		detailKey.setTemplateId(merchFeeTemplateDetail.getTemplateId());
		detailKey.setTransType(merchFeeTemplateDetail.getTransType());
		detailKey.setUlMoney(merchFeeTemplateDetail.getUlMoney());
		
		this.merchFeeTemplateDetail = (MerchFeeTemplateDetail) this.merchFeeTemplateDetailDAO.findByPk(detailKey);
		
		this.updateUlmoney = this.merchFeeTemplateDetail.getUlMoney().toString();
		this.feeRateComma = this.merchFeeTemplateDetail.getFeeRate().toString();
		
		if(merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.FEE.getValue())){ // 金額固定比例
			this.maxAmtComma = this.merchFeeTemplateDetail.getMaxAmt().toString();
			this.minAmtComma = this.merchFeeTemplateDetail.getMinAmt().toString();
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		//cardMerchFee.setUpdateBy(this.getSessionUserCode());
		
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			merchFeeTemplateDetail.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		merchFeeTemplateDetail.setTemplateId(this.merchFeeTemplate.getTemplateId());
		merchFeeTemplateDetail.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		
		if (merchFeeTemplateDetail.getFeeType().equals(CardMerchFeeFeeType.FEE.getValue())){ // 金額固定比例
			merchFeeTemplateDetail.setMinAmt(AmountUtils.parseBigDecimal(this.getMinAmtComma()));
			merchFeeTemplateDetail.setMaxAmt(AmountUtils.parseBigDecimal(this.getMaxAmtComma()));
		} else { // 其他将单笔最高手续费和最低手续费置零
			merchFeeTemplateDetail.setMinAmt(new BigDecimal(0.0));
			merchFeeTemplateDetail.setMaxAmt(new BigDecimal(0.0));
		}
		
		this.cardMerchFeeService.modifyMerchFeeTemplateDetail(merchFeeTemplateDetail, this.getSessionUserCode());
		this.cardMerchFeeService.modifyMerchFeeTemplate(merchFeeTemplate, this.getSessionUserCode());
		
		String msg = "修改商户手续费模板["+ merchFeeTemplate.getTemplateId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/merchFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))){
			throw new BizException("没有权限设置商户手续费模板！");
		}
		
		MerchFeeTemplateDetailKey detailKey = new MerchFeeTemplateDetailKey();
		detailKey.setCardBin(this.getCardBin());
		detailKey.setTemplateId(new Long(this.getTemplateId()));
		detailKey.setTransType(this.getTransType());
		detailKey.setUlMoney(this.getUlMoney());
		
		this.merchFeeTemplateDetail = (MerchFeeTemplateDetail) this.merchFeeTemplateDetailDAO.findByPk(detailKey);
		this.merchFeeTemplateDetail.setBranchCode(this.getBranchCode());
		
		this.cardMerchFeeService.deleteMerchFeeTemplateDetail(merchFeeTemplateDetail);
		String msg = "删除商户手续费模板["+ merchFeeTemplateDetail.getTemplateId() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/merchFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		transTypeList = CardMerchFeeTransType.getList(); // 交易类型
		costCycleList = CostCycleType.getDayMonth(); // 计费周期 
		feeTypeList = CardMerchFeeFeeType.getList(); // 计费方式
	}
	
	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getCostCycleList() {
		return costCycleList;
	}

	public void setCostCycleList(Collection costCycleList) {
		this.costCycleList = costCycleList;
	}

	public List<Type> getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(List<Type> feeTypeList) {
		this.feeTypeList = feeTypeList;
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

	public List getSelectMerchTypeList() {
		return selectMerchTypeList;
	}

	public void setSelectMerchTypeList(List selectMerchTypeList) {
		this.selectMerchTypeList = selectMerchTypeList;
	}

	public String getUpdateUlmoney() {
		return updateUlmoney;
	}

	public void setUpdateUlmoney(String updateUlmoney) {
		this.updateUlmoney = updateUlmoney;
	}

	public String getFeeRateComma() {
		return feeRateComma;
	}

	public void setFeeRateComma(String feeRateComma) {
		this.feeRateComma = feeRateComma;
	}

	public String getMaxAmtComma() {
		return maxAmtComma;
	}

	public void setMaxAmtComma(String maxAmtComma) {
		this.maxAmtComma = maxAmtComma;
	}

	public String getMinAmtComma() {
		return minAmtComma;
	}

	public void setMinAmtComma(String minAmtComma) {
		this.minAmtComma = minAmtComma;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public MerchFeeTemplate getMerchFeeTemplate() {
		return merchFeeTemplate;
	}

	public void setMerchFeeTemplate(MerchFeeTemplate merchFeeTemplate) {
		this.merchFeeTemplate = merchFeeTemplate;
	}

	public MerchFeeTemplateDetail getMerchFeeTemplateDetail() {
		return merchFeeTemplateDetail;
	}

	public void setMerchFeeTemplateDetail(
			MerchFeeTemplateDetail merchFeeTemplateDetail) {
		this.merchFeeTemplateDetail = merchFeeTemplateDetail;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BigDecimal getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(BigDecimal ulMoney) {
		this.ulMoney = ulMoney;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}
