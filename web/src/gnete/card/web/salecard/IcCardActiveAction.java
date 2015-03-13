package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.IcCardActive;
import gnete.card.entity.IcEcashReversal;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.ReversalType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.IcCardService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: IcCardActiveAction.java
 *
 * @description: IC卡激活处理Action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-21
 */
public class IcCardActiveAction extends BaseAction {
	
	@Autowired
	private IcCardService icCardService;
	
	private IcCardActive icCardActive;
	private List<RegisterState> statusList;
	
	private IcEcashReversal icEcashReversal;
	
	// 是否需要在登记簿中记录签名信息
	private boolean signatureReg;
	
	private Paginater page;

	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (icCardActive != null) {
			params.put("activeBatchId", icCardActive.getActiveBatchId());
			params.put("cardId", MatchMode.ANYWHERE.toMatchString(icCardActive.getCardId()));
			params.put("status", icCardActive.getStatus());
		}
		// 如果登录用户为运营中心，运营中心部门时，查看所有
		if (RoleType.CENTER.getValue().equals(super.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(super.getLoginRoleType())){
		}
		// 登录用户为分支时，查看自己及自己的下级分支机构管理的所有发卡机构的记录
		else if (RoleType.FENZHI.getValue().equals(super.getLoginRoleType())) {
			params.put("fenzhiList", super.getMyManageFenzhi());
		}
		// 登录用户为售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(super.getLoginRoleType())) {
			params.put("activeBatchId", super.getSessionUser().getBranchNo());
		}
		// 登录用户发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())) {
			params.put("activeBatchId", super.getSessionUser().getDeptId());
			
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(super.getLoginRoleType())) {
			params.put("cardBranch", super.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查看IC卡激活记录");
		}
		
		this.page = this.icCardService.findIcCardActivePage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	/**
	 * 明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		icCardActive = this.icCardService.findIcCardActiveDetail(icCardActive.getActiveBatchId());
		
		return DETAIL;
	}
	
	/**
	 * 新增初始页面
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (this.getLoginRoleType().equals(RoleType.CARD.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())
				|| this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){
			
		} else {
			throw new BizException("没有权限做IC卡激活或余额补登操作！");
		}
		return ADD;
	}
	
	public void add() throws Exception {
		JSONObject object = new JSONObject();
		
		String serialNo = request.getParameter("serialNo");
		
		try {
			// 在表中插入数据
			String refId = this.icCardService.addIcCardActive(icCardActive, super.getSessionUser(), serialNo);
			
			// 轮询登记簿
			icCardActive = this.icCardService.dealIcCardActive(refId);
			if (RegisterState.NORMAL.getValue().equals(icCardActive.getStatus())) {
				object.put("chkRespCode", icCardActive.getChkRespCode());
				object.put("arpc", icCardActive.getArpc());
				object.put("cardId", icCardActive.getCardId());
				object.put("writeRespCode", icCardActive.getWriteRespCode());
				object.put("writeScript", icCardActive.getWriteScript());
				object.put("activeBatchId", icCardActive.getActiveBatchId());
			}
			Assert.notEquals(RegisterState.DISABLE.getValue(), icCardActive.getStatus(), "后台处理失败，原因：" + icCardActive.getRemark());

			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 发生冲正请求
	 * @throws Exception
	 */
	public String reversal() throws Exception {
		this.icCardService.addIcEcashReversal(icEcashReversal, super.getSessionUser(), ReversalType.FILL_UP);
		String msg = LogUtils.r("激活批次为[{0}]的IC卡激活处理失败，已向后台发起冲正！", icEcashReversal.getDepositBatchId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icCardActive/list.do", msg);
		
		return SUCCESS;
	}
	
	/**
	 * 根据前台返回的结果发送激活成功或失败的通知 
	 * @return
	 * @throws Exception
	 */
	public String notice() throws Exception {
//		String activeResult = request.getParameter("activeResult");
//		String msg = "";
//		if ("01".equals(activeResult)) {
//			// 激活成功
//			msg = "卡号["+ icCardActive.getCardId() + "]激活成功！";
//		} else if ("02".equals(activeResult)) {
//			// 激活失败
//			msg = "卡号["+ icCardActive.getCardId() + "]激活时前台失败，请重试！";
//		} else {
//			// 后台生成arpc失败
//			msg = "卡号["+ icCardActive.getCardId() + "]后台校验产生ARPC失败，错误码：[" + activeResult + "]";
//		}
//		
//		this.log(msg, UserLogType.OTHER);
//		this.addActionMessage("/pages/icCardActive/list.do", msg);
		
		icCardActive = this.icCardService.noticeActiveSuccess(icEcashReversal.getDepositBatchId());
		
		String msg = LogUtils.r("卡号[{0}]批次为[{1}]的IC卡激活或余额补登成功！", icCardActive.getCardId(), icCardActive.getActiveBatchId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icCardActive/list.do", msg);
		return SUCCESS;
	}
	
	public String noticeError() throws Exception {
		String msg = request.getParameter("errorMsg");
		throw new BizException(msg);
//		this.addActionMessage("/pages/icCardActive/list.do", msg);
//		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isSignatureReg() {
		return signatureReg;
	}

	public void setSignatureReg(boolean signatureReg) {
		this.signatureReg = signatureReg;
	}

	public List<RegisterState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<RegisterState> statusList) {
		this.statusList = statusList;
	}

	public IcCardActive getIcCardActive() {
		return icCardActive;
	}

	public void setIcCardActive(IcCardActive icCardActive) {
		this.icCardActive = icCardActive;
	}

	public IcEcashReversal getIcEcashReversal() {
		return icEcashReversal;
	}

	public void setIcEcashReversal(IcEcashReversal icEcashReversal) {
		this.icEcashReversal = icEcashReversal;
	}

}
