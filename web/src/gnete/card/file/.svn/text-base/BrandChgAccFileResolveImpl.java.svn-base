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
 * <p>处理"品牌转换"文件的转换</p>
 * <p>{原品牌:品牌值:号码总数,新品牌:品牌值:号码总数 </p>
 * @Project: Card
 * @File: BrandChgAccFileResolveImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class BrandChgAccFileResolveImpl extends AbstractPointAccFileResolveImpl {
	private final String ORIG_TAG = "原品牌";

	private final String NEW_TAG = "新品牌";

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

	@Override
	protected PointAccReg getPointAccRegResolve(String fileName, String branchCode, List<String[]> resolveContentList)throws BizException {
		return super.getDefaulttPointAccRegResolve(fileName, branchCode, resolveContentList);
	}

	@Override
	protected String getPointAccTransType() {
		return PointAccTransYype.BRAND_CHG.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String getContentListRemark(List<String[]> resolveContentList) throws BizException {
		String remark = null;
		Map cardBrandMap = null;
		try {
			cardBrandMap = getCardBrandMap(resolveContentList);
			remark = getCardBrandMapRemark(cardBrandMap);
		} catch (Exception ex) {
			throw new BizException("品牌转换文件统计异常,原因[" + ex.getMessage() + "]");
		} finally {
			cardBrandMap = null;
		}

		return remark;
	}

	@SuppressWarnings("unchecked")
	private String getCardBrandMapRemark(Map cardBrandMap) throws Exception {
		List<String> keyDescriptList = new ArrayList<String>();

		for (Object key : cardBrandMap.keySet()) {
			List<String> numberList = (List<String>) cardBrandMap.get(key);

			String keyDescript = new StringBuilder().append(key).append(DEFAULT_DSCRIPT_SPLIT).append(numberList.size()).toString();
			keyDescriptList.add(keyDescript);
		}

		return CommonHelper.filterArray2Str(keyDescriptList.toArray(new String[keyDescriptList.size()]));
	}

	// 号码|原品牌|新品牌|备注
	@SuppressWarnings("unchecked")
	private Map getCardBrandMap(List<String[]> resolveContentList) throws Exception {
		Map cardBrandMap = CommonHelper.createDefaultMultiMap();

		for (String[] resolveContent : resolveContentList) {
			String num = resolveContent[0];
			String origBrand = resolveContent[1];
			String newBrand = resolveContent[2];

			cardBrandMap.put(getBrandNameTag(ORIG_TAG, origBrand), num);
			cardBrandMap.put(getBrandNameTag(NEW_TAG, newBrand), num);
		}

		return cardBrandMap;
	}

	private String getBrandNameTag(String tag, String brand) {
		return new StringBuilder().append(tag).append(DEFAULT_DSCRIPT_SPLIT).append(brand).toString();
	}

}
