package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @Project: CardWeb
 * @File: SubTransShareImpl.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-12-14下午7:47:49
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
@Service("subTransShare")
public class SubTransShareImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看计费自交易报表!";
	
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
		List<BranchInfo> fenzhiList = new ArrayList<BranchInfo>();
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			request.setAttribute("fenzhiList",sortBranchList(branchInfoDAO.findByType(RoleType.FENZHI.getValue())));
			request.setAttribute("showFenzhi",true);
		}
		// 运营分支机构登录时
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			fenzhiList.add(branchInfoDAO.findBranchInfo(userInfo.getBranchNo()));
			request.setAttribute("fenzhiList",sortBranchList(fenzhiList));
			request.setAttribute("showFenzhi",true);
		}
		// 发卡机构登录时
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())) {
			BranchInfo branchInfo = branchInfoDAO.findBranchInfo(userInfo.getBranchNo());
			BranchInfo fenzhiBranchInfo = branchInfoDAO.findParentByBranch(userInfo.getBranchNo());
			request.setAttribute("fenzhiCode",fenzhiBranchInfo.getBranchCode());
			request.setAttribute("fenzhiName",fenzhiBranchInfo.getBranchName());
			request.setAttribute("branchCode",branchInfo.getBranchCode());
			request.setAttribute("branchName",branchInfo.getBranchName());
			request.setAttribute("showCard", true);
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
	}
}
