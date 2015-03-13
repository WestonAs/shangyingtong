package gnete.card.web.intgratedservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.LossCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.LossCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LossCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 批量卡挂失
 * @author aps-lib
 *
 */
public class LossCardRegBatAction extends BaseAction {

	@Autowired
	private LossCardRegDAO lossCardRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private LossCardService losscardService;
	
	private List statusList;
	private Collection certTypeList;
	private LossCardReg lossCardReg;
	private Paginater page;
	private int cardNum = 0;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (lossCardReg != null) {
			params.put("lossBatchId", lossCardReg.getLossBatchId());
			params.put("branchCode", lossCardReg.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(lossCardReg.getBranchName()));
		}
		
		// 如果登录用户为运营中心，运营中心部门
		if (isCenterOrCenterDeptRoleLogined()){
		}
		// 运营分支机构可以看到管理的发卡机构及其售卡代理的记录
		else if(isFenzhiRoleLogined()) {
			params.put("fenzhiList", super.getMyManageFenzhi());
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (isCardOrCardDeptRoleLogined()) {
			params.put("cardBranchList", this.branchInfoDAO.findChildrenList(this.getLoginBranchCode()));
		}
		// 售卡代理
		else if(isCardSellRoleLogined()){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		params.put("isBatch", true);
		
		this.page = this.lossCardRegDAO.findLossCard(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;

	}
	
	// 明细页面
	public String detail() throws Exception {
		this.lossCardReg = (LossCardReg) this.lossCardRegDAO.findByPk(this.lossCardReg.getLossBatchId());

		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();
		
		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()){
			throw new BizException("非发卡机构、机构网点、售卡代理，不允许进行操作。");
		}
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		lossCardReg.setBranchCode(branch.getBranchCode());
		lossCardReg.setBranchName(branch.getBranchName());
		
		//保存数据
		this.losscardService.addLossCardBat(lossCardReg, this.getSessionUser());
		
		String msg = "新增挂失批次["+this.lossCardReg.getLossBatchId()+"]成功！";
		this.addActionMessage("/intgratedService/lossCardBat/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 校验起始卡号是否输入正确
	public void checkStartCardId() throws Exception {
		JSONObject object = new JSONObject();
		try {
			CardInfo startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(lossCardReg.getStartCard());
			Assert.notNull(startCardInfo, "起始卡号不存在。");
			
			object.put("success", true);
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		
		this.respond(object.toString());
	}
	
	// 校验批量挂失卡号是否输入正确
	public void checkCardId() throws Exception {
		String startCard = this.lossCardReg.getStartCard();
		String endCard = this.lossCardReg.getEndCard();
		
		JSONObject object = new JSONObject();
		try {
			// 检查起始卡号和结束卡号是否存在
			CardInfo startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
			Assert.notNull(startCardInfo, "起始卡号[" + startCard + "]不存在。");
			CardInfo endCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(endCard);
			Assert.notNull(endCardInfo, "结束卡号[" + endCard + "]不存在。");
			
			// 检查起始卡号和结束卡号的卡BIN是否一致
			Assert.equals(startCardInfo.getCardBin(), endCardInfo.getCardBin(), "起始卡号和结束卡号卡BIN不一致，不能挂失。");
			
	    	long cardCnt = this.cardInfoDAO.getCardNum(startCard,endCard);
			Assert.notTrue(cardCnt <= 0 , "结束卡号不能小于起始卡号。");
			Assert.notTrue(cardCnt > 1000 , "不能挂失超过1000张卡。");

			object.put("cardNum", cardCnt);
			
			// 取得起始卡号和结束卡号之间的卡列表
			List<CardInfo> cardList = this.cardInfoDAO.getCardList(startCard, endCard);
			// 遍历检查卡是否满足挂失要求
			for(CardInfo card : cardList){
				Assert.isTrue(card.getCardStatus().equals(CardState.PRESELLED.getValue())
						|| card.getCardStatus().equals(CardState.ACTIVE.getValue()), "只有预售出或者正常状态的磁卡才能挂失。");
				
				//检查登录机构是否有权限
				CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), card, "卡挂失");
			}
			object.put("success", true);
		} catch (Exception e){
			object.put("success", false);
			object.put("error", e.getMessage());
		}
		
		this.respond(object.toString());
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
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

	public LossCardReg getLossCardReg() {
		return lossCardReg;
	}

	public void setLossCardReg(LossCardReg lossCardReg) {
		this.lossCardReg = lossCardReg;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

}
