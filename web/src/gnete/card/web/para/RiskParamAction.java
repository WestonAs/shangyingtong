package gnete.card.web.para;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.RiskParamDAO;
import gnete.card.entity.RiskParam;
import gnete.card.entity.RiskParamKey;
import gnete.card.entity.type.RiskType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.RiskParamService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class RiskParamAction extends BaseAction{

	@Autowired
	private RiskParamDAO riskParamDAO;
	@Autowired
	private RiskParamService riskParamService;
	
	private RiskParam riskParam;
	private Paginater page;
	private List<RiskType> riskTypeList;
	
	@Override
	public String execute() throws Exception {
		
		if ( !isCenterOrCenterDeptRoleLogined()){
			throw new BizException("没有权限查询。");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		this.riskTypeList = RiskType.getAll();
		
		if (riskParam != null) {
			params.put("binNo", riskParam.getBinNo());
			params.put("riskType", riskParam.getRiskType());
		}
		this.page = this.riskParamDAO.findRiskParam(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		RiskParamKey key = new RiskParamKey();
		key.setBinNo(riskParam.getBinNo());
		key.setRiskType(riskParam.getRiskType());
		
		this.riskParam = (RiskParam) this.riskParamDAO.findByPk(key);
		return DETAIL;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!(isCenterOrCenterDeptRoleLogined())){
			throw new BizException("非营运中心或者是营运中心部门不能操作。");
		}
		RiskParamKey key = new RiskParamKey();
		key.setBinNo(riskParam.getBinNo());
		key.setRiskType(riskParam.getRiskType());
		this.riskParam = (RiskParam) this.riskParamDAO.findByPk(key);
		
		this.riskTypeList = RiskType.getAll();
		
		return MODIFY;
	}
	
	// 修改对象操作
	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		
		this.riskParamService.modifyRiskParam(this.riskParam, this.getSessionUserCode());
		
		String msg = LogUtils.r("修改风控种类为["+ RiskType.valueOf(riskParam.getRiskType()).getName()+"]的卡BIN风险监控参数成功");
		this.addActionMessage("/para/riskPara/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}

	public RiskParam getRiskParam() {
		return riskParam;
	}

	public void setRiskParam(RiskParam riskParam) {
		this.riskParam = riskParam;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<RiskType> getRiskTypeList() {
		return riskTypeList;
	}

	public void setRiskTypeList(List<RiskType> riskTypeList) {
		this.riskTypeList = riskTypeList;
	}

}
