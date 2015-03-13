package gnete.card.web.nonrealtimemonitor;

import flink.util.Paginater;
import gnete.card.dao.TransDAO;
import gnete.card.entity.Trans;
import gnete.card.entity.type.RoleType;
import gnete.card.service.GenerateFileService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;


public class HisRiskTransAction extends BaseAction {

	private static final String IS_GENERATING_HIS_TRANS_EXCEL = "IS_GENERATING_HIS_TRANS_EXCEL";
	private boolean generateExcelTableTitle = true;// 是否生成excel表标题行
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private TransDAO transDAO;
	
	private Trans trans;
	
	private Paginater page;

	private Collection respCodeTypeList;
	private String settStartDate;
	private String settEndDate;
	
	public static final String[] riskFlags = {"1", "9"};
	public static final String[] types = {"10", "11", "13"};
	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else{
			throw new BizException("非运营机构禁止查看交易交易信息。");
		}
		if(StringUtils.isNotBlank(settStartDate)){ // 默认查询前一工作日
			params.put("settStartDate", settStartDate);
		} else {
			settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}

		if (StringUtils.isNotBlank(settEndDate)) {
			params.put("settEndDate", settEndDate);
		} else {
			settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
		}
		
		params.put("settStartDate",settStartDate);
		params.put("settEndDate",settEndDate);
		params.put("riskFlags", riskFlags);
		params.put("types", types);
		if (trans != null) {
			params.put("merNo",trans.getMerNo());
			params.put("cardId",trans.getCardId());
		}
		
		page = transDAO.findHisRiskTrans(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(trans, "交易对象不能为空");
		Assert.notEmpty(trans.getTransSn(), "交易对象交易流水号不能为空");

		trans = (Trans) transDAO.findByPk(trans);
		return DETAIL;
	}
	
	/**
	 * ajax请求：判断是否真正生产历史交易明细的excel文件
	 */
	public void ajaxIsGeneratingExcel() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(	IS_GENERATING_HIS_TRANS_EXCEL))) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}
	
	/**
	 * 手动生成Excel文件
	 */
	public void generate() throws Exception {
		ActionContext.getContext().getSession().put(IS_GENERATING_HIS_TRANS_EXCEL, Boolean.TRUE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(settStartDate)){ // 默认查询前一工作日
				params.put("settStartDate", settStartDate);
			} else{
				settStartDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			}
			
			if(StringUtils.isNotBlank(settEndDate)){
				params.put("settEndDate", settEndDate);
			} else{
				settEndDate = SysparamCache.getInstance().getPreWorkDateNotFromCache();
			}
			params.put("riskFlags", riskFlags);
			params.put("types", types);
			if (trans != null) {
				params.put("merNo",trans.getMerNo());
				params.put("cardId",trans.getCardId());
			}
			this.generateFileService.generateHisRiskTransExcel(response, params, this.generateExcelTableTitle);
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_HIS_TRANS_EXCEL);
		}
	}
	
	private void initPage() {
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public boolean isGenerateExcelTableTitle() {
		return generateExcelTableTitle;
	}

	public void setGenerateExcelTableTitle(boolean generateExcelTableTitle) {
		this.generateExcelTableTitle = generateExcelTableTitle;
	}

	public Collection getRespCodeTypeList() {
		return respCodeTypeList;
	}

	public void setRespCodeTypeList(Collection respCodeTypeList) {
		this.respCodeTypeList = respCodeTypeList;
	}

	public String getSettStartDate() {
		return settStartDate;
	}

	public void setSettStartDate(String settStartDate) {
		this.settStartDate = settStartDate;
	}

	public String getSettEndDate() {
		return settEndDate;
	}

	public void setSettEndDate(String settEndDate) {
		this.settEndDate = settEndDate;
	}

}
