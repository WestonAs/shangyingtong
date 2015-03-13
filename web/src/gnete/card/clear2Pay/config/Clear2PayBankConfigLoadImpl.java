package gnete.card.clear2Pay.config;

import flink.field.MsgFieldPropTypeCreateFactory;
import flink.field.MsgFieldPropTypeCreateFactoryImpl;
import flink.field.MsgPropertyType;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.etc.BizException;
import gnete.etc.RuntimeBizException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p></p>
 * @Project: Card
 * @File: Clear2PayBankConfigLoadImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class Clear2PayBankConfigLoadImpl implements IClear2PayBankConfigLoad {
	private final MsgFieldPropTypeCreateFactory msgFieldPropCreateFactory = new MsgFieldPropTypeCreateFactoryImpl();
	
	private Map<String,String> configSeqMap ;
	
	public void setClear2PayBankSeqMap(Map<String,String> configSeqMap) {
		 this.configSeqMap = configSeqMap;
	}

	public Map<String, Map<String,Clear2PayBankConfigTemplate>> getClear2PayBankResolveMap(InputStream configInput)
			throws BizException {
		try {
			List<String> configLines = FileHelper.readFilterLines(configInput, CONFIG_ENCODING);

			if (CommonHelper.checkIsEmpty(configLines)) {
				return Collections.<String,Map<String,Clear2PayBankConfigTemplate>>emptyMap();
			}

			Map<String, IClear2PayBankConfigLineBuffer> configLineBufferMap = getClear2PayBankConfigLineBufferMap(configLines);

		
			return getClear2PayBankResolveMap(configLineBufferMap);
		} catch (IOException ex) {
            throw new BizException("读取网银通配置文件异常,原因[" + ex.getMessage()+"]");
		} finally {
			FileHelper.closeInputStream(configInput);
		}
    }
	
	
	/**
	 * 
	  * @description：
	  * @param configLineBufferMap
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-16 下午03:58:40
	  * @See:
	 */
	private Map<String,Map<String,Clear2PayBankConfigTemplate>> getClear2PayBankResolveMap(Map<String,IClear2PayBankConfigLineBuffer> configLineBufferMap)
	     throws BizException {
	     Map<String,Map<String,Clear2PayBankConfigTemplate>> clear2PayBankResolveMap = 
	    	     new LinkedHashMap<String,Map<String,Clear2PayBankConfigTemplate>>(configLineBufferMap.size());
	     
	    	     
	     for(Iterator<Map.Entry<String, IClear2PayBankConfigLineBuffer>> iterator = configLineBufferMap.entrySet().iterator(); iterator.hasNext();) {
	    	 Map.Entry<String, IClear2PayBankConfigLineBuffer> entry = iterator.next();
	    	 String bankNo = entry.getKey();
	    	 IClear2PayBankConfigLineBuffer payBankConfigLineBuffer = entry.getValue();
	    	 
	    	 Map<String,String[]> contentBuffer = payBankConfigLineBuffer.getContentBuffer();
	    	 
             Map<String,Clear2PayBankConfigTemplate> configTemplateMap = new LinkedHashMap<String,Clear2PayBankConfigTemplate>();
             
             for(Iterator<Map.Entry<String, String[]>> iter = contentBuffer.entrySet().iterator(); iter.hasNext();) {
            	 Map.Entry<String, String[]> _entry = iter.next();
            	 String propLine = _entry.getKey();
            	 String[] contentArray = _entry.getValue();
            	 configTemplateMap.put(propLine, getClear2PayBankConfigTemplate(propLine,contentArray));
             }
            	    	 
	    	 clear2PayBankResolveMap.put(bankNo, configTemplateMap);
	     }	     	   
	     
	     return clear2PayBankResolveMap;
	}
	
   /**
	 *  
	  * <p>模板类型构造
	  *  <li>propLine=contentArray</li>
	  *  <li>payType=划款类型|string|100|O</li>
	  * </p> 
	  * @param propLine
	  * @param contentArray
	  * @return
	  * @throws BizException  
	  * @version: 2011-6-16 下午02:44:43
	  * @See:
	 */
	private Clear2PayBankConfigTemplate getClear2PayBankConfigTemplate(final String propLine,final String[] contentArray) throws BizException{
		try {
			return new Clear2PayBankConfigTemplate() {

				public IClear2PayBankFuncTemplate getMsgPropFuncTemplate() {
					if(contentArray.length > IClear2PayBankConfigLineBuffer.CONTENT_CHECK_LENGTH) {
						String funcDescribe= contentArray[contentArray.length - 1];
						
						final Object[] describeArray = getFuncDescribeArray(funcDescribe);
						
						return new IClear2PayBankFuncTemplate() {

							public String getFuncDescribe() {
								String operate = (String)describeArray[0];
								return operate;
							}
							
							public int getFuncResortIndex() {
								Integer position = (Integer)describeArray[1];
								return position.intValue();
							}

							public String getFuncResortContent() {
								String content = (String)describeArray[2];
								return content;
							}
							
							public String toString() {
								return ToStringBuilder.reflectionToString(this).toString();
							}

							public int getFuncLimitMax() {
								return getMsgPropertyMax();
							}
						};
					}
					
					return null;
				}

				public String getMsgPropertyField() {
					return propLine;
				}

				public int getMsgPropertyMax() {
					String arrange = contentArray[2];
					return CommonHelper.str2Int(arrange, MAX_ARRANG);
				}

				public String getMsgPropertyName() {
					String propName = contentArray[0];
					return propName;
				}

				public MsgPropertyType getMsgPropertyType() {
					String propType = contentArray[1];
					return msgFieldPropCreateFactory.getMsgPropertyType(propType);
				}

				public boolean isMsgPropertyNeeded() {
					String propNeeded = contentArray[3];
					return CommonHelper.toBooleanObject(propNeeded).booleanValue();
				}
				
				public String toString() {
					return ToStringBuilder.reflectionToString(this).toString();
				}
			};
		}catch(Exception ex) {
			throw new BizException("网银通配置文件内部构建处理存在异常,原因[" + ex.getMessage() + "]");
		}
		
	}
	
	/**
	 * 
	  * <li>对可能存在的支持函数进行检查</li>
	  * @param funcDescribe
	  * @return  
	  * @version: 2011-6-16 下午03:45:03
	  * @See:
	 */
	private  Object[] getFuncDescribeArray(String funcDescribe) {
    	if(CommonHelper.checkIsEmpty(funcDescribe)) {
    		throw new RuntimeBizException("网银通配置文件中函数处理部分不能为空!");
    	}
    	
    	if(!(funcDescribe.startsWith(FUNC_TAG_PREFIX) && funcDescribe.endsWith(FUNC_TAG_SUFFIX))) {
    		throw new RuntimeBizException("网银通配置文件函数处理不符合规范,定义部分开始为[" + FUNC_TAG_PREFIX + "],结尾部分为[" +  FUNC_TAG_SUFFIX + "]!");
    	}
    	
    	String fundDecribeDefine = CommonHelper.getSubString(funcDescribe, FUNC_TAG_PREFIX, FUNC_TAG_SUFFIX);
    	
    	String[] describeArray = CommonHelper.getSupportSplitItemGroup(fundDecribeDefine);
    	
    	if(describeArray.length != FUNC_CHECK_LENGTH) {
    		throw new RuntimeBizException("网银通配置文件函数处理不符合规范,必须分为[函数操作,标记位置,涉及内容]三部分!");
    	}
    	
    	String operate = describeArray[0];
    	
    	if(!CommonHelper.containsElement(CHECK_FUNCS, operate)) {
    	   throw new RuntimeBizException("网银通配置文件函数处理不符合规范,不支持设定的函数操作[" + operate + "]!");	
    	}
    	
    	int position = CommonHelper.str2Int(describeArray[1], 0);
    	String content = describeArray[2];
    	
    	return new Object[] {operate,position,content};
    }
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	  * <li>得到跟标志保持匹配的行记录结构(目的用于得到解析器的保存)</li>
	  * @param configLines
	  * @return  
	  * @version: 2011-6-16 下午12:51:29
	  * @See:
	 */
	private Map<String, IClear2PayBankConfigLineBuffer> getClear2PayBankConfigLineBufferMap(List<String> configLines) throws BizException {
		Map<String, IClear2PayBankConfigLineBuffer> configLineBufferMap = new LinkedHashMap<String, IClear2PayBankConfigLineBuffer>();

		Stack<String> tagNodeStack = new Stack<String>();
		AtomicInteger tagFound = new AtomicInteger();

		for (String line : configLines) {
			if (line.startsWith(CONFIG_COMMENT_BRACKET)) {
				continue;
			}

			// 1.1 如果是标志位置信息
			if (line.startsWith(CONFIG_LEFT_BRACKET) && line.endsWith(CONFI_REIGHT_BRACKET)) {
				String tagNode = CommonHelper.getSubString(line, CONFIG_LEFT_BRACKET, CONFI_REIGHT_BRACKET);

				if (CommonHelper.checkIsNotEmpty(tagNode)) {
					if (tagNodeStack.contains(tagNode)) {
						tagNodeStack.pop();
					}

					tagNodeStack.push(tagNode);
					tagFound.getAndIncrement();
				}
			} else {
				//1.2 否则进行出栈检查
				if (!tagNodeStack.isEmpty()) {
					String tagNode = tagNodeStack.peek();
					//1.3 设置Buffer构造的KEY
					String lineBufferKey = tagNode.concat(tagFound.toString());
					IClear2PayBankConfigLineBuffer payBankConfigLineBuffer = configLineBufferMap.get(lineBufferKey);
					
					if(payBankConfigLineBuffer == null) {
						payBankConfigLineBuffer = new Clear2PayBankConfigLineBufferImpl();
						configLineBufferMap.put(lineBufferKey, payBankConfigLineBuffer);
					}
					//1.4 进行缓存数据保存(方法中进行LINE内容的分解处理)
					payBankConfigLineBuffer.setTagNode(tagNode);
					payBankConfigLineBuffer.setContentBuffer(line);
				}
			}
		}
		
		return filterClear2PayBankLineBufferMap(configLineBufferMap);
	}
	
	/**
	 * 
	  * <li>检查是否存在重复的bank_no定义,并转换成跟bank_no关联的行记录缓存</li>
	  * @param configLineBufferMap  
	  * @version: 2011-6-16 下午01:08:50
	  * @See:
	 */
	private Map<String, IClear2PayBankConfigLineBuffer> filterClear2PayBankLineBufferMap(Map<String, IClear2PayBankConfigLineBuffer> configLineBufferMap) throws BizException {
		List<String> bankNoList = new LinkedList<String>();
		
		Map<String, IClear2PayBankConfigLineBuffer> filterConfigLineBufferMap = new 
		    LinkedHashMap<String,IClear2PayBankConfigLineBuffer>(configLineBufferMap.size());
		
		try {
			for(Iterator<Map.Entry<String, IClear2PayBankConfigLineBuffer>> iterator = configLineBufferMap.entrySet().iterator();iterator.hasNext();) {
				Map.Entry<String, IClear2PayBankConfigLineBuffer> entry = iterator.next();
				
				IClear2PayBankConfigLineBuffer payBankConfigLineBuffer = entry.getValue();
                
				String bankNo = payBankConfigLineBuffer.getBankNo();
			    
			    checkBankNo(bankNo,bankNoList);
			    
			    checkBankNoBuffer(payBankConfigLineBuffer,bankNo,filterConfigLineBufferMap);
			}
		}finally {
			bankNoList = null;
		}
		
		return filterConfigLineBufferMap;
	}
	
	/**
	 * 
	  * <li>对银行id编号进行检查</li>
	  * @param bankNo
	  * @param bankNoList
	  * @throws BizException  
	  * @version: 2011-6-17 上午10:48:53
	  * @See:
	 */
	private void checkBankNo(String bankNo, List<String> bankNoList) throws BizException {
		//1.1 检查网银通的配置BANK_NO
	    if(CommonHelper.checkIsEmpty(bankNo)) {
	    	throw new BizException("网银通配置文件中银行标志信息为空!");
	    }
	    
	    if(CommonHelper.checkIsNotEmpty(configSeqMap)) {
	    	boolean hasBankNo = this.configSeqMap.containsValue(bankNo);
	    	
	    	if(!hasBankNo) {
	    		throw new BizException("网银通配置文件中银行标志在电子联航号中不存在,请仔细检查!");
	    	}
	    }
	    
	    //1.2 检查缓存记录中是否存相同的编号
	    if(bankNoList.contains(bankNo)) {
	    	throw new BizException("网银通配置文件中存在相同银行标志[" + bankNo +"]信息,请检查配置文件!");
	    }
	    
	    bankNoList.add(bankNo);	    
	    
	}
	
	/**
	 * 
	  * <li>对内部记录进行缓存进行检查(必须满足配置信息与行读取信息大小一致)</li>
	  * @param payBankConfigLineBuffer
	  * @param bankNo
	  * @param filterConfigLineBufferMap
	  * @throws BizException  
	  * @version: 2011-6-18 上午09:24:57
	  * @See:
	 */
	private void checkBankNoBuffer(IClear2PayBankConfigLineBuffer payBankConfigLineBuffer,String bankNo,
			Map<String, IClear2PayBankConfigLineBuffer> filterConfigLineBufferMap) throws BizException {
		Map<String,String[]>  contentBuffer = payBankConfigLineBuffer.getContentBuffer();
	    List<String> contentList = payBankConfigLineBuffer.getContentList();
	    
	    if(contentBuffer.size() != contentList.size()) {
	    	throw new BizException("网银通银行标志[" + bankNo +"]存在不合规范的配置信息,请检查配置文件!");
	    }	
	    
	    filterConfigLineBufferMap.put(bankNo, payBankConfigLineBuffer);
	}

}
