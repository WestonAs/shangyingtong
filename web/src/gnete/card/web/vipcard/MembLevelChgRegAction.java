package gnete.card.web.vipcard;

import flink.util.AbstractType;
import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembLevelChgRegDAO;
import gnete.card.dao.MembRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembLevelChgReg;
import gnete.card.entity.MembReg;
import gnete.card.entity.state.MembLevelChgState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.VipCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.util.CardUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员级别变更
 * @Project: CardWeb
 * @File: MembLevelChgRegAction.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-25下午2:31:58
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class MembLevelChgRegAction extends BaseAction{

	@Autowired
	private MembLevelChgRegDAO membLevelChgRegDao;
	@Autowired
	private MembClassDefDAO membClassDefDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private AcctInfoDAO acctInfoDAO;
	
	@Autowired
	private VipCardService vipCardService;
	
	private MembLevelChgReg membLevelChgReg;
	
	/** 会员类型 */
	private List<MembClassDef> membClassDefList; 

	private Paginater page;
	
	@Override
	public String execute() throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (membLevelChgReg != null) {
			params.put("membClass", membLevelChgReg.getMembClass());
			params.put("cardId", membLevelChgReg.getCardId());
		}
		// 运营机构可以看到所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CENTER_DEPT.getValue().equals(this.getLoginRoleType())) {
		}
		// 发卡可以看到自己的和自己下级的记录
		else if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
			params.put("cardIssuer", this.getLoginBranchCode());
		} else {
			throw new BizException("没有权限查询会员级别变更资料");
		}
		membClassDefList = vipCardService.loadMtClass(this.getLoginBranchCode());
		this.page = this.membLevelChgRegDao.findPage(params, this.getPageNumber(), this.getPageSize());
		logger.debug("用户[" + this.getSessionUserCode() + "]查询会员级别变更资料列表");
		
		return LIST;
	}
	
	//取得会员级别变更资料的明细
	public String detail() throws Exception {
		Assert.notNull(membLevelChgReg, "会员级别变更资料不能为空!");
		Assert.notNull(membLevelChgReg.getId(), "会员级别变更资料ID不能为空!");
		// 运营机构可以看到所有
		if (RoleType.CENTER.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
		} else {
			throw new BizException("没有权限查询会员级别变更资料");
		}
		this.membLevelChgReg = (MembLevelChgReg) this.membLevelChgRegDao.findByPk(membLevelChgReg.getId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		initPage();
		return ADD;
	}
	
	/* 根据卡号，返回相应的json数据 */
    public void searchMembInfo(){
    	String cardId = request.getParameter("cardId");
    	JSONObject object = new JSONObject();
    	if (CommonHelper.isEmpty(cardId)) {return;}
    	CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardId);
    	if(cardInfo == null){
    		object.put("resultMessage", "卡号[" + cardId + "]不存在，请重新录入卡号！");
    		this.respond(object.toString());
    		return;
    	}
    	
    	String resultAcctId = cardInfo.getAcctId(); 
    	if (StringUtils.isEmpty(resultAcctId)) {
    		object.put("resultMessage", "卡号[" + cardId + "]的账户不存在，请重新录入卡号！");
    		this.respond(object.toString());
    		return;
		}
    	AcctInfo acctInfo = (AcctInfo)acctInfoDAO.findByPk(cardInfo.getAcctId());
    	if(acctInfo == null){
    		object.put("resultMessage", "卡号[" + cardId + "]账户信息不存在，请重新录入卡号！");
    		this.respond(object.toString());
    		return;
    	}else{
    		if(StringUtils.isBlank(acctInfo.getMembClass())){
    			object.put("resultMessage", "卡号[" + cardId + "]没有会员类型, 请重新录入卡号！");
    			this.respond(object.toString());
    			return;
    		}else{
    			object.put("membClass", StringUtils.isBlank(acctInfo.getMembClass()) ? StringUtils.EMPTY : acctInfo.getMembClass());
    			object.put("membLevel", StringUtils.isBlank(acctInfo.getMembLevel()) ? StringUtils.EMPTY : acctInfo.getMembLevel());
    			this.respond(object.toString());
    			return;
    		}
    	}
    }
	
	// 新增会员级别变更
	public String add() throws Exception {
		Assert.notNull(membLevelChgReg, "会员级别变更资料不能为空!");
		Assert.notEmpty(membLevelChgReg.getCardId(), "会员级别变更资料卡ID不能为空!");
		
		CardInfo cardInfo = (CardInfo)cardInfoDAO.findByPk(membLevelChgReg.getCardId());
		Assert.notNull(cardInfo, "卡号["+membLevelChgReg.getCardId()+"]信息不存在!");
		Assert.equals(cardInfo.getCardIssuer(), getSessionUser().getBranchNo(), "卡号[" + cardInfo.getCardId() + "]不是您所在的发卡机构的卡，不能级别变更！");
		
		membLevelChgReg.setProcStatus(MembLevelChgState.WAITEDEAL.getValue());//待处理
		membLevelChgReg.setUpdateBy(getSessionUser().getUserId());
		membLevelChgReg.setUpdateTime(new Date());
		membLevelChgReg.setCardIssuer(getSessionUser().getBranchNo());
		//保存数据
		this.membLevelChgRegDao.insert(membLevelChgReg);
		
		//发送处理命令
		MsgSender.sendMsg(MsgType.MEMB_LEVLE, membLevelChgReg.getId(), getSessionUser().getUserId());
		
		String msg = "添加会员级别变更[" + this.membLevelChgReg.getId() + "]成功！";
		this.addActionMessage("/vipCard/membLevelChgReg/list.do?goBack=goBack", msg);
		this.log(msg, UserLogType.ADD);
		return SUCCESS;
	}
	
	// 删除会员级别变更记录
	public String delete() throws Exception {
		 initPage();
		Assert.notNull(membLevelChgReg, "会员级别变更资料不能为空!");
		Assert.notNull(membLevelChgReg.getId(), "会员级别变更资料ID不能为空!");
		this.membLevelChgRegDao.delete(membLevelChgReg);
		
		String msg = "删除会员级别变更[" +this.membLevelChgReg.getId()+ "]成功！";
		this.log(msg, UserLogType.DELETE);
		this.addActionMessage("/vipCard/membLevelChgReg/list.do?goBack=goBack", msg);
		
		return SUCCESS;
	}
	
	private void initPage() throws Exception {
		if (RoleType.CARD.getValue().equals(this.getLoginRoleType())
				|| RoleType.CARD_DEPT.getValue().equals(this.getLoginRoleType())) {
		} else {
			throw new BizException("没有权限维护会员级别变更资料");
		}
	}
	
	/**
	 * 根据会员类型ID得到会员级别列表。（返回到页面为String字符串）
	 * @throws Exception
	 
	public List loadMtClassName(String membClass) throws Exception {
		Assert.notEmpty(membClass, "会员类型不能为空!");
		List orderedList = new ArrayList();
		MembClassDef membClassDef = (MembClassDef)this.membClassDefDAO.findByPk(membClass);
		Assert.notNull(membClassDef, "会员类型信息不能为空!");
		String membClassName = membClassDef.getMembClassName();
		String[] membClassNames = membClassName.split("\\,");
		int i=1;//会员级别
		for (String membClassNameItem : membClassNames){
			orderedList.add(new AbstractType(membClassNameItem,String.valueOf(i++)){});
		}
		return orderedList;
	}*/
	
	public List<MembClassDef> getMembClassDefList() {
		return membClassDefList;
	}

	public void setMembClassDefList(List<MembClassDef> membClassDefList) {
		this.membClassDefList = membClassDefList;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Paginater getPage() {
		return page;
	}

	public void setMembLevelChgReg(MembLevelChgReg membLevelChgReg) {
		this.membLevelChgReg = membLevelChgReg;
	}

	public MembLevelChgReg getMembLevelChgReg() {
		return membLevelChgReg;
	}
}
