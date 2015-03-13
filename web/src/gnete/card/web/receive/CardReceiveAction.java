package gnete.card.web.receive;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AppRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.flag.ReceiveFlag;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.AppOrgType;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardReceiveService;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardReceiveAction.java
 * 
 * @description: 领卡相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
public class CardReceiveAction extends BaseAction {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardReceiveService cardReceiveService;

	/**
	 * 领卡登记表
	 */
	private AppReg appReg;

	private List statusList;
	private List flagList;
	private List<NameValuePair> appOrgList;
	private List<BranchInfo> cardBranchList; // 卡的领出机构列表
	private List<DepartmentInfo> deptList;
	private List<BranchInfo> cardIssuerList; // 发卡机构列表
	private List<BranchInfo> cardSellList; // 售卡代理列表
	private List appOrgTypeList;
	private List<CardStockState> stockStateList; // 卡库存状态，已领卡和卡在库

	private Paginater page;

	private String startDate;
	private String endDate;

	private String checkCardNum;
	
	private BigDecimal appFaceValue;
	private BigDecimal appSumAmt;
	
	private boolean showCardBranch = false;
	private boolean showAppOrg = false;

	@Override
	public String execute() throws Exception {
		statusList = CheckState.getAll();
		flagList = ReceiveFlag.getAll();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ReceiveFlag.RECEIVE.getValue());
		if (appReg != null) {
			params.put("cardIssuer", MatchMode.ANYWHERE.toMatchString(appReg.getCardIssuer()));
			params.put("appOrgId", appReg.getAppOrgId());
			params.put("status", appReg.getStatus());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("strNo", appReg.getStrNo());
			params.put("orderNo", appReg.getOrderNo());
			params.put("orderUnit", StringUtils.trimToEmpty(appReg.getOrderUnit()));
		}

		if (isCenterOrCenterDeptRoleLogined()) {
			showCardBranch = true;
			
		} else if (isFenzhiRoleLogined()) {
			showCardBranch = true;
			showAppOrg = true;
			appOrgList = this.getMyReciveIssuer();// 得到我所管理的领卡机构(商户)，领卡部门编号
			params.put("fenzhiCode", this.getLoginBranchCode());
			
		} else if (isCardRoleLogined()) {
			showCardBranch = true;
			showAppOrg = true;
			appOrgList = this.getMyReciveIssuer();// 得到我所管理的领卡机构(商户)，领卡部门编号
			params.put("cardBranch", getSessionUser().getBranchNo());
			
		} else if (isCardDeptRoleLogined()) {
			params.put("appOrgId", this.getSessionUser().getDeptId());
			
		} else if (isCardSellRoleLogined()) {
			params.put("appOrgId", this.getSessionUser().getBranchNo());
			
		} else if (isMerchantRoleLogined()) {
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
			
		} else {
			throw new BizException("没有权限查询领卡记录");
		}

		page = appRegDAO.findAppRegPage(params, getPageNumber(), getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		appReg = (AppReg) appRegDAO.findByPk(appReg.getId());
		
		CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(appReg.getCardSubClass());
		Assert.notNull(subClass, "卡类型[" + appReg.getCardSubClass() + "]已经不存在");
		appFaceValue = (subClass.getFaceValue() == null ? new BigDecimal("0.00") : subClass.getFaceValue());
		appSumAmt = AmountUtil.multiply(new BigDecimal(appReg.getCardNum()), appFaceValue);

		return DETAIL;
	}

	/**
	 * 领卡机构自己申请领卡时，卡的领入机构是自己。
	 *	1.发卡机构领卡时。
     *  	可领卡的发卡机构为自己，自己的上级，下级和同级。（一级发卡机构及下面所有的发卡机构之间可以互相领对方发的卡）
     * 		领出机构可为自己，自己的上级，下级和同级
	 *		注：发卡机构和领出机构相同时，要选择库存状态为“在库”还是“已领卡”的卡。不同时，只能领“已领卡”的卡
	 *	2. 售卡代理领卡时（售卡代理不能向发卡机构领“已领卡”的卡，如果要领的话可让发卡机构将该卡先返库）
     * 		可领卡的发卡机构为自己代理的发卡机构。
     *		领出机构可为自己代理的发卡机构，和该发卡机构下的其他售卡代理。（售卡代理之间领卡必须要与同一发卡机构有代理关系）
     * 		注：领出机构为发卡机构时，卡库存的状态为“卡在库”，领出机构为售卡代理时卡库存状态为“已领卡”。
	 *	3. 商户领卡时，可领卡的发卡机构为与自己有特约关系的发卡机构 。
     * 		领出机构为发卡机构（只能从发卡机构那里领库存状态为“卡在库”的赠券卡）
	 *	4. 发卡机构部门领卡时，可领卡的发卡机构为自己所属的发卡机构。
     * 		卡的领出机构为自己所属的发卡机构（只能从发卡机构那里领库存状态为“卡在库”的卡）
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		cardIssuerList = new ArrayList<BranchInfo>(); // 只需要先得到可领出的卡的发卡机构
		cardBranchList = new ArrayList<BranchInfo>(); // 卡的领出机构列表
		BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
		
		if (isCardRoleLogined()) {// 如果是发卡机构登录，可领卡的发卡机构为自己，自己的上级，下级和同级
			// 发卡机构的顶级
			BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(loginBranch.getBranchCode());
			cardIssuerList.addAll(this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
			// 可领卡状态
			stockStateList = CardStockState.getReceiveList();
			cardBranchList = cardIssuerList;
		} else if (isCardSellRoleLogined()) { // 如果登陆的是售卡代理，可以领自己代理的发卡机构发的卡
			this.cardIssuerList.addAll(this.branchInfoDAO.findCardByProxy(this.getLoginBranchCode()));
			// 可领卡状态
			stockStateList = CardStockState.getReceiveList();
			cardBranchList = cardIssuerList;
		} else if (isCardDeptRoleLogined()) { // 如果是发卡机构网点登陆，可以领自己的发卡机构发的卡
			cardIssuerList.add(loginBranch);
			// 可领卡状态
			stockStateList = CardStockState.getReceiveList();
			cardBranchList = cardIssuerList;
		} else if (isMerchantRoleLogined()) { // 如果是商户登录,可以领与自己有特约关系的发卡机构发的卡
			this.cardIssuerList.addAll(this.branchInfoDAO.findByMerch(this.getSessionUser().getMerchantNo()));
			// 可领卡状态
			stockStateList = CardStockState.getReceiveList();
			cardBranchList = cardIssuerList;
		} else {
			throw new BizException("当前用户没有领卡权限");
		}
		this.sortBranchList(cardBranchList);
		this.sortBranchList(cardIssuerList);
		
		appReg = new AppReg();
		if (isCardRoleLogined()) { // 如果是发卡机构登录，领卡机构是自己（可以向自己领卡，还可以向自己的上级领卡）
			appReg.setAppOrgType(AppOrgType.BRANCH.getValue());
			appReg.setAppOrgId(this.getSessionUser().getBranchNo());
		} else if (isCardDeptRoleLogined()) { // 如果是发卡机构网点登陆，领卡机构为自己的部门号，领卡机构类型为部门领卡
			appReg.setAppOrgType(AppOrgType.DEPART.getValue());
			appReg.setAppOrgId(getSessionUser().getDeptId());
		} else if (isCardSellRoleLogined()) { // 如果登陆的是售卡代理，领卡机构为自己的机构号，可以领某一发卡机构的卡，也可以领某一售卡代理的卡。
			appReg.setAppOrgType(AppOrgType.BRANCH.getValue());
			appReg.setAppOrgId(getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()) { // 商户
			appReg.setAppOrgType(AppOrgType.MERCH.getValue());
			appReg.setAppOrgId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("当前用户没有领卡权限");
		}
		return ADD;
	}
	
	/**
	 * 发卡机构代别人领卡。发卡机构替部门，售卡代理或下级发卡机构领卡。领出机构为自己
	 * @return
	 * @throws Exception
	 */
	public String showDelegate() throws Exception {
		// 如果是发卡机构登录，可以替发卡机构的部门，售卡代理和下级发卡机构领卡
		if (isCardRoleLogined()) {
			appOrgTypeList = AppOrgType.getDeptAndBranch();
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
			
			// 发卡机构的顶级。取得所有的发卡机构列表
			BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(loginBranch.getBranchCode());
			cardIssuerList = this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode());
			
			// 售卡代理
			cardSellList = sortBranchList(this.branchInfoDAO.findCardProxy(this.getLoginBranchCode(), ProxyType.SELL));
			
			List<BranchInfo> loginList = new ArrayList<BranchInfo>();
			loginList.add(loginBranch);
			
			// 卡的领入发卡机构列表（不包含自己）
			cardBranchList = new ArrayList<BranchInfo>();
			for (BranchInfo branchInfo : cardIssuerList) {
				if (!StringUtils.equals(branchInfo.getBranchCode(), loginBranch.getBranchCode())) {
					cardBranchList.add(branchInfo);
				}
			}
			this.sortBranchList(cardBranchList);
			
			// 部门列表
			deptList = this.getMyDept();
			
			stockStateList = CardStockState.getReceiveList();
			
			appReg = new AppReg();
			
			appReg.setCardSectorId(this.getLoginBranchCode()); //卡的领出机构为当前登录的机构
			
		} else {
			throw new BizException("当前用户没有权限替别人领卡 ");
		}
		return "delegate";
	}
	
	public String delegate() throws Exception {
		
		// 保存数据
		AppReg reg = cardReceiveService.addCardReceive(appReg, getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的领卡申请已经提交。", reg.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardReceive/receive/list.do", msg);
		
		return SUCCESS;
	}
	
	/**
	 * 根据页面上的值取得领出机构列表
	 * @return
	 * @throws Exception
	 * 这个方法已不用
	 * @deprecated
	 */
	public void loadBranch() throws Exception {
		String cardIssuer = request.getParameter("cardIssuer");
		BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
		BranchInfo cardBranch = this.branchInfoDAO.findBranchInfo(cardIssuer); // 选中的发卡机构
		
		this.cardBranchList = new ArrayList<BranchInfo>();
		// 如果登录机构为发卡机构
		if (isCardRoleLogined()) {
			// 发卡机构的顶级
			BranchInfo rootBranchInfo = this.branchInfoDAO.findRootByBranch(loginBranch.getBranchCode());
			
			// 领出机构列表
			cardBranchList.addAll(this.branchInfoDAO.findChildrenList(rootBranchInfo.getBranchCode()));
		}
		// 登录机构为售卡代理，即售卡代理领卡时
		else if (isCardSellRoleLogined()) {
			// 卡的领出机构为发卡机构和发卡机构的售卡代理，不包括自己
			this.cardBranchList.addAll(this.branchInfoDAO.findProxyByProxy(this.getLoginBranchCode()));
			this.cardBranchList.add(cardBranch);
		}
		// 商户领卡时
		else if (isMerchantRoleLogined()) {
			this.cardBranchList.add(cardBranch);
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (BranchInfo branchInfo : cardBranchList){
			sb.append("<option value=\"").append(branchInfo.getBranchCode())
						.append("\">").append(branchInfo.getBranchName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * 根据页面传入的参数，取得可领取的卡的起始卡号
	 * 
	 * @throws Exception
	 */
	public void getStrCardId() throws Exception {
		JSONObject object = new JSONObject();
		String stockStatus = request.getParameter("stockStatus");
		
//		String strNo = this.getStrNo(appReg.getCardSubClass(), appReg.getCardSectorId());
		try {
			String strNo = this.cardReceiveService.getStrNo(appReg.getCardSubClass(), 
					appReg.getCardSectorId(), appReg.getAppOrgId(), stockStatus, this.getSessionUser());
			if (StringUtils.isEmpty(strNo)) {
				object.put("success", false);
				object.put("errorMsg", "指定条件的卡已被全部领出，请更改条件！");
			} else {
				object.put("success", true);
				object.put("strCardId", strNo);
			}
		} catch (BizException e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		respond(object.toString());
	}
	
	/**
	 * 取得领入机构类型列表。针对于替别人领卡时
	 * @throws Exception
	 */
	public void getAppOrgType() throws Exception {
		String stockStatus = request.getParameter("stockStatus");
		if (StringUtils.isEmpty(stockStatus)) {
			return;
		}
		
		List<AppOrgType> typeList = new ArrayList<AppOrgType>();
		if (CardStockState.RECEIVED.getValue().equals(stockStatus)) {
			typeList = AppOrgType.getBranch();
		} else {
			typeList = AppOrgType.getDeptAndBranch();
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (AppOrgType type : typeList){
			sb.append("<option value=\"").append(type.getValue())
						.append("\">").append(type.getName()).append("</option>");
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * 检查输入的起始卡号
	 * @throws Exception
	 */
	public void checkUserId() throws Exception {
		String branchCode = request.getParameter("branchCode"); //卡的领出机构
		String appOrgId = request.getParameter("appOrgId"); // 卡的领入机构
		String strCardId = request.getParameter("strCardId"); //输入的起始卡号
		String stockStatus = request.getParameter("stockStatus"); //卡库存状态，如果选了的话，则是有值的
		String cardSubClass = appReg.getCardSubClass(); // 卡类型
		if (StringUtils.isEmpty(strCardId) || strCardId.length() < 18) {
			return;
		}
		
		JSONObject object = new JSONObject();
		
		try {
			// 卡库存状态不为空的话，要检查卡号是否符合选中的卡库存状态
			if (StringUtils.isNotEmpty(stockStatus)) {
				CardStockInfo info = this.cardStockInfoDAO.findCardStockInfoByCardId(strCardId);
				
				Assert.notNull(info, "卡库存表中没有卡号[" + strCardId + "]的记录");
				Assert.equals(info.getCardStatus(), stockStatus, "卡号[" + strCardId + "]的库存状态与选中的状态不符。");
			}
			
//			String defaultStrCardId = this.cardReceiveService.getStrNo(appReg.getCardSubClass(), branchCode, this.getSessionUser());
			String defaultStrCardId = this.cardReceiveService.getStrNo(appReg.getCardSubClass(), 
					branchCode, appOrgId, stockStatus, this.getSessionUser());
			if (StringUtils.equals(strCardId, defaultStrCardId)) { // 卡号所属的卡子类型前没有未领出的卡
				object.put("success", true);
				object.put("msg", "前面还有[0]张可领的卡");
				respond(object.toString());
				return;
			}

			CardInfo strCardInfo = (CardInfo) this.cardInfoDAO.findByPk(strCardId);
			Assert.notNull(strCardInfo, "卡号[" + strCardId + "]不存在");
			// 输入的起始卡号所属的卡子类型必须与选中的卡类型一致
			Assert.equals(cardSubClass, strCardInfo.getCardSubclass(), "卡号[" + strCardId + "]与选中的卡类型不一致");
			// 输入的起始卡号必须比默认的起始卡号大
			if (NumberUtils.toLong(strCardId) < NumberUtils.toLong(defaultStrCardId)) {
				throw new BizException("最小可领取的卡号为[" + defaultStrCardId + "]");
			}
			
			String status = "";
			String cardSector = "";
			// 1. 发卡机构领卡时
			if (isCardRoleLogined()) {
				//  领入机构号与登录机构号一致的时候是自己领卡，不一致时是替别人领卡
				if (StringUtils.equals(appOrgId, this.getLoginBranchCode())) {
					// 对于自己领卡。卡的发卡机构，领出机构一致时。
					if (StringUtils.equals(strCardInfo.getCardIssuer(), branchCode)) {
						// 卡的发卡机构，领出机构一致时。领入机构也相同时，只能领“在库”的卡 。发卡机构号要传进去
						if (StringUtils.equals(strCardInfo.getCardIssuer(), appOrgId)) {
							status = CardStockState.IN_STOCK.getValue();
						}
						// 领入机构不相同时，要选择库存状态为“在库”还是“已领卡”的卡 。
						else {
							Assert.notEmpty(stockStatus, "卡库存状态不能为空");
							status = stockStatus;
							if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
								cardSector = branchCode;
							}
						}
					} else {
						status = CardStockState.RECEIVED.getValue();
						cardSector = branchCode;
					}
				}
				// 对于替别人领卡的情况
				else {
					// 发卡机构为自己时，要选择卡库存状态
					if (StringUtils.equals(strCardInfo.getCardIssuer(), this.getLoginBranchCode())) {
						Assert.notEmpty(stockStatus, "卡库存状态不能为空");
						status = stockStatus;
						if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
							cardSector = branchCode;
						}
					} else {
						status = CardStockState.RECEIVED.getValue();
						cardSector = branchCode;
					}
				}
			}
			// 2. 商户领卡时
			else if (isMerchantRoleLogined()) {
				status = CardStockState.IN_STOCK.getValue();
			}
			// 售卡代理领卡时（1.从发卡机构领卡，2.从售卡代理领卡）
			else if (isCardSellRoleLogined()) {
				// 从卡的发卡机构领卡时，即卡的发卡机构和卡的领出机构一致时。只能领“卡在库”状态的卡
				if (StringUtils.equals(strCardInfo.getCardIssuer(), branchCode)) {
					status = CardStockState.IN_STOCK.getValue();
				} else {
					// 从售卡代理领卡时，只能领“已领出”的卡
					status = CardStockState.RECEIVED.getValue();
					cardSector = branchCode;
				}
			}
			long unReceive = this.cardStockInfoDAO.getInStockNum(defaultStrCardId, strCardId, status, cardSector);
			object.put("msg", "前面还有[" + (unReceive - 1) + "]张可领的卡");
			object.put("success", true);
		} catch (BizException e) {
			object.put("success", false);
			object.put("msg", e.getMessage());
		}
		respond(object.toString());
	}
	
	/**
	 * 新建领卡记录（领卡机构自己发申请）
	 */
	public String add() throws Exception {
		// 保存数据
		AppReg reg = cardReceiveService.addCardReceive(appReg, getSessionUser());

		String msg = LogUtils.r("ID为[{0}]的领卡申请已经提交。", reg.getId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardReceive/receive/list.do", msg);
		return SUCCESS;
	}

	/**
	 * 领卡审核列表
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (isCardRoleLogined()) {// 只有对从自己这里领走的卡做审核
			params.put("cardBranch", getSessionUser().getBranchNo());
			
		} else if (isCardSellRoleLogined()) {// 售卡代理做领卡审核，对从自己这里领走的卡做审核
			
		} else {
			throw new BizException("没有权限做领卡审核。");
		}
		params.put("cardSectorId", getSessionUser().getBranchNo());
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String ids[] = workflowService.getMyJob(WorkflowConstants.CARD_RECEIVE, getSessionUser());

		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		params.put("flag", ReceiveFlag.RECEIVE.getValue());
		params.put("ids", ids);
		page = appRegDAO.findAppRegPage(params, getPageNumber(), getPageSize());
		return CHECK_LIST;
	}

	/**
	 * 单个流程审核
	 */
	public String showCheck() throws Exception {
		appReg = (AppReg) appRegDAO.findByPk(appReg.getId());
		appReg.setCheckStrNo(appReg.getStrNo());
		appReg.setCheckCardNum(appReg.getCardNum());
		
		if (StringUtils.equals(appReg.getAppOrgType(), AppOrgType.BRANCH.getValue())) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(appReg.getAppOrgId());
			request.setAttribute("appOrgName", info.getBranchName());
		}
		else if (StringUtils.equals(appReg.getAppOrgType(), AppOrgType.DEPART.getValue())) {
			DepartmentInfo info = (DepartmentInfo) departmentInfoDAO.findByPk(appReg.getAppOrgId());
			request.setAttribute("appOrgName", info.getDeptName());
		}
		else if (StringUtils.equals(appReg.getAppOrgType(), AppOrgType.MERCH.getValue())) {
			MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(appReg.getAppOrgId());
			request.setAttribute("appOrgName", info.getMerchName());
		}
		return "check";
	}

	/**
	 * 审核起始卡号是否正确
	 */
	public void isCorrectCardNo() throws Exception {
		JSONObject object = new JSONObject();
		
		try {
			Assert.notEmpty(appReg.getStrNo(), "申请起始卡号不能为空");
			Long strNoSeq = NumberUtils.toLong(StringUtils.substring(appReg.getStrNo(), 11, 18));
			String strNoPrefix = StringUtils.substring(appReg.getStrNo(), 0, 11);
			
			Assert.notEmpty(appReg.getCheckStrNo(), "审核起始卡号不能为空");
			Long checkStrNoSeq = NumberUtils.toLong(StringUtils.substring(appReg.getCheckStrNo(), 11, 18));
			String checkStrNoPrefix = appReg.getCheckStrNo().substring(0, 11);
			
			Assert.isTrue(CardUtil.isValidCardId(appReg.getCheckStrNo()), "审核起始卡号[" + appReg.getCheckStrNo() + "]错误");
			Assert.equals(strNoPrefix, checkStrNoPrefix, "申请起始卡号和审核起始卡号必须是同一批次的");
			
			Assert.isTrue(checkStrNoSeq >= strNoSeq, "审核起始卡号不能小于申请起始卡号");
			Assert.isTrue(checkStrNoSeq <= (strNoSeq + appReg.getCardNum().longValue() - 1), "审核卡数量超出范围");
			
			object.put("isCorrectCardNo", true);
		} catch (Exception e) {
			object.put("isCorrectCardNo", false);
			object.put("errMsg", e.getMessage());
		}
		respond(object.toString());
	}

	/**
	 * 是否是正确的审核卡数量
	 */
	public void isCorrectCardNum() throws Exception {
		boolean isCorrectCardNum = false;
		if (StringUtils.isNotBlank(checkCardNum)
				&& StringUtils.isNotBlank(appReg.getCheckStrNo())) {
			Long strNoSeq = NumberUtils.toLong(appReg.getStrNo().substring(11, 18));
			Long checkStrNoSeq = NumberUtils.toLong(appReg.getCheckStrNo().substring(11, 18));
			isCorrectCardNum = ((checkStrNoSeq + NumberUtils.toLong(checkCardNum)) <= (strNoSeq + appReg.getCardNum().longValue()));
		}
		respond("{'isCorrectCardNum':" + isCorrectCardNum + "}");
	}

	/**
	 * 取得可领卡总数量，本次可领卡数量
	 */
	public void canReaciveCardNum() throws Exception {
		String branchCode = appReg.getCardSectorId(); //卡的领出机构
		String appOrgId = request.getParameter("appOrgId"); // 卡的领入机构号
		String strCardId = appReg.getStrNo(); //输入的起始卡号
		String cardSubClass = appReg.getCardSubClass(); // 卡类型
		String stockStatus = request.getParameter("stockStatus");
		if (StringUtils.isEmpty(strCardId)
				|| strCardId.length() < 18
				|| StringUtils.isEmpty(cardSubClass)
				|| StringUtils.isEmpty(branchCode)
				|| appReg.getCardNum() == null) { // 领卡数量
			return;
		}
		
		JSONObject object = new JSONObject();
		try {
			CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubClass);
			Assert.notNull(subClass, "卡类型[" + cardSubClass + "]已经不存在");
			BigDecimal faceValue = (subClass.getFaceValue() == null ? BigDecimal.ZERO : subClass.getFaceValue());

			// 可领出的卡的总数量和本次可领卡数量
//			long[] nums = this.cardReceiveService.canReaciveCardNum(cardSubClass, branchCode, strCardId, this.getSessionUser());
			long[] nums = this.cardReceiveService.canReaciveCardNum(cardSubClass, branchCode, appOrgId, stockStatus, strCardId, this.getSessionUser());
			
			object.put("canReaciveCardNum", nums[0]);
			object.put("thisTimeCanReacive", nums[1]);
			object.put("endCardId", CardUtil.getEndCard(strCardId, appReg.getCardNum()));
			object.put("appFaceValue", faceValue);
			object.put("appSumAmt", AmountUtil.multiply(faceValue, new BigDecimal(appReg.getCardNum())));
			
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		respond(object.toString());
	}
	
	/**
	 * 当前登陆用户角色为发卡机构或发卡机构网点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (isCardOrCardDeptRoleLogined()) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (isMerchantRoleLogined()) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

	/**
	 * 当前登录用户角色为售卡代理时
	 */
	public String getSellBranch() {
		String sellBranch = "";
		if (isCardSellRoleLogined()) {
			sellBranch = getSessionUser().getBranchNo();
		}
		return sellBranch;
	}
	
	public String getReceiveCardType() {
		String cardType = "";
		if (isMerchantRoleLogined()) {
			cardType = CardType.COUPON.getValue();
		}
		return cardType;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public AppReg getAppReg() {
		return appReg;
	}

	public void setAppReg(AppReg appReg) {
		this.appReg = appReg;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public List getFlagList() {
		return flagList;
	}

	public void setFlagList(List flagList) {
		this.flagList = flagList;
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

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public String getCheckCardNum() {
		return checkCardNum;
	}

	public void setCheckCardNum(String checkCardNum) {
		this.checkCardNum = checkCardNum;
	}

	public List<DepartmentInfo> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DepartmentInfo> deptList) {
		this.deptList = deptList;
	}

	public List getAppOrgTypeList() {
		return appOrgTypeList;
	}

	public void setAppOrgTypeList(List appOrgTypeList) {
		this.appOrgTypeList = appOrgTypeList;
	}

	public List<NameValuePair> getAppOrgList() {
		return appOrgList;
	}

	public void setAppOrgList(List<NameValuePair> appOrgList) {
		this.appOrgList = appOrgList;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public boolean isShowAppOrg() {
		return showAppOrg;
	}

	public void setShowAppOrg(boolean showAppOrg) {
		this.showAppOrg = showAppOrg;
	}

	public List<BranchInfo> getCardIssuerList() {
		return cardIssuerList;
	}

	public void setCardIssuerList(List<BranchInfo> cardIssuerList) {
		this.cardIssuerList = cardIssuerList;
	}

	public List<CardStockState> getStockStateList() {
		return stockStateList;
	}

	public void setStockStateList(List<CardStockState> stockStateList) {
		this.stockStateList = stockStateList;
	}

	public List<BranchInfo> getCardSellList() {
		return cardSellList;
	}

	public void setCardSellList(List<BranchInfo> cardSellList) {
		this.cardSellList = cardSellList;
	}

	public BigDecimal getAppFaceValue() {
		return appFaceValue;
	}

	public void setAppFaceValue(BigDecimal appFaceValue) {
		this.appFaceValue = appFaceValue;
	}

	public BigDecimal getAppSumAmt() {
		return appSumAmt;
	}

	public void setAppSumAmt(BigDecimal appSumAmt) {
		this.appSumAmt = appSumAmt;
	}
}
