package gnete.card.web.point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointConsmRuleDefDAO;
import gnete.card.dao.PointExcCouponRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointConsmRuleDef;
import gnete.card.entity.PointExcCouponReg;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointExchgService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

/**
 * 积分兑换赠券
 * @author aps-lib
 *
 */
public class PointExcCouponAction extends BaseAction{

	@Autowired
	private PointExcCouponRegDAO pointExcCouponRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private PointConsmRuleDefDAO pointConsmRuleDefDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private PointExchgService pointExchgService;
	
	private PointExcCouponReg pointExcCouponReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<PointBal> pointBalList;
	private List<CouponClassDef> couponList;
	private Long pointExcCouponRegId;
	private List<PointConsmRuleDef> pointConsmRuleList;
	private PointBal pointBal;
	private PointConsmRuleDef pointConsmRuleDef;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		this.cardBranchList = new ArrayList<BranchInfo>();
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if(CollectionUtils.isEmpty(cardBranchList)){
				params.put("branchCode", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if(this.pointExcCouponReg!=null){
			params.put("cardId", this.pointExcCouponReg.getCardId());
		}
		this.page = this.pointExcCouponRegDAO.findPointExcCouponReg(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	//取得积分兑换赠券记录的明细
	public String detail() throws Exception {
		this.pointExcCouponReg = (PointExcCouponReg) this.pointExcCouponRegDAO.findByPk(this.pointExcCouponReg.getPointExcCouponRegId());
		this.log("查询积分兑换赠券["+this.pointExcCouponReg.getPointExcCouponRegId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	/*public String prepareAdd() throws Exception {
		
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非商户不能操作。");
		}
		return "prepareAdd";
	}*/
	
	public void validateCardId(){
		String cardId = this.pointExcCouponReg.getCardId();
		String resultAcctId = "";
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "该卡号不存在，请重新输入。");
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "该卡号没有账户，不能兑换赠券。");
			//判断商户是该卡发卡机构的特约商户
			CardToMerchKey key = new CardToMerchKey();
			key.setBranchCode(cardInfo.getCardIssuer());
			key.setMerchId(this.getSessionUser().getMerchantNo());
			CardToMerch cardToMerch = (CardToMerch) this.cardToMerchDAO.findByPk(key);
			Assert.notNull(cardToMerch, "该商户不是该卡发卡机构的特约商户。");
			resultAcctId = cardInfo.getAcctId();
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'resultAcctId':'" + resultAcctId + "'}");
	}
	
	//查询卡号在该商户下的可用积分类型
	public String getPointBalAvalList(){
		Map<String, Object> params = new HashMap<String, Object>();
		String cardId = this.pointExcCouponReg.getCardId();
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		if(cardInfo==null){
			return null;
		}
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		String jinstId = RoleType.MERCHANT.getValue().equals(this.getLoginRoleType()) ? 
				roleInfo.getMerchantNo() : roleInfo.getBranchNo();
		params.put("acctId", cardInfo.getAcctId());
		params.put("jinstId", jinstId);
		this.pointBalList = this.pointBalDAO.getPointBalList(params);
		
		return "pointBalList";
	}
	
	//查询商户定义的赠券
	public String getCouponAvalList(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jinstId", this.getSessionUser().getMerchantNo());
		this.couponList = this.couponClassDefDAO.findCouponClassByJinst(params);
		return "couponList";
	}
	
	//查询可用积分值
	public void getPtAvlb(){
		String acctId = this.pointExcCouponReg.getAcctId();
		String ptClass = this.pointExcCouponReg.getPtClass();
		String ptAvlb = "";
		try{
			PointBalKey key = new PointBalKey();
			key.setAcctId(acctId);
			key.setPtClass(ptClass);
			PointBal pointBal = (PointBal) this.pointBalDAO.findByPk(key);
			Assert.notNull(pointBal, "该账户没有积分账户余额。");
			ptAvlb = pointBal.getPtAvlb().toString();
		}
		catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'ptAvlb':'" + ptAvlb + "'}");
	}
	
	// 检查输入的可兑换积分，提示实际使用的兑换积分，输出兑换的赠券金额
	public void getCouponAmt(){
		BigDecimal ptAvlb = this.pointBal.getPtAvlb(); // 可用积分
		BigDecimal ptValue = this.pointExcCouponReg.getPtValue(); // 兑换积分
		Map params = new HashMap();
		BigDecimal couponAmt = new BigDecimal(0);
		
		try{
			Assert.isTrue(ptAvlb.compareTo(ptValue)>=0, "可用积分要大于等于兑换积分["+ ptValue.toString() +"]，请输入有效的兑换积分。");
			String ptClass = this.pointExcCouponReg.getPtClass();
			String couponClass = this.pointExcCouponReg.getCouponClass();
			params.put("ptClass", ptClass);
			params.put("couponClass", couponClass);
			params.put("ptValue", ptValue);
			List<PointConsmRuleDef> ruleList = this.pointConsmRuleDefDAO.getPointConsmRuleByClass(params);
			Assert.notEmpty(ruleList, 
					"没有适用于兑换积分为["+ ptValue.toString()+ "]的积分兑换赠券规则。");
			
			// 把ruleList按积分参数从大到小排序
			ruleList = BubbleSort(ruleList);
			
			PointConsmRuleDef ptConsmRuleDef = null;
			BigDecimal pointParam = null; 
			BigDecimal couponParam = null;
			
			// 遍历规则列表，根据输入兑换积分计算兑换赠券
			for(int i=0; i<ruleList.size(); i++){
				ptConsmRuleDef = ruleList.get(i);
				pointParam = ptConsmRuleDef.getPtParam();
				couponParam = ptConsmRuleDef.getRuleParam1();
				
				if(ptValue.compareTo(pointParam)>=0){
					BigDecimal times = ptValue.divideToIntegralValue(pointParam);
					couponAmt = couponAmt.add(couponParam.multiply(times));
					ptValue = ptValue.subtract(pointParam.multiply(times));
				}
				
				if(ptValue.compareTo(new BigDecimal(0))==0){
					break;
				}
			}
			//Assert.isTrue(ptValue.compareTo(new BigDecimal(0))==0, "请输入正确的兑换积分,请参考所列积分兑换赠券的兑换积分。");
		}
		catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'couponAmt':'" + couponAmt.toString() + 
				"', 'ptValue':'" + ptValue.toString() +"'}");
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		if(!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非商户不能操作。");
		}
		initPage();
		return ADD;
	}	
	
	// 新增积分兑换礼品登记
	public String add() throws Exception {		
		PointClassDef pointClassDef = (PointClassDef)this.pointClassDefDAO.findByPk(this.pointExcCouponReg.getPtClass());
		String ptClassName = pointClassDef.getClassName()!=null ? pointClassDef.getClassName():"";
		this.pointExcCouponReg.setPtName(ptClassName);
		
		CouponClassDef couponClassDef = (CouponClassDef)this.couponClassDefDAO.findByPk(this.pointExcCouponReg.getCouponClass());
		String couponClassName = couponClassDef.getClassName()!=null ? couponClassDef.getClassName():"";
		this.pointExcCouponReg.setCouponName(couponClassName);
		
		this.pointExcCouponReg.setBranchCode(this.getSessionUser().getMerchantNo());
		
		this.pointExchgService.addPointExcCouponReg(pointExcCouponReg, this.getSessionUserCode());
		String msg = "登记积分兑换赠券["+this.pointExcCouponReg.getPointExcCouponRegId()+"]申请成功！";

		this.addActionMessage("/pointExchg/pointExcCoupon/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		
		if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非者商户不能操作。");
		}
		initPage();
		this.pointExcCouponReg = (PointExcCouponReg)this.pointExcCouponRegDAO.findByPk(this.pointExcCouponReg.getPointExcCouponRegId());
			
		return MODIFY;
	}
	
	// 修改积分兑换赠券登记
	public String modify() throws Exception {
		
		this.pointExchgService.modifyPointExcCouponReg(this.pointExcCouponReg, this.getSessionUserCode());
		this.addActionMessage("/pointExchg/pointExcCoupon/list.do", "修改积分兑换赠券登记成功！");	
		return SUCCESS;
	}
	
	// 删除积分兑换赠券登记
	public String delete() throws Exception {
		
		this.pointExchgService.deletePointExcCouponReg(this.getPointExcCouponRegId());
		String msg = "删除积分兑换赠券[" +this.getPointExcCouponRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pointExchg/pointExcCoupon/list.do", msg);
		return SUCCESS;
	}
	
	//取得所选积分和赠券的积分兑换赠券规则列表
	public String getPtConsmRuleList() throws Exception {
		Map params = new HashMap();
		params.put("ptClass", this.pointExcCouponReg.getPtClass());
		params.put("couponClass", this.pointExcCouponReg.getCouponClass());
		this.pointConsmRuleList = this.pointConsmRuleDefDAO.getPointConsmRuleByClass(params);
		this.pointConsmRuleList = BubbleSort(this.pointConsmRuleList);
		return "pointConsmRuleList";
	}
	
	// 冒泡排序
	private List<PointConsmRuleDef> BubbleSort(List<PointConsmRuleDef> list){ 
		int i;
		int j;
		int n = list.size();
		boolean exchange; //交换标志
		PointConsmRuleDef temp = null;
		
		for(i=1; i<n; i++){ 
			exchange = false; 
			for(j = n-1; j>=i; j--){ 
				if(list.get(j).getPtParam().compareTo(list.get(j-1).getPtParam())>0){ //交换记录
					temp = list.get(j);
					list.set(j, list.get(j-1));
					list.set(j-1, temp);
					exchange = true; //发生了交换,将交换标志置为真
				}
			}
			if(!exchange){ //本趟排序未发生交换，提前终止算法
				return list;
			}
		}
		return list;
	}
	
	private void initPage(){
		
	}

	public PointExcCouponReg getPointExcCouponReg() {
		return pointExcCouponReg;
	}

	public void setPointExcCouponReg(PointExcCouponReg pointExcCouponReg) {
		this.pointExcCouponReg = pointExcCouponReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public Long getPointExcCouponRegId() {
		return pointExcCouponRegId;
	}

	public void setPointExcCouponRegId(Long pointExcCouponRegId) {
		this.pointExcCouponRegId = pointExcCouponRegId;
	}

	public List<PointBal> getPointBalList() {
		return pointBalList;
	}

	public void setPointBalList(List<PointBal> pointBalList) {
		this.pointBalList = pointBalList;
	}

	public PointBal getPointBal() {
		return pointBal;
	}

	public void setPointBal(PointBal pointBal) {
		this.pointBal = pointBal;
	}

	public PointConsmRuleDef getPointConsmRuleDef() {
		return pointConsmRuleDef;
	}

	public void setPointConsmRuleDef(PointConsmRuleDef pointConsmRuleDef) {
		this.pointConsmRuleDef = pointConsmRuleDef;
	}

	public List<CouponClassDef> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponClassDef> couponList) {
		this.couponList = couponList;
	}

	public List<PointConsmRuleDef> getPointConsmRuleList() {
		return pointConsmRuleList;
	}

	public void setPointConsmRuleList(List<PointConsmRuleDef> pointConsmRuleList) {
		this.pointConsmRuleList = pointConsmRuleList;
	}

}
