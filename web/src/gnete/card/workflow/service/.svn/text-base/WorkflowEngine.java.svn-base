package gnete.card.workflow.service;

import java.util.List;

import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.card.workflow.entity.WorkflowHis;

/**
 * 工作流引擎接口
 * @author aps-lih
 *
 */
public interface WorkflowEngine {

	/**
	 * 启动流程
	 * @param jobslip	流程ID
	 * @param adapter	流程ID
	 * @param ids	工作单ID数组
	 * @param userInfo	启动流程操作员
	 */
	void startFlow(Object jobslip, WorkflowAdapter adapter, String[] ids, UserInfo userInfo) throws Exception;
	
	/**
	 * 流程执行
	 * 
	 * @param workflowAdapter	流程适配器
	 * @param ids	注意：该ids不是数组，由界面js处理组成的字符串，需要根据","split生成数组
	 * @param pass	审核是否通过
	 * @param param 
	 * @param userInfo	审核人信息
	 * @throws Exception
	 */
	void doFlow(WorkflowAdapter workflowAdapter, String ids, 
			boolean pass, String desc, String param, UserInfo userInfo) throws Exception;


	/**
	 * 得到我当前流程的待办任务
	 * 
	 * @param workflowId	流程ID
	 * @param roles	我拥有的角色列表
	 * @return
	 */
	String[] getMyJob(String workflowId, List<RoleInfo> roles);

	/**
	 * 查找流程处理记录(生成流程图时必用)
	 * 
	 * @param workflowId	流程ID
	 * @param refid		所引用的工单对象
	 * @return
	 */
	List<WorkflowHis> findFlowHis(String workflowId, String refid);

	/**
	 * 删除某一个工单流程
	 * @param workflowId
	 * @param refId
	 */
	void deleteFlow(String workflowId, String refId) throws Exception;
	
}
