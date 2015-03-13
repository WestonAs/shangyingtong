package gnete.card.workflow.service.impl;

import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.BranchProxyDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BranchProxyKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.workflow.app.WorkflowAdapter;
import gnete.card.workflow.compare.ExpAnalyse;
import gnete.card.workflow.dao.WorkflowConfigDAO;
import gnete.card.workflow.dao.WorkflowDAO;
import gnete.card.workflow.dao.WorkflowHisDAO;
import gnete.card.workflow.dao.WorkflowMultitransDAO;
import gnete.card.workflow.dao.WorkflowNodeConditionDAO;
import gnete.card.workflow.dao.WorkflowNodeDAO;
import gnete.card.workflow.dao.WorkflowPosDAO;
import gnete.card.workflow.dao.WorkflowTodoDAO;
import gnete.card.workflow.entity.Workflow;
import gnete.card.workflow.entity.WorkflowConfig;
import gnete.card.workflow.entity.WorkflowConfigKey;
import gnete.card.workflow.entity.WorkflowHis;
import gnete.card.workflow.entity.WorkflowMultitrans;
import gnete.card.workflow.entity.WorkflowNode;
import gnete.card.workflow.entity.WorkflowNodeCondition;
import gnete.card.workflow.entity.WorkflowNodeKey;
import gnete.card.workflow.entity.WorkflowPos;
import gnete.card.workflow.entity.WorkflowPosKey;
import gnete.card.workflow.entity.WorkflowTodo;
import gnete.card.workflow.entity.state.WorkflowState;
import gnete.card.workflow.entity.state.WorkflowTodoState;
import gnete.card.workflow.entity.type.WorkflowNodeFollow;
import gnete.card.workflow.entity.type.WorkflowNodeType;
import gnete.card.workflow.entity.type.WorkflowOperType;
import gnete.card.workflow.service.WorkflowEngine;
import gnete.etc.Assert;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工作流引擎
 * @author aps-lih
 */
@Service("workflowEngine")
public class WorkflowEngineImpl implements WorkflowEngine {

	@Autowired
	private WorkflowConfigDAO workflowConfigDAO;
	@Autowired
	private WorkflowNodeDAO workflowNodeDAO;
	@Autowired
	private WorkflowNodeConditionDAO workflowNodeConditionDAO;
	@Autowired
	private WorkflowPosDAO workflowPosDAO;
	@Autowired
	private WorkflowHisDAO workflowHisDAO;
	@Autowired
	private WorkflowTodoDAO workflowTodoDAO;
	@Autowired
	private WorkflowMultitransDAO workflowMultitransDAO;
	@Autowired
	private WorkflowDAO workflowDAO;
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private BranchProxyDAO branchProxyDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	public void startFlow(Object jobslip, WorkflowAdapter adapter, String[] ids, UserInfo userInfo) throws Exception {
		Assert.notNull(jobslip, "流程处理异常：工作单对象为空");
		Assert.notTrue(userInfo.getRole().getRoleType().equals(RoleType.PERSONAL.getValue()), "流程处理异常：个人用户没有权限！");
		/*
		 * 启动一个流程实例的过程：
		 * 1. 读取该商户或机构是否配置了流程
		 * 2. 根据流程配置情况生成待办任务记录、流程录入记录
		 */
		// 商户和机构需读取是否已经自定义了流程
		String workflowId = adapter.getWorkflowId();
		WorkflowConfig config = null;
		String isBranch = null;
		String refid = null;
		if (userInfo.getRole().getRoleType().equals(RoleType.MERCHANT.getValue())) {
			isBranch = Symbol.NO;
			refid = userInfo.getMerchantNo();
		} else {
			isBranch = Symbol.YES;
			refid = userInfo.getBranchNo();
		}
		config = (WorkflowConfig) this.workflowConfigDAO.findByPk(
				new WorkflowConfigKey(isBranch, refid, workflowId));
		
		// 读取默认的流程
		if (config == null) {
			refid = WorkflowConstants.DEFAULT_REF_ID;
			isBranch = Symbol.YES;
		}
		Workflow workflow = (Workflow) this.workflowDAO.findByPk(workflowId);
		
//		String userId = userInfo.getUserId();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			
			// 记录流程处理记录
			// 读取节点信息
			WorkflowNode node = null;
			
			node = (WorkflowNode) this.workflowNodeDAO.findByPk(
					new WorkflowNodeKey(isBranch, 0, refid, workflowId));
			
			Assert.notNull(node, "该流程[" + workflowId +"]没有配置合适的节点信息！");
				
			// 添加处理记录 （流程节点、处理人、处理时间、所做操作、处理意见）
			WorkflowHis workflowHis = new WorkflowHis();
			workflowHis.setNodeId(0);
			workflowHis.setNodeName(node.getNodeName());
			workflowHis.setNodeType(node.getNodeType());
			workflowHis.setOperTime(new Date());
			workflowHis.setOperType(WorkflowOperType.REGISTER.getValue());
			workflowHis.setRefId(id);
			workflowHis.setUserId(userInfo.getUserId());
			workflowHis.setWorkflowId(workflowId);
			this.workflowHisDAO.insert(workflowHis);
		
//			// 如果未配置则读取默认设置
//			if (config == null) {
//				// 添加流程状态信息
//				WorkflowPos pos = new WorkflowPos();
//				pos.setNodeId(0);
//				pos.setRefId(id);
//				pos.setStatus(WorkflowState.FINISH.getValue());
//				pos.setWorkflowId(workflowId);
//				this.workflowPosDAO.insert(pos);
//				
//				adapter.flowEnd(id, null, userId);
//				continue;
//			}
			
			// 有配置则查找下一节点
			WorkflowNode nextNode = this.findNextNode(jobslip, node, isBranch, userInfo, refid);
			
			// 如果没有处理节点，则直接结束流程
			if (nextNode == null 
					|| (config == null && nextNode.getNodeId() > workflow.getDefaultLevel())
					|| (config != null && nextNode.getNodeId() > config.getAuditLevel())) {
				WorkflowPos pos = new WorkflowPos();
				pos.setNodeId(0);
				pos.setRefId(id);
				pos.setStatus(WorkflowState.FINISH.getValue());
				pos.setWorkflowId(workflowId);
				this.workflowPosDAO.insert(pos);
				
				adapter.flowEnd(id, null, userInfo.getUserId());
				continue;
			}
			
			// 更新流程状态为运行到下一节点
			WorkflowPos pos = new WorkflowPos();
			pos.setStatus(WorkflowState.RUNNING.getValue());
			pos.setNodeId(nextNode.getNodeId());
			pos.setRefId(id);
			pos.setWorkflowId(workflowId);
			this.workflowPosDAO.insert(pos);
			
			// 生成下一个节点的待办任务
			this.addTodoTask(workflowId, id, nextNode);
			
			adapter.postForward(id, nextNode.getNodeId(), null, userInfo.getUserId());
		}
		
	}
	
	public void doFlow(WorkflowAdapter adapter, String ids, 
			boolean pass, String desc, String param, UserInfo userInfo) throws Exception {
		Assert.notNull(adapter, "流程处理异常：流程适配器为空，请重新配置！");
		Assert.notTrue(userInfo.getRole().getRoleType().equals(RoleType.PERSONAL.getValue()), "流程处理异常：个人用户没有权限！");
		String[] idsArray = ids.split(",");
		
		String workflowId = adapter.getWorkflowId();
		
		// 商户和机构需读取是否已经自定义了流程
		WorkflowConfig config = null;
		String isBranch = null;
		if (userInfo.getRole().getRoleType().equals(RoleType.MERCHANT.getValue())) {
			isBranch = Symbol.NO;
			config = (WorkflowConfig) this.workflowConfigDAO.findByPk(
					new WorkflowConfigKey(isBranch, userInfo.getMerchantNo(), workflowId));
		} else {
			isBranch = Symbol.YES;
			config = (WorkflowConfig) this.workflowConfigDAO.findByPk(
					new WorkflowConfigKey(isBranch, userInfo.getBranchNo(), workflowId));
		}
		
		// 处理该流程
		this.processFlow(adapter, idsArray, pass, desc, userInfo, config, isBranch, param);
	}
	
	public List<WorkflowHis> findFlowHis(String workflowId, String refid) {
		return this.workflowHisDAO.findByRefId(workflowId, refid);
	}
	
	public String[] getMyJob(String workflowId, List<RoleInfo> roles) {
		// 得到待办任务
		List<WorkflowTodo> todoList = this.workflowTodoDAO.findTodoByWorkflowId(workflowId, roles);
		if (CollectionUtils.isEmpty(todoList)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		
		// 验证节点属性，剔除掉没有权限的审批节点
		RoleInfo roleInfo =  roles.get(0);
		
		WorkflowConfig config = null;
		String isBranch = null;
		String refid = null;
		if (roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())) {
			isBranch = Symbol.NO;
			refid = roleInfo.getMerchantNo();
		} else {
			isBranch = Symbol.YES;
			refid = roleInfo.getBranchNo();
		}
		config = (WorkflowConfig) this.workflowConfigDAO.findByPk(new WorkflowConfigKey(isBranch, refid, workflowId));
		
		if (config == null) {
			refid = WorkflowConstants.DEFAULT_REF_ID;
			isBranch = Symbol.YES;
		}
		
		Map<String, UserInfo> userCache = new HashMap<String, UserInfo>();
		Map<String, WorkflowNode> workflowNodeCache = new HashMap<String, WorkflowNode>();
		Map<String, Boolean> checkSelfSuperBranchCache = new HashMap<String, Boolean>();
		List<WorkflowTodo> result = new ArrayList<WorkflowTodo>();
		for (WorkflowTodo todo: todoList) {
			WorkflowNode node = getWorkflowNodeFromCache(workflowNodeCache, isBranch, todo.getNodeId(),	refid, workflowId);
			if (node == null){ continue; }
			
			if (WorkflowNodeFollow.NORMAL.getValue().equals(node.getIsReg())) { // 节点审批人不做限制
				result.add(todo);
				continue;
			}
			
			//XXX 读取申请人的机构信息
			WorkflowHis his = this.workflowHisDAO.findByRefIdAndNodeId(workflowId, todo.getRefId(), 0);
			if (his == null) { continue; }
			
			UserInfo user = getUserInfoFromCache(userCache, his.getUserId());
			if (user == null) {	continue; }
				
			if (WorkflowNodeFollow.REG.getValue().equals(node.getIsReg())) { // 同一机构
				// 如果一致则添加任务，否则剔除
				if (roleInfo.getMerchantNo() != null 
						&& roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()) 
						&& roleInfo.getMerchantNo().equals(user.getMerchantNo())) {
					result.add(todo);
				} else if (roleInfo.getBranchNo() != null 
						&& roleInfo.getBranchNo().equals(user.getBranchNo())) {
					result.add(todo);
				}
				
			} else if (WorkflowNodeFollow.SELF_SUPER.getValue().equals(node.getIsReg())) { // 同一机构或上级
				if (roleInfo.getMerchantNo() != null 
						&& roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()) 
						&& roleInfo.getMerchantNo().equals(user.getMerchantNo())) {
					result.add(todo);
				} else if (roleInfo.getBranchNo() != null
						&& getIsSuperBranchFromCache(checkSelfSuperBranchCache, roleInfo.getBranchNo(),
								user.getBranchNo())) {
					result.add(todo);
				}

			} else if (WorkflowNodeFollow.PROXY_CARD.getValue().equals(node.getIsReg())) { // 节点审批人必须是与该申请人机构有代理关系的发卡机构
				String proxyBranch = user.getBranchNo();
				BranchProxyKey branchProxyKey = new BranchProxyKey(proxyBranch, roleInfo.getBranchNo(), ProxyType.SELL.getValue());
				if (this.branchProxyDAO.isExist(branchProxyKey)) {
					result.add(todo);
				}
				
			} else if (WorkflowNodeFollow.FENZHI.getValue().equals(node.getIsReg())) { // 节点审批人必须是与该申请人所属机构或商户的管理分支机构
				// 如果申请人是商户的话
				if (StringUtils.isNotEmpty(user.getMerchantNo())) {
					MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(user.getMerchantNo());
					if (merchInfo == null) {
						continue;
					}
					// 节点审批人的机构号与商户的管理机构号一致则添加任务
					if (StringUtils.equals(merchInfo.getParent(), roleInfo.getBranchNo())) {
						result.add(todo);
					}
				}
				// 如果申请人不是商户
				else {
					BranchInfo branchInfo = this.branchInfoDAO.findBranchInfo(user.getBranchNo());
					if (branchInfo == null) {
						continue;
					}
					//节点审批人的机构号与机构的管理机构号一致则添加任务
					if (StringUtils.equals(branchInfo.getParent(), roleInfo.getBranchNo())) {
						result.add(todo);
					}
				}
			}
			
		} //-- end of for loop --
		
		// 组成工单id数组返回
		String[] ids = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			ids[i] = result.get(i).getRefId();
		}
		return ids;
	}
	
	
	private Boolean getIsSuperBranchFromCache(Map<String, Boolean> cache, String superBranchCode,
			String subBranchCode) {
		String key = new StringBuilder("#").append(superBranchCode).append("#").append(subBranchCode).toString();
		if (!cache.containsKey(key)) {
			cache.put(key, branchInfoDAO.isSuperBranch(superBranchCode, subBranchCode));
		}
		return cache.get(key);
	}
	
	private WorkflowNode getWorkflowNodeFromCache(Map<String, WorkflowNode> workflowNodeCache,
			String isBranch, Integer nodeId, String refid, String workflowId) {
		String key = new StringBuilder("#").append(isBranch).append("#").append(nodeId).append("#")
				.append(refid).append("#").append(workflowId).toString();
		if (!workflowNodeCache.containsKey(key)) {
			WorkflowNode node = (WorkflowNode) this.workflowNodeDAO.findByPk(new WorkflowNodeKey(isBranch,
					nodeId, refid, workflowId));
			workflowNodeCache.put(key, node);
		}
		return workflowNodeCache.get(key);
	}

	private UserInfo getUserInfoFromCache(Map<String, UserInfo> userCache, String userId){
		if(!userCache.containsKey(userId)){
			userCache.put(userId, (UserInfo) this.userInfoDAO.findByPk(userId));
		}
		return userCache.get(userId); 
	}
	
	/**
	 * 处理流程 审批通过、审批不通过 操作
	 */
	private void processFlow(WorkflowAdapter adapter, String[] ids, boolean pass, String desc,
			UserInfo userInfo, WorkflowConfig config, String isBranch, String param) throws Exception {
		String workflowId = adapter.getWorkflowId();
		Workflow workflow = (Workflow) this.workflowDAO.findByPk(workflowId);
		String refid;
		
		if (config == null) {
			refid = WorkflowConstants.DEFAULT_REF_ID;
			isBranch = Symbol.YES;
		} else {
			refid = config.getRefId();
		}
		
		
		// 得到当前处理人拥有的角色，默认是登录时选择的角色
		List<RoleInfo> roles = new ArrayList<RoleInfo>();
		roles.add(userInfo.getRole());
		
		
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			
			// 读取流程的申请人
			WorkflowHis reg = this.workflowHisDAO.findByRefIdAndNodeId(workflowId, id, 0);
			Assert.notTrue(reg != null && reg.getUserId().equals(userInfo.getUserId()), "申请人与审核人["+userInfo.getUserId()+"]不能是同一个人");
			
			/*
			 * 引擎的处理流程：
			 * 1. 添加处理记录	workflow_his
			 * 2. 更新待办任务为已办workflow_todo
			 * 3. 查找下一个节点
			 * 4. 更新当前任务处理状态workflow_pos
			 * 5. 生成新的待办任务
			 * 6. 调用适配器的回调方法
			 */
			
			// 读取流程运行到的位置
			WorkflowPos pos = (WorkflowPos) this.workflowPosDAO.findByPk(new WorkflowPosKey(id, workflowId));
			Assert.notNull(pos, "未找到流程数据!");
			Assert.notTrue(WorkflowState.FINISH.getValue().equals(pos.getStatus()), "流程处理异常：该流程已结束!");
			
			// 读取节点信息
			int nodeId = pos.getNodeId();
			WorkflowNode node = (WorkflowNode) this.workflowNodeDAO.findByPk(
					new WorkflowNodeKey(isBranch, nodeId, refid, workflowId));
			
			// 添加处理记录 （流程节点、处理人、处理时间、所做操作、处理意见）
			WorkflowHis workflowHis = new WorkflowHis();
			workflowHis.setNodeId(nodeId);
			workflowHis.setNodeName(node.getNodeName());
			workflowHis.setNodeType(node.getNodeType());
			workflowHis.setOperTime(new Date());
			if (pass) {
				workflowHis.setOperType(WorkflowOperType.CHECK_PASS.getValue());
			} else {
				workflowHis.setOperType(WorkflowOperType.CHECK_NOPASS.getValue());
			}
			workflowHis.setOperDesc(desc);
			workflowHis.setRefId(id);
			workflowHis.setUserId(userInfo.getUserId());
			workflowHis.setWorkflowId(workflowId);
			this.workflowHisDAO.insert(workflowHis);
			
			// 将待办任务置为已办
			this.updateTodoTask(workflowId, id, roles);
			
			if (pass) {
				// 如果运行到最后一个节点，则结束该流程
				if ((config != null && nodeId >= config.getAuditLevel())
						|| (config == null && nodeId >= workflow.getDefaultLevel())) {
					pos.setStatus(WorkflowState.FINISH.getValue());
					this.workflowPosDAO.update(pos);
					
					adapter.flowEnd(id, param, userInfo.getUserId());
					continue;
				}
				
				// 如果是协同处理，判断当前流程是否已经协同结束，如果未结束则执行该IF中内容
				if (node.getNodeType().equals(WorkflowNodeType.MULTITRANS.getValue())
						&& !this.workflowTodoDAO.isNodeFinished(workflowId, id, roles)) {
					continue;
				}
				
				// 否则就读取下一节点，方法是：1. 读取工作单对象, 2. 将工单对象与节点信息传入，查找下一个节点, 3. 若找到节点则继续，未找到节点则结束流程
				Object jobslip = adapter.getJobslip(id);
				WorkflowNode nextNode = this.findNextNode(jobslip, node, isBranch, userInfo, refid);
				
				// 如果没有处理节点，则直接结束流程
				if (nextNode == null 
						|| (config != null && nextNode.getNodeId() > config.getAuditLevel())
						|| (config == null && nextNode.getNodeId() > workflow.getDefaultLevel())) {
					pos.setStatus(WorkflowState.FINISH.getValue());
					this.workflowPosDAO.update(pos);
					
					adapter.flowEnd(id, param, userInfo.getUserId());
					continue;
				}
				
				// 更新流程状态为运行到下一节点
				pos.setStatus(WorkflowState.RUNNING.getValue());
				pos.setNodeId(nextNode.getNodeId());
				this.workflowPosDAO.update(pos);
				
				// 生成下一个节点的待办任务
				this.addTodoTask(workflowId, id, nextNode);
				
				adapter.postForward(id, nextNode.getNodeId(), param, userInfo.getUserId());
			} else {
				pos.setStatus(WorkflowState.BACKWORD.getValue());
				pos.setNodeId(0);
				this.workflowPosDAO.update(pos);
				
				adapter.postBackward(id, nodeId, param, userInfo.getUserId());
			}
		}
		
	}
	
	/**
	 * 读取下一个处理节点
	 * @param jobslip 工单对象
	 * @param node 当前节点
	 * @param isBranch 
	 * @param userInfo 
	 * @return
	 */
	private WorkflowNode findNextNode(Object jobslip, WorkflowNode node, String isBranch, UserInfo userInfo, String refid) {
		WorkflowNode nextNode = null;
		String workflowId = node.getWorkflowId();
		
		int i = 0;
		// 定义下一个节点ID
		int currentNodeId = node.getNodeId() + 1;
		while (true){
			// 读取该节点条件
			List<WorkflowNodeCondition> conditions = this.workflowNodeConditionDAO.findByNodeId(
					workflowId, currentNodeId, isBranch, refid);
			
			// 如果所设条件为空或满足所设条件则选定该节点
			if (CollectionUtils.isEmpty(conditions) ||
					ExpAnalyse.analysisExpress(jobslip, conditions)) {
				nextNode = (WorkflowNode) this.workflowNodeDAO.findByPk(
						new WorkflowNodeKey(isBranch, currentNodeId, refid, workflowId));
				break;
			}
			
			// 若下一节点不符合条件，则将node节点加一，继续查找下级节点
			currentNodeId++;
			
			// 避免死循环
			if (i > 999) {
				break;
			}
		}
		return nextNode;
	}

	/**
	 * 添加待办任务
	 */
	private void addTodoTask(String workflowId, String id, WorkflowNode nextNode) {
		if (WorkflowNodeType.COMMON.getValue().equals(nextNode.getNodeType())) {
			WorkflowTodo todo = new WorkflowTodo();
			todo.setNodeId(nextNode.getNodeId());
			todo.setRefId(id);
			todo.setWorkflowId(workflowId);
			todo.setIsBranch(nextNode.getIsBranch());
			todo.setRefBranchId(nextNode.getRefId());
			todo.setStatus(WorkflowTodoState.TODO.getValue());

			this.workflowTodoDAO.insert(todo);
		}
		// 协同处理
		else if (WorkflowNodeType.MULTITRANS.getValue().equals(nextNode.getNodeType())) {
			List<WorkflowMultitrans> list = this.workflowMultitransDAO.findByNodeId(
					workflowId, nextNode.getNodeId(), nextNode.getIsBranch(), nextNode.getRefId());
			
			for (WorkflowMultitrans multitrans : list) {
				WorkflowTodo todo = new WorkflowTodo();
				todo.setNodeId(multitrans.getNodeId());	
				todo.setRefId(id);
				todo.setWorkflowId(workflowId);
				todo.setIsBranch(multitrans.getIsBranch());
				todo.setRefBranchId(nextNode.getRefId());
				todo.setStatus(WorkflowTodoState.TODO.getValue());
				todo.setMultiNodeId(multitrans.getNodeId());

				this.workflowTodoDAO.insert(todo);
			}
		}
	}

	/**
	 * 将待办任务置为已办
	 */
	private void updateTodoTask(String workflowId, String id,
			List<RoleInfo> roles) {
		// 查找我的当前待办任务，
		// 注意：此处绝大部分都是返回一个任务，只有当前任务节点处在协同节点，而该用户的多个角色又同时都是协同处理人时才返回多条(这种情况无法避免)
		List<WorkflowTodo> list = this.workflowTodoDAO.findTodoByRefid(workflowId, id, roles);
		for (WorkflowTodo todo : list) {
			todo.setStatus(WorkflowTodoState.DONE.getValue());
			this.workflowTodoDAO.update(todo);
		}
	}
	
	public void deleteFlow(String workflowId, String refId) throws Exception {
		Assert.notEmpty(workflowId, "流程ID不能为空");
		Assert.notEmpty(refId, "工单ID不能为空");
		
		this.workflowPosDAO.delete(new WorkflowPosKey(refId, workflowId));
		this.workflowHisDAO.deleteByRefId(workflowId, refId);
		this.workflowTodoDAO.deleteByRefId(workflowId, refId);
	}

}
