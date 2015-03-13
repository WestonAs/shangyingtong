package gnete.card.web.webServiceConf;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.WsMsgTypeCtrlDAO;
import gnete.card.entity.WsMsgTypeCtrl;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ws接口访问控制 处理
 */
@SuppressWarnings("serial")
public class WsMsgTypeCtrlAction extends BaseAction {

	@Autowired
	private WsMsgTypeCtrlDAO	wsMsgTypeCtrlDAO;

	private Paginater			page;

	private WsMsgTypeCtrl		wsMsgTypeCtrl;

	@Override
	public String execute() throws Exception {
		checkRoleLogined();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("record", wsMsgTypeCtrl);
		params.put("isIssDirectCall", this.getFormMapValue("isIssDirectCall"));
		this.page = this.wsMsgTypeCtrlDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		checkRoleLogined();
		wsMsgTypeCtrl = (WsMsgTypeCtrl) wsMsgTypeCtrlDAO.findByPk(wsMsgTypeCtrl);
		return DETAIL;
	}

	public String showAdd() throws Exception {
		checkRoleLoginedAndCert();
		return ADD;
	}

	public String add() throws Exception {
		checkRoleLoginedAndCert();

		if (StringUtils.isNotEmpty(wsMsgTypeCtrl.getRelatedIssNo())) {
			Assert.notEquals(wsMsgTypeCtrl.getBranchCode(), wsMsgTypeCtrl.getRelatedIssNo(), "外部系统编号与关联发卡机构编号不能相同");
		}
		wsMsgTypeCtrl.setInsertTime(new Date());
		wsMsgTypeCtrl.setUpdateTime(new Date());
		wsMsgTypeCtrl.setUpdateBy(this.getSessionUserCode());
		wsMsgTypeCtrlDAO.insert(wsMsgTypeCtrl);

		String msg = LogUtils.r("新增ws接口类型访问控制[{0}][{1}][{2}]成功", wsMsgTypeCtrl.getBranchCode(),
				wsMsgTypeCtrl.getMsgType(), wsMsgTypeCtrl.getRelatedIssNo());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/para/webServiceConf/wsMsgTypeCtrl/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		checkRoleLoginedAndCert();
		wsMsgTypeCtrl = (WsMsgTypeCtrl) wsMsgTypeCtrlDAO.findByPk(wsMsgTypeCtrl);
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkRoleLoginedAndCert();

		WsMsgTypeCtrl orig = (WsMsgTypeCtrl) wsMsgTypeCtrlDAO.findByPk(wsMsgTypeCtrl);
		Assert.notNull(orig, "指定的配置记录不存在");
		
		wsMsgTypeCtrl.setInsertTime(orig.getInsertTime());
		wsMsgTypeCtrl.setUpdateBy(this.getSessionUserCode());
		wsMsgTypeCtrl.setUpdateTime(new Date());
		
		wsMsgTypeCtrlDAO.update(wsMsgTypeCtrl);

		String msg = String.format("修改ws接口类型访问控制[%s][%s][%s]成功", wsMsgTypeCtrl.getBranchCode(),
				wsMsgTypeCtrl.getMsgType(), wsMsgTypeCtrl.getRelatedIssNo());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/webServiceConf/wsMsgTypeCtrl/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		this.checkRoleLoginedAndCert();

		wsMsgTypeCtrlDAO.delete(wsMsgTypeCtrl);

		String msg = String.format("删除ws接口类型访问控制[%s][%s][%s]成功", wsMsgTypeCtrl.getBranchCode(),
				wsMsgTypeCtrl.getMsgType(), wsMsgTypeCtrl.getRelatedIssNo());
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/para/webServiceConf/wsMsgTypeCtrl/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	private void checkRoleLogined() throws BizException {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限访问ws接口访问控制配置");
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

	public WsMsgTypeCtrl getWsMsgTypeCtrl() {
		return wsMsgTypeCtrl;
	}

	public void setWsMsgTypeCtrl(WsMsgTypeCtrl wsMsgTypeCtrl) {
		this.wsMsgTypeCtrl = wsMsgTypeCtrl;
	}

}
