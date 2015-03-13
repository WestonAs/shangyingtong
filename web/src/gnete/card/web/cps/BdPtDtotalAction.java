package gnete.card.web.cps;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BDPtDtotalDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BDPtDtotal;
import gnete.card.entity.state.CpStatus;
import gnete.card.service.CpsService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 保得积分统计查询
 * @author aps-lib
 * since 2011-8-19
 */
public class BdPtDtotalAction extends BaseAction{

	@Autowired
	private BDPtDtotalDAO bdPtDtotalDAO;
	@Autowired
	private CpsService cpsService;
	@Autowired
	private BranchInfoDAO	branchInfoDAO;

	private Paginater page;
	private BDPtDtotal bdPtDtotal;
	private boolean showMerch = false;
	private boolean showCard = false;
	private List<CpStatus> cpStatusList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		this.cpStatusList = CpStatus.getList();
		
		if (bdPtDtotal != null) {
			params.put("branchCode", bdPtDtotal.getCardIssuer());
			params.put("merNo", bdPtDtotal.getMerNo());
			params.put("cpSn", bdPtDtotal.getCpSn());
			params.put("cpDate", bdPtDtotal.getCpDate());
			params.put("returnDate", bdPtDtotal.getReturnDate());
			params.put("cpStatus", bdPtDtotal.getCpStatus());
			params.put("cardIssuerName", MatchMode.ANYWHERE.toMatchString(bdPtDtotal.getCardIssuerName()));
			params.put("merName", MatchMode.ANYWHERE.toMatchString(bdPtDtotal.getMerName()));
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {// 如果登录用户为运营中心，运营中心部门
			
		} else if (isFenzhiRoleLogined()) {// 分支机构，能查询其管理的发卡机构
			params.put("parent", getSessionUser().getBranchNo());
			
		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构、机构部门
			params.put("cardIssuer", getSessionUser().getBranchNo());
			
		} else if (isMerchantRoleLogined()) {// 商户
			params.put("merNo", getSessionUser().getMerchantNo());
			
		} else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.bdPtDtotalDAO.findBDPtDtotal(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		Assert.notNull(this.bdPtDtotal.getCpSn(), "代收费流水不能为空");
		this.bdPtDtotal = (BDPtDtotal) this.bdPtDtotalDAO.findByPk(this.bdPtDtotal.getCpSn());
		checkUserOprtPriv(bdPtDtotal);
		
		return DETAIL;
	}
	
	/** 手动发起代收 */
	public String collectManual() throws Exception {
		Assert.notNull(this.bdPtDtotal.getCpSn(), "代收费流水不能为空");
		BDPtDtotal bdPtDtotal = (BDPtDtotal) this.bdPtDtotalDAO.findByPk(this.bdPtDtotal.getCpSn());
		checkUserOprtPriv(bdPtDtotal);
		
		this.cpsService.sendCtManualMsg(bdPtDtotal, this.getSessionUser().getBranchNo(), this.getSessionUserCode());
		
		String msg = LogUtils.r("手动发起代收流水[{0}]成功！", this.bdPtDtotal.getCpSn());
		this.addActionMessage("/cps/BDPtDtotal/list.do?goBack=goBack", msg);
		
		return SUCCESS;
	}
	
	/** 手动确认代收 */
	public String collectConfirm() throws Exception {
		Assert.notNull(this.bdPtDtotal.getCpSn(), "代收费流水不能为空");
		BDPtDtotal bdPtDtotal = (BDPtDtotal) this.bdPtDtotalDAO.findByPk(this.bdPtDtotal.getCpSn());
		checkUserOprtPriv(bdPtDtotal);
		
		this.cpsService.sendCtConfirmMsg(bdPtDtotal, this.getSessionUser().getBranchNo(), this.getSessionUserCode());
		
		String msg = LogUtils.r("手动确认代收流水[{0}]成功！", this.bdPtDtotal.getCpSn());
		this.addActionMessage("/cps/BDPtDtotal/list.do?goBack=goBack", msg);
		
		return SUCCESS;
	}
	
	/** 检测用户是否可操作指定记录 */
	private void checkUserOprtPriv(BDPtDtotal entry) throws BizException {
		super.checkEffectiveCertUser();
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isCardOrCardDeptRoleLogined())) {
			throw new BizException("非运营中心、运营机构、发卡机构没有权限操作代收付记录！");
		}

		Assert.notNull(entry, "指定的记录不存在！");

		if (isFenzhiRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isDirectManagedBy(entry.getCardIssuer(), getLoginBranchCode()),
					"该用户不能操作指定的记录");
		} else if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isSuperBranch(getLoginBranchCode(), entry.getCardIssuer()),
					"登录的机构不是指定发卡机构的本身或上级机构！");
		}
	}
	
	// ------------------------------- getter and setter followed------------------------

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BDPtDtotal getBdPtDtotal() {
		return bdPtDtotal;
	}

	public void setBdPtDtotal(BDPtDtotal bdPtDtotal) {
		this.bdPtDtotal = bdPtDtotal;
	}

	public boolean isShowMerch() {
		return showMerch;
	}

	public void setShowMerch(boolean showMerch) {
		this.showMerch = showMerch;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}

	public List<CpStatus> getCpStatusList() {
		return cpStatusList;
	}

	public void setCpStatusList(List<CpStatus> cpStatusList) {
		this.cpStatusList = cpStatusList;
	}
}
