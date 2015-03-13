package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.report.FenzhiOperateType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: FenzhiOperateFeeShareImpl.java
 *
 * @description: 运营分支机构运营手续费报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
@Service("fenzhiOperateFeeImpl")
public class FenzhiOperateFeeImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看运营分支机构运营手续费报表!";
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		
		String roleType = userInfo.getRole().getRoleType();
		boolean showFenzhi = false;
		
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			showFenzhi = true;
			request.setAttribute("fenzhiList", sortBranchList(branchInfoDAO.findByType(RoleType.FENZHI.getValue())));
		}
		// 运营分支机构登录时
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			showFenzhi = false;
			request.setAttribute("fenzhi", branchInfoDAO.findBranchInfo(userInfo.getBranchNo()));
//			request.setAttribute("cardBranchList", branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo()));
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		request.setAttribute("reportTypeList", FenzhiOperateType.getAll());
		request.setAttribute("showFenzhi", showFenzhi);
	}

}
