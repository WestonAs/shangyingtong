package gnete.card.web.para;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.InsServiceAuthorityDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.InsServiceAuthority;
import gnete.card.entity.InsServiceAuthorityKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.InsServiceType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.InsServiceAuthorityService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: InsServiceAction.java
 *
 * @description: 业务权限参数定义
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-06-21
 */
public class InsServiceAction extends BaseAction{

	@Autowired
	private InsServiceAuthorityDAO insServiceAuthorityDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private InsServiceAuthorityService insServiceAuthorityService;
	
	private InsServiceAuthority insServiceAuthority;
	private Paginater page;
	private List<CardTypeState> statusList;
	private List<InsServiceType> insServiceTypeList; 
	private List<IssType> insTypeList;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;
	private String branchCode;
	private String insId;
	private String insType;
	private String serviceId;
	private boolean showQuery = false;
	
	private String[] serviceIds; //业务权限点id
	
	@Override
	public String execute() throws Exception {
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (insServiceAuthority != null) {
			params.put("insId", insServiceAuthority.getInsId());
			params.put("insName", MatchMode.ANYWHERE.toMatchString(insServiceAuthority.getInsName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			this.showQuery = true;
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			
			if(CollectionUtils.isEmpty(branchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("branchCode", " ");
			}
			this.showQuery = true;
		} 
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())){
			params.put("insId", getSessionUser().getBranchNo());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("insId", getSessionUser().getMerchantNo());
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = branchList.size() + merchInfoList.size();
			String[] insIds = new String[length];
			int i = 0;
			for( ; i<branchList.size(); i++){
				insIds[i] = (branchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				insIds[i] = (merchInfoList.get(i-branchList.size())).getMerchId();
			}
			params.put("insIds", insIds);
		}
		
		this.page = this.insServiceAuthorityDAO.findInsServiceAuthority(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		hasRightToDo();
		initPage();
		return ADD;
	}
	
	public String add() throws Exception {
		hasRightToDo();
		
		if(IssType.CARD.getValue().equals(this.insServiceAuthority.getInsType()) && this.getBranchCode() != null){
			this.insServiceAuthority.setInsId(this.getBranchCode());
		}
		
		this.insServiceAuthorityService.addInsServiceAuthority(insServiceAuthority, this.serviceIds, this.getSessionUserCode());
		StringBuffer sb = new StringBuffer(128);
		for (String id : this.serviceIds) {
			sb.append(InsServiceType.valueOf(id).getName()).append(",");
		}
		String serviceName = sb.toString();
		serviceName = serviceName.substring(0, serviceName.lastIndexOf(","));
		String msg = "新增" + IssType.valueOf(insServiceAuthority.getInsType()).getName() + "[" 
				+ this.insServiceAuthority.getInsId() + "]业务类型为[" + serviceName + "]的业务权限成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/para/insServicePara/list.do", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		hasRightToDo();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		hasRightToDo();
		
		InsServiceAuthorityKey key = new InsServiceAuthorityKey();
		key.setInsId(this.getInsId());
		key.setInsType(this.getInsType());
		key.setServiceId(this.getServiceId());
		this.insServiceAuthority = (InsServiceAuthority) this.insServiceAuthorityDAO.findByPk(key);
		
		this.insServiceAuthorityService.modifyInsServiceAuthority(insServiceAuthority, this.getSessionUserCode());
		String msg = "修改" + IssType.valueOf(insServiceAuthority.getInsType()).getName() + 
		"[" + this.insServiceAuthority.getInsId() + "]业务类型为[" + InsServiceType.valueOf(this.insServiceAuthority.getServiceId()).getName() + "]的业务权限成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/insServicePara/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		hasRightToDo();
		InsServiceAuthorityKey key = new InsServiceAuthorityKey();
		key.setInsId(this.getInsId());
		key.setInsType(this.getInsType());
		key.setServiceId(this.getServiceId());
		
		this.insServiceAuthorityService.deleteInsServiceAuthority(key);
		String msg = "删除" + IssType.valueOf(key.getInsType()).getName() 
				+ "[" + key.getInsId() + "]业务类型为[" 
				+ InsServiceType.valueOf(key.getServiceId()).getName() + "]的业务权限成功";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/para/insServicePara/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		
		this.statusList = CardTypeState.getList();
		this.insTypeList = IssType.getAll();
		//this.insServiceTypeList = InsServiceType.getAll();
	}
	
	// 根据机构类型查询可用的业务类型列表，服务端查询，返回到前端
	public void serviceList() throws BizException {
		String insType = this.insServiceAuthority.getInsType();
		
		//发卡机构
		if(IssType.CARD.getValue().equals(insType)){
			this.insServiceTypeList =  InsServiceType.getAll();
		}
		// 商户
		else if(IssType.MERCHANT.getValue().equals(insType)){
			this.insServiceTypeList =  InsServiceType.getMerchServiceList();
		}
		else {
			this.insServiceTypeList.clear();
		}
		if (CollectionUtils.isNotEmpty(insServiceTypeList)) {
			StringBuffer sb = new StringBuffer(128);
			for (InsServiceType type : insServiceTypeList) {
				sb.append("<input type=\"checkbox\" name=\"serviceIds\" value=\"")
							.append(type.getValue()).append("\"/>")
							.append(type.getName()).append("<br />");
			}
			respond(sb.toString());
		}
	}
	
	private void hasRightToDo() throws BizException {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())
				&& !RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				&& !RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("没有权限进行该操作。");
		} 
	}
	
	/**
	 * 返回登录机构
	 * @return
	 */
	public String getParent() {
		if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			return super.getSessionUser().getBranchNo();
		}
		return "";
	}

	public InsServiceAuthority getInsServiceAuthority() {
		return insServiceAuthority;
	}

	public void setInsServiceAuthority(InsServiceAuthority insServiceAuthority) {
		this.insServiceAuthority = insServiceAuthority;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CardTypeState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardTypeState> statusList) {
		this.statusList = statusList;
	}

	public List<InsServiceType> getInsServiceTypeList() {
		return insServiceTypeList;
	}

	public void setInsServiceTypeList(List<InsServiceType> insServiceTypeList) {
		this.insServiceTypeList = insServiceTypeList;
	}

	public List<IssType> getInsTypeList() {
		return insTypeList;
	}

	public void setInsTypeList(List<IssType> insTypeList) {
		this.insTypeList = insTypeList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public boolean isShowQuery() {
		return showQuery;
	}

	public void setShowQuery(boolean showQuery) {
		this.showQuery = showQuery;
	}

	public String[] getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String[] serviceIds) {
		this.serviceIds = serviceIds;
	}

}
