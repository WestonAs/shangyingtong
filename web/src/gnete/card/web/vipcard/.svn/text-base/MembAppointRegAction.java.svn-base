package gnete.card.web.vipcard;

import flink.util.CommonHelper;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.MembAppointDetailRegDAO;
import gnete.card.dao.MembAppointRegDAO;
import gnete.card.dao.MembInfoRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MembAppointReg;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembInfoReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.MembImportType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SingleBatchType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VipCardService;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MembAppointRegAction extends BaseAction {
	
	@Autowired
	private MembAppointRegDAO membAppointRegDAO;
	@Autowired
	private MembAppointDetailRegDAO membAppointDetailRegDAO;
	@Autowired
	private MembInfoRegDAO membInfoRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private VipCardService vipCardService;
	
	private MembAppointReg membAppointReg;
	private MembInfoReg membInfoReg;

	/** 会员类型 */
	private List<MembClassDef> membClassDefList; 
	
	/** 会员资料批次列表 */
	private List<MembInfoReg> membInfoIdList;
	
	/** 明细列表 */
	Paginater membAppointDetailRegPage;
	
	private boolean showMembDetail;
	
	//删除使用,指定添加
    private String cardIssuer;
    
	//新增使用
    private String cardCount;
    private List<MembImportType> membImportTypeList;
    private String importType;
    private String membInfoIds;

	private Paginater page;
	
	private static final String LIST_URL = "/vipCard/membAppointReg/list.do";

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (membAppointReg != null) {
			params.put("cardIssuer", membAppointReg.getCardIssuer());
			params.put("cardId", membAppointReg.getCardId());
			params.put("membInfoRegId", membAppointReg.getMembInfoRegId());
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			params.put("cardIssuer", getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.membAppointRegDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	
	/** 
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		if(!(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType()))){ 
			throw new BizException("非发卡机构不能操作。");
		}
		cardIssuer =getSessionUser().getBranchNo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", cardIssuer);
		membInfoIdList = membInfoRegDAO.findMembInfoIdList(params);
		
		membClassDefList = vipCardService.loadMtClass(cardIssuer);
		
		membImportTypeList = MembImportType.getAll();
		
		return ADD;
	}
	
	/** 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		Assert.notNull(membAppointReg, "会员资料关联信息不能为空");
		Assert.notEmpty(membAppointReg.getStartCardId(), "开始卡号不能为空");
		Assert.notEmpty(cardCount, "卡连续数不能为空");
		Assert.notTrue(!NumberUtils.isDigits(cardCount),"卡连续张数必须为正整数");
		
		Assert.notEmpty(importType, "会员录入方式不能为空");
		List<MembInfoReg>membInfoRegList =null;
		Map<String,Object> params = new HashMap<String, Object>();
		if(MembImportType.BATCH.getValue().equals(importType)){
			Assert.notNull(membInfoReg, "会员资料不能为空");
			Assert.notNull(membInfoReg.getMembInfoId(), "会员资料批次ID不能为空");
			params.put("membInfoId",membInfoReg.getMembInfoId());
			membAppointReg.setSaleBatchId(membInfoReg.getMembInfoId());
			membInfoRegList = membInfoRegDAO.findList(params);
		}else{
			Assert.notEmpty(membInfoIds, "会员资料登记ID不能为空");
			params.put("ids",membInfoIds.split("\\,"));
			membInfoRegList = membInfoRegDAO.findList(params);
		}
		
		if(membInfoRegList.size() != Integer.valueOf(cardCount)){
			throw new BizException("卡数["+cardCount+"]和批次会员数["+membInfoRegList.size()+"]不相等");
		}
		
		membAppointReg.setCardIssuer(getSessionUser().getBranchNo());
		membAppointReg.setUpdateTime(new Date());
		membAppointReg.setUpdateBy(getSessionUser().getUserId());
		membAppointReg.setStatus(RegisterState.WAITEDEAL.getValue());
		membAppointReg.setAppointType(SingleBatchType.BATCH.getValue());//统一成批量处理
		Long waitsInfoId = vipCardService.addMembAppointRegBat(membAppointReg, membInfoRegList, getSessionUser());
		String msg = LogUtils.r("批量新增会员资料关联[{0}]成功", waitsInfoId);
//		if(Integer.valueOf(cardCount) > 1){//卡数大于1为批量
//		}else{
//			membAppointReg.setAppointType(SingleBatchType.SINGLE.getValue());
//			membAppointReg.setMembInfoRegId(membInfoRegList.get(0).getMembInfoRegId());
//		    membAppointRegDAO.insert(membAppointReg);
//		    msg = LogUtils.r("新增会员资料关联[{0}]成功", membAppointReg.getMembAppointRegId());
//		}
		
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 明细
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Assert.notNull(membAppointReg, "会员资料关联信息不能为空");
		Assert.notNull(membAppointReg.getMembAppointRegId(), "会员资料关联ID不能为空");
		
		membAppointReg = (MembAppointReg)this.membAppointRegDAO.findByPk(membAppointReg);
		
		if(SingleBatchType.SINGLE.getValue().equals(membAppointReg.getAppointType())){
			showMembDetail = false;
		}else {
			showMembDetail = true;
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("membAppointRegId", membAppointReg.getMembAppointRegId());
			membAppointDetailRegPage = membAppointDetailRegDAO.findPage(params, getPageNumber(), getPageSize());
		}
		
		return DETAIL;
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		if(!(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType()))){ 
			throw new BizException("非发卡机构不能操作。");
		}
		Assert.notNull(membAppointReg, "会员资料关联信息不能为空");
		Assert.notNull(membAppointReg.getMembAppointRegId(), "会员资料关联ID不能为空");
		
		MembAppointReg tmpMembAppointReg  = (MembAppointReg)membAppointRegDAO.findByPk(membAppointReg);
		
		if(null != tmpMembAppointReg){
		    this.membAppointRegDAO.delete(membAppointReg);
		    
		    if(SingleBatchType.BATCH.getValue().equals(tmpMembAppointReg.getAppointType())){//删除批量明细
		    	membAppointDetailRegDAO.deleteByMembAppointRegId(tmpMembAppointReg.getMembAppointRegId());
		    }
		}
		
		String msg = LogUtils.r("删除会员资料关联卡号[{0}]]成功", membAppointReg.getMembAppointRegId());
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	// 服务端检索卡相关信息，返回给客户端
    public void searchCardInfo(){
    	String cardId = request.getParameter("cardId");
    	JSONObject object = new JSONObject();
    	if (CommonHelper.isEmpty(cardId)) {return;}
    	CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
    	if(cardInfo == null){
    		object.put("resultMessage", "卡号[" + cardId + "]不存在，请重新录入卡号！");
    		this.respond(object.toString());
    		return;
    	}
    	
    	String resultAcctId = cardInfo.getAcctId(); 
    	if (StringUtils.isEmpty(resultAcctId)) {
    		object.put("resultMessage", "卡号[" + cardId + "]的账户不存在，请重新录入卡号！");
    		this.respond(object.toString());
    		return;
		}
    	
    	// 如果卡数量不为空的话，则是批量充值
    	String cardCount = request.getParameter("cardCount");
    	if (StringUtils.isNotBlank(cardCount)) {
			if (!NumberUtils.isDigits(cardCount)){
				object.put("resultMessage", "卡连续张数必须为正整数");
	    		this.respond(object.toString());
				return;
			} else {
				String resultEndCardId = CardUtil.getEndCard(cardId, NumberUtils.toInt(cardCount));
				object.put("resultEndCardId", StringUtils.isBlank(resultEndCardId) ? StringUtils.EMPTY : resultEndCardId);
				this.respond(object.toString());
				return;
			}
		}
    }
    
    public String showSelect() throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	cardIssuer = request.getParameter("hiddenCardIssuer");
		params.put("cardIssuer", cardIssuer);
		membInfoIdList = membInfoRegDAO.findMembInfoIdList(params);
    	
		
		return "select";
	}

	/**
	 * 会员选择
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (membInfoReg != null) {
			params.put("membInfoRegId", membInfoReg.getMembInfoRegId());
			params.put("custName", membInfoReg.getCustName());
			params.put("membInfoId", membInfoReg.getMembInfoId());
			params.put("cardIssuer", membInfoReg.getCardIssuer());
		}
		page = membInfoRegDAO.findPage(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		
		return "data";
	}
	
	public MembAppointReg getMembAppointReg() {
		return membAppointReg;
	}

	public void setMembAppointReg(MembAppointReg membAppointReg) {
		this.membAppointReg = membAppointReg;
	}
	
	public MembInfoReg getMembInfoReg() {
		return membInfoReg;
	}

	public void setMembInfoReg(MembInfoReg membInfoReg) {
		this.membInfoReg = membInfoReg;
	}
	
	public List<MembClassDef> getMembClassDefList() {
		return membClassDefList;
	}

	public void setMembClassDefList(List<MembClassDef> membClassDefList) {
		this.membClassDefList = membClassDefList;
	}

	public List<MembInfoReg> getMembInfoIdList() {
		return membInfoIdList;
	}

	public void setMembInfoIdList(List<MembInfoReg> membInfoIdList) {
		this.membInfoIdList = membInfoIdList;
	}

	 public String getCardCount() {
		return cardCount;
	}

	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}

	public Paginater getMembAppointDetailRegPage() {
		return membAppointDetailRegPage;
	}


	public void setMembAppointDetailRegPage(Paginater membAppointDetailRegPage) {
		this.membAppointDetailRegPage = membAppointDetailRegPage;
	}

	public boolean isShowMembDetail() {
		return showMembDetail;
	}


	public void setShowMembDetail(boolean showMembDetail) {
		this.showMembDetail = showMembDetail;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}
	
	public List<MembImportType> getMembImportTypeList() {
		return membImportTypeList;
	}

	public void setMembImportTypeList(List<MembImportType> membImportTypeList) {
		this.membImportTypeList = membImportTypeList;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public String getMembInfoIds() {
		return membInfoIds;
	}

	public void setMembInfoIds(String membInfoIds) {
		this.membInfoIds = membInfoIds;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
