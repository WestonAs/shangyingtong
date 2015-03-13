package gnete.card.web.vipcard;

import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.PointBal;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.MembUpgradeMthdType;
import gnete.card.entity.type.RenewType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RenewCardRegService;
import gnete.card.service.VipCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * VIP升级换卡
 * @author aps-lib
 *
 */
public class MembUpgradeAction extends BaseAction{
	
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private RenewCardRegDAO renewCardRegDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private VipCardService vipCardService;
	@Autowired
	private RenewCardRegService renewCardRegService;

	private CardInfo cardInfo;
	private AcctInfo acctInfo;
	private MembClassDef membClassDef;
	private RenewCardReg renewCardReg;
	private PointBal pointBal;
	private Paginater page;
	private List<RenewType> upgradeCardTypeList;
	private Collection certTypeList;
	private Long renewCardId;
	
	@Override
	public String execute() throws Exception {
		this.upgradeCardTypeList = RenewType.getUpgradeCard();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(renewCardReg != null){
			params.put("newCardId", renewCardReg.getNewCardId());
			params.put("cardId", renewCardReg.getCardId());
			params.put("custName", renewCardReg.getCustName());
			params.put("acctId", renewCardReg.getAcctId());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
		}
		// 运营分支机构
		else if (RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("cardIssuers", this.getMyCardBranch());
		}
		// 售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(getLoginRoleType())) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		// 挂失换卡和损坏换卡类型
		params.put("types", upgradeCardTypeList);
		
		this.page = this.renewCardRegDAO.findRenewCard(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}
	

	//取得会员卡的升级明细
	public String detail() throws Exception {
		this.renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(this.renewCardReg.getRenewCardId());
//		this.log("查询会员["+this.renewCardReg.getRenewCardId()+"]升级明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())&
				!RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
		initPage();
		return ADD;
	}

	// 新增信息
	public String add() throws Exception {			
		
		//保存数据
		this.renewCardRegService.addUpgradeRecord(renewCardReg, this.getSessionUser());

		String msg = "换卡登记成功！新卡号为[" + this.renewCardReg.getNewCardId()
				+ "]，旧卡号为[" + this.renewCardReg.getCardId() + "]";
		this.addActionMessage("/vipCard/vipUpgrade/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	//判断积分是否满足升级要求
	public void isMeetUpgradeRule() throws Exception{
		//获取页面的cardId
		String cardId = this.renewCardReg.getCardId();
		boolean isMeet = this.vipCardService.isMeetUpgradeRule(cardId);
		this.respond("{'isMeet':"+ isMeet +"}");
	}
	
	// 删除会员升级换卡资料
	public String delete() throws Exception {
		this.vipCardService.deleteUpgradeRecord(this.renewCardId);
		
		String msg = "删除会员换卡记录[" +this.renewCardId+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/vipCard/vipUpgrade/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();
	}
	
	/** 校验输入旧卡号的正确性 */
	public void cardIdCheck() throws Exception {
		CardInfo cardInfo = null;
		
		JSONObject object = new JSONObject();
		
		String oldCardLevel = "";
		String membLevel = "";
		String upgradeMthd = "";
		String upgradeThrhd = "";
		String className = "";
		String membClassName = "";
		try {
			String cardId = this.renewCardReg.getCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
			Assert.equals(cardInfo.getCardClass(), CardType.MEMB.getValue(), "该卡不是会员卡，不能升级换卡。");
			
			Assert.isTrue(CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus()), 
					"原卡[" + cardId + "]目前不是正常(已激活)状态，不能换卡。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleType(),  this.getLoginBranchCode() , cardInfo, "vip会员资料注册");
			
			// 取得旧卡的会员级别
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef, "原会员卡不存在会员级别，不能换卡。");
			oldCardLevel = cardSubClassDef.getMembLevel();
			MembClassDef membClassDef = (MembClassDef) this.membClassDefDAO.findByPk(cardSubClassDef.getMembClass());
			Assert.notNull(membClassDef, "原会员卡的会员子类型不存在，不能换卡。");
			className = membClassDef.getClassName();
			membLevel = membClassDef.getMembLevel();
			upgradeMthd = MembUpgradeMthdType.valueOf(membClassDef.getMembUpgradeMthd()).getName();
			upgradeThrhd = upgradeMthd.equals("0") ? "" : membClassDef.getMembUpgradeThrhd();
			membClassName = membClassDef.getMembClassName();
			
			// 取得旧会员卡对应会员级别的级别名称
			String[] membClassNameList = null;
			if(membClassName.split(",")!=null){
				membClassNameList = membClassName.split(",");
			} else {
				membClassNameList = new String[1];
				membClassNameList[0] = membClassName;
			}
			String ClassLevel = (new BigDecimal(oldCardLevel).subtract(new BigDecimal(1))).toString();
			if(Integer.valueOf(ClassLevel) < 0){
				ClassLevel = "0";
			}
			oldCardLevel = membClassNameList[new BigDecimal(ClassLevel).intValue()];
			
			object.put("oldCardLevel", oldCardLevel);
			object.put("className", className);
			object.put("membLevel", membLevel);
			object.put("upgradeMthd", upgradeMthd);
			object.put("upgradeThrhd", upgradeThrhd);
			object.put("membClassName", membClassName);
			object.put("success", true);
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
//		this.respond("{'success':"+ true + ", 'oldCardLevel':'" + oldCardLevel + 
//				"', 'className':'" + className + "', 'membLevel':'" + membLevel + 
//				"', 'upgradeMthd':'" + upgradeMthd + "', 'upgradeThrhd':'" + upgradeThrhd + 
//				"', 'membClassName':'" + membClassName + "'}");
		this.respond(object.toString());
	}
	
	/** 校验输入新卡号的正确性 */
	public void newCardIdCheck() throws Exception {
		CardInfo cardInfo = null;
		String newCardLevel = "";
		try {
			String cardId = this.renewCardReg.getNewCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在，请重新输入。");
			CardInfo oldCardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.renewCardReg.getCardId());
			Assert.equals(cardInfo.getCardClass(), oldCardInfo.getCardClass(), "旧卡（"+CardType.valueOf(oldCardInfo.getCardClass()).getName()+
						"）和新卡（"+CardType.valueOf(cardInfo.getCardClass()).getName()+"）卡种类不同，请输入会员卡卡号。");
			Assert.equals(cardInfo.getCardStatus(), CardState.FORSALE.getValue(), "新卡不是领卡待售状态，请选择请他新卡。");
			
			UserInfo user = this.getSessionUser();
			String branchCode = user.getBranchNo();
			if(RoleType.CARD_DEPT.getValue().equals(user.getRole().getRoleType())){
				branchCode = user.getDeptId();
			}
			Assert.equals(cardInfo.getAppOrgId(), branchCode, "该机构不是新卡的领卡机构，请输入正确的新卡号。");
			
			// 取得新卡的会员级别
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef, "新会员卡不存在会员级别，不能换卡。");
			newCardLevel = cardSubClassDef.getMembLevel();
			MembClassDef membClassDef = (MembClassDef) this.membClassDefDAO.findByPk(cardSubClassDef.getMembClass());
			String membClassName = membClassDef.getMembClassName();
			
			// 取得新会员卡对应会员级别的级别名称
			String[] membClassNameList = null;
			if(membClassName.split(",")!=null){
				membClassNameList = membClassName.split(",");
			} else {
				membClassNameList = new String[1];
				membClassNameList[0] = membClassName;
			}
			String ClassLevel = (new BigDecimal(newCardLevel).subtract(new BigDecimal(1))).toString();
			if(Integer.valueOf(ClassLevel) < 0){
				ClassLevel = "0";
			}
			newCardLevel = membClassNameList[new BigDecimal(ClassLevel).intValue()];
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + ", 'newCardLevel':'" + newCardLevel + "'}");
	}
	
	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setCardInfoDAO(CardInfoDAO cardInfoDAO) {
		this.cardInfoDAO = cardInfoDAO;
	}

	public CardInfoDAO getCardInfoDAO() {
		return cardInfoDAO;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}


	public void setAcctInfoDAO(AcctInfoDAO acctInfoDAO) {
		this.acctInfoDAO = acctInfoDAO;
	}


	public AcctInfoDAO getAcctInfoDAO() {
		return acctInfoDAO;
	}


	public void setAcctInfo(AcctInfo acctInfo) {
		this.acctInfo = acctInfo;
	}


	public AcctInfo getAcctInfo() {
		return acctInfo;
	}


	public void setMembClassDefDAO(MembClassDefDAO membClassDefDAO) {
		this.membClassDefDAO = membClassDefDAO;
	}


	public MembClassDefDAO getMembClassDefDAO() {
		return membClassDefDAO;
	}

	public MembClassDef getMembClassDef() {
		return membClassDef;
	}

	public void setMembClassDef(MembClassDef membClassDef) {
		this.membClassDef = membClassDef;
	}


	public void setPointBalDAO(PointBalDAO pointBalDAO) {
		this.pointBalDAO = pointBalDAO;
	}


	public PointBalDAO getPointBalDAO() {
		return pointBalDAO;
	}


	public void setPointBal(PointBal pointBal) {
		this.pointBal = pointBal;
	}


	public PointBal getPointBal() {
		return pointBal;
	}


	public void setUpgradeCardTypeList(List upgradeCardTypeList) {
		this.upgradeCardTypeList = upgradeCardTypeList;
	}


	public List getUpgradeCardTypeList() {
		return upgradeCardTypeList;
	}


	public RenewCardReg getRenewCardReg() {
		return renewCardReg;
	}


	public void setRenewCardReg(RenewCardReg renewCardReg) {
		this.renewCardReg = renewCardReg;
	}


	public RenewCardRegDAO getRenewCardRegDAO() {
		return renewCardRegDAO;
	}


	public void setRenewCardRegDAO(RenewCardRegDAO renewCardRegDAO) {
		this.renewCardRegDAO = renewCardRegDAO;
	}


	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}


	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setRenewCardId(Long renewCardId) {
		this.renewCardId = renewCardId;
	}


	public Long getRenewCardId() {
		return renewCardId;
	}

}
