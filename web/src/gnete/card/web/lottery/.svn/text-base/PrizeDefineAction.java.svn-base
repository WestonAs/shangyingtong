package gnete.card.web.lottery;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BrushSetDAO;
import gnete.card.dao.CouponClassDefDAO;
import gnete.card.dao.DrawDefDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.dao.PointClassDefDAO;
import gnete.card.dao.PrizeDefDAO;
import gnete.card.entity.BrushSet;
import gnete.card.entity.CouponClassDef;
import gnete.card.entity.DrawDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.PointClassDef;
import gnete.card.entity.PrizeDef;
import gnete.card.entity.PrizeDefKey;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PrizeType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LotteryService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: PrizeDefineAction.java
 *
 * @description: 奖项定义相关处理
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public class PrizeDefineAction extends BaseAction {
	
	@Autowired
	private PrizeDefDAO prizeDefDAO;
	@Autowired
	private BrushSetDAO brushSetDAO;
	@Autowired
	private DrawDefDAO drawDefDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private PointClassDefDAO pointClassDefDAO;
	@Autowired
	private CouponClassDefDAO couponClassDefDAO;
	@Autowired
	private LotteryService lotteryService;
	
	private PrizeDef prizeDef;
	private BrushSet brushSet;

	private String pinstId_sel;
	
	private String drawId;
	private String prizeNo;

	/** 机构类型代码 */
	private List pInstTypeList;
	private List awdTypeList;
	private List<DrawDef> drawList;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		awdTypeList = PrizeType.getAll();
		pInstTypeList = IssType.getAll();
		String issId = null;
		List<MerchInfo> merchInfoList = new ArrayList<MerchInfo>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CENTER.getValue())) {
		}else if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			issId = getSessionUser().getBranchNo();
			params.put("jinstId", issId);
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			issId = getSessionUser().getMerchantNo();
			params.put("jinstId", getSessionUser().getMerchantNo());
		}
		if (CollectionUtils.isNotEmpty(merchInfoList)) {
			String[] jinstIds = new String[merchInfoList.size() + 1];
			int i = 0;
			for( ; i<merchInfoList.size(); i++){
				jinstIds[i] = (merchInfoList.get(i)).getMerchId();
			}
			jinstIds[i] = getSessionUser().getBranchNo();
			params.put("jinstIds", jinstIds);
			params.put("jinstId", "");
		}
		if (prizeDef != null) {
			params.put("prizeName", MatchMode.ANYWHERE.toMatchString(prizeDef.getPrizeName()));
			params.put("drawId", prizeDef.getDrawId());
			params.put("jinstId", MatchMode.ANYWHERE.toMatchString(prizeDef.getJinstId()));
			params.put("awdType", prizeDef.getAwdType());
		}
		drawList = drawDefDAO.findByIssId(issId);
		page = prizeDefDAO.findPrizeDefPage(params, getPageNumber(), getPageSize());
		return LIST;
	}
	
	public String detail() throws Exception {
		PrizeDefKey prizeDefKey = new PrizeDefKey();
		prizeDefKey.setDrawId(prizeDef.getDrawId());
		prizeDefKey.setPrizeNo(prizeDef.getPrizeNo());
		prizeDef = (PrizeDef) prizeDefDAO.findByPk(prizeDefKey);
		
		Map<String, Object> params = new HashMap<String, Object>();//明细
		params.put("drawId", prizeDef.getDrawId());
		params.put("prizeNo", prizeDef.getPrizeNo());
		brushSet = brushSetDAO.findByDrawPrizeNo(params);
		return DETAIL;
	}
	
	public String showAdd() throws Exception {
		String issId = null;
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			issId = getSessionUser().getBranchNo();
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户
			issId = getSessionUser().getMerchantNo();
		} else {
			throw new BizException("非商户或发卡机构禁止新增一个新的奖项。");
		}
		drawList = drawDefDAO.findByIssId(issId);
		awdTypeList = PrizeType.getAll();
		pInstTypeList = IssType.getAll();
		return ADD;
	}
	
	public String add() throws Exception {
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			if (prizeDef.getJinstType().equals(IssType.CARD.getValue())) {
				prizeDef.setJinstId(getSessionUser().getBranchNo());
			}
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			prizeDef.setJinstType(IssType.MERCHANT.getValue());
			prizeDef.setJinstId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的奖项。");
		}
		PrizeDef tmpPrizeDef = lotteryService.addPrizeDef(prizeDef, getSessionUser());
		
		//入明细表
		DrawDef tmpDrawDef = (DrawDef)drawDefDAO.findByPk(tmpPrizeDef.getDrawId());//获取有效时段
		if(null == tmpDrawDef){
			throw new BizException("抽奖活动["+tmpPrizeDef.getDrawId()+"]不存在。");
		}
		brushSet.setDrawId(tmpPrizeDef.getDrawId());
		brushSet.setPrizeNo(tmpPrizeDef.getPrizeNo());
		brushSet.setAwdBeginDate(tmpDrawDef.getEffDate());
		brushSet.setAwdEndDate(tmpDrawDef.getExpirDate());
		brushSet.setLeftAwdCnt(brushSet.getTotalAwdCnt());//初始一样
		lotteryService.addBrushSet(brushSet, getSessionUser());
		
		String msg = LogUtils.r("奖项定义[{0},{1}]已经提交。", prizeDef.getDrawId(),prizeDef.getPrizeNo());
		log(msg, UserLogType.ADD);
		addActionMessage("/lottery/prizeDef/list.do", msg);
		return SUCCESS;
	}
	

	public String showModify() throws Exception {
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止修改活动。");
		}
		
		PrizeDefKey prizeDefKey = new PrizeDefKey();
		prizeDefKey.setDrawId(prizeDef.getDrawId());
		prizeDefKey.setPrizeNo(prizeDef.getPrizeNo());
		drawId = prizeDef.getDrawId();
		prizeNo = prizeDef.getPrizeNo().toString();
		prizeDef = (PrizeDef) prizeDefDAO.findByPk(prizeDefKey);
		
		if(null == prizeDef){
			throw new BizException("要修改的奖项["+drawId+","+prizeNo+"]不存在。");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();//明细
		params.put("drawId", prizeDef.getDrawId());
		params.put("prizeNo", prizeDef.getPrizeNo());
		brushSet = brushSetDAO.findByDrawPrizeNo(params);
		
		if(null == brushSet){
			throw new BizException("要修改的奖项明细不存在["+prizeDef.getDrawId()+","+prizeDef.getPrizeNo()+"]不存在。");
		}
		
		MerchInfo merchInfo = (MerchInfo) merchInfoDAO.findByPk(prizeDef.getJinstId());
		if (merchInfo != null) {
			pinstId_sel = merchInfo.getMerchName();
		}
		awdTypeList = PrizeType.getAll();
		pInstTypeList = IssType.getAll();
		return MODIFY;
	}
	
	public String modify() throws Exception{
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			if (prizeDef.getJinstType().equals(IssType.CARD.getValue())) {
				prizeDef.setJinstId(getSessionUser().getBranchNo());
			}
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户，参与机构为自己本身
			prizeDef.setJinstType(IssType.MERCHANT.getValue());
			prizeDef.setJinstId(getSessionUser().getMerchantNo());
		} else {
			throw new BizException("非商户或发卡机构禁止定义一个新的奖项。");
		}
		
		lotteryService.modifyPrizeDef(prizeDef, getSessionUser());
		
		Map<String, Object> params = new HashMap<String, Object>();//明细
		params.put("drawId", prizeDef.getDrawId());
		params.put("prizeNo", prizeDef.getPrizeNo());
		BrushSet tmpBrushSet = brushSetDAO.findByDrawPrizeNo(params);//修改明细
		if(null != tmpBrushSet && !tmpBrushSet.getTotalAwdCnt().equals(brushSet.getTotalAwdCnt())){
			tmpBrushSet.setTotalAwdCnt(brushSet.getTotalAwdCnt());
			tmpBrushSet.setLeftAwdCnt(brushSet.getTotalAwdCnt());
			lotteryService.modifyBrushSet(tmpBrushSet, getSessionUser());
		}

		String msg = LogUtils.r("修改奖项定义[{0},{1}]成功。", prizeDef.getDrawId(),prizeDef.getPrizeNo());
		log(msg, UserLogType.UPDATE);
		addActionMessage("/lottery/prizeDef/list.do", msg);
		return SUCCESS;
	}

	public String delete() throws Exception {
		if (!(getLoginRoleTypeCode().equals(RoleType.CARD.getValue()) || getLoginRoleTypeCode()
				.equals(RoleType.MERCHANT.getValue()))) {
			throw new BizException("非商户或发卡机构禁止操作奖项定义。");
		}
		PrizeDefKey prizeDefKey = new PrizeDefKey();
		prizeDefKey.setDrawId(drawId);
		prizeDefKey.setPrizeNo(Integer.valueOf(prizeNo));
		boolean isPrizeSuccess = lotteryService.deletePrizeDef(prizeDefKey);
		
		Map<String, Object> params = new HashMap<String, Object>();//明细
		params.put("drawId", drawId);
		params.put("prizeNo", prizeNo);
		BrushSet tmpBrushSet = brushSetDAO.findByDrawPrizeNo(params);//删除明细
		
		boolean isBrushSuccess = true;
		if(null != tmpBrushSet){
			isBrushSuccess =  lotteryService.deleteBrushSet(tmpBrushSet);
		}
		
		if (isPrizeSuccess && isBrushSuccess) {
			String msg = LogUtils.r("删除奖项定义[{0},{1}]成功。", drawId,prizeNo);
			log(msg, UserLogType.DELETE);
			addActionMessage("/lottery/prizeDef/list.do", msg);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据传进来的规则类型，得到相应的子类参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getSubClassList() throws Exception {
		String ruleType = request.getParameter("ruleType");
		StringBuffer sb = new StringBuffer(128);
		Map<String, Object> params = new HashMap<String, Object>();
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			params.put("jinstId", getSessionUser().getBranchNo());
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			params.put("jinstId", getSessionUser().getMerchantNo());
		}
		if (StringUtils.equals(ruleType, PrizeType.POINTS.getValue())) {
			// 使用积分子类型
			List<PointClassDef> pointClassList = pointClassDefDAO.findPtClassByJinst(params);
			for (PointClassDef pointClassDef : pointClassList) {
				sb.append("<option value=\"")
						.append(pointClassDef.getPtClass()).append("\">")
						.append(pointClassDef.getClassName()).append("</option>");
			}
		} else if (StringUtils.equals(ruleType,PrizeType.COUPONS.getValue())) {
			// 使用赠券子类型
			List<CouponClassDef> couponClassList = couponClassDefDAO.findCouponClassByJinst(params);
			for (CouponClassDef couponClassDef : couponClassList) {
				sb.append("<option value=\"")
						.append(couponClassDef.getCoupnClass()).append("\">")
						.append(couponClassDef.getClassName()).append("</option>");
			}
		}
		this.respond(sb.toString());
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public PrizeDef getPrizeDef() {
		return prizeDef;
	}

	public void setPrizeDef(PrizeDef prizeDef) {
		this.prizeDef = prizeDef;
	}

	public BrushSet getBrushSet() {
		return brushSet;
	}

	public void setBrushSet(BrushSet brushSet) {
		this.brushSet = brushSet;
	}

	public List getAwdTypeList() {
		return awdTypeList;
	}

	public void setAwdTypeList(List awdTypeList) {
		this.awdTypeList = awdTypeList;
	}

	public String getPinstId_sel() {
		return pinstId_sel;
	}

	public void setPinstId_sel(String pinstId_sel) {
		this.pinstId_sel = pinstId_sel;
	}

	
	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public List<DrawDef> getDrawList() {
		return drawList;
	}

	public void setDrawList(List<DrawDef> drawList) {
		this.drawList = drawList;
	}
	
	public List getPInstTypeList() {
		return pInstTypeList;
	}

	public void setPInstTypeList(List instTypeList) {
		pInstTypeList = instTypeList;
	}
	
	/**
	 * 当前登陆用户角色为发卡机构点时
	 */
	public String getCardIssuerNo() {
		String cardIssuerNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			cardIssuerNo = getSessionUser().getBranchNo();
		}
		return cardIssuerNo;
	}

	/**
	 * 当前登陆用户为商户时
	 */
	public String getMerchantNo() {
		String merchantNo = "";
		if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			merchantNo = getSessionUser().getMerchantNo();
		}
		return merchantNo;
	}

}
