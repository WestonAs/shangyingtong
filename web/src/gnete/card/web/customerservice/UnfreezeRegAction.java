package gnete.card.web.customerservice;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.UnfreezeRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.UnfreezeReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UnfreezeRegService;
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
 * 卡账户解付
 * @author aps-lib
 *
 */
public class UnfreezeRegAction extends BaseAction {
	
	@Autowired
	private UnfreezeRegDAO unfreezeRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private UnfreezeRegService unfreezeRegService;

	private UnfreezeReg unfreezeReg;
	private Long unfreezeId;
	private Paginater page;
	private Collection subAcctTypeList;
//	private final static String PRIVILEGE = "0510";
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
		
		this.subAcctTypeList = SubacctType.getListWithoutIC();
		
		if (unfreezeReg != null) {
			params.put("unfreezeId", unfreezeReg.getUnfreezeId());
			params.put("cardId", unfreezeReg.getCardId());
			params.put("subAcctType", unfreezeReg.getSubacctType());
		}
		this.page = this.unfreezeRegDAO.findUnfreezeWithMultiParms(params, this.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.unfreezeReg = (UnfreezeReg) this.unfreezeRegDAO.findByPk(this.unfreezeReg.getUnfreezeId());

//		this.log("查询解付ID[" + this.unfreezeReg.getUnfreezeId() + "]明细信息成功", UserLogType.SEARCH);
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
		unfreezeReg.setBranchCode(branch.getBranchCode());
		unfreezeReg.setBranchName(branch.getBranchName());
		
		String acctId = ((CardInfo)this.cardInfoDAO.findByPk(this.unfreezeReg.getCardId())).getAcctId();
		this.unfreezeReg.setAcctId(acctId);
		
		this.unfreezeRegService.addUnfreeze(unfreezeReg,  this.getSessionUserCode());

		String msg = "解付登记成功！解付ID为[" + this.unfreezeReg.getUnfreezeId() + "]";
		this.addActionMessage("/unfreeze/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws BizException {
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		initPage();
		this.unfreezeReg = (UnfreezeReg) this.unfreezeRegDAO
		.findByPk(this.unfreezeReg.getUnfreezeId());

		return MODIFY;
	}
	
	// 修改
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.unfreezeRegService.modifyUnfreeze(this.unfreezeReg, this.getSessionUserCode());
		String msg = "修改解付信息成功，解付ID[" + this.unfreezeReg.getUnfreezeId() + "]！";
		this.addActionMessage("/unfreeze/list.do", msg);
		return SUCCESS;
	}
	
	// 删除挂失信息
	public String delete() throws Exception{
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		this.unfreezeRegService.delete(this.getUnfreezeId());
		String msg = "删除解付信息成功，解付ID[" + this.getUnfreezeId() + "]！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/unfreeze/list.do", msg);
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		return "select";
	}
	
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (unfreezeReg != null) {
			params.put("acctId", unfreezeReg.getAcctId());
			params.put("cardId", unfreezeReg.getCardId());
		}
		this.page = this.unfreezeRegDAO.findCardInfo(params, this.getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	private void initPage() {
		// 加载子账户类型
		this.subAcctTypeList = SubacctType.getListWithoutIC();
		
	}
	
	// 根据卡号和账户子类型找到冻结金额
	public void getFznAmt() throws Exception {
		CardInfo cardInfo = null;
		SubAcctBal subAcctBal = null;
		
		try {
			String cardId = this.unfreezeReg.getCardId();
			String subacctType = this.unfreezeReg.getSubacctType();
			Assert.notEmpty(subacctType, "请选择子账户类型。");
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
			SubAcctBalKey key = new SubAcctBalKey();
			key.setAcctId(cardInfo.getAcctId());
			key.setSubacctType(subacctType);
			subAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			Assert.notNull(subAcctBal, "子账户余额不存在。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡账户解付");
			
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		BigDecimal fznAmt = subAcctBal.getFznAmt();
		this.respond("{'success':"+ true +", 'fznAmt':'" + fznAmt.toString() + "'}");
	}
	
	// 校验输入卡号是否有效
	public void cardIdCheck() throws Exception {
		try {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.unfreezeReg.getCardId());
			Assert.notNull(cardInfo, "卡号不存在，请重新输入。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡账户解付");
			
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}
	
	public String getSubAcctList() throws Exception{
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.unfreezeReg.getCardId());
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
	
	public UnfreezeReg getUnfreezeReg() {
		return unfreezeReg;
	}

	public void setUnfreezeReg(UnfreezeReg unfreezeReg) {
		this.unfreezeReg = unfreezeReg;
	}

	public Long getUnfreezeId() {
		return unfreezeId;
	}

	public void setUnfreezeId(Long unfreezeId) {
		this.unfreezeId = unfreezeId;
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

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}


}
