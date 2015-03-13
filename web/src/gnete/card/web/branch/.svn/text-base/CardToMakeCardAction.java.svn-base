package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardToMakeCardDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardToMakeCard;
import gnete.card.entity.CardToMakeCardKey;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发卡机构制卡厂商关系
 * 
 * @author aps-lib
 * @history 2011-6-29
 */
public class CardToMakeCardAction extends BaseAction{
	@Autowired
	private CardToMakeCardDAO cardToMakeCardDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private BranchService branchService;
	
	private CardToMakeCard cardToMakeCard;
	private Paginater page;
	private List<BranchInfo> branchList;
	private String branchCode;
	private String makeCard;
	private boolean showCard = false;
	private boolean showMakeCard = false;
	private String branchName = null;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardToMakeCard != null) {
			params.put("branchCode", cardToMakeCard.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardToMakeCard.getBranchName()));
			params.put("makeCardName", MatchMode.ANYWHERE.toMatchString(cardToMakeCard.getMakeCardName()));
			params.put("status", cardToMakeCard.getStatus());
			params.put("makeCard", cardToMakeCard.getMakeCard());
		}
		
		// 营运中心、中心部门
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
			this.showCard = true;
			this.showMakeCard = true;
		}
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.showCard = true;
			this.showMakeCard = true;
			branchList = new ArrayList<BranchInfo>();
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			
			if(CollectionUtils.isEmpty(branchList)){
				params.put("branchCode", " ");
			}
		}
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showMakeCard = true;
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		// 制卡厂商
		else if (RoleType.CARD_MAKE.getValue().equals(this.getLoginRoleType())){
			this.showCard = true;
			params.put("makeCard", this.getSessionUser().getBranchNo());
		} 
		else {
			throw new BizException("没有权限查询！");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		this.page = this.cardToMakeCardDAO.findCardToMakeCard(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、机构部门不能操作。");
		}
		
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
		Assert.notNull(branchInfo, "登录的发卡机构数据不存在！");
		this.branchName = branchInfo.getBranchName();
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.cardToMakeCard.setBranchCode(this.getSessionUser().getBranchNo());
		this.branchService.addCardToMakeCard(cardToMakeCard, this.getSessionUserCode());	
		String msg = "新增发卡机构[" + cardToMakeCard.getBranchCode() + 
				"]与制卡厂商[" + cardToMakeCard.getMakeCard() + "]关系对象成功。";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/pages/cardToMakeCard/list.do", msg);
		return SUCCESS;
	}
	
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、机构部门不能操作。");
		} 
		
		Assert.notNull(this.getBranchCode(), "发卡机构编号不能为空");
		Assert.notNull(this.getMakeCard(), "制卡机构编号不能为空");
		CardToMakeCardKey key = new CardToMakeCardKey();
		key.setBranchCode(this.getBranchCode());
		key.setMakeCard(this.getMakeCard());
		this.cardToMakeCard = (CardToMakeCard) this.cardToMakeCardDAO.findByPk(key);
		
		this.branchService.modifyCardToMakeCard(cardToMakeCard, this.getSessionUserCode());
		
		String msg = "修改发卡机构[" + cardToMakeCard.getBranchCode() + 
		"]与制卡厂商[" + cardToMakeCard.getMakeCard() + "]关系对象成功。";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/pages/cardToMakeCard/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&&
				!RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("非发卡机构、机构部门不能操作。");
		} 
		
		Assert.notNull(this.getBranchCode(), "发卡机构编号不能为空");
		Assert.notNull(this.getMakeCard(), "制卡机构编号不能为空");
		CardToMakeCardKey key = new CardToMakeCardKey();
		key.setBranchCode(this.getBranchCode());
		key.setMakeCard(this.getMakeCard());
		this.branchService.deleteCardToMakeCard(key);
		
		String msg = "删除发卡机构[" + this.getBranchCode() + 
		"]与制卡厂商[" + this.getMakeCard() + "]关系对象成功。";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/pages/cardToMakeCard/list.do", msg);
		return SUCCESS;
	}


	public CardToMakeCard getCardToMakeCard() {
		return cardToMakeCard;
	}

	public void setCardToMakeCard(CardToMakeCard cardToMakeCard) {
		this.cardToMakeCard = cardToMakeCard;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMakeCard() {
		return makeCard;
	}

	public void setMakeCard(String makeCard) {
		this.makeCard = makeCard;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowMakeCard() {
		return showMakeCard;
	}

	public void setShowMakeCard(boolean showMakeCard) {
		this.showMakeCard = showMakeCard;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
