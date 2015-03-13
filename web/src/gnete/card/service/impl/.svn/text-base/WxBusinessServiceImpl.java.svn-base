package gnete.card.service.impl;

import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.WxDepositReconRegDAO;
import gnete.card.dao.WxReconciliationDetailDAO;
import gnete.card.dao.WxUserInfoDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WxCommonDataBean;
import gnete.card.entity.WxDepositReconReg;
import gnete.card.entity.WxReconciliationDetail;
import gnete.card.entity.WxUserInfo;
import gnete.card.entity.state.WxRecocitionDetailState;
import gnete.card.entity.state.WxRecocitionState;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.WxBusinessService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxBusinessService")
public class WxBusinessServiceImpl implements WxBusinessService {
	
	private static final Logger logger = LoggerFactory.getLogger(WxBusinessServiceImpl.class);
	
	@Autowired
	private WxDepositReconRegDAO wxDepositReconRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private WxUserInfoDAO wxUserInfoDAO;
	@Autowired
	private WxReconciliationDetailDAO wxReconciliationDetailDAO;

	@Override
	public Paginater findWxDepositReconRegPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.wxDepositReconRegDAO.findPage(params, pageNumber, pageSize);
	}

	@Override
	public WxDepositReconReg findWxDepositReconReg(Long id) {
		return (WxDepositReconReg) this.wxDepositReconRegDAO.findByPk(id);
	}

	@Override
	public void addWxDepositReconReg(WxDepositReconReg wxDepositReconReg, UserInfo sessionUser) throws BizException {
		Assert.notNull(wxDepositReconReg, "要添加实名卡扣款充值调账补帐登记薄对象不能为空");
		Assert.notNull(sessionUser, "登录用户信息不能为空");
		Assert.notNull(wxDepositReconReg.getReconDetailId(), "对账结果明细ID不能为空");
	
		try {
			wxDepositReconReg.setIssNo(sessionUser.getBranchNo());
			
			WxReconciliationDetail detail = (WxReconciliationDetail) this.wxReconciliationDetailDAO.findByPk(wxDepositReconReg.getReconDetailId());
			Assert.notNull(detail, "对账结果明细表中不存在ID为[" + wxDepositReconReg.getReconDetailId() + "]的记录");
			Assert.equals(detail.getProStatus(), WxRecocitionDetailState.UN_DEAL.getValue(), "对账结果明细表的状态不为“未处理”，不能进行调账或补帐");
			
			WxCommonDataBean bean = getWxCommonDataBean(wxDepositReconReg.getIssNo(), wxDepositReconReg.getCardId(), wxDepositReconReg.getExtCardId());
			
			Assert.equals(bean.getCardIssuer(), sessionUser.getBranchNo(), "用户[" + sessionUser.getUserName() + "]只能为自己所属的机构卡进行调账补帐");
			
			wxDepositReconReg.setCardId(bean.getCardId());
			wxDepositReconReg.setIssNo(bean.getCardIssuer());
			wxDepositReconReg.setExtCardId(bean.getExtCardId());
			wxDepositReconReg.setStatus(WxRecocitionState.WAIT_DEAL.getValue());
			wxDepositReconReg.setUpdateTime(new Date());
			wxDepositReconReg.setUpdateUser(sessionUser.getUserId());
			
			this.wxDepositReconRegDAO.insert(wxDepositReconReg);
			
			// 组实名卡充值调账补帐报文，然后发到后台。根据后台处理结果更新登记薄记录
			MsgSender.sendMsg(MsgType.WX_DEPOSIT_RECO, wxDepositReconReg.getSeqId(), sessionUser.getUserId());
		} catch (Exception e) {
			logger.error("添加实名卡扣款充值调账补帐登记薄失败，原因{" + e.getMessage() + "}", e);
			throw new BizException(e.getMessage());
		}
	}
	
	@Override
	public WxCommonDataBean getWxCommonDataBean(String cardIssuer, String cardId, String extCardId) throws BizException {
		WxCommonDataBean bean = new WxCommonDataBean();
		
		try {
			// 都不为空的时候
			if (CommonHelper.isNotEmpty(cardId) && CommonHelper.isNotEmpty(cardIssuer) && CommonHelper.isNotEmpty(extCardId)) {
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				
				Assert.notNull(cardInfo, "卡信息表中没有卡号[" + cardId + "]的记录.");
				Assert.equals(cardInfo.getCardIssuer(), cardIssuer, "卡号[" + cardId + "]对应的发卡机构号与传入的发卡机构号[" + cardIssuer + "]不一致.");
				Assert.notEmpty(cardInfo.getExternalCardId(), "卡号[" + cardId + "]对应的用户标识号不能为空");
				
				WxUserInfo userInfo = (WxUserInfo) this.wxUserInfoDAO.findByPk(cardInfo.getExternalCardId());
				Assert.notNull(userInfo, "微信平台用户信息表中没有用户标识号为[" + cardInfo.getExternalCardId() + "]的记录");
				Assert.equals(extCardId, userInfo.getExternalCardId(), 
						"微信平台用户信息表中的手机号[" + userInfo.getExternalCardId() + "]与传入的手机号[" + extCardId + "]不一致。");
				
				Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + cardId + "]的账号不存在，请联系管理员确定是否开卡成功！");
				
				bean.setAcctId(cardInfo.getAcctId());
				bean.setCardId(cardId);
				bean.setCardIssuer(cardIssuer);
				bean.setExtCardId(extCardId);
				bean.setUserId(userInfo.getUserId());
				bean.setUserInfo(userInfo);
				bean.setCardInfo(cardInfo);
			}
			// 卡号不为空时
			else if (CommonHelper.isNotEmpty(cardId)) {
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);
				
				Assert.notNull(cardInfo, "卡信息表中没有卡号[" + cardId + "]的记录.");
				Assert.notEmpty(cardInfo.getExternalCardId(), "卡号[" + cardId + "]对应的用户标识号不能为空");
				Assert.notEmpty(cardInfo.getCardIssuer(), "卡号[" + cardId + "]对应的发卡机构号不能为空");
				
				WxUserInfo userInfo = (WxUserInfo) this.wxUserInfoDAO.findByPk(cardInfo.getExternalCardId());
				Assert.notNull(userInfo, "微信平台用户信息表中没有用户标识号为[" + cardInfo.getExternalCardId() + "]的记录");
				Assert.notEmpty(userInfo.getExternalCardId(), "用户[" + userInfo.getUserId() + "]对应的外部卡号（手机号）不能为空");
				
				Assert.notEmpty(cardInfo.getAcctId(), "卡号[" + cardId + "]的账号不存在，请联系管理员确定是否开卡成功！");
				
				bean.setAcctId(cardInfo.getAcctId());
				bean.setCardId(cardId);
				bean.setCardIssuer(cardInfo.getCardIssuer());
				bean.setExtCardId(userInfo.getExternalCardId());
				bean.setUserId(userInfo.getUserId());
				bean.setUserInfo(userInfo);
				bean.setCardInfo(cardInfo);
			} 
			// 外部卡号和发卡机构不能同时为空，那一定是个人用户或个体户。
			else if (CommonHelper.isNotEmpty(cardIssuer) && CommonHelper.isNotEmpty(extCardId)) {
				Map<String, Object> userParams = new HashMap<String, Object>();
				
				userParams.put("externalCardId", extCardId);
				
				List<WxUserInfo> userInfoList = this.wxUserInfoDAO.findList(userParams);
				Assert.notEmpty(userInfoList, "外部卡号（手机号）[" + extCardId + "]对应的用户不存在。");		

				WxUserInfo userInfo = userInfoList.get(0);

				// 根据发卡机构和用户标识号得到卡信息表的记录
				Map<String, Object> cardParams = new HashMap<String, Object>();
				
				cardParams.put("cardIssuer", cardIssuer);
				cardParams.put("externalCardId", userInfo.getUserId());
				
				List<CardInfo> cardInfoList = this.cardInfoDAO.findCardFileList(cardParams);
				Assert.notEmpty(cardInfoList, "发卡机构[" + cardIssuer + "]和用户标识[" + userInfo.getUserId() + "]对应的卡记录不存在，请先开卡。");
				
				Assert.notEmpty(cardInfoList.get(0).getAcctId(), "卡号[" + cardInfoList.get(0).getCardId() + "]的账号不存在，请联系管理员确定是否开卡成功！");
				
				bean.setAcctId(cardInfoList.get(0).getAcctId());
				bean.setCardId(cardInfoList.get(0).getCardId());
				bean.setUserId(userInfo.getUserId());
				bean.setCardIssuer(cardIssuer);
				bean.setExtCardId(extCardId);
				bean.setUserInfo(userInfo);
				bean.setCardInfo(cardInfoList.get(0));
			} else {
				throw new BizException("请至少传入发卡机构号和外部卡号（手机号）或卡号。");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizException("用户未注册");
		}
		
		return bean;
	}
}
