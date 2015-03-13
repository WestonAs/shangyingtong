package gnete.card.web.dict;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.BankNoDAO;
import gnete.card.entity.BankInfo;
import gnete.card.entity.BankNo;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: BankInfoAction.java
 *
 * @description: 银行行名查询
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-5
 */
public class BankInfoAction extends BaseAction {
	
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private BankNoDAO bankNoDAO;
	@Autowired
	private AreaDAO areaDAO;
	
	private Paginater page;
	
	private List<BankNo> bankTypeList;
	private List<NameValuePair> parentList;
	private List<NameValuePair> cityList;
	private List<CommonState> statusList;
	
	private BankInfo bankInfo;
	
	/** 界面选择时是否单选 */
	private boolean radio;
	
	private String areaSelect;// 省份
	private String bankTypeCode; // 行别
	private String cityCode; // 城市编码

	@Override
	public String execute() throws Exception {
		this.parentList = this.areaDAO.findParent();
		this.bankTypeList = this.bankNoDAO.findAll();
		this.statusList = CommonState.getList();
		
		if(bankInfo!=null && bankInfo.getParent()!=null && !bankInfo.getParent().equals("")){
			this.cityList = this.areaDAO.findCityByParent(bankInfo.getParent());
		}else{
			this.cityList = Collections.emptyList();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (bankInfo != null) {
			params.put("bankType", bankInfo.getBankType());
			params.put("bankName", MatchMode.ANYWHERE.toMatchString(bankInfo.getBankName()));
			params.put("province", bankInfo.getParent());
			params.put("cityCode", bankInfo.getCityCode());
			params.put("bankNo", bankInfo.getBankNo());
			params.put("status", bankInfo.getStatus());
		}
		this.page = this.bankInfoDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		
		this.bankTypeList = this.bankNoDAO.findAll();
		return ADD;
	}

	public String add() throws Exception {
		this.checkEffectiveCertUser();
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		
		Object bank = this.bankInfoDAO.findByPk(this.bankInfo.getBankNo());
		Assert.isNull(bank, "银行代码["+this.bankInfo.getBankNo()+"]已存在");
		
		this.bankInfo.setStatus("00");
		this.bankInfo.setUpdateby(this.getSessionUser().getUserId());
		this.bankInfo.setUpdatetime(new Date());
		this.bankInfoDAO.insert(this.bankInfo);
		
		String msg = LogUtils.r("添加银行代码[{0}]成功", this.bankInfo.getBankNo());
		this.addActionMessage("/pages/bankInfo/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}

	public String showModify() throws BizException {
		this.checkEffectiveCertUser();
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		
		this.bankInfo = (BankInfo)this.bankInfoDAO.findByPk(this.bankInfo.getBankNo());
		this.statusList = CommonState.getList();
		
		return MODIFY;
	}

	public String modify() throws Exception {
		this.checkEffectiveCertUser();
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "只有运营中心或部门才能做该操作！");
		
		//this.bankInfo.setStatus("00");
		this.bankInfo.setUpdateby(this.getSessionUser().getUserId());
		this.bankInfo.setUpdatetime(new Date());
		this.bankInfoDAO.update(this.bankInfo);
		
		String msg = LogUtils.r("修改银行代码[{0}]成功", this.bankInfo.getBankNo());
		this.addActionMessage("/pages/bankInfo/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String showSelect() throws Exception {
		this.parentList = this.areaDAO.findParent();
		this.bankTypeList = this.bankNoDAO.findAll();
		this.areaSelect = "5810";
		this.cityList = this.areaDAO.findCityByParent("5810");
		return "select";
	}
	
	/**
	 * 银行选择器
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (bankInfo != null) {
			params.put("bankType", bankInfo.getBankType());
			params.put("province", bankInfo.getParent()); // 省份
			params.put("cityCode", bankInfo.getAddrNo()); // 城市
			params.put("bankName", MatchMode.ANYWHERE.toMatchString(bankInfo.getBankName()));
		}
		params.put("status", CommonState.NORMAL.getValue());
		
		this.page = this.bankInfoDAO.find(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BankNo> getBankTypeList() {
		return bankTypeList;
	}

	public void setBankTypeList(List<BankNo> bankTypeList) {
		this.bankTypeList = bankTypeList;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getAreaSelect() {
		return areaSelect;
	}

	public void setAreaSelect(String areaSelect) {
		this.areaSelect = areaSelect;
	}

	public BankInfo getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}

	public List<NameValuePair> getParentList() {
		return parentList;
	}

	public void setParentList(List<NameValuePair> parentList) {
		this.parentList = parentList;
	}

	public String getBankTypeCode() {
		return bankTypeCode;
	}

	public void setBankTypeCode(String bankTypeCode) {
		this.bankTypeCode = bankTypeCode;
	}

	public List<NameValuePair> getCityList() {
		return cityList;
	}

	public void setCityList(List<NameValuePair> cityList) {
		this.cityList = cityList;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
	}

}
