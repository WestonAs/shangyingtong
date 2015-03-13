package gnete.card.web.user;

import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.UserCertificateDAO;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserCertificateKey;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.UserService;
import gnete.card.web.BaseAction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * UserCertificateAction.java.
 * @deprecated
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class UserCertAction extends BaseAction {

	@Autowired
	private UserCertificateDAO userCertificateDAO;

	private Paginater page;
	
	private UserCertificate userCertificate;
	
	@Autowired
	private UserService userService;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (userCertificate != null) {
			params.put("userId", userCertificate.getUserId());
			params.put("dnNo", userCertificate.getDnNo());
		}
		this.page = this.userCertificateDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception{
		this.userCertificate = (UserCertificate) this.userCertificateDAO.findByPk(
				new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(), 
						userCertificate.getStartDate(), userCertificate.getUserId()));
		return DETAIL;
	}
	
	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		return ADD;
	}
	
	// 新增对象操作
	public String add() throws Exception {
		// 调用service业务接口
		this.userService.addUserCert(this.userCertificate, this.getSessionUserCode());
		
		String msg = LogUtils.r("添加用户证书[{0}, {1}, {2}, {3}]成功", userCertificate.getDnNo(), userCertificate.getSeqNo(), 
				userCertificate.getStartDate(), userCertificate.getUserId());
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.ADD);
		
		return SUCCESS;
	}
	
	public String showModify() throws Exception {
		this.userCertificate = (UserCertificate) this.userCertificateDAO.findByPk(
				new UserCertificateKey(userCertificate.getDnNo(), userCertificate.getSeqNo(), 
				userCertificate.getStartDate(), userCertificate.getUserId()));
		return MODIFY;
	}
	
	public String modify() throws Exception {
		this.userService.modifyUserCert(this.userCertificate, this.getSessionUserCode());

		String msg = LogUtils.r("修改用户证书[{0}, {1}, {2}, {3}]成功", userCertificate.getDnNo(), userCertificate.getSeqNo(), 
				userCertificate.getStartDate(), userCertificate.getUserId());
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		String dnNo = request.getParameter("dnNo");
		String seqNo = request.getParameter("seqNo");
		String startDate = request.getParameter("startDate");
		String userId = request.getParameter("userId");
		this.userService.cancelUserCert(new UserCertificateKey(dnNo, seqNo, startDate, userId), this.getSessionUserCode());
		
		String msg = LogUtils.r("注销用户证书[{0}, {1}, {2}, {3}]成功", dnNo, seqNo, startDate, userId);
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public String activate() throws Exception {
		String dnNo = request.getParameter("dnNo");
		String seqNo = request.getParameter("seqNo");
		String startDate = request.getParameter("startDate");
		String userId = request.getParameter("userId");
		this.userService.activeUserCert(new UserCertificateKey(dnNo, seqNo, startDate, userId), this.getSessionUserCode());
		
		String msg = LogUtils.r("生效用户证书[{0}, {1}, {2}, {3}]成功", dnNo, seqNo, startDate, userId);
		this.addActionMessage("/pages/user/list.do", msg);
		this.log(msg, UserLogType.UPDATE);
		return SUCCESS;
	}
	
	public UserCertificate getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(UserCertificate userCertificate) {
		this.userCertificate = userCertificate;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

}
