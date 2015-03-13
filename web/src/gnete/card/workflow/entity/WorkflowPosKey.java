package gnete.card.workflow.entity;

public class WorkflowPosKey {
    private String refId;

    private String workflowId;
    
    public WorkflowPosKey() {
	}

    public WorkflowPosKey(String refId, String workflowId) {
		super();
		this.refId = refId;
		this.workflowId = workflowId;
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