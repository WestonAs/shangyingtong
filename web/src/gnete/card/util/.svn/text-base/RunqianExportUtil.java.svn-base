package gnete.card.util;

import flink.exception.ExcelExportException;
import flink.exception.PdfExportException;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.runqian.report4.ide.usermodel.ReportExporter;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.runqian.report4.view.excel.ExcelReport;

public class RunqianExportUtil {
	/**
	 * Log4J Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RunqianExportUtil.class);
	
	/**
	 * 将多个报表模板生成到一个Excel2003文件中
	 * 
	 * @param contexts
	 *            报表Context，包括要传入的参数
	 * @param raqPaths
	 *            报表模板保存路径
	 * @param excelTabNames
	 *            excel中每个sheet名称
	 * @param reportFolder
	 *            生成的Excel文件的保存目录
	 * @param fileName
	 *            保存的Excel文件名（包含路径）
	 */
	public static boolean exportExcel2003(List<Context> contexts, List<String> raqPaths,
			List<String> excelTabNames, String reportFolder,
			String excelFileName) throws ExcelExportException {
		boolean flag = false;
		if (CollectionUtils.isEmpty(contexts)
				|| CollectionUtils.isEmpty(raqPaths)
				|| CollectionUtils.isEmpty(excelTabNames)) {
			throw new ExcelExportException("传入的List对象为空！");
		}
		if (contexts.size() != raqPaths.size()
				|| contexts.size() != excelTabNames.size()) {
			throw new ExcelExportException("传入List参数的大小不一致！");
		}

		File file = new File(reportFolder);
		if (!(file.exists() && (file.isDirectory()))) {
			file.mkdirs();
		}

		try {
			ExcelReport er = new ExcelReport();
			er.setLocale(new Locale("zh", "CN"));
			
			for (int i = 0; i < contexts.size(); i++) {
				// 读入报表
				ReportDefine rd = (ReportDefine) ReportUtils.read(raqPaths.get(i));
				Engine engine = new Engine(rd, contexts.get(i));

				// 计算报表
				IReport report = engine.calc();

				er.export(excelTabNames.get(i), report);
			}
			er.saveTo(excelFileName);

			flag = true;
		} catch (Exception e) {
			String msg = "生成报表出错,原因:" + e.getMessage();
			logger.error(msg, e);
			throw new ExcelExportException(msg);
		}
		return flag;
	}
	
	/**
	 * 将多个报表模板生成到一个Excel2007文件中
	 * 
	 * @param contexts
	 *            报表Context，包括要传入的参数
	 * @param raqPaths
	 *            报表模板保存路径
	 * @param excelTabNames
	 *            excel中每个sheet名称
	 * @param reportFolder
	 *            生成的Excel文件的保存目录
	 * @param fileName
	 *            保存的Excel文件名（包含路径）
	 */
	public static boolean exportExcel2007(List<Context> contexts, List<String> raqPaths,
			List<String> excelTabNames, String reportFolder,
			String excelFileName) throws ExcelExportException {
		boolean flag = false;
		if (CollectionUtils.isEmpty(contexts)
				|| CollectionUtils.isEmpty(raqPaths)
				|| CollectionUtils.isEmpty(excelTabNames)) {
			throw new ExcelExportException("传入的List对象为空！");
		}
		if (contexts.size() != raqPaths.size()
				|| contexts.size() != excelTabNames.size()) {
			throw new ExcelExportException("传入List参数的大小不一致！");
		}


		try {
			File file = new File(reportFolder);
			if (!(file.exists() && (file.isDirectory()))) {
				file.mkdirs();
			}
			
			ReportExporter re = new ReportExporter(excelFileName, ReportExporter.EXPORT_EXCEL2007);
			
			for (int i = 0; i < contexts.size(); i++) {
				// 读入报表
				ReportDefine rd = (ReportDefine) ReportUtils.read(raqPaths.get(i));
				Engine engine = new Engine(rd, contexts.get(i));

				// 计算报表
				IReport report = engine.calc();

				// 添加sheet页
				re.addSheet(report, excelTabNames.get(i));
			}
			re.save();
			
			flag = true;
		} catch (Throwable e) {
			String msg = "生成报表出错,原因:" + e.getMessage();
			logger.error(msg, e);
			throw new ExcelExportException(msg);
		}
		return flag;
	}
	
	/**
	 * 将多个报表模板生成到一个Excel2007文件中
	 * 
	 * @param contexts
	 *            报表Context，包括要传入的参数
	 * @param raqPaths
	 *            报表模板保存路径
	 * @param excelTabNames
	 *            excel中每个sheet名称
	 * @param reportFolder
	 *            生成的Excel文件的保存目录
	 * @param fileName
	 *            保存的Excel文件名（包含路径）
	 */
	public static boolean exportExcel2007(List<Context> contexts, List<String> raqPaths,
			List<String> excelTabNames, String reportFolder,
			OutputStream excelFileOutput) throws ExcelExportException {
		//boolean flag = false;
		if (CollectionUtils.isEmpty(contexts)
				|| CollectionUtils.isEmpty(raqPaths)
				|| CollectionUtils.isEmpty(excelTabNames)) {
			throw new ExcelExportException("传入的List对象为空！");
		}
		if (contexts.size() != raqPaths.size()
				|| contexts.size() != excelTabNames.size()) {
			throw new ExcelExportException("传入List参数的大小不一致！");
		}
		
		try {
			ReportExporter re = new ReportExporter(excelFileOutput, ReportExporter.EXPORT_EXCEL2007);
			
			for (int i = 0; i < contexts.size(); i++) {
				// 读入报表
				ReportDefine rd = (ReportDefine) ReportUtils.read(raqPaths.get(i));
				Engine engine = new Engine(rd, contexts.get(i));

				// 计算报表
				IReport report = engine.calc();

				// 添加sheet页
				re.addSheet(report, excelTabNames.get(i));
			}
			re.save();
			
			//flag = true;
		} catch (Throwable e) {
			String msg = "生成报表出错,原因:" + e.getMessage();
			logger.error(msg, e);
			throw new ExcelExportException(msg);
		}
		return true;
	}
	
	/**
	 * 生成PDF格式的是文件
	 * 
	 * @param context
	 *            报表Context，包括要传入的参数
	 * @param raqPath
	 *            报表模板保存路径
	 * @param reportFolder
	 *            生成的导出文件的保存目录
	 * @param exportFileName
	 *            导出文件的文件名（包含路径）
	 */
	public static boolean exportPdf(Context context, String raqPath,
			String reportFolder, String exportFileName) throws PdfExportException {
		boolean flag = false;
		
		File file = new File(reportFolder);
		if (!(file.exists() && (file.isDirectory()))) {
			file.mkdirs();
		}
		
		try {
			// 读入报表
			ReportDefine reportDefine = (ReportDefine) ReportUtils.read(raqPath);
			Engine engine = new Engine(reportDefine, context);
			// 计算报表
			IReport report = engine.calc();

			/**
			 * 此方法的四个参数分别是：
			 *  1.pdf文件的路径；
			 *  2.本报表的对象；
			 *  3.PDF文件的分页方式（true为分页，false为不分页）；
			 *  4.导出的PDF为图片格式还是文本格式（true为图片，false为文本）
			 */
			ReportUtils.exportToPDF(exportFileName, report, false, false);
//			ReportUtils.exportToPDF(exportFileName, report, true, false);
		} catch (Throwable e) {
			logger.debug("生成报表出错,报表路径" + raqPath, e);
			throw new PdfExportException("生成报表出错,原因:" + e.getMessage());
		}
		flag = true;
		
		return flag;
	}
	
	/**
	 * 生成PDF格式的是文件
	 * 
	 * @param context
	 *            报表Context，包括要传入的参数
	 * @param raqPath
	 *            报表模板保存路径
	 * @param reportFolder
	 *            生成的导出文件的保存目录
	 * @param exportFileName
	 *            导出文件的文件名（包含路径）
	 */
	public static boolean exportPdf(Context context, String raqPath,
			String reportFolder, OutputStream outputStream) throws PdfExportException {
		boolean flag = false;
		
		try {
			// 读入报表
			ReportDefine reportDefine = (ReportDefine) ReportUtils.read(raqPath);
			Engine engine = new Engine(reportDefine, context);
			// 计算报表
			IReport report = engine.calc();

			ReportUtils.exportToPDF(outputStream, report, false, false);
		} catch (Throwable e) {
			logger.debug("生成报表出错,报表路径" + raqPath, e);
			throw new PdfExportException("生成报表出错,原因:" + e.getMessage());
		}
		flag = true;
		
		return flag;
	}
	
	public static void main(String[] args) {
	}
}
