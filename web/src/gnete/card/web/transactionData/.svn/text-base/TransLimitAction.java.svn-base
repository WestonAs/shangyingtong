package gnete.card.web.transactionData;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.TransLimitDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.TransLimit;
import gnete.card.entity.TransLimitKey;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.TransactionDataService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 交易禁止规则定义
 * @author aps-lib
 *
 */
public class TransLimitAction extends BaseAction{

	@Autowired
	private TransLimitDAO transLimitDAO;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private TransactionDataService transactionDataService;
	
	private TransLimit transLimit;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<CardTypeState> statusList; 
	private List<TransType> transTypeList;
	private String cardIssuer;
	private String merNo;
	private String cardBin;
	private String transType;
	private String merchs;
	private String cardBins;
	private String parent;
	
	private boolean showCenter = false;
	private boolean showFenZhi = false;
	private boolean showCard = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.statusList = CardTypeState.getList();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if ( transLimit != null) {
			params.put("merNo", transLimit.getMerNo());
			params.put("cardIssuer", transLimit.getCardIssuer());
			params.put("cardBin", transLimit.getCardBin());
			params.put("status", transLimit.getStatus());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(transLimit.getBranchName()));
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(transLimit.getMerchName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
//			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 发卡机构或者发卡机构部门
		else if (this.getLoginRoleType().equals(RoleType.CARD.getValue())||
				this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())){
			params.put("cardIssuer", this.getSessionUser().getBranchNo());
//			cardBranchList.add((BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
		} 
		// 商户
		else if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){
			params.put("merNo", this.getSessionUser().getMerchantNo());
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
//		if (CollectionUtils.isNotEmpty(cardBranchList)) {
//			params.put("cardIssuers", cardBranchList);
//		}
		
		this.page = this.transLimitDAO.findTransLimit(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	//取得业务权限明细
	public String detail() throws Exception {
		TransLimitKey key = new TransLimitKey();
		key.setCardBin(this.transLimit.getCardBin());
		key.setCardIssuer(this.transLimit.getCardIssuer());
		key.setMerNo(this.transLimit.getMerNo());
		if(StringUtils.isEmpty(this.transLimit.getTransType())){
			key.setTransType("  ");
		} else{
			key.setTransType(this.transLimit.getTransType());
		}
		this.transLimit = (TransLimit) this.transLimitDAO.findByPk(key);
		
		return DETAIL;
		
	}
	
	public String showModify() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())&&
				!RoleType.FENZHI.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非营运中心、营运中心部门、分支机构、发卡机构及其部门不能操作。");
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		TransLimitKey key = new TransLimitKey();
		key.setCardBin(this.getCardBin());
		key.setCardIssuer(this.getCardIssuer());
		key.setMerNo(this.getMerNo());
		if(StringUtils.isEmpty(this.getTransType())){
			key.setTransType("  ");
		} else{
			key.setTransType(this.getTransType());
		}
		this.transLimit = (TransLimit) this.transLimitDAO.findByPk(key);
		
		this.transactionDataService.modifyTransLimit(this.transLimit, this.getSessionUserCode());
		String msg = "修改发卡机构[" + this.transLimit.getCardIssuer()+ "],商户["+this.transLimit.getMerNo()+
		"]和卡BIN["+this.transLimit.getCardBin()+"]控制规则定义成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/transLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String showAdd() throws Exception {
		
		// 营运中心、中心部门
		if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCenter = true;
			this.showCard = false;
			this.showFenZhi = false;
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.showFenZhi = true;
			this.showCenter = false;
			this.showCard = false;
			this.parent = this.getSessionUser().getBranchNo();
		}
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			showCard = true;
			showCenter = false;
			this.showFenZhi = false;
			this.transLimit = new TransLimit();
			this.transLimit.setCardIssuer(this.getSessionUser().getBranchNo());
		} else{
			throw new BizException("非营运中心、营运中心部门、分支机构、发卡机构及发卡机构部门不能操作。");
		}
		this.transTypeList = TransType.getAll();
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		List<TransLimit> transLimitList = new ArrayList<TransLimit>();
		if (StringUtils.isNotEmpty(merchs)) {
			String[] merchArray = merchs.split(",");
			for (int j = 0; j < merchArray.length; j++) {
				if(StringUtils.isNotEmpty(cardBins)){ //卡BIN非空
					String[] cardBinArray = cardBins.split(",");
					for(int i = 0; i < cardBinArray.length; i++){
						TransLimit transLimit = new TransLimit();
						transLimit.setCardIssuer(this.transLimit.getCardIssuer());
						transLimit.setTransType(this.transLimit.getTransType());
						transLimit.setMerNo(merchArray[j]);
						transLimit.setCardBin(cardBinArray[i]);
						transLimitList.add(transLimit);
					}
				}
				else { //卡BIN为空
					TransLimit transLimit = new TransLimit();
					transLimit.setCardIssuer(this.transLimit.getCardIssuer());
					transLimit.setTransType(this.transLimit.getTransType());
					transLimit.setMerNo(merchArray[j]);
					transLimit.setCardBin("*");
					transLimitList.add(transLimit);
				}
			}
		}
		
		this.transactionDataService.addTransLimit(transLimitList.toArray(new TransLimit[transLimitList.size()]) , this.getSessionUserCode());	
		String msg = "新增发卡机构[" + this.transLimit.getCardIssuer()+ "交易控制规则定义成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/transLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	// 删除交易控制规则定义
	public String delete() throws Exception {
		TransLimitKey key = new TransLimitKey();
		key.setCardIssuer(this.getCardIssuer());
		key.setMerNo(this.getMerNo());
		key.setCardBin(this.getCardBin());
		if(StringUtils.isEmpty(this.getTransType())){
			key.setTransType("  ");
		} else{
			key.setTransType(this.getTransType());
		}
		
		this.transactionDataService.deleteTransLimit(key);
		String msg = "删除发卡机构["+this.getCardIssuer()+ "],商户["+this.getMerNo()+
			"]和卡BIN["+this.getCardBin()+"]的交易控制规则定义成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/transLimit/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public TransLimit getTransLimit() {
		return transLimit;
	}

	public void setTransLimit(TransLimit transLimit) {
		this.transLimit = transLimit;
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

	public List<CardTypeState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardTypeState> statusList) {
		this.statusList = statusList;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getMerchs() {
		return merchs;
	}

	public void setMerchs(String merchs) {
		this.merchs = merchs;
	}

	public String getCardBins() {
		return cardBins;
	}

	public void setCardBins(String cardBins) {
		this.cardBins = cardBins;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public List<TransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<TransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public boolean isShowFenZhi() {
		return showFenZhi;
	}

	public void setShowFenZhi(boolean showFenZhi) {
		this.showFenZhi = showFenZhi;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
