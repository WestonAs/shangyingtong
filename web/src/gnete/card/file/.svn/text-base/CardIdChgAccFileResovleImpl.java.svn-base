package gnete.card.file;

import flink.util.CommonHelper;
import gnete.card.entity.PointAccReg;
import gnete.etc.BizException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gnete.card.entity.type.PointAccTransYype;

/**
 * <p>处理"卡号变更"文件的转换</p> 
 * <p>{品牌:原号码:总数，品牌:新号码:总数}</p>
 * @Project: Card
 * @File: CardIdChgAccFileResovleImpl.java
 * @See: 
 * @author: aps-zbw
 * @modified: aps-lib
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class CardIdChgAccFileResovleImpl extends AbstractPointAccFileResolveImpl {
	private final String ORIG_TAG = "原号码";

	private final String NEW_TAG = "新号码";

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

	/**
	 * 
	 */
	@Override
	protected PointAccReg getPointAccRegResolve(String fileName, String branchCode, List<String[]> resolveContentList)
			throws BizException {
		return super.getDefaulttPointAccRegResolve(fileName, branchCode, resolveContentList);
	}

	@Override
	protected String getPointAccTransType() {
		return PointAccTransYype.CARD_ID_CHG.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String getContentListRemark(List<String[]> resolveContentList) throws BizException {
		String remark = null;
		Map cardIdMap = null;
		try {
			cardIdMap = getCardIdMap(resolveContentList);
			remark = getCardIdMapRemark(cardIdMap);
		} catch (Exception ex) {
			throw new BizException("卡号更改文件统计异常,原因[" + ex.getMessage() + "]");
		} finally {
			cardIdMap = null;
		}

		return remark;
	}

	@SuppressWarnings("unchecked")
	private String getCardIdMapRemark(Map cardIdMap) throws Exception {
		List<String> keyDescriptList = new ArrayList<String>();

		for (Object key : cardIdMap.keySet()) {
			List<String> numberList = (List<String>) cardIdMap.get(key);

			String keyDescript = new StringBuilder().append(key.toString()).append(DEFAULT_DSCRIPT_SPLIT).append(numberList.size())
					.toString();
			keyDescriptList.add(keyDescript);
		}

		return CommonHelper.filterArray2Str(keyDescriptList.toArray(new String[keyDescriptList.size()]));
	}

	//原号码|新号码|品牌|备注
	@SuppressWarnings("unchecked")
	private Map getCardIdMap(List<String[]> resolveContentList) throws Exception {
		Map cardIdMap = CommonHelper.createDefaultMultiMap();

		for (String[] resolveContent : resolveContentList) {
			String origNum = resolveContent[0];
			String newNum = resolveContent[1];
			String brand = resolveContent[2];

			cardIdMap.put(getBrandNumTag(brand, ORIG_TAG), origNum);
			cardIdMap.put(getBrandNumTag(brand, NEW_TAG), newNum);
		}

		return cardIdMap;
	}

	private String getBrandNumTag(String brand, String tag) {
		return new StringBuilder().append(brand).append(DEFAULT_DSCRIPT_SPLIT).append(tag).toString();
	}

}
