package gnete.card.web.infoAndBizQuery;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.type.RiskLevelType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 外部卡导入处理
 */
public class RiskManageQueryAction extends BaseAction {
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;

	private Paginater page;

	private List<RiskLevelType> riskLevelTypeList;

	@Override
	public String execute() throws Exception {
		this.riskLevelTypeList = RiskLevelType.getAll();
		this.formMap = this.formMap == null ? new HashMap<String, String>() : this.formMap;
		if (this.formMap.get("type") == null) {
			this.formMap.put("type", "B");
		}

		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心才有权限查询");

		Map<String, Object> params = new HashMap<String, Object>();
		List<String> allowedParamKeys = Arrays.asList("riskLevel", "license", "licenseEffDayCnt",
				"legalPersonIdcard", "legalPersonIdcardEffDayCnt");
		for (String key : allowedParamKeys) {
			if (StringUtils.isNotEmpty(formMap.get(key))) {
				params.put(key, formMap.get(key).trim());
			}
		}
		if ("B".equals(formMap.get("type"))) {// 机构
			this.page = this.branchInfoDAO.findBranch(params, this.getPageNumber(), this.getPageSize());
		} else if ("M".equals(formMap.get("type"))) {// 商户
			this.page = this.merchInfoDAO.find(params, this.getPageNumber(), this.getPageSize());
		}
		return LIST;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<RiskLevelType> getRiskLevelTypeList() {
		return riskLevelTypeList;
	}

	public void setRiskLevelTypeList(List<RiskLevelType> riskLevelTypeList) {
		this.riskLevelTypeList = riskLevelTypeList;
	}
}
