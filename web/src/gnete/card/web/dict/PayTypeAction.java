package gnete.card.web.dict;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.PayTypeCodeDAO;
import gnete.card.entity.PayTypeCode;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * PayTypeAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class PayTypeAction extends BaseAction {

	@Autowired
	private PayTypeCodeDAO payTypeCodeDAO;

	private Paginater page;
	
	private PayTypeCode payTypeCode;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (payTypeCode != null) {
			params.put("payCode", payTypeCode.getPayCode());
			params.put("payName", MatchMode.ANYWHERE.toMatchString(payTypeCode.getPayName()));
		}
		this.page = this.payTypeCodeDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.payTypeCode = (PayTypeCode) this.payTypeCodeDAO.findByPk(this.payTypeCode.getPayCode());
		return DETAIL;
	}
	
	public PayTypeCode getPayTypeCode() {
		return payTypeCode;
	}
	

	public void setPayTypeCode(PayTypeCode payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
