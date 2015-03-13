package gnete.card.web.cardtypeset;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SettMthdType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 次卡子类型
 * @author aps-lib
 *
 */
public class AccuClassAction extends BaseAction {
	
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	
	private AccuClassDef accuClassDef;
	private Paginater page;
	private Collection jinstTypeList;
	private Collection settMthdList;
	private List<BranchInfo> branchList;
	private boolean showSettAmt = false;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//String jinsId = this.getCardIssuer();
		//params.put("jinsId", jinsId);
		if(accuClassDef!=null){
			params.put("className", MatchMode.ANYWHERE.toMatchString(accuClassDef.getClassName()));
		}
		
		branchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(branchList)){
				params.put("jinsId", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		this.page = accuClassDefDAO.findAccuClassDef(params, this.getPageNumber(), this.getPageSize());
		jinstTypeList = IssType.getAll();
		settMthdList = SettMthdType.getForAccu();
		return LIST;
	}
	
	public String detail() throws Exception {
		this.accuClassDef = (AccuClassDef)this.accuClassDefDAO.findByPk(accuClassDef.getAccuClass());
		if(SettMthdType.FIXSUM.getValue().equals(accuClassDef.getSettMthd())){
			showSettAmt = true;
		}
		String msg = "查询次卡子类型[" + accuClassDef.getAccuClass() + "明细]成功";
		this.log(msg, UserLogType.SEARCH);
		addActionMessage("/fee/accuClassDef/list.do", msg);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构不能定义次卡类型。");
		}
		
		this.getCardIssuer();
		jinstTypeList = IssType.getAll();
		settMthdList = SettMthdType.getForAccu();
		return ADD;
	}
	
	public String add() throws Exception {
		
		UserInfo user = this.getSessionUser();
		RoleInfo roleInfo = user.getRole();
		
		if(RoleType.CARD.getValue().equals(roleInfo.getRoleType())){
			accuClassDef.setCardIssuer(user.getBranchNo());
			accuClassDef.setJinstType(IssType.CARD.getValue());
			accuClassDef.setJinstId(user.getBranchNo());
			BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(user.getBranchNo());
			accuClassDef.setJinstName(branchInfo.getBranchName());
		}
		
		this.cardTypeSetService.addAccuClassDef(accuClassDef);	
		String msg = "添加次卡子类型[" + accuClassDef.getAccuClass() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/cardTypeSet/accuClass/list.do", msg);
		return SUCCESS;
	}
	

	public String showModify() throws Exception {
		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构不能修改次卡类型。");
		}
		
		this.getCardIssuer();
		this.accuClassDef = (AccuClassDef)this.accuClassDefDAO.findByPk(accuClassDef.getAccuClass());
		jinstTypeList = IssType.getAll();
		settMthdList = SettMthdType.getAll();
		return MODIFY;
	}
	public String modify() throws Exception {
		
		this.cardTypeSetService.modifyAccuClassDef(accuClassDef);
		String msg = "修改次卡子类型[" + accuClassDef.getAccuClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/accuClass/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		this.getCardIssuer();
		this.cardTypeSetService.deleteAccuClassDef(accuClassDef);
		String msg = "删除次卡子类型[" + accuClassDef.getAccuClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/cardTypeSet/accuClass/list.do", msg);
		return SUCCESS;
	}

	
	private String getCardIssuer() throws BizException{
		UserInfo user = this.getSessionUser();
		String cardIssuer = null;
		
		if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){
			cardIssuer = user.getBranchNo();
		}
		else if(user.getRole().getRoleType().equals(RoleType.CENTER.getValue())){
			cardIssuer = "";
		}
		else{
			throw new BizException("用户角色不是营运中心或者发卡机构,不允许进行操作");
		}
		return cardIssuer;
	}

	public AccuClassDef getAccuClassDef() {
		return accuClassDef;
	}

	public void setAccuClassDef(AccuClassDef accuClassDef) {
		this.accuClassDef = accuClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getJinstTypeList() {
		return jinstTypeList;
	}

	public void setJinstTypeList(Collection jinstTypeList) {
		this.jinstTypeList = jinstTypeList;
	}

	public Collection getSettMthdList() {
		return settMthdList;
	}

	public void setSettMthdList(Collection settMthdList) {
		this.settMthdList = settMthdList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowSettAmt() {
		return showSettAmt;
	}

	public void setShowSettAmt(boolean showSettAmt) {
		this.showSettAmt = showSettAmt;
	}
	
}
