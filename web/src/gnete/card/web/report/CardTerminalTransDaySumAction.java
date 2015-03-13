package gnete.card.web.report;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.util.DateUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 发卡机构-终端交易日汇总
 */
public class CardTerminalTransDaySumAction extends BaseAction {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	private List<BranchInfo> cardBranchList;
	
	
	@Override
	public String execute(){
		return null;
	}
	
	public String terminalTransDaySum() throws Exception {

		if (isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined()) {

		} else if (isCardOrCardDeptRoleLogined()) {
			cardBranchList = this.branchInfoDAO.findChildrenList(this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查看发卡机构终端汇总统计报表");
		}

		if (StringUtils.isNotBlank(this.getFormMapValue("settDate"))
				&& StringUtils.isNotBlank(this.getFormMapValue("cardBranchCode"))) {
			StringBuilder reportParams = new StringBuilder();
			reportParams.append("settDate=").append(this.getFormMapValue("settDate")).append(";");
			reportParams.append("currDate=").append(DateUtil.formatDate("yyyyMMdd")).append(";");
			BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(this.getFormMapValue("cardBranchCode"));
			reportParams.append("cardBranchCode=").append(cardBranch.getBranchCode()).append(";");
			reportParams.append("cardBranchName=").append(cardBranch.getBranchName()).append(";");
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "terminalTransDaySum";
	}
	
	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}
}
