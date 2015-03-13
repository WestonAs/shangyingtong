package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.ClusterMerchDAO;
import gnete.card.dao.MerchClusterInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ClusterMerch;
import gnete.card.entity.ClusterMerchKey;
import gnete.card.entity.MerchClusterInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class CardClusterMerchAction extends BaseAction {
	
	@Autowired
	private MerchClusterInfoDAO merchClusterInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private ClusterMerchDAO clusterMerchDAO;
	
	private MerchClusterInfo merchClusterInfo;
	private ClusterMerch clusterMerch;
	
	private Paginater page;
	
	private String merchClusterName;
	private String merchNos;//新增用
	private String merchNo;//删除用

	private List<CommonState> statusList;

	private static final String LIST_URL = "/pages/cardClusterMerch/list.do";
	private static final String LIST_URL_DETAIL = "/pages/cardClusterMerch/detail.do";

	@Override
	public String execute() throws Exception {
		this.statusList = CommonState.getList();
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchClusterInfo != null) {
			params.put("merchClusterId", MatchMode.ANYWHERE.toMatchString(merchClusterInfo.getMerchClusterName()));
			params.put("status", merchClusterInfo.getStatus());
		}
		// 运营中心看所有的
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
		}
		// 分支机构查看其管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			List<BranchInfo> branches = this.getMyCardBranch();
			params.put("cardIssuers", branches);
		} else {
			throw new BizException("没有权限查看经营商户集群关系");
		}
		this.page = this.merchClusterInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	/**
	 * 明细
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		this.statusList = CommonState.getList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchClusterId", merchClusterInfo.getMerchClusterId());
		params.put("status", merchClusterInfo.getStatus());
		if(null != clusterMerch){
		    params.put("merch", MatchMode.ANYWHERE.toMatchString(clusterMerch.getMerchNo()));//模糊查询
		}
		this.page = this.clusterMerchDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return DETAIL;
	}
	
	/** 
	 * 新增集群
	 * @return
	 * @throws Exception
	 */
	public String showAddCluster() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
			throw new BizException("没有权限维护经营商户集群");
		}
		return ADD;
	}
	
	/** 
	 * 新增集团
	 * @return
	 * @throws Exception
	 */
	public String addCluster() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notEmpty(merchClusterInfo.getMerchClusterName(), "集群名称不能为空");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchClusterName", merchClusterInfo.getMerchClusterName());
		params.put("status", CommonState.NORMAL.getValue());
		Assert.isNull(this.merchClusterInfoDAO.findByMerchClusterName(params), "集群名称[" + merchClusterInfo.getMerchClusterName() + "]已存在");
		
		merchClusterInfo.setRemark(merchClusterInfo.getRemark());//备注
		merchClusterInfo.setStatus(CommonState.NORMAL.getValue());//正常
		merchClusterInfo.setUpdateBy(getSessionUser().getUserId());
		merchClusterInfo.setUpdateTime(new Date());
		this.merchClusterInfoDAO.insert(merchClusterInfo);
		
		String msg = LogUtils.r("新增集群[{0}][{1}]成功", merchClusterInfo.getMerchClusterId(), merchClusterInfo.getMerchClusterName());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 删除集群
	 * @return
	 * @throws Exception
	 */
	public String deleteCluster() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notEmpty(merchClusterInfo.getMerchClusterId()+"", "集群ID不能为空");
		
		this.merchClusterInfoDAO.delete(merchClusterInfo.getMerchClusterId());
		this.clusterMerchDAO.deleteByMerchClusterId(merchClusterInfo.getMerchClusterId());//删除集群对应的商户数据
		String msg = LogUtils.r("删除集群[{0}][{1}]成功", merchClusterInfo.getMerchClusterId(), merchClusterName);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	//-------------------------------clusterbranch-------------------------------
	/** 
	 * 新增集群关系
	 * @return
	 * @throws Exception
	 */
	public String showAddClusterMerch() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
			throw new BizException("没有权限维护经营商户集群");
		}
		return ADD;
	}
	
	/** 
	 * 新增集群与商户关系
	 * @return
	 * @throws Exception
	 */
	public String addClusterMerch() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo userInfo = getSessionUser();
		Assert.notEmpty(merchClusterInfo.getMerchClusterId()+"", "集群ID不能为空");
		Assert.notEmpty(merchNos, "传入的商户信息不能空");
		Assert.notNull(userInfo, "登录用户信息不能为空");
		
		ClusterMerch merchClusterInfoTemp = new ClusterMerch();
		String[] merchs = merchNos.split(",");
		for (int i = 0; i < merchs.length; i++) {
			ClusterMerchKey clusterMerchKey = new ClusterMerchKey();
			clusterMerchKey.setMerchNo(merchs[i]);
			clusterMerchKey.setMerchClusterId(merchClusterInfo.getMerchClusterId());
			ClusterMerch clusterMerch = (ClusterMerch) this.clusterMerchDAO.findByPk(clusterMerchKey);
			Assert.isNull(clusterMerch, "集群[" + merchClusterInfo.getMerchClusterId() + "]已经与商户[" + merchs[i] + "]建立了关系");
			
			MerchInfo merchInfo = (MerchInfo)merchInfoDAO.findByPk(merchs[i]);
			if(null != merchInfo){
				merchClusterInfoTemp.setMerchName(merchInfo.getMerchName());
			}
			merchClusterInfoTemp.setMerchClusterId(merchClusterInfo.getMerchClusterId());
			merchClusterInfoTemp.setMerchNo(merchs[i]);
			merchClusterInfoTemp.setUpdateBy(getSessionUser().getUserId());
			merchClusterInfoTemp.setUpdateTime(new Date());
			merchClusterInfoTemp.setStatus(CommonState.NORMAL.getValue());//正常
			merchClusterInfoTemp.setRemark(merchClusterInfo.getRemark());//备注
			this.clusterMerchDAO.insert(merchClusterInfoTemp);
		}
		
		String msg = LogUtils.r("新增集群与商户关系[{0}][{1}]成功", merchClusterInfo.getMerchClusterId(), merchNos);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(LIST_URL_DETAIL+"?merchClusterInfo.merchClusterId="+merchClusterInfo.getMerchClusterId(), msg);
		return SUCCESS;
	}
	
	/**
	 * 删除集群与运营关系
	 * @return
	 * @throws Exception
	 */
	public String deleteClusterMerch() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notNull(clusterMerch.getMerchClusterId(), "集群ID不能为空");
		Assert.notEmpty(merchNo, "商户编号不能为空");
		
		ClusterMerchKey clusterMerchKey = new ClusterMerchKey();
		clusterMerchKey.setMerchNo(merchNo);
		clusterMerchKey.setMerchClusterId(clusterMerch.getMerchClusterId());
		this.clusterMerchDAO.delete(clusterMerchKey);
		
		String msg = LogUtils.r("删除集群与商户关系[{0}][{1}]成功", clusterMerch.getMerchClusterId(), merchNo);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(LIST_URL_DETAIL+"?merchClusterInfo.merchClusterId="+merchClusterInfo.getMerchClusterId(), msg);
		return SUCCESS;
	}
	
	public MerchClusterInfo getMerchClusterInfo() {
		return merchClusterInfo;
	}

	public void setMerchClusterInfo(MerchClusterInfo merchClusterInfo) {
		this.merchClusterInfo = merchClusterInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
	}

	public ClusterMerch getClusterMerch() {
		return clusterMerch;
	}

	public void setClusterMerch(ClusterMerch clusterMerch) {
		this.clusterMerch = clusterMerch;
	}
	
	public String getMerchClusterName() {
		return merchClusterName;
	}

	public void setMerchClusterName(String merchClusterName) {
		this.merchClusterName = merchClusterName;
	}

	public String getMerchNos() {
		return merchNos;
	}

	public void setMerchNos(String merchNos) {
		this.merchNos = merchNos;
	}
	
	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}
	
	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}
}
