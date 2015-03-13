package gnete.card.web.couponprize;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CouponBalDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DepositCouponRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CouponBal;
import gnete.card.entity.CouponBalKey;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DepositCouponReg;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CouponBusService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 赠券赠送相关业务处理
 * @Project: CardWeb
 * @File: CouponDepositAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-3-25上午9:35:45
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class CouponDepositAction extends BaseAction {
	
	@Autowired
	private DepositCouponRegDAO depositCouponRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CouponBalDAO couponBalDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private CouponBusService couponBusService;
	
	private DepositCouponReg depositCouponReg;
	
	private Paginater page;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	/** 状态列表 */
	private List<RegisterState> statusList;
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
    private List<BranchInfo> cardBranchList;
    /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
    /** 结束日期 */
    private String endDate;
	
	/** 默认的列表页面 */
	private static final String DEFAULT_LIST_URL = "/pages/couponDeposit/list.do";
	
	/** 返回列表页面所需要带的参数 */
	private static final String SEARCH_PARAMS = "?goBack=goBack";

	@Override
	public String execute() throws Exception {
		
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositCouponReg != null) {
			params.put("depositBatchId", depositCouponReg.getDepositBatchId());
			params.put("depositCardId", MatchMode.ANYWHERE.toMatchString(depositCouponReg.getCardId()));			
			params.put("listCouponClass", MatchMode.ANYWHERE.toMatchString(depositCouponReg.getCouponClass()));			
			params.put("status", depositCouponReg.getStatus());
			params.put("cardBranch", depositCouponReg.getCardBranch()); //发卡机构
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showCardBranch = true;
		}
		// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 登录用户为售卡代理或发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", super.getSessionUser().getBranchNo());
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		}
		//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setBranchCode(this.getSessionUser().getMerchantNo());
			cardBranchList = new ArrayList<BranchInfo>();
			cardBranchList.add(branchInfo);
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看赠送记录");
		}
		
		params.put("isBatch", false); // 单笔
		
		this.page = this.depositCouponRegDAO.findDepositCouponRegPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询赠券赠送列表！");
		
		return LIST;
	}
	
	/**
	 * 单笔赠券赠送明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("depositBatchId", depositCouponReg.getDepositBatchId());
		this.depositCouponReg = this.depositCouponRegDAO.findDepositCouponCheckList(params).get(0);
		
		return DETAIL;
	}
	
	/**
	 * 单笔赠券赠送新增页面初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
		}
		else {
			throw new BizException("没有权限做赠券赠送操作！");
		}

		depositCouponReg = new DepositCouponReg();
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
    		// 随机数
    		this.depositCouponReg.setRandomSessionid(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositCouponReg.setRandomSessionid("");
		}
    	
		return ADD;
	}
	
	/**
	 * 单笔赠券赠送新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		String serialNo = request.getParameter("serialNo");
		this.couponBusService.addDepositCouponReg(depositCouponReg, this.getSessionUser(), serialNo);
		
		String msg = LogUtils.r("用户[{0}]添加赠券赠送登记ID为[{1}]的记录成功", this.getSessionUserCode(), depositCouponReg.getDepositBatchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_URL + SEARCH_PARAMS, msg);
		
		return SUCCESS;
	}
	
	/**
	 * 检查卡号
	 * @throws Exception
	 */
	public void checkCard() {
		JSONObject object = new JSONObject();
		
		String cardId = request.getParameter("cardId");
		String cardCount = request.getParameter("cardCount");
		try {
			CardInfo cardInfo = this.couponBusService.checkCardId(cardId, cardCount, this.getSessionUser());

			// 发卡机构和发卡机构网点和售卡代理
			if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
					|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
					|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
				object.put("cardIssuer", cardInfo.getCardIssuer());
			}//  如果登录用户为商户时
			else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
				object.put("cardIssuer", getSessionUser().getMerchantNo());
			}
			object.put("endCardId", cardInfo.getCardId());
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 根据卡号得到该卡的赠券类型列表。（返回到页面为String字符串）
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String cardIssuer = request.getParameter("cardIssuer");
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		if (StringUtils.isNotEmpty(cardIssuer)) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("jinstId", cardIssuer); // 联名机构编号，为要赠送的卡所属的发卡机构
			List<CouponClassDef> couponClassList = this.couponClassDefDAO.findCouponClassList(params);

			for (CouponClassDef couponClass : couponClassList){
				sb.append("<option value=\"").append(couponClass.getCoupnClass()).append("\">")
						.append(couponClass.getCoupnClass()).append("-").append(couponClass.getClassName()).append("</option>");
			}
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * 根据卡号、赠券类型，得到该卡的赠券类型的赠券余额
	 * @throws Exception
	 */
	public void calcCardOther() {
		String cardId = request.getParameter("cardId");
		String couponClass = request.getParameter("couponClass");
		if (StringUtils.isEmpty(cardId)
				|| StringUtils.isEmpty(couponClass)) {
			return;
		}
		JSONObject object = new JSONObject();
		
		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做赠券赠送");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在。");
			
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(couponClass);
			Assert.notNull(couponClassDef, "赠券类型[" + couponClass + "]不存在");
			
			CouponBalKey couponBalKey = new CouponBalKey();
			
			couponBalKey.setAcctId(cardInfo.getAcctId());
			couponBalKey.setCouponClass(couponClass);
			CouponBal couponBal = (CouponBal) couponBalDAO.findByPk(couponBalKey);
			if (couponBal != null) {
				object.put("avlPoint", couponBal.getBalance()); //可用赠券
			} else {
				object.put("avlPoint", "0.0");
			}
//			Assert.notNull(couponBal, "卡号[" + cardId + "]赠券类型为[" + couponClass + "]的赠券账户不存在。");

			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 根据卡号，赠券类型号和赠送赠券，得到赠券折算金额
	 * @throws Exception
	 */
	public void calRealAmt() {
		String cardId = request.getParameter("cardId");
		String couponClass = request.getParameter("couponClass");
		String point = request.getParameter("point");
		JSONObject object = new JSONObject();
		
		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做赠券赠送");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在。");
			
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(couponClass);
			Assert.notNull(couponClassDef, "赠券类型[" + couponClass + "]不存在");
			
			// 赠券折算金额
			object.put("refAmt", AmountUtil.multiply(NumberUtils.createBigDecimal(point), couponClassDef.getSettRate()));
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public DepositCouponReg getDepositCouponReg() {
		return depositCouponReg;
	}

	public void setDepositCouponReg(DepositCouponReg depositCouponReg) {
		this.depositCouponReg = depositCouponReg;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
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

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}
}
