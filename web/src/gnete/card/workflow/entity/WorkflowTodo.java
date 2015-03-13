package gnete.card.workflow.entity;

public class WorkflowTodo {
    private Long id;

    private String workflowId;

    private String refId;

    private Integer nodeId;

    private String isBranch;
    
    private String refBranchId;

    private String status;
    
    private Integer multiNodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getIsBranch() {
		return isBranch;
	}

	public void setIsBranch(String isBranch) {
		this.isBranch = isBranch;
	}

	public String getRefBranchId() {
		return refBranchId;
	}

	public void setRefBranchId(String refBranchId) {
		this.refBranchId = refBranchId;
	}

	public Integer getMultiNodeId() {
		return multiNodeId;
	}

	public void setMultiNodeId(Integer multiNodeId) {
		this.multiNodeId = multiNodeId;
	}

}