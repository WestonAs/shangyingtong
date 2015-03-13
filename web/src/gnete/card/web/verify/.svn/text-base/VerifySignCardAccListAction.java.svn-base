package gnete.card.web.verify;

import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.SignCardAccListDAO;
import gnete.card.entity.SignCardAccList;
import gnete.card.entity.SignCardAccListKey;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.SignCardAccListState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.VerifyService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: VerifySignCardAccListAction.java
 *
 * @description:
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: MaoJian
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-8
 */
public class VerifySignCardAccListAction extends BaseAction {
	
	@Autowired
	private SignCardAccListDAO signCardAccListDAO;
	@Autowired
	private VerifyService verifyService;
	
	private SignCardAccList card;
	private Paginater page;
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchNo", this.getSessionUser().getBranchNo());
		if(card!=null){
			params.put("accMonth",card.getAccMonth());
			params.put("cardId", card.getCardId());
		}
		this.page = signCardAccListDAO.findSignCardAccList(params, this.getPageNumber(), this.getPageSize());
		List list = page.getList();
		String curDate = DateUtil.getCurrentDate();
		for(Iterator it=list.iterator();it.hasNext();){
			SignCardAccList signCard = (SignCardAccList)it.next();
			if(SignCardAccListState.INITIAL.getValue().equals(signCard.getStatus())){
				if(signCard.getExpDate().compareTo(curDate)>=0){
					signCard.setExpFlag("1");
				}
			}
		}
		return LIST;
	}
	
	public String detail() throws Exception {
		this.getCardIssuer();
		this.card = (SignCardAccList)this.signCardAccListDAO.findByPk(card);
		String msg = "查询签单卡账单明细类型[" + card.getCardId() + "明细]成功";
		this.log(msg, UserLogType.SEARCH);
		addActionMessage("/fee/pointClassDef/list.do", msg);
		return DETAIL;
	}
	

	public String showVerify() throws Exception {
		SignCardAccListKey key = new SignCardAccListKey();
		key.setAccMonth(request.getParameter("accMonth"));
		key.setCardId(request.getParameter("cardId"));
		this.card = (SignCardAccList)this.signCardAccListDAO.findByPk(key);
		return "verify";
	}
	public String verify() throws Exception {

		this.verifyService.verifySignCardAccList(card, getSessionUser());
		
		String msg = "签单卡账单核销["+ card.getCardId() +"]成功";
		this.log(msg, UserLogType.UPDATE);
		addActionMessage("/verify/verifySignCardAccList/list.do", msg);
		return SUCCESS;
	}


	public SignCardAccList getCard() {
		return card;
	}


	public void setCard(SignCardAccList card) {
		this.card = card;
	}


	public Paginater getPage() {
		return page;
	}


	public void setPage(Paginater page) {
		this.page = page;
	}

	private String getCardIssuer() throws BizException{
		UserInfo user = this.getSessionUser();
		String cardIssuer = null;
		cardIssuer = user.getBranchNo();
//		if(user.getRole().equals(IssType.CARD.getValue())){
//			cardIssuer = user.getBranchNo();
//		}else{
//			throw new BizException("用户角色不是发卡机构,不允许进行操作");
//		}
		return cardIssuer;
	}



}
