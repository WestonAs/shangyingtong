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
 * @File: SellProxySellDepositDSetImpl.java
 *
 * @description: 售卡代理菜单下的售卡充值日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
@Service("sellProxySellDepositDSetImpl")
public class SellProxySellDepositDSetImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看售卡代理日结算报表!";
	
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
		
		//如果是运营中心或运营中心部门时,可查看所有售卡代理的报表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			showCardList = true;
			request.setAttribute("cardBranchList", sortBranchList(branchInfoDAO.findByType(RoleType.CARD_SELL.getValue())));
		}
		// 运营分支机构登录时,可查看自己管理的售卡代理的报表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			showCardList = true;
//			List<BranchInfo> cardBranchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo());
//			request.setAttribute("cardBranchList", cardBranchList);
			request.setAttribute("cardBranchList", sortBranchList(branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(), userInfo.getBranchNo())));
		}
		// 售卡代理角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.CARD_SELL.getValue())) {
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
