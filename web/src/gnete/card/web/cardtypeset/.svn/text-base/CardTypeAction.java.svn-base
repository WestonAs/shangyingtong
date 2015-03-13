package gnete.card.web.cardtypeset;

import flink.util.Paginater;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class CardTypeAction extends BaseAction {
	
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	
	private CardTypeCode cardTypeCode;
	private Paginater page;
	private String code;
	private String status;
	private Collection stateList;
	
	@Override
	public String execute() throws Exception {
		if(!RoleType.CENTER.getValue().equals(this.getLoginRoleType())&
				!RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非营运中心、营运中心部门不能操作。");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if(cardTypeCode!=null){
			params.put("cardTypeCode",cardTypeCode.getCardTypeCode());
			params.put("status",cardTypeCode.getStatus());
		}
		this.page = cardTypeCodeDAO.find(params, this.getPageNumber(), this.getPageSize());
		stateList = CardTypeState.getList();
		return LIST;
	}
	
	public String showAdd() throws Exception {
		return ADD;
	}
	
	public String add() throws Exception {
		this.cardTypeSetService.addCardTypeCode(cardTypeCode);	
		String msg = "添加卡类型[" + cardTypeCode.getCardTypeCode() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/cardTypeSet/cardType/list.do", msg);
		return SUCCESS;
	}
	

	public String showModify() throws Exception {
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(cardTypeCode.getCardTypeCode());
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if(!RoleType.CENTER.getValue().equals(this.getLoginRoleType())&
				!RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非营运中心、营运中心部门不能操作。");
		}
		cardTypeCode.setCardTypeCode(code);
		cardTypeCode.setStatus(status);
		cardTypeCode.setUpdateBy(this.getSessionUserCode());
		this.cardTypeSetService.modifyCardTypeCode(cardTypeCode);
		String msg = "修改卡类型[" + CardType.valueOf(cardTypeCode.getCardTypeCode()).getName() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/cardType/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.cardTypeSetService.deleteCardTypeCode(cardTypeCode);
		String msg = "删除卡类型[" + cardTypeCode.getCardTypeCode() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/fee/cardTypeCode/list.do", msg);
		return SUCCESS;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Collection getStateList() {
		return stateList;
	}

	public void setStateList(Collection stateList) {
		this.stateList = stateList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	
}
