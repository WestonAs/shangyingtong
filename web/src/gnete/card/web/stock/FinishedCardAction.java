package gnete.card.web.stock;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardInputDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.entity.CardInput;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardStockService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: FinishedCardAction.java
 * 
 * @description: 成品卡入库登记相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
public class FinishedCardAction extends BaseAction {

	@Autowired
	private CardInputDAO cardInputDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private CardStockService cardStockService;

	private CardInput cardInput;

	private List statusList;
	private List<CardTypeCode> cardTypeList;

	private Paginater page;

	private String cardNo;
	private String startDate;
	private String endDate;

	@Override
	public String execute() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		statusList = CheckState.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardInput != null) {
			params.put("cardType", cardInput.getCardType());
			params.put("status", cardInput.getStatus());
			params.put("cardNo", cardNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
//		List<BranchInfo> branchList = this.getMyCardBranch();
//		if (CollectionUtils.isEmpty(branchList)) {
//			branchList.add(new BranchInfo());
//		}
//		params.put("branchList", branchList);
		// 运营中心查看所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 运营机构查看自己管理的发卡机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 发卡机构查看自己的
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("branchList", this.getMyCardBranch());
		} else {
			throw new BizException("没有权限查看成品卡入库列表");
		}
		page = cardInputDAO.findCardInputPage(params, getPageNumber(), getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查看成品卡入库列表成功");
		return LIST;
	}

	public String detail() throws Exception {
		cardInput = (CardInput) cardInputDAO.findByPk(cardInput.getId());
		return DETAIL;
	}

	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.FINISHED_CARD_STOCK, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		page = cardInputDAO.findCardInputPage(params, getPageNumber(), getPageSize());
		return CHECK_LIST;
	}

	public String checkDetail() throws Exception {
		cardInput = (CardInput) cardInputDAO.findByPk(cardInput.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		if (!(this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue()))) {
			throw new BizException("没有权限进行成品卡入库操作。");
		}
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		return ADD;
	}

	/**
	 * 输入的起始卡号是否正确（卡号格式与卡类型是否匹配）
	 */
	public void isCorrect() {
		try {
			boolean isCorrect = false;
			if (StringUtils.isNotBlank(cardInput.getCardType())
					&& StringUtils.isNotBlank(cardInput.getStrNo())) {
				isCorrect = cardStockService.isCorrectStrNo(cardInput, getSessionUser());
			}
			respond("{'isCorrect': " + isCorrect + "}");
		} catch (BizException e) {
			respond("{'isCorrect': false, 'errorMsg':'" + e.getMessage() + "'}");
		}
	}

	/**
	 * 根据输入的起始卡号，卡数量，计算出结束卡号
	 */
	public void getEndNo() throws Exception {
		String endNo = "";
		if (StringUtils.isNotBlank(cardInput.getStrNo())
				&& (cardInput.getInputNum() != null)) {
			endNo = cardStockService.getEndNo(cardInput);
		}
		respond("{'endNo':'" + endNo + "'}");
	}

	/**
	 * 成品库入库新建
	 */
	public String add() throws Exception {
		// 保存数据
		cardStockService.addCardInput(cardInput, getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的卡入库申请已经提交！", cardInput.getId());
		addActionMessage("/cardStock/finishedCard/list.do", msg);
		log(msg, UserLogType.ADD);

		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardInput getCardInput() {
		return cardInput;
	}

	public void setCardInput(CardInput cardInput) {
		this.cardInput = cardInput;
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

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List<CardTypeCode> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardTypeCode> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
