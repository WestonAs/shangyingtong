package gnete.card.web.customerservice;

import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardDeferRegDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardDeferReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.ExpirMthdType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SubacctType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardDeferRegService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 单张卡延期
 * @author aps-lib
 *
 */
public class CardDeferRegAction extends BaseAction {

	@Autowired
	private CardDeferRegDAO cardDeferDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	@Autowired
	private SubAcctBalDAO subAcctBalDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private  CardDeferRegService cardDeferRegService;

	private List<BranchInfo> cardBranchList;
	private CardDeferReg cardDeferReg;
	private Long cardDeferId;
	private Paginater page;
	private File upload;
	private String uploadFileName;

	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (cardDeferReg != null) {
			params.put("cardDeferId", cardDeferReg.getCardDeferId());
			params.put("cardId", cardDeferReg.getCardId());
			params.put("effDate", cardDeferReg.getEffDate());
			params.put("expirDate", cardDeferReg.getExpirDate());
		}

		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())) {
			params.put("cardIssuers", this.getMyCardBranch());
		}
		// 发卡机构部门
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getDeptId());
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		params.put("isBatch", false); //不是批量

		this.page = this.cardDeferDAO.findCardDeferPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询单张卡延期列表");
		
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.cardDeferReg = (CardDeferReg) this.cardDeferDAO.findByPk(this.cardDeferReg.getCardDeferId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){ 
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
//		initPage();
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {			
		
		this.cardDeferRegService.addCardDefer(cardDeferReg,  this.getSessionUser());
		String msg = "卡延期登记成功！延期ID为[" + this.cardDeferReg.getCardDeferId() + "]";
		this.addActionMessage("/carddDefer/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws BizException {
		
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){ 
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
//		initPage();
		this.cardDeferReg = (CardDeferReg) this.cardDeferDAO.findByPk(this.cardDeferReg.getCardDeferId());

		return MODIFY;
	}
	
	// 修改
	public String modify() throws Exception {
		this.cardDeferRegService.modifyCardDefer(cardDeferReg,  this.getSessionUser());
		String msg = "修改延期信息成功，延期ID[" + this.cardDeferReg.getCardDeferId() + "]！";
		this.addActionMessage("/carddDefer/list.do", msg);
		return SUCCESS;
	}
	
	// 删除挂失信息
	public String delete() throws Exception{
		
		this.operatePrivilege();
		this.cardDeferRegService.delete(this.getCardDeferId());
		String msg = "删除卡延期信息成功，ID[" + this.getCardDeferId() + "]！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/carddDefer/list.do", msg);
		return SUCCESS;
	}
	
	// 打开新增页面的初始化操作
	public String showFileCardDeferReg() throws Exception {
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_DEPT.getValue())&&
				!this.getLoginRoleType().equals(RoleType.CARD_SELL.getValue())){ 
			throw new BizException("非发卡机构、机构网点或者售卡代理不能操作。");
		}
//		initPage();
		return "addFileCardDeferReg";
	}
	
	// 新增对象操作
	public String addFileCardDeferReg() throws Exception {
		this.cardDeferRegService.addFileCardDeferReg(upload, uploadFileName, this.getSessionUser());
		
		String msg = "文件批量添加卡延期全部成功！";
		this.addActionMessage("/carddDefer/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	/**
	 * 审核列表
	 * @return
	 * @throws Exception
	 */
	public String checkList() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 发卡机构可以审核自己发的卡和自己领的卡的记录
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		}
		// 发卡机构部门
		else if (RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getDeptId());
		}
		// 售卡代理
		else if (RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())) {
			params.put("cardBranchCheck", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限做卡延期审核");
		}
		
		// 首先调用流程引擎，得到我的待审批的工作单ID
		String[] ids = this.workflowService.getMyJob(WorkflowConstants.WORKFLOW_CARD_DEFFER, this.getSessionUser());
		
		if (ArrayUtils.isEmpty(ids)) {
			return CHECK_LIST;
		}
		
		params.put("ids", ids);
		
		this.page = this.cardDeferDAO.findCardDeferPage(params, this.getPageNumber(), this.getPageSize());
		
		return CHECK_LIST;
	}
	
	// 根据卡号找到原失效日期
	public void getExpirDate() throws Exception {
		JSONObject object = new JSONObject();
		try {
			String cardId = this.cardDeferReg.getCardId();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(StringUtils.trim(cardId));
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在,请重新输入。");
			
			CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cardSubClassDef, "卡号[" + cardId + "]所属的卡子类型不存在");
			
			// 指定失效日期，到该日期失效，已制卡、已入库、已领卡待售、预售出、正常、已过期的卡能够延期
			if(ExpirMthdType.EXPIR_DATE.getValue().equals(cardSubClassDef.getExpirMthd())){
				List<String> list = new ArrayList<String>();
				list.add(CardState.MADED.getValue());
				list.add(CardState.STOCKED.getValue());
				list.add(CardState.FORSALE.getValue());
				list.add(CardState.PRESELLED.getValue());
				list.add(CardState.ACTIVE.getValue());
				list.add(CardState.EXCEEDED.getValue());
				Assert.isTrue(list.contains(cardInfo.getCardStatus()), "卡号[" + cardInfo.getCardId()
						+ "]指定了失效日期，卡状态不能为[" + cardInfo.getCardStatusName() + "], 不能延期。");
			}
			// 指定有效期（月数），从售卡日起，经过该有效期月数失效，正常、已过期的卡能够延期
			else if (ExpirMthdType.EXPIR_MONTH.getValue().equals(cardSubClassDef.getExpirMthd())) {
				List<String> list = Arrays.asList(CardState.ACTIVE.getValue(), CardState.EXCEEDED.getValue());
				Assert.isTrue(list.contains(cardInfo.getCardStatus()), "卡号[" + cardInfo.getCardId()
						+ "]指定了售卡后失效月数，卡状态不能为[" + cardInfo.getCardStatusName() + "], 不能延期。");
			}
			
			Assert.notTrue(cardInfo.getExtenLeft() <= 0, "卡号[" + cardInfo.getCardId() + "]剩余延期次数为0, 不能延期。");
			
			//检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo, "卡延期");

			Assert.notEmpty(cardInfo.getExpirDate(), "卡号失效日期不能为空,请重新输入有效卡号。");
			
			AcctInfo acctInfo = acctInfoDAO.findAcctInfoByCardId(cardInfo.getCardId());
			BigDecimal balance = BigDecimal.ZERO;// 余额（充值资金余额 + 返利直接余额）
			if(acctInfo!=null){
				SubAcctBal rebateBal = (SubAcctBal) this.subAcctBalDAO.findByPk(acctInfo.getAcctId(), SubacctType.REBATE.getValue());
				if (rebateBal != null) {
					balance = balance.add(rebateBal.getAvlbBal());
				}
				SubAcctBal depositBal = (SubAcctBal) this.subAcctBalDAO.findByPk(acctInfo.getAcctId(), SubacctType.DEPOSIT.getValue());
				if (depositBal != null) {
					balance = balance.add(depositBal.getAvlbBal());
				}
			}
			object.put("success", true);
			object.put("expirDate", cardInfo.getExpirDate());
			object.put("cardStatusName", cardInfo.getCardStatusName());
			object.put("balance", balance.setScale(2, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	public CardDeferReg getCardDeferReg() {
		return cardDeferReg;
	}

	public void setCardDeferReg(CardDeferReg cardDeferReg) {
		this.cardDeferReg = cardDeferReg;
	}

	public Long getCardDeferId() {
		return cardDeferId;
	}

	public void setCardDeferId(Long cardDeferId) {
		this.cardDeferId = cardDeferId;
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

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
