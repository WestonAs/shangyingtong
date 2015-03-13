package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ManageShareImpl.java
 *
 * @description: 集团菜单下的分润报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
@Service("groupOperationFeeImpl")
public class GroupOperationFeeImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看运营手续费收入集团汇总月报表!";
	
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
		List<BranchInfo> groupList = new ArrayList<BranchInfo>();
		
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			groupList = branchInfoDAO.findByType(RoleType.GROUP.getValue());
		}
		// 运营分支机构登录时
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			groupList = branchInfoDAO.findByTypeAndManage(RoleType.GROUP.getValue(), userInfo.getBranchNo());
		}
		// 集团用户登录时
		else if (StringUtils.equals(roleType, RoleType.GROUP.getValue())) {
			request.setAttribute("groupId", userInfo.getBranchNo());
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("groupList", sortBranchList(groupList));
	}

}
