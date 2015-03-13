package gnete.card.web.makecard;

import flink.etc.DatePair;
import flink.etc.MatchMode;
import flink.util.AmountUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.AccuClassDefDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.dao.CardissuerPlanDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DiscntClassDefDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembClassDiscntDAO;
import gnete.card.dao.MembClassTempDAO;
import gnete.card.dao.PlanDefDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PointClassTempDAO;
import gnete.card.entity.AccuClassDef;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.CardSubClassTemp;
import gnete.card.entity.CardissuerPlan;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DiscntClassDef;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembClassDiscnt;
import gnete.card.entity.MembClassTemp;
import gnete.card.entity.PlanDef;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PointClassTemp;
import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.SingleProductService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @File: SingleProductCardAction.java
 *
 * @description: 发卡机构套餐关系（给发卡机构绑定套餐）
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-9
 */
public class SingleProductCardAction extends BaseAction {

	@Autowired
	private SingleProductService singleProductService;
	@Autowired
	private CardissuerPlanDAO cardissuerPlanDAO;
	@Autowired
	private PlanDefDAO planDefDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private CardSubClassTempDAO cardSubClassTempDAO;
	@Autowired
	private MembClassTempDAO membClassTempDAO;
	@Autowired
	private PointClassTempDAO pointClassTempDAO;
	@Autowired
	private MembClassDiscntDAO membClassDiscntDAO;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private AccuClassDefDAO accuClassDefDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private DiscntClassDefDAO discntClassDefDAO;

	private List<CommonState> statusList;

	private CardissuerPlan cardissuerPlan;
	private CardSubClassDef cardSubClassDef;
	private List<MembClassDiscnt> membClassDisctList;
	
	private CouponClassDef couponClassDef;
	private AccuClassDef accuClassDef;
	private PointClassDef pointClassDef;
	private MembClassDef membClassDef;
	private DiscntClassDef discntClassDef;
	
	private String[] memLevels; //会员级别
	private String[] discnts; // 折扣率
	private String makeBranch; // 制卡厂商编号
	private String makeBranchName; // 制卡厂商名称
	private String pointExchangeRate; // 积分兑换率 
	
	private String startDate;
	private String endDate;

	private Paginater page;
	
	private static final String DEFAULT_URL = "/pages/singleProduct/card/list.do?goBack=goBack";

	@Override
	public String execute() throws Exception {
		// 状态列表
		this.statusList = CommonState.getList();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (cardissuerPlan != null) {
			params.put("planId", cardissuerPlan.getPlanId());
			params.put("planName", MatchMode.ANYWHERE.toMatchString(cardissuerPlan.getPlanName()));
			params.put("cardExampleName", MatchMode.ANYWHERE.toMatchString(cardissuerPlan.getCardExampleName()));
			params.put("status", cardissuerPlan.getStatus());
			
			DatePair datePair = new DatePair(this.startDate, this.endDate);
			datePair.setTruncatedTimeDate(params);
		}
		
		if (isCenterOrCenterDeptRoleLogined()) {
			
		} else if (isFenzhiRoleLogined()) {
			params.put("fenzhiList", this.getMyManageFenzhi());
		} else {
			throw new BizException("没有权限查看");
		}

		this.page = this.singleProductService.findCardPage(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}

	public String detail() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchCode", cardissuerPlan.getBrancheCode());
		
		List<CardissuerPlan> list = this.cardissuerPlanDAO.findList(params);
		if (CollectionUtils.isNotEmpty(list)) {
			cardissuerPlan = list.get(0);
		}
		
		if (cardissuerPlan != null) {
			this.cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardissuerPlan.getCardSubclass());
			couponClassDef = (CouponClassDef) couponClassDefDAO.findByPk(cardSubClassDef.getCouponClass());
			accuClassDef = (AccuClassDef) accuClassDefDAO.findByPk(cardSubClassDef.getFrequencyClass());
			pointClassDef = (PointClassDef) pointClassDefDAO.findByPk(cardSubClassDef.getPtClass());
			membClassDef = (MembClassDef) membClassDefDAO.findByPk(cardSubClassDef.getMembClass());
			discntClassDef = (DiscntClassDef) discntClassDefDAO.findByPk(cardSubClassDef.getDiscntClass());
			
			if (StringUtils.isNotEmpty(cardSubClassDef.getMembClass())) {
				this.membClassDisctList = this.membClassDiscntDAO.findListByMembClass(cardSubClassDef.getMembClass());
			}
		}
		
		return DETAIL;
	}

	public String showAdd() throws Exception {
		Assert.isTrue(isFenzhiRoleLogined(), "没有权限操作！");
		
//		this.planList = this.singleProductService.findPlanList(this.getLoginBranchCode());
		return ADD;
	}
	
	/**
	 * 新增发卡机构套餐配置
	 */
	public String add() throws Exception {
		this.singleProductService.addCardExamplePlan(cardissuerPlan, memLevels, discnts, pointExchangeRate, 
				makeBranch, getSessionUser());
		
		String msg = LogUtils.r("新增发卡机构套餐配置[{0}]成功。", cardissuerPlan.getBrancheCode());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage(DEFAULT_URL, msg);
		
		return SUCCESS;
	}
	
	/**
	 * 根据传入的套餐编号，若有会员类型产生，则为每个会员级别设定一个优惠折扣
	 * @return
	 * @throws Exception
	 */
	public void loadBranch() throws Exception {
		String planNo = request.getParameter("planNo");
		
		CardSubClassTemp cardSubClassTemp = this.getTempByPlanNo(planNo);;
		if (cardSubClassTemp == null) {
			return;
		}
		
		MembClassTemp membClassTemp = (MembClassTemp) this.membClassTempDAO.findByPk(cardSubClassTemp.getMembClass());
		if (membClassTemp == null) {
			return;
		}
		
		if (StringUtils.isEmpty(membClassTemp.getMembClassName())) { // 会员级别名称不能为空
			return;
		}
		
		int level = NumberUtils.toInt(membClassTemp.getMembLevel());
		if (level < 0) {
			return;
		}
		String[] classNames = StringUtils.split(membClassTemp.getMembClassName(), ",");
		if (level != classNames.length) {
			return;
		}
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("<tr>")
			.append("<td align=\"center\" nowrap class=\"titlebg\">会员级别</td>")
			.append("<td align=\"center\" nowrap class=\"titlebg\">级别名称</td>")
			.append("<td align=\"center\" nowrap class=\"titlebg\">折扣率（请输入整数）</td>")
			.append("</tr>");
		for (int i = 1; i <= level; i++) {
			String memLevl = StringUtils.leftPad(String.valueOf(i), 2, "0");
			sb.append("<tr>")
				.append("<td align=\"center\" >")
				.append("<input type=\"text\" name=\"memLevels\" value=\"").append(memLevl).append("\" class=\"readonly u_half\" readonly=\"readonly\" /></td>")
				.append("<td align=\"center\" >")
				.append("<input type=\"text\" value=\"").append(classNames[i-1]).append("\" class=\"readonly\" readonly=\"readonly\" /></td>")
				.append("<td align=\"center\" ><input id=\"id_discnt_\"" + i + " type=\"text\" name=\"discnts\" class=\"{required:true, num:true, max:100, min:1}\" maxLength=\"3\">")
				.append("<span class=\"field_tipinfo\">请输入小于或等于100的正整数</span></td>")
				.append("</tr>");
		}
		this.respond(sb.toString());
	}
	
	/**
	 * 加载积分兑换率
	 * @throws Exception
	 */
	public void loadCity() throws Exception {
		String planNo = request.getParameter("planNo");

		CardSubClassTemp cardSubClassTemp = this.getTempByPlanNo(planNo);
		if (cardSubClassTemp == null) {
			return;
		}
		
		PointClassTemp pointClassTemp = (PointClassTemp) this.pointClassTempDAO.findByPk(cardSubClassTemp.getPtClass());
		if (pointClassTemp == null) {
			return;
		}
		StringBuffer sb = new StringBuffer(128);
		BigDecimal pointDiscntRate = AmountUtil.divide(pointClassTemp.getPtDiscntRate(), new BigDecimal(100));
		if (pointDiscntRate == null) {
			pointDiscntRate = new BigDecimal("0.00");
		}
		sb.append("<tr>")
			.append("<td width=\"80\" height=\"30\" align=\"right\">积分兑换率</td>")
			.append("<td><input type=\"text\" name=\"pointExchangeRate\" ")
			.append("class=\"{required:true, num:true, max:1.01}\" maxLength=\"4\" value=\"")
			.append(pointDiscntRate).append("\"/>")
			.append("<span class=\"field_tipinfo\">请输入小于或等于1的数</span></td>")
			.append("</tr>");
		this.respond(sb.toString());
	}
	
	/**
	 * 根据套餐编号取得卡类型模板
	 * @param planNo
	 * @return
	 */
	private CardSubClassTemp getTempByPlanNo(String planNo) {
		if (StringUtils.isEmpty(planNo)) {
			return null;
		}
		
		PlanDef planDef = (PlanDef) this.planDefDAO.findByPk(planNo);
		if (planDef == null) {
			return null;
		}
		
		return (CardSubClassTemp) this.cardSubClassTempDAO.findByPk(planDef.getCardSubclassTemp());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<CommonState> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CommonState> statusList) {
		this.statusList = statusList;
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

	public CardissuerPlan getCardissuerPlan() {
		return cardissuerPlan;
	}

	public void setCardissuerPlan(CardissuerPlan cardissuerPlan) {
		this.cardissuerPlan = cardissuerPlan;
	}

	public String[] getMemLevels() {
		return memLevels;
	}

	public void setMemLevels(String[] memLevels) {
		this.memLevels = memLevels;
	}

	public String[] getDiscnts() {
		return discnts;
	}

	public void setDiscnts(String[] discnts) {
		this.discnts = discnts;
	}

	public String getMakeBranch() {
		return makeBranch;
	}

	public void setMakeBranch(String makeBranch) {
		this.makeBranch = makeBranch;
	}

	public String getMakeBranchName() {
		return makeBranchName;
	}

	public void setMakeBranchName(String makeBranchName) {
		this.makeBranchName = makeBranchName;
	}

	public String getPointExchangeRate() {
		return pointExchangeRate;
	}

	public void setPointExchangeRate(String pointExchangeRate) {
		this.pointExchangeRate = pointExchangeRate;
	}

	public CardSubClassDef getCardSubClassDef() {
		return cardSubClassDef;
	}

	public void setCardSubClassDef(CardSubClassDef cardSubClassDef) {
		this.cardSubClassDef = cardSubClassDef;
	}

	public List<MembClassDiscnt> getMembClassDisctList() {
		return membClassDisctList;
	}

	public void setMembClassDisctList(List<MembClassDiscnt> membClassDisctList) {
		this.membClassDisctList = membClassDisctList;
	}

	public MembClassDef getMembClassDef() {
		return membClassDef;
	}

	public void setMembClassDef(MembClassDef membClassDef) {
		this.membClassDef = membClassDef;
	}

	public CouponClassDef getCouponClassDef() {
		return couponClassDef;
	}

	public void setCouponClassDef(CouponClassDef couponClassDef) {
		this.couponClassDef = couponClassDef;
	}

	public AccuClassDef getAccuClassDef() {
		return accuClassDef;
	}

	public void setAccuClassDef(AccuClassDef accuClassDef) {
		this.accuClassDef = accuClassDef;
	}

	public PointClassDef getPointClassDef() {
		return pointClassDef;
	}

	public void setPointClassDef(PointClassDef pointClassDef) {
		this.pointClassDef = pointClassDef;
	}

	public DiscntClassDef getDiscntClassDef() {
		return discntClassDef;
	}

	public void setDiscntClassDef(DiscntClassDef discntClassDef) {
		this.discntClassDef = discntClassDef;
	}
}
