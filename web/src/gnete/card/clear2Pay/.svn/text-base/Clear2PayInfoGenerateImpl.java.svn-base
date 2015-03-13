package gnete.card.clear2Pay;

import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.clear2Pay.config.Clear2PayBankConfigLoadImpl;
import gnete.card.clear2Pay.config.Clear2PayBankConfigTemplate;
import gnete.card.clear2Pay.config.IClear2PayBankConfigLoad;
import gnete.card.clear2Pay.config.IClear2PayBankFuncTemplate;
import gnete.card.dao.BankInfoDAO;
import gnete.etc.BizException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>网银通划付文件处理类</p>
 * @Project: Card
 * @File: Clear2PayInfoGenerateImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("clear2PayInfoGenerate")
public class Clear2PayInfoGenerateImpl extends AbstractClear2PayInfoGenerateImpl implements InitializingBean {
	/** 配置模板所在路径 */
	private final String CLEAR2PAY_CONFIG_PATH = "/gnete/card/clear2Pay/config/clear2PayBank.config";

	/** 模板描述缓存 */ 
	private final Map<String, String[]> fileTagDescribeMap = new HashMap<String, String[]>();

	/** 函数解析定义数据结构*/
	private final Map<String, IClear2PayBankFuncProcess> funcProcessMap = new HashMap<String, IClear2PayBankFuncProcess>();

	/**
	 * 电子联航号
	 */
	@Autowired
	private BankInfoDAO bankInfoDAO;

	/**模板初始化定义数据结构 */
	private Map<String, Map<String, Clear2PayBankConfigTemplate>> configTemplateMap = null;
	
	/**适配字段返回值的特殊属性(由返回结果集定义) */
	private final String SEQNO_PROPERTY = "SEQNO";

	/**
	 * <p>
	 *   1. 检查当前处理的环境参数 
	 *   2. 检查传入的参数是否合法(满足子类的处理要求)
	 * <p>  
	 */
	@Override
	protected boolean checkClear2PayBankParams(String rmaDate, Map clear2FileInfoMap) throws BizException {
		if (this.configTemplateMap == null) {
			throw new BizException("网银通配置解析模板加载为空,请检查!");
		}
		
		if (!CommonHelper.checkDateStr(rmaDate, FILE_DATEPATTERN)) {
			logger.error("划付日期参数[" + rmaDate + "]不合乎规范[" + FILE_DATEPATTERN + "]");
			throw new BizException("划付日期参数[" + rmaDate + "]不合乎规范[" + FILE_DATEPATTERN + "]");
		}

		if (CommonHelper.checkIsEmpty(clear2FileInfoMap)) {
			logger.error("划付结果参数为空!");
			return false;
		}
		
		for(Iterator iterator = clear2FileInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Object key = entry.getKey();
			if (key == null || !(key instanceof String)) {
				logger.error("划付结果参数不合法规范,KEY类型必须是字符!");
				throw new BizException("划付结果参数不合法规范,KEY类型必须是字符!");
			}
			Object value = entry.getValue();
			if (value == null || !(value instanceof Map)) {
				logger.error("划付结果参数不合法规范,VALUE类型必须是Map!");
				throw new BizException("划付结果参数不合法规范,VALUE类型必须是Map!");
			}
		}
		
		return true;
	}
	
	/**
	 * <p>生成处理结果返回结果结构</p>
	 * <p>
	 *    1. 相关文件生成的临时目录
	 *    2. 每个机构(机构号)对应的划账文件   Map<String,Map<String, File>>
	 *    3. 每个机构(机构号)关联的电子联行号 Map<String,Map<String,Collection<String>>>
	 * </p>   
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object[] generateClear2PayBankBranchFileResult(String rmaDate, Map clear2FileInfoMap) throws BizException {

		Map filterClear2BankInfoMap = getFileterClear2BranchFileInfoMap(clear2FileInfoMap);

		if (CommonHelper.checkIsEmpty(filterClear2BankInfoMap)) {
			return new Object[] {};
		}
		
		Map<String, File> clear2PayBankFileMap = new HashMap<String, File>(clear2FileInfoMap.size());

		Map<String,Collection<String>> clear2PayBankKeyMap = new HashMap<String,Collection<String>>();
		
		File tempDirFile = getFileLocalDir();
		
		try {
			for (Iterator iterator_s = filterClear2BankInfoMap.entrySet().iterator(); iterator_s.hasNext();) {
				Map.Entry entry_s = (Map.Entry) iterator_s.next();
				String branchCode = entry_s.getKey().toString();
				Map clear2FileBranchMapList = (Map) entry_s.getValue();
				for (Iterator iterator = clear2FileBranchMapList.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry entry = (Map.Entry) iterator.next();
					// 1.1 提取银行id以及关联的划账文件数据
					String clear2FileBankNo = entry.getKey().toString();

					List<Map> clear2FileBankNoMapList = (List<Map>) entry.getValue();

					// 1.2 根据日期以及银行id创建要生成的划账文件
					String fileName = getClear2PayBranchFileName(branchCode, clear2FileBankNo, rmaDate);


					// 1.3 根据银行id以及划账文件数据获得待写入文件的内容(包括统计部分以及内容行部分)
					Object[] fileResults = getClear2PayFileResult(clear2FileBankNo, clear2FileBankNoMapList);
					
					if (CommonHelper.checkIsEmpty(fileResults)) {
						logger.info("联行号(前3位):{}没有在配置模板中配置文件格式,不进行文件生成.", clear2FileBankNo);
						continue;
					}
					
					File localFile = FileHelper.getFile(tempDirFile, fileName);

					Collection<String> fileContents = (Collection<String>) fileResults[0];

					String tmpKeyName = new StringBuilder().append(branchCode).append(FILE_SEPARATOR).append(clear2FileBankNo).toString();
					// 1.4 将待写入的文件内容写入到划账文件中
					if (FileHelper.persistFile(localFile, getFileEncoding(), fileContents)) {
						clear2PayBankFileMap.put(tmpKeyName, FileHelper.getFile(tempDirFile, fileName));
					}

					Collection<String> fileKeys = (Collection<String>) fileResults[1];

					// 1.5 保存银行id对应的流水号(作为主键用于后续更新查询)
					if (CommonHelper.checkIsNotEmpty(fileKeys)) {
						clear2PayBankKeyMap.put(tmpKeyName, fileKeys);
					}
				}
			}
			
        } catch (IOException ex) {
			logger.error("构造网银通划付(机构划付给商户)文件失败", ex);
			throw new BizException("构造网银通划付(机构划付给商户)文件失败,日期[" + rmaDate + "],原因[" + ex.getMessage() + "]");
		}
		
		logger.info("========网银通划付(机构划付给商户)文件结果[{}]==============", clear2PayBankFileMap.toString());
		
		logger.info("========网银通划付(机构划付给商户)文件银行关联电子联行号结果[{}]==============", clear2PayBankKeyMap.toString());

		return new Object[] {tempDirFile,clear2PayBankFileMap,clear2PayBankKeyMap};
	}

	/**
	 * 
	  * <p>在上面内容的基础上进行合并处理</p>
	  * @param rmaDate
	  * @param clear2FileInfoMap
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午04:48:17
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object[] generateClear2PayBankFileTotalResult(String rmaDate, 
			Map clear2FileInfoMap, String filePrex) throws BizException  {
		Map filterClear2BankInfoMap = getFileterClear2FileInfoMap(clear2FileInfoMap);

		if (CommonHelper.checkIsEmpty(filterClear2BankInfoMap)) {
			return new Object[] {};
		}
		
		Collection<PayBankFileResult> fileResultList = new LinkedList<PayBankFileResult>();
		
		for(Iterator iterator = filterClear2BankInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry)iterator.next();
			//1.1 提取银行id以及关联的划账文件数据
			String clear2FileBankNo = entry.getKey().toString();
			List<Map> clear2FileBankNoMapList = (List<Map>)entry.getValue();
				
			//1.2 转换为结果返回对象
			PayBankFileResult fileResult = getClear2PayFileTotalResult(clear2FileBankNo,clear2FileBankNoMapList);
				
			if(fileResult.checkInValid()) {
				continue;
			}
				
			fileResultList.add(fileResult);
		}		
		
		return generateClear2PayBankFileTotalResult(rmaDate,fileResultList, filePrex);
	}
	
	/**
	 * 
	  * <p>返回合并处理结果(生成的临时目录文件夹，合并后的文件以及合并关联的划账流水记录)</p>
	  * @param fileResultList
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午07:25:57
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private Object[] generateClear2PayBankFileTotalResult(String rmaDate,
			Collection<PayBankFileResult> fileResultList, String filePrex) throws BizException {
		if(CommonHelper.checkIsEmpty(fileResultList)) {
			return new Object[] {};
		}
				
		File tempDirFile = getFileLocalDir();
		
//		String fileName = getClear2PayFileName(rmaDate);
		String fileName = getClear2PayFileNameRev(rmaDate, filePrex);
		
		try {
			File localFile = FileHelper.getFile(tempDirFile, fileName);
			Object[] fileResults = getFileContentResult(fileResultList);
			
			Collection<String> fileContents = (Collection<String>)fileResults[0];
			
			if (FileHelper.persistFile(localFile, getFileEncoding(), fileContents)) {
				Collection<String> fileKeys = (Collection<String>)fileResults[1];
				
				return new Object[] {tempDirFile, FileHelper.getFile(tempDirFile, fileName), fileKeys};
			}			
			
		}catch(IOException ex) {
			logger.error("构造网银通文件失败", ex);
			throw new BizException("构造网银通文件失败,日期[" + rmaDate + "],原因[" + ex.getMessage() + "]");
		}

		return new Object[] {};
	}
	
	/**
	 * 
	  * <p>得到划账文件内容以及所有KEY值(流水号)</p>
	  * @param fileResultList
	  * @return  
	  * @version: 2011-7-6 下午08:30:38
	  * @See:
	 */
	private Object[] getFileContentResult(Collection<PayBankFileResult> fileResultList)  {
		//1.1 定义当天文件的结果(总笔数、总金额、设计)
		AtomicInteger statisCount = new AtomicInteger(0);
		BigDecimal statisDecimal = BigDecimal.ZERO;
			
		Map<String,String> totalPropInfoMap = new TreeMap<String,String>();
		
		String statisDescribe = null;
		
		String propDescribe = null;
		
		for(PayBankFileResult fileResult : fileResultList) {
			Object[] statisResult = fileResult.getStatisResult();	
			Integer count = (Integer)statisResult[0];
			BigDecimal statis = (BigDecimal)statisResult[1];
			
			if(statisDescribe == null) {
				statisDescribe = fileResult.getStatisDescribe();
			}
			
			if(propDescribe == null) {
				propDescribe = fileResult.getPropertyDescribe();
			}
			
			//1.2 做累加处理
			statisCount.getAndAdd(count.intValue());			
			statisDecimal = statisDecimal.add(statis);
			
			//1.3 内部进行排序处理并保存到大的结构中
			Map<String,String> propInfoMap = fileResult.getSortedPropInfoMap();
			
			if(CommonHelper.checkIsNotEmpty(propInfoMap)) {
				totalPropInfoMap.putAll(propInfoMap);
			}
		}
		
		//1.4 得到统计结果行值
		String statisResult = new StringBuilder().append(statisCount.get()).append(FILE_CONNECT)
		                                         .append(CommonHelper.getCommonFormatAmt(statisDecimal)).toString();
		
		Collection<String> fileContents = new LinkedList<String>();
		fileContents.add(statisDescribe);
		fileContents.add(statisResult);
		fileContents.add(propDescribe);
		fileContents.addAll(totalPropInfoMap.values());
		
		return new Object[] {fileContents,new LinkedList<String>(totalPropInfoMap.keySet())};
	}
	
	/**
	  * <p>
	  *   1. 将结果集中的数据按照机构号进行分解(MultiValueMap):先按机构分组,再按联行号分组
	  *   2. 接受行号必须是网银通电子联行号中支持的(不支持的将会过滤掉)
	  *   3. 机构号对应着其下多个RECACCOUNT的记录
	  * </p>
	 * @Date 2013-3-8下午2:38:03
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	private Map getFileterClear2BranchFileInfoMap(Map clear2FileInfoMap) throws BizException {
		Map infoMap = new HashMap();
		Map filterInfoMap = CommonHelper.createDefaultMultiMap();

		//先按机构分组
		for(Iterator iterator = clear2FileInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry)iterator.next();
			
			//1.1 获得流水号关联的MAP集合(与查询返回对应)
			Map clear2PayBankFileRowMap = (Map)entry.getValue();
            
			//1.2 提取电子联行号(从单个MAP集和中提取)
			String clear2FileBankNo = getClear2PayBankNo(clear2PayBankFileRowMap);
			
			//1.3 为空或者不在电子联航号缓存中的继续读取
			if (CommonHelper.checkIsEmpty(clear2PayBankFileRowMap) || CommonHelper.checkIsEmpty(clear2FileBankNo)) {
				continue;
			}
			
			if(!checkClear2FileBankNo(clear2FileBankNo)) {
				continue;
			}
			
			//1.4 提取机构号
			String clear2FileBranchCode = getClear2PayBranchCode(clear2PayBankFileRowMap);
			
			//1.5 否则保存到缓存中(表示每个机构所拥有的划账数据)
			filterInfoMap.put(clear2FileBranchCode, clear2PayBankFileRowMap);			
		}
		
		//再按联行号分组
		for(Iterator iterator = filterInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry)iterator.next();
			String branchCode = (String)entry.getKey();
			List<Map> clear2PayBankBranchFileRowMap = (List<Map>)entry.getValue();
			Map tmpMap = new HashMap();
			String tmpKey = null;
			for(Map map : clear2PayBankBranchFileRowMap){
				tmpKey=getClear2PayBankKey(map);
				tmpMap.put(tmpKey, map);
			}
			infoMap.put(branchCode,getFileterClear2FileInfoMap(tmpMap));
		}
		return infoMap;
	}
	
	/**
	 * 
	  * <p>
	  *   1. 将结果集中的数据按照接受行号进行分解(MultiValueMap)
	  *   2. 接受行号必须是网银通电子联行号中支持的(不支持的将会过滤掉)
	  *   3. 银行编号对应着其下多个关联的行号(读取前面三位)
	  * </p>
	  * @param clear2FileInfoMap
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-18 上午09:06:01
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private Map getFileterClear2FileInfoMap(Map clear2FileInfoMap) throws BizException {
		Map filterInfoMap = CommonHelper.createDefaultMultiMap();

		for(Iterator iterator = clear2FileInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry)iterator.next();
			
			//1.1 获得流水号关联的MAP集合(与查询返回对应)
			Map clear2PayBankFileRowMap = (Map)entry.getValue();
            
			//1.2 提取电子联行号(从单个MAP集和中提取)
			String clear2FileBankNo = getClear2PayBankNo(clear2PayBankFileRowMap);
			
			//1.3 为空或者不在电子联航号缓存中的继续读取
			if (CommonHelper.checkIsEmpty(clear2PayBankFileRowMap) || CommonHelper.checkIsEmpty(clear2FileBankNo)) {
				continue;
			}
			
			if(!checkClear2FileBankNo(clear2FileBankNo)) {
				continue;
			}
			
			//1.4 否则保存到缓存中(表示每家银行id所拥有的划账数据)
			filterInfoMap.put(getFilterRecBankNo(clear2FileBankNo), clear2PayBankFileRowMap);			
		}
		
		return filterInfoMap;
	}
	
	/**
	 *  <p>判断电子联航号是否在数据库中存在记录</p>
	  * @param clear2FileBankNo
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-12 下午11:07:12
	  * @See:
	 */
    private boolean checkClear2FileBankNo(String clear2FileBankNo) throws BizException{
    	try {
    		return (this.bankInfoDAO.findByPk(clear2FileBankNo) != null);
    	}catch(DataAccessException ex) {
    		throw new BizException("根据[" + clear2FileBankNo + "]查询电子联航号信息失败,原因[" + ex.getMessage() + "]");
    	}
    	
    }
	
	/**
	 * 
	  * <p>文件生成主要产生方法</p>
	  * <p>
	  *   1.产生统计标题部分 
	  *   2.产生统计部分数据
	  *   3.产生行标题部分
	  *   4.产生行数据部分
	  * </p>   
	  * @param recBankNo
	  * @param clear2FileBankNoMapList
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-28 下午04:00:12
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private Object[] getClear2PayFileResult(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException{
	    String[] fileTagDescribe = getFileTagDescribeInfo(recBankNo);
		
		if(CommonHelper.checkIsEmpty(fileTagDescribe)) {
			return new Object[] {};
		}
		
		Collection<String> payFileLines = new LinkedList<String>();
		
		Object[] filePropInfoResult = getFilePropInfoResult(recBankNo,clear2FileBankNoMapList);
		
		payFileLines.add(fileTagDescribe[0]);
		payFileLines.add(getFileStatisInfo(recBankNo, clear2FileBankNoMapList));
		payFileLines.add(fileTagDescribe[1]);
		
		Collection<String> propInfoList = (Collection<String>)filePropInfoResult[0];
		Collection<String> keyInfoList = (Collection<String>)filePropInfoResult[1];
		
		payFileLines.addAll(propInfoList);
		
		return new Object[] {payFileLines,keyInfoList};
	}
	
	/**
	 * 
	  * <p>同上将结果转换成对象(便于后续进行统计)</p>
	  * @param recBankNo
	  * @param clear2FileBankNoMapList
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午07:13:40
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private PayBankFileResult getClear2PayFileTotalResult(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException {
		PayBankFileResult payBankFileResult = new PayBankFileResult();
		
		String[] fileTagDescribe = getFileTagDescribeInfo(recBankNo);
		
		if(CommonHelper.checkIsNotEmpty(fileTagDescribe)) {
			payBankFileResult.setPayBankNo(recBankNo);
			payBankFileResult.setStatisDescribe(fileTagDescribe[0]);
			payBankFileResult.setPropertyDescribe(fileTagDescribe[1]);
			
			Object[] statisResult = getFileStatisInfoPair(recBankNo,clear2FileBankNoMapList);
			payBankFileResult.setStatisResult(statisResult);
			
			Map<String,String> filePropInfoResultMap = getFilePropInfoResultMap(recBankNo,clear2FileBankNoMapList) ;
			payBankFileResult.setPropInfoMap(filePropInfoResultMap);
		}
		
		return payBankFileResult;
	}
	
	@Override
	protected String getFileEncoding() {
		return FILE_ENCODING;
	}

	@Override
	protected File getFileLocalDir() {
		return FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * 
	 * @description：<li>从文件中获得属性内容</li>
	 * @param recBankNo
	 * @param clear2FileInfoList
	 * @return
	 * @version: 2011-6-14 下午07:49:07
	 * @See:
	 */	
	private Object[] getFilePropInfoResult(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException {
		Map<String, Clear2PayBankConfigTemplate> templateMap = this.configTemplateMap.get(recBankNo);

		if (CommonHelper.checkIsEmpty(templateMap)) {
			return new Object[] {};
		}

		Collection<String> propInfoList = new LinkedList<String>();
		
		Collection<String> keyInfoList = new LinkedList<String>();

		Long seqNo = 1L;//用于修正序号
		for (Map clear2FileMap : clear2FileBankNoMapList) {
			String[] propInfoResult = getFilePropInfoResult(templateMap,clear2FileMap, seqNo++);
			propInfoList.add(propInfoResult[0]);
			keyInfoList.add(propInfoResult[1]);
		}

		return new Object[] {propInfoList,keyInfoList};
	}
	
	/**
	 * 
	  * <p>同上转换成Map的数据结果</p>
	  * @param recBankNo
	  * @param clear2FileBankNoMapList
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午07:42:19
	  * @See:
	 */
	private Map<String,String> getFilePropInfoResultMap(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException {
		Map<String, Clear2PayBankConfigTemplate> templateMap = this.configTemplateMap.get(recBankNo);
		
		if(CommonHelper.checkIsEmpty(templateMap)) {
			return Collections.<String,String>emptyMap();
		}
		
		Map<String,String> resultMap = new HashMap<String,String>();
		
		for (Map clear2FileMap : clear2FileBankNoMapList) {
			String[] propInfoResult = getFilePropInfoResult(templateMap,clear2FileMap);
			String propKey = propInfoResult[1];
			String propValue = propInfoResult[0];			
			resultMap.put(propKey, propValue);
		}
		
		return resultMap;
	}

	/**
	 * 
	 * <p>根据获取的模板和行记录来进行解析</p>
	 * @param configTemplateList
	 * @param clear2FileMap
	 * @return
	 * @throws BizException
	 * @version: 2011-6-16 下午05:10:56
	 * @See:
	 */	
	private String[] getFilePropInfoResult(Map<String, Clear2PayBankConfigTemplate> templateMap, Map clear2FileMap, Long seqNo) throws BizException {
		StringBuilder propInfoBuffer = new StringBuilder(200);

		String keyPropValue = null;
		
		for(Iterator<Map.Entry<String, Clear2PayBankConfigTemplate>> iterator = templateMap.entrySet().iterator(); iterator.hasNext();) {
			//1.1 遍历模板获得属性及其对应模板
			Map.Entry<String, Clear2PayBankConfigTemplate> entry = iterator.next();
			String propLine = entry.getKey();
			Clear2PayBankConfigTemplate configTemplate = entry.getValue();
			
			//1.2 根据配置属性及其模板获得该数据处理处理结果
			String fileMapKey = propLine.toUpperCase();
			Object value;
			if(fileMapKey.equals(SEQNO_PROPERTY)){
				value = seqNo;
			}else{
				value = clear2FileMap.get(fileMapKey);
			}
			
			String propFieldInfo = (value == null) ? DEFAULT_VALUE : getFilterPropFieldInfo(value, configTemplate);
			
			//1.3 流水号作为缺省的属性（保持唯一即每个生成的行数据跟该流水对应）
		    if(keyPropValue == null) {
				if(fileMapKey.equals(getClear2PayBankKeyProperty())) {
						keyPropValue = propFieldInfo;
				}
			}

			//每个属性提取值之间用|进行分割
		    StringBuilder tempBuffer = new StringBuilder(propFieldInfo).append(FILE_CONNECT);

			propInfoBuffer.append(tempBuffer);
		}
		
		return new String[] {propInfoBuffer.toString(),keyPropValue};
	}		
	
	/**
	 * 
	 * <p>根据获取的模板和行记录来进行解析</p>
	 * @param configTemplateList
	 * @param clear2FileMap
	 * @return
	 * @throws BizException
	 * @version: 2011-6-16 下午05:10:56
	 * @See:
	 */	
	private String[] getFilePropInfoResult(Map<String, Clear2PayBankConfigTemplate> templateMap, Map clear2FileMap) throws BizException {
		StringBuilder propInfoBuffer = new StringBuilder(200);

		String keyPropValue = null;
		
		for(Iterator<Map.Entry<String, Clear2PayBankConfigTemplate>> iterator = templateMap.entrySet().iterator(); iterator.hasNext();) {
			//1.1 遍历模板获得属性及其对应模板
			Map.Entry<String, Clear2PayBankConfigTemplate> entry = iterator.next();
			String propLine = entry.getKey();
			Clear2PayBankConfigTemplate configTemplate = entry.getValue();
			
			//1.2 根据配置属性及其模板获得该数据处理处理结果
			String fileMapKey = propLine.toUpperCase();
			Object value = getAdapterFixValue(fileMapKey,clear2FileMap.get(fileMapKey));
			
			String propFieldInfo = (value == null) ? DEFAULT_VALUE : getFilterPropFieldInfo(value, configTemplate);
			
			//1.3 流水号作为缺省的属性（保持唯一即每个生成的行数据跟该流水对应）
		    if(keyPropValue == null) {
				if(fileMapKey.equals(getClear2PayBankKeyProperty())) {
						keyPropValue = propFieldInfo;
				}
			}

			//每个属性提取值之间用|进行分割
		    StringBuilder tempBuffer = new StringBuilder(propFieldInfo).append(FILE_CONNECT);

			propInfoBuffer.append(tempBuffer);
		}
		
		return new String[] {propInfoBuffer.toString(),keyPropValue};
	}		
	
	/**
	 * 
	  * <p>
	  *   1. ROWNUM返回的JAVA类型竟然是BigDecimal(十分诡异)
	  *   2. 由于跟配置文件不吻合只能做硬编码处理(待后面再解决)
	  * </p>
	  * @param fileMapKey
	  * @param value
	  * @return  
	  * @version: 2011-6-17 下午08:18:27
	  * @See:
	 */
	private Object getAdapterFixValue(String fileMapKey,Object value) {
		return (fileMapKey.equals(SEQNO_PROPERTY) && value instanceof BigDecimal) ? Long.valueOf(((BigDecimal)value).longValue()) : value;
	}

	/**
	 * 
	 * <p> 
	 * <li>根据模板解析器来处理字符</li> 
	 * <li>解析顺序:获取原始字符串--->按照模板定义的字段进行范围获取--->查看是否有fun(有则按照当时支持的函数进行处理)</li>
	 * </p> 
	 * @param value
	 * @param configTemplate
	 * @return
	 * @version: 2011-6-16 下午06:58:34
	 * @See:
	 */
	private String getFilterPropFieldInfo(Object value, Clear2PayBankConfigTemplate configTemplate) throws BizException{
		try {
			String origValue = configTemplate.getMsgPropertyType().toString(value);

			if (CommonHelper.isEmpty(origValue)) {
				return origValue;
			}

			origValue = CommonHelper.getBeginStrByIndex(origValue, configTemplate.getMsgPropertyMax());

			IClear2PayBankFuncTemplate funcTemplate = configTemplate.getMsgPropFuncTemplate();

			if (funcTemplate == null) {
				return origValue;
			}

			return getFilterPropFieldInfo(origValue, funcTemplate);
		}catch(Exception ex) {
			throw new BizException("模板解析出现异常,传入值[" + value + "],模板类型[" + configTemplate.getMsgPropertyField()+ "],原因[" + ex.getMessage() + "]");
		}		
	}

	/**
	 * 
	 * <p>根据配置的函数来进行字符串的函数处理</p>
	 * @param origValue
	 * @param funcProcess
	 * @return
	 * @version: 2011-6-16 下午07:06:14
	 * @See:
	 */
	private String getFilterPropFieldInfo(String origValue, IClear2PayBankFuncTemplate funcTemplate) {
		IClear2PayBankFuncProcess funcProcess = funcProcessMap.get(funcTemplate.getFuncDescribe());

		if (funcProcess == null) {
			return origValue;
		}

		return funcProcess.getClear2BankFuncProcessResult(origValue, funcTemplate);
	}

	/**
	 * 
	 * <p>从文件中获得统计内容</p>
	 * @param recBankNo
	 * @param clear2FileInfoList
	 * @return
	 * @version: 2011-6-14 下午07:48:07
	 * @See:
	 */
	private String getFileStatisInfo(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException {
		BigDecimal statisDecimal = BigDecimal.ZERO;

		try {
			for (Map clear2FileBankNoMap : clear2FileBankNoMapList) {
				BigDecimal amount = (BigDecimal) clear2FileBankNoMap.get(getClear2PayBankAmountProperty());

				statisDecimal = statisDecimal.add(amount);
			}
		} catch (Exception ex) {
			logger.error("网银通总额统计异常,针对银行编号[" + recBankNo + "]", ex);

			throw new BizException("网银通总额统计异常,针对银行编号[" + recBankNo + "],原因[" + ex.getMessage() + "]");
		}
		
		return new StringBuilder().append(clear2FileBankNoMapList.size()).append(FILE_CONNECT)
		                           .append(CommonHelper.getCommonFormatAmt(statisDecimal)).toString();
		
	}
	
	/**
	 * 
	  * <p>从文件中获得统计内容(统计)</p>
	  * @param recBankNo
	  * @param clear2FileBankNoMapList
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-6 下午06:33:07
	  * @See:
	 */
	private Object[] getFileStatisInfoPair(String recBankNo, List<Map> clear2FileBankNoMapList) throws BizException {
		BigDecimal statisDecimal = BigDecimal.ZERO;

		try {
			for (Map clear2FileBankNoMap : clear2FileBankNoMapList) {
				BigDecimal amount = (BigDecimal) clear2FileBankNoMap.get(getClear2PayBankAmountProperty());

				statisDecimal = statisDecimal.add(amount);
			}
		} catch (Exception ex) {
			logger.error("网银通总额统计异常,针对银行编号[" + recBankNo + "]", ex);

			throw new BizException("网银通总额统计异常,针对银行编号[" + recBankNo + "],原因[" + ex.getMessage() + "]");
		}
		
		return new Object[] {clear2FileBankNoMapList.size(),statisDecimal};
	}

	/**
	 *  <p>根据银行id获得标题头信息(从配置文件中提取)</p>
	  * @param recBankNo
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-12 下午11:10:15
	  * @See:
	 */
	private String[] getFileTagDescribeInfo(String recBankNo) throws BizException {
		String[] fileTagDescribeInfo = fileTagDescribeMap.get(recBankNo);

		if (CommonHelper.checkIsEmpty(fileTagDescribeInfo)) {
			Map<String, Clear2PayBankConfigTemplate> templateMap = this.configTemplateMap.get(recBankNo);

			if (CommonHelper.checkIsEmpty(templateMap)) {
				return new String[] {};
			}

			return getFileTagDescribeInfo(templateMap);
		}

		return fileTagDescribeInfo;
	}

	private String[] getFileTagDescribeInfo(Map<String, Clear2PayBankConfigTemplate> templateMap) {
		StringBuilder propBuilder = new StringBuilder(200);

		Collection<Clear2PayBankConfigTemplate> configTemplateList = templateMap.values();

		for (Clear2PayBankConfigTemplate configTemplate : configTemplateList) {
			StringBuilder tempBuilder = new StringBuilder();
			String msgPropName = configTemplate.getMsgPropertyName();
			tempBuilder.append(msgPropName).append(FILE_CONNECT);
			propBuilder.append(tempBuilder);
		}

		return new String[] { FILE_COMMNET.concat(getClear2PayBankStatis()),
				               FILE_COMMNET.concat(CommonHelper.removeEndStr(propBuilder.toString(), FILE_CONNECT)) };
	}

	/**
	 * 
	 * <p>加载针对获取数据的模板</p>
	 * @throws Exception
	 * @version: 2011-6-16 下午04:07:45
	 * @See:
	 */
	public void afterPropertiesSet() throws Exception {

		// 1.2 加载配置文件
		try {
			IClear2PayBankConfigLoad clear2PayConfigLoad = new Clear2PayBankConfigLoadImpl();
			synchronized (obj) {				
				loadConfigTemplateMap(clear2PayConfigLoad);
			}
		} catch (Exception ex) {
			logger.error("网银通划付配置文件处理异常", ex);
		}

	}
	
	private void loadConfigTemplateMap(IClear2PayBankConfigLoad clear2PayConfigLoad) throws Exception {
        InputStream clear2PayConfigStream = FileHelper.getResourceStream(CLEAR2PAY_CONFIG_PATH);

		if (this.configTemplateMap == null) {
			this.configTemplateMap = clear2PayConfigLoad.getClear2PayBankResolveMap(clear2PayConfigStream);

			this.funcProcessMap.put(IClear2PayBankConfigLoad.CHECK_FUNCS[0], new Clear2PayBankAddFuncProcessImpl());
			this.funcProcessMap.put(IClear2PayBankConfigLoad.CHECK_FUNCS[1], new Clear2PayBankRemoveFuncProcessImpl());

			logger.info("=================网银通文件内部模板文件加载处理成功!==================");
		}
	}

	@Override
	protected String getClear2PayBankKey(Map clear2PayBankFileRowMap) {
		return clear2PayBankFileRowMap.get(getClear2PayBankKeyProperty()).toString();
	}
	
	@Override
	protected String getClear2PayBankNo(Map clear2PayBankFileRowMap) {
		return clear2PayBankFileRowMap.get(getClear2PayBankNoProperty()).toString();
	}
	
	@Override
	protected String getClear2PayBranchCode(Map clear2PayBankFileRowMap) {
		return clear2PayBankFileRowMap.get(getClear2PayBranchCodeProperty()).toString();
	}

	/**
	 * <p>1.1 检查开户行是否是电子联航中支持的 1.2 提取开户行的前三位</p>
	 */
	@Override
	protected String getFilterRecBankNo(String recBankNo) throws BizException {
		return CommonHelper.getBeginStrByIndex(recBankNo, BANKNO_INDEX);
	}

	@Override
	protected String getClear2PayBankAmountProperty() {
		return "amount".toUpperCase();
	}
	
	@Override
	protected String getClear2PayBranchCodeProperty() {
		return "payCode".toUpperCase();
	}

	@Override
	protected String getClear2PayBankNoProperty() {
		return "recBankNo".toUpperCase();
	}
	
	@Override
	protected String getClear2PayBankKeyProperty() {
		return "busiOrderNo".toUpperCase();
	}

	@Override
	protected String getClear2PayBankStatis() {
		return "总笔数|总金额";
	}

	// -------------------------------------------内部函数解析处理关联部分------------------------------------------------------------
	protected static interface IClear2PayBankFuncProcess {
		String getClear2BankFuncProcessResult(String origValue, IClear2PayBankFuncTemplate funcTemplate);
	}

	/**内部函数处理抽象类 */
	protected static abstract class AbstractClear2PayBankFuncProcessImpl implements IClear2PayBankFuncProcess {

		public String getClear2BankFuncProcessResult(String origValue, IClear2PayBankFuncTemplate funcTemplate) {
			String resortContent = funcTemplate.getFuncResortContent();

			if (CommonHelper.checkIsEmpty(resortContent)) {
				return origValue;
			}

			int expandLength = (resortContent.length() + origValue.length());

			if (expandLength > funcTemplate.getFuncLimitMax()) {
				return origValue;
			}

			int resortIndex = funcTemplate.getFuncResortIndex();

			String[] pairValue = new String[] { origValue.substring(0, resortIndex), origValue.substring(resortIndex) };

			return getClear2BankFuncProcessResult(pairValue, resortContent);
		}

		protected abstract String getClear2BankFuncProcessResult(String[] pairValue, String resortContent);
	}

	 
	/**处理ADD函数*/
	protected static class Clear2PayBankAddFuncProcessImpl extends AbstractClear2PayBankFuncProcessImpl {

		@Override
		protected String getClear2BankFuncProcessResult(String[] pairValue, String resortContent) {
			return new StringBuilder().append(pairValue[0]).append(resortContent).append(pairValue[1]).toString();
		}

	}

	
	/** 处理REMOVE函数处理*/
	protected static class Clear2PayBankRemoveFuncProcessImpl extends AbstractClear2PayBankFuncProcessImpl {
		@Override
		protected String getClear2BankFuncProcessResult(String[] pairValue, String resortContent) {
			if (CommonHelper.checkIsEmpty(pairValue[0])) {
				return new StringBuilder().append(pairValue[0])
				                           .append(CommonHelper.removeStartStr(pairValue[1], resortContent))
						                   .toString();
			}
			return new StringBuilder().append(CommonHelper.removeStartStr(pairValue[0], resortContent))
			                           .append(pairValue[1]).toString();
		}
	}
	
	/**
	 *  <p>定义每天划账文件产生结果</p>
	  * @Project: Card
	  * @File: PayBankFileResult.java
	  * @See:
	  * @description：
	  * @author: aps-zbw
	  * @modified:
	  * @Email: aps-zbw@cnaps.com.cn
	  * @Date: 2011-7-6
	  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
	  * @version:  V1.0
	 */
	protected static class PayBankFileResult {
		private String payBankNo;        //银行id编号
		
		private String statisDescribe;   //描述统计标题
		 
	    private Object[] statisResult;   //保存统计结果
		
		private String propertyDescribe;  //属性栏描述
			
		private Map<String,String> propInfoMap;  //保存行数据以及对应的KEY
		
		public String getPayBankNo() {
			return payBankNo;
		}

		public void setPayBankNo(String payBankNo) {
			this.payBankNo = payBankNo;
		}

		public String getStatisDescribe() {
			return statisDescribe;
		}

		public void setStatisDescribe(String statisDescribe) {
			this.statisDescribe = statisDescribe;
		}

		public Object[] getStatisResult() {
			return statisResult;
		}

		public void setStatisResult(Object[] statisResult) {
			this.statisResult = statisResult;
		}

		public String getPropertyDescribe() {
			return propertyDescribe;
		}
		
		public void setPropertyDescribe(String propertyDescribe) {
			this.propertyDescribe = propertyDescribe;
		}
		
		/**
		 * 
		  * <p>内部方法便于后续排序</p> 
		  * @version: 2011-7-6 下午07:46:49
		  * @See:
		 */
		public void setPropInfoMap(Map<String,String> propInfoMap) {
			this.propInfoMap = propInfoMap;
		}
		
		public Map<String, String> getPropInfoMap() {
			return propInfoMap;
		}
		
		/**
		 *  <p>通过TreeMap根据KEY来进行排序</p>
		  * @return  
		  * @version: 2011-7-12 下午11:01:26
		  * @See:
		 */
		public Map<String,String> getSortedPropInfoMap() {
			if(this.propInfoMap == null) {
				return Collections.<String,String>emptyMap();
			}
			
			SortedMap<String,String> sortedPropMap = new TreeMap<String,String>(this.propInfoMap);
			
			return sortedPropMap;
		}
		
		public boolean checkInValid() {
			return (CommonHelper.checkIsEmpty(this.payBankNo) || CommonHelper.checkIsEmpty(this.statisDescribe) 
					      || CommonHelper.checkIsEmpty(this.propertyDescribe));
		}
		
	}




}
