package gnete.card.web.gift;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.GiftDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.GiftDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.state.GiftDefState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PtExchgType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SettMthdType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.GiftService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 礼品定义
 * @author aps-lib
 *
 */
public class GiftAction extends BaseAction {

	@Autowired
	private GiftDefDAO giftDefDAO;
	@Autowired
	private GiftService giftService;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	
	private GiftDef giftDef;
	private Paginater page;
	private Collection jinstTypeList; // 联名机构类型
	private Collection settMthdTypeList; // 清算方法
	private Collection ptExchgTypeList; // 积分兑换类型列表
	private Collection statusList; // 审核状态
	private List<PointClassDef> pointClassDefList;
	private String giftId;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		this.getMyCardBranch();
		
		//加载状态列表
		this.statusList = GiftDefState.ALL.values();		
		
		if (giftDef != null) {
			params.put("giftId", giftDef.getGiftId());
			params.put("className", MatchMode.ANYWHERE.toMatchString(giftDef.getPtClassName()));
			params.put("giftName", MatchMode.ANYWHERE.toMatchString(giftDef.getGiftName()));
		}
		
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门运营分支机构时
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if(CollectionUtils.isEmpty(cardBranchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("jinstId", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			cardBranchList.addAll(this.getMyCardBranch());
		}
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = cardBranchList.size()+merchInfoList.size();
			String[] jinstIds = new String[length];
			int i = 0;
			for( ; i<cardBranchList.size(); i++){
				jinstIds[i] = (cardBranchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				jinstIds[i] = (merchInfoList.get(i-cardBranchList.size())).getMerchId();
			}
			params.put("jinstIds", jinstIds);
		}
		
		this.page = this.giftDefDAO.findGift(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得礼品定义申请的明细
	public String detail() throws Exception {
		
		this.giftDef = (GiftDef) this.giftDefDAO.findByPk(this.giftDef.getGiftId());
		
		this.log("查询礼品定义["+this.giftDef.getGiftId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&
				!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机或者商户不能操作。");
		}
		initPage();
		this.pointClassDefList = this.getPtClassList();
		return ADD;
	}	
	
	// 新增礼品定义
	public String add() throws Exception {			
		
		//判断登录用户的机构类型并保存数据
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(RoleType.CARD.getValue().equals(roleInfo.getRoleType())){ 
			giftDef.setJinstType(IssType.CARD.getValue());
			giftDef.setJinstId(roleInfo.getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(roleInfo.getRoleType())){
			giftDef.setJinstType(IssType.MERCHANT.getValue());
			giftDef.setJinstId(roleInfo.getMerchantNo());
		}
		else{
			
		}
		this.giftService.addGiftDef(giftDef);
		
		//启动单个流程
		this.workflowService.startFlow(giftDef, "giftAdapter", giftDef.getGiftId(), this.getSessionUser());
		
		String msg = "定义礼品["+this.giftDef.getGiftId()+"]申请成功！";
		this.addActionMessage("/gift/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue())||
				roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()))){ 
			throw new BizException("非发卡机或者商户不能操作。");
		}
		initPage();
		this.pointClassDefList = this.getPtClassList();
		this.giftDef = (GiftDef)this.giftDefDAO.findByPk(this.giftDef.getGiftId());
			
		return MODIFY;
	}
	
	// 修改礼品信息
	public String modify() throws Exception {
		
		this.giftService.modifyGift(this.giftDef);
		
		this.addActionMessage("/gift/list.do", "修改礼品定义成功！");	
		return SUCCESS;
	}
	
	// 删除礼品定义
	public String delete() throws Exception {
		
		this.giftService.delete(this.getGiftId());
		
		String msg = "删除礼品[" +this.getGiftId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/gift/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		
		//加载联名机构类型做为下拉列表
		this.jinstTypeList = IssType.getAll();
		//加载清算方法做为下拉列表
		this.settMthdTypeList = SettMthdType.getAll();
		//加载积分兑换类型下拉列表
		this.ptExchgTypeList = PtExchgType.getAll();
	}

	// 礼品定义审核列表
	public String giftCheckList() throws Exception{
		
		// 首先调用流程引擎，得到我的待审批的工单ID
		String[] giftDefIds = this.workflowService.getMyJob(Constants.WORKFLOW_GIFT_DEF, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(giftDefIds)) {
			return "giftCheckList";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", giftDefIds);
		this.page = this.giftDefDAO.findGift(params, this.getPageNumber(), this.getPageSize());
		return "giftCheckList";
	}

	//取得礼品定义申请的明细，流程审核人查看的
	public String giftCheckDetail() throws Exception {
		
		this.giftDef = (GiftDef) this.giftDefDAO.findByPk(this.giftDef.getGiftId());
		
		this.log("查询礼品定义["+this.giftDef.getGiftId()+"]审核明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	//判断礼品代码是否已经存在
	public void isExistGift() throws Exception{
		//获取页面的giftId
		String giftId = this.giftDef.getGiftId();
		boolean isExist = this.giftService.isExistGift(giftId);
		this.respond("{'isExist':"+ isExist +"}");
	}
	
	//  查询登录用户定义的积分类型列表，服务端查询，返回到前端
	private List<PointClassDef> getPtClassList() throws BizException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(roleInfo.getRoleType().equals(RoleType.CARD.getValue())){
			params.put("jinstId", roleInfo.getBranchNo());
		}
		else if(roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())){
			params.put("jinstId", roleInfo.getMerchantNo());
		}
		else{
		}
		
		return this.pointClassDefDAO.findPtClassByJinst(params);
	}
	
	public Paginater getPage() {
		return page;
	}
	
	public void setPage(Paginater page) {
		this.page = page;
	}

	public GiftDef getGiftDef() {
		return giftDef;
	}

	public void setGiftDef(GiftDef giftDef) {
		this.giftDef = giftDef;
	}

	public Collection getJinstTypeList() {
		return jinstTypeList;
	}

	public void setJinstTypeList(Collection jinstTypeList) {
		this.jinstTypeList = jinstTypeList;
	}

	public Collection getSettMthdTypeList() {
		return settMthdTypeList;
	}

	public void setSettMthdTypeList(Collection settMthdTypeList) {
		this.settMthdTypeList = settMthdTypeList;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getGiftId() {
		return giftId;
	}

	public Collection getPtExchgTypeList() {
		return ptExchgTypeList;
	}

	public void setPtExchgTypeList(Collection ptExchgTypeList) {
		this.ptExchgTypeList = ptExchgTypeList;
	}

	public void setPointClassDefList(List<PointClassDef> pointClassDefList) {
		this.pointClassDefList = pointClassDefList;
	}

	public List<PointClassDef> getPointClassDefList() {
		return pointClassDefList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}


}
