package gnete.card.web.transactionData;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.RetransCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RetransCardReg;
import gnete.card.entity.type.PlatformType;
import gnete.card.entity.type.UserLogType;
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
 * 补账登记查询
 * @author aps-lib
 *
 */
public class RetransQueryAction extends BaseAction{
	
	@Autowired
	private RetransCardRegDAO retransCardRegDao;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private RetransCardReg retransCardReg;
	private Paginater page;
	private Collection platformList;
	List<BranchInfo> cardBranchList;
	
	//封装交易日期
	private String startDate;
	private String endDate;
	
	@Override
	public String execute() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		
		//加载发起平台类型列表
		this.platformList = PlatformType.ALL.values();
		
		if (retransCardReg != null) {
			params.put("platform", retransCardReg.getPlatform());
			params.put("merchId", retransCardReg.getMerchId());
			params.put("termId",retransCardReg.getTermId());
			params.put("acctId", retransCardReg.getAcctId());
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(retransCardReg.getCardId())); //卡号
			params.put("cardBranch", retransCardReg.getCardBranch());//发卡机构
			params.put("initiator", retransCardReg.getInitiator());//发起方
			//交易日期
			DatePair datePair = new DatePair(this.startDate, this.endDate); 
			datePair.setTruncatedTimeDate(params);
		}
		
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 分支机构
			cardBranchList.addAll(branchInfoDAO.findCardByManange(getLoginBranchCode()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构 或 发卡机构部门
			cardBranchList = branchInfoDAO.findTreeBranchList(getLoginBranchCode());
			
		} else if (isMerchantRoleLogined()) {
			params.put("merchId", retransCardReg.getMerchId());
			
		} else {
			throw new BizException("没有权限查询。");
		}

		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		this.page = this.retransCardRegDao.findRetransCardReg(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得追款交易明细
	public String detail() throws Exception {
		
		this.retransCardReg = (RetransCardReg) this.retransCardRegDao.findByPk(this.retransCardReg.getRetransCardId());
		
		this.log("查询追款交易["+this.retransCardReg.getRetransCardId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}

	public void setRetransCardRegDao(RetransCardRegDAO retransCardRegDao) {
		this.retransCardRegDao = retransCardRegDao;
	}

	public RetransCardRegDAO getRetransCardRegDao() {
		return retransCardRegDao;
	}
	
	public RetransCardReg getRetransCardReg() {
		return retransCardReg;
	}

	public void setRetransCardReg(RetransCardReg retransCardReg) {
		this.retransCardReg = retransCardReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getPlatformList() {
		return platformList;
	}

	public void setPlatformList(Collection platformList) {
		this.platformList = platformList;
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
