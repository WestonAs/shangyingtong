package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CertType.java
 *
 * @description: 证件类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-27
 */
public class CertType extends AbstractType {
	
	public static final Map<String, CertType> ALL = new HashMap<String, CertType>();
	
	/**
	 * 证件类型
	 */
	public static final CertType ID_CARD = new CertType("身份证","01");
	public static final CertType PASSPROT = new CertType("护照","02");
	public static final CertType DIRVER_LICENSE = new CertType("驾驶执照","03");
	public static final CertType HOME_VISIT_PERMIT = new CertType("回乡证(港澳台)","04");
	public static final CertType MILITARY_IDENTIFICATION = new CertType("军官证","05");
	public static final CertType HOUSEHOLD_REGISTER = new CertType("户口本","06");
	public static final CertType ENTERPRISE_ORGANIZATION_CODE = new CertType("企业组织机构代码","07");
	public static final CertType OTHER_CERT = new CertType("其他","09");
	public static final CertType STUDENT_IDENTITY_CARD = new CertType("学生证","10");
	
	protected CertType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CertType valueOf(String value) {
		CertType cert = (CertType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	@SuppressWarnings("unchecked")
	public static List<CertType> getAll(){
		return getValueOrderedList(CertType.ALL);
	}
}
