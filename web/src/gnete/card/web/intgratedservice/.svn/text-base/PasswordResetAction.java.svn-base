package gnete.card.web.intgratedservice;

import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.PasswordResetRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.PasswordResetReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardPasswordService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 密码重置
 * @author aps-lib
 *
 */
public class PasswordResetAction extends BaseAction{

	@Autowired
	private PasswordResetRegDAO passwordResetRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardPasswordService cardPasswordService;
	
	private PasswordResetReg passwordResetReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private List<RegisterState>	statusList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("passwordResetReg", passwordResetReg);
		
		cardBranchList = new ArrayList<BranchInfo>();
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) { // 运营分支机构
			cardBranchList.addAll(branchInfoDAO.findCardByManange(getLoginBranchCode()));
			params.put("cardIssuers", cardBranchList);
			
		} else if (isCardOrCardDeptRoleLogined()) { // 发卡机构或发卡机构部门
			cardBranchList.addAll(branchInfoDAO.findChildrenList(getLoginBranchCode()));
			params.put("cardIssuers", cardBranchList);

		} else if (isCardSellRoleLogined()) { // 售卡代理
			params.put("saleOrgId", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.passwordResetRegDAO.findPwReset(params, this.getPageNumber(), this.getPageSize());
		this.statusList = RegisterState.getForCheck();		
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		this.passwordResetReg = (PasswordResetReg) this.passwordResetRegDAO.findByPk(this.passwordResetReg.getPasswordResetRegId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "没有权限添加密码重置记录");
		this.checkEffectiveCertUser();
		
		return ADD;
	}
	
	// 新增磁卡挂失信息
	public String add() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "没有权限添加密码重置记录");
		this.checkEffectiveCertUser();
		
		//保存数据
		this.cardPasswordService.addPasswordResetReg(passwordResetReg, this.getSessionUser());
		
		String msg = "新增密码重置ID["+this.passwordResetReg.getPasswordResetRegId()+"]成功！";
		this.addActionMessage("/passwordReset/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 审核流程-待审核记录列表
	public String checkList() throws Exception {
		Assert.isTrue(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined(), "没有权限查询审核列表。");
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		TimeInterval t = new TimeInterval();
		String[] passwordResetRegIds = this.workflowService.getMyJob(Constants.WORKFLOW_PASSWORD_RESET_REG, this.getSessionUser());
		logger.info("[{}]查询卡密码重置待审核id数组，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		if (ArrayUtils.isEmpty(passwordResetRegIds)) {
			return CHECK_LIST;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", passwordResetRegIds);
		t.reset();
		this.page = this.passwordResetRegDAO.findPwReset(params, this.getPageNumber(), this.getPageSize());
		logger.info("[{}]查询卡密码审核列表，耗时[{}]秒", this.getSessionUserCode(), t.getIntervalOfSec());
		
		return CHECK_LIST;
	}
	
	// 审核流程-待审核记录明细
	public String checkDetail() throws Exception{
		
		Assert.notNull(passwordResetReg, "密码重置对象不能为空");	
		Assert.notNull(passwordResetReg.getPasswordResetRegId(), "密码重置对象ID不能为空");	
		
		// 密码重置登记簿明细
		this.passwordResetReg = (PasswordResetReg)this.passwordResetRegDAO.findByPk(passwordResetReg.getPasswordResetRegId());		
		return DETAIL;	
	}
	
	// 判断输入卡号是发卡机构所发卡或者售卡代理所售卡
	public void validateCardId(){
		try{
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.passwordResetReg.getCardId());
			Assert.notNull(cardInfo, "该卡号不存在，请重新输入。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "密码重置");
			
		}catch (Exception e) {
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true + "}");
	}

	public PasswordResetReg getPasswordResetReg() {
		return passwordResetReg;
	}

	public void setPasswordResetReg(PasswordResetReg passwordResetReg) {
		this.passwordResetReg = passwordResetReg;
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

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}
	
}
