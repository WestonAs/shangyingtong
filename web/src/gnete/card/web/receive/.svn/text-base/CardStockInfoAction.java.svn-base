package gnete.card.web.receive;

import flink.etc.MatchMode;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.CardReceiveService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardStockInfoAction.java
 * 
 * @description: 卡库存信息
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-23
 */
public class CardStockInfoAction extends BaseAction {

	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private CardReceiveService cardReceiveService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private CardStockInfo cardStockInfo;

	private List cardStatusList;
	private List cardClassList;
	private List<BranchInfo> cardBranchList;
	private List<NameValuePair> appOrgList;
	
	private String stockBranchName;
	private String strCardId;
	private String endCardId;
	/** 查询链接是否来自列表页面; 否则表示查询链接来自菜单，那么进入列表页是默认不查询 */
	private boolean linkFromListPage;
	private Paginater page;
	private boolean showCardBranch = false;

	@Override
	public String execute() throws Exception {
		cardStatusList = CardStockState.getAll();
		cardClassList = CardType.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		if (cardStockInfo != null) {
//			params.put("cardId", MatchMode.ANYWHERE.toMatchString(cardStockInfo.getCardId()));
			params.put("strCardId", strCardId);
			params.put("endCardId", endCardId);
			params.put("makeId", MatchMode.ANYWHERE.toMatchString(cardStockInfo.getMakeId()));
			params.put("cardIssuer", cardStockInfo.getCardIssuer());
			params.put("cardClass", cardStockInfo.getCardClass());
			params.put("appOrgId", cardStockInfo.getAppOrgId().split("\\|")[0]);
			params.put("cardStatus", cardStockInfo.getCardStatus());
			params.put("cardSubclass", cardStockInfo.getCardSubclass());
		}
		
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心，运营中心部门
			showCardBranch = true;
		} else if (isFenzhiRoleLogined()) {// // 分支机构
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) { // 发卡机构
			params.put("cardBranch", this.getLoginBranchCode());
			BranchInfo rootBranch = this.branchInfoDAO.findRootByBranch(this.getLoginBranchCode());
			cardBranchList = this.branchInfoDAO.findChildrenList(rootBranch.getBranchCode());
			
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			params.put("appOrgId", super.getSessionUser().getDeptId());
			cardBranchList = new ArrayList<BranchInfo>();
			cardBranchList.add(this.branchInfoDAO.findBranchInfo(this.getLoginBranchCode()));
			
		} else if (isCardSellRoleLogined()) { //售卡代理
			params.put("appOrgId", getSessionUser().getBranchNo());
			cardBranchList = this.branchInfoDAO.findCardByProxy(this.getLoginBranchCode());
			
		} else if (isMerchantRoleLogined()) {//商户
			params.put("appOrgId", getSessionUser().getMerchantNo());
			cardBranchList = this.branchInfoDAO.findCardByProxy(this.getLoginBranchCode());
			
		} else {
			throw new BizException("没有权限查看卡库存信息");
		}
		
		if (linkFromListPage) {
			this.page = cardStockInfoDAO.findCardStockInfoPage(params, getPageNumber(), getPageSize());
		} 
	
		return LIST;
	}
	

	public String detail() throws Exception {
		cardStockInfo = (CardStockInfo) cardStockInfoDAO.findByPk(cardStockInfo.getId());
		return DETAIL;
	}
	
	/**
	 * 手动生成Excel文件
	 * @throws Exception
	 */
	public void generate() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardStockInfo != null) {
//			params.put("cardId", MatchMode.ANYWHERE.toMatchString(cardStockInfo.getCardId()));
			params.put("strCardId", strCardId);
			params.put("endCardId", endCardId);
			params.put("makeId", MatchMode.ANYWHERE.toMatchString(cardStockInfo.getMakeId()));
			params.put("cardIssuer", cardStockInfo.getCardIssuer());
			params.put("cardClass", cardStockInfo.getCardClass());
			params.put("appOrgId", cardStockInfo.getAppOrgId().split("\\|")[0]);
			params.put("cardStatus", cardStockInfo.getCardStatus());
		}
		
		// 如果登录用户为运营中心，运营中心部门时，可查看所有的
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
		}
		// 分支机构看自己管理的发卡机构。发卡机构可查看自己的记录 
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			params.put("cardBranch", this.getLoginBranchCode());
		}
		// 如果登录用户为发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())) {
			params.put("appOrgId", super.getSessionUser().getDeptId());
		}
		// 如果登录用户为售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(getLoginRoleType())) {
			params.put("appOrgId", getSessionUser().getBranchNo());
		}
		// 如果登录用户为商户
		else if (RoleType.MERCHANT.getValue().equals(getLoginRoleType())) {
			params.put("appOrgId", getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查看卡库存信息");
		}

		this.cardReceiveService.generateExcel(response, params);
	}
	
	// 自动完成，查询时使用,自动出领卡机构
	public void appOrgList() throws Exception {
		String keyWord = request.getParameter("q");
		
		keyWord = keyWord.split("\\|")[0];
		List<NameValuePair> list = this.cardReceiveService.getReciveIssuer(keyWord, this.getSessionUser());
		
		JSONArray json = new JSONArray();
		for (NameValuePair pair : list) {
			JSONObject object = new JSONObject();
			object.put("value", pair.getValue());
			object.put("name", pair.getName());
			
			json.add(object);
		}
		respond(json.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardStockInfo getCardStockInfo() {
		return cardStockInfo;
	}

	public void setCardStockInfo(CardStockInfo cardStockInfo) {
		this.cardStockInfo = cardStockInfo;
	}

	public List getCardStatusList() {
		return cardStatusList;
	}

	public void setCardStatusList(List cardStatusList) {
		this.cardStatusList = cardStatusList;
	}

	public List getCardClassList() {
		return cardClassList;
	}

	public void setCardClassList(List cardClassList) {
		this.cardClassList = cardClassList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<NameValuePair> getAppOrgList() {
		return appOrgList;
	}

	public void setAppOrgList(List<NameValuePair> appOrgList) {
		this.appOrgList = appOrgList;
	}

	public String getStockBranchName() {
		return stockBranchName;
	}

	public void setStockBranchName(String stockBranchName) {
		this.stockBranchName = stockBranchName;
	}

	public String getStrCardId() {
		return strCardId;
	}

	public void setStrCardId(String strCardId) {
		this.strCardId = strCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}
	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public boolean isLinkFromListPage() {
		return linkFromListPage;
	}

	public void setLinkFromListPage(boolean linkFromListPage) {
		this.linkFromListPage = linkFromListPage;
	}
}
