package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReportType.java
 *
 * @description: 下载自动生成报表的报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-9
 */
public class ReportType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	public static final ReportType CENTER_DAILY = new ReportType("运营中心日报表", "dayReport/center");
	public static final ReportType FENZHI_DAILY = new ReportType("运营分支机构日报表", "dayReport/fenzhi");
	public static final ReportType CARD_DAILY = new ReportType("发卡机构日报表（excel）", "dayReport/cardBranch");
	public static final ReportType CARD_TRANS_PDF_DAILY = new ReportType("发卡机构交易明细日报表（PDF）", "dayReport/pdfCardTrans");
	public static final ReportType CARD_SELLCARD_PDF_DAILY = new ReportType("发卡机构售卡明细日报表（PDF）", "dayReport/pdfCardSellCard");
	public static final ReportType CARD_DEPOSIT_PDF_DAILY = new ReportType("发卡机构充值明细日报表（PDF）", "dayReport/pdfCardDeposit");
	public static final ReportType MERCH_DAILY = new ReportType("商户日报表（excel）", "dayReport/merch");
	public static final ReportType MERCH_TRANS_PDF_DAILY = new ReportType("商户交易明细日报表（PDF）", "dayReport/pdfMerchTransPdf");
	public static final ReportType PROXY_DAILY = new ReportType("售卡代理日报表", "dayReport/saleProxy");

	public static final ReportType MERCH_MONTH = new ReportType("商户月报表", "monthReport/merch");
	public static final ReportType CENTER_MONTH = new ReportType("运营中心月报表", "monthReport/center");
	public static final ReportType FENZHI_MONTH = new ReportType("运营分支机构月报表", "monthReport/fenzhi");
	public static final ReportType CARD_MONTH = new ReportType("发卡机构月报表", "monthReport/card");

	public static final ReportType BALANCE = new ReportType("余额报表", "balanceReport");
	
	public static final ReportType CONSM_CHARGE_BAL = new ReportType("累积消费充值余额报表", "consmChargeBalReport");
	
	@SuppressWarnings("unchecked")
	protected ReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ReportType valueOf(String value) {
		ReportType type = (ReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(ReportType.ALL);
	}
	
	/**
	 * 得到发卡机构的报表类型
	 * @return
	 */
	public static List getCardDayReport() {
		Map params = new HashMap();
		params.put(CARD_DAILY.getValue(), CARD_DAILY);
		params.put(CARD_TRANS_PDF_DAILY.getValue(), CARD_TRANS_PDF_DAILY);
		params.put(CARD_SELLCARD_PDF_DAILY.getValue(), CARD_SELLCARD_PDF_DAILY);
		params.put(CARD_DEPOSIT_PDF_DAILY.getValue(), CARD_DEPOSIT_PDF_DAILY);
		
		return getOrderedList(params);
	}
	
	/**
	 * 得到商户的报表类型
	 * @return
	 */
	public static List getMerchDayReport() {
		Map params = new HashMap();
		params.put(MERCH_DAILY.getValue(), MERCH_DAILY);
		params.put(MERCH_TRANS_PDF_DAILY.getValue(), MERCH_TRANS_PDF_DAILY);
		
		return getOrderedList(params);
	}
	
	public static List getReportConfigType() {
		Map params = new HashMap();
		params.put(CONSM_CHARGE_BAL.getValue(), CONSM_CHARGE_BAL);
		
		return getOrderedList(params);
	}
	
	/**
	 * 得到目前日报表类型
	 * @return
	 */
	public static List getDayReportType(){
		Map params = new HashMap();
		params.put(CENTER_DAILY.getValue(), CENTER_DAILY);
		params.put(FENZHI_DAILY.getValue(), FENZHI_DAILY);
		params.put(CARD_DAILY.getValue(), CARD_DAILY);
		params.put(MERCH_DAILY.getValue(), MERCH_DAILY);
		params.put(PROXY_DAILY.getValue(), PROXY_DAILY);
		params.put(BALANCE.getValue(), BALANCE);
		params.put(CONSM_CHARGE_BAL.getValue(), CONSM_CHARGE_BAL);
		return getOrderedList(params);
	}
	
	/**
	 * 得到目前月报表类型
	 * @return
	 */
	public static List getMonthReportType(){
		Map params = new HashMap();
		params.put(CENTER_MONTH.getValue(), CENTER_MONTH);
		params.put(FENZHI_MONTH.getValue(), FENZHI_MONTH);
		params.put(CARD_MONTH.getValue(), CARD_MONTH);
		params.put(MERCH_MONTH.getValue(), MERCH_MONTH);
		return getOrderedList(params);
	}
}
