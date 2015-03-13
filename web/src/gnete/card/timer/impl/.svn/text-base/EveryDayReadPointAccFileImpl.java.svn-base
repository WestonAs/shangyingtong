package gnete.card.timer.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gnete.card.dao.MessageParamDAO;
import gnete.card.entity.MessageParam;
import gnete.card.entity.MessageParamKey;
import gnete.card.entity.MessageReg;
import gnete.card.entity.PointAccReg;
import gnete.card.entity.state.MessageRegStatus;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.service.PointAccService;
import gnete.card.service.mgr.SysparamCache;
import gnete.card.timer.EveryDayTimerHandler;
import gnete.etc.BizException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @File: EveryDayReadPointPresentFileImpl.java
 *
 * @description: 每天定时读取积分赠送及账户变动文件
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-4-12
 */
public class EveryDayReadPointAccFileImpl extends EveryDayTimerHandler{
	
	static final Logger logger = Logger.getLogger(EveryDayReadPointAccFileImpl.class);
	
	@Autowired
	private PointAccService pointAccFileService;
	@Autowired
	private MessageParamDAO messageParamDAO;

	@Override
	protected String executeTime() {
		return SysparamCache.getInstance().getPointAccReadTime();
	}

	@Override
	protected void doTask() throws BizException {
		logger.debug("读取积分赠送及账户变动文件定时器开始执行");
		
		// 取得前一工作日
		String date = pointAccFileService.getYesterdayDate();
		
		// 取得FTP积分充值及账户变动文件根目录的机构代码文件列表
		String[] branchList = pointAccFileService.getFileList();
		
		for(String branchCode : branchList){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardIssuer", branchCode);
			List<MessageParam> messageParamList = this.messageParamDAO.getMessageParamList(params);
				
			// 如果发卡机构没有配置短信参数则不处理
			if(messageParamList.isEmpty()){
				continue;
			}
			try {
				List<PointAccReg> reg = pointAccFileService.readPointAccFile(branchCode, date);
				if (reg.size() != 0) {
					for(PointAccReg pointAccReg : reg){
						pointAccReg.setEventCode(messageParamList.get(0).getEventCode());
						
						MessageParamKey key = new MessageParamKey();
						key.setCardIssuer(pointAccReg.getCardIssuer());
						key.setEventCode(pointAccReg.getEventCode());
						MessageParam messageParam = (MessageParam)this.messageParamDAO.findByPk(key);
						MessageReg messageReg = new MessageReg();
						messageReg.setCardIssuer(messageParam.getCardIssuer());
						messageReg.setEventCode(messageParam.getEventCode());
						messageReg.setMobileNo(messageParam.getMobileNo());
						messageReg.setStatus(MessageRegStatus.WAITE_SENT.getValue());
						messageReg.setUpdateTime(new Date());
						
						String[] fileName = StringUtils.split(pointAccReg.getFileName(), "/");
						if(fileName.length == 0){
							throw new BizException("文件格式不正确。");
						}
						
						String msg = "业务类型: " + PointAccTransYype.valueOf(pointAccReg.getTransType()).getName()
						+ ", 文件名称: " + fileName[fileName.length-1] + ", 导入时间: " + DateUtil.formatDate(pointAccReg.getTime(), "yyyy-MM-dd HH:mm:ss") 
						+ ", 记录总数: " + pointAccReg.getRecordNum()+ "。";
						
						// 积分赠送
						if(PointAccTransYype.POINT_PRESENT.getValue().equals(pointAccReg.getTransType())){
							msg += "总金额为: " + pointAccReg.getAmt() + "。";
						}
						
						messageReg.setMessage(msg);
						pointAccFileService.addMessageReg(messageReg);
						
						pointAccReg.setMessageRegId(messageReg.getMessageRegId());
						pointAccFileService.addPointAccReg(pointAccReg);
					}
					
					logger.warn("读取积分赠送及账户变动文件成功！");
				}
			} catch (BizException e) {
				logger.error("读取积分赠送及账户变动失败，" + e.getMessage());
			}
		}
		
		logger.debug("读取积分赠送及账户变动定时器结束执行");
		
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
