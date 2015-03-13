package gnete.card.clear2Pay;

import gnete.etc.BizException;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;

import java.util.List;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import flink.util.FileHelper;

/**
 * 
 * @Project: Card
 * @File: Clear2PayBankSeqInfoFetchImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-15
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
@Service("clear2PayBankSeqInfoFetch")
public class Clear2PayBankSeqInfoFetchImpl implements IClear2PayBankSeqInfoFetch {
	private final int DEFAULT_BANKID_IDNEX = 0;

	private final int DEFAULT_BANKCODE_INDEX = 1;

	public Map<String, String> getClear2PayBankSeqInfoMap(File file) throws BizException {
		try {
			return getClear2PayBankSeqInfoMap(FileHelper.getBufferedInputStream(file));
		} catch (IOException ex) {
			throw new BizException("处理网银通电子联行号文件信息异常,原因[" + ex.getMessage() + "]");
		}
	}

	public Map<String, String> getClear2PayBankSeqInfoMap(InputStream payBankSeqStream) throws BizException {
		try {
			List<String> bankSeqInfoContentList = FileHelper.readFilterLines(payBankSeqStream, CONTENT_ENCODING);

			return getBankSeqInfoResultMap(bankSeqInfoContentList);
		} catch (IOException ex) {
			throw new BizException("处理网银通电子联行号文件信息异常,原因[" + ex.getMessage() + "]");
		} finally {
			FileHelper.closeInputStream(payBankSeqStream);
		}

	}

	private Map<String, String> getBankSeqInfoResultMap(List<String> bankSeqInfoContentList) {
		Map<String, String> resultMap = new HashMap<String, String>(bankSeqInfoContentList.size());

		for (String bankSeqInfoContent : bankSeqInfoContentList) {
			String[] bankSeqInfoArray = bankSeqInfoContent.split(CONTENT_SPLIT);
			String bank_id = bankSeqInfoArray[DEFAULT_BANKID_IDNEX];
			String bank_no = bankSeqInfoArray[DEFAULT_BANKCODE_INDEX];

			resultMap.put(bank_no, bank_id);
		}

		return resultMap;
	}

}
