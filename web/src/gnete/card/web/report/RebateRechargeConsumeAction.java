package gnete.card.web.report;

import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;

/***
 * @description: 返利账户充值消费统计报表
 * @author luoxq
 *
 */
public class RebateRechargeConsumeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922205292570817848L;
	
	@Override
	public String execute(){
		return null;
	}
	
	public String gather() throws Exception {
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		}else if(isFenzhiRoleLogined()){
			
		}else if(isCardRoleLogined()){
			
		}else{
			throw new BizException("没有权限返利账户充值消费汇总统计报表");
		}
		if (StringUtils.isNotBlank(this.getFormMapValue("settMonth"))) {	
			StringBuilder reportParams = new StringBuilder();
			if(isFenzhiRoleLogined()){
				reportParams.append("branchCode=").append(this.getSessionUser().getBranchNo()).append(";");
			}else if(isCardRoleLogined()){
				reportParams.append("cardIssuer=").append(this.getSessionUser().getBranchNo()).append(";");
			}
			
			reportParams.append("settMonth=").append(this.getFormMapValue("settMonth").trim()).append(";");
			
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "rebateRechargeConsumeGather";
	}
	
	public String detail() throws Exception {

		if (isCenterOrCenterDeptRoleLogined()) {

		}else if(isFenzhiRoleLogined()){
			
		}else if(isCardRoleLogined()){
			
		}else{
			throw new BizException("没有权限返利账户充值消费明细统计报表");
		}
        
		if (StringUtils.isNotBlank(this.getFormMapValue("settMonth"))) {
			StringBuilder reportParams = new StringBuilder();
			
			if(isFenzhiRoleLogined()){
				reportParams.append("branchCode=").append(this.getSessionUser().getBranchNo()).append(";");
			}else if(isCardRoleLogined()){
				reportParams.append("cardIssuer=").append(this.getSessionUser().getBranchNo()).append(";");
			}
			reportParams.append("settMonth=").append(this.getFormMapValue("settMonth")).append(";");
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "rebateRechargeConsumeDetail";
	}
}
