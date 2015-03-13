package gnete.card.web.greatDiscount.greatDiscntTrans;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.GreatDiscntTransDAO;
import gnete.card.entity.GreatDiscntTrans;
import gnete.card.entity.state.GreatDiscntTransState;
import gnete.card.entity.type.PayWayType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 高级折扣
 * @Project: CardWeb
 * @File: GreatDiscntTransAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-30上午9:33:36
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class GreatDiscntTransAction extends BaseAction {

	@Autowired
	private GreatDiscntTransDAO greatDiscntTransDAO;
	
	/** 定义状态 */
	private List greatDiscntTransStatusList;
	/** 支持支付方式 */
	private List payWayList;
	/** 处理状态 */
	private List payStutusList;
	private GreatDiscntTrans greatDiscntTrans;
	
	private List<TransType> greatDiscntTransTypeList;

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
			params.put("merchNo", getSessionUser().getMerchantNo());
		} else{
			throw new BizException("非商户或发卡机构禁止查看高级折扣交易信息。");
		}
		
		if (greatDiscntTrans != null) {
			params.put("cardId", greatDiscntTrans.getCardId());
			params.put("transType", greatDiscntTrans.getTransType());
		}
		page = greatDiscntTransDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(greatDiscntTrans, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntTrans.getTransSn(), "高级折扣对象ID不能为空");
		greatDiscntTrans = (GreatDiscntTrans) greatDiscntTransDAO.findByPk(greatDiscntTrans);
		return DETAIL;
	}

	public String delete() throws Exception {
		Assert.notNull(greatDiscntTrans, "高级折扣对象不能为空");
		Assert.notEmpty(greatDiscntTrans.getTransSn(), "高级折扣对象ID不能为空");
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || 
				getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作。");
		}
		boolean isSuccess = greatDiscntTransDAO.delete(greatDiscntTrans)>0? true:false;
		if (isSuccess) {
			String msg = LogUtils.r("删除高级折扣交易信息[{0}]成功。", greatDiscntTrans.getTransSn());
			log(msg, UserLogType.DELETE);
			addActionMessage("/greatDiscount/greatDiscntTrans/list.do", msg);
		}
		return SUCCESS;
	}
	
	private void initPage() {
		greatDiscntTransTypeList = TransType.getGreatDiscntTransType();
		greatDiscntTransStatusList = GreatDiscntTransState.getAll();
		payWayList = PayWayType.getAll();
		payStutusList = GreatDiscntTransState.getAll();
	}

	public List getPayStutusList() {
		return payStutusList;
	}

	public void setPayStutusList(List payStutusList) {
		this.payStutusList = payStutusList;
	}

	public List getGreatDiscntTransStatusList() {
		return greatDiscntTransStatusList;
	}

	public void setGreatDiscntTransStatusList(List greatDiscntTransStatusList) {
		this.greatDiscntTransStatusList = greatDiscntTransStatusList;
	}

	public List getPayWayList() {
		return payWayList;
	}

	public void setPayWayList(List payWayList) {
		this.payWayList = payWayList;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public List<TransType> getGreatDiscntTransTypeList() {
		return greatDiscntTransTypeList;
	}

	public void setGreatDiscntTransTypeList(List<TransType> greatDiscntTransTypeList) {
		this.greatDiscntTransTypeList = greatDiscntTransTypeList;
	}

	public GreatDiscntTrans getGreatDiscntTrans() {
		return greatDiscntTrans;
	}

	public void setGreatDiscntTrans(GreatDiscntTrans greatDiscntTrans) {
		this.greatDiscntTrans = greatDiscntTrans;
	}

	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

}
