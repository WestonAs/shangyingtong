package gnete.card.workflow.app;

import gnete.etc.BizException;

/**
 * 
 * @author aps-lih
 */
public interface WorkflowAdapter {
	
	/**
	 * 取得当前工作流id
	 */
	public String getWorkflowId();
	
	public Object getJobslip(String refid);
	
	/**
	 * 流程下发时调用
	 * 
	 * @param refid		表单对应的业务ID
	 * @param node		节点
	 * @param param 	参数
	 * @param userId	用户id
	 * @throws BizException
	 */
	public void postForward(String refid, Integer nodeId, String param, String userId) throws BizException;
	
	/**
	 * 流程退回时调用
	 * 
	 * @param refid	工作单对象
	 * @param node		节点
	 * @param param 	参数
	 * @param userId	用户id
	 * @throws BizException
	 */
	public void postBackward(String refid, Integer nodeId,  String param, String userId) throws BizException;
	
	/**
	 * 流程结束时调用
	 * @param refid	工作单对象
	 * @param param 	参数
	 * @throws BizException
	 */
	public void flowEnd(String refid, String param, String userId) throws BizException;
	
}
