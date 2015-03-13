package gnete.card.web.customerservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.CardDeferBatRegDAO;
import gnete.card.dao.CardDeferRegDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardDeferReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.ExpirMthdType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardDeferRegService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 批量卡延期
 * @author aps-lib
 *
 */
public class CardDeferBatAction extends BaseAction {

	@Autowired
	private CardDeferRegDAO cardDeferRegDAO;
	@Autowired
	private CardDeferBatRegDAO cardDeferBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardDeferRegService cardDeferRegService;
	
	private CardDeferReg cardDeferReg;
	private Paginater page;
	private Paginater batPage;

	private List<BranchInfo> cardBranchList;
	private boolean showCard = false;
	private boolean showSellProxy = false;
	private int cardNum = 0;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardDeferReg != null) {
			params.put("cardDeferId", cardDeferReg.getCardDeferId());
			params.put("branchCode", cardDeferReg.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardDeferReg.getBranchName()));
		}

		// 如果登录用户为
		if (isCenterOrCenterDeptRoleLogined()){ // 运营中心，运营中心部门
			
		} else if(isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else if (isCardRoleLogined()) {//发卡机构
			params.put("cardIssuers", this.getMyCardBranch());
		} else if (isCardDeptRoleLogined()) {// 发卡机构部门时
			params.put("branchCode", this.getSessionUser().getDeptId());
		} else if(isCardSellRoleLogined()){// 售卡代理
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		params.put("isBatch", true); // 是批量
		this.page = this.cardDeferRegDAO.findCardDeferPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;

	}
	
	/**
	 * 卡延期明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		this.cardDeferReg = (CardDeferReg)this.cardDeferRegDAO.findByPk(cardDeferReg.getCardDeferId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardDeferId", cardDeferReg.getCardDeferId());
		this.batPage = this.cardDeferBatRegDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(isCenterOrCenterDeptRoleLogined()){//营运中心、中心部门
			
		} else if(isCardOrCardDeptRoleLogined()){//发卡机构、机构网点
			this.showCard = true;
			this.showSellProxy = false;
		} else if(isCardSellRoleLogined()){//售卡代理
			this.showSellProxy = true;
			this.showCard = false;
		} else {
			throw new BizException("非营运中心、中心部门、发卡机构、机构网点或者售卡代理不能操作。");
		}
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {			
		this.cardDeferRegService.addCardDeferBat(cardDeferReg, this.getSessionUser());
		String msg = "批量卡延期登记成功！延期批次为[" + this.cardDeferReg.getCardDeferId() + "]";
		this.addActionMessage("/cardDeferBat/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 校验起始卡号是否输入正确
	public void checkStartCardId() throws Exception {
		String startCard = this.cardDeferReg.getStartCard();
		CardInfo startCardInfo = null;
		try {
			startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
			Assert.notNull(startCardInfo, "起始卡号不存在。");
		}
		catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true + "}");
	}
	
	// 校验批量延期卡号是否输入正确
	public void checkCardId() throws Exception {
		String startCard = this.cardDeferReg.getStartCard();
		String endCard = this.cardDeferReg.getEndCard();
		
		JSONObject object = new JSONObject();
		try {
			// 检查起始卡号和结束卡号是否存在
			CardInfo startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
			Assert.notNull(startCardInfo, "起始卡号[" + startCard + "]不存在。");
			CardInfo endCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(endCard);
			Assert.notNull(endCardInfo, "结束卡号[" + endCard + "]不存在。");
			
			// 检查起始卡号和结束卡号的卡BIN是否一致
			Assert.equals(startCardInfo.getCardBin(), endCardInfo.getCardBin(), 
					"起始卡号的卡BIN[" + startCardInfo.getCardBin() + "]与结束卡号的卡BIN[" + endCardInfo.getCardBin() + "]不一致，不能延期");
			
			// 取得起始卡号和结束卡号之间的卡列表
	    	this.cardNum = this.cardInfoDAO.getCardNum(startCard,endCard).intValue();
			Assert.notTrue(this.cardNum <= 0 , "结束卡号要大于起始卡号。");
			Assert.notTrue(this.cardNum > 1000 , "不能超过1000张卡。");

			List<CardInfo> cardList = this.cardInfoDAO.getCardList(startCard, endCard);
			// 检查卡的可延期次数、卡的状态、卡的失效日期
			for(CardInfo card : cardList){
				Assert.notTrue(card.getExtenLeft() == 0, "卡号[" + card.getCardId() + "]剩余延期次数为0, 不能延期。");
				Assert.notEmpty(card.getExpirDate(), "卡号[" + card.getCardId() + "的失效日期为空, 不能延期");
				CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(card.getCardSubclass());
				
				Assert.notNull(cardSubClassDef, "卡号[" + card.getCardId() + "]所属的卡类型不存在");
				
				// 指定失效日期，到该日期失效，已制卡、已入库、已领卡待售、预售出、正常、已过期的卡能够延期
				if(ExpirMthdType.EXPIR_DATE.getValue().equals(cardSubClassDef.getExpirMthd())){
					List<String> list = new ArrayList<String>();
					list.add(CardState.MADED.getValue());
					list.add(CardState.STOCKED.getValue());
					list.add(CardState.FORSALE.getValue());
					list.add(CardState.PRESELLED.getValue());
					list.add(CardState.ACTIVE.getValue());
					list.add(CardState.EXCEEDED.getValue());
					Assert.isTrue(list.contains(card.getCardStatus()), 
							"卡号[" + card.getCardId() + "]指定了失效日期，卡状态不能为[" + card.getCardStatusName() + "], 不能延期。");
				} 
				// 指定有效期（月数），从售卡日起，经过该有效期月数失效，正常、已过期的卡能够延期
				else if(ExpirMthdType.EXPIR_MONTH.getValue().equals(cardSubClassDef.getExpirMthd())){
//					boolean flag = card.getCardStatus().equals(CardState.ACTIVE.getValue())||
//					card.getCardStatus().equals(CardState.EXCEEDED.getValue());
					
					List<String> list = new ArrayList<String>();
					
					list.add(CardState.ACTIVE.getValue());
					list.add(CardState.EXCEEDED.getValue());
					Assert.isTrue(list.contains(card.getCardStatus()), 
							"卡号[" + card.getCardId() + "]指定了售卡后失效月数，卡状态不能为[" + card.getCardStatusName() + "], 不能延期。");
				}
				
				//检查登录机构是否有权限
				CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleType(), this.getLoginBranchCode(), card, "卡延期");
				
			}
			object.put("cardNum", cardNum);
			object.put("success", true);
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		this.respond(object.toString());
	}

	public CardDeferReg getCardDeferReg() {
		return cardDeferReg;
	}

	public void setCardDeferReg(CardDeferReg cardDeferReg) {
		this.cardDeferReg = cardDeferReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public Paginater getBatPage() {
		return batPage;
	}

	public void setBatPage(Paginater batPage) {
		this.batPage = batPage;
	}
	
	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowSellProxy() {
		return showSellProxy;
	}

	public void setShowSellProxy(boolean showSellProxy) {
		this.showSellProxy = showSellProxy;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

}
