package gnete.card.service;

import gnete.card.entity.ChannelTrade;
import gnete.etc.BizException;

public interface ChannelTradeService {

	byte[] createTradeFile(ChannelTrade channelTrade) throws BizException;
	
	String createChannelTrade(ChannelTrade channelTrade) throws BizException;
	
	int handleReturnFile() throws BizException;

	void setResult(ChannelTrade channelTrade) throws BizException;
}
