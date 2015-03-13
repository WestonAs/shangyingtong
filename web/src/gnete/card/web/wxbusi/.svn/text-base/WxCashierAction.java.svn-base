package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxCashierInfoDAO;
import gnete.card.entity.WxCashierInfo;
import gnete.card.entity.WxCashierInfoKey;
import gnete.card.entity.state.WxCashierState;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
/**
 * <li>微信收银员查询请求处理类</li>
 * @Project: cardWx
 * @File: WxCashierAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxCashierAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1671925752123575438L;

	@Autowired
	private WxCashierInfoDAO wxCashierInfoDAO;
	
	private WxCashierInfo wxCashierInfo;
	private WxCashierInfoKey key;
	
	private String startDate;
	
	private String endDate;
	
	private List<WxCashierState> wxCashierStateList;
	
	private Paginater page;
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	@Override
	public String execute() throws Exception {
		this.wxCashierStateList = WxCashierState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != wxCashierInfo){
			params.put("merchNo", wxCashierInfo.getMerchNo());
			params.put("cashierExtid", wxCashierInfo.getCashierExtid());
			params.put("acctSubject", wxCashierInfo.getAcctSubject());
			params.put("status", wxCashierInfo.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}

		// 判断登录角色的类型// 运营中心和运营中心部门
		if (isCenterOrCenterDeptRoleLogined()) { 
			//
		} 
		// 分支机构
		else if (isFenzhiRoleLogined()) { 
			params.put("fenzhiList", super.getMyManageFenzhi());
		} 
		// 发卡机构或商户
		else if (isCardOrCardDeptRoleLogined() || isMerchantRoleLogined()) { 
			params.put("merchNo", getLoginBranchCode());
		} 
		// 其他机构 均只能查询自己
		else {
			throw new BizException("没有权限访问该菜单！");
		}
		
		this.page = wxCashierInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		key = new WxCashierInfoKey();
		key.setMerchNo(wxCashierInfo.getMerchNo());
		key.setAcctSubject(wxCashierInfo.getAcctSubject());
		key.setCashierExtid(wxCashierInfo.getCashierExtid());
		
		this.wxCashierInfo = (WxCashierInfo)wxCashierInfoDAO.findByPk(key);
		
		Assert.notNull(wxCashierInfo, "收银员对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询微信银行卡充值[" + wxCashierInfo.getMerchNo() + "]明细信息");
		
		return DETAIL;
	}

	public WxCashierInfo getWxCashierInfo() {
		return wxCashierInfo;
	}

	public void setWxCashierInfo(WxCashierInfo wxCashierInfo) {
		this.wxCashierInfo = wxCashierInfo;
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

	public List<WxCashierState> getWxCashierStateList() {
		return wxCashierStateList;
	}

	public void setWxCashierStateList(List<WxCashierState> wxCashierStateList) {
		this.wxCashierStateList = wxCashierStateList;
	}

	public WxCashierInfoKey getKey() {
		return key;
	}

	public void setKey(WxCashierInfoKey key) {
		this.key = key;
	}
}
