package gnete.card.web.intgratedservice;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.UnLossCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UnLossCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UnLossCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 批量卡解挂
 * @author aps-lib
 *
 */
public class UnLossCardRegBatAction extends BaseAction {

	@Autowired
	private UnLossCardRegDAO unLossCardRegDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private UnLossCardService unLossCardService;
	
	private List statusList;
	private Collection certTypeList;
	private UnLossCardReg unLossCardReg;
	private Paginater page;
	private List<BranchInfo> cardBranchList;
	private int cardNum = 0;
		
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		cardBranchList = new ArrayList<BranchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门
		if (isCenterOrCenterDeptRoleLogined()){
		}
		// 运营分支机构可以看到管理的发卡机构及其售卡代理的记录
		else if(isFenzhiRoleLogined()) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			List<BranchInfo> branchList = cardBranchList;
			for(BranchInfo branchInfo:branchList){
				cardBranchList.addAll(this.branchInfoDAO.findCardProxy(branchInfo.getBranchCode(), ProxyType.SELL));
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (isCardOrCardDeptRoleLogined()) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			cardBranchList.addAll(this.branchInfoDAO.findCardProxy(getSessionUser().getBranchNo(), ProxyType.SELL));
		}
		// 售卡代理
		else if(isCardSellRoleLogined()){
			params.put("branchCode", this.getSessionUser().getBranchNo());
		}
		else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)) {
			params.put("branches", cardBranchList);
		}
		
		if (unLossCardReg != null) {
			params.put("unlossBatchId", unLossCardReg.getUnlossBatchId());
			params.put("branchCode", unLossCardReg.getBranchCode());
			params.put("branchName", MatchMode.ANYWHERE.toMatchString(unLossCardReg.getBranchName()));
		}

		this.page = this.unLossCardRegDAO.findUnLossCardBat(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;

	}
	
	// 明细页面
	public String detail() throws Exception {
		this.unLossCardReg = (UnLossCardReg) this.unLossCardRegDAO.findByPk(this.unLossCardReg.getUnlossBatchId());
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		this.checkEffectiveCertUser();

		if (!isCardOrCardDeptRoleLogined() && !isCardSellRoleLogined()) {
			throw new BizException("非发卡机构、机构网点、售卡代理，不允许进行操作。");
		}
		initPage();
		return ADD;
	}
	
	// 新增信息
	public String add() throws Exception {
		this.checkEffectiveCertUser();
		
		UserInfo user = this.getSessionUser();
		BranchInfo branch = (BranchInfo) this.branchInfoDAO.findByPk(user.getBranchNo());
		unLossCardReg.setBranchCode(branch.getBranchCode());
		unLossCardReg.setBranchName(branch.getBranchName());
		
		//保存数据
		this.unLossCardService.addUnLossCardBat(unLossCardReg, this.getSessionUserCode());
		
		String msg = "新增解挂批次["+this.unLossCardReg.getUnlossBatchId()+"]成功！";
		this.addActionMessage("/intgratedService/unLossCardBat/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 校验起始卡号是否输入正确
	public void checkStartCardId() throws Exception {
		String startCard = this.unLossCardReg.getStartCard();
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
	
	// 校验批量挂失卡号是否输入正确
	public void checkCardId() throws Exception {
		String startCard = this.unLossCardReg.getStartCard();
		String endCard = this.unLossCardReg.getEndCard();
		CardInfo startCardInfo = null;
		CardInfo endCardInfo = null;
		
		try {
			// 检查起始卡号和结束卡号是否存在
			startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
			Assert.notNull(startCardInfo, "起始卡号不存在。");
			endCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(endCard);
			Assert.notNull(endCardInfo, "结束卡号不存在。");
			
			// 检查起始卡号和结束卡号的卡BIN是否一致
			Assert.equals(startCardInfo.getCardBin(), endCardInfo.getCardBin(), "起始卡号和结束卡号卡BIN不一致，不能解挂。");
			
	    	this.cardNum = this.cardInfoDAO.getCardNum(startCard,endCard).intValue();
			Assert.notTrue(this.cardNum<=0 , "结束卡号要大于起始卡号。");
			Assert.notTrue(this.cardNum>1000 , "不能解挂超过1000张卡。");
			
			// 取得起始卡号和结束卡号之间的卡列表
			List<CardInfo> cardList = this.cardInfoDAO.getCardList(startCard, endCard);
			// 遍历检查卡是否满足解挂要求
			for(CardInfo card:cardList){
				Assert.isTrue(card.getCardStatus().equals(CardState.LOSSREGISTE.getValue()), "只有挂失状态的的卡才能解挂。");
				
				//检查登录机构是否有权限
				CardOprtPrvlgUtil.checkPrvlg(this.getLoginRoleTypeCode(), this.getLoginBranchCode(), card, "卡解挂");
			}
		} catch (Exception e){
			this.respond("{'success':"+false+", 'error':'" + e.getMessage() + "'}");
			return;
		}
		this.respond("{'success':"+ true +", 'cardNum':'" + new BigDecimal(cardNum).toString() + "'}");
	}

	private void initPage() {
		// 加载类型证件列表
		this.certTypeList = CertType.getAll();
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

	public UnLossCardReg getUnLossCardReg() {
		return unLossCardReg;
	}

	public void setUnLossCardReg(UnLossCardReg unLossCardReg) {
		this.unLossCardReg = unLossCardReg;
	}

}
