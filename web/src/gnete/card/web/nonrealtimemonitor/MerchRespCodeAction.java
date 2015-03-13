package gnete.card.web.nonrealtimemonitor;

import flink.util.Paginater;
import gnete.card.dao.MerchRespCodeDAO;
import gnete.card.entity.MerchRespCode;
import gnete.card.entity.type.RoleType;
import gnete.card.service.GenerateFileService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;


public class MerchRespCodeAction extends BaseAction {

	private static final String IS_GENERATING_MERCH_RESP_CODE_TRANS_EXCEL = "IS_GENERATING_MERCH_RESP_CODE_TRANS_EXCEL";
	private boolean generateExcelTableTitle = true;// 是否生成excel表标题行
	@Autowired
	private GenerateFileService generateFileService;
	@Autowired
	private MerchRespCodeDAO merchRespCodeDAO;
	
	private MerchRespCode merchRespCode;
	
	private Paginater page;

	private Collection respCodeTypeList;
	private String startDate;
	private String endDate;
	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else{
			throw new BizException("非运营机构禁止查看商户错误码监控交易信息。");
		}
		
		if (merchRespCode != null) {
			params.put("merNo",merchRespCode.getMerNo());
			params.put("startDate",startDate);
			params.put("endDate",endDate);
			params.put("respCode",merchRespCode.getRespCode());

		}
		page = merchRespCodeDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(merchRespCode, "商户错误码监控对象不能为空");
		Assert.notEmpty(merchRespCode.getMerNo(), "商户错误码监控对象风险商户号不能为空");
		Assert.notEmpty(merchRespCode.getSettDate(), "商户错误码监控对象清算日期不能为空");
		Assert.notEmpty(merchRespCode.getRespCode(), "商户错误码监控对象返回码不能为空");

		merchRespCode = (MerchRespCode) merchRespCodeDAO.findByPk(merchRespCode);
		return DETAIL;
	}
	
	/**
	 * ajax请求：判断是否真正生产历史交易明细的excel文件
	 */
	public void ajaxIsGeneratingExcel() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(	IS_GENERATING_MERCH_RESP_CODE_TRANS_EXCEL))) {
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
		ActionContext.getContext().getSession().put(IS_GENERATING_MERCH_RESP_CODE_TRANS_EXCEL, Boolean.TRUE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (merchRespCode != null) {
				params.put("merNo",merchRespCode.getMerNo());
				params.put("settDate",merchRespCode.getSettDate());
				params.put("respCode",merchRespCode.getRespCode());
			}
			this.generateFileService.generateMerchRespCodeTransExcel(response, params, this.generateExcelTableTitle);
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_MERCH_RESP_CODE_TRANS_EXCEL);
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


	public MerchRespCode getMerchRespCode() {
		return merchRespCode;
	}

	public void setMerchRespCode(MerchRespCode merchRespCode) {
		this.merchRespCode = merchRespCode;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
