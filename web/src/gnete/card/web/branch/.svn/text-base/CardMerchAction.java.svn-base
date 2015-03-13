package gnete.card.web.branch;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardToMerchDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardToMerch;
import gnete.card.entity.CardToMerchKey;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.state.CardMerchState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.BranchService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardMerchAction.java
 *
 * @description: 商户关系管理
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: Henry
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-7-7
 */
public class CardMerchAction extends BaseAction {

	@Autowired
	private CardToMerchDAO cardToMerchDAO;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	@Autowired
	private MerchInfoDAO merchInfoDAO;

	private Paginater page;
	
	private CardToMerch cardToMerch;
	private List<CardMerchState> statusList;
	
	private String merchs;
	
	private String areaCode;// 发卡机构地区码
	private String areaName;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.statusList = CardMerchState.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardToMerch != null) {
			params.put("merchName", MatchMode.ANYWHERE.toMatchString(cardToMerch.getMerchId()));
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(cardToMerch.getBranchCode()));
			params.put("proxyName", MatchMode.ANYWHERE.toMatchString(cardToMerch.getProxyId()));
			params.put("areaCode", areaCode);
			params.put("status", cardToMerch.getStatus());
		}
		
		// 分不同角色查看不同的数据
		List<BranchInfo> branchs = new ArrayList<BranchInfo>();
		List<MerchInfo> merchs = new ArrayList<MerchInfo>();
		List<BranchInfo> proxys = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心

		} else if (isFenzhiRoleLogined()) {// 分支机构可以查看自己管理的发卡机构、商户的
			// branchs = this.getMyCardBranch();
			// merchs = this.getMyMerch();
			// if (CollectionUtils.isEmpty(branchs)) {
			// branchs.add(new BranchInfo());
			// }
			// if (CollectionUtils.isEmpty(merchs)) {
			// merchs.add(new MerchInfo());
			// }
			params.put("fenzhiCode", this.getLoginBranchCode());

		} else if (isCardRoleLogined()) {// 发卡机构可以查看发卡机构为自己的
			branchs.add((BranchInfo) this.branchInfoDAO.findByPk(this.getLoginBranchCode()));

		} else if (isMerchantRoleLogined()) {// 商户可以查看商户为自己的
			merchs.add((MerchInfo) this.merchInfoDAO.findByPk(this.getLoginBranchCode()));

		} else if (isCardMerchantRoleLogined()) {// 发卡机构商户代理
			proxys.add((BranchInfo) this.branchInfoDAO.findByPk(this.getLoginBranchCode()));

		} else {
			throw new BizException("没有权限查看商户关系!");
		}
		
		params.put("branchs", branchs);
		params.put("merchs", merchs);
		params.put("proxys", proxys);
		this.page = this.cardToMerchDAO.find(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.cardToMerch = (CardToMerch) this.cardToMerchDAO.findByPk(
				new CardToMerchKey(this.cardToMerch.getBranchCode(), this.cardToMerch.getMerchId()));
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护商户关系！");
		}
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		this.branchService.addCardMerch(this.cardToMerch, merchs.split(","), this.getSessionUserCode());

		String msg = LogUtils.r("添加发卡机构与商户关系[{0}, {1}]成功", this.cardToMerch.getBranchCode(), merchs);
		this.addActionMessage("/pages/cardMerch/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护商户关系！");
		}
		String branchCode = request.getParameter("branchCode");
		String merchId = request.getParameter("merchId");
		this.branchService.cancelCardMerch(new CardToMerchKey(branchCode, merchId), this.getSessionUserCode());

		String msg = LogUtils.r("注销发卡机构与商户关系[{0}, {1}]成功", branchCode, merchId);
		this.addActionMessage("/pages/cardMerch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String activate() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护商户关系！");
		}
		String branchCode = request.getParameter("branchCode");
		String merchId = request.getParameter("merchId");
		this.branchService.activateCardMerch(new CardToMerchKey(branchCode, merchId), this.getSessionUserCode());

		String msg = LogUtils.r("生效发卡机构与商户关系[{0}, {1}]成功", branchCode, merchId);
		this.addActionMessage("/pages/cardMerch/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined())) {
			throw new BizException("没有权限维护商户关系！");
		}
		String branchCode = request.getParameter("branchCode");
		String merchId = request.getParameter("merchId");
		this.branchService.deleteCardMerch(new CardToMerchKey(branchCode, merchId), this.getSessionUserCode());
		
		String msg = LogUtils.r("删除发卡机构与商户关系[{0}, {1}]成功", branchCode, merchId);
		this.addActionMessage("/pages/cardMerch/list.do", msg);
		this.log(msg, UserLogType.DELETE);
		return SUCCESS;
	}
	
	public CardToMerch getCardToMerch() {
		return cardToMerch;
	}

	public void setCardToMerch(CardToMerch cardToMerch) {
		this.cardToMerch = cardToMerch;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getMerchs() {
		return merchs;
	}

	public void setMerchs(String merchs) {
		this.merchs = merchs;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<CardMerchState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CardMerchState> statusList) {
		this.statusList = statusList;
	}

}
