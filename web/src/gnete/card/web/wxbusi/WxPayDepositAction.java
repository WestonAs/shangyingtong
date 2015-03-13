package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxPayDepositInfoDAO;
import gnete.card.entity.WxPayDepositInfo;
import gnete.card.entity.state.WxPayDepositState;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
/**
 * <li>微信便民充值请求处理类</li>
 * @Project: cardWx
 * @File: WxPayDepositAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxPayDepositAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4886385092647302215L;

	@Autowired
	private WxPayDepositInfoDAO wxPayDepositInfoDAO;
	
	private WxPayDepositInfo wxPayDepositInfo;
	
	private String startDate;
	
	private String endDate;
	
	private List<WxPayDepositState> wxPayDepositStateList;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		
		this.wxPayDepositStateList = WxPayDepositState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != wxPayDepositInfo){
			params.put("chargeBillNo", wxPayDepositInfo.getChargeBillNo());
			params.put("cardId", wxPayDepositInfo.getCardId());
			params.put("payBankAcct", wxPayDepositInfo.getPayBankAcct());
			params.put("status", wxPayDepositInfo.getStatus());
			
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
		// 发卡机构
		else if (isCardOrCardDeptRoleLogined()) { 
			params.put("cardIssuer", getLoginBranchCode());
		} 
		// 其他角色没有权限
		else {
			throw new BizException("没有权限访问该菜单！");
		}
		
		this.page = this.wxPayDepositInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public WxPayDepositInfo getWxPayDepositInfo() {
		return wxPayDepositInfo;
	}

	public void setWxPayDepositInfo(WxPayDepositInfo wxPayDepositInfo) {
		this.wxPayDepositInfo = wxPayDepositInfo;
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

	public List<WxPayDepositState> getWxPayDepositStateList() {
		return wxPayDepositStateList;
	}

	public void setWxPayDepositStateList(
			List<WxPayDepositState> wxPayDepositStateList) {
		this.wxPayDepositStateList = wxPayDepositStateList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String detail() throws Exception {
		this.wxPayDepositInfo = (WxPayDepositInfo)this.wxPayDepositInfoDAO.findByPk(this.wxPayDepositInfo.getChargeBillNo());
		
		Assert.notNull(wxPayDepositInfo, "便民充值对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询微信便民充值缴费[" + this.wxPayDepositInfo.getChargeBillNo() + "]明细信息");
		
		return DETAIL;
	}
}
