package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.DepositRegDAO;
import gnete.card.dao.SaleSignCardRegDAO;
import gnete.card.dao.SignCustDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.DepositReg;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.DepositFromPageType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.DepositService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SignDepositRegAction.java
 * 
 * @description: 签单卡充值
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: BenYan
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-10-28
 */
public class SignDepositRegAction extends BaseAction {

	@Autowired
	private DepositRegDAO depositRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private SaleSignCardRegDAO saleSignCardRegDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private DepositService depositService;

	private DepositReg depositReg;
	private CardTypeCode cardTypeCode;
	private CardInfo cardInfo;
	private SignCust signCust;
	private CardBin cardBin;

	private List statusList;
	private Paginater page;

	/*
	 * 默认列表显示
	 */
	@Override
	public String execute() throws Exception {
		statusList = CommonState.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("branchList", this.getMyDepositList());
		
		if (depositReg != null) {
			params.put("depositBatchId", depositReg.getDepositBatchId());
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(depositReg.getCardId()));
			params.put("status", depositReg.getStatus());
			params.put("fromPage", DepositFromPageType.SIGN.getValue());
		}
		page = depositRegDAO.findDepositRegSign(params, getPageNumber(), getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 */
	public String detail() throws Exception {
		// 充值登记簿明细
		depositReg = (DepositReg) depositRegDAO.findByPk(depositReg
				.getDepositBatchId());
		// 卡类型
		cardTypeCode = (CardTypeCode) cardTypeCodeDAO.findByPk(depositReg
				.getCardClass());
		// 账户ID
		cardInfo = (CardInfo) cardInfoDAO.findByPk(depositReg.getCardId());
		// 签单客户明细
		signCust = (SignCust) signCustDAO.findByPk(depositReg
				.getCardCustomerId());
		// 卡BIN明细
		cardBin = (CardBin) cardBinDAO.findByPk(CardBin.getBinNo(depositReg
				.getCardId()));

		return DETAIL;
	}

	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType()
						.equals(RoleType.CARD_DEPT.getValue())
				|| this.getLoginRoleType()
						.equals(RoleType.CARD_SELL.getValue())) {
		} else {
			throw new BizException("非充值机构禁止进入充值！");
		}
		return ADD;
	}

	/**
	 * 服务端检索签单卡相关信息，返回给客户端
	 */
	public String searchSignCardInfo() {
		try {
			if (depositReg == null) {
				return null;
			}
			// 检索CardInfo
			String binNo = CardBin.getBinNo(this.depositReg.getCardId());
			Assert.notTrue(binNo.equals(CardBin.ERROR), "卡号错误！");

			CardInfo info = (CardInfo) this.cardInfoDAO
					.findByPk(this.depositReg.getCardId());
			Assert.notNull(info, "查无此卡！");
			Assert.isTrue(info.getCardStatus().equals(
					CardState.ACTIVE.getValue()), "此卡不是正常或已激活状态");

			// 检索充值子账户余额
			String acctId = info.getAcctId();
			SubAcctBal subAcctBal = new SubAcctBal();
			subAcctBal.setAcctId(acctId);
			subAcctBal.setSubacctType(SubacctType.DEPOSIT.getValue());

			subAcctBal = (SubAcctBal) subAcctBalDAO.findByPk(subAcctBal);
			String avblBal = "";
			if (subAcctBal == null) {
				avblBal = "0.00";
			} else {
				avblBal = subAcctBal.getAvlbBal().toString();
			}

			// 检索卡类型
			CardTypeCode code = (CardTypeCode) cardTypeCodeDAO.findByPk(info
					.getCardClass());
			Assert.notNull(code, "查询卡类型错误。");
//			Assert.isTrue(code.getCardTypeCode().equals(CardType.SIGN.getValue()), "此卡不是签单卡。");
			String cardClass = code.getCardTypeCode();
			String cardClassName = code.getCardTypeName();

			// 检索卡子类型
			CardSubClassDef subClass = (CardSubClassDef) cardSubClassDefDAO
					.findByPk(info.getCardSubclass());
			String cardSubClassName = subClass.getCardSubclassName();

			// 检索签单客户
			SignCust cust = (SignCust) signCustDAO.findByPk(info
					.getCardCustomerId());
			Assert.notNull(cust, "该签单客户不存在。");
			String signCustId = cust.getSignCustId().toString();
			String signCustIdName = cust.getSignCustName();

			// 查询签单卡售卡记录
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardId", depositReg.getCardId());
			List<SaleSignCardReg> list = saleSignCardRegDAO
					.findSaleSignCardReg(params);
			Assert.notEmpty(list, "找不到该卡号的售卡记录");
			SaleSignCardReg saleSignCardReg = list.get(0);

			String custName = saleSignCardReg.getCustName();
			String certTypeName = saleSignCardReg.getCertTypeName();
			String certNo = saleSignCardReg.getCertNo();
			String address = saleSignCardReg.getAddress();
			String phone = saleSignCardReg.getPhone();
			String email = saleSignCardReg.getEmail();
			String overdraft = saleSignCardReg.getOverdraft().toString();

			respond("{'acctId':'" + acctId + "','cardClass':'" + cardClass
					+ "','cardClassName':'" + cardClassName + "','overdraft':'"
					+ overdraft + "','cardSubClassName':'" + cardSubClassName
					+ "','signCustId':'" + signCustId + "','signCustIdName':'"
					+ signCustIdName + "','custName':'" + custName
					+ "','certTypeName':'" + certTypeName + "','certNo':'"
					+ certNo + "','avblBal':'" + avblBal + "','address':'"
					+ address + "','phone':'" + phone + "','email':'" + email
					+ "','passCheck':true}");
		} catch (BizException e) {
			respond("{'passCheck':false, 'errorMsg':'" + e.getMessage() + "'}");
		}
		return null;
	}

	/**
	 * 签单卡充值新建
	 */
	public String add() throws Exception {
		// 设置页面来源
		this.depositReg.setFromPage(DepositFromPageType.SIGN.getValue());
		
		// 充值类型为按金额充值
		depositReg.setDepositType(DepositType.AMT.getValue());

		// 设置充值机构
		if (isCardDeptRoleLogined()) {
			this.depositReg.setDepositBranch(getSessionUser().getDeptId());
		} else {
			this.depositReg.setDepositBranch(getSessionUser().getBranchNo());
		}
		
		String serialNo = request.getParameter("serialNo");
		
		depositService.addDepositReg(depositReg, getSessionUser(), serialNo);
		String msg = LogUtils.r("给卡号[{0}]充值[{1}]成功，充值登记号为[{2}]！", 
				depositReg.getCardId(), depositReg.getRealAmt(), depositReg.getDepositBatchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/depositRegSign/list.do", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public DepositReg getDepositReg() {
		return depositReg;
	}

	public void setDepositReg(DepositReg depositReg) {
		this.depositReg = depositReg;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public SignCust getSignCust() {
		return signCust;
	}

	public void setSignCust(SignCust signCust) {
		this.signCust = signCust;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

}
