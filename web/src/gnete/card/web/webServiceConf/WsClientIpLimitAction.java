package gnete.card.web.webServiceConf;

import flink.util.Paginater;
import gnete.card.dao.WsClientIpLimitDAO;
import gnete.card.dao.WsClientIpLimitDtlDAO;
import gnete.card.entity.WsClientIpLimit;
import gnete.card.entity.WsClientIpLimitDtl;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ws客户端ip限制 配置
 */
@SuppressWarnings("serial")
public class WsClientIpLimitAction extends BaseAction {

	@Autowired
	private WsClientIpLimitDAO			wsClientIpLimitDAO;
	@Autowired
	private WsClientIpLimitDtlDAO	wsClientIpLimitDtlDAO;

	private Paginater					page;

	private WsClientIpLimit				wsClientIpLimit;
	private WsClientIpLimitDtl		wsClientIpLimitDtl;

	@Override
	public String execute() throws Exception {
		checkRoleLogined();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", wsClientIpLimit);
		params.put("branchName", this.getFormMapValue("branchName"));
		this.page = this.wsClientIpLimitDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception {
		checkRoleLogined();
		wsClientIpLimit = (WsClientIpLimit) wsClientIpLimitDAO.findByPk(wsClientIpLimit.getBranchCode());
		return DETAIL;
	}


	public String showAdd() throws Exception {
		checkRoleLoginedAndCert();
		return ADD;
	}

	public String add() throws Exception {
		checkRoleLoginedAndCert();

		wsClientIpLimit.setInsertTime(new Date());
		wsClientIpLimit.setUpdateTime(new Date());
		wsClientIpLimit.setUpdateBy(this.getSessionUserCode());
		
		wsClientIpLimitDAO.insert(wsClientIpLimit);

		String msg = String.format("新增ws客户端ip限制[%s]成功", wsClientIpLimit.getBranchCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/para/webServiceConf/wsClientIpLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		checkRoleLoginedAndCert();
		wsClientIpLimit = (WsClientIpLimit) wsClientIpLimitDAO.findByPk(wsClientIpLimit.getBranchCode());
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkRoleLoginedAndCert();

		WsClientIpLimit orig = (WsClientIpLimit) wsClientIpLimitDAO.findByPk(wsClientIpLimit.getBranchCode());
		Assert.notNull(orig, "指定的配置记录不存在");

		wsClientIpLimit.setInsertTime(orig.getInsertTime());
		wsClientIpLimit.setUpdateBy(this.getSessionUserCode());
		wsClientIpLimit.setUpdateTime(new Date());

		wsClientIpLimitDAO.update(wsClientIpLimit);

		String msg = String.format("修改ws客户端ip限制[%s]成功", wsClientIpLimit.getBranchCode());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/webServiceConf/wsClientIpLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		this.checkRoleLoginedAndCert();

		wsClientIpLimitDAO.delete(wsClientIpLimit.getBranchCode());

		String msg = String.format("删除ws客户端ip限制[%s]成功", wsClientIpLimit.getBranchCode());
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/para/webServiceConf/wsClientIpLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	
	public String listDtl() throws Exception {
		checkRoleLogined();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", wsClientIpLimitDtl);
		params.put("branchName", this.getFormMapValue("branchName"));
		this.page = this.wsClientIpLimitDtlDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "listDtl";
	}
	
	public String detailDtl() throws Exception {
		checkRoleLogined();
		wsClientIpLimitDtl = (WsClientIpLimitDtl) wsClientIpLimitDtlDAO.findByPk(wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		return "detailDtl";
	}
	
	public String showAddDtl() throws Exception {
		checkRoleLoginedAndCert();
		return "addDtl";
	}
	
	public String addDtl() throws Exception {
		checkRoleLoginedAndCert();
		
		wsClientIpLimitDtl.setInsertTime(new Date());
		wsClientIpLimitDtl.setUpdateTime(new Date());
		wsClientIpLimitDtl.setUpdateBy(this.getSessionUserCode());
		
		wsClientIpLimitDtlDAO.insert(wsClientIpLimitDtl);
		
		String msg = String.format("新增ws客户端ip限制明细[%s][%s]成功", wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/para/webServiceConf/wsClientIpLimit/listDtl.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String showModifyDtl() throws Exception {
		checkRoleLoginedAndCert();
		wsClientIpLimitDtl = (WsClientIpLimitDtl) wsClientIpLimitDtlDAO.findByPk(wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		return "modifyDtl";
	}
	
	public String modifyDtl() throws Exception {
		this.checkRoleLoginedAndCert();
		
		WsClientIpLimitDtl orig = (WsClientIpLimitDtl) wsClientIpLimitDtlDAO.findByPk(wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		Assert.notNull(orig, "指定的配置记录不存在");
		
		wsClientIpLimitDtl.setInsertTime(orig.getInsertTime());
		wsClientIpLimitDtl.setUpdateBy(this.getSessionUserCode());
		wsClientIpLimitDtl.setUpdateTime(new Date());
		
		wsClientIpLimitDtlDAO.update(wsClientIpLimitDtl);
		
		String msg = String.format("修改ws客户端ip限制明细[%s][%s]成功", wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/webServiceConf/wsClientIpLimit/listDtl.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String deleteDtl() throws Exception {
		this.checkRoleLoginedAndCert();
		
		wsClientIpLimitDtlDAO.delete(wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		
		String msg = String.format("删除ws客户端ip限制明细[%s][%s]成功", wsClientIpLimitDtl.getBranchCode(), wsClientIpLimitDtl.getIp());
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/para/webServiceConf/wsClientIpLimit/listDtl.do?goBack=goBack", msg);
		return SUCCESS;
	}

	
	
	private void checkRoleLogined() throws BizException {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限访问ws客户端ip限制配置");
	}

	private void checkRoleLoginedAndCert() throws BizException {
		this.checkRoleLogined();
		this.checkEffectiveCertUser();
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WsClientIpLimit getWsClientIpLimit() {
		return wsClientIpLimit;
	}

	public void setWsClientIpLimit(WsClientIpLimit wsClientIpLimit) {
		this.wsClientIpLimit = wsClientIpLimit;
	}

	public WsClientIpLimitDtl getWsClientIpLimitDtl() {
		return wsClientIpLimitDtl;
	}

	public void setWsClientIpLimitDtl(WsClientIpLimitDtl wsClientIpLimitDtl) {
		this.wsClientIpLimitDtl = wsClientIpLimitDtl;
	}

}
