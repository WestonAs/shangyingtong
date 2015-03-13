package gnete.card.web.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.util.Paginater;
import gnete.card.dao.TransDAO;
import gnete.card.entity.Trans;
import gnete.card.entity.type.TransType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;


/**
 * @File: MerchantMonitorAction.java
 *
 * @description: 商户风险交易监控
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-10
 */
public class MerchantMonitorAction extends BaseAction {

	@Autowired
	private TransDAO transDAO;
	
	private Trans trans;
	private List<TransType> transTypeList;
	
	private Paginater page;
	private String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();

	@Override
	public String execute() throws Exception {
		transTypeList = TransType.getTransType();
		Map<String, Object> params = new HashMap<String, Object>();
		if (trans != null) {
			params.put("transType", trans.getTransType());
			params.put("sysTraceNum", trans.getSysTraceNum());
		}
		params.put("", "");
		params.put("settDate", workDate);
		this.page = this.transDAO.findMerchTrans(params, getPageNumber(), getPageSize());
		return LIST;
	}

	public String searchTrans() throws Exception {
		String[] riskFlags = {"1", "9"};
		Map<String, Object> params = new HashMap<String, Object>();
		if (trans != null) {
			params.put("riskFlags", riskFlags);
			params.put("settDate", workDate);
		}
		this.page = this.transDAO.findTrans(params, getPageNumber(), getPageSize());
		return "data";
	}
	
	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<TransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<TransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

}
