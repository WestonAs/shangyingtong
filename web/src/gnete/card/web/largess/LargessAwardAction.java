package gnete.card.web.largess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import flink.etc.MatchMode;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.LargessDefDAO;
import gnete.card.dao.LargessRegDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.LargessDef;
import gnete.card.entity.LargessReg;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LargessService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 派送赠品
 * @author aps-lib
 *
 */
public class LargessAwardAction extends BaseAction{

	@Autowired
	private LargessRegDAO largessRegDAO;
	@Autowired
	private LargessDefDAO largessDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private LargessService largessService;
	
	private LargessReg largessReg;
	private LargessDef largessDef;
	private Paginater page;
	private Long largessRegId;
	private List<LargessDef> largessDefList;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (largessReg != null) {
			params.put("ticketNo", largessReg.getTicketNo());
			params.put("largessName", MatchMode.ANYWHERE.toMatchString(largessReg.getLargessName()));
		}
		
		cardBranchList = new ArrayList<BranchInfo>();
		merchInfoList = new ArrayList<MerchInfo>();
		
		// 如果登录用户为运营中心，运营中心部门运营分支机构时
		if (RoleType.CENTER.getValue().equals(getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())){
		}
		// 运营分支机构
		else if(RoleType.FENZHI.getValue().equals(getLoginRoleType())) {
			cardBranchList.addAll(this.branchInfoDAO.findCardByManange(getSessionUser().getBranchNo()));
			this.merchInfoList = this.getMyFranchMerchByFenzhi(this.getSessionUser().getBranchNo());
			if(CollectionUtils.isEmpty(cardBranchList)&&CollectionUtils.isEmpty(merchInfoList)){
				params.put("branchCode", " ");
			}
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo) this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			params.put("branchCode", this.getSessionUser().getMerchantNo());
		} else{
			throw new BizException("没有权限查询。");
		}
		
		if (CollectionUtils.isNotEmpty(cardBranchList)||CollectionUtils.isNotEmpty(merchInfoList)) {
			int length = cardBranchList.size()+merchInfoList.size();
			String[] jinstIds = new String[length];
			int i = 0;
			for( ; i<cardBranchList.size(); i++){
				jinstIds[i] = (cardBranchList.get(i)).getBranchCode();
			}
			for( ; i<length; i++){
				jinstIds[i] = (merchInfoList.get(i-cardBranchList.size())).getMerchId();
			}
			params.put("jinstIds", jinstIds);
		}
		
		this.page = this.largessRegDAO.findLargessReg(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得派赠登记的明细
	public String detail() throws Exception {
		this.largessReg = (LargessReg) this.largessRegDAO.findByPk(this.largessReg.getLargessRegId());
		this.largessDef = (LargessDef) this.largessDefDAO.findByPk(this.largessReg.getLargessId());
		this.log("查询派赠编号["+this.largessReg.getLargessRegId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		initPage();
		this.largessDefList = this.getLargessDefineList();
		return ADD;
	}	
	
	// 派赠登记
	public String add() throws Exception {		
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			this.largessReg.setBranchCode(roleInfo.getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			this.largessReg.setBranchCode(roleInfo.getMerchantNo());
		}
		else if(RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			this.largessReg.setBranchCode(roleInfo.getBranchNo());
		}
		this.largessService.addLargessReg(largessReg, this.getSessionUserCode());
		String msg = "派赠登记["+this.largessReg.getLargessRegId()+"]成功！";
		this.addActionMessage("/largessAward/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		initPage();
		this.largessDefList = this.getLargessDefList();
		this.largessReg = (LargessReg)this.largessRegDAO.findByPk(this.largessReg.getLargessRegId());
		return MODIFY;
	}
	
	// 修改赠品登记信息
	public String modify() throws Exception {
		this.largessService.modifyLargessReg(this.largessReg, this.getSessionUserCode());
		this.addActionMessage("/largessAward/list.do", "修改派赠登记成功！");	
		return SUCCESS;
	}
	
	// 删除赠品登记记录
	public String delete() throws Exception {
		this.largessService.delete(this.getLargessRegId());
		String msg = "删除派赠登记[" +this.getLargessRegId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/largessAward/list.do", msg);
		return SUCCESS;
	}
	
	private void initPage() throws BizException{
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&
				!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())&
				!RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机构、售卡代理或者商户不能操作。");
		}
	}
	
	// 查询登录用户定义的赠品类型列表，服务端查询，返回到前端
	private List<LargessDef> getLargessDefineList() throws BizException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		RoleInfo roleInfo = this.getSessionUser().getRole();
		
		// 发卡机构
		if(RoleType.CARD.getValue().equals(roleInfo.getRoleType())){
			params.put("jinstId", roleInfo.getBranchNo());
		}
		// 商户
		else if(RoleType.MERCHANT.getValue().equals(roleInfo.getRoleType())){
			params.put("jinstId", roleInfo.getMerchantNo());
		}
		// 售卡代理
		else if(RoleType.CARD_SELL.getValue().equals(this.getLoginRoleType())){
			params.put("jinstIds", this.getMyCardBranchList());
		}
		
		return this.largessDefDAO.findLargessDefByBranch(params);
	}

	public void setLargessRegDAO(LargessRegDAO largessRegDAO) {
		this.largessRegDAO = largessRegDAO;
	}

	public LargessRegDAO getLargessRegDAO() {
		return largessRegDAO;
	}

	public void setLargessReg(LargessReg largessReg) {
		this.largessReg = largessReg;
	}

	public LargessReg getLargessReg() {
		return largessReg;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setLargessDefDAO(LargessDefDAO largessDefDAO) {
		this.largessDefDAO = largessDefDAO;
	}

	public LargessDefDAO getLargessDefDAO() {
		return largessDefDAO;
	}

	public void setLargessDef(LargessDef largessDef) {
		this.largessDef = largessDef;
	}

	public LargessDef getLargessDef() {
		return largessDef;
	}

	public void setLargessService(LargessService largessService) {
		this.largessService = largessService;
	}

	public LargessService getLargessService() {
		return largessService;
	}

	public void setLargessRegId(Long largessRegId) {
		this.largessRegId = largessRegId;
	}

	public Long getLargessRegId() {
		return largessRegId;
	}

	public List<LargessDef> getLargessDefList() {
		return largessDefList;
	}

	public void setLargessDefList(List<LargessDef> largessDefList) {
		this.largessDefList = largessDefList;
	}

	public List<BranchInfo> getCardBranchList() {
		return cardBranchList;
	}

	public void setCardBranchList(List<BranchInfo> cardBranchList) {
		this.cardBranchList = cardBranchList;
	}

	public List<MerchInfo> getMerchInfoList() {
		return merchInfoList;
	}

	public void setMerchInfoList(List<MerchInfo> merchInfoList) {
		this.merchInfoList = merchInfoList;
	}

}
