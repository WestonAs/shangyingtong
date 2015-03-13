package gnete.card.entity;

import java.util.Collection;

import flink.util.IPrivilege;

public class Privilege implements IPrivilege,java.io.Serializable {
    
	private static final long serialVersionUID = 1L;

	private String limitId;

    private String limitName;

    private String parent;

    private String isMenu;

    private Integer menuOrder;

    private String ifAudit;
    
    // 定义属性.
    private Collection children;
    private String entry;
    
    private String isWorkflow;
    
    public Privilege()
    {
    }

    
    public Privilege(String limitId, String limitName, String parent, String isMenu, Integer menuOrder, String ifAudit, String entry) {
		super();
		this.limitId = limitId;
		this.limitName = limitName;
		this.parent = parent;
		this.isMenu = isMenu;
		this.menuOrder = menuOrder;
		this.ifAudit = ifAudit;
		this.entry = entry;
	}

    public String getLimitId() {
        return limitId;
    }

    public void setLimitId(String limitId) {
        this.limitId = limitId;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getIfAudit() {
        return ifAudit;
    }

    public void setIfAudit(String ifAudit) {
        this.ifAudit = ifAudit;
    }

	public Collection getChildren() {
		return children;
	}

	public void setChildren(Collection children) {
		this.children = children;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getCode() {
		return this.limitId;
	}

	public void setCode(String code) {
		this.limitId = code;
	}

	public String getName() {
		return this.limitName;
	}


	public String getIsWorkflow() {
		return isWorkflow;
	}


	public void setIsWorkflow(String isWorkflow) {
		this.isWorkflow = isWorkflow;
	}
}