package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.SaleSignCardRegDAO;
import gnete.card.dao.SignCustDAO;
import gnete.card.dao.SignRuleRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SaleSignCardReg;
import gnete.card.entity.SignCust;
import gnete.card.entity.SignRuleReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SaleCardRuleService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SaleSignCardRegAction.java
 * 
 * @description: 签单卡售卡登记簿Action
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author BenYan
 * @modify ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-8-14
 */
public class SaleSignCardRegAction extends BaseAction {

	@Autowired
	private SaleSignCardRegDAO saleSignCardRegDAO;
	@Autowired
	private SaleCardRuleService saleCardRuleService;
	@Autowired
	private SignRuleRegDAO signRuleRegDAO;
	@Autowired
	private SignCustDAO signCustDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;

	private SaleSignCardReg saleSignCardReg;

	private List<SignCust> signCustList;
	private List statusList;
	private List certTypeList;

	private Paginater page;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.statusList = RegisterState.getForTrade();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchList", this.getMyDepositList());
		if (saleSignCardReg != null) {
			params.put("cardId", saleSignCardReg.getCardId());
			params.put("signCustId", saleSignCardReg.getSignCustId());
			params.put("signCustName", MatchMode.ANYWHERE
					.toMatchString(saleSignCardReg.getSignCustName()));
			params.put("signRuleId", saleSignCardReg.getSignRuleId());
			params.put("signRuleName", MatchMode.ANYWHERE
					.toMatchString(saleSignCardReg.getSignRuleName()));
			params.put("custName", MatchMode.ANYWHERE
					.toMatchString(saleSignCardReg.getCustName()));
			params.put("status", saleSignCardReg.getStatus());
		}
		this.page = this.saleSignCardRegDAO.findSaleSignCardReg(params, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		saleSignCardReg = (SaleSignCardReg) saleSignCardRegDAO
				.findByPk(saleSignCardReg.getSaleSignCardId());

		return DETAIL;
	}

	/**
	 * 查询指定客户的签单规则列表，服务端查询，返回到前端
	 */
	public String getSignRuleList() {
		String signCustId = request.getParameter("signCustId");
		StringBuffer sb = new StringBuffer(128);
		if (StringUtils.isNotBlank(signCustId)) {
			List<SignRuleReg> signRuleRegList = signRuleRegDAO
					.findSignRuleByCust(signCustId);
			sb.append("<option value=\"\">").append("--请选择--").append(
					"</option>");
			for (SignRuleReg signRuleReg : signRuleRegList) {
				sb.append("<option value=\"").append(
						signRuleReg.getSignRuleId()).append("\">").append(
						signRuleReg.getSignRuleName()).append("</option>");
			}
			respond(sb.toString());
		}
		return null;
	}

	/**
	 * 根据卡号查询卡子类型表得到工本费
	 */
	public String getExpensesAmt() {
		String cardId = request.getParameter("cardId");
		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			if (RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
				Assert.isTrue(StringUtils.equals(cardInfo.getAppOrgId(),
						getSessionUser().getDeptId()), "卡号[" + cardId
						+ "]不是自己领的。");
			} else {
				Assert.isTrue(StringUtils.equals(cardInfo.getAppOrgId(),
						getSessionUser().getBranchNo()), "卡号[" + cardId
						+ "]不是自己领的。");
			}
			CardSubClassDef subClass = (CardSubClassDef) cardSubClassDefDAO
					.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(subClass, "该卡所属卡子类型不存在");
//			Assert.isTrue(subClass.getCardClass().equals(CardType.SIGN.getValue()), "该卡不是签单卡");
			StringBuffer sb = new StringBuffer(128);
			sb.append("{'checkCardId':true, 'expenses':'").append(
					subClass.getCardPrice()).append("'}");
			respond(sb.toString());
		} catch (BizException e) {
			respond("{'checkCardId':false, 'error':'" + e.getMessage() + "'}");
		}
		return null;
	}

	/**
	 * 取得透支金额
	 */
	public String getOverdraftAmt() {
		String signRuleId = request.getParameter("signRuleId");
		SignRuleReg reg = (SignRuleReg) signRuleRegDAO.findByPk(NumberUtils
				.toLong(signRuleId));
		if (reg != null) {
			respond("{overdraft:'" + reg.getOverdraft().toString() + "'}");
		} else {
			respond("{'overdraft':'0.0'}");
		}
		return null;
	}

	/**
	 * 打开新增页面的初始化操作
	 */
	public String showAdd() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String branchCode = "";
		if (getLoginRoleType().equals(RoleType.CARD.getValue())) {
			// 如果登录用户角色为发卡机构时
			branchCode = getSessionUser().getBranchNo();
			params.put("cardBranch", branchCode);
		} else if (getLoginRoleType().equals(RoleType.CARD_SELL.getValue())) {
			// 登录用户角色为售卡代理
			branchCode = getSessionUser().getBranchNo();
			params.put("sellBranch", branchCode);
		} else if (getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())) {
			// 登录用户角色为发卡机构网点
			branchCode = getSessionUser().getDeptId();
			params.put("cardBranch", getSessionUser().getBranchNo());
		} else {
			throw new BizException("该用户登录的角色不能售签单卡");
		}
		// 签单客户列表
		signCustList = signCustDAO.findSignCust(params);
		// 证件类型列表
		this.certTypeList = CertType.getAll();

		return ADD;
	}

	/**
	 * 新增
	 */
	public String add() throws Exception {
		
		String branchCode = "";
		// 如果登录用户角色为发卡机构时
		if (getLoginRoleType().equals(RoleType.CARD.getValue())
				|| getLoginRoleType().equals(RoleType.CARD_SELL.getValue())) {
			branchCode = getSessionUser().getBranchNo();
		} else if (getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())) {
			branchCode = getSessionUser().getDeptId();
		} else {
			throw new BizException("该用户登录的角色不能售签单卡");
		}

		saleSignCardReg.setBranchCode(branchCode);
		saleCardRuleService.addSaleSignCardReg(saleSignCardReg,
				getSessionUser());

		String msg = LogUtils.r("添加卡号[{0}]的签单卡售卡登记成功！", saleSignCardReg
				.getCardId());
		this.addActionMessage("/signCardMgr/saleSignCardReg/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;

	}

	public SaleSignCardReg getSaleSignCardReg() {
		return saleSignCardReg;
	}

	public void setSaleSignCardReg(SaleSignCardReg saleSignCardReg) {
		this.saleSignCardReg = saleSignCardReg;
	}

	public List<SignCust> getSignCustList() {
		return signCustList;
	}

	public void setSignCustList(List<SignCust> signCustList) {
		this.signCustList = signCustList;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(List certTypeList) {
		this.certTypeList = certTypeList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
}
