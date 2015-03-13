package flink.tree;

import gnete.card.entity.Privilege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class PrivilegeTreeTool {

	public static List<Privilege> getChildren(String parentId, List<Privilege> allLimits) {
		List<Privilege> result = new ArrayList<Privilege>(allLimits.size());
		
		for(Privilege privilege : allLimits) {
			if(StringUtils.equals(privilege.getParent(), parentId)) {
				result.add(privilege);
			}
		}
		
		//排序
		sortTree(result);
		return result;
	}
	
	/**
	 * 排序
	 * @param result
	 */
	public static void sortTree(List<Privilege> list) {
		Collections.sort(list, new Comparator<Privilege>(){

			public int compare(Privilege o1, Privilege o2) {			
				return compareTo(o1.getParent(),o2.getParent());
			}
			
			private int compareTo(String s1, String s2) {
				if((s1 == null) || (s2 == null)) {
					return 0;
				}
				
				return s1.compareTo(s2);				
			}
			
		});
		/*
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				Privilege p1 = (Privilege) o1;
				Privilege p2 = (Privilege) o2;
				
				int result = compareTo(p1.getParent(), p2.getParent());
				return result ;
			}

			private int compareTo(String s1, String s2) {
				
				if (s1 == null && s2 == null) {
					return 0;
				}
				
				if (s1 != null) {
					return s1.compareTo(s2);
				}
				
				return 0;
			}
		});*/
	}
	
	/**
	 * 生成树形XML
	 * @param rootId
	 * @param hasLimits
	 * @return
	 */
	public static String buildTree(String rootId, List<Privilege> allLimit, Map hasLimitMap){		
		StringBuffer sb = new StringBuffer(2048);
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<tree id=\"0\">");
		sb.append(buildChildrenTree(rootId, hasLimitMap, allLimit));
		
		sb.append("</tree>");
		return sb.toString();
	}
	
	/**
	 * 生成明细的树形XML
	 * @param rootId
	 * @param hasLimits
	 * @return
	 */
	public static String buildDetailTree(String rootId, List limits){		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<tree id=\"0\">");
		sb.append(buildChildrenTree(rootId, null, limits));
		
		sb.append("</tree>");
		return sb.toString();
	}

	/**
	 * 递归得到树形，并组装成XML数据
	 * 
	 * @param parentId	父节点ID
	 * @param hasLimitMap 拥有的权限Map
	 * @param allLimits 所有权限列表 
	 * @return
	 */
	private static String buildChildrenTree(String parentId, Map hasLimitMap, List<Privilege> allLimits){
		StringBuilder sb = new StringBuilder(1024);
		TreeNode temp = null;
		List<Privilege> tree = getChildren(parentId, allLimits);
		for (Privilege element : tree) {
			//Privilege element = (Privilege) iter.next();
			boolean checked = false;
			if (hasLimitMap != null) {
				if (hasLimitMap.get(element.getLimitId()) != null) {
					checked = true;
				}
			}
			boolean isMenu = "Y".equals(element.getIsMenu());
			// 验证是否有下级
			List<Privilege> chileren = getChildren(element.getLimitId(), allLimits);
			if (CollectionUtils.isEmpty(chileren)){
				isMenu = false;
			}
			temp = new TreeNode(element.getLimitId(), element.getLimitName(),element.getParent(), isMenu, checked);
			
			sb.append(getTreeXmlByObject(temp));
			sb.append(buildChildrenTree(element.getLimitId(), hasLimitMap, allLimits));
			sb.append("</item>");
		}
		return sb.toString();
	}

	private static String getTreeXmlByObject(TreeNode node) {
		StringBuilder sb = new StringBuilder(512);
		sb.append("<item ");
		sb.append("text=\"").append(node.getText()).append("\" ");
		sb.append("id=\"").append(node.getId()).append("\" ");
		if (node.isIsfolder() && node.isChecked()) {
			// -1状态表示为树形的checkbox第三状态，即下级未全部选中
			sb.append("checked=\"-1\" ");
		} else if (node.isChecked()) {
			sb.append("checked=\"1\" ");
		}
		// 有下级则为true
		if (node.isIsfolder()) {
			sb.append("im0=\"folderClosed.gif\" ");
			sb.append("im1=\"folderOpen.gif\" ");
			sb.append("im2=\"folderClosed.gif\" ");
		} else {
			sb.append("im0=\"leaf.gif\" ");
			sb.append("im1=\"leaf.gif\" ");
			sb.append("im2=\"leaf.gif\" ");
		}
		sb.append(">");
		return sb.toString();
	}
}
