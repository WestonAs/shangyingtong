package gnete.card.web.cardInfo;

import flink.util.Paginater;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.type.RoleType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 新旧卡号对照查询
 * @author aps-lib
 *
 */
public class RenewCardAction extends BaseAction{

	@Autowired
	private RenewCardRegDAO renewCardRegDAO;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	
	private RenewCardReg renewCardReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	
	// 默认方法显示列表页面
	public String execute() throws Exception {
			
		Map<String, Object> params = new HashMap<String, Object>();
		if (renewCardReg != null) {
			params.put("cardIdSearch", renewCardReg.getCardId());
		}
		
		// 如果登录用户为运营中心，运营中心部门时
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())){
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("cardIssuers", this.getMyCardBranch());
		}
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else{
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.renewCardRegDAO.findRenewCard(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;

	}
	
	// 明细页面
	public String detail() throws Exception{
		
		this.renewCardReg = (RenewCardReg) this.renewCardRegDAO.findByPk(this.renewCardReg.getRenewCardId());
		logger.debug("查询新旧卡号[" + this.renewCardReg.getRenewCardId() + "]对照明细信息");
		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public RenewCardReg getRenewCardReg() {
		return renewCardReg;
	}

	public void setRenewCardReg(RenewCardReg renewCardReg) {
		this.renewCardReg = renewCardReg;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

}
