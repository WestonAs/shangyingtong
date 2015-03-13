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
 * @File: TransMonitorAction.java
 *
 * @description: 风险交易监控
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-10
 */
public class TransMonitorAction extends BaseAction {
	
	@Autowired
	private TransDAO transDAO;
	
	private Trans trans;
	private List<TransType> transTypeList;
	private String lastTransDate;
	
	private Paginater page;
	private String workDate = SysparamCache.getInstance().getWorkDateNotFromCache();

	@Override
	public String execute() throws Exception {
		transTypeList = TransType.getTransType();
		String[] riskFlags = {"1", "9"};
		Map<String, Object> params = new HashMap<String, Object>();
		if (trans != null) {
			params.put("sysTraceNum", trans.getSysTraceNum());
			params.put("transType", trans.getTransType());
		}
		params.put("riskFlags", riskFlags);
		params.put("settDate", workDate);
		this.page = this.transDAO.findTrans(params, getPageNumber(), getPageSize());
		return LIST;
	}
	
	public String listMonitor() throws Exception {
		transTypeList = TransType.getMonitorTransType();
		String[] riskFlags = {"1", "9"};
		Map<String, Object> params = new HashMap<String, Object>();
		if (trans != null) {
			params.put("sysTraceNum", trans.getSysTraceNum());
			params.put("cardId", trans.getCardId());
			params.put("transType", trans.getTransType());
			params.put("transSn", trans.getTransSn());
			params.put("cardIssuer", trans.getCardIssuer());
			params.put("merNo", trans.getMerNo());
			params.put("termlId", trans.getTermlId());
		}
		params.put("procStatus", "11");//处理失败
		params.put("riskFlags", riskFlags);
		params.put("settDate", workDate);
		this.page = this.transDAO.findTransMonitor(params, getPageNumber(), getPageSize());
		
		lastTransDate = this.transDAO.getLastTransDate(params);
		return "listMonitor";
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
	
	public String getLastTransDate() {
		return lastTransDate;
	}

	public void setLastTransDate(String lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
}
