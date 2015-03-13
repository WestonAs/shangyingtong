package gnete.card.web.report;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.RoleType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: ReportTransSetImpl.java
 *
 * @description: 交易清算日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-6
 */
@Service("reportTransSet")
public class ReportTransSetImpl extends AbstractCardReportLoadImpl {
    
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;

	/**
	 * 交易清算日结算汇总报表
	 */
	private final String TRANS_SET_SUM_FILE = "/transSetSum.raq";
	
	/**
	 * 交易清算日结算明细报表，按收款方统计
	 */
	private final String TRANS_SET_SUM_DETAIL_FILE = "/transSetDetail.raq";

	/**
	 * 交易清算日结算明细报表,按付款方统计
	 */
	private final String TRANS_SET_SUM_DETAIL_PAY_FILE = "/transSetDetailPay.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看交易清算日结算报表!";

	@SuppressWarnings("unchecked")
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {

		String roleType = userInfo.getRole().getRoleType();
		String reportFile = "";
		boolean showDateScope = false;
		boolean showRecvCode = false;
		boolean showPayCode = false;

		StringBuffer payBranch= new StringBuffer(128);
		
		//如果是运营中心或运营中心部门时,看的是汇总表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			reportFile = TRANS_SET_SUM_FILE;
			showDateScope = true;
			showRecvCode = true;
			showPayCode = true;
			
			// 发卡机构是付款方
			List<BranchInfo> branchList = branchInfoDAO.findByType(RoleType.CARD.getValue());
			request.setAttribute("payCodeList", branchList);
			
			// 商户是收款方
			List<MerchInfo> merchList = merchInfoDAO.findAll();
			request.setAttribute("recvCodeList", merchList);
		}
		//如果是分支机构，看的是自己管理的机构的汇总表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			reportFile = TRANS_SET_SUM_FILE;
			showDateScope = true;
			showRecvCode = true;
			showPayCode = true;

			// 管理的发卡机构是付款方时
			List<BranchInfo> branchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(), userInfo.getBranchNo());
			request.setAttribute("payCodeList", branchList);
			
			// 管理的商户是收款方
			List<MerchInfo> merchList = merchInfoDAO.findByManage(userInfo.getBranchNo());
			request.setAttribute("recvCodeList", merchList);
		} 
		//如果是商户，看的是收款方为自己明细报表
		else if (StringUtils.equals(roleType, RoleType.MERCHANT.getValue())) {
			reportFile = TRANS_SET_SUM_DETAIL_FILE;
			showPayCode = true;

			payBranch.append("recvCode=" + userInfo.getMerchantNo() + ";");
			MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(userInfo.getMerchantNo());
			payBranch.append("recvName=" + info.getMerchName() + ";");
		} 
		//如果是发卡机构时，看的是付款方为自己的
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
					|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {	
			reportFile = TRANS_SET_SUM_DETAIL_PAY_FILE;
			showRecvCode = true;
		
			// 收款方为发卡机构的特约商户
			List<MerchInfo> merchList = merchInfoDAO.findFranchMerchList(userInfo.getBranchNo());
			request.setAttribute("recvCodeList", merchList);
			
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
		request.setAttribute("transTypeList", DSetTransType.getTransSetTypeList());// 交易类型
		request.setAttribute("payBranch", payBranch.toString());
		
		request.setAttribute("reportFile", reportFile);
	}

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType, String[] params)
			throws Exception {
		return null;
	}

}
