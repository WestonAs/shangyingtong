package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 用户证书状态
 * 
 * @author aps-lih
 * @modified : aps-zbw
 */
public class UserCertificateState extends AbstractState {
	public static final Map<String, UserCertificateState> ALL = new LinkedHashMap<String, UserCertificateState>();

	public static final UserCertificateState UNBOUND = new UserCertificateState("未指派", "00");
	public static final UserCertificateState ASSIGNED = new UserCertificateState("已指派", "01");
	public static final UserCertificateState BOUND = new UserCertificateState("已绑定", "02");
	public static final UserCertificateState BREAK = new UserCertificateState("已暂停", "03");
	//public static final UserCertificateState REMOVE = new UserCertificateState("已删除", "04");

	protected UserCertificateState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static UserCertificateState valueOf(String value) {
		UserCertificateState type = (UserCertificateState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("证书绑定状态错误！");
		}

		return type;
	}

	public static List<UserCertificateState> getCertificateStateList() {
		return Arrays.asList(UNBOUND, BOUND, BREAK);
	}

	public static List<UserCertificateState> getMidCertificateStateList() {
		return Arrays.asList(UNBOUND, ASSIGNED, BOUND, BREAK);
	}

	public static List<UserCertificateState> getAllCertificateStateList() {
		return new ArrayList<UserCertificateState>(ALL.values());
	}
}
