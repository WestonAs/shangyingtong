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
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.LargessDef;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.LargessService;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

public class LargessDefAction extends BaseAction{

	@Autowired
	private LargessDefDAO largessDefDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private LargessService largessService;
	
	private LargessDef largessDef;
	private Paginater page;
	private Long largessId;
	private List<BranchInfo> cardBranchList;
	private List<MerchInfo> merchInfoList;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (largessDef != null) {
			params.put("largessId", largessDef.getLargessId());
			params.put("largessName", MatchMode.ANYWHERE.toMatchString(largessDef.getLargessName()));
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
		}
		// 如果登录用户为发卡机构或发卡机构部门时
		else if (RoleType.CARD.getValue().equals(getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(getLoginRoleType())) {
			cardBranchList.add((BranchInfo) this.branchInfoDAO.findByPk(getSessionUser().getBranchNo()));
			merchInfoList.addAll(this.getMyFranchMerch(this.getSessionUser().getBranchNo()));
		}
		else if(RoleType.MERCHANT.getValue().equals(getLoginRoleType())){
			merchInfoList.add((MerchInfo)this.merchInfoDAO.findByPk(this.getSessionUser().getMerchantNo()));
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
		
		this.page = this.largessDefDAO.findLargessDef(params, this.getPageNumber(), this.getPageSize());	
		
		return LIST;
	}
	
	//取得赠品定义的明细
	public String detail() throws Exception {
		
		this.largessDef = (LargessDef) this.largessDefDAO.findByPk(this.largessDef.getLargessId());
		
		this.log("查询赠品定义["+this.largessDef.getLargessId()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
		
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
	//	initPage();
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&
				!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机或者商户不能操作。");
		}
		return ADD;
	}	
	
	// 新增赠品定义
	public String add() throws Exception {			
		RoleInfo roleInfo = this.getSessionUser().getRole();
		if(RoleType.CARD.getValue().equals(this.getLoginRoleType())){
			this.largessDef.setJinstType(IssType.CARD.getValue());
			this.largessDef.setJinstId(roleInfo.getBranchNo());
			this.largessDef.setBranchCode(roleInfo.getBranchNo());
		}
		else if(RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){
			this.largessDef.setJinstType(IssType.MERCHANT.getValue());
			this.largessDef.setJinstId(roleInfo.getMerchantNo());
			this.largessDef.setBranchCode(roleInfo.getMerchantNo());
		}
		this.largessService.addLargessDef(largessDef, this.getSessionUserCode());
		String msg = "赠品定义["+this.largessDef.getLargessId()+"]成功！";
		this.addActionMessage("/largessDef/list.do", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 打开修改页面的初始化操作
	public String showModify() throws Exception {
		//initPage();
		if(!RoleType.CARD.getValue().equals(this.getLoginRoleType())&
				!RoleType.MERCHANT.getValue().equals(this.getLoginRoleType())){ 
			throw new BizException("非发卡机或者商户不能操作。");
		}
		this.largessDef = (LargessDef)this.largessDefDAO.findByPk(this.largessDef.getLargessId());
		return MODIFY;
	}
	
	// 修改赠品定义信息
	public String modify() throws Exception {
		
		this.largessService.modifyLargessDef(this.largessDef, this.getSessionUserCode());
		
		this.addActionMessage("/largessDef/list.do", "修改赠品定义成功！");	
		return SUCCESS;
	}
	
	// 删除赠品定义
	public String delete() throws Exception {
		
		this.largessService.deleteLargessDef(this.getLargessId());
		
		String msg = "删除赠品定义[" +this.getLargessId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/largessDef/list.do", msg);
		return SUCCESS;
	}

	public void setLargessService(LargessService largessService) {
		this.largessService = largessService;
	}

	public LargessService getLargessService() {
		return largessService;
	}
	
	public LargessDefDAO getLargessDefDAO() {
		return largessDefDAO;
	}

	public void setLargessDefDAO(LargessDefDAO largessDefDAO) {
		this.largessDefDAO = largessDefDAO;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setLargessId(Long largessId) {
		this.largessId = largessId;
	}

	public Long getLargessId() {
		return largessId;
	}

	public LargessDef getLargessDef() {
		return largessDef;
	}

	public void setLargessDef(LargessDef largessDef) {
		this.largessDef = largessDef;
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
