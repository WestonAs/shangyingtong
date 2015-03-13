package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.IcCancelCardReg;
import gnete.card.entity.IcCardReversal;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.IcCancelCardType;
import gnete.card.entity.type.IcReversalType;
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
 * @File: IcCancelCardAction.java
 *
 * @description: IC卡销卡
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-10-9 下午03:47:35
 */
public class IcCancelCardAction extends BaseAction {
	
	@Autowired
	private IcCardService icCardService;
	
	private IcCancelCardReg icCancelCardReg;
	private IcCardReversal icCardReversal;
	
	private List<RegisterState> statusList;
	/** 证件类型 */
	private List<CertType> certTypeList;
	private List<IcCancelCardType> cancelTypeList;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	private Paginater page;

	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (icCancelCardReg != null) {
			params.put("id", icCancelCardReg.getId());
			params.put("searchCardId", MatchMode.ANYWHERE.toMatchString(icCancelCardReg.getCardId()));
			params.put("status", icCancelCardReg.getStatus());
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
			params.put("branchCode", super.getSessionUser().getBranchNo());
		}
		// 登录用户发卡机构部门时
		else if (RoleType.CARD_DEPT.getValue().equals(super.getLoginRoleType())) {
			params.put("cardBranch", super.getSessionUser().getBranchNo());
		}
		// 如果登录用户为发卡机构时
		else if (RoleType.CARD.getValue().equals(super.getLoginRoleType())) {
			params.put("cardIssuerList", super.getMyCardBranch());
		} else {
			throw new BizException("没有权限查看IC卡销卡登记记录");
		}
		
		this.page = this.icCardService.findIcCancelCardRegPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	public String detail() throws Exception {
		this.icCancelCardReg = this.icCardService.findIcCancelCardRegDetail(icCancelCardReg.getId());
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
		} else {
			throw new BizException("没有权限做IC卡销卡操作！");
		}
		this.certTypeList = CertType.getAll();
		
		return ADD;
	}
	
	public void add() throws Exception {
		JSONObject object = new JSONObject();
		
		String serialNo = request.getParameter("serialNo");
		String signature = request.getParameter("signature");
		try {
			icCancelCardReg.setCancelType(IcCancelCardType.CAN_READ_CARD.getValue());
			// 在表中插入数据
			String refId = this.icCardService.addIcCancelCardReg(icCancelCardReg, super.getSessionUser(), serialNo, signature);
			
			// 轮询登记簿
			icCancelCardReg = this.icCardService.dealIcCancelCardReg(refId);
			if (RegisterState.NORMAL.getValue().equals(icCancelCardReg.getStatus())) {
				object.put("chkRespCode", icCancelCardReg.getChkRespCode());
				object.put("arpc", icCancelCardReg.getArpc());
				object.put("writeRespCode", icCancelCardReg.getWriteRespCode());
				object.put("writeScript", icCancelCardReg.getWriteScript());
				object.put("batchId", icCancelCardReg.getId());
				object.put("result", true);
			} else {
				Assert.notEquals(RegisterState.DISABLE.getValue(), icCancelCardReg.getStatus(), "后台处理失败，原因：" + icCancelCardReg.getRemark());
			}
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
		String refId = this.icCardService.addIcCardReversal(icCardReversal, super.getSessionUser(), IcReversalType.CANCEL_CARD);
		String msg = LogUtils.r("销卡批次为[{0}]的IC卡销卡处理失败，已向后台发起冲正！", refId);
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icCancelCard/list.do", msg);
		
		return SUCCESS;
	}
	
	/**
	 * 发送退卡成功的通知 
	 * @return
	 * @throws Exception
	 */
	public String notice() throws Exception {
		this.icCardService.noticeIcCancelCardSuccess(icCancelCardReg.getId());
		
		String msg = LogUtils.r("销卡批次为[{0}]的IC卡销卡处理成功！", icCancelCardReg.getId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icCancelCard/list.do", msg);
		
		return SUCCESS;
	}
	
	public String noticeError() throws Exception {
		String msg = request.getParameter("errorMsg");
		throw new BizException(msg);
	}
	
	/**
	 * 不可读卡销卡
	 * @return
	 * @throws Exception
	 */
	public String showUnreadCancel() throws Exception {
		// 发卡机构和发卡机构网点和售卡代理
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
		} else {
			throw new BizException("没有权限做IC卡销卡操作！");
		}
		this.certTypeList = CertType.getAll();
		this.cancelTypeList = IcCancelCardType.getUnReadTypeList();
		
		return "unreadCancel";
	}
	
	/**
	 * 不可读卡销卡
	 * @return
	 * @throws Exception
	 */
	public void unreadCancel() throws Exception {
		JSONObject object = new JSONObject();
		
		String serialNo = request.getParameter("serialNo");
		String signature = request.getParameter("signature");
		try {
			Assert.notEmpty(icCancelCardReg.getCancelType(), "销卡类型不能为空");
			// 在表中插入数据
			String refId = this.icCardService.addIcCancelCardReg(icCancelCardReg, super.getSessionUser(), serialNo, signature);
			
			// 轮询登记簿
			icCancelCardReg = this.icCardService.dealIcCancelCardReg(refId);
			if (RegisterState.NORMAL.getValue().equals(icCancelCardReg.getStatus())) {
				object.put("batchId", icCancelCardReg.getId());
			}
				
			Assert.notEquals(RegisterState.DISABLE.getValue(), icCancelCardReg.getStatus(), "后台处理失败，原因：" + icCancelCardReg.getRemark());
			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		this.respond(object.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public IcCardReversal getIcCardReversal() {
		return icCardReversal;
	}

	public void setIcCardReversal(IcCardReversal icCardReversal) {
		this.icCardReversal = icCardReversal;
	}

	public IcCancelCardReg getIcCancelCardReg() {
		return icCancelCardReg;
	}

	public void setIcCancelCardReg(IcCancelCardReg icCancelCardReg) {
		this.icCancelCardReg = icCancelCardReg;
	}

	public List<CertType> getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(List<CertType> certTypeList) {
		this.certTypeList = certTypeList;
	}

	public List<IcCancelCardType> getCancelTypeList() {
		return cancelTypeList;
	}

	public void setCancelTypeList(List<IcCancelCardType> cancelTypeList) {
		this.cancelTypeList = cancelTypeList;
	}

}
