package gnete.card.web.intgratedservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardExtraInfoService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class ChangePassWordAction extends BaseAction{

	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardExtraInfoService cardExtraInfoService;
	
	private CardInfo cardInfo;
	private Paginater page;
	List<BranchInfo> cardBranchList;
	private String newPassWord;
	private String newPassWordAgain;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardInfo != null) {
			params.put("cardId", cardInfo.getCardId());
			params.put("custName", cardInfo.getCustName());
			params.put("credNo", cardInfo.getCredNo());
			params.put("telNo", cardInfo.getTelNo());
			params.put("mobileNo", cardInfo.getMobileNo());
		}
		// 当前登录用户所属或所管理的发卡机构列表
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(branchInfoDAO
					.findCardByManange(getSessionUser().getBranchNo()));
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) branchInfoDAO
					.findByPk(getSessionUser().getBranchNo()));
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("cardIssuers", cardBranchList);
		}
					
		this.page = this.cardInfoDAO.findCardFile(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		String cardId = this.cardInfo.getCardId();
		this.cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		newPassWord = "";
		return MODIFY;
	}
	
	// 修改密码
	public String modify() throws Exception {
		this.cardInfo.setPassword(this.newPassWord);
		this.cardExtraInfoService.modifyCardPassWord(this.cardInfo, this.getSessionUserCode());
		String msg = "修改卡号["+this.cardInfo.getCardId()+"]密码信息成功！";
		this.addActionMessage("/intgratedService/changePassWord/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
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

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getNewPassWordAgain() {
		return newPassWordAgain;
	}

	public void setNewPassWordAgain(String newPassWordAgain) {
		this.newPassWordAgain = newPassWordAgain;
	}

}
