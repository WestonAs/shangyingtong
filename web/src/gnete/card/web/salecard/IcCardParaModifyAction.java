package gnete.card.web.salecard;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.IcCardParaModifyReg;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.IcCardService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * @File: IcCardParaModifyAction.java
 *
 * @description: IC卡片参数修改Action
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-9-11 上午09:50:42
 */
public class IcCardParaModifyAction extends BaseAction {
	
	@Autowired
	private IcCardService icCardService;
	
	private IcCardParaModifyReg icCardParaModifyReg;
	private List<RegisterState> statusList;
	
	/** 是否需要在登记簿中记录签名信息 */
	private boolean signatureReg;
	
	private Paginater page;

	@Override
	public String execute() throws Exception {
		this.statusList = RegisterState.getAll();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (icCardParaModifyReg != null) {
			params.put("id", icCardParaModifyReg.getId());
			params.put("searchCardId", MatchMode.ANYWHERE.toMatchString(icCardParaModifyReg.getCardId()));
			params.put("status", icCardParaModifyReg.getStatus());
		}
		if (isCenterOrCenterDeptRoleLogined()){
			
		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", super.getMyManageFenzhi());
			
		} else if ( isCardSellRoleLogined()) {
			params.put("branchCode", super.getSessionUser().getBranchNo());
			
		} else if (isCardDeptRoleLogined()) {
			params.put("cardBranch", super.getSessionUser().getBranchNo());
			
		} else if (isCardRoleLogined()) {
			params.put("cardIssuerList", super.getMyCardBranch());
			 
		} else {
			throw new BizException("没有权限查看IC卡卡片参数修改登记记录");
		}
		
		this.page = this.icCardService.findParaModifyPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	/**
	 * 明细页面
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {

		this.icCardParaModifyReg = this.icCardService.findParaModifyDetail(icCardParaModifyReg.getId());
		
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
		
		JSONObject object = this.icCardService.searchCardInfoForModify(cardId);
		object.put("randomSessionid", randomSessionid);
		object.put("signatureReg", signatureReg);
		
		this.respond(object.toString());
	}
	
	/**
	 * 新增初始页面
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		if ( !(isCardOrCardDeptRoleLogined() || isCardSellRoleLogined()) ){
			throw new BizException("没有权限做IC卡卡片参数修改操作！");
		}
		return ADD;
	}
	
	public void add() throws Exception {
		JSONObject object = new JSONObject();
		
		String serialNo = request.getParameter("serialNo");
		String signature = request.getParameter("signature");
		
		try {
			// 在表中插入数据
			String refId = this.icCardService.addParaModifyReg(icCardParaModifyReg, super.getSessionUser(), serialNo, signature);
			
			// 轮询登记簿
			IcCardParaModifyReg modifyReg = this.icCardService.dealModifyPara(refId);
			if (RegisterState.NORMAL.getValue().equals(modifyReg.getStatus())) {
				object.put("chkRespCode", modifyReg.getChkRespCode());
				object.put("arpc", modifyReg.getArpc());
				object.put("cardId", modifyReg.getCardId());
				object.put("writeRespCode", modifyReg.getWriteRespCode());
				object.put("writeScript", modifyReg.getWriteScript());
				object.put("id", modifyReg.getId());
			}
			Assert.notEquals(RegisterState.DISABLE.getValue(), modifyReg.getStatus(), "后台处理失败，原因：" + modifyReg.getRemark());

			object.put("result", true);
		} catch (BizException e) {
			object.put("result", false);
			object.put("msg", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	/**
	 * 写卡成功的相关操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String notice() throws Exception {
		
		IcCardParaModifyReg paraModifyReg = this.icCardService.noticeModifyParaSuccess(icCardParaModifyReg.getId());
		
		String msg = LogUtils.r("卡号[{0}]批次为[{1}]的IC卡卡片参数修改成功！", paraModifyReg.getCardId(), paraModifyReg.getId());
		this.log(msg, UserLogType.OTHER);
		this.addActionMessage("/pages/icParaMgr/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public String noticeError() throws Exception {
		String msg = request.getParameter("errorMsg");
		throw new BizException(msg);
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

	public IcCardParaModifyReg getIcCardParaModifyReg() {
		return icCardParaModifyReg;
	}

	public void setIcCardParaModifyReg(IcCardParaModifyReg icCardParaModifyReg) {
		this.icCardParaModifyReg = icCardParaModifyReg;
	}

}
