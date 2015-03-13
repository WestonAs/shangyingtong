package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.type.MembDegradeMthdType;
import gnete.card.entity.type.MembUpgradeMthdType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductTemplateService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
/**
 * @File: SingleProductAction.java
 * 
 * @description:单机产品会员模板类型
 * 
 * @copyright: (c) 2012 YLINK INC.
 * @author: aps-hc
 * @version: 1.0
 * @since 1.0 2012-2-23
 */
public class SingleProductMembAction extends BaseAction {
	@Autowired
	private SingleProductTemplateService singleProductTemplateService;
	@Autowired
	private MembClassTempDAO membClassTempDAO;
	private Paginater page;
	private MembClassTemp membClassTemp;
	private String membClass;
	private Collection membUpgradeMthdTypeList; // 升级方式类型
	private Collection membDegradeMthdTypeList; // 保级方式类型
	private static final String  DEFAULT_SUCCESS_URL="/pages/singleProduct/memb/list.do?goBack=goBack";
	
	private String[] membClassName;
	private String[] membUpgradeThrhd;
	private String[] membDegradeThrhd;
	private List<BranchInfo> cardBranchList;
	List<String> membClassNameList;
	
	List<MembClassTemp> membClassList;
	
	
	@Override
	public String execute() throws Exception {
		

		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} 
		else {
			throw new BizException("没有权限查看会员类型模板列表");
		}
		
		
		if (membClassTemp != null) {
			params.put("membLevel", membClassTemp.getMembLevel());
			params.put("className", MatchMode.ANYWHERE.toMatchString(membClassTemp.getClassName()));
		}
		page = membClassTempDAO.findMembClassTemp(params, getPageNumber(), getPageSize());
		return LIST;
	}
	
	public String showAdd()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能定义会员类型模板。");
		}
		initPage();
		return ADD;
	}
	/**
	 *	新增会员模板定义 
	 */
	public String add()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能修改会员类型模板。");
		}
		String membClassNameMerge = this.getMembClassName().length > 0 
			? StringUtils.trimWhitespace(this.getMembClassName()[0]): null ;
		for(int i=1; i < this.membClassName.length; i++){
			membClassNameMerge = membClassNameMerge.concat(",");
			membClassNameMerge = membClassNameMerge.concat(StringUtils.trimWhitespace(membClassName[i]));
		}
		this.membClassTemp.setMembClassName(membClassNameMerge);//会员级别名称
		
		if(this.getMembUpgradeThrhd()!=null){
			String membUpgradeThrhdMerge = this.getMembUpgradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembUpgradeThrhd()[0]) : null;
			for(int i=1; i < this.membUpgradeThrhd.length; i++){
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(",");
				membUpgradeThrhdMerge = membUpgradeThrhdMerge.concat(StringUtils.trimWhitespace(membUpgradeThrhd[i]));
			}
			this.membClassTemp.setMembUpgradeThrhd(membUpgradeThrhdMerge);//会员升级条件
		}
		
		if(this.getMembDegradeThrhd()!=null){
			String membDegradeThrhdMerge = this.getMembDegradeThrhd().length>0 
				? StringUtils.trimWhitespace(this.getMembDegradeThrhd()[0]) : null;
			for(int i=1; i < this.membDegradeThrhd.length; i++){
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(",");
				membDegradeThrhdMerge = membDegradeThrhdMerge.concat(StringUtils.trimWhitespace(membDegradeThrhd[i]));
			}
			this.membClassTemp.setMembDegradeThrhd(membDegradeThrhdMerge);//会员保级条件
		}
		
		singleProductTemplateService.addMembClassTemp(membClassTemp,this.getSessionUser());
		
		String msg = "定义会员类型模板["+this.membClassTemp.getMembClass()+"]成功！";
		this.addActionMessage(DEFAULT_SUCCESS_URL, msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	public String showModify()throws Exception{
		initPage();
		membClassTemp = (MembClassTemp)this.membClassTempDAO.findByPk(membClassTemp.getMembClass());
		return MODIFY;
	}	
	public String modify()throws Exception{
		this.singleProductTemplateService.modifyMembClassTemp(membClassTemp,this.getSessionUser());
		String msg = "修改会员类型模板[" + membClassTemp.getMembClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String delete()throws Exception{
		System.out.println("membClassTemp.getMembClass():"+membClassTemp.getMembClass());
		this.singleProductTemplateService.deleteMembClassTemp(membClassTemp);
		
		String msg = "删除会员类型模板[" + membClassTemp.getMembClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String detail()throws Exception{
		this.membClassTemp = (MembClassTemp)this.membClassTempDAO.findByPk(membClassTemp.getMembClass());
		this.log("查询积分卡类型["+this.membClassTemp.getMembClass()+"]信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	private void initPage() {
		this.membUpgradeMthdTypeList = MembUpgradeMthdType.getAll(); //加载会员升级方式作为下拉列表
		this.membDegradeMthdTypeList = MembDegradeMthdType.getAll(); //加载会员保级方式作为下拉列表
	}
	
	public MembClassTemp getMembClassTemp() {
		return membClassTemp;
	}

	public void setMembClassTemp(MembClassTemp membClassTemp) {
		this.membClassTemp = membClassTemp;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getMembDegradeMthdTypeList() {
		return membDegradeMthdTypeList;
	}

	public void setMembDegradeMthdTypeList(Collection membDegradeMthdTypeList) {
		this.membDegradeMthdTypeList = membDegradeMthdTypeList;
	}

	public Collection getMembUpgradeMthdTypeList() {
		return membUpgradeMthdTypeList;
	}

	public void setMembUpgradeMthdTypeList(Collection membUpgradeMthdTypeList) {
		this.membUpgradeMthdTypeList = membUpgradeMthdTypeList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getMembClass() {
		return membClass;
	}

	public void setMembClass(String membClass) {
		this.membClass = membClass;
	}

	public List<MembClassTemp> getMembClassList() {
		return membClassList;
	}

	public void setMembClassList(List<MembClassTemp> membClassList) {
		this.membClassList = membClassList;
	}

	public String[] getMembClassName() {
		return membClassName;
	}

	public void setMembClassName(String[] membClassName) {
		this.membClassName = membClassName;
	}

	public List<String> getMembClassNameList() {
		return membClassNameList;
	}

	public void setMembClassNameList(List<String> membClassNameList) {
		this.membClassNameList = membClassNameList;
	}

	public String[] getMembDegradeThrhd() {
		return membDegradeThrhd;
	}

	public void setMembDegradeThrhd(String[] membDegradeThrhd) {
		this.membDegradeThrhd = membDegradeThrhd;
	}

	public String[] getMembUpgradeThrhd() {
		return membUpgradeThrhd;
	}

	public void setMembUpgradeThrhd(String[] membUpgradeThrhd) {
		this.membUpgradeThrhd = membUpgradeThrhd;
	}

}
