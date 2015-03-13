package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.report.CardSellDepositDSetType;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: CardSellDepositFeeDayImpl.java
 *
 * @description: 发卡机构售卡充值销售手续费月统计报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-3 下午02:48:48
 */
@Service("cardSellDepositFeeMonthImpl")
public class CardSellDepositFeeMonthImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看发卡机构售卡充值销售手续费月统计报表!";
	
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
		
		//如果是运营中心或运营中心部门时,可查看所有发卡机构的报表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			showCardList = true;
			request.setAttribute("cardBranchList", sortBranchList(branchInfoDAO.findByType(RoleType.CARD.getValue())));
		}
		// 运营分支机构登录时,可查看自己管理的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			showCardList = true;
			request.setAttribute("cardBranchList", sortBranchList(branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo())));
		}
		// 集团机构角色登录时，可查看自己集团下的发卡机构的报表
		else if (StringUtils.equals(roleType, RoleType.GROUP.getValue())) {
			showCardList = true;
			request.setAttribute("cardBranchList", sortBranchList(branchInfoDAO.findByGroupId(userInfo.getBranchNo())));
		}
		// 发卡机构角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
				|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {
			showCardList = false;
			request.setAttribute("cardBranch", userInfo.getBranchNo());
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("reportTypeList", CardSellDepositDSetType.getAll());
		request.setAttribute("showCardList", showCardList);
	}

}
