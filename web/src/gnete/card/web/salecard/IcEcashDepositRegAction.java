package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.RebateRuleDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.IcEcashDepositReg;
import gnete.card.entity.IcEcashReversal;
import gnete.card.entity.Rebate;
import gnete.card.entity.RebateRule;
import gnete.card.entity.state.RebateRuleState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.ReversalType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.IcCardService;
import gnete.card.service.RebateRuleService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: IcEcashDepositRegAction.java
 *
 * @description: IC卡电子现金账户充值处理Action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-13
 */
public class IcEcashDepositRegAction extends BaseAction {
	
	@Autowired
	private IcCardService icCardService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private RebateRuleDAO rebateRuleDAO;  
	@Autowired
	private RebateRuleService rebateRuleService;
	
	private List<RebateRule> rebateRuleList;
	
	private IcEcashDepositReg icEcashDepositReg;
	private List<RegisterState> statusList;
	private IcEcashReversal icEcashReversal;
	
	// 是否需要在登记簿中记录签名信息
	private boolean signatureReg;
	
	private Paginater page;

	
	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (icEcashDepositReg != null) {
			params.put("depositBatchId", icEcashDepositReg.getDepositBatchId());
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(icEcashDepositReg.getCardId()));
			params.put("status", icEcashDepositReg.getStatus());
		}
		if (isCenterOrCenterDeptRoleLogined()) {

		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", super.getMyManageFenzhi());

		} else if (isCardSellRoleLogined()) {
			params.put("depositBranch", super.getSessionUser().getBranchNo());

		} else if (isCardDeptRoleLogined()) {
			params.put("depositBranch", super.getSessionUser().getDeptId());

		} else if (isCardRoleLogined()) {
			params.put("cardBranch", super.getSessionUser().getBranchNo());

		} else {
			throw new BizException("没有权限查看IC卡电子现金充值记录");
		}
		
		this.page = this.icCardService.findIcEcashDepositPage(params, super.getPageNumber(), super.getPageSize());
		
		return LIST;
	}
	
	/**
	 * 明细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		icEcashDepositReg = this.icCardService.getIcEcashDepositDetail(icEcashDepositReg);
		return DETAIL;
	}
	
	/**
	 * 查询卡信息
	 * @throws Exception
	 */
	public void searchCardId() throws Exception {
		String cardId = request.getParameter("cardId");
		
		signatureReg = StringUtils.equals(SysparamCache.getInstance().getSignatureReg(), Symbol.YES);
		String randomSessionid = Long.toString(System.currentTimeMillis()) + WebUtils.getSessionId(request);
		
		JSONObject object = this.icCardService.searchCardId(cardId);
		object.put("randomSessionid", randomSessionid);
		object.put("signatureReg", signatureReg);
		this.respond(object.toString());
	}
	
	/**
	 * 进入新增页面的初始化操作
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (!(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined())){
			throw new BizException("没有权限做IC卡电子现金充值！");
		}
		return ADD;
	}
	
	/**
	 * 添加充值记录
	 * @return
	 * @throws Exception
	 */
	public void add() throws Exception {
		
		JSONObject object = new JSONObject();
		
		String serialNo = request.getParameter("serialNo");
		try {
			// 在表中插入数据
			String refId = this.icCardService.addIcEcashDepositReg(icEcashDepositReg, super.getSessionUser(), serialNo);
			
			// 轮询登记簿
			icEcashDepositReg = this.icCardService.dealIcEcashDeposit(refId);
			if (RegisterState.NORMAL.getValue().equals(icEcashDepositReg.getStatus())) {
				object.put("chkRespCode", icEcashDepositReg.getChkRespCode());
				object.put("arpc", icEcashDepositReg.getArpc());
				object.put("writeRespCode", icEcashDepositReg.getWriteRespCode());
				object.put("writeScript", icEcashDepositReg.getWriteScript());
				object.put("cardId", icEcashDepositReg.getCardId());
				object.put("depositBatchId", icEcashDepositReg.getDepositBatchId());
			}
			Assert.notEquals(RegisterState.DISABLE.getValue(), icEcashDepositReg.getStatus(), "后台处理失败，原因：" + icEcashDepositReg.getRemark());

			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		this.respond(object.toString());
		
//		String msg = LogUtils.r("充值批次为[{0}]的IC卡电子现金充值处理成功", icEcashDepositReg.getDepositBatchId());
//		this.log(msg, UserLogType.ADD);
//		this.addActionMessage("/pages/icEcashDeposit/list.do", msg);
//		return SUCCESS;
	}
	
	/**
	 * 发生冲正请求
	 * @throws Exception
	 */
	public String reversal() throws Exception {
		this.icCardService.addIcEcashReversal(icEcashReversal, super.getSessionUser(), ReversalType.DEPOSIT);
		String msg = LogUtils.r("充值批次为[{0}]的IC卡电子现金充值处理失败，已向后台发起冲正！", icEcashReversal.getDepositBatchId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icEcashDeposit/list.do", msg);
		
		return SUCCESS;
	}
	
	/**
	 * 发送充值成功的通知 
	 * @return
	 * @throws Exception
	 */
	public String notice() throws Exception {
		this.icCardService.noticeSuccess(icEcashReversal.getDepositBatchId());
		
		String msg = LogUtils.r("充值批次为[{0}]的IC卡电子现金充值成功！", icEcashReversal.getDepositBatchId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icEcashDeposit/list.do", msg);
		
		return SUCCESS;
	}
	
	public String noticeError() throws Exception {
		String msg = request.getParameter("errorMsg");
		throw new BizException(msg);
//		this.addActionMessage("/pages/icEcashDeposit/list.do", msg);
//		return "exception";
	}

	
    public String findRebateRule() throws Exception {
    	String cardId = this.getFormMapValue("cardId");
    	String cardCustomerId = this.getFormMapValue("cardCustomerId");
    	
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			this.setMessage("卡号["+ cardId +"]不存在");
			return "rebateRule";
		}
		
		rebateRuleList = new ArrayList<RebateRule>();
		Map<String, Object> params = new HashMap<String, Object>();
		// 查找通用返利规则
		params.put("isCommon", Symbol.YES);
		params.put("cardBranch", cardInfo.getCardIssuer().trim());
		String[] statusArray = new String[]{RebateRuleState.NORMAL.getValue(), RebateRuleState.USED.getValue()};
		params.put("statusArray", statusArray);
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		
		// 查找该购卡客户的返利规则
		params.put("isCommon", Symbol.NO);
		params.put("cardBranch", cardInfo.getCardIssuer());
		params.put("statusArray", statusArray);
		params.put("cardBin", cardInfo.getCardBin());
		params.put("depositCardCustomerId", cardCustomerId);
		rebateRuleList.addAll(this.rebateRuleDAO.findRebateRule(params));
		if (CollectionUtils.isEmpty(rebateRuleList)) {
			this.setMessage("没有合适的返利规则！");
		}
		return "rebateRule";
	}
	
    // 服务端计算返利金额、实收金额等，返回给客户端
	public void calRealAmt(){
    	JSONObject object = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amt", this.icEcashDepositReg.getDepositAmt().toString());
		RebateRule  rebateRule = (RebateRule)this.rebateRuleDAO.findByPk(this.icEcashDepositReg.getRebateId());
		params.put("rebateRule", rebateRule);
		Rebate rebate = this.rebateRuleService.calculateRebate(params);  // 计算返利金额等
		object.put("rebateAmt", rebate.getRebateAmt());
		object.put("isUsedPeriodRule", rebateRuleDAO.isUsedPeriodRule(this.getFormMapValue("cardId"),
				this.icEcashDepositReg.getRebateId()));
		this.respond(object.toString());
	}

    
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public IcEcashDepositReg getIcEcashDepositReg() {
		return icEcashDepositReg;
	}

	public void setIcEcashDepositReg(IcEcashDepositReg icEcashDepositReg) {
		this.icEcashDepositReg = icEcashDepositReg;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public IcEcashReversal getIcEcashReversal() {
		return icEcashReversal;
	}

	public void setIcEcashReversal(IcEcashReversal icEcashReversal) {
		this.icEcashReversal = icEcashReversal;
	}

	public void setRebateRuleList(List<RebateRule> rebateRuleList) {
		this.rebateRuleList = rebateRuleList;
	}

	public List<RebateRule> getRebateRuleList() {
		return rebateRuleList;
	}
}
