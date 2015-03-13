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
 * @description: 会员手续费日报表
 */
public class MembFeeDtotalAction extends BaseAction {
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	@Override
	public String execute(){
		return null;
	}
	
	/** 发卡机构会员手续费收益 */
	public String cardMembFeeEarning() throws Exception {

		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isCardOrCardDeptRoleLogined())) {
			throw new BizException("没有权限查看发卡机构会员手续费收益报表");
		}

		if (StringUtils.isNotBlank(this.getFormMapValue("startSettDate"))
				&& StringUtils.isNotBlank(this.getFormMapValue("endSettDate"))
				&& StringUtils.isNotBlank(this.getFormMapValue("cardBranchCode"))) {
			StringBuilder reportParams = new StringBuilder();
			reportParams.append("startSettDate=").append(this.getFormMapValue("startSettDate")).append(";");
			reportParams.append("endSettDate=").append(this.getFormMapValue("endSettDate")).append(";");
			reportParams.append("currDate=").append(DateUtil.formatDate("yyyyMMdd")).append(";");
			BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(this.getFormMapValue("cardBranchCode"));
			reportParams.append("cardBranchCode=").append(cardBranch.getBranchCode()).append(";");
			reportParams.append("cardBranchName=").append(cardBranch.getBranchName()).append(";");
			
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "cardMembFeeEarning";
	}
	
	/** 运营分支机构会员手续费收益 */
	public String fenzhiMembFeeEarning() throws Exception {

		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限查看运营分支机构会员手续费收益报表");
		}

		if (StringUtils.isNotBlank(this.getFormMapValue("startSettDate"))
				&& StringUtils.isNotBlank(this.getFormMapValue("endSettDate"))
				&& StringUtils.isNotBlank(this.getFormMapValue("branchCode"))) {
			StringBuilder reportParams = new StringBuilder();
			reportParams.append("startSettDate=").append(this.getFormMapValue("startSettDate")).append(";");
			reportParams.append("endSettDate=").append(this.getFormMapValue("endSettDate")).append(";");
			reportParams.append("currDate=").append(DateUtil.formatDate("yyyyMMdd")).append(";");
			BranchInfo cardBranch = (BranchInfo) branchInfoDAO.findByPk(this.getFormMapValue("branchCode"));
			reportParams.append("branchCode=").append(cardBranch.getBranchCode()).append(";");
			reportParams.append("branchName=").append(cardBranch.getBranchName()).append(";");
			
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "fenzhiMembFeeEarning";
	}
}
