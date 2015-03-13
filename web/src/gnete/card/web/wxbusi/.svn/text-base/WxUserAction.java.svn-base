package gnete.card.web.wxbusi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.DatePair;
import flink.util.Paginater;
import gnete.card.dao.WxUserInfoDAO;
import gnete.card.entity.WxUserInfo;
import gnete.card.entity.type.WxUserType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
/**
 * <li>微信用户信息查询请求处理类</li>
 * @Project: cardWx
 * @File: WxUserAction.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2962038166760601563L;

	@Autowired
	private WxUserInfoDAO wxUserInfoDAO;
	
	private WxUserInfo wxUserInfo;
	
	private String startDate;
	
	private String endDate;
	
	private List<WxUserType> wxUserTypeList;
	
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		
		this.wxUserTypeList = WxUserType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != wxUserInfo){
			params.put("userId", wxUserInfo.getUserId());
			params.put("accessNo", wxUserInfo.getAccessNo());
			params.put("externalCardId", wxUserInfo.getExternalCardId());
			params.put("userType", wxUserInfo.getUserType());
			
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
		
		this.page = wxUserInfoDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		
		this.wxUserInfo = wxUserInfoDAO.findCardIdByPk(wxUserInfo.getUserId());
		
		Assert.notNull(wxUserInfo, "用户信息对象不能为空");
		
		logger.debug("用户[" + this.getSessionUserCode() + "]查询用户信息[" + wxUserInfo.getUserId() + "]明细信息");
		
		return DETAIL;
	}
	
	public WxUserInfo getWxUserInfo() {
		return wxUserInfo;
	}

	public void setWxUserInfo(WxUserInfo wxUserInfo) {
		this.wxUserInfo = wxUserInfo;
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

	public List<WxUserType> getWxUserTypeList() {
		return wxUserTypeList;
	}

	public void setWxUserTypeList(List<WxUserType> wxUserTypeList) {
		this.wxUserTypeList = wxUserTypeList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
