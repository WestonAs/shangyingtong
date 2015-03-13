package gnete.card.web.point;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointExcRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointExcReg;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.PtExchgRuleType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointExchgService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 积分返利
 * @author aps-lib
 *
 */
public class PointExcAction extends BaseAction{
	
	@Autowired
	private PointExcRegDAO pointExcRegDAO;
	@Autowired
	private PointExchgService pointExchgService;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private PointExcReg pointExcReg;
	private PointClassDef pointClassDef;
	private PointBal pointBal;
	private CardInfo cardInfo;
	private Paginater page;
	private Long pointExcId;
	private List<PointBal> pointBalList;
	private Collection ptExchgRuleTypeList;
	private List<BranchInfo> cardBranchList;

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
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		// 发卡机构部门
		else if(RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())){
			
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if (pointExcReg != null) {
			params.put("cardId", pointExcReg.getCardId());
			params.put("ptClass", pointExcReg.getPtClass());
			params.put("className", MatchMode.ANYWHERE.toMatchString(pointExcReg.getPtClassName()));
		}
		
		this.page = this.pointExcRegDAO.findPointExcReg(params,this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	//取得积分返利记录的明细
	public String detail() throws Exception {
		
		this.pointExcReg = (PointExcReg) this.pointExcRegDAO.findByPk(this.pointExcReg.getPointExcId());
		this.pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(this.pointExcReg.getPtClass());
		this.log("查询积分返利["+this.pointExcReg.getPointExcId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	public String prepareAdd() throws Exception {
		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构不能积分返利。");
		}
		return "prepareAdd";
	}
	
	//取得输入卡号的可用积分余额记录
	public String pointBalList() throws Exception {
		Map params = new HashMap();
		this.cardInfo = (CardInfo) this.cardInfoDAO.findByPk(pointExcReg.getCardId());
		if(cardInfo==null){
			return null;
		}
		params.put("acctId", this.cardInfo.getAcctId());
		String acctId = cardInfo.getAcctId();
		AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(acctId);
		if(acctInfo==null){
			return null;
		}
		String ptClass = acctInfo.getPtClass();
		if(ptClass==null){
			return null;
		}
		params.put("ptClass", ptClass);
		params.put("jinstId", this.getSessionUser().getBranchNo());
		this.pointBalList = pointBalDAO.findPointBalAval(params);
		
		return "pointBalList";
	}
	
	//取得输入卡号的通用积分类型和可用积分余额
	public void getPtClassAndPointBal() throws Exception {
		PointBal pointBal = null;
		PointClassDef pointClassDef = null;
		
		JSONObject object = new JSONObject();
		
		try{
			this.cardInfo = (CardInfo) this.cardInfoDAO.findByPk(pointExcReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + pointExcReg.getCardId() +"]不存在,请重新输入。");
			
			Map params = new HashMap();
			
			params.put("acctId", this.cardInfo.getAcctId());
			String acctId = cardInfo.getAcctId();
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(acctId);
			Assert.notNull(acctInfo, "卡号[" + pointExcReg.getCardId() +"]关联帐户[" + acctId + "]不存在。");
			
			pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(acctInfo.getPtClass());
			Assert.notNull(pointClassDef, "卡号[" + pointExcReg.getCardId() +"]不存在相关的通用积分。");
			//this.pointExcReg.setPtClass(pointClassDef.getPtClass());
			params.put("ptClass", pointClassDef.getPtClass());
			
			params.put("jinstId", this.getSessionUser().getBranchNo());
			this.pointBalList = pointBalDAO.findPointBalAval(params);
			Assert.notEmpty(pointBalList, "卡号[" + pointExcReg.getCardId() +"]在机构[" 
					+ this.getSessionUser().getBranchNo() + "]不存在可用返利积分余额。");
			
			pointBal = pointBalList.get(0);
			
			object.put("ptClass", pointClassDef.getPtClass());
			object.put("ptClassName", pointClassDef.getClassName());
			object.put("ptRef", AmountUtil.format(pointClassDef.getPtRef()));
			object.put("ptDiscntRate", pointClassDef.getPtDiscntRate());
			object.put("ptAvlb", AmountUtil.format(pointBal.getPtAvlb()));
			object.put("ptExchgRuleTypeName", pointClassDef.getPtExchgRuleTypeName());
			object.put("success", true);
		}
		catch (Exception e) {
//			this.respond("{'success':" + false + ", 'error':'" + e.getMessage() + "'}");
//			return;
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		
		this.respond(object.toString());
		
//		String ptClass = pointClassDef.getPtClass();
//		String ptClassName = pointClassDef.getClassName();
//		BigDecimal ptRef = pointClassDef.getPtRef();
//		ptRef = ptRef.setScale(2);
//		String ptDiscntRate = pointClassDef.getPtDiscntRate().toString();
//		BigDecimal ptAvlb = pointBal.getPtAvlb();
//		ptAvlb = ptAvlb.setScale(2);
//		String ptExchgRuleTypeName = pointClassDef.getPtExchgRuleTypeName();
//
//		// Assert.notNull(expirDate, "卡号失效日期不能为空,请重新输入有效卡号。");
//		this.respond("{'success':"+ true + ", 'ptClass':'" + ptClass + 
//				"', 'ptClassName':'" + ptClassName + "', 'ptRef':'" + ptRef.toString() + 
//				"', 'ptDiscntRate':'" + ptDiscntRate + "', 'ptAvlb':'" + ptAvlb.toString() + 
//				"', 'ptExchgRuleTypeName':'" + ptExchgRuleTypeName + "'}");
		
	}
	
	// 打开积分返利的初始化操作
	public String showAdd() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机构不能积分返利。");
		}
		
		return ADD;
	}	
	
	// 积分返利登记
	public String add() throws Exception {	
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机构不能积分返利。");
		}
		else {
			pointExcReg.setBranchCode(this.getSessionUser().getBranchNo());
		}
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(pointExcReg.getCardId());
		Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
		
		this.pointExcReg.setAcctId(cardInfo.getAcctId());
		
		String ptClass = this.pointExcReg.getPtClass();
		PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(ptClass);
		pointExcReg.setJinstType(pointClassDef.getJinstType());
		pointExcReg.setJinstId(pointClassDef.getJinstId());
		
		//this.pointExcReg.setPtClass(ptClass);
		pointExchgService.addPointExcReg(pointExcReg, this.getSessionUserCode());
		String msg = "登记积分返利["+this.pointExcReg.getPointExcId()+"]申请成功！";

		this.addActionMessage("/pointExchg/pointExc/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 删除积分返利登记
	public String delete() throws Exception {
		
		this.pointExchgService.deletePointExc(pointExcId);
		String msg = "删除积分返利[" +this.getPointExcId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pointExchg/pointExc/list.do", msg);
		return SUCCESS;
	}
	
	// 根据卡返利积分输入返利金额
	public void getExcAmt() throws Exception {
		PointClassDef pointClassDef = null;
		BigDecimal excAmt = null;
		
		try {
			String ptClass = this.pointBal.getPtClass();
			pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(ptClass);
			Assert.notNull(pointClassDef, "积分类型不存在。");
			
			BigDecimal excPoint = this.pointExcReg.getExcPoint();
			BigDecimal ptDiscntRate = pointClassDef.getPtDiscntRate();
			BigDecimal ptAvlb = pointBal.getPtAvlb();
			
			Assert.isTrue(ptAvlb.compareTo(excPoint)>=0, "返利积分不能大于可用积分，请重新输入有效的返利积分值。");
			
			String ptExchgRuleType = pointClassDef.getPtExchgRuleType();
			BigDecimal ptRef = pointClassDef.getPtRef();
			if(PtExchgRuleType.FULLEXCHANGE.getValue().equals(ptExchgRuleType)){
				Assert.isTrue(excPoint.compareTo(ptRef)>=0, "返利积分不能小于参考积分，请输入有效的返利积分值。");
			}
			else if(PtExchgRuleType.TIMESEXCHANGE.getValue().equals(ptExchgRuleType)){
				Assert.isTrue(excPoint.compareTo(ptRef)>=0, "返利积分不能小于参考积分，请输入有效的返利积分值。");
				Assert.isTrue(excPoint.longValue()%ptRef.longValue()==0, "返利积分需要是参考积分的整数倍，请输入有效的返利积分。");
			}
			
			excAmt = excPoint.multiply(ptDiscntRate);
			excAmt = excAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
			
		}catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true +", 'excAmt':'" + excAmt.toString() + "'}");
	}
	
	public void validateCardId(){
		String cardId = this.pointExcReg.getCardId();
		
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "该卡号不存在，请重新输入。");
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "该卡号没有账户，不能返利。");
			Assert.notNull(acctInfo.getPtClass(), "不存在通用积分，不能返利。");
			Assert.equals(cardInfo.getCardIssuer(), this.getSessionUser().getBranchNo(), "该发卡机构不是该卡的发行机构，不能返利。");
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}

	public PointExcReg getPointExcReg() {
		return pointExcReg;
	}

	public void setPointExcReg(PointExcReg pointExcReg) {
		this.pointExcReg = pointExcReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Long getPointExcId() {
		return pointExcId;
	}

	public void setPointExcId(Long pointExcId) {
		this.pointExcId = pointExcId;
	}

	public void setPointBal(PointBal pointBal) {
		this.pointBal = pointBal;
	}

	public PointBal getPointBal() {
		return pointBal;
	}

	public void setPointClassDef(PointClassDef pointClassDef) {
		this.pointClassDef = pointClassDef;
	}

	public PointClassDef getPointClassDef() {
		return pointClassDef;
	}

	public void setPtExchgRuleTypeList(Collection ptExchgRuleTypeList) {
		this.ptExchgRuleTypeList = ptExchgRuleTypeList;
	}

	public Collection getPtExchgRuleTypeList() {
		return ptExchgRuleTypeList;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<PointBal> getPointBalList() {
		return pointBalList;
	}

	public void setPointBalList(List<PointBal> pointBalList) {
		this.pointBalList = pointBalList;
	}
}
