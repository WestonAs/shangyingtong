package gnete.card.web.nonrealtimemonitor;

import flink.util.Paginater;
import gnete.card.dao.CardAreaRiskDAO;
import gnete.card.entity.CardAreaRisk;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
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


public class CardAreaRiskAction extends BaseAction {
	
	private static final String IS_GENERATING_CARD_AREA_RISK_TRANS_EXCEL = "IS_GENERATING_CARD_AREA_RISK_TRANS_EXCEL";
	private boolean generateExcelTableTitle = true;// 是否生成excel表标题行
	@Autowired
	private GenerateFileService generateFileService;
	
	@Autowired
	private CardAreaRiskDAO cardAreaRiskDAO;
	
	private CardAreaRisk cardAreaRisk;
	
	private Paginater page;
	
	// 交易类型
	private Collection transTypeList;
	private String startDate;
	private String endDate;

	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		} else{
			throw new BizException("非运营中心禁止查看风险卡地点监控交易信息。");
		}
		
		if (cardAreaRisk != null) {
			params.put("cardId",cardAreaRisk.getCardId());
			params.put("merNo",cardAreaRisk.getMerNo());
			params.put("transType",cardAreaRisk.getTransType());
			params.put("startDate",startDate);
			params.put("endDate",endDate);
			params.put("cardIssuer",cardAreaRisk.getCardIssuer());

		}
		page = cardAreaRiskDAO.findPage(params, getPageNumber(),getPageSize());
		
		transTypeList = TransType.getNonRealTimeMonitorTransType();
		
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(cardAreaRisk, "风险卡地点监控对象不能为空");
		Assert.notEmpty(cardAreaRisk.getTransSn(), "风险卡地点监控对象交易流水不能为空");

		cardAreaRisk = (CardAreaRisk) cardAreaRiskDAO.findByPk(cardAreaRisk);
		return DETAIL;
	}
	
	
	/**
	 * ajax请求：判断是否真正生产历史交易明细的excel文件
	 */
	public void ajaxIsGeneratingExcel() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(	IS_GENERATING_CARD_AREA_RISK_TRANS_EXCEL))) {
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
		ActionContext.getContext().getSession().put(IS_GENERATING_CARD_AREA_RISK_TRANS_EXCEL, Boolean.TRUE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (cardAreaRisk != null) {
				params.put("cardId",cardAreaRisk.getCardId());
				params.put("merNo",cardAreaRisk.getMerNo());
				params.put("transType",cardAreaRisk.getTransType());
				params.put("startDate",startDate);
				params.put("endDate",endDate);
				params.put("cardIssuer",cardAreaRisk.getCardIssuer());
			}
			this.generateFileService.generateCardAreaRiskTransExcel(response, params, this.generateExcelTableTitle);
		} finally {
			ActionContext.getContext().getSession().remove(IS_GENERATING_CARD_AREA_RISK_TRANS_EXCEL);
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

	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}

	public Collection getTransTypeList() {
		return transTypeList;
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
	public CardAreaRisk getCardAreaRisk() {
		return cardAreaRisk;
	}

	public void setCardAreaRisk(CardAreaRisk cardAreaRisk) {
		this.cardAreaRisk = cardAreaRisk;
	}

	public boolean isGenerateExcelTableTitle() {
		return generateExcelTableTitle;
	}

	public void setGenerateExcelTableTitle(boolean generateExcelTableTitle) {
		this.generateExcelTableTitle = generateExcelTableTitle;
	}
}
