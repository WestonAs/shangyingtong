package flink.file;

import gnete.etc.BizException;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import flink.util.FileHelper;
import flink.util.CommonHelper;
import org.springframework.util.ObjectUtils;

/**
 * 
  * @Project: Card
  * @File: AbstractFileLineImporter.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-23
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public abstract class AbstractFileLineImporter implements IFileLineImporter {
	protected final String DEFAULT_ENCODING = "UTF-8";
	
	protected final String DEFAULT_LINESPLIT = ",";
	
	protected final long DEFAULT_MAX_FILE = 5 * 1024 * 1024;
	
	protected final String[] DEFAULT_FILE_SUFFIX = new String[] {".txt",".csv"};

	public List getFileImporterList(File importFile, String uploadFileName) throws BizException {
		checkImporter(importFile, uploadFileName);

		try {
			List<String> lineContentList = FileHelper.readLines(importFile, getFileEncoding());

			List importList = new ArrayList(lineContentList.size());

			String lineSplit = getLineSplit();
			
			// 获取表头字段数目
			int fieldNum = 0;
			if(lineContentList.size()!=0){
				fieldNum = lineContentList.get(0).split(lineSplit).length;
			}
			
			// 从第二行开始解析
			for (int i=1; i<lineContentList.size(); i++) {
				String lineContent = lineContentList.get(i);
				if (CommonHelper.checkIsEmpty(lineContent)) {
					continue;
				}

				String[] contentArray = lineContent.split(lineSplit, -1);
				
				// 明细元素个数要和模板配置明细个数一致.
				if (contentArray.length != fieldNum) {
					throw new Exception("明细元素个数和模板配置明细个数不一致.");
				}

				if (CommonHelper.checkIsEmpty(contentArray)) {
                    continue;
				}

				importList.add(getImportObject(contentArray, fieldNum));
			}

			return importList;

		} catch (IOException ex) {
			/*throw new BizException("读取导入文件异常,文件[" + FileHelper.getFullFileName(importFile) + "],原因[" + ex.getMessage()
					+ "]");*/
			throw new BizException("读取导入文件异常,文件[" + uploadFileName + "],原因[" + ex.getMessage()
					+ "]");
		} catch (Exception ex) {
			throw new BizException("文件转换处理出现异常,原因[" + ex.getMessage() + "]");
		}

	}

	protected void checkImporter(File importFile, String uploadFileName) throws BizException {
		if (CommonHelper.checkIsEmpty(getFileEncoding())) {
			throw new BizException("必须指定导入文件的编码格式!");
		}

		if (CommonHelper.checkIsEmpty(getFileSuffix())) {
			throw new BizException("导入文件后缀必须制定！");
		}

		if (CommonHelper.checkIsEmpty(getLineSplit())) {
			throw new BizException("文件内容行分隔符必须设定!");
		}

		if (getFileMaxSize() <= 0) {
			throw new BizException("文件限制大小必须设定!");
		}

		//String fileName = FileHelper.getFullFileName(importFile);
		String fileName = uploadFileName;

		boolean result = false;
		for (String suffix : getFileSuffix()) {
			if (fileName.endsWith(suffix)) {
				result = true;
				break;
			}
		}

		if (!result) {
			throw new BizException("上传文件不符合规范,文件必须为[" + ObjectUtils.nullSafeToString(getFileSuffix()) + "]后缀!");
		}

		long fileLength = importFile.length();

		if (fileLength > getFileMaxSize()) {
			throw new BizException("您上传了过大的文件,请重新上传!");
		}
	}

	/**
	 * 
	  * @description：设定文件的编码
	  * @return  
	  * @version: 2010-12-23 下午05:34:37
	  * @See:
	 */
	protected abstract String getFileEncoding();

	/**
	 * 
	  * @description：设定文件的后缀
	  * @return  
	  * @version: 2010-12-23 下午05:34:48
	  * @See:
	 */
	protected abstract String[] getFileSuffix();

	/**
	 * 
	  * @description：设定文件内容的分隔符
	  * @return  
	  * @version: 2010-12-23 下午05:34:58
	  * @See:
	 */
	protected abstract String getLineSplit();

	/**
	 * 
	  * @description：设定文件的最大大小
	  * @return  
	  * @version: 2010-12-23 下午05:35:14
	  * @See:
	 */
	protected abstract long getFileMaxSize();
	
	/**
	 * 
	  * @description：获得行内容转换的对象
	  * @param contentArray
	  * @return
	  * @throws Exception  
	  * @version: 2010-12-23 下午05:35:28
	  * @See:
	 */
	protected abstract Object getImportObject(String[] contentArray, int fieldNum) throws Exception;
}
