package flink.tree;

import java.util.HashMap;

public class TreeNode {
	
	private Object id;

	private String text;

	private Object parentId;

	/**
	 * 仅用于需要异步读取数据的文件夹节点。当节点有子节点时，该属性不起作用。节点永远为文件夹节点
	 */
	private boolean isfolder = false;

	private HashMap attrs = new HashMap();
	
	private boolean checked = false;
	
	private boolean hasChildren = false;

	public TreeNode(Object id, String text, Object parentId) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
	}

	public TreeNode(Object id, String text, Object parentId,
			boolean isFolder) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
		this.isfolder = isFolder;
	}
	
	public TreeNode(Object id, String text, Object parentId,
			boolean isFolder, boolean checked) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
		this.isfolder = isFolder;
		this.checked = checked;
	}

	public TreeNode(Object id, String text, Object parentId,
			boolean isFolder, HashMap attrs) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
		this.isfolder = isFolder;
		this.attrs = attrs;
	}

	public void addAttribute(String key, Object value) {

		this.attrs.put(key, value);
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getParentId() {
		return parentId;
	}

	public void setParentId(Object parentId) {
		this.parentId = parentId;
	}

	public HashMap getAttrs() {
		return attrs;
	}

	public void setAttrs(HashMap attrs) {
		this.attrs = attrs;
	}

	public boolean isIsfolder() {
		return isfolder;
	}

	public void setIsfolder(boolean isfolder) {
		this.isfolder = isfolder;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

}
