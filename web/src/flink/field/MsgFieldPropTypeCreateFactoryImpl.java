package flink.field;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import flink.util.CommonHelper;
import gnete.etc.RuntimeBizException;

/**
 * 
  * @Project: Card
  * @File: MsgFieldPropTypeCreateFactoryImpl.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-9-3
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class MsgFieldPropTypeCreateFactoryImpl implements MsgFieldPropTypeCreateFactory{
    /** 抽象数据类型缓存工厂类*/
	private final Map<String,MsgPropertyType<?>> fieldTypeCreateFactoryMap  = new ConcurrentHashMap<String,MsgPropertyType<?>>();
	
    public MsgFieldPropTypeCreateFactoryImpl() {
	    init();	
	}
	
	/**
	 * 
	  * @description：  所支持的类型范畴
	  * @version: 2010-9-3 下午02:20:22
	  * @See:
	 */
	private void init() {
		registMsgType(BigIntegerMsgPropertyType.INSTANCE);
		registMsgType(CharMsgPropertyType.INSTANCE);
		registMsgType(DoubleMsgPropertyType.INSTANCE);
		registMsgType(FloatMsgPropertyType.INSTANCE);
		registMsgType(IntegerMsgPropertyType.INSTANCE);
		registMsgType(LongMsgPropertyType.INSTANCE);
		registMsgType(StringMsgPropertyType.INSTANCE);
		registMsgType(TradeDateMsgPropertyType.INSTANCE);
		registMsgType(TradeTimeMsgPropertyType.INSTANCE);
		registMsgType(BigDecimalMsgPropertyType.INSTANCE);
	}
	
	private void registMsgType(MsgPropertyType<?> msgType) {
		String[] types = msgType.getDefaultMatchTypeString();
		if(CommonHelper.checkIsEmpty(types)) {
			return ;
		}
		
		for(String type : types) {
			if(CommonHelper.checkIsEmpty(type)) {
				continue;
			}
			
			fieldTypeCreateFactoryMap.put(type, msgType);
		}
	}
	
	public  MsgPropertyType<?> getMsgPropertyType(String typeValue) throws RuntimeBizException{
		if(CommonHelper.isEmpty(typeValue)) {
			throw new RuntimeBizException("类型参数不能为空!");
		}
		
		MsgPropertyType<?> msgPropertyType = fieldTypeCreateFactoryMap.get(typeValue);
		
		if(msgPropertyType == null) {
			throw new RuntimeBizException("类型参数[" + typeValue + "]不支持!");
		}
		
		return msgPropertyType;
	}

}
