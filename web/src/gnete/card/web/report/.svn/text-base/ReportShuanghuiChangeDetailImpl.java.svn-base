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
 * @File: ReportShuanghuiChangeDetailImpl.java
 *
 * @description:
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-31 下午02:40:36
 */
@Service("reportShuanghuiChangeDetail")
public class ReportShuanghuiChangeDetailImpl extends AbstractCardReportLoadImpl {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	/** 双汇卡资金变动明细日报表 */
	private final String SELLCARD_CHANGE_DETAIL_FILE = "/card/sellCardChangeDetail.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看双汇卡资金变动明细日报表!";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		
		String loginRoleType = userInfo.getRole().getRoleType();
		String loginBranchCode = userInfo.getBranchNo();
		String reportFile = null;
		boolean showCardBranch = false;
		List<BranchInfo> cardBranchList = new ArrayList<BranchInfo>();
		
		//如果是运营中心或运营中心部门，可以查看该报表
		if (StringUtils.equals(loginRoleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(loginRoleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = SELLCARD_CHANGE_DETAIL_FILE;
			
			showCardBranch = true;
			cardBranchList = sortBranchList(branchInfoDAO.findByType(RoleType.CARD.getValue()));
		}
		//如果是分支机构，只能是双汇的分支机构能够查看报表
		else if (StringUtils.equals(loginRoleType, RoleType.FENZHI.getValue())) {
			reportFile = SELLCARD_CHANGE_DETAIL_FILE;
			showCardBranch = true;
			
			cardBranchList = sortBranchList(sortBranchList(branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo())));
		} 
		//如果是发卡机构时，只能是双汇自己可以查看
		else if (StringUtils.equals(loginRoleType, RoleType.CARD.getValue())
					|| StringUtils.equals(loginRoleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = SELLCARD_CHANGE_DETAIL_FILE;
			showCardBranch = true;
			
			cardBranchList = sortBranchList(this.branchInfoDAO.findChildrenList(loginBranchCode));
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("showCardBranch", showCardBranch);
		request.setAttribute("cardBranchList", cardBranchList);
		
		request.setAttribute("reportFile", reportFile);
	}

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

}
