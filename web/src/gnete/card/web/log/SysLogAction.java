package gnete.card.web.log;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.SysLogDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.SysLog;
import gnete.card.entity.state.SysLogViewState;
import gnete.card.entity.type.SysLogType;
import gnete.card.service.LogService;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * SysLogAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-9-1
 */
public class SysLogAction extends BaseAction {
	
	@Autowired
	private SysLogDAO sysLogDAO;

	private SysLog sysLog;
	
	private Paginater page;
	
	private String startDate;
	private String endDate;

	private List<BranchInfo> branchList;
	
	private List<MerchInfo> merchList;
	
	private List viewList;
	private List logTypeList;
	
	@Autowired
	private LogService logService;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		initPage();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (sysLog != null) {
			params.put("branchCode", sysLog.getBranchNo());
			params.put("merchCode", sysLog.getMerchantNo());
			params.put("state", sysLog.getState());
			params.put("logType", sysLog.getLogType());
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		this.page = this.sysLogDAO.findLog(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	private void initPage() {
//		this.branchList = this.getMyBranch();
//		this.merchList = this.getMyMerch();
		this.viewList = SysLogViewState.getAll();
		this.logTypeList = SysLogType.getAll();
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.sysLog = (SysLog) this.sysLogDAO.findByPk(this.sysLog.getId());
		if (SysLogViewState.UN_READ.getValue().equals(this.sysLog.getState())) {
			logService.readSysLog(this.sysLog.getId(), this.getSessionUserCode());
		}
		return DETAIL;
	}

	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public List getViewList() {
		return viewList;
	}

	public void setViewList(List viewList) {
		this.viewList = viewList;
	}

	public List getLogTypeList() {
		return logTypeList;
	}

	public void setLogTypeList(List logTypeList) {
		this.logTypeList = logTypeList;
	}	
}
