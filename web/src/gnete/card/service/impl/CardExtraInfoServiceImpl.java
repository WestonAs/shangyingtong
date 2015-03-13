package gnete.card.service.impl;

import flink.file.CardExtraInfoImporter;
import flink.util.DateUtil;
import flink.util.MobileNumUtil;
import flink.util.Paginater;
import flink.util.StringUtil;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.PwdChangeStatusType;
import gnete.card.entity.type.RoleType;
import gnete.card.service.CardExtraInfoService;
import gnete.card.tag.NameTag;
import gnete.card.util.BranchUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardExtraInfoService")
public class CardExtraInfoServiceImpl implements CardExtraInfoService {

	@Autowired
	private CardExtraInfoDAO	cardExtraInfoDAO;
	@Autowired
	private CardInfoDAO			cardInfoDAO;

	public boolean addCardExtraInfo(CardExtraInfo cardExtraInfo, String updateBy) throws BizException {
		Assert.notNull(cardExtraInfo, "添加的持卡人信息不能为空");
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardExtraInfo.getCardId());
		Assert.notNull(cardInfo, "卡号不存在。");
		CardExtraInfo oldInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardExtraInfo.getCardId());
		Assert.isNull(oldInfo, "已存在该卡号持卡人信息，可以通过编辑来修改持卡人信息。");
		cardExtraInfo.setUpdateBy(updateBy);
		cardExtraInfo.setUpdateTime(new Date());
		cardExtraInfo.setPassword(Constants.DEFUALT_SEARCH_PW);

		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId()) && cardExtraInfo.isOpenSms()) {
			cardInfoDAO.updatePhoneNum(cardExtraInfo.getMobileNo(), cardExtraInfo.getCardId());
		}

		return this.cardExtraInfoDAO.insert(cardExtraInfo) != null;
	}

	public boolean deleteCardExtraInfo(String cardId) throws BizException {
		Assert.notNull(cardId, "删除的持卡人信息不能为空");

		CardExtraInfo cardExtraInfo = (CardExtraInfo) cardExtraInfoDAO.findByPk(cardId);
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardExtraInfo.getCardId());
		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId()) && cardExtraInfo.isOpenSms()) {
			cardInfoDAO.updatePhoneNum("", cardExtraInfo.getCardId());
		}

		return this.cardExtraInfoDAO.delete(cardId) > 0;
	}

	public boolean modifyCardExtraInfo(CardExtraInfo cardExtraInfo, String updateBy) throws BizException {
		Assert.notNull(cardExtraInfo, "修改的持卡人信息不能为空");

		cardExtraInfo.setUpdateTime(new Date());
		cardExtraInfo.setUpdateBy(updateBy);

		CardExtraInfo oldCardExtraInfo = (CardExtraInfo) cardExtraInfoDAO.findByPk(cardExtraInfo.getCardId());
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardExtraInfo.getCardId());
		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId())) {
			if (cardExtraInfo.isOpenSms()) {
				cardInfoDAO.updatePhoneNum(cardExtraInfo.getMobileNo(), cardExtraInfo.getCardId());
			} else if (oldCardExtraInfo.isOpenSms()) {
				cardInfoDAO.updatePhoneNum("", cardExtraInfo.getCardId());
			}
		}

		return this.cardExtraInfoDAO.update(cardExtraInfo) > 0;
	}

	public boolean modifyCardPassWord(CardInfo cardInfo, String updateBy) throws BizException {
		Assert.notNull(cardInfo, "修改的卡信息不能为空");
		CardInfo temp = (CardInfo) this.cardInfoDAO.findByPk(cardInfo.getCardId());
		temp.setPassword(cardInfo.getPassword());
		temp.setPwdChangeStatus(PwdChangeStatusType.PWMODIFIED.getValue());
		return this.cardInfoDAO.update(temp) > 0;
	}

	public void modifyPass(String cardId, String oldPass, String newPass, UserInfo sessionUser)
			throws BizException {
		Assert.notEmpty(oldPass, "用户旧密码不能为空");
		Assert.notEmpty(newPass, "用户新密码不能为空");

		CardExtraInfo cardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);
		Assert.notNull(cardExtraInfo, "持卡人信息不存在");

		String md5 = null;
		String newMd5 = null;
		try {
			md5 = StringUtil.getMD5(oldPass);
			newMd5 = StringUtil.getMD5(newPass);
		} catch (Exception e) {
			throw new BizException("生成查询密码时发生错误！");
		}
		Assert.notTrue(!md5.equals(cardExtraInfo.getPassword()), "旧密码不对");

		cardExtraInfo.setPassword(newMd5);
		cardExtraInfo.setUpdateTime(new Date());
		if (sessionUser != null) {
			cardExtraInfo.setUpdateBy(sessionUser.getUserId());
		}

		this.cardExtraInfoDAO.update(cardExtraInfo);

	}

	public void checkLogin(CardExtraInfo cardExtraInfo, String password) throws BizException {
		Assert.notNull(cardExtraInfo, "卡号不正确或者输入卡号没有相关持卡人信息。");
		String loginPwd = null;
		try {
			loginPwd = StringUtil.getMD5(password);
		} catch (Exception e) {
			throw new BizException("生成查询密码时发生错误！");
		}
		Date pwdExpirTime = cardExtraInfo.getPwdExpirTime();

		if (pwdExpirTime != null) {
			Date currenTime = new Date();
			Assert.isTrue(pwdExpirTime.after(currenTime), "查询密码已过期。");
		}

		Assert.isTrue(loginPwd.equals(cardExtraInfo.getPassword()), "密码错误");
	}

	public List<CardExtraInfo> addCardExtraInfoBat(File upload, String uploadFileName, UserInfo user,
			String branchCode) throws Exception {
		Assert.notEmpty(branchCode, "选择的的机构编号不能为空！");
		String roleType = user.getRole().getRoleType();
		if (RoleType.CARD.getValue().equals(roleType) || RoleType.CARD_DEPT.getValue().equals(roleType)) {
			Assert.isTrue(BranchUtil.isBelong2SameTopBranch(branchCode, user.getBranchNo()),
					"选择的发卡机构与登录的机构不属于同一顶级发卡机构");
		}

		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertCardExtraInfoList中
		List<CardExtraInfo> uninsertCardExtraInfoList = new ArrayList<CardExtraInfo>();
		try {
			CardExtraInfoImporter cardExtraInfoImporter = new CardExtraInfoImporter();
			List<CardExtraInfo> CardExtraInfoList = cardExtraInfoImporter.getFileImporterList(
					upload, uploadFileName);

			for (CardExtraInfo cardExtraInfo : CardExtraInfoList) {
				// 检查数据正确性
				if (StringUtils.isNotEmpty(cardExtraInfo.getCredType())
						&& CertType.ALL.get(cardExtraInfo.getCredType()) == null) {
					cardExtraInfo.setAddress("证件类型代码[" + cardExtraInfo.getCredType() + "]不正确。");
					uninsertCardExtraInfoList.add(cardExtraInfo);
					continue;
				} else if (StringUtils.isNotEmpty(cardExtraInfo.getBirthday())
						&& !DateUtil.isValidDate(cardExtraInfo.getBirthday(), "yyyyMMdd")) {
					cardExtraInfo.setAddress("生日[" + cardExtraInfo.getBirthday() + "]输入有误，正确格式为20120101。");
					uninsertCardExtraInfoList.add(cardExtraInfo);
					continue;
				} else if (StringUtils.trim(cardExtraInfo.getCardId()).length() > 20) {
					cardExtraInfo.setAddress("长度最多不能超过20位。");
					uninsertCardExtraInfoList.add(cardExtraInfo);
					continue;
				}

				String cardId = cardExtraInfo.getCardId();
				CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(cardId);

				// 检查输入卡号是否存在
				if (cardInfo == null) {
					cardExtraInfo.setAddress("不存在，不能录入持卡人信息。");
					uninsertCardExtraInfoList.add(cardExtraInfo);
					continue;
				}

				// 检查卡号与发卡机构所属关系
				if (!StringUtils.equals(cardInfo.getCardIssuer(), branchCode)) {
					cardExtraInfo.setAddress("不属于所选的发卡机构[" + branchCode + "]，不能录入持卡人信息。");
					uninsertCardExtraInfoList.add(cardExtraInfo);
					continue;
				}

				cardExtraInfo.setCardCustomerId(cardInfo.getCardCustomerId()); // 购卡客户号
				cardExtraInfo.setCardBranch(cardInfo.getCardIssuer()); // 发卡机构号
				cardExtraInfo.setSaleOrgId(cardInfo.getSaleOrgId()); // 售卡机构
				cardExtraInfo.setUpdateBy(user.getUserId());
				cardExtraInfo.setUpdateTime(new Date());

				// 检查卡号的卡附加信息表中是否存在数据
				CardExtraInfo oldCardExtraInfo = (CardExtraInfo) this.cardExtraInfoDAO.findByPk(cardId);

				if (oldCardExtraInfo != null) {
					// 如果存在卡附加信息，更新卡附加信息
					this.cardExtraInfoDAO.update(cardExtraInfo);
				} else {
					// 如果不存在卡附加信息，插入卡附加信息
					this.cardExtraInfoDAO.insert(cardExtraInfo);
				}
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return uninsertCardExtraInfoList;
	}

	@Override
	public void export(HttpServletResponse response, Map<String, Object> params) throws IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("持卡人信息.csv".getBytes("gb18030"), "ISO-8859-1"));
		StringBuilder title = new StringBuilder();
		title.append("卡号,发卡机构编号,发卡机构名称,持卡人姓名,证件类型,证件号码");
		title.append(",联系地址,手机号,联系电话,邮件地址,生日");
		response.getWriter().println(title);
		int pageNumber = 1;
		int pageSize = 1024 * 40;
		Paginater paginater = null;
		do {
			paginater = cardExtraInfoDAO.findCardExtraInfo(params, pageNumber, pageSize);
			for (Object obj : paginater.getData()) {
				CardExtraInfo cardExtraInfo = (CardExtraInfo) obj;
				StringBuilder line = new StringBuilder();
				line.append("	").append(cardExtraInfo.getCardId());
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getCardBranch()));
				line.append(",\t").append(StringUtils.defaultString(NameTag.getBranchName(cardExtraInfo.getCardBranch())));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getCustName()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getCredTypeName()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getCredNo()));

				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getAddress()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getMobileNo()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getTelNo()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getEmail()));
				line.append(",\t").append(StringUtils.defaultString(cardExtraInfo.getBirthday()));

				line.append("\r\n");
				response.getWriter().print(line);
			}
			pageNumber++;
		} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}

}
