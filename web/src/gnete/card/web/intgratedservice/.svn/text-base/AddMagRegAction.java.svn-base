package gnete.card.web.intgratedservice;

import flink.util.Paginater;
import gnete.card.dao.AddMagRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.AddMagReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.AddMagService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import gnete.etc.Constants;

public class AddMagRegAction extends BaseAction {

	@Autowired
	private AddMagRegDAO addMagRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	@Autowired
	private AddMagService addMagService;
	
	private String cardMagId;
	private Collection statusList;
	private Collection certTypeList;
	private AddMagReg addMagReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
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
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
		
		if (addMagReg != null) {
			params.put("addMagId", addMagReg.getAddMagId());
			params.put("cardId", addMagReg.getCardId());
		}
		this.page = this.addMagRegDAO.findAddMag(params, this
				.getPageNumber(), this.getPageSize());

		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.addMagReg = (AddMagReg) this.addMagRegDAO.findByPk(this.addMagReg.getAddMagId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.operatePrivilege();
		initPage();
		return ADD;
	}

	
	// 新增补磁信息
	public String add() throws Exception {		
		
		/*if(!validateCardExtraInfo(addMagReg.getCardId(), addMagReg.getCertType(), addMagReg.getCertNo())){
			throw new BizException("卡号与持卡人姓名、证件类型和证件号码不一致,请输入持卡人正确信息。");
		}*/
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		addMagReg.setBranchCode(branch.getBranchCode());
		addMagReg.setBranchName(branch.getBranchName());
		
		this.addMagService.addAddMag(addMagReg, this.getSessionUserCode());
		String msg = "新增磁卡["+this.addMagReg.getCardId()+"]补磁信息成功！";
		this.addActionMessage("/intgratedService/addmag/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws BizException {
		this.operatePrivilege();
		initPage();
		this.addMagReg = (AddMagReg) this.addMagRegDAO
		.findByPk(this.addMagReg.getAddMagId());

		return MODIFY;
	}

	// 修改
	public String modify() throws Exception {
		return SUCCESS;
	}

	private void initPage() {
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();

	}
	
	/* 
	 * 根据操作机构是否有权限操作该卡
	 */
	public void cardIdCheck() throws Exception {
		CardInfo cardInfo = null;
		try {
			String cardId = this.addMagReg.getCardId();
			cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号不存在,请重新输入。");
			
			UserInfo user = this.getSessionUser();
			
			if(user.getRole().getRoleType().equals(RoleType.CARD_SELL.getValue())){
				boolean flag = isCardSellPrivilege(cardInfo, user, Constants.ADD_MAG_PRIVILEGE_CODE);
				Assert.isTrue(flag, "该售卡代理没有权限为该卡补磁。");
			}
			else if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){
				String cardIssuer = cardInfo.getCardIssuer();
				Assert.equals(cardIssuer, user.getBranchNo(), "该发卡机构不是该卡的发行机构,不能补磁。");
			}
			else if(user.getRole().getRoleType().equals(RoleType.CARD_DEPT.getValue())){
				boolean flag = isCardDeptPrivilege(cardInfo, user);
				Assert.isTrue(flag, "该机构网点没有权限为该卡补磁。");
			}
			else{
				throw new BizException("非发卡机构、机构网点或者售卡代理不能补磁。");
			}
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(this.addMagReg.getCardId());
		if(cardExtraInfo!=null&&cardExtraInfo.getCustName()!=null&&
				cardExtraInfo.getCredType()!=null&&cardExtraInfo.getCredNo()!=null){
			this.respond("{'success':"+ true+ ", 'custName_hidden':'"+cardExtraInfo.getCustName()
					+ "','certType_hidden':'"+cardExtraInfo.getCredType()
					+ "','certNo_hidden':'"+cardExtraInfo.getCredNo() + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public AddMagReg getAddMagReg() {
		return addMagReg;
	}

	public void setAddMagReg(AddMagReg addMagReg) {
		this.addMagReg = addMagReg;
	}

	public String getCardMagId() {
		return cardMagId;
	}

	public void setCardMagId(String cardMagId) {
		this.cardMagId = cardMagId;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
