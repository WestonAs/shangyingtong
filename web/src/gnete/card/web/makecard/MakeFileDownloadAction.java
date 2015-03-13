package gnete.card.web.makecard;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.flag.MakeFlag;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MakeCardService;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: MakeFileDownloadAction.java
 * 
 * @description: 制卡文本下载的相关处理
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-31
 */
public class MakeFileDownloadAction extends BaseAction {

	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private MakeCardRegDAO makeCardRegDAO;
	@Autowired
	private MakeCardService makeCardService;
//	@Autowired
//	private CostRecordDAO costRecordDAO;

	private Collection statusList;
	private Collection makeFlagList;

	private List<MakeCardReg> regList;

	private MakeCardApp makeCardApp;
	private Date startDate;
	private Date endDate;
	private String id;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = MakeCardAppState.ALL.values();
		// 制卡方式列表 
		this.makeFlagList = MakeFlag.ALL.values();
		// 当前机构下的卡样状态有效的制卡登记记录列表
		this.regList = getEffectiveMakeCardReg();
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("makeCardApp", makeCardApp);
		
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		if (isCenterOrCenterDeptRoleLogined()) {// 运营中心或部门
			
		} else if (isFenzhiRoleLogined()) {// 运营分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());
			
		} else if (isCardRoleLogined()) { // 发卡机构
			params.put("cardIssuerList", this.getMyCardBranch());
			
		}else if (isCardMakeRoleLogined()) {// 制卡厂商
			params.put("makeUser", getSessionUser().getBranchNo());
			
		} else {
			throw new BizException("没有权限");
		}
		page = makeCardAppDAO.findMakeCardAppPage(params, getPageNumber(), getPageSize());
		return LIST;
	}

	/**
	 * 查询当前机构下的卡样状态有效的制卡登记记录
	 */
	private List<MakeCardReg> getEffectiveMakeCardReg() {
		Map<String, Object> cardRegMap = new HashMap<String, Object>();
		cardRegMap.put("branchCode", this.getSessionUser().getBranchNo());
		cardRegMap.put("picStatus", MakeCardRegState.EFFECTIVE.getValue());
		return this.makeCardRegDAO.findByBranchCode(cardRegMap);
	}

	public String detail() throws Exception {
		makeCardApp = (MakeCardApp) makeCardAppDAO.findByPk(makeCardApp.getId());
		return DETAIL;
	}

	public String showDownload() throws Exception {
		Assert.notEmpty(id, "要下载卡样的制卡申请ID不能为空");
		
		MakeCardApp makeCardApp = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(id));
		Assert.notNull(makeCardApp, "找不到制卡申请ID[" + id + "]对应的记录。");
		
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())) {
			Assert.equals(getLoginBranchCode(), makeCardApp.getBranchCode(), "发卡机构只能下载自己的制卡文件");
		}

//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("makeId", id);
//		List<CostRecord> list = this.costRecordDAO.findList(params);
//		if (CollectionUtils.isNotEmpty(list)) {
//			CostRecord costRecord = list.get(0);
//			if (costRecord != null) {
//				Assert.equals(costRecord.getStatus(), CostRecordState.PAYED.getValue(), "只有“已付款”的单机产品卡样才能够下载");
//			}
//		}
		
		return "download";
	}
	
	/**
	 * 下载制卡文件
	 */
	public String download() throws Exception {
		this.makeCardService.downloadMakeFile(id, getSessionUser());
		
		String msg = LogUtils.r("下载制卡申请ID为[{0}]的制卡文本成功。", id);
		this.log(msg, UserLogType.OTHER);
		
		return null;
	}

//	/**
//	 * 下载制卡文件
//	 */
//	public String download() throws Exception {
//		// 查询系统参数，得到制卡文件保存路径
//		String path = ParaMgr.getInstance().getMakeFileSavePath();
//		makeCardApp = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(id));
//		if (makeCardApp == null) {
//			throw new BizException("该ID对应的制卡申请对象不存在。");
//		}
//		String fileName = path + "/" + makeCardApp.getMakeId() + "_"
//				+ makeCardApp.getAppId() + ".txt";
//		if (IOUtil.isFileExist(fileName)) {
//			makeCardService.downloadMakeFile(id, getSessionUser());
//			String msg = LogUtils.r("下载制卡申请ID为[{0}]的制卡文本成功。", id);
//			log(msg, UserLogType.OTHER);
//			try {
//				IOUtil.downloadFile(fileName);
//			} catch (IOException e) {
//				addActionError("文件下载时发生异常。");
//				LOG.error("文件下载时发生异常", e);
//				throw new BizException("文件下载时发生异常。");
//			}
//		} else {
//			addActionError("要下载的制卡文本不存在。");
//			LOG.error("要下载的制卡文本不存在。");
//			throw new BizException("要下载的制卡文本不存在。");
//		}
//
//		return null;
//	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getStatusList() {
		return statusList;
	}

	public void setStatusList(Collection statusList) {
		this.statusList = statusList;
	}

	public Collection getMakeFlagList() {
		return makeFlagList;
	}

	public void setMakeFlagList(Collection makeFlagList) {
		this.makeFlagList = makeFlagList;
	}

	public List<MakeCardReg> getRegList() {
		return regList;
	}

	public void setRegList(List<MakeCardReg> regList) {
		this.regList = regList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MakeCardApp getMakeCardApp() {
		return makeCardApp;
	}

	public void setMakeCardApp(MakeCardApp makeCardApp) {
		this.makeCardApp = makeCardApp;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
