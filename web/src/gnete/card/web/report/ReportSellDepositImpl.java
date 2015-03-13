package gnete.card.web.report;


import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ReportSellDepositImpl.java
 *
 * @description: 售卡充值日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-6
 */
@Service("reportSellDeposit")
public class ReportSellDepositImpl extends AbstractCardReportLoadImpl {
    
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	/**
	 * 售卡充值日结算汇总报表
	 */
	private final String SELL_DEPOSIT_DSET_SUM_FILE = "/sellDepositSum.raq";
	
	/**
	 * 售卡充值日结算明细报表
	 */
	private final String SELL_DEPOSIT_DSET_DETAIL_FILE = "/sellDepositDetail.raq";

	/**
	 * 售卡充值日结算明细报表
	 */
	private final String SELL_DEPOSIT_DSET_DETAIL_PAY_FILE = "/sellDepositDetailPay.raq";
	
	private final String REPORT_ERROR_MSG = "没有权限查看售卡充值日结算报表!";

	@SuppressWarnings("unchecked")
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo)  {

		String roleType = userInfo.getRole().getRoleType();
		String reportFile = null;
		boolean showDateScope = false;
		boolean showRecvCode = false;
		boolean showPayCode = false;

		StringBuffer payBranch= new StringBuffer(128);
		
		//如果是运营中心或运营中心部门时,看的是汇总表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = SELL_DEPOSIT_DSET_SUM_FILE;
			showDateScope = true;
			showRecvCode = true;
			showPayCode = true;
			
			// 发卡机构是付款方时
			List<BranchInfo> branchList = branchInfoDAO.findByType(RoleType.CARD.getValue());
			request.setAttribute("recvCodeList", branchList);
			
			// 售卡代理是付款方时
			List<BranchInfo> sellList = branchInfoDAO.findByType(RoleType.CARD_SELL.getValue());
			sellList.addAll(0, branchList);
			request.setAttribute("payCodeList", sellList);
		}
		//如果是分支机构，看的是自己管理的机构的汇总表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			reportFile = SELL_DEPOSIT_DSET_SUM_FILE;
			showDateScope = true;
			showRecvCode = true;
			showPayCode = true;

			// 管理的发卡机构是付款方时
			List<BranchInfo> branchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo());
			request.setAttribute("recvCodeList", branchList);
			
			// 管理的售卡代理是付款方时
			List<BranchInfo> sellList = branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(), userInfo.getBranchNo());
			sellList.addAll(0, branchList);
			request.setAttribute("payCodeList", sellList);
		} 
		//如果是发卡机构时，看的是收款方为自己的
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
					|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = SELL_DEPOSIT_DSET_DETAIL_FILE;
			showPayCode = true;
			
			//付款方为自己和自己的售卡代理
			List<BranchInfo> branchList = branchInfoDAO.findCardProxy(userInfo.getBranchNo(), ProxyType.SELL);
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			branchList.add(info);
			request.setAttribute("payCodeList", branchList);
		
			payBranch.append("recvCode=" + userInfo.getBranchNo() + ";");
			payBranch.append("recvName=" + info.getBranchName() + ";");
		}
		//如果是售卡代理时，看的是付款方为自己的
		else if (StringUtils.equals(roleType, RoleType.CARD_SELL.getValue())) {	
			reportFile = SELL_DEPOSIT_DSET_DETAIL_PAY_FILE;
			
			payBranch.append("payCode=" + userInfo.getBranchNo() + ";");
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());
			payBranch.append("payName=" + info.getBranchName() + ";");
		}
		//否则提示没有权限
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("showDateScope", showDateScope);
		request.setAttribute("showRecvCode", showRecvCode);
		request.setAttribute("showPayCode", showPayCode);
		request.setAttribute("transTypeList", DSetTransType.getSellCardTypeList());// 交易类型
		request.setAttribute("payBranch", payBranch.toString());
		
		request.setAttribute("reportFile", reportFile);
	}

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType, String[] params)
			throws Exception {
		return null;
	}

}
