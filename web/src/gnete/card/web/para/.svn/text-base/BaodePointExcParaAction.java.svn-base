package gnete.card.web.para;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BaodePointExcParaDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BaodePointExcPara;
import gnete.card.entity.BaodePointExcParaKey;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.type.PtUsageType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BaodeService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 保得积分返还参数定义
 * @author aps-lib
 * @history 2011-8-17
 */
public class BaodePointExcParaAction extends BaseAction{

	@Autowired
	private BaodePointExcParaDAO baodePointExcParaDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private BaodeService baodeService;
	
	private BaodePointExcPara baodePointExcPara;
	private Paginater page;
	private boolean showAll = false;
	private boolean showMerch = false;
	private List<BranchInfo> branchList;
	private String branchCode;
	private String merNo;
	private String ptClass;
	private String branchName;
	private List<PointClassDef> ptClassList;
	private String ptClassName;
	private boolean firstFlag = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (baodePointExcPara != null) {
			params.put("branchCode", baodePointExcPara.getBranchCode());
			params.put("merNo", baodePointExcPara.getMerNo());
			params.put("ptClass", baodePointExcPara.getPtClass());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(baodePointExcPara.getBranchName()));
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(baodePointExcPara.getMerchName()));
			params.put("ptClassName", MatchMode.ANYWHERE.toMatchString(baodePointExcPara.getPtClassName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			this.showAll = true;
			this.showMerch = true;
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			this.showAll = true;
			this.showMerch = true;
		} 
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())){
			params.put("branchCode", getSessionUser().getBranchNo());
			this.branchList = new ArrayList<BranchInfo>();
			branchList.add( (BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			this.showAll = false;
			this.showMerch = true;
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("merNo", getSessionUser().getMerchantNo());
			this.showAll = true;
			this.showMerch = false;
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.baodePointExcParaDAO.findBaodePointExcPara(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、发卡机构部门不能操作。");
		} 
		
		// 发卡机构一定需要先设置自己的积分返还率
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", this.getSessionUser().getBranchNo());
		params.put("merNo", this.getSessionUser().getBranchNo());
		List<BaodePointExcPara> paraList = this.baodePointExcParaDAO.findBaodePointExcParaList(params);
		
		this.firstFlag = paraList.isEmpty() ? true : false;  
		
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		this.branchName = branchInfo.getBranchName();
		this.branchCode = this.getSessionUser().getBranchNo();
		
		params.clear();
		params.put("jinstId", this.getSessionUser().getBranchNo());
		params.put("ptUsage", PtUsageType.BAODE_POINT.getValue());
		this.ptClassList = this.pointClassDefDAO.findPtClassByJinst(params);
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		this.baodePointExcPara.setBranchCode(this.getBranchCode());
		
		if(firstFlag && baodePointExcPara.getMerNo() == null){ // 如果是第一次添加积分返还参数
			this.baodePointExcPara.setMerNo(this.getBranchCode()); // 商户号填的是添加参数定义的机构号
		}
		
		this.baodeService.addBaodePointExcPara(this.baodePointExcPara, this.getSessionUserCode());	
		
		String msg = "新增发卡机构[" + baodePointExcPara.getBranchCode() + "]、商户[" + 
						baodePointExcPara.getMerNo() + "]、积分类型" +
						"[" + baodePointExcPara.getPtClass() + "]的保得积分返还参数定义成功！";
		
		this.log(msg, UserLogType.ADD);
		addActionMessage("/para/baodePointExcPara/list.do", msg);
		
		return SUCCESS;
	}

	public String showModify() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、发卡机构部门不能操作。");
		} 
		
		BaodePointExcParaKey key = new BaodePointExcParaKey();
		key.setBranchCode(this.getBranchCode());
		key.setMerNo(this.getMerNo());
		key.setPtClass(this.getPtClass());
		
		this.baodePointExcPara = (BaodePointExcPara) this.baodePointExcParaDAO.findByPk(key);
		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(this.baodePointExcPara.getPtClass());
		this.baodePointExcPara.setPtClassName(pointClassDef.getClassName());
		return MODIFY;
	}
	
	public String modify() throws Exception {
		
		Assert.isTrue(baodePointExcPara.getBranchCode()!=null && baodePointExcPara.getMerNo()!=null && 
						baodePointExcPara.getPtClass()!=null, "发卡机构、商户、积分类型不能为空");
		
		this.baodeService.modifyBaodePointExcPara(baodePointExcPara, this.getSessionUserCode());
		
		String msg = "修改发卡机构[" + baodePointExcPara.getBranchCode() + "]、商户[" + 
		baodePointExcPara.getMerNo() + "]、积分类型" +
		"[" + baodePointExcPara.getPtClass() + "]的保得积分返还参数定义成功！";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/baodePointExcPara/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、发卡机构部门不能操作。");
		} 
		
		Assert.isTrue(this.getBranchCode()!=null && this.getMerNo()!=null && 
						this.getPtClass()!=null, "发卡机构、商户、积分类型不能为空");
		
		BaodePointExcParaKey key = new BaodePointExcParaKey();
		key.setBranchCode(this.getBranchCode());
		key.setMerNo(this.getMerNo());
		key.setPtClass(this.getPtClass());
		
		this.baodeService.deleteBaodePointExcPara(key);
		
		String msg = "删除发卡机构[" + this.getBranchCode() + "]、商户[" + 
						this.getMerNo() + "]、积分类型" +
						"[" + this.getPtClass() + "]的保得积分返还参数定义成功！";
		
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/para/baodePointExcPara/list.do", msg);
		return SUCCESS;
	}
	
	public BaodeService getBaodeService() {
		return baodeService;
	}

	public void setBaodeService(BaodeService baodeService) {
		this.baodeService = baodeService;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<PointClassDef> getPtClassList() {
		return ptClassList;
	}

	public void setPtClassList(List<PointClassDef> ptClassList) {
		this.ptClassList = ptClassList;
	}

	public BaodePointExcPara getBaodePointExcPara() {
		return baodePointExcPara;
	}

	public void setBaodePointExcPara(BaodePointExcPara baodePointExcPara) {
		this.baodePointExcPara = baodePointExcPara;
	}

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}

	public boolean isFirstFlag() {
		return firstFlag;
	}

	public void setFirstFlag(boolean firstFlag) {
		this.firstFlag = firstFlag;
	}

}
