package gnete.card.web.customerservice;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.FreezeRegDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.FreezeReg;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.FreezeRegService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 卡账户冻结
 * @author aps-lib
 *
 */
public class FreezeRegAction extends BaseAction {
	
	@Autowired
	private FreezeRegDAO freezeRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private FreezeRegService freezeRegService;
	
	private FreezeReg freezeReg;
	private Long freezeId;
	private Paginater page;
	private Collection subAcctTypeList;
	private String branchCode;
	private List<BranchInfo> cardBranchList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
			
		} else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardBranch", this.getLoginBranchCode());
			
		} else if (isCardSellRoleLogined()) {
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
			
		} else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		this.subAcctTypeList = SubacctType.getAll();
		
		if (freezeReg != null) {
			params.put("freezeId", freezeReg.getFreezeId());
			params.put("cardId", freezeReg.getCardId());
			params.put("subAcctType", freezeReg.getSubacctType());
		}
		this.page = this.freezeRegDAO.findFreezeWithMultiParms(params,
				this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.freezeReg = (FreezeReg) this.freezeRegDAO
				.findByPk(this.freezeReg.getFreezeId());

		this.log("查询冻结卡ID[" + this.freezeReg.getFreezeId() + "]明细信息成功",
				UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		initPage();
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {	
		this.checkEffectiveCertUser();
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		freezeReg.setBranchCode(branch.getBranchCode());
		freezeReg.setBranchName(branch.getBranchName());
		String acctId = ((CardInfo)this.cardInfoDAO.findByPk(this.freezeReg.getCardId())).getAcctId();
		freezeReg.setAcctId(acctId);
		
		this.freezeRegService.addFreeze(freezeReg,  this	.getSessionUserCode());

		String msg = "冻结登记成功！冻结ID为[" + this.freezeReg.getFreezeId() + "]";
		this.addActionMessage("/freeze/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws BizException {
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		initPage();
		this.freezeReg = (FreezeReg) this.freezeRegDAO.findByPk(this.freezeReg.getFreezeId());
		return MODIFY;
	}
	
	// 修改
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.freezeRegService.modifyFreeze(this.freezeReg, this.getSessionUserCode());
		String msg = "修改冻结信息成功，冻结ID[" + this.freezeReg.getFreezeId() + "]！";
		this.addActionMessage("/freeze/list.do", msg);
		return SUCCESS;
	}
	
	// 删除挂失信息
	public String delete() throws Exception{
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		this.freezeRegService.delete(this.getFreezeId());
		String msg = "删除冻结信息成功，冻结ID[" + this.getFreezeId() + "]！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/freeze/list.do", msg);
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (freezeReg != null) {
			params.put("acctId", freezeReg.getAcctId());
			params.put("cardId", freezeReg.getCardId());
		}
		params.put("branchCode", branchCode);
		this.page = this.freezeRegDAO.findCardInfo(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	private void initPage() {
		
		// 加载子账户类型
		this.subAcctTypeList = SubacctType.getAll();
		
	}
	
	// 根据卡号找到原冻结金额
	public void getFznAmt() throws Exception {
		CardInfo cardInfo = null;
		SubAcctBal subAcctBal = null;
		
		try {
			String cardId = this.freezeReg.getCardId();
			String subacctType = this.freezeReg.getSubacctType();
			Assert.notEmpty(subacctType, "请选择子账户类型。");
			
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			SubAcctBalKey key = new SubAcctBalKey();
			key.setAcctId(cardInfo.getAcctId());
			key.setSubacctType(subacctType);
			subAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			Assert.notNull(subAcctBal, "子账户余额不存在。");
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		BigDecimal fznAmt = subAcctBal.getFznAmt();
		BigDecimal avlbBal = subAcctBal.getAvlbBal();
		this.respond("{'success':"+ true +", 'fznAmt':'" + fznAmt.toString()
				+ "', 'avlbBal':'" + avlbBal.toString() +"'}");
	}
	
	// 校验输入卡号是否有效
	public void cardIdCheck() throws Exception {
		try {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.freezeReg.getCardId());
			Assert.notNull(cardInfo, "卡号不存在，请重新输入。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡账户冻结");
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}
	
	public String getSubAcctList() throws Exception{
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.freezeReg.getCardId());
		String cardClass = cardInfo.getCardClass();
		
		// 赠券卡
		if(CardType.COUPON.getValue().equals(cardClass)){
			this.subAcctTypeList = SubacctType.getCouponAcct();
		}
		// 非赠券卡
		else{
			this.subAcctTypeList = SubacctType.getNotCouponAcct();
		}
		return "subAcctList";
	}
	
	public Long getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getSubAcctTypeList() {
		return subAcctTypeList;
	}

	public void setSubAcctTypeList(Collection subAcctTypeList) {
		this.subAcctTypeList = subAcctTypeList;
	}

	public FreezeReg getFreezeReg() {
		return freezeReg;
	}

	public void setFreezeReg(FreezeReg freezeReg) {
		this.freezeReg = freezeReg;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}


}
