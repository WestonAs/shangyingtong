package gnete.card.web.pointAccService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.SysLogDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.SysLog;
import gnete.card.entity.type.RoleType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 充值及变更文件异常查询 
 * @author aps-lib
 *
 */

public class PointAccFileExInfoAction extends BaseAction{

	@Autowired
	private SysLogDAO sysLogDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private SysLog sysLog;
	private Paginater page;
	private List<BranchInfo> branchList;
	private String logDate;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.branchList = new ArrayList<BranchInfo>();
		
		// 取得潮州移动的机构号
		String czBranchCode = SysparamCache.getInstance().getParamValue("T02");
		BranchInfo czBranch = branchInfoDAO.findBranchInfo(czBranchCode);
		this.branchList.add((BranchInfo)this.branchInfoDAO.findByPk(czBranch.getBranchCode()));
		
		// 营运中心、中心部门
		if(RoleType.CENTER.getValue().equals(getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			params.put("branchCode", czBranch.getBranchCode());
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			if(czBranch.getParent().equals(this.getSessionUser().getBranchNo())){
				params.put("branchCode", czBranch.getBranchCode());
			}
			else {
				throw new BizException("非潮州移动的管理机构没有权限查询。");
			}
				
		}
		// 发卡机构
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		if (sysLog != null){
			params.put("logDate", logDate);
		}
		
		this.page = this.sysLogDAO.findLog(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.sysLog = (SysLog) this.sysLogDAO.findByPk(this.sysLog.getId());
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

}
