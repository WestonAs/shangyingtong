package gnete.card.web.cardtypeset;

import flink.util.CommonHelper;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.dao.MerchGroupPointCouponLimitDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchToGroupDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.MerchGroup;
import gnete.card.entity.MerchGroupPointCouponLimit;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchToGroup;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.flag.ConsumeFlag;
import gnete.card.entity.flag.SendFlag;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.LimitType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.tag.NameTag;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class MerchGroupPointCouponLimitAction extends BaseAction {

	@Autowired
	private MerchGroupPointCouponLimitDAO merchGroupPointCouponLimitDAO;
	@Autowired
	private MerchGroupDAO merchGroupDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private MerchToGroupDAO merchToGroupDAO;
	
	private MerchGroupPointCouponLimit merchGroupPointCouponLimit;
	
	private List<LimitType> limitTypeList;
	private List<SendFlag> sendFlagList;
	private List<ConsumeFlag> consumeFlagList;
	private List<MerchGroup> merchGroupList;
	private List<PointClassDef> pointClassDefList;
	private List<CouponClassDef> couponClassDefList;

	private String limitId;
	private String limitType;
	private String groupId;
	private String merchId;
	
//	private String merchName;
//	private String limitIdName;
//	private String groupIdName;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			params.put("cardIssuer", getSessionUser().getBranchNo());
			List<MerchGroup> merchGroupList= merchGroupDAO.findList(params);
			if(CommonHelper.checkIsEmpty(merchGroupList)){
				return LIST;
			}else{
				String[] groupIds = new String[merchGroupList.size()];
				int i=0;
				for(MerchGroup merchGroup : merchGroupList){
					groupIds[i++] =merchGroup.getGroupId();
				}
				params.put("groupIds", groupIds);
			}
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			params.put("merchId", getSessionUser().getMerchantNo());
		} else{
			throw new BizException("非商户或发卡机构禁止查看商圈积分赠券控制规则定义。");
		}
		if (merchGroupPointCouponLimit != null) {
			params.put("groupId",merchGroupPointCouponLimit.getGroupId());
			if (!getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())){
			    params.put("merchId",merchGroupPointCouponLimit.getMerchId());
			}

		}
		page = merchGroupPointCouponLimitDAO.findPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		Assert.notNull(merchGroupPointCouponLimit, "商圈积分赠券控制规则对象不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitId(), "商圈积分赠券控制规则对象ID不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitType(), "商圈积分赠券控制规则对象类型不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getGroupId(), "商圈积分赠券控制规则对象商圈不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getMerchId(), "商圈积分赠券控制规则对象商户号不能为空");
		merchGroupPointCouponLimit = (MerchGroupPointCouponLimit) merchGroupPointCouponLimitDAO.findByPk(merchGroupPointCouponLimit);
		return DETAIL;
	}


	/**
	 * 新增初始化页面
	 */
	public String showAdd() throws Exception {
		initPage();
		Map<String, Object> params = new HashMap<String, Object>();
		if(this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			params.put("cardIssuer", getSessionUser().getBranchNo());
			params.put("jinstType", IssType.CIRCLE.getValue());
			pointClassDefList = pointClassDefDAO.findPtClassByJinst(params);
			
			params.put("issId", getSessionUser().getBranchNo());
			couponClassDefList = couponClassDefDAO.findCouponClassList(params);
//			params.put("status", CommonState.NORMAL.getValue());
//			merchGroupList = merchGroupDAO.findList(params);
		}else if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			params.put("merchId", getSessionUser().getMerchantNo());
			List<MerchToGroup> merchToGroupList = merchToGroupDAO.findMerchToGroup(params);
			
			if(!CommonHelper.checkIsEmpty(merchToGroupList)){
				int i=0;
			    String[] merchGroupIds = new String[merchToGroupList.size()];
			    for(MerchToGroup merchToGroup : merchToGroupList){
			    	merchGroupIds[i++] = merchToGroup.getGroupId();
			    }
			    params.put("jinstIds",merchGroupIds);
			    pointClassDefList = pointClassDefDAO.findPontClassList(params);
			    
			    couponClassDefList = couponClassDefDAO.findCouponClassList(params);
			    
			    
//			    params.put("groupIds", merchGroupIds);
//			    params.put("status", CommonState.NORMAL.getValue());
//				merchGroupList = merchGroupDAO.findList(params);
			}else{
				throw new BizException("该商户没有商圈记录。");
			}
		}else{
			throw new BizException("非发卡机构或者商户不能操作。");
		}
		
		return ADD;
	}

	public boolean isExistDef() throws Exception {
		boolean isExistDef = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limitId", merchGroupPointCouponLimit.getLimitId());
		params.put("limitType", merchGroupPointCouponLimit.getLimitType());
		params.put("groupId", merchGroupPointCouponLimit.getGroupId());
		params.put("merchId", merchGroupPointCouponLimit.getMerchId());
		isExistDef = merchGroupPointCouponLimitDAO.findList(params).size() > 0 ? true: false;
		return isExistDef;
	}
	
	public void loadMerchGroupId() throws Exception {
		String classId = request.getParameter("classId");
		String limitType = request.getParameter("limitType");
		String groupId = null;
		if(limitType.equals(LimitType.POINT.getValue())){//积分
			PointClassDef pointClassDef = (PointClassDef)pointClassDefDAO.findByPk(classId);
			groupId = pointClassDef.getJinstId();
		}else if(limitType.equals(LimitType.COUPON.getValue())){//赠券
			CouponClassDef coupon = (CouponClassDef) couponClassDefDAO.findByPk(classId);
			groupId = coupon.getJinstId();
		}
		
		MerchGroup tmpMerchGroup = new MerchGroup();
		tmpMerchGroup.setGroupId(groupId);
		MerchGroup merchGroup = (MerchGroup)merchGroupDAO.findByPk(tmpMerchGroup);
		
		//构造返回
		JSONObject object = new JSONObject();
		object.put("groupId", merchGroup.getGroupId());
		object.put("groupName", merchGroup.getGroupName());
		this.respond(object.toString());
	}
	
	/**
	 * 新增
	 */
	public String add() throws Exception {
		Assert.notNull(merchGroupPointCouponLimit, "商圈积分赠券控制规则对象不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitId(), "商圈积分赠券控制规则对象ID不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitType(), "商圈积分赠券控制规则对象类型不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getGroupId(), "商圈积分赠券控制规则对象商圈不能为空");
		if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			merchGroupPointCouponLimit.setMerchId(getSessionUser().getMerchantNo());
		}else{
		    Assert.notEmpty(merchGroupPointCouponLimit.getMerchId(), "商圈积分赠券控制规则对象商户号不能为空");
		}
		
		Assert.notTrue(isExistDef(), "商户["+merchGroupPointCouponLimit.getLimitId()+","+merchGroupPointCouponLimit.getMerchId()+"]商圈积分赠券控制规则已存在!");
		merchGroupPointCouponLimit.setInsertTime(new Date());
		merchGroupPointCouponLimit.setUpdateBy(getSessionUser().getUserId());
		merchGroupPointCouponLimit.setStatus(CommonState.NORMAL.getValue());
		merchGroupPointCouponLimitDAO.insert(merchGroupPointCouponLimit);
		
		String msg = LogUtils.r("商圈积分赠券控制规则ID为[{0},{1}]定义已经提交。", merchGroupPointCouponLimit.getLimitId(), merchGroupPointCouponLimit.getMerchId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardTypeSet/pointCouponsRule/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		initPage();
		if(this.getLoginRoleType().equals(RoleType.CARD.getValue())){
			
		}else if(this.getLoginRoleType().equals(RoleType.MERCHANT.getValue())){ 
			
		}else{
			throw new BizException("非发卡机构或者商户不能操作。");
		}
		limitId = merchGroupPointCouponLimit.getLimitId();
		groupId = merchGroupPointCouponLimit.getGroupId();
		merchGroupPointCouponLimit = (MerchGroupPointCouponLimit) merchGroupPointCouponLimitDAO.findByPk(merchGroupPointCouponLimit);
		if(null == merchGroupPointCouponLimit){
			throw new BizException("要修改的商圈积分赠券控制规则["+limitId+","+groupId+"]不存在。");
		}
		if(CommonHelper.isEmpty(merchGroupPointCouponLimit.getPtName())){
			merchGroupPointCouponLimit.setPtName(merchGroupPointCouponLimit.getCoupnName());
		}
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		Assert.notNull(merchGroupPointCouponLimit, "商圈积分赠券控制规则对象不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitId(), "商圈积分赠券控制规则对象ID不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getLimitType(), "商圈积分赠券控制规则对象类型不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getGroupId(), "商圈积分赠券控制规则对象商圈不能为空");
		Assert.notEmpty(merchGroupPointCouponLimit.getMerchId(), "商圈积分赠券控制规则对象商户号不能为空");
		
		merchGroupPointCouponLimit.setUpdateBy(getSessionUser().getUserId());
		merchGroupPointCouponLimit.setUpdateTime(new Date());
		merchGroupPointCouponLimitDAO.update(merchGroupPointCouponLimit);
		String msg = LogUtils.r("修改商圈积分赠券控制规则[{0},{1}]成功。", merchGroupPointCouponLimit.getLimitId(), merchGroupPointCouponLimit.getMerchId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/cardTypeSet/pointCouponsRule/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		Assert.notEmpty(limitId, "商圈积分赠券控制规则对象ID不能为空");
		Assert.notEmpty(limitType, "商圈积分赠券控制规则对象类型不能为空");
		Assert.notEmpty(groupId, "商圈积分赠券控制规则对象商圈不能为空");
		Assert.notEmpty(merchId, "商圈积分赠券控制规则对象商户号不能为空");
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || 
				getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作。");
		}
		merchGroupPointCouponLimit = new MerchGroupPointCouponLimit();
		merchGroupPointCouponLimit.setLimitId(limitId);
		merchGroupPointCouponLimit.setLimitType(limitType);
		merchGroupPointCouponLimit.setGroupId(groupId);
		merchGroupPointCouponLimit.setMerchId(merchId);
		boolean isSuccess = merchGroupPointCouponLimitDAO.delete(merchGroupPointCouponLimit)>0? true:false;
		if (isSuccess) {
			String msg = LogUtils.r("删除商圈积分赠券控制规则[{0},{1}]成功。", merchGroupPointCouponLimit.getLimitId(), merchGroupPointCouponLimit.getMerchId());
			log(msg, UserLogType.DELETE);
			addActionMessage("/cardTypeSet/pointCouponsRule/list.do", msg);
		}
		return SUCCESS;
	}
	
	private void initPage() {
		limitTypeList = LimitType.getAll();
		sendFlagList = SendFlag.getAll();
		consumeFlagList = ConsumeFlag.getAll();
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public List<LimitType> getLimitTypeList() {
		return limitTypeList;
	}

	public void setLimitTypeList(List<LimitType> limitTypeList) {
		this.limitTypeList = limitTypeList;
	}

	public List<SendFlag> getSendFlagList() {
		return sendFlagList;
	}

	public void setSendFlagList(List<SendFlag> sendFlagList) {
		this.sendFlagList = sendFlagList;
	}

	public List<ConsumeFlag> getConsumeFlagList() {
		return consumeFlagList;
	}

	public void setConsumeFlagList(List<ConsumeFlag> consumeFlagList) {
		this.consumeFlagList = consumeFlagList;
	}

	public List<MerchGroup> getMerchGroupList() {
		return merchGroupList;
	}

	public void setMerchGroupList(List<MerchGroup> merchGroupList) {
		this.merchGroupList = merchGroupList;
	}

	public List<PointClassDef> getPointClassDefList() {
		return pointClassDefList;
	}

	public void setPointClassDefList(List<PointClassDef> pointClassDefList) {
		this.pointClassDefList = pointClassDefList;
	}

	public List<CouponClassDef> getCouponClassDefList() {
		return couponClassDefList;
	}

	public void setCouponClassDefList(List<CouponClassDef> couponClassDefList) {
		this.couponClassDefList = couponClassDefList;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	
//	public String getMerchName() {
//		return merchName;
//	}
//
//	public void setMerchName(String merchName) {
//		this.merchName = merchName;
//	}
//
//	public String getLimitIdName() {
//		return limitIdName;
//	}
//
//	public void setLimitIdName(String limitIdName) {
//		this.limitIdName = limitIdName;
//	}
//
//	public String getGroupIdName() {
//		return groupIdName;
//	}
//
//	public void setGroupIdName(String groupIdName) {
//		this.groupIdName = groupIdName;
//	}

	public MerchGroupPointCouponLimit getMerchGroupPointCouponLimit() {
		return merchGroupPointCouponLimit;
	}

	public void setMerchGroupPointCouponLimit(MerchGroupPointCouponLimit merchGroupPointCouponLimit) {
		this.merchGroupPointCouponLimit = merchGroupPointCouponLimit;
	}

	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}
}
