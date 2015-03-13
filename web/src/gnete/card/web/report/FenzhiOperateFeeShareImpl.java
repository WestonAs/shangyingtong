package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.report.FenzhiFeeShareType;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: FenzhiOperateFeeShareImpl.java
 *
 * @description: 运营分支机构运营手续费及分润报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-10
 */
@Service("fenzhiOperateFeeShare")
public class FenzhiOperateFeeShareImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看运营手续费及分润报表!";
	
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
		List<FenzhiFeeShareType> reportTypeList = FenzhiFeeShareType.getAll();
		
		List<BranchInfo> fenzhiList = new ArrayList<BranchInfo>();
		
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			fenzhiList = branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			request.setAttribute("fenzhiList", sortBranchList(fenzhiList));
			request.setAttribute("showfenzhi", false);
		}
		// 运营分支机构登录时
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			fenzhiList.add(branchInfoDAO.findBranchInfo(userInfo.getBranchNo()));
			request.setAttribute("fenzhiList", sortBranchList(fenzhiList));
			request.setAttribute("showfenzhi", true);
			request.setAttribute("fenzhicode", fenzhiList.get(0).getBranchCode());
			request.setAttribute("fenzhiname", fenzhiList.get(0).getBranchName());
//			request.setAttribute("cardBranchList", branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo()));
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("reportTypeList", reportTypeList);
	}

}
