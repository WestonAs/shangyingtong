package gnete.card.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.util.DateUtil;
import gnete.card.dao.MessageParamDAO;
import gnete.card.dao.MessageRegDAO;
import gnete.card.dao.PointAccRegDAO;
import gnete.card.entity.MessageParam;
import gnete.card.entity.MessageParamKey;
import gnete.card.entity.MessageReg;
import gnete.card.entity.PointAccReg;
import gnete.card.entity.state.MessageRegStatus;
import gnete.card.entity.state.PointAccState;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.msg.MsgSender;
import gnete.card.msg.MsgType;
import gnete.card.service.IPointAccFileProcess;
import gnete.card.service.PointAccService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("pointAccService")
public class PointAccServiceImpl implements PointAccService {

	@Autowired
	private PointAccRegDAO pointAccRegDAO;
	@Autowired
	private MessageParamDAO messageParamDAO;
	@Autowired
	private MessageRegDAO messageRegDAO;
	@Autowired
	private IPointAccFileProcess pointAccFileProcess;
	
	public List<PointAccReg> readPointAccFile(String branchCode, String date) throws BizException {
		Assert.notNull(branchCode, "发卡机构不能为空");
		Assert.notNull(date, "日期不能为空");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", branchCode);
		List<MessageParam> messageParamList = this.messageParamDAO.getMessageParamList(params);
		Assert.notEmpty(messageParamList, "发卡机构没有配置短信参数。");
		
		return this.pointAccFileProcess.getPointAccRegList(branchCode, date);
	}
	
	public void processPointAccFile(String branchCode, String date) throws BizException {
		Assert.notNull(branchCode, "发卡机构不能为空");
		Assert.notNull(date, "日期不能为空");
		
		this.pointAccFileProcess.processPointAccRegList(branchCode, date);
	}

	public String[] getFileList() throws BizException {
		return this.pointAccFileProcess.getFileList();
	}

	public boolean addPointAccReg(PointAccReg pointAccReg) throws BizException {
		Assert.notNull(pointAccReg, "新增对象不能为空");
		pointAccReg.setTime(new Date());
		pointAccReg.setUpdateTime(new Date());
		pointAccReg.setStatus(PointAccState.WAIT_EFFECT.getValue());
		
		return this.pointAccRegDAO.insert(pointAccReg) != null;
	}

	public boolean stopPointAcc(Long pointAccId) throws BizException {
		Assert.notNull(pointAccId, "注销对象编号不能为空");
		PointAccReg reg = (PointAccReg) this.pointAccRegDAO.findByPk(pointAccId);
		Assert.notNull(reg, "注销对象不存在");
		reg.setStatus(PointAccState.CANCEL.getValue());
		reg.setUpdateTime(new Date());
		int count = this.pointAccRegDAO.update(reg);
		return count > 0;
	}

	public boolean addMessageParam(MessageParam messageParam, String userId)
			throws BizException {
		Assert.notNull(messageParam, "新增对象不能为空");
		messageParam.setUpdateBy(userId);
		messageParam.setUpdateTime(new Date());
		return this.messageParamDAO.insert(messageParam)!=null;
	}
	
	public boolean modifyMessageParam(MessageParam messageParam, String userId)
			throws BizException {
		Assert.notNull(messageParam, "修改对象不能为空");
		messageParam.setUpdateBy(userId);
		messageParam.setUpdateTime(new Date());
		return this.messageParamDAO.update(messageParam) > 0;
	}

	public boolean deleteMessageParam(MessageParamKey key) throws BizException {
		Assert.notNull(key, "删除的对象不能为空");
		MessageParam messageParam = (MessageParam) this.messageParamDAO.findByPk(key);
		Assert.notNull(messageParam, "删除的对象不能为空");
		return this.messageParamDAO.delete(key) > 0;
	}

	public boolean addMessageReg(MessageReg messageReg) throws BizException {
		Assert.notNull(messageReg, "添加的短信登记不能为空"); 
		messageReg.setUpdateTime(new Date());
		messageReg.setStatus(MessageRegStatus.WAITE_SENT.getValue());
		
		if(this.messageRegDAO.insert(messageReg) != null){
			//登记成功后，发送报文
			MsgSender.sendMsg(MsgType.MESSAGE_SEND, messageReg.getMessageRegId(), "admin");
			return true;
		}
		else{
			return false;
		}
	}

	public String getYesterdayDate() throws BizException {
		Date today = new Date(); 
		Calendar ca = Calendar.getInstance();  
		ca.setTime(today); 
        ca.add(Calendar.DAY_OF_YEAR, -1); 
        Date yesterday = ca.getTime(); 
		return DateUtil.getDateYYYYMMDD(yesterday);
	}

	public MessageReg getMessageReg(PointAccReg pointAccReg)
			throws BizException {
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
		
		return messageReg;
	}

	public String getMsg(PointAccReg pointAccReg) throws BizException {
		String[] fileName = StringUtils.split(pointAccReg.getFileName(), "/");
		if(fileName.length == 0){
			throw new BizException("文件格式不正确。");
		}
		
		String msg = "业务类型: " + PointAccTransYype.valueOf(pointAccReg.getTransType()).getName()
		+ ", 文件名称: " + fileName[fileName.length-1] + ", 导入时间: " + DateFormatUtils.format(pointAccReg.getTime(), "yyyy-MM-dd HH:mm:ss") 
		+ ", 记录总数: " + pointAccReg.getRecordNum()+ "。";
		
		// 积分赠送
		if(PointAccTransYype.POINT_PRESENT.getValue().equals(pointAccReg.getTransType())){
			msg += "总金额为: " + pointAccReg.getAmt() + "。";
		}
		return msg;
	}

	public boolean addMsgAndPointAcc(PointAccReg pointAccReg)
			throws BizException {
		MessageReg messageReg = getMessageReg(pointAccReg);
		String msg = getMsg(pointAccReg);
		
		messageReg.setMessage(msg);
		boolean addMsgFlag = addMessageReg(messageReg);
		
		pointAccReg.setMessageRegId(messageReg.getMessageRegId());
		boolean addPointAccRegFlag = addPointAccReg(pointAccReg);
		return addMsgFlag && addPointAccRegFlag;
	}

}
