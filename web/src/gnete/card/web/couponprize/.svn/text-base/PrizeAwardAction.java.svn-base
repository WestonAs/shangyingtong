package gnete.card.web.couponprize;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.AwardRegDAO;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.entity.AwardReg;
import gnete.card.entity.AwardRegKey;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.state.AwdState;
import gnete.card.entity.type.TransType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.AwardRegisterService;
import gnete.card.web.BaseAction;

public class PrizeAwardAction extends BaseAction {

	@Autowired
	private AwardRegDAO awardRegDAO;
	
	@Autowired
	private CardExtraInfoDAO cardExtraInfoDAO;
	
	@Autowired
	private AwardRegisterService awardRegisterService;

	private AwardReg awardReg;
	
	private CardExtraInfo cardExtraInfo;

	private Paginater page;
	
	// 中奖状态
	private Collection awdStatusList;
	
	// 交易类型
	private Collection transTypeList;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//加载中奖状态列表
		this.awdStatusList = AwdState.ALL.values();	
		//加载交易类型列表
		this.transTypeList = TransType.ALL.values();	
		
		if (awardReg != null) {
			params.put("custName", MatchMode.ANYWHERE.toMatchString(awardReg.getCustName()));
			params.put("credNo",  MatchMode.START.toMatchString(awardReg.getCredNo()));
			params.put("cardId", awardReg.getCardId());
			params.put("transSn", awardReg.getTransSn());
			params.put("awdTicketNo", awardReg.getAwdTicketNo());
		}
		this.page = this.awardRegDAO.findAwardRegCusCred(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得奖品以及中奖人的明细
	public String detail() throws Exception {
		
		AwardRegKey awardRegKey = new AwardRegKey();
		awardRegKey.setAwdTicketNo(this.awardReg.getAwdTicketNo());
		awardRegKey.setDrawId(this.awardReg.getDrawId());
		
		this.awardReg = (AwardReg) this.awardRegDAO.findByPk(awardRegKey);
		this.cardExtraInfo = (CardExtraInfo)this.cardExtraInfoDAO.findByPk(this.awardReg.getCardId());
		
		this.log("查询奖品["+this.awardReg.getAwdTicketNo()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		
		initPage();
		
		AwardRegKey awardRegKey = new AwardRegKey();
		awardRegKey.setAwdTicketNo(this.awardReg.getAwdTicketNo());
		awardRegKey.setDrawId(this.awardReg.getDrawId());
		
		this.awardReg = (AwardReg)this.awardRegDAO.findByPk(awardRegKey);
			
		return MODIFY;
	}
	
	// 更新中奖状态，录入兑奖人
	public String modify() throws Exception {
		
		this.awardRegisterService.updateAwardRegister(this.awardReg, this.getSessionUserCode());
		
		this.addActionMessage("/prizeAward/list.do", "兑奖成功！");	
		return SUCCESS;
	}
	
	// 删除奖品登记
	public String delete() throws Exception {
		
		this.awardRegisterService.delete(this.awardReg.getDrawId(), this.awardReg.getAwdTicketNo());
		
		String msg = "删除奖品记录[" +this.awardReg.getAwdTicketNo()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/prizeAward/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		
		//加载中奖状态列表
		this.awdStatusList = AwdState.getAwdPrizeState();	
		//加载交易类型列表
		this.transTypeList = TransType.getAll();	
	}

	public void setAwardReg(AwardReg awardReg) {
		this.awardReg = awardReg;
	}

	public AwardReg getAwardReg() {
		return awardReg;
	}

	public void setAwardRegDAO(AwardRegDAO awardRegDAO) {
		this.awardRegDAO = awardRegDAO;
	}

	public AwardRegDAO getAwardRegDAO() {
		return awardRegDAO;
	}



	public void setAwardRegisterService(AwardRegisterService awardRegisterService) {
		this.awardRegisterService = awardRegisterService;
	}



	public AwardRegisterService getAwardRegisterService() {
		return awardRegisterService;
	}



	public void setPage(Paginater page) {
		this.page = page;
	}



	public Paginater getPage() {
		return page;
	}



	public void setAwdStatusList(Collection awdStatusList) {
		this.awdStatusList = awdStatusList;
	}



	public Collection getAwdStatusList() {
		return awdStatusList;
	}



	public void setTransTypeList(Collection transTypeList) {
		this.transTypeList = transTypeList;
	}



	public Collection getTransTypeList() {
		return transTypeList;
	}

	public void setCardExtraInfo(CardExtraInfo cardExtraInfo) {
		this.cardExtraInfo = cardExtraInfo;
	}

	public CardExtraInfo getCardExtraInfo() {
		return cardExtraInfo;
	}

	public void setCardExtraInfoDAO(CardExtraInfoDAO cardExtraInfoDAO) {
		this.cardExtraInfoDAO = cardExtraInfoDAO;
	}

	public CardExtraInfoDAO getCardExtraInfoDAO() {
		return cardExtraInfoDAO;
	}
	

}
