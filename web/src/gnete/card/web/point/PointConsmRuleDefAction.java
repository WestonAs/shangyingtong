package gnete.card.web.point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointConsmRuleDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointConsmRuleDef;
import gnete.card.entity.state.PromotionsRuleState;
import gnete.card.entity.type.PointConsmRuleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointExchgService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 积分兑换赠券规则定义
 * @author aps-lib
 *
 */
public class PointConsmRuleDefAction extends BaseAction{
	
	@Autowired
	private PointConsmRuleDefDAO pointConsmRuleDefDAO;
	@Autowired
	private PointExchgService pointExchgService;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private PointConsmRuleDef pointConsmRuleDef;
	private Paginater page;
	private Collection PtExchgRuleTypeList;
	private Collection PtConsmRuleStateList;
	private List<PointClassDef> pointClassDefList;
	private List<CouponClassDef> couponClassDefList;
	private String ptExchgRuleId;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;
	private PointClassDef ptClassDef;
	private CouponClassDef cpClassDef;
	private List<PointConsmRuleDef> pointConsmRuleList;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		if (pointConsmRuleDef != null) {
			params.put("ptClass", pointConsmRuleDef.getPtClass());
			params.put("couponClass", pointConsmRuleDef.getCouponClass());
		}
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门运营分支机构时
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if(CollectionUtils.isEmpty(branchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("branchCode", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = branchList.size()+merchInfoList.size();
			String[] jinstIds = new String[length];
			int i = 0;
			for( ; i<branchList.size(); i++){
				jinstIds[i] = (branchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				jinstIds[i] = (merchInfoList.get(i-branchList.size())).getMerchId();
			}
			params.put("branchCodes", jinstIds);
		}
		
		this.page = this.pointConsmRuleDefDAO.findPointConsmRule( params,this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	//取得积分兑换赠券定义记录的明细
	public String detail() throws Exception {
		this.pointConsmRuleDef = (PointConsmRuleDef) this.pointConsmRuleDefDAO.findByPk(this.pointConsmRuleDef.getPtExchgRuleId());
		this.ptClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(this.pointConsmRuleDef.getPtClass());
		this.cpClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(this.pointConsmRuleDef.getCouponClass());
		this.log("查询积分兑换赠券定义["+this.pointConsmRuleDef.getPtExchgRuleId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		/*if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&
				!this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			throw new BizException("非发卡机构或者商户不能操作。");
		}*/
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		initPage();
		this.pointClassDefList = this.getPointClassDef();
		this.couponClassDefList = this.getCouponClassDef();
		return ADD;
	}	
	
	// 新增积分兑换赠券定义登记
	public String add() throws Exception {		
		
		this.pointConsmRuleDef.setRuleStatus(PromotionsRuleState.EFFECT.getValue());
		this.pointConsmRuleDef.setBranchCode(RoleType.CARD.getValue().equals(this.getLoginRoleType()) ? 
				this.getSessionUser().getBranchNo() : this.getSessionUser().getMerchantNo());
		this.pointExchgService.addPointConsmRuleDef(pointConsmRuleDef, this.getSessionUserCode());
		String msg = "新增积分兑换赠券定义["+this.pointConsmRuleDef.getPtExchgRuleId()+"]成功！";
		this.addActionMessage("/pointExchg/pointConsmRule/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		/*if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&
				!this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			throw new BizException("非发卡机构或者商户不能操作。");
		}*/
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		initPage();
		this.pointConsmRuleDef = (PointConsmRuleDef)this.pointConsmRuleDefDAO.findByPk(this.pointConsmRuleDef.getPtExchgRuleId());
		return MODIFY;
	}
	
	// 修改积分兑换赠券定义
	public String modify() throws Exception {
		/*if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&
				!this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			throw new BizException("非发卡机构或者商户不能操作。");
		}*/
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		this.pointExchgService.modifyPointConsmRuleDef(pointConsmRuleDef, this.getSessionUserCode());
		this.addActionMessage("/pointExchg/pointConsmRule/list.do", "修改积分兑换赠券定义成功！");	
		return SUCCESS;
	}
	
	// 删除积分兑换赠券定义
	public String delete() throws Exception {
		this.pointExchgService.deletePointConsmRuleDef(this.getPtExchgRuleId());
		String msg = "删除积分兑换赠券定义[" +this.getPtExchgRuleId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pointExchg/pointConsmRule/list.do", msg);
		return SUCCESS;
	}
	
	
	// 查询当前机构下可用的积分子类型定义
	private List<PointClassDef> getPointClassDef() {
		
		Map<String, Object> PointClassDefMap = new HashMap<String, Object>();
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			PointClassDefMap.put("jinstId",  this.getSessionUser().getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			PointClassDefMap.put("jinstId",  this.getSessionUser().getMerchantNo());
		}
		
		return this.pointClassDefDAO.findPtClassByJinst(PointClassDefMap);
	}
	
	
	// 查询当前机构下可用的赠券子类型定义
	private List<CouponClassDef> getCouponClassDef() {
		Map<String, Object> couponClassDefMap = new HashMap<String, Object>();
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			couponClassDefMap.put("jinstId",  this.getSessionUser().getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			couponClassDefMap.put("jinstId",  this.getSessionUser().getMerchantNo());
		}
		return this.couponClassDefDAO.findCouponClassByJinst(couponClassDefMap);
	}
	
	private void initPage() {
		this.PtExchgRuleTypeList = PointConsmRuleType.getAll();
		this.PtConsmRuleStateList = PromotionsRuleState.getPtConsmRuleState();
	}
	
	// 生效
	public String effect() throws Exception {
		/*if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&
				!this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			throw new BizException("非发卡机构或者商户不能操作。");
		}*/
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		this.pointExchgService.effectptConsmRuleDef(this.getPtExchgRuleId());
		
		String msg = "规则["+this.getPtExchgRuleId()+"]生效！";
		this.addActionMessage("/pointExchg/pointConsmRule/list.do", msg);
		
		return SUCCESS;
	}
	
	// 失效
	public String invalid() throws Exception {
		/*if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&
				!this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			throw new BizException("非发卡机构或者商户不能操作。");
		}*/
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		this.pointExchgService.invalidptConsmRuleDef(this.getPtExchgRuleId());
		
		String msg = "规则["+this.getPtExchgRuleId()+"]失效！";
		this.addActionMessage("/pointExchg/pointConsmRule/list.do", msg);
		
		return SUCCESS;
	}
	
	// 根据输入的积分参数计算可兑换的赠券范围
	public void couponAmtSpan(){
		BigDecimal ptParam = this.pointConsmRuleDef.getPtParam();
		String couponAmtMin = "";
		String couponAmtMax = "";
		PointConsmRuleDef lessThanRule = null;
		PointConsmRuleDef greaterThanRule = null;
		
		Map params = new HashMap();
		params.put("ptClass", this.pointConsmRuleDef.getPtClass());
		params.put("couponClass", this.pointConsmRuleDef.getCouponClass());
		List<PointConsmRuleDef> ruleList = this.pointConsmRuleDefDAO.getPointConsmRuleByClass(params);
		ruleList = BubbleSort(ruleList);
		
		try {
			for(int i=0; i<ruleList.size(); i++){ // 遍历规则列表ruleList找出与输入的积分参数相邻的积分段
				if(ruleList.get(i).getPtParam().compareTo(ptParam)>0){
					greaterThanRule = ruleList.get(i);
				} 
				else if(ruleList.get(i).getPtParam().compareTo(ptParam)<0){
					lessThanRule = ruleList.get(i);
					break;
				}
				else if(ruleList.get(i).getPtParam().compareTo(ptParam)==0){
					throw new Exception("积分参数段["+ptParam+"]已存在，请输入不同的积分参数段。");
				}
			}
			
			// 计算最小兑换赠券
			if(lessThanRule != null){
				BigDecimal couponAmt = ptParam.divide(lessThanRule.getPtParam(), 4, BigDecimal.ROUND_UP).multiply(lessThanRule.getRuleParam1());
				long integral = Math.round(couponAmt.doubleValue());
				
				if(couponAmt.compareTo(new BigDecimal(integral))==0){
					couponAmt = couponAmt.add(new BigDecimal(1));
					couponAmt = new BigDecimal(Math.ceil(couponAmt.doubleValue()));
				} else {
					couponAmt = new BigDecimal(Math.ceil(couponAmt.doubleValue()));
				}
				
				couponAmtMin = couponAmt.toString();
			}
			
			// 计算最大兑换赠券
			if(greaterThanRule != null){
				BigDecimal couponAmt = ptParam.divide(greaterThanRule.getPtParam(), 4, BigDecimal.ROUND_UP).multiply(greaterThanRule.getRuleParam1());
				long integral = Math.round(couponAmt.doubleValue());
				
				if(couponAmt.compareTo(new BigDecimal(integral))==0){
					couponAmt = couponAmt.subtract(new BigDecimal(1));
					couponAmt = new BigDecimal(Math.ceil(couponAmt.doubleValue()));
				} else {
					couponAmt = new BigDecimal(Math.ceil(couponAmt.doubleValue())).subtract(new BigDecimal(1));
				}
				
				couponAmtMax = couponAmt.toString();
			}
			
			if(!couponAmtMin.equals("")&&!couponAmtMax.equals("")){
				if(new BigDecimal(couponAmtMin).compareTo(new BigDecimal(couponAmtMax))>0){
					throw new Exception("可输入的最小兑换赠券["+couponAmtMin+"]不能大于可输入的最大兑换赠券["+couponAmtMax+"]。");
				}
			}
		}
		catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'couponAmtMin':'" + couponAmtMin + "', 'couponAmtMax':'" + couponAmtMax + "'}");
	}
	
	//取得所选积分和赠券的积分兑换赠券规则列表
	public String getPtConsmRuleList() throws Exception {
		Map params = new HashMap();
		params.put("ptClass", this.pointConsmRuleDef.getPtClass());
		params.put("couponClass", this.pointConsmRuleDef.getCouponClass());
		this.pointConsmRuleList = this.pointConsmRuleDefDAO.getPointConsmRuleByClass(params);
		this.pointConsmRuleList = BubbleSort(this.pointConsmRuleList);
		return "pointConsmRuleList";
	}
	
	// 冒泡排序
	private List<PointConsmRuleDef> BubbleSort(List<PointConsmRuleDef> list){ 
		int i;
		int j;
		int n = list.size();
		boolean exchange; //交换标志
		PointConsmRuleDef temp = null;
		
		for(i=1; i<n; i++){ 
			exchange = false; 
			for(j = n-1; j>=i; j--){ 
				if(list.get(j).getPtParam().compareTo(list.get(j-1).getPtParam())>0){ //交换记录
					temp = list.get(j);
					list.set(j, list.get(j-1));
					list.set(j-1, temp);
					exchange = true; //发生了交换,将交换标志置为真
				}
			}
			if(!exchange){ //本趟排序未发生交换，提前终止算法
				return list;
			}
		}
		return list;
	}

	public PointConsmRuleDef getPointConsmRuleDef() {
		return pointConsmRuleDef;
	}

	public void setPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef) {
		this.pointConsmRuleDef = pointConsmRuleDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getPtExchgRuleTypeList() {
		return PtExchgRuleTypeList;
	}

	public void setPtExchgRuleTypeList(Collection ptExchgRuleTypeList) {
		PtExchgRuleTypeList = ptExchgRuleTypeList;
	}

	public Collection getPtConsmRuleStateList() {
		return PtConsmRuleStateList;
	}

	public void setPtConsmRuleStateList(Collection ptConsmRuleStateList) {
		PtConsmRuleStateList = ptConsmRuleStateList;
	}

	public String getPtExchgRuleId() {
		return ptExchgRuleId;
	}

	public void setPtExchgRuleId(String ptExchgRuleId) {
		this.ptExchgRuleId = ptExchgRuleId;
	}

	public List<PointClassDef> getPointClassDefList() {
		return pointClassDefList;
	}

	public void setPointClassDefList(List<PointClassDef> pointClassDefList) {
		this.pointClassDefList = pointClassDefList;
	}

	public CouponClassDefDAO getCouponClassDefDAO() {
		return couponClassDefDAO;
	}

	public void setCouponClassDefDAO(CouponClassDefDAO couponClassDefDAO) {
		this.couponClassDefDAO = couponClassDefDAO;
	}

	public void setCouponClassDefList(List<CouponClassDef> couponClassDefList) {
		this.couponClassDefList = couponClassDefList;
	}

	public List<CouponClassDef> getCouponClassDefList() {
		return couponClassDefList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public PointClassDef getPtClassDef() {
		return ptClassDef;
	}

	public void setPtClassDef(PointClassDef ptClassDef) {
		this.ptClassDef = ptClassDef;
	}

	public CouponClassDef getCpClassDef() {
		return cpClassDef;
	}

	public void setCpClassDef(CouponClassDef cpClassDef) {
		this.cpClassDef = cpClassDef;
	}

	public List<PointConsmRuleDef> getPointConsmRuleList() {
		return pointConsmRuleList;
	}

	public void setPointConsmRuleList(List<PointConsmRuleDef> pointConsmRuleList) {
		this.pointConsmRuleList = pointConsmRuleList;
	}

}
