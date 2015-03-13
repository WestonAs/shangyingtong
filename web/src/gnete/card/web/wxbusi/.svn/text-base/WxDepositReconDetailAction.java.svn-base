package gnete.card.web.wxbusi;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchParaDAO;
import gnete.card.dao.WxReconciliationDetailDAO;
import gnete.card.entity.BranchPara;
import gnete.card.entity.BranchParaKey;
import gnete.card.entity.WxReconciliationDetail;
import gnete.card.entity.state.WxRecocitionProcState;
import gnete.card.entity.type.WxReconErrorType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <li>实名卡扣款充值对账结果明细查询</li>
 * @File: WxDepositReconDetailAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-22 下午03:36:57
 */
public class WxDepositReconDetailAction extends BaseAction {
	
	@Autowired
	private WxReconciliationDetailDAO wxReconciliationDetailDAO;
	@Autowired
	private BranchParaDAO branchParaDAO;
	
	private WxReconciliationDetail wxReconciliationDetail;
	
	private List<WxReconErrorType> typeList;
	private List<WxRecocitionProcState> statusList;
	
	private String startDate;
	private String endDate;
	
	private Paginater page;

	/** */
	private static final long serialVersionUID = -524193388810597847L;
	
	/** 默认返回的列表页面 */
//	private static final String DEFAULT_RETURN_LIST_URL = "/wxbusi/depositReco/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		this.typeList = WxReconErrorType.getAll();
		this.statusList = WxRecocitionProcState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (wxReconciliationDetail != null) {
			params.put("id", wxReconciliationDetail.getId());
			params.put("merchNo", MatchMode.ANYWHERE.toMatchString(wxReconciliationDetail.getMerchNo()));
			params.put("settDate", wxReconciliationDetail.getSettDate());
			params.put("errorType", wxReconciliationDetail.getErrorType());
			params.put("proStatus", wxReconciliationDetail.getProStatus());
			params.put("status", wxReconciliationDetail.getProStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 运营中心能看到所有
		if (isCenterOrCenterDeptRoleLogined()) {
			
		}
		// 分支机构可以看到自己管理的发卡机构的记录
		else if (isFenzhiRoleLogined()) {
//			params.put("fenzhiList", super.getMyManageFenzhi());
		}
		// 发卡机构可以查看自己的记录
		else if (isCardOrCardDeptRoleLogined()) {
			BranchParaKey key = new BranchParaKey();
			
			key.setParaCode("005");
			key.setIsBranch("1");
			key.setRefCode("000000000000000");
			BranchPara branchPara = (BranchPara) this.branchParaDAO.findByPk(key);
			if (!StringUtils.equals(branchPara.getParaValue(), super.getLoginBranchCode())) {
				params.put("merchNo", "error");
			}
		}
		//其他的没有权限查询
		else {
			throw new BizException("没有权限");
		}
		
		this.page = this.wxReconciliationDetailDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	public String detail() throws Exception {
		this.wxReconciliationDetail = (WxReconciliationDetail) this.wxReconciliationDetailDAO.findByPk(wxReconciliationDetail.getId());
		
		return DETAIL;
	}
	

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public WxReconciliationDetail getWxReconciliationDetail() {
		return wxReconciliationDetail;
	}

	public void setWxReconciliationDetail(
			WxReconciliationDetail wxReconciliationDetail) {
		this.wxReconciliationDetail = wxReconciliationDetail;
	}

	public List<WxReconErrorType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<WxReconErrorType> typeList) {
		this.typeList = typeList;
	}

	public List<WxRecocitionProcState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<WxRecocitionProcState> statusList) {
		this.statusList = statusList;
	}
}
