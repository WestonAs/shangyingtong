package gnete.card.web.intgratedservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.UnLossCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UnLossCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UnLossCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 卡解挂
 * @author aps-lib
 *
 */
public class UnLossCardRegAction extends BaseAction {

	@Autowired
	private UnLossCardRegDAO unlossCardRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private UnLossCardService unLosscardService;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;

	private String cardId;
	private Long unlossBatchId;
	private List statusList;
	private Collection certTypeList;
	private UnLossCardReg unlossCardReg;
	private Paginater page;
	private String custName;
	private List<BranchInfo> cardBranchList;

	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			if (CollectionUtils.isEmpty(cardBranchList)) {
				cardBranchList.add(new BranchInfo());
			}
		} else if (isCardOrCardDeptRoleLogined()) {// 如果登录用户为发卡机构或发卡机构部门时
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		} else if (isCardSellRoleLogined()) {// 售卡代理
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if (unlossCardReg != null){
			params.put("unlossBatchId", unlossCardReg.getUnlossBatchId());
			params.put("cardId", unlossCardReg.getCardId());
			params.put("custName", MatchMode.ANYWHERE.toMatchString(unlossCardReg.getCustName()));
		}
		
		this.page = this.unlossCardRegDAO.findUnLossCard(params, this
				.getPageNumber(), this.getPageSize());

		return LIST;
	}
	
	/** 明细页面 */
	public String detail() throws Exception {
		this.unlossCardReg = (UnLossCardReg) this.unlossCardRegDAO.findByPk(this.unlossCardReg
				.getUnlossBatchId());
		return DETAIL;
	}

	/** 打开新增页面的初始化操作 */
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		this.operatePrivilege();
		initPage();
		return ADD;
	}

	
	/** 新增信息 */
	public String add() throws Exception {	
		this.checkEffectiveCertUser();
		
		/*if(!validateCardExtraInfo(unlossCardReg.getCardId(), unlossCardReg.getCertType(), unlossCardReg.getCertNo())){
			throw new BizException("卡号与持卡人姓名、证件类型和证件号码不一致,请输入持卡人正确信息。");
		}*/
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		unlossCardReg.setBranchCode(branch.getBranchCode());
		unlossCardReg.setBranchName(branch.getBranchName());
		
		this.unLosscardService.addUnLossCard(unlossCardReg, this.getSessionUserCode());
		String msg = "新增磁卡[" + this.unlossCardReg.getCardId() + "]解挂信息成功！";
		this.addActionMessage("/intgratedService/unlosscard/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	private void initPage() {
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();
	}

	/**
	 * 根据操作机构是否有权限操作该卡
	 */
	public void cardIdCheck() throws Exception {
		CardInfo cardInfo = null;
		try {
			String cardId = this.unlossCardReg.getCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡解挂");
			
			// 判断输入卡号的卡状态是否有效
			Assert.equals(cardInfo.getCardStatus(), CardState.LOSSREGISTE.getValue(), "该卡[" + cardInfo.getCardId() + "]状态为[" + 
					CardState.valueOf(cardInfo.getCardStatus()).getName() +"], 非挂失的卡不能解挂。");
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.unlossCardReg.getCardId());
		if(cardExtraInfo!=null){
			this.respond("{'success':"+ true+ ", 'custName_hidden':'"+getStr(cardExtraInfo.getCustName())
					+ "','certType_hidden':'"+getStr(cardExtraInfo.getCredType())
					+ "','certNo_hidden':'"+getStr(cardExtraInfo.getCredNo()) + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}
	
	private String getStr(String str){
		return StringUtils.isNotEmpty(str) ? str : "";
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Long getUnlossBatchId() {
		return unlossBatchId;
	}

	public void setUnlossBatchId(Long unlossBatchId) {
		this.unlossBatchId = unlossBatchId;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}

	public UnLossCardReg getUnlossCardReg() {
		return unlossCardReg;
	}

	public void setUnlossCardReg(UnLossCardReg unlossCardReg) {
		this.unlossCardReg = unlossCardReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}


}
