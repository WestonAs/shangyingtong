package gnete.card.web.lottery;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.DrawDefDAO;
import gnete.card.dao.DrawInfoRegDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.CardBin;
import gnete.card.entity.DrawDef;
import gnete.card.entity.DrawInfoReg;
import gnete.card.entity.DrawInfoRegKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.DrawDefineState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.DrawType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.ProbType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LotteryService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: DrawDefineAction.java
 * 
 * @description: 抽奖活动定义相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public class DrawDefineAction extends BaseAction {

	@Autowired
	private DrawDefDAO drawDefDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private DrawInfoRegDAO drawInfoRegDAO;
	
	/** 机构类型代码 */
	private List issTypeList;
	/** 抽奖方法类型 */
	private List drawTypeList;
	/** 机构类型代码 */
	private List pInstTypeList;
	/** 抽奖相关交易类型 */
	private List transTypeList;
	/** 金额类型 */
	private List amtTypeList;
	/** 概率折算方法 */
	private List probTypeList;
    /** 抽奖定义 */
	private DrawDef drawDef;
	/** 抽奖用户信息 */
	private DrawInfoReg drawInfoReg;
	
	private String drawStatus;//新增用户按钮显示控制
	private boolean showBrushSet; 

	private Paginater page;

	// 卡bin范围
	private String cardBinScope_sel;
	private String pinstId_sel;
	private String drawId;
	private String jionDrawId;
	
	@Override
	public String execute() throws Exception {
		drawTypeList = DrawType.getAll();
//		transTypeList = TransType.getAll();
		transTypeList = TransType.getDrawType();

		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			params.put("issId", getSessionUser().getBranchNo());
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			params.put("issId", getSessionUser().getMerchantNo());
		} else{
			throw new BizException("非商户或发卡机构禁止查看抽奖活动定义。");
		}
		if (drawDef != null) {
//			params.put("drawId", MatchMode.ANYWHERE.toMatchString(drawDef.getDrawId()));
			params.put("drawName", MatchMode.ANYWHERE.toMatchString(drawDef	.getDrawName()));
			params.put("pinstId", MatchMode.ANYWHERE.toMatchString(drawDef.getPinstId()));
			params.put("drawType", drawDef.getDrawType());
			params.put("transType", drawDef.getTransType());
		}
		page = drawDefDAO.findDrawDefPage(params, getPageNumber(),getPageSize());
		return LIST;
	}

	/**
	 * 列表明细
	 */
	public String detail() throws Exception {
		showBrushSet = false;
		drawDef = (DrawDef) drawDefDAO.findByPk(drawDef.getDrawId());
		if(DrawType.BRUSH_ISIN.getValue().equals(drawDef.getDrawType())){//即刷即中
			showBrushSet = true;
		}
		return DETAIL;
	}


	/**
	 * 抽奖活动定义审核列表
	 */
	public String checkList() throws Exception {
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = workflowService.getMyJob(WorkflowConstants.DRAW_DEFINE, getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("status", DrawDefineState.WAITED.getValue());//待审核
		page = drawDefDAO.findDrawDefPage(params, getPageNumber(), getPageSize());
		return CHECK_LIST;
	}
	
	/**
	 * 审核明细
	 */
	public String checkDetail() throws Exception {
		drawDef = (DrawDef) drawDefDAO.findByPk(drawDef.getDrawId());
		return DETAIL;
	}

	/**
	 * 新增初始化页面
	 */
	public String showAdd() throws Exception {
		initPage();
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止定义一个新的抽奖活动。");
		}
		return ADD;
	}

	public void isExistDrawId() throws Exception {
		boolean isExistDrawId = false;
		if (StringUtils.isNotBlank(drawDef.getDrawId())) {
			isExistDrawId = drawDefDAO.isExist(drawDef.getDrawId());
		}
		respond("{'isExistDrawId':"+ isExistDrawId + "}");
	}
	
	/**
	 * 新增
	 */
	public String add() throws Exception {
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			drawDef.setIssType(IssType.CARD.getValue());
			drawDef.setIssId(getSessionUser().getBranchNo());
			if (drawDef.getPinstType().equals(IssType.CARD.getValue())) {
				drawDef.setPinstId(getSessionUser().getBranchNo());
			}
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			drawDef.setIssType(IssType.MERCHANT.getValue());
			drawDef.setIssId(getSessionUser().getMerchantNo());
			drawDef.setPinstType(IssType.MERCHANT.getValue());
			drawDef.setPinstId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的抽奖活动。");
		}
		DrawDef def = lotteryService.addDrawDef(drawDef, getSessionUser());
		
		workflowService.startFlow(def, WorkflowConstants.LOTTERY_ADAPTER, def.getDrawId(), getSessionUser());
		String msg = LogUtils.r("抽奖活动ID为[{0}]的活动定义已经提交。", def.getDrawId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/lottery/drawDef/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		initPage();
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止修改活动。");
		}
		String drawId = drawDef.getDrawId();
		drawDef = (DrawDef) drawDefDAO.findByPk(drawId);
		
		if(null == drawDef){
			throw new BizException("要修改的抽奖活动["+drawId+"]不存在。");
		}
		
		cardBinScope_sel ="";
		if (StringUtils.isNotEmpty(drawDef.getCardBinScope())) {
			String[] binscopes = drawDef.getCardBinScope().split(",");
			for (int i = 0; i < binscopes.length; i++) {
				CardBin cardBin = (CardBin) cardBinDAO.findByPk(binscopes[i]);
				cardBinScope_sel += cardBin.getBinName();
				if (i < binscopes.length - 1) {
					cardBinScope_sel += ",";
				}
			}
		}

		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(drawDef.getPinstId());
		if (merchInfo != null) {
			pinstId_sel = merchInfo.getMerchName();
		}
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			drawDef.setIssType(IssType.CARD.getValue());
			drawDef.setIssId(getSessionUser().getBranchNo());
			if (drawDef.getPinstType().equals(IssType.CARD.getValue())) {
				drawDef.setPinstId(getSessionUser().getBranchNo());
			}
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			drawDef.setIssType(IssType.MERCHANT.getValue());
			drawDef.setIssId(getSessionUser().getMerchantNo());
			drawDef.setPinstType(IssType.MERCHANT.getValue());
			drawDef.setPinstId(getSessionUser().getMerchantNo());
		}
		
		lotteryService.modifyDrawDef(drawDef, getSessionUser());
		String msg = LogUtils.r("修改抽奖活动ID为[{0}]的抽奖活动成功。", drawDef.getDrawId());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/lottery/drawDef/list.do", msg);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止修改活动。");
		}
		boolean isSuccess = lotteryService.deleteDrawDef(drawId);
		if (isSuccess) {
			String msg = LogUtils.r("删除促销活动[{0}]成功。", drawId);
			log(msg, UserLogType.DELETE);
			addActionMessage("/lottery/drawDef/list.do", msg);
		}
		return SUCCESS;
	}
	
	//以下为抽奖用户相关
	/**
	 * 显示参与抽奖的用户列表
	 * @Date 2012-12-27下午3:14:39
	 * @return String
	 */
	public String showDrawInfo() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止查看抽奖活动。");
		}
		if (drawDef != null) {
			params.put("drawId", drawDef.getDrawId());
		}
		
		if(drawInfoReg != null){
			params.put("jionDrawId", drawInfoReg.getJionDrawId());
			params.put("cardIssuer", drawInfoReg.getCardIssuer());
		}
		page = drawInfoRegDAO.findDrawInfoReg(params, getPageNumber(),getPageSize());
		return LIST;
	}
	
	public String showAddDrawInfo() throws Exception {
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
					.equals(RoleType.MERCHANT.getValue()))) {
				throw new BizException("非商户或发卡机构禁止操作抽奖用户。");
		}
		return ADD;
	}
	
	public String addDrawInfo() throws Exception {
		drawDef = (DrawDef) drawDefDAO.findByPk(drawDef.getDrawId());
		
		drawInfoReg.setDrawId(drawDef.getDrawId());
		drawInfoReg.setCardIssuer(drawDef.getPinstId());
		drawInfoReg.setDrawName(drawDef.getDrawName());
		
		DrawInfoRegKey drawInfoRegKey = new DrawInfoRegKey();
		drawInfoRegKey.setDrawId(drawInfoReg.getDrawId());
		drawInfoRegKey.setJionDrawId(drawInfoReg.getJionDrawId());
		
		if(null !=drawInfoRegDAO.findByPk(drawInfoRegKey)){
			throw new BizException("参与抽奖活动用户["+drawInfoReg.getDrawId()+","+drawInfoReg.getJionDrawId()+"]的已存在。");
		}
		lotteryService.addDrawInfoReg(drawInfoReg, getSessionUser());
		String msg = LogUtils.r("参与抽奖活动用户[{0},{1}]的已添加。", drawInfoReg.getDrawId(),drawInfoReg.getJionDrawId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/lottery/drawDef/showDrawInfo.do?drawDef.drawId="+drawDef.getDrawId(), msg);
		return SUCCESS;
	}
	
	public String deleteDrawInfo() throws Exception {
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止删除用户。");
		}
		
		DrawInfoRegKey drawInfoRegKey = new DrawInfoRegKey();
		drawInfoRegKey.setDrawId(drawId);
		drawInfoRegKey.setJionDrawId(jionDrawId);
		boolean isSuccess = lotteryService.deleteDrawInfoReg(drawInfoRegKey);
		if (isSuccess) {
			String msg = LogUtils.r("删除抽奖用户[{0},{1}]成功。", drawInfoRegKey.getDrawId(),drawInfoRegKey.getJionDrawId());
			log(msg, UserLogType.DELETE);
			this.addActionMessage("/lottery/drawDef/showDrawInfo.do?drawDef.drawId="+drawInfoRegKey.getDrawId(), msg);
		}
		return SUCCESS;
	}
	
	/**
	 * 新增，修改时页面初始化
	 */
	private void initPage() {
		issTypeList = IssType.getAll();
		drawTypeList = DrawType.getAll();
		pInstTypeList = IssType.getAll();
//		transTypeList = TransType.getAll();
		transTypeList = TransType.getDrawType();
		amtTypeList = AmtType.getAll();
		probTypeList = ProbType.getAll();
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List getDrawTypeList() {
		return drawTypeList;
	}

	public void setDrawTypeList(List drawTypeList) {
		this.drawTypeList = drawTypeList;
	}

	public List getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List transTypeList) {
		this.transTypeList = transTypeList;
	}

	public DrawDef getDrawDef() {
		return drawDef;
	}

	public void setDrawDef(DrawDef drawDef) {
		this.drawDef = drawDef;
	}

	public DrawInfoReg getDrawInfoReg() {
		return drawInfoReg;
	}

	public void setDrawInfoReg(DrawInfoReg drawInfoReg) {
		this.drawInfoReg = drawInfoReg;
	}

	public List getIssTypeList() {
		return issTypeList;
	}

	public void setIssTypeList(List issTypeList) {
		this.issTypeList = issTypeList;
	}

	public List getPInstTypeList() {
		return pInstTypeList;
	}

	public void setPInstTypeList(List instTypeList) {
		pInstTypeList = instTypeList;
	}

	public List getAmtTypeList() {
		return amtTypeList;
	}

	public void setAmtTypeList(List amtTypeList) {
		this.amtTypeList = amtTypeList;
	}

	public List getProbTypeList() {
		return probTypeList;
	}

	public void setProbTypeList(List probTypeList) {
		this.probTypeList = probTypeList;
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

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

	public String getCardBinScope_sel() {
		return cardBinScope_sel;
	}

	public void setCardBinScope_sel(String cardBinScope_sel) {
		this.cardBinScope_sel = cardBinScope_sel;
	}

	public String getPinstId_sel() {
		return pinstId_sel;
	}

	public void setPinstId_sel(String pinstId_sel) {
		this.pinstId_sel = pinstId_sel;
	}

	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}
	
	public String getJionDrawId() {
		return jionDrawId;
	}

	public void setJionDrawId(String jionDrawId) {
		this.jionDrawId = jionDrawId;
	}
	
	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}
	
	public boolean isShowBrushSet() {
		return showBrushSet;
	}

	public void setShowBrushSet(boolean showBrushSet) {
		this.showBrushSet = showBrushSet;
	}
}
