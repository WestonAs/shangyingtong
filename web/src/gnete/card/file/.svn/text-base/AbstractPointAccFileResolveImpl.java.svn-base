package gnete.card.file;

import flink.util.CommonHelper;
import flink.util.FileHelper;
import gnete.card.entity.PointAccReg;
import gnete.etc.BizException;

import java.io.InputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import gnete.card.entity.state.PointAccState;

import org.apache.commons.lang.StringUtils;

/**
 * p>抽象潮州移动文件处理实现类(面向不同要求的文件)</p>
 * @Project: Card
 * @File: AbstractPointAccFileResolveImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class AbstractPointAccFileResolveImpl implements IPointAccFileResolve {
	//缺省行数据分隔符
	protected final String DEFAULT_LINE_SPLIT = "|";
	
	//缺省内部描述分隔符
	protected final String DEFAULT_DSCRIPT_SPLIT = ":";	

	//缺省GBK文件编码
	protected final String DEFAULT_FILE_ENCODING = "GBK";

	//支持的首行注释符
	protected final String[] SUPPORT_NOTES_TAG = new String[] { "#", "/", "-" };

	//文件最少数据行
	protected final int MIN_LINE_LENGTH = 3;

	//标题头位置
	protected final int HEAD_LINE_INDEX = 0;

	//检查行位置
	protected final int INTERVAL_LINE_INDEX = 1;
	
	//最大数据行的检查(中间最大行)
	protected final int MAX_CHECK = 10;
	
	protected final String END = "END";

	public PointAccReg getPointAccRegResolve(String fileName, String branchCode, InputStream input) throws BizException {
		checkResolve(fileName, input);

		List<String[]> resolveContentList = null;

		try {
			List<String> lineContentList = FileHelper.readFilterLines(input, getFileEncoding());

			// 1.1 获得文件行大小
			int lineConentSize = lineContentList.size();

			if (lineConentSize < MIN_LINE_LENGTH) {
				throw new BizException("文件不规范至少需要标题行、数据行和结束行!");
			}

			// 1.2 获得待处理的缓存的数据行
			resolveContentList = getResolveContentList(lineConentSize, lineContentList);

		} catch (IOException ex) {
             throw new BizException("文件解析出现异常,文件名[" + fileName + "],原因[" + ex.getMessage() +"]");
		} finally {
			FileHelper.closeInputStream(input);
		}

		return (CommonHelper.checkIsEmpty(resolveContentList)) ? null : getPointAccRegResolve(fileName,branchCode, resolveContentList);
	}

	protected List<String[]> getResolveContentList(int lineConentSize, List<String> lineContentList) throws BizException {
		List<String[]> resolveContentList = new LinkedList<String[]>();

		// 1.2 获得文件启示的标题和数据列
		int[] lineDataIndex = getLineDataNum(lineContentList);

		// 1.3 读取(如果有问题则抛出异常,为空则过滤掉)
		for (int i = lineDataIndex[0]; i < lineConentSize-1; i++) {
			String lineContent = lineContentList.get(i).trim();
			String[] lineArrayMsg = CommonHelper.getMsgLineArrayInSplit(lineContent, DEFAULT_LINE_SPLIT);
			if (CommonHelper.checkIsEmpty(lineArrayMsg)) {
				continue;
			}

			if (lineArrayMsg.length != lineDataIndex[1]) {
				throw new BizException("文件数据行跟行标题设置不匹配!");
			}

			resolveContentList.add(lineArrayMsg);
		}

		return resolveContentList;
	}

	/**
	 * 
	 * <p>从第二行开始查找直到找到有值的数据行,没有找到则抛出异常</p>
	 * @param lineContentList
	 * @param fieldNum
	 * @return
	 * @version: 2011-4-14 下午05:11:05
	 * @See:
	 */
	protected int[] getLineDataNum(List<String> lineContentList) throws BizException {
		int fieldNum = getLineFieldNum(lineContentList);
		
		boolean hasFound = false;
		 //1.1 设置第二行为初始化数据行
		 int checked = 0;		
		 int dataIndex = INTERVAL_LINE_INDEX;
		//1.2 从第二行开始查找(最多查10行,假如中间存在多个空白行的话)
		for(int i = INTERVAL_LINE_INDEX, ln = lineContentList.size(); i < ln ; i ++) {
			 String lineContent = lineContentList.get(i).trim();
			 //1.3 开始计数大于10则推出
			 checked ++ ;
			 if(checked >  MAX_CHECK) {
				  break;
			 }
			 if(CommonHelper.checkIsEmpty(lineContent)) {
				 continue;
			 }			 
			 //1.4 提取分段信息
			 String[] intervalArray = CommonHelper.getMsgLineArrayInSplit(lineContent, DEFAULT_LINE_SPLIT);
			 
			 //1.5 如果没有找到则继续循环
			 if (intervalArray.length != fieldNum) {
				 continue;
			 } else {
				 //找到了则退出并获得数据标题
				 dataIndex = i ;
				 hasFound = true;
				 break;
			 }
		}
		
	    if(!hasFound) {
	    	throw new BizException("没有找到符合规范的匹配数据行,请检查原文件!");
	    }
	    
	    return new int[] {dataIndex,fieldNum};
    }

	/**
	 * 
	 * <p>获得行标题的FIELD个数说明</p>
	 * @param lineContentList
	 * @return
	 * @throws BizException
	 * @version: 2011-4-14 下午04:59:09
	 * @See:
	 */
	protected int getLineFieldNum(List<String> lineContentList) throws BizException {
		String headLine = lineContentList.get(HEAD_LINE_INDEX).trim();

		if (CommonHelper.checkIsEmpty(headLine)) {
			throw new BizException("行标题需要在文件首行体现且不为空!");
		}

		//1.1 检查标题头尾
		checkHeadLine(headLine);

		//1.2 检查是否有注释头
		String tagNote = null;
		boolean hasTagNote = false;
		for (String _tagNote : SUPPORT_NOTES_TAG) {
			if (headLine.startsWith(_tagNote)) {
				tagNote = _tagNote;
				hasTagNote = true;
				break;
			}
		}

		//1.3 有注释字符则从最后一位开始到最后为标题说明部分
		String headFieldContent = (hasTagNote) ? StringUtils.substringAfterLast(headLine, tagNote) : headLine;

		//1.4 得到标题列数目且不能小于1
		int fieldNum = CommonHelper.getMsgLineArrayInSplit(headFieldContent, DEFAULT_LINE_SPLIT).length;

		if (fieldNum < INTERVAL_LINE_INDEX) {
			throw new BizException("行标题中字段属性不能设置为空!");
		}

		//1.5检查是否有END结束标志
		if(!checkEnd(lineContentList)){
			throw new BizException("文件需要设置END结束标志!");
		}
		
		return fieldNum;
	}

	/**
	 * <p> 
	 * <li>可以有SUPPORT_NOTES_TAG的符号开头</li> 
	 * <li>但不能以这个结尾，中间不能有上述内容字符AA#BB-这样的(尚未实现)</li>
	 * </p>     
	 * @param headLine
	 * @version: 2011-4-14 下午04:46:10
	 * @See:
	 */
	protected void checkHeadLine(String headLine) throws BizException {
		boolean result = false;

		for (String tagNote : SUPPORT_NOTES_TAG) {
			if (headLine.endsWith(tagNote)) {
				result = true;
				break;
			}
		}

		if (result) {
			throw new BizException("您的文件行标题不符合规范,行标题为[" + headLine + "]");
		}
	}
	
	/**
	 * <p>检查结束标志符END</p>
	 * @param lineContentList
	 * @throws BizException
	 */
	protected boolean checkEnd(List<String> lineContentList) throws BizException {
		int num = lineContentList.size();
		String endLine = lineContentList.get(num-1).trim();
		if(endLine.startsWith(END)){
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * <p>根据提取的文件含内容转换成PointAccReg对象</p>
	 * @param resolveContentList
	 * @return
	 * @throws BizException
	 * @version: 2011-4-14 下午05:35:23
	 * @See:
	 */
	protected abstract PointAccReg getPointAccRegResolve(String fileName, String branchCode,List<String[]> resolveContentList) throws BizException;
	
	/**
	 * 
	  * <p>对文件内容进行统计</p>
	  * @param resolveContentList
	  * @return
	  * @throws Exception  
	  * @version: 2011-4-15 下午03:21:39
	  * @See:
	 */
	protected abstract String getContentListRemark(List<String[]> resolveContentList)  throws BizException;
	
	
	/**
	 * 
	  * <p>处理缺省的PointAccReg组合,包括统计的支持</p>
	  * @param fileName
	  * @param branchCode
	  * @param resolveContentList
	  * @return
	  * @throws BizException  
	  * @version: 2011-4-15 下午03:25:33
	  * @See:
	 */
	protected PointAccReg getDefaulttPointAccRegResolve(String fileName, String branchCode,List<String[]> resolveContentList) throws BizException {
		PointAccReg pointAccReg = getInitPointAccReg(fileName,branchCode,resolveContentList);
		
		String remark = getContentListRemark(resolveContentList);
	    
	    if(CommonHelper.checkIsNotEmpty(remark)) {
	    	 pointAccReg.setRemark(remark);
	    }
	   
		return pointAccReg;
	}

	/**
	 * 
	 * <p>设置文件的编码</p>
	 * @return
	 * @version: 2011-4-14 下午03:37:37
	 * @See:
	 */
	protected abstract String getFileEncoding();

	/**
	 * 
	 * <p>检测文件命名规范或者流的大小(待定)</p>
	 * @param fileName
	 * @param input
	 * @throws BizException
	 * @version: 2011-4-14 下午03:36:07
	 * @See:
	 */
	protected abstract void checkResolve(String fileName, InputStream input) throws BizException;

	/**
	 * 
	 * <p>设定行值分隔符</p>
	 * @return
	 * @version: 2011-4-14 下午03:33:09
	 * @See:
	 */
	protected abstract String getLineSplit();

	/**
	 * 
	 * <p>设定处理的类型</p>
	 * @return
	 * @version: 2011-4-14 下午07:40:05
	 * @See:
	 */
	protected abstract String getPointAccTransType();

	/**
	 * 
	 * <p>获得一个各个文件处理初始化的结果</p>
	 * @param resolveContentList
	 * @return
	 * @version: 2011-4-14 下午07:24:44
	 * @See:
	 */
	protected PointAccReg getInitPointAccReg(String fileName, String branchCode, List<String[]> resolveContentList) {
		PointAccReg pointAccReg = new PointAccReg();

		pointAccReg.setFileName(fileName);
		
		pointAccReg.setCardIssuer(branchCode);

		pointAccReg.setTransType(getPointAccTransType());

		pointAccReg.setRecordNum(resolveContentList.size());
		
		Date date = CommonHelper.getCalendarDate();

		pointAccReg.setTime(date);
		
		pointAccReg.setImportDate(CommonHelper.getCommonDateFormatStr(date));

		pointAccReg.setStatus(PointAccState.WAIT_EFFECT.getValue());

		return pointAccReg;

	}

}
