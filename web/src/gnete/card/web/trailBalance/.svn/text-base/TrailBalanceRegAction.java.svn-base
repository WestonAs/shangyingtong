package gnete.card.web.trailBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.TrailBalanceRegDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.TrailBalanceReg;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TrailType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.TrailBalanceService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * 手动试算平衡
 * @author aps-lib
 *
 */
public class TrailBalanceRegAction extends BaseAction{
	
	@Autowired
	private TrailBalanceRegDAO trailBalanceRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private TrailBalanceService trailBalanceService;
	
	private TrailBalanceReg trailBalanceReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	private List<MerchInfo> merchList;
	private List<BranchInfo> trailTypeList;
	private List<AccuClassDef> accuClassDefList;
	private List<CouponClassDef> couponClassDefList;
	private List<PointClassDef> pointClassDefList;
	private String className;
	private boolean showCenter = false;
	private boolean showCard = false;
	private boolean showMerch = false;
	private boolean showClass = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.trailTypeList = TrailType.getAll();
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
			showMerch = false;
		}
		// 运营分支机构,可以查看其管理的发卡机构发行的卡
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(branchInfoDAO
					.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			showMerch = false;
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
			showMerch = false;
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo) this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
			merchList = new ArrayList<MerchInfo>();
			merchList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
			showMerch = true;
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = cardBranchList.size()+merchInfoList.size();
			String[] issIds = new String[length];
			int i = 0;
			for( ; i<cardBranchList.size(); i++){
				issIds[i] = (cardBranchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				issIds[i] = (merchInfoList.get(i-cardBranchList.size())).getMerchId();
			}
			params.put("issIds", issIds);
		}
		
		if (trailBalanceReg != null){
			params.put("id", trailBalanceReg.getId());
			params.put("issId", MatchMode.ANYWHERE.toMatchString(trailBalanceReg.getIssId()));
			params.put("trailType",trailBalanceReg.getTrailType());
			params.put("settDate",trailBalanceReg.getSettDate());
		}
		
		this.page = this.trailBalanceRegDAO.findTrailBalanceReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.trailBalanceReg = (TrailBalanceReg) this.trailBalanceRegDAO.findByPk(this.trailBalanceReg.getId());
		//积分帐户
		if(TrailType.POINT_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = pointClassDef.getClassName();
		}
		//赠券帐户
		else if(TrailType.COUPON_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = couponClassDef.getClassName();
		}
		//次卡账户
		else if(TrailType.TIME_CARD_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = accuClassDef.getClassName();
		}
		this.log("查询试算平衡登记ID[" + this.trailBalanceReg.getId() + "]明细信息成功",
				UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		// 营运中心或中心部门
		/*if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCenter = true;
			this.showCard = false;
			this.showMerch = false;
			this.trailTypeList = TrailType.getAll();
		}*/
		// 发卡机构或者机构网点
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCard = true;
			this.showCenter = false;
			this.showMerch = false;
			this.trailTypeList = TrailType.getAll();
			this.trailBalanceReg = new TrailBalanceReg();
			this.trailBalanceReg.setIssId(this.getSessionUser().getBranchNo());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			this.showCenter = false;
			this.showCard = false;
			this.showMerch = true;
			this.trailTypeList = TrailType.getMerchType();
			this.trailBalanceReg = new TrailBalanceReg();
			this.trailBalanceReg.setIssId(this.getSessionUser().getMerchantNo());
		} else {
			throw new BizException("没有权限新增试算平衡登记。");
		}
		
		return ADD;
	}
	
	public String delete() throws Exception {
		this.trailBalanceService.deleteTrailBalanceReg(this.trailBalanceReg.getId());
		String msg = "删除试算平衡登记ID[" +this.trailBalanceReg.getId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/trailBalance/trailBalanceReg/list.do", msg);
		return SUCCESS;
	}
	
	// 新增试算平衡信息
	public String add() throws Exception {		
		
		//保存数据
		this.trailBalanceService.addTrailBalanceReg(trailBalanceReg, this.getSessionUserCode());
		
		//启动单个流程（后续错误，但数据已提交）
		this.workflowService.startFlow(trailBalanceReg, "trailBalanceAdapter",
				Long.toString(trailBalanceReg.getId()), this.getSessionUser());
		
		String msg = "新增试算平衡ID["+this.trailBalanceReg.getId()+"]成功！";
		this.addActionMessage("/trailBalance/trailBalanceReg/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 查询登录机构和次卡试算类型得到可用的次卡子类型列表，服务端查询，返回到前端
	public String getAccuClassList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String issId = this.trailBalanceReg.getIssId();
		String trailType = this.trailBalanceReg.getTrailType();
		
		if(TrailType.TIME_CARD_ACCT.getValue().equals(trailType)){
			this.showClass = true;
			params.put("cardIssuer", issId);
			this.accuClassDefList = this.accuClassDefDAO.findAccuClassList(params);
		}
		else {
			return null;
		}
		return "accuClassList";
	}
	
	// 查询登录机构和赠券试算类型得到可用的赠券子类型列表，服务端查询，返回到前端
	public String getCouponClassList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String issId = this.trailBalanceReg.getIssId();
		String trailType = this.trailBalanceReg.getTrailType();
		
		//赠券账户
		if(TrailType.COUPON_ACCT.getValue().equals(trailType)){
			this.showClass = true;
			params.put("jinstId", issId);
			this.couponClassDefList = this.couponClassDefDAO.findCouponClassList(params);
		}
		else {
			return null;
		}
		return "couponClassList";
	}
	
	// 查询登录机构和积分试算类型得到可用的积分子类型列表，服务端查询，返回到前端
	public String getPointClassList() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		String issId = this.trailBalanceReg.getIssId();
		String trailType = this.trailBalanceReg.getTrailType();
		
		//积分账户
		if(TrailType.POINT_ACCT.getValue().equals(trailType)){
			this.showClass = true;
			params.put("jinst", issId);
			this.pointClassDefList = this.pointClassDefDAO.findPontClassList(params);
		}
		else {
			return null;
		}
		return "pointClassList";
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] trailBalanceRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_TRAIL_BALANCE_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(trailBalanceRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", trailBalanceRegIds);
		this.page = this.trailBalanceRegDAO.findTrailBalanceReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(trailBalanceReg, "试算平衡对象不能为空");	
		Assert.notNull(trailBalanceReg.getId(), "试算平衡对象ID不能为空");	
		
		// 试算平衡登记簿明细
		this.trailBalanceReg = (TrailBalanceReg)this.trailBalanceRegDAO.findByPk(trailBalanceReg.getId());	
		
		//积分帐户
		if(TrailType.POINT_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			PointClassDef pointClassDef = (PointClassDef) this.pointClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = pointClassDef.getClassName();
		}
		//赠券帐户
		else if(TrailType.COUPON_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = couponClassDef.getClassName();
		}
		//次卡账户
		else if(TrailType.TIME_CARD_ACCT.getValue().equals(this.trailBalanceReg.getTrailType())){
			AccuClassDef accuClassDef = (AccuClassDef) this.accuClassDefDAO.findByPk(this.trailBalanceReg.getClassId());
			this.className = accuClassDef.getClassName();
		}
		
		return DETAIL;	
	}

	public TrailBalanceReg getTrailBalanceReg() {
		return trailBalanceReg;
	}

	public void setTrailBalanceReg(TrailBalanceReg trailBalanceReg) {
		this.trailBalanceReg = trailBalanceReg;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<BranchInfo> getTrailTypeList() {
		return trailTypeList;
	}

	public void setTrailTypeList(List<BranchInfo> trailTypeList) {
		this.trailTypeList = trailTypeList;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public boolean isShowClass() {
		return showClass;
	}

	public void setShowClass(boolean showClass) {
		this.showClass = showClass;
	}

	public List<AccuClassDef> getAccuClassDefList() {
		return accuClassDefList;
	}

	public void setAccuClassDefList(List<AccuClassDef> accuClassDefList) {
		this.accuClassDefList = accuClassDefList;
	}

	public List<CouponClassDef> getCouponClassDefList() {
		return couponClassDefList;
	}

	public void setCouponClassDefList(List<CouponClassDef> couponClassDefList) {
		this.couponClassDefList = couponClassDefList;
	}

	public List<PointClassDef> getPointClassDefList() {
		return pointClassDefList;
	}

	public void setPointClassDefList(List<PointClassDef> pointClassDefList) {
		this.pointClassDefList = pointClassDefList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

	public List<MerchInfo> getMerchList() {
		return merchList;
	}

	public void setMerchList(List<MerchInfo> merchList) {
		this.merchList = merchList;
	}

}
