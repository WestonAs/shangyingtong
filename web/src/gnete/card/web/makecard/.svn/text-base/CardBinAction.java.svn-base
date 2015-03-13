package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardBinReg;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.CurrCode;
import gnete.card.entity.state.CardBinRegState;
import gnete.card.entity.state.CardBinState;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardBinService;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardBinAction.java
 * 
 * @description: 发卡机构卡BIN申请，发卡机构可以申请，它的管理机构可以帮它申请
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-26
 */
public class CardBinAction extends BaseAction {

	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private CurrCodeDAO currCodeDAO;
	@Autowired
	private CardBinService cardBinService;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;

	private CardBinReg cardBinReg;
	private CardBin cardBin;

	private String cardTypeCode;
	private String cardCurrCode;

	private Paginater page;

	private List<CardTypeCode> cardTypeList;
	private List<CurrCode> currCodeList;
	private List statusList;

	private String cardBranchName;

	// 界面选择时是否单选
	private boolean radio;
	private String cardIssuerBin;
	private String merchIdBin;

	@Override
	public String execute() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		// 加载状态列表
		this.statusList = CardBinState.getList();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardBin != null) {
			params.put("cardIssuer", cardBin.getCardIssuer());
			params.put("binNo", MatchMode.ANYWHERE.toMatchString(cardBin.getBinNo()));
			params.put("binName", MatchMode.ANYWHERE.toMatchString(cardBin.getBinName()));
			params.put("cardType", cardBin.getCardType());
			params.put("status", cardBin.getStatus());
		}
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心 或 部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构 或 部门
			params.put("cardIssuerList", this.getMyCardBranch());
			
		} else {
			throw new BizException("没有权限查看卡BIN记录");
		}
		
		this.page = this.cardBinService.findCardBinPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 申请记录列表
	 * @return
	 * @throws Exception
	 */
	public String regList() throws Exception {
		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		// 加载状态列表
		this.statusList = CardBinRegState.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardBinReg != null) {
			params.put("cardIssuer", cardBinReg.getCardIssuer());
			params.put("binNo", MatchMode.ANYWHERE.toMatchString(cardBinReg.getBinNo()));
			params.put("binName", MatchMode.ANYWHERE.toMatchString(cardBinReg.getBinName()));
			params.put("cardType", cardBinReg.getCardType());
			params.put("status", cardBinReg.getStatus());
		}
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心 或 部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构 或 部门
			params.put("cardIssuerList", this.getMyCardBranch());
			
		} else {
			throw new BizException("没有权限查看卡BIN申请记录");
		}
		this.page = this.cardBinService.findCardBinRegPage(params, this.getPageNumber(), this.getPageSize());
		return "regList";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String cardBinCheckList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isFenzhiRoleLogined()) {
			params.put("checkFenzhiCode", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限进行卡BIN审核操作");
		}
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.CARD_BIN_REG, this.getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}

		params.put("ids", ids);
		
		this.page = this.cardBinService.findCardBinRegPage(params, this.getPageNumber(), this.getPageSize());
	
		return CHECK_LIST;
	}

	// 打开新增卡BIN页面的初始化操作
	public String showAddCardBin() throws Exception {
		if ( !isFenzhiRoleLogined() && !isCardOrCardDeptRoleLogined()) {
			throw new BizException("非运营机构或发卡机构，没有权限申请卡BIN。");
		}

		// 加载状态有效的卡类型做为下拉列表
		this.cardTypeList = cardTypeCodeDAO.findCardTypeCode(CardTypeState.NORMAL.getValue());
		// 加载状态有效的货币代码列表
		this.currCodeList = currCodeDAO.findCurrCode(CurrCodeState.NORMAL.getValue());

		/*
		int length = 6;// 卡bin长度要查系统参数表
		String binNo = "";

		// 卡bin号码是否限制以99开头
		String binNoLimit = ParaMgr.getInstance().getBinNoLimit();
		if (StringUtils.equals(binNoLimit, Symbol.YES)) {
			binNo = generateCardBin("99",length);
		} else {
			binNo = generateCardBin(length);
		}*/

		this.cardBinReg = new CardBinReg();
		cardBinReg.setBinNo(this.cardBinService.getBinNo(this.getSessionUser()));
		cardBinReg.setCardType(CardType.DEPOSIT.getValue());
		return ADD;
	}

	// 新增卡bin
	public String add() throws Exception {
		if ( !isFenzhiRoleLogined() && !isCardOrCardDeptRoleLogined()) {
			throw new BizException("没有权限申请卡BIN。");
		}
		
		if (StringUtils.isBlank(cardBinReg.getCardIssuer())) {
			cardBinReg.setCardIssuer(this.getSessionUser().getBranchNo());
		}
		
		cardBinReg.setInitiator(this.getLoginBranchCode());
		
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(cardBinReg.getCardIssuer(), cardBinReg
					.getInitiator()), "发卡机构与发起方不是属于同一顶级机构");
		}
		
		// 保存数据
		this.cardBinService.addCardBin(cardBinReg, this.getSessionUser());

		String msg = LogUtils.r("卡BIN[{0}]申请已经提交！", cardBinReg.getBinNo());

		this.addActionMessage("/addCardBin/cardBinList.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	// 判断卡BIN是否已经存在
	public void isExistBinNo() throws Exception {
		// 获取页面的BinNo
		String binNo = this.cardBinReg.getBinNo();
		boolean isExist = this.cardBinService.isExistCardBin(binNo);
		this.respond("{'isExist':" + isExist + "}");
	}

	// 取得卡BIN申请的明细
	public String cardBinRegDetail() {
		cardBinReg = this.cardBinService.findCardBinRegDetail(cardBinReg.getId());
		return DETAIL;
	}
	
	public String detail() throws Exception {
		return DETAIL;
	}

	// 取得卡BIN申请的明细，流程审核人查看的
	public String cardBinCheckDetail() {
		cardBinReg = this.cardBinService.findCardBinRegDetail(cardBinReg.getId());
		return DETAIL;
	}

	/**
	 * 生成以99开头，同时卡bin管理表里不存在的卡bin号码
	 * 
	 * @param length
	 * @return
	 */
	/*
	private String generateCardBin(String prex, int length) {
		String binNoEnd = "";
		String binNo = "";
		while (true) {
			binNoEnd = getNo(length - prex.length());
			binNo = prex + binNoEnd;
			// 检查卡bin是否已经存在
			boolean isExist = cardBinDAO.isExist(binNo);
			// 如果不存在则跳出while循环
			if (!isExist) {
				break;
			}
		}
		return binNo;
	}*/

	/**
	 * 生成一个卡bin管理表里不存在的卡BIN号码
	 * 
	 * @param length
	 * @return
	 */
	/*
	private String generateCardBin(int length) {
		// List list = this.cardBinDAO.findAll();
		// String binNo = null;
		// while (true) {
		// binNo = getNo(length);
		// // 定义一个bin是否存在
		// boolean isExist = false;
		// for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		// cardBin = (CardBin) iterator.next();
		// if (StringUtils.equals(binNo, cardBin.getBinNo())) {
		// isExist = true;
		// }
		// }
		// // 如果不存在则跳出while循环
		// if (!isExist) {
		// break;
		// }
		// }
//		String binNo = "";
//		while (true) {
//			binNo = getNo(length);
//			boolean isExist = cardBinDAO.isExist(binNo);
//			// 如果不存在则跳出while循环
//			if (!isExist) {
//				break;
//			}
//		}
		return this.generateCardBin(StringUtils.EMPTY, length);
	}*/

	/**
	 * 生成指定长度的随机数字串
	 * 
	 * @param length
	 * @return
	 */
	/*
	private String getNo(int length) {
		Random random = new Random();
		int upLimit = 10;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(Integer.toString(random.nextInt(upLimit)));
		}
		return buffer.toString();
	}*/

	public String showSelect() throws Exception {
		return "select";
	}

	/**
	 * 卡BIN选择
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardBin != null) {
			params.put("binNo", cardBin.getBinNo());
			params.put("slectBinName", MatchMode.ANYWHERE.toMatchString(cardBin.getBinName()));
			params.put("status", CardBinState.NORMAL.getValue());
			params.put("cardIssuer", cardIssuerBin);
			params.put("merchIdBin", merchIdBin);
		}
		page = cardBinService.findCardBinPage(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		
		return "data";
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getCardCurrCode() {
		return cardCurrCode;
	}

	public void setCardCurrCode(String cardCurrCode) {
		this.cardCurrCode = cardCurrCode;
	}

	public CardBinReg getCardBinReg() {
		return cardBinReg;
	}

	public void setCardBinReg(CardBinReg cardBinReg) {
		this.cardBinReg = cardBinReg;
	}

	public CardBin getCardBin() {
		return cardBin;
	}

	public void setCardBin(CardBin cardBin) {
		this.cardBin = cardBin;
	}

	public List<CardTypeCode> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardTypeCode> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public List<CurrCode> getCurrCodeList() {
		return currCodeList;
	}

	public void setCurrCodeList(List<CurrCode> currCodeList) {
		this.currCodeList = currCodeList;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getCardIssuerBin() {
		return cardIssuerBin;
	}

	public void setCardIssuerBin(String cardIssuerBin) {
		this.cardIssuerBin = cardIssuerBin;
	}

	public String getMerchIdBin() {
		return merchIdBin;
	}

	public void setMerchIdBin(String merchIdBin) {
		this.merchIdBin = merchIdBin;
	}

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

}
