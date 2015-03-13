package gnete.card.web.couponprize;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CouponBatRegDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CouponBatReg;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CouponRegService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 批量派赠赠券卡
 * @author aps-lib
 *
 */
public class CouponbatRegAction extends BaseAction{

	@Autowired
	private CouponBatRegDAO couponBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CouponRegService couponRegService;
	
	private CouponBatReg couponBatReg;
	private Paginater page;
	private Long couponBatRegId;
	private Collection statusList;
	private List<BranchInfo> cardIssuerList;
	private List<BranchInfo> cardBranchList;
	//private static final int CARD_NO_LENGTH = 19;
	
	@Override
	public String execute() throws Exception {
		
		this.statusList = RegisterState.getForTrade();
		Map<String, Object> params = new HashMap<String, Object>();
		
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				params.put("cardIssuer", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getMerchantNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if(this.couponBatReg!=null){
			params.put("couponBatRegId", this.couponBatReg.getCouponBatRegId()); 
			params.put("status", this.couponBatReg.getStatus());
		}
		this.page = this.couponBatRegDAO.findCouponBatReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得批量派赠赠券卡明细
	public String detail() throws Exception {
		
		this.couponBatReg = (CouponBatReg) this.couponBatRegDAO.findByPk(this.couponBatReg.getCouponBatRegId());
		this.log("查询批量派赠赠券卡["+this.couponBatReg.getCouponBatRegId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD_SELL.getValue())||
				roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())||
				roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构、售卡代理或者商户不能派赠赠券卡。");
		}
		this.cardIssuerList = this.getCardIssuerByMerch();
		return ADD;
	}	
	
	// 批量派赠赠券卡登记
	public String add() throws Exception {		
		
		initPage();
		UserInfo user = this.getSessionUser();
		
		if((RoleType.MERCHANT.getValue()).equals(user.getRole().getRoleType())){
			couponBatReg.setBranchCode(user.getMerchantNo());
		}
		else if((RoleType.CARD_SELL.getValue()).equals(user.getRole().getRoleType())||
				(RoleType.CARD.getValue()).equals(user.getRole().getRoleType())){
			couponBatReg.setBranchCode(user.getBranchNo());
		}
		
		this.couponRegService.addCouponBatReg(couponBatReg, this.getSessionUserCode());
		
		String msg = "批量派赠赠券卡登记["+this.couponBatReg.getCouponBatRegId()+"]成功！";
		this.addActionMessage("/couponbatAward/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD_SELL.getValue())||
				roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()))
				||roleInfo.getRoleType().equals(RoleType.CARD.getValue())){ 
			throw new BizException("非发卡机构、售卡代理或者商户不能派赠赠券卡。");
		}
		initPage();
		this.cardIssuerList = this.getCardIssuerByMerch();
		this.couponBatReg = (CouponBatReg)this.couponBatRegDAO.findByPk(this.couponBatReg.getCouponBatRegId());
		return MODIFY;
	}
	
	// 修改批量派赠登记信息
	public String modify() throws Exception {
		
		this.couponRegService.modifyCouponBatReg(this.couponBatReg);
		this.addActionMessage("/couponbatAward/list.do", "修改批量派赠赠券卡信息成功！");	
		return SUCCESS;
	}
	
	// 删除批量派赠赠券卡信息
	public String delete() throws Exception {
		this.couponRegService.deleteCouponBatReg(this.getCouponBatRegId());
		String msg = "删除批量派赠赠品卡信息[" +this.getCouponBatRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/couponbatAward/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage(){
		
	}
	
	public void validateCardId(){
		String cardId = this.couponBatReg.getStartCard();
		String cardIssuer = this.couponBatReg.getCardIssuer();
		boolean faceValueFlag = false;
		String resultFaceValue = "";
		
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "该卡号不存在，请重新输入。");
			
			// 赠券卡的领卡机构需要和派赠机构一致
			String appOrgId = cardInfo.getAppOrgId();
			UserInfo userInfo = this.getSessionUser();
			String loginCode = RoleType.MERCHANT.getValue().equals(this.getLoginRoleType()) ? 
					userInfo.getMerchantNo() : userInfo.getBranchNo();
			Assert.equals(appOrgId , loginCode, "登陆用户不是赠券卡的领卡机构，请检查卡号是否输入正确。");
			Assert.equals(cardInfo.getCardClass(), CardType.COUPON.getValue(), "起始卡号不是赠券卡。");
			Assert.equals(cardInfo.getCardStatus(), CardState.FORSALE.getValue() , "起始卡号不是领卡待售状态。");
			
			Assert.notTrue(CommonHelper.isEmpty(cardIssuer), "请选择发行机构。");
			
			Assert.isTrue(cardInfo.getCardIssuer().equals(cardIssuer), "发行机构不是赠券卡的发行机构，请重新输入。");
			
			//Long couponCardNum = this.getCouponCardNum(cardId, new BigDecimal(1), cardIssuer);
			//Assert.equals(couponCardNum.toString(), "1" , "起始卡号不是待售赠券卡，请重新输入。");
			
			Assert.notNull(cardInfo.getCardSubclass(), "该卡不存在卡子类型。");
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef.getCouponClass(), "该卡不存在关联的赠券类型。");
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(cardSubClassDef.getCouponClass());
			if(couponClassDef.getFaceValue().equals(new BigDecimal(0))){
				faceValueFlag = true;
			}
			resultFaceValue = couponClassDef.getFaceValue().toString();
			
		}catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'faceValueFlag':" + faceValueFlag + ", 'resultFaceValue':'" + resultFaceValue + "'}");
	}
	
	public void validateCardNum(){
		String cardId = this.couponBatReg.getStartCard();
		String cardIssuer = this.couponBatReg.getCardIssuer();
		BigDecimal cardNum = this.couponBatReg.getCardNum();
		UserInfo userInfo = this.getSessionUser();
		String loginCode = RoleType.MERCHANT.getValue().equals(this.getLoginRoleType()) ? 
				userInfo.getMerchantNo() : userInfo.getBranchNo();
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		CardInfo cardTemp = null;
		
		try{
			// 校验输入卡数量
			String cardBin = cardInfo.getCardBin();
			String strNo = MatchMode.START.toMatchString(StringUtils.substring(cardId, 0, 3) + cardBin);
			
			List<CardInfo> cardList = this.cardInfoDAO.getCardListByCardBin(strNo);
			CardInfo maxCard = cardList.get(cardList.size()-1);
			cardList = this.cardInfoDAO.getCardList(cardInfo.getCardId(), maxCard.getCardId());
			Assert.isTrue(cardNum.compareTo(new BigDecimal(cardList.size()))<=0, "输入卡数量超过起始卡号所在卡BIN["+cardInfo.getCardBin()+"]的范围，请输入合法的数量。");
			cardList = cardList.subList(0, cardNum.intValue());
			
			// 遍历判断每张卡是否是登陆用户的所领卡且是领卡待售的赠券卡;
			for(int i=0; i<cardList.size(); i++){
				cardTemp = (CardInfo) this.cardInfoDAO.findByPk(cardList.get(i).getCardId());
				Assert.equals(cardTemp.getAppOrgId(), loginCode , "起始卡号到终止卡号之间不完全为该机构所领卡。");
				Assert.equals(cardTemp.getCardClass(), CardType.COUPON.getValue() , "起始卡号到终止卡号之间不完全为赠券卡。");
				Assert.equals(cardTemp.getCardStatus(), CardState.FORSALE.getValue() , "起始卡号到终止卡号之间不完全为领卡待售状态。");
				Assert.equals(cardTemp.getCardIssuer() ,cardIssuer , "起始卡号到终止卡号的发行机构不一致。");
			}
			
		}catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true  + "}");
	}
	
	private List<BranchInfo> getCardIssuerByMerch(){
		return this.getMyCardBranch();
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CouponBatRegDAO getCouponBatRegDAO() {
		return couponBatRegDAO;
	}

	public void setCouponBatRegDAO(CouponBatRegDAO couponBatRegDAO) {
		this.couponBatRegDAO = couponBatRegDAO;
	}

	public CouponBatReg getCouponBatReg() {
		return couponBatReg;
	}

	public void setCouponBatReg(CouponBatReg couponBatReg) {
		this.couponBatReg = couponBatReg;
	}

	public Long getCouponBatRegId() {
		return couponBatRegId;
	}

	public void setCouponBatRegId(Long couponBatRegId) {
		this.couponBatRegId = couponBatRegId;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public List<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(List<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

}
