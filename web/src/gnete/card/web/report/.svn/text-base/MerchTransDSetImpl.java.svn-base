package gnete.card.web.report;

import flink.util.CommonHelper;
import gnete.card.dao.ClusterMerchDAO;
import gnete.card.dao.MerchClusterInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.ClusterMerch;
import gnete.card.entity.MerchClusterInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @File: MerchTransDSetImpl.java
 *
 * @description: 商户菜单角色下的商户交易清算日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-22
 */
@Service("merchTransDSetImpl")
public class MerchTransDSetImpl extends AbstractCardReportLoadImpl {
	
	private final String REPORT_ERROR_MSG = "没有权限查看商户交易清算日结算报表!";
	
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private MerchClusterInfoDAO merchClusterInfoDAO;
	@Autowired
	private ClusterMerchDAO clusterMerchDAO;

	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType,
			String[] params) throws Exception {
		return null;
	}

	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		
		String roleType = userInfo.getRole().getRoleType();
		boolean showMerchList = false;
		boolean showMerchCluster = false;
		
		//如果是运营中心或运营中心部门时,可查看所有商户的报表
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {
			showMerchList = true;
//			request.setAttribute("merchList", merchInfoDAO.findAll());
		}
		// 运营分支机构登录时,可查看自己管理的商户的报表
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			showMerchList = true;
			request.setAttribute("merchList", sortMerchList(merchInfoDAO.findByManage(userInfo.getBranchNo())));
		}
		// 发卡机构登录时,可查看自己管理的商户集群的报表
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())) {
			showMerchCluster = true;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardIssuer", userInfo.getBranchNo());
			setMerchClusterInfo(request, params);
//			List<MerchClusterInfo> merchClusterInfos = merchClusterInfoDAO.findMerchClusterInfo(params);
//			request.setAttribute("merchClusterInfos", merchClusterInfos);
		}
		// 商户角色登录时，可查看自己的报表
		else if (StringUtils.equals(roleType, RoleType.MERCHANT.getValue())) {
			showMerchList = false;
			request.setAttribute("merchId", userInfo.getMerchantNo());
		}
		// 其他角色没有权限查看报表
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}
		
		request.setAttribute("loginRoleType", userInfo.getRole().getRoleType());
	    request.setAttribute("loginBranchCode", userInfo.getBranchNo());
		
		request.setAttribute("showMerchList", showMerchList);
		request.setAttribute("showMerchCluster", showMerchCluster);
	}
	
	public void setMerchClusterInfo(HttpServletRequest request, Map params){
//		request.setAttribute("merchClusterInfos", merchClusterInfoDAO.findAll());
		List<MerchClusterInfo> merchClusterInfos = merchClusterInfoDAO.findMerchClusterInfo(params);
		request.setAttribute("merchClusterInfos", merchClusterInfos);
		
		String[] merchClusterIds = new String[merchClusterInfos.size()];
		for(int i=0; i<merchClusterInfos.size(); i++){
			merchClusterIds[i] =  merchClusterInfos.get(i).getMerchClusterId().toString();
		}
		//集群处理:分别存储各个集群的机构号列表
		Map< String, Object> pars = new HashMap<String, Object>();
		pars.put("merchClusterIds", merchClusterIds);
		List<ClusterMerch> clusterMerchList = clusterMerchDAO.findClusterMerch(pars);
	    HashMap clusterMerchMap = new HashMap();
	    StringBuffer stringBuffer = new StringBuffer();
	    Long merchClusterId = null;
		for(ClusterMerch clusterMerch : clusterMerchList){
			if(null == merchClusterId){//首次进入
				merchClusterId = clusterMerch.getMerchClusterId();
			}else if(merchClusterId.compareTo(clusterMerch.getMerchClusterId()) != 0){//一个集群所有机构遍历完毕
				clusterMerchMap.put(merchClusterId, stringBuffer.toString().substring(0,stringBuffer.length()-1));//剔除
				stringBuffer = new StringBuffer();
				merchClusterId = clusterMerch.getMerchClusterId();
			}else{//同一集群的机构
			}
			stringBuffer.append(clusterMerch.getMerchNo()).append(",");
		}
		if(CommonHelper.isNotEmpty(stringBuffer.toString())){
			clusterMerchMap.put(merchClusterId, stringBuffer.toString().substring(0,stringBuffer.length()-1));
		}
		if(CommonHelper.checkIsNotEmpty(clusterMerchMap)){
		    request.setAttribute("clusterMerchMap", clusterMerchMap);
		}
	}

}
