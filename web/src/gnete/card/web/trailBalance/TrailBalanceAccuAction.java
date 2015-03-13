package gnete.card.web.trailBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TrailBalanceAccuDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.TrailBalanceAccu;
import gnete.card.entity.TrailBalanceAccuKey;
import gnete.card.entity.flag.BalanceFlag;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

public class TrailBalanceAccuAction extends BaseAction{
	
	@Autowired
	private TrailBalanceAccuDAO trailBalanceAccuDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private TrailBalanceAccu trailBalanceAccu;
	private List<BranchInfo> cardBranchList;
	private List<BranchInfo> branchList;
	private List<BalanceFlag> balanceFlagList;
	private Paginater page;
	private boolean showCard = false;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.balanceFlagList = BalanceFlag.getAll();
				
		if (trailBalanceAccu != null) {
			params.put("settDate", this.trailBalanceAccu.getSettDate());
			params.put("cardIssuer", this.trailBalanceAccu.getCardIssuer());
			params.put("balanceFlag", this.trailBalanceAccu.getBalanceFlag());
		}
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showCard = false;
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			showCard = false;
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			showCard = true;
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
					
		this.page = this.trailBalanceAccuDAO.findTrailBalanceAccu(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		
		TrailBalanceAccuKey key = new TrailBalanceAccuKey();
		key.setAccuClass(trailBalanceAccu.getAccuClass());
		key.setSettDate(trailBalanceAccu.getSettDate());
		
		this.trailBalanceAccu = (TrailBalanceAccu)this.trailBalanceAccuDAO.findByPk(key);
		Assert.notNull(trailBalanceAccu, "次卡账户试算平衡对象不存在。");
		
		this.log("查询日期为["+trailBalanceAccu.getSettDate()+"]、发卡机构为["+trailBalanceAccu.getCardIssuer()+
				"]、次卡子类型为["+trailBalanceAccu.getAccuClass()+"]的次卡账户试算平衡明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	public TrailBalanceAccu getTrailBalanceAccu() {
		return trailBalanceAccu;
	}

	public void setTrailBalanceAccu(TrailBalanceAccu trailBalanceAccu) {
		this.trailBalanceAccu = trailBalanceAccu;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<BalanceFlag> getBalanceFlagList() {
		return balanceFlagList;
	}

	public void setBalanceFlagList(List<BalanceFlag> balanceFlagList) {
		this.balanceFlagList = balanceFlagList;
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

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}
}
