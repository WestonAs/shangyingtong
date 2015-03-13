package gnete.card.web.cardtypeset;

import flink.etc.MatchMode;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.CouponUsageType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 赠券子类型
 * @author aps-lib
 *
 */
public class CouponClassAction extends BaseAction {
	
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private MerchGroupDAO merchGroupDAO;
	
	private CouponClassDef couponClassDef;
	private Paginater page;
	private Collection jinstTypeList;
	private Collection couponUsageList;
	private List<BranchInfo> branchList;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	private List<MerchGroup> merchGroupList;
	private List<CouponClassDef> couponClassDefList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		/*String jinstId = this.getCardIssuer();
		params.put("jinstId", jinstId);*/
		
		if(couponClassDef!=null){
			params.put("className", MatchMode.ANYWHERE.toMatchString(couponClassDef.getClassName()));
		}
		
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		couponClassDefList = new ArrayList<CouponClassDef>();
		
		// 如果登录用户为运营中心，运营中心部门
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
			params.put("issId", getSessionUser().getBranchNo());
			couponClassDefList = couponClassDefDAO.findCouponClassList(params);
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)||CollectionUtils.isNotEmpty(couponClassDefList)) {
			int length = cardBranchList.size()+merchInfoList.size()+couponClassDefList.size();
			String[] jinstIds = new String[length];
			int j = 0;
			for(int i = 0 ; i<cardBranchList.size(); i++){
				jinstIds[j++] = (cardBranchList.get(i)).getBranchCode();
			}
			for(int i = 0 ; i<couponClassDefList.size(); i++){
				jinstIds[j++] = (couponClassDefList.get(i)).getJinstId();
			}
			for(int i = 0 ; i<merchInfoList.size(); i++){
				jinstIds[j++] = (merchInfoList.get(i)).getMerchId();
			}
			params.put("jinstIds", jinstIds);
		}
		
		this.page = couponClassDefDAO.findCouponClassDef(params, this.getPageNumber(), this.getPageSize());
		jinstTypeList = IssType.getAll();
		couponUsageList = CouponUsageType.getAll();
		return LIST;
	}
	
	public String detail() throws Exception {
		this.couponClassDef = (CouponClassDef)this.couponClassDefDAO.findByPk(couponClassDef.getCoupnClass());
		String msg = "查询赠券子类型[" + couponClassDef.getCoupnClass() + "明细]成功";
		this.log(msg, UserLogType.SEARCH);
		addActionMessage("/fee/couponClassDef/list.do", msg);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			params.put("cardIssuer", getSessionUser().getBranchNo());
		}else if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			params.put("cardIssuer", getSessionUser().getMerchantNo());
		}else{
			throw new BizException("非发卡机构或者商户不能操作。");
		}
		params.put("status", CommonState.NORMAL.getValue());
		merchGroupList = merchGroupDAO.findList(params);
		jinstTypeList = IssType.getCircleDefineType();
		branchList = this.getBranchinfoList();
		couponUsageList = CouponUsageType.getAll();
		return ADD;
	}
	
	public String add() throws Exception {
		UserInfo user = this.getSessionUser();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())){ //商户
			this.couponClassDef = setJinstByMerch(user, this.couponClassDef);
		} else if(roleInfo.getRoleType().equals(RoleType.CARD.getValue())){ //发卡机构 
			this.couponClassDef = setJinstByCard(user, this.couponClassDef);
		}
		else{
			throw new BizException("非发卡机构或者商户不能定义赠券类型。");
		}
		
		this.cardTypeSetService.addCouponClassDef(couponClassDef);	
		String msg = "添加赠券子类型[" + couponClassDef.getCoupnClass() + "]成功";
		this.log(msg, UserLogType.ADD);
		addActionMessage("/cardTypeSet/couponClass/list.do", msg);
		return SUCCESS;
	}
	
	/* 登录用户为商户 */
	private CouponClassDef setJinstByMerch(UserInfo user, CouponClassDef couponClassDef){
		couponClassDef.setIssType(IssType.CARD.getValue());
		couponClassDef.setJinstId(user.getMerchantNo());
		MerchInfo merchInfo = (MerchInfo)this.merchInfoDAO.findByPk(user.getMerchantNo());
		couponClassDef.setJinstName(merchInfo.getMerchName());
		couponClassDef.setJinstType(IssType.MERCHANT.getValue());
		return couponClassDef;
	}
	
	/* 登录用户为发卡机构 */
	private CouponClassDef setJinstByCard(UserInfo user, CouponClassDef couponClassDef){
		couponClassDef.setIssId(user.getBranchNo());
		couponClassDef.setIssType(IssType.CARD.getValue());
		
		/* 假如联名机构类型是通用（发卡机构），则联名机构编码是定义机构本身 */
		String jinstType = couponClassDef.getJinstType();
		if(CommonHelper.isNotEmpty(jinstType) && jinstType.equals(IssType.CARD.getValue())){//通用
			couponClassDef.setJinstType(IssType.CARD.getValue());
			couponClassDef.setJinstId(user.getBranchNo());
		}
		BranchInfo branchInfo = (BranchInfo)this.branchInfoDAO.findByPk(user.getBranchNo());
		couponClassDef.setJinstName(branchInfo.getBranchName());
		//}
		
		return couponClassDef;
	}

	public String showModify() throws Exception {
		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue())||roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue()))){ 
			throw new BizException("非发卡机构或者商户不能定义赠卡子类型。");
		}
		
		this.branchList = this.getBranchinfoList();
		this.couponClassDef = (CouponClassDef)this.couponClassDefDAO.findByPk(couponClassDef.getCoupnClass());
		jinstTypeList = IssType.getAll();
		couponUsageList = CouponUsageType.getAll();
		return MODIFY;
	}
	
	public String modify() throws Exception {
		UserInfo user = this.getSessionUser();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		if(roleInfo.getRoleType().equals(RoleType.MERCHANT.getValue())){ 
			couponClassDef.setIssType(IssType.CARD.getValue());
		}
		else if(roleInfo.getRoleType().equals(RoleType.CARD.getValue())){ 
			couponClassDef.setIssId(user.getBranchNo());
			couponClassDef.setIssType(IssType.CARD.getValue());
		}
		else{
			throw new BizException("非发卡机构或者商户不能修改赠券类型。");
		}
		
		this.cardTypeSetService.modifyCouponClassDef(this.couponClassDef);
		String msg = "修改赠券子类型[" + couponClassDef.getCoupnClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/couponClass/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		this.cardTypeSetService.deleteCouponClassDef(couponClassDef);
		String msg = "删除赠券子类型[" + couponClassDef.getCoupnClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage("/cardTypeSet/couponClass/list.do", msg);
		return SUCCESS;
	}
	
	// 查询和商户用户有特约关系的发卡机构列表，服务端查询，返回到前端
	private List<BranchInfo> getBranchinfoList() throws BizException{
		return this.getMyCardBranch();
	}
	
	/*private String getCardIssuer() throws BizException{
		UserInfo user = this.getSessionUser();
		String cardIssuer = null;
		
		if(user.getRole().getRoleType().equals(RoleType.CARD.getValue())){ //发卡机构
			cardIssuer = user.getBranchNo();
		}
		else if(user.getRole().getRoleType().equals(RoleType.MERCHANT.getValue())){ //商户
			cardIssuer = user.getMerchantNo();
		}
		else if(user.getRole().getRoleType().equals(RoleType.CENTER.getValue())){ //营运中心
			cardIssuer = "";
		}
		else{
			throw new BizException("用户角色不是营运中心、发卡机构或者商户,不允许进行操作");
		}
		return cardIssuer;
	}*/

	public CouponClassDef getCouponClassDef() {
		return couponClassDef;
	}

	public void setCouponClassDef(CouponClassDef couponClassDef) {
		this.couponClassDef = couponClassDef;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getJinstTypeList() {
		return jinstTypeList;
	}

	public void setJinstTypeList(Collection jinstTypeList) {
		this.jinstTypeList = jinstTypeList;
	}

	public Collection getCouponUsageList() {
		return couponUsageList;
	}

	public void setCouponUsageList(Collection couponUsageList) {
		this.couponUsageList = couponUsageList;
	}

	public List<BranchInfo> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BranchInfo> branchList) {
		this.branchList = branchList;
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

	public List<MerchGroup> getMerchGroupList() {
		return merchGroupList;
	}

	public void setMerchGroupList(List<MerchGroup> merchGroupList) {
		this.merchGroupList = merchGroupList;
	}

	public List<CouponClassDef> getCouponClassDefList() {
		return couponClassDefList;
	}

	public void setCouponClassDefList(List<CouponClassDef> couponClassDefList) {
		this.couponClassDefList = couponClassDefList;
	}

}
