package gnete.card.web.report;

import flink.util.CommonHelper;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.ClusterBranchDAO;
import gnete.card.dao.ClusterInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ClusterBranch;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.report.CenterOperateType;
import gnete.card.entity.type.report.CenterSearchType;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: CenterOperateFeeImpl.java
 *
 * @description: 中心角色的平台运营手续费报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-8
 */
@Service("centerOperateFee")
public class CenterOperateFeeImpl extends AbstractCardReportLoadImpl {
	
	/**
	 *  积分卡业务平台运营手续费收入明细报表
	 */
//	private final String OPERATE_FEE_DETAIL = "/center/optFeeDetail.raq";

	/**
	 *  积分卡业务平台运营手续费收入汇总报表
	 */
//	private final String OPERATE_FEE_SUM = "/center/optFeeSum.raq";
	
	private final String REPORT_ERROR_MSG = "没有权限查看平台运营手续费报表!";
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	@Autowired
	private ClusterInfoDAO clusterInfoDAO;
	
	@Autowired
	private ClusterBranchDAO clusterBranchDAO;

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		
		String roleType = userInfo.getRole().getRoleType();
		List<CenterOperateType> reportTypeList = null;
		List<CenterSearchType> reportSearchTypeList = null;
		
		//如果是运营中心或运营中心部门时
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			reportTypeList = CenterOperateType.getAll();
			reportSearchTypeList = CenterSearchType.getAll();
			
			request.setAttribute("clusterInfoList", clusterInfoDAO.findAll());
			
			//集群处理:分别存储各个集群的机构号列表
			List<ClusterBranch> clusterBranchList = clusterBranchDAO.findClusterBranchList(null);
		    HashMap clusterBranchMap = new HashMap();
		    StringBuffer stringBuffer = new StringBuffer();
		    Long clusterId = null;
			for(ClusterBranch clusterBranch : clusterBranchList){
				if(null == clusterId){//首次进入
					clusterId = clusterBranch.getClusterid();
				}else if(clusterId.compareTo(clusterBranch.getClusterid()) != 0){//一个集群所有机构遍历完毕
					clusterBranchMap.put(clusterId, stringBuffer.toString().substring(0,stringBuffer.length()-1));//剔除
					stringBuffer = new StringBuffer();
					clusterId = clusterBranch.getClusterid();
				}else{//同一集群的机构
				}
				stringBuffer.append(clusterBranch.getBranchcode()).append(",");
			}
			if(CommonHelper.isNotEmpty(stringBuffer.toString())){
				clusterBranchMap.put(clusterId, stringBuffer.toString().substring(0,stringBuffer.length()-1));
			}
			if(CommonHelper.checkIsNotEmpty(clusterBranchMap)){
			    request.setAttribute("clusterBranchMap", clusterBranchMap);
			}
			
			//所有分支机构号列表
			List<BranchInfo> fenzhiBranchList = branchInfoDAO.findByType(RoleType.FENZHI.getValue());
			request.setAttribute("clusterBranchList", sortBranchList(fenzhiBranchList));
			StringBuffer allFenzhiBranchs = new StringBuffer();
			for(BranchInfo branchInfo : fenzhiBranchList){
				allFenzhiBranchs.append(branchInfo.getBranchCode()).append(",");
			}
			if(CommonHelper.isNotEmpty(allFenzhiBranchs.toString())){
			    request.setAttribute("clusterBranchListStr", allFenzhiBranchs.toString().substring(0,allFenzhiBranchs.length()-1));
			}
		} else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("reportTypeList", reportTypeList);
		request.setAttribute("reportSearchTypeList", reportSearchTypeList);
	}

}
