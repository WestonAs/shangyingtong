package gnete.card.web.ajax;

import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchClusterInfoDAO;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchClusterInfo;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于 通用ajax查询 <br/>
 * 
 * <b>注意：如果某个ajax查询只是针对某特定模块的，请放到其特定模块下，而不要放到本action中</b>
 * 
 */
@SuppressWarnings("serial")
public class AjaxFindAction extends BaseAction {
	@Autowired
	private CardSubClassDefDAO	cardSubClassDefDAO;
	@Autowired
	private MerchClusterInfoDAO	merchClusterInfoDAO;
	@Autowired
	private CardExtraInfoDAO	cardExtraInfoDAO;
	@Autowired
	private CardInfoDAO			cardInfoDAO;
	@Autowired
	private CouponClassDefDAO	couponClassDefDAO;

	@Override
	public String execute() throws Exception {
		return null;
	}

	/**
	 * ajax查找发卡机构的卡子类型列表（根据发卡机构编号）
	 */
	public void ajaxFindCardSubClasses() {
		JSONObject json = new JSONObject();
		String cardIssuer = this.formMap.get("cardIssuer");
		if (StringUtils.isNotBlank(cardIssuer)) {
			List<CardSubClassDef> cardSubClassDefs = cardSubClassDefDAO
					.findCardSubClassDefByBranNo(cardIssuer);
			json.put("cardSubClassDefs", cardSubClassDefs);
		}
		this.responseJsonObject(json);
	}

	/**
	 * ajax查找发卡机构的商户集群信息（根据发卡机构编号）
	 */
	public void ajaxFindMerchClusterInfos() {
		JSONObject json = new JSONObject();
		String cardIssuer = this.formMap.get("cardIssuer");
		if (StringUtils.isNotBlank(cardIssuer)) {
			List<MerchClusterInfo> merchClusterInfos = merchClusterInfoDAO.findByCardIssuer(cardIssuer);
			json.put("merchClusterInfos", merchClusterInfos);
		}
		this.responseJsonObject(json);
	}

	/**
	 * ajax查询持卡人信息（根据条件）
	 */
	public void ajaxFindCardExtraInfos() {
		String cardBranch = this.formMap.get("cardBranch");
		String credNo = this.formMap.get("credNo");
		String mobileNo = this.formMap.get("mobileNo");

		JSONObject retJson = new JSONObject();
		if (StringUtils.isBlank(cardBranch)) {
			responseJsonObject("-1", "发卡机构参数不能为空！");
			return;
		}
		if (isCardOrCardDeptRoleLogined()) {
			try {
				if (!BranchUtil.isBelong2SameTopBranch(this.getLoginBranchCode(), cardBranch)) {
					responseJsonObject("-1", "请检查发卡机构参数！");
					return;
				}
			} catch (BizException e) {
				logger.warn("", e);
				responseJsonObject("-1", "访问异常！");
				return;
			}
		}

		if (StringUtils.isBlank(credNo) && StringUtils.isBlank(mobileNo)) {
			responseJsonObject("-1", "身份证号与手机号参数不能都为空！");
			return;
		}

		List<CardExtraInfo> list = cardExtraInfoDAO.findCardExtraInfoByParam(formMap);
		retJson.put("cnt", list == null ? 0 : list.size());
		responseJsonObject(retJson, "1", "查询成功！");
		return;
	}

	/** ajax加载赠券类型 */
	public void ajaxFindCouponClass() {
		String cardId = this.getFormMapValue("cardId");
		JSONObject retJson = new JSONObject();
		if (isCardOrCardDeptRoleLogined() && StringUtils.isNotEmpty(cardId)) {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			if (cardId != null && this.getLoginBranchCode().equals(cardInfo.getCardIssuer())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("jinstId", cardInfo.getCardIssuer()); // 联名机构编号，为要赠送的卡所属的发卡机构
				List<CouponClassDef> couponClassList = this.couponClassDefDAO.findCouponClassList(params);
				if (!CollectionUtils.isEmpty(couponClassList)) {
					retJson.put("couponClassList", couponClassList);
					responseJsonObject(retJson);
					return;
				}
			}
		}
		responseJsonObject("-1", "没有找到合适的赠券类型！");
		return;
	}

}
