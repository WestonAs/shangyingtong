package gnete.card.web.lottery;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AwardRegDAO;
import gnete.card.dao.DrawDefDAO;
import gnete.card.entity.AwardReg;
import gnete.card.entity.AwardRegKey;
import gnete.card.entity.DrawDef;
import gnete.card.entity.type.DrawType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LotteryService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽奖
 * @Project: CardWeb
 * @File: DrawAwardAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-12-28上午11:57:12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class DrawAwardAction extends BaseAction {

	@Autowired
	private DrawDefDAO drawDefDAO;
	@Autowired
	private AwardRegDAO awardRegDAO;
	@Autowired
	private LotteryService lotteryService;
	
	/** 抽奖方法类型 */
	private List drawTypeList;
	/** 机构类型代码 */
	private List pInstTypeList;
	/** 抽奖相关交易类型 */
	private List transTypeList;
	private List<DrawDef> drawList;

	/** 抽奖定义 */
	private DrawDef drawDef;
	/** 用户中奖信息 */
	private AwardReg awardReg;

	private Paginater page;

	private String drawId;
	private String awdTicketNo;
	
	@Override
	public String execute() throws Exception {
		drawTypeList = DrawType.getAll();
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
		String[] drawStatus ={"10","21"};//只能查看审核通过和已抽奖的活动 DrawDefineState
		params.put("statuses", drawStatus);
		page = drawDefDAO.findDrawDefPage(params, getPageNumber(),getPageSize());
		return LIST;
	}
	
	/** 启动抽奖 */
	public String startDrawAward() throws Exception{
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止启动抽奖活动。");
		}
		
		Long waitsinfoId = lotteryService.startDrawAction(drawId, getSessionUser());
		String msg = LogUtils.r("抽奖活动已启动,请到用户中奖信息查询页面查看中奖情况。待处理表ID:"+waitsinfoId);
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/lottery/drawAward/list.do", msg);
		return SUCCESS;
	}
	
	/**
	 * 显示中奖的用户列表
	 * @Date 2012-12-27下午3:14:39
	 * @return String
	 */
	public String showDrawAward() throws Exception {
		String issId = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			issId = getSessionUser().getBranchNo();
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			issId = getSessionUser().getMerchantNo();
		}else{
			throw new BizException("非商户或发卡机构禁止查看抽奖活动。");
		}
		drawList = drawDefDAO.findByIssId(issId);
		if(null != awardReg){
		    params.put("drawId", awardReg.getDrawId());
		    params.put("awdTicketNo", awardReg.getAwdTicketNo());
		}
		page = awardRegDAO.findAwardReg(params, getPageNumber(),getPageSize());
		return LIST;
	}
	
	/**
	 * 领奖
	 * @Date 2012-12-27下午3:14:39
	 * @return String
	 */
	public String acceptDrawAward() throws Exception {
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作领奖活动。");
		}
		
		AwardRegKey awardRegKey = new AwardRegKey();
		awardRegKey.setDrawId(drawId);
		awardRegKey.setAwdTicketNo(awdTicketNo);
		awardReg = (AwardReg)awardRegDAO.findByPk(awardRegKey);
		if(awardReg == null){
			throw new BizException("中奖用户信息["+drawId+","+awdTicketNo+"]不存在。");
		}
		
		lotteryService.modifyAwardReg(awardReg, getSessionUser());
		String msg = LogUtils.r("中奖用户[{0},{1}]完成领奖。", drawId,awdTicketNo);
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/lottery/drawAward/showDrawAward.do?awardReg.drawId="+drawId, msg);
		return SUCCESS;
	}
	
	/**
	 * 删除中奖用户信息
	 * @Date 2012-12-27下午3:14:39
	 * @return String
	 */
	public String deleteDrawAward() throws Exception {
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作中奖用户信息。");
		}
		
		AwardRegKey awardRegKey = new AwardRegKey();
		awardRegKey.setDrawId(drawId);
		awardRegKey.setAwdTicketNo(awdTicketNo);
		awardReg = (AwardReg)awardRegDAO.findByPk(awardRegKey);
		if(awardReg == null){
			throw new BizException("中奖用户信息["+drawId+","+awdTicketNo+"]不存在。");
		}
		
		lotteryService.deleteAwardReg(awardRegKey);
		String msg = LogUtils.r("中奖用户信息[{0},{1}]已删除。", drawId,awdTicketNo);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/lottery/drawAward/showDrawAward.do?awardReg.drawId="+drawId, msg);
		return SUCCESS;
	}
	
	public AwardReg getAwardReg() {
		return awardReg;
	}

	public void setAwardReg(AwardReg awardReg) {
		this.awardReg = awardReg;
	}
	
	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public String getAwdTicketNo() {
		return awdTicketNo;
	}

	public void setAwdTicketNo(String awdTicketNo) {
		this.awdTicketNo = awdTicketNo;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public List getpInstTypeList() {
		return pInstTypeList;
	}

	public void setpInstTypeList(List pInstTypeList) {
		this.pInstTypeList = pInstTypeList;
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

    public List<DrawDef> getDrawList() {
		return drawList;
	}

	public void setDrawList(List<DrawDef> drawList) {
		this.drawList = drawList;
	}
	
	public DrawDef getDrawDef() {
		return drawDef;
	}

	public void setDrawDef(DrawDef drawDef) {
		this.drawDef = drawDef;
	}
	
	public List getPInstTypeList() {
		return pInstTypeList;
	}

	public void setPInstTypeList(List instTypeList) {
		pInstTypeList = instTypeList;
	}
}