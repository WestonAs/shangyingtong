package gnete.card.workflow.entity;

public class WorkflowConfigKey {
    private String isBranch;

    private String refId;

    private String workflowId;
    
    public WorkflowConfigKey() {
	}

    public WorkflowConfigKey(String isBranch, String refId, String workflowId) {
		super();
		this.isBranch = isBranch;
		this.refId = refId;
		this.workflowId = workflowId;
	}

	public String getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(String isBranch) {
        this.isBranch = isBranch;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}