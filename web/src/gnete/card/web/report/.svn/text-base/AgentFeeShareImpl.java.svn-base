package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: AgentFeeShareImpl.java
 *
 * @description: 运营代理商分润汇总报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
@Service("agentFeeShareImpl")
public class AgentFeeShareImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看运营代理商分润汇总报表!";
	
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
		boolean showCardList = false;
		
		//如果是运营中心或运营中心部门时,可查看所有运营代理商的报表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			showCardList = true;
			request.setAttribute("cardBranchList", branchInfoDAO.findByType(RoleType.AGENT.getValue()));
		}
		// 运营分支机构登录时,可查看自己管理的运营代理商的报表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			showCardList = true;
//			List<BranchInfo> cardBranchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo());
//			request.setAttribute("cardBranchList", cardBranchList);
			request.setAttribute("cardBranchList", branchInfoDAO.findByTypeAndManage(RoleType.AGENT.getValue(), userInfo.getBranchNo()));
		}
		// 运营代理商角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.AGENT.getValue())) {
			showCardList = false;
			request.setAttribute("cardBranch", userInfo.getBranchNo());
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("showCardList", showCardList);
	}

}
