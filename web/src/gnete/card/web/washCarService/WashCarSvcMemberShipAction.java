package gnete.card.web.washCarService;

import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.WashCarSvcMemberShipDAO;
import gnete.card.entity.WashCarSvcMbShipDues;
import gnete.card.entity.state.WashCarCheckState;
import gnete.card.entity.state.WashCarSvcMemberShipState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.WashCarSvcMbShipDuesService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * 
 * @author slt02
 *
 */
public class WashCarSvcMemberShipAction extends BaseAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = -347712989759005013L;
	/**缴费状态*/
	private List<WashCarSvcMemberShipState> washCarSvcMemberShipList;
	/**登记状态*/
	private List<WashCarCheckState> washCarSvcCheckList;
	@Autowired
	private WashCarSvcMbShipDuesService washCarSvcMbShipDuesService;
	
	@Autowired
	private WashCarSvcMemberShipDAO washCarSvcMemberShipDAO;
	
	private WashCarSvcMbShipDues washCarSvcMbShipDues;
	
	private String branchNamme;
	
	private Paginater page;
	
	/**上传文件*/
	private File upload;
	/** 上传的文件名 */
	private String uploadFileName;

	@Override
	public String execute() throws Exception {
		getStatusList();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(washCarSvcMbShipDues != null){
			params.put("cardId", washCarSvcMbShipDues.getCardId());
			params.put("cardIssuer", washCarSvcMbShipDues.getCardIssuer());
			params.put("status", washCarSvcMbShipDues.getStatus());
			params.put("checkStatus", washCarSvcMbShipDues.getCheckStatus());
		}
		
	    this.page = washCarSvcMemberShipDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String detail()throws Exception {
		washCarSvcMbShipDues = (WashCarSvcMbShipDues) washCarSvcMemberShipDAO.findByPk(this.washCarSvcMbShipDues.getId());
		return DETAIL;
	}
    
	public String showAdd()throws Exception{
		checkRoleLogined();
		getStatusList();
		return ADD;
	}
	
	public String add()throws Exception{
		
		String msg = String.format("成功","");
		this.log(msg, UserLogType.ADD);
		addActionMessage("/washCarService/washCarServiceMemberShipDues/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String showModify()throws Exception{
		
		return MODIFY;
	}
	
	public String modify()throws Exception{
		
		return SUCCESS;
	}
	
    public void export()throws Exception{
    	this.washCarSvcMbShipDuesService.exportWashCarSvcMbShipExcel(response);
	}
	
	public String showAddFile()throws Exception{
		
		return "addFile";
	}
	
	public String addFile()throws Exception{
		Assert.isTrue(IOUtil.testFileFix(uploadFileName,"xls"), "导入的文件格式只能是Excel2003");
		this.washCarSvcMbShipDuesService.importWashCarSvcMbShipExcel(upload,washCarSvcMbShipDues,this.getSessionUser());
		String msg = String.format("会员缴费导入成功","");
		this.log(msg, UserLogType.ADD);
		addActionMessage("/washCarService/washCarServiceMemberShipDues/list.do?goBack=goBack", msg);
		return SUCCESS;
	 }
		
    public String checkList()throws Exception{
    	// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WASH_CAR_SVC_MB_SHIP_DUES, this.getSessionUser());
		System.out.println(ids.length);
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
		}
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		
		this.page = this.washCarSvcMemberShipDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
    
    public String checkDetail()throws Exception {
		washCarSvcMbShipDues = (WashCarSvcMbShipDues) washCarSvcMemberShipDAO.findByPk(this.washCarSvcMbShipDues.getId());
		return "checkDetail";
	}
    
    private void checkRoleLogined() throws BizException {
		Assert.isTrue(isCardOrCardDeptRoleLogined(), "没有权限操作该页面");
	}
    
    @SuppressWarnings("unchecked")
	private void getStatusList(){
       this.washCarSvcMemberShipList = WashCarSvcMemberShipState.getAll();
       this.washCarSvcCheckList = WashCarCheckState.getAll();
    }  

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}


	public List<WashCarSvcMemberShipState> getWashCarSvcMemberShipList() {
		return washCarSvcMemberShipList;
	}

	public void setWashCarSvcMemberShipList(
			List<WashCarSvcMemberShipState> washCarSvcMemberShipList) {
		this.washCarSvcMemberShipList = washCarSvcMemberShipList;
	}

	public List<WashCarCheckState> getWashCarSvcCheckList() {
		return washCarSvcCheckList;
	}

	public void setWashCarSvcCheckList(List<WashCarCheckState> washCarSvcCheckList) {
		this.washCarSvcCheckList = washCarSvcCheckList;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public WashCarSvcMbShipDues getWashCarSvcMbShipDues() {
		return washCarSvcMbShipDues;
	}

	public void setWashCarSvcMbShipDues(WashCarSvcMbShipDues washCarSvcMbShipDues) {
		this.washCarSvcMbShipDues = washCarSvcMbShipDues;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getBranchNamme() {
		return branchNamme;
	}

	public void setBranchNamme(String branchNamme) {
		this.branchNamme = branchNamme;
	}

}
