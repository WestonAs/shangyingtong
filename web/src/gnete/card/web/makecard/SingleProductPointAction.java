package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.PtExchgRuleType;
import gnete.card.entity.type.PtInstmMthdType;
import gnete.card.entity.type.PtUsageType;
import gnete.card.entity.type.PtUseLimitType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CardTypeSetService;
import gnete.card.service.SingleProductTemplateService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: SingleProductAction.java
 * 
 * @description:单机产品积分模板类型
 * 
 * @copyright: (c) 2012 YLINK INC.
 * @author: aps-hc
 * @version: 1.0
 * @since 1.0 2012-2-23
 */

public class SingleProductPointAction extends BaseAction {
	@Autowired
	private SingleProductTemplateService singleProductTemplateService;
	@Autowired
	private PointClassTempDAO pointClassTempDAO;
	@Autowired
	private CardTypeSetService cardTypeSetService;
	private Paginater page;
	private PointClassTemp pointClassTemp;
	private Collection ptUsageList;//积分用途
	private Collection amtTypeList;//金额类型
	private Collection ptInstmMthdList;//积分分期方法
	private List<PtUseLimitType> ptUseLimitList;//积分用途
	private String[] ptUseLimitCodes;
	private Collection ptExchgRuleTypeList;//积分兑换规则类型
	
	private static final String  DEFAULT_SUCCESS_URL="/pages/singleProduct/point/list.do?goBack=goBack";
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 登录用户为运营中心时，可查看所有信息
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 若当前登录用户角色为运营机构
		else if (RoleType.FENZHI.getValue().equals(this.getLoginRoleType())) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} 
		else {
			throw new BizException("没有权限查看积分类型模板列表");
		}
		ptUsageList = PtUsageType.getAll();
		if(pointClassTemp != null){
			params.put("className", MatchMode.ANYWHERE.toMatchString(pointClassTemp.getClassName()));
			params.put("ptUsage", pointClassTemp.getPtUsage());
		}
		page = pointClassTempDAO.findPointClassTemp(params, getPageNumber(), getPageSize());
		return LIST;
	}
	public String showAdd()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能定义积分类型模板。");
		}
		initPage();
		ptExchgRuleTypeList=PtExchgRuleType.getFullExchange();//积分类型只显示 满积分起兑
		
		return ADD;
	}

	public String add()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能定义积分类型模板。");
		}
		pointClassTemp.setBranchCode(getSessionUser().getBranchNo());//支构设置为当前登陆用户的机构代码
		singleProductTemplateService.addPointClassTemp(pointClassTemp, ptUseLimitCodes);
		String msg = "添加积分类型模板[" + pointClassTemp.getPtClass() + "]成功";
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String showModify()throws Exception{
		if(!RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			throw new BizException("非分支机构不能修改积分类型模板。");
		}
		initPage();
		pointClassTemp = (PointClassTemp)this.pointClassTempDAO.findByPk(pointClassTemp.getPtClass());
		return MODIFY;
	}
	public String detail()throws Exception{
		this.pointClassTemp = (PointClassTemp)this.pointClassTempDAO.findByPk(pointClassTemp.getPtClass());
		
		String ptUseLimit = this.pointClassTemp.getPtUseLimit();
		
		if(ptUseLimit!=null){
			this.pointClassTemp.setPtUseLimit(cardTypeSetService.getPtUseLimit(ptUseLimit));
		}
		
//		this.log("查询积分类型["+this.pointClassTemp.getPtClass()+"]信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	public String modify()throws Exception{
		this.singleProductTemplateService.modifyPointClassTemp(pointClassTemp, ptUseLimitCodes,this.getSessionUser());
		String msg = "修改积分类型模板[" + pointClassTemp.getPtClass() + "]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	public String delete()throws Exception{
		this.singleProductTemplateService.deletePointClassTemp(pointClassTemp);
		String msg = "删除积分类型模板[" + pointClassTemp.getPtClass() + "]成功";
		this.log(msg, UserLogType.DELETE);
		addActionMessage(DEFAULT_SUCCESS_URL, msg);
		return SUCCESS;
	}
	private void initPage(){
		amtTypeList = AmtType.getAll();
		ptUsageList = PtUsageType.getAllExcBaode();
		ptInstmMthdList = PtInstmMthdType.getAll();
		ptUseLimitList = PtUseLimitType.getAll();
		ptExchgRuleTypeList=PtExchgRuleType.getAll();
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public PointClassTemp getPointClassTemp() {
		return pointClassTemp;
	}

	public void setPointClassTemp(PointClassTemp pointClassTemp) {
		this.pointClassTemp = pointClassTemp;
	}

	public Collection getPtUsageList() {
		return ptUsageList;
	}

	public void setPtUsageList(Collection ptUsageList) {
		this.ptUsageList = ptUsageList;
	}
	public Collection getAmtTypeList() {
		return amtTypeList;
	}
	public void setAmtTypeList(Collection amtTypeList) {
		this.amtTypeList = amtTypeList;
	}
	public PointClassTempDAO getPointClassTempDAO() {
		return pointClassTempDAO;
	}
	public void setPointClassTempDAO(PointClassTempDAO pointClassTempDAO) {
		this.pointClassTempDAO = pointClassTempDAO;
	}
	public Collection getPtInstmMthdList() {
		return ptInstmMthdList;
	}
	public void setPtInstmMthdList(Collection ptInstmMthdList) {
		this.ptInstmMthdList = ptInstmMthdList;
	}
	public List<PtUseLimitType> getPtUseLimitList() {
		return ptUseLimitList;
	}
	public void setPtUseLimitList(List<PtUseLimitType> ptUseLimitList) {
		this.ptUseLimitList = ptUseLimitList;
	}
	public String[] getPtUseLimitCodes() {
		return ptUseLimitCodes;
	}
	public void setPtUseLimitCodes(String[] ptUseLimitCodes) {
		this.ptUseLimitCodes = ptUseLimitCodes;
	}
	public Collection getPtExchgRuleTypeList() {
		return ptExchgRuleTypeList;
	}
	public void setPtExchgRuleTypeList(Collection ptExchgRuleTypeList) {
		this.ptExchgRuleTypeList = ptExchgRuleTypeList;
	}

}
