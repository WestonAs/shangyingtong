package gnete.card.timer.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.timer.FlinkTimerTask;
import flink.timer.IntervalEnum;
import flink.util.CommonHelper;
import gnete.card.dao.MessageParamDAO;
import gnete.card.entity.MessageParam;
import gnete.card.entity.PointAccReg;
import gnete.card.service.PointAccService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.BizException;

/**
 *  <p>潮州移动文件处理定时器任务</p>
 */
@Service("pointAccFileProcessDayTimerTask")
public class PointAccFileProcessDayTimerTask extends FlinkTimerTask{
    @Autowired
	private PointAccService pointAccFileService;
	
	@Autowired
	private MessageParamDAO messageParamDAO;

	@Override
	protected IntervalEnum getIntervalEnum() {
		return IntervalEnum.DAY;
	}

	@Override
	protected String getTaskDescribe() {
		return "定时处理积分赠送及账户变动";
	}
	
	@Override
	protected long getCheckScheduleSleepTime() {		
		return 5 * 6 * DEFAULT_SLEEP_TIME; 
	}

	@Override
	protected Date getTaskFirstTime() throws Exception {
		String executeTime = SysparamCache.getInstance().getPointAccReadTime();
		return CommonHelper.getFormatDateTime(executeTime);
	}
	
	@Override
	protected void processTask() throws BizException {
		//1.1 取得昨天的日期
		String date = pointAccFileService.getYesterdayDate();
		
		//1.2 取得FTP积分充值及账户变动文件根目录的机构代码文件列表
		String[] branchList = pointAccFileService.getFileList();
		
		for(String branchCode : branchList) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardIssuer", branchCode);
			List<MessageParam> messageParamList = this.messageParamDAO.getMessageParamList(params);
				
			//1.3 如果发卡机构没有配置短信参数则不处理
			if(messageParamList.isEmpty()){
				continue;
			}
			
			try {
				//1.4 处理积分赠送及文件变动
				List<PointAccReg> reg = pointAccFileService.readPointAccFile(branchCode, date);
				if (reg.size() != 0) {
					for(PointAccReg pointAccReg : reg){
						pointAccReg.setEventCode(messageParamList.get(0).getEventCode());
						pointAccFileService.addMsgAndPointAcc(pointAccReg);
					}
					
					logger.warn("读取积分赠送及账户变动文件成功！");
				}
				//pointAccFileService.processPointAccFile(branchCode, date);
			}catch(Exception ex) {
				logger.error("读取积分赠送及账户变动失败，原因[" + ex.getMessage()+"]" );
			}
		}
		
	}

}
