package gnete.card.web.fee;

import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardMembFeeDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardMembFee;
import gnete.card.entity.type.CardMembFeeFeeType;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.util.DateUtil;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 发卡机构会员运营手续费设置
 */
public class CardMembFeeAction extends BaseAction {
	@Autowired
	private CardMembFeeDAO	cardMembFeeDAO;
	@Autowired
	private BranchInfoDAO	branchInfoDAO;
	@Autowired
	private CardInfoDAO		cardInfoDAO;

	private Paginater		page;
	private CardMembFee		cardMembFee;

	private Collection		feeTypeList;
	private Collection		transTypeList;
	private Collection		costCycleTypeList;

	@Override
	public String execute() throws Exception {
		buildTypeLists();

		Map<String, Object> params = new HashMap<String, Object>();

		if (this.cardMembFee != null) {
			params.put("cardMembFee", cardMembFee);
		}

		if (isCenterOrCenterDeptRoleLogined()) { // 营运中心或部门

		} else if (isFenzhiRoleLogined()) { // 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardOrCardDeptRoleLogined()) { // 发卡机构
			params.put("branchList", getMyCardBranch());
		} else {
			throw new BizException("没有权限查询发卡机构会员手续费！");
		}

		page = this.cardMembFeeDAO.findCardMembFee(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		this.cardMembFee = (CardMembFee) this.cardMembFeeDAO.findByPk(this.cardMembFee.getFeeRuleId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		super.checkEffectiveCertUser();
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("没有权限设置发卡机构会员手续费！");
		}
		
		buildTypeLists();

		return ADD;
	}

	public String add() throws Exception {
		checkUserOprtPriv(cardMembFee);

		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardMembFee.getCardId());
		String errMsg = String.format("发卡机构%s没有会员卡号%s", cardMembFee.getBranchCode(), cardMembFee.getCardId());
		Assert.isTrue(cardInfo != null
				&& StringUtils.equals(cardInfo.getCardIssuer(), cardMembFee.getBranchCode()), errMsg);

		String errMsg2 = String.format("已经存在发卡机构%s，卡号%s，交易类型%s指定的手续费设置", cardMembFee.getBranchCode(),
				cardMembFee.getCardId(), cardMembFee.getTransTypeName());
		Assert.isTrue(cardMembFeeDAO.findBy(cardMembFee.getBranchCode(), cardMembFee.getCardId(), cardMembFee
				.getTransType()) == null, errMsg2);
		
		cardMembFee.setCurCode("CNY");
		cardMembFee.setUlMoney(AmountUtil.format(Constants.FEE_MAXACCOUNT));// 设置默认值
		cardMembFee.setModifyDate(DateUtil.formatDate("yyyyMMdd"));
		cardMembFee.setUpdateBy(this.getSessionUserCode());
		cardMembFee.setUpdateTime(new Date());
		this.cardMembFeeDAO.insert(cardMembFee);

		String msg = "添加发卡机构会员手续费成功";
		String logMsg = msg + "，手续费：" + cardMembFee.toString();
		this.log(logMsg, UserLogType.ADD);
		addActionMessage("/fee/cardMembFee/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showModify() throws Exception {
		CardMembFee oldCardMembFee = (CardMembFee) cardMembFeeDAO.findByPk(cardMembFee.getFeeRuleId());
		checkUserOprtPriv(oldCardMembFee);

		buildTypeLists();
		this.cardMembFee = (CardMembFee) cardMembFeeDAO.findByPk(this.cardMembFee.getFeeRuleId());
		return MODIFY;
	}

	public String modify() throws Exception {

		CardMembFee oldCardMembFee = (CardMembFee) cardMembFeeDAO.findByPk(cardMembFee.getFeeRuleId());
		checkUserOprtPriv(oldCardMembFee);
		
		CardMembFee cmf = cardMembFeeDAO.findBy(cardMembFee.getBranchCode(), cardMembFee.getCardId(), cardMembFee
				.getTransType());
		if (cmf != null && !cmf.getFeeRuleId().equals(cardMembFee.getFeeRuleId())) {
			throw new BizException(String.format("已经存在发卡机构%s，卡号%s，交易类型%s指定的手续费设置", cardMembFee
					.getBranchCode(), cardMembFee.getCardId(), cardMembFee.getTransTypeName()));
		}
		
		cardMembFeeDAO.findBy(cardMembFee.getBranchCode(), cardMembFee.getCardId(),
				cardMembFee.getTransType());
		
		cardMembFee.setBranchCode(oldCardMembFee.getBranchCode());
		cardMembFee.setCardId(oldCardMembFee.getCardId());
		cardMembFee.setUlMoney(oldCardMembFee.getUlMoney());
		cardMembFee.setCurCode(oldCardMembFee.getCurCode());

		cardMembFee.setModifyDate(DateUtil.formatDate("yyyyMMdd"));
		cardMembFee.setUpdateBy(this.getSessionUserCode());
		cardMembFee.setUpdateTime(new Date());

		this.cardMembFeeDAO.update(cardMembFee);

		String msg = "修改发卡机构会员手续费成功";
		String logMsg = msg + "，原手续费：" + oldCardMembFee.toString() + "，新手续费：" + cardMembFee.toString();
		this.log(logMsg, UserLogType.UPDATE);
		addActionMessage("/fee/cardMembFee/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		CardMembFee oldCardMembFee = (CardMembFee) cardMembFeeDAO.findByPk(cardMembFee.getFeeRuleId());
		checkUserOprtPriv(oldCardMembFee);

		cardMembFeeDAO.delete(cardMembFee.getFeeRuleId());
		String msg = "删除发卡机构会员手续费成功";
		String logMsg = msg + "，手续费：" + oldCardMembFee.toString();
		this.log(logMsg, UserLogType.DELETE);
		addActionMessage("/fee/cardMembFee/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/** 构建页面用到的类型列表 */
	private void buildTypeLists() {
		feeTypeList = CardMembFeeFeeType.getAll();
		transTypeList = TransType.getCardMembFeeTransTypeList();
		costCycleTypeList = CostCycleType.getDay();
	}

	/** 用户是否可操作该cardMembFee */
	private void checkUserOprtPriv(CardMembFee cardMembFee) throws BizException {
		super.checkEffectiveCertUser();
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("没有权限设置发卡机构会员手续费！");
		}

		Assert.notNull(cardMembFee, "指定的记录不存在！");
		Assert.isTrue(branchInfoDAO.isSuperBranch(getLoginBranchCode(), cardMembFee.getBranchCode()),
				"登录的机构不是指定发卡机构的本身或上级机构！");
	}

	// ------------------------------- getter and setter followed------------------------

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardMembFee getCardMembFee() {
		return cardMembFee;
	}

	public void setCardMembFee(CardMembFee cardMembFee) {
		this.cardMembFee = cardMembFee;
	}

	public Collection getFeeTypeList() {
		return feeTypeList;
	}

	public void setFeeTypeList(Collection feeTypeList) {
		this.feeTypeList = feeTypeList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public void setCostCycleTypeList(Collection costCycleTypeList) {
		this.costCycleTypeList = costCycleTypeList;
	}

	public Collection getCostCycleTypeList() {
		return costCycleTypeList;
	}

}
