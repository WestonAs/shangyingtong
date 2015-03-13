package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.service.mgr.SysparamCache;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ReportCZCMCCPointSumImpl.java
 *
 * @description: 潮州移动的新增优惠积分统计月报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-7-20
 */
@Service("reportCZCMCCPointSum")
public class ReportCZCMCCPointSumImpl extends AbstractCardReportLoadImpl {

	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	/**
	 *  新增优惠积分统计月报表
	 */
	private final String MONTH_POINT_SUM_FILE = "/chaozhouCMCC/monthPointSum.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看潮州移动的新增优惠积分统计月报表!";
	
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
		
		// 取得潮州移动的机构号
		String czBranchCode = SysparamCache.getInstance().getParamValue("T02");
		BranchInfo czBranch = branchInfoDAO.findBranchInfo(czBranchCode);

		//如果是运营中心或运营中心部门，可以查看该报表
		if (StringUtils.equals(loginRoleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(loginRoleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = MONTH_POINT_SUM_FILE;
		}
		//如果是分支机构，只能是潮州移动的分支机构能够查看报表
		else if (StringUtils.equals(loginRoleType, RoleType.FENZHI.getValue())) {
			reportFile = MONTH_POINT_SUM_FILE;
			if (!StringUtils.equals(loginBranchCode, czBranch.getParent())) {
				request.setAttribute("errMsg", REPORT_ERROR_MSG);
			}
		} 
		//如果是发卡机构时，只能是潮州移动自己可以查看
		else if (StringUtils.equals(loginRoleType, RoleType.CARD.getValue())
					|| StringUtils.equals(loginRoleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = MONTH_POINT_SUM_FILE;
			if (!StringUtils.equals(loginBranchCode, czBranchCode)) {
				request.setAttribute("errMsg", REPORT_ERROR_MSG);
			}
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("czBranch", czBranch); // 潮州移动
		request.setAttribute("reportFile", reportFile);
		
	}

}
