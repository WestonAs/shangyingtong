package gnete.card.web.para;

import flink.util.Paginater;
import gnete.card.dao.BranchBizConfigDAO;
import gnete.card.dao.BranchBizConfigTypeDAO;
import gnete.card.entity.BranchBizConfig;
import gnete.card.entity.BranchBizConfigType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 机构/商户 简单业务参数或业务控制的配置模块
 */
@SuppressWarnings("serial")
public class BranchBizConfigAction extends BaseAction {

	@Autowired
	private BranchBizConfigDAO		branchBizConfigDAO;
	@Autowired
	private BranchBizConfigTypeDAO	branchBizConfigTypeDAO;

	private Paginater				page;

	private BranchBizConfig			branchBizConfig;

	private BranchBizConfigType		branchBizConfigType;

	@Override
	public String execute() throws Exception {
		checkRoleLogined();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", branchBizConfig);
		params.put("branchName", this.getFormMapValue("branchName"));
		this.page = this.branchBizConfigDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		checkRoleLogined();
		branchBizConfig = (BranchBizConfig) branchBizConfigDAO.findByPk(branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		checkRoleLoginedAndCert();
		return ADD;
	}

	public String add() throws Exception {
		checkRoleLoginedAndCert();

		branchBizConfig.setInsertTime(new Date());
		branchBizConfig.setUpdateTime(new Date());
		branchBizConfig.setUpdateUser(this.getSessionUserCode());

		branchBizConfigDAO.insert(branchBizConfig);

		String msg = String.format("新增机构简单业务配置[%s][%s]成功", branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/para/branchBizConfig/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		checkRoleLoginedAndCert();
		branchBizConfig = (BranchBizConfig) branchBizConfigDAO.findByPk(branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkRoleLoginedAndCert();

		BranchBizConfig orig = (BranchBizConfig) branchBizConfigDAO.findByPk(branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		Assert.notNull(orig, "指定的配置记录不存在");

		branchBizConfig.setInsertTime(orig.getInsertTime());
		branchBizConfig.setUpdateTime(new Date());
		branchBizConfig.setUpdateUser(this.getSessionUserCode());

		branchBizConfigDAO.update(branchBizConfig);

		String msg = String.format("修改机构简单业务配置[%s][%s]成功", branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/branchBizConfig/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		this.checkRoleLoginedAndCert();

		branchBizConfigDAO.delete(branchBizConfig);

		String msg = String.format("删除机构简单业务配置[%s][%s]成功", branchBizConfig.getBranchCode(),
				branchBizConfig.getConfigType());
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/para/branchBizConfig/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String listType() throws Exception {
		checkRoleLogined();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", branchBizConfigType);
		params.put("branchName", this.getFormMapValue("branchName"));
		this.page = this.branchBizConfigTypeDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "listType";
	}

	public String detailType() throws Exception {
		checkRoleLogined();
		branchBizConfigType = (BranchBizConfigType) branchBizConfigTypeDAO.findByPk(branchBizConfigType
				.getConfigType());
		return "detailType";
	}

	public String showAddType() throws Exception {
		checkRoleLoginedAndCert();
		return "addType";
	}

	public String addType() throws Exception {
		checkRoleLoginedAndCert();

		branchBizConfigType.setInsertTime(new Date());
		branchBizConfigType.setUpdateTime(new Date());
		branchBizConfigType.setUpdateUser(this.getSessionUserCode());

		branchBizConfigTypeDAO.insert(branchBizConfigType);

		String msg = String.format("新增机构简单业务配置类型[%s]成功", branchBizConfigType.getConfigType());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/para/branchBizConfig/listType.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModifyType() throws Exception {
		checkRoleLoginedAndCert();
		branchBizConfigType = (BranchBizConfigType) branchBizConfigTypeDAO.findByPk(branchBizConfigType
				.getConfigType());
		return "modifyType";
	}

	public String modifyType() throws Exception {
		this.checkRoleLoginedAndCert();

		BranchBizConfigType orig = (BranchBizConfigType) branchBizConfigTypeDAO.findByPk(branchBizConfigType
				.getConfigType());
		Assert.notNull(orig, "指定的配置记录不存在");

		branchBizConfigType.setInsertTime(orig.getInsertTime());
		branchBizConfigType.setUpdateTime(new Date());
		branchBizConfigType.setUpdateUser(this.getSessionUserCode());

		branchBizConfigTypeDAO.update(branchBizConfigType);

		String msg = String.format("修改机构简单业务配置类型[%s]成功", branchBizConfigType.getConfigType());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/branchBizConfig/listType.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String deleteType() throws Exception {
		this.checkRoleLoginedAndCert();

		branchBizConfigTypeDAO.delete(branchBizConfigType.getConfigType());

		String msg = String.format("删除机构简单业务配置类型[%s]成功", branchBizConfigType.getConfigType());
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/para/branchBizConfig/listType.do?goBack=goBack", msg);
		return SUCCESS;
	}

	private void checkRoleLoginedAndCert() throws BizException {
		this.checkRoleLogined();
		this.checkEffectiveCertUser();
	}

	private void checkRoleLogined() throws BizException {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限访问机构简单业务配置");
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BranchBizConfig getBranchBizConfig() {
		return branchBizConfig;
	}

	public void setBranchBizConfig(BranchBizConfig branchBizConfig) {
		this.branchBizConfig = branchBizConfig;
	}

	public BranchBizConfigType getBranchBizConfigType() {
		return branchBizConfigType;
	}

	public void setBranchBizConfigType(BranchBizConfigType branchBizConfigType) {
		this.branchBizConfigType = branchBizConfigType;
	}

}
