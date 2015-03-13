package gnete.card.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.PasswordResetRegDAO;
import gnete.card.entity.CardInfo;
import gnete.card.entity.PasswordResetReg;
import gnete.card.entity.UserInfo;
import gnete.card.entity.state.RegisterState;
import gnete.card.service.CardPasswordService;
import gnete.card.util.CardOprtPrvlgUtil;
import gnete.card.workflow.service.WorkflowService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("cardPasswordService")
public class CardPasswordServiceImpl implements CardPasswordService {
	@Autowired
	private PasswordResetRegDAO passwordResetRegDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;
	@Autowired
	private WorkflowService workflowService;

	public boolean addPasswordResetReg(PasswordResetReg passwordResetReg,
			UserInfo user) throws BizException {
		Assert.notNull(passwordResetReg, "添加的密码重置对象不能为空。");
		
		CardInfo cardInfo = (CardInfo) this.cardInfoDAO.findByPk(passwordResetReg.getCardId());
		Assert.notNull(cardInfo, "卡号[" + passwordResetReg.getCardId() + "]不存在，请重新输入。");
		
		String roleType = user.getRole().getRoleType();
		//检查登录机构是否有权限
		CardOprtPrvlgUtil.checkPrvlg(roleType, user.getBranchNo(), cardInfo, "密码重置");
		
		passwordResetReg.setStatus(RegisterState.WAITED.getValue());
		passwordResetReg.setUpdateBy(user.getUserId());
		passwordResetReg.setUpdateTime(new Date());
		
		passwordResetRegDAO.insert(passwordResetReg);
		
		//启动单个流程
		try {
			this.workflowService.startFlow(passwordResetReg, "passwordResetAdapter",
					Long.toString(passwordResetReg.getPasswordResetRegId()), user);
		} catch (Exception e) {
			throw new BizException("启动密码重置审核流程时发生异常，原因：" + e);
		}
		
		return passwordResetReg.getPasswordResetRegId() != null;
	}

	public boolean deletePasswordResetReg(Long passwordResetRegId)
			throws BizException {
		Assert.notNull(passwordResetRegId, "删除的密码重置对象不能为空。");
		
		return passwordResetRegDAO.delete(passwordResetRegId) > 0;
	}

}
