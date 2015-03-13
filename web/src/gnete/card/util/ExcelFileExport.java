package gnete.card.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flink.exception.ExcelExportException;

/**
 * @File: ExcelFileExport.java
 * @description: 导出到Excel文件
 */
public class ExcelFileExport {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelFileExport.class);
	
	/**
	 * 导出Excel2007文件
	 * @param filePath 导出的Excel文件保存的路径
	 * @param excelName 导出的Excel文件名
	 * @param sheetNameList Excel文件里的sheet名的集合
	 * @param titleItemList Excel文件里每个sheet的第一行的内容的数组集合
	 * @param dataLists 内容的集合
	 * @param starLine 报表文件的开始行号（从0开始。）
	 * @return
	 * @throws ExcelExportException
	 */
	public static String generateFileXlsx(String filePath, String excelName, List<String> sheetNameList,
			List<Object[]> titleItemList, List<List<Object[]>> dataLists, int starLine)
			throws ExcelExportException {

		// 首先判断报表文件保存目录是否存在，不存在则先创建目录
		File file = new File(filePath);
		if (!(file.exists() && (file.isDirectory()))) {
			file.mkdirs();
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(filePath + File.separator + excelName));
			generateFileXlsx(fos, sheetNameList, titleItemList, dataLists, starLine);
		} catch (FileNotFoundException e) {
			throw new ExcelExportException("生成EXCEL文件出错", e);
		}
		return filePath;
	}
	/**
	 * 导出Excel2007文件
	 * @param filePath 导出的Excel文件保存的路径
	 * @param excelName 导出的Excel文件名
	 * @param sheetNameList Excel文件里的sheet名的集合
	 * @param titleItemList Excel文件里每个sheet的第一行的内容的数组集合
	 * @param dataLists 内容的集合
	 * @param starLine 报表文件的开始行号（从0开始。）
	 * @return
	 * @throws ExcelExportException
	 */
	public static void generateFileXlsx(OutputStream outputStream, List<String> sheetNameList,
			List<Object[]> titleItemList, List<List<Object[]>> dataLists, int starLine)
			throws ExcelExportException {

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 创建可写入的Excel工作薄
			if (sheetNameList.size() != titleItemList.size()) {
				logger.warn("传入sheet的个数与titleItemList的个数不一致");
				throw new ExcelExportException("参数传入错误！");
			}

			for (int i = 0; i < sheetNameList.size(); i++) {
				String sheetName = sheetNameList.get(i);
				// 添加工作表
				XSSFSheet sheet = workbook.createSheet(sheetName);
				XSSFRow firstRow = sheet.createRow(0);
				List<Integer> contentSizeList = new ArrayList<Integer>();
				// 日期单元格格式处理
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
				cellStyle.setAlignment(HorizontalAlignment.RIGHT);
				cellStyle.setWrapText(false);

				// 设置表头内容
				Object[] titles = titleItemList.get(i);
				for (int j = 0; j < titles.length; j++) {
					XSSFCell cell = firstRow.createCell(j);
					cell.setCellValue(titles[j].toString());
					cell.setCellStyle(cellStyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 表头为中文，每个中文占2个字节
					contentSizeList.add(titles[j].toString().length() * 2 + 1);
				}

				// 写内容,内容为空的话只写表头
				List<Object[]> dataList = dataLists.get(i);
				if (CollectionUtils.isEmpty(dataList)) {
					continue;
				}
				for (int k = 0; k < dataList.size(); k++) {
					XSSFRow row = sheet.createRow(k + 1);
					Object[] data = (Object[]) dataList.get(k);

					if (data.length != titles.length) {
						logger.info("{} ; {}", Arrays.toString(data) , Arrays.toString(titles));
						logger.error("表头列数（{}）与数据内容的列数（{}）不一致!", titles.length, data.length);
						throw new ExcelExportException("参数传入错误!");
					}
					for (int j = 0; j < data.length; j++) {
						XSSFCell cell = row.createCell(j);
						if (data[j] == null) {
							data[j] = "";
						} else {
							if (data[j] instanceof Double) {
								cell.setCellValue((Double) data[j]);
							} else {
								cell.setCellValue(data[j].toString());
							}
						}
						if (contentSizeList.get(j) < data[j].toString().length()) {
							contentSizeList.set(j, data[j].toString().length());
						}
						cell.setCellStyle(cellStyle);
					}
				}
				for (int j = 0; j < contentSizeList.size(); j++) {
					sheet.setColumnWidth(j, (contentSizeList.get(j) + 1) * 256);// 设置列宽
				}
			}
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			throw new ExcelExportException("生成EXCEL文件出错。", e);
		}
	}
	
	/**
	 * 导出Excel文件
	 * @param filePath 导出的Excel文件保存的路径
	 * @param excelName 导出的Excel文件名
	 * @param sheetNameList Excel文件里的sheet名的集合
	 * @param titleItemList Excel文件里每个sheet的第一行的内容的数组集合
	 * @param dataLists 内容的集合
	 * @param starLine 报表文件的开始行号（从0开始。）
	 * @return
	 * @throws ExcelExportException
	 */
	public static String generateFile(String filePath, String excelName, List<String> sheetNameList,
			List<Object[]> titleItemList, List<List<Object[]>> dataLists, int starLine)
			throws ExcelExportException {

		// 首先判断报表文件保存目录是否存在，不存在则先创建目录
		File file = new File(filePath);
		if (!(file.exists() && (file.isDirectory()))) {
			file.mkdirs();
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(filePath + File.separator + excelName));
			generateFile(fos, sheetNameList, titleItemList, dataLists, starLine);
			
		} catch (FileNotFoundException e) {
			throw new ExcelExportException("生成EXCEL文件出错", e);
		}
		return filePath;
	}
	
	/** 导出Excel文件
	 * @param outputStream
	 * @param sheetNameList
	 * @param titleItemList
	 * @param dataLists
	 * @param starLine 数据行的开始行号
	 * @throws ExcelExportException
	 */
	public static void generateFile(OutputStream outputStream, List<String> sheetNameList, 
			List<Object[]> titleItemList, List<List<Object[]>> dataLists, 
			int starLine) throws ExcelExportException {
		try {
			WritableWorkbook workbook = null;
			//创建可写入的Excel工作薄
			workbook = Workbook.createWorkbook(outputStream);
			if (sheetNameList.size() != titleItemList.size()) {
				logger.error("传入sheet的个数与titleItemList的个数不一致");
				throw new ExcelExportException("参数传入错误！");
			}
			
			for (int i = 0; i < sheetNameList.size(); i++) {
				String sheetName = sheetNameList.get(i);
				//添加工作表
				WritableSheet sheet = workbook.createSheet(sheetName, i); 
				
				WritableCellFormat format = new WritableCellFormat();
				format.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setAlignment(Alignment.RIGHT);
				format.setWrap(false);

				List<Integer> contentSizeList = new ArrayList<Integer>();
				// 设置表头内容
				Object[] titles = titleItemList.get(i);
				for (int j = 0; j < titles.length; j++){
					// 表头为中文，每个中文占2个字节
					contentSizeList.add(titles[j].toString().length()*2 + 1);
					//Label(列号,行号 ,内容 )
					Label label = new Label(j, 0, titles[j].toString(), format); //put the title in row1 
					sheet.addCell(label); 
				}
				
				// 写内容,内容为空的话只写表头
				List<Object[]> dataList = dataLists.get(i);
				if (CollectionUtils.isEmpty(dataList)) {
					continue;
				}
				for (int k = 0; k < dataList.size(); k++ ) {
					Object[] data = (Object[]) dataList.get(k);
					
					if (data.length != titles.length) {
						logger.info("{} ; {}", Arrays.toString(data) , Arrays.toString(titles));
						logger.error("表头列数（{}）与数据内容的列数（{}）不一致!", titles.length, data.length);
						throw new ExcelExportException("参数传入错误!");
					}
					for (int j = 0; j < data.length; j++) {
						String content = data[j] == null ? "" : data[j].toString();
						if (contentSizeList.get(j) < content.length()) {
							contentSizeList.set(j, content.length());
						}
						sheet.setColumnView(j, contentSizeList.get(j) + 1);
						//Label(列号,行号 ,内容 )
						Label label = new Label(j, k + starLine, data[j] == null ? "" : data[j].toString(), format);
						sheet.addCell(label);
					}
				}
			}
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			throw new ExcelExportException("生成EXCEL文件出错,发生IOException", e);
		} catch (WriteException e) {
			throw new ExcelExportException("生成EXCEL文件出错,发生WriteException", e);
		}
	}

	public static void main(String[] args) {
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("sheet1");
		sheetNameList.add("sheet2");
		sheetNameList.add("sheet3");
		
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(new String[]{"日期", "交易代号", "卡号", "交易金额" ,"交易时间"});
		titleItemList.add(new String[]{"受理商户号", "受理商户名", "日期", "充值笔数", "交易金额" ,"交易时间"});
		titleItemList.add(new String[]{"日期", "消费笔数", "消费金额", "退款金额"});
		
		List<Object[]> dataList1 = new ArrayList<Object[]>();
		dataList1.add(new Object[]{"20110211", "CNY", "2081818188181111", "200.00", "20110622"});
		dataList1.add(new Object[]{"20110213", "CNY", "2081818188181114", "200.00", "20110622"});
		dataList1.add(new Object[]{"20110212", "CNY", "2081818188181113", "200.00", "20110622"});
		dataList1.add(new Object[]{"20110214", "CNY", "2081818188181112", "200.00", "20110622"});
		dataList1.add(new Object[]{"20110215", "CNY", "2081818188181116", "200.00", "20110622"});
		dataList1.add(new Object[]{"20110216", "CNY", "2081818188181115", "200.00", "20110622"});
		List<Object[]> dataList2 = new ArrayList<Object[]>();
		dataList2.add(new Object[]{"2081111000003031", "吉之岛1", "20110101", "200", 1100.00, "20110622"});
		dataList2.add(new Object[]{"2081111000003032", "吉之岛2", "20110101", "200", 1100.01, "20110622"});
		dataList2.add(new Object[]{"2081111000003033", "吉之岛3", "20110101", "200", 1100.00, "20110622"});
		
		List<Object[]> dataList3 = new ArrayList<Object[]>();
		dataList3.add(new Object[]{"20110101", "吉之岛2", "200", "1100.00"});
		dataList3.add(new Object[]{"20110102", "吉之岛3", "200", "1100.00"});
		
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(dataList1);
		dataLists.add(dataList2);
		dataLists.add(dataList3);
		
		try {
//			ExcelFileExport.generateFile("D:", "ceshi.xls", sheetNameList, titleItemList, dataLists, 1);
			ExcelFileExport.generateFileXlsx("D:", "ceshi.xlsx", sheetNameList, titleItemList, dataLists, 1);
		} catch (ExcelExportException e) {
			e.printStackTrace();
		}
	}

}
