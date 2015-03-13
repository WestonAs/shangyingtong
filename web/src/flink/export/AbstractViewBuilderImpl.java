package flink.export;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flink.util.CommonHelper;
import flink.util.DownLoadHelper;
import flink.util.FileHelper;

/**
 * 
 * @Project: Card
 * @File: AbstractViewBuilderImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-5-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public abstract class AbstractViewBuilderImpl implements IViewBuilder {
     public void viewExport(List list, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String contentHead = getViewContentHead();
		if (CommonHelper.checkIsEmpty(list) || CommonHelper.checkIsEmpty(contentHead)) {
			return;
		}

		contentHead = filterContentHead(contentHead);

		// 1.1 获得本地文件临时文件夹
		File sysTempDir = FileHelper.getLocalTempDir(System.getProperty("java.io.tmpdir"));

		// 1.2 根据传入的head构造好本地文件
		File headFile = getLocalHeadFile(sysTempDir, contentHead);

		try {
			// 1.3 将LIST的文件整理成contentType的形式写入到headFile中再进行处理
			Object[] exportStream = getLocalExportStream(list, sysTempDir, headFile);

			if (CommonHelper.checkIsEmpty(exportStream)) {
				return;
			}

			// 1.4 使用流、文件名以及下载内容体进行输出处理
			InputStream stream = (InputStream) exportStream[0];
			contentHead = (String) exportStream[1];
			DownLoadHelper.processDownLoad(response, getViewContentType().getContentType(), contentHead, stream);

		} finally {
			//1.5 删除产生的临时文件夹
			FileHelper.deleteFile(sysTempDir);
		}
	}

	/**
	 * 
	 * <p>根据产生的本地文件将原来的内容本地化(有可能后续进行压缩)</p>
	 * @param list          查询结果记录
	 * @param sysTempDir    临时文件目录
	 * @param headFile      本地产生文件
	 * @return
	 * @throws IOException
	 * @version: 2011-5-19 下午04:53:36
	 * @See:
	 */
	protected abstract Object[] getLocalExportStream(List list, File sysTempDir, File headFile) throws Exception;

	/**
	 * 
	 * <p>获得视图输出的内容</p>
	 * @return
	 * @version: 2011-5-19 下午04:54:09
	 * @See:
	 */
	protected abstract ContentType getViewContentType();

	/**
	 * 
	 *<p>获得输出的名称</p>
	 * @return
	 * @version: 2011-5-19 下午04:54:25
	 * @See:
	 */
	protected abstract String getViewContentHead();

	/**
	 * 
	 * <p>在系统临时目录中创建跟传入文件名称的文件</p>
	 * @param tempDir        临时文件目录
	 * @param contentHead    文件名称
	 * @return
	 * @throws IOException
	 * @version: 2011-5-19 下午04:47:44
	 * @See:
	 */
	protected static File getLocalHeadFile(File tempDir, String contentHead) throws IOException {
		return FileHelper.getFile(tempDir, contentHead);
	}

	/**
	 * 
	 * <p>在系统临时目录中创建跟文件名称相同的压缩文件名</p>
	 * @param tempDir        临时文件目录
	 * @param contentHead    文件名称
	 * @return
	 * @throws Exception
	 * @version: 2011-5-19 下午04:48:29
	 * @See:
	 */
	protected static File getLocaleZipFile(File tempDir, String contentHead) throws Exception {
		return FileHelper.getFile(tempDir, FileHelper.getCreateFileName(contentHead, "", "", DEFAULT_ZIP_SUFFIX));
	}

	/**
	 * 
	 * <p>如果输入的名称没有带后缀那么则缺省生成.txt的文件名</p>
	 * @param contentHead   文件名称
	 * @return
	 * @version: 2011-5-19 下午04:57:54
	 * @See:
	 */
	protected static String filterContentHead(String contentHead) {
		String fileName = FileHelper.getFileName(contentHead);

		return fileName.equalsIgnoreCase(contentHead) ? contentHead.concat(DEFAULT_FILE_SUFFIX) : contentHead;
	}

}
