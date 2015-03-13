package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.IcAppmodelDescDAO;
import gnete.card.dao.IcTempParaDAO;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.IcAppmodelDesc;
import gnete.card.entity.IcTempPara;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.flag.CardFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.CardSubClassExpirMthd;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.PasswordType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductTemplateService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductSubClassAction.java
 *
 * @description:
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: HeChuan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2012-6-27 下午03:10:05
 */
public class SingleProductSubClassAction extends BaseAction {
	
	private CardSubClassTemp cardSubClassTemp;
	private List<CardTypeCode> cardTypeList;
	private Paginater page;
	private String cardSubclass;
	
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private CardSubClassTempDAO cardSubClassTempDAO;
	@Autowired
	private SingleProductTemplateService singleProductTemplateService;
	private static final String  DEFAULT_SUCCESS_URL="/pages/singleProduct/subclass/list.do?goBack=goBack";
	@Autowired
	private IcTempParaDAO icTempParaDAO;
	@Autowired
	private PointClassTempDAO pointClassTempDAO;
	@Autowired
	private MembClassTempDAO membClassTempDAO;
	@Autowired
	private IcAppmodelDescDAO icAppmodelDescDAO;
	
	/**
	 * 积分子类型定义
	 */
	private List<PointClassTemp> ponitClassList;
	/**
	 * 会员子类型定义
	 */
	private List<MembClassTemp> membClassList;

	private List pwdTypeList;
	private List expirMthdList;
	private List yesOrNoList;
	private List<IcAppmodelDesc> modelList;
	private List<CardFlag> cardFlagList;
	private List<YesOrNoFlag> isYesList;
	
	private PointClassTemp pointClassTemp;
	private MembClassTemp membClassTemp;
	private IcTempPara icTempPara;
	
	private IcAppmodelDesc icAppmodelDesc;
	private String startDate;
	private String endDate;
	
	@Override
	public String execute() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		
		List<CardTypeCode> list = new ArrayList<CardTypeCode>();
		for (CardTypeCode typeCode : cardTypeList) {
			if (!StringUtils.equals(typeCode.getCardTypeCode(), CardType.VIRTUAL.getValue())) {
				list.add(typeCode);
			}
		}
		this.cardTypeList = list;
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardSubClassTemp != null) {
			params.put("cardSubclass", MatchMode.ANYWHERE.toMatchString(cardSubClassTemp.getCardSubclass()));
			params.put("cardSubclassName", MatchMode.ANYWHERE.toMatchString(cardSubClassTemp.getCardSubclassName()));
			params.put("cardClass", cardSubClassTemp.getCardClass());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		// 运营中心查看所有
		if (RoleType.CENTER.getValue().equals(super.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(super.getLoginRoleType())) {
		}
		// 分支机构查看自己及自己的下级所管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(super.getLoginRoleType())) {
			params.put("branchCode", super.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查询卡类型模板列表");
		}
		
		page = cardSubClassTempDAO.findSubClassTemp(params, getPageNumber(), getPageSize());
		return LIST;
	}
	public String showAdd()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能定义卡类型模板。");
		}
		initPage();
		this.cardSubClassTemp = new CardSubClassTemp();
		this.cardSubClassTemp.setChkPwd(new BigDecimal(1));
		this.cardSubClassTemp.setPwdType(PasswordType.FIXED.getValue());
		this.cardSubClassTemp.setExpirMthd(CardSubClassExpirMthd.SPECIFY_DATE.getValue());
		this.cardSubClassTemp.setIcType(CardFlag.CARD.getValue());
		this.cardSubClassTemp.setChangeFaceValue(Symbol.YES);
		this.cardSubClassTemp.setDepositFlag(Symbol.YES);
		
		return ADD;
	}

	public String add()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能定义卡类型模板。");
		}
		singleProductTemplateService.addSubClassTemp(cardSubClassTemp, icTempPara, this.getSessionUser());
		String msg = LogUtils.r("卡类型[{0}]的新增成功", cardSubClassTemp.getCardSubclassName());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String showModify()throws Exception{
		initPage();
		cardSubClassTemp = (CardSubClassTemp)this.cardSubClassTempDAO.findByPk(cardSubClassTemp.getCardSubclass());
		return MODIFY;
	}
	public String detail()throws Exception{
		cardSubClassTemp = (CardSubClassTemp)this.cardSubClassTempDAO.findByPk(cardSubClassTemp.getCardSubclass());
		pointClassTemp = (PointClassTemp) pointClassTempDAO.findByPk(cardSubClassTemp.getPtClass());
		membClassTemp = (MembClassTemp) membClassTempDAO.findByPk(cardSubClassTemp.getMembClass());
		
		icAppmodelDesc = (IcAppmodelDesc) this.icAppmodelDescDAO.findByPk(cardSubClassTemp.getIcModelNo());
		icTempPara = (IcTempPara) this.icTempParaDAO.findByPk(cardSubClassTemp.getCardSubclass());
		
		return DETAIL;
	}
	
	public String modify()throws Exception{
		
		if (StringUtils.equals(cardSubClassTemp.getExpirMthd(),
				CardSubClassExpirMthd.SPECIF_MOTHS.getValue())) {
			cardSubClassTemp.setExpirDate(StringUtils.EMPTY);
		} else {
			cardSubClassTemp.setEffPeriod(null);
		}
		if((cardSubClassTemp.getCardSubclass()==null || "".equals(cardSubClassTemp.getCardSubclass())) && cardSubclass!=null){
			cardSubClassTemp.setCardSubclass(cardSubclass);
		}
		this.singleProductTemplateService.modifySubClassTemp(cardSubClassTemp,this.getSessionUser());
		String msg = "修改卡类型模板[" + cardSubClassTemp.getCardSubclass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String delete()throws Exception{
		this.singleProductTemplateService.deleteSubClassTemp(cardSubclass);
		String msg = "删除卡类型模板[" + cardSubclass + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	private void initPage() throws Exception {
		this.yesOrNoList = TrueOrFalseFlag.getAll();
		this.expirMthdList = CardSubClassExpirMthd.getAll();
		this.cardTypeList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		this.pwdTypeList = PasswordType.getAll();
		this.modelList = this.icAppmodelDescDAO.findAll();
		this.cardFlagList = CardFlag.getAll();
		this.isYesList = YesOrNoFlag.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("issId", getSessionUser().getBranchNo());
		params.put("branchCode", getSessionUser().getBranchNo());
		params.put("status", CommonState.NORMAL.getValue());

		// 获取各子类型定义
		this.ponitClassList = pointClassTempDAO.findList(params);//积分类型
		this.membClassList = membClassTempDAO.findList(params);//会员类型
	}
	public CardSubClassTemp getCardSubClassTemp() {
		return cardSubClassTemp;
	}
	public void setCardSubClassTemp(CardSubClassTemp cardSubClassTemp) {
		this.cardSubClassTemp = cardSubClassTemp;
	}
	public List<CardTypeCode> getCardTypeList() {
		return cardTypeList;
	}
	public void setCardTypeList(List<CardTypeCode> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}
	public Paginater getPage() {
		return page;
	}
	public void setPage(Paginater page) {
		this.page = page;
	}
	public String getCardSubclass() {
		return cardSubclass;
	}
	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public List<CardFlag> getCardFlagList() {
		return cardFlagList;
	}
	public void setCardFlagList(List<CardFlag> cardFlagList) {
		this.cardFlagList = cardFlagList;
	}

	public List getExpirMthdList() {
		return expirMthdList;
	}
	public void setExpirMthdList(List expirMthdList) {
		this.expirMthdList = expirMthdList;
	}

	public List<IcAppmodelDesc> getModelList() {
		return modelList;
	}
	public void setModelList(List<IcAppmodelDesc> modelList) {
		this.modelList = modelList;
	}

	public List getPwdTypeList() {
		return pwdTypeList;
	}
	public void setPwdTypeList(List pwdTypeList) {
		this.pwdTypeList = pwdTypeList;
	}
	public List getYesOrNoList() {
		return yesOrNoList;
	}
	public void setYesOrNoList(List yesOrNoList) {
		this.yesOrNoList = yesOrNoList;
	}
	public IcTempPara getIcTempPara() {
		return icTempPara;
	}
	public void setIcTempPara(IcTempPara icTempPara) {
		this.icTempPara = icTempPara;
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
	public IcAppmodelDesc getIcAppmodelDesc() {
		return icAppmodelDesc;
	}
	public void setIcAppmodelDesc(IcAppmodelDesc icAppmodelDesc) {
		this.icAppmodelDesc = icAppmodelDesc;
	}
	public List<MembClassTemp> getMembClassList() {
		return membClassList;
	}
	public void setMembClassList(List<MembClassTemp> membClassList) {
		this.membClassList = membClassList;
	}
	public List<PointClassTemp> getPonitClassList() {
		return ponitClassList;
	}
	public void setPonitClassList(List<PointClassTemp> ponitClassList) {
		this.ponitClassList = ponitClassList;
	}
	public MembClassTemp getMembClassTemp() {
		return membClassTemp;
	}
	public void setMembClassTemp(MembClassTemp membClassTemp) {
		this.membClassTemp = membClassTemp;
	}
	public PointClassTemp getPointClassTemp() {
		return pointClassTemp;
	}
	public void setPointClassTemp(PointClassTemp pointClassTemp) {
		this.pointClassTemp = pointClassTemp;
	}
	public List<YesOrNoFlag> getIsYesList() {
		return isYesList;
	}
	public void setIsYesList(List<YesOrNoFlag> isYesList) {
		this.isYesList = isYesList;
	}

}
