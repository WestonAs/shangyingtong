package gnete.card.web.pointAccService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.PointAccRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PointAccReg;
import gnete.card.entity.state.PointAccState;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointAccService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 积分充值及账户变更
 * @author aps-lib
 *
 */
public class PointAccRegAction extends BaseAction{

	@Autowired
	private PointAccRegDAO pointAccRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointAccService pointAccService;
	
	private PointAccReg pointAccReg;
	private Paginater page;
	private Long pointAccId;
	private Collection<PointAccTransYype> pointAccTransTypeList;
	private Collection<PointAccState> pointAccStatusList;
	private List<BranchInfo> branchList;
	private boolean showCard = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.pointAccTransTypeList = PointAccTransYype.getAll();
		this.pointAccStatusList = PointAccState.ALL.values();
		branchList = new ArrayList<BranchInfo>();
		
		if (pointAccReg != null){
			params.put("pointAccId", pointAccReg.getPointAccId());
			params.put("transType", pointAccReg.getTransType());
			params.put("status", pointAccReg.getStatus());
			params.put("cardIssuer", pointAccReg.getCardIssuer());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(pointAccReg.getBranchName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(branchList)){
				params.put("cardIssuer", " ");
			}
		}
		// 发卡机构、发卡机构部门
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			showCard = true;
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		this.page = this.pointAccRegDAO.findPointAccReg(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.pointAccReg = (PointAccReg) this.pointAccRegDAO
				.findByPk(this.pointAccReg.getPointAccId());
		return DETAIL;
	}
	
	// 注销
	public String cancel() throws Exception {
		
		// 非发卡机构不能操作
		if(!RoleType.CARD.getValue().equals(getLoginRoleType())){
			throw new BizException("没有权限操作。");
		}
		
		this.pointAccService.stopPointAcc(this.getPointAccId());
		this.pointAccReg = (PointAccReg) this.pointAccRegDAO.findByPk(this.getPointAccId());
		Assert.notNull(this.pointAccReg, "对象不存在。");
		
		String msg = "注销文件["+this.pointAccReg.getFileName()+"]成功！";
		this.addActionMessage("/pointAccService/pointAccReg/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		
		return SUCCESS;
	}

	public PointAccReg getPointAccReg() {
		return pointAccReg;
	}

	public void setPointAccReg(PointAccReg pointAccReg) {
		this.pointAccReg = pointAccReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Long getPointAccId() {
		return pointAccId;
	}

	public void setPointAccId(Long pointAccId) {
		this.pointAccId = pointAccId;
	}

	public Collection<PointAccTransYype> getPointAccTransTypeList() {
		return pointAccTransTypeList;
	}

	public void setPointAccTransTypeList(
			Collection<PointAccTransYype> pointAccTransTypeList) {
		this.pointAccTransTypeList = pointAccTransTypeList;
	}

	public Collection<PointAccState> getPointAccStatusList() {
		return pointAccStatusList;
	}

	public void setPointAccStatusList(Collection<PointAccState> pointAccStatusList) {
		this.pointAccStatusList = pointAccStatusList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

}
