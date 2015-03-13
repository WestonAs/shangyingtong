package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.ClusterBranchDAO;
import gnete.card.dao.ClusterInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.ClusterBranch;
import gnete.card.entity.ClusterBranchKey;
import gnete.card.entity.ClusterInfo;
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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CardClusterAction extends BaseAction {
	
	@Autowired
	private ClusterInfoDAO clusterInfoDAO;
	@Autowired
	private ClusterBranchDAO clusterBranchDAO;
	
	private ClusterInfo clusterInfo;
	private ClusterBranch clusterBranch;
	
	private String cardBranches; // 运营机构号
	private String branchCode; // 运营机构号
	private String clusterName; // 集群名称
	
	private Paginater page;
	
	private List<CommonState> statusList;

	private static final String LIST_URL = "/pages/cardCluster/list.do";
	private static final String LIST_URL_DETAIL = "/pages/cardCluster/detail.do";

	@Override
	public String execute() throws Exception {
		this.statusList = CommonState.getList();
		Map<String, Object> params = new HashMap<String, Object>();
		if (clusterInfo != null) {
			params.put("clusterId", MatchMode.ANYWHERE.toMatchString(clusterInfo.getClustername()));
			params.put("status", clusterInfo.getStatus());
		}
		// 运营中心看所有的
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
		}
		// 分支机构查看其管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			List<BranchInfo> branches = this.getMyCardBranch();
			if (CollectionUtils.isEmpty(branches)) {
				branches.add(new BranchInfo());
			}
			params.put("branchList", branches);
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
					|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
				params.put("branchCode", getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查看经营机构集群关系");
		}
		this.page = this.clusterInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
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
		params.put("clusterId", clusterInfo.getClusterid());
		params.put("status", clusterInfo.getStatus());
		this.page = this.clusterBranchDAO.findPage(params, this.getPageNumber(), this.getPageSize());
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
			throw new BizException("没有权限维护经营机构集群");
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
		
		Assert.notEmpty(clusterInfo.getClustername(), "集群名称不能为空");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clusterName", clusterInfo.getClustername());
		params.put("status", CommonState.NORMAL.getValue());
		Assert.isNull(this.clusterInfoDAO.findByClusterName(params), "集群名称[" + clusterInfo.getClustername() + "]已存在");
		
		clusterInfo.setRemark(clusterInfo.getRemark());//备注
		clusterInfo.setStatus(CommonState.NORMAL.getValue());//正常
		clusterInfo.setUpdateby(getSessionUser().getUserId());
		clusterInfo.setUpdatetime(new Date());
		this.clusterInfoDAO.insert(clusterInfo);
		
		String msg = LogUtils.r("新增集群[{0}][{1}]成功", clusterInfo.getClusterid(), clusterInfo.getClustername());
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
		
		Assert.notEmpty(clusterInfo.getClusterid()+"", "集群ID不能为空");
		
		this.clusterInfoDAO.delete(clusterInfo.getClusterid());
		this.clusterBranchDAO.deleteByClusterid(clusterInfo.getClusterid());//删除集群对应的运营机构数据
//		String clustername = new String(clusterName.getBytes("iso-8859-1"), "utf-8");//名称单独传送(和表单clusterInfo.clustername有冲突)
		String msg = LogUtils.r("删除集群[{0}][{1}]成功", clusterInfo.getClusterid(), clusterName);
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
	public String showAddClusterBranch() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())
				|| RoleType.FENZHI.getValue().equals(getLoginRoleType()))) {
			throw new BizException("没有权限维护经营机构集群");
		}
		return ADD;
	}
	
	/** 
	 * 新增集群与运营机构关系
	 * @return
	 * @throws Exception
	 */
	public String addClusterBranch() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo userInfo = getSessionUser();
		Assert.notEmpty(clusterInfo.getClusterid()+"", "集群ID不能为空");
		Assert.notEmpty(cardBranches, "传入的运营机构信息不能空");
		Assert.notNull(userInfo, "登录用户信息不能为空");
		
		ClusterBranch clusterInfoTemp = new ClusterBranch();
		String[] branchIds = cardBranches.split(",");
		for (int i = 0; i < branchIds.length; i++) {
			ClusterBranchKey clusterBranchKey = new ClusterBranchKey();
			clusterBranchKey.setBranchcode(branchIds[i]);
			clusterBranchKey.setClusterid(clusterInfo.getClusterid());
			ClusterBranch clusterBranch = (ClusterBranch) this.clusterBranchDAO.findByPk(clusterBranchKey);
			Assert.isNull(clusterBranch, "集群[" + clusterInfo.getClusterid() + "]已经与运营机构关系[" + branchIds[i] + "]建立了关系");
			
			clusterInfoTemp.setClusterid(clusterInfo.getClusterid());
			clusterInfoTemp.setBranchcode(branchIds[i]);
			clusterInfoTemp.setUpdateby(getSessionUser().getUserId());
			clusterInfoTemp.setUpdatetime(new Date());
			clusterInfoTemp.setStatus(CommonState.NORMAL.getValue());//正常
			clusterInfoTemp.setRemark(clusterInfo.getRemark());//备注
			this.clusterBranchDAO.insert(clusterInfoTemp);
		}
		
		String msg = LogUtils.r("新增集群与运营机构关系[{0}][{1}]成功", clusterInfo.getClusterid(), cardBranches);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(LIST_URL_DETAIL+"?clusterInfo.clusterid="+clusterInfo.getClusterid(), msg);
		return SUCCESS;
	}
	
	/**
	 * 删除集群与运营关系
	 * @return
	 * @throws Exception
	 */
	public String deleteClusterBranch() throws Exception {
		this.checkEffectiveCertUser();
		
		Assert.notNull(clusterBranch.getClusterid(), "集群ID不能为空");
		Assert.notEmpty(clusterBranch.getBranchcode(), "运营机构编号不能为空");
		
		ClusterBranchKey clusterBranchKey = new ClusterBranchKey();
		clusterBranchKey.setBranchcode(clusterBranch.getBranchcode());
		clusterBranchKey.setClusterid(clusterBranch.getClusterid());
		this.clusterBranchDAO.delete(clusterBranchKey);
		
		String msg = LogUtils.r("删除集群与运营机构关系[{0}][{1}]成功", clusterInfo.getClusterid(), clusterBranch.getBranchcode());
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(LIST_URL_DETAIL+"?clusterInfo.clusterid="+clusterInfo.getClusterid(), msg);
		return SUCCESS;
	}
	
	public ClusterInfo getClusterInfo() {
		return clusterInfo;
	}

	public void setClusterInfo(ClusterInfo clusterInfo) {
		this.clusterInfo = clusterInfo;
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

	public ClusterBranch getClusterBranch() {
		return clusterBranch;
	}

	public void setClusterBranch(ClusterBranch clusterBranch) {
		this.clusterBranch = clusterBranch;
	}

	public String getCardBranches() {
		return cardBranches;
	}

	public void setCardBranches(String cardBranches) {
		this.cardBranches = cardBranches;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}
