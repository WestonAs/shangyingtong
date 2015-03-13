package gnete.card.event;

import flink.event.FlinkAppEvent;
import flink.event.FlinkOperateEnum;
import flink.event.WaitsInfoAppEvent;
import gnete.card.service.WaitsinfoService;
import gnete.etc.BizException;

import flink.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 
 * @Project: Card
 * @File: WaitsInfoAppEventListener.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-18
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("waitsInfoEventListener")
public class WaitsInfoAppEventListener extends flink.event.impl.AbstractFlinkAppEventListener {

	@Autowired
	@Qualifier("waitsinfoService")
	private WaitsinfoService waitsinfoService;

	@Override
	protected boolean checkApplicationEvent(FlinkAppEvent event) {
		return (event instanceof WaitsInfoAppEvent);
	}

	@Override
	protected void processApplicationEvent(FlinkAppEvent event) {
		WaitsInfoAppEvent waitsInfoAppEvent = (WaitsInfoAppEvent) event;

		FlinkOperateEnum operateEnum = waitsInfoAppEvent.getFlinkOperateEnum();

		// 1.1 如果是添加命令表的动作
		if (operateEnum.equals(FlinkOperateEnum.ADD)) {
			try {
				// 1.2 进行命令处理
				Long[] cmdsReturn = waitsinfoService.addCmds(waitsInfoAppEvent.getWaitsInfoList());
				if (checkEmptyCmdsReturn(cmdsReturn)) {
					return;
				}
				// 1.3 如果返回不为空则打印记录
				logger.info("命令处理成功,返回结果" + CommonHelper.filterArray2Str(cmdsReturn));
			} catch (BizException ex) {
				// 否则记录错误日志
				logger.error("命令表监听事件处理异常,操作[" + operateEnum + "],原因[" + ex.getMessage() + "]");
			}
		}
	}

	private boolean checkEmptyCmdsReturn(Long[] cmdsReturn) {
		return (cmdsReturn == null) || (cmdsReturn.length == 0);
	}

}
