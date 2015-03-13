package gnete.card.web.greatDiscount.greatDiscntProtclCentFee;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.GreatDiscntProtclCentFeeDAO;
import gnete.card.dao.GreatDiscntProtclDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.GreatDiscntProtclCentFee;
import gnete.card.entity.GreatDiscntProtclDef;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class GreatDiscntProtclCentFeeAction extends BaseAction {

	@Autowired
	private GreatDiscntProtclCentFeeDAO greatDiscntProtclCentFeeDAO;
	@Autowired
	private GreatDiscntProtclDefDAO greatDiscntProtclDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private GreatDiscntProtclCentFee greatDiscntProtclCentFee;
	
	private List<GreatDiscntProtclDef> protclList;

	private String greatDiscntProtclNo;
	private String cardBranchName;
	
	private Paginater page;

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
			throw new BizException("非商户或发卡机构禁止查看高级折扣中心费率定义。");
		}
		if (greatDiscntProtclCentFee != null) {
			params.put("greatDiscntProtclNo",greatDiscntProtclCentFee.getGreatDiscntProtclNo());
			params.put("greatDiscntProtclName",MatchMode.ANYWHERE.toMatchString(greatDiscntProtclCentFee.getGreatDiscntProtclName()));

		}
		page = greatDiscntProtclCentFeeDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(greatDiscntProtclCentFee, "高级折扣中心费率对象不能为空");
		Assert.notEmpty(greatDiscntProtclCentFee.getGreatDiscntProtclNo(), "高级折扣中心费率对象折扣协议号不能为空");

		greatDiscntProtclCentFee = (GreatDiscntProtclCentFee) greatDiscntProtclCentFeeDAO.findByPk(greatDiscntProtclCentFee);
		return DETAIL;
	}


	/**
	 * 新增初始化页面
	 */
	public String showAdd() throws Exception {
		initPage();
		return ADD;
	}

	public boolean isExistDef() throws Exception {
		boolean isExistDef = false;
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(greatDiscntProtclCentFee.getGreatDiscntProtclNo())) {
			params.put("greatDiscntProtclNo", greatDiscntProtclCentFee.getGreatDiscntProtclNo());
			isExistDef = greatDiscntProtclCentFeeDAO.findList(params).size() > 0 ? true: false;
		}
		return isExistDef;
	}
	
	/**
	 * 新增
	 */
	public String add() throws Exception {
		Assert.notNull(greatDiscntProtclCentFee, "高级折扣中心费率对象不能为空");
		Assert.notEmpty(greatDiscntProtclCentFee.getCardIssuer(), "高级折扣中心费率对象机构号不能为空");
		Assert.notEmpty(greatDiscntProtclCentFee.getGreatDiscntProtclNo(), "高级折扣中心费率对象协议号不能为空");
		
		Assert.notTrue(isExistDef(), "["+greatDiscntProtclCentFee.getGreatDiscntProtclNo()+"]高级折扣中心费率已存在!");
		
		//获取协议名称
		GreatDiscntProtclDef greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(greatDiscntProtclCentFee.getGreatDiscntProtclNo());
		Assert.notNull(greatDiscntProtclDef, "高级折扣协议["+greatDiscntProtclCentFee.getGreatDiscntProtclNo()+"]对象信息不存在");
		
		greatDiscntProtclCentFee.setCentIncomeFee(greatDiscntProtclCentFee.getCentIncomeFee().divide(new BigDecimal(100)));
		greatDiscntProtclCentFee.setGreatDiscntProtclName(greatDiscntProtclDef.getGreatDiscntProtclName());
		greatDiscntProtclCentFee.setInsertTime(new Date());
		greatDiscntProtclCentFeeDAO.insert(greatDiscntProtclCentFee);
		
		String msg = LogUtils.r("高级折扣中心费率ID为[{0}]定义已经提交。", greatDiscntProtclCentFee.getGreatDiscntProtclNo());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/greatDiscount/greatDiscntProtclCentFee/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		initPage();
		greatDiscntProtclCentFee = (GreatDiscntProtclCentFee) greatDiscntProtclCentFeeDAO.findByPk(greatDiscntProtclCentFee);
		
		if(null == greatDiscntProtclCentFee){
			throw new BizException("要修改的高级折扣中心费率["+greatDiscntProtclCentFee.getGreatDiscntProtclNo()+"]不存在。");
		}else{
			greatDiscntProtclCentFee.setCentIncomeFee(greatDiscntProtclCentFee.getCentIncomeFee().multiply(new BigDecimal(100)).setScale(0));
			String cardIssuer = greatDiscntProtclCentFee.getChlCode();
			if(CommonHelper.isNotEmpty(cardIssuer)){
				//读取机构名
			     BranchInfo branchInfo = branchInfoDAO.findBranchInfo(cardIssuer);
			     if(null != branchInfo){
			    	 cardBranchName = branchInfo.getBranchName();
			     }
			     
			     //加载高级折扣协议列表
			     protclList = loadGreatDiscntProtclDef(cardIssuer);
			}
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		greatDiscntProtclCentFee.setCentIncomeFee(greatDiscntProtclCentFee.getCentIncomeFee().divide(new BigDecimal(100)));
		greatDiscntProtclCentFee.setUpdateTime(new Date());
		greatDiscntProtclCentFeeDAO.update(greatDiscntProtclCentFee);
		String msg = LogUtils.r("修改高级折扣中心费率[{0}]成功。", greatDiscntProtclCentFee.getGreatDiscntProtclNo());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/greatDiscount/greatDiscntProtclCentFee/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		initPage();
		Assert.notEmpty(greatDiscntProtclNo, "高级折扣中心费率对象ID不能为空");
		
		greatDiscntProtclCentFee = new GreatDiscntProtclCentFee();
		greatDiscntProtclCentFee.setGreatDiscntProtclNo(greatDiscntProtclNo);
		boolean isSuccess = greatDiscntProtclCentFeeDAO.delete(greatDiscntProtclCentFee)>0? true:false;
		if (isSuccess) {
			String msg = LogUtils.r("删除高级折扣中心费率[{0}]成功。", greatDiscntProtclCentFee.getGreatDiscntProtclNo());
			log(msg, UserLogType.DELETE);
			addActionMessage("/greatDiscount/greatDiscntProtclCentFee/list.do", msg);
		}
		return SUCCESS;
	}
	
	private void initPage() throws Exception{
		if (!(getLoginRoleTypeCode().equals(RoleType.CENTER.getValue()) || 
				getLoginRoleTypeCode().equals(RoleType.CARD_DEPT.getValue()))) {
			throw new BizException("非运营机构无权操作高级折扣中心费率。");
		}
	}
	
	public void loadProtclList() throws BizException{
		String cardIssuer = request.getParameter("cardIssuer");
		Assert.notEmpty(cardIssuer, "机构号不能为空");
		
		List<GreatDiscntProtclDef> greatDiscntProtclDefList = loadGreatDiscntProtclDef(cardIssuer);
		if (CollectionUtils.isEmpty(greatDiscntProtclDefList)) {
			return;
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (GreatDiscntProtclDef greatDiscntProtclDef : greatDiscntProtclDefList){
			sb.append("<option value=\"").append(greatDiscntProtclDef.getGreatDiscntProtclId())
						.append("\">").append(greatDiscntProtclDef.getGreatDiscntProtclName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	private List<GreatDiscntProtclDef> loadGreatDiscntProtclDef(String cardIssuer){
		List<BranchInfo> branchInfoList = this.branchInfoDAO.findCardByManange(cardIssuer);
		if (CollectionUtils.isEmpty(branchInfoList)) {
			return null;
		}
		
		int i=0;
		String[] branchCodes = new String[branchInfoList.size()];
		for (BranchInfo branchInfo : branchInfoList){
			branchCodes[i++] = branchInfo.getBranchCode();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuers", branchCodes);
		
		return  greatDiscntProtclDefDAO.findList(params);
	}
	
	public void loadCardIssuer(){
		JSONObject object = new JSONObject();
		
		String protclId = request.getParameter("protclId");
		if (StringUtils.isEmpty(protclId)) {
			return;
		}
		try {
			GreatDiscntProtclDef greatDiscntProtclDef = (GreatDiscntProtclDef) greatDiscntProtclDefDAO.findByPk(protclId);
			Assert.notNull(greatDiscntProtclDef, "高级折扣协议["+protclId+"]对象信息不存在");
	
			object.put("success", true);
			object.put("cardIssuer", greatDiscntProtclDef.getCardIssuer());
		}catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		this.respond(object.toString());
	}
	
	public List<GreatDiscntProtclDef> getProtclList() {
		return protclList;
	}

	public void setProtclList(List<GreatDiscntProtclDef> protclList) {
		this.protclList = protclList;
	}
	
	public String getGreatDiscntProtclNo() {
		return greatDiscntProtclNo;
	}

	public void setGreatDiscntProtclNo(String greatDiscntProtclNo) {
		this.greatDiscntProtclNo = greatDiscntProtclNo;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public GreatDiscntProtclCentFee getGreatDiscntProtclCentFee() {
		return greatDiscntProtclCentFee;
	}

	public void setGreatDiscntProtclCentFee(GreatDiscntProtclCentFee greatDiscntProtclCentFee) {
		this.greatDiscntProtclCentFee = greatDiscntProtclCentFee;
	}

}
