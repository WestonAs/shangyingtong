package gnete.card.web.log;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.UserLogDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserLog;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-30
 */
public class UserLogAction extends BaseAction {
	
	@Autowired
	private UserLogDAO userLogDAO;

	private UserLog userLog;
	
	private Paginater page;
	
	private String startDate;
	private String endDate;
	
	private String merchId_sel;
	private String cardBranchName;
	private boolean showBranch = false;
	private boolean showMerch = false;

	private List<UserLogType> logTypeList;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.logTypeList = UserLogType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (userLog != null) {
			params.put("branchCode", userLog.getBranchNo());
			params.put("merchCode", userLog.getMerchantNo());
			params.put("userId", userLog.getUserId());
			params.put("logType", userLog.getLogType());
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		// 运营中心登录，查看所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			showBranch = true;
			showMerch = true;
		}
		// 分支机构登录，可查看自己管理的所有机构和商户的日志
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showBranch = true;
			showMerch = true;
			List<BranchInfo> list = this.getMyManageFenzhi();
			params.put("fenzhiList", list);
		}
		// 商户登录，只能看自己的日志
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			params.put("merchCode", this.getLoginBranchCode());
		}
		// 个人用户，只能查看自己的日志
		else if (RoleType.PERSONAL.getValue().equals(this.getLoginRoleType())) {
			params.put("userId", this.getSessionUserCode());
		}
		// 其他的情况只能看自己的机构的
		else {
			params.put("branchCode", this.getLoginBranchCode());
		}
		this.page = this.userLogDAO.findLog(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.userLog = (UserLog) this.userLogDAO.findByPk(this.userLog.getId());
		return DETAIL;
	}

	public UserLog getUserLog() {
		return userLog;
	}

	public void setUserLog(UserLog userLog) {
		this.userLog = userLog;
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

	public List<UserLogType> getLogTypeList() {
		return logTypeList;
	}

	public void setLogTypeList(List<UserLogType> logTypeList) {
		this.logTypeList = logTypeList;
	}

	public String getMerchId_sel() {
		return merchId_sel;
	}

	public void setMerchId_sel(String merchId_sel) {
		this.merchId_sel = merchId_sel;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public boolean isShowBranch() {
		return showBranch;
	}

	public void setShowBranch(boolean showBranch) {
		this.showBranch = showBranch;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}	
}
