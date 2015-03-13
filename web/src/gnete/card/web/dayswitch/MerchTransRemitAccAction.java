package gnete.card.web.dayswitch;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchTransDSetDAO;
import gnete.card.dao.MerchTransRmaDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchTransDSet;
import gnete.card.entity.MerchTransRmaDetail;
import gnete.card.entity.MerchTransRmaDetailKey;
import gnete.card.entity.state.VerifyCheckState;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.XferType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchTransRemitAccAction extends BaseAction{

	@Autowired
	private MerchTransRmaDetailDAO merchTransRmaDetailDAO;
	@Autowired
	private MerchTransDSetDAO merchTransDSetDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	private List chkStatusList;
	private List xferTypeList;
	private MerchTransRmaDetail merchTransRmaDetail;
	private MerchTransDSet merchTransDSet;
	private Paginater page;
	private Paginater merchTransDSetPage;
	private boolean showCard = false;
	private boolean showMerch = false;
	private String startDate;
	private String endDate;
	
	@Override
	public String execute() throws Exception {
		chkStatusList = VerifyCheckState.getList();
		this.xferTypeList = XferType.getAll();
		branchList = new ArrayList<BranchInfo>();
		merchList = new ArrayList<MerchInfo>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (merchTransRmaDetail != null) {
			params.put("payName", MatchMode.ANYWHERE.toMatchString(merchTransRmaDetail.getPayName()));
			params.put("recvName", MatchMode.ANYWHERE.toMatchString(merchTransRmaDetail.getRecvName()));
			params.put("rmaDate", merchTransRmaDetail.getRmaDate());
			params.put("xferType", merchTransRmaDetail.getXferType());
			params.put("startDate", this.getStartDate());
			params.put("endDate",this.getEndDate());
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {// 登录用户为运营中心、营运中心部门
			this.showCard = false;
			this.showMerch = false;
		} else if (isFenzhiRoleLogined()) { // 营运分支机构
			this.showCard = false;
			this.showMerch = false;
			this.branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isNotEmpty(this.branchList)) {
				params.put("cardIssuers", this.branchList);
			}
		} else if (isCardRoleLogined()) {// 发卡机构
			this.showCard = true;
			this.showMerch = false;
			params.put("payCode", this.getSessionUser().getBranchNo());
			params.put("recvCode", this.getSessionUser().getBranchNo());
			this.branchList
					.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
		} else if (isMerchantRoleLogined()) {// 商户
			this.showMerch = true;
			this.showCard = false;
			params.put("recvCode", this.getSessionUser().getMerchantNo());
			this.merchList.add((MerchInfo) this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		} else {
			throw new BizException("没有权限查询！");
		}
		this.page = this.merchTransRmaDetailDAO.findMerchTransRmaDetail(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 取得明细
	public String detail() throws Exception {
		MerchTransRmaDetailKey key = new MerchTransRmaDetailKey();
		key.setCurCode(this.merchTransRmaDetail.getCurCode());
		key.setPayCode(this.merchTransRmaDetail.getPayCode());
		key.setRecvCode(this.merchTransRmaDetail.getRecvCode());
		key.setRmaDate(this.merchTransRmaDetail.getRmaDate());
		this.merchTransRmaDetail = (MerchTransRmaDetail) this.merchTransRmaDetailDAO.findByPk(key);
		
		Assert.notNull(this.merchTransRmaDetail, "发卡机构商户划付对象不存在。");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//取得发卡发卡机构商户交易结算日结算明细
		params.clear();
		params.put("rmaSn", this.merchTransRmaDetail.getRmaSn());
		this.merchTransDSetPage = this.merchTransDSetDAO.findMerchTransDSet(params, this.getPageNumber(), this.getPageSize());
		
		this.log("查询划付日期为["+this.merchTransRmaDetail.getRmaDate()+"]、付款方为[" + this.merchTransRmaDetail.getPayCode()+ "]、收款方为["+
				this.merchTransRmaDetail.getRecvCode()+"明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List getChkStatusList() {
		return chkStatusList;
	}

	public void setChkStatusList(List chkStatusList) {
		this.chkStatusList = chkStatusList;
	}

	public MerchTransDSet getMerchTransDSet() {
		return merchTransDSet;
	}

	public void setMerchTransDSet(MerchTransDSet merchTransDSet) {
		this.merchTransDSet = merchTransDSet;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getXferTypeList() {
		return xferTypeList;
	}

	public void setXferTypeList(List xferTypeList) {
		this.xferTypeList = xferTypeList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public Paginater getMerchTransDSetPage() {
		return merchTransDSetPage;
	}

	public void setMerchTransDSetPage(Paginater merchTransDSetPage) {
		this.merchTransDSetPage = merchTransDSetPage;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public MerchTransRmaDetail getMerchTransRmaDetail() {
		return merchTransRmaDetail;
	}

	public void setMerchTransRmaDetail(MerchTransRmaDetail merchTransRmaDetail) {
		this.merchTransRmaDetail = merchTransRmaDetail;
	}


}
