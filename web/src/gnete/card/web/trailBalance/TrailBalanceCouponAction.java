package gnete.card.web.trailBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.TrailBalanceCouponDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.TrailBalanceCoupon;
import gnete.card.entity.flag.BalanceFlag;
import gnete.card.entity.type.RoleType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class TrailBalanceCouponAction extends BaseAction{

	@Autowired
	private TrailBalanceCouponDAO trailBalanceCouponDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private TrailBalanceCoupon trailBalanceCoupon;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	private List<BalanceFlag> balanceFlagList;
	private Paginater page;
	private boolean showMerch = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.balanceFlagList = BalanceFlag.getAll();
				
		if (trailBalanceCoupon != null) {
			params.put("settDate", this.trailBalanceCoupon.getSettDate());
			params.put("issId", this.trailBalanceCoupon.getIssId());
			params.put("balanceFlag", this.trailBalanceCoupon.getBalanceFlag());
		}
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showMerch = false;
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(branchInfoDAO
					.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			showMerch = false;
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
			showMerch = false;
		}
		//商户
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
			showMerch = true;
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = cardBranchList.size()+merchInfoList.size();
			String[] issIds = new String[length];
			int i = 0;
			for( ; i<cardBranchList.size(); i++){
				issIds[i] = (cardBranchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				issIds[i] = (merchInfoList.get(i-cardBranchList.size())).getMerchId();
			}
			params.put("issIds", issIds);
		}
		
		this.page = this.trailBalanceCouponDAO.findTrailBalanceCoupon(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		
		return DETAIL;
	}

	public TrailBalanceCoupon getTrailBalanceCoupon() {
		return trailBalanceCoupon;
	}

	public void setTrailBalanceCoupon(TrailBalanceCoupon trailBalanceCoupon) {
		this.trailBalanceCoupon = trailBalanceCoupon;
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

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

}
