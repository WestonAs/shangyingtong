package gnete.card.web.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ChlFeeTemplateDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ChlFeeTemplate;
import gnete.card.entity.ChlFeeTemplateKey;
import gnete.card.entity.type.AmtCntType;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchSharesService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;
import org.apache.commons.lang.StringUtils;

/**
 * 分支机构平台运营手续费模板
 *
 */
public class ChlFeeTemplateAction extends BaseAction{

	@Autowired 
	private ChlFeeTemplateDAO chlFeeTemplateDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchSharesService branchSharesService;
	
	private ChlFeeTemplate chlFeeTemplate;
	private Paginater page;
	private Collection costCycleList;
	private List<Type> feeTypeList;
	private List<Type> feeContentList;
	private List<BranchInfo> branchList;
	
	private Long templateId;
	private String feeContent;
	private String ulMoney;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	
	private boolean showProxy = false;
	private String updateUlmoney;
	
	@Override
	public String execute() throws Exception {
		initPage();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(chlFeeTemplate!=null){
			params.put("templateId",chlFeeTemplate.getTemplateId());
			params.put("feeType", chlFeeTemplate.getFeeType());
			params.put("costCycle", chlFeeTemplate.getCostCycle());
			params.put("feeContent", chlFeeTemplate.getFeeContent());
			params.put("templateName", MatchMode.ANYWHERE.toMatchString(chlFeeTemplate.getTemplateName()));
		}
		
		if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("templateId", this.getSessionUser().getBranchNo());
			this.showProxy = true;
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			this.showProxy = false;
		} else {
			throw new BizException("没有权限查询分支机构平台运营手续费模板！");
		}
		this.page = chlFeeTemplateDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 取得明细
	public String detail() throws Exception {
		
		ChlFeeTemplateKey key = new ChlFeeTemplateKey();
		key.setTemplateId(this.chlFeeTemplate.getTemplateId());
		key.setFeeContent(this.chlFeeTemplate.getFeeContent());
		key.setUlMoney(this.chlFeeTemplate.getUlMoney());
		this.chlFeeTemplate = (ChlFeeTemplate) this.chlFeeTemplateDAO.findByPk(key);
		
		this.log("查询分支机构["+this.chlFeeTemplate.getTemplateId()+"]的平台运营手续费模板明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue());
		initPage();
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		// 如果是分段的
		List<ChlFeeTemplate> feeList = new ArrayList<ChlFeeTemplate>();
		Long templateId = this.getSeq();
		if (this.chlFeeTemplate.getFeeType().equals(ChlFeeType.TRADE_MONEY.getValue())
				|| this.chlFeeTemplate.getFeeType().equals(ChlFeeType.TRADE_RATE.getValue())
				|| this.chlFeeTemplate.getFeeType().equals(ChlFeeType.ACCUMULATIVE_RATE.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				ChlFeeTemplate fee = new ChlFeeTemplate();
				fee.setTemplateId(templateId);
				fee.setTemplateName(chlFeeTemplate.getTemplateName());
				fee.setFeeContent(this.chlFeeTemplate.getFeeContent());
				fee.setFeeType(this.chlFeeTemplate.getFeeType());
				fee.setCostCycle(this.chlFeeTemplate.getCostCycle());
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				// 成本模式为 预付资金、 通用积分和专属积分、赠券、次卡时，按金额计费
				if(ChlFeeContentType.PREPAY.getValue().equals(this.chlFeeTemplate.getFeeContent())||
						ChlFeeContentType.COUPON.getValue().equals(this.chlFeeTemplate.getFeeContent())||
						ChlFeeContentType.POINT.getValue().equals(this.chlFeeTemplate.getFeeContent())||
						ChlFeeContentType.TIME_CARD.getValue().equals(this.chlFeeTemplate.getFeeContent())){
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
				}
				// 按笔数收费
				else{
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
				}
				feeList.add(fee);
			}
		} else {
			this.chlFeeTemplate.setTemplateId(templateId);
			this.chlFeeTemplate.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			this.chlFeeTemplate.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			// 成本模式为 预付资金、 通用积分和专属积分、赠券、次卡时，按金额计费
			if(ChlFeeContentType.PREPAY.getValue().equals(this.chlFeeTemplate.getFeeContent())||
					ChlFeeContentType.COUPON.getValue().equals(this.chlFeeTemplate.getFeeContent())||
					ChlFeeContentType.POINT.getValue().equals(this.chlFeeTemplate.getFeeContent()) ||
					ChlFeeContentType.TIME_CARD.getValue().equals(this.chlFeeTemplate.getFeeContent())){
				this.chlFeeTemplate.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
			}
			// 按笔数收费
			else{
				this.chlFeeTemplate.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
			}
			feeList.add(this.chlFeeTemplate);
		}
		
		this.branchSharesService.addChlFeeTemplate(feeList.toArray(new ChlFeeTemplate[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加运营中心与分支机构平台运营手续费模板["+ templateId +"]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/chlFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		ChlFeeTemplate key = new ChlFeeTemplate();
		key.setTemplateId(this.chlFeeTemplate.getTemplateId());
		key.setFeeContent(this.chlFeeTemplate.getFeeContent());
		key.setUlMoney(AmountUtil.format(this.chlFeeTemplate.getUlMoney()));
		this.chlFeeTemplate = (ChlFeeTemplate)this.chlFeeTemplateDAO.findByPk(key);
		this.feeRateComma = this.chlFeeTemplate.getFeeRate().toString();

		this.updateUlmoney = chlFeeTemplate.getUlMoney().toString();
		if(ChlFeeType.TRADE_MONEY.getValue().equals(this.chlFeeTemplate.getFeeType())||
				ChlFeeType.TRADE_RATE.getValue().equals(this.chlFeeTemplate.getFeeType())){
			this.feeTypeList = ChlFeeType.getTradeChlFee();
		}
		else{
			this.feeTypeList = new ArrayList<Type>();
			this.feeTypeList.add(ChlFeeType.valueOf(this.chlFeeTemplate.getFeeType()));
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			this.chlFeeTemplate.setUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));//#
		}
		this.chlFeeTemplate.setUpdateBy(this.getSessionUserCode());
		this.chlFeeTemplate.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		this.branchSharesService.modifyChlFeeTemplate(this.chlFeeTemplate);
		String msg = "修改运营中心与分支机构平台运营手续费模板["+ this.chlFeeTemplate.getTemplateId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/chlFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限删除分支机构分润参数！");
		}
		this.chlFeeTemplate.setTemplateId(this.getTemplateId());
		this.chlFeeTemplate.setFeeContent(this.getFeeContent());
		this.chlFeeTemplate.setUlMoney(AmountUtil.format(new BigDecimal(this.getUlMoney())));
		
		this.branchSharesService.deleteChlFeeTemplate(this.chlFeeTemplate);
		String msg = "删除运营中心与分支机构平台运营手续费模板["+ this.chlFeeTemplate.getTemplateId() +"]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/chlFeeTemplate/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		this.feeTypeList = ChlFeeType.getForChlFee();
		//this.costCycleList = CostCycleType.getYear();
		this.feeContentList = ChlFeeContentType.getList();
	}

	// 服务端查询计费周期列表，返回给前端
	public String queryCostCycleList(){
		
		// 预付资金、通用积分资金、赠券资金默认为"按年"，其他为"按月"
		if(this.chlFeeTemplate.getFeeContent().equals(ChlFeeContentType.PREPAY.getValue())||
				this.chlFeeTemplate.getFeeContent().equals(ChlFeeContentType.POINT.getValue())||
				this.chlFeeTemplate.getFeeContent().equals(ChlFeeContentType.COUPON.getValue())){
			this.costCycleList = CostCycleType.getYear();
		}
		else {
			this.costCycleList = CostCycleType.getMonth();
		}
		
		return "costCycleList";
	}
	
	public Long getSeq(){
		return chlFeeTemplateDAO.getSeq();
	}
	
	public ChlFeeTemplate getChlFeeTemplate() {
		return chlFeeTemplate;
	}

	public void setChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) {
		this.chlFeeTemplate = chlFeeTemplate;
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

	public List<Type> getFeeContentList() {
		return feeContentList;
	}

	public void setFeeContentList(List<Type> feeContentList) {
		this.feeContentList = feeContentList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getFeeContent() {
		return feeContent;
	}

	public void setFeeContent(String feeContent) {
		this.feeContent = feeContent;
	}

	public String getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(String ulMoney) {
		this.ulMoney = ulMoney;
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

}
