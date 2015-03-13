package gnete.card.service.impl;

import flink.exception.ExcelExportException;
import flink.file.ExcelFileImport;
import flink.file.ExcelImportException;
import flink.util.DateUtil;
import flink.util.StringUtil;
import flink.util.Validator;
import gnete.card.dao.SequenceDAO;
import gnete.card.dao.WashCarSvcMemberShipDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WashCarSvcMbShipDues;
import gnete.card.service.WashCarSvcMbShipDuesService;
import gnete.card.util.ExcelFileExport;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("washCarSvcMbShipDuesService")
public class WashCarSvcMbShipDuesServiceImpl implements WashCarSvcMbShipDuesService {
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private WashCarSvcMemberShipDAO washCarSvcMemberShipDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SequenceDAO sequenceDAO;
	//生成缴交会费Excel模版
	@Override
	public void exportWashCarSvcMbShipExcel(HttpServletResponse response) throws BizException {
		
		logger.info("生成缴交会费导入Excel模版");
		
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("缴交会费Excel模版");
		
		//每个工作表的表头集合
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getMsgSendFileWashCarItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getMsgSendFileWashCarData());
		
		// 报表内容的起始行号
		int starLine = 1;
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-Type", "application/ms-excel");

			String fileName = ("缴交会费Excel" + DateUtil.formatDate("yyyyMMddHHmmss") + ".xls");
			fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");

			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			ExcelFileExport.generateFile(outputStream, sheetNameList,titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成缴交会费Excel文件发生ExcelExportException。原因："
					+ e.getMessage());
			throw new BizException("生成缴交会费Excel文件发生ExcelExportException。原因："
					+ e.getMessage());
		} catch (IOException e) {
			logger.error("生成缴交会费Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成缴交会费Excel文件发生ExcelExportException。原因："
					+ e.getMessage());
		}
	}

	private List<Object[]> getMsgSendFileWashCarData() {
		List<Object[]> date = new ArrayList<Object[]>();
		for(int i = 0;i<100;i++){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(null);
			rowDataList.add(null);
			rowDataList.add(null);
			rowDataList.add(null);
			rowDataList.add(null);
			date.add(rowDataList.toArray());
		}
		return date;
	}

	private static Object[] getMsgSendFileWashCarItem()throws BizException {
		List<String> rowDataList = new ArrayList<String>();
	
		rowDataList.add("卡号(农行按规则生成的外部卡号)");
		rowDataList.add("缴费金额");
		rowDataList.add("缴费状态");
		rowDataList.add("缴费有效开始时间(YYYYMMDD)");
		rowDataList.add("缴费有效结束时间(YYYYMMDD)");
		
		return rowDataList.toArray();
	}
    
	//导入Excel文件
	@Override
	public void importWashCarSvcMbShipExcel(File file,WashCarSvcMbShipDues washCarSvcMbShipDues,UserInfo sessionUser)throws BizException {
       Assert.notNull(file,"导入文件不能为空");
       
       String[] titles = {"卡号(农行按规则生成的外部卡号)","缴费金额","缴费状态","缴费有效开始时间(YYYYMMDD)","缴费有效结束时间(YYYYMMDD)"};
       List<List<Object[]>> allSheetList = null;
       try{
    	   allSheetList = ExcelFileImport.readFile(file,titles);
       }catch(ExcelImportException e){
    	   logger.debug("读取Excel文件的时候出错",e);
    	   throw new BizException("读取Excel文件的出现错误,原因：" + e.getMessage());
       }
       
       Assert.notEmpty(allSheetList, "要导入的Excel文档内容不能为空");
       
       List<Object[]> resultList = allSheetList.get(0);
       
       /** 数据明细起始行 */
		int line = 2;
		//批量导入时，生成批量流水号
		Long flows = null;
//		List<WashCarSvcMbShipDues> inertWashList = new ArrayList<WashCarSvcMbShipDues>();//新增集合
//		List<WashCarSvcMbShipDues> updateWashList = new ArrayList<WashCarSvcMbShipDues>();//数据重复更新集合
		for(Object[] object : resultList){
			String cardId = object[0].toString().trim();//卡号(农行按规则生成的外部卡号)
			String tollAmt = object[1].toString().trim();//缴费金额
			String status = object[2].toString().trim();//缴费状态
			String tollStartDate = object[3].toString().trim();//开始时间
			String tollEndDate = object[4].toString().trim();//结束时间
			Assert.isTrue(Validator.isDouble(tollAmt), "第" + line + "行,金额请输入数字形式正确格式");
			Assert.isTrue(StringUtil.isNumeric(tollStartDate), "第" + line + "行,开始时间请输入正确格式YYYYMMDD");
			Assert.isTrue(StringUtil.isNumeric(tollEndDate), "第" + line + "行,结束时间请输入正确格式YYYYMMDD");
			Assert.isTrue(tollStartDate.length() == 8, "第" + line + "行,开始时间请输入正确长度YYYYMMDD");
			Assert.isTrue(tollEndDate.length() == 8, "第" + line + "行,结束时间请输入正确长度YYYYMMDD");
			Assert.isTrue(checkStatus(status), "第" + line + "行,缴费状态请输入数字0或数字1");
			WashCarSvcMbShipDues addWashCarDues = null;
			WashCarSvcMbShipDues updateWashCarDues = null;
			
			
			if(status.equals("0")){
				System.out.println(checkCardId(cardId));
				//检测数据库是否有未缴费记录,同卡号只能保留一条未缴费记录
				flows = this.sequenceDAO.getSequence("WASH_CAR_SVC_MB_SHIP_ID");
				if(checkCardId(cardId)){
					System.out.println("1");
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("cardId", cardId);
					params.put("status", "0");
					updateWashCarDues = (WashCarSvcMbShipDues) this.washCarSvcMemberShipDAO.findWashCarMb(params);
					if(updateWashCarDues != null){
						updateWashCarDues.setTollAmt(new BigDecimal(tollAmt));
						updateWashCarDues.setStatus(status);
						updateWashCarDues.setTollStartDate(tollStartDate);
						updateWashCarDues.setTollEndDate(tollEndDate);
					}
					washCarSvcMemberShipDAO.update(updateWashCarDues);
				}else{
					//检测没有未缴费记录所有导入数据已新增导入
					System.out.println("2");
					addWashCarDues = new WashCarSvcMbShipDues();
					addWashCarDues.setId(flows);
					addWashCarDues.setCardId(cardId);
					addWashCarDues.setCardIssuer(sessionUser.getBranchNo());
					addWashCarDues.setTollAmt(new BigDecimal(tollAmt));
					addWashCarDues.setStatus(status);
					addWashCarDues.setTollStartDate(tollStartDate);
					addWashCarDues.setTollEndDate(tollEndDate);
					addWashCarDues.setCheckStatus("00");
					addWashCarDues.setInsertUser(sessionUser.getUserId());
					addWashCarDues.setInsertTime(new Date());
					washCarSvcMemberShipDAO.insert(addWashCarDues);
				}
			}else{
				flows = this.sequenceDAO.getSequence("WASH_CAR_SVC_MB_SHIP_ID");
				//检测没有未缴费记录所有导入数据已新增导入
				System.out.println("2");
				addWashCarDues = new WashCarSvcMbShipDues();
				addWashCarDues.setId(flows);
				addWashCarDues.setCardId(cardId);
				addWashCarDues.setCardIssuer(sessionUser.getBranchNo());
				addWashCarDues.setTollAmt(new BigDecimal(tollAmt));
				addWashCarDues.setStatus(status);
				addWashCarDues.setTollStartDate(tollStartDate);
				addWashCarDues.setTollEndDate(tollEndDate);
				addWashCarDues.setCheckStatus("00");
				addWashCarDues.setInsertUser(sessionUser.getUserId());
				addWashCarDues.setInsertTime(new Date());
				washCarSvcMemberShipDAO.insert(addWashCarDues);
			}
			
			if(addWashCarDues != null&&addWashCarDues.getId() != null){
				// 启动审核流程
				try {
					this.workflowService.startFlow(addWashCarDues, WorkflowConstants.WASH_CAR_SVC_MBSHIP_DUES_ADAPTER,flows.toString(), sessionUser);
				} catch (Exception e) {
					throw new BizException("启动机构审核流程时发生异常，原因：" + e.getMessage());
				}
			}
			addWashCarDues = null;
			updateWashCarDues = null;
		    line++;
		}
//		washCarSvcMemberShipDAO.updateBatch(updateWashList);
//		washCarSvcMemberShipDAO.insertBatch(inertWashList);
		
	}
	
	//检测是否同卡号
	private boolean checkCardId(String cardId){
		boolean fag = false;
		List<WashCarSvcMbShipDues> allWashList = this.washCarSvcMemberShipDAO.washCarList("0");//缴交会员所有数据集合
		for(WashCarSvcMbShipDues washCarSvcMbShipDues : allWashList){
			if(washCarSvcMbShipDues.getCardId().equals(cardId)){
				fag = true;
			}
		}
		return fag;
	}
	
	//检测状态格式输入是否正确
	private boolean checkStatus(String status){
		boolean fag = false;
		if(status.equals("0")||status.equals("1")){
			fag = true;
		}
		return fag;
	}
}
