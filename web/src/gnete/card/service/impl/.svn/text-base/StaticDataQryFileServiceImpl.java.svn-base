package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.GdnhglIssuerCardStaticDAO;
import gnete.card.dao.GdysIssuerTermStaticDAO;
import gnete.card.dao.LogIpGuardDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.StatisticsCardInfoDAO;
import gnete.card.dao.TransCenterMothDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.GdnhglIssuerCardStatic;
import gnete.card.entity.GdysIssuerTermStatic;
import gnete.card.entity.LogIpGuard;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.StatisticsCardBjInfo;
import gnete.card.entity.StatisticsCardInfo;
import gnete.card.entity.TransCenterMoth;
import gnete.card.service.StaticDataQryFileService;
import gnete.card.util.ExcelFileExport;
import gnete.etc.BizException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("staticDataQryFileService")
public class StaticDataQryFileServiceImpl implements StaticDataQryFileService {
    
	static Logger logger = LoggerFactory.getLogger(StaticDataQryFileServiceImpl.class);
	
	//DAO
	@Autowired
	private LogIpGuardDAO logIpGuardDAO;
	@Autowired
	private TransCenterMothDAO transCenterMothDAO;
	@Autowired
	private GdysIssuerTermStaticDAO gdysIssuerTermStaticDAO;
	@Autowired
	private MerchInfoDAO merhcInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private GdnhglIssuerCardStaticDAO gdnhglIssuerCardStaticDAO;
	@Autowired
	private StatisticsCardInfoDAO statisticsCardInfoDAO;
	
	@Override
	public void generateipStaticCsv(HttpServletResponse response,
			Map<String, Object> params) throws BizException, IOException {
		
		 response.setContentType("application/csv;charset=gb18030");
		 response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("ip地址监控统计.csv".getBytes("gb18030"), "ISO-8859-1"));
		 
		 StringBuilder title = ipStaticTitle();
		 response.getWriter().println(title);
		 response.getWriter().flush();
		 int pageNumber = 1;
		 int pageSize = 51200;
		 logger.info("分页查询，每页[{}]条", pageSize);
		 Paginater paginater = null;
		 do {
				paginater = this.logIpGuardDAO.findLogIpGuard(params, pageNumber, pageSize);
				for (Object obj : paginater.getData()) {
					LogIpGuard logIpGuard = (LogIpGuard) obj;
					StringBuilder line = buildLogIpGuardCvsLine(logIpGuard);
					response.getWriter().print(line);
				}
				pageNumber++;
			} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}
    
	private StringBuilder ipStaticTitle(){
		StringBuilder title = new StringBuilder();
		title.append("统计日期,IP,管理机构,管理机构名称,商户名称,登录次数");
		
		return title;
	}
	
	private StringBuilder buildLogIpGuardCvsLine(LogIpGuard logIpGuard) {
		StringBuilder line = new StringBuilder();
		line.append(StringUtils.defaultString(logIpGuard.getStatDate()));
		line.append(",\t").append(StringUtils.defaultString(logIpGuard.getLoginIp()));
		line.append(",\t").append(StringUtils.defaultString(logIpGuard.getBranchNo()));
		line.append(",\t").append(StringUtils.defaultString(logIpGuard.getBranchName()));
		line.append(",\t").append(StringUtils.defaultString(logIpGuard.getMerchName()));
		line.append(",").append(StringUtils.defaultString(logIpGuard.getLogcnt()));
		line.append("\r\t");
		return line;
	}

	@Override
	public void generatefenZhiConsumeStaticCsv(Map<String,Object> params,HttpServletResponse response
			) throws BizException, IOException {
		
		 response.setContentType("application/csv;charset=gb18030");
		 response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("分支机构月消费数据统计.csv".getBytes("gb18030"), "ISO-8859-1"));
		 
		 StringBuilder title = fenZhiConsumeStaticTitle();
		 response.getWriter().println(title);
		 response.getWriter().flush();
		 int pageNumber = 1;
		 int pageSize = 51200;
		 logger.info("分页查询，每页[{}]条", pageSize);
		 Paginater paginater = null;
		 
		 do {
				paginater = this.transCenterMothDAO.findTransCenterMoth(params, pageNumber, pageSize);
				for (Object obj : paginater.getData()) {
					TransCenterMoth newTransCenterMonth = (TransCenterMoth) obj;
					StringBuilder line = fenZhiConsumeStaticCvsLine(newTransCenterMonth);
					response.getWriter().print(line);
				}
				pageNumber++;
			} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}

	private StringBuilder fenZhiConsumeStaticTitle() {
		StringBuilder title = new StringBuilder();
		title.append("统计月份,分支机构编号,分支机构名称,消费总笔数,消费总金额,运营手续费");
		return title;
	}
    
	private StringBuilder fenZhiConsumeStaticCvsLine(
			TransCenterMoth newTransCenterMonth) {
		StringBuilder line = new StringBuilder();
		String feeAmt = "";
		line.append(newTransCenterMonth.getMonth());
		line.append(",\t").append(StringUtils.defaultString(newTransCenterMonth.getParent()));
		line.append(",\t").append(StringUtils.defaultString(newTransCenterMonth.getBranchName()));
		line.append(",\t").append(StringUtils.defaultString(newTransCenterMonth.getTransNum().toString()));
		line.append(",\t").append(StringUtils.defaultString(newTransCenterMonth.getSettAmt().toString()));
		if(newTransCenterMonth.getSettAmt() != null 
		  ||!newTransCenterMonth.getSettAmt().equals("")){
			feeAmt = AmountUtil.multiply(newTransCenterMonth.getSettAmt(),new BigDecimal(0.001)).setScale(2, 
					RoundingMode.HALF_UP).toString();
		}
		line.append(",\t").append(StringUtils.defaultString(feeAmt));
		line.append("\r\t");
		return line;
	}

	@Override
	public void generateGdysTermConsumeMStaticCsv(HttpServletResponse response,
			GdysIssuerTermStatic gdysIssuerTermStatic) throws BizException,
			IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("广东银商终端消费月数据统计.csv".getBytes("gb18030"), "ISO-8859-1"));
		
		 StringBuilder title = gdysTermConsumeMStaticTitle();
		 response.getWriter().println(title);
		 response.getWriter().flush();
		 int pageNumber = 1;
		 int pageSize = 51200;
		 logger.info("分页查询，每页[{}]条", pageSize);
		 Paginater paginater = null;
		 
		 do {
				paginater = this.gdysIssuerTermStaticDAO.findGdysIssuerTermStatic(gdysIssuerTermStatic,pageNumber, pageSize);
				for (Object obj : paginater.getData()) {
					GdysIssuerTermStatic termStatic = (GdysIssuerTermStatic) obj;
					StringBuilder line = gdysIssuerTermStaticCvsLine(termStatic);
					response.getWriter().print(line);
				}
				pageNumber++;
			} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());		
	}
    
	private StringBuilder gdysTermConsumeMStaticTitle() {
		StringBuilder title = new StringBuilder();
		title.append("统计月份,发卡机构,商户,终端号,总数量,总金额");
		return title;
	}
	
	@SuppressWarnings("unchecked")
	private StringBuilder gdysIssuerTermStaticCvsLine(
			GdysIssuerTermStatic termStatic) {
		StringBuilder line = new StringBuilder();
		String branchName = "";
		String merchName = "";
		List<BranchInfo> branchList = this.branchInfoDAO.findAll();
		List<MerchInfo> merchList = this.merhcInfoDAO.findAll();
		//取发卡机构名称
		for(BranchInfo branchInfo:branchList){
			if(branchInfo.getBranchCode().equals(termStatic.getCardIssuer())){
				branchName = branchInfo.getBranchName();
			}
		}
		//取商户名称
		for(MerchInfo merchInfo:merchList){
			if(merchInfo.getMerchId().equals(termStatic.getMerNo())){
				merchName = merchInfo.getMerchName();
			}
		}
		
		line.append(termStatic.getMonth());
		line.append(",\t").append(StringUtils.defaultString(termStatic.getCardIssuer() + "-" + branchName));
		line.append(",\t").append(StringUtils.defaultString(termStatic.getMerNo() + "-" + merchName));
		line.append(",\t").append(StringUtils.defaultString(termStatic.getTermlId()));
		line.append(",\t").append(StringUtils.defaultString(termStatic.getTotalnum().toString()));
		line.append(",\t").append(StringUtils.defaultString(termStatic.getTotalamt().toString()));
		line.append("\r\t");
		return line;
	}

	@Override
	public void generateNhglStaticCsv(HttpServletResponse response,
			GdnhglIssuerCardStatic gdnhglIssuerCardStatic) throws BizException,
			IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("南湖国旅消费充值月统计.csv".getBytes("gb18030"), "ISO-8859-1"));
		
		 StringBuilder title = gdysNhglStaticTitle();
		 response.getWriter().println(title);
		 response.getWriter().flush();
		 int pageNumber = 1;
		 int pageSize = 51200;
		 logger.info("分页查询，每页[{}]条", pageSize);
		 Paginater paginater = null;
		 
		 do {
				paginater = this.gdnhglIssuerCardStaticDAO.findGdnhglIssuerCardStatic(gdnhglIssuerCardStatic, pageNumber, pageSize);
				for (Object obj : paginater.getData()) {
					GdnhglIssuerCardStatic newGdnhglIssuerCard = (GdnhglIssuerCardStatic) obj;
					StringBuilder line = gdysIssuerNhglStaticCvsLine(newGdnhglIssuerCard);
					response.getWriter().print(line);
				}
				pageNumber++;
			} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());		
	}
    
	private StringBuilder gdysNhglStaticTitle() {
		StringBuilder title = new StringBuilder();
		title.append("统计月份,卡号,充值金额,消费金额,余额");
		return title;
	}
	
	private StringBuilder gdysIssuerNhglStaticCvsLine(
			GdnhglIssuerCardStatic newGdnhglIssuerCard) {
		StringBuilder line = new StringBuilder();
		line.append(newGdnhglIssuerCard.getMonth());
		line.append(",\t").append(StringUtils.defaultString(newGdnhglIssuerCard.getCardId()));
		line.append(",\t").append(StringUtils.defaultString(newGdnhglIssuerCard.getIncrAmt().toString()));
		line.append(",\t").append(StringUtils.defaultString(newGdnhglIssuerCard.getPerAmt().toString()));
		line.append(",\t").append(StringUtils.defaultString(newGdnhglIssuerCard.getBal().toString()));
		line.append("\r\t");
		return line;
	}

	@Override
	public void generateJzdStaticExcel(HttpServletResponse response,
			StatisticsCardInfo statisticsCardInfo) throws BizException{
        
		List<StatisticsCardInfo> cardInfoList = this.statisticsCardInfoDAO.findCardInfoList(statisticsCardInfo);
		
		if(cardInfoList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("吉之岛状态汇总统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getStatusItemTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getSattusData(cardInfoList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "吉之岛状态汇总统计" + DateUtil.formatDate("yyyyMM") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("生成吉之岛状态汇总统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("生成吉之岛状态汇总统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("生成吉之岛状态汇总统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("生成吉之岛状态汇总统计Excel文件发生IOException。原因：" + e.getMessage());
		}
	}
    
	@SuppressWarnings("unchecked")
	private List<Object[]> getSattusData(List<StatisticsCardInfo> cardInfoList) {
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(cardInfoList)) {
			return Collections.<Object[]> emptyList();
		}
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		for(StatisticsCardInfo cardInfo:cardInfoList){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(cardInfo.getCardStatus());
			rowDataList.add(cardInfo.getStatMonth());
			rowDataList.add(cardInfo.getTotalCardNum());
			rowDataList.add(cardInfo.getTotalFaceValue().setScale(2, RoundingMode.HALF_UP));
			rowDataList.add(cardInfo.getTotalBal().setScale(2, RoundingMode.HALF_UP));
			
			data.add(rowDataList.toArray());
		}
		return data;
	}

	private Object[] getStatusItemTitle() {
		List<String> titleList = new ArrayList<String>();
		titleList.add("卡状态");
		titleList.add("有效期");
		titleList.add("积分卡张数");
		titleList.add("积分卡原值");
		titleList.add("积分卡余额");
		return titleList.toArray();
	}
	
	/** 设置response的Header信息 */
	private void buildResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.setHeader("Content-Type", "application/ms-excel");
		fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	}

	@Override
	public void generateipStaticExcel(HttpServletResponse response,
			Map<String, Object> params) throws BizException {
		List<LogIpGuard> logIpGuardList = this.logIpGuardDAO.findLogIpGuard(params);
		if(logIpGuardList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("ip地址监控统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getlogIpGuardItemTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getlogIpGuardData(logIpGuardList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "ip地址监控统计" + DateUtil.formatDate("yyyyMM") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("ip地址监控统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("ip地址监控统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("ip地址监控统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("ip地址监控统计Excel文件发生IOException。原因：" + e.getMessage());
		}
	}

	private List<Object[]> getlogIpGuardData(List<LogIpGuard> logIpGuardList) {
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(logIpGuardList)) {
			return Collections.<Object[]> emptyList();
		}
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		for(LogIpGuard logIpGuard:logIpGuardList){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(logIpGuard.getStatDate());
			rowDataList.add(logIpGuard.getLoginIp());
			rowDataList.add(logIpGuard.getBranchNo());
			rowDataList.add(logIpGuard.getBranchName());
			rowDataList.add(logIpGuard.getMerchName());
			rowDataList.add(logIpGuard.getLogcnt());
			data.add(rowDataList.toArray());
		}
		return data;
	}

	private Object[] getlogIpGuardItemTitle() {
		List<String> title = new ArrayList<String>();
		title.add("统计日期");
		title.add("IP");
		title.add("管理机构编号");
		title.add("管理机构名称");
		title.add("商户名称");
		title.add("登录次数");
		return title.toArray();
	}

	@Override
	public void generateGdysTermConsumeMStaticExcel(
			HttpServletResponse response,GdysIssuerTermStatic gdysIssuerTermStatic) throws BizException {
		List<GdysIssuerTermStatic> gdysIssuerTermList = this.gdysIssuerTermStaticDAO.findGdysIssuerTermStatic(gdysIssuerTermStatic);
		if(gdysIssuerTermList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("广东银商充值消费月统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getgdysIssuerTermItemTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getgdysIssuerTermData(gdysIssuerTermList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "广东银商充值消费月统计" + DateUtil.formatDate("yyyyMM") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("广东银商充值消费月统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("广东银商充值消费月统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("广东银商充值消费月统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("广东银商充值消费月统计Excel文件发生IOException。原因：" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getgdysIssuerTermData(
			List<GdysIssuerTermStatic> gdysIssuerTermList) {
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(gdysIssuerTermList)) {
			return Collections.<Object[]> emptyList();
		}
		
		String branchName = "";
		String merchName = "";
		List<BranchInfo> branchList = this.branchInfoDAO.findAll();
		List<MerchInfo> merchList = this.merhcInfoDAO.findAll();
		
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		for(GdysIssuerTermStatic gdysIssuerTermStatic:gdysIssuerTermList){
			
			//取发卡机构名称
			for(BranchInfo branchInfo:branchList){
				if(branchInfo.getBranchCode().equals(gdysIssuerTermStatic.getCardIssuer())){
					branchName = branchInfo.getBranchName();
				}
			}
			//取商户名称
			for(MerchInfo merchInfo:merchList){
				if(merchInfo.getMerchId().equals(gdysIssuerTermStatic.getMerNo())){
					merchName = merchInfo.getMerchName();
				}
			}
			
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(gdysIssuerTermStatic.getMonth());
			rowDataList.add(gdysIssuerTermStatic.getCardIssuer());
			rowDataList.add(branchName);
			rowDataList.add(gdysIssuerTermStatic.getMerNo());
			rowDataList.add(merchName);
			rowDataList.add(gdysIssuerTermStatic.getTermlId());
			rowDataList.add(gdysIssuerTermStatic.getTotalnum());
			rowDataList.add(gdysIssuerTermStatic.getTotalamt());
			data.add(rowDataList.toArray());
		}
		return data;
	}

	private Object[] getgdysIssuerTermItemTitle() {
		List<String> title  = new ArrayList<String>();
		title.add("统计月份");
		title.add("发卡机构编号");
		title.add("发卡机构名称");
		title.add("商户编号");
		title.add("商户名称");
		title.add("终端编号");
		title.add("总数量");
		title.add("总金额");
		
		return title.toArray();
	}

	@Override
	public void generatefenZhiConsumeStaticExcel(Map<String,Object> params,HttpServletResponse response
			) throws BizException {
        
		List<TransCenterMoth> transCenterMothList = this.transCenterMothDAO.findTransCenterMoth(params);
		if(transCenterMothList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("分支机构月消费数据统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getTransCenterMothTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getTransCenterMothData(transCenterMothList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "分支机构月消费数据统计" + DateUtil.formatDate("yyyyMM") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("分支机构月消费数据统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("分支机构月消费数据统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("分支机构月消费数据统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("分支机构月消费数据统计Excel文件发生IOException。原因：" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getTransCenterMothData(
			List<TransCenterMoth> transCenterMothList) {
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(transCenterMothList)) {
			return Collections.<Object[]> emptyList();
		}
		
		String branchName = "";
		String feeAmt = "";
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<BranchInfo> branchList = this.branchInfoDAO.findAll();
		
		for(TransCenterMoth transCenterMoth:transCenterMothList){
			
			for(BranchInfo branchInfo:branchList){
				if(branchInfo.getBranchCode().equals(transCenterMoth.getParent())){
					branchName = branchInfo.getBranchName();
				}
			}
			
			
			feeAmt = AmountUtil.multiply(transCenterMoth.getSettAmt(),new BigDecimal(0.001)).setScale(5, 
					RoundingMode.HALF_UP).toString();
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(transCenterMoth.getMonth());
			rowDataList.add(transCenterMoth.getParent());
			rowDataList.add(branchName);
			rowDataList.add(transCenterMoth.getTransNum());
			rowDataList.add(transCenterMoth.getSettAmt());
			rowDataList.add(feeAmt);
			data.add(rowDataList.toArray());
			branchName = "";
		}
		return data;
	}

	private Object[] getTransCenterMothTitle() {
		List<String> title = new ArrayList<String>();
		title.add("统计月份");
		title.add("分支机构编号");
		title.add("分支机构名称");
		title.add("消费总笔数");
		title.add("消费总金额");
		title.add("运营手续费");
		return title.toArray();
	}

	@Override
	public void generateNhglStaticExcel(HttpServletResponse response,
			GdnhglIssuerCardStatic gdnhglIssuerCardStatic) throws BizException {
		List<GdnhglIssuerCardStatic> gdnhglIssuerCardStaticList = this.gdnhglIssuerCardStaticDAO.findGdnhglIssuerCardStatic(gdnhglIssuerCardStatic);
		if(gdnhglIssuerCardStaticList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("南湖国旅消费充值卡余额数据统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getGdnhglIssuerCardTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getGdnhglIssuerCardData(gdnhglIssuerCardStaticList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "南湖国旅消费充值卡余额数据统计" + DateUtil.formatDate("yyyyMM") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("南湖国旅消费充值卡余额数据统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("南湖国旅消费充值卡余额数据统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("南湖国旅消费充值卡余额数据统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("南湖国旅消费充值卡余额数据统计Excel文件发生IOException。原因：" + e.getMessage());
		}	
	}

	private List<Object[]> getGdnhglIssuerCardData(
			List<GdnhglIssuerCardStatic> gdnhglIssuerCardStaticList) {
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(gdnhglIssuerCardStaticList)) {
			return Collections.<Object[]> emptyList();
		}
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		for(GdnhglIssuerCardStatic gdnhglIssuerCardStatic:gdnhglIssuerCardStaticList){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(gdnhglIssuerCardStatic.getMonth());
			rowDataList.add(gdnhglIssuerCardStatic.getCardId());
			rowDataList.add(gdnhglIssuerCardStatic.getIncrAmt().setScale(2, RoundingMode.HALF_UP));
			rowDataList.add(gdnhglIssuerCardStatic.getPerAmt().setScale(2, RoundingMode.HALF_UP));
			rowDataList.add(gdnhglIssuerCardStatic.getBal().setScale(2, RoundingMode.HALF_UP));
			data.add(rowDataList.toArray());
		}
		return data;
	}

	private Object[] getGdnhglIssuerCardTitle() {
		List<String> title = new ArrayList<String>();
		title.add("统计月份");
		title.add("卡号");
		title.add("充值金额");
		title.add("消费金额");
		title.add("余额");
		return title.toArray();
	}

	@Override
	public void generateBjStaticExcel(HttpServletResponse response,
			StatisticsCardBjInfo statisticsCardBjInfo) throws BizException {
		List<StatisticsCardBjInfo> statisticsCardBjList = this.statisticsCardInfoDAO.findCardBjInfoList(statisticsCardBjInfo);
		if(statisticsCardBjList.size() <= 0){
			throw new BizException("要导出数据数量为0,请先查询确认数据");
		}
		
		//工作表的集合sheet
		List<String> sheetNameList = Arrays.asList("百佳汇总统计");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(statisticsCardBjItemTitle());
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(statisticsCardBjData(statisticsCardBjList));
	    
		int starLine = 1;
		try{
			this.buildResponseHeader(response, "百佳汇总统计" + DateUtil.formatDate("yyyyMMdd") + ".xls");
			ExcelFileExport.generateFile(response.getOutputStream(), sheetNameList, titleItemList, dataLists, starLine);
		}catch(ExcelExportException e){
			logger.error("百佳汇总统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
			throw new BizException("百佳汇总统计Excel文件发生ExcelExportException。原因：" + e.getMessage());
		}catch(IOException e){
			logger.error("百佳汇总统计Excel文件发生IOException。原因：" + e.getMessage());
			throw new BizException("百佳汇总统计Excel文件发生IOException。原因：" + e.getMessage());
		}
	}

	private Object[] statisticsCardBjItemTitle() {
		List<String> title = new ArrayList<String>();
		title.add("发卡机构编号");
		title.add("发卡机构名称");
		title.add("汇总日期");
		title.add("到期日");
		title.add("待制卡");
		title.add("已制卡");
		title.add("已入库");
		title.add("已领卡待售");
		title.add("预售出");
		title.add("正常（已激活）");
		title.add("已过期");
		title.add("已退卡(销户)");
		title.add("挂失");
		title.add("挂失已补卡");
		title.add("已延期");
		title.add("已回收");
		title.add("锁定");
		title.add("合计");
		return title.toArray();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> statisticsCardBjData(
			List<StatisticsCardBjInfo> statisticsCardBjList) {
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(statisticsCardBjList)) {
			return Collections.<Object[]> emptyList();
		}
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		List<BranchInfo> branchList = this.branchInfoDAO.findAll();
		String branchName = "";
		
		for(StatisticsCardBjInfo statisticsCardBjInfo:statisticsCardBjList){
			
			for(BranchInfo branchInfo:branchList){
				if(statisticsCardBjInfo.getCardIssuer().equals(branchInfo.getBranchCode())){
					branchName = branchInfo.getBranchName();
				}
			}
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(statisticsCardBjInfo.getCardIssuer());
			rowDataList.add(branchName);
			rowDataList.add(statisticsCardBjInfo.getStaticData());
			rowDataList.add(statisticsCardBjInfo.getValidDt());
			rowDataList.add(statisticsCardBjInfo.getSta0());
			rowDataList.add(statisticsCardBjInfo.getSta1());
			rowDataList.add(statisticsCardBjInfo.getSta2());
			rowDataList.add(statisticsCardBjInfo.getSta3());
			rowDataList.add(statisticsCardBjInfo.getSta4());
			rowDataList.add(statisticsCardBjInfo.getSta5());
			rowDataList.add(statisticsCardBjInfo.getSta6());
			rowDataList.add(statisticsCardBjInfo.getSta7());
			rowDataList.add(statisticsCardBjInfo.getSta8());
			rowDataList.add(statisticsCardBjInfo.getSta9());
			rowDataList.add(statisticsCardBjInfo.getSta10());
			rowDataList.add(statisticsCardBjInfo.getSta11());
			rowDataList.add(statisticsCardBjInfo.getSta12());
			rowDataList.add(statisticsCardBjInfo.getTotal());
			data.add(rowDataList.toArray());
		}
		return data;
	}
}