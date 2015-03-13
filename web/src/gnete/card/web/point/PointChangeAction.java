package gnete.card.web.point;

import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.PointChgRegDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.PointChgReg;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PointBusService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 积分调整
 * @author aps-lib
 *
 */
public class PointChangeAction extends BaseAction{
	
	@Autowired
	private PointChgRegDAO pointChgRegDAO;
	@Autowired
	private PointBalDAO pointBalDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointBusService pointBusService;
	
	private PointChgReg pointChgReg;
	private PointBal pointBal;
	private Paginater page;
	private Collection ptClassList;
	private Collection pointBalList;
	private File upload;
	private String uploadFileName;
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;
	private List<RegisterState>	statusList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pointChgReg", pointChgReg);
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			// this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if (CollectionUtils.isEmpty(branchList)) {
				params.put("branchCode", " ");
			}
			
		} else if (isCardOrCardDeptRoleLogined()) {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			
		} else if (isMerchantRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getMerchantNo());
			
		} else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			params.put("cardIssuers", branchList);
		}
		
		page = pointChgRegDAO.findPointChgReg(params, this.getPageNumber(), this.getPageSize());
		this.statusList = RegisterState.getForCheck();	
		
		return LIST;
	}
	
	//取得明细
	public String detail() throws Exception {
		this.pointChgReg = (PointChgReg) this.pointChgRegDAO.findByPk(pointChgReg.getPointChgId());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询积分调整[" + this.pointChgReg.getPointChgId() + "]明细信息");
		return DETAIL;
	}
	
	// 打开积分调整的初始化操作
	public String showAdd() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined(), "非发卡机构、机构网点或者商户,不允许进行操作。");
		
		dealIsNeedSign();
		PointBalKey key = new PointBalKey();
		key.setAcctId(pointChgReg.getAcctId());
		key.setPtClass(pointChgReg.getPtClass());
		pointBal = (PointBal)this.pointBalDAO.findByPk(key);
		pointBal.setPtClassName(((PointClassDef)pointClassDefDAO.findByPk(pointBal.getPtClass())).getClassName());
		return ADD;
	}	
	
	// 积分调整登记
	public String add() throws Exception {		
		
		this.checkUserSignatureSerialNo();
		this.pointBusService.pointChange(pointChgReg, this.getSessionUser());
		
//		//启动单个流程
//		this.workflowService.startFlow(pointChgReg, "pointChgRegAdapter", Long.toString(pointChgReg.getPointChgId()), this.getSessionUser());
//		
		String msg = "新增积分调整登记[" + pointChgReg.getAcctId() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/pointBus/pointChange/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined(), "非发卡机构、机构网点或者商户,不允许进行操作。");
		
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		TimeInterval t = new TimeInterval();
		String[] pointChgRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_POINT_CHG_REG, this.getSessionUser());
		logger.info("[{}]查询积分调整待审核id数组，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		if (ArrayUtils.isEmpty(pointChgRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", pointChgRegIds);
		t.reset();
		this.page = this.pointChgRegDAO.findPointChgReg(params, this.getPageNumber(), this.getPageSize());
		logger.info("[{}]查询积分调整审核列表，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		return CHECK_LIST;
	}
	
	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(pointChgReg, "积分调整对象不能为空");	
		Assert.notNull(pointChgReg.getPointChgId(), "积分调整登记ID不能为空");	
		
		// 积分调整登记簿明细
		this.pointChgReg = (PointChgReg)this.pointChgRegDAO.findByPk(pointChgReg.getPointChgId());		
		
		return DETAIL;	
	}
	
	// 打开新增页面的初始化操作
	public String showFilePointChgReg() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined(), "非发卡机构、机构网点或者商户,不允许进行操作。");

		this.dealIsNeedSign();
		
		return "addFilePointChgReg";
	}
	
	// 新增对象操作
	public String addFilePointChgReg() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined(), "非发卡机构、机构网点或者商户,不允许进行操作。");
		
		this.checkUserSignatureSerialNo();
		
		List<PointChgReg> unInsertList =  this.pointBusService.addFilePointChgReg(upload, this.getUploadFileName(), this.getSessionUser());
		String msg = "";
		if(unInsertList.size() == 0){
			msg = "文件批量添加积分调整全部成功";
		}
		else{
			for(PointChgReg pointChgReg : unInsertList){
				msg = msg + pointChgReg.getRemark() + ";";
			}
			msg = msg + "以上积分调整记录有误，不能调整积分。";
		}
		
		this.addActionMessage("/pointBus/pointChange/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	//积分账户
	public String prepareAdd() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isMerchantRoleLogined(), "非发卡机构、机构网点或者商户,不允许进行操作。");
		
//		this.ptClassList = pointClassDefDAO.findAll();
		
		Map params = new HashMap();
		if(pointChgReg != null){
			params.put("ptClass", pointChgReg.getPtClass());
			params.put("acctId", pointChgReg.getAcctId());
			pointBalList = this.pointBalDAO.findPointBal(params);
		}
		return "prepareAdd";
	}
	
	// 取得积分账户列表
	public String pointBalList() throws Exception {
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.pointChgReg.getCardId());
		if(cardInfo == null){
			return null;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (isCardOrCardDeptRoleLogined()) {
			params.put("jinstId", this.getSessionUser().getBranchNo());
		} else if (isMerchantRoleLogined()) {
			params.put("jinstId", this.getSessionUser().getMerchantNo());
		}
		if(StringUtils.isEmpty(cardInfo.getAcctId())){
			return null;
		}
		params.put("acctId", cardInfo.getAcctId());
		this.pointBalList = this.pointBalDAO.findPointBal(params);
		
		return "pointBalList";
	}
	
	public void validateCardId(){
		String cardId = this.pointChgReg.getCardId();
		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject object = new JSONObject();
		
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "该卡号不存在，请重新输入。");
			Assert.notNull(cardInfo.getAcctId(), "该卡号没有账户，不能调整积分。");
			
			params.put("acctId", cardInfo.getAcctId());
			
			if (isCardOrCardDeptRoleLogined()) {
				params.put("jinstId", this.getSessionUser().getBranchNo());
			} else if (isMerchantRoleLogined()) {
				params.put("jinstId", this.getSessionUser().getMerchantNo());
			}
			Assert.isTrue(this.pointBalDAO.findPointBal(params).size()>0, "该卡号不存在积分账户。");
			
			object.put("success", true);
		}catch (Exception e) {
			object.put("success", false);
			object.put("error", e.getMessage());
		}
//		this.respond("{'success':"+ true + "}");
		this.respond(object.toString());
	}
	
	public PointChgReg getPointChgReg() {
		return pointChgReg;
	}

	public void setPointChgReg(PointChgReg pointChgReg) {
		this.pointChgReg = pointChgReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getPtClassList() {
		return ptClassList;
	}

	public void setPtClassList(Collection ptClassList) {
		this.ptClassList = ptClassList;
	}

	public Collection getPointBalList() {
		return pointBalList;
	}

	public void setPointBalList(Collection pointBalList) {
		this.pointBalList = pointBalList;
	}

	public PointBal getPointBal() {
		return pointBal;
	}

	public void setPointBal(PointBal pointBal) {
		this.pointBal = pointBal;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}
}
