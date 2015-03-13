package gnete.card.web.cardholder;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CouponBalDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TransDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.Trans;
import gnete.card.service.CardExtraInfoService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 持卡人登录
 * @author lib
 * @history 2011-3-28
 */
public class CardholderLoginAction extends BaseAction{

	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private CouponBalDAO couponBalDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private TransDAO transDAO;
	@Autowired
	private CardExtraInfoService cardExtraInfoService;
	
	private CardExtraInfo cardExtraInfo;
	private Trans trans;
	private String cardId;
	private Paginater page;
	private Paginater PointPage;
	private Paginater CouponPage;
	private String settStartDate;
	private String settEndDate;
	private String oldPass;
	private String newPass;
	
	@Override
	public String execute() throws Exception {
		try{
			this.cardId = this.cardExtraInfo.getCardId();
		}catch (Exception e) {
		}
		return SUCCESS;
	}
	
	/**
	 * 持卡人检验
	 * @return
	 * @throws Exception
	 */
	public void checkCardholderLogin() throws Exception {
		JSONObject object = new JSONObject();
		String msg = "";
		boolean checkSuccess = false;
		
		// 验证持卡人卡号和密码
		String password = this.cardExtraInfo.getPassword();
		this.cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardExtraInfo.getCardId());
		try {
			this.cardExtraInfoService.checkLogin(cardExtraInfo, password);
			checkSuccess = true;
		} catch (BizException e) {
			msg = e.getMessage();
		}
		object.put("success", checkSuccess);
		object.put("msg", msg);
		this.respond(object.toString());
	}
	
	/**
	 * 查询持卡人余额
	 * @return
	 * @throws Exception
	 */
	public String queryBalance() throws Exception {
		Assert.notNull(cardId, "卡号不能为空");
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.cardId);
		Assert.notNull(cardInfo, "卡号不存在");
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
		Assert.notNull(acctInfo, "账户不能为空");
		
		Map<String, Object> subAcctParams = new HashMap<String, Object>();
		Map<String, Object> PointBalParams = new HashMap<String, Object>();
		Map<String, Object> CouponBalParams = new HashMap<String, Object>();
		
		// 子账户列表信息
		subAcctParams.put("acctId", acctInfo.getAcctId());
		this.page = this.subAcctBalDAO.findSubAcctBal(subAcctParams, this.getPageNumber(), this.getPageSize());
		
		// 积分账户列表信息
		PointBalParams.put("acctId", acctInfo.getAcctId());
		this.PointPage = this.pointBalDAO.getPointBalList(PointBalParams, this.getPageNumber(), this.getPageSize());
		
		// 赠券账户列表信息
		CouponBalParams.put("acctId", acctInfo.getAcctId());
		this.CouponPage = this.couponBalDAO.getCouponBalList(CouponBalParams, this.getPageNumber(), this.getPageSize());
		
		return "queryBalance";
	}
	
	/**
	 * 查询持卡人交易
	 * @return
	 * @throws Exception
	 */
	public String queryTrans() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Assert.notNull(cardId, "卡号不能为空");
		params.put("cardId", this.cardId);
		
		params.put("settStartDate", settStartDate);
		params.put("settEndDate", settEndDate);
		if (trans != null) {
			params.put("transSn", trans.getTransSn());
		}
		this.page = this.transDAO.findTrans(params, this.getPageNumber(), this.getPageSize());	
		
		return "queryTrans";
	}
	
	// 打开初始化操作
	public String showChangePassword() throws Exception {
		Assert.notNull(cardId, "卡号不能为空");
		this.cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);
		return "changePassword";
	}
	
	// 修改查询密码
	public String changePassword() throws Exception {
		try{
			Assert.notNull(cardId, "卡号不能为空");
			this.cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.cardId);
			this.cardExtraInfoService.modifyPass(this.cardExtraInfo.getCardId(), oldPass, newPass, this.getSessionUser());
		}catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPointPage() {
		return PointPage;
	}

	public void setPointPage(Paginater pointPage) {
		PointPage = pointPage;
	}

	public Paginater getCouponPage() {
		return CouponPage;
	}

	public void setCouponPage(Paginater couponPage) {
		CouponPage = couponPage;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
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

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

}
