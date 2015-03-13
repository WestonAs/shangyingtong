package gnete.card.web.para;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.RmaFileParaDAO;
import gnete.card.entity.RmaFilePara;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RmaService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 划付文件权限参数定义
 * @author aps-lib
 * @history 2011-8-31
 */
public class RmaFileParaAction extends BaseAction{

	@Autowired
	private RmaFileParaDAO rmaFileParaDAO;
	@Autowired
	private RmaService rmaService;
	
	private RmaFilePara rmaFilePara;
	private Paginater page;
	private boolean showChl = false;
	private boolean showCardMerch = false;
	private String issCode;
	private List<CardTypeState> statusList;
	private List<IssType> issTypeList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (rmaFilePara != null) {
			params.put("chlCode", rmaFilePara.getChlCode());
			params.put("chlName", MatchMode.ANYWHERE.toMatchString(rmaFilePara.getChlName()));
			params.put("issName", MatchMode.ANYWHERE.toMatchString(rmaFilePara.getIssName()));
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
		
		this.page = this.rmaFileParaDAO.findRmaFilePara(params, this.getPageNumber(), this.getPageSize());
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
		
		if(IssType.CARD.getValue().equals(this.rmaFilePara.getIssType()) && this.getIssCode() != null){
			this.rmaFilePara.setIssCode(this.getIssCode());
		}
		
		this.rmaFilePara.setChlCode(this.getSessionUser().getBranchNo());
		this.rmaService.addRmaFilePara(rmaFilePara, this.getSessionUserCode());	
		
		String msg = "新增" + IssType.valueOf(rmaFilePara.getIssType()).getName() + 
						"[" + rmaFilePara.getIssCode() + "]的划付文件权限参数成功！";
		
		this.log(msg, UserLogType.ADD);
		addActionMessage("/para/rmaFilePara/list.do", msg);
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
		
		Assert.notNull(this.getIssCode(), "机构编号不能为空！");
		
		this.rmaFilePara = (RmaFilePara) this.rmaFileParaDAO.findByPk(this.getIssCode());
		
		this.rmaService.modifyRmaFilePara(rmaFilePara, this.getSessionUserCode());
		
		String msg = "修改" + IssType.valueOf(rmaFilePara.getIssType()).getName() + 
						"[" + this.rmaFilePara.getIssCode() + "]的划付文件权限参数成功！";
		
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/para/rmaFilePara/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if(!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非分支机构不能操作。");
		}
		
		Assert.notNull(this.getIssCode(), "机构编号不能为空！");
		this.rmaService.deleteRmaFilePara(this.getIssCode());
		
		String msg = "删除" + IssType.valueOf(rmaFilePara.getIssType()).getName() + 
						"[" + this.rmaFilePara.getIssCode() + "]划付文件权限参数成功！";
		
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/para/rmaFilePara/list.do", msg);
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

	public RmaService getRmaService() {
		return rmaService;
	}

	public void setRmaService(RmaService rmaService) {
		this.rmaService = rmaService;
	}

	public RmaFilePara getRmaFilePara() {
		return rmaFilePara;
	}

	public void setRmaFilePara(RmaFilePara rmaFilePara) {
		this.rmaFilePara = rmaFilePara;
	}

}
