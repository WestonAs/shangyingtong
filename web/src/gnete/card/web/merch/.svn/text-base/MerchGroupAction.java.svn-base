package gnete.card.web.merch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchToGroupDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchToGroup;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MerchGroupAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class MerchGroupAction extends BaseAction {

	@Autowired
	private MerchGroupDAO merchGroupDAO;
	
	@Autowired
	private MerchService merchService;
	
	@Autowired
	private MerchToGroupDAO merchToGroupDAO;
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private Paginater page;
	
	private MerchGroup merchGroup;
	
	private String merchs;
	
	private String merchNames;
	
	private String branchName;
	private String groupId;

	// 界面选择时是否单选
	private boolean radio;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			throw new BizException("没有权限。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchGroup != null) {
			params.put("groupId", merchGroup.getGroupId());
			params.put("groupName", MatchMode.ANYWHERE.toMatchString(merchGroup.getGroupName()));
		}
		this.page = this.merchGroupDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.merchGroup = (MerchGroup) this.merchGroupDAO.findByPk(merchGroup);
		initPage();
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、分支机构不能操作。");
		}
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		// 调用service业务接口
		this.merchService.addMerchGroup(this.merchGroup, this.merchs, this.getSessionUser());
		
		String msg = LogUtils.r("添加商圈[{0}]成功", this.merchGroup.getGroupId());
		this.addActionMessage("/pages/merchGroup/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	private void initPage(){
		List<MerchToGroup> list = this.merchToGroupDAO.findByGroupId(this.merchGroup.getGroupId());
		this.merchs = "";
		this.merchNames = "";
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				MerchToGroup toGroup = list.get(i);
				merchs += toGroup.getMerchId();
				merchNames += toGroup.getMerchName();
				if (i < list.size() - 1) {
					merchs += ",";
					merchNames += ",";
				}
			}
		}
		
		BranchInfo branchInfo = branchInfoDAO.findBranchInfo(merchGroup.getCardIssuer());
		this.branchName = branchInfo.getBranchName();
	}
	
	public String showModify() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、分支机构不能操作。");
		}
		this.merchGroup = (MerchGroup) this.merchGroupDAO.findByPk(merchGroup);
		initPage();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.merchService.modifyMerchGroup(this.merchGroup, this.merchs, this.getSessionUserCode());

		String msg = LogUtils.r("修改商圈[{0}]成功", this.merchGroup.getGroupId());
		this.addActionMessage("/pages/merchGroup/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		String groupId = request.getParameter("groupId");
		this.merchService.deleteMerchGroup(groupId, this.getSessionUserCode());
		
		String msg = LogUtils.r("删除商圈[{0}]成功", groupId);
		this.addActionMessage("/pages/merchGroup/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchGroup != null) {
			params.put("groupId", merchGroup.getGroupId());
			params.put("groupName", MatchMode.ANYWHERE.toMatchString(merchGroup.getGroupName()));
		}
		this.page = this.merchGroupDAO.findPage(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	/**
	 * 注销商圈
	 */
	public String cancel() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、分支机构不能操作。");
		}
		Assert.notEmpty(groupId, "商圈对象ID不能为空");
		
		merchGroup = new MerchGroup();
		merchGroup.setGroupId(groupId);
		merchGroup = (MerchGroup) merchGroupDAO.findByPk(merchGroup);
		
		merchGroup.setStatus(CommonState.DISABLE.getValue());
		merchGroupDAO.update(merchGroup);
		
		String msg = LogUtils.r("注销ID为[{0}]的商圈成功。", merchGroup.getGroupId());
		log(msg, UserLogType.OTHER);
		addActionMessage("/pages/merchGroup/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 启用商圈
	 */
	public String enable() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、分支机构不能操作。");
		}
		Assert.notEmpty(groupId, "商圈对象ID不能为空");
		
		merchGroup = new MerchGroup();
		merchGroup.setGroupId(groupId);
		merchGroup = (MerchGroup) merchGroupDAO.findByPk(merchGroup);
		
		merchGroup.setStatus(CommonState.NORMAL.getValue());
		merchGroupDAO.update(merchGroup);
		
		String msg = LogUtils.r("启用ID为[{0}]的商圈成功。", merchGroup.getGroupId());
		log(msg, UserLogType.OTHER);
		addActionMessage("/pages/merchGroup/list.do", msg);
		return SUCCESS;
	}
	
	public MerchGroup getMerchGroup() {
		return merchGroup;
	}

	public void setMerchGroup(MerchGroup merchGroup) {
		this.merchGroup = merchGroup;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getMerchs() {
		return merchs;
	}

	public void setMerchs(String merchs) {
		this.merchs = merchs;
	}

	public String getMerchNames() {
		return merchNames;
	}

	public void setMerchNames(String merchNames) {
		this.merchNames = merchNames;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}
	

}
