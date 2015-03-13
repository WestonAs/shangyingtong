package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.util.CommonHelper;
import gnete.card.dao.OldReportParaDAO;
import gnete.card.dao.TransDAO;
import gnete.card.entity.OldReportPara;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.report.OldReportType;
import gnete.card.service.OldReportService;
import gnete.card.util.ExcelFileExport;
import gnete.card.util.OldReportItemsUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("oldReportService")
public class OldReportServiceImpl implements OldReportService {
	
	private static final Logger logger = Logger.getLogger(OldReportServiceImpl.class);

	@Autowired
	private OldReportParaDAO oldReportParaDAO;
	@Autowired
	private TransDAO transDAO;
	
	public boolean addOldReportPara(OldReportPara oldReportPara, String userId)
			throws BizException {
		Assert.notNull(oldReportPara, "添加的旧报表权限对象不能为空");
		
		//检查新增对象是否已经存在
		OldReportPara old = (OldReportPara)this.oldReportParaDAO.findByPk(oldReportPara.getIssCode());
		
		Assert.isNull(old, IssType.valueOf(oldReportPara.getIssType()).getName() + 
				"["+ oldReportPara.getIssCode() + "]的旧报表权限已经设置，不能重复定义");
				
		oldReportPara.setStatus(CardTypeState.NORMAL.getValue());		
		oldReportPara.setUpdateTime(new Date());
		oldReportPara.setUpdateBy(userId);
				
		return this.oldReportParaDAO.insert(oldReportPara) != null; 
	}

	public boolean modifyOldReportPara(OldReportPara oldReportPara,
			String userId) throws BizException {
		Assert.notNull(oldReportPara, "更新的旧报表权限对象不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(oldReportPara.getStatus())){
			oldReportPara.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(oldReportPara.getStatus())){
			oldReportPara.setStatus(CardTypeState.NORMAL.getValue());
		}
		oldReportPara.setUpdateBy(userId);
		oldReportPara.setUpdateTime(new Date());
		return this.oldReportParaDAO.update(oldReportPara) > 0;
	}
	
	public boolean deleteOldReportPara(String issCode) throws BizException {
		Assert.notNull(issCode, "删除的旧报表权限对象不能为空");		
		return this.oldReportParaDAO.delete(issCode) > 0;		
	}
	
	public void generateOldExpireCardReport(String reportPath, Map<String, Object> params) throws BizException {
		// 生成的excel报表文件的文件名
		String fileName = params.get("cardIssuer").toString() 
				+ OldReportType.EXPIRE_CARD.getValue() + params.get("settDate").toString()
				+ ".xlsx"; 
		
		// 报表文件在web服务器上保存的路径
		String filePath = reportPath + File.separator + OldReportType.EXPIRE_CARD.getValue()
				+ File.separator + params.get("settDate").toString();
		
		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("过期卡受理商户明细");
		sheetNameList.add("过期卡交易终端小计");
		sheetNameList.add("过期卡交易总计");
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(OldReportItemsUtil.getExpireAcceptedDetailItem());
		titleItemList.add(OldReportItemsUtil.getExpireTransTermrlSumItem());
		titleItemList.add(OldReportItemsUtil.getExpireTransSumItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getExpireAcceptedDetailData(params));
		dataLists.add(getExpireTransTermrlSumData(params));
		dataLists.add(getExpireTransSumData(params));

		if(CommonHelper.checkIsEmpty(dataLists.get(0))){
			//没有数据不生成文件
			return;
		}
		
		// 报表内容的起始行号
		int starLine = 1;
		
		try {
			ExcelFileExport.generateFileXlsx(filePath, fileName, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成旧系统过期商户明细报表出错。原因：" + e.getMessage());
			throw new BizException("生成旧系统过期商户明细报表出错。原因：" + e.getMessage());
		}
	}
	
	
	/**
	 * 生成过期卡受理商户明细报表内容list
	 * @param settDate 清算日期
	 * @param cardIssuer 发卡机构编号
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getExpireAcceptedDetailData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findExpireCardTransReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("per"));
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("rcvTime"));
			rowDataList.add(map.get("termlId"));
			rowDataList.add(map.get("sn"));
			rowDataList.add(map.get("retrivlRefNum"));
			rowDataList.add(map.get("respCode"));
			rowDataList.add(map.get("flag"));
			rowDataList.add(map.get("oldTransSn"));
			rowDataList.add(map.get("additonInfo"));
			rowDataList.add(map.get("expirDate"));
			rowDataList.add(map.get("cardIssuer"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成过期卡交易终端小计报表内容list
	 * @param settDate 清算日期
	 * @param cardIssuer 发卡机构编号
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getExpireTransTermrlSumData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findExpireTransTermrlSumReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("termlId"));
			rowDataList.add(map.get("transCnt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("refundCnt"));
			rowDataList.add(map.get("refundAmt"));
			rowDataList.add(map.get("depositCnt"));
			rowDataList.add(map.get("depositAmt"));
			rowDataList.add(map.get("cardIssuer"));

			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 * 生成过期卡过期卡交易总计报表内容list
	 * @param settDate 清算日期
	 * @param cardIssuer 发卡机构编号
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getExpireTransSumData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findExpireTransSumReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("transCnt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("refundCnt"));
			rowDataList.add(map.get("refundAmt"));
			rowDataList.add(map.get("depositCnt"));
			rowDataList.add(map.get("depositAmt"));
			rowDataList.add(map.get("cardIssuer"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	public void generateOldActiveCardReport(String reportPath,
			Map<String, Object> params) throws BizException {
		// 生成的excel报表文件的文件名
		String fileName = params.get("cardIssuer").toString() 
				+ OldReportType.ACTIVE_CARD.getValue() + params.get("settDate").toString()
				+ ".xlsx"; 
		
		// 报表文件在web服务器上保存的路径
		String filePath = reportPath + File.separator + OldReportType.ACTIVE_CARD.getValue()
				+ File.separator + params.get("settDate").toString();
		
		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("激活明细");
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(OldReportItemsUtil.getActiveCardItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getActiveCardData(params));
		
		if(CommonHelper.checkIsEmpty(dataLists.get(0))){
			//没有数据不生成文件
			return;
		}
		
		int starLine = 1;
		
		try {
			ExcelFileExport.generateFileXlsx(filePath, fileName, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成旧系统卡激活报表出错。原因：" + e.getMessage());
			throw new BizException("生成旧系统卡激活报表出错。原因：" + e.getMessage());
		}
		
	}
	
	/**
	 * 卡激活
	 * @param params
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getActiveCardData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findActiveCardReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Double faceValue = null;
		Double cnt = null;
		Double amt = null;
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("funNo"));
			rowDataList.add(map.get("cardType"));
			rowDataList.add(map.get("minCardId"));
			rowDataList.add(map.get("maxCardId"));
			if(null != map.get("faceValue")){
				faceValue = Double.valueOf(((BigDecimal)map.get("faceValue")).toString());
			}
			rowDataList.add(faceValue);
			if(null != map.get("cnt")){
				cnt = Double.valueOf(((BigDecimal)map.get("cnt")).toString());
			}
			rowDataList.add(cnt);
			if(null != map.get("amt")){
				amt = Double.valueOf(((BigDecimal)map.get("amt")).toString());
			}
			rowDataList.add(amt);
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("updateUser"));
			rowDataList.add(map.get("cardBin"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	public void generateOldMerchDetailReport(String reportPath,
			Map<String, Object> params) throws BizException {
		// 生成的excel报表文件的文件名
		String fileName = params.get("cardIssuer").toString() 
				+ OldReportType.MERCH_DETAIL.getValue() + params.get("settDate").toString()
				+ ".xlsx"; 
		
		// 报表文件在web服务器上保存的路径
		String filePath = reportPath + File.separator + OldReportType.MERCH_DETAIL.getValue()
				+ File.separator + params.get("settDate").toString();
		
		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("受理商户明细");
		sheetNameList.add("终端小计");
		sheetNameList.add("总计");
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(OldReportItemsUtil.getMerchDetailItem());
		titleItemList.add(OldReportItemsUtil.getTerminalSubTotalItem());
		titleItemList.add(OldReportItemsUtil.getMerchDetailSumItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getMerchDetailData(params));
		dataLists.add(getTerminalSubTotalData(params));
		dataLists.add(getMerchDetailSumData(params));

		if(CommonHelper.checkIsEmpty(dataLists.get(0))){
			//没有数据不生成文件
			return;
		}
		
		int starLine = 1;
		
		try {
			ExcelFileExport.generateFileXlsx(filePath, fileName, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成旧系统商户明细报表出错。原因：" + e.getMessage());
			throw new BizException("生成旧系统商户明细报表出错。原因：" + e.getMessage());
		}
	}
	
	/**
	 * 受理商户明细
	 * @param params
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getMerchDetailData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findMerchDetailReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("per"));
			rowDataList.add(map.get("cardId"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("rcvTime"));
			rowDataList.add(map.get("termlId"));
			rowDataList.add(map.get("sn"));
			rowDataList.add(map.get("retrivlRefNum"));
			rowDataList.add(map.get("respCode"));
			rowDataList.add(map.get("flag"));
			rowDataList.add(map.get("oldTransSn"));
			rowDataList.add(map.get("additonInfo"));
			rowDataList.add(map.get("cardBin"));
//			rowDataList.add(map.get("cardIssuer"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}

	/**
	 *  终端小计报表类容list
	 * @param params
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getTerminalSubTotalData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findTerminalSubTotalReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("termlId"));
			rowDataList.add(map.get("transCnt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("revcCnt"));
			rowDataList.add(map.get("revcAmt"));
			rowDataList.add(map.get("chgCnt"));
			rowDataList.add(map.get("chgAmt"));
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
	
	/**
	 *  商户总计报表的内容list
	 * @param params
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getMerchDetailSumData(Map<String, Object> params) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		List<Map<String, Object>> list = this.transDAO.findMerchDetailSumReport(params);
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		BigDecimal totalTransCnt =  new BigDecimal(0).setScale(0);
		BigDecimal totalSettAmt = new BigDecimal(0).setScale(2);
		for (Map<String, Object> map : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(map.get("merNo"));
			rowDataList.add(map.get("merchName"));
			rowDataList.add(map.get("settDate"));
			rowDataList.add(map.get("transCnt"));
			rowDataList.add(map.get("settAmt"));
			rowDataList.add(map.get("revcCnt"));
			rowDataList.add(map.get("revcAmt"));
			rowDataList.add(map.get("chgCnt"));
			rowDataList.add(map.get("chgAmt"));
			data.add(rowDataList.toArray());
			
			totalTransCnt = totalTransCnt.add((BigDecimal)map.get("transCnt"));
			totalSettAmt = totalSettAmt.add((BigDecimal)map.get("settAmt"));
		}
		data.add(new Object[]{"", "" , "消费总计", totalTransCnt, totalSettAmt, "", "", "", "" });
		return data;
	}

}
