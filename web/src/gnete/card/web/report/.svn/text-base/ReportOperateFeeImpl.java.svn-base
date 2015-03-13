package gnete.card.web.report;


import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.OptFeeReportType;
import gnete.card.entity.type.RoleType;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ReportOperateFeeImpl.java
 *
 * @description: 运营手续费报表返回
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-2
 */
@Service("reportOperateFee")
public class ReportOperateFeeImpl extends AbstractCardReportLoadImpl {
    
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	/**
	 * 运营手续费汇总(运营手续费及分润明细报表)
	 */
	private final String OPERATE_FEE_SUM_FILE = "/operateFeeSum.raq";
	
	/**
	 * 运营手续费按发卡机构统计(发卡机构运营手续费报表)
	 */
	private final String OPERATE_FEE_CARD_FILE = "/operateFeeCard.raq";
	
	/**
	 * 按分支机构统计(管理方分润报表)
	 */
	private final String OPERATE_FEE_FENZHI_FILE = "/operateFeeFenzhi.raq";
	
	/**
	 * 按代理机构统计(发展方分润报表)
	 */
	private final String OPERATE_FEE_PROXY_FILE = "/operateFeeProxy.raq";
	
	/**
	 * 按机具出机方统计(机具出机方分润报表)
	 */
	private final String OPERATE_FEE_PROV_FILE = "/operateFeeProv.raq";
	
	/**
	 * 按机具维护方统计(机具维护方分润报表)
	 */
	private final String OPERATE_FEE_MANAGE_FILE = "/operateFeeManage.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看运营手续费报表!";

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
			reportFile = OPERATE_FEE_SUM_FILE;
			reportTypeList = OptFeeReportType.getAll();
			
			request.setAttribute("branchList", branchInfoDAO.findByType(RoleType.CARD.getValue()));
			request.setAttribute("fenzhiList", branchInfoDAO.findByType(RoleType.FENZHI.getValue()));
			request.setAttribute("proxyList", branchInfoDAO.findByType(RoleType.AGENT.getValue()));
			request.setAttribute("provList", branchInfoDAO.findByType(RoleType.TERMINAL.getValue()));
			request.setAttribute("manageList", branchInfoDAO.findByType(RoleType.TERMINAL_MAINTAIN.getValue()));
		}
		//如果是分支机构，可查分支
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			reportFile = OPERATE_FEE_FENZHI_FILE;
			reportTypeList = OptFeeReportType.getAll();

			List<BranchInfo> branchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo());
			request.setAttribute("branchList", branchList);
			
			List<BranchInfo> fenzhiList = new ArrayList<BranchInfo>(); 
			BranchInfo fenzhi = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			fenzhiList.add(fenzhi);
			request.setAttribute("fenzhiList", fenzhiList);
			
			List<BranchInfo> proxyList = branchInfoDAO.findByTypeAndManage(RoleType.AGENT.getValue(), userInfo.getBranchNo());
			request.setAttribute("proxyList", proxyList);
			
			List<BranchInfo> provList = branchInfoDAO.findByTypeAndManage(RoleType.TERMINAL.getValue(), userInfo.getBranchNo());
			request.setAttribute("provList", provList);
			
			List<BranchInfo> manageList = branchInfoDAO.findByTypeAndManage(RoleType.TERMINAL_MAINTAIN.getValue(), userInfo.getBranchNo());
			request.setAttribute("manageList", manageList);
		} 
		//如果是发卡机构
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
					|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = OPERATE_FEE_CARD_FILE;
		
			branch.append("branchCode=" + userInfo.getBranchNo() + ";");
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			branch.append("branchName=" + info.getBranchName() + ";");
		}
		//如果是运营代理
		else if (StringUtils.equals(roleType, RoleType.AGENT.getValue())) {
			reportFile = OPERATE_FEE_PROXY_FILE;
			
			branch.append("proxyCode=" + userInfo.getBranchNo() + ";");
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			branch.append("proxyName=" + info.getBranchName() + ";");
		} 
		//如果是机具出机方
		else if (StringUtils.equals(roleType, RoleType.TERMINAL.getValue())) {
			reportFile = OPERATE_FEE_PROV_FILE;
			
			branch.append("provCode=" + userInfo.getBranchNo() + ";");
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			branch.append("provName=" + info.getBranchName() + ";");
		} 
		//如果是机具维护方
		else if (StringUtils.equals(roleType, RoleType.TERMINAL_MAINTAIN.getValue())) {
			reportFile = OPERATE_FEE_MANAGE_FILE;
			
			branch.append("manageCode=" + userInfo.getBranchNo() + ";");
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			branch.append("manageName=" + info.getBranchName() + ";");
		} 
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		//填充报表类型列表
		//paraMap.put("reportTypeList", reportTypeList);
		request.setAttribute("transTypeList", DSetTransType.getDsetTransTypeList());// 交易类型
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
