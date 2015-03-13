package gnete.card.workflow.entity;

public class WorkflowNode extends WorkflowNodeKey {
    private String nodeName;

    private String nodeType;
    
    private String auditLimit;
    
    private String isReg;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

	public String getAuditLimit() {
		return auditLimit;
	}

	public void setAuditLimit(String auditLimit) {
		this.auditLimit = auditLimit;
	}

	public String getIsReg() {
		return isReg;
	}

	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}

}