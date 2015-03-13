package gnete.card.web.cardtypeset;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.RolePrivilegeDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PtExchgRuleType;
import gnete.card.entity.type.PtInstmMthdType;
import gnete.card.entity.type.PtUsageType;
import gnete.card.entity.type.PtUseLimitType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 积分子类型
 * @author aps-lib
 *
 */
public class PointClassAction extends BaseAction {
	
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private RolePrivilegeDAO rolePrivilegeDAO;
	@Autowired
	private MerchGroupDAO merchGroupDAO;
	
	private PointClassDef pointClassDef;
	private Paginater page;
	private Collection jinstTypeList;
	private Collection amtTypeList;
	private Collection ptUsageList;
	private Collection ptInstmMthdList;
	private Collection ptExchgRuleTypeList;
	private Collection cardIssuerList;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;
	private List<PtUseLimitType> ptUseLimitList;
	private String[] ptUseLimitCodes;
	private String pointHasTypes;
	
	private List<MerchGroup> merchGroupList;
	private List<PointClassDef> pointClassDefList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		ptUsageList = PtUsageType.getAll();
		
		if(pointClassDef != null){
			params.put("className", MatchMode.ANYWHERE.toMatchString(pointClassDef.getClassName()));
			params.put("ptUsage", pointClassDef.getPtUsage());
		}
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		pointClassDefList = new ArrayList<PointClassDef>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if(CollectionUtils.isEmpty(branchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("jinstId", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
//			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			params.put("cardIssuer", getSessionUser().getBranchNo());
			pointClassDefList = pointClassDefDAO.findPtClassByJinst(params);
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
			getPtUsageListByBaodeLimit();
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
			getPtUsageListByBaodeLimit();
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)||CollectionUtils.isNotEmpty(merchInfoList)||CollectionUtils.isNotEmpty(pointClassDefList)) {
			int length = branchList.size()+merchInfoList.size()+pointClassDefList.size();
			String[] jinstIds = new String[length];
			int j = 0;
			for(int i = 0; i<branchList.size(); i++){
				jinstIds[j++] = (branchList.get(i)).getBranchCode();
			}
			for(int i = 0; i<merchInfoList.size(); i++){
				jinstIds[j++] = (merchInfoList.get(i)).getMerchId();
			}
			for(int i = 0; i<pointClassDefList.size(); i++){
				jinstIds[j++] = (pointClassDefList.get(i)).getJinstId();
			}
			params.put("jinstIds", jinstIds);
		}
		
		this.page = pointClassDefDAO.findPointClassDef(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception {
		this.pointClassDef = (PointClassDef)this.pointClassDefDAO.findByPk(pointClassDef.getPtClass());
		String ptUseLimit = this.pointClassDef.getPtUseLimit();
		
		if(ptUseLimit!=null){
			this.pointClassDef.setPtUseLimit(cardTypeSetService.getPtUseLimit(ptUseLimit));
		}
		
		this.log("查询积分卡类型["+this.pointClassDef.getPtClass()+"]信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			params.put("cardIssuer", getSessionUser().getBranchNo());
		}else if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			params.put("cardIssuer", getSessionUser().getMerchantNo());
		}else{
			throw new BizException("非发卡机构或者商户不能操作。");
		}
		params.put("status", CommonState.NORMAL.getValue());
		merchGroupList = merchGroupDAO.findList(params);
		
		initPage();
		jinstTypeList = IssType.getCircleDefineType();
		ptUseLimitList = PtUseLimitType.getAll();
		return ADD;
	}
	
	public String add() throws Exception {
		
		UserInfo user = this.getSessionUser();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		// 登录用户为商户 
		if(roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())){ 
			pointClassDef.setJinstType(IssType.MERCHANT.getValue());
			pointClassDef.setJinstId(user.getMerchantNo());
			MerchInfo merchInfo = (MerchInfo)this.merchInfoDAO.findByPk(user.getMerchantNo());
			pointClassDef.setJinstName(merchInfo.getMerchName());
		}
		// 登录用户为发卡机构 
		else if(roleInfo.getRoleType().equals(RoleType.CARD.getValue())){ 
			String jinstType = pointClassDef.getJinstType();
			if(CommonHelper.isNotEmpty(jinstType) && jinstType.equals(IssType.CARD.getValue())){//通用
				pointClassDef.setJinstType(IssType.CARD.getValue());
				pointClassDef.setJinstId(user.getBranchNo());
			}
			pointClassDef.setCardIssuer(user.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(user.getBranchNo());
			pointClassDef.setJinstName(branchInfo.getBranchName());
		}
		else{
			throw new BizException("非发卡机构或者商户不能定义积分类型。");
		}
		
		this.cardTypeSetService.addPointClassDef(pointClassDef, ptUseLimitCodes);	
		
		String msg = "添加积分子类型[" + pointClassDef.getPtClass() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/cardTypeSet/pointClass/list.do", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue())||roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()))){ 
			throw new BizException("非发卡机构或者商户不能修改积分子类型。");
		}
		
		initPage();
		jinstTypeList = IssType.getAll();
		this.pointClassDef = (PointClassDef)this.pointClassDefDAO.findByPk(this.pointClassDef.getPtClass());
		
		BigDecimal ptDeprecRate = pointClassDef.getPtDeprecRate();
		BigDecimal ptDiscntRate = pointClassDef.getPtDiscntRate();
		if(ptDeprecRate!=null && StringUtils.isNotBlank(ptDeprecRate.toString())){
			ptDeprecRate = ptDeprecRate.multiply(new BigDecimal(100)).setScale(0);
			pointClassDef.setPtDeprecRate(ptDeprecRate);
		}
		if(ptDiscntRate!=null && StringUtils.isNotBlank(ptDiscntRate.toString())){
			ptDiscntRate = ptDiscntRate.multiply(new BigDecimal(100)).setScale(2);
			pointClassDef.setPtDiscntRate(ptDiscntRate);
		}
		
		this.ptUseLimitList = PtUseLimitType.getAll();
		String ptUseLimit = this.pointClassDef.getPtUseLimit();
		this.pointHasTypes = "";
		
		if(ptUseLimit!=null){
			List<String> ptUseLimitCodeList = this.cardTypeSetService.getPtUseLimitCode(ptUseLimit);
			for (String code : ptUseLimitCodeList) {
				this.pointHasTypes += code + ",";
			}
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.getCardIssuer();
		this.cardTypeSetService.modifyPointClassDef(pointClassDef, ptUseLimitCodes);
		String msg = "修改积分子类型[" + pointClassDef.getPtClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/pointClass/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		this.getCardIssuer();
		this.cardTypeSetService.deletePointClassDef(pointClassDef);
		String msg = "删除积分子类型[" + pointClassDef.getPtClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/cardTypeSet/pointClass/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		amtTypeList = AmtType.getAll();
		ptInstmMthdList = PtInstmMthdType.getAll();
		ptExchgRuleTypeList = PtExchgRuleType.getFullExchange();
		List branchTypeList = new LinkedList();
		branchTypeList.add(BranchType.CARD);
		cardIssuerList = branchInfoDAO.findByTypes(branchTypeList);
		
		getPtUsageListByBaodeLimit();

	}
	
	// 根据保得积分权限返回不同的积分使用方法列表
	private void getPtUsageListByBaodeLimit(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getSessionUser().getBranchNo());
		params.put("roleId", this.getSessionUser().getRole().getRoleId());
		params.put("limitId", "baodePointExcPara_add");
		
		if(rolePrivilegeDAO.hasPrivilege(params)){ // 如果有保得积分权限点
			ptUsageList = PtUsageType.getAll();	
		}
		else{
			ptUsageList = PtUsageType.getAllExcBaode();
		}
		
		/*if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){ //如果登录用户是商户，不显示保得积分类型
			ptUsageList = PtUsageType.getAllExcBaode();
		}*/
	}
	
	public PointClassDef getPointClassDef() {
		return pointClassDef;
	}

	public void setPointClassDef(PointClassDef pointClassDef) {
		this.pointClassDef = pointClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getJinstTypeList() {
		return jinstTypeList;
	}

	public void setJinstTypeList(Collection jinstTypeList) {
		this.jinstTypeList = jinstTypeList;
	}

	public Collection getAmtTypeList() {
		return amtTypeList;
	}

	public void setAmtTypeList(Collection amtTypeList) {
		this.amtTypeList = amtTypeList;
	}

	public Collection getPtUsageList() {
		return ptUsageList;
	}

	public void setPtUsageList(Collection ptUsageList) {
		this.ptUsageList = ptUsageList;
	}

	public Collection getPtInstmMthdList() {
		return ptInstmMthdList;
	}

	public void setPtInstmMthdList(Collection ptInstmMthdList) {
		this.ptInstmMthdList = ptInstmMthdList;
	}

	public Collection getPtExchgRuleTypeList() {
		return ptExchgRuleTypeList;
	}

	public void setPtExchgRuleTypeList(Collection ptExchgRuleTypeList) {
		this.ptExchgRuleTypeList = ptExchgRuleTypeList;
	}

	public Collection getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(Collection cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}
	
	private String getCardIssuer() throws BizException{
		UserInfo user = this.getSessionUser();
		String cardIssuer = null;
		
		if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){ //发卡机构
			cardIssuer = user.getBranchNo();
		}
		else if(user.getRole().getRoleType().equals(RoleType.MERCHANT.getValue())){ //商户
			cardIssuer = user.getMerchantNo();
		}
		else if(user.getRole().getRoleType().equals(RoleType.CENTER.getValue())){ //营运中心
			cardIssuer = "";
		}
		else{
			throw new BizException("用户角色不是营运中心、发卡机构或者商户,不允许进行操作");
		}
		return cardIssuer;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<PtUseLimitType> getPtUseLimitList() {
		return ptUseLimitList;
	}

	public void setPtUseLimitList(List<PtUseLimitType> ptUseLimitList) {
		this.ptUseLimitList = ptUseLimitList;
	}

	public String[] getPtUseLimitCodes() {
		return ptUseLimitCodes;
	}

	public void setPtUseLimitCodes(String[] ptUseLimitCodes) {
		this.ptUseLimitCodes = ptUseLimitCodes;
	}

	public String getPointHasTypes() {
		return pointHasTypes;
	}

	public void setPointHasTypes(String pointHasTypes) {
		this.pointHasTypes = pointHasTypes;
	}
	
	public List<MerchGroup> getMerchGroupList() {
		return merchGroupList;
	}

	public void setMerchGroupList(List<MerchGroup> merchGroupList) {
		this.merchGroupList = merchGroupList;
	}

}
