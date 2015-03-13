package gnete.card.web.report;

import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import org.apache.commons.lang.StringUtils;

/***
 * @description: 商盈通系统充值支付类平衡表
 * @author luoxq
 *
 */
public class RechargeDefrayBalanceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922205292570817848L;
	
	@Override
	public String execute(){
		return null;
	}
	
	public String rechargeDefrayBalance() throws Exception {

		if (isCenterOrCenterDeptRoleLogined()) {

		}else {
			throw new BizException("没有权限查看充值支付平衡报表");
		}
        
		if (StringUtils.isNotBlank(this.getFormMapValue("settDate"))) {
			StringBuilder reportParams = new StringBuilder();
			reportParams.append("settDate=").append(this.getFormMapValue("settDate")).append(";");
			reportParams.append("issCode=").append(this.getFormMapValue("cardBranchCode")).append(";");
			
			this.getFormMap().put("reportParamsString", reportParams.toString());
		}
		return "rechargeDefrayBalance";
	}
}
