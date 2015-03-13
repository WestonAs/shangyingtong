package gnete.card.web.cardInfo;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CouponBalDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TransDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CouponBal;
import gnete.card.entity.PointBal;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.Trans;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.SubacctType;
import gnete.card.service.GenerateFileService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardAcctReviewAction.java
 *
 * @description: 卡账户详细查询(新)
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-17 下午08:27:21
 */
public class CardAcctReviewAction extends BaseAction {
	
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private CouponBalDAO couponBalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransDAO transDAO;
	@Autowired
	private GenerateFileService generateFileService;

	private AcctInfo acctInfo;
	private CardInfo cardInfo;
	private Trans trans;
	
	private Paginater page;
	/** 账户交易明细页面 */
	private Paginater acctTransPage;
	
	/** 子账户余额列表 */
	private List<SubAcctBal> subAcctBalList;
	/** 积分账户列表 */
	private List<PointBal> pointBalList;
	/** 赠券账户列表 */
	private List<CouponBal> couponBalList;
	/** 发卡机构列表 */
	private List<BranchInfo> cardBranchList;
	/** 卡种列表 */
	private List<CardType> cardTypeList;
	
	/** 开始清算日期 */
	private String settStartDate;
	/** 结束清算日期 */
	private String settEndDate;
	
	/** 开始卡号 */
	private String startCardId;
	/** 结束卡号 */
	private String endCardId;
	
	private String cardIds;
	
	private String branchName;
	
	/** 查询最大的卡号数1000万，去掉校验位后的卡数量 */
	private static final BigDecimal MAX_SIZE = new BigDecimal(100000000);
	
	@Override
	public String execute() throws Exception {
		
		this.cardTypeList = CardType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) {// 发卡机构
			this.cardBranchList = this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode());
			params.put("cardIssuers", this.cardBranchList);

		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
			this.cardBranchList = Arrays.asList(loginBranch);
			params.put("appOrgId", this.getSessionUser().getDeptId());

		} else if (isCardSellRoleLogined()) {// 售卡代理登录时
			this.cardBranchList = this.getMyCardBranch();
			params.put("appOrgId", this.getSessionUser().getBranchNo());

		} else if (isMerchantRoleLogined()) {// 商户
			this.cardBranchList = this.getMyCardBranch();
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		if (acctInfo != null || cardInfo !=null) {
			params.put("startCardId", this.getStartCardId()); // 起始卡号
			if (StringUtils.isNotBlank(this.getStartCardId())&&StringUtils.isBlank(this.getEndCardId()) ){
				params.put("endCardId", this.getStartCardId()); // 结束卡号与开始卡号一样
			}else{
				params.put("endCardId", this.getEndCardId()); // 结束卡号
			}
			params.put("cardIssuer", acctInfo.getCardIssuer()); // 发卡机构号
			params.put("cardBin", cardInfo.getCardBin()); // 卡BIN
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(cardInfo.getExternalCardId())) {
				params.put("externalCardId", cardInfo.getExternalCardId()); // 外部号码
			}
			
			params.put("cardClass", acctInfo.getCardClass()); // 卡种类
			
			String startCardId = (String)params.get("startCardId");
			String endCardId = (String)params.get("endCardId");
			// 起始卡号和结束卡号都不为空时，要检查卡数量
			if (StringUtils.isNotBlank(startCardId) && StringUtils.isNotBlank(endCardId)) {
				if (AmountUtil.gt(AmountUtil.subtract(NumberUtils.createBigDecimal(endCardId), NumberUtils.createBigDecimal(startCardId)), MAX_SIZE)) {
					this.page = Paginater.EMPTY;
				} else {
					this.page = this.acctInfoDAO.findAcctInfo(params, this.getPageNumber(), this.getPageSize());
				}
			} 
			// 否则，发卡机构、卡BIN、外部号码 查询参数必须有一个
			else if (StringUtils.isBlank(acctInfo.getCardIssuer())
					&& StringUtils.isBlank(cardInfo.getCardBin())
					&& StringUtils.isBlank(cardInfo.getExternalCardId())) {
				this.page = Paginater.EMPTY;
			} else {
				this.page = this.acctInfoDAO.findAcctInfo(params, this.getPageNumber(), this.getPageSize());
			}
		}
		page = this.setPageData(page);
		
		return LIST;
	}
	
	
	public String listDiscontinuousCard() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardRoleLogined()) {// 发卡机构
			params.put("cardIssuers", this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode()));
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			params.put("appOrgId", this.getSessionUser().getDeptId());
		} else if (isCardSellRoleLogined()) {// 售卡代理登录时
			params.put("appOrgId", this.getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		if (StringUtils.isNotBlank(cardIds)) {
			Assert.isTrue(cardIds.matches("[0-9\r\n]*"), "请输入合法的卡号");
			String[] cardIdArr = cardIds.trim().split("\r\n");
			if (!ArrayUtils.isEmpty(cardIdArr)) {
				params.put("cardIdArr", cardIdArr);
				this.page = this.acctInfoDAO.findAcctInfo(params, this.getPageNumber(), this.getPageSize());
				this.setPageData(page);
			}
		}
		return "listDiscontinuousCard";
	}
	
	
	private Paginater setPageData(Paginater paginater) {
		if (paginater == null || paginater.getMaxRowCount() == 0 ) {
			return Paginater.EMPTY;
		}
		
		List<AcctInfo> resultList = new ArrayList<AcctInfo>();
		List<AcctInfo> infoList = (List<AcctInfo>) paginater.getData();
		
		for (AcctInfo info : infoList) {
			String acctId = info.getAcctId();
			
			SubAcctBalKey rebateKey = new SubAcctBalKey(acctId, SubacctType.REBATE.getValue());
			SubAcctBal rebateBal = (SubAcctBal) this.subAcctBalDAO.findByPk(rebateKey);
			
			if (rebateBal == null) {
				info.setRebateAvlbBal(BigDecimal.ZERO);
			} else {
				info.setRebateAvlbBal(rebateBal.getAvlbBal());
			}
			
			SubAcctBalKey depositKey = new SubAcctBalKey(acctId, SubacctType.DEPOSIT.getValue());
			SubAcctBal depositBal = (SubAcctBal) this.subAcctBalDAO.findByPk(depositKey);
			
			if (depositBal == null) {
				info.setDepositAvlbBal(BigDecimal.ZERO);
			} else {
				info.setDepositAvlbBal(depositBal.getAvlbBal());
			}
			
			resultList.add(info);
		}
		
		paginater.setData(resultList);
		
		return paginater;
	}
	
	// 显示卡账户详细信息，包括子账户和历史余额表的部分信息
	public String detail()throws Exception{
		
		Map<String, Object> params = new HashMap<String, Object>();
		Assert.notNull(acctInfo, "卡账户对象不能为空");
		params.put("acctId", acctInfo.getAcctId());
		Assert.notEmpty(acctInfo.getAcctId(), "要查询的账户id不能为空");
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) {// 发卡机构
			params.put("cardIssuers", this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode()));
		
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			params.put("appOrgId", this.getSessionUser().getDeptId());
			
		} else if (isCardSellRoleLogined()) {// 售卡代理登录时
			params.put("appOrgId", this.getSessionUser().getBranchNo());
			
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else{
			throw new BizException("没有权限查询。");
		} 
		
		// 账户信息明细
		this.acctInfo = (AcctInfo)this.acctInfoDAO.findByPkWithCheck(params);
		Assert.notNull(acctInfo, "账号[" + acctInfo.getAcctId() + "]不存在。");
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("acctId", acctInfo.getAcctId());

		// 子账户列表信息
		this.subAcctBalList = this.subAcctBalDAO.findSubAcctBal(searchParams);
		
		// 积分账户列表信息
		this.pointBalList = this.pointBalDAO.findPointBal(searchParams);
		
		// 赠券账户列表信息
		this.couponBalList = this.couponBalDAO.findCouponBalList(searchParams);
		
		
		return DETAIL;
	}
	
	// 账户交易明细，默认为查询当前月的交易明细
	public String acctTransList(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		String cardId = this.getFormMapValue("cardId");
		params.put("cardId", cardId);
		
		// 获得当前月和下个月
		String currentMonth = DateUtil.formatDate("yyyyMM");
		
		// 默认为当月的起始日期
		if(StringUtils.isBlank(settStartDate)){
			settStartDate = currentMonth.concat("01");
		} 
		params.put("settStartDate", settStartDate);
		
		// 默认为当月的结束日期
		if(StringUtils.isBlank(settEndDate)){
			settEndDate = DateUtil.getLastDay(new Date());
		}
		params.put("settEndDate", settEndDate);
		
		this.acctTransPage = this.transDAO.findTrans(params, this.getPageNumber(), this.getPageSize());	
		
		return "acctTransList";
	}
	
	/**
	 * 手动生成Excel文件
	 * @throws Exception
	 */
	public void generate() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
			
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) {// 发卡机构
			params.put("cardIssuers", this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode()));
			
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			params.put("appOrgId", this.getSessionUser().getDeptId());
			
		} else if (isCardSellRoleLogined()) {// 售卡代理登录时
			params.put("appOrgId", this.getSessionUser().getBranchNo());
			
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		} 
		
		if (acctInfo != null || cardInfo !=null) {
			params.put("startCardId", this.getStartCardId()); // 起始卡号
			if (StringUtils.isNotBlank(this.getStartCardId())&&StringUtils.isBlank(this.getEndCardId()) ){
				params.put("endCardId", this.getStartCardId()); // 结束卡号与开始卡号一样
			}else{
				params.put("endCardId", this.getEndCardId()); // 结束卡号
			}
			params.put("cardIssuer", acctInfo.getCardIssuer()); // 发卡机构号
			params.put("cardBin", cardInfo.getCardBin()); // 卡BIN
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(cardInfo.getExternalCardId())) {
				params.put("externalCardId", cardInfo.getExternalCardId()); // 外部号码
			}
			params.put("cardClass", acctInfo.getCardClass()); // 卡种类
			
			this.generateFileService.generateCardAcctExcel(response, params);
		}
	}

	public AcctInfo getAcctInfo() {
		return acctInfo;
	}

	public void setAcctInfo(AcctInfo acctInfo) {
		this.acctInfo = acctInfo;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getAcctTransPage() {
		return acctTransPage;
	}

	public void setAcctTransPage(Paginater acctTransPage) {
		this.acctTransPage = acctTransPage;
	}

	public List<SubAcctBal> getSubAcctBalList() {
		return subAcctBalList;
	}

	public void setSubAcctBalList(List<SubAcctBal> subAcctBalList) {
		this.subAcctBalList = subAcctBalList;
	}

	public List<PointBal> getPointBalList() {
		return pointBalList;
	}

	public void setPointBalList(List<PointBal> pointBalList) {
		this.pointBalList = pointBalList;
	}

	public List<CouponBal> getCouponBalList() {
		return couponBalList;
	}

	public void setCouponBalList(List<CouponBal> couponBalList) {
		this.couponBalList = couponBalList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public String getStartCardId() {
		return startCardId;
	}

	public void setStartCardId(String startCardId) {
		this.startCardId = startCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCardIds() {
		return cardIds;
	}

	public void setCardIds(String cardIds) {
		this.cardIds = cardIds;
	}
}
