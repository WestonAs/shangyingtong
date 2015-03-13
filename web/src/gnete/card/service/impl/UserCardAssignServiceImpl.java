package gnete.card.service.impl;

import java.util.Map;
import java.util.List;
import java.util.Collection;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.MakeCardPersonDAO;
import gnete.card.dao.UserInfoDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.MakeCardPerson;
import gnete.card.entity.MakeCardPersonKey;
import gnete.card.entity.state.CommonState;
import gnete.card.service.IUserCardAssignService;
import gnete.etc.BizException;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @Project: MyCard
 * @File: UserCardAssignImpl.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Service("userCardAssignService")
public class UserCardAssignServiceImpl implements IUserCardAssignService {

	@Autowired
	private MakeCardPersonDAO makeCardPersonDAO;

	@Autowired
	private UserInfoDAO userInfoDAO;

	private static final int CHECK_ONLY_SIZE = 1;

	public Object[] getUserCardAssignResult(Map params, int pageNumber, int pageSize) throws BizException {

		Object[] result = new Object[] {};

		try {
			Paginater cardPersonPage = this.makeCardPersonDAO.findMakeCardPerson(params, pageNumber, pageSize);

			boolean checkHasPerson = checkHasCardPerson(cardPersonPage);

			result = new Object[] { cardPersonPage, !(checkHasPerson) };
		} catch (DataAccessException ex) {
			throw new BizException("查询制卡人异常,原因[" + ex.getMessage() + "]");
		}

		return result;
	}

	public List<UserInfo> getUserCardInfoList(String branchCode) throws BizException {
		try {
			List<UserInfo> userCardInfoList = this.userInfoDAO.findByBranch(branchCode, null);

			return new CopyOnWriteArrayList<UserInfo>(userCardInfoList);
		} catch (DataAccessException ex) {
			throw new BizException("查询制卡机构[" + branchCode + "]用户列表异常，原因[" + ex.getMessage() + "]");
		}
	}

	public Object[] getUserCardModifyResult(MakeCardPerson cardPerson) throws BizException {
		checkCardPerson(cardPerson, CommonState.NORMAL.getValue());

		String branchNo = cardPerson.getBranchNo();

		List<UserInfo> userCardInfoList = getUserCardInfoList(branchNo);

		if (CommonHelper.checkIsEmpty(userCardInfoList)) {
			throw new BizException("制卡厂商机构[" + branchNo + "]用户列表为空,无法进行编辑!");
		}

		if (checkCardPersonOnly(userCardInfoList, cardPerson)) {
			return new Object[] {};
		}

		return new Object[] { cardPerson, getModifyUserCardInfoList(userCardInfoList, cardPerson) };
	}

	public boolean processUserCardPersonAdd(String userId, UserInfo loginUser) throws BizException {
		if (CommonHelper.checkIsEmpty(userId)) {
			throw new BizException("制卡厂商选定用户不能为空!");
		}

		boolean checkHasPerson = checkHasCardPerson(loginUser.getBranchNo(), CommonState.NORMAL.getValue(), userId);

		if (checkHasPerson) {
			throw new BizException("您选择的用户[" + userId + "]已经选定,无法再次选定!");
		}

		MakeCardPerson cardPerson = getBoundMakeCardPerson(userId, loginUser);

		try {
			this.makeCardPersonDAO.insert(cardPerson);
		} catch (DataAccessException ex) {
			throw new BizException("制卡厂商[" + loginUser.getBranchNo() + "]选定用户[" + userId + "]处理失败，原因["
					+ ex.getMessage() + "]");
		}

		return true;
	}

	public boolean processUserCardPersonModify(MakeCardPerson cardPerson, String userId, UserInfo loginUser)
			throws BizException {
		checkCardPerson(cardPerson, CommonState.NORMAL.getValue());

		if (CommonHelper.checkIsEmpty(userId)) {
			throw new BizException("制卡厂商选定用户不能为空!");
		}

		try {			
			MakeCardPerson _cardPerson = getStateMakeCardPerson(cardPerson, CommonState.DISABLE.getValue(), loginUser);
			
			if(checkHasCardPerson(_cardPerson.getBranchNo(),_cardPerson.getState(),_cardPerson.getUserId())) {
				delMakeCardPerson(_cardPerson.getBranchNo(),_cardPerson.getState(),_cardPerson.getUserId());
			}
			this.makeCardPersonDAO.update(_cardPerson);
			
			
			MakeCardPerson newCardPerson = getModifyMakeCardPerson(cardPerson, userId, loginUser);
			
			this.makeCardPersonDAO.insert(newCardPerson);
			
		} catch (DataAccessException ex) {
			throw new BizException("制卡厂商[" + cardPerson.toString() + "]编辑失败,原因[" + ex.getMessage() + "]");
		}

		return true;
	}
	
	
	public boolean processUserCardPersonState(MakeCardPerson cardPerson, String state, UserInfo loginUser)
			throws BizException {
		checkCardPerson(cardPerson);

		try {
			//如果原更新的记录存在
			if(checkHasCardPerson(cardPerson.getBranchNo(),state,cardPerson.getUserId())) {				
				//删除掉原记录
				delMakeCardPerson(cardPerson.getBranchNo(),state,cardPerson.getUserId());
			} 
			
			MakeCardPerson _cardPerson = getStateMakeCardPerson(cardPerson, state, loginUser);
			
			this.makeCardPersonDAO.update(_cardPerson);
			
		} catch (DataAccessException ex) {
			throw new BizException("制卡厂商[" + cardPerson.toString() + "]状态调整失败,原因[" + ex.getMessage() + "]");
		}

		return true;
	}
	
	
	

	public boolean processUserCardPersonDel(MakeCardPerson cardPerson) throws BizException {
		checkCardPerson(cardPerson, CommonState.NORMAL.getValue());

		try {
			return delMakeCardPerson(cardPerson.getBranchNo(), cardPerson.getState(), cardPerson.getUserId());
		} catch (DataAccessException ex) {
			throw new BizException("删除制卡厂商选择人信息[" + cardPerson.toString() + "]失败,原因[" + ex.getMessage() + "]");
		}

	}

	// ------------------------------------------------获取制卡人信息----------------------------------------------------
	private boolean checkHasCardPerson(Paginater cardPersonPage) {
		Collection<MakeCardPerson> cardPersonList = cardPersonPage.getData();

		if (CommonHelper.checkIsEmpty(cardPersonList)) {
			return false;
		}

		for (MakeCardPerson cardPerson : cardPersonList) {
			if (cardPerson.getState().equals(CommonState.NORMAL.getValue())) {
				return true;
			}
		}

		return false;
	}

	// ------------------------------------------------编辑制卡人信息-----------------------------------------------------------
	private boolean checkCardPersonOnly(List<UserInfo> userCardInfoList, MakeCardPerson cardPerson) {
		if (userCardInfoList.size() == CHECK_ONLY_SIZE) {
			UserInfo userInfo = userCardInfoList.get(0);

			return userInfo.getUserId().equals(cardPerson.getUserId());
		}

		return false;
	}

	private List<UserInfo> getModifyUserCardInfoList(List<UserInfo> userCardInfoList, MakeCardPerson cardPerson) {
		String checkUserId = cardPerson.getUserId();

		for (int i = 0, ln = userCardInfoList.size(); i < ln; i++) {
			UserInfo userInfo = userCardInfoList.get(i);

			if (userInfo.getUserId().equals(checkUserId)) {
				synchronized (userCardInfoList) {
					userCardInfoList.remove(i);
					break;
				}

			}
		}

		return userCardInfoList;
	}

	public MakeCardPerson getSelectMakeCardPerson(MakeCardPerson cardPerson) throws BizException {
		checkCardPerson(cardPerson);

		try {
			return findMakeCardPerson(cardPerson.getBranchNo(), cardPerson.getState(), cardPerson.getUserId());
		} catch (DataAccessException ex) {
			throw new BizException("查询制卡厂商选择用户异常，原因[" + ex.getMessage() + "]");
		}

	}

	// ---------------------------------------------检查制卡人状态-------------------------------------------------------------
	private void checkCardPerson(MakeCardPerson cardPerson) throws BizException {
		boolean check = (cardPerson != null) && (cardPerson.getBranchNo() != null) && (cardPerson.getUserId() != null)
				&& (cardPerson.getState() != null);

		if (!check) {
			throw new BizException("制卡厂商选择参数不合法!");
		}
	}

	private void checkCardPerson(MakeCardPerson cardPerson, String state) throws BizException {
		checkCardPerson(cardPerson);

		String _state = cardPerson.getState();

		if (!_state.equals(state)) {
			throw new BizException("制卡厂商状态参数不合法,原状态为[" + _state + "],检查状态为[" + state + "]");
		}
	}

	// --------------------------------------------制卡人信息构造-------------------------------------------------------------
	private MakeCardPerson getBoundMakeCardPerson(String userId, UserInfo loginUser) {
		MakeCardPerson cardPerson = new MakeCardPerson(loginUser.getBranchNo(), CommonState.NORMAL.getValue(), userId);

		cardPerson.setUpdateUser(loginUser.getUserId());
		cardPerson.setUpdateTime(CommonHelper.getCalendarDate());

		return cardPerson;
	}

	private MakeCardPerson getModifyMakeCardPerson(MakeCardPerson cardPerson, String userId, UserInfo loginUser) {
		MakeCardPerson _cardPerson = (MakeCardPerson) cardPerson.clone();

		_cardPerson.setUserId(userId);
		_cardPerson.setUpdateUser(loginUser.getUserId());
		_cardPerson.setUpdateTime(CommonHelper.getCalendarDate());

		return _cardPerson;
	}

	private MakeCardPerson getStateMakeCardPerson(MakeCardPerson cardPerson, String state, UserInfo loginUser) {
		MakeCardPerson _cardPerson = (MakeCardPerson) cardPerson.clone();
		_cardPerson.setUpdateState(state);
		_cardPerson.setUpdateTime(CommonHelper.getCalendarDate());
		_cardPerson.setUpdateUser(loginUser.getUserId());

		return _cardPerson;

	}

	// ---------------------------------------------------制卡人信息查询----------------------------------------------------

	private boolean checkHasCardPerson(String branchNo, String state, String userId) throws DataAccessException {
		MakeCardPersonKey cardPersonKey = new MakeCardPersonKey(branchNo, state, userId);

		return (makeCardPersonDAO.findByPk(cardPersonKey) != null);
	}

	private MakeCardPerson findMakeCardPerson(String branchNo, String state, String userId) throws DataAccessException {
		MakeCardPersonKey cardPersonKey = new MakeCardPersonKey(branchNo, state, userId);

		return (MakeCardPerson) makeCardPersonDAO.findByPk(cardPersonKey);
	}

	// ----------------------------------------------------删除制卡人---------------------------------------------------------
	private boolean delMakeCardPerson(String branchNo, String state, String userId) throws DataAccessException {
		MakeCardPersonKey cardPersonKey = new MakeCardPersonKey(branchNo, state, userId);

		this.makeCardPersonDAO.delete(cardPersonKey);

		return true;
	}

}
