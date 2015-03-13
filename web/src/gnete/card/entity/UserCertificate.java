package gnete.card.entity;

import gnete.card.entity.state.UserCertificateState;
import gnete.card.entity.state.CertificateState;
import gnete.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @Project: MyCard
 * @File: UserCertificate.java
 * @See:
 * @description： 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class UserCertificate extends UserCertificateKey implements java.lang.Cloneable,java.io.Serializable {	
		
	private static final long serialVersionUID = 538722882906401650L;

	private String state;

	private String useState;

	private Date updateTime;

	private String updateUser;

	private String branchCode; // 所在机构I编号(均来自机构证书表)
		
    private String uploadName; // 证书上传名称

	private String fileName; // 证书下载(保存)文件名称

	private String endDate; // 证书过期时间

	private String update; // 更新时间
	
	private String deptId;

	private String branchName;
	
	private String bndBranchName;
	
	/**
	 * @description: <li>增加绑定的机构编号</li>
	 */
	private String bndBranch; // 证书绑定机构(可以是商户也可以是机构)
	
	private String showBound = Boolean.FALSE.toString();     //控制绑定

	private String showUnBound = Boolean.FALSE.toString();  //控制解除绑定
	
	private String showChgBound = Boolean.FALSE.toString();  //控制变更绑定
	
	private String showAssign = Boolean.FALSE.toString();   //控制指派
	
	private String showRecycle = Boolean.FALSE.toString();  //控制回收
	
	private String showRemove = Boolean.FALSE.toString();   //控制删除
	
	public UserCertificate() {
		
	}
	
	public UserCertificate(String dnNo, String seqNo, String startDate) {
		super(dnNo, seqNo, startDate);
	}
	
	/** 
	 * 获得离失效日期的天数，负数表示已经失效 
	 */
	public long getDaysToExpire() {
		Calendar now = new GregorianCalendar();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return (DateUtil.parseDate("yyyyMMdd", this.endDate).getTime() - now.getTimeInMillis())
				/ (24 * 60 * 60 * 1000);
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getStateName() {
		return CertificateState.ALL.get(this.state) == null ? "" : CertificateState.valueOf(this.state).getName();
	}

	public String getUseStateName() {
		return UserCertificateState.ALL.get(this.useState) == null ? "" : UserCertificateState.valueOf(this.useState).getName();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	public String getShowBound() {
		return showBound;
	}

	public void setShowBound(String showBound) {
		this.showBound = showBound;
	}

	public String getShowUnBound() {
		return this.showUnBound;
	}

	public void setShowUnBound(String showUnBound) {
		this.showUnBound = showUnBound;
	}
	
	public String getShowRecycle() {
		return showRecycle;
	}

	public void setShowRecycle(String showRecycle) {
		this.showRecycle = showRecycle;
	}
	
	public String getShowRemove() {
		return showRemove;
	}

	public void setShowRemove(String showRemove) {
		this.showRemove = showRemove;
	}
	
	public String getShowAssign() {
		return showAssign;
	}

	public void setShowAssign(String showAssign) {
		this.showAssign = showAssign;
	}

	public String getShowChgBound() {
		return showChgBound;
	}

	public void setShowChgBound(String showChgBound) {
		this.showChgBound = showChgBound;
	}

	public String getBndBranch() {
		return bndBranch;
	}

	public void setBndBranch(String bndBranch) {
		this.bndBranch = bndBranch;
	}

	public Object clone() {
		UserCertificate userCertificate = null;
		try {
			userCertificate = (UserCertificate) super.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return userCertificate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(super.getSeqNo()).append(super.getDnNo()).toHashCode();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBndBranchName() {
		return bndBranchName;
	}

	public void setBndBranchName(String bndBranchName) {
		this.bndBranchName = bndBranchName;
	}



}