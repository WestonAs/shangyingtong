package gnete.card.web.businessSubAccount;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankAcctInfoDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.entity.Area;
import gnete.card.entity.BankAcct;
import gnete.card.entity.BankInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.CustType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountInfoAction extends BaseAction {

	
	
	private static final long serialVersionUID = 1L;
	private BankAcct bankAcct;
	private Paginater page;
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BankAcct getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(BankAcct bankAcct) {
		this.bankAcct = bankAcct;
	}
	@Autowired
	private BankAcctInfoDAO bankAcctInfoDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private AreaDAO areaDAO;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if(bankAcct != null){
			params.put("acctNo", bankAcct.getAcctNo());
			params.put("custId", bankAcct.getCustId());
			params.put("acctName", bankAcct.getAcctName());
			params.put("bankNo", bankAcct.getBankNo());
		}
		String custId = "";
		if (isMerchantRoleLogined()) {
			custId = getSessionUser().getMerchantNo();
		} else {
			custId = getSessionUser().getBranchNo();
		}
		params.put("custId", custId);
		page = this.bankAcctInfoDAO.findPaginater(params, this.getPageNumber(), this.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			BankAcct bankAcct = (BankAcct)page.getList().get(i);
			BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankAcct.getBankNo());
			if (bankInfo != null) {
				bankAcct.setBankName(bankInfo.getBankName());
				String bankAdd = bankInfo.getAddrNo();
				Area area = (Area)areaDAO.findByPk(bankAdd);
				bankAcct.setBankAdd(area.getAreaName());
			}
		}
		return LIST;
	}

	public String showAdd() throws Exception {
		return ADD;
	}
	public String add() throws Exception {
		String acctNo = bankAcct.getAcctNo();
		BankAcct ba = (BankAcct)bankAcctInfoDAO.findByPk(acctNo);
		Assert.isNull(ba, "账号已存在");
		String msg = LogUtils.r("添加银行账户信息[{0}]成功", bankAcct.getAcctNo());
		this.log(msg, UserLogType.ADD);
		UserInfo userInfo = getSessionUser();
		String custId = "";
		String custType = "";
		if (isMerchantRoleLogined()) {
			custId = userInfo.getMerchantNo();
			custType = CustType.TYPE_MERCHANT.getValue();
		} else {
			custId = userInfo.getBranchNo();
			custType = CustType.TYPE_BRANCH.getValue();
		}
		bankAcct.setCustType(custType);
		bankAcct.setCustId(custId);
		bankAcct.setCreateTime(new Date());
		bankAcct.setUpdateTime(new Date());
		bankAcctInfoDAO.insert(bankAcct);
		this.addActionMessage("/businessSubAccount/bankAccountMng/list.do", msg);
		return SUCCESS;
	}
	public String showModify() throws Exception {
		bankAcct = (BankAcct)bankAcctInfoDAO.findByPk(bankAcct.getAcctNo());
		String bankNo = bankAcct.getBankNo();
		BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankNo);
		if (bankInfo != null) {
			bankAcct.setBankName(bankInfo.getBankName());
		}
		return MODIFY;
	}

	public String modify() throws Exception {
		BankAcct bankAcct1 = (BankAcct)bankAcctInfoDAO.findByPk(bankAcct.getAcctNo());
		bankAcct1.setUpdateTime(new Date());
		bankAcct1.setRemark(bankAcct.getRemark());
		bankAcct1.setAcctName(bankAcct.getAcctName());
		bankAcct1.setBankNo(bankAcct.getBankNo());
		bankAcctInfoDAO.update(bankAcct1);
		String msg = LogUtils.r("修改银行账户信息[{0}]成功", bankAcct.getAcctNo());
		this.log(msg, UserLogType.UPDATE);
		this.addActionMessage("/businessSubAccount/bankAccountMng/list.do", msg);
		return SUCCESS;
	}
	public String delete() throws Exception {
		String msg = LogUtils.r("删除银行账户信息[{0}]成功", bankAcct.getAcctNo());
		bankAcctInfoDAO.delete(bankAcct.getAcctNo());
		this.addActionMessage("/businessSubAccount/bankAccountMng/list.do", msg);
		return SUCCESS;
	}
	public String detail() throws Exception {
		bankAcct = (BankAcct)bankAcctInfoDAO.findByPk(bankAcct.getAcctNo());
		bankAcct = (BankAcct)bankAcctInfoDAO.findByPk(bankAcct.getAcctNo());
		String bankNo = bankAcct.getBankNo();
		BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankNo);
		if (bankInfo != null) {
			bankAcct.setBankName(bankInfo.getBankName());
		}
		return DETAIL;
	}
	
}
