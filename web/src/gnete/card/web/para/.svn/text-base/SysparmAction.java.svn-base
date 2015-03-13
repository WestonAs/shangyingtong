package gnete.card.web.para;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.SysparmDAO;
import gnete.card.entity.Sysparm;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class SysparmAction extends BaseAction {
	
	@Autowired
	private SysparmDAO sysparmDAO;

	private Sysparm sysparm;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (sysparm != null) {
			params.put("paraCode", sysparm.getParaCode());
			params.put("paraName", MatchMode.ANYWHERE.toMatchString(sysparm.getParaName()));
			params.put("showFlag", sysparm.getShowFlag());
			params.put("modifyFlag", sysparm.getModifyFlag());
		}
		this.page = this.sysparmDAO.findSysparm(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception{
		this.sysparm = (Sysparm) this.sysparmDAO.findByPk(this.sysparm.getParaCode());
		return DETAIL;
	}
	
	public String showModify() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心（部门）用户才能修改");
		
		this.sysparm = (Sysparm) this.sysparmDAO.findByPk(this.sysparm.getParaCode());
		Assert.isTrue("1".equals(sysparm.getModifyFlag()), "该参数不可修改");
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心（部门）用户才能修改");
		
		Sysparm old = (Sysparm) this.sysparmDAO.findByPk(this.sysparm.getParaCode());
		Assert.notNull(old, "系统参数不存在：" + sysparm.getParaCode());
		Assert.isTrue("1".equals(old.getModifyFlag()), "该参数不可修改");
		
		old.setParaName(sysparm.getParaName());
		old.setParaValue(sysparm.getParaValue());
		old.setNote(sysparm.getNote());
		old.setUpdateUser(this.getSessionUserCode());
		old.setUpdateTime(new Date());
		sysparmDAO.update(old);
		SysparamCache.getInstance().remove(old.getParaCode());
		
		String msg = LogUtils.r("修改系统参数[{0}]成功！", sysparm.getParaCode());
		this.addActionMessage("/para/sysparm/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Sysparm getSysparm() {
		return sysparm;
	}

	public void setSysparm(Sysparm sysparm) {
		this.sysparm = sysparm;
	}

}
