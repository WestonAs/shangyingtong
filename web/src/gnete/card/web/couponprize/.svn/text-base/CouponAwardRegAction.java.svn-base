package gnete.card.web.couponprize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CouponAwardRegDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CouponAwardReg;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CouponRegService;
import gnete.card.tag.NameTag;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

/**
 * 赠券赠送
 * @author aps-lib
 *
 */
public class CouponAwardRegAction extends BaseAction{

	@Autowired
	private CouponAwardRegDAO couponAwardRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private CouponRegService couponRegService;
	
	private CouponAwardReg couponAwardReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	private Long couponAwardRegId;
	private List<CouponClassDef> couponClassDefList;
	
	private List<RegisterState> statusList;
	private List<IssType> insTypeList;
	private String cardBins;
	
	private boolean showMerch = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			this.cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			
			if(CollectionUtils.isEmpty(cardBranchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("insId", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("insId", this.getSessionUser().getBranchNo());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			params.put("insId", this.getSessionUser().getMerchantNo());
			this.showMerch = true;
		}
		// 其他
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = cardBranchList.size() + merchInfoList.size();
			String[] insIds = new String[length];
			int i = 0;
			for( ; i<cardBranchList.size(); i++){
				insIds[i] = (cardBranchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				insIds[i] = (merchInfoList.get(i-cardBranchList.size())).getMerchId();
			}
			params.put("insIds", insIds);
		}
		
		if(this.couponAwardReg != null){
			params.put("couponAwardRegId", this.couponAwardReg.getCouponAwardRegId()); 
			params.put("status", this.couponAwardReg.getStatus());
			params.put("insId", this.couponAwardReg.getInsId());
			params.put("insName", MatchMode.ANYWHERE.toMatchString(this.couponAwardReg.getInsName()));
			params.put("couponName", MatchMode.ANYWHERE.toMatchString(this.couponAwardReg.getCouponName()));
		}
		
		this.page = this.couponAwardRegDAO.findCouponAwardReg(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String showAdd() throws Exception {
		hasRightToDo();
		initPage();
		this.couponAwardReg = new CouponAwardReg();
		this.couponAwardReg.setInsId(this.getLoginBranchCode());
		
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){ //发卡机构
			this.couponAwardReg.setInsType(IssType.CARD.getValue());
			this.couponAwardReg.setInsName(NameTag.getBranchName(this.getSessionUser().getBranchNo()));
		} else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ //商户
			this.couponAwardReg.setInsType(IssType.MERCHANT.getValue());
			this.couponAwardReg.setInsName(NameTag.getMerchName(this.getSessionUser().getMerchantNo()));
		}
		
		this.couponClassDefList = this.getCouponClassDef();
		
		return ADD;
	}
	
	public String add() throws Exception {
		
		List<CouponAwardReg> couponAwardRegList = new ArrayList<CouponAwardReg>();
		
		CouponClassDef couponClassDef = (CouponClassDef) this.couponClassDefDAO.findByPk(this.couponAwardReg.getCouponClass());
		String couponClassName = couponClassDef.getClassName();
		
		if (StringUtils.isNotEmpty(cardBins)) { // 卡BIN非空
			String[] cardBinArray = cardBins.split(",");
			for (int i = 0; i < cardBinArray.length; i++) {
				CouponAwardReg couponAwardReg = new CouponAwardReg();
				couponAwardReg.setInsId(this.couponAwardReg.getInsId());
				couponAwardReg.setInsType(this.couponAwardReg.getInsType());
				couponAwardReg.setCouponClass(this.couponAwardReg.getCouponClass());
				couponAwardReg.setCouponName(couponClassName);
				couponAwardReg.setCardBin(cardBinArray[i]);
				couponAwardReg.setRemark(this.couponAwardReg.getRemark());
				couponAwardRegList.add(couponAwardReg);
			}
		} else { // 卡BIN为空
			CouponAwardReg couponAwardReg = new CouponAwardReg();
			couponAwardReg.setInsId(this.couponAwardReg.getInsId());
			couponAwardReg.setInsType(this.couponAwardReg.getInsType());
			couponAwardReg.setCouponClass(this.couponAwardReg.getCouponClass());
			couponAwardReg.setCouponName(couponClassName);
			couponAwardReg.setCardBin("*");
			couponAwardReg.setRemark(this.couponAwardReg.getRemark());
			couponAwardRegList.add(couponAwardReg);
		}
		
		this.couponRegService.addCouponAwardReg(couponAwardRegList.toArray(new CouponAwardReg[couponAwardRegList.size()]) , this.getSessionUser());	
		String msg = "新增[" + this.couponAwardReg.getInsId()+ "赠送赠券成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/couponAwardReg/list.do", msg);
		return SUCCESS;
	}
	
	// 删除
	public String delete() throws Exception {
		//this.retransCardRegService.deleteRetransCardReg(this.retransCardReg.getCardCustomerId(), this.retransCardReg.getBinNo());
		this.addActionMessage("/couponAwardReg/list.do", "删除赠送赠券记录成功！");
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] couponAwardRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_COUPON_AWARD_REG, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(couponAwardRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", couponAwardRegIds);
		this.page = this.couponAwardRegDAO.findCouponAwardReg(params, this.getPageNumber(), this.getPageSize());
		return CHECK_LIST;
	}
	
	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(couponAwardReg, "赠券赠送对象不能为空");	
		Assert.notNull(couponAwardReg.getCouponAwardRegId(), "赠券赠送登记ID不能为空");	
		
		// 赠券赠送登记簿明细
		this.couponAwardReg = (CouponAwardReg)this.couponAwardRegDAO.findByPk(couponAwardReg.getCouponAwardRegId());		
		
		return DETAIL;	
	}
	
	// 操作权限
	private void hasRightToDo() throws BizException {
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())
				&& !RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			throw new BizException("没有权限进行该操作。");
		} 
	}
	
	// 查询当前机构下可用的赠券子类型定义
	private List<CouponClassDef> getCouponClassDef() {
		Map<String, Object> couponClassDefMap = new HashMap<String, Object>();
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			couponClassDefMap.put("jinstId",  this.getSessionUser().getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			couponClassDefMap.put("jinstId",  this.getSessionUser().getMerchantNo());
		}
		return this.couponClassDefDAO.findCouponClassByJinst(couponClassDefMap);
	}
	
	private void initPage() {
		this.statusList = RegisterState.getAll();
		this.insTypeList = IssType.getAll();
	}

	public CouponAwardReg getCouponAwardReg() {
		return couponAwardReg;
	}

	public void setCouponAwardReg(CouponAwardReg couponAwardReg) {
		this.couponAwardReg = couponAwardReg;
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

	public List<IssType> getInsTypeList() {
		return insTypeList;
	}

	public void setInsTypeList(List<IssType> insTypeList) {
		this.insTypeList = insTypeList;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public Long getCouponAwardRegId() {
		return couponAwardRegId;
	}

	public void setCouponAwardRegId(Long couponAwardRegId) {
		this.couponAwardRegId = couponAwardRegId;
	}

	public String getCardBins() {
		return cardBins;
	}

	public void setCardBins(String cardBins) {
		this.cardBins = cardBins;
	}

	public List<CouponClassDef> getCouponClassDefList() {
		return couponClassDefList;
	}

	public void setCouponClassDefList(List<CouponClassDef> couponClassDefList) {
		this.couponClassDefList = couponClassDefList;
	}

}
