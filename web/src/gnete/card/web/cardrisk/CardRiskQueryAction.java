package gnete.card.web.cardrisk;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.CardRiskBalanceDAO;
import gnete.card.dao.CardRiskChgDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardRiskBalance;
import gnete.card.entity.CardRiskChg;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardRiskQueryAction.java
 *
 * @description: 机构风险准备金
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-28 下午03:24:32
 */
public class CardRiskQueryAction extends BaseAction{

	@Autowired
	private CardRiskBalanceDAO cardRiskBalanceDAO;
	@Autowired
	private CardRiskChgDAO cardRiskChgDAO;
	
	private CardRiskBalance cardRiskBalance;
	private CardRiskChg cardRiskChg;
	private Paginater page;
	
	private List<BranchInfo> branchList;
	
	private boolean showAll;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		if (cardRiskBalance != null) {
			params.put("branchCode", cardRiskBalance.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardRiskBalance.getBranchName()));
		}
		// 如果是运营中心则允许查询所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())) {
			showAll = true;
		}
		// 分支机构则允许查询其管理的机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			showAll = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 发卡机构则允许查自己
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			showAll = true;
			params.put("branchCode", this.getLoginBranchCode());
		}
		// 其他不允许进入
		else {
			throw new BizException("该机构禁止访问该菜单！");
		}
		
		this.page = this.cardRiskBalanceDAO.findCardRiskBalance(params, this.getPageNumber(), this.getPageSize());	
		return LIST;
	}
	
	//取得机构风险准备金余额的明细
	public String detail() throws Exception {
		
		/*this.earnestMoney = (EarnestMoney) this.earnestMoneyDAO.findByPk(this.earnestMoney.getBranchCode());
		
		this.log("查询机构["+this.earnestMoney.getBranchCode()+"]风险准备金明细信息成功", UserLogType.SEARCH);
		return DETAIL;*/
		return DETAIL;
	}
	
	//取得机构风险准备金变动明细
	public String chgDetail() throws Exception {
		
		this.cardRiskChg = (CardRiskChg) this.cardRiskChgDAO.findByPk(this.cardRiskChg.getId());
		this.log("查询机构准备金变动["+this.cardRiskChg.getId()+"]信息成功", UserLogType.SEARCH);
		return "chgDetail";
	}
	
	public String cardRiskChgList() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("branchCode", cardRiskBalance.getBranchCode());
		
		this.page = this.cardRiskChgDAO.findCardRiskChg(params, this.getPageNumber(), this.getPageSize());	
		return "cardRiskChgList";
	}

	public CardRiskBalance getCardRiskBalance() {
		return cardRiskBalance;
	}

	public void setCardRiskBalance(CardRiskBalance cardRiskBalance) {
		this.cardRiskBalance = cardRiskBalance;
	}

	public CardRiskChg getCardRiskChg() {
		return cardRiskChg;
	}

	public void setCardRiskChg(CardRiskChg cardRiskChg) {
		this.cardRiskChg = cardRiskChg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

}
