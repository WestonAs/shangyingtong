package gnete.card.export;

import java.io.File;
import java.util.List;

import flink.util.CommonHelper;
import flink.util.FileHelper;
import flink.export.AbstractViewBuilderImpl;
import flink.export.ContentType;
import java.util.Iterator;

import gnete.card.entity.UserCertificate;

/**
 * <p>用户数字证书查询结果视图输出</p>
 * @Project: Card
 * @File: UserCertificateViewBuilderImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-5-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class UserCertificateViewBuilderImpl extends AbstractViewBuilderImpl {

	private final String exportName;

	public UserCertificateViewBuilderImpl(String exportName) {
		this.exportName = exportName;
	}

	@Override
	protected String getViewContentHead() {
		return this.exportName;
	}

	@Override
	protected ContentType getViewContentType() {		
		return ContentType.ZIP_CONTENT;
	}

	/**
	 * <p>将输出的数据整理成AA|bb|cc|的换行形式</p>
	 */
	@Override
	protected Object[] getLocalExportStream(List list, File sysTempDir, File headFile) throws Exception {
		boolean persist = FileHelper.persistFile(getUserCertificateContent(list), headFile,DEFAULT_EXPORT_ENCODING);

		if (persist) {
			// 1.1 将headFile进行压缩处理
			File zipHeadFile = getLocaleZipFile(sysTempDir, FileHelper.getFullFileName(headFile));

			// 1.2 将原文件拷贝到压缩文件中
			FileHelper.copyFile2Zip(headFile, zipHeadFile);

			return new Object[] { FileHelper.getBufferedInputStream(zipHeadFile), FileHelper.getFullFileName(zipHeadFile) };
		}

		return new Object[] {};
	}

	/**
	 *  <p>构造用户数字证书查询结果整理成视图输出(一行行)</p>
	  * @param list
	  * @return  
	  * @version: 2011-7-12 上午11:35:33
	  * @See:
	 */
	protected static String getUserCertificateContent(List list) {
		StringBuilder contentBuilder = new StringBuilder(DEFAULT_BUFFER);
		
		contentBuilder.append(getUserCertificateHead());
		contentBuilder.append(DEFAULT_LINE_SPLIT);
		
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UserCertificate userCertificate = (UserCertificate) iter.next();
			StringBuilder rowBuilder = getUserCertificateRow(userCertificate);
			contentBuilder.append(rowBuilder);
			contentBuilder.append(DEFAULT_LINE_SPLIT);
		}
		
		return contentBuilder.toString();
	}
	
	/**
	 * 
	  * <p>用户数字证书输出标题行头</p>
	  * @return  
	  * @version: 2011-5-19 下午08:34:04
	  * @See:
	 */
	protected static StringBuilder getUserCertificateHead() {
		return new StringBuilder(100).append("所属机构").append(DEFAULT_CONTENT_SPLIT)
		                              .append("绑定机构").append(DEFAULT_CONTENT_SPLIT)
		                              .append("绑定用户").append(DEFAULT_CONTENT_SPLIT)
		                              .append("证书序列号").append(DEFAULT_CONTENT_SPLIT)
		                              .append("证书文件名").append(DEFAULT_CONTENT_SPLIT)
		                              .append("开通日期").append(DEFAULT_CONTENT_SPLIT)
		                              .append("到期日期").append(DEFAULT_CONTENT_SPLIT)
		                              .append("证书状态").append(DEFAULT_CONTENT_SPLIT)
		                              .append("证书DN信息").append(DEFAULT_CONTENT_SPLIT);
	}

	/**
	 * 
	  *<p>用户数字证书输出消息体(单行记录)</p>
	  * @param userCertificate
	  * @return  
	  * @version: 2011-5-19 下午08:34:15
	  * @See:
	 */
	protected static StringBuilder getUserCertificateRow(UserCertificate userCertificate) {
		StringBuilder sb = new StringBuilder(200);

		sb.append(userCertificate.getBranchCode()).append(DEFAULT_CONTENT_SPLIT)
		  .append(CommonHelper.safeTrim(userCertificate.getBndBranch())).append(DEFAULT_CONTENT_SPLIT)
		  .append(CommonHelper.safeTrim(userCertificate.getUserId())).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getSeqNo()).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getFileName()).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getStartDate()).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getEndDate()).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getUseStateName()).append(DEFAULT_CONTENT_SPLIT)
		  .append(userCertificate.getDnNo()).append(DEFAULT_CONTENT_SPLIT);

		return sb;
	}

}
