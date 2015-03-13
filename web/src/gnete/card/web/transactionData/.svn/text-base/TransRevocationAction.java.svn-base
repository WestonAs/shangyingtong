package gnete.card.web.transactionData;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.TransAcctDtlDAO;
import gnete.card.dao.TransPointDtlDAO;
import gnete.card.dao.TransRevocationDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.TransRevocation;
import gnete.card.entity.state.RevcState;
import gnete.card.entity.state.RevcType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 撤销/退货/冲正查询
 * @author aps-lib
 *
 */
public class TransRevocationAction extends BaseAction{

	@Autowired
	private TransRevocationDAO transRevocationDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransAcctDtlDAO transAcctDtlDAO;
	@Autowired
	private TransPointDtlDAO transPointDtlDAO;
	
	private TransRevocation transRevocation;
	private Collection<RevcState> statusList;
	private Collection<RevcType> transTypeList;
	private String settStartDate;
	private String settEndDate;
	private List<BranchInfo> cardBranchList;
	private Paginater page;
	private Paginater acctPage;
	private Paginater pointPage;
	
	@Override
	public String execute() throws Exception {
		this.statusList = RevcState.ALL.values();		
		this.transTypeList = RevcType.ALL.values();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(settStartDate)){
			params.put("settStartDate", settStartDate);
		}else{
			settStartDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		}
		if(StringUtils.isNotBlank(settEndDate)){
			params.put("settEndDate", settEndDate);
		}else{
			settEndDate = SysparamCache.getInstance().getWorkDateNotFromCache();
		}
		
		params.put("settStartDate", settStartDate);
		params.put("settEndDate", settEndDate);
		
		if(this.transRevocation!=null){
			params.put("sysTraceNum", this.transRevocation.getSysTraceNum());
			params.put("revcType", this.transRevocation.getRevcType());
			params.put("revcStatus", this.transRevocation.getRevcStatus());
			params.put("cardId", this.transRevocation.getCardId()); //卡号
			params.put("cardIssuer", this.transRevocation.getCardIssuer()); //发卡机构
			params.put("initrNo", this.transRevocation.getInitrNo()); //发起方
		}
		if (isCardOrCardDeptRoleLogined()){
			params.put("cardIssuers", this.branchInfoDAO.findTreeBranchList(getLoginBranchCode()));
		} else if (isMerchantRoleLogined()){
			params.put("merNo", this.getSessionUser().getMerchantNo());
		} else if (isFenzhiRoleLogined()){
			cardBranchList = new ArrayList<BranchInfo>();
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isNotEmpty(cardBranchList)) {
				params.put("cardIssuers", cardBranchList);
			}
		} else if (isCenterOrCenterDeptRoleLogined()){
		
		} else {
			throw new BizException("没有权限查询！");
		}
		
		this.page = this.transRevocationDAO.findTransRevocation(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得交易明细
	public String detail() throws Exception {
		
		this.transRevocation = (TransRevocation) this.transRevocationDAO.findByPk(this.transRevocation.getRevcSn());
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Assert.notNull(this.transRevocation, "交易信息不存在。");
		
		//取得交易账户变动明细
		params.clear();
		params.put("transSn", this.transRevocation.getRevcSn());
		this.acctPage = this.transAcctDtlDAO.findTransAcctDtl(params, this.getPageNumber(), this.getPageSize());
		
		//取得交易积分变动明细
		params.clear();
		params.put("transSn", this.transRevocation.getRevcSn());
		this.pointPage = this.transPointDtlDAO.findTransPointDtl(params, this.getPageNumber(), this.getPageSize());
		
//		this.log("查询交易["+this.transRevocation.getRevcSn()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}

	public TransRevocation getTransRevocation() {
		return transRevocation;
	}

	public void setTransRevocation(TransRevocation transRevocation) {
		this.transRevocation = transRevocation;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public String getSettStartDate() {
		return settStartDate;
	}

	public void setSettStartDate(String settStartDate) {
		this.settStartDate = settStartDate;
	}

	public String getSettEndDate() {
		return settEndDate;
	}

	public void setSettEndDate(String settEndDate) {
		this.settEndDate = settEndDate;
	}

	public void setStatusList(Collection<RevcState> statusList) {
		this.statusList = statusList;
	}

	public void setTransTypeList(Collection<RevcType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public Paginater getAcctPage() {
		return acctPage;
	}

	public void setAcctPage(Paginater acctPage) {
		this.acctPage = acctPage;
	}

	public Paginater getPointPage() {
		return pointPage;
	}

	public void setPointPage(Paginater pointPage) {
		this.pointPage = pointPage;
	}

}
