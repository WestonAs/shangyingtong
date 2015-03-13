package gnete.card.web.vipcard;

import flink.util.Paginater;
import flink.util.TimeInterval;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembRegDAO;
import gnete.card.entity.Area;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembReg;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.EducationType;
import gnete.card.entity.type.SexType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VipCardService;
import gnete.card.service.mgr.BranchBizConfigCache;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

/**
 * 会员资料注册
 * @author aps-lib
 *
 */
public class MembRegAction extends BaseAction{

	private static final String	IS_EXPORTING_MEMB_REG	= "IS_EXPORTING_MEMB_REG";
	@Autowired
	private MembRegDAO membRegDao;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private VipCardService vipCardService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private AreaDAO areaDAO;
	
	private MembReg membReg;
	
	private Paginater page;
	
	private Collection credTypeList;
	private Collection sexTypeList;
	private Collection educationTypeList;
	
	/** 会员类型 */
	private List<MembClassDef> membClassDefList; 

	private String membRegId;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = buildQueryParams();
		
		membClassDefList = vipCardService.loadMtClass(this.getLoginBranchCode());
		this.page = this.membRegDao.findMembReg(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询会员注册资料列表");
		return LIST;
	}
	
	public String detail() throws Exception {
		this.membReg = (MembReg) this.membRegDao.findByPk(this.membReg.getMembRegId());
		checkUserOprtPriv(this.membReg);
		
		buildFormMapAccAreaName();
		return DETAIL;
	}

	public String showAdd() throws Exception {
		initPage();
		return ADD;
	}
	
	/* 检测卡号，查找卡号的会员类型，返回相应的json数据 */
	public String cardIdCheck() {
		JSONObject json = new JSONObject();
		try {
			String cardId = membReg.getCardId();
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
			Assert.notNull(cardInfo, "卡号[" + cardId + "]不存在,请重新输入！");

			// 检查登录机构是否有权限
			CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), cardInfo,
					"vip会员资料注册");

			String preMsg = "卡号[" + cardId + "]";
			CardSubClassDef cscDef = (CardSubClassDef) cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
			Assert.notNull(cscDef, preMsg + "的卡类型不存在,请重新输入！");
			Assert.isTrue(StringUtils.isNotBlank(cscDef.getMembClass()), preMsg + "没有关联的会员类型,请重新输入！");
			MembClassDef mcDef = (MembClassDef) membClassDefDAO.findByPk(cscDef.getMembClass());
			Assert.notNull(mcDef, preMsg + "对应的会员类型不存在,请重新输入！");

			json.put("membClass", mcDef.getMembClass());
			json.put("className", mcDef.getClassName());
			json.put("success", true);
		} catch (Exception e) {
			json.put("success", false);
			json.put("error", e.getMessage());
		}
		this.respond(json.toString());
		return null;
	}
	
	public String add() throws Exception {			
		//保存数据
		this.vipCardService.addMembReg(membReg, this.getSessionUser());
		
		String msg = "添加会员注册[" + this.membReg.getMembRegId() + "]成功！";
		this.addActionMessage("/vipCard/membReg/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		if(BranchBizConfigCache.isNeedMembBankAcctInfo(this.getLoginBranchCode())){
			this.checkEffectiveCertUser();
		}
		initPage();
		
		this.membReg = (MembReg)this.membRegDao.findByPk(this.membReg.getMembRegId());
		checkUserOprtPriv(this.membReg);
		
		if (this.membReg != null && StringUtils.isEmpty(this.membReg.getMembClass())) {
			CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(membReg.getCardId());
			Assert.notNull(cardInfo, "卡号[" + membReg.getCardId() + "]不存在,请重新输入！");

			Assert.isTrue(StringUtils.isNotBlank(cardInfo.getMembClass()), "卡号[" + membReg.getCardId()
					+ "]没有关联的会员类型！");
			this.membReg.setMembClass(cardInfo.getMembClass());
		}
		
		buildFormMapAccAreaName();
		
		return MODIFY;
	}
	
	public String modify() throws Exception {
		if(BranchBizConfigCache.isNeedMembBankAcctInfo(this.getLoginBranchCode())){
			this.checkEffectiveCertUser();
		}
		
		checkUserOprtPriv(this.membReg);
		
		this.vipCardService.modifyMembReg(this.membReg, this.getSessionUser());
		this.addActionMessage("/vipCard/membReg/list.do?goBack=goBack", "修改卡号[" + membReg.getCardId() + "]会员注册资料成功！");	
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		
		MembReg orig = (MembReg)this.membRegDao.findByPk(this.membReg.getMembRegId());
		checkUserOprtPriv(orig);
		
		this.vipCardService.deleteMembReg(this.membReg.getMembRegId());
		String msg = "删除会员注册[" +this.membReg.getMembRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/vipCard/membReg/list.do?goBack=goBack", msg);
		return SUCCESS;
	}
	
	public void ajaxIsExporting() throws Exception {
		JSONObject json = new JSONObject();
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_EXPORTING_MEMB_REG))) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		this.respond(json.toString());
	}
	
	public void export() throws Exception {
		if (Boolean.TRUE.equals(ActionContext.getContext().getSession().get(IS_EXPORTING_MEMB_REG))) {
			return;
		}
		ActionContext.getContext().getSession().put(IS_EXPORTING_MEMB_REG, Boolean.TRUE);
		try {
			Map<String, Object> params = buildQueryParams();

			String msg = String.format("用户%s, 机构%s导出会员信息...  [params: %s]", this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), params );
			logger.info(msg);
			log(msg, UserLogType.OTHER);
			TimeInterval ti = new TimeInterval();
			vipCardService.export(this.response, params);
			String msg2 = String.format("用户%s,机构%s导出会员信息完成！用时 %s秒", this.getSessionUserCode(),
					this.getSessionUser().getBranchNo(), ti.getIntervalOfSec() );
			logger.info(msg2);
			log(msg2, UserLogType.OTHER);
		} finally {
			IOUtils.closeQuietly(response.getWriter());
			ActionContext.getContext().getSession().remove(IS_EXPORTING_MEMB_REG);
		}
	}

	private Map<String, Object> buildQueryParams() throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (membReg != null) {
			params.put("custName", membReg.getCustName());
			params.put("membClass", membReg.getMembClass());
			params.put("cardId", membReg.getCardId());
			params.put("credNo", membReg.getCredNo());
			params.put("mobileNo", membReg.getMobileNo());
			params.put("cardIssuer", membReg.getCardIssuer());
		}

		if (isCenterOrCenterDeptRoleLogined()) {// 运营机构 或部门

		} else if (isFenzhiRoleLogined()) {// 分支机构
			params.put("fenzhiList", this.getMyManageFenzhi());

		} else if (isCardOrCardDeptRoleLogined()) {// 发卡机构或网点
			params.put("cardBranch", this.getLoginBranchCode());

		} else {
			throw new BizException("没有权限查询会员注册资料");
		}
		return params;
	}
	
	private void initPage() throws Exception {
		if (!isCardOrCardDeptRoleLogined()) {
			throw new BizException("没有权限维护会员注册资料");
		}
		//加载证件类型作为下拉列表
		this.credTypeList= CertType.getAll();
		//加载性别作为下拉列表
		this.sexTypeList = SexType.getAll();
		//加载学历作为下拉列表
		this.educationTypeList = EducationType.getAll();
	}
	
	/** 构造FormMap的accAreaName属性*/
	private void buildFormMapAccAreaName() {
		if(membReg != null && StringUtils.isNotBlank(membReg.getAccAreaCode())){
			Area accArea = (Area) this.areaDAO.findByPk(this.membReg.getAccAreaCode());
			if (accArea != null) {
				this.formMap.put("accAreaName",accArea.getAreaName());
			}
		}
	}
	
	/** 检测用户是否可操作指定记录 */
	private void checkUserOprtPriv(MembReg membReg) throws BizException {
		
		if (!(isCenterOrCenterDeptRoleLogined() || isFenzhiRoleLogined() || isCardOrCardDeptRoleLogined())) {
			throw new BizException("非运营中心、运营机构、发卡机构没有权限操作指定记录！");
		}

		Assert.notNull(membReg, "指定的记录不存在！");

		if (isFenzhiRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isDirectManagedBy(membReg.getCardIssuer(), getLoginBranchCode()),
					"该用户不能操作指定的记录");
		} else if (isCardOrCardDeptRoleLogined()) {
			Assert.isTrue(branchInfoDAO.isSuperBranch(getLoginBranchCode(), membReg.getCardIssuer()),
					"登录的机构不是指定发卡机构的本身或上级机构！");
		}
	}
	
	/* ***********************************************************************
	 * 		getters and setters followed
	 * ***********************************************************************/
	
	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setMembReg(MembReg membReg) {
		this.membReg = membReg;
	}

	public MembReg getMembReg() {
		return membReg;
	}

	public void setCredTypeList(Collection credTypeList) {
		this.credTypeList = credTypeList;
	}

	public Collection getCredTypeList() {
		return credTypeList;
	}

	public void setSexTypeList(Collection sexTypeList) {
		this.sexTypeList = sexTypeList;
	}

	public Collection getSexTypeList() {
		return sexTypeList;
	}
	
	public Collection getEducationTypeList() {
		return educationTypeList;
	}

	public void setEducationTypeList(Collection educationTypeList) {
		this.educationTypeList = educationTypeList;
	}
	
	public List<MembClassDef> getMembClassDefList() {
		return membClassDefList;
	}

	public void setMembClassDefList(List<MembClassDef> membClassDefList) {
		this.membClassDefList = membClassDefList;
	}

	public void setMembClassDefDAO(MembClassDefDAO membClassDefDAO) {
		this.membClassDefDAO = membClassDefDAO;
	}

	public MembClassDefDAO getMembClassDefDAO() {
		return membClassDefDAO;
	}

	public void setBranchInfoDAO(BranchInfoDAO branchInfoDAO) {
		this.branchInfoDAO = branchInfoDAO;
	}

	public BranchInfoDAO getBranchInfoDAO() {
		return branchInfoDAO;
	}
}
