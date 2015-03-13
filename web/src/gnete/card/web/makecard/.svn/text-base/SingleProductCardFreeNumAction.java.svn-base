package gnete.card.web.makecard;

import flink.util.Paginater;
import gnete.card.dao.CardissuerFreeNumDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardissuerFreeNum;
import gnete.card.entity.CardissuerFreeNumKey;
import gnete.card.entity.state.CardFreeNumState;
import gnete.card.entity.type.RoleType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductCardFreeNumAction.java
 *
 * @description: 单机产品发卡机构赠送卡数量查询
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-10-24 上午11:02:40
 */
public class SingleProductCardFreeNumAction extends BaseAction {
	
	@Autowired
	private CardissuerFreeNumDAO cardissuerFreeNumDAO;
	
	private CardissuerFreeNum cardissuerFreeNum;
	
	/** 状态列表 */
	private List<CardFreeNumState> statusList;
	/** 发卡机构列表 */
	private List<BranchInfo> cardBranchList;
	private boolean showBranchList = false;
	
	/** 发卡机构名 */
	private String cardBranchName;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		this.statusList = CardFreeNumState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardissuerFreeNum != null) {
			params.put("termId", cardissuerFreeNum.getTermId());
			params.put("issId", cardissuerFreeNum.getIssId());
			params.put("status", cardissuerFreeNum.getStatus());
		}
		// 运营中心查看所有
		if (RoleType.CENTER.getValue().equals(super.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(super.getLoginRoleType())) {
		}
		// 分支机构查看自己及自己的下级所管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(super.getLoginRoleType())) {
			params.put("fenzhiList", super.getMyManageFenzhi());
		} 
		// 发卡机构可以查询自己的赠送卡数量
		else if (RoleType.CARD.getValue().equals(super.getLoginRoleType())) {
			showBranchList = true;
			this.cardBranchList = super.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		}
		else {
			throw new BizException("没有权限查询单机产品赠送卡数量列表");
		}
		this.page = this.cardissuerFreeNumDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail()throws Exception{
		CardissuerFreeNumKey key = new CardissuerFreeNumKey();
		
		key.setTermId(cardissuerFreeNum.getTermId());
		key.setPeriod(cardissuerFreeNum.getPeriod());
		
		this.cardissuerFreeNum = (CardissuerFreeNum) this.cardissuerFreeNumDAO.findByPk(key);
		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CardFreeNumState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardFreeNumState> statusList) {
		this.statusList = statusList;
	}

	public CardissuerFreeNum getCardissuerFreeNum() {
		return cardissuerFreeNum;
	}

	public void setCardissuerFreeNum(CardissuerFreeNum cardissuerFreeNum) {
		this.cardissuerFreeNum = cardissuerFreeNum;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isShowBranchList() {
		return showBranchList;
	}

	public void setShowBranchList(boolean showBranchList) {
		this.showBranchList = showBranchList;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}
}
