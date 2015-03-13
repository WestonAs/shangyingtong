package gnete.card.web.businessSubAccount;

import flink.util.Paginater;
import gnete.card.dao.SubAccountTypeDAO;
import gnete.card.entity.SubAccountType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BusinessSubAccountService;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author aps-lzi
 * @since JDK1.6
 * @history 2011-12-1
 */
public class SubAccountTypeAction extends BaseAction {
	
	@Autowired
	private SubAccountTypeDAO subAccountTypeDAO;
	@Autowired
	private BusinessSubAccountService businessSubAccountService;
	
	private SubAccountType subAccountType;
	
	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (subAccountType != null) {
			params.put("subAccountTypeId", subAccountType.getSubAccountTypeId()); 
			params.put("subAccountTypeName", subAccountType.getSubAccountTypeName());
		}
		this.page = this.subAccountTypeDAO.findSubAccountType(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.subAccountType = (SubAccountType) this.subAccountTypeDAO.findByPk(this.subAccountType.getSubAccountTypeId());
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.subAccountType = (SubAccountType) this.subAccountTypeDAO.findByPk(this.subAccountType.getSubAccountTypeId());
		return MODIFY;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		 
		this.businessSubAccountService.modifySubAccountType(subAccountType, this.getSessionUserCode());
		this.addActionMessage("/businessSubAccount/subAccountType/list.do", "修改成功！");
		this.log("修改虚账户类型["+ subAccountType.getSubAccountTypeId() +"]成功！", UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public SubAccountType getSubAccountType() {
		return subAccountType;
	}

	public void setSubAccountType(SubAccountType subAccountType) {
		this.subAccountType = subAccountType;
	}

}
