package gnete.card.web.test;

import gnete.card.dao.TestLeaveDAO;
import gnete.card.entity.TestLeave;
import gnete.card.web.BaseAction;
import gnete.etc.WorkflowConstants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class TestAction extends BaseAction {

	@Autowired
	private TestLeaveDAO testLeaveDAO;

	private List<TestLeave> list;
	
	private TestLeave testLeave;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		System.out.println(testLeave.getLeavetime());
		System.out.println(testLeave.getName());
		
//		this.list = this.testLeaveDAO.findAll();
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.testLeave = (TestLeave) this.testLeaveDAO.findByPk(this.testLeave.getId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		// 调用service业务接口
		this.testLeaveDAO.insert(this.testLeave);
		
		this.workflowService.startFlow(this.testLeave, WorkflowConstants.TEST_ADAPTER, this.testLeave.getId(), this.getSessionUser());
		
		this.addActionMessage("/test/list.do", "添加成功！");
		return SUCCESS;
	}
	
	public String checkList() throws Exception {
		// 得到我审批的列表
		String[] ids = this.workflowService.getMyJob("Leave", this.getSessionUser());
		
		this.list = this.testLeaveDAO.findByIds(ids);
		return CHECK_LIST;
	}

	public TestLeave getTestLeave() {
		return testLeave;
	}
	

	public void setTestLeave(TestLeave testLeave) {
		this.testLeave = testLeave;
	}

	public List<TestLeave> getList() {
		return list;
	}

	public void setList(List<TestLeave> list) {
		this.list = list;
	}

}
