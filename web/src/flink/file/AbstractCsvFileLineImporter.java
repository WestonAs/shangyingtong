package flink.file;

/**
 * 
 * @Project: Card
 * @File: AbstractCsvFileLineImporter.java
 * @See:
 * @description：<li>处理csv或txt文件的内容导入</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-23
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractCsvFileLineImporter extends AbstractFileLineImporter {

	public AbstractCsvFileLineImporter() {
		super();
	}

	@Override
	protected String getFileEncoding() {

		return DEFAULT_ENCODING;
	}

	@Override
	protected long getFileMaxSize() {		
		return DEFAULT_MAX_FILE;
	}

	@Override
	protected String[] getFileSuffix() {		
		return DEFAULT_FILE_SUFFIX;
	}

	@Override
	protected String getLineSplit() {		
		return DEFAULT_LINESPLIT;
	}

}
