package gnete.card.web.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.WhiteCardInputDAO;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.WhiteCardInput;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardStockService;
import gnete.card.web.BaseAction;
import gnete.etc.WorkflowConstants;

/**
 * @File: WhiteCardAction.java
 * 
 * @description: 白卡入库处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-20
 */
public class WhiteCardAction extends BaseAction {

	@Autowired
	private WhiteCardInputDAO whiteCardInputDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private CardStockService cardStockService;

	private WhiteCardInput whiteCardInput;

	private List statusList;
	private List<CardTypeCode> cardTypeList;
	private List<CardSubClassDef> cardSubtypeList;
	private List<MakeCardReg> makeIdList;

	private Paginater page;

	private String cardNo;
	private String startDate;
	private String endDate;

	@Override
	public String execute() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO
				.findCardTypeCode(CardTypeState.NORMAL.getValue());
		statusList = CheckState.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", getSessionUser().getBranchNo());
		if (whiteCardInput != null) {
			params.put("cardType", whiteCardInput.getCardType());
			params.put("status", whiteCardInput.getStatus());
			params.put("cardNo", cardNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		page = whiteCardInputDAO.findWhiteCardInputPage(params,
				getPageNumber(), getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		whiteCardInput = (WhiteCardInput) whiteCardInputDAO
				.findByPk(whiteCardInput.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = this.cardTypeCodeDAO
				.findCardTypeCode(CardTypeState.NORMAL.getValue());
		return ADD;
	}

	/**
	 * 根据所选卡类别获得可选的卡子类型列表
	 */
	public String subTypeList() throws Exception {
		cardSubtypeList = cardStockService.getSubTypeList(whiteCardInput,
				getSessionUser());
		return "subTypeList";
	}

	/**
	 * 根据卡子类型获得可选的制卡登记ID列表
	 */
	public String makeIdList() throws Exception {
		makeIdList = cardStockService.getMakeIdList(whiteCardInput,
				getSessionUser());
		return "makeIdList";
	}

	/**
	 * 新增白卡入库登记
	 */
	public String add() throws Exception {
		WhiteCardInput input = cardStockService.addWhiteCardInput(
				whiteCardInput, getSessionUser());

		// 启动单个流程
		workflowService.startFlow(input, "whiteCardStockAdapter", Long
				.toString(whiteCardInput.getId()), getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的卡入库申请已经提交！", whiteCardInput.getId());
		addActionMessage("/cardStock/whiteCard/list.do", msg);
		log(msg, UserLogType.ADD);

		return SUCCESS;
	}

	/**
	 * 审核列表
	 */
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(
				WorkflowConstants.WHITE_CARD_STOCK, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		page = whiteCardInputDAO.findWhiteCardInputPage(params,
				getPageNumber(), getPageSize());
		return CHECK_LIST;
	}

	public String checkDetail() throws Exception {
		whiteCardInput = (WhiteCardInput) whiteCardInputDAO
				.findByPk(whiteCardInput.getId());
		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WhiteCardInput getWhiteCardInput() {
		return whiteCardInput;
	}

	public void setWhiteCardInput(WhiteCardInput whiteCardInput) {
		this.whiteCardInput = whiteCardInput;
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

	public List<CardSubClassDef> getCardSubtypeList() {
		return cardSubtypeList;
	}

	public void setCardSubtypeList(List<CardSubClassDef> cardSubtypeList) {
		this.cardSubtypeList = cardSubtypeList;
	}

	public List<MakeCardReg> getMakeIdList() {
		return makeIdList;
	}

	public void setMakeIdList(List<MakeCardReg> makeIdList) {
		this.makeIdList = makeIdList;
	}
}
