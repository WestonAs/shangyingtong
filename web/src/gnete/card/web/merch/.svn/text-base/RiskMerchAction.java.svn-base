package gnete.card.web.merch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.RiskMerchDAO;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RiskMerch;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MerchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class RiskMerchAction extends BaseAction {

	@Autowired
	private RiskMerchDAO riskMerchDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private MerchService merchService;
	
	private RiskMerch riskMerch;
	private Paginater page; 
	private String merchId;
	
	@Override
	public String execute() throws Exception {
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (riskMerch != null){
			params.put("merchId", riskMerch.getMerchId());
			params.put("merchName",  MatchMode.ANYWHERE.toMatchString(riskMerch.getMerchName()));
		}
		
		this.page = this.riskMerchDAO.findRiskMerch(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.riskMerch = (RiskMerch)this.riskMerchDAO.findByPk(this.riskMerch.getMerchId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(!this.getLoginRoleType().equals(RoleType.CENTER.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("非营运中心或者是营运中心部门不能操作。");
		}
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {			
		
		String merchId = this.riskMerch.getMerchId();
		MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(merchId);
		this.riskMerch.setMerchName(merchInfo.getMerchName());
		merchService.addRiskMerch(this.riskMerch, this.getSessionUserCode());
		
		String msg = "新增风险商户["+this.riskMerch.getMerchName()+"]成功！";
		this.addActionMessage("/riskMerch/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 删除信息
	public String delete() throws Exception{
		
		if(!this.getLoginRoleType().equals(RoleType.CENTER.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CENTER_DEPT.getValue())){
			throw new BizException("非营运中心或者是营运中心部门不能操作。");
		}
		this.merchService.deleteRiskMerch(this.getMerchId());
		String msg = "删除风险商户信息成功，商户号为[" + this.getMerchId() + "].";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/riskMerch/list.do", msg);
		return SUCCESS;
	}

	public RiskMerch getRiskMerch() {
		return riskMerch;
	}

	public void setRiskMerch(RiskMerch riskMerch) {
		this.riskMerch = riskMerch;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

}
