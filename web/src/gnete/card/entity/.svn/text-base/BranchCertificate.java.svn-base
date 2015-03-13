package gnete.card.entity;

import java.util.Date;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @Project: MyCard
 * @File: BranchCertificate.java
 * @See:
 * @description： <li>用户数字证书的录入,描述数字证书的信息</li> <li>参考数据库文档中----7.4.2 机构/商户证书表</li>
 * 
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class BranchCertificate extends BranchCertificateKey implements java.lang.Cloneable {	
	public BranchCertificate() {
		super();
	}
	
	public BranchCertificate(String dnNo, String seqNo, String startDate) {
		super(dnNo, seqNo, startDate);
	}

	private String endDate; // 证书结束日期

	private String state; // 证书状态(如果日期在开通和结束之间则视为生效)

	private String useState; // 证书绑定状态

	private String branchCode; // 证书(录入或分配)所在机构

	private String deptId; // 证书(录入或分配)所在部门

	private String fileName; // 证书的文件名

	private Date updateTime; // 录入时间

	private String updateUser; // 录入用户

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Object clone() {
		BranchCertificate branchCertificate = null;
		try {
			branchCertificate = (BranchCertificate) super.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return branchCertificate;
	}

	// --用于构造HashMap时充当KEY的散列值
	public int hasCode() {
		return new HashCodeBuilder(17, 37).append(super.getDnNo()).append(super.getSeqNo()).append(
				super.getStartDate()).toHashCode();
	}

}
