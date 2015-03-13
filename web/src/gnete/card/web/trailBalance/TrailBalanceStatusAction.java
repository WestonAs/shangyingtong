package gnete.card.web.trailBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TrailBalanceAccuDAO;
import gnete.card.dao.TrailBalanceCouponDAO;
import gnete.card.dao.TrailBalancePointDAO;
import gnete.card.dao.TrailBalanceStatusDAO;
import gnete.card.dao.TrailBalanceSubacctDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.TrailBalanceStatus;
import gnete.card.entity.TrailBalanceStatusKey;
import gnete.card.entity.flag.BalanceFlag;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 试算平衡结果查询
 * @author aps-lib
 *
 */
public class TrailBalanceStatusAction extends BaseAction{

	@Autowired
	private TrailBalanceStatusDAO trailBalanceStatusDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TrailBalanceCouponDAO trailBalanceCouponDAO;
	@Autowired
	private TrailBalanceAccuDAO trailBalanceAccuDAO;
	@Autowired
	private TrailBalancePointDAO trailBalancePointDAO;
	@Autowired
	private TrailBalanceSubacctDAO trailBalanceSubacctDAO;
	
	private TrailBalanceStatus trailBalanceStatus;
	private List<BalanceFlag> balanceFlagList;
	private List<BranchInfo> branchList;
	private List<BranchInfo> cardIssuerList;
	private Paginater page;
	private Paginater subAcctPage;
	private Paginater pointPage;
	private Paginater couponPage;
	private Paginater accuPage;
	private boolean showCard = false;
	private boolean showSubAcct = false;
	private boolean showAccu = false;
	private boolean showPoint = false;
	private boolean showCoupon = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.balanceFlagList = BalanceFlag.getAll();
				
		if (this.trailBalanceStatus != null) {
			params.put("settDate", this.trailBalanceStatus.getSettDate());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(this.trailBalanceStatus.getBranchName()));
			params.put("balanceFlag", this.trailBalanceStatus.getBalanceFlag());
		}
		// 当前登录用户所属或所管理的发卡机构列表
		cardIssuerList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showCard = false;
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardIssuerList.addAll(branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			showCard = false;
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardIssuerList.add((BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			branchList = new ArrayList<BranchInfo>();
			branchList.add((BranchInfo) branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			showCard = true;
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardIssuerList)) {
			params.put("cardIssuers", cardIssuerList);
		}
					
		this.page = this.trailBalanceStatusDAO.findTrailBalanceStatus(params, this.getPageNumber(), this.getPageSize());	
		return LIST;
	}
	
	// 取得明细
	public String detail() throws Exception {
		
		TrailBalanceStatusKey key = new TrailBalanceStatusKey();
		key.setCardIssuer(this.trailBalanceStatus.getCardIssuer());
		key.setSettDate(this.trailBalanceStatus.getSettDate());
		this.trailBalanceStatus = (TrailBalanceStatus) this.trailBalanceStatusDAO.findByPk(key);
		Assert.notNull(this.trailBalanceStatus, "试算结果不存在。");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", this.trailBalanceStatus.getCardIssuer());
		params.put("settDate", this.trailBalanceStatus.getSettDate());
		
		//取得资金账户试算平衡明细
		this.subAcctPage = this.trailBalanceSubacctDAO.findTrailBalanceSubacct(params, this.getPageNumber(), this.getPageSize());
		
		//取得次卡账户试算平衡明细
		this.accuPage = this.trailBalanceAccuDAO.findTrailBalanceAccu(params, this.getPageNumber(), this.getPageSize());
		
		//取得赠券账户试算平衡明细
		this.couponPage = this.trailBalanceCouponDAO.findTrailBalanceCoupon(params, this.getPageNumber(), this.getPageSize());
		
		//取得积分账户试算平衡明细
		this.pointPage = this.trailBalancePointDAO.findTrailBalancePoint(params, this.getPageNumber(), this.getPageSize());
		
		this.log("查询清算日期为["+this.trailBalanceStatus.getSettDate()+"]，发卡机构为["+this.trailBalanceStatus.getCardIssuer()+ "]的试算平衡明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}

	public TrailBalanceStatus getTrailBalanceStatus() {
		return trailBalanceStatus;
	}

	public void setTrailBalanceStatus(TrailBalanceStatus trailBalanceStatus) {
		this.trailBalanceStatus = trailBalanceStatus;
	}

	public List<BalanceFlag> getBalanceFlagList() {
		return balanceFlagList;
	}

	public void setBalanceFlagList(List<BalanceFlag> balanceFlagList) {
		this.balanceFlagList = balanceFlagList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public List<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(List<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}

	public Paginater getSubAcctPage() {
		return subAcctPage;
	}

	public void setSubAcctPage(Paginater subAcctPage) {
		this.subAcctPage = subAcctPage;
	}

	public Paginater getPointPage() {
		return pointPage;
	}

	public void setPointPage(Paginater pointPage) {
		this.pointPage = pointPage;
	}

	public Paginater getCouponPage() {
		return couponPage;
	}

	public void setCouponPage(Paginater couponPage) {
		this.couponPage = couponPage;
	}

	public Paginater getAccuPage() {
		return accuPage;
	}

	public void setAccuPage(Paginater accuPage) {
		this.accuPage = accuPage;
	}

	public boolean isShowSubAcct() {
		return showSubAcct;
	}

	public void setShowSubAcct(boolean showSubAcct) {
		this.showSubAcct = showSubAcct;
	}

	public boolean isShowAccu() {
		return showAccu;
	}

	public void setShowAccu(boolean showAccu) {
		this.showAccu = showAccu;
	}

	public boolean isShowPoint() {
		return showPoint;
	}

	public void setShowPoint(boolean showPoint) {
		this.showPoint = showPoint;
	}

	public boolean isShowCoupon() {
		return showCoupon;
	}

	public void setShowCoupon(boolean showCoupon) {
		this.showCoupon = showCoupon;
	}

}
