package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * DeptAction.java.
 *  部门管理
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class DeptAction extends BaseAction {

	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private BranchService branchService;

	private Paginater page;
	private DepartmentInfo departmentInfo;
	
	private String privileges;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (departmentInfo != null) {
			params.put("deptId", departmentInfo.getDeptId());
			params.put("deptName", MatchMode.ANYWHERE.toMatchString(departmentInfo.getDeptName()));
		}
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CARD.getValue())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("只有运营中心和发卡机构才能查看部门(网点)信息");
		}
		this.page = this.departmentInfoDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(this.departmentInfo.getDeptId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		// 只有运营中心和发卡机构才能看到该菜单
		if ( !(RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD.getValue().equals(this.getLoginRoleType()))){
			throw new BizException("只有运营中心和发卡机构才能添加部门");
		}
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		if (getLoginRoleType().equals(RoleType.CENTER.getValue())
				|| getLoginRoleType().equals(RoleType.CARD.getValue())){
			this.departmentInfo.setBranchNo(this.getSessionUser().getBranchNo());
		}
		
		// 调用service业务接口
		this.branchService.addDept(this.departmentInfo, this.privileges, this.getSessionUserCode());
		
		String msg = LogUtils.r("添加部门[{0}]成功", this.departmentInfo.getDeptId());
		this.addActionMessage("/pages/dept/list.do", msg);
		this.log(msg, UserLogType.ADD);

		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.departmentInfo = (DepartmentInfo) this.departmentInfoDAO.findByPk(this.departmentInfo.getDeptId());
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.modifyDept(this.departmentInfo, this.privileges, this.getSessionUserCode());

		String msg = LogUtils.r("修改部门[{0}]成功", this.departmentInfo.getDeptId());
		this.addActionMessage("/pages/dept/list.do", msg);
		this.log(msg, UserLogType.UPDATE);

		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		this.checkEffectiveCertUser();
		
		String deptId = request.getParameter("deptId");
		this.branchService.cancelDept(deptId, this.getSessionUserCode());
		
		String msg = LogUtils.r("注销部门[{0}]成功", deptId);
		this.addActionMessage("/pages/dept/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
		String deptId = request.getParameter("deptId");
		this.branchService.activeDept(deptId, this.getSessionUserCode());
		
		String msg = LogUtils.r("生效部门[{0}]成功", deptId);
		this.addActionMessage("/pages/dept/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}
	

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

}
