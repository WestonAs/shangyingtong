package gnete.card.web.intgratedservice.wsBankCardBinding;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.WsBankCardBindingInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.WsBankCardBindingInfo;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 绑定银行卡查询 处理
 */
@SuppressWarnings("serial")
public class WsBankCardBindingInfoAction extends BaseAction {
	@Autowired
	private WsBankCardBindingInfoDAO	wsBankCardBindingInfoDAO;
	@Autowired
	private BranchInfoDAO				branchInfoDAO;

	private Paginater					page;
	private WsBankCardBindingInfo		wsBankCardBindingInfo;

	/** 发卡机构列表（发卡机构角色登录时在下拉框显示） */
	private List<BranchInfo>			cardBranchList;

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
		this.page = this.wsBankCardBindingInfoDAO.findPage(wsBankCardBindingInfo, inCardBranches,
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
		
		wsBankCardBindingInfo = (WsBankCardBindingInfo) wsBankCardBindingInfoDAO
				.findByPk(wsBankCardBindingInfo.getSeqId());
		
		if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(
					branchInfoDAO.isSuperBranch(this.getLoginBranchCode(),
							wsBankCardBindingInfo.getCardIssuer()), "没有权限访问指定的赠送申请记录");
		}
		return DETAIL;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public WsBankCardBindingInfo getWsBankCardBindingInfo() {
		return wsBankCardBindingInfo;
	}

	public void setWsBankCardBindingInfo(WsBankCardBindingInfo wsBankCardBindingInfo) {
		this.wsBankCardBindingInfo = wsBankCardBindingInfo;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}
}
