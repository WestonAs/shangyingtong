package gnete.card.web.dict;

import flink.util.Paginater;
import gnete.card.dao.ExchangeRateDAO;
import gnete.card.entity.ExchangeRate;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 汇率查询Action.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class XrateAction extends BaseAction {

	@Autowired
	private ExchangeRateDAO exchangeRateDAO;

	private Paginater page;
	
	private ExchangeRate exchangeRate;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (exchangeRate != null) {
			params.put("currCode", exchangeRate.getCurrCode());
		}
		this.page = this.exchangeRateDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public ExchangeRate getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(ExchangeRate exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}
