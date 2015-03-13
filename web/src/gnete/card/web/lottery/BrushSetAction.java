package gnete.card.web.lottery;

import flink.etc.MatchMode;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BrushSetDAO;
import gnete.card.dao.DrawDefDAO;
import gnete.card.dao.PrizeDefDAO;
import gnete.card.entity.BrushSet;
import gnete.card.entity.DrawDef;
import gnete.card.entity.PrizeDef;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LotteryService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: BrushSetAction.java
 * 
 * @description: 即刷即中设置。
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-19
 */
public class BrushSetAction extends BaseAction {

	@Autowired
	private BrushSetDAO brushSetDAO;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private DrawDefDAO drawDefDAO;
	@Autowired
	private PrizeDefDAO prizeDefDAO;

	private BrushSet brushSet;

	private List<DrawDef> drawDefList;
	private List<PrizeDef> prizeDefList;
	
	/** 机构类型代码 */
	private List pInstTypeList;

	private Paginater page;

	@Override
	public String execute() throws Exception {
		pInstTypeList = IssType.getAll();
		Map<String, Object> params = new HashMap<String, Object>();
		if (brushSet != null) {
			params.put("drawId", MatchMode.ANYWHERE.toMatchString(brushSet.getDrawId()));
		}
		page = brushSetDAO.findBrushSet(params, getPageNumber(),getPageSize());
		return LIST;
	}

	public String detail() throws Exception {
		brushSet = (BrushSet) brushSetDAO.findByPk(brushSet);
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
			throw new BizException("非商户或发卡机构禁止新增一个新的即刷即中设置。");
		}
		drawDefList = drawDefDAO.findByIssId(issId);
		return ADD;
	}
	
	/**
	 * 根据抽奖活动ID查询奖项定义列表
	 * @return
	 */
	public String prizeDefList() throws Exception {
		if (StringUtils.isNotBlank(brushSet.getDrawId())) {
			prizeDefList = prizeDefDAO.findByDrawId(brushSet.getDrawId());
		}
		return "prizeDefList";
	}

	public String add() throws Exception {
		lotteryService.addBrushSet(brushSet, getSessionUser());
		String msg = LogUtils.r("新增促销活动ID为[{0}]成功。", brushSet.getDrawId());
		log(msg, UserLogType.ADD);
		addActionMessage("/lottery/brushSet/list.do", msg);
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		brushSet = (BrushSet) brushSetDAO.findByPk(brushSet);
		String issId = null;
		if (getLoginRoleTypeCode().equals(RoleType.CARD.getValue())) {
			// 当前用户为发卡机构
			issId = getSessionUser().getBranchNo();
		} else if (getLoginRoleTypeCode().equals(RoleType.MERCHANT.getValue())) {
			// 当前用户为商户
			issId = getSessionUser().getMerchantNo();
		} else {
			throw new BizException("非商户或发卡机构禁止新增一个新的即刷即中设置。");
		}
		drawDefList = drawDefDAO.findByIssId(issId);
		return MODIFY;
	}
	
	public String modify() throws Exception {
		return SUCCESS;
	}

	public String delete() throws Exception {
		return SUCCESS;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public BrushSet getBrushSet() {
		return brushSet;
	}

	public void setBrushSet(BrushSet brushSet) {
		this.brushSet = brushSet;
	}

	public List<DrawDef> getDrawDefList() {
		return drawDefList;
	}

	public void setDrawDefList(List<DrawDef> drawDefList) {
		this.drawDefList = drawDefList;
	}

	public List<PrizeDef> getPrizeDefList() {
		return prizeDefList;
	}

	public void setPrizeDefList(List<PrizeDef> prizeDefList) {
		this.prizeDefList = prizeDefList;
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
	
	public List getPInstTypeList() {
		return pInstTypeList;
	}

	public void setPInstTypeList(List instTypeList) {
		pInstTypeList = instTypeList;
	}

}
