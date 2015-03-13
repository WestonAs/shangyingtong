package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxWithdrawInfoDAO;
import gnete.card.entity.WxWithdrawInfo;
import gnete.card.entity.state.WxWithDrawState;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
/**
 * <li>微信提现查询请求处理类</li>
 * @Project: cardWx
 * @File: WxBankDepositAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxWithDrawAction extends BaseAction {

	/** */
	private static final long serialVersionUID = -97775336435208892L;

	@Autowired
	private WxWithdrawInfoDAO wxWithdrawInfoDAO;
	
	private WxWithdrawInfo wxWithdrawInfo;
	
	private String startDate;
	
	private String endDate;
	
	private List<WxWithDrawState> wxWithDrawStateList;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		
		this.wxWithDrawStateList = WxWithDrawState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != wxWithdrawInfo){
			params.put("withdrawBillNo", wxWithdrawInfo.getWithdrawBillNo());
			params.put("cardId", wxWithdrawInfo.getCardId());
			params.put("extCardId", wxWithdrawInfo.getExtCardId());
			params.put("status", wxWithdrawInfo.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 判断登录角色的类型// 运营中心和运营中心部门
		if (isCenterOrCenterDeptRoleLogined()) { 
	
		} 
		// 分支机构
		else if (isFenzhiRoleLogined()) { 
			params.put("fenzhiList", super.getMyManageFenzhi());
		} 
		// 发卡机构或商户
		else if (isCardOrCardDeptRoleLogined()) { 
			params.put("insId", getLoginBranchCode());
		} 
		// 其他机构 均只能查询自己
		else {
			throw new BizException("没有权限访问该菜单！");
		}
		
		this.page = wxWithdrawInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		
		this.wxWithdrawInfo = (WxWithdrawInfo)wxWithdrawInfoDAO.findByPk(this.wxWithdrawInfo.getWithdrawBillNo());
		
		Assert.notNull(wxWithdrawInfo, "提现对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询微信提现[" + this.wxWithdrawInfo.getWithdrawBillNo() + "]明细信息");
		
		return DETAIL;
	}

	public WxWithdrawInfo getWxWithdrawInfo() {
		return wxWithdrawInfo;
	}

	public void setWxWithdrawInfo(WxWithdrawInfo wxWithdrawInfo) {
		this.wxWithdrawInfo = wxWithdrawInfo;
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

	public List<WxWithDrawState> getWxWithDrawStateList() {
		return wxWithDrawStateList;
	}

	public void setWxWithDrawStateList(List<WxWithDrawState> wxWithDrawStateList) {
		this.wxWithDrawStateList = wxWithDrawStateList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
