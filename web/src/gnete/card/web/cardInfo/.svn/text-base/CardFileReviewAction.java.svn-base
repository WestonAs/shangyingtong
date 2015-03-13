package gnete.card.web.cardInfo;

import flink.util.AmountUtil;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardTypeCode;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;
import gnete.card.service.GenerateFileService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardFileReviewAction.java
 *
 * @description: 卡档案查询（新）
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-16 下午06:04:57
 */
public class CardFileReviewAction extends BaseAction {
	
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private CardTypeCodeDAO cardTypeCodeDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private GenerateFileService generateFileService;
	
	private CardInfo cardInfo;
	private CardExtraInfo cardExtraInfo;
	private Paginater page;
    private CardTypeCode cardTypeCode;
    
    /** 卡状态 */
    private List<CardState> cardStatusList;
    /** 卡类型 */
    private List<CardType> cardTypeList;
    /** 其实卡号 */
    private String startCardId;
	/** 起始卡号 */
	private String endCardId;
	/** 外部号码 */
	private String externalCardId;
	/** 机构名称 */
	private String branchName;
	/** 发卡机构列表（发卡机构登录时提供选择） */
	private List<BranchInfo> cardBranchList;
	
	/** 查询最大的卡号数1000万，去掉校验位后的卡数量 */
	private static final BigDecimal MAX_SIZE = new BigDecimal(100000000);

	@Override
	public String execute() throws Exception {
		this.cardStatusList = CardState.getAll();
		this.cardTypeList = CardType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCenterOrCenterDeptRoleLogined()){// 运营中心，运营中心部门
			
		} else if(isFenzhiRoleLogined()) {// 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) {// 发卡机构
			this.cardBranchList = this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode());
			params.put("cardIssuers", this.cardBranchList);
			
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			BranchInfo loginBranch = this.branchInfoDAO.findBranchInfo(this.getSessionUser().getBranchNo());
			this.cardBranchList = Arrays.asList(loginBranch);
			params.put("appOrgId", this.getSessionUser().getDeptId());
			
		} else if(isCardSellRoleLogined()){// 售卡代理登录时
			this.cardBranchList = this.getMyCardBranch();
			params.put("appOrgId", this.getSessionUser().getBranchNo());
			
		} else if(isMerchantRoleLogined()){// 商户
			this.cardBranchList = this.getMyCardBranch();
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (cardInfo != null) {
			params.put("startCardId", this.getStartCardId()); // 起始卡号
			if (StringUtils.isNotBlank(this.getStartCardId())&&StringUtils.isBlank(this.getEndCardId()) ){
				params.put("endCardId", this.getStartCardId()); // 结束卡号与开始卡号一样
			}else{
				params.put("endCardId", this.getEndCardId()); // 结束卡号
			}
			params.put("cardIssuer", cardInfo.getCardIssuer()); // 发卡机构号
			params.put("cardBin", cardInfo.getCardBin()); // 卡BIN
			params.put("cardStatus", cardInfo.getCardStatus()); // 卡状态
			params.put("cardClass", cardInfo.getCardClass()); // 卡种类
			params.put("cardCustomerId", cardInfo.getCardCustomerId()); // 购卡客户ID
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(cardInfo.getExternalCardId())) {
				params.put("externalCardId", cardInfo.getExternalCardId()); // 外部号码
			}
			
			String startCardId = (String)params.get("startCardId");
			String endCardId = (String)params.get("endCardId");
			// 起始卡号和结束卡号都不为空时，要检查卡数量
			if (StringUtils.isNotBlank(startCardId) && StringUtils.isNotBlank(endCardId)) {
				if (AmountUtil.gt(AmountUtil.subtract(NumberUtils.createBigDecimal(endCardId), NumberUtils.createBigDecimal(startCardId)), MAX_SIZE)) {
					this.page = Paginater.EMPTY;
				} else {
					this.page = this.cardInfoDAO.findCardFile(params, this.getPageNumber(), this.getPageSize());
				}
			} 
			// 否则，发卡机构、卡BIN、外部号码 查询参数 必须有一个
			else if (StringUtils.isBlank(cardInfo.getCardIssuer())
					&& StringUtils.isBlank(cardInfo.getCardBin())
					&& StringUtils.isBlank(cardInfo.getExternalCardId())) {
				this.page = Paginater.EMPTY;
			} else {
				this.page = this.cardInfoDAO.findCardFile(params, this.getPageNumber(), this.getPageSize());
			}
		}
		
		return LIST;
	}
	
	// 明细页面,显示卡信息和持卡人详细信息
	public String detail() throws Exception{
		// 卡信息明细
		this.cardInfo = (CardInfo)this.cardInfoDAO.findByPk(this.cardInfo.getCardId());
		Assert.notNull(cardInfo, "卡号[" + this.cardInfo.getCardId() + "]不存在。");
		
		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.cardInfo.getCardId());
		
		// 卡类型
		this.cardTypeCode = (CardTypeCode)this.cardTypeCodeDAO.findByPk(this.cardInfo.getCardClass());
			
		return DETAIL;
	}
	
	/**
	 * 手动生成Excel文件
	 * @throws Exception
	 */
	public void generate() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardRoleLogined()) {// 发卡机构
			params.put("cardIssuers", this.branchInfoDAO.findTreeBranchList(this.getLoginBranchCode()));
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门
			params.put("appOrgId", this.getSessionUser().getDeptId());
		} else if (isCardSellRoleLogined()) {// 售卡代理
			params.put("appOrgId", this.getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("appOrgId", this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		if (cardInfo != null) {
			params.put("startCardId", this.getStartCardId()); // 起始卡号
			if (StringUtils.isNotBlank(this.getStartCardId())&&StringUtils.isBlank(this.getEndCardId()) ){
				params.put("endCardId", this.getStartCardId()); // 结束卡号与开始卡号一样
			}else{
				params.put("endCardId", this.getEndCardId()); // 结束卡号
			}
			params.put("cardIssuer", cardInfo.getCardIssuer()); // 发卡机构号
			params.put("cardBin", cardInfo.getCardBin()); // 卡BIN
			params.put("cardStatus", cardInfo.getCardStatus()); // 卡状态
			params.put("cardClass", cardInfo.getCardClass()); // 卡种类
			params.put("cardCustomerId", cardInfo.getCardCustomerId()); // 购卡客户ID
			if (isFormMapFieldTrue("useExternalCardSearch")
					&& StringUtils.isNotBlank(cardInfo.getExternalCardId())) {
				params.put("externalCardId", cardInfo.getExternalCardId()); // 外部号码
			}
			
			this.generateFileService.generateCardFileExcel(response, params);
		}
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public CardTypeCode getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(CardTypeCode cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public List<CardState> getCardStatusList() {
		return cardStatusList;
	}

	public void setCardStatusList(List<CardState> cardStatusList) {
		this.cardStatusList = cardStatusList;
	}

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public String getStartCardId() {
		return startCardId;
	}

	public void setStartCardId(String startCardId) {
		this.startCardId = startCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
