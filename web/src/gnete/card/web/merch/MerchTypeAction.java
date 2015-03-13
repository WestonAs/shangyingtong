package gnete.card.web.merch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.MerchTypeDAO;
import gnete.card.entity.MerchType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * MerchTypeAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class MerchTypeAction extends BaseAction {

	@Autowired
	private MerchTypeDAO merchTypeDAO;
	
	@Autowired
	private MerchService merchService;

	private Paginater page;
	
	private MerchType merchType;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchType != null) {
			params.put("merchType", merchType.getMerchType());
			params.put("typeName", MatchMode.ANYWHERE.toMatchString(merchType.getTypeName()));
		}
		this.page = this.merchTypeDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.merchType = (MerchType) this.merchTypeDAO.findByPk(this.merchType.getMerchType());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		// 调用service业务接口
		this.merchService.addMerchType(this.merchType, this.getSessionUserCode());
		
		String msg = LogUtils.r("添加商户类型[{0}]成功", this.merchType.getMerchType());
		this.addActionMessage("/pages/merchType/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.merchType = (MerchType) this.merchTypeDAO.findByPk(this.merchType.getMerchType());
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.merchService.modifyMerchType(this.merchType, this.getSessionUserCode());

		String msg = LogUtils.r("修改商户类型[{0}]成功", this.merchType.getMerchType());
		this.addActionMessage("/pages/merchType/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		String merchType = request.getParameter("merchTypeCode");
		this.merchService.cancelMerchType(merchType, this.getSessionUserCode());
		
		String msg = LogUtils.r("注销商户类型[{0}]成功", merchType);
		this.addActionMessage("/pages/merchType/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String activate() throws Exception {
		String merchType = request.getParameter("merchTypeCode");
		this.merchService.activeMerchType(merchType, this.getSessionUserCode());
		
		String msg = LogUtils.r("生效商户类型[{0}]成功", merchType);
		this.addActionMessage("/pages/merchType/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public MerchType getMerchType() {
		return merchType;
	}

	public void setMerchType(MerchType merchType) {
		this.merchType = merchType;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
