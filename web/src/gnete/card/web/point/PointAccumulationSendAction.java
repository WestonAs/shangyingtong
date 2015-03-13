package gnete.card.web.point;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointSendApplyRegDAO;
import gnete.card.dao.PointSendDetailDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointSendApplyReg;
import gnete.card.entity.state.PointSendApplyRegState;
import gnete.card.entity.state.PointSendDetailState;
import gnete.card.entity.type.UserLogType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 积分累积赠送 处理
 */
@SuppressWarnings("serial")
public class PointAccumulationSendAction extends BaseAction {

	@Autowired
	private PointSendApplyRegDAO pointSendApplyRegDAO;
	@Autowired
	private PointSendDetailDAO pointSendDetailDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDao;

	private Paginater page;
	private PointSendApplyReg pointSendApplyReg;

	/** 发卡机构列表（发卡机构角色登录时在下拉框显示） */
	private List<BranchInfo> cardBranchList;
	private List<PointSendApplyRegState> statusList;

	/** 积分类型 */
	private List<PointClassDef> pointClassDefs;

	// 审核汇总的可赠送记录所需的页面传入参数
	private boolean isPass; // 通过 还是 不通过
	private boolean isBatchSelect; // 批量选择的记录 还是 全部待审核的记录
	private List<Integer> batchIds;// 

	@Override
	public String execute() throws Exception {
		this.statusList = PointSendApplyRegState.getAll();

		List<BranchInfo> inCardBranches = null;// 发卡机构范围条件

		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) {// 分支机构
			inCardBranches = this.getMyCardBranch();
		} else if (isCardRoleLogined()) {// 发卡机构
			inCardBranches = this.getMyCardBranch();
			this.cardBranchList = inCardBranches;
		} else {
			throw new BizException("没有权限查看积分累积赠送记录");
		}
		this.page = this.pointSendApplyRegDAO.findPage(pointSendApplyReg, inCardBranches, this
				.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		pointSendApplyReg = (PointSendApplyReg) pointSendApplyRegDAO.findByPk(pointSendApplyReg.getApplyId());
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(pointSendApplyReg.getCardIssuer(), this
					.getLoginBranchCode()), "没有权限访问指定的赠送申请记录");
		}
		this.page = pointSendDetailDAO.findPageByApplyId(pointSendApplyReg.getApplyId(), getPageNumber(),
				this.getPageSize());
		return DETAIL;
	}

	/**
	 * 批量审核通过/不通过 或 待审核记录全部审核通过/不通过
	 */
	public String checkToPass() throws Exception {
		pointSendApplyReg = (PointSendApplyReg) pointSendApplyRegDAO.findByPk(pointSendApplyReg.getApplyId());
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(pointSendApplyReg.getCardIssuer(), this
					.getLoginBranchCode()), "没有权限审核赠送明细记录applyId=" + pointSendApplyReg.getApplyId());
		} else {
			throw new BizException("没有权限审核赠送明细记录");
		}
		Assert.isTrue(PointSendApplyRegState.SUMMARIZING_SUCCESS.getValue().equals(
				pointSendApplyReg.getStatus()), "只有汇总成功的申请才能做审核操作！");
		if (isBatchSelect() && CollectionUtils.isEmpty(batchIds)) {
			throw new BizException("批量审核没有选择相应的明细记录！");
		}

		pointSendDetailDAO.checkToPass(isPass, isBatchSelect, batchIds, pointSendApplyReg.getApplyId(), this
				.getSessionUserCode());

		return detail();// 重新查询明细记录
	}

	/**
	 * ajax访问：积分赠送处理前检查
	 * 
	 * @return
	 * @throws BizException
	 */
	public void ajaxPreSendPointCheck() throws BizException {
		long cnt = pointSendDetailDAO.count(pointSendApplyReg.getApplyId(), PointSendDetailState.WAITED
				.getValue());
		JSONObject json = new JSONObject();
		json.put("waitedDetailCnt", cnt);
		this.respond(json.toString());
	}

	/**
	 * 赠送处理
	 */
	public String sendPointProcess() throws BizException {
		pointSendApplyReg = (PointSendApplyReg) pointSendApplyRegDAO.findByPk(pointSendApplyReg.getApplyId());
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(pointSendApplyReg.getCardIssuer(), this
					.getLoginBranchCode()), "没有权限审核赠送明细记录applyId=" + pointSendApplyReg.getApplyId());
		} else {
			throw new BizException("没有权限审核赠送明细记录");
		}
		Assert.isTrue(PointSendApplyRegState.SUMMARIZING_SUCCESS.getValue().equals(
				pointSendApplyReg.getStatus()), "只有汇总成功的申请才能做赠送操作！");

		// 发送后台积分累积赠送 实际赠送请求命令
		MsgSender.sendMsg(MsgType.POINT_ACCUMULATION_SEND, pointSendApplyReg.getApplyId(), this
				.getSessionUserCode());
		
		pointSendApplyReg.setStatus(PointSendApplyRegState.DEALING.getValue());
		pointSendApplyRegDAO.update(pointSendApplyReg);

		String msg = LogUtils.r("积分累积赠送处理请求发送[{0},{1}]成功", MsgType.POINT_ACCUMULATION_SEND, pointSendApplyReg
				.getApplyId());
		this.addActionMessage("/intgratedService/pointBiz/pointAccumulationSend/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public String showAdd() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("非发卡机构不能做积分累积赠送操作");
		}
		pointClassDefs = pointClassDefDao.findPtClassByJinstId(this.getLoginBranchCode());
		return ADD;
	}

	/**
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		if (!isCardRoleLogined()) {
			throw new BizException("非发卡机构不能做积分累积赠送操作");
		}

		pointSendApplyReg.setCardIssuer(this.getLoginBranchCode());
		pointSendApplyReg.setEntryUser(this.getSessionUserCode());
		pointSendApplyReg.setStatus(PointSendApplyRegState.WAITE_DEAL.getValue());
		pointSendApplyReg.setUpdateTime(new Date());
		pointSendApplyRegDAO.insert(pointSendApplyReg);

		// 发送后台积分累积赠送 明细汇总请求命令
		MsgSender.sendMsg(MsgType.POINT_ACCUMULATION_SEND_SUMMARIZING, pointSendApplyReg.getApplyId(), this
				.getSessionUserCode());

		String msg = LogUtils.r("新增积分累积赠送登记[{0}]成功", pointSendApplyReg.getApplyId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/intgratedService/pointBiz/pointAccumulationSend/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public PointSendApplyReg getPointSendApplyReg() {
		return pointSendApplyReg;
	}

	public void setPointSendApplyReg(PointSendApplyReg pointSendApplyReg) {
		this.pointSendApplyReg = pointSendApplyReg;
	}

	public List<PointSendApplyRegState> getStatusList() {
		return statusList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public List<PointClassDef> getPointClassDefs() {
		return pointClassDefs;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean isBatchSelect() {
		return isBatchSelect;
	}

	public void setBatchSelect(boolean isBatchSelect) {
		this.isBatchSelect = isBatchSelect;
	}

	public List<Integer> getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(List<Integer> batchIds) {
		this.batchIds = batchIds;
	}

}
