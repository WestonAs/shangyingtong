package gnete.card.web.report;

import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @File: SingleProductCenterPOSDayRevImpl.java
 *
 * @description: 运营中心单机产品POS缴费月收入汇总报表（套餐费）
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-26 下午08:06:49
 */
@Service("singleProductCenterPOSMonthRev")
public class SingleProductCenterPOSMonthRevImpl extends AbstractCardReportLoadImpl {
	
	/** 运营中心单机产品POS缴费月收入汇总报表（套餐费）*/
	private final String CENTER_POSPLAN_REVSUM_FILE = "/singleProduct/centerPOSPlanRevMonthSum.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看运营中心单机产品POS缴费月收入汇总报表（套餐费）!";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		
		String loginRoleType = userInfo.getRole().getRoleType();
		String reportFile = null;
		
		//如果是运营中心或运营中心部门，可以查看该报表
		if (StringUtils.equals(loginRoleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(loginRoleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = CENTER_POSPLAN_REVSUM_FILE;
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("reportFile", reportFile);
	}

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

}
