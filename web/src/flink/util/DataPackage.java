package flink.util;

import java.util.Collection;

/**
 * 数据包.
 * 
 * @author haichen.ma
 *
 */
public interface DataPackage {
	Collection getData();
	void setData(Collection data);	
	
	PackageHeader getPackageHeader();
	void setPackageHeader(PackageHeader header);
}
