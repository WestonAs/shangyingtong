package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.util.IOUtil;
import gnete.card.dao.ReportResourceDAO;
import gnete.card.service.ReportResourceService;
import gnete.card.util.ExcelFileExport;
import gnete.etc.BizException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reportResourceService")
public class ReportResourceServiceImpl implements ReportResourceService {
	
	static Logger logger = Logger.getLogger(ReportResourceServiceImpl.class);
	
	@Autowired
	private ReportResourceDAO reportResourceDAO;

	public void getCenterOperateFeeExcel(Map<String, Object> params, String tempPath)
			throws BizException {
		// 1.根据条件取出数据
		// List<Map<String, Object>> preFundList = this.reportResourceDAO.findOptDetailPreFund(params);
		
		// 1.根据取得的数据在本地生成excel文件
		String fileName = gernerateCenterExcel(tempPath, params);
		//fileName = fileName.replace("/", "\\");
		
		// 2.下载文件
		try{
			IOUtil.downloadFile(fileName);
		} catch (IOException e) {
			logger.error("下载excel文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载excel文件时发生异常，原因：" + e.getMessage());
		}
	}
	
	public void getAgentFeeShareExcel(Map<String, Object> params,
			String tempPath) throws BizException {
		// 1.根据取得的数据在本地生成excel文件
		String fileName = gernerateAgentExcel(tempPath, params);
//		fileName = fileName.replace("/", "\\");
		
		// 2.下载文件
		try{
			IOUtil.downloadFile(fileName);
		} catch (IOException e) {
			logger.error("下载excel文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载excel文件时发生异常，原因：" + e.getMessage());
		}
		
	}
	
	/**
	 *  在本地生成excel文件
	 * @param params
	 * @throws BizException
	 */
	private String gernerateCenterExcel(String tempPath, Map<String, Object> params) throws BizException {
		// 生成的excel报表文件的文件名
		String fileName = ObjectUtils.toString(params.get("branchCode").toString())
				+ "_" + ObjectUtils.toString(params.get("feeMonth")) + "_" + "optFeeDetail"
				+ ".xls"; 
		
		// 报表文件在web服务器上保存的路径
		String filePath = tempPath;
		
		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("预付资金");
		sheetNameList.add("次卡可用次数");
		sheetNameList.add("通用积分资金");
		sheetNameList.add("赠券资金");
		sheetNameList.add("专属积分交易笔数");
		/*sheetNameList.add("会员数");
		sheetNameList.add("折扣卡会员数");*/
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getReportResourceItem());
		titleItemList.add(getReportResourceItem());
		titleItemList.add(getReportResourceItem());
		titleItemList.add(getReportResourceItem());
		titleItemList.add(getReportResourceItem());
		/*titleItemList.add(getReportResourceMembItem());
		titleItemList.add(getReportResourceMembItem());*/
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getReportSrcPreFundData(params));
		dataLists.add(getReportSrcAccuTimeAvailData(params));
		dataLists.add(getReportSrcUniPointFundData(params));
		dataLists.add(getReportSrcCouponFundData(params));
		dataLists.add(getReportSrcSpePointTransNumData(params));
		/*dataLists.add(getReportSrcMembData(params));
		dataLists.add(getReportSrcDisCountMembData(params));*/
		
		// 报表内容的起始行号
		int starLine = 1;
		
		try {
			ExcelFileExport.generateFile(filePath, fileName, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成平台运营手续费收入明细报表-Excel文件出错。原因：" + e.getMessage());
			throw new BizException("生成平台运营手续费收入明细报表-Excel文件出错。原因：" + e.getMessage());
		}
		
		return filePath + File.separator + fileName;
	}

	/**
	 *  在本地生成excel文件
	 * @param params
	 * @throws BizException
	 */
	private String gernerateAgentExcel(String tempPath, Map<String, Object> params) throws BizException {
		// 生成的excel报表文件的文件名
		String fileName = ObjectUtils.toString(params.get("proxyId"))
		+ "_" + ObjectUtils.toString(params.get("feeMonth")) + "_" + "agentFeeShare" 
		+ ".xls"; 
		
		// 报表文件在web服务器上保存的路径
		String filePath = tempPath;
		
		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("发卡机构运营手续费月统计表");
		sheetNameList.add("消费交易");
		sheetNameList.add("积分交易");
		sheetNameList.add("次卡交易");
		sheetNameList.add("积分兑换礼品");
		sheetNameList.add("售卡充值");
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getReportResourceAgentOptItem());
		titleItemList.add(getReportResourceAgentTransItem());
		titleItemList.add(getReportResourceAgentTransItem());
		titleItemList.add(getReportResourceAgentTransItem());
		titleItemList.add(getReportResourceAgentTransItem());
		titleItemList.add(getReportResourceAgentTransItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getReportSrcAgentOptData(params));
		dataLists.add(getReportSrcAgentFeeConsum(params));
		dataLists.add(getReportSrcAgentFeePoint(params));
		dataLists.add(getReportSrcAgentFeeAccu(params));
		dataLists.add(getReportSrcAgentFeePointExcGift(params));
		dataLists.add(getReportSrcAgentFeeSale(params));
		
		// 报表内容的起始行号
		int starLine = 1;
		
		try {
			ExcelFileExport.generateFile(filePath, fileName, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("运营代理商分润月统计报表-Excel文件出错。原因：" + e.getMessage());
			throw new BizException("生成运营代理商分润月统计报表-Excel文件出错。原因：" + e.getMessage());
		}
		
		return filePath + File.separator + fileName;
	}

	/**
	 * 设置平台运营手续费明细报表数据源表头
	 * 预付资金、次卡可用次数、通用积分资金
	 * 赠券资金、专属积分交易笔数
	 * @return
	 * @throws BizException
	 */
	private Object[] getReportResourceItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		
		rowDataList.add("卡号");
		rowDataList.add("卡BIN");
		rowDataList.add("发起方编号(商户或机构)");
		rowDataList.add("发起方名称");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("分支或运营机构编号");
		rowDataList.add("分支或运营机构名称");
		rowDataList.add("交易类型");
		rowDataList.add("交易金额");
		rowDataList.add("清算金额");
		rowDataList.add("赠送通用赠券金额");
		rowDataList.add("赠送专属赠券金额");
		rowDataList.add("通用积分变动金额");
		rowDataList.add("专属积分变动金额");
		rowDataList.add("赠券账户使用金额");
		rowDataList.add("返利账户变动金额");
		rowDataList.add("预付资金变动金额");
		
		return rowDataList.toArray();
	}
	
	/**
	 * 设置平台运营手续费明细报表数据源表头
	 * 会员数、折扣卡会员数
	 * @return
	 * @throws BizException
	 */
	private String getReportResourceMembItem() throws BizException{
		String rowDataItem = "";
		rowDataItem = rowDataItem.concat("卡号").concat("|").concat("卡BIN").concat("|").concat("发卡机构编号")
			.concat("|").concat("发卡机构名称");
		
		return rowDataItem;
	}
	
	/**
	 * 设置运营代理商分润月统计报表数据源表头
	 * 发卡机构运营手续费月统计表
	 * @return
	 * @throws BizException
	 */
	private Object[] getReportResourceAgentOptItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("分支机构编号");
		rowDataList.add("分支机构名称");
		rowDataList.add("交易类型");
		rowDataList.add("交易笔数");
		rowDataList.add("交易金额");
		rowDataList.add("应付手续费金额");
		
		return rowDataList.toArray();
	}
	
	/**
	 * 设置运营代理商分润月统计报表数据源表头
	 * 消费交易、积分交易、次卡交易、积分兑换礼品、售卡充值
	 * @return
	 * @throws BizException
	 */
	private Object[] getReportResourceAgentTransItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		
		rowDataList.add("卡号");
		rowDataList.add("卡BIN");
		rowDataList.add("发起方编号(商户或机构)");
		rowDataList.add("发起方名称");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("分支或运营机构编号");
		rowDataList.add("分支或运营机构名称");
		rowDataList.add("运营代理编号");
		rowDataList.add("运营代理名称");
		rowDataList.add("交易类型");
		rowDataList.add("交易金额");
		rowDataList.add("清算金额");
		rowDataList.add("赠送通用赠券金额");
		rowDataList.add("赠送专属赠券金额");
		rowDataList.add("通用积分变动金额");
		rowDataList.add("专属积分变动金额");
		rowDataList.add("赠券账户使用金额");
		rowDataList.add("返利账户变动金额");
		rowDataList.add("预付资金变动金额");
		
		return rowDataList.toArray();
	}
	
	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 预付资金
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcPreFundData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailPreFund(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 次卡可用次数
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAccuTimeAvailData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailAccuTimeAvail(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 通用积分资金
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcUniPointFundData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailUniPointFund(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 赠券资金
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcCouponFundData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailCouponFund(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 专属积分交易笔数
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcSpePointTransNumData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailCouponFund(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 会员数
	 * @return
	 * @throws BizException
	 */
	private List<String[]> getReportSrcMembData(Map<String, Object> params) throws BizException{
		List<String[]> data = new ArrayList<String[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailMembNum(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<String> rowDataList = new ArrayList<String>();
			rowDataList.add(map.get("cardId").toString());
			rowDataList.add(map.get("cardBin").toString());
			rowDataList.add(map.get("cardIssuer").toString());
			rowDataList.add(map.get("cardIssuerName").toString());
			
			data.add(rowDataList.toArray(new String[rowDataList.size()]));
		}
		
		return data;
	}
	
	/**
	 * 把List<String[]>列表转换成List<String>
	 * @param srcList
	 * @return
	 */
	private List<String> getReportSrc(List<String[]> srcList){
		List<String> mergeList = new ArrayList<String>();
		
		for(String[] src : srcList){
			String temp = "";
			for(String singleSrc : src){
				temp = temp.concat(singleSrc).concat("|");
			}
			mergeList.add(temp.substring(0, temp.lastIndexOf("|")));
		}
		
		return mergeList;
	}

	/**
	 * 生成平台运营手续费明细报表数据源内容list
	 * 折扣卡会员数
	 * @return
	 * @throws BizException
	 */
	private List<String[]> getReportSrcDisCountMembData(Map<String, Object> params) throws BizException{
		List<String[]> data = new ArrayList<String[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findOptDetailDisCountMembNum(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<String> rowDataList = new ArrayList<String>();
			rowDataList.add(map.get("cardId").toString());
			rowDataList.add(map.get("cardBin").toString());
			rowDataList.add(map.get("cardIssuer").toString());
			rowDataList.add(map.get("cardIssuerName").toString());
			
			data.add(rowDataList.toArray(new String[rowDataList.size()]));
		}
		
		return data;
	}
	
	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 发卡机构运营手续费月统计表
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentOptData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeShareOpt(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("chlCode"));
			rowDataList.add(map.get("chlCodeName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchCodeName"));
			rowDataList.add(map.get("transType"));
			rowDataList.add(map.get("transNum"));
			rowDataList.add(map.get("amount"));
			rowDataList.add(map.get("feeAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 消费交易
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentFeeConsum(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeShareConsum(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("centProxyNo"));
			rowDataList.add(map.get("centProxyNoName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 积分交易
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentFeePoint(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeSharePoint(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("centProxyNo"));
			rowDataList.add(map.get("centProxyNoName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 次卡交易
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentFeeAccu(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeShareAccu(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("centProxyNo"));
			rowDataList.add(map.get("centProxyNoName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 积分兑换礼品
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentFeePointExcGift(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeSharePointExcGift(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("centProxyNo"));
			rowDataList.add(map.get("centProxyNoName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成运营代理商分润月统计报表数据源内容list
	 * 售卡充值 
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getReportSrcAgentFeeSale(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<Map<String, Object>> list = this.reportResourceDAO.findAgentFeeShareSaleCardRecharge(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("cardBin"));
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merName"));
			rowDataList.add(map.get("cardIssuer"));
			rowDataList.add(map.get("cardIssuerName"));
			rowDataList.add(map.get("branchCode"));
			rowDataList.add(map.get("branchName"));
			rowDataList.add(map.get("centProxyNo"));
			rowDataList.add(map.get("centProxyNoName"));
			rowDataList.add(map.get("transTypeName"));
			rowDataList.add(map.get("transAmt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("issuerCouponAmt"));
			rowDataList.add(map.get("merchCouponAmt"));
			rowDataList.add(map.get("issuerPointSettAmt"));
			rowDataList.add(map.get("merchPointSettAmt"));
			rowDataList.add(map.get("dedCouponAmt"));
			rowDataList.add(map.get("dedOtherAmt"));
			rowDataList.add(map.get("dedSubacctAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	public void getCenterOperateFeeDiscntMembTxt(Map<String, Object> params,
			String tempPath) throws BizException {
		List<String> membSrcList = getReportSrc(getReportSrcDisCountMembData(params));
		
		membSrcList.add(0, getReportResourceMembItem());
		
		// 文件名
		StringBuffer fileName = new StringBuffer();
		fileName.append(params.get("feeMonth"));
		fileName.append("_");
		fileName.append(params.get("branchCode"));
		fileName.append("_");
		fileName.append("operateFeeDiscntMemb");
		fileName.append(".txt");
		
		try {
			// 1.根据取得的数据在本地生成txt文件
			String filePath = tempPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath.toString());
			
			IOUtils.writeLines(membSrcList, null, fos, "GBK");
			IOUtils.closeQuietly(fos);
			
			// 2.下载
			IOUtil.downloadFile(filePath);
			
		} catch (FileNotFoundException e) {
			logger.error("下载txt文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载txt文件时发生异常，原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("下载txt文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载txt文件时发生异常，原因：" + e.getMessage());
		}
		
	}

	public void getCenterOperateFeeMembTxt(Map<String, Object> params,
			String tempPath) throws BizException {
		List<String> membSrcList = getReportSrc(getReportSrcMembData(params));
		membSrcList.add(0, getReportResourceMembItem());
		
		// 文件名
		StringBuffer fileName = new StringBuffer();
		fileName.append(params.get("feeMonth"));
		fileName.append("_");
		fileName.append(params.get("branchCode"));
		fileName.append("_");
		fileName.append("operateFeeMemb");
		fileName.append(".txt");
		
		try {
			// 1.根据取得的数据在本地生成txt文件
			String filePath = tempPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath.toString());
			
			IOUtils.writeLines(membSrcList, null, fos, "GBK");
			IOUtils.closeQuietly(fos);
			
			// 2.下载
			IOUtil.downloadFile(filePath);
			
		} catch (FileNotFoundException e) {
			logger.error("下载txt文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载txt文件时发生异常，原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("下载txt文件时发生异常，原因：" + e.getMessage());
			throw new BizException("下载txt文件时发生异常，原因：" + e.getMessage());
		}
		
	}

}
