package gnete.card.service;

import gnete.card.entity.GdnhglIssuerCardStatic;
import gnete.card.entity.GdysIssuerTermStatic;
import gnete.card.entity.StatisticsCardBjInfo;
import gnete.card.entity.StatisticsCardInfo;
import gnete.etc.BizException;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/***
 * 生成系统统计查询csv文件
 * @author slt02
 *
 */
public interface StaticDataQryFileService {
	
	/**
	 * 导出 ip监控统计Csv文件
	 * @throws IOException 
	 */
	void generateipStaticCsv(HttpServletResponse response, Map<String, Object> params) throws BizException, IOException;
	
	/**
	 * 导出 ip监控统计excel文件
	 * 
	 */
	void generateipStaticExcel(HttpServletResponse response, Map<String, Object> params) throws BizException;
	
	/**
	 * 导出 分支机构月统计Csv文件
	 * @throws IOException 
	 */
	void generatefenZhiConsumeStaticCsv(Map<String,Object> params,HttpServletResponse response) throws BizException, IOException;
	
	/**
	 * 导出 分支机构月统计Excel文件
	 * 
	 */
	void generatefenZhiConsumeStaticExcel(Map<String,Object> params,HttpServletResponse response) throws BizException;
	
	/**
	 * 导出 广东银商终端消费月统计Csv文件
	 * @throws IOException 
	 */
	void generateGdysTermConsumeMStaticCsv(HttpServletResponse response, GdysIssuerTermStatic gdysIssuerTermStatic) throws BizException, IOException;
	
	/**
	 * 导出 广东银商终端消费月统计Excel文件
	 *
	 */
	void generateGdysTermConsumeMStaticExcel(HttpServletResponse response, GdysIssuerTermStatic gdysIssuerTermStatic) throws BizException;
	
	/**
	 * 导出 南湖国旅月统计Csv文件
	 * @throws IOException 
	 */
	void generateNhglStaticCsv(HttpServletResponse response, GdnhglIssuerCardStatic gdnhglIssuerCardStatic) throws BizException, IOException;
	
	/**
	 * 导出 南湖国旅月统计Csv文件
	 *  
	 */
	void generateNhglStaticExcel(HttpServletResponse response, GdnhglIssuerCardStatic gdnhglIssuerCardStatic) throws BizException;
	
	/**
	 * 导出 吉之岛统计excel文件
	 * @throws IOException 
	 */
	void generateJzdStaticExcel(HttpServletResponse response, StatisticsCardInfo statisticsCardInfo) throws BizException;
	
	/**
	 * 导出 百佳统计excel文件
	 * @throws IOException 
	 */
	void generateBjStaticExcel(HttpServletResponse response, StatisticsCardBjInfo statisticsCardBjInfo) throws BizException;
}
