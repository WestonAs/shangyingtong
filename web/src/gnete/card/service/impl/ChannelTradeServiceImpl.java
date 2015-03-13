package gnete.card.service.impl;

import flink.util.AmountUtil;
import flink.util.DateUtil;
import flink.util.FtpUtils;
import flink.util.LogUtils;
import flink.util.SpringContext;
import gnete.card.dao.AccountSystemInfoDAO;
import gnete.card.dao.AreaDAO;
import gnete.card.dao.BankInfoDAO;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.entity.AccountSystemInfo;
import gnete.card.entity.Area;
import gnete.card.entity.BankInfo;
import gnete.card.entity.ChannelTrade;
import gnete.card.entity.state.ChnlTradeState;
import gnete.card.entity.state.TradeResultState;
import gnete.card.entity.type.ChnlRetCode;
import gnete.card.entity.type.TradeType;
import gnete.card.service.ChannelTradeService;
import gnete.card.service.TradeResultHandleService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("channelTradeService")
public class ChannelTradeServiceImpl implements ChannelTradeService {
	@Autowired
	private ChannelTradeDAO channelTradeDAO;
	@Autowired
	private AccountSystemInfoDAO accountSystemInfoDAO;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private AreaDAO areaDAO;
	protected final Log logger = LogFactory.getLog(getClass());
	public byte[] createTradeFile(ChannelTrade channelTrade) throws BizException {
		StringBuilder file = new StringBuilder();
		appendWithComma(file, "F");
		appendWithComma(file, channelTrade.getChnlMerNo());
		appendWithComma(file, DateUtil.getCurrentDate());
		appendWithComma(file, 1);
		appendWithComma(file, AmountUtil.multiply(channelTrade.getAmount(), new BigDecimal(100)).intValue());
		file.append(ChannelTrade.BUSI_CODE);
		file.append("\r\n");
		//明细
		appendWithComma(file, 1);
		appendCommas(file, 1);
		appendWithComma(file, channelTrade.getBankType());
		appendWithComma(file, "00");
		appendWithComma(file, channelTrade.getAcctNo());
		appendWithComma(file, channelTrade.getAcctName());
		appendWithComma(file, channelTrade.getProvince());
		appendWithComma(file, channelTrade.getCity());
		appendWithComma(file, channelTrade.getBankName());
		//0:私人,1:公司
		appendWithComma(file, channelTrade.getAcctProType());
		appendWithComma(file, AmountUtil.multiply(channelTrade.getAmount(), new BigDecimal(100)).intValue());
		appendWithComma(file, ChannelTrade.CURRENCY);
		appendCommas(file, 6);
		appendWithComma(file, "积分卡系统发起代付交易");
		appendCommas(file, 1);
		try {
			return file.toString().getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BizException("不支持的字符编码");
		}
	}

	private void appendWithComma(StringBuilder file, Object str) {
		if (str == null) {
			str = "";
		}
		file.append(str);
		file.append(",");
	}
	private void appendCommas(StringBuilder file, int num) {
		while(num > 0){
			file.append(",");
			num --;
		}
	}

	@Override
	public String createChannelTrade(ChannelTrade channelTrade) throws BizException{
		channelTrade.setCreateTime(new Date());
		String bankCode = channelTrade.getBankCode();
		if (StringUtils.isNotEmpty(bankCode)) {
			BankInfo bankInfo = (BankInfo)bankInfoDAO.findByPk(bankCode);
			if (bankInfo != null) {
				String addrNo = bankInfo.getAddrNo();
				Area area = (Area)areaDAO.findByPk(addrNo);
				if (area != null) {
					channelTrade.setCity(area.getCityName().replace("市", ""));
					channelTrade.setProvince(area.getParentName().replace("省", ""));
				}
			}
		}
		AccountSystemInfo asi = (AccountSystemInfo)accountSystemInfoDAO.findByPk(channelTrade.getSrcSystemId());
		channelTrade.setChnlMerNo(asi.getChnlMerNo());
		byte[] tradeFile = createTradeFile(channelTrade);
		String timestamp = DateUtil.getCurrentTimeMillis();
		String postfix = timestamp.substring(12, 17);
		String fileName = channelTrade.getChnlMerNo()+"_"+"F02"+DateUtil.getCurrentDate()+"_"+postfix+".txt";
		channelTrade.setTradeFileName(fileName);
		channelTrade.setTradeFileContent(tradeFile);
		channelTrade.setReturnState(ChnlTradeState.UN_RETURN.getValue());
		String id = (String)channelTradeDAO.insert(channelTrade);
		InputStream is = new ByteArrayInputStream(tradeFile);
		InputStream flagInputStream = new ByteArrayInputStream(new byte[0]);
		try {
			FtpUtils.upload(asi.getFtpAdd(), Integer.parseInt(asi.getFtpPort()), asi.getFtpUser(), asi.getFtpPwd(), asi.getFtpPath(), fileName, is);
			String flagName = fileName+".flag";
			FtpUtils.upload(asi.getFtpAdd(), Integer.parseInt(asi.getFtpPort()), asi.getFtpUser(), asi.getFtpPwd(), asi.getFtpPath(), flagName, flagInputStream);
		} catch (Exception e) {
			throw new BizException(LogUtils.r("上传交易文件[{0}]失败", channelTrade.getTradeFileName()));
		}
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(flagInputStream);
		return id;
	}

	@Override
	public int handleReturnFile() throws BizException {
		int cnt = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		//查询15天以内的渠道交易
		params.put("startCreateTime", DateUtil.addDays(new Date(), -15));
		params.put("returnState", ChnlTradeState.UN_RETURN.getValue());
		List<ChannelTrade> list = channelTradeDAO.findChannelTrades(params);
		for (int i = 0; i < list.size(); i++) {
			ChannelTrade channelTrade = list.get(i);
			String srcCustId = channelTrade.getSrcCustId();
			params.clear();
			params.put("custId", srcCustId);
			List<AccountSystemInfo> asis = accountSystemInfoDAO.findByInfos(params);
			AccountSystemInfo asi = asis.get(0);
			String fileName = channelTrade.getTradeFileName();
			String returnFileName = fileName.replace("txt", "rnt");
			byte[] returnFile = null;
			try {
				String[] fileNames = FtpUtils.getFileNames(asi.getFtpAdd(), Integer.parseInt(asi.getFtpPort()), asi.getFtpUser(), asi.getFtpPwd(), asi.getFtpPath());
				if (ArrayUtils.contains(fileNames, fileName+".err.flag")) {
					//文件有误
					String errorFileName = fileName+".err";
					returnFile = FtpUtils.getFile(asi.getFtpAdd(), Integer.parseInt(asi.getFtpPort()), asi.getFtpUser(), asi.getFtpPwd(), asi.getFtpPath(), errorFileName);
					channelTrade.setReturnFileName(errorFileName);
					channelTrade.setReturnFileContent(returnFile);
					parseReturnErrorFile(channelTrade, returnFile);
					cnt++;
					continue;
				} else if (!ArrayUtils.contains(fileNames, returnFileName+".flag")) {
					logger.debug(LogUtils.r("回盘文件[{0}]未准备好", returnFileName));
					continue;
				}
				returnFile = FtpUtils.getFile(asi.getFtpAdd(), Integer.parseInt(asi.getFtpPort()), asi.getFtpUser(), asi.getFtpPwd(), asi.getFtpPath(), returnFileName);
			} catch (Exception e) {
				logger.equals("获取回盘文件失败");
				e.printStackTrace();
			}
			if (ArrayUtils.isEmpty(returnFile)) {
				logger.debug("获取回盘文件失败或暂无回盘文件");
				continue;
			}
			String returnFileStr = null;
			try {
				returnFileStr = new String(returnFile);
				logger.debug(LogUtils.r("回盘文件内容:\n[{0}]", returnFileStr));
			} catch (Exception e) {
				e.printStackTrace();
			}
			channelTrade.setReturnFileName(returnFileName);
			channelTrade.setReturnFileContent(returnFile);
			try {
				parseReturnFile(channelTrade, returnFile);
				cnt++;
			} catch (Exception e) {
				continue;
			}
		}
		return cnt;
	}
	
	private void parseReturnErrorFile(ChannelTrade channelTrade, byte[] returnFile) throws BizException{
		InputStream is = new ByteArrayInputStream(returnFile);
		try {
			List lineList =  IOUtils.readLines(is, "GBK");
			String returnInfo = (String)lineList.get(0);;
			channelTrade.setReturnInfo(returnInfo);
			channelTrade.setReturnTime(new Date());
			channelTrade.setReturnState(ChnlTradeState.RETURNED.getValue());
			channelTrade.setResult(TradeResultState.FAILURE.getValue());
			String tradeType = channelTrade.getTradeType();
			TradeType type = TradeType.valueOf(tradeType);
			String beanName = type.getBeanName();
			channelTradeDAO.update(channelTrade);
			TradeResultHandleService tradeResultHandleService = (TradeResultHandleService)SpringContext.getService(beanName);
			tradeResultHandleService.handleTradeResult(channelTrade.getId());
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BizException("解析回盘文件错误");
		}
		
	}

	private void parseReturnFile(ChannelTrade channelTrade, byte[] returnFile) throws BizException{
		InputStream is = new ByteArrayInputStream(returnFile);
		try {
			List lineList =  IOUtils.readLines(is, "GBK");
			String line = (String)lineList.get(1);
			String[] infos = line.split(",");
			String returnCode = infos[19];
			String returnInfo = infos[20];
			//处理中，不予处理
			if(ChnlRetCode.C_2000.getValue().equals(returnCode)) {
				return;
			}
			channelTrade.setReturnCode(returnCode);
			channelTrade.setReturnInfo(returnInfo);
			channelTrade.setReturnTime(new Date());
			channelTrade.setReturnState(ChnlTradeState.RETURNED.getValue());
			if (ChnlRetCode.C_0000.getValue().equals(returnCode)) {
				channelTrade.setResult(TradeResultState.SUCCESS.getValue());
			} else {
				channelTrade.setResult(TradeResultState.FAILURE.getValue());
			}
			String tradeType = channelTrade.getTradeType();
			TradeType type = TradeType.valueOf(tradeType);
			String beanName = type.getBeanName();
			channelTradeDAO.update(channelTrade);
			TradeResultHandleService tradeResultHandleService = (TradeResultHandleService)SpringContext.getService(beanName);
			tradeResultHandleService.handleTradeResult(channelTrade.getId());
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BizException("解析回盘文件错误");
		}
	}

	@Override
	public void setResult(ChannelTrade channelTrade) throws BizException {
		ChannelTrade trade = (ChannelTrade)channelTradeDAO.findByPkWithLock(channelTrade.getId());
		Assert.isTrue(ChnlTradeState.UN_RETURN.getValue().equals(trade.getReturnState()), "交易状态已过期,无法进行此操作");
		trade.setResult(channelTrade.getResult());
		trade.setReturnState(ChnlTradeState.MANUAL.getValue());
		channelTradeDAO.update(trade);
		TradeType type = TradeType.valueOf(trade.getTradeType());
		String beanName = type.getBeanName();
		TradeResultHandleService tradeResultHandleService = (TradeResultHandleService)SpringContext.getService(beanName);
		tradeResultHandleService.handleTradeResult(trade.getId());
	}
	

	
}
