package gnete.card.service.impl;

import flink.file.MembInfoRegImporter;
import flink.util.CommonHelper;
import flink.util.DateUtil;
import flink.util.MobileNumUtil;
import flink.util.Paginater;
import gnete.card.dao.AcctInfoDAO;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.MembAppointDetailRegDAO;
import gnete.card.dao.MembAppointRegDAO;
import gnete.card.dao.MembClassDefDAO;
import gnete.card.dao.MembInfoRegDAO;
import gnete.card.dao.MembRegDAO;
import gnete.card.dao.PointBalDAO;
import gnete.card.dao.RenewCardRegDAO;
import gnete.card.entity.AcctInfo;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MembAppointDetailReg;
import gnete.card.entity.MembAppointReg;
import gnete.card.entity.MembClassDef;
import gnete.card.entity.MembInfoReg;
import gnete.card.entity.MembReg;
import gnete.card.entity.PointBal;
import gnete.card.entity.PointBalKey;
import gnete.card.entity.RenewCardReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.CardState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.EducationType;
import gnete.card.entity.type.SexType;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.VipCardService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("VipCardService")
public class VipCardServiceImpl implements VipCardService {

	@Autowired
	private MembClassDefDAO			membClassDefDAO;
	@Autowired
	private AcctInfoDAO				acctInfoDAO;
	@Autowired
	private PointBalDAO				pointBalDAO;
	@Autowired
	private RenewCardRegDAO			renewCardRegDAO;
	@Autowired
	private MembRegDAO				membRegDAO;
	@Autowired
	private CardSubClassDefDAO		cardSubClassDefDAO;
	@Autowired
	private CardInfoDAO				cardInfoDAO;

	@Autowired
	private MembInfoRegDAO			membInfoRegDAO;
	@Autowired
	private MembAppointRegDAO		membAppointRegDAO;
	@Autowired
	private MembAppointDetailRegDAO	membAppointDetailRegDAO;

	public boolean addmMembClassDef(MembClassDef membClassDef, String sessionUserCode, String cardIssuer)
			throws BizException {

		Assert.notNull(membClassDef, "添加的会员定义对象不能为空");

		membClassDef.setUpdateBy(sessionUserCode);
		membClassDef.setCardIssuer(cardIssuer);
		membClassDef.setUpdateTime(new Date());
		membClassDef.setStatus(RegisterState.PASSED.getValue());
		// membClassDef.setStatus(MemberCertifyState.WAITED.getValue()); //待审核状态

		return this.membClassDefDAO.insert(membClassDef) != null;
	}

	public boolean delete(String membClass) throws BizException {

		Assert.notNull(membClass, "删除的会员定义对象不能为空");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("membClass", membClass);
		Collection<CardSubClassDef> dataList = this.cardSubClassDefDAO.findCardSubClassDef(params, 0, 20)
				.getData();

		Assert.isEmpty(dataList, "该会员子类型已在使用，不能删除！");

		int count = this.membClassDefDAO.delete(membClass);

		return count > 0;
	}

	public boolean modifyMembClassDef(MembClassDef membClassDef, String sessionUserCode) throws BizException {

		Assert.notNull(membClassDef, "更新的会员定义对象不能为空");

		membClassDef.setStatus(RegisterState.PASSED.getValue());
		membClassDef.setUpdateBy(sessionUserCode);
		membClassDef.setUpdateTime(new Date());

		int count = this.membClassDefDAO.update(membClassDef);

		return count > 0;
	}

	public boolean isExistMembClass(String membClass) throws BizException {

		Assert.notNull(membClass, "会员类型不能为空");
		return this.membClassDefDAO.findByPk(membClass) != null;
	}

	public boolean addUpgradeCard(RenewCardReg renewCardReg, String sessionUserCode) throws BizException {

		return false;
	}

	public boolean isMeetUpgradeRule(String cardId) throws BizException {

		AcctInfo acctInfo = acctInfoDAO.findAcctInfoByCardId(cardId);

		if (acctInfo == null) {
			return false;
		}

		// 如果该卡定义了会员类型
		if (acctInfo.getMembClass() != null) {
			// 取得升级积分阈值
			String membClass = acctInfo.getMembClass();
			MembClassDef membClassDef = (MembClassDef) membClassDefDAO.findByPk(membClass);
			String membUpgradeThrhd = membClassDef.getMembUpgradeThrhd();

			// 取得积分余额
			String ptClass = acctInfo.getPtClass();
			if (ptClass == null) {
				// 未定义积分类型
				return false;
			}

			PointBalKey pointBalKey = new PointBalKey();
			pointBalKey.setAcctId(acctInfo.getAcctId());
			pointBalKey.setPtClass(ptClass);
			PointBal pointBal = (PointBal) pointBalDAO.findByPk(pointBalKey);

			if (pointBal == null) {
				return false;
			}
			BigDecimal ptAvlb = pointBal.getPtAvlb();

			// 如果积分余额大于等于升级积分阈值，那么可以升级

			if (new BigDecimal(membUpgradeThrhd).compareTo(ptAvlb) <= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean deleteUpgradeRecord(Long renewCardId) throws BizException {
		Assert.notNull(renewCardId, "删除的会员升级换卡记录不能为空");

		return this.renewCardRegDAO.delete(renewCardId) > 0;
	}

	/*** 会员注册登记模块 ***/
	public boolean addMembReg(MembReg membReg, UserInfo userInfo) throws BizException {
		Assert.notNull(membReg, "添加的会员注册对象不能为空");

		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(membReg.getCardId());
		Assert.notNull(cardInfo, "卡[" + membReg.getCardId() + "]不存在");
		List<String> statusList = Arrays.asList(CardState.ACTIVE.getValue(), CardState.PRESELLED.getValue());
		Assert.isTrue(statusList.contains(cardInfo.getCardStatus()), "卡[" + membReg.getCardId()
				+ "]的状态不是“预售出”或“正常（已激活）”状态");

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("cardId", membReg.getCardId());
		Assert.isEmpty(this.membRegDAO.findList(params), "卡号[" + membReg.getCardId() + "]的会员资料已经被注册，不能重复注册。");

		String roleType = userInfo.getRole().getRoleType();
		// 检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(roleType, userInfo.getBranchNo(), cardInfo, "vip会员资料注册");

		membReg.setCardIssuer(cardInfo.getCardIssuer());
		membReg.setRegTime(new Date());
		membReg.setUpdateBy(userInfo.getUserId());
		membReg.setUpdateTime(membReg.getRegTime());

		// 注册的手机号不为空， 则 更新 卡信息表的phoneNum字段
		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId()) ) {
			cardInfoDAO.updatePhoneNum(membReg.getMobileNo(), membReg.getCardId());
		}

		return this.membRegDAO.insert(membReg) != null;
	}

	public boolean deleteMembReg(Long membRegId) throws BizException {
		Assert.notNull(membRegId, "删除的会员注册ID不能为空");
		MembReg membReg = (MembReg) membRegDAO.findByPk(membRegId);
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(membReg.getCardId());

		// cardInfo的externalCardId不是手机号， 则 置空 卡信息表的phoneNum字段
		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId())) {
			cardInfoDAO.updatePhoneNum("", membReg.getCardId());
		}
		return this.membRegDAO.delete(membRegId) > 0;
	}

	public boolean modifyMembReg(MembReg membReg, UserInfo sessionUser) throws BizException {
		Assert.notNull(membReg, "更新的会员注册对象不能为空");
		MembReg old = (MembReg)membRegDAO.findByPk(membReg.getMembRegId());
		Assert.isTrue(StringUtils.equals(old.getCardIssuer(),sessionUser.getBranchNo()), "只能修改本机构的会员注册信息！");
		
		
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(membReg.getCardId());
		// cardInfo的externalCardId不是手机号， 则 更新 卡信息表的phoneNum字段
		if (!MobileNumUtil.isMobileNum(cardInfo.getExternalCardId())) {
			cardInfoDAO.updatePhoneNum(membReg.getMobileNo(), membReg.getCardId());
		}

		membReg.setUpdateBy(sessionUser.getUserId());
		membReg.setUpdateTime(new Date());
		return this.membRegDAO.update(membReg) > 0;
	}

	/*** 高级会员登记 ***/
	public boolean addMembInfoReg(MembInfoReg membInfoReg, UserInfo userInfo) throws BizException {
		Assert.notNull(membInfoReg, "添加的会员登记对象不能为空");
		if (CommonHelper.isNotEmpty(membInfoReg.getCredType())
				&& CommonHelper.isEmpty(membInfoReg.getCredNo())) {
			throw new BizException("证件类型代码[" + membInfoReg.getCredType() + "]不为空时,证件号不能为空。");
		}

		membInfoReg.setMembInfoId(membInfoRegDAO.selectMembInfoRegSEQ());
		membInfoReg.setCardIssuer(userInfo.getBranchNo());
		membInfoReg.setUpdateBy(userInfo.getUserId());
		membInfoReg.setInsertTime(new Date());

		return this.membInfoRegDAO.insert(membInfoReg) != null;
	}

	public List<MembInfoReg> addMembInfoRegBat(File upload, String uploadFileName, UserInfo user)
			throws BizException {
		Long seq = membInfoRegDAO.selectMembInfoRegSEQ();

		List<MembClassDef> membClassDefs = loadMtClass(user.getBranchNo());
		Map<String, String> membClassMap = new HashMap<String, String>();
		for (MembClassDef membClassDef : membClassDefs) {
			membClassMap.put(membClassDef.getMembClass(), membClassDef.getMembLevel());
		}
		// 检查记录是否合法，合法的记录插入登记簿中，不合法的放在uninsertMembInfoRegList中
		List<MembInfoReg> uninsertMembInfoRegList = new ArrayList<MembInfoReg>();
		try {
			MembInfoRegImporter membInfoRegImporter = new MembInfoRegImporter();
			List<MembInfoReg> MembInfoRegList = membInfoRegImporter.getFileImporterList(upload,
					uploadFileName);

			for (MembInfoReg membInfoReg : MembInfoRegList) {

				// 会员类型和级别检查
				if (CommonHelper.isNotEmpty(membInfoReg.getMembClass())) {
					if (!membClassMap.keySet().contains(membInfoReg.getMembClass())) {
						// throw new BizException("会员类型[" + membInfoReg.getMembClass() + "]不存在。");
						membInfoReg.setAddress("会员类型[" + membInfoReg.getMembClass() + "]不存在。");
						uninsertMembInfoRegList.add(membInfoReg);
						continue;
					} else {
						if (CommonHelper.isNotEmpty(membInfoReg.getMembLevel())) {
							if (!StringUtils.isNumeric(membInfoReg.getMembLevel())) {
								// throw new BizException("会员级别[" + membInfoReg.getMembClass() + "]不是整数。");
								membInfoReg.setAddress("会员级别[" + membInfoReg.getMembClass() + "]不是整数。");
								uninsertMembInfoRegList.add(membInfoReg);
								continue;
							} else if (Integer.valueOf(membInfoReg.getMembLevel()) > Integer
									.valueOf(membClassMap.get(membInfoReg.getMembClass()))) {
								// throw new BizException("会员级别[" +
								// membClassMap.get(membInfoReg.getMembClass()) +
								// "]不能超过最大级别["+membInfoReg.getMembLevel()+"]。");
								membInfoReg.setAddress("会员级别[" + membInfoReg.getMembLevel() + "]不能超过最大级别["
										+ membClassMap.get(membInfoReg.getMembClass()) + "]。");
								uninsertMembInfoRegList.add(membInfoReg);
								continue;
							}
						} else {
							membInfoReg.setMembLevel("0");// 默认无级别
						}
					}
				} else {
					membInfoReg.setAddress("会员类型不能为空。");
					uninsertMembInfoRegList.add(membInfoReg);
					continue;
				}

				// 检查数据正确性
				if (CommonHelper.isNotEmpty(membInfoReg.getCredType())) {
					if (CertType.ALL.get(membInfoReg.getCredType()) == null) {
						membInfoReg.setAddress("证件类型代码[" + membInfoReg.getCredType() + "]不正确。");
						uninsertMembInfoRegList.add(membInfoReg);
						continue;
					}
					if (CommonHelper.isEmpty(membInfoReg.getCredNo())) {
						membInfoReg.setAddress("证件类型代码[" + membInfoReg.getCredType() + "]不为空时,证件号不能为空。");
						uninsertMembInfoRegList.add(membInfoReg);
						continue;
					}
				}
				if (CommonHelper.isNotEmpty(membInfoReg.getEducation())
						&& EducationType.ALL.get(membInfoReg.getEducation()) == null) {
					membInfoReg.setAddress("学历类型代码[" + membInfoReg.getEducation() + "]不正确。");
					uninsertMembInfoRegList.add(membInfoReg);
					continue;
				}
				if (CommonHelper.isNotEmpty(membInfoReg.getSex())
						&& SexType.ALL.get(membInfoReg.getSex()) == null) {
					membInfoReg.setAddress("性别类型代码[" + membInfoReg.getSex() + "]不正确。");
					uninsertMembInfoRegList.add(membInfoReg);
					continue;
				}
				// 检查生日
				else if (!DateUtil.isValidDate(membInfoReg.getBirthday(), "yyyyMMdd")) {
					membInfoReg.setAddress("生日[" + membInfoReg.getBirthday() + "]输入有误，正确格式为20120101。");
					uninsertMembInfoRegList.add(membInfoReg);
					continue;
				}

				membInfoReg.setMembInfoId(seq);
				membInfoReg.setCardIssuer(user.getBranchNo()); // 发卡机构
				membInfoReg.setUpdateBy(user.getUserId());
				membInfoReg.setInsertTime(new Date());

				membInfoRegDAO.insert(membInfoReg);
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
		return uninsertMembInfoRegList;
	}

	public boolean deleteMembInfoReg(MembInfoReg membInfoReg) throws BizException {
		Assert.notNull(membInfoReg, "删除的会员登记ID不能为空");

		return this.membInfoRegDAO.delete(membInfoReg) > 0;
	}

	public boolean modifyMembInfoReg(MembInfoReg membInfoReg, String sessionUserCode) throws BizException {
		Assert.notNull(membInfoReg, "更新的会员登记对象不能为空");

		membInfoReg.setUpdateBy(sessionUserCode);
		membInfoReg.setUpdateTime(new Date());

		return this.membInfoRegDAO.update(membInfoReg) > 0;
	}

	public boolean isExistMembInfoReg(MembInfoReg membInfoReg) throws BizException {
		Assert.notNull(membInfoReg, "会员登记编号不能为空");

		return this.membInfoRegDAO.findByPk(membInfoReg) != null;
	}

	public List<MembClassDef> loadMtClass(String cardIssuer) throws BizException {
		Assert.notEmpty(cardIssuer, "机构编号不能为空");
		List<MembClassDef> membClassDefList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (!cardIssuer.equals(Constants.CENTER)) {
			params.put("cardIssuer", cardIssuer);
		}
		params.put("status", "10"); // 审核通过
		membClassDefList = membClassDefDAO.findByCardIssuer(params);
		sordMembClassDefList(membClassDefList);// 排序
		for (MembClassDef membClassDef : membClassDefList) {
			membClassDef.setClassName(membClassDef.getMembClass() + "-" + membClassDef.getClassName());// 优化显示
		}
		return membClassDefList;
	}

	public long addMembAppointRegBat(MembAppointReg membAppointReg, List<MembInfoReg> membInfoRegList,
			UserInfo user) throws BizException {
		MembAppointDetailReg membAppointDetailReg = null;
		List<CardInfo> cardInfoList = this.cardInfoDAO.getCardList(membAppointReg.getStartCardId(),
				membAppointReg.getEndCardId());

		checkCardInfo(cardInfoList, user);

		membAppointRegDAO.insert(membAppointReg);

		for (int i = 0; i < cardInfoList.size(); i++) {
			CardInfo cardInfo = cardInfoList.get(i);
			MembInfoReg membInfoReg = membInfoRegList.get(i);
			membAppointDetailReg = new MembAppointDetailReg();
			membAppointDetailReg.setCardId(cardInfo.getCardId());
			membAppointDetailReg.setCardIssuer(user.getBranchNo());
			membAppointDetailReg.setMembAppointRegId(membAppointReg.getMembAppointRegId());
			membAppointDetailReg.setMembInfoId(membInfoReg.getMembInfoRegId());
			membAppointDetailReg.setSaleBatchId(membAppointReg.getSaleBatchId());
			membAppointDetailReg.setStatus(RegisterState.WAITEDEAL.getValue());
			membAppointDetailReg.setUpdateBy(user.getUserId());
			membAppointDetailReg.setUpdateTime(new Date());
			membAppointDetailRegDAO.insert(membAppointDetailReg);
		}

		return MsgSender
				.sendMsg(MsgType.MEMB_APPOINT, membAppointReg.getMembAppointRegId(), user.getUserId());
	}

	public void checkCardInfo(List<CardInfo> cardInfoList, UserInfo user) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < cardInfoList.size(); i++) {
			CardInfo cardInfo = cardInfoList.get(i);
			Assert.equals(cardInfo.getCardIssuer(), user.getBranchNo(), "第[" + (i + 1) + "]张卡，卡号["
					+ cardInfo.getCardId() + "]不是您所在的发卡机构的卡，不能关联！");

			params.put("cardId", cardInfo.getCardId());
			List<MembReg> membRegList = membRegDAO.findList(params);
			Assert.notTrue(CommonHelper.checkIsNotEmpty(membRegList), "第[" + (i + 1) + "]张卡，卡号["
					+ cardInfo.getCardId() + "]已关联!");
		}
	}

	public void sordMembClassDefList(List<MembClassDef> membClassDefList) {
		if (CommonHelper.checkIsNotEmpty(membClassDefList)) {
			Collections.sort(membClassDefList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					MembClassDef membClassDef1 = (MembClassDef) o1;
					MembClassDef membClassDef2 = (MembClassDef) o2;
					return membClassDef1.getMembClass().compareTo(membClassDef2.getMembClass());
				}
			});
		}
	}

	@Override
	public void export(HttpServletResponse response, Map<String, Object> params) throws IOException {
		response.setContentType("application/csv;charset=gb18030");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("会员信息.csv".getBytes("gb18030"), "ISO-8859-1"));
		StringBuilder title = new StringBuilder();
		title.append("注册编号,客户姓名,性别,年龄,学历,会员类型,会员类型名称");
		title.append(",卡号,发卡机构编号,发卡机构名称,证件类型,证件号码");
		title.append(",联系电话,联系地址,手机号,邮件地址,行业,月收入,生日,登记日期");
		title.append(",可用余额,冻结金额,可用积分");
		response.getWriter().println(title);
		int pageNumber = 1;
		int pageSize = 5000;
		Paginater paginater = null;
		do {
			paginater = membRegDAO.findMembRegToExport(params, pageNumber, pageSize);
			for (Object obj : paginater.getData()) {
				MembReg membReg = (MembReg) obj;
				StringBuilder line = new StringBuilder();
				line.append(membReg.getMembRegId());
				line.append(",\t").append(StringUtils.defaultString(membReg.getCustName()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getSexName()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getAge()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getEducationName()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getMembClass()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getMembClassName()));

				line.append(",\t").append(StringUtils.defaultString(membReg.getCardId()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getCardIssuer()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getBranchName()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getCredTypeName()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getCredNo()));

				line.append(",\t").append(StringUtils.defaultString(membReg.getTelNo()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getAddress()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getMobileNo()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getEmail()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getJob()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getSalary()));
				line.append(",\t").append(StringUtils.defaultString(membReg.getBirthday()));
				line.append(",").append(
						membReg.getRegTime() == null ? "" : gnete.util.DateUtil.formatDate(membReg
								.getRegTime()));

				line.append(",").append(membReg.getAvlbBal() == null ? "" : membReg.getAvlbBal().setScale(2));
				line.append(",").append(membReg.getFznAmt() == null ? "" : membReg.getFznAmt().setScale(2));
				line.append(",").append(membReg.getPtAvlb() == null ? "" : membReg.getPtAvlb().setScale(2));

				line.append("\r\n");
				response.getWriter().print(line);
			}
			pageNumber++;
		} while (paginater != null && paginater.getCurrentPage() < paginater.getLastPage());
	}

	
}
