package gnete.card.web.fee;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.AmountUtils;
import flink.util.Paginater;
import flink.util.Type;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ChlFeeDAO;
import gnete.card.dao.ChlFeeTemplateDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ChlFee;
import gnete.card.entity.ChlFeeKey;
import gnete.card.entity.ChlFeeTemplate;
import gnete.card.entity.type.AmtCntType;
import gnete.card.entity.type.ChlFeeContentType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchSharesService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ChlFeeAction extends BaseAction{

	@Autowired 
	private ChlFeeDAO chlFeeDAO;
	@Autowired
	private ChlFeeTemplateDAO chlFeeTemplateDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchSharesService branchSharesService;
	
	private ChlFee chlFee;
	private ChlFeeTemplate chlFeeTemplate;
	private Paginater page;
	private Collection costCycleList;
	private List<Type> feeTypeList;
	private List<Type> feeContentList;
	private List<BranchInfo> branchList;
	private List<ChlFeeTemplate> chlFeeTemplateList;
	
	private String branchCode;
	private String feeContent;
	private String ulMoney;
	
	private String[] ulimit;
	private String[] feeRate;
	private String feeRateComma;
	private String segment;
	private String amtOrFeeRate;
	
	private boolean showProxy = false;
	private String updateUlmoney;
	
	@Override
	public String execute() throws Exception {
		initPage();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(chlFee!=null){
			params.put("branchCode",chlFee.getBranchCode());
			params.put("feeType", chlFee.getFeeType());
			params.put("costCycle", chlFee.getCostCycle());
			params.put("feeContent", chlFee.getFeeContent());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(chlFee.getBranchName()));
		}
		
		if (this.getLoginRoleType().equals(RoleType.FENZHI.getValue())){
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("branchCode", this.getSessionUser().getBranchNo());
			this.showProxy = true;
		} else if (this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			branchList = this.branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			this.showProxy = false;
		} else {
			throw new BizException("没有权限查询分支机构平台运营手续费！");
		}
		this.page = chlFeeDAO.findChlFee(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 取得明细
	public String detail() throws Exception {
		
		ChlFeeKey key = new ChlFeeKey();
		key.setBranchCode(this.chlFee.getBranchCode());
		key.setFeeContent(this.chlFee.getFeeContent());
		key.setUlMoney(this.chlFee.getUlMoney());
		this.chlFee = (ChlFee) this.chlFeeDAO.findByPk(key);
		
		this.log("查询分支机构["+this.chlFee.getBranchCode()+"]的平台运营手续费明细信息成功", UserLogType.SEARCH);
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
		List<ChlFee> feeList = new ArrayList<ChlFee>();
		if (this.chlFee.getFeeType().equals(ChlFeeType.TRADE_MONEY.getValue())
				|| this.chlFee.getFeeType().equals(ChlFeeType.TRADE_RATE.getValue())
				|| this.chlFee.getFeeType().equals(ChlFeeType.ACCUMULATIVE_RATE.getValue())) {
			
			for (int i = 0; i < ulimit.length; i++) {
				ChlFee fee = new ChlFee();
				fee.setBranchCode(this.chlFee.getBranchCode());
				fee.setFeeContent(this.chlFee.getFeeContent());
				fee.setFeeType(this.chlFee.getFeeType());
				fee.setCostCycle(this.chlFee.getCostCycle());
				fee.setUlMoney(AmountUtils.parseBigDecimal(ulimit[i]));
				fee.setFeeRate(AmountUtils.parseBigDecimal(feeRate[i]));
				
				// 成本模式为 预付资金、 通用积分和专属积分、赠券、次卡时，按金额计费
				if(ChlFeeContentType.PREPAY.getValue().equals(this.chlFee.getFeeContent())||
						ChlFeeContentType.COUPON.getValue().equals(this.chlFee.getFeeContent())||
						ChlFeeContentType.POINT.getValue().equals(this.chlFee.getFeeContent())||
						ChlFeeContentType.TIME_CARD.getValue().equals(this.chlFee.getFeeContent())){
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
				}
				// 按笔数收费
				else{
					fee.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
				}
				feeList.add(fee);
			}
		} else {
			this.chlFee.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));
			this.chlFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
			
			// 成本模式为 预付资金、 通用积分和专属积分、赠券、次卡时，按金额计费
			if(ChlFeeContentType.PREPAY.getValue().equals(this.chlFee.getFeeContent())||
					ChlFeeContentType.COUPON.getValue().equals(this.chlFee.getFeeContent())||
					ChlFeeContentType.POINT.getValue().equals(this.chlFee.getFeeContent()) ||
					ChlFeeContentType.TIME_CARD.getValue().equals(this.chlFee.getFeeContent())){
				this.chlFee.setAmtOrCnt(new BigDecimal(AmtCntType.AMT.getValue()));
			}
			// 按笔数收费
			else{
				this.chlFee.setAmtOrCnt(new BigDecimal(AmtCntType.CNT.getValue()));
			}
			feeList.add(this.chlFee);
		}
		
		this.branchSharesService.addChlFee(feeList.toArray(new ChlFee[feeList.size()]), this.getSessionUserCode());	
		String msg = "添加运营中心与分支机构["+ this.chlFee.getBranchCode() +"]平台运营手续费成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/fee/chlFee/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		ChlFeeKey key = new ChlFeeKey();
		key.setBranchCode(this.chlFee.getBranchCode());
		key.setFeeContent(this.chlFee.getFeeContent());
		key.setUlMoney(AmountUtil.format(this.chlFee.getUlMoney()));
		this.chlFee = (ChlFee)this.chlFeeDAO.findByPk(key);
		this.feeRateComma = this.chlFee.getFeeRate().toString();

		this.updateUlmoney = chlFee.getUlMoney().toString();
		if(ChlFeeType.TRADE_MONEY.getValue().equals(this.chlFee.getFeeType())||
				ChlFeeType.TRADE_RATE.getValue().equals(this.chlFee.getFeeType())){
			this.feeTypeList = ChlFeeType.getTradeChlFee();
		}
		else{
			this.feeTypeList = new ArrayList<Type>();
			this.feeTypeList.add(ChlFeeType.valueOf(this.chlFee.getFeeType()));
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateUlmoney)) {
			this.chlFee.setNewUlMoney(AmountUtils.parseBigDecimal(updateUlmoney));
		}
		this.chlFee.setUpdateBy(this.getSessionUserCode());
		this.chlFee.setFeeRate(AmountUtils.parseBigDecimal(this.getFeeRateComma()));
		this.branchSharesService.modifyChlFee(this.chlFee);
		String msg = "修改运营中心与分支机构["+ this.chlFee.getBranchCode() +"]平台运营手续费成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/fee/chlFee/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限删除分支机构分润参数！");
		}
		this.chlFee.setBranchCode(this.getBranchCode());
		this.chlFee.setFeeContent(this.getFeeContent());
		this.chlFee.setUlMoney(AmountUtil.format(new BigDecimal(this.getUlMoney())));
		
		this.branchSharesService.deleteChlFee(this.chlFee);
		String msg = "删除运营中心与分支机构["+ this.chlFee.getBranchCode() +"]平台运营手续费成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/chlFee/list.do", msg);
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
		if(this.chlFee.getFeeContent().equals(ChlFeeContentType.PREPAY.getValue())||
				this.chlFee.getFeeContent().equals(ChlFeeContentType.POINT.getValue())||
				this.chlFee.getFeeContent().equals(ChlFeeContentType.COUPON.getValue())){
			this.costCycleList = CostCycleType.getYear();
		}
		else {
			this.costCycleList = CostCycleType.getMonth();
		}
		
		return "costCycleList";
	}

        // 打开新增页面的初始化操作
	public String showAddTemplate() throws Exception {
		if (!this.getLoginRoleType().equals(RoleType.CENTER.getValue())
				&& !this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("没有权限设置分支机构分润参数！");
		}
		
		this.chlFeeTemplateList = this.chlFeeTemplateDAO.findDistinctList();
		
		return "addTemplate";
	}
	
	// 使用分支机构手续费模板新增分支机构手续费
	public String addTemplate() throws Exception {
		Assert.notEmpty(chlFee.getBranchCode(), "机构号不能为空!");
		Assert.notNull(chlFeeTemplate.getTemplateId(), "模板ID不能为空!");
		// 取币种
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(this.chlFee.getBranchCode());
		Assert.notNull(branchInfo, "机构["+chlFee.getBranchCode()+"]不存在!");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", this.chlFeeTemplate.getTemplateId());
		List<ChlFeeTemplate> chlFeeTemplateList = this.chlFeeTemplateDAO.findList(params);
		
		List<ChlFee> cardFeeList = new ArrayList<ChlFee>();
		for (int i = 0; i < chlFeeTemplateList.size(); i++) {
			ChlFeeTemplate temp = chlFeeTemplateList.get(i);
			ChlFee fee = new ChlFee();
			fee.setBranchCode(branchInfo.getBranchCode());
			fee.setBranchName(branchInfo.getBranchName());
			fee.setCurCode(branchInfo.getCurCode());
			templateToFee(fee, temp);
			cardFeeList.add(fee);
		}
		
	    this.branchSharesService.addChlFee(cardFeeList.toArray(new ChlFee[cardFeeList.size()]), this.getSessionUserCode());
		
		String msg = "使用分支机构模板添加发卡机构与分支机构手续费费率参数["+ chlFee.getBranchCode() +"]成功";
		this.addActionMessage("/fee/chlFee/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	//取得模板数据
	public void getTempaltePara() throws Exception {
		JSONObject jsonObject = new JSONObject();
		
		this.chlFeeTemplateList = new ArrayList<ChlFeeTemplate>();
		
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("templateId", this.chlFeeTemplate.getTemplateId());
			chlFeeTemplateList = this.chlFeeTemplateDAO.findList(params);
			
			Assert.notEmpty(chlFeeTemplateList, "手续费模板不存在,请重新选择！");
		}
		catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("error", e.getMessage());
//			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		ChlFeeTemplate chlFeeTemplate = chlFeeTemplateList.get(0);
		String feeContent = chlFeeTemplate.getFeeContent();
		String feeContentName = chlFeeTemplate.getFeeContent() != null ?
				                ChlFeeContentType.valueOf(chlFeeTemplate.getFeeContent()).getName():"";//计费内容
	    String feeType = chlFeeTemplate.getFeeType();
		String feeTypeName = chlFeeTemplate.getFeeType() != null ? 
								ChlFeeType.valueOf(chlFeeTemplate.getFeeType()).getName() : ""; // 计费方式
		String costCycleName = chlFeeTemplate.getCostCycle() != null ? 
								CostCycleType.valueOf(chlFeeTemplate.getCostCycle()).getName() : "" ; // 计费周期
		
		String feeRate = chlFeeTemplate.getFeeRate().toString();// 费率

		jsonObject.put("success", true);
		jsonObject.put("feeContentName", feeContentName);
		jsonObject.put("feeContent", feeContent);
		jsonObject.put("feeTypeName", feeTypeName);
		jsonObject.put("feeType", feeType);
		jsonObject.put("costCycleName", costCycleName);
		jsonObject.put("feeRate", feeRate);
		
		this.respond(jsonObject.toString());
//		this.respond("{'success':"+ true +
//				", 'feeContentName':'" + feeContentName +
//				"', 'feeContent':'" + feeContent +
//				"', 'feeTypeName':'" + feeTypeName + 
//				"', 'feeType':'" + feeType + 
//				"', 'costCycleName':'" + costCycleName + 
//				"', 'feeRate':'" + feeRate +
//				"'}");
		
	}
	
	public String chlFeeTemplateList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateId", this.chlFeeTemplate.getTemplateId());
		chlFeeTemplateList = this.chlFeeTemplateDAO.findList(params);
		
		if (chlFeeTemplateList.isEmpty()) {
			return null;
		}
		
		return "chlFeeTemplateList";
	}
	
	private void templateToFee(ChlFee dest,  ChlFeeTemplate src){
		dest.setFeeContent(src.getFeeContent());
		dest.setFeeType(src.getFeeType());
		dest.setAmtOrCnt(src.getAmtOrCnt());
		dest.setCostCycle(src.getCostCycle());
		dest.setUlMoney(src.getUlMoney());
		dest.setFeeRate(src.getFeeRate());
	}
	
	public ChlFee getChlFee() {
		return chlFee;
	}

	public void setChlFee(ChlFee chlFee) {
		this.chlFee = chlFee;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	
	public ChlFeeTemplate getChlFeeTemplate() {
		return chlFeeTemplate;
	}

	public void setChlFeeTemplate(ChlFeeTemplate chlFeeTemplate) {
		this.chlFeeTemplate = chlFeeTemplate;
	}

	public List<ChlFeeTemplate> getChlFeeTemplateList() {
		return chlFeeTemplateList;
	}

	public void setChlFeeTemplateList(List<ChlFeeTemplate> chlFeeTemplateList) {
		this.chlFeeTemplateList = chlFeeTemplateList;
	}
	
	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getAmtOrFeeRate() {
		return amtOrFeeRate;
	}

	public void setAmtOrFeeRate(String amtOrFeeRate) {
		this.amtOrFeeRate = amtOrFeeRate;
	}

}
