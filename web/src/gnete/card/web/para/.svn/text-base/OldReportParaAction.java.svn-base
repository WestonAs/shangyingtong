package gnete.card.web.para;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.OldReportParaDAO;
import gnete.card.entity.OldReportPara;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.OldReportService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 旧报表权限参数定义
 * @author aps-lib
 * @history 2011-6-24
 */
public class OldReportParaAction extends BaseAction{

	@Autowired
	private OldReportParaDAO oldReportParaDAO;
	@Autowired
	private OldReportService oldReportService;
	
	private OldReportPara oldReportPara;
	private Paginater page;
	private boolean showChl = false;
	private boolean showCardMerch = false;
	private String issCode;
	private List<CardTypeState> statusList;
	private List<IssType> issTypeList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (oldReportPara != null) {
			params.put("chlCode", oldReportPara.getChlCode());
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(oldReportPara.getChlName()));
			params.put("issName", MatchMode.ANYWHERE.toMatchString(oldReportPara.getIssName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			this.showChl = true;
			this.showCardMerch = true;
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			this.showChl = false;
			this.showCardMerch = true;
		} 
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())){
			params.put("issCode", getSessionUser().getBranchNo());
			this.showChl = false;
			this.showCardMerch = false;
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("issCode", getSessionUser().getMerchantNo());
			this.showChl = false;
			this.showCardMerch = false;
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.oldReportParaDAO.findOldReportPara(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showAdd() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		} 
		initPage();
		return ADD;
	}
	
	public String add() throws Exception {
		
		if(IssType.CARD.getValue().equals(this.oldReportPara.getIssType()) && this.getIssCode() != null){
			this.oldReportPara.setIssCode(this.getIssCode());
		}
		
		this.oldReportPara.setChlCode(this.getSessionUser().getBranchNo());
		this.oldReportService.addOldReportPara(oldReportPara, this.getSessionUserCode());	
		String msg = "新增" + IssType.valueOf(oldReportPara.getIssType()).getName() + 
						"[" + oldReportPara.getIssCode() + "]的旧报表权限参数成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/para/oldReportPara/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		
		Assert.notNull(this.getIssCode(), "机构编号不能为空");
		this.oldReportPara = (OldReportPara) this.oldReportParaDAO.findByPk(this.getIssCode());
		
		this.oldReportService.modifyOldReportPara(oldReportPara, this.getSessionUserCode());
		
		String msg = "修改" + IssType.valueOf(oldReportPara.getIssType()).getName() + 
		"[" + this.oldReportPara.getIssCode() + "]的旧报表权限参数成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/oldReportPara/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		
		Assert.notNull(this.getIssCode(), "机构编号不能为空");
		this.oldReportPara = (OldReportPara) this.oldReportParaDAO.findByPk(this.getIssCode());
		this.oldReportService.deleteOldReportPara(this.getIssCode());
		
		String msg = "删除" + IssType.valueOf(oldReportPara.getIssType()).getName() + 
		"[" + this.oldReportPara.getIssCode() + "]旧报表权限参数成功";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/para/oldReportPara/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		this.statusList = CardTypeState.getList();
		this.issTypeList = IssType.getAll();
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
	
	public OldReportPara getOldReportPara() {
		return oldReportPara;
	}

	public void setOldReportPara(OldReportPara oldReportPara) {
		this.oldReportPara = oldReportPara;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isShowChl() {
		return showChl;
	}

	public void setShowChl(boolean showChl) {
		this.showChl = showChl;
	}

	public boolean isShowCardMerch() {
		return showCardMerch;
	}

	public void setShowCardMerch(boolean showCardMerch) {
		this.showCardMerch = showCardMerch;
	}

	public String getIssCode() {
		return issCode;
	}

	public void setIssCode(String issCode) {
		this.issCode = issCode;
	}

	public List<CardTypeState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardTypeState> statusList) {
		this.statusList = statusList;
	}

	public List<IssType> getIssTypeList() {
		return issTypeList;
	}

	public void setIssTypeList(List<IssType> issTypeList) {
		this.issTypeList = issTypeList;
	}

}
