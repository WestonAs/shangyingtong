package gnete.card.web.customerservice;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.UnfreezeRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UnfreezeReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UnfreezeRegService;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 批量卡账户解付
 * @author aps-lib
 *
 */
public class UnfreezeRegBatAction extends BaseAction {

	@Autowired
	private UnfreezeRegDAO unfreezeRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private UnfreezeRegService unfreezeRegService;
	
	private List statusList;
	private Collection certTypeList;
	private UnfreezeReg unfreezeReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private int cardNum = 0;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		if (isCenterOrCenterDeptRoleLogined()) {
		} else if (isFenzhiRoleLogined()) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			List<BranchInfo> branchList = cardBranchList;
			for (BranchInfo branchInfo : branchList) {
				cardBranchList.addAll(this.branchInfoDAO.findCardProxy(branchInfo.getBranchCode(),
						ProxyType.SELL));
			}
		} else if (isCardOrCardDeptRoleLogined()) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			cardBranchList.addAll(this.branchInfoDAO.findCardProxy(getSessionUser().getBranchNo(),
					ProxyType.SELL));
		} else if (isCardSellRoleLogined()) {
			params.put("branchCode", this.getSessionUser().getBranchNo());
		} else {
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("branches", cardBranchList);
		}
		
		if (unfreezeReg != null) {
			params.put("unfreezeId", unfreezeReg.getUnfreezeId());
		}

		this.page = this.unfreezeRegDAO.findUnfreezeBat(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;

	}
	
	// 明细页面
	public String detail() throws Exception {
		this.unfreezeReg = (UnfreezeReg) this.unfreezeRegDAO.findByPk(this.unfreezeReg.getUnfreezeId());

//		this.log("查询解付批次[" + this.unfreezeReg.getUnfreezeId() + "]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()) {
			throw new BizException("非发卡机构、机构网点、售卡代理，不允许进行操作。");
		}
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		unfreezeReg.setBranchCode(branch.getBranchCode());
		unfreezeReg.setBranchName(branch.getBranchName());
		
		//保存数据
		this.unfreezeRegService.addUnfreezeRegBat(unfreezeReg, this.getSessionUserCode());
		
		String msg = "新增解付批次["+this.unfreezeReg.getUnfreezeId()+"]成功！";
		this.addActionMessage("/unfreezeBat/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 校验起始卡号是否输入正确
	public void checkStartCardId() throws Exception {
		String startCard = this.unfreezeReg.getStartCard();
		CardInfo startCardInfo = null;
		try {
			startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
			Assert.notNull(startCardInfo, "起始卡号不存在。");
		}
		catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		
		this.respond("{'success':"+ true + "}");
	}
	
	// 校验批量解付卡号
	public void checkCardId() throws Exception {
        JSONObject object = new JSONObject();
		
		String startCard = request.getParameter("cardId");
		String cardCount = request.getParameter("cardCount");
		String endCard = null;
		try {
			CardInfo cardInfo = null;
			int count = 1; 
			if (StringUtils.isNotEmpty(cardCount)) {
				Assert.isTrue(NumberUtils.isDigits(cardCount), "卡数量必须为正整数");
				count = Integer.valueOf(cardCount);
				Assert.notTrue(count>1000 , "不能解付超过1000张卡。");
			}

			String[] cardArray =null;
			// 检查起始卡号和结束卡号的卡BIN是否一致
			if(startCard.length()==19){ // 新卡号
				cardArray = CardUtil.getCard(startCard, count);
				endCard = cardArray[cardArray.length-1];
			} else if(startCard.length()==18){ // 旧卡号
				cardArray = CardUtil.getOldCard(startCard, count);
				endCard = cardArray[cardArray.length-1];
			} else { // 只支持18位的旧卡号和19位的新卡号
				throw new BizException("只支持18位的旧卡号和19位的新卡号！");
			}
			
			UserInfo user = this.getSessionUser();
			for (String cardNo : cardArray) {
				cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNo);
				Assert.notNull(cardInfo, "卡号[" + cardNo + "]不存在");
				Assert.equals(cardInfo.getCardStatus(), CardState.ACTIVE.getValue(), "卡号[" + cardNo + "]不是“正常(已激活)”状态的卡");
				
				// 发卡机构和机构部门，检查卡是否都为发卡机构发行的卡
				if (isCardOrCardDeptRoleLogined()) {
					Assert.equals(user.getBranchNo(), cardInfo.getCardIssuer(), "卡号[" + cardInfo.getCardId()
							+ "]不是登陆机构发行的卡，不能解付。");
				}
				// 售卡代理，检查卡是否都为售卡代理所售的卡
				else if (isCardSellRoleLogined()) {
					Assert.equals(user.getBranchNo(), cardInfo.getSaleOrgId(), "卡号[" + cardInfo.getCardId()
							+ "]不是登陆机构所售的卡，不能解付。");
				}
			}
			Assert.equals(StringUtils.substring(startCard, 3, 10), StringUtils.substring(endCard, 3, 10), 
				"起始卡号的卡BIN["+StringUtils.substring(startCard, 3, 10)+
				"]和结束卡号的卡BIN["+StringUtils.substring(endCard, 3, 10)+"]不一致。");
			object.put("endCardId", cardInfo.getCardId());
			object.put("success", true);
		} catch (Exception e) {
			object.put("success", false);
			object.put("errorMsg", e.getMessage());
		}
		
		this.respond(object.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public Collection getCertTypeList() {
		return certTypeList;
	}

	public void setCertTypeList(Collection certTypeList) {
		this.certTypeList = certTypeList;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public UnfreezeReg getUnfreezeReg() {
		return unfreezeReg;
	}

	public void setUnfreezeReg(UnfreezeReg unfreezeReg) {
		this.unfreezeReg = unfreezeReg;
	}

}
