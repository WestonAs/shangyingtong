package gnete.card.web.washCarService;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.WashCarActivityDAO;
import gnete.card.dao.WashCarActivityRecordDAO;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.WashCarActivity;
import gnete.card.entity.WashCarActivityRecord;
import gnete.card.entity.type.UserLogType;
import gnete.card.entity.type.WashCarCycleType;
import gnete.card.entity.type.WashTherInvalIdType;
import gnete.card.service.WashCarActivityService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * WashCarActivityAction
 * 洗车活动
 * @author slt02
 *
 */
public class WashCarActivityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1354984600708386290L;
    
	@Autowired
	private WashCarActivityDAO washCarActivityDAO;
	@Autowired
	private WashCarActivityRecordDAO washCarActivityRecordDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private WashCarActivityService washCarActivityService;
	
	private List<WashCarCycleType> washCarCycleList;
	
	private List<WashTherInvalIdType> washTherInvalIdList;
	
	private String merchName;//商户名
	
	private String cardIssuerName;//发卡机构名
	
	private WashCarActivity washCarActivity;
	private WashCarActivityRecord washCarActivityRecord;
	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		washCarCycleList = WashCarCycleType.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if(washCarActivity != null){
			if(isCardRoleLogined()){
				params.put("cardIssuer",this.getSessionUser().getBranchNo());
			}else if(isMerchantRoleLogined()){
				params.put("merchId", this.getSessionUser().getMerchantNo());
			}else{
				params.put("merchId", washCarActivity.getMerchId());
				params.put("cardIssuer",washCarActivity.getCardIssuer());
			}
			params.put("activityId", washCarActivity.getActivityId());
			params.put("activityName", washCarActivity.getActivityName());
			
			params.put("merchId", washCarActivity.getMerchId());
			params.put("washCarCycle", washCarActivity.getWashCarCycle());
			
		}
		page = washCarActivityDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
    public String detail()throws Exception {
    	
    	this.washCarActivity = (WashCarActivity) this.washCarActivityDAO.findByPk(washCarActivity.getActivityId());
		return DETAIL;
	}
    
	@SuppressWarnings("unchecked")
	public String showAdd()throws Exception{
		washCarCycleList = WashCarCycleType.getAll();
		washTherInvalIdList = WashTherInvalIdType.getAll();
		// 如果登录用户为运营中心，运营中心部门
		if(isCenterRoleLogined()){
			
		}else if(isCardOrCardDeptRoleLogined()){
			washCarActivity = new WashCarActivity();
			washCarActivity.setCardIssuer(this.getSessionUser().getBranchNo());
		}else if(isMerchantRoleLogined()){
			washCarActivity = new WashCarActivity();
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(getSessionUser().getMerchantNo());
			merchName = merchInfo.getMerchName();
			washCarActivity.setMerchId(getSessionUser().getMerchantNo());
			washCarActivity.setCardIssuer(merchInfo.getParent());
		}else{
			throw new BizException("没有权限新增活动!");
		}
		return ADD;
	}
	
	public String add()throws Exception{
		this.washCarActivityService.addWashCarActivity(washCarActivity, this.getSessionUser());
		String msg = LogUtils.r("新增洗车活动规则编号[{0}]成功",washCarActivity.getActivityId());
		this.log(msg, UserLogType.ADD);
		addActionMessage("/washCarService/washCarActivity/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String showModify()throws Exception{
		washCarCycleList = WashCarCycleType.getAll();
		washTherInvalIdList = WashTherInvalIdType.getAll();
		
		if(isCardRoleLogined()){
			
		}else if(isMerchantRoleLogined()){
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(getSessionUser().getMerchantNo());
			merchName = merchInfo.getMerchName();
		}else{
			throw new BizException("没有权限新增活动!");
		}
		
		this.washCarActivity = (WashCarActivity) this.washCarActivityDAO.findByPk(washCarActivity.getActivityId());
		
		if(isCardRoleLogined()){
			MerchInfo merchInfo = (MerchInfo) this.merchInfoDAO.findByPk(washCarActivity.getMerchId());
			merchName = merchInfo.getMerchName();
		}
		return MODIFY;
	}
	
	public String modify()throws Exception{
		
		this.washCarActivityService.modifyWashCarActivity(washCarActivity, this.getSessionUser());
		String msg = LogUtils.r("修改洗车活动规则编号[{0}]成功",washCarActivity.getActivityId());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/washCarService/washCarActivity/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String lastDetail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(washCarActivityRecord != null){
			if(isCardRoleLogined()){
				params.put("cardIssuer", this.getSessionUser().getBranchNo());
			}
			params.put("activityId", washCarActivityRecord.getActivityId());
			params.put("cardId", washCarActivityRecord.getCardId());
			params.put("extId", washCarActivityRecord.getExtId());
		}
		page = this.washCarActivityRecordDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return "lastDetail";
	}

	public WashCarActivity getWashCarActivity() {
		return washCarActivity;
	}

	public void setWashCarActivity(WashCarActivity washCarActivity) {
		this.washCarActivity = washCarActivity;
	}

	public List<WashCarCycleType> getWashCarCycleList() {
		return washCarCycleList;
	}

	public void setWashCarCycleList(List<WashCarCycleType> washCarCycleList) {
		this.washCarCycleList = washCarCycleList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<WashTherInvalIdType> getWashTherInvalIdList() {
		return washTherInvalIdList;
	}

	public void setWashTherInvalIdList(List<WashTherInvalIdType> washTherInvalIdList) {
		this.washTherInvalIdList = washTherInvalIdList;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getCardIssuerName() {
		return cardIssuerName;
	}

	public void setCardIssuerName(String cardIssuerName) {
		this.cardIssuerName = cardIssuerName;
	}

	public WashCarActivityRecord getWashCarActivityRecord() {
		return washCarActivityRecord;
	}

	public void setWashCarActivityRecord(WashCarActivityRecord washCarActivityRecord) {
		this.washCarActivityRecord = washCarActivityRecord;
	}
	
}
