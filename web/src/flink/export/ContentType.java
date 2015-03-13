package flink.export;

/**
 * <p>内容类型枚举</p>
 * @Project: Card
 * @File: ContentTypeEnum.java
 * @See: 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-5-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public enum ContentType {

	ZIP_CONTENT("application/zip"),

	EXCEL_CONTENT("application/vnd.ms-excel"),

	TXT_CONTENT("text/html"),

	PDF_CONTENT("application/pdf");

	private String contentType;

	private ContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
}
