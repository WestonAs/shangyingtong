package flink.export;

/**
 * <p>支持视图输出的文件类型</p>
 * @Project: Card
 * @File: ViewSupportEnum.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-5-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public enum ViewType {
	TXT("txt"), CSV("cvs"), XML("xml"), EXCEL("excel"), PDF("pdf"), TXT_ZIP("txt" + " " + "zip"), CSV_ZIP("cvs" + " " + "zip"), 
	XML_ZIP("xml" + " " + "zip"), EXCEL_ZIP("excel" + " " + "zip"), PDF_ZIP("pdf" + " " + "zip");

	private String viewType;

	private ViewType(String view) {
		this.viewType = view;
	}

	public String getViewType() {
		return this.viewType;
	}
}
