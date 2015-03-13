package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.DepositPointRegDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.DepositPointReg;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointBusService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: PointDepositAction.java
 *
 * @description: 积分充值相关业务处理action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-18 下午06:03:19
 */
public class PointDepositAction extends BaseAction {
	
	@Autowired
	private DepositPointRegDAO depositPointRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private PointBusService pointBusService;
	
	private DepositPointReg depositPointReg;
	
	private Paginater page;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	/** 状态列表 */
	private List<RegisterState> statusList;
	/** 是否显示发卡机构列表 */
    private boolean showCardBranch = false;
    /** 发卡机构列表 */
    private List<BranchInfo> cardBranchList;
    /** 发卡机构名称 */
    private String cardBranchName;
    /** 开始日期 */
    private String startDate; 
    /** 结束日期 */
    private String endDate;
	
	/** 默认的列表页面 */
	private static final String DEFAULT_LIST_URL = "/pages/pointDeposit/list.do";
	
	/** 返回列表页面所需要带的参数 */
	private static final String SEARCH_PARAMS = "?goBack=goBack";

	@Override
	public String execute() throws Exception {
		
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (depositPointReg != null) {
			params.put("depositBatchId", depositPointReg.getDepositBatchId());
			params.put("depositCardId", MatchMode.ANYWHERE.toMatchString(depositPointReg.getCardId()));			
			params.put("listPtClass", MatchMode.ANYWHERE.toMatchString(depositPointReg.getPtClass()));			
			params.put("status", depositPointReg.getStatus());
			params.put("cardBranch", depositPointReg.getCardBranch()); //发卡机构
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showCardBranch = true;
		}
		// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			showCardBranch = true;
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 登录用户为售卡代理或发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("depositBranch", super.getSessionUser().getBranchNo());
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setBranchCode(this.getSessionUser().getMerchantNo());
			cardBranchList = new ArrayList<BranchInfo>();
			cardBranchList.add(branchInfo);
			params.put("cardBranchList", cardBranchList);
		} else {
			throw new BizException("没有权限查看充值记录");
		}
		
		params.put("isBatch", false); // 单笔
		
		this.page = this.depositPointRegDAO.findDepositPointRegPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询积分充值列表！");
		
		return LIST;
	}
	
	/**
	 * 单笔积分充值明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("depositBatchId", depositPointReg.getDepositBatchId());
		this.depositPointReg = this.depositPointRegDAO.findDepositPointCheckList(params).get(0);
		
		return DETAIL;
	}
	
	/**
	 * 单笔积分充值新增页面初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			
		}//  如果登录用户为商户时
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
		} else {
			throw new BizException("没有权限做积分充值操作！");
		}

		depositPointReg = new DepositPointReg();
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		if (signatureReg) {
    		// 随机数
    		this.depositPointReg.setRandomSessionid(Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request));
		} else {
			this.depositPointReg.setRandomSessionid("");
		}
    	
		return ADD;
	}
	
	/**
	 * 单笔积分充值新增
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		String serialNo = request.getParameter("serialNo");
		this.pointBusService.addDepositPointReg(depositPointReg, this.getSessionUser(), serialNo);
		
		String msg = LogUtils.r("用户[{0}]添加积分充值登记ID为[{1}]的记录成功", this.getSessionUserCode(), depositPointReg.getDepositBatchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_URL + SEARCH_PARAMS, msg);
		
		return SUCCESS;
	}
	
	/**
	 * 检查卡号
	 * @throws Exception
	 */
	public void checkCard() {
		JSONObject object = new JSONObject();
		
		String cardId = request.getParameter("cardId");
		String cardCount = request.getParameter("cardCount");
		try {
			CardInfo cardInfo = this.pointBusService.checkCardId(cardId, cardCount, this.getSessionUser());

			// 发卡机构和发卡机构网点和售卡代理
			if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
					|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
					|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
				object.put("cardIssuer", cardInfo.getCardIssuer());
			}//  如果登录用户为商户时
			else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
				object.put("cardIssuer", getSessionUser().getMerchantNo());
			}
			object.put("endCardId", cardInfo.getCardId());
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 根据卡号得到该卡的积分类型列表。（返回到页面为String字符串）
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String cardIssuer = request.getParameter("cardIssuer");
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		if (StringUtils.isNotEmpty(cardIssuer)) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("jinstId", cardIssuer); // 联名机构编号，为要充值的卡所属的发卡机构
			List<PointClassDef> ptClassList = this.pointClassDefDAO.findPontClassList(params);

			for (PointClassDef ptClass : ptClassList){
				sb.append("<option value=\"").append(ptClass.getPtClass()).append("\">")
						.append(ptClass.getPtClass()).append("-").append(ptClass.getClassName()).append("</option>");
			}
		}
		
		this.respond(sb.toString());
	}
	
	/**
	 * 根据卡号、积分类型，得到该卡的积分类型的积分余额
	 * @throws Exception
	 */
	public void calcCardOther() {
		String cardId = request.getParameter("cardId");
		String ptClass = request.getParameter("ptClass");
		if (StringUtils.isEmpty(cardId)
				|| StringUtils.isEmpty(ptClass)) {
			return;
		}
		JSONObject object = new JSONObject();
		
		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做积分充值");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在。");
			
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(ptClass);
			Assert.notNull(pointClassDef, "积分类型[" + ptClass + "]不存在");
			
			PointBalKey pointBalKey = new PointBalKey();
			
			pointBalKey.setAcctId(cardInfo.getAcctId());
			pointBalKey.setPtClass(ptClass);
			PointBal pointBal = (PointBal) pointBalDAO.findByPk(pointBalKey);
			if (pointBal != null) {
				object.put("avlPoint", pointBal.getPtAvlb()); //可用积分
			} else {
				object.put("avlPoint", "0.0");
			}
//			Assert.notNull(pointBal, "卡号[" + cardId + "]积分类型为[" + ptClass + "]的积分账户不存在。");

			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 根据卡号，积分类型号和充值积分，得到积分折算金额
	 * @throws Exception
	 */
	public void calRealAmt() {
		String cardId = request.getParameter("cardId");
		String ptClass = request.getParameter("ptClass");
		String point = request.getParameter("point");
		JSONObject object = new JSONObject();
		
		try {
			CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在。");
			Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "只有“正常(已激活)”状态的卡才可以做积分充值");
			
			AcctInfo acctInfo = (AcctInfo) this.acctInfoDAO.findByPk(cardInfo.getAcctId());
			Assert.notNull(acctInfo, "卡号[" + cardId + "]的账户不存在。");
			
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(ptClass);
			Assert.notNull(pointClassDef, "积分类型[" + ptClass + "]不存在");
			
			// 积分折算金额
			object.put("refAmt", AmountUtil.multiply(NumberUtils.createBigDecimal(point), pointClassDef.getPtDiscntRate()));
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public DepositPointReg getDepositPointReg() {
		return depositPointReg;
	}

	public void setDepositPointReg(DepositPointReg depositPointReg) {
		this.depositPointReg = depositPointReg;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
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

	public String getCardBranchName() {
		return cardBranchName;
	}

	public void setCardBranchName(String cardBranchName) {
		this.cardBranchName = cardBranchName;
	}

	public boolean isShowCardBranch() {
		return showCardBranch;
	}

	public void setShowCardBranch(boolean showCardBranch) {
		this.showCardBranch = showCardBranch;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}
}
