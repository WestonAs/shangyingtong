package gnete.card.web.vipcard;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembInfoRegDAO;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembInfoReg;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.EducationType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SexType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VipCardService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员资料登记
 */
public class MembInfoRegAction extends BaseAction{

	@Autowired
	private MembInfoRegDAO membInfoRegDao;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VipCardService vipCardService;
	
	private MembInfoReg membInfoReg;
	
	private Paginater page;
	
	private Collection credTypeList;
	private Collection sexTypeList;
	private Collection educationTypeList;
	
	/** 会员类型 */
	private List<MembClassDef> membClassDefList; 
	/** 会员资料批次列表 */
	private List<MembInfoReg> membInfoIdList;

	private File upload;
	private String uploadFileName;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (membInfoReg != null) {
			params.put("custName", membInfoReg.getCustName());
			params.put("membClass", membInfoReg.getMembClass());
			params.put("membInfoId", membInfoReg.getMembInfoId());
			params.put("credNo", membInfoReg.getCredNo());
			params.put("mobileNo", membInfoReg.getMobileNo());		
			params.put("telNo", membInfoReg.getTelNo());		
		}
		// 运营中心可以看到所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			membInfoIdList = membInfoRegDao.findAll();
		}
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("cardIssuer", getSessionUser().getBranchNo());
			membInfoIdList = membInfoRegDao.findMembInfoIdList(params);
		} else {
			throw new BizException("没有权限查询会员登记资料");
		}
		membClassDefList = vipCardService.loadMtClass(this.getLoginBranchCode());
		this.page = this.membInfoRegDao.findPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询会员登记资料列表成功");
		
		return LIST;
	}
	
	//取得会员登记资料的明细
	public String detail() throws Exception {
		// 运营机构可以看到所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())
				||RoleType.FENZHI.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
		} else {
			throw new BizException("没有权限查询会员登记资料");
		}
		
		this.membInfoReg = (MembInfoReg) this.membInfoRegDao.findByPk(membInfoReg);
		
		this.log("查询会员登记资料["+this.membInfoReg.getMembInfoRegId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		initPage();
		
		membClassDefList = vipCardService.loadMtClass(getSessionUser().getBranchNo());
		return ADD;
	}
	
	// 新增会员登记
	public String add() throws Exception {			
		//保存数据
		this.vipCardService.addMembInfoReg(membInfoReg, this.getSessionUser());
		
		String msg = "添加会员登记[" + this.membInfoReg.getMembInfoRegId() + "]成功！";
		this.addActionMessage("/vipCard/membInfoReg/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showAddBat() throws Exception {
		initPage();
		return "addBat";
	}
	
	// 新增对象操作
	public String addBat() throws Exception {
		
		List<MembInfoReg> unInsertList = this.vipCardService.addMembInfoRegBat(upload, this.getUploadFileName(), this.getSessionUser());
		StringBuilder sb = new StringBuilder(128);
		if(unInsertList.size() == 0){
			sb.append("文件批量添加会员登记信息全部成功。");
		} else{
			for(MembInfoReg membInfoReg : unInsertList){
				sb.append(membInfoReg.getCustName()).append(",").append(membInfoReg.getAddress());
			}
			sb.append(System.getProperty("line.separator"));
			sb.append("以上会员登记信息记录有误，不能录入。");
			throw new BizException(sb.toString());
		}
		this.addActionMessage("/vipCard/membInfoReg/list.do", sb.toString());
		this.log(sb.toString(), UserLogType.ADD);
		
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		initPage();
		this.membInfoReg = (MembInfoReg)this.membInfoRegDao.findByPk(membInfoReg);
		return MODIFY;
	}
	
	// 修改会员登记信息
	public String modify() throws Exception {
		this.vipCardService.modifyMembInfoReg(membInfoReg, this.getSessionUserCode());
		
		this.addActionMessage("/vipCard/membInfoReg/list.do?goBack=goBack", "修改[" + membInfoReg.getMembInfoId() + "]会员登记资料成功！");	
		return SUCCESS;
	}
	
	// 删除会员登记记录
	public String delete() throws Exception {
		
		this.vipCardService.deleteMembInfoReg(membInfoReg);
		
		String msg = "删除会员登记[" +membInfoReg.getMembInfoRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/vipCard/membInfoReg/list.do?goBack=goBack", msg);
		
		return SUCCESS;
	}
	
	private void initPage() throws Exception {
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
		} else {
			throw new BizException("没有权限维护会员登记资料");
		}
		
		//加载证件类型作为下拉列表
		this.credTypeList= CertType.getAll();
		//加载性别作为下拉列表
		this.sexTypeList = SexType.getAll();
		//加载学历作为下拉列表
		this.educationTypeList = EducationType.getAll();
	}
	
	/**
	 * 根据会员类型ID得到会员级别列表。（返回到页面为String字符串）
	 * @throws Exception
	 */
	public void loadMtClassName() throws Exception {
		String membClass = request.getParameter("membClass");
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		if (StringUtils.isNotEmpty(membClass)) {
			MembClassDef membClassDef = (MembClassDef)this.membClassDefDAO.findByPk(membClass);

			String membClassName = membClassDef.getMembClassName();
			String[] membClassNames = membClassName.split("\\,");
			int i=1;//会员级别
			for (String membClassNameItem : membClassNames){
				sb.append("<option value=\"").append(i++).append("\">")
						//.append(mtClass.getMembClass()).append("-")
						.append(membClassNameItem).append("</option>");
			}
		}
		this.respond(sb.toString());
	}
	
	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setMembInfoReg(MembInfoReg membInfoReg) {
		this.membInfoReg = membInfoReg;
	}

	public MembInfoReg getMembInfoReg() {
		return membInfoReg;
	}

	public void setCredTypeList(Collection credTypeList) {
		this.credTypeList = credTypeList;
	}

	public Collection getCredTypeList() {
		return credTypeList;
	}

	public void setSexTypeList(Collection sexTypeList) {
		this.sexTypeList = sexTypeList;
	}

	public Collection getSexTypeList() {
		return sexTypeList;
	}
	
	public Collection getEducationTypeList() {
		return educationTypeList;
	}

	public void setEducationTypeList(Collection educationTypeList) {
		this.educationTypeList = educationTypeList;
	}
	
	public List<MembClassDef> getMembClassDefList() {
		return membClassDefList;
	}

	public void setMembClassDefList(List<MembClassDef> membClassDefList) {
		this.membClassDefList = membClassDefList;
	}
	
	public List<MembInfoReg> getMembInfoIdList() {
		return membInfoIdList;
	}

	public void setMembInfoIdList(List<MembInfoReg> membInfoIdList) {
		this.membInfoIdList = membInfoIdList;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setMembClassDefDAO(MembClassDefDAO membClassDefDAO) {
		this.membClassDefDAO = membClassDefDAO;
	}

	public MembClassDefDAO getMembClassDefDAO() {
		return membClassDefDAO;
	}

	public void setBranchInfoDAO(BranchInfoDAO branchInfoDAO) {
		this.branchInfoDAO = branchInfoDAO;
	}

	public BranchInfoDAO getBranchInfoDAO() {
		return branchInfoDAO;
	}
}
