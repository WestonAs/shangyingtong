package gnete.card.web.report;

import flink.util.DateUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.ShfrReportType;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
  * @Project: Card
  * @File: ShfrMonthStatisReportLoadImpl.java
  * @See:
  * @description：
        <li>商户代理分润报表</li>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-3
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
@Service("shfrMonthReportLoad")
public class ShfrMonthStatisReportLoadImpl extends AbstractCardReportLoadImpl{
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private final String REPORT_CARD_FILE = "/fkjgFrMonthDs.raq";

	private final String REPORT_PROXY_FILE = "/shdlFrMonthDs.raq";

	private final String REPORT_ERROR_MSG = "没有权限查看商户代理分润月报表!";
	
	/**
	 * @description: 
	 *   <li>向页面输出角色类型(roleType)+报表类型(reportTypeList)+发卡或商户代理列表(branchList or shdlList)+分支机构信息(branchInfo)</li>
	 *   <li>分支机构信息用于当角色为发卡机构或商户代理角色时自身的机构类信息</li>
	
	 */
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
        String roleType = userInfo.getRole().getRoleType();
		
		List<ShfrReportType> shfrReportTypeList = new ArrayList<ShfrReportType>();
        
		BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());

		/**
		 * @description: 
		 *   <li>如果是运营中心或者运营中心部门则可以根据类型查找其下所有的类型机构</li> 
		 *   <li>可查看所有的发卡机构和商户代理的报表类型</li>
		 */
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {

			List<BranchInfo> branchList = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			List<BranchInfo> shdlList = this.branchInfoDAO.findByType(RoleType.CARD_MERCHANT.getValue());

			request.setAttribute("branchList", sortBranchList(branchList));
			request.setAttribute("shdlList", sortBranchList(shdlList));
			
			shfrReportTypeList.add(ShfrReportType.CARD);
			shfrReportTypeList.add(ShfrReportType.MERCHANT);
		}

		/**
		 * @description: 
		 *   <li>如果是分支机构则查询其管辖下的所有相关类型机构</li> 
		 *   <li>可查看所有的发卡机构和商户代理的报表类型</li>
		 */
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			List<BranchInfo> branchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(),
					userInfo.getBranchNo());
			List<BranchInfo> shdlList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD_MERCHANT.getValue(),userInfo.getBranchNo());

			request.setAttribute("branchList", sortBranchList(branchList));
			request.setAttribute("shdlList", sortBranchList(shdlList));

			shfrReportTypeList.add(ShfrReportType.CARD);
			shfrReportTypeList.add(ShfrReportType.MERCHANT);
		}

		/**
		 * @description:
		 *    <li>如果是发卡机构则可查看商户代理，发卡机构及其名称则绑定是其自身信息</li>
		 */
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
				|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {
			List<BranchInfo> shdlList =  this.branchInfoDAO.findCardProxy(userInfo.getBranchNo(), ProxyType.MERCHANT);
			request.setAttribute("shdlList", sortBranchList(shdlList));
			shfrReportTypeList.add(ShfrReportType.CARD_MERCHANT);
			request.setAttribute("branchInfo", branchInfo.getBranchCode() + "|" + branchInfo.getBranchName());
		}

		/**
		 * @description:
		 *   <li>如果是商户代理则可查看分支机构，商户代理及其名称则绑定其自身</li>
		 */
		else if (StringUtils.equals(roleType, RoleType.CARD_MERCHANT.getValue())) {
			List<BranchInfo> branchList = this.branchInfoDAO.findCardByProxy(userInfo.getBranchNo());	
			request.setAttribute("branchList", sortBranchList(branchList));
			shfrReportTypeList.add(ShfrReportType.MERCHANT_CARD);
			request.setAttribute("branchInfo", branchInfo.getBranchCode() + "|" + branchInfo.getBranchName());
		}

		/**
		 * @description:否则页面提示没有权限查看返利报表
		 */
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}

		request.setAttribute("roleType", roleType);
		
		request.setAttribute("reportTypeList", shfrReportTypeList);		
	}

	@Override
	protected String[] getLoadQueryParams(String roleType,String reportType,String[] params) throws Exception{
       
		return (checkTopLevel(roleType)) ? getTopLevelQueryParams(roleType,reportType,params) :
			                                getLowLevelQueryParams(roleType,reportType,params); 
		
    }
	
	/**
	 * 
	  * @description：如果是中心(部门)或分支机构
	  * @param roleType
	  * @param reportType
	  * @param params
	  * @return
	  * @throws Exception  
	  * @version: 2010-12-5 上午10:39:00
	  * @See:
	 */
	private String[] getTopLevelQueryParams(String roleType,String reportType,String[] params) throws Exception{
        String reportFile = null;
		
		StringBuilder searchBuilder = new StringBuilder(100);
		
		String feeDate = params[0];
		
        String branchCode = params[1];
		
		String branchName = getBranchName(branchCode);
		
		//String branchValue = params[1];
		
		//String branchCode = branchValue.split("\\|")[0];

		//String branchName = branchValue.split("\\|")[1];
		
		String[] feeDatePair = DateUtil.getReportDateMonthPare(feeDate);
		/**
		 * @description:且报表类型为发卡机构时，则统计提交发卡机构下所有商户代理的分润报表
		 */
		if(StringUtils.equals(reportType, ShfrReportType.CARD.getValue())) {
			reportFile = REPORT_CARD_FILE;	
			
			searchBuilder.append("branchCode=" + branchCode).append(";")
			             .append("branchName=" + branchName).append(";");
			searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
			             .append("_feeDate=" + feeDatePair[1]).append(";");
		}
		
		/**
		 * @description:且报表类型为商户代理时,则统计提交售卡代理关联发卡机构的分润报表
		 */
		else if (StringUtils.equals(reportType, ShfrReportType.MERCHANT.getValue())) {
			reportFile = REPORT_PROXY_FILE;	
			
			searchBuilder.append("merchId=" + branchCode).append(";")
			             .append("merchName=" + branchName).append(";");
	        searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
	                     .append("_feeDate=" + feeDatePair[1]).append(";");
		} else {
			//否则返回为空
			return new String[] {};
		}
		
		/**
		 * @description:<li>增加制表日期</li>
		 */
		searchBuilder.append("printDate=" + DateUtil.getCurrentDate());
		
		return new String[] {reportFile,searchBuilder.toString()};
			
	}
	
	/**
	 * 
	  * @description：如果是发卡机构或者售卡代理
	  * @param roleType
	  * @param reportType
	  * @param params
	  * @return
	  * @throws Exception  
	  * @version: 2010-12-5 上午10:39:12
	  * @See:
	 */
	private String[] getLowLevelQueryParams(String roleType,String reportType,String[] params) throws Exception {
		String reportFile = null;
			
		StringBuilder searchBuilder = new StringBuilder(100);
		
		String feeDate = params[0];
		
        String branchValue = params[1];
		
		String branchCode = branchValue.split("\\|")[0];

		String branchName = branchValue.split("\\|")[1];
		
		String[] feeDatePair = DateUtil.getReportDateMonthPare(feeDate);
		
		//String branchIdValue = params[2];
		
		//String branchId = branchIdValue.split("\\|")[0];
		
		String branchId = params[2];
		
		if(StringUtils.equals(roleType,  RoleType.CARD.getValue()) && 
				StringUtils.equals(reportType, ShfrReportType.CARD_MERCHANT.getValue())) {
			reportFile = REPORT_CARD_FILE;	
			
			searchBuilder.append("branchCode=" + branchCode).append(";")
                         .append("branchName=" + branchName).append(";");
            searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
                         .append("_feeDate=" + feeDatePair[1]).append(";");
            searchBuilder.append("merchId=" + branchId).append(";");
			
		} else if (StringUtils.equals(roleType, RoleType.CARD_SELL.getValue()) &&
				     StringUtils.equals(reportType, ShfrReportType.MERCHANT_CARD.getValue())) {
			reportFile = REPORT_PROXY_FILE;
			
			searchBuilder.append("merchId=" + branchCode).append(";")
                         .append("merchName=" + branchName).append(";");
            searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
                         .append("feeDate=" + feeDatePair[1]).append(";");
            searchBuilder.append("orgId=" + branchId).append(";");
		} else {
			//否则返回为空
			return new String[] {};
		}
		
		/**
		 * @description:<li>增加制表日期</li>
		 */
		searchBuilder.append("printDate=" + DateUtil.getCurrentDate());
		
		return new String[] {reportFile,searchBuilder.toString()};
	}
	
	private String getBranchName(String branchCode) {
		BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(branchCode);
		
		return branchInfo.getBranchName();
	}
	
	private boolean checkTopLevel(String roleType) {
		return (StringUtils.equals(roleType, RoleType.CENTER.getValue()) || 
		                      StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue()) ||
		                      StringUtils.equals(roleType, RoleType.FENZHI.getValue()));
		
	}

}
