package gnete.card.web.para;

import flink.etc.DatePair;
import flink.util.IOUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.LogoConfigDAO;
import gnete.card.entity.LogoConfig;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.PublishNoticeService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 发卡机构logo参数配置
 */
public class LogoConfigAction extends BaseAction{

	@Autowired
	private LogoConfigDAO logoConfigDAO;
	@Autowired
	private PublishNoticeService publishNoticeService;

	private LogoConfig logoConfig;
	private Paginater page;
	
	// 封装上传文件域的属性(首页大图)
	private File uploadHomeBig;
	// 封装上传文件名的属性
	private String uploadHomeBigFileName;
	// 封装上传文件域的属性（首页小图）
	private File uploadHomeSmall;
	// 封装上传文件名的属性
	private String uploadHomeSmallFileName;
	// 封装上传文件域的属性（登录后的小图）
	private File uploadLoginSmall;
	// 封装上传文件名的属性
	private String uploadLoginSmallFileName;
	
	private String branchCode;
	
	private String startDate;
	private String endDate;
	private String branchName;
	
	private static final String DEFAULT_LIST_PAGE = "/para/logoConfig/list.do";
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (logoConfig != null) {
			params.put("branchNo", logoConfig.getBranchNo());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiCode", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		this.page = this.logoConfigDAO.findLogoConfigPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!isCenterRoleLogined()){
			throw new BizException("只有运营中心有权限进行此操作");
		} 
		return ADD;
	}
	
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!isCenterRoleLogined()){
			throw new BizException("只有运营中心有权限进行此操作");
		} 
		
		// 定义可上传文件的 类型
		List<String> fileTypes = Arrays.asList("jpg", "png", "gif");
		
		Assert.isTrue(IOUtil.testFileFix(uploadHomeBigFileName, fileTypes), "上传的文件只能是jpg，gif或png格式的图片");
		Assert.isTrue(IOUtil.testFileFix(uploadHomeSmallFileName, fileTypes), "上传的文件只能是jpg，gif或png格式的图片");
		Assert.isTrue(IOUtil.testFileFix(uploadLoginSmallFileName, fileTypes), "上传的文件只能是jpg，gif或png格式的图片");
		
		this.publishNoticeService.addCardLogoConfig(logoConfig, uploadHomeBig, uploadHomeSmall, uploadLoginSmall, this.getSessionUser());	
		
		String msg = LogUtils.r("新增发卡机构[{0}]Logo参数配置成功！", logoConfig.getBranchNo());
		
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_LIST_PAGE, msg);
		return SUCCESS;
	}

	
	public String delete() throws Exception {
		this.checkEffectiveCertUser();
		
		if(!isCenterRoleLogined()){
			throw new BizException("只有运营中心有权限进行此操作");
		}
		
		this.publishNoticeService.deleteCardLogoConfig(branchCode);
		
		String msg = LogUtils.r("删除发卡机构[{0}]Logo参数配置成功！", branchCode);
		
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(DEFAULT_LIST_PAGE, msg);
		return SUCCESS;
	}

	public LogoConfig getLogoConfig() {
		return logoConfig;
	}

	public void setLogoConfig(LogoConfig logoConfig) {
		this.logoConfig = logoConfig;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public File getUploadHomeBig() {
		return uploadHomeBig;
	}

	public void setUploadHomeBig(File uploadHomeBig) {
		this.uploadHomeBig = uploadHomeBig;
	}

	public String getUploadHomeBigFileName() {
		return uploadHomeBigFileName;
	}

	public void setUploadHomeBigFileName(String uploadHomeBigFileName) {
		this.uploadHomeBigFileName = uploadHomeBigFileName;
	}

	public File getUploadHomeSmall() {
		return uploadHomeSmall;
	}

	public void setUploadHomeSmall(File uploadHomeSmall) {
		this.uploadHomeSmall = uploadHomeSmall;
	}

	public String getUploadHomeSmallFileName() {
		return uploadHomeSmallFileName;
	}

	public void setUploadHomeSmallFileName(String uploadHomeSmallFileName) {
		this.uploadHomeSmallFileName = uploadHomeSmallFileName;
	}

	public File getUploadLoginSmall() {
		return uploadLoginSmall;
	}

	public void setUploadLoginSmall(File uploadLoginSmall) {
		this.uploadLoginSmall = uploadLoginSmall;
	}

	public String getUploadLoginSmallFileName() {
		return uploadLoginSmallFileName;
	}

	public void setUploadLoginSmallFileName(String uploadLoginSmallFileName) {
		this.uploadLoginSmallFileName = uploadLoginSmallFileName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	

}
