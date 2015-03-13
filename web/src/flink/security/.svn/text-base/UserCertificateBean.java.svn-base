package flink.security;

import java.security.cert.Certificate;

/**
 * <p>证书实体及其用户状态绑定类</p>
 * <p>保存用户对应证书使用状态和关联的正式实体</p>
 * @Project: Card
 * @File: UserCertificateBean.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class UserCertificateBean implements java.lang.Cloneable{

	private String useState;

	private Certificate certificate;

	public UserCertificateBean() {

	}

	public UserCertificateBean(String useState, Certificate certificate) {
		this.useState = useState;
		this.certificate = certificate;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public boolean checkInValid() {
		return (this.useState == null) || (this.certificate == null);
	}

	public Object clone() {
		UserCertificateBean userCertificateBean = null;
		try {
			userCertificateBean = (UserCertificateBean) super.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return userCertificateBean;
	}

}
