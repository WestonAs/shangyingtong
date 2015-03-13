package flink.util;

import java.util.Collection;

/**
 * 权限接口.
 * 
 * @author aps-mhc
 */
public interface IPrivilege {
	/**
	 * 权限编号.
	 * @return
	 */
	String getCode();
	
	void setCode(String code);
	
	/**
	 * 权限名称.
	 * @return
	 */
	String getName();
	
	/**
	 * 权限入口.
	 * @return
	 */
	String getEntry();
	
	/**
	 * 父级权限编号.
	 * @return
	 */
	String getParent();
	
	/**
	 * 子权限集合.
	 * @param children
	 */
	void setChildren(Collection<IPrivilege> children);
	
	/**
	 * 是否需要审核
	 * @return
	 */
	String getIfAudit();
}
