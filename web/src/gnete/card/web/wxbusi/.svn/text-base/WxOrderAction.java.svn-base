package gnete.card.web.wxbusi;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxBillInfoDAO;
import gnete.card.entity.WxBillInfo;
import gnete.card.entity.state.WxBillState;
import gnete.card.entity.type.WxBillType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * <li>微信订单信息查询请求处理类</li>
 * @Project: cardWx
 * @File: WxOrderAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxOrderAction extends BaseAction {

	/** */
	private static final long serialVersionUID = 7468995555972472415L;

	@Autowired
	private WxBillInfoDAO wxBillInfoDAO;
	
	private WxBillInfo wxBillInfo;
	
	private String startDate;
	private String endDate;
	
	private List<WxBillState> wxBillStateList;
	private List<WxBillType> billTypeList;
	
	private Paginater page;

	@Override
	public String execute() throws Exception {
		//加载订单状态
		this.wxBillStateList = WxBillState.getAll();
		this.billTypeList = WxBillType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (wxBillInfo != null) {
			params.put("status", wxBillInfo.getStatus());
			params.put("billNo", wxBillInfo.getBillNo());
			params.put("payerCardId", wxBillInfo.getPayerCardId());
			params.put("recvCardId", wxBillInfo.getRecvCardId());
			params.put("billType", wxBillInfo.getBillType());
			
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
		else if (isCardOrCardDeptRoleLogined() || isMerchantRoleLogined()) { 
			params.put("insNo", getLoginBranchCode());
		} 
		// 其他角色没有权限
		else {
			throw new BizException("没有权限访问该菜单！");
		}
		
		this.page = this.wxBillInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception{
		this.wxBillInfo = (WxBillInfo)this.wxBillInfoDAO.findByPk(this.wxBillInfo.getBillNo());
		
//		Map<String, Object> subAcctParams = new HashMap<String, Object>();
		Assert.notNull(wxBillInfo, "卡对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询微信订单[" + this.wxBillInfo.getBillNo() + "]明细信息");
		return DETAIL;
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

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WxBillInfo getWxBillInfo() {
		return wxBillInfo;
	}

	public void setWxBillInfo(WxBillInfo wxBillInfo) {
		this.wxBillInfo = wxBillInfo;
	}

	public List<WxBillState> getWxBillStateList() {
		return wxBillStateList;
	}

	public void setWxBillStateList(List<WxBillState> wxBillStateList) {
		this.wxBillStateList = wxBillStateList;
	}

	public List<WxBillType> getBillTypeList() {
		return billTypeList;
	}

	public void setBillTypeList(List<WxBillType> billTypeList) {
		this.billTypeList = billTypeList;
	}

}
