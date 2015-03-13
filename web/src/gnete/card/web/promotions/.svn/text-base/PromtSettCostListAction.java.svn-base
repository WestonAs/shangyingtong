package gnete.card.web.promotions;

import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.PromtSettCostListDAO;
import gnete.card.entity.PromtSettCostList;
import gnete.card.entity.type.RoleType;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class PromtSettCostListAction extends BaseAction {

	@Autowired
	private PromtSettCostListDAO promtSettCostListDAO;
	
	private PromtSettCostList promtSettCostList;
	
	private String transSn;
	private String promtId;
	private String settStartDate;
	private String settEndDate;
	
	private Paginater page;

	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			params.put("cardIssuer", getSessionUser().getBranchNo());
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			params.put("cardIssuer", getSessionUser().getMerchantNo());
		} else{
			throw new BizException("非商户或发卡机构禁止查看促销活动交易明细交易信息。");
		}
		
		if(StringUtils.isNotBlank(settStartDate)){ // 默认查询前一工作日
			params.put("settStartDate", settStartDate);
		} else {
			settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}

		if (StringUtils.isNotBlank(settEndDate)) {
			params.put("settEndDate", settEndDate);
		} else {
			settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		params.put("settStartDate", settStartDate);
		params.put("settEndDate", settEndDate);
		
		if(StringUtils.isNotBlank(settStartDate) && StringUtils.isNotBlank(settEndDate)){ //如果清算日期不为空，检查跨度是否超过31天
			if (DateUtil.chkDateFormat(settStartDate) && DateUtil.chkDateFormat(settEndDate)) { // 检查清算日期格式
				Date curDate = DateUtil.formatDate(settStartDate, "yyyyMMdd");
				Date oriDate = DateUtil.formatDate(settEndDate, "yyyyMMdd");
				int diffDays = DateUtil.getDateDiffDays(curDate, oriDate);
				if (diffDays > 31) {
					throw new BizException("清算日期的查询间隔不得超过31天");
				}
			}
		}
		
		if (promtSettCostList != null) {
			params.put("transSn",promtSettCostList.getTransSn());
			params.put("promtId",promtSettCostList.getPromtId());
			params.put("promtDonateId",promtSettCostList.getPromtDonateId());
			params.put("cardIssuer",promtSettCostList.getCardIssuer());
			params.put("merchId",promtSettCostList.getMerchId());
		}
		page = promtSettCostListDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(promtSettCostList, "促销活动交易明细对象不能为空");
		Assert.notEmpty(promtSettCostList.getTransSn(), "促销活动交易明细对象交易流水不能为空");
		Assert.notEmpty(promtSettCostList.getPromtId(), "促销活动交易明细对象促销活动id不能为空");

		promtSettCostList = (PromtSettCostList) promtSettCostListDAO.findByPk(promtSettCostList);
		return DETAIL;
	}
	
	private void initPage() {
	}
	
	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn;
	}

	public String getPromtId() {
		return promtId;
	}

	public void setPromtId(String promtId) {
		this.promtId = promtId;
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
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public PromtSettCostList getPromtSettCostList() {
		return promtSettCostList;
	}

	public void setPromtSettCostList(PromtSettCostList promtSettCostList) {
		this.promtSettCostList = promtSettCostList;
	}

}
