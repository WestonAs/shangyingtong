package gnete.card.service.impl;

import flink.file.CardDeferImporter;
import flink.util.CommonHelper;
import flink.util.DateUtil;
import gnete.card.dao.CardDeferBatRegDAO;
import gnete.card.dao.CardDeferRegDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.CardDeferBatReg;
import gnete.card.entity.CardDeferReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.IsFileFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.ExpirMthdType;
import gnete.card.entity.type.RoleType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.CardDeferRegService;
import gnete.card.tag.NameTag;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Symbol;
import gnete.etc.WorkflowConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Filename: CardDeferRegServiceImpl.java Description: Copyright: Copyright
 * (c)2010 Company: 深圳雁联计算系统有限公司
 * 
 * @author: aps-zsg
 * @version: V1.0 Create at: 2010-9-25 下午06:14:20
 */
@Service("CardDeferRegService")
public class CardDeferRegServiceImpl implements CardDeferRegService {

	@Autowired
	private CardDeferRegDAO cardDeferRegDAO;
	@Autowired
	private CardDeferBatRegDAO cardDeferBatRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
//	@Autowired
//	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private WorkflowService workflowService;

	public void addCardDefer(CardDeferReg cardDefer, UserInfo user) throws BizException {
		Assert.notNull(cardDefer, "添加的延期对象不能为空！");
		Assert.notNull(user, "登录用户信息不能为空");
		Assert.notEmpty(cardDefer.getNewExpirDate(), "延期日期不能为空");

		CardInfo cardInfo = (CardInfo)this.cardInfoDAO.findByPk(cardDefer.getCardId());
		
		Assert.notNull(cardInfo, "卡号[" + cardDefer.getCardId() + "]不存在");
		Assert.notTrue(cardInfo.getExtenLeft() == 0, "卡号[" + cardInfo.getCardId() + "]剩余延期次数为0, 不能延期。");

		Assert.notEmpty(cardInfo.getExpirDate(), "原失效日期为空！不能延期。");
//		Assert.notTrue(cardInfo.getExpirDate() == null, "原失效日期为空！不能延期。");

//		Assert.isTrue(cardDefer.getNewExpirDate().compareTo(
//				cardInfo.getExpirDate()) > 0, "添加的延期日期不能小于或等于原失效日期！");
		
		String roleType = user.getRole().getRoleType();
		
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			cardDefer.setBranchCode(user.getDeptId());
			cardDefer.setBranchName(NameTag.getDeptName(user.getDeptId()));
		} else {
			cardDefer.setBranchCode(user.getBranchNo());
			cardDefer.setBranchName(NameTag.getBranchName(user.getBranchNo()));
		}
		
		cardDefer.setAppOrgId(cardInfo.getAppOrgId());
		cardDefer.setCardBranch(cardInfo.getCardIssuer());

		cardDefer.setEffDate(cardInfo.getEffDate());
		cardDefer.setExpirDate(cardInfo.getExpirDate());
		cardDefer.setUpdateUser(user.getUserId());
		cardDefer.setUpdateTime(new Date());

		if (isNeedCheck(user)) {
			cardDefer.setStatus(RegisterState.WAITED.getValue());
			this.cardDeferRegDAO.insert(cardDefer);
			
			startCarddefRegFlow(cardDefer, user);
		} else {
			cardDefer.setStatus(RegisterState.WAITEDEAL.getValue());
			this.cardDeferRegDAO.insert(cardDefer);
			
			// 发报文到后台
			MsgSender.sendMsg(MsgType.CARD_DEFER, cardDefer.getCardDeferId(), user.getUserId());
		}
	}
	
	private void startCarddefRegFlow(CardDeferReg cardDefer, UserInfo user) throws BizException {
		try {
			this.workflowService.startFlow(cardDefer, 
					WorkflowConstants.CARD_DEFFER_ADAPTER, Long.toString(cardDefer.getCardDeferId()), user);
		} catch (Exception e) {
			throw new BizException("启动卡延期审核流程时，发生异常。");
		}
	}
	
	public void addCardDeferBat(CardDeferReg cardDefer, UserInfo user) throws BizException {
		Assert.notNull(cardDefer, "添加的延期对象不能为空！");
		Assert.notNull(user, "登录用户信息不能为空");
		String startCard = cardDefer.getStartCard();
		Assert.notEmpty(startCard, "起始卡号不能为空");
		Assert.notEmpty(cardDefer.getEndCard(), "结束卡号不能为空");
		Assert.notEmpty(cardDefer.getNewExpirDate(), "延期日期不能为空");
		
		CardInfo startCardInfo  = (CardInfo) this.cardInfoDAO.findByPk(startCard);
		Assert.notNull(startCardInfo, "起始卡号[" + startCard + "]不存在。");
		
		String roleType = user.getRole().getRoleType();
		
		if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			cardDefer.setBranchCode(user.getDeptId());
			cardDefer.setBranchName(NameTag.getDeptName(user.getDeptId()));
		} else {
			cardDefer.setBranchCode(user.getBranchNo());
			cardDefer.setBranchName(NameTag.getBranchName(user.getBranchNo()));
		}
		
		cardDefer.setAppOrgId(startCardInfo.getAppOrgId());
		cardDefer.setCardBranch(startCardInfo.getCardIssuer());
		
		//String endCard = cardDefer.getEndCard();
		
		// 遍历检验开始卡号和结束卡号之间的所有卡是否符合要求
		/*for(){
			CardInfo cardInfo = new CardInfo();
			cardInfo = this.cardDeferRegDAO.findCardInfo(cardDefer.getCardId());
			
			Assert.notTrue(cardInfo.getExtenLeft() == 0, "卡号["
					+ cardInfo.getCardId() + "]可延期次数为0！");
		}*/
		
		cardDefer.setUpdateUser(user.getUserId());
		cardDefer.setUpdateTime(new Date());
		if (isNeedCheck(user)) {
			cardDefer.setStatus(RegisterState.WAITED.getValue());
			this.cardDeferRegDAO.insert(cardDefer);
			
			startCarddefRegFlow(cardDefer, user);
		} else {
			cardDefer.setStatus(RegisterState.WAITEDEAL.getValue());
			this.cardDeferRegDAO.insert(cardDefer);
			
			// 发报文到后台
			MsgSender.sendMsg(MsgType.CARD_DEFER, cardDefer.getCardDeferId(), user.getUserId());
		}
		
	}

	public boolean delete(Long cardDeferId) throws BizException {
		Assert.notNull(cardDeferId, "删除的延期对象不能为空！");
		int count = this.cardDeferRegDAO.delete(cardDeferId);
		return count > 0;
	}

	public boolean modifyCardDefer(CardDeferReg cardDefer, UserInfo user) throws BizException {
		Assert.notNull(cardDefer, "更新的延期对象不能为空！");
		Assert.notNull(user, "登录用户对象不能为空");
		
		cardDefer.setUpdateUser(user.getUserId());
		cardDefer.setUpdateTime(new Date());
		cardDefer.setStatus(RegisterState.WAITEDEAL.getValue());
		
		return this.cardDeferRegDAO.update(cardDefer) > 0;
	}
	
	public List<String> addFileCardDeferReg(File upload, String uploadFileName, UserInfo user) throws BizException {
		
		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertCardDeferRegList中
		List<String> errorCardList = new ArrayList<String>();
		String roleType = user.getRole().getRoleType();
		
		try{
			CardDeferImporter cardDeferImporter = new CardDeferImporter();
			List<CardDeferReg> cardDeferRegList = cardDeferImporter.getFileImporterList(upload, uploadFileName);
			
			List<CardDeferBatReg> cardDeferBatRegList = new ArrayList<CardDeferBatReg>();
			for(CardDeferReg cardDeferReg : cardDeferRegList){
				String prex = "第[" + (cardDeferRegList.indexOf(cardDeferReg) + 1)+ "]条记录";
				
				// 卡号不能为空
				if (StringUtils.isEmpty(cardDeferReg.getCardId())) {
					errorCardList.add(prex + "卡号为空");
					continue;
				}
				
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardDeferReg.getCardId());
				
				// 检查卡号是否存在
				if (cardInfo == null) {
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]不存在");
					continue;
				}
					
				// 检查定义了卡失效方式的卡子类型是否存在
				CardSubClassDef cardSubClassDef = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardInfo.getCardSubclass());
				if(cardSubClassDef == null){
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]所属卡类型不存在");
					continue;
				}
				
				// 检查卡状态
				if(!checkCardState(cardInfo, cardSubClassDef)){
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]状态不正确，不能做延期");
					continue;
				}
				
				// 卡号剩余次数
				if(cardInfo.getExtenLeft() == 0){
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]剩余次数为0");
					continue;
				}
				
				// 卡信息表中原失效日期是否为空
				if(StringUtils.isEmpty(cardInfo.getExpirDate())){
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]原失效日期为空");
					continue;
				}
				
				// 延期日期格式是否正确
				if(!DateUtil.isValidDate(cardDeferReg.getNewExpirDate(), "yyyyMMdd")){
					errorCardList.add(prex + "卡号[" + cardDeferReg.getCardId() + "]延期日期格式不正确");
					continue;
				}
				
				//卡延期明细登记簿
				CardDeferBatReg tmpCardDeferBatReg = new CardDeferBatReg();
				tmpCardDeferBatReg.setCardId(cardInfo.getCardId());
				tmpCardDeferBatReg.setNewExpirDate(cardDeferReg.getNewExpirDate());
				tmpCardDeferBatReg.setRemark(cardDeferReg.getRemark());
				tmpCardDeferBatReg.setInsertTime(new Date());
				cardDeferBatRegList.add(tmpCardDeferBatReg);
			}
			
			Assert.isEmpty(errorCardList,  "以下记录存在错误，不进行卡延期操作:" + ObjectUtils.nullSafeToString(errorCardList.toArray()));//如有一张卡不符合规则，整批次拒绝提交。
			//入库处理
			boolean isNeedCheck = isNeedCheck(user);
			CardDeferReg tmpCardDeferReg = new CardDeferReg();
			
			if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
				tmpCardDeferReg.setBranchCode(user.getDeptId());
				tmpCardDeferReg.setBranchName(NameTag.getDeptName(user.getDeptId()));
			} else {
				tmpCardDeferReg.setBranchCode(user.getBranchNo());
				tmpCardDeferReg.setBranchName(NameTag.getBranchName(user.getBranchNo()));
			}
			tmpCardDeferReg.setCardNum(Long.valueOf(cardDeferBatRegList.size()));
			tmpCardDeferReg.setUpdateUser(user.getUserId());
			tmpCardDeferReg.setUpdateTime(new Date());
			tmpCardDeferReg.setDeferType(IsFileFlag.YES.getValue());//文件方式
			
			//插入到登记簿中
			if (isNeedCheck) {
				tmpCardDeferReg.setStatus(RegisterState.WAITED.getValue());
			} else {
				tmpCardDeferReg.setStatus(RegisterState.WAITEDEAL.getValue());
			}
			this.cardDeferRegDAO.insert(tmpCardDeferReg);
			
			//明细关联主表主键
			for(CardDeferBatReg cardDeferBatReg : cardDeferBatRegList){
				cardDeferBatReg.setCardDeferId(tmpCardDeferReg.getCardDeferId());
			}
			this.cardDeferBatRegDAO.insertBatch(cardDeferBatRegList);
			
			//数据入库后,才发起审核或发送命令
			if (isNeedCheck) {
				startCarddefRegFlow(tmpCardDeferReg, user);
			} else {// 发报文到后台
				MsgSender.sendMsg(MsgType.CARD_DEFER, tmpCardDeferReg.getCardDeferId(), user.getUserId());
			}
		}catch (Exception e) {
			throw new BizException(e);
		}
		
		return errorCardList;
	}

	public boolean checkCardState(CardInfo cardInfo, CardSubClassDef cardSubClassDef) {
		boolean flag = false;
		
		// 指定失效日期，到该日期失效，已制卡、已入库、已领卡待售、预售出、正常、已过期的卡能够延期
		if(ExpirMthdType.EXPIR_DATE.getValue().equals(cardSubClassDef.getExpirMthd())){
			List<String> list = new ArrayList<String>();
			
			list.add(CardState.MADED.getValue());
			list.add(CardState.STOCKED.getValue());
			list.add(CardState.FORSALE.getValue());
			list.add(CardState.PRESELLED.getValue());
			list.add(CardState.ACTIVE.getValue());
			list.add(CardState.EXCEEDED.getValue());
			
			flag = list.contains(cardInfo.getCardStatus());
			
//			flag = cardInfo.getCardStatus().equals(CardState.MADED.getValue())||
//			cardInfo.getCardStatus().equals(CardState.STOCKED.getValue())||
//			cardInfo.getCardStatus().equals(CardState.FORSALE.getValue())||
//			cardInfo.getCardStatus().equals(CardState.PRESELLED.getValue())||
//			cardInfo.getCardStatus().equals(CardState.ACTIVE.getValue())||
//			cardInfo.getCardStatus().equals(CardState.EXCEEDED.getValue());
//			flag = true;
		} 
		// 指定有效期（月数），从售卡日起，经过该有效期月数失效，正常、已过期的卡能够延期
		else if(ExpirMthdType.EXPIR_MONTH.getValue().equals(cardSubClassDef.getExpirMthd())){
			List<String> list = new ArrayList<String>();
			
			list.add(CardState.ACTIVE.getValue());
			list.add(CardState.EXCEEDED.getValue());
			
			flag = list.contains(cardInfo.getCardStatus());
			
//			flag = cardInfo.getCardStatus().equals(CardState.ACTIVE.getValue())||
//			cardInfo.getCardStatus().equals(CardState.EXCEEDED.getValue());
//			flag = true;
		}
		// 未指定卡的失效方式
		return flag;
	}
	
	/**
	 * 判断卡延期是否需要审核
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private boolean isNeedCheck(UserInfo user) throws BizException {
		boolean isCheckForSell = false;
		
		String roleType = user.getRole().getRoleType();

		// 发卡机构的话需要检查是否配置了审核权限
		if (RoleType.CARD.getValue().equals(roleType)) {
			SaleDepositCheckConfig config = (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(user.getBranchNo());
			Assert.notNull(config, "发卡机构[" + user.getBranchNo() + "]没有配置审核权限");
			
			isCheckForSell = StringUtils.equals(Symbol.YES, config.getCarddeferCheck());
		}
		
		return isCheckForSell;
	}

}
