package gnete.card.web.report;

import java.util.ArrayList;
import java.util.List;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reportCommonAcctRma")
public class ReportCommonAcctRmaImpl extends AbstractCardReportLoadImpl {

	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	/**
	 *  积分卡业务共管账户划付报表
	 */
	private final String COMMON_ACCT_RMA_FILE = "/card/commonAcctRma.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看积分卡业务共管账户划付报表!";
	
	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		String loginRoleType = userInfo.getRole().getRoleType();
		String loginBranchCode = userInfo.getBranchNo();
		String reportFile = null;
		boolean showCardIssuerList = false;
		
		// 发卡机构列表
		List<BranchInfo> cardIssuerList = new ArrayList<BranchInfo>();
		
		//如果是运营中心或运营中心部门，可以查看该报表
		if (StringUtils.equals(loginRoleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(loginRoleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = COMMON_ACCT_RMA_FILE;
			showCardIssuerList = true;
			cardIssuerList = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			
		}
		//如果是分支机构
		else if (StringUtils.equals(loginRoleType, RoleType.FENZHI.getValue())) {
			reportFile = COMMON_ACCT_RMA_FILE;
			showCardIssuerList = true;
			cardIssuerList = this.branchInfoDAO.findCardByManange(loginBranchCode);
			
		} 
		// 集团机构角色登录时，可查看自己集团下的发卡机构的报表
		else if (StringUtils.equals(loginRoleType, RoleType.GROUP.getValue())) {
			reportFile = COMMON_ACCT_RMA_FILE;
			showCardIssuerList = true;
			cardIssuerList = branchInfoDAO.findByGroupId(userInfo.getBranchNo());
		}
		//如果是发卡机构
		else if (StringUtils.equals(loginRoleType, RoleType.CARD.getValue())
					|| StringUtils.equals(loginRoleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = COMMON_ACCT_RMA_FILE;
			showCardIssuerList = false;
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("cardIssuerList", sortBranchList(cardIssuerList));
		request.setAttribute("reportFile", reportFile);
		request.setAttribute("showCardIssuerList", showCardIssuerList);
		request.setAttribute("loginBranchCode", loginBranchCode);
		
	}

}
