package flink.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 权限辅助类.
 * 
 * @author aps-mhc
 *
 */
public abstract class PrivilegeHelper {
	/**
	 * 整理成权限树形式.
	 * 
	 * @param privileges
	 * 
	 * @return
	 */
	public static IPrivilege getPrivilegeTree(IPrivilege root, Collection<IPrivilege> privileges) {
		List<IPrivilege> children = getChildren(root, privileges);
		root.setChildren(children);
		
		return root;
	}

	/**
	 * 获取权限的子权限.
	 * 
	 * @param parent
	 * @param privileges
	 * @return
	 */
     private static List<IPrivilege> getChildren(IPrivilege parent, Collection<IPrivilege> privileges) {
		List<IPrivilege> result = new ArrayList<IPrivilege>();
		
		/*
		for (Iterator i = privileges.iterator(); i.hasNext();) {
			IPrivilege privilege = (IPrivilege) i.next();
			
			if (isChild(privilege, parent)) {
				privilege.setChildren(getChildren(privilege, privileges));
				result.add(privilege);
			}
		}*/
		
        for(IPrivilege privilege : privileges) {
        	if (isChild(privilege, parent)) {
				privilege.setChildren(getChildren(privilege, privileges));
				result.add(privilege);
			}	
		}
		
		return result;
	}
	
	/**
	 * 前者是否为后者的子权限.
	 * 
	 * @param privilege
	 * @param parent
	 * @return
	 */
	private static boolean isChild(IPrivilege privilege, IPrivilege parent) {
		return StringUtils.equals(privilege.getParent(), parent.getCode());
	}
	
	/**
	 * 查找父节点.
	 * @param privilege
	 * @param privileges
	 * @return
	 */
	public static IPrivilege getParent(IPrivilege privilege, Collection<IPrivilege> privileges) {
		String code = privilege.getCode();
		
		/*		
		for (Iterator i = privileges.iterator(); i.hasNext();) {
			IPrivilege p = (IPrivilege) i.next();
			
			if (p.getCode().equals(code)) {
				return p;
			}
		}*/
		
		IPrivilege parentPrivilege = null;
		
		 for(IPrivilege p : privileges) {
			 if(p.getCode().equals(code))  {
				 parentPrivilege = p;
				 break;
			 }
		 }
		 
		 return parentPrivilege;

	}
	
	/**
	 * 根据编号查找权限对象.
	 * @param code
	 * @param privileges
	 * @return
	 */
	public static IPrivilege getPrivilege(String code, Collection<IPrivilege> privileges) {
		/*
		for (Iterator i = privileges.iterator(); i.hasNext();) {
			IPrivilege p = (IPrivilege) i.next();
			
			if (p.getCode().equals(code)) {
				return p;
			}
		}
		
		return null;
		*/
		
		IPrivilege searchPrivilege = null;
		
		 for(IPrivilege p : privileges) {
			 if(p.getCode().equals(code))  {
				 searchPrivilege = p;
				 break;
			 }
		 }
		 
		 return searchPrivilege;
	}
	
}
