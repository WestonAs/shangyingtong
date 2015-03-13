package gnete.card.web.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.runqian.report4.view.excel.ExcelReport;

/**
 * @File: GenerateReportFiles.java
 *
 * @description: 生成报表文件
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-12
 */
@Deprecated
public class GenerateReportFiles {

	static Logger logger = Logger.getLogger(GenerateReportFiles.class);
	
	public static void saveToExcel(List<String> templateList) throws Exception {
		
		
		Context ctx = new Context();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", "20100101");
		params.put("endDate", "20101231");
		ctx.setParamMap(params);
		String path = "E:/code/card/web/WebRoot/reportTemplate/daySetTotal.raq";
		
		//读入报表
		ReportDefine rd = (ReportDefine) ReportUtils.read(path);
		Engine engine = new Engine(rd, ctx);
		//计算报表
		IReport report = engine.calc();
		
		Context ctx1 = new Context();
		String path1 = "E:/code/card/web/WebRoot/reportTemplate/daySetTotal.raq";
		ReportDefine rd1 = (ReportDefine) ReportUtils.read(path1);
		Engine engine1 = new Engine(rd1, ctx1);
		IReport report1 = engine1.calc();
		ExcelReport er = new ExcelReport();
		//导出的excel文件命名为moreSheetInExcel.xls，包括两个sheet，一个为one，另一个为two，保存在D盘下
		er.export("one", report);
		er.export("two", report1);
		er.saveTo("D:/moreSheetInExcel.xls"); //输出到指定文件
	}
	
	public static void main(String[] args) {
		try {
			saveToExcel(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
