package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 
  * @Project: MyCard
  * @File: CertificateState.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-7
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class CertificateState extends AbstractState{
	public static final Map ALL = new HashMap();

	public static final CertificateState VALID = new CertificateState("生效", "00");
	public static final CertificateState INVALID = new CertificateState("失效", "01");	
	
	@SuppressWarnings("unchecked")
	protected CertificateState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CertificateState valueOf(String value) {
		CertificateState type = (CertificateState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("证书状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
