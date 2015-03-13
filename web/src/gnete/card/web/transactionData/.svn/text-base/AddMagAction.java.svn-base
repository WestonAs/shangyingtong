package gnete.card.web.transactionData;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flink.util.Paginater;
import gnete.card.dao.AddMagRegDAO;
import gnete.card.entity.AddMagReg;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.TransactionDataService;
import gnete.card.web.BaseAction;

public class AddMagAction extends BaseAction{

	@Autowired
	private AddMagRegDAO addMagRegDAO;
	
	@Autowired
	private TransactionDataService transactionDataService;
		
	private  AddMagReg addMagReg;
	
	private Paginater page;
	
	private long addMagId;
	
	@Override
	public String execute() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		if (addMagReg != null) {
			params.put("cardId", addMagReg.getCardId());
		}
		this.page = this.addMagRegDAO.findAddMag(params, this.getPageNumber(), this.getPageSize());
		
		return LIST;
	}
	
	// 取得写磁记录明细
	public String detail() throws Exception{
		this.addMagReg = (AddMagReg) this.addMagRegDAO.findByPk(this.addMagReg.getAddMagId());

//		this.log("查询写磁["+this.addMagReg.getAddMagId()+"]记录详细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	public String delete() throws Exception {
		
		this.transactionDataService.delete(this.getAddMagId());
		
		String msg = "删除写磁记录[" +this.getAddMagId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/addMag/list.do", msg);
		return SUCCESS;
	}
	
	public void setAddMagRegDAO(AddMagRegDAO addMagRegDAO) {
		this.addMagRegDAO = addMagRegDAO;
	}
	
	public AddMagRegDAO getAddMagRegDAO() {
		return addMagRegDAO;
	}
	
	public void setAddMagReg(AddMagReg addMagReg) {
		this.addMagReg = addMagReg;
	}
	
	public AddMagReg getAddMagReg() {
		return addMagReg;
	}
	
	public void setPage(Paginater page) {
		this.page = page;
	}
	
	public Paginater getPage() {
		return page;
	}

	public void setAddMagId(long addMagId) {
		this.addMagId = addMagId;
	}

	public long getAddMagId() {
		return addMagId;
	}

	public void setTransactionDataService(TransactionDataService transactionDataService) {
		this.transactionDataService = transactionDataService;
	}

	public TransactionDataService getTransactionDataService() {
		return transactionDataService;
	}

	

}
