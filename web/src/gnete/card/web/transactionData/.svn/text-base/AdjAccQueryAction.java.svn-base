package gnete.card.web.transactionData;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.AdjAccRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.AdjAccReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.type.PlatformType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调账登记查询
 * @author aps-lib
 *
 */
public class AdjAccQueryAction extends BaseAction{

	@Autowired
	private AdjAccRegDAO adjAccRegDao;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private AdjAccReg adjAccReg;
	private Paginater page;
	private Collection platformList;
	List<BranchInfo> cardBranchList;
	
	private String startDate;
	private String endDate;
	 
	@Override
	public String execute() throws Exception {
		//加载发起平台类型列表
		this.setPlatformList(PlatformType.ALL.values());
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (adjAccReg != null) {
			params.put("platform", adjAccReg.getPlatform());
			params.put("merchId", adjAccReg.getMerNo());
			params.put("termlId",adjAccReg.getTermlId());
			params.put("acctId", adjAccReg.getAcctId());
			params.put("cardId", adjAccReg.getCardId()); //卡号
			params.put("cardBranch", adjAccReg.getCardBranch()); //发卡机构
			params.put("initiator", adjAccReg.getInitiator()); //发起方
			//交易日期     
			DatePair datePair = new DatePair(this.startDate, this.endDate); 
			datePair.setTruncatedTimeDate(params);
		}
		
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 分支机构
			cardBranchList.addAll(branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或、卡机构部门
			cardBranchList = branchInfoDAO.findTreeBranchList(getLoginBranchCode());
		} else if (isMerchantRoleLogined()) {
			params.put("merNo", adjAccReg.getMerNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		this.page = this.adjAccRegDao.findAdjAccReg(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	

	//取得调账交易明细
	public String detail() throws Exception {
		this.adjAccReg = (AdjAccReg) this.adjAccRegDao.findByPk(this.adjAccReg.getAdjAccId());
//		this.log("查询调账交易["+this.adjAccReg.getAdjAccId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}

	public AdjAccRegDAO getAdjAccRegDao() {
		return adjAccRegDao;
	}

	public void setAdjAccRegDao(AdjAccRegDAO adjAccRegDao) {
		this.adjAccRegDao = adjAccRegDao;
	}

	public AdjAccReg getAdjAccReg() {
		return adjAccReg;
	}

	public void setAdjAccReg(AdjAccReg adjAccReg) {
		this.adjAccReg = adjAccReg;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPlatformList(Collection platformList) {
		this.platformList = platformList;
	}

	public Collection getPlatformList() {
		return platformList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
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

}
