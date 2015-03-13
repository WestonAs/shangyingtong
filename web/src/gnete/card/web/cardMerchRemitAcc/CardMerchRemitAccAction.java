package gnete.card.web.cardMerchRemitAcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardMerchRemitAccountDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardMerchRemitAccount;
import gnete.card.entity.CardMerchRemitAccountKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.type.DayOfMonthType;
import gnete.card.entity.type.DayOfWeekType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.XferType;
import gnete.card.service.CardMerchRemitAccService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * @File: CardMerchRemitAccAction.java
 *
 * @description: 划付参数设置
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiZhenBin
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-5-16 下午04:50:10
 */
public class CardMerchRemitAccAction extends BaseAction{

	@Autowired
	private CardMerchRemitAccountDAO cardMerchRemitAccountDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardMerchRemitAccService cardMerchRemitAccService;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private CardMerchRemitAccount cardMerchRemitAccount;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchList;
	private List xferTypeList;
	private List dayOfWeekTypeList;
	private List dayOfMonthTypeList;
	private String[] dayOfCycle;
	private boolean showCard = false;
	private boolean showMerch = false;
	private String branchCode;
	private String merchId;
	
	@Override
	public String execute() throws Exception {
		
		this.xferTypeList = XferType.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (cardMerchRemitAccount != null) {
			params.put("branchCode", cardMerchRemitAccount.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardMerchRemitAccount.getBranchName()));
			params.put("merchId", cardMerchRemitAccount.getMerchId());
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(cardMerchRemitAccount.getMerchName()));
			params.put("xferType", cardMerchRemitAccount.getXferType());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			showCard = true;
			branchList = this.getMyCardBranch();
			params.put("branchCode", branchList.get(0).getBranchCode());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			showMerch = true;
			merchList = this.getMyMerch();
			params.put("merchId", merchList.get(0).getMerchId());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("branchCodes", cardBranchList);
		}
		
		this.page = this.cardMerchRemitAccountDAO.findRemitAccount(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		CardMerchRemitAccountKey key = new CardMerchRemitAccountKey();
		key.setBranchCode(this.cardMerchRemitAccount.getBranchCode());
		key.setMerchId(this.cardMerchRemitAccount.getMerchId());
		
		this.cardMerchRemitAccount = (CardMerchRemitAccount) this.cardMerchRemitAccountDAO.findByPk(key);
		BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.cardMerchRemitAccount.getBranchCode());
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(this.cardMerchRemitAccount.getMerchId());
		this.cardMerchRemitAccount.setBranchName(branchInfo!=null?branchInfo.getBranchName():"");
		this.cardMerchRemitAccount.setMerchName(merchInfo!=null?merchInfo.getMerchName():"");
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		// 发卡机构或者机构网点
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			
			this.cardMerchRemitAccount = new CardMerchRemitAccount();
			this.cardMerchRemitAccount.setBranchCode(this.getSessionUser().getBranchNo());
		}
		else {
			throw new BizException("没有权限设置划账参数。");
		}
		this.xferTypeList = XferType.getAll();
		this.dayOfWeekTypeList = DayOfWeekType.getAll();
		this.dayOfMonthTypeList = DayOfMonthType.getAllWithOutEveryDay();
		return ADD;
	}
	
	// 新增划账参数设置
	public String add() throws Exception {		
		
		if(XferType.LIMIT_REMIT.getValue().equals(this.cardMerchRemitAccount.getXferType())){
			this.cardMerchRemitAccount.setDayOfCycle("0");
		}
		/*else{
			String dayOfCycle = this.cardMerchRemitAccount.getDayOfCycle();
			this.cardMerchRemitAccount.setDayOfCycle(dayOfCycle+",");
		}*/
		
		//保存数据
		this.cardMerchRemitAccService.addCardMerchRemitAccount(this.cardMerchRemitAccount, this.getSessionUserCode());
		
		String msg = "新增发卡机构["+this.cardMerchRemitAccount.getBranchCode()+
		"]和商户["+this.cardMerchRemitAccount.getMerchId()+"]的划账参数成功！";
		this.addActionMessage("/cardMerchRemitAcc/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			
			CardMerchRemitAccountKey key = new CardMerchRemitAccountKey();
			key.setBranchCode(this.cardMerchRemitAccount.getBranchCode());
			key.setMerchId(this.cardMerchRemitAccount.getMerchId());
			
			this.cardMerchRemitAccount = (CardMerchRemitAccount) this.cardMerchRemitAccountDAO.findByPk(key);
			/*if(this.cardMerchRemitAccount!=null & this.cardMerchRemitAccount.getXferType()!=XferType.LIMIT_REMIT.getValue()){
				String str = this.cardMerchRemitAccount.getDayOfCycle();
				str = str.substring(0, str.length()-1);
				this.cardMerchRemitAccount.setDayOfCycle(str);
			}*/
		}
		else {
			throw new BizException("没有权限修改划账参数。");
		}
		
		this.xferTypeList = XferType.getAll();
		this.dayOfWeekTypeList = DayOfWeekType.getAll();
		this.dayOfMonthTypeList = DayOfMonthType.getAllWithOutEveryDay();
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		
		if(XferType.LIMIT_REMIT.getValue().equals(this.cardMerchRemitAccount.getXferType())){
			this.cardMerchRemitAccount.setDayOfCycle("0");
		}
		/*else{
			String dayOfCycle = this.cardMerchRemitAccount.getDayOfCycle();
			this.cardMerchRemitAccount.setDayOfCycle(dayOfCycle+",");
		}*/
		
		this.cardMerchRemitAccService.modifyCardMerchRemitAccount(this.cardMerchRemitAccount, this.getSessionUserCode());
		String msg = "修改发卡机构["+this.cardMerchRemitAccount.getBranchCode()+
		"]和商户["+this.cardMerchRemitAccount.getMerchId()+"]的划账参数成功！";
		this.addActionMessage("/cardMerchRemitAcc/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		CardMerchRemitAccountKey key = new CardMerchRemitAccountKey();
		key.setBranchCode(this.getBranchCode());
		key.setMerchId(this.getMerchId());
		this.cardMerchRemitAccService.deleteCardMerchRemitAccount(key);
		String msg = "删除划账参数成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/cardMerchRemitAcc/list.do", msg);
		return SUCCESS;
	}

	public CardMerchRemitAccount getCardMerchRemitAccount() {
		return cardMerchRemitAccount;
	}

	public void setCardMerchRemitAccount(CardMerchRemitAccount cardMerchRemitAccount) {
		this.cardMerchRemitAccount = cardMerchRemitAccount;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public List getXferTypeList() {
		return xferTypeList;
	}

	public void setXferTypeList(List xferTypeList) {
		this.xferTypeList = xferTypeList;
	}

	public List getDayOfWeekTypeList() {
		return dayOfWeekTypeList;
	}

	public void setDayOfWeekTypeList(List dayOfWeekTypeList) {
		this.dayOfWeekTypeList = dayOfWeekTypeList;
	}

	public List getDayOfMonthTypeList() {
		return dayOfMonthTypeList;
	}

	public void setDayOfMonthTypeList(List dayOfMonthTypeList) {
		this.dayOfMonthTypeList = dayOfMonthTypeList;
	}

	public String[] getDayOfCycle() {
		return dayOfCycle;
	}

	public void setDayOfCycle(String[] dayOfCycle) {
		this.dayOfCycle = dayOfCycle;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

}
