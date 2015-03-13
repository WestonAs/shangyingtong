package gnete.card.web.cardtypeset;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 折扣子类型
 * @author aps-lib
 *
 */
public class DiscntClassAction extends BaseAction{

	@Autowired
	private DiscntClassDefDAO discClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	
	private DiscntClassDef discntClassDef;
	private Paginater page;
	private String discntClass;
	private List<BranchInfo> branchList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(discntClassDef != null){
			params.put("discntClass", discntClassDef.getDiscntClass());
			params.put("className",  MatchMode.ANYWHERE.toMatchString(discntClassDef.getClassName()));
			params.put("branchName",  MatchMode.ANYWHERE.toMatchString(discntClassDef.getBranchName()));
		}
		
		branchList = new ArrayList<BranchInfo>();
		
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心/运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(branchList)) {
				params.put("cardIssuer", " ");
			}
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或发卡机构部门
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		} else {
			throw new BizException("没有权限查询。");
		}

		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		this.page = discClassDefDAO.findDiscntClassDef(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 取得折扣子类型明细
	public String detail() throws Exception {
		this.discntClassDef = (DiscntClassDef)this.discClassDefDAO.findByPk(discntClassDef.getDiscntClass());
		String msg = "查询折扣子类型[" + discntClassDef.getDiscntClass() + "]明细成功";
		this.log(msg, UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
	//	initPage();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){
			throw new BizException("非发卡机构不能操作。");
		}
		return ADD;
	}	
	
	public String add() throws Exception {
		
		UserInfo user = this.getSessionUser();
		discntClassDef.setCardIssuer(user.getBranchNo());
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		discntClassDef.setBranchName(branchInfo.getBranchName());
		this.cardTypeSetService.addDiscntClassDef(discntClassDef);	
		String msg = "添加折扣子类型[" + discntClassDef.getDiscntClass() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/cardTypeSet/discntClass/list.do", msg);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){
			throw new BizException("非发卡机构不能操作。");
		}
		this.discntClassDef = (DiscntClassDef)this.discClassDefDAO.findByPk(discntClassDef.getDiscntClass());
		return MODIFY;
	}
	
	public String modify() throws Exception {
		
		this.cardTypeSetService.modifyDiscntClassDef(discntClassDef);
		String msg = "修改折扣子类型[" + discntClassDef.getDiscntClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/discntClass/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.cardTypeSetService.deleteDiscntClassDef(this.getDiscntClass());
		String msg = "删除折扣子类型[" + this.getDiscntClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/cardTypeSet/discntClass/list.do", msg);
		return SUCCESS;
	}

	public DiscntClassDef getDiscntClassDef() {
		return discntClassDef;
	}

	public void setDiscntClassDef(DiscntClassDef discntClassDef) {
		this.discntClassDef = discntClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getDiscntClass() {
		return discntClass;
	}

	public void setDiscntClass(String discntClass) {
		this.discntClass = discntClass;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

}
