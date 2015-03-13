package gnete.card.timer.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.CommonHelper;
import gnete.card.clear2Pay.IClear2PayBankFileProcess;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

/**
 * <p>网银通划账文件生成定时器任务</p>
 * <ul>
 * <li>1. 每天产生针对网银通的划付文件</li> 
 * <li>2. 产生的文件将面向当前录入的开户行</li> 
 * <li>3. 将该文件传入根据开户行的目录下</li>
 * </ul>
 */
@Service("clear2PayFileGenerateTimeTask")
public class Clear2PayFileGenerateTimeTask extends FlinkTimerTask {
	@Autowired
	@Qualifier("clear2PayBankFileProcess")
	private IClear2PayBankFileProcess clear2PayBankFileProcess;

	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.DAY;
	}

	@Override
	protected String getTaskDescribe() {
		return "网银通转账支付文件生成";
	}

	@Override
	protected Date getTaskFirstTime() throws Exception {
		String executeTime = SysparamCache.getInstance().getClear2PayCheckTime();
		return CommonHelper.getFormatDateTime(executeTime);
	}

	@Override
	protected void processTask() throws BizException {
		String rmaDate = CommonHelper.getCommonDateFormatStr();
		
		try {
			boolean result1 = this.clear2PayBankFileProcess.processClear2PayInfoBankBranchFile(rmaDate);
			
			logger.info("======生成网银通划付(机构划付给商户)文件,日期[{}]处理结果[{}]", rmaDate , result1);
		} catch (Exception e) {
			logger.error("生成网银通划付(机构划付给商户)文件,日期[{}]发生异常[{}]。", rmaDate, e);
		}
		
		try {
			boolean result1 = this.clear2PayBankFileProcess.processClear2PayInfoBankSingleProductFile(rmaDate);
			
			logger.info("======生成网银通网银通单机产品划账文件,日期[{}]处理结果[{}]", rmaDate, result1);
		} catch (Exception e) {
			logger.error("生成网银通网银通单机产品划账文件,日期[{}]发生异常[{}]。", rmaDate, e);
		}
	}
}
