package gnete.card.clear2Pay.config;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import flink.util.CommonHelper;


/**
 * <p>网银通配置文件保存接口实现类</p>
 * <p>对应每个区域部分所对应的节点及行记录信息</p>
 * @Project: Card
 * @File: Clear2PayBankConfigLineBufferImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-16
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class Clear2PayBankConfigLineBufferImpl implements IClear2PayBankConfigLineBuffer {
	/** 银行id编号*/
	private String bankNo;

	/** 区域节点信息编号*/
	private String tagNode;

	/** 节点对应配置关联的键值对缓存*/
	private final Map<String, String[]> lineBufferMap = new LinkedHashMap<String, String[]>();
	
	/** 节点对应配置信息行缓存*/
	private final List<String> lineBufferList = new LinkedList<String>();
	
	public String getBankNo() {
		return this.bankNo;
	}

	public Map<String, String[]> getContentBuffer() {
		return this.lineBufferMap;
	}
	
	public List<String> getContentList() {
		return this.lineBufferList;
	}

	public String getTagNode() {
		return this.tagNode;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;

	}

	public void setContentBuffer(String lineInfo) {
		String[] lineArray = lineInfo.split(LINE_SPLIT);

		if (!checkLineArray(lineArray)) {
			return;
		}
		
		String propLine = lineArray[0];
		String valueLine = lineArray[1];
		
		//1.1 得到BANK_NO对应的值
		if(checkLineBankNo(propLine)) {
			setBankNo(valueLine);
			return ;
		}
		
		//1.2 保存原始的行记录
		this.lineBufferList.add(lineInfo);

		//1.3 保存标志行需处理的值(目的用于生成解析器)
		String[] contentArray = valueLine.split(CONTENT_SPLIT);
		
	    if(!checkContentArray(contentArray)) {
			return ;
		}	
		
		lineBufferMap.put(propLine, contentArray);
	}

	public void setTagNode(String tagNode) {
		this.tagNode = tagNode;

	}
	
	public String toString() {
		 return "CONTENTBUFFER=[" + getContentBuffer().toString() + "]"; 
	}
    
	private static boolean checkLineArray(String[] lineArray) {
		return CommonHelper.checkIsNotEmpty(lineArray) && (lineArray.length == LINE_CHECK_LENGTH);
	}
	
	private static boolean checkLineBankNo(String propLine) {
		return propLine.startsWith(BANKNO_TAG);
	}
	
	private static boolean checkContentArray(String[] contentArray) {
		return CommonHelper.checkIsNotEmpty(contentArray) && (contentArray.length >= CONTENT_CHECK_LENGTH);
	}

	

}
