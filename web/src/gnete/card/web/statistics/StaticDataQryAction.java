package gnete.card.web.statistics;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.GdnhglIssuerCardStaticDAO;
import gnete.card.dao.GdysIssuerTermStaticDAO;
import gnete.card.dao.LogIpGuardDAO;
import gnete.card.dao.StatisticsCardInfoDAO;
import gnete.card.dao.TransCenterMothDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.GdnhglIssuerCardStatic;
import gnete.card.entity.GdysIssuerTermStatic;
import gnete.card.entity.LogIpGuard;
import gnete.card.entity.StatisticsCardBjInfo;
import gnete.card.entity.StatisticsCardInfo;
import gnete.card.entity.TransCenterMoth;
import gnete.card.service.StaticDataQryFileService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统统计数据查询 处理
 */
@SuppressWarnings("serial")
public class StaticDataQryAction extends BaseAction {

	private Paginater			page;
	
	//实体类
	private LogIpGuard logIpGuard;//ip监控
	
	private TransCenterMoth transCenterMoth;//分支机构月消费
	
	private GdysIssuerTermStatic gdysIssuerTermStatic;//银商终端月消费
	
	private GdnhglIssuerCardStatic gdnhglIssuerCardStatic;//南湖国旅月统计
	
	private StatisticsCardInfo statisticsCardInfo;//吉之岛统计
	
	private StatisticsCardBjInfo statisticsCardBjInfo;
	
    private String startDate;
	
	private String endDate;
	
	//DAO
	@Autowired
	private TransCenterMothDAO transCenterMothDAO;
	@Autowired
	private LogIpGuardDAO logIpGuardDAO;
	@Autowired
	private GdysIssuerTermStaticDAO gdysIssuerTermStaticDAO;
	@Autowired
	private GdnhglIssuerCardStaticDAO gdnhglIssuerCardStaticDAO;
	@Autowired
	private StatisticsCardInfoDAO statisticsCardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	//service
	@Autowired
	private StaticDataQryFileService staticDataQryFileService;
	
	//property
	private String branchName;
	private String merchName;

	@Override
	public String execute() throws Exception {
		throw new BizException("不支持该方法");
	}
    
	/**ip地址监控*/
	public String ipStaticList() throws Exception {
		checkRoleLogined();
		
		Map<String,Object> params = new HashMap<String,Object>();
		if(logIpGuard != null){
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("branchNo", logIpGuard.getBranchNo());
			params.put("fuzzyBranchName",MatchMode.ANYWHERE.toMatchString(logIpGuard.getBranchName()));
			params.put("fuzzyMerchName", MatchMode.ANYWHERE.toMatchString(logIpGuard.getMerchName()));
			System.out.println(MatchMode.ANYWHERE.toMatchString(logIpGuard.getBranchName()));
		}
		
		this.page = this.logIpGuardDAO.findLogIpGuard(params, this.getPageNumber(), this.getPageSize());
		return "ipStaticList";
	}

	public void ipStaticExport() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		if(logIpGuard != null){
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("branchNo", logIpGuard.getBranchNo());
			params.put("fuzzyBranchName",MatchMode.ANYWHERE.toMatchString(logIpGuard.getBranchName()));
			params.put("fuzzyMerchName", MatchMode.ANYWHERE.toMatchString(logIpGuard.getMerchName()));
			System.out.println(MatchMode.ANYWHERE.toMatchString(logIpGuard.getBranchName()));
		}
		this.staticDataQryFileService.generateipStaticExcel(response, params);
	}
    
	/**各分支机构月消费数据*/
	@SuppressWarnings("unchecked")
	public String fenZhiConsumeStaticList() throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(isCenterOrCenterDeptRoleLogined()){
			if(transCenterMoth != null){
				params.put("month",transCenterMoth.getMonth());
				params.put("parent",transCenterMoth.getParent());
			}
			
		}else if(isFenzhiRoleLogined()){
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
			branchName = branchInfo.getBranchName();
			
			if(transCenterMoth != null){
				if(!transCenterMoth.getMonth().equals("")){
					params.put("month",transCenterMoth.getMonth());
					params.put("parent",this.getSessionUser().getBranchNo());
				}else{
					params.put("month",transCenterMoth.getMonth());
					params.put("parent",this.getSessionUser().getBranchNo());
					params.put("fenzhiList",this.getMyManageFenzhi());
				}
			}
			
		}else{
			throw new Exception("没有权限查看页面");
		}
		
	    this.page = this.transCenterMothDAO.findTransCenterMoth(params, this.getPageNumber(), this.getPageSize());
		return "fenZhiConsumeStaticList";
	}

	public void fenZhiConsumeStaticExport() throws Exception {
		
		Map<String,Object> params = new HashMap<String,Object>();
        if(isCenterOrCenterDeptRoleLogined()){
        	if(transCenterMoth != null){
				params.put("month",transCenterMoth.getMonth());
				params.put("parent",transCenterMoth.getParent());
			}
		}else if(isFenzhiRoleLogined()){
			BranchInfo branchInfo = (BranchInfo) this.branchInfoDAO.findByPk(this.getSessionUser().getBranchNo());
			branchName = branchInfo.getBranchName();
			
			if(transCenterMoth != null){
				if(!transCenterMoth.getMonth().equals("")){
					params.put("month",transCenterMoth.getMonth());
					params.put("parent",this.getSessionUser().getBranchNo());
				}else{
					params.put("month",transCenterMoth.getMonth());
					params.put("parent",this.getSessionUser().getBranchNo());
					params.put("fenzhiList",this.getMyManageFenzhi());
				}
			}
			
		}
		this.staticDataQryFileService.generatefenZhiConsumeStaticExcel(params,response);
	}

	/**广东银商终端统计*/
	public String gdysTermConsumeMStaticList() throws Exception {
		checkRoleLogined();
		this.page = this.gdysIssuerTermStaticDAO.findGdysIssuerTermStatic(gdysIssuerTermStatic,this.getPageNumber(),
				 this.getPageSize());
		return "gdysTermConsumeMStaticList";
	}
	
	public void gdysTermConsumeMStaticExport() throws Exception {
		
		this.staticDataQryFileService.generateGdysTermConsumeMStaticExcel(response, gdysIssuerTermStatic);
	}

	/**南湖国旅消费充值卡余额月统计*/
	public String nhglStaticList() throws Exception {
		checkRoleLogined();
		this.page = this.gdnhglIssuerCardStaticDAO.findGdnhglIssuerCardStatic(gdnhglIssuerCardStatic, 
				this.getPageNumber(), this.getPageSize());
		return "nhglStaticList";
	}

	public void nhglStaticExport() throws Exception {
		
		this.staticDataQryFileService.generateNhglStaticExcel(response, gdnhglIssuerCardStatic);
	}
	
	
	public String bjStaticList() throws Exception {
		checkRoleLogined();
		this.page = this.statisticsCardInfoDAO.findStatisticsCardBjInfo(statisticsCardBjInfo,this.getPageNumber(),this.getPageSize());
		return "bjStaticList";
	}
	
	public void bjStaticExport() throws Exception {
		this.staticDataQryFileService.generateBjStaticExcel(response, statisticsCardBjInfo);
	}
    
	public String jzdStaticList() throws Exception {
		checkRoleLogined();
		this.page = this.statisticsCardInfoDAO.findStatisticsCardInfo(statisticsCardInfo, this.getPageNumber(), this.getPageSize());
		return "jzdStaticList";
	}
	
	public void jzdStaticExport() throws Exception {
		this.staticDataQryFileService.generateJzdStaticExcel(response, statisticsCardInfo);
	}

	private void checkRoleLogined() throws BizException {
		Assert.isTrue(isCenterOrCenterDeptRoleLogined(), "没有权限访问该页面");
	}

	private void checkRoleLoginedAndCert() throws BizException {
		this.checkRoleLogined();
		this.checkEffectiveCertUser();
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public LogIpGuard getLogIpGuard() {
		return logIpGuard;
	}

	public void setLogIpGuard(LogIpGuard logIpGuard) {
		this.logIpGuard = logIpGuard;
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

	public TransCenterMoth getTransCenterMoth() {
		return transCenterMoth;
	}

	public void setTransCenterMoth(TransCenterMoth transCenterMoth) {
		this.transCenterMoth = transCenterMoth;
	}

	public GdysIssuerTermStatic getGdysIssuerTermStatic() {
		return gdysIssuerTermStatic;
	}

	public void setGdysIssuerTermStatic(GdysIssuerTermStatic gdysIssuerTermStatic) {
		this.gdysIssuerTermStatic = gdysIssuerTermStatic;
	}

	public GdnhglIssuerCardStatic getGdnhglIssuerCardStatic() {
		return gdnhglIssuerCardStatic;
	}

	public void setGdnhglIssuerCardStatic(
			GdnhglIssuerCardStatic gdnhglIssuerCardStatic) {
		this.gdnhglIssuerCardStatic = gdnhglIssuerCardStatic;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public StatisticsCardInfo getStatisticsCardInfo() {
		return statisticsCardInfo;
	}

	public void setStatisticsCardInfo(StatisticsCardInfo statisticsCardInfo) {
		this.statisticsCardInfo = statisticsCardInfo;
	}

	public StatisticsCardBjInfo getStatisticsCardBjInfo() {
		return statisticsCardBjInfo;
	}

	public void setStatisticsCardBjInfo(StatisticsCardBjInfo statisticsCardBjInfo) {
		this.statisticsCardBjInfo = statisticsCardBjInfo;
	}
}
