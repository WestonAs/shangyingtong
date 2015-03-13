package gnete.card.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import flink.util.FileHelper;
import gnete.card.entity.CardInfo;
import gnete.card.service.TxtService;

@Service("txtService")
public class TxtServiceImpl implements TxtService {
	
	static final Logger logger = Logger.getLogger(TxtServiceImpl.class);
	
	private static final String DEFAULT_CONTENT_SPLIT = "|";
	String DEFAULT_LINE_SPLIT = "\n";
	int DEFAULT_BUFFER = 4096;
	String DEFAULT_EXPORT_ENCODING = "utf-8";
	
	public boolean geneteConsmChargeBalReportTxt(List<CardInfo> list, File templatePath, String[] head) throws Exception {
		
		StringBuilder contentBuilder = new StringBuilder(DEFAULT_BUFFER);
		
		contentBuilder.append(getHead(head));
		contentBuilder.append(DEFAULT_LINE_SPLIT);
		
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			CardInfo cardInfo = (CardInfo) iter.next();
			StringBuilder rowBuilder = getCardInfoRow(cardInfo);
			contentBuilder.append(rowBuilder);
			contentBuilder.append(DEFAULT_LINE_SPLIT);
		}
		
		boolean persist = FileHelper.persistFile(contentBuilder.toString(), templatePath, DEFAULT_EXPORT_ENCODING);
		return persist;
	}


	public StringBuilder getHead(String[] heads) throws Exception {
		StringBuilder headTitle = new StringBuilder(100);
		for(String head : heads){
			headTitle.append(head).append(DEFAULT_CONTENT_SPLIT);
		}
		
		return headTitle;
		
	}
	
	/**
	  * 累积消费充值余额报表输出消息体(单行记录)
	  * @param cardInfo
	  * @return  
	  * @version: 2011-11-15 
	 */
	protected static StringBuilder getCardInfoRow(CardInfo cardInfo) {
		StringBuilder sb = new StringBuilder(200);

		sb.append(cardInfo.getCardIssuer()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getCardSubclassName()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getCardId()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getSaleDate()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getConsumedStoredValue()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getAccuChargeAmt()).append(DEFAULT_CONTENT_SPLIT)
		  .append(cardInfo.getBal()).append(DEFAULT_CONTENT_SPLIT);

		return sb;
	}
	
	public File getWebDirFile(File reportDir, String contentHead) throws IOException {
		return FileHelper.getFile(reportDir, contentHead);
	}
	
}
