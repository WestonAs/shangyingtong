package gnete.card.web.intgratedservice;

import flink.util.Paginater;
import gnete.card.dao.CardSmsRecordDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardSmsRecord;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardSmsRecordService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 手机短信营销
 */
public class CardSmsRecordAction extends BaseAction {
	@Autowired
	private CardSmsRecordService	cardSmsRecordService;
	@Autowired
	private CardSmsRecordDAO		cardSmsRecordDAO;

	private CardSmsRecord			cardSmsRecord;

	// 封装上传文件域的属性
	private File					upload;

	private Paginater				page;

	private List<BranchInfo>		cardBranchList;

	@Override
	public String execute() throws Exception {
		cardSmsRecord = cardSmsRecord != null ? cardSmsRecord : new CardSmsRecord();
		cardSmsRecord.setComeFrom("1");// 来自“手机短信营销”页面

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSmsRecord", cardSmsRecord);

		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心，运营中心部门

		} else if (isFenzhiRoleLogined()) { // 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardOrCardDeptRoleLogined()) { // 发卡机构或部门时
			this.cardBranchList = this.getMyCardBranch();
			params.put("cardBranchList", cardBranchList);

		} else {
			throw new BizException("没有权限查看短信记录");
		}
		this.page = this.cardSmsRecordDAO.findByPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		this.cardSmsRecord = (CardSmsRecord) cardSmsRecordDAO.findByPk(cardSmsRecord.getId());
		return DETAIL;
	}

	public String showAdd() throws Exception {
		checkAddPrivilege();
		return ADD;
	}
	
	/** 显示文件方式新增页面  */
	public String showFileModeAdd() throws Exception {
		checkAddPrivilege();
		return "fileModeAdd";
	}

	public String add() throws Exception {
		checkAddPrivilege();
		
		String mobiles = this.getFormMapValue("mobiles");
		Assert.notBlank(mobiles, "手机号不能为空");
		Assert.isTrue(mobiles.matches("[0-9\r\n]*"), "请输入合法的手机号");
		String[] mobileArr = mobiles.trim().split("\r\n");

		String smsSendContent = this.getFormMapValue("smsSendContent");
		Assert.notBlank(smsSendContent, "短信内容不能为空");

		cardSmsRecordService.addCardSmsRecords(mobileArr, smsSendContent.trim(), this.getSessionUser());

		String msg = String.format("新增待处理发送手机短信成功，共[%s]条", mobileArr.length);
		String retUrl = "/intgratedService/cardSmsRecord/list.do?goBack=goBack";
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(retUrl, msg);
		return SUCCESS;
	}

	/** 文件方式新增短信营销记录 */
	public String fileModeAdd() throws Exception{
		checkAddPrivilege();
		
		int addCnt = cardSmsRecordService.addCardSmsRecords(upload, this.getSessionUser());
		
		String msg = String.format("新增待处理发送手机短信成功，共[%s]条", addCnt);
		String retUrl = "/intgratedService/cardSmsRecord/list.do?goBack=goBack";
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(retUrl, msg);
		return SUCCESS;
	}
	
	
	
	private void checkAddPrivilege() throws BizException{
		if (!isCardRoleLogined()) {
			throw new BizException("非发卡机构不能做手机短信营销操作");
		}
		Assert.isTrue(cardSmsRecordDAO.isAuthorizedBranch(getLoginBranchCode()), "用户所在的发卡机构没有发送短信的权限!");
	}
	
	public CardSmsRecord getCardSmsRecord() {
		return cardSmsRecord;
	}

	public void setCardSmsRecord(CardSmsRecord cardSmsRecord) {
		this.cardSmsRecord = cardSmsRecord;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

}
