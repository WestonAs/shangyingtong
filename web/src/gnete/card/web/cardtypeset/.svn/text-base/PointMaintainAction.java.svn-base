package gnete.card.web.cardtypeset;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.MerchPointRateDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.MerchPointRate;
import gnete.card.entity.MerchPointRateKey;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class PointMaintainAction extends BaseAction {
	
	@Autowired
	private MerchPointRateDAO merchPointRateDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	
	private MerchPointRate merchPointRate;
	
	private List<BranchInfo> branchList;
	private List<MerchInfo> merchInfoList;
	
	//删除费率使用
	private String merNo;
    private String ptClass;
    private String cardIssuer;
	
    //页面显示控制
	private String parent;
	private boolean showCenter = false;
	private boolean showFenZhi = false;
	private boolean showCard = false;
    
	private Paginater page;
	
	private static final String LIST_URL = "/cardTypeSet/pointMaintain/list.do";

	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (merchPointRate != null) {
			params.put("cardIssuer", MatchMode.ANYWHERE.toMatchString(merchPointRate.getCardIssuer()));
			params.put("merNo", MatchMode.ANYWHERE.toMatchString(merchPointRate.getMerNo()));
			params.put("ptClass", merchPointRate.getPtClass());
		}
		
		branchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			branchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			branchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(branchList)) {
			String[] cardIssuers = new String[branchList.size()];
			for(int i=0 ; i<branchList.size(); i++){
				cardIssuers[i] = (branchList.get(i)).getBranchCode();
			}
			params.put("cardIssuers", cardIssuers);
		}
		if(CollectionUtils.isNotEmpty(merchInfoList)){
			String[] merNos = new String[merchInfoList.size()];
			for(int i=0 ; i<merchInfoList.size(); i++){
				merNos[i] = (merchInfoList.get(i)).getMerchId();
			}
			params.put("merNos", merNos);
		}
		this.page = this.merchPointRateDAO.findPage(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	
	/** 
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String showAdd() throws Exception {
		if(!this.getLoginRoleType().equals(RoleType.CARD.getValue())){ 
			throw new BizException("非发卡机构不能操作。");
		}
		// 营运中心、中心部门
		if(RoleType.CENTER.getValue().equals(this.getLoginRoleType())||
				RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())){
			this.showCenter = true;
			this.showCard = false;
			this.showFenZhi = false;
		} 
		// 分支机构
		else if(RoleType.FENZHI.getValue().equals(this.getLoginRoleType())){
			this.showFenZhi = true;
			this.showCenter = false;
			this.showCard = false;
			this.parent = this.getSessionUser().getBranchNo();
		}
		// 发卡机构、机构部门
		else if(RoleType.CARD.getValue().equals(this.getLoginRoleType())||
				RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())){
			showCard = true;
			showCenter = false;
			this.showFenZhi = false;
			this.merchPointRate = new MerchPointRate();
			this.merchPointRate.setCardIssuer(this.getSessionUser().getBranchNo());
		} else{
			throw new BizException("非营运中心、营运中心部门、分支机构、发卡机构及发卡机构部门不能操作。");
		}
		return ADD;
	}
	
	/** 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		Assert.notEmpty(merchPointRate.getCardIssuer(), "发卡机构不能为空");
		Assert.notEmpty(merchPointRate.getMerNo(), "商户代码不能为空");
		Assert.notEmpty(merchPointRate.getPtClass(), "积分类型不能为空");
		MerchPointRateKey merchPointRateKey = new MerchPointRateKey();
		merchPointRateKey.setCardIssuer(merchPointRate.getCardIssuer());
		merchPointRateKey.setMerNo(merchPointRate.getMerNo());
		merchPointRateKey.setPtClass(merchPointRate.getPtClass());
		Assert.isNull(this.merchPointRateDAO.findByPk(merchPointRateKey), "机构积分类型[" + merchPointRate.getCardIssuer() +"]已存在");
		
		merchPointRate.setPtDiscntRate(divPtDiscntRate(merchPointRate.getPtDiscntRate()));
		merchPointRate.setInsertTime(new Date());
		merchPointRate.setUpdateTime(new Date());
		this.merchPointRateDAO.insert(merchPointRate);
		
		String msg = LogUtils.r("新增机构积分费率[{0}][{1}][{2}]成功", merchPointRate.getCardIssuer(), merchPointRate.getPtClass(), merchPointRate.getMerNo());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 明细
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		MerchPointRateKey merchPointRateKey = new MerchPointRateKey();
		merchPointRateKey.setCardIssuer(merchPointRate.getCardIssuer());
		merchPointRateKey.setMerNo(merchPointRate.getMerNo());
		merchPointRateKey.setPtClass(merchPointRate.getPtClass());
		merchPointRate = (MerchPointRate)this.merchPointRateDAO.findByPk(merchPointRateKey);
		return DETAIL;
	}
	
	public String showModify() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构不能修改。");
		}
		Assert.notEmpty(merchPointRate.getCardIssuer(), "发卡机构不能为空");
		Assert.notEmpty(merchPointRate.getMerNo(), "商户代码不能为空");
		Assert.notEmpty(merchPointRate.getPtClass(), "积分类型不能为空");
		MerchPointRateKey merchPointRateKey = new MerchPointRateKey();
		merchPointRateKey.setCardIssuer(merchPointRate.getCardIssuer());
		merchPointRateKey.setMerNo(merchPointRate.getMerNo());
		merchPointRateKey.setPtClass(merchPointRate.getPtClass());
		merchPointRate = (MerchPointRate)this.merchPointRateDAO.findByPk(merchPointRateKey);
		merchPointRate.setPtDiscntRate(mulPtDiscntRate(merchPointRate.getPtDiscntRate()));
		return MODIFY;
	}
	
	public String modify() throws Exception {
		Assert.notEmpty(merchPointRate.getCardIssuer(), "发卡机构不能为空");
		Assert.notEmpty(merchPointRate.getMerNo(), "商户代码不能为空");
		Assert.notEmpty(merchPointRate.getPtClass(), "积分类型不能为空");
		MerchPointRateKey merchPointRateKey = new MerchPointRateKey();
		merchPointRateKey.setCardIssuer(merchPointRate.getCardIssuer());
		merchPointRateKey.setMerNo(merchPointRate.getMerNo());
		merchPointRateKey.setPtClass(merchPointRate.getPtClass());
		MerchPointRate tmpMerchPointRate = (MerchPointRate)this.merchPointRateDAO.findByPk(merchPointRateKey);
		Assert.notNull(tmpMerchPointRate, "机构积分类型[" + merchPointRate.getCardIssuer() +"]不存在");
		
		tmpMerchPointRate.setPtDiscntRate(divPtDiscntRate(merchPointRate.getPtDiscntRate()));
		tmpMerchPointRate.setUpdateTime(new Date());
		tmpMerchPointRate.setRemark(merchPointRate.getRemark());
		merchPointRateDAO.update(tmpMerchPointRate);
		String msg = LogUtils.r("修改机构积分费率[{0}][{1}][{2}]成功", merchPointRate.getCardIssuer(), merchPointRate.getPtClass(), merchPointRate.getMerNo());
		this.log(msg, UserLogType.UPDATE);
		addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(!(roleInfo.getRoleType().equals(RoleType.CARD.getValue()))){ 
			throw new BizException("非发卡机构不能修改。");
		}
		Assert.notEmpty(cardIssuer, "发卡机构不能为空");
		Assert.notEmpty(merNo, "商户代码不能为空");
		Assert.notEmpty(ptClass, "积分类型不能为空");
		MerchPointRateKey merchPointRateKey = new MerchPointRateKey();
		merchPointRateKey.setCardIssuer(cardIssuer);
		merchPointRateKey.setMerNo(merNo);
		merchPointRateKey.setPtClass(ptClass);
		
		this.merchPointRateDAO.delete(merchPointRateKey);
		String msg = LogUtils.r("删除机构积分费率[{0}][{1}][{2}]成功", cardIssuer,merNo, ptClass);
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage(LIST_URL, msg);
		return SUCCESS;
	}
	
	public BigDecimal divPtDiscntRate(BigDecimal ptDiscntRate){
		if(ptDiscntRate!=null && StringUtils.isNotBlank(ptDiscntRate.toString())){
			ptDiscntRate = ptDiscntRate.divide(new BigDecimal(100));
		}
		return ptDiscntRate;
	}
	
	public BigDecimal mulPtDiscntRate(BigDecimal ptDiscntRate){
		if(ptDiscntRate!=null && StringUtils.isNotBlank(ptDiscntRate.toString())){
			ptDiscntRate = ptDiscntRate.multiply(new BigDecimal(100)).setScale(2);
		}
		return ptDiscntRate;
	}
	
	/**
	 * 根据卡号得到该卡的积分类型列表。（返回到页面为String字符串）
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String cardIssuer = request.getParameter("cardIssuer");
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("<option value=\"\">--请选择--</option>");
		if (StringUtils.isNotEmpty(cardIssuer)) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("jinstId", cardIssuer); // 联名机构编号，为要充值的卡所属的发卡机构
			params.put("ptUsage", "6"); // 只能查浮动费率积分
			List<PointClassDef> ptClassList = this.pointClassDefDAO.findPontClassList(params);

			for (PointClassDef ptClass : ptClassList){
				sb.append("<option value=\"").append(ptClass.getPtClass()).append("\">")
						.append(ptClass.getPtClass()).append("-").append(ptClass.getClassName()).append("</option>");
			}
		}
		
		this.respond(sb.toString());
	}
	
	public MerchPointRate getMerchPointRate() {
		return merchPointRate;
	}

	public void setMerchPointRate(MerchPointRate merchPointRate) {
		this.merchPointRate = merchPointRate;
	}
	
	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}


	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean isShowCenter() {
		return showCenter;
	}

	public void setShowCenter(boolean showCenter) {
		this.showCenter = showCenter;
	}

	public boolean isShowFenZhi() {
		return showFenZhi;
	}

	public void setShowFenZhi(boolean showFenZhi) {
		this.showFenZhi = showFenZhi;
	}

	public boolean isShowCard() {
		return showCard;
	}

	public void setShowCard(boolean showCard) {
		this.showCard = showCard;
	}
}
