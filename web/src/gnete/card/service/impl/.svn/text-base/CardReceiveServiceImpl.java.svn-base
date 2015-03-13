package gnete.card.service.impl;

import flink.etc.MatchMode;
import flink.exception.ExcelExportException;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AppRegDAO;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardBinDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardStockInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.AppReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardBin;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardStockInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.ReceiveFlag;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.state.CardStockState;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.CardReceiveService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.tag.NameTag;
import gnete.card.util.CardUtil;
import gnete.card.util.ExcelFileExport;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardReceiveService")
public class CardReceiveServiceImpl implements CardReceiveService {

	@Autowired
	private AppRegDAO appRegDAO;
	@Autowired
	private CardBinDAO cardBinDAO;
	@Autowired
	private CardStockInfoDAO cardStockInfoDAO;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	@Autowired
	private DepartmentInfoDAO departmentInfoDAO;
	@Autowired
	private MerchInfoDAO merchInfoDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	
	static Logger logger = LoggerFactory.getLogger(CardReceiveServiceImpl.class);
	
	/**
	 * 卡号长度为19位
	 */
	private static final int CARD_NO_LENGTH = 19;
	
	/**
	 * 生成的Excel文件的最大条数
	 */
	private static final int MAX_ROW_COUNT = 65000;

	public AppReg addCardReceive(AppReg appReg, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(appReg, "领卡登记对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的信息不能为空。");
		Assert.isTrue(appReg.getCardNum() > 0, "领卡数量必须大于0");
		Assert.notEmpty(Integer.toString(appReg.getCardNum()), "领卡的数量不能为空。");
		Assert.notEmpty(appReg.getCardSubClass(), "卡类型不能为空");
		Assert.notEmpty(appReg.getStrNo(), "起始卡号不能为空");
		Assert.notEmpty(appReg.getAppOrgId(), "卡的领入机构号不能为空");

		CardInfo strCardInfo = (CardInfo) this.cardInfoDAO.findByPk(appReg.getStrNo());
		Assert.notNull(strCardInfo, "卡号[" + appReg.getStrNo() + "]不存在");

		CardBin cardBin = (CardBin) cardBinDAO.findByPk(strCardInfo.getCardBin());
		Assert.notNull(cardBin, "卡号[" + appReg.getStrNo() + "]所属卡BIN不存在。");
		
		String roleType = sessionUser.getRole().getRoleType();
		String workDate = SysparamCache.getInstance().getWorkDateNotFromCache(); // 取得系统工作日
		appReg.setAppDate(workDate);
//		appReg.setCardIssuer(cardBin.getCardIssuer());// 发卡机构
		appReg.setFlag(ReceiveFlag.RECEIVE.getValue());
		appReg.setStatus(CheckState.WAITED.getValue());
		appReg.setUpdateBy(sessionUser.getUserId());
		appReg.setUpdateTime(new Date());
		appReg.setEndCardId(CardUtil.getEndCard(appReg.getStrNo(), appReg.getCardNum()));
		
		if (RoleType.CARD.getValue().equals(roleType)) {
			if (StringUtils.equals(appReg.getAppOrgId(), sessionUser.getBranchNo())) { // 自己领卡（领入机构是登录机构）
				
				if (StringUtils.equals(strCardInfo.getCardIssuer(), appReg.getCardSectorId())) { // 从卡的发卡机构领出

					if (StringUtils.equals(strCardInfo.getCardIssuer(), appReg.getAppOrgId())) { 
						// 领入到卡的发卡机构，只能领“在库”的卡 。
						appReg.setCardStockStatus(CardStockState.IN_STOCK.getValue());
					} else {
						// 领入到其他发卡机构（非卡的发卡机构），要选择库存状态为“在库”还是“已领卡”的卡
						Assert.notEmpty(appReg.getCardStockStatus(), "卡库存状态不能为空");
					}
				} else {
					appReg.setCardStockStatus(CardStockState.RECEIVED.getValue());
				}
			} else { // 替别人领卡（领出机构是自身）

				if (StringUtils.equals(strCardInfo.getCardIssuer(), appReg.getCardSectorId())) {
					// 从 卡的发卡机构 领出，要选择卡库存状态
					Assert.notEmpty(appReg.getCardStockStatus(), "卡库存状态不能为空");
				} else {
					//从 非卡的发卡机构 领出
					appReg.setCardStockStatus(CardStockState.RECEIVED.getValue());
				}
			}
		} else if (RoleType.MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			appReg.setCardStockStatus(CardStockState.IN_STOCK.getValue());
			
		} else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			if (StringUtils.equals(strCardInfo.getCardIssuer(), appReg.getCardSectorId())) {
				// 从 卡的发卡机构 领出时，只能领“卡在库”状态的卡
				appReg.setCardStockStatus(CardStockState.IN_STOCK.getValue());
			} else {
				appReg.setCardStockStatus(CardStockState.RECEIVED.getValue());
			}
		}

		// 更新卡库存信息表
		List<CardStockInfo> stockList = this.getCardNoList(appReg.getStrNo(), appReg.getCardNum().intValue());
		for (CardStockInfo info : stockList) {
	
			String errorMsg = "卡号[" + info.getCardId() + "]的状态为[" + info.getCardStatusName() + "]，不能领取，请查询卡库存信息。";

			if (RoleType.CARD.getValue().equals(roleType)) {
				//领的是在库的卡时
				if (StringUtils.equals(appReg.getCardStockStatus(), CardStockState.IN_STOCK.getValue())) {
					Assert.equals(info.getCardStatus(), CardStockState.IN_STOCK.getValue(), errorMsg);
				} else {
					String msg = "卡号[" + info.getCardId() + "]不在机构["+ appReg.getCardSectorId() +"]，不能从该机构处领卡";
					Assert.equals(info.getAppOrgId(), appReg.getCardSectorId(), msg);
				}
			}
			// 2. 商户领卡时
			else if (RoleType.MERCHANT.getValue().equals(roleType)) {
				Assert.equals(info.getCardStatus(), CardStockState.IN_STOCK.getValue(), errorMsg);
			}
			// 3. 售卡代理领卡时（1.从发卡机构领卡，2.从售卡代理领卡）
			else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
				// 从卡的发卡机构领出时， 只能领“卡在库”状态的卡
				if (StringUtils.equals(strCardInfo.getCardIssuer(), appReg.getCardSectorId())) {
					Assert.equals(info.getCardStatus(), CardStockState.IN_STOCK.getValue(), errorMsg);
				} else {
					// 从非卡的发卡机构领出时，只能领“已领出”的卡
					Assert.equals(info.getCardStatus(), CardStockState.RECEIVED.getValue(), errorMsg);
					String msg = "卡号[" + info.getCardId() + "]不在机构["+ appReg.getCardSectorId() +"]，不能从该机构处领卡";
					Assert.notTrue(StringUtils.equals(info.getAppOrgId(), appReg.getCardSectorId()), msg);
				}
			}
		}
		
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("cardStatus", CardStockState.ON_THE_WAY.getValue());
		params1.put("appOrgId", appReg.getAppOrgId());
		params1.put("appDate", workDate);
		params1.put("cardSectorId", appReg.getCardSectorId());
		params1.put("strCardId", appReg.getStrNo());
		params1.put("endCardId", CardUtil.getMaxEndCardId(appReg.getStrNo(), appReg.getCardNum()));
		params1.put("cardStatusLimit", appReg.getCardStockStatus()); // 卡状态限制，防止并发领卡
		
		int cnt = cardStockInfoDAO.updateStockBatch(params1); // 更新卡库存
		Assert.isTrue(cnt == appReg.getCardNum(), "领卡失败！请检查卡的库存的状态……");
		
		// 在领卡登记簿中插入记录
		appRegDAO.insert(appReg);
		// 启动单个流程
		try {
			workflowService.startFlow(appReg, "cardReceiveAdapter", Long.toString(appReg.getId()), sessionUser);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}

		return appReg ;
	}
	
	/**
	 * 根据起始卡号，卡数量生成卡号数组
	 * @param strCardId
	 * @param cardNum
	 * @return
	 * @throws BizException
	 */
	private List<CardStockInfo> getCardNoList(String strCardId, int cardNum) throws BizException {
		if (StringUtils.isEmpty(strCardId)) {
			return Collections.<CardStockInfo> emptyList();
		}
		
		List<CardStockInfo> list = new ArrayList<CardStockInfo>();
		
		if (strCardId.length() == 19) {// 新卡是19位
			String[] cardNoArray = CardUtil.getCard(strCardId, cardNum);
			for (int i = 0; i < cardNoArray.length; i++) {
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardNoArray[i]);
				Assert.notNull(cardInfo, "卡信息表没有第[" + (i + 1) + "]张卡[" + cardNoArray[i] + "]的记录");
				
				CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardNoArray[i]);
				Assert.notNull(info, "卡库存中没有第[" + (i + 1) + "]张[" + cardNoArray[i] + "]的记录。");
				list.add(info);
			}
		} else if (strCardId.length() == 18) {// 旧卡是18位
			String[] cardNoArray = CardUtil.getOldCard(strCardId, cardNum);
			for (int i = 0; i < cardNoArray.length; i++) {
				String cardId = cardNoArray[i];
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				
				//旧卡的另外一种校验规则
				if (cardInfo == null) {
					String cardPrex = cardNoArray[i].substring(0, cardNoArray[i].length() - 1);
					cardId = cardPrex + CardUtil.getOldCardLast(cardPrex);
					
					cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				}
				Assert.notNull(cardInfo, "卡信息表没有第[" + (i + 1) + "]张卡[" + cardId + "]的记录");
				
				CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardId);
				Assert.notNull(info, "卡库存中没有第[" + (i + 1) + "]张[" + cardId + "]的记录。");
				
				list.add(info);
			}
		} else {
			throw new BizException("卡号的长度有误");
		}
		return list;
	}
	
	@Deprecated
	public String getStrNo(String cardSubclass, String branchCode, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubclass);
		Assert.notNull(subClass, "选中的卡类型已经不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", cardSubclass);
		// 发卡机构领卡时
		if (RoleType.CARD.getValue().equals(roleType)) {
			// 领卡的发卡机构与领出的发卡机构一致时（发卡机构把自己的卡领出时），只能领取“卡在库”的卡
			if (StringUtils.equals(branchCode, user.getBranchNo())) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 从发卡机构的上级领卡，或发卡机构同级调拨时
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", branchCode);
			}
		} 
		// 商户领卡时
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
		}
		// 售卡代理领卡时
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			// 售卡代理领卡时。从发卡机构领卡时.只能领取“卡在库”的卡
			if (StringUtils.equals(subClass.getCardIssuer(), branchCode)) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 从售卡代理领卡时
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", branchCode);
			}
		} else {
			throw new BizException("没有权限领卡");
		}
		
		return cardStockInfoDAO.getStrNo(params);
	}
	
	@Deprecated
	public String getStrNo(String cardSubclass, String branchCode,
			String appOrgId, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		// 根据卡类型号可得到要领的卡的发卡机构号
		CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubclass);
		Assert.notNull(subClass, "选中的卡类型已经不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", cardSubclass);
		// 发卡机构登录时，可自己提领卡申请或帮别人领卡。其他情况下都是自己提领卡申请
		if (RoleType.CARD.getValue().equals(roleType)) {
			// 领卡机构号与登录机构号一致时，发卡机构自己做领卡操作，可领自己的卡和上级的卡
			if (StringUtils.equals(appOrgId, user.getBranchNo())) {
				// 领卡的发卡机构与领出的发卡机构一致时（发卡机构把自己的卡领出时），只能领取“卡在库”的卡
				if (StringUtils.equals(branchCode, user.getBranchNo())) {
					params.put("cardStatus", CardStockState.IN_STOCK.getValue());
				} else {
					// 从发卡机构的上级领卡，或发卡机构同级调拨时。只能领取“已领卡”的卡
					params.put("cardStatus", CardStockState.RECEIVED.getValue());
					params.put("appOrgId", branchCode);
				}
			}
			// 否则，则是发卡机构帮别人领卡，只能领自己发的卡。只能领取“卡在库”的卡
			else {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			}
		} 
		// 商户领卡时或发卡机构部门领卡时，只能领在库的卡
		else if (RoleType.MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
		}
		// 售卡代理领卡时
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			// 售卡代理领卡时。从发卡机构领卡时.只能领取“卡在库”的卡
			if (StringUtils.equals(subClass.getCardIssuer(), branchCode)) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 从售卡代理领卡时
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", branchCode);
			}
		} else {
			throw new BizException("没有权限领卡");
		}
		
		return cardStockInfoDAO.getStrNo(params);
	}
	
	public String getStrNo(String cardSubclass, String branchCode,
			String appOrgId, String stockStatus, UserInfo user)
			throws BizException {
		String roleType = user.getRole().getRoleType();
		
		// 根据卡类型号可得到要领的卡的发卡机构号
		CardSubClassDef subClass = (CardSubClassDef) this.cardSubClassDefDAO.findByPk(cardSubclass);
		Assert.notNull(subClass, "选中的卡类型已经不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", cardSubclass);
		
		// 发卡机构登录时，可自己提领卡申请或帮别人领卡。其他情况下都是自己提领卡申请
		if (RoleType.CARD.getValue().equals(roleType)) {
			//  领入机构号与登录机构号一致的时候是自己领卡，不一致时是替别人领卡
			if (StringUtils.equals(appOrgId, user.getBranchNo())) {
				// 对于自己领卡。卡的发卡机构，领出机构
				if (StringUtils.equals(subClass.getCardIssuer(), branchCode)) {
					// 领入机构也相同时，只能领“在库”的卡 。
					if (StringUtils.equals(subClass.getCardIssuer(), appOrgId)) {
						params.put("cardStatus", CardStockState.IN_STOCK.getValue());
					}
					// 领入机构不相同时，要选择库存状态为“在库”还是“已领卡”的卡 。
					else {
						Assert.notEmpty(stockStatus, "卡库存状态不能为空");
						params.put("cardStatus", stockStatus);
						if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
							params.put("appOrgId", branchCode);
						}
					}
				} else {
					params.put("cardStatus", CardStockState.RECEIVED.getValue());
					params.put("appOrgId", branchCode);
				}
			}
			// 对于替别人领卡的情况
			else {
				// 发卡机构为自己时，要选择卡库存状态
				if (StringUtils.equals(subClass.getCardIssuer(), user.getBranchNo())) {
					Assert.notEmpty(stockStatus, "卡库存状态不能为空");
					params.put("cardStatus", stockStatus);
					if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
						params.put("appOrgId", branchCode);
					}
				} else {
					params.put("cardStatus", CardStockState.RECEIVED.getValue());
					params.put("appOrgId", branchCode);
				}
			}
		}
		// 商户领卡时或发卡机构部门领卡时，只能领在库的卡
		else if (RoleType.MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
		}
		// 售卡代理领卡时
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			// 售卡代理领卡时。从发卡机构领卡时.只能领取“卡在库”的卡
			if (StringUtils.equals(subClass.getCardIssuer(), branchCode)) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 从售卡代理领卡时
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", branchCode);
			}
		} else {
			throw new BizException("没有权限领卡");
		}
		
		return cardStockInfoDAO.getStrNo(params);
	}
	
	@Deprecated
	public long[] canReaciveCardNum(String cardSubClass,
			String cardSectorId, String strNo, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		String defaultStrCardId = this.getStrNo(cardSubClass, cardSectorId, user);
		CardInfo strCardInfo = (CardInfo) this.cardInfoDAO.findByPk(strNo);
		Assert.notNull(strCardInfo, "卡号[" + strNo + "]不存在");
		// 输入的起始卡号所属的卡子类型必须与选中的卡类型一致
		Assert.equals(cardSubClass, strCardInfo.getCardSubclass(), "卡号[" + strNo + "]与选中的卡类型不一致");
		// 输入的起始卡号必须比默认的起始卡号大
		if (NumberUtils.toLong(strNo) < NumberUtils.toLong(defaultStrCardId)) {
			throw new BizException("最小可领取的卡号为[" + defaultStrCardId + "]");
		}
		
		CardStockInfo strStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(strNo);
		Assert.notNull(strStockInfo, "库存表中无卡号[" + strNo + "]的记录");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", cardSubClass);
		// 1. 发卡机构领卡时
		if (RoleType.CARD.getValue().equals(roleType)) {
			// 登录机构号与要领出卡的发卡机构一致时（发卡机构把自己的卡领出时），只能领取“卡在库”的卡
			if (StringUtils.equals(strCardInfo.getCardIssuer(), user.getBranchNo())) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 不一致，即发卡机构同级调拨时，只能领“已领卡”的卡
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", cardSectorId);
			}
		}
		// 2. 商户领卡时
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
		}
		// 3. 售卡代理领卡时（1.从发卡机构领卡，2.从售卡代理领卡）
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			// 从卡的发卡机构领卡时，即卡的发卡机构和卡的领出机构一致时。只能领“卡在库”状态的卡
			if (StringUtils.equals(strCardInfo.getCardIssuer(), cardSectorId)) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			} else {
				// 从售卡代理领卡时，只能领“已领出”的卡
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", cardSectorId);
			}
		}
		
		// 可领出的卡的总数量
		Long canReaciveCardNum = this.cardStockInfoDAO.getCouldReceive(params);
		
		params.put("strNo", strNo);
		
//		// 卡的领出机构号（卡库存状态为已领卡时，不为空）
//		String brancCode = "";
//		if (StringUtils.equals(CardStockState.RECEIVED.getValue(), MapUtils.getString(params, "cardStatus"))) {
//			brancCode = cardSectorId;
//		}
//		
//		// 得到不连续的最小卡号
//		String cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardId(cardSubClass, 
//				MapUtils.getString(params, "cardStatus"), strNo, brancCode);
		
		// 起始卡号后的，最小不能领的卡号
		String cantReceiveCardId = "";
		String cardStatus = MapUtils.getString(params, "cardStatus");
		// 领的是已领卡的卡时
		if (StringUtils.equals(CardStockState.RECEIVED.getValue(), cardStatus)) {
			Assert.notEmpty(cardSectorId, "领出机构号不能为空");
			cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardIdReceived(cardSubClass, strNo, cardSectorId);
		}
		// 领的是在库状态的卡时
		else if (StringUtils.equals(CardStockState.IN_STOCK.getValue(), cardStatus)) {
			cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardIdInStock(cardSubClass, strNo);
		}
		
		cantReceiveCardId = StringUtils.isEmpty(cantReceiveCardId) ? StringUtils.EMPTY : cantReceiveCardId;
		params.put("cantReceiveCardId", cantReceiveCardId);
		// 本次可领卡数量
		Long canReaciveThisTime = this.cardStockInfoDAO.getCouldReceiveThisTime(params);
		
		return new long[]{canReaciveCardNum, canReaciveThisTime};
	}
	
	public long[] canReaciveCardNum(String cardSubClass, String cardSectorId,
			String appOrgId, String stockStatus, String strNo, UserInfo user) throws BizException {
		String roleType = user.getRole().getRoleType();
		
		CardInfo strCardInfo = (CardInfo) this.cardInfoDAO.findByPk(strNo);
		Assert.notNull(strCardInfo, "卡号[" + strNo + "]不存在");
		// 输入的起始卡号所属的卡子类型必须与选中的卡类型一致
		Assert.equals(cardSubClass, strCardInfo.getCardSubclass(), "卡号[" + strNo + "]与选中的卡类型不一致");
		
		CardStockInfo strStockInfo = this.cardStockInfoDAO.findCardStockInfoByCardId(strNo);
		Assert.notNull(strStockInfo, "库存表中无卡号[" + strNo + "]的记录");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardSubclass", cardSubClass);
		// 1. 发卡机构领卡时
		if (RoleType.CARD.getValue().equals(roleType)) {
		//  领入机构号与登录机构号一致的时候是自己领卡，不一致时是替别人领卡
			if (StringUtils.equals(appOrgId, user.getBranchNo())) {
				// 对于自己领卡。卡的发卡机构，领出机构一致时。
				if (StringUtils.equals(strCardInfo.getCardIssuer(), cardSectorId)) {
					// 卡的发卡机构，领出机构一致时。领入机构也相同时，只能领“在库”的卡 。发卡机构号要传进去
					if (StringUtils.equals(strCardInfo.getCardIssuer(), appOrgId)) {
						params.put("cardStatus", CardStockState.IN_STOCK.getValue());
						params.put("cardIssuer", strCardInfo.getCardIssuer());
					}
					// 领入机构不相同时，要选择库存状态为“在库”还是“已领卡”的卡 。
					else {
						Assert.notEmpty(stockStatus, "卡库存状态不能为空");
						params.put("cardStatus", stockStatus);
						if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
							params.put("appOrgId", cardSectorId);
						} else {
							params.put("cardIssuer", strCardInfo.getCardIssuer());
						}
					}
				} else {
					params.put("cardStatus", CardStockState.RECEIVED.getValue());
					params.put("appOrgId", cardSectorId);
				}
			}
			// 对于替别人领卡的情况
			else {
				// 发卡机构为自己时，要选择卡库存状态
				if (StringUtils.equals(strCardInfo.getCardIssuer(), user.getBranchNo())) {
					Assert.notEmpty(stockStatus, "卡库存状态不能为空");
					params.put("cardStatus", stockStatus);
					if (StringUtils.equals(stockStatus, CardStockState.RECEIVED.getValue())) {
						params.put("appOrgId", cardSectorId);
					} else {
						params.put("cardIssuer", strCardInfo.getCardIssuer());
					}
				} else {
					params.put("cardStatus", CardStockState.RECEIVED.getValue());
					params.put("appOrgId", cardSectorId);
				}
			}
		}
		// 2. 商户领卡时
		else if (RoleType.MERCHANT.getValue().equals(roleType)
				|| RoleType.CARD_DEPT.getValue().equals(roleType)) {
			params.put("cardStatus", CardStockState.IN_STOCK.getValue());
			params.put("cardIssuer", strCardInfo.getCardIssuer());
		}
		// 3. 售卡代理领卡时（1.从发卡机构领卡，2.从售卡代理领卡）
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			// 从卡的发卡机构领卡时，即卡的发卡机构和卡的领出机构一致时。只能领“卡在库”状态的卡
			if (StringUtils.equals(strCardInfo.getCardIssuer(), cardSectorId)) {
				params.put("cardStatus", CardStockState.IN_STOCK.getValue());
				params.put("cardIssuer", strCardInfo.getCardIssuer());
			} else {
				// 从售卡代理领卡时，只能领“已领出”的卡
				params.put("cardStatus", CardStockState.RECEIVED.getValue());
				params.put("appOrgId", cardSectorId);
			}
		}
		
		// 可领出的卡的总数量
		Long canReaciveCardNum = this.cardStockInfoDAO.getCouldReceive(params);
		logger.debug("可领出的卡的总数量为[" + canReaciveCardNum + "]");
		
		params.put("strNo", strNo);
		
		// 起始卡号后的，最小不能领的卡号
		String cantReceiveCardId = "";
		String cardStatus = MapUtils.getString(params, "cardStatus");
		// 领的是已领卡的卡时
		if (StringUtils.equals(CardStockState.RECEIVED.getValue(), cardStatus)) {
			Assert.notEmpty(cardSectorId, "领出机构号不能为空");
			cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardIdReceived(cardSubClass, strNo, cardSectorId);
		}
		// 领的是在库状态的卡时
		else if (StringUtils.equals(CardStockState.IN_STOCK.getValue(), cardStatus)) {
			cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardIdInStock(cardSubClass, strNo);
		}
		cantReceiveCardId = StringUtils.isEmpty(cantReceiveCardId) ? StringUtils.EMPTY : cantReceiveCardId;
		logger.debug("本次领卡不连续的最小卡号[" + cantReceiveCardId + "]");
		
//		String cantReceiveCardId = this.cardStockInfoDAO.getCantReceiveCardId(cardSubClass, 
//				MapUtils.getString(params, "cardStatus"), strNo, brancCode);
		
		params.put("cantReceiveCardId", cantReceiveCardId);
		//params.put("appOrgId", value);// 卡的领出机构
		// 本次可领卡数量
		Long canReaciveThisTime = this.cardStockInfoDAO.getCouldReceiveThisTime(params);
		logger.debug("本次可领卡的数量[" + canReaciveThisTime + "]");
		
		return new long[]{canReaciveCardNum, canReaciveThisTime};
	}

	public AppReg addCardWithdraw(AppReg appReg, UserInfo sessionUser)
			throws BizException {
		Assert.notNull(appReg, "领卡登记对象不能为空。");
		Assert.notNull(sessionUser, "登录用户的信息不能为空。");
		Assert.notEmpty(appReg.getStrNo(), "起始卡号不能为空。");
		Assert.notEmpty(Integer.toString(appReg.getCardNum()), "要返库的卡的数量不能为空。");
		Assert.notTrue(appReg.getStrNo().length() != CARD_NO_LENGTH, "起始卡号长度错误!");

		String appOrgId = "";
		String roleType = sessionUser.getRole().getRoleType();
		// 登陆用户为发卡机构或售卡代理
		if (roleType.equals(RoleType.CARD.getValue())
				|| roleType.equals(RoleType.CARD_SELL.getValue())) {
			appOrgId = sessionUser.getBranchNo();
		}
		// 登陆用户为发卡机构网点
		else if (roleType.equals(RoleType.CARD_DEPT.getValue())) {
			appOrgId = sessionUser.getDeptId();
		}
		// 登陆用户为商户
		else if (roleType.equals(RoleType.MERCHANT.getValue())) {
			appOrgId = sessionUser.getMerchantNo();
		}
		
		// 检查起始卡号，卡数量
		checkCardWithDraw(appReg.getStrNo(), appReg.getCardNum(), appOrgId);
		
		// 查找卡库存对象
		CardStockInfo stockInfo = cardStockInfoDAO.findCardStockInfoByCardId(appReg.getStrNo());
		
		String binNo = CardBin.getBinNo(appReg.getStrNo());
		CardBin cardBin = (CardBin) cardBinDAO.findByPk(binNo);
		Assert.notNull(cardBin, "该卡号所属卡Bin不存在。");

		appReg.setCardIssuer(stockInfo.getCardIssuer());// 发卡机构
		appReg.setAppDate(stockInfo.getAppDate());
		appReg.setAppOrgId(stockInfo.getAppOrgId());
		appReg.setFlag(ReceiveFlag.WITHDRAW.getValue());
		appReg.setStatus(CheckState.WAITED.getValue());
		appReg.setUpdateBy(sessionUser.getUserId());
		appReg.setUpdateTime(new Date());
		
		appReg.setCardSectorId(stockInfo.getCardSectorId());// 卡的返入机构号

		appRegDAO.insert(appReg);
		
		// 启动单个流程
		try {
			workflowService.startFlow(appReg, "cardWithdrawAdapter", Long.toString(appReg.getId()), sessionUser);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
		return appReg;
	}

	/**
	 * 根据起始卡号，卡数量，检查要返库的卡号是否满足返库条件
	 * @param strNo 起始卡号
	 * @param cardNum 卡数量
	 * @param appOrgId 领卡机构
	 * @return
	 * @throws BizException
	 */
	private boolean checkCardWithDraw(String strNo, int cardNum, String appOrgId) throws BizException {
		boolean flag = false;
		String[] cardNoArray = CardUtil.getCard(strNo, cardNum);
		try {
			for (String cardId : cardNoArray) {
				CardStockInfo info = cardStockInfoDAO.findCardStockInfoByCardId(cardId);
				Assert.notNull(info, "卡库存中没有卡号["+ cardId +"]的记录，不能返库！");
				Assert.equals(info.getCardStatus(), CardStockState.RECEIVED.getValue(), "卡号["+ cardId +"]不是领卡状态，不能返库。");
				Assert.equals(appOrgId, info.getAppOrgId(), "只能对自己所领出的卡进行返库操作");
				
				flag = true;
			}
		} catch (BizException e) {
			flag = false;
			throw new BizException(e.getMessage());
		}
		return flag;
		
	}
	
	public List<NameValuePair> getReciveIssuer(String keyWord, UserInfo user)
			throws BizException {
		if (StringUtils.isEmpty(keyWord) || user == null) {
			return Collections.<NameValuePair> emptyList();
		}
		String roleType = user.getRole().getRoleType();
		
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		
		//根据传入的关键字， 首先取出发卡机构号
		Map<String, Object> params = new HashMap<String, Object>();
		if (RoleType.CENTER.getValue().equals(roleType)) {

			//根据传入的关键字， 运营中心是查所有发卡机构
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("branchTypes", new String[]{BranchType.CARD.getValue()});
			params.put("status", BranchState.NORMAL.getValue());
			
			List<BranchInfo> cardList = this.branchInfoDAO.findBranchList(params);
			
			for (BranchInfo branch : cardList) {
				NameValuePair branchPair = new NameValuePair();
				branchPair.setValue(branch.getBranchCode());
				branchPair.setName(branch.getBranchName());
				
				result.add(branchPair);
				
				// 发卡机构的所有部门
				params.clear();
				params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
				params.put("branchCode", branch.getBranchCode());
				List<DepartmentInfo> deptList = this.departmentInfoDAO.findDeptList(params);
				for (DepartmentInfo dept : deptList) {
					NameValuePair deptPair = new NameValuePair();
					deptPair.setValue(dept.getDeptId());
					deptPair.setName(dept.getDeptName());
					
					result.add(deptPair);
				}
			}
			
			// 所有售卡代理
			params.clear();
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("branchTypes", new String[]{BranchType.CARD_SELL.getValue()});
			params.put("status", BranchState.NORMAL.getValue());
			
			List<BranchInfo> sellList = this.branchInfoDAO.findBranchList(params);
			for (BranchInfo branchSell : sellList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(branchSell.getBranchCode());
				sellPair.setName(branchSell.getBranchName());
				
				result.add(sellPair);
			}
			
			// 所有的商户关键字要作为参数传入
			params.clear();
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("status", MerchState.NORMAL.getValue());
			List<MerchInfo> merList = this.merchInfoDAO.find(params);
			for (MerchInfo merchInfo : merList) {
				NameValuePair merchPair = new NameValuePair();
				merchPair.setValue(merchInfo.getMerchId());
				merchPair.setName(merchInfo.getMerchName());
				
				result.add(merchPair);
			}
		}
		// 如果为分支机构，可以查自己管理的
		else if (RoleType.FENZHI.getValue().equals(roleType)) {
			// 管理的发卡机构
			params.clear();
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("branchTypes", new String[]{BranchType.CARD.getValue()});
			params.put("status", BranchState.NORMAL.getValue());
			params.put("parent", user.getBranchNo());
			
			List<BranchInfo> cardList = this.branchInfoDAO.findBranchList(params);
			
			for (BranchInfo branch : cardList) {
				NameValuePair branchPair = new NameValuePair();
				branchPair.setValue(branch.getBranchCode());
				branchPair.setName(branch.getBranchName());
				
				result.add(branchPair);
			}
			
			// 管理的售卡代理
			params.clear();
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("branchTypes", new String[]{BranchType.CARD_SELL.getValue()});
			params.put("status", BranchState.NORMAL.getValue());
			params.put("parent", user.getBranchNo());
			
			List<BranchInfo> sellList = this.branchInfoDAO.findBranchList(params);
			for (BranchInfo branchSell : sellList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(branchSell.getBranchCode());
				sellPair.setName(branchSell.getBranchName());
				
				result.add(sellPair);
			}
			
			// 管理的商户
			params.clear();
			params.put("keyWord", MatchMode.ANYWHERE.toMatchString(keyWord));
			params.put("manageBranch", user.getBranchNo());
			params.put("status", MerchState.NORMAL.getValue());
			List<MerchInfo> merList = this.merchInfoDAO.find(params);
			for (MerchInfo merchInfo : merList) {
				NameValuePair merchPair = new NameValuePair();
				merchPair.setValue(merchInfo.getMerchId());
				merchPair.setName(merchInfo.getMerchName());
				
				result.add(merchPair);
			}
		}
		// 如果登录用户为发卡机构，可看到自己，自己部门的领卡记录和自己的售卡代理领的卡
		else if (RoleType.CARD.getValue().equals(roleType)) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(user.getBranchNo());
			NameValuePair pair = new NameValuePair();
			pair.setValue(info.getBranchCode());
			pair.setName(info.getBranchName());
			result.add(pair);// 自己
			List<DepartmentInfo> list = departmentInfoDAO.findByBranch(user.getBranchNo());
			for (DepartmentInfo dept : list) {
				NameValuePair namePair = new NameValuePair();
				namePair.setValue(dept.getDeptId());
				namePair.setName(dept.getDeptName());
				result.add(namePair);// 部门
			}
			List<BranchInfo> sellList = branchInfoDAO.findCardProxy(user.getBranchNo(), ProxyType.SELL);
			for (BranchInfo sellBranch : sellList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(sellBranch.getBranchCode());
				sellPair.setName(sellBranch.getBranchName());
				result.add(sellPair);// 售卡代理
			}
			
			List<BranchInfo> chidrenList = branchInfoDAO.findChildrenList(user.getBranchNo());
			for (BranchInfo childBranch : chidrenList) {
				NameValuePair sellPair = new NameValuePair();
				sellPair.setValue(childBranch.getBranchCode());
				sellPair.setName(childBranch.getBranchName());
				result.add(sellPair);// 下级发卡机构
			}
			
			List<MerchInfo> merchList = merchInfoDAO.findFranchMerchList(user.getBranchNo());
			for (MerchInfo merchInfo : merchList) {
				NameValuePair merchPair = new NameValuePair();
				merchPair.setValue(merchInfo.getMerchId());
				merchPair.setName(merchInfo.getMerchName());
				
				result.add(merchPair); //关联的商户
			}
		}
		// 如果登录用户为部门的话，可看到自己的领卡记录
		else if (RoleType.CARD_DEPT.getValue().equals(roleType)) {
			DepartmentInfo dept = (DepartmentInfo) departmentInfoDAO.findByPk(user.getDeptId());
			NameValuePair pair = new NameValuePair();
			pair.setValue(dept.getDeptId());
			pair.setName(dept.getDeptName());
			result.add(pair);
		}
		// 如果登陆的是售卡代理，可看到自己的领卡记录
		else if (RoleType.CARD_SELL.getValue().equals(roleType)) {
			BranchInfo info = (BranchInfo) branchInfoDAO.findByPk(user.getBranchNo());
			NameValuePair pair = new NameValuePair();
			pair.setValue(info.getBranchCode());
			pair.setName(info.getBranchName());
			result.add(pair);
		}
		// 如果登陆的是商户代理，可看到自己的领卡记录
		else if (RoleType.MERCHANT.getValue().equals(roleType)) {
			MerchInfo info = (MerchInfo) merchInfoDAO.findByPk(user.getMerchantNo());
			NameValuePair pair = new NameValuePair();
			pair.setValue(info.getMerchId());
			pair.setName(info.getMerchName());
			result.add(pair);
		}
		
		return result;
	}
	
	public void generateExcel(HttpServletResponse response, Map<String, Object> params)
			throws BizException {
		Paginater page = this.cardStockInfoDAO.findCardStockInfoPage(params, 0, 1);
		page.getMaxRowCount();
		if (page.getMaxRowCount() > MAX_ROW_COUNT) {
			throw new BizException("要导出的数量超出范围，请缩小查询范围。");
		}
		List<CardStockInfo> list = this.cardStockInfoDAO.findCardStockInfoList(params);

		// 工作表的集合sheet
		List<String> sheetNameList = new ArrayList<String>();
		sheetNameList.add("卡库存信息");
		
		// 每个工作表的第一行即表头的集合list
		List<Object[]> titleItemList = new ArrayList<Object[]>();
		titleItemList.add(getCardStockInfoDetailItem());
		
		// 每个工作表的数据类容的集合list
		List<List<Object[]>> dataLists = new ArrayList<List<Object[]>>();
		dataLists.add(getCardStockInfoDetailData(list));
		
		// 报表内容的起始行号
		int starLine = 1;
		
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-Type", "application/ms-excel");
			
			String fileName = "卡库存信息.xls";
			fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
			
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			
			ExcelFileExport.generateFile(outputStream, sheetNameList, titleItemList, dataLists, starLine);
		} catch (ExcelExportException e) {
			logger.error("生成卡库存信息Excel文件发生ExcelExportException。原因：" + e);
			throw new BizException("生成卡库存信息Excel文件发生ExcelExportException。原因：" + e.getMessage());
		} catch (IOException e) {
			logger.error("生成卡库存信息Excel文件发生IOException，原因：" + e.getMessage());
			throw new BizException("生成卡库存信息Excel文件发生IOException。原因：" + e.getMessage());
		}
	}
	
	/**
	 * 设置卡库存信息明细报表表头 
	 * @return
	 * @throws BizException
	 */
	private static Object[] getCardStockInfoDetailItem() throws BizException{
		List<String> rowDataList = new ArrayList<String>();
		
		rowDataList.add("卡号");
		rowDataList.add("卡BIN");
		rowDataList.add("卡种类");
		rowDataList.add("卡子类");
		rowDataList.add("卡批次");
		rowDataList.add("发卡机构编号");
		rowDataList.add("发卡机构名称");
		rowDataList.add("领卡机构编号");
		rowDataList.add("领卡机构名称");
		rowDataList.add("领卡日期");
		rowDataList.add("状态");
//		rowDataList.add("操作人");
		
		return rowDataList.toArray();
	}
	
	/**
	 * 生成卡库存信息内容list
	 * @param settDate 清算日期
	 * @param cardIssuer 发卡机构编号
	 * @return
	 * @throws BizException
	 */
	private List<Object[]> getCardStockInfoDetailData(List<CardStockInfo> list) throws BizException{
		List<Object[]> data = new ArrayList<Object[]>();
		
		// 如果取不到数据则返回空
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (CardStockInfo stockInfo : list){
			List<Object> rowDataList = new ArrayList<Object>();
			rowDataList.add(stockInfo.getCardId());
			rowDataList.add(stockInfo.getCardBin());
			rowDataList.add(stockInfo.getCardClassName());
			rowDataList.add(stockInfo.getCardSubclass());
			rowDataList.add(stockInfo.getMakeId());
			rowDataList.add(stockInfo.getCardIssuer());
			rowDataList.add(NameTag.getBranchName(stockInfo.getCardIssuer()));
			rowDataList.add(stockInfo.getAppOrgId());
			
			String appOrgName = NameTag.getBranchName(stockInfo.getAppOrgId());
			if (StringUtils.isBlank(appOrgName)) {
				appOrgName = NameTag.getDeptName(stockInfo.getAppOrgId()) ;
				appOrgName = StringUtils.isBlank(appOrgName) ? NameTag.getMerchName(stockInfo.getAppOrgId()) : appOrgName;
			}
			rowDataList.add(appOrgName);
			rowDataList.add(stockInfo.getAppDate());
			rowDataList.add(stockInfo.getCardStatusName());
//			rowDataList.add(NameTag.getUserName(stockInfo.get)
			
			data.add(rowDataList.toArray());
		}
		
		return data;
	}
}
