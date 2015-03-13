package flink.event;

import java.util.List;

/**
 * <p>命令表业务事件类(用于包装基本的命令表参数{String msgType, Long refId, String userCode})</p>
 * <p>支持命令表中批量的处理(即多条命令，用于前后台交互处理)</p>
 * <p>注意其中传入的为数组形式</p>
 * @Project: Card
 * @File: WaitsInfoAppEvent.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-18
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class WaitsInfoAppEvent extends FlinkAppEvent {
	private static final long serialVersionUID = 568792012374209182L;

	private final List<Object[]> waitsInfoList;

	public WaitsInfoAppEvent(Object source, FlinkOperateEnum operateEnum, List<Object[]> waitsInfoList) {
		super(source, operateEnum);
		this.waitsInfoList = waitsInfoList;
	}

	public List<Object[]> getWaitsInfoList() {
		return this.waitsInfoList;
	}
	
	public String toString() {
		return new StringBuilder().append("命令表操作事件,操作[").append(getFlinkOperateEnum()).append("],")
		                           .append("事件处理对象总数[").append(getWaitsInfoList().size()).append("]")
		                           .toString();
	}

}
