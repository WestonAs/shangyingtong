package gnete.card.web.greatDiscount.greatDiscntProtclDef;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.CommonHelper;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.GreatDiscntProtclDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.GreatDiscntProtclDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.GreatDiscntProtclDefState;
import gnete.card.entity.type.PayWayType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 高级折扣
 * @Project: CardWeb
 * @File: GreatDiscntProtclDefAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-30上午9:33:36
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class GreatDiscntProtclDefAction extends BaseAction {

	@Autowired
	private GreatDiscntProtclDefDAO greatDiscntProtclDefDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	
	/** 定义状态 */
	private List greatDiscntProtclDefStatusList;
	/** 支持支付方式 */
	private List payWayList;
	private GreatDiscntProtclDef greatDiscntProtclDef;
	
	private Paginater page;

	// 卡bin范围
	private String cardBinScope_sel;
	//商户名 : 修改
	private String merchName;

	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			params.put("cardIssuer", getSessionUser().getBranchNo());
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			params.put("merchNo", getSessionUser().getMerchantNo());
		} else{
			throw new BizException("非商户或发卡机构禁止查看高级折扣定义。");
		}
		if (greatDiscntProtclDef != null) {
			params.put("greatDiscntProtclName", MatchMode.ANYWHERE.toMatchString(greatDiscntProtclDef	.getGreatDiscntProtclName()));
			params.put("ruleStatus", greatDiscntProtclDef.getRuleStatus());
		}
		page = greatDiscntProtclDefDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getGreatDiscntProtclId(), "高级折扣对象ID不能为空");
		greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclDef.getGreatDiscntProtclId());
		return DETAIL;
	}


	/**
	 * 高级折扣定义审核列表
	 */
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.WORKFLOW_GREAT_DISCNT_PROTCL_DEF, getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("ruleStatus", GreatDiscntProtclDefState.WAIT.getValue());//待审核
		page = greatDiscntProtclDefDAO.findPage(params, getPageNumber(), getPageSize());
		return CHECK_LIST;
	}
	
	/**
	 * 列表明细
	 */
	public String checkDetail() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getGreatDiscntProtclId(), "高级折扣对象ID不能为空");
		greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclDef.getGreatDiscntProtclId());
		return DETAIL;
	}
	
	/**
	 * 注销折扣协议规则
	 */
	public String cancel() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getGreatDiscntProtclId(), "高级折扣对象ID不能为空");
		
		greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclDef.getGreatDiscntProtclId());
		
		greatDiscntProtclDef.setRuleStatus(GreatDiscntProtclDefState.EXPIRE.getValue());
		greatDiscntProtclDefDAO.update(greatDiscntProtclDef);
		
		String msg = LogUtils.r("注销折扣协议号为[{0}]的折扣协议规则成功。", greatDiscntProtclDef.getGreatDiscntProtclId());
		log(msg, UserLogType.OTHER);
		addActionMessage("/greatDiscount/greatDiscntProtclDef/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 启用折扣协议规则
	 */
	public String enable() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getGreatDiscntProtclId(), "高级折扣对象ID不能为空");
		
		greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclDef.getGreatDiscntProtclId());
		
		Assert.notTrue(isExistDef(), "商户["+greatDiscntProtclDef.getMerchNo()+"]高级折扣已存在记录(生效或待审核)!");
		
		greatDiscntProtclDef.setRuleStatus(GreatDiscntProtclDefState.EFFECT.getValue());
		greatDiscntProtclDefDAO.update(greatDiscntProtclDef);
		
		String msg = LogUtils.r("启用折扣协议号为[{0}]的折扣协议规则成功。", greatDiscntProtclDef.getGreatDiscntProtclId());
		log(msg, UserLogType.OTHER);
		addActionMessage("/greatDiscount/greatDiscntProtclDef/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 新增初始化页面
	 */
	public String showAdd() throws Exception {
		initPage();
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || 
				getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止定义一个新的高级折扣。");
		}
		return ADD;
	}

	public boolean isExistDef() throws Exception {
		boolean isExistDef = false;
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(greatDiscntProtclDef.getCardIssuer()) && StringUtils.isNotBlank(greatDiscntProtclDef.getMerchNo())) {
			params.put("cardIssuer", greatDiscntProtclDef.getCardIssuer());
			params.put("merchNo", greatDiscntProtclDef.getMerchNo());
			params.put("ruleStatuses", new String[]{GreatDiscntProtclDefState.EFFECT.getValue(),GreatDiscntProtclDefState.WAIT.getValue()});//增加判断
			isExistDef = greatDiscntProtclDefDAO.findList(params).size() > 0 ? true: false;
		}
		return isExistDef;
	}
	
	/**
	 * 新增
	 */
	public String add() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getCardIssuer(), "高级折扣对象机构号不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getMerchNo(), "高级折扣对象商户号不能为空");
		Assert.notTrue(isExistDef(), "商户["+greatDiscntProtclDef.getMerchNo()+"]高级折扣已存在(生效或待审核)!");
		greatDiscntProtclDef.setIssuerHolderDiscntRate(AmountUtil.divide(greatDiscntProtclDef.getIssuerHolderDiscntRate(), new BigDecimal(100)));
		greatDiscntProtclDef.setIssuerMerchDiscntRate(AmountUtil.divide(greatDiscntProtclDef.getIssuerMerchDiscntRate(), new BigDecimal(100)));
		
		if(CommonHelper.isEmpty(greatDiscntProtclDef.getCardBinScope())){
			greatDiscntProtclDef.setCardBinScope("*");
		}
		
		greatDiscntProtclDef.setInsertTime(new Date());
		greatDiscntProtclDef.setUpdateBy(getSessionUser().getUserId());
		greatDiscntProtclDef.setRuleStatus(GreatDiscntProtclDefState.WAIT.getValue());
		greatDiscntProtclDefDAO.insert(greatDiscntProtclDef);
		
		workflowService.startFlow(greatDiscntProtclDef, WorkflowConstants.GREAT_DISCNT_PROTCL_DEF_ADAPTER, greatDiscntProtclDef.getGreatDiscntProtclId(), getSessionUser());
		String msg = LogUtils.r("高级折扣ID为[{0}]定义已经提交。", greatDiscntProtclDef.getGreatDiscntProtclId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/greatDiscount/greatDiscntProtclDef/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		initPage();
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止修改活动。");
		}
		String greatDiscntProtclId = greatDiscntProtclDef.getGreatDiscntProtclId();
		greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclId);
		
		if(null == greatDiscntProtclDef){
			throw new BizException("要修改的高级折扣["+greatDiscntProtclId+"]不存在。");
		}
		greatDiscntProtclDef.setIssuerHolderDiscntRate(AmountUtil.multiply(greatDiscntProtclDef.getIssuerHolderDiscntRate(), new BigDecimal(100)).setScale(0));
		greatDiscntProtclDef.setIssuerMerchDiscntRate(AmountUtil.multiply(greatDiscntProtclDef.getIssuerMerchDiscntRate(), new BigDecimal(100)).setScale(0));
		
		cardBinScope_sel ="";
		if (StringUtils.isNotEmpty(greatDiscntProtclDef.getCardBinScope())) {
			String[] binscopes = greatDiscntProtclDef.getCardBinScope().split(",");
			if(binscopes[0].equals("*")){
				cardBinScope_sel ="*";
			}else{
				for (int i = 0; i < binscopes.length; i++) {
					CardBin cardBin = (CardBin) cardBinDAO.findByPk(binscopes[i]);
					cardBinScope_sel += cardBin.getBinName();
					if (i < binscopes.length - 1) {
						cardBinScope_sel += ",";
					}
				}
			}
		}

		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(greatDiscntProtclDef.getMerchNo());
		if (merchInfo != null) {
			merchName = merchInfo.getMerchName();
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		greatDiscntProtclDef.setUpdateBy(getSessionUser().getUserId());
		greatDiscntProtclDef.setUpdateTime(new Date());
		greatDiscntProtclDefDAO.update(greatDiscntProtclDef);
		String msg = LogUtils.r("修改高级折扣[{0}]成功。", greatDiscntProtclDef.getGreatDiscntProtclId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/greatDiscount/greatDiscntProtclDef/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		Assert.notNull(greatDiscntProtclDef, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntProtclDef.getGreatDiscntProtclId(), "高级折扣对象ID不能为空");
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || 
				getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作。");
		}
		boolean isSuccess = greatDiscntProtclDefDAO.delete(greatDiscntProtclDef)>0? true:false;
		if (isSuccess) {
			String msg = LogUtils.r("删除高级折扣定义[{0}]成功。", greatDiscntProtclDef.getGreatDiscntProtclId());
			log(msg, UserLogType.DELETE);
			addActionMessage("/greatDiscount/greatDiscntProtclDef/list.do", msg);
		}
		return SUCCESS;
	}
	
	private void initPage() {
		greatDiscntProtclDefStatusList = GreatDiscntProtclDefState.getAll();
		payWayList = PayWayType.getAll();
	}

	public List getGreatDiscntProtclDefStatusList() {
		return greatDiscntProtclDefStatusList;
	}

	public void setGreatDiscntProtclDefStatusList(List greatDiscntProtclDefStatusList) {
		this.greatDiscntProtclDefStatusList = greatDiscntProtclDefStatusList;
	}

	public List getPayWayList() {
		return payWayList;
	}

	public void setPayWayList(List payWayList) {
		this.payWayList = payWayList;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public GreatDiscntProtclDef getGreatDiscntProtclDef() {
		return greatDiscntProtclDef;
	}

	public void setGreatDiscntProtclDef(GreatDiscntProtclDef greatDiscntProtclDef) {
		this.greatDiscntProtclDef = greatDiscntProtclDef;
	}

	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

	public String getCardBinScope_sel() {
		return cardBinScope_sel;
	}

	public void setCardBinScope_sel(String cardBinScope_sel) {
		this.cardBinScope_sel = cardBinScope_sel;
	}
	
	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}


}
