package gnete.card.web.user;

import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.entity.MakeCardPerson;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.IUserCardAssignService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @Project: MyCard
 * @File: UserCardAssignAction.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class UserCardAssignAction extends BaseAction {
	@Autowired
	@Qualifier("userCardAssignService")
	private IUserCardAssignService userCardAssignService;

	// -----------------------------------------显示查询-----------------------------------------------------------
	private Paginater cardPersonPage;    //制卡厂商选定用户列表信息

	private List<CommonState> cardPersonStateList;  //生效失效状态选择

	private boolean showAdd;    //控制添加制卡选择人按钮

	private String startDate;    //查询开始日期

	private String endDate;      //查询结束日期

	private String state;        //状态变量

	@Override
	public String execute() throws Exception {

		Object[] cardPersonListResult = getCardPersonListResult();

		if (CommonHelper.checkIsNotEmpty(cardPersonListResult)) {
			this.cardPersonPage = (Paginater) cardPersonListResult[0];
			this.showAdd = (Boolean) cardPersonListResult[1];
		}

		return LIST;
	}

	private Object[] getCardPersonListResult() throws Exception {
		if (!super.getLoginRoleType().equals(RoleType.CARD_MAKE.getValue())) {
			throw new BizException("您没有指定制卡人的权限!");
		}

		this.cardPersonStateList = CommonState.getList();

		return this.userCardAssignService
				.getUserCardAssignResult(getCardPersonParams(), getPageNumber(), getPageSize());
	}

	/**
	 * 
	 * @description：注意这里需要对日期做一个格式化的处理
	 * @return
	 * @version: 2010-12-21 下午09:18:28
	 * @See:
	 */
	private Map getCardPersonParams() {
		Map params = new HashMap();

		String branchCode = super.getLoginBranchCode();

		params.put("branchNo", branchCode);

		if (CommonHelper.checkIsNotEmpty(getStartDate())) {
			params.put("startDate", CommonHelper.getTruncateDayOfMonthDate(getStartDate(), "yyyyMMdd"));
		}

		if (CommonHelper.checkIsNotEmpty(getEndDate())) {
			Date endDate = CommonHelper.getTruncateDayOfMonthDate(this.endDate, "yyyyMMdd");
			params.put("endDate", CommonHelper.getCompareDate(endDate, 1));
		}

		if (CommonHelper.checkIsNotEmpty(getState())) {
			params.put("state", getState());
		}

		return params;
	}
	
	//------------------------------------------------处理明细-------------------------------------------------------	
	public String detail() throws Exception {
		this.cardPerson = this.userCardAssignService.getSelectMakeCardPerson(this.cardPerson);
		
		return DETAIL;
	}

	// -----------------------------------------------处理添加-------------------------------------------------------
	private List<UserInfo> userInfoList;     //制卡厂商机构下用户列表

	private String userId;                   //选定制卡人

	/**
	 * 
	 * @description：进入/pages/cardPerson/add.jsp
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午11:29:43
	 * @See:
	 */
	public String showAdd() throws Exception {

		this.userInfoList = this.userCardAssignService.getUserCardInfoList(super.getLoginBranchCode());

		return ADD;
	}

	/**
	 * 
	 * @description：处理制卡厂商选择人选定处理
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午11:30:51
	 * @See:
	 */
	public String add() throws Exception {

		UserInfo loginUser = super.getSessionUser();

		boolean addFlag = this.userCardAssignService.processUserCardPersonAdd(getUserId(), loginUser);

		if (addFlag) {
			String msg = "用户[" + loginUser.getUserId() + "]为机构[" + loginUser.getBranchNo()
			                    +"]添加制卡人[" + getUserId() +"]成功!";
			this.addActionMessage("/pages/cardPerson/list.do", msg);
			super.log(msg, UserLogType.ADD);
		}

		return SUCCESS;
	}

	// -----------------------------------------------处理编辑-------------------------------------------------------------
	private MakeCardPerson cardPerson;           //选择制卡人关联信息
	
    /**
	 * 
	 * @description：进入/pages/cardPerson/modify.jsp
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午07:17:01
	 * @See:
	 */
	public String showModify() throws Exception {
		Object[] cardPersonModifyResult = this.userCardAssignService.getUserCardModifyResult(getCardPerson());

		if (CommonHelper.checkIsEmpty(cardPersonModifyResult)) {
			String msg = "制卡机构当前只有当前用户[" + getCardPerson().getUserId() + "]，不能进行其他用户编辑!";
			this.addActionMessage("/pages/cardPerson/list.do", msg);
			super.log(msg, UserLogType.ADD);
			return SUCCESS;
		}

		this.cardPerson = (MakeCardPerson) cardPersonModifyResult[0];
		this.userInfoList = (List<UserInfo>) cardPersonModifyResult[1];

		return MODIFY;
	}

	/**
	 * 
	 * @description：
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午07:17:04
	 * @See:
	 */
	public String modify() throws Exception {
		UserInfo loginUser = super.getSessionUser();

		boolean modifyFlag = this.userCardAssignService.processUserCardPersonModify(getCardPerson(), getUserId(),
				loginUser);

		if (modifyFlag) {
			 String msg = "用户[" + loginUser.getUserId() + "]为机构[" + loginUser.getBranchNo()
                         +"]添加制卡人[" + getUserId() +"]并修改原制卡人[" + getCardPerson().getUserId() +  "]成功!";
			 this.addActionMessage("/pages/cardPerson/list.do", msg);
             super.log(msg, UserLogType.UPDATE);
		}

		return SUCCESS;
	}

	// -----------------------------------------------处理状态处理---------------------------------------------------------
	/**
	 * 
	 * @description：
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午11:36:28
	 * @See:
	 */
	public String cancle() throws Exception {

		UserInfo loginUser = super.getSessionUser();

		boolean cancelFlag = this.userCardAssignService.processUserCardPersonState(getCardPerson(),
				                        CommonState.DISABLE.getValue(), loginUser);

		if (cancelFlag) {
			 String msg = "用户[" + loginUser.getUserId() + "]为机构[" + loginUser.getBranchNo()
                         +"]设置制卡人[" + getCardPerson().getUserId() +"]状态为[" +  CommonState.DISABLE.getName()+  "]成功!";
			 this.addActionMessage("/pages/cardPerson/list.do", msg);
             super.log(msg, UserLogType.UPDATE);
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @description：
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午11:36:34
	 * @See:
	 */
	public String activate() throws Exception {
		UserInfo loginUser = super.getSessionUser();

		boolean activateFlag = this.userCardAssignService.processUserCardPersonState(getCardPerson(),
				CommonState.NORMAL.getValue(), loginUser);

		if (activateFlag) {
			 String msg = "用户[" + loginUser.getUserId() + "]为机构[" + loginUser.getBranchNo()
                            +"]设置制卡人[" + getCardPerson().getUserId() +"]状态为[" +  CommonState.NORMAL.getName()+  "]成功!";
			 this.addActionMessage("/pages/cardPerson/list.do", msg);
             super.log(msg, UserLogType.UPDATE);
             
		}

		return SUCCESS;
	}

	// -----------------------------------------------处理状态删除---------------------------------------------------------
	/**
	 * 
	 * @description：
	 * @return
	 * @throws Exception
	 * @version: 2010-12-21 下午11:36:51
	 * @See:
	 */
	public String delete() throws Exception {
		UserInfo loginUser = super.getSessionUser();

		boolean delFlag = this.userCardAssignService.processUserCardPersonDel(getCardPerson());

		if (delFlag) {
			String msg = "用户[" + loginUser.getUserId() + "]为机构[" + loginUser.getBranchNo()
                       +"]删除制卡人[" + getCardPerson().getUserId() +"]成功!";
			this.addActionMessage("/pages/cardPerson/list.do", msg);
            super.log(msg, UserLogType.DELETE);
		}

		return SUCCESS;
	}

	// -----------------------------------------------------------------------------------------------------
	public Paginater getCardPersonPage() {
		return cardPersonPage;
	}

	public void setCardPersonPage(Paginater cardPersonPage) {
		this.cardPersonPage = cardPersonPage;
	}

	public boolean isShowAdd() {
		return showAdd;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<CommonState> getCardPersonStateList() {
		return cardPersonStateList;
	}

	public void setCardPersonStateList(List<CommonState> cardPersonStateList) {
		this.cardPersonStateList = cardPersonStateList;
	}

	// ------------------------------------------------------------------------------------------------------------
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// ---------------------------------------------------------------------------------------------------------------
	public MakeCardPerson getCardPerson() {
		return cardPerson;
	}

	public void setCardPerson(MakeCardPerson cardPerson) {
		this.cardPerson = cardPerson;
	}
	
	
}
