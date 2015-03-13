package flink.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import flink.util.FileHelper;

/***
 * @File: ExcelFileImport.java
 * @description: 从Excel2003中导入文件
 * @author slt02
 */
@SuppressWarnings("unchecked")
public class ExcelFileImport {

	/** Log4J Logger for this class */
	private static final Logger logger = Logger.getLogger(ExcelFileImport.class);
	
	/**
	 * 从Excel中读取文件。
	 * @param workbook
	 * @return
	 * @throws ExcelImportException
	 */
	public static List<List<Object[]>> readFile(Workbook workbook, String[] titiles) throws ExcelImportException {
		List<List<Object[]>> result = null;
		if (workbook == null) {
			return null;
		}
		
		try {
			// 取得所有工作表(sheet)对象
			Sheet[] sheets = workbook.getSheets();
			
			if (ArrayUtils.isEmpty(sheets)) {
				return ListUtils.EMPTY_LIST;
			}
			
			result = new ArrayList<List<Object[]>>();
			
			// 遍历每个工作表，将得到的数据放到一个List<Object[]>中
			for (int i = 0; i < sheets.length; i++) {
				Sheet sheet = sheets[i];
				
				List<Object[]> sheetList = new ArrayList<Object[]>();
				
				// 表头的个数 
				int cellNum = 0;
				
				if (ArrayUtils.isEmpty(titiles)) {
					// 取出表头的内容，主要是取得元素的个数
					Cell[] titleCell = sheet.getRow(0);
					if (ArrayUtils.isEmpty(titleCell)) {
						continue;
					}
					cellNum = titleCell.length;

					StringBuilder sb = new StringBuilder(64);
					
					for (int m = 0; m < titleCell.length; m++) {
						sb.append(StringUtils.trim(titleCell[m].getContents()));
						sb.append(";");
					}
					logger.debug("title content: [" + sb.toString() + "]");
				} else {
					cellNum = titiles.length;
				}
				

				// 得到当前行的所有单元格。要忽略第一行的内容,第一行是表头
				for (int j = 1; j < sheet.getRows(); j++) {
					List<String> cellList = new ArrayList<String>();
					
					Cell[] cells = sheet.getRow(j); // 取出第一行的内容（非表头）
					if (ArrayUtils.isEmpty(cells)) {
						continue;
					}
					
					// 如果遇到空行则忽略，处理下一行
					if (isBlankRow(cells)) {
						continue;
					}
					
					// 遍历所有的单元格,将得到的内容保存到一个List中
					StringBuilder context = new StringBuilder(64);
					for (int k = 0; k < cells.length; k++) {
						cellList.add(StringUtils.trim(cells[k].getContents()));
						context.append(StringUtils.trim(cells[k].getContents()));
						context.append(";");
					}
					
					if (cellNum > cells.length) {
						String msg = "表头单元格的个数[" + cellNum + "], 第[" + (j + 1)+ "]行的单元格的个数[" + cells.length + "]";
						logger.error(msg);
						logger.error("第[" + (j + 1)+ "]行的内容为：[" + context.toString() +"]");
						throw new Exception("第[" + (j + 1)+ "]行的单元格的个数不能小于表头的单元格的个数。<br>" + msg);
					}
					
					sheetList.add(cellList.toArray());
				}
				
				result.add(sheetList);
			}
		} catch (Exception e) {
			logger.error("发生其他异常", e);
			throw new ExcelImportException("读取Excel文件发生异常，原因：" + e.getMessage(), e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return result;
	}
	
	/**
	 * 从输入流中读取Excel文件
	 * @param input
	 * @return
	 * @throws ExcelImportException
	 */
	public static List<List<Object[]>> readFile(InputStream input, String[] titiles) throws ExcelImportException {
		List<List<Object[]>> result = null;
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(input);
			
			result = readFile(workbook, titiles);
		} catch (BiffException e) {
			logger.error("读取Excel文件时发生BiffException异常，错误信息：" + e);
			throw new ExcelImportException("读取Excel文件时发生BiffException异常", e);
		} catch (IOException ex) {
			logger.error("读取Excel文件时发生IOException异常，错误信息：" + ex);
			throw new ExcelImportException("读取Excel文件时发生IOException异常", ex);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			IOUtils.closeQuietly(input);
		}
		
		return result;
	}
	
	public static List<List<Object[]>> readFile(File file, String[] titiles) throws ExcelImportException {
		List<List<Object[]>> result = null;
		Workbook workbook = null;
		
		try {
			workbook = Workbook.getWorkbook(file);
			
			result = readFile(workbook, titiles);
		} catch (BiffException e) {
			logger.error("读取Excel文件时发生BiffException异常，错误信息：" + e);
			throw new ExcelImportException("读取Excel文件时发生BiffException异常", e);
		} catch (IOException ex) {
			logger.error("读取Excel文件时发生IOException异常，错误信息：" + ex);
			throw new ExcelImportException("读取Excel文件时发生IOException异常", ex);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		
		return result;
	}
	
	/**
	 * 判断当前行所有的元素是否都为空
	 * @param cells
	 * @return
	 */
	private static boolean isBlankRow(Cell[] cells) {
		boolean isBlankRow = true;
		for (int i = 0; i < cells.length; i++) {
			if (StringUtils.isNotBlank(cells[i].getContents())) {
				isBlankRow = false;
				
				break;
			}
		}
		return isBlankRow;
	}
	
	public static void main(String[] args) throws Exception {
		File file = FileHelper.getFile("D:", "ceshi.xls");
		
		List<List<Object[]>> list = ExcelFileImport.readFile(file, ArrayUtils.EMPTY_STRING_ARRAY);
		logger.debug(list);
	}
}
