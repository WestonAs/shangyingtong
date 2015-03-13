package gnete.card.msg.convert;

import gnete.card.entity.TestLeave;
import gnete.card.msg.MsgConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TestLeaveConverter implements MsgConverter {

	public String toMessage(Object obj) {
		StringBuffer sb = new StringBuffer(128);
		TestLeave leave = (TestLeave) obj;
		
		// 组装报文，根据定长报文格式组装
		sb.append(StringUtils.leftPad(leave.getId(), 12, ' '));
		sb.append(StringUtils.leftPad(leave.getName(), 30, ' '));
		
		return sb.toString();
	}

}
