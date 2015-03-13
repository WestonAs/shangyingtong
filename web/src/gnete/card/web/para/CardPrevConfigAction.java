package gnete.card.web.para;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PublishNoticeService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: CardPrevConfigAction.java
 *
 * @description: 发卡机构卡前三位配置
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-5-9 上午10:43:50
 */
public class CardPrevConfigAction extends BaseAction{

	@Autowired
	private PublishNoticeService publishNoticeService;
	
	private String branchCode;

	private CardPrevConfig cardPrevConfig;
	private Paginater page;

	private String startDate;
	private String endDate;
	private String branchName; // 机构名称
	
	private static final String DEFAULT_LIST_PAGE = "/para/cardPrevConfig/list.do";
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardPrevConfig != null) {
			params.put("branchNo", cardPrevConfig.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(branchName));
			params.put("status", cardPrevConfig.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 分支机构可以查询
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.publishNoticeService.findCardPrevConfigPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CENTER.getValue().equals(getLoginRoleType())){
			throw new BizException("只有运营中心有权限进行此操作");
		} 
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CENTER.getValue().equals(getLoginRoleType())){
			throw new BizException("只有运营中心有权限进行此操作");
		} 
		
		this.publishNoticeService.addCardPrevConfig(cardPrevConfig, this.getSessionUser());	
		
		String msg = LogUtils.r("新增发卡机构[{0}]卡前三位配置成功！", cardPrevConfig.getBranchCode());
		
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_PAGE, msg);
		
		return SUCCESS;
	}

	
	/**
	 * 删除发卡机构卡前三位配置
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!RoleType.CENTER.getValue().equals(getLoginRoleType())){
			throw new BizException("只有运营中心有权限进行此操作");
		}
		
		this.publishNoticeService.deleteCardPrevConfig(branchCode);
		
		String msg = LogUtils.r("删除发卡机构[{0}]卡前三位配置成功！", branchCode);
		
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(DEFAULT_LIST_PAGE, msg);
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public CardPrevConfig getCardPrevConfig() {
		return cardPrevConfig;
	}

	public void setCardPrevConfig(CardPrevConfig cardPrevConfig) {
		this.cardPrevConfig = cardPrevConfig;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	

}
