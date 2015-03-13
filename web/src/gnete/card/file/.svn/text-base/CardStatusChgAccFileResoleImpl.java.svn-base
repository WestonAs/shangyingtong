package gnete.card.file;

import gnete.card.entity.PointAccReg;
import gnete.card.entity.type.PointAccTransYype;
import gnete.etc.BizException;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import flink.util.CommonHelper;

/**
 * 
 * <p>处理"卡状态"文件的转换</p> 
 * <p>{品牌1：状态：号码总数, 品牌2：状态2：号码总数,..}</p>
 * @Project: Card
 * @File: CardStatusChgAccFileResoleImpl.java
 * @See:
 * @author: aps-zbw
 * @modified: aps-lib
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CardStatusChgAccFileResoleImpl extends AbstractPointAccFileResolveImpl {
	
	@Override
	protected void checkResolve(String fileName, InputStream input) throws BizException {
		// TODO
	}

	@Override
	protected String getFileEncoding() {
		return super.DEFAULT_FILE_ENCODING;
	}

	@Override
	protected String getLineSplit() {
		return super.DEFAULT_LINE_SPLIT;
	}

	// 号码|品牌|号码状态(销户、挂失、解挂) |备注
	@Override
	protected PointAccReg getPointAccRegResolve(String fileName, String branchCode, List<String[]> resolveContentList)
			throws BizException {
		return super.getDefaulttPointAccRegResolve(fileName, branchCode, resolveContentList);
	}

	private String getCardStatusKey(String brand, String state) {
		return new StringBuilder().append(brand).append(DEFAULT_DSCRIPT_SPLIT).append(state).toString();
	}

	@Override
	protected String getPointAccTransType() {
		return PointAccTransYype.CARD_STATUS_CHG.getValue();
	}

	/**
	 * 
	 * <p>对品牌以及状态进行统计</p>
	 * @param resolveContentList
	 * @return
	 * @throws Exception
	 * @version: 2011-4-15 下午03:11:38
	 * @See:
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String getContentListRemark(List<String[]> resolveContentList) throws BizException {
		String remark = null;
		Map cardStatusMap = null;
		try {
			cardStatusMap = getCardStatusMap(resolveContentList);
			remark = getCardStatusMapRemark(cardStatusMap);

		} catch (Exception ex) {
			throw new BizException("卡状态文件统计异常,原因[" + ex.getMessage() + "]");
		} finally {
			cardStatusMap = null;
		}
		return remark;
	}

	@SuppressWarnings("unchecked")
	private String getCardStatusMapRemark(Map cardStatusMap) throws Exception {
		List<String> keyDescriptList = new ArrayList<String>();

		for (Object key : cardStatusMap.keySet()) {
			List<String> numberList = (List<String>) cardStatusMap.get(key);                        
			String keyDescript = getCardStatusDescript(key.toString(),numberList);
			keyDescriptList.add(keyDescript);
		}

		return CommonHelper.filterArray2Str(keyDescriptList.toArray(new String[keyDescriptList.size()]));
	}
	
	private String getCardStatusDescript(String statusKey, List<String> numberList) {
		String[] keyArray = statusKey.split(DEFAULT_DSCRIPT_SPLIT);
		
		Map stateMap  = CommonHelper.createDefaultMultiMap();
		stateMap.put("A2", "销户");
		stateMap.put("A4", "挂失");
		stateMap.put("A5", "解挂");
		
		List<String> stateName = (List<String>) stateMap.get(keyArray[1]);
		
		return new StringBuilder(keyArray[0]).append(DEFAULT_DSCRIPT_SPLIT).append(stateName.toString())
		                                                          .append(DEFAULT_DSCRIPT_SPLIT).append(numberList.size()).toString();
	}

	@SuppressWarnings("unchecked")
	private Map getCardStatusMap(List<String[]> resolveContentList) throws Exception {
		Map cardStatusMap = CommonHelper.createDefaultMultiMap();

		for (String[] resolveContent : resolveContentList) {
			String number = resolveContent[0];
			String brand = resolveContent[1];
			String state = resolveContent[2];

			String key = getCardStatusKey(brand, state);

			cardStatusMap.put(key, number);
		}

		return cardStatusMap;
	}

}
