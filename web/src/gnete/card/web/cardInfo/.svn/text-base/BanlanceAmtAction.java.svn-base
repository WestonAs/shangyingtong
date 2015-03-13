package gnete.card.web.cardInfo;

import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.type.RoleType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BanlanceAmtAction extends BaseAction {
	@Autowired
	private CardInfoDAO cardInfoDAO;
	private BigDecimal strAvlbBal;
	private BigDecimal endAvlbBal;
	private String cardBranch;
	private Map<String, Object> map;

	@Override
	public String execute() throws Exception {

		// 如果登录用户为发卡机构或发卡机构部门时
		if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranch = getSessionUser().getBranchNo();
			if (!validateParams())
				return LIST;
			map = cardInfoDAO.findCardBanlanceAmt(strAvlbBal, endAvlbBal, cardBranch);
			if (map.get("totalAmt") == null) {
				map.put("totalAmt", BigDecimal.ZERO);
			}
		} else {
			throw new BizException("只有发卡机构或发卡部门才有权限查询。");
		}
		return LIST;
	}

	private boolean validateParams() {
		return strAvlbBal != null && endAvlbBal != null
				&& StringUtils.isNotEmpty(cardBranch);
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public BigDecimal getStrAvlbBal() {
		return strAvlbBal;
	}

	public void setStrAvlbBal(BigDecimal strAvlbBal) {
		this.strAvlbBal = strAvlbBal;
	}

	public BigDecimal getEndAvlbBal() {
		return endAvlbBal;
	}

	public void setEndAvlbBal(BigDecimal endAvlbBal) {
		this.endAvlbBal = endAvlbBal;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
