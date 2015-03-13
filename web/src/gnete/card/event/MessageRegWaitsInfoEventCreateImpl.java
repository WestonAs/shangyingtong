package gnete.card.event;

import flink.event.FlinkAppEvent;
import flink.event.FlinkOperateEnum;
import flink.event.WaitsInfoAppEvent;
import flink.event.impl.AbstractFlinkAppEventCreateImpl;
import flink.util.CommonHelper;
import gnete.card.dao.MessageParamDAO;
import gnete.card.dao.MessageRegDAO;
import gnete.card.dao.PointAccRegDAO;
import gnete.card.entity.MessageParam;
import gnete.card.entity.MessageReg;
import gnete.card.entity.PointAccReg;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.state.MessageRegStatus;
import gnete.card.entity.type.PointAccTransYype;
import gnete.card.msg.MsgType;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <p>短信处理业务事件(积分充值记录作为事件源)</p>
 * @Project: Card
 * @File: MessageRegWaitsInfoEventCreateImpl.java
 * @See:

 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("msgRegAppEventCreate")
public class MessageRegWaitsInfoEventCreateImpl extends AbstractFlinkAppEventCreateImpl<List<PointAccReg>> {
	/**
	 * <p>短信参配置查询</p>
	 */
	@Autowired
	private MessageParamDAO messageParamDAO;

	/**
	 * <p>短信表内容添加</p>
	 */
	@Autowired
	private MessageRegDAO messageRegDAO;

	/**
	 * <p>积分充值记录添加</p>
	 */
	@Autowired
	private PointAccRegDAO pointAccRegDao;

	/** 声明短信参数发卡机构查询参数*/
	private static final String CARD_ISSURE_KEY = "cardIssuers";

	/** 导出时间定义的Pattern*/
	private static final String SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 缺省短信触发事件 */
	private final FlinkAppEvent defaultAppEvent =
		new WaitsInfoAppEvent(this, FlinkOperateEnum.ANY, Collections.<Object[]> emptyList());
	
	@Override
	protected boolean checkFlinkAppEventParams(List<PointAccReg> eventResource, Map params) {
		return CommonHelper.checkIsNotEmpty(eventResource);
	}
	
	@Override
	protected FlinkAppEvent createDefaultFlinkAppEvent() {
		return defaultAppEvent;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	protected FlinkAppEvent createFlinkAppEvent(List<PointAccReg> eventResource, Map params) throws BizException {
		Object[] pointAccRegArray = getProcessedPointAccRegArray(eventResource, params);

		if (CommonHelper.checkIsEmpty(pointAccRegArray)) {
			return defaultAppEvent;
		}

		List<PointAccReg> pointAccRegList = (List<PointAccReg>) pointAccRegArray[1];

		try {
			this.pointAccRegDao.insertBatch(pointAccRegList);

			logger.info("====积分充值记录登记簿添加成功====内容[" + pointAccRegList.toString() + "]");

		} catch (DataAccessException ex) {
			throw new BizException("写入积分充值登记簿异常,原因[" + ex.getMessage() + "]");
		}

		List<MessageReg> msgRegList = (List<MessageReg>) pointAccRegArray[0];

		return getPointAccAppEvent(msgRegList);
	}

	/**
	 * 
	 * <p>获得待写入登记簿的PointAccReg集合</p>
	 * @param pointAccRegList
	 * @param params
	 * @return
	 * @throws BizException
	 * @version: 2011-4-19 下午03:00:45
	 * @See:
	 */
	@SuppressWarnings("unchecked")
	private Object[] getProcessedPointAccRegArray(List<PointAccReg> pointAccRegList, Map params) throws BizException {
		Map<MessageParam, PointAccReg> msgParamMap = getMsgParamMap(pointAccRegList, params);

		if (CommonHelper.checkIsEmpty(msgParamMap)) {
			return new Object[] {};
		}

		try {
			List<MessageReg> msgRegList = new ArrayList<MessageReg>(msgParamMap.size());
			
			for(Iterator<Map.Entry<MessageParam, PointAccReg>> iterator = msgParamMap.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<MessageParam, PointAccReg> entry = iterator.next();
				MessageParam msgParam = entry.getKey();
				PointAccReg pointAccReg = entry.getValue();
				MessageReg msgReg = getMessageReg(msgParam, pointAccReg);
				msgRegList.add(msgReg);
			}

			this.messageRegDAO.insertBatch(msgRegList);

			logger.info("短信表添加成功,内容[" + msgRegList.toString() + "]");

			List<PointAccReg> _pointAccRegList = getPointAccRegList(msgRegList, msgParamMap);

			return new Object[] { msgRegList, _pointAccRegList };

		} catch (DataAccessException ex) {
			throw new BizException("写入短信表出现异常,原因[" + ex.getMessage() + "]");
		}
	}
	
	

	/**
	 * <p>从写入的短信及先前保存的集合中提取待处理的登记簿</p>
	 * @param msgRegList
	 * @param msgParamMap
	 * @return
	 * @version: 2011-4-19 下午03:03:57
	 * @See:
	 */
	private List<PointAccReg> getPointAccRegList(List<MessageReg> msgRegList, Map<MessageParam, PointAccReg> msgParamMap) {
		List<PointAccReg> pointAccRegList = new ArrayList<PointAccReg>(msgRegList.size());
		Collection<PointAccReg> _pointAccRegList = msgParamMap.values();
		for (MessageReg msgReg : msgRegList) {
			String cardIssure = msgReg.getCardIssuer();
			for (PointAccReg pointAccReg : _pointAccRegList) {
				if (cardIssure.equals(pointAccReg.getCardIssuer())) {
					PointAccReg _pointAccReg = (PointAccReg) pointAccReg.clone();
					_pointAccReg.setEventCode(msgReg.getEventCode());
					_pointAccReg.setMessageRegId(msgReg.getMessageRegId());
					pointAccRegList.add(_pointAccReg);
				}
			}
		}
		return pointAccRegList;
	}

	/**
	 *  <p>构造短信表写入信息</p>
	  * @param msgParam
	  * @param pointAccReg
	  * @return  
	  * @version: 2011-7-12 上午11:06:42
	  * @See:
	 */
	private MessageReg getMessageReg(MessageParam msgParam, PointAccReg pointAccReg) {
		MessageReg messageReg = new MessageReg();
		messageReg.setCardIssuer(msgParam.getCardIssuer());
		messageReg.setEventCode(msgParam.getEventCode());
		messageReg.setMobileNo(msgParam.getMobileNo());
		messageReg.setStatus(MessageRegStatus.WAITE_SENT.getValue());
		messageReg.setUpdateTime(new Date());
		messageReg.setMessage(getMessageRegSndMsg(pointAccReg));
		return messageReg;
	}

	/**
	 * <p>构造短信发送内容(每天充值信息可以构造成一个发送记录)</li>
	  * @param pointAccReg
	  * @return  
	  * @version: 2011-7-12 上午11:03:06
	  * @See:
	 */
	private String getMessageRegSndMsg(PointAccReg pointAccReg) {
		StringBuilder msgBuilder = new StringBuilder(100);

		msgBuilder.append("业务类型:").append(PointAccTransYype.valueOf(pointAccReg.getTransType()).getName()).append(",")
		          .append("文件名称:").append(pointAccReg.getFileName()).append(",")
		          .append("导入时间:").append(CommonHelper.getDateFormatStr(pointAccReg.getTime(), SECOND_PATTERN)).append(",")
		          .append("记录总数:").append(pointAccReg.getRecordNum()).append(".");

		// 积分赠送
		if (PointAccTransYype.POINT_PRESENT.getValue().equals(pointAccReg.getTransType())) {
			msgBuilder.append("总金额为:").append(pointAccReg.getAmt()).append(".");
		}

		return msgBuilder.toString();
	}

	/**
	 * 	
	 * <p>获得跟短信参数配置(一个cardIssure可以有多个信道,作为KEY)来关联的积分充值信息</p>
	 * @param pointAccRegList
	 * @param params
	 * @return
	 * @throws BizException
	 * @version: 2011-4-19 下午02:10:22
	 * @See:
	 */
	private Map<MessageParam, PointAccReg> getMsgParamMap(List<PointAccReg> pointAccRegList, Map params) throws BizException {
		List<MessageParam> msgParamList = getMessageParamList(pointAccRegList, params);
		if (CommonHelper.checkIsEmpty(msgParamList)) {
			logger.warn("=======没有找到跟提取文件匹配的短信参数信息!=======,文件内容[" + pointAccRegList.toString() + "]");
			return Collections.<MessageParam, PointAccReg> emptyMap();
		}

		Map<MessageParam, PointAccReg> msgParamMap = new HashMap<MessageParam, PointAccReg>(msgParamList.size());

		for (MessageParam msgParam : msgParamList) {
			String cardIssure = msgParam.getCardIssuer();
			for (PointAccReg pointAccReg : pointAccRegList) {
				if (cardIssure.equals(pointAccReg.getCardIssuer())) {
					msgParamMap.put(msgParam, pointAccReg);
				}
			}
		}
		return msgParamMap;
	}

	/**
	  * <p>根据积分充值记录涉及的机构获得相关短信参数配置</p> 
	  * @param pointAccRegList
	  * @param params
	  * @return
	  * @throws BizException  
	  * @version: 2011-7-12 上午11:00:18
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	private List<MessageParam> getMessageParamList(List<PointAccReg> pointAccRegList, Map params) throws BizException {
		List<BranchInfo> branchInfoList = new ArrayList<BranchInfo>(pointAccRegList.size());
		for (PointAccReg pointAccReg : pointAccRegList) {
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setBranchCode(pointAccReg.getCardIssuer());
			branchInfoList.add(branchInfo);
		}

		try {
			params.put(CARD_ISSURE_KEY, branchInfoList);
			return this.messageParamDAO.findMessageParam(params);
		} catch (DataAccessException ex) {
			throw new BizException("根据积分充值记录查找消息参数表异常,原因[" + ex.getMessage() + "]");
		}

	}

	/**
	  * <p>根据触发处理短信发送的命令表信息</p>  
	  * @param msgRegList
	  * @return  
	  * @version: 2011-7-12 上午11:03:56
	  * @See:
	 */
	private WaitsInfoAppEvent getPointAccAppEvent(List<MessageReg> msgRegList) {

		List<Object[]> pointAccRegCmdList = new ArrayList<Object[]>(msgRegList.size());
		for (MessageReg msgReg : msgRegList) {
			pointAccRegCmdList.add(getPointAccRegCmd(msgReg));
		}

		return new WaitsInfoAppEvent(this, FlinkOperateEnum.ADD, pointAccRegCmdList);

	}

	private Object[] getPointAccRegCmd(MessageReg msgReg) {
		return new Object[] { MsgType.MESSAGE_SEND, msgReg.getMessageRegId(), Constants.DEFAULT_SYSUSER };
	}
	
	
    
	

}
