package gnete.card.web.couponprize;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CouponRegDAO;
import gnete.card.entity.CouponReg;
import gnete.card.entity.type.PtExchgType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.CouponRegService;
import gnete.card.web.BaseAction;

public class CouponRegAction extends BaseAction{
	
	@Autowired
	private CouponRegDAO couponRegDAO;
	
	@Autowired
	private CardInfoDAO cardInfoDAO;
	
	@Autowired
	private CouponRegService couponRegService;
	
	private CouponReg couponReg;
	
	private Paginater page;
	
	private Long couponRegId;
	
	// 积分兑换类型列表
	private Collection<PtExchgType> ptExchgTypeList;

	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (couponReg != null) {
			params.put("ticketNo", couponReg.getTicketNo());
		}
		this.page = this.couponRegDAO.findCouponReg(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得赠券派赠明细
	public String detail() throws Exception {
		
		this.couponReg = (CouponReg) this.couponRegDAO.findByPk(this.couponReg.getCouponRegId());
		
		this.log("查询赠券派赠["+this.couponReg.getCouponRegId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
	//	initPage();
		return ADD;
	}	
	
	// 派赠登记并更新赠券账户余额
	public String add() throws Exception {			
		initPage();
		
		//派赠登记成功更新赠券账户余额
		if(this.couponRegService.addCouponReg(couponReg, this.getSessionUserCode())){
			// 发报文请求后台处理
			/*BigDecimal backAmt = this.couponReg.getBackAmt();
			String cardId = this.couponReg.getCardId();
			String acctId = ((CardInfo)this.cardInfoDAO.findByPk(cardId)).getAcctId();*/
			
			/*this.couponRegService.updateCouponBal(backAmt, acctId);*/
		}
		
		String msg = "赠券派赠登记["+this.couponReg.getCouponRegId()+"]成功！";
		this.addActionMessage("/couponAward/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		initPage();
		this.couponReg = (CouponReg)this.couponRegDAO.findByPk(this.couponReg.getCouponRegId());
		return MODIFY;
	}
	
	// 修改派赠登记信息
	public String modify() throws Exception {
		this.couponRegService.modifyCouponReg(this.couponReg);
		this.addActionMessage("/couponAward/list.do", "修改赠券派赠信息成功！");	
		return SUCCESS;
	}
	
	// 删除赠券派赠登记
	public String delete() throws Exception {
		
		this.couponRegService.deleteCouponReg(this.getCouponRegId());
		String msg = "删除赠品派赠登记[" +this.getCouponRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/couponAward/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() {
		//加载积分兑换类型做为下拉列表
		this.ptExchgTypeList = PtExchgType.getAll();
	}

	public CouponRegDAO getCouponRegDAO() {
		return couponRegDAO;
	}

	public void setCouponRegDAO(CouponRegDAO couponRegDAO) {
		this.couponRegDAO = couponRegDAO;
	}

	public CouponReg getCouponReg() {
		return couponReg;
	}

	public void setCouponReg(CouponReg couponReg) {
		this.couponReg = couponReg;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public Long getCouponRegId() {
		return couponRegId;
	}

	public void setCouponRegId(Long couponRegId) {
		this.couponRegId = couponRegId;
	}

	public void setCouponRegService(CouponRegService couponRegService) {
		this.couponRegService = couponRegService;
	}

	public CouponRegService getCouponRegService() {
		return couponRegService;
	}

	public void setCardInfoDAO(CardInfoDAO cardInfoDAO) {
		this.cardInfoDAO = cardInfoDAO;
	}

	public CardInfoDAO getCardInfoDAO() {
		return cardInfoDAO;
	}

	public void setPtExchgTypeList(Collection<PtExchgType> ptExchgTypeList) {
		this.ptExchgTypeList = ptExchgTypeList;
	}

	public Collection<PtExchgType> getPtExchgTypeList() {
		return ptExchgTypeList;
	}

}
