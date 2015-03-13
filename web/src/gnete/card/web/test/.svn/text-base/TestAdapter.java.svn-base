package gnete.card.web.test;

import org.springframework.beans.factory.annotation.Autowired;

import gnete.card.dao.TestLeaveDAO;
import gnete.card.entity.TestLeave;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.etc.BizException;

/**
 * 
 * @author aps-lih
 */
public class TestAdapter implements WorkflowAdapter {
	
	@Autowired
	private TestLeaveDAO testLeaveDAO;

	public String getWorkflowId() {
		return "Leave";
	}
	
	public Object getJobslip(String refid) {
		return this.testLeaveDAO.findByPk(refid);
	}
	
	public void postForward(String refid, Integer node, String param, String userId)
		throws BizException {
	
	}
	
	public void postBackward(String refid, Integer node, String param, String userId)
			throws BizException {
		
	}
	
	public void flowEnd(String refid, String param, String userId) throws BizException {
		TestLeave leave = (TestLeave) this.testLeaveDAO.findByPk(refid);
		leave.setState("1");
		
		this.testLeaveDAO.update(leave);
	}

}
