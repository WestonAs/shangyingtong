package gnete.card.web.intgratedservice.wsBankCardBinding;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.WsBankVerBindingRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.WsBankVerBindingReg;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.WsBankVerBindingRegState;
import gnete.card.entity.type.UserLogType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.UserCertificateRevService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.BooleanMessage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * 银行卡绑定/解绑/默认卡 处理
 */
@SuppressWarnings("serial")
public class WsBankVerBindingRegAction extends BaseAction {
	private static final String		ADD_WS_BANK_VER_BINDING_RANDOM	= "ADD_WS_BANK_VER_BINDING_RANDOM";
	@Autowired
	private WsBankVerBindingRegDAO	wsBankVerBindingRegDAO;
	@Autowired
	private BranchInfoDAO			branchInfoDAO;
	@Autowired
	private CardInfoDAO				cardInfoDAO;
	@Autowired
	private UserCertificateRevService userCertificateRevService;
	
	private Paginater				page;
	private WsBankVerBindingReg		wsBankVerBindingReg;

	/** 发卡机构列表（发卡机构角色登录时在下拉框显示） */
	private List<BranchInfo>		cardBranchList;

	@Override
	public String execute() throws Exception {

		List<BranchInfo> inCardBranches = null;// 发卡机构范围条件

		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心，运营中心部门

		} else if (isCardRoleLogined()) {// 发卡机构
			inCardBranches = this.getMyCardBranch();
			this.cardBranchList = inCardBranches;
		} else {
			throw new BizException("没有权限查看银行卡绑定/解绑/默认卡 等级记录");
		}
		this.page = this.wsBankVerBindingRegDAO.findPage(wsBankVerBindingReg, inCardBranches,
				this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	/**
	 * 明细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Assert.isTrue(isCenterRoleLogined() || isCardOrCardDeptRoleLogined(), "没有权限查看该页面");
		
		wsBankVerBindingReg = (WsBankVerBindingReg) wsBankVerBindingRegDAO.findByPk(wsBankVerBindingReg
				.getBindingId());
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(
					branchInfoDAO.isSuperBranch(this.getLoginBranchCode(),
							wsBankVerBindingReg.getCardIssuer()), "没有权限访问该页面");
		}
		return DETAIL;
	}

	public String showAdd() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做此操作");
		this.checkEffectiveCertUser();
		if (SysparamCache.getInstance().isNeedSign()) {
			dealIsNeedSign();
			this.saveSessonLoginRandom();
		}
		
		return ADD;
	}

	public void cardIdCheck(){
		if (wsBankVerBindingReg == null || StringUtils.isBlank(wsBankVerBindingReg.getCardId())) {
			responseJsonObject("-1", "卡号不能为空");
			return;
		}
		BooleanMessage bm = validateCardId(this.wsBankVerBindingReg.getCardId());
		if(bm.isSucc()){
			this.responseJsonObject("1", "合法卡号");
		}else{
			this.responseJsonObject("-1", bm.getMsg());
		}
		return;
	}

	public String add() throws Exception {
		Assert.isTrue(isCardRoleLogined(), "非发卡机构不能做此操作");
		this.checkEffectiveCertUser();

		if (SysparamCache.getInstance().isNeedSign()) {
			Assert.isTrue( this.getSessonLoginRandom() != null && this.getSessonLoginRandom().equals(this.getFormMapValue("random")),
						"页面失效，请重新进入新增页面");
			checkUserSignatureSerialNo();
			boolean isSucc = userCertificateRevService.processUserSigVerify(this.getFormMapValue("serialNo"),
					this.getFormMapValue("mySign"),
					this.wsBankVerBindingReg.getCardId() + this.getSessonLoginRandom());
			Assert.isTrue(isSucc, "验证签名失败");
			this.clearSessonLoginRandom();
		}
		
		BooleanMessage bm = validateCardId(this.wsBankVerBindingReg.getCardId());
		Assert.isTrue(bm.isSucc(), bm.getMsg());
		
		this.wsBankVerBindingReg.setSetType("0"); //0：银行卡，1：预付卡
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(this.wsBankVerBindingReg.getCardId());
		this.wsBankVerBindingReg.setCardIssuer(cardInfo.getCardIssuer());
		this.wsBankVerBindingReg.setExtCardId(cardInfo.getExternalCardId());
		this.wsBankVerBindingReg.setRandom(RandomStringUtils.randomNumeric(10));
		this.wsBankVerBindingReg.setStatus(WsBankVerBindingRegState.PRE_PROCESS.getValue());
		this.wsBankVerBindingReg.setUpdateTime(new Date());
		this.wsBankVerBindingReg.setUpdateUser(this.getSessionUserCode());
		this.wsBankVerBindingReg.setVerStatus("0");
		
		wsBankVerBindingRegDAO.insert(this.wsBankVerBindingReg);
		
		MsgSender.sendMsg(MsgType.WS_BANK_VER_BINDING_REG, wsBankVerBindingReg.getBindingId(), this.getSessionUserCode());
		
		String msg = LogUtils.r("新增银行卡绑定/解绑/默认卡登记[{0}]成功", wsBankVerBindingReg.getBindingId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(
				"/intgratedService/wsBankCardBinding/wsBankVerBindingReg/list.do?goBack=goBack", msg);
		return SUCCESS;
	}

	/** 保存登录密码随机数 */
	private void saveSessonLoginRandom() {
		String random = RandomStringUtils.randomNumeric(16);
		this.formMap.put("random", random);
		WebUtils.setSessionAttribute(request, ADD_WS_BANK_VER_BINDING_RANDOM, random);
	}

	/** 获得会话保存的登录密码随机数 */
	private String getSessonLoginRandom() {
		return (String) WebUtils.getSessionAttribute(request, ADD_WS_BANK_VER_BINDING_RANDOM);
	}

	/** 清除会话保存的登录密码 */
	private void clearSessonLoginRandom() {
		WebUtils.setSessionAttribute(request, ADD_WS_BANK_VER_BINDING_RANDOM, null);
	}

	/** 验证卡号合法性 */
	private BooleanMessage validateCardId(String cardId){
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			return new BooleanMessage(false, "卡号[" + wsBankVerBindingReg.getCardId() + "]不存在，请重新录入卡号！");
		}
		if(!this.getLoginBranchCode().equals(cardInfo.getCardIssuer())){
			return new BooleanMessage(false, "卡号[" + wsBankVerBindingReg.getCardId() + "]的发卡机构不是用户所在的机构，无法操作本卡号！");
		}
		if (!CardState.ACTIVE.getValue().equals(cardInfo.getCardStatus())) {
			return new BooleanMessage(false, "卡状态不为“" + CardState.ACTIVE.getName() + "”！");
		}
		return new BooleanMessage(true);
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WsBankVerBindingReg getWsBankVerBindingReg() {
		return wsBankVerBindingReg;
	}

	public void setWsBankVerBindingReg(WsBankVerBindingReg wsBankVerBindingReg) {
		this.wsBankVerBindingReg = wsBankVerBindingReg;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}
}
