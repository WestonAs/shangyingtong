package gnete.card.service.impl;

import flink.util.MobileNumUtil;
import gnete.card.dao.CardSmsRecordDAO;
import gnete.card.entity.CardSmsRecord;
import gnete.card.entity.UserInfo;
import gnete.card.service.CardSmsRecordService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cardSmsRecordService")
public class CardSmsRecordServiceImpl implements CardSmsRecordService {
	@Autowired
	private CardSmsRecordDAO	cardSmsRecordDAO;

	static Logger				logger	= LoggerFactory.getLogger(CardSmsRecordServiceImpl.class);

	@Override
	public void addCardSmsRecords(String[] mobileArr, String smsSendContent, UserInfo user)
			throws BizException {
		Assert.notEmpty(mobileArr, "手机号不能为空");
		Assert.notBlank(smsSendContent, "短信内容不能为空");
		List<CardSmsRecord> recordList = new ArrayList<CardSmsRecord>(mobileArr.length);
		for (String mobile : mobileArr) {
			Assert.isTrue(MobileNumUtil.isMobileNum(mobile), mobile + "不是手机号码");
			CardSmsRecord record = new CardSmsRecord(user.getBranchNo(), "00", mobile.trim(), smsSendContent,
					"1", "1", new Date(), user.getUserId());
			recordList.add(record);
		}
		cardSmsRecordDAO.insertBatch(recordList);
	}

	@Override
	public int addCardSmsRecords(File file, UserInfo user) throws BizException {
		try {
			List<CardSmsRecord> recordList = new ArrayList<CardSmsRecord>();

			List<String> lines = FileUtils.readLines(file, "GBK");
			Assert.notEmpty(lines, "文件内容不能为空！");
			Assert.isTrue(lines.size() <= 10001, "明细行不能大于10000行！");
			for (int i = 1; i < lines.size(); i++) {
				String prefix = "明细第" + i + "行：";
				Assert.notBlank(lines.get(i), prefix + "文件的明细行不能是空行！");
				String[] arr = lines.get(i).split("\\|");
				Assert.isTrue(arr.length == 2, prefix + "明细行有误！");
				String mobile = arr[0];
				String smsSendContent = arr[1];
				Assert.isTrue(MobileNumUtil.isMobileNum(mobile), prefix + mobile + "不是手机号码");
				Assert.notBlank(smsSendContent, prefix + "短信内容不能为空");
				Assert.isTrue(smsSendContent.getBytes("GBK").length <= 150, prefix
						+ "短信内容不能超过75个中文字符或150个英文字符！");

				CardSmsRecord record = new CardSmsRecord(user.getBranchNo(), "00", mobile.trim(),
						smsSendContent, "1", "1", new Date(), user.getUserId());
				recordList.add(record);
			}
			cardSmsRecordDAO.insertBatch(recordList);
			return recordList.size();
		} catch (Exception e) {
			logger.warn("文件方式添加短信营销记录，处理上传文件失败", e);
			throw new BizException("处理上传文件失败！" + e.getMessage(), e);
		}
	}
}
