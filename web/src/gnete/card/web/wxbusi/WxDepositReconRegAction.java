package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.WxDepositReconReg;
import gnete.card.entity.state.WxRecocitionState;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.WxReconOpeType;
import gnete.card.service.WxBusinessService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * <li>实名卡扣款充值对账处理</li>
 * @Project: cardWx
 * @File: WxDepositReconRegAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午04:52:59
 */
public class WxDepositReconRegAction extends BaseAction {
	
	@Autowired
	private WxBusinessService wxBusinessService;
	
	private WxDepositReconReg wxDepositReconReg;
	
	private List<WxReconOpeType> opeTypeList;
	private List<WxRecocitionState> statusList;
	
	private String startDate;
	private String endDate;
	
	private Paginater page;

	/** */
	private static final long serialVersionUID = -524193388810597847L;
	
	/** 默认返回的列表页面 */
	private static final String DEFAULT_RETURN_LIST_URL = "/wxbusi/depositReco/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		this.opeTypeList = WxReconOpeType.getAll();
		this.statusList = WxRecocitionState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (wxDepositReconReg != null) {
			params.put("seqId", wxDepositReconReg.getSeqId());
			params.put("reconDetailId", wxDepositReconReg.getReconDetailId());
			params.put("opeType", wxDepositReconReg.getOpeType());
			params.put("searchCardId", MatchMode.ANYWHERE.toMatchString(wxDepositReconReg.getCardId()));
			params.put("searchExtCardId", MatchMode.ANYWHERE.toMatchString(wxDepositReconReg.getExtCardId()));
			params.put("status", wxDepositReconReg.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 运营中心能看到所有
		if (isCenterOrCenterDeptRoleLogined()) {
			
		}
		// 分支机构可以看到自己管理的发卡机构的记录
		else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", super.getMyManageFenzhi());
		}
		// 发卡机构可以查看自己的记录
		else if (isCardOrCardDeptRoleLogined()) {
			params.put("issNo", super.getLoginBranchCode());
		}
		//其他的没有权限查询
		else {
			throw new BizException("没有权限");
		}
		
		this.page = this.wxBusinessService.findWxDepositReconRegPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	public String detail() throws Exception {
		this.wxDepositReconReg = this.wxBusinessService.findWxDepositReconReg(wxDepositReconReg.getSeqId());
		
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		this.opeTypeList = WxReconOpeType.getAll();
		Assert.isTrue(super.isCardRoleLogined(), "只有发卡机构能够进行实名卡扣款充值的调账补帐操作");
		return ADD;
	}
	
	public String add() throws Exception {
		this.wxBusinessService.addWxDepositReconReg(wxDepositReconReg, super.getSessionUser());
		
		String msg = LogUtils.r("添加对账结果明细ID为[{0}]的调账或补帐记录成功！", wxDepositReconReg.getReconDetailId());
		this.addActionMessage(DEFAULT_RETURN_LIST_URL, msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WxDepositReconReg getWxDepositReconReg() {
		return wxDepositReconReg;
	}

	public void setWxDepositReconReg(WxDepositReconReg wxDepositReconReg) {
		this.wxDepositReconReg = wxDepositReconReg;
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

	public List<WxReconOpeType> getOpeTypeList() {
		return opeTypeList;
	}

	public void setOpeTypeList(List<WxReconOpeType> opeTypeList) {
		this.opeTypeList = opeTypeList;
	}

	public List<WxRecocitionState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<WxRecocitionState> statusList) {
		this.statusList = statusList;
	}
}
