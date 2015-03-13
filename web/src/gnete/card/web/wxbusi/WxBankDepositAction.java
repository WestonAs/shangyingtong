package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxBankDepositRegDAO;
import gnete.card.entity.WxBankDepositReg;
import gnete.card.entity.state.WxBankDepositState;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
/**
 * <li>微信银行卡充值请求处理类</li>
 * @Project: cardWx
 * @File: WxBankDepositAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxBankDepositAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8781938672086343893L;

	@Autowired
	private WxBankDepositRegDAO wxBankDepositRegDAO;
	
	private WxBankDepositReg wxBankDepositReg;
	
	private String startDate;
	private String endDate;
	
	private List<WxBankDepositState> wxBankDepositStateList;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		this.wxBankDepositStateList = WxBankDepositState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(null != wxBankDepositReg){
			params.put("depositId", wxBankDepositReg.getDepositId());
			params.put("wsSn", wxBankDepositReg.getWsSn());
			params.put("cardId", wxBankDepositReg.getCardId());
			params.put("status", wxBankDepositReg.getStatus());
			
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
		else if (isCardOrCardDeptRoleLogined() || isMerchantRoleLogined()) { 
			params.put("insId", getLoginBranchCode());
		} 
		// 其他机构 均只能查询自己
		else {
			throw new BizException("没有权限访问该菜单！");
		}
		
		this.page = wxBankDepositRegDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		
		this.wxBankDepositReg = (WxBankDepositReg)wxBankDepositRegDAO.findByPk(this.wxBankDepositReg.getDepositId());
		
		Assert.notNull(wxBankDepositReg, "银行卡充值对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询微信银行卡充值[" + wxBankDepositReg.getDepositId() + "]明细信息");
		
		return DETAIL;
	}
	
	public WxBankDepositReg getWxBankDepositReg() {
		return wxBankDepositReg;
	}

	public void setWxBankDepositReg(WxBankDepositReg wxBankDepositReg) {
		this.wxBankDepositReg = wxBankDepositReg;
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

	public List<WxBankDepositState> getWxBankDepositStateList() {
		return wxBankDepositStateList;
	}

	public void setWxBankDepositStateList(
			List<WxBankDepositState> wxBankDepositStateList) {
		this.wxBankDepositStateList = wxBankDepositStateList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
