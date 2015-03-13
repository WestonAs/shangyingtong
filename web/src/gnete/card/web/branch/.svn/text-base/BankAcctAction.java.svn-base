package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.InsBankacctDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.Area;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.InsBankacct;
import gnete.card.entity.InsBankacctKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.BankAcctType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 银行账户信息
 * 
 * @author aps-lib
 * @history 2011-8-23
 */
public class BankAcctAction extends BaseAction{
	@Autowired
	private InsBankacctDAO insBankacctDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private AreaDAO areaDAO;
	@Autowired
	private BranchService branchService;
	
	private InsBankacct insBankacct;
	private Paginater page;
	private List<IssType> issTypeList;
	private List<BankAcctType> bankAcctTypeList;
	private List<AcctType> acctTypeList;
	private String bankAcctType;
	private String insCode;
	private String type;
	private String typeName;
	private String accAreaName;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		initPage();

		if (insBankacct != null) {
			params.put("insCode", insBankacct.getInsCode());
			params.put("insName", MatchMode.ANYWHERE.toMatchString(insBankacct.getInsName()));
			params.put("accNo", insBankacct.getAccNo());
			params.put("bankAcctType", insBankacct.getBankAcctType());
			params.put("acctType", insBankacct.getAcctType());
		}
		
		// 营运中心、中心部门
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 分支机构看到其管理的发卡机构和商户的银行账户
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			params.put("parent", this.getSessionUser().getBranchNo());
		}
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			params.put("insCode", this.getSessionUser().getBranchNo());
		}
		// 商户
		else if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			params.put("insCode", this.getSessionUser().getMerchantNo());
		} 
		else {
			throw new BizException("没有权限查询！");
		}
		
		this.page = this.insBankacctDAO.findInsBankAcct(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		
		InsBankacctKey key = new InsBankacctKey();
		key.setBankAcctType(this.getBankAcctType());
		key.setInsCode(this.getInsCode());
		key.setType(this.getType());
		
		this.insBankacct = (InsBankacct) this.insBankacctDAO.findByPk(key);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		
		initPage();
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.addInsBankacct(insBankacct, this.getSessionUserCode());
		String msg = "新增[" + insBankacct.getInsCode() + "]银行账户成功。";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/pages/bankAcct/list.do", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		initPage();
		InsBankacctKey key = new InsBankacctKey();
		key.setBankAcctType(this.getBankAcctType());
		key.setInsCode(this.getInsCode());
		key.setType(this.getType());
		
		this.insBankacct = (InsBankacct) this.insBankacctDAO.findByPk(key);
		this.typeName = insBankacct.getTypeName();
		
		Area area = (Area) this.areaDAO.findByPk(this.insBankacct.getAccAreaCode());
		if (area != null) {
			this.setAccAreaName(area.getAreaName());
		}
		
		String insName = "";
		if(IssType.CARD.getValue().equals(this.insBankacct.getType())){ // 发卡机构
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.insBankacct.getInsCode());
			insName = branchInfo.getBranchName();
		}
		else { // 商户
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(this.insBankacct.getInsCode());
			insName = merchInfo.getMerchName();
		}
			
		this.insBankacct.setInsName(insName);
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.modifyInsBankacct(insBankacct, this.getSessionUserCode());
		String msg = "修改[" + insBankacct.getInsCode() + "]银行账户成功。";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/pages/bankAcct/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		
		InsBankacctKey key = new InsBankacctKey();
		key.setBankAcctType(this.getBankAcctType());
		key.setInsCode(this.getInsCode());
		key.setType(this.getType());
		this.insBankacctDAO.delete(key);
		
		String msg = "删除[" + insBankacct.getInsCode() + "]银行账户成功。";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pages/bankAcct/list.do", msg);
		return SUCCESS;
	}

	private void initPage() {
		this.issTypeList = IssType.getAll();
		this.bankAcctTypeList = BankAcctType.getAll();
		this.acctTypeList = AcctType.getAll();
	}
	
	/**
	 * 返回登录机构
	 * @return
	 */
	public String getParent() {
		if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			return super.getSessionUser().getBranchNo();
		}
		return "";
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public InsBankacct getInsBankacct() {
		return insBankacct;
	}

	public void setInsBankacct(InsBankacct insBankacct) {
		this.insBankacct = insBankacct;
	}

	public List<IssType> getIssTypeList() {
		return issTypeList;
	}

	public void setIssTypeList(List<IssType> issTypeList) {
		this.issTypeList = issTypeList;
	}

	public List<BankAcctType> getBankAcctTypeList() {
		return bankAcctTypeList;
	}

	public void setBankAcctTypeList(List<BankAcctType> bankAcctTypeList) {
		this.bankAcctTypeList = bankAcctTypeList;
	}

	public String getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAccAreaName() {
		return accAreaName;
	}

	public void setAccAreaName(String accAreaName) {
		this.accAreaName = accAreaName;
	}

	public List<AcctType> getAcctTypeList() {
		return acctTypeList;
	}

	public void setAcctTypeList(List<AcctType> acctTypeList) {
		this.acctTypeList = acctTypeList;
	}

}
