package gnete.card.web.dict;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.entity.CurrCode;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * CurrCodeAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class CurrCodeAction extends BaseAction {

	@Autowired
	private CurrCodeDAO currCodeDAO;

	private Paginater page;
	
	private CurrCode currCode;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (currCode != null) {
			params.put("currCode", currCode.getCurrCode());
			params.put("currName", MatchMode.ANYWHERE.toMatchString(currCode.getCurrName()));
		}
		this.page = this.currCodeDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.currCode = (CurrCode) this.currCodeDAO.findByPk(this.currCode.getCurrCode());
		return DETAIL;
	}
	
	public CurrCode getCurrCode() {
		return currCode;
	}
	

	public void setCurrCode(CurrCode currCode) {
		this.currCode = currCode;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
