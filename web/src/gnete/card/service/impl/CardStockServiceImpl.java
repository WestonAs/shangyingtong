package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardInputDAO;
import gnete.card.dao.CardPrevConfigDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.MakeCardAppDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.dao.SampleCheckDAO;
import gnete.card.dao.WhiteCardInputDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardInput;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MakeCardApp;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.SampleCheck;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WhiteCardInput;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.MakeCardAppState;
import gnete.card.service.CardStockService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.util.CardUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardStockService")
public class CardStockServiceImpl implements CardStockService {

	@Autowired
	private CardInputDAO cardInputDAO;
	@Autowired
	private WhiteCardInputDAO whiteCardInputDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private MakeCardRegDAO makeCardRegDAO;
	@Autowired
	private SampleCheckDAO sampleCheckDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private MakeCardAppDAO makeCardAppDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private CardPrevConfigDAO cardPrevConfigDAO;

	/**
	 * 卡号长度为19位
	 */
	private static final int CARD_NO_LENGTH = 19;

	public CardInput addCardInput(CardInput cardInput, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(cardInput, "要新增的成品卡入库对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的对象不能为空。");
		
		// 判断卡号是否已经完成制卡
//		String cardNoPrefix = cardInput.getStrNo().substring(0, 11);
//		String strNoSeq = cardInput.getStrNo().substring(11, 18);
//		Long seq = NumberUtils.toLong(strNoSeq);
//		for (int i = 0; i < cardInput.getInputNum().longValue(); i++) {
//			Long cardNoSeq = seq + i;
//			String cardNoStr = StringUtils.leftPad(Long.toString(cardNoSeq), 7, "0");
//			String nextCardNo = cardNoPrefix + cardNoStr;
//			nextCardNo = nextCardNo + CardUtil.luhnMod10(nextCardNo);
//			CardInfo info = (CardInfo) cardInfoDAO.findByPk(nextCardNo);
//			Assert.notNull(info, "找不到卡[" + nextCardNo + "]的信息");
//
//			MakeCardApp app = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(info.getBatchNo()));
//			Assert.isTrue(StringUtils.equals(app.getStatus(),
//					MakeCardAppState.ALREADY_LOAD.getValue()), "该卡所属批次的制卡还未完成");
//		}
		
		String endCardId = CardUtil.getEndCard(cardInput.getStrNo(), cardInput.getInputNum().intValue());
		CardInfo endCardInfo = (CardInfo) cardInfoDAO.findByPk(endCardId);
		Assert.notNull(endCardInfo, "卡信息表中没有结束卡号[" + endCardId + "]的信息");
		
		MakeCardApp app = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(endCardInfo.getBatchNo()));
		Assert.isTrue(StringUtils.equals(app.getStatus(), MakeCardAppState.ALREADY_LOAD.getValue()), 
				"该卡所属批次[" + endCardInfo.getBatchNo() + "]的制卡还未完成");

		cardInput.setBranchCode(sessionUser.getBranchNo());
		cardInput.setInputDate(DateUtil.formatDate("yyyyMMdd"));
		cardInput.setStatus(CheckState.WAITED.getValue());
		cardInput.setUpdateBy(sessionUser.getUserId());
		cardInput.setUpdateTime(new Date());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("strCardNo", cardInput.getStrNo());
		params.put("endCardNo", cardInput.getEndNo());
		params.put("waited_status", CheckState.WAITED.getValue());
		params.put("passed_status", CheckState.PASSED.getValue());
		boolean isExist = cardInputDAO.isExistCardInput(params) > 0;
		Assert.notTrue(isExist, "要入库的这批卡中有卡号已经入库或正等待审批。");

		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("strNo", cardInput.getStrNo());
		pMap.put("endNo", cardInput.getEndNo());
		boolean isInStock = cardStockInfoDAO.isInStock(pMap);
		Assert.notTrue(isInStock, "要入库的这批卡中有卡号已经入过库。");

		cardInputDAO.insert(cardInput);
		
		// 启动单个流程
		try {
			workflowService.startFlow(cardInput, "finishedCardStockAdapter", Long.toString(cardInput.getId()), sessionUser);
		} catch (Exception e) {
			throw new BizException("启动成品卡入库流程时出错，原因：" + e.getMessage());
		}
		return cardInput;
	}

	public String getEndNo(CardInput cardInput) throws BizException {
		Assert.notEmpty(cardInput.getStrNo(), "传入的起始卡号不能为空。");
		Assert.notNull(cardInput.getInputNum(), "传入的入库卡数量不能为空");
		Assert.notEmpty(cardInput.getInputNum().toString(), "传入的入库卡数量不能为空");
		Assert.notTrue(cardInput.getStrNo().length() != CARD_NO_LENGTH, "传入的起始卡号长度不对。");
		String strCardNo = cardInput.getStrNo().substring(11, CARD_NO_LENGTH - 1);
		String prixStr = cardInput.getStrNo().substring(0, 11);
//		BigDecimal result = cardInput.getInputNum().add(new BigDecimal(strCardNo)).subtract(new BigDecimal(1L));
		BigDecimal result = AmountUtil.subtract(AmountUtil.add(NumberUtils.createBigDecimal(strCardNo), 
				cardInput.getInputNum()), NumberUtils.createBigDecimal("1"));
		
		String cardpre = prixStr + StringUtils.leftPad(String.valueOf(result.longValue()), 7, "0");
		return cardpre + CardUtil.luhnMod10(cardpre);
	}

	public boolean isCorrectStrNo(CardInput cardInput, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(cardInput.getCardType(), "传入的卡类型不能为空。");
		Assert.notEmpty(cardInput.getStrNo(), "传入的起始卡号不能为空。");
		Assert.notTrue(cardInput.getStrNo().length() != CARD_NO_LENGTH, "卡号长度不对。");
		String type = cardInput.getStrNo().substring(9, 11);
		String sysNo = cardInput.getStrNo().substring(0, 3);
//		Assert.isTrue(StringUtils.equals(sysNo, ParaMgr.getInstance().getSysNo()), "卡号与系统号不符");
//		Assert.isTrue(StringUtils.equals(type, cardInput.getCardType()), "卡号与卡类型不符");
		Assert.equals(sysNo, getSysNo(sessionUser.getBranchNo()), "卡号与系统号不符");
		Assert.equals(type, cardInput.getCardType(), "卡号与卡类型不符");

		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardInput.getStrNo());
		Assert.notNull(cardInfo, "卡号[" + cardInput.getStrNo() + "]不存在");
		String batchNo = cardInfo.getBatchNo();
		Assert.notEmpty(batchNo, "该卡的批次号为空");

		MakeCardApp app = (MakeCardApp) makeCardAppDAO.findByPk(NumberUtils.toLong(batchNo));
//		Assert.isTrue(StringUtils.equals(app.getStatus(), MakeCardAppState.ALREADY_LOAD.getValue()), "该卡所属批次的制卡还未完成");
		Assert.equals(app.getStatus(), MakeCardAppState.ALREADY_LOAD.getValue(), "该卡所属批次的制卡还未完成");
		// Assert.isTrue(StringUtils.equals(cardInfo.getCardStatus(),
		// CardState.MADED.getValue()), "该卡状态不是已制卡，不能入库");
//		Assert.isTrue(StringUtils.equals(sessionUser.getBranchNo(), cardInfo.getCardIssuer()), "此卡不属于当前发卡机构。");
		Assert.equals(cardInfo.getCardIssuer(), sessionUser.getBranchNo(), "此卡不属于当前发卡机构。");
		return true;
	}
	
	/**
	 * 系统号
	 */
	private String getSysNo(String branchCode) {
		String sysNo = "";
		
		CardPrevConfig config = (CardPrevConfig) this.cardPrevConfigDAO.findByPk(branchCode);
		if (config != null) {
			sysNo = config.getCardPrev();
		} else {
			sysNo = SysparamCache.getInstance().getSysNo();
		}
		return sysNo;
	} 

	public WhiteCardInput addWhiteCardInput(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(whiteCardInput, "要新增的白卡入库登记对象不能为空。");
		Assert.notNull(sessionUser, "登录的用户对象不能为空。");

		whiteCardInput.setBranchCode(sessionUser.getBranchNo());
		whiteCardInput.setStatus(CheckState.WAITED.getValue());
		whiteCardInput.setUpdateBy(sessionUser.getUserId());
		whiteCardInput.setUpdateTime(new Date());

		whiteCardInputDAO.insert(whiteCardInput);
		return whiteCardInput;
	}

	public List<CardSubClassDef> getSubTypeList(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException {
		List<CardSubClassDef> subList = null;
		if (StringUtils.isNotBlank(whiteCardInput.getCardType())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardIssuer", sessionUser.getBranchNo()); // 机构号
			params.put("cardClass", whiteCardInput.getCardType());
			params.put("mustExpirDate", DateUtil.formatDate("yyyyMMdd"));

			subList = cardSubClassDefDAO.findCardSubClass(params);
		}
		return subList;
	}

	public List<MakeCardReg> getMakeIdList(WhiteCardInput whiteCardInput,
			UserInfo sessionUser) throws BizException {
		List<MakeCardReg> makeIdList = null;
		if (StringUtils.isNotBlank(whiteCardInput.getCardSubtype())) {
			makeIdList = makeCardRegDAO.findByCardSubtype(whiteCardInput.getCardSubtype());
		}
		return makeIdList;
	}

	public SampleCheck addSampleCheck(SampleCheck sampleCheck,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(sampleCheck, "要新增的制卡抽检对象不能为空。");
		Assert.notNull(sessionUser, "登录的用户信息对象不能为空。");
		// TODO 等待完善
		sampleCheckDAO.insert(sampleCheck);
		return sampleCheck;
	}

	public boolean delteSampleCheck(SampleCheck sampleCheck,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(sampleCheck, "要删除的制卡抽检对象不能为空。");
		Assert.notNull(sessionUser, "登录的用户信息对象不能为空。");
		Assert.notEmpty(Long.toString(sampleCheck.getId()), "传入的制卡抽检ID不能为空。");
		// TODO 待完善

		return sampleCheckDAO.delete(sampleCheck.getId()) > 0;
	}
	
	public void updateCardStockState(String strNo, int cardNum, String status)
			throws BizException {
		Assert.notEmpty(strNo, "起始卡号不能为空");
		Assert.notTrue(strNo.length() != 19, "卡号必须为19位");

		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("cardStatus", status);
		params.put("strCardId", strNo);
		params.put("endCardId", CardUtil.getMaxEndCardId(strNo, cardNum));
		
		this.cardStockInfoDAO.updateStockBatch(params);
	}
}
