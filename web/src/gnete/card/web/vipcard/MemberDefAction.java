package gnete.card.web.vipcard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.MembDegradeMthdType;
import gnete.card.entity.type.MembUpgradeMthdType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VipCardService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;

public class MemberDefAction extends BaseAction {

	@Autowired
	private MembClassDefDAO membClassDefDao;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VipCardService vipCardService;

	private MembClassDef membClassDef;
	private Paginater page;
	private String membClass;
	private Collection membUpgradeMthdTypeList; // 升级方式类型
	private Collection membDegradeMthdTypeList; // 保级方式类型
	
	private String[] membClassName;
	private String[] membUpgradeThrhd;
	private String[] membDegradeThrhd;
	private List<BranchInfo> cardBranchList;
	List<String> membClassNameList;
	
	List<MembClassDef> membClassList;

	@Override
	public String execute() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				params.put("cardIssuer", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if (membClassDef != null) {
			params.put("membClass", membClassDef.getMembClass());
			params.put("className", MatchMode.ANYWHERE.toMatchString(membClassDef.getClassName()));
		}
		
		this.page = this.membClassDefDao.findMembClassDef(params,this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	//取得会员定义申请的明细
	public String detail() throws Exception {
		
		this.membClassDef = (MembClassDef) this.membClassDefDao.findByPk(this.membClassDef.getMembClass());
		
//		this.log("查询会员定义["+this.membClassDef.getMembClass()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())){ 
			throw new BizException("非发卡机构不能操作。");
		}
		initPage();
		this.membClassDef = (MembClassDef)this.membClassDefDao.findByPk(this.membClassDef.getMembClass());
		String membClassNameMerge = this.membClassDef.getMembClassName();
		String membUpgradeThrhdMerge = this.membClassDef.getMembUpgradeThrhd();
		String membDegradeThrhdMerge = this.membClassDef.getMembDegradeThrhd();
		/*String membUpgradeMthd = this.membClassDef.getMembUpgradeMthd();
		String membDegradeMthd = this.membClassDef.getMembDegradeMthd();*/
		
		if(membClassNameMerge.split(",")!=null){
			this.membClassName = membClassNameMerge.split(",");
		} else {
			this.membClassName = new String[1];
			this.membClassName[0] = membClassNameMerge;
		}
		
		// 如果升级条件不为空
		if(membUpgradeThrhdMerge!=null){
			
			if (membUpgradeThrhdMerge.split(",") != null) {
				this.membUpgradeThrhd = membUpgradeThrhdMerge.split(",");
			} else {
				this.membUpgradeThrhd = new String[1];
				this.membUpgradeThrhd[0] = membUpgradeThrhdMerge;
			}
		} else {
			
		}
		
		// 如果保级条件不为空
		if(membDegradeThrhdMerge!=null){
			if (membDegradeThrhdMerge.split(",") != null) {
				this.membDegradeThrhd = membDegradeThrhdMerge.split(",");
			} else {
				this.membDegradeThrhd = new String[1];
				this.membDegradeThrhd[0] = membDegradeThrhdMerge;
			}
		}
		
		membClassList = new ArrayList<MembClassDef>();
		
		for (int i = 0; i < membClassName.length; i++) {
			MembClassDef classDef = new MembClassDef();
			
			String membClassNameStr = membClassName[i];
			classDef.setMembClassName(membClassNameStr);
			
			String membUpgradeThrhdStr = null;
			if(membUpgradeThrhdMerge!=null){
				membUpgradeThrhdStr = membUpgradeThrhd[i];
				
			} 
			classDef.setMembUpgradeThrhd(membUpgradeThrhdStr);
			
			String membDegradeThrhdStr = null;
			if(membDegradeThrhdMerge!=null){
				membDegradeThrhdStr = membDegradeThrhd[i];
			} 
			classDef.setMembDegradeThrhd(membDegradeThrhdStr);
			
			membClassList.add(classDef);
		}
		
		return MODIFY;
	}
	
	// 修改会员定义信息
	public String modify() throws Exception {
		String membClassNameMerge = this.getMembClassName().length > 0 
			? StringUtils.trimWhitespace(this.getMembClassName()[0]) : null ;
		for(int i=1; i < this.membClassName.length; i++){
			membClassNameMerge = membClassNameMerge.concat(",");
			membClassNameMerge = membClassNameMerge.concat(StringUtils.trimWhitespace(membClassName[i]));
		}	
		this.membClassDef.setMembClassName(membClassNameMerge);
			
		if(this.getMembUpgradeThrhd()!=null){
			String membUpgradeThrhdMerge = this.getMembUpgradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembUpgradeThrhd()[0]) : null;
			for(int i=1; i < this.membUpgradeThrhd.length; i++){
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(",");
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(StringUtils.trimWhitespace(membUpgradeThrhd[i]));
			}
			this.membClassDef.setMembUpgradeThrhd(membUpgradeThrhdMerge);
		} else {
			this.membClassDef.setMembUpgradeThrhd("");
		}
		
		if(this.getMembDegradeThrhd()!=null){
			String membDegradeThrhdMerge = this.getMembDegradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembDegradeThrhd()[0]) : null;
			for(int i=1; i < this.membDegradeThrhd.length; i++){
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(",");
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(StringUtils.trimWhitespace(membDegradeThrhd[i]));
			}
			this.membClassDef.setMembDegradeThrhd(membDegradeThrhdMerge);
		} else {
			this.membClassDef.setMembDegradeThrhd("");
		}
		
		this.membClassDef.setCardIssuer(this.membClassDef.getCardIssuer());
		
		this.vipCardService.modifyMembClassDef(this.membClassDef, this.getSessionUserCode());
		
		String msg = "修改会员定义["+this.membClassDef.getMembClass()+"]成功！";
		this.addActionMessage("/vipCard/vipCardDef/list.do", msg );
		this.log(msg, UserLogType.UPDATE);

		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())){ 
			throw new BizException("非发卡机构不能操作。");
		}
		initPage();
		return ADD;
	}	
	
	// 新增会员定义
	public String add() throws Exception {		
		
		String membClassNameMerge = this.getMembClassName().length > 0 
			? StringUtils.trimWhitespace(this.getMembClassName()[0]): null ;
		for(int i=1; i < this.membClassName.length; i++){
			membClassNameMerge = membClassNameMerge.concat(",");
			membClassNameMerge = membClassNameMerge.concat(StringUtils.trimWhitespace(membClassName[i]));
		}
		this.membClassDef.setMembClassName(membClassNameMerge);
		
		if(this.getMembUpgradeThrhd()!=null){
			String membUpgradeThrhdMerge = this.getMembUpgradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembUpgradeThrhd()[0]) : null;
			for(int i=1; i < this.membUpgradeThrhd.length; i++){
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(",");
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(StringUtils.trimWhitespace(membUpgradeThrhd[i]));
			}
			this.membClassDef.setMembUpgradeThrhd(membUpgradeThrhdMerge);
		}
		
		if(this.getMembDegradeThrhd()!=null){
			String membDegradeThrhdMerge = this.getMembDegradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembDegradeThrhd()[0]) : null;
			for(int i=1; i < this.membDegradeThrhd.length; i++){
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(",");
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(StringUtils.trimWhitespace(membDegradeThrhd[i]));
			}
			this.membClassDef.setMembDegradeThrhd(membDegradeThrhdMerge);
		}
		
		this.membClassDef.setCardIssuer(this.getSessionUser().getBranchNo());
		this.membClassDef.setJinstId(this.getSessionUser().getBranchNo());
		this.membClassDef.setJinstType(IssType.CARD.getValue());
		this.vipCardService.addmMembClassDef(membClassDef, this.getSessionUserCode(),this.getSessionUser().getBranchNo());
		
		//启动单个流程
		//this.workflowService.startFlow(membClassDef, "membDefAdapter", membClassDef.getMembClass(), this.getSessionUser());
		
		String msg = "定义会员["+this.membClassDef.getMembClass()+"]成功！";
		this.addActionMessage("/vipCard/vipCardDef/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 删除会员定义
	public String delete() throws Exception {
		
		this.vipCardService.delete(this.membClass);
		
		String msg = "删除会员定义[" +this.getMembClass()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/vipCard/vipCardDef/list.do", msg);
		return SUCCESS;
	}
	
	// 礼品定义审核列表
	public String membDefCheckList() throws Exception{
		
		// 首先调用流程引擎，得到我的待审批的工单ID
		String[] membDefIds = this.workflowService.getMyJob(Constants.WORKFLOW_MEMB_CLASS_DEF, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(membDefIds)) {
			return "membDefCheckList";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", membDefIds);
		this.page = this.membClassDefDao.findMembClassDef(params, this.getPageNumber(), this.getPageSize());
		return "membDefCheckList";
	}

	//取得礼品定义申请的明细，流程审核人查看的
	public String membDefCheckDetail() throws Exception {
		
		this.membClassDef = (MembClassDef) this.membClassDefDao.findByPk(this.membClassDef.getMembClass());
		this.log("查询会员定义申请["+this.membClassDef.getMembClass()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	
	private void initPage() {
		this.membUpgradeMthdTypeList = MembUpgradeMthdType.getAll(); //加载会员升级方式作为下拉列表
		this.membDegradeMthdTypeList = MembDegradeMthdType.getAll(); //加载会员保级方式作为下拉列表
	}

	public MembClassDef getMembClassDef() {
		return membClassDef;
	}

	public void setMembClassDef(MembClassDef membClassDef) {
		this.membClassDef = membClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public void setVipCardService(VipCardService vipCardService) {
		this.vipCardService = vipCardService;
	}

	public VipCardService getVipCardService() {
		return vipCardService;
	}
	
	public Collection getMembUpgradeMthdTypeList() {
		return membUpgradeMthdTypeList;
	}

	public void setMembUpgradeMthdTypeList(Collection membUpgradeMthdTypeList) {
		this.membUpgradeMthdTypeList = membUpgradeMthdTypeList;
	}

	public Collection getMembDegradeMthdTypeList() {
		return membDegradeMthdTypeList;
	}

	public void setMembDegradeMthdTypeList(Collection membDegradeMthdTypeList) {
		this.membDegradeMthdTypeList = membDegradeMthdTypeList;
	}

	public void setMembClass(String membClass) {
		this.membClass = membClass;
	}

	public String getMembClass() {
		return membClass;
	}

	public String[] getMembClassName() {
		return membClassName;
	}

	public void setMembClassName(String[] membClassName) {
		this.membClassName = membClassName;
	}

	public String[] getMembUpgradeThrhd() {
		return membUpgradeThrhd;
	}

	public void setMembUpgradeThrhd(String[] membUpgradeThrhd) {
		this.membUpgradeThrhd = membUpgradeThrhd;
	}

	public String[] getMembDegradeThrhd() {
		return membDegradeThrhd;
	}

	public void setMembDegradeThrhd(String[] membDegradeThrhd) {
		this.membDegradeThrhd = membDegradeThrhd;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<String> getMembClassNameList() {
		return membClassNameList;
	}

	public void setMembClassNameList(List<String> membClassNameList) {
		this.membClassNameList = membClassNameList;
	}

	public List<MembClassDef> getMembClassList() {
		return membClassList;
	}

	public void setMembClassList(List<MembClassDef> membClassList) {
		this.membClassList = membClassList;
	}

}
