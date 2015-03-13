package gnete.card.web.point;

import flink.etc.MatchMode;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.GiftDefDAO;
import gnete.card.dao.GiftExcRegDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.GiftDef;
import gnete.card.entity.GiftExcReg;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.PtExchgType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointExchgService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 积分兑换礼品
 * @author aps-lib
 *
 */
public class PointExcGiftAction extends BaseAction {

	@Autowired
	private GiftExcRegDAO giftExcRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private GiftDefDAO giftDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointExchgService pointExchgService;

	private GiftExcReg giftExcReg;
	private Paginater page;
	private Collection ptClassTypeList;
	private List<PointClassDef> pointClassDefList;
	private List<GiftDef> giftDefList;
	private Long giftExcId;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;

	@Override
	public String execute() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if (roleInfo.getRoleType().equals(RoleType.FENZHI.getValue())) {
			//params.put("cardIssuers", this.getMyCardBranch());
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if(CollectionUtils.isEmpty(branchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("branchCode", " ");
			}
		}
		// 发卡机构、机构网点
		else if (roleInfo.getRoleType().equals(RoleType.CARD.getValue())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", roleInfo.getBranchNo());
		} 
		// 商户
		else if (roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())) {
			params.put("branchCode", roleInfo.getMerchantNo());
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = branchList.size()+merchInfoList.size();
			String[] jinstIds = new String[length];
			int i = 0;
			for( ; i<branchList.size(); i++){
				jinstIds[i] = (branchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				jinstIds[i] = (merchInfoList.get(i-branchList.size())).getMerchId();
			}
			params.put("branchCodes", jinstIds);
		}

		// 加载积分兑换类型列表
		this.ptClassTypeList = PtExchgType.ALL.values();

		if (giftExcReg != null) {
			params.put("cardId", giftExcReg.getCardId());
			params.put("acctId", giftExcReg.getAcctId());
			params.put("giftId", giftExcReg.getGiftId());
			params.put("giftName", MatchMode.ANYWHERE.toMatchString(giftExcReg.getGiftName()));
		}

		this.page = this.giftExcRegDAO.findGiftExcReg(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 取得积分兑换礼品记录的明细
	public String detail() throws Exception {
		this.giftExcReg = (GiftExcReg) this.giftExcRegDAO
				.findByPk(this.giftExcReg.getGiftExcId());
		this.log("查询积分兑换礼品[" + this.giftExcReg.getGiftExcId() + "]明细信息成功",
				UserLogType.SEARCH);
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if (!roleInfo.getRoleType().equals(RoleType.CARD.getValue())
				& !roleInfo.getRoleType().equals(RoleType.CARD_DEPT.getValue())
				& !roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())) {
			throw new BizException("非发卡机构、机构网点或者商户不能操作。");
		}
		initPage();
		return ADD;
	}

	// 新增积分兑换礼品登记
	public String add() throws Exception {

		RoleInfo roleInfo = this.getSessionUser().getRole();
		UserInfo userInfo = this.getSessionUser();

		if (roleInfo.getRoleType().equals(RoleType.CARD.getValue())
				|| roleInfo.getRoleType().equals(RoleType.CARD_SELL.getValue())) {
			giftExcReg.setBranchCode(userInfo.getBranchNo());
		} else if (roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())) {
			giftExcReg.setBranchCode(userInfo.getMerchantNo());
		}

		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO
				.findByPk(giftExcReg.getPtClass());
		giftExcReg.setJinstType(pointClassDef.getJinstType());
		giftExcReg.setJinstId(pointClassDef.getJinstId());

		String cardId = giftExcReg.getCardId();
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		String acctId = cardInfo.getAcctId();
		giftExcReg.setAcctId(acctId);
		GiftDef giftDef = (GiftDef) giftDefDAO.findByPk(this.giftExcReg
				.getGiftId());
		String giftName = giftDef.getGiftName();
		BigDecimal ptValue = giftDef.getPtValue();
		giftExcReg.setGiftName(giftName);
		giftExcReg.setPtValue(ptValue);

		this.pointExchgService.addGiftExcReg(giftExcReg, this
				.getSessionUserCode());
		String msg = "登记积分兑换礼品[" + this.giftExcReg.getGiftExcId() + "]申请成功！";

		this.addActionMessage("/pointExchg/pointExcGift/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	// 查询输入卡号可用的积分类型列表，服务端查询，返回到前端
	public String getPtClassList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String cardId = this.giftExcReg.getCardId();
		String ptClassTag = "ptClassTag";
		this.request.setAttribute(ptClassTag, "");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			return null;
		}
		RoleInfo roleInfo = this.getSessionUser().getRole();

		String jinstId = RoleType.MERCHANT.getValue().equals(this.getLoginRoleType()) ? 
				roleInfo.getMerchantNo() : roleInfo.getBranchNo();
		params.put("acctId", cardInfo.getAcctId());
		params.put("jinstId", jinstId);
		this.pointClassDefList = this.pointClassDefDAO
				.getPtClassByCardOrMerch(params);

		return "pointClassDefList";
	}

	// 校验输入卡号
	public void validateCardId() {
		String cardId = this.giftExcReg.getCardId();
		Map<String, Object> params = new HashMap<String, Object>();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		try {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "输入卡号不存在，请重新输入。");
			String jinstId = "";

			// 商户
			if (RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())) {
				Assert.isTrue(merchBelongCard(roleInfo, cardInfo.getCardIssuer()), "该商户不是输入卡号发卡机构的特约商户。");
				jinstId = roleInfo.getMerchantNo();
			}
			// 发卡机构、机构网点
			else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())||
					RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
				Assert.isTrue(roleInfo.getBranchNo().equals(cardInfo.getCardIssuer()), "该机构不是输入卡号的发卡机构。");
				jinstId = roleInfo.getBranchNo();
			}
			// 售卡代理
			else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
				boolean flag = isCardSellPrivilege(cardInfo, this.getSessionUser(), Constants.POINT_EXC_GIFT_PRIVILEGE_CODE);
				Assert.isTrue(flag, "该售卡代理没有权限为该卡积分兑换礼品。");
			}

			Assert.notNull(cardInfo.getAcctId(), "该卡没有可用账户。");
			params.put("acctId", cardInfo.getAcctId());
			params.put("jinstId", jinstId);
			this.pointClassDefList = this.pointClassDefDAO.getPtClassByCardOrMerch(params);

			Assert.notEmpty(this.pointClassDefList, "该卡没有可用积分类型。");

		} catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':" + true + "}");
	}

	/* 
	 * 检查输入卡号对应账户的可用积分是否大于等于礼品所需兑换积分
	 * 根据卡号、积分类型找到该卡该积分的可用积分
	 */
	public void validatePtClass() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String cardId = this.giftExcReg.getCardId();
		String ptClass = this.giftExcReg.getPtClass();
		String resultPtAvlb = "";

		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡信息不存在。");
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "账户不存在。");
			
			PointBalKey pointBalKey = new PointBalKey();
			pointBalKey.setAcctId(cardInfo.getAcctId());
			pointBalKey.setPtClass(ptClass);
			PointBal pointBal = (PointBal) pointBalDAO.findByPk(pointBalKey);
			Assert.notNull(pointBal, "积分账户不存在。");

			BigDecimal ptAvlb = pointBal.getPtAvlb();
			if (ptAvlb == null) {
				ptAvlb = new BigDecimal(0);
			}

			params.put("ptClass", ptClass);
			params.put("ptValue", ptAvlb);
			params.put("status", RegisterState.PASSED.getValue());
			String curTime = DateUtil.getCurrentDate();
			params.put("curTime", curTime);
			this.giftDefList = giftDefDAO.findGiftByPtClass(params);

			Assert.notEmpty(giftDefList, "该积分类型没有可兑换的礼品。");
			
			// 该卡该积分的可用积分
			resultPtAvlb = pointBal.getPtAvlb() == null ? "0" : pointBal.getPtAvlb().toString();

		} catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true + ", 'resultPtAvlb':"+ resultPtAvlb + "}" );
	}

	// 判断商户是否属于该卡所属发卡机构
	private boolean merchBelongCard(RoleInfo roleInfo, String cardIssuer) {
		String merchNo = roleInfo.getMerchantNo();
		CardToMerchKey cardToMerchKey = new CardToMerchKey();
		cardToMerchKey.setBranchCode(cardIssuer);
		cardToMerchKey.setMerchId(merchNo);
		return cardToMerchDAO.findByPk(cardToMerchKey) != null;
	}

	// 根据积分类型查找满足条件的礼品列表，服务端查询，返回到前端
	public String getGiftList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String cardId = this.giftExcReg.getCardId();
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			return null;
		}
		String acctId = cardInfo.getAcctId();
		String ptClass = this.giftExcReg.getPtClass();
		PointBalKey pointBalKey = new PointBalKey();
		pointBalKey.setAcctId(acctId);
		pointBalKey.setPtClass(ptClass);

		PointBal pointBal = (PointBal) pointBalDAO.findByPk(pointBalKey);
		if (pointBal == null) {
			return null;
		}
		// Assert.notNull(pointBal, "积分帐户余额为空，不能兑换礼品");
		BigDecimal ptAvlb = pointBal.getPtAvlb();
		if (ptAvlb == null) {
			ptAvlb = new BigDecimal(0);
		}

		params.put("ptClass", ptClass);
		params.put("ptValue", ptAvlb);
		String curTime = DateUtil.getCurrentDate();
		// curTime = "20121119";
		params.put("status", RegisterState.PASSED.getValue());
		params.put("curTime", curTime);
		this.giftDefList = giftDefDAO.findGiftByPtClass(params);

		return "giftDefList";
	}

	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if (!roleInfo.getRoleType().equals(RoleType.CARD.getValue())
				& !roleInfo.getRoleType().equals(RoleType.CARD_DEPT.getValue())
				& !roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())) {
			throw new BizException("非发卡机构、机构网点或者商户不能操作。");
		}
		initPage();
		this.giftExcReg = (GiftExcReg) this.giftExcRegDAO
				.findByPk(this.giftExcReg.getGiftExcId());

		return MODIFY;
	}

	// 修改积分兑换礼品登记
	public String modify() throws Exception {

		this.pointExchgService.modifyGiftExcReg(giftExcReg, this
				.getSessionUserCode());
		this.addActionMessage("/pointExchg/pointExcGift/list.do",
				"修改积分兑换礼品登记成功！");
		return SUCCESS;
	}

	// 删除积分兑换礼品登记
	public String delete() throws Exception {

		this.pointExchgService.deleteGiftExcReg(this.getGiftExcId());
		String msg = "删除积分兑换礼品[" + this.getGiftExcId() + "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pointExchg/pointExcGift/list.do", msg);
		return SUCCESS;
	}

	// 根据礼品代码找到兑换积分
	public void getPtValue() throws Exception {
		String resultPtValue = "";
		String giftId = this.giftExcReg.getGiftId();
		GiftDef giftDef = (GiftDef) giftDefDAO.findByPk(giftId);
		
		try {
			if (giftDef != null) {
				BigDecimal ptValue = giftDef.getPtValue();
				
				if (ptValue != null) {
					resultPtValue = ptValue.toString();
				}
			}
			
		} catch (Exception e) {
			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true + ", 'resultPtValue':"+ resultPtValue + "}" );
	}

	private void initPage() {

	}

	public GiftExcReg getGiftExcReg() {
		return giftExcReg;
	}

	public void setGiftExcReg(GiftExcReg giftExcReg) {
		this.giftExcReg = giftExcReg;
	}

	public void setPtClassTypeList(Collection ptClassTypeList) {
		this.ptClassTypeList = ptClassTypeList;
	}

	public Collection getPtClassTypeList() {
		return ptClassTypeList;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setGiftExcId(Long giftExcId) {
		this.giftExcId = giftExcId;
	}

	public Long getGiftExcId() {
		return giftExcId;
	}

	public void setPointClassDefList(List<PointClassDef> pointClassDefList) {
		this.pointClassDefList = pointClassDefList;
	}

	public List<PointClassDef> getPointClassDefList() {
		return pointClassDefList;
	}

	public void setGiftDefList(List<GiftDef> giftDefList) {
		this.giftDefList = giftDefList;
	}

	public List<GiftDef> getGiftDefList() {
		return giftDefList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

}
