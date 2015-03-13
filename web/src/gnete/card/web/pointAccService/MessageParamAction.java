package gnete.card.web.pointAccService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MessageParamDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MessageParam;
import gnete.card.entity.MessageParamKey;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointAccService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class MessageParamAction extends BaseAction{

	@Autowired
	private MessageParamDAO messageParamDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointAccService pointAccService;
	
	private MessageParam messageParam;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<BranchInfo> branchList;
	private boolean showCard = false;
	private String cardIssuer;
	private String eventCode;
	private String updateEventCode;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				params.put("cardIssuer", " ");
			}
		}
		// 发卡机构
		else if(RoleType.CARD.getValue().equals(getLoginRoleType())){
			this.showCard = true;
			this.branchList = new ArrayList<BranchInfo>();
			this.branchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			params.put("cardIssuer", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (messageParam != null){
			params.put("cardIssuer", messageParam.getCardIssuer());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(messageParam.getBranchName()));
			params.put("eventCode", messageParam.getEventCode());
			params.put("operator", MatchMode.ANYWHERE.toMatchString(messageParam.getOperator()));
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		this.page = this.messageParamDAO.findMessageParam(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构不能操作。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", this.getSessionUser().getBranchNo());
		List<MessageParam> messageParamList = this.messageParamDAO.getMessageParamList(params);
		if(messageParamList.size()!=0){
			throw new BizException("发卡机构[" + this.getSessionUser().getBranchNo() + "]不能重复定义短信参数。");
		}
		this.messageParam = new MessageParam();
		this.messageParam.setCardIssuer(this.getSessionUser().getBranchNo());
		return ADD;
	}	
	
	// 短信参数定义
	public String add() throws Exception {		
		
		this.pointAccService.addMessageParam(messageParam, this.getSessionUserCode());
		String msg = "发卡机构["+this.messageParam.getCardIssuer()+"]短信参数定义成功！";

		this.addActionMessage("/pointAccService/messageParam/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构不能操作。");
		}
		
		MessageParamKey key = new MessageParamKey();
		key.setCardIssuer(this.messageParam.getCardIssuer());
		key.setEventCode(this.messageParam.getEventCode());
		this.messageParam = (MessageParam)this.messageParamDAO.findByPk(key);
		
		this.updateEventCode = this.messageParam.getEventCode();
			
		return MODIFY;
	}
	
	// 修改短信参数定义
	public String modify() throws Exception {
		if (StringUtils.isNotEmpty(updateEventCode)) {
			this.messageParam.setNewEventCode(updateEventCode);
		}
		this.pointAccService.modifyMessageParam(messageParam, this.getSessionUserCode());
		String msg = "修改发卡机构["+this.messageParam.getCardIssuer()+"]短信参数定义成功！";
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/pointAccService/messageParam/list.do", msg);	
		
		return SUCCESS;
	}
	
	// 删除积分兑换赠券登记
	public String delete() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构不能操作。");
		}
		MessageParamKey key = new MessageParamKey();
		key.setCardIssuer(this.getCardIssuer());
		key.setEventCode(this.getEventCode());
		this.pointAccService.deleteMessageParam(key);
		String msg = "删除发卡机构[" +this.messageParam.getCardIssuer()+ "]短信参数定义成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pointAccService/messageParam/list.do", msg);
		return SUCCESS;
	}
	
	public MessageParam getMessageParam() {
		return messageParam;
	}

	public void setMessageParam(MessageParam messageParam) {
		this.messageParam = messageParam;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
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

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getUpdateEventCode() {
		return updateEventCode;
	}

	public void setUpdateEventCode(String updateEventCode) {
		this.updateEventCode = updateEventCode;
	}

}
