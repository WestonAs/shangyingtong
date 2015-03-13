package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardAreaRiskDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.MerchRespCodeDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.dao.TransDAO;
import gnete.card.dao.TransDtotalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardAreaRisk;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MerchRespCode;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;
import gnete.card.entity.Trans;
import gnete.card.entity.TransDtotal;
import gnete.card.entity.type.SubacctType;
import gnete.card.service.GenerateFileService;
import gnete.card.tag.NameTag;
import gnete.card.util.ExcelFileExport;
import gnete.etc.BizException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("generateFileService")
public class GenerateFileServiceImpl implements GenerateFileService {

	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private TransDAO transDAO;
	@Autowired
	private TransDtotalDAO transDtotalDAO;
	
	@Autowired
	private CardAreaRiskDAO cardAreaRiskDAO;
	@Autowired
	private MerchRespCodeDAO merchRespCodeDAO;
	
	static Logger logger = LoggerFactory.getLogger(GenerateFileServiceImpl.class);
	
	/**
	 * 生成的Excel文件的最大条数
	 */
	private static final int MAX_ROW_COUNT = 65000;
	
	public void generateCardFileExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		Paginater page = this.cardInfoDAO.findCardFile(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<CardInfo> list = this.cardInfoDAO.findCardFileList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("卡档案信息");		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getCardFileDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getCardFileDetailData(list));
		// 报表内容的起始行号
		int starLine = 1;
		try {
			this.buildResponseHeader(response, "卡档案信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成卡档案信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("生成卡档案信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("生成卡档案信息Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成卡档案信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}
	}

	public void generateCardAcctExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		Paginater page = this.acctInfoDAO.findAcctInfo(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<AcctInfo> list = this.acctInfoDAO.findAcctInfoList(params);
		// 工作表的集合sheet
		List<String> sheetNameList =  Arrays.asList("卡帐户信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getCardAcctDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getCardAcctDetailData(list));
		// 报表内容的起始行号
		int starLine = 1;
		try {
			buildResponseHeader(response, "卡帐户信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成卡帐户信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("生成卡帐户信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("生成卡帐户信息Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成卡帐户信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}
	}
	
	public void generateHisTransExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		Paginater page = this.transDAO.findHisTrans(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<Trans> list = this.transDAO.findHisTransList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("历史交易信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getHisTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getHisTransDetailData(list));
		// 报表内容的起始行号
		int starLine = 1;
		try {
			this.buildResponseHeader(response, "历史交易信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			throw new BizException("生成历史交易信息Excel文件发生ExcelExportException。", e);
		} catch (IOException e) {
			throw new BizException("生成历史交易信息Excel文件发生IOException。", e);
		}
	}
	
	public void generateHisTransCsv(HttpServletResponse response,
			Map<String, Object> params) throws BizException, IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("历史交易明细.csv".getBytes("gb18030"), "ISO-8859-1"));
		//标题行
		StringBuilder title = buildTransTitleCvsLine();
		response.getWriter().println(title);
		response.getWriter().flush();
		int pageNumber = 1;
		int pageSize = 51200;
		logger.info("分页查询，每页[{}]条", pageSize);
		Paginater paginater = null;
		do {
			paginater = this.transDAO.findHisTrans(params, pageNumber, pageSize);
			for (Object obj : paginater.getData()) {
				Trans trans = (Trans) obj;
				//明细行
				StringBuilder line = buildTransDataCvsLine(trans);
				response.getWriter().print(line);
			}
			pageNumber++;
		} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}
	
	public void generateTransCsv(HttpServletResponse response,
			Map<String, Object> params) throws BizException, IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("当日交易明细.csv".getBytes("gb18030"), "ISO-8859-1"));
		//标题行
		StringBuilder title = buildTransTitleCvsLine();
		response.getWriter().println(title);
		response.getWriter().flush();
		int pageNumber = 1;
		int pageSize = 51200;
		logger.info("分页查询，每页[{}]条", pageSize);
		Paginater paginater = null;
		do {
			paginater = this.transDAO.findTrans(params, pageNumber, pageSize);
			for (Object obj : paginater.getData()) {
				Trans trans = (Trans) obj;
				//明细行
				StringBuilder line = buildTransDataCvsLine(trans);
				response.getWriter().print(line);
			}
			pageNumber++;
		} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}

	private StringBuilder buildTransTitleCvsLine() {
		StringBuilder title = new StringBuilder();
		title.append("系统跟踪号,交易流水,清算日期,卡号,发卡机构编号,发卡机构名称,交易类型,发起方编号,发起方名称");
		title.append(",终端号,交易金额(次数),清算金额,处理状态,处理时间,应付商户金额,实付商户金额,应收持卡人金额,实收持卡人金额");
		title.append(",转账入账卡号,资金子账户扣款,赠券账户扣款,其它账户扣款,商户代理商编号,运营代理商机构编号,外部号码");
		return title;
	}

	private StringBuilder buildTransDataCvsLine(Trans trans) {
		StringBuilder line = new StringBuilder();
		/*防止显示过长数字字符串（比如卡号）时，excel默认使用科学计数法，所以加\t*/
		line.append("\t").append(StringUtils.defaultString(trans.getSysTraceNum()));
		line.append(",\t").append(trans.getTransSn());
		line.append(",\t").append(StringUtils.defaultString(trans.getSettDate()));
		line.append(",\t").append(StringUtils.defaultString(trans.getCardId()));
		line.append(",\t").append(StringUtils.defaultString(trans.getCardIssuer()));
		line.append(",\t").append(StringUtils.defaultString(NameTag.getBranchName(trans.getCardIssuer())));
		line.append(",\t").append(StringUtils.defaultString(trans.getTransTypeName()));
		line.append(",\t").append(StringUtils.defaultString(trans.getMerNo()));
		String merName = NameTag.getBranchName(trans.getMerNo());
		line.append(",\t").append(
				StringUtils.isNotBlank(merName) ? merName : NameTag.getMerchName(trans.getMerNo()));
		
		line.append(",\t").append(StringUtils.defaultString(trans.getTermlId()));
		line.append(",").append(trans.getTransAmt());
		line.append(",").append(trans.getSettAmt());
		line.append(",\t").append(trans.getProcStatusName());
		line.append(",\t").append(trans.getRcvTime() == null? "" : DateUtil.getDate(trans.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
		line.append(",").append(trans.getMerPyaAmt());
		line.append(",").append(trans.getMerPaidAmt());
		line.append(",").append(trans.getChdrRvaAmt());
		line.append(",").append(trans.getChdrPdpAmt());
		
		line.append(",\t").append(StringUtils.defaultString(trans.getEiaCardId()));
		line.append(",\t").append(trans.getDedSubacctAmt());
		line.append(",\t").append(trans.getDedCouponAmt());
		line.append(",\t").append(trans.getDedOtherAmt());
		line.append(",\t").append(StringUtils.defaultString(trans.getMerProxyNo()));
		line.append(",\t").append(StringUtils.defaultString(trans.getCentProxyNo()));
		line.append(",\t").append(StringUtils.defaultString(trans.getReserved4()));
		
		line.append("\r\n");
		return line;
	}
	
	public void generateHisRiskTransExcel(HttpServletResponse response,
			Map<String, Object> params, boolean isGenerateExcelTableTitle) throws BizException {
		Paginater page = this.transDAO.findHisRiskTrans(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<Trans> list = this.transDAO.findHisRiskTransList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("历史风险交易信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getHisRiskTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getHisRiskTransDetailData(list));
		// 报表内容的起始行号
		int starLine = isGenerateExcelTableTitle ? 1 : 0;
		try {
			this.buildResponseHeader(response, "历史风险交易信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成历史风险交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("生成历史交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("生成历史交风险易信息Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成历史风险交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}
	}
	
	public void generateEcashHisTransExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		Paginater page = this.transDAO.findHisTrans(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<Trans> list = this.transDAO.findHisTransList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("电子现金消费历史交易信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getEcashHisTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getEcashHisTransDetailData(list));
		// 报表内容的起始行号
		int starLine = 1;
		try {
			this.buildResponseHeader(response, "电子现金消费历史交易信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成历史交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("生成历史交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("生成历史交易信息Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成历史交易信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}
	}
	
	@Override
	public void generateMerchClusterHisTransExcel(HttpServletResponse response, Map<String, Object> params,
			boolean isGenerateExcelTableTitle) throws BizException {
		Paginater page = this.transDAO.findHisTrans(params, 0, 1);
		if (page.getMaxRowCount() > 100000) {
			throw new BizException("要导出的数量超出" + 100000 + "条，请缩小查询范围。");
		}

		List<Trans> list = this.transDAO.findHisTransList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("商户集群历史交易明细");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getMerchClusterHisTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getMerchClusterHisTransDetailData(list));
		// 报表内容的起始行号
		int starLine = isGenerateExcelTableTitle ? 1 : 0;
		try {
			this.buildResponseHeader(response, "商户集群历史交易明细.xlsx");
			ExcelFileExport.generateFileXlsx(response.getOutputStream(), sheetNameList, titleItemList,
					dataLists, starLine);
		} catch (Exception e) {
			String msg = "生成商户集群历史交易明细Excel文件发生Exception，原因：";
			logger.error(msg, e);
			throw new BizException(msg + e.getMessage());
		}
	}

	@Override
	public void generateMerchClusterHisTransSummaryExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		Paginater page = this.transDtotalDAO.listByPage(params, 0, 1);
		if (page.getMaxRowCount() > 100000) {
			throw new BizException("要导出的数量超出" + 100000 + "条，请缩小查询范围。");
		}

		List<TransDtotal> list = this.transDtotalDAO.list(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("商户集群历史交易汇总");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getMerchClusterHisTransSummaryItem());

		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getMerchClusterHisTransSummaryData(list));

		// 报表内容的起始行号
		int starLine = 1;
		try {
			this.buildResponseHeader(response, "商户集群历史交易汇总.xlsx");
			ExcelFileExport.generateFileXlsx(response.getOutputStream(), sheetNameList, titleItemList,
					dataLists, starLine);
		} catch (Exception e) {
			String msg = "生成商户集群历史交易汇总Excel文件发生Exception，原因：";
			logger.error(msg, e);
			throw new BizException(msg + e.getMessage());
		}
	}

	//-----------------------------------------------CardAreaRisk start---------------------------------------------------------------
	/**
	 * 风险卡地点监控信息
	 */
	@Override
	public void generateCardAreaRiskTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle)
	throws BizException {
		Paginater page = this.cardAreaRiskDAO.findPage(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<CardAreaRisk> list = this.cardAreaRiskDAO.findList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("风险卡地点监控信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getCardAreaRiskTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getCardAreaRiskTransDetailData(list));
		// 报表内容的起始行号
		int starLine = isGenerateExcelTableTitle ? 1 : 0;
		try {
			this.buildResponseHeader(response, "风险卡地点监控信息.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch (Exception e) {
			String msg = "生成风险卡地点监控信息Excel文件发生Exception，原因：";
			logger.error(msg, e);
			throw new BizException(msg + e.getMessage());
		}
	}
	
	/**
	 * 设置风险卡地点监控信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getCardAreaRiskTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("交易流水");
		rowDataList.add("卡号ID");
		rowDataList.add("风险商户号");
		rowDataList.add("交易地区码");
		rowDataList.add("交易类型");
		rowDataList.add("发卡机构");
		rowDataList.add("清算日期");
		rowDataList.add("交易时间");
		return rowDataList.toArray();
	}
	
	/**
	 * 生成风险卡地点监控信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getCardAreaRiskTransDetailData(List<CardAreaRisk> list) throws BizException{
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) return null;
		
		List<Object[]> data = new ArrayList<Object[]>();
		for (CardAreaRisk cardAreaRisk : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(cardAreaRisk.getTransSn());
			rowDataList.add(cardAreaRisk.getCardId());
			rowDataList.add(cardAreaRisk.getMerNo());
			rowDataList.add(cardAreaRisk.getAreaCode());
			rowDataList.add(cardAreaRisk.getTransTypeName());
			rowDataList.add(cardAreaRisk.getCardIssuer());
			rowDataList.add(cardAreaRisk.getSettDate());
			rowDataList.add(cardAreaRisk.getRcvTime() == null ? "" : DateUtil.getDate(cardAreaRisk.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
			data.add(rowDataList.toArray());
		}
		return data;
	}
   //--------------------------------------------CardAreaRisk end------------------------------------------------------------------
	
	
	//-----------------------------------------------MerchRespCode start---------------------------------------------------------------
	/**
	 * 商户错误码监控信息
	 */
	@Override
	public void generateMerchRespCodeTransExcel(HttpServletResponse response, Map<String, Object> params, boolean isGenerateExcelTableTitle)
	throws BizException {
		Paginater page = this.merchRespCodeDAO.findPage(params, 0, 1);
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出" + MAX_ROW_COUNT + "条，请缩小查询范围。");
		}
		
		List<MerchRespCode> list = this.merchRespCodeDAO.findList(params);
		// 工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("商户错误码监控信息");
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getMerchRespCodeTransDetailItem());
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getMerchRespCodeTransDetailData(list));
		// 报表内容的起始行号
		int starLine = isGenerateExcelTableTitle ? 1 : 0;
		try {
			this.buildResponseHeader(response, "商户错误码监控.xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch (Exception e) {
			String msg = "生成商户错误码监控信息Excel文件发生Exception，原因：";
			logger.error(msg, e);
			throw new BizException(msg + e.getMessage());
		}
	}
	
	/**
	 * 设置商户错误码监控信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getMerchRespCodeTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("风险商户号");
		rowDataList.add("清算日期");
		rowDataList.add("返回码");
		rowDataList.add("失败交易笔数");
		rowDataList.add("插入时间");
		return rowDataList.toArray();
	}
	
	/**
	 * 生成商户错误码监控信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getMerchRespCodeTransDetailData(List<MerchRespCode> list) throws BizException{
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) return null;
		
		List<Object[]> data = new ArrayList<Object[]>();
		for (MerchRespCode merchRespCode : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(merchRespCode.getMerNo());
			rowDataList.add(merchRespCode.getSettDate());
			rowDataList.add(merchRespCode.getRespCode());
			rowDataList.add(merchRespCode.getTransNum());
			rowDataList.add(merchRespCode.getInsertTime() == null ? "" : DateUtil.getDate(merchRespCode.getInsertTime(), "yyyy-MM-dd HH:mm:ss"));
			data.add(rowDataList.toArray());
		}
		return data;
	}
   //--------------------------------------------MerchRespCode end------------------------------------------------------------------
	
	
	/** 设置response的Header信息 */
	private void buildResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.setHeader("Content-Type", "application/ms-excel");
		fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	}
	
	/**
	 * 设置卡档案信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getCardFileDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("卡号");
		rowDataList.add("卡BIN");
		rowDataList.add("卡种类");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("领卡机构编号");
		rowDataList.add("领卡机构名称");
		rowDataList.add("领卡日期");
		rowDataList.add("售卡机构编号");
		rowDataList.add("售卡机构名称");
		rowDataList.add("售卡日期");
		rowDataList.add("卡状态");
		rowDataList.add("卡有效日期");
		rowDataList.add("失效日期");
		rowDataList.add("交易总金额");
		rowDataList.add("购卡客户姓名");
		rowDataList.add("证件类型");
		rowDataList.add("证件号");
		rowDataList.add("手机号");
		rowDataList.add("外部号码");
		return rowDataList.toArray();
	}

	/**
	 * 设置卡帐户信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getCardAcctDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("卡号");
		rowDataList.add("账号");
		rowDataList.add("卡种类");
		rowDataList.add("卡子类型");
		rowDataList.add("币种");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("交易次数");
		rowDataList.add("初始充值金额");
		rowDataList.add("累积充值金额");
		rowDataList.add("充值资金余额");
		rowDataList.add("返利资金余额");
		rowDataList.add("有效日期");
		rowDataList.add("失效日期");
		rowDataList.add("外部号码");
		return rowDataList.toArray();
	}

	/**
	 * 设置历史交易信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getHisTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("系统跟踪号");
		rowDataList.add("交易流水");
		rowDataList.add("清算日期");
		rowDataList.add("卡号");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("交易类型");
		rowDataList.add("发起方编号");
		rowDataList.add("发起方名称");
		rowDataList.add("终端号");
		rowDataList.add("交易金额(次数)");
		rowDataList.add("清算金额");
		rowDataList.add("处理状态");
		rowDataList.add("处理时间");
		rowDataList.add("应付商户金额");
		rowDataList.add("实付商户金额");
		rowDataList.add("应收持卡人金额");
		rowDataList.add("实收持卡人金额");
		rowDataList.add("转账入账卡号");
		rowDataList.add("资金子账户扣款");
		rowDataList.add("赠券账户扣款");
		rowDataList.add("其它账户扣款");
		rowDataList.add("商户代理商编号");
		rowDataList.add("运营代理商机构编号");
		rowDataList.add("外部号码");
		return rowDataList.toArray();
	}
	
	/**
	 * 设置历史交易信息文件表头 
	 * @return
	 * @throws BizException
	 */

	private static Object[] getHisRiskTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("清算日期");
		rowDataList.add("商户编号");
		rowDataList.add("商户名称");
		rowDataList.add("卡号");
		rowDataList.add("检索参考号");
		rowDataList.add("交易流水");
		rowDataList.add("交易日期");
		rowDataList.add("交易响应码");
		rowDataList.add("风险类型");
		rowDataList.add("风险标志");
		rowDataList.add("交易金额");
		rowDataList.add("累计交易笔数");
		rowDataList.add("累计交易金额");
		return rowDataList.toArray();
	}
	
	/**
	 * 设置电子现金消费历史交易信息文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getEcashHisTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("系统跟踪号");
		rowDataList.add("交易流水");
		rowDataList.add("清算日期");
		rowDataList.add("卡号");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("交易类型");
		rowDataList.add("发起方编号");
		rowDataList.add("发起方名称");
		rowDataList.add("终端号");
		rowDataList.add("交易金额(次数)");
		rowDataList.add("清算金额");
		rowDataList.add("处理状态");
		rowDataList.add("处理时间");
		rowDataList.add("接收时间");
		rowDataList.add("应付商户金额");
		rowDataList.add("实付商户金额");
		rowDataList.add("应收持卡人金额");
		rowDataList.add("实收持卡人金额");
		rowDataList.add("转账入账卡号");
		rowDataList.add("资金子账户扣款");
		rowDataList.add("赠券账户扣款");
		rowDataList.add("其它账户扣款");
		rowDataList.add("商户代理商编号");
		rowDataList.add("运营代理商机构编号");
		return rowDataList.toArray();
	}
	
	/**
	 * 设置商户集群历史交易明细文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getMerchClusterHisTransDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		rowDataList.add("商户编号");
		rowDataList.add("商户名称");
		rowDataList.add("清算日期");
		rowDataList.add("交易类型");
		
		rowDataList.add("卡号");
		rowDataList.add("发卡机构");
		rowDataList.add("清算金额");
		rowDataList.add("交易时间");
		
		rowDataList.add("终端号");
		rowDataList.add("流水号");
		rowDataList.add("参考号");
		rowDataList.add("响应码");
		
		rowDataList.add("处理状态");
		rowDataList.add("原流水号");
		rowDataList.add("附加信息");
		rowDataList.add("卡BIN");
		return rowDataList.toArray();
	}
	
	/**
	 * 设置商户集群历史交易汇总文件表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getMerchClusterHisTransSummaryItem() throws BizException {
		return new String[] { "清算日期", "商户编号", "商户名称", "发卡机构编号", "发卡机构名称"
				, "交易类型", "币种", "交易笔数", "清算金额" };
	}
	
	/**
	 * 生成卡档案信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getCardFileDetailData(List<CardInfo> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (CardInfo cardInfo : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(cardInfo.getCardId());
			rowDataList.add(cardInfo.getCardBin());
			rowDataList.add(cardInfo.getCardClassName());
			rowDataList.add(cardInfo.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(cardInfo.getCardIssuer()));
			rowDataList.add(cardInfo.getAppOrgId());
			
			String appOrgName = NameTag.getBranchName(cardInfo.getAppOrgId());
			if (StringUtils.isBlank(appOrgName)) {
				appOrgName = NameTag.getDeptName(cardInfo.getAppOrgId());
				appOrgName = StringUtils.isBlank(appOrgName) ? NameTag.getMerchName(cardInfo.getAppOrgId()) : appOrgName;
			}
			rowDataList.add(appOrgName);
			rowDataList.add(cardInfo.getAppDate());

			rowDataList.add(cardInfo.getSaleOrgId());
			String saleOrgName = NameTag.getBranchName(cardInfo.getSaleOrgId());
			if (StringUtils.isBlank(saleOrgName)) {
				saleOrgName = NameTag.getDeptName(cardInfo.getSaleOrgId());
				saleOrgName = StringUtils.isBlank(saleOrgName) ? NameTag.getMerchName(cardInfo.getSaleOrgId()) : saleOrgName;
			}
			rowDataList.add(saleOrgName);
			rowDataList.add(cardInfo.getSaleDate());
			
			rowDataList.add(cardInfo.getCardStatusName());
			rowDataList.add(cardInfo.getEffDate());
			rowDataList.add(cardInfo.getExpirDate());
			rowDataList.add(cardInfo.getAmount());
			rowDataList.add(cardInfo.getCustName());
			rowDataList.add(cardInfo.getCredTypeName());
			rowDataList.add(cardInfo.getCredNo());
			rowDataList.add(cardInfo.getMobileNo());
			rowDataList.add(cardInfo.getExternalCardId());
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成历史交易信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getHisTransDetailData(List<Trans> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Trans trans : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(trans.getSysTraceNum());
			rowDataList.add(trans.getTransSn());
			rowDataList.add(trans.getSettDate());
			rowDataList.add(trans.getCardId());
			rowDataList.add(trans.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(trans.getCardIssuer()));
			rowDataList.add(trans.getTransTypeName());
			rowDataList.add(trans.getMerNo());
			
			String merName = NameTag.getBranchName(trans.getMerNo());
			if (StringUtils.isBlank(merName)) {
				merName = NameTag.getMerchName(trans.getMerNo());
			}
			rowDataList.add(merName);
			
			rowDataList.add(trans.getTermlId());
			rowDataList.add(trans.getTransAmt());
			rowDataList.add(trans.getSettAmt());
			rowDataList.add(trans.getProcStatusName());
			
			// 处理时间
			rowDataList.add(trans.getRcvTime() == null? "" : DateUtil.getDate(trans.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
			rowDataList.add(trans.getMerPyaAmt());
			rowDataList.add(trans.getMerPaidAmt());
			rowDataList.add(trans.getChdrRvaAmt());
			rowDataList.add(trans.getChdrPdpAmt());
			rowDataList.add(trans.getEiaCardId());
			rowDataList.add(trans.getDedSubacctAmt());
			rowDataList.add(trans.getDedCouponAmt());
			rowDataList.add(trans.getDedOtherAmt());
			rowDataList.add(trans.getMerProxyNo());
			rowDataList.add(trans.getCentProxyNo());
			rowDataList.add(trans.getReserved4());
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	/**
	 * 生成历史交易信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getHisRiskTransDetailData(List<Trans> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		for (Trans trans : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(trans.getSettDate());
			rowDataList.add(trans.getMerNo());
			rowDataList.add(NameTag.getMerchName(trans.getMerNo()));
			rowDataList.add(trans.getCardId());
			rowDataList.add(trans.getPosSn());
			rowDataList.add(trans.getTransSn());
			rowDataList.add(trans.getRcvTime() == null? "" : DateUtil.getDate(trans.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
			rowDataList.add(trans.getRespCode());
			rowDataList.add(trans.getRiskTypeName());
			rowDataList.add(trans.getRiskFlagName());
			rowDataList.add(trans.getTransAmt());
			rowDataList.add(trans.getTradeCnt());
			rowDataList.add(trans.getAmount());
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	/**
	 * 生成电子现金消费历史交易信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getEcashHisTransDetailData(List<Trans> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Trans trans : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(trans.getSysTraceNum());
			rowDataList.add(trans.getTransSn());
			rowDataList.add(trans.getSettDate());
			rowDataList.add(trans.getCardId());
			rowDataList.add(trans.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(trans.getCardIssuer()));
			rowDataList.add(trans.getTransTypeName());
			rowDataList.add(trans.getMerNo());
			
			String merName = NameTag.getBranchName(trans.getMerNo());
			if (StringUtils.isBlank(merName)) {
				merName = NameTag.getMerchName(trans.getMerNo());
			}
			rowDataList.add(merName);
			
			rowDataList.add(trans.getTermlId());
			rowDataList.add(trans.getTransAmt());
			rowDataList.add(trans.getSettAmt());
			rowDataList.add(trans.getProcStatusName());
			
			// 处理时间
			rowDataList.add(trans.getRcvTime() == null? "" : DateUtil.getDate(trans.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
			rowDataList.add(trans.getProcTime() == null? "" : DateUtil.getDate(trans.getProcTime(), "yyyy-MM-dd HH:mm:ss"));//接收时间
			rowDataList.add(trans.getMerPyaAmt());
			rowDataList.add(trans.getMerPaidAmt());
			rowDataList.add(trans.getChdrRvaAmt());
			rowDataList.add(trans.getChdrPdpAmt());
			rowDataList.add(trans.getEiaCardId());
			rowDataList.add(trans.getDedSubacctAmt());
			rowDataList.add(trans.getDedCouponAmt());
			rowDataList.add(trans.getDedOtherAmt());
			rowDataList.add(trans.getMerProxyNo());
			rowDataList.add(trans.getCentProxyNo());
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	/**
	 * 生成商户集群历史交易明细内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getMerchClusterHisTransDetailData(List<Trans> list) throws BizException{
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) return null;
		
		List<Object[]> data = new ArrayList<Object[]>();
		for (Trans trans : list){
			List<Object> rowDataList = new ArrayList<Object>();
			
			rowDataList.add(trans.getMerNo());
			rowDataList.add(StringUtils.isBlank(trans.getMerName()) ? NameTag.getMerchName(trans.getMerNo())
					: trans.getMerName());
			rowDataList.add(trans.getSettDate());
			rowDataList.add(trans.getTransTypeName());
			
			rowDataList.add(trans.getCardId());
			rowDataList.add(NameTag.getBranchName(trans.getCardIssuer()));
			rowDataList.add(trans.getSettAmt());
			// 处理时间
			rowDataList.add(trans.getRcvTime() == null ? "" : DateUtil.getDate(trans.getRcvTime(), "yyyy-MM-dd HH:mm:ss"));
			
			rowDataList.add(trans.getTermlId());
			boolean isEmptySn = StringUtils.isBlank(trans.getPosSn()) || trans.getPosSn().length() < 17; 
			rowDataList.add(isEmptySn ? "" : trans.getPosSn().substring(10, 16));
			rowDataList.add(trans.getRetrivlRefNum());
			rowDataList.add(trans.getRespCode());

			rowDataList.add(trans.getProcStatusName());
			rowDataList.add("");
			rowDataList.add(trans.getReserved2());
			rowDataList.add(trans.getCardBin());
			
			data.add(rowDataList.toArray());
		}
		return data;
	}
	
	/**
	 * 生成商户集群历史交易汇总内容list
	 */
	private List<Object[]> getMerchClusterHisTransSummaryData(List<TransDtotal> list) throws BizException{
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) return null;
		
		List<Object[]> data = new ArrayList<Object[]>();
		for (TransDtotal trans : list){
			List<Object> rowDataList = new ArrayList<Object>();
			
			rowDataList.add(trans.getWorkdate());
			rowDataList.add(trans.getMerchId());
			rowDataList.add( NameTag.getMerchName(trans.getMerchId()));
			rowDataList.add(trans.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(trans.getCardIssuer()));
			
			rowDataList.add(trans.getTransTypeName());
			rowDataList.add(trans.getCurCode());
			rowDataList.add(trans.getTransNum());
			rowDataList.add(trans.getSettAmt());

			//添加数据行
			data.add(rowDataList.toArray());
		}
		return data;
	}

	/**
	 * 生成卡账户信息内容list
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getCardAcctDetailData(List<AcctInfo> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		SubAcctBalKey key = new SubAcctBalKey();
		
		for (AcctInfo acctInfo : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(acctInfo.getCardId());
			rowDataList.add(acctInfo.getAcctId());
			rowDataList.add(acctInfo.getCardClassName());
			rowDataList.add(acctInfo.getCardSubclass());
			rowDataList.add(acctInfo.getCurCode());
			rowDataList.add(acctInfo.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(acctInfo.getCardIssuer()));
			rowDataList.add(acctInfo.getTradeCnt());
			rowDataList.add(acctInfo.getInitialChargeAmt());
			rowDataList.add(acctInfo.getAccuChargeAmt());
			
			key.setAcctId(acctInfo.getAcctId());
			key.setSubacctType(SubacctType.DEPOSIT.getValue());
			SubAcctBal subAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			rowDataList.add(subAcctBal.getAvlbBal());
			
			key.setSubacctType(SubacctType.REBATE.getValue());
			subAcctBal = (SubAcctBal) this.subAcctBalDAO.findByPk(key);
			rowDataList.add(subAcctBal.getAvlbBal());
			
			rowDataList.add(acctInfo.getEffDate());
			rowDataList.add(acctInfo.getExpirDate());
			rowDataList.add(acctInfo.getExternalCardId());
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

}
