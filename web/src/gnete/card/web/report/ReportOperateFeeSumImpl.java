package gnete.card.web.report;


import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.OptFeeReportType;
import gnete.card.entity.type.OptFeeSumReportType;
import gnete.card.entity.type.RoleType;
import gnete.etc.Symbol;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ReportOperateFeeImpl.java
 *
 * @description: 运营手续费及分润汇总报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-2
 */
@Service("reportOperateFeeSum")
public class ReportOperateFeeSumImpl extends AbstractCardReportLoadImpl {
    
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private final String REPORT_ERROR_MSG = "没有权限查看运营手续费及分润汇总报表!";

	@SuppressWarnings("unchecked")
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo){
		// 取得Session中用户的信息
		//UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);

		String roleType = userInfo.getRole().getRoleType();
		String reportFile = null;
		List<OptFeeReportType> reportTypeList = null;

		StringBuffer branch= new StringBuffer(128);
		
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			reportTypeList = OptFeeSumReportType.getAll();
			BranchInfo centerBranch = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			
			List<BranchInfo> branchList = branchInfoDAO.findByType(RoleType.CARD.getValue());
			request.setAttribute("branchList", sortBranchList(branchList));

			List<BranchInfo> fenzhiList = branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			fenzhiList.add(centerBranch);
			request.setAttribute("fenzhiList", sortBranchList(fenzhiList));
			
			List<BranchInfo> proxyList = branchInfoDAO.findByType(RoleType.AGENT.getValue());
			proxyList.add(centerBranch);
			request.setAttribute("proxyList", sortBranchList(proxyList));
			
			List<BranchInfo> provList = branchInfoDAO.findByType(RoleType.TERMINAL.getValue());
			proxyList.add(centerBranch);
			request.setAttribute("provList", sortBranchList(provList));
			
			List<BranchInfo> manageList = branchInfoDAO.findByType(RoleType.TERMINAL_MAINTAIN.getValue());
			proxyList.add(centerBranch);
			request.setAttribute("manageList", sortBranchList(manageList));
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		//填充报表类型列表
		//paraMap.put("reportTypeList", reportTypeList);
		String showReportType = "";
		if (CollectionUtils.isNotEmpty(reportTypeList)) {
			showReportType = Symbol.YES;
		} else {
			showReportType = Symbol.NO;
		}
		request.setAttribute("showReportType", showReportType); // 是否显示报表类型选择
		request.setAttribute("reportTypeList", reportTypeList);
		request.setAttribute("branchReport", branch.toString());

		request.setAttribute("reportFile", reportFile);
	}

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType, String[] params)
			throws Exception {
		return null;
	}

}
