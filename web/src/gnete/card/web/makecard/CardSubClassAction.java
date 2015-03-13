package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.dao.IcAppmodelDescDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.IcAppmodelDesc;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.flag.CardFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.flag.ecouponTypeFlag;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.MemberCertifyState;
import gnete.card.entity.type.CardSubClassExpirMthd;
import gnete.card.entity.type.PasswordType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MakeCardService;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardSubClassAction.java
 * 
 * @description: 卡类型处理Action
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-29
 */
public class CardSubClassAction extends BaseAction {

	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private MakeCardService makeCardService;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private DiscntClassDefDAO discntClassDefDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private IcAppmodelDescDAO icAppmodelDescDAO;
	@Autowired
	private IcTempParaDAO icTempParaDAO;

	private List<CardTypeCode> cardTypeList;

	/**
	 * 赠券子类型定义
	 */
	private List<CouponClassDef> coupnClassList;
	/**
	 * 次卡类型定义
	 */
	private List<AccuClassDef> accuClassList;
	/**
	 * 积分子类型定义
	 */
	private List<PointClassDef> ponitClassList;
	/**
	 * 会员子类型定义
	 */
	private List<MembClassDef> membClassList;
	/**
	 * 折扣子类型定义
	 */
	private List<DiscntClassDef> discntClassList;
	/**
	 * 当前机构可用卡BIN列表
	 */
	private List<CardBin> cardBinList;
	private CardBin cardBin;// 查找cardBin列表的输入条件，用于保存cardIssuer和cardType
	
	
	/**
	 * 密码类型列表
	 */
	private List pwdTypeList;
	private List expirMthdList;
	private List trueOrFalseList;
	private List<IcAppmodelDesc> modelList;
	private List<CardFlag> cardFlagList;
	private List<YesOrNoFlag> yesOrNoList;
	/**
	 * 电子消费券可用列表
	 */
	private List<ecouponTypeFlag> ecouponList;
	
	
	private CardSubClassDef cardSubClassDef;

	private CouponClassDef couponClassDef;
	private AccuClassDef accuClassDef;
	private PointClassDef pointClassDef;
	private MembClassDef membClassDef;
	private DiscntClassDef discntClassDef;
	private IcTempPara icTempPara;
	private IcAppmodelDesc icAppmodelDesc;

	private Paginater page;

	private String cardSubclass;
	private String startDate;
	private String endDate;

	// 界面选择时是否单选
	private boolean radio;
	private String merchantNo;
	private String cardIssuerNo;
	private String sellBranch;
	private String cardType;

	@Override
	public String execute() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardSubClassDef != null) {
			params.put("cardSubclass", MatchMode.ANYWHERE.toMatchString(cardSubClassDef.getCardSubclass()));
			params.put("cardSubclassName", MatchMode.ANYWHERE.toMatchString(cardSubClassDef
					.getCardSubclassName()));
			params.put("cardClass", cardSubClassDef.getCardClass());
			params.put("cardIssuer", cardSubClassDef.getCardIssuer());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心

		} else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("fenzhiList", super.getMyManageFenzhi());
		} else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardBranchList", super.getMyCardBranch());
		} else {
			throw new BizException("没有权限查询卡类型列表");
		}
		this.page = this.cardSubClassDefDAO.findCardSubClassDef(params, this.getPageNumber(), this
				.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		detailData();
		return DETAIL;
	}

	private void detailData() throws Exception {
		this.cardSubClassDef = (CardSubClassDef) cardSubClassDefDAO.findByPk(cardSubClassDef
				.getCardSubclass());
		couponClassDef = (CouponClassDef) couponClassDefDAO.findByPk(cardSubClassDef.getCouponClass());
		accuClassDef = (AccuClassDef) accuClassDefDAO.findByPk(cardSubClassDef.getFrequencyClass());
		pointClassDef = (PointClassDef) pointClassDefDAO.findByPk(cardSubClassDef.getPtClass());
		membClassDef = (MembClassDef) membClassDefDAO.findByPk(cardSubClassDef.getMembClass());
		discntClassDef = (DiscntClassDef) discntClassDefDAO.findByPk(cardSubClassDef.getDiscntClass());

		this.icAppmodelDesc = (IcAppmodelDesc) this.icAppmodelDescDAO
				.findByPk(cardSubClassDef.getIcModelNo());
		this.icTempPara = (IcTempPara) this.icTempParaDAO.findByPk(cardSubClassDef.getCardSubclass());
	}
	
	@Override
	public String preShowAdd() throws BizException{
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能新增卡类型！");
		return super.preShowAdd();
	}
	
	/** 显示新增页面 */
	public String showAdd() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能新增卡类型！");
		
		Assert.notBlank(cardSubClassDef.getCardIssuer(), "发卡机构不能为空，请先选择发卡机构！");
		
		this.cardSubClassDef.setChkPwd(new BigDecimal(1));
		this.cardSubClassDef.setPwdType(PasswordType.FIXED.getValue());
		this.cardSubClassDef.setExpirMthd(CardSubClassExpirMthd.SPECIFY_DATE.getValue());
		this.cardSubClassDef.setChkPfcard(new BigDecimal(1));
		this.cardSubClassDef.setAutoCancelFlag(new BigDecimal(1));
		this.cardSubClassDef.setIcType(CardFlag.CARD.getValue());
		this.cardSubClassDef.setChangeFaceValue(Symbol.YES);
		this.cardSubClassDef.setDepositFlag(Symbol.YES);
		initPage();

		return ADD;
	}

	public String binNoList() throws Exception {
		cardBinList = cardBinDAO.findCardBin(cardBin.getCardIssuer(), cardBin.getCardType(),
				CardBinState.NORMAL.getValue());
		return "binNoList";
	}

	private void initPage() throws Exception {
		this.trueOrFalseList = TrueOrFalseFlag.getAll();
		this.expirMthdList = CardSubClassExpirMthd.getAll();
		this.cardTypeList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		this.pwdTypeList = PasswordType.getAll();
		this.modelList = this.icAppmodelDescDAO.findAll();
		this.cardFlagList = CardFlag.getAll();
		this.yesOrNoList = YesOrNoFlag.getAll();
		this.ecouponList = ecouponTypeFlag.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("issId", this.cardSubClassDef.getCardIssuer());
		params.put("cardIssuer", this.cardSubClassDef.getCardIssuer());
		params.put("status", MemberCertifyState.PASSED.getValue());

		// 获取各子类型定义
		this.coupnClassList = couponClassDefDAO.findCouponClassList(params);// 赠券子类型
		this.accuClassList = accuClassDefDAO.findAccuClassList(params);
		this.ponitClassList = pointClassDefDAO.findPontClassList(params);
		this.membClassList = membClassDefDAO.findByCardIssuer(params);
		this.discntClassList = discntClassDefDAO.findDiscntClassList(params);
	}

	public void getFaceValue() {
		try {
			String coupnClassId = request.getParameter("coupnClassId");
			CouponClassDef coupon = (CouponClassDef) couponClassDefDAO.findByPk(coupnClassId);
			Assert.notNull(coupon, "找不到该赠券子类型");
			Assert.notNull(coupon.getFaceValue(), "查到赠券子类型的参考面值不能为空");
			respond("{'success':true, 'faceValue':'" + coupon.getFaceValue() + "'}");
		} catch (BizException e) {
			respond("{'success':false, 'errorMsg':'" + e.getMessage() + "'}");
		}
	}

	/** 新增卡类型 */
	public String add() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能新增卡类型！");
		}
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(cardSubClassDef.getCardIssuer(), this
					.getLoginBranchCode()), "申请卡类型的发卡机构与发起方不是属于同一顶级机构！");
		}
		// 保存数据到数据库
		CardSubClassDef classDef = this.makeCardService.addCardSubClass(cardSubClassDef, icTempPara, this
				.getSessionUser());
		String msg = LogUtils.r("卡类型[{0}]的新增成功", classDef.getCardSubclassName());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardSubClass/list.do", msg);
		return SUCCESS;
	}

	public String checkList() throws Exception {
		if (!(isCenterOrCenterDeptRoleLogined())) {
			throw new BizException("没有权限进行卡类型审核操作");
		}

		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.CARD_SUB_CLASS_DEF, this
				.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		this.page = this.cardSubClassDefDAO.findCardSubClassDef(params, this.getPageNumber(), this
				.getPageSize());
		return CHECK_LIST;
	}

	public String checkDetail() throws Exception {
		detailData();
		return DETAIL;
	}

	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才能修改卡类型！");
		}
		this.cardSubClassDef = (CardSubClassDef) cardSubClassDefDAO.findByPk(cardSubClassDef
				.getCardSubclass());
		if (isExpireDateOrMonths()) {
			return "modifyExpireDateOrMonths";
		} else {
			initPage();
			return MODIFY;
		}
	}

	/**
	 * 修改卡类型
	 */
	public String modify() throws Exception {
		if (isExpireDateOrMonths()) {
			CardSubClassDef old = (CardSubClassDef) cardSubClassDefDAO.findByPk(cardSubClassDef
					.getCardSubclass());
			old.setExpirDate(this.cardSubClassDef.getExpirDate());
			old.setEffPeriod(this.cardSubClassDef.getEffPeriod());
			cardSubClassDefDAO.update(old);
		} else {
			if (StringUtils.equals(cardSubClassDef.getExpirMthd(), CardSubClassExpirMthd.SPECIF_MOTHS.getValue())) {
				cardSubClassDef.setExpirDate(StringUtils.EMPTY);
			} else {
				cardSubClassDef.setEffPeriod(null);
			}
			this.makeCardService.modifyCardSubClass(this.cardSubClassDef, this.getSessionUser());
			
		}

		String msg = LogUtils.r("修改卡类型[{0}]成功", cardSubClassDef.getCardSubclassName());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardSubClass/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/**
	 * 删除卡类型
	 */
	public String delete() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("只有发卡机构才删除卡类型！");
		}
		this.makeCardService.deleteCardSubClass(this.getCardSubclass());

		String msg = LogUtils.r("删除卡类型[{0}]成功", this.getCardSubclass());
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/cardSubClass/list.do", msg);
		return SUCCESS;
	}

	public String showSelect() throws Exception {
		return "select";
	}

	/**
	 * 卡类型选择器
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardSubClassDef != null) {
			params.put("merchantNo", merchantNo);
			params.put("cardIssuer", cardIssuerNo);
			params.put("sellBranch", sellBranch);
			params.put("cardClass", cardType);
			params.put("status", CheckState.PASSED.getValue());
			params.put("cardSubclass", MatchMode.ANYWHERE.toMatchString(cardSubClassDef.getCardSubclass()));
			params.put("cardSubclassName", MatchMode.ANYWHERE.toMatchString(cardSubClassDef
					.getCardSubclassName()));
		}
		page = cardSubClassDefDAO.findCardSubClassDef(params, getPageNumber(),
				Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	
	private boolean isExpireDateOrMonths(){
			return "expireDateOrMonths".equalsIgnoreCase(this.getFormMapValue("modifyType"));
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

	public List<CardTypeCode> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardTypeCode> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public CardSubClassDef getCardSubClassDef() {
		return cardSubClassDef;
	}

	public void setCardSubClassDef(CardSubClassDef cardSubClassDef) {
		this.cardSubClassDef = cardSubClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CouponClassDef> getCoupnClassList() {
		return coupnClassList;
	}

	public void setCoupnClassList(List<CouponClassDef> coupnClassList) {
		this.coupnClassList = coupnClassList;
	}

	public List<AccuClassDef> getAccuClassList() {
		return accuClassList;
	}

	public void setAccuClassList(List<AccuClassDef> accuClassList) {
		this.accuClassList = accuClassList;
	}

	public List<PointClassDef> getPonitClassList() {
		return ponitClassList;
	}

	public void setPonitClassList(List<PointClassDef> ponitClassList) {
		this.ponitClassList = ponitClassList;
	}

	public List<MembClassDef> getMembClassList() {
		return membClassList;
	}

	public void setMembClassList(List<MembClassDef> membClassList) {
		this.membClassList = membClassList;
	}

	public List<DiscntClassDef> getDiscntClassList() {
		return discntClassList;
	}

	public void setDiscntClassList(List<DiscntClassDef> discntClassList) {
		this.discntClassList = discntClassList;
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public List<CardBin> getCardBinList() {
		return cardBinList;
	}

	public void setCardBinList(List<CardBin> cardBinList) {
		this.cardBinList = cardBinList;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public List getPwdTypeList() {
		return pwdTypeList;
	}

	public void setPwdTypeList(List pwdTypeList) {
		this.pwdTypeList = pwdTypeList;
	}

	public List getTrueOrFalseList() {
		return trueOrFalseList;
	}

	public void setTrueOrFalseList(List trueOrFalseList) {
		this.trueOrFalseList = trueOrFalseList;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getCardIssuerNo() {
		return cardIssuerNo;
	}

	public void setCardIssuerNo(String cardIssuerNo) {
		this.cardIssuerNo = cardIssuerNo;
	}

	public String getSellBranch() {
		return sellBranch;
	}

	public void setSellBranch(String sellBranch) {
		this.sellBranch = sellBranch;
	}

	public CouponClassDef getCouponClassDef() {
		return couponClassDef;
	}

	public void setCouponClassDef(CouponClassDef couponClassDef) {
		this.couponClassDef = couponClassDef;
	}

	public AccuClassDef getAccuClassDef() {
		return accuClassDef;
	}

	public void setAccuClassDef(AccuClassDef accuClassDef) {
		this.accuClassDef = accuClassDef;
	}

	public PointClassDef getPointClassDef() {
		return pointClassDef;
	}

	public void setPointClassDef(PointClassDef pointClassDef) {
		this.pointClassDef = pointClassDef;
	}

	public MembClassDef getMembClassDef() {
		return membClassDef;
	}

	public void setMembClassDef(MembClassDef membClassDef) {
		this.membClassDef = membClassDef;
	}

	public DiscntClassDef getDiscntClassDef() {
		return discntClassDef;
	}

	public void setDiscntClassDef(DiscntClassDef discntClassDef) {
		this.discntClassDef = discntClassDef;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public List getExpirMthdList() {
		return expirMthdList;
	}

	public void setExpirMthdList(List expirMthdList) {
		this.expirMthdList = expirMthdList;
	}

	public IcTempPara getIcTempPara() {
		return icTempPara;
	}

	public void setIcTempPara(IcTempPara icTempPara) {
		this.icTempPara = icTempPara;
	}

	public List<IcAppmodelDesc> getModelList() {
		return modelList;
	}

	public void setModelList(List<IcAppmodelDesc> modelList) {
		this.modelList = modelList;
	}

	public List<CardFlag> getCardFlagList() {
		return cardFlagList;
	}

	public void setCardFlagList(List<CardFlag> cardFlagList) {
		this.cardFlagList = cardFlagList;
	}

	public IcAppmodelDesc getIcAppmodelDesc() {
		return icAppmodelDesc;
	}

	public void setIcAppmodelDesc(IcAppmodelDesc icAppmodelDesc) {
		this.icAppmodelDesc = icAppmodelDesc;
	}

	public List<YesOrNoFlag> getYesOrNoList() {
		return yesOrNoList;
	}

	public void setYesOrNoList(List<YesOrNoFlag> yesOrNoList) {
		this.yesOrNoList = yesOrNoList;
	}

	public List<ecouponTypeFlag> getEcouponList() {
		return ecouponList;
	}

	public void setEcouponList(List<ecouponTypeFlag> ecouponList) {
		this.ecouponList = ecouponList;
	}
	
}
