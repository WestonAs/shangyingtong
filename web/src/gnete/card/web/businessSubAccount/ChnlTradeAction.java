package gnete.card.web.businessSubAccount;

import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BankNoDAO;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.entity.BankNo;
import gnete.card.entity.ChannelTrade;
import gnete.card.entity.state.ChnlTradeState;
import gnete.card.entity.state.TradeResultState;
import gnete.card.entity.type.TradeType;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.ChannelTradeService;
import gnete.card.web.BaseAction;
import gnete.etc.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author libaozhu
 * @date 2013-3-29
 */
public class ChnlTradeAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChannelTrade channelTrade = new ChannelTrade();
	@Autowired
	private ChannelTradeDAO channelTradeDAO;
	@Autowired
	private BankNoDAO bankNoDAO;
	@Autowired
	private ChannelTradeService channelTradeService;
	private Paginater page;
	
	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("srcCustId", getLoginBranchCode());
		params.put("tradeType", channelTrade.getTradeType());
		params.put("amount", channelTrade.getAmount());
		params.put("startCreateTime", DateUtil.formatDate(channelTrade.getStartCreateDate(), "yyyyMMdd"));
		params.put("endCreateTime", DateUtil.formatDate(channelTrade.getEndCreateDate(), "yyyyMMdd"));
		params.put("acctNo", channelTrade.getAcctNo());
		params.put("acctName", channelTrade.getAcctName());
		params.put("returnState", channelTrade.getReturnState());
		this.page = channelTradeDAO.findPaginater(params, super.getPageNumber(), super.getPageSize());
		for (int i = 0; i < page.getList().size(); i++) {
			ChannelTrade channelTrade = (ChannelTrade)page.getList().get(i);
			String tradeType = channelTrade.getTradeType();
			channelTrade.setTradeTypeName(TradeType.valueOf(tradeType).getName());
			String bankType = channelTrade.getBankType();
			BankNo bankNo = (BankNo)bankNoDAO.findByPk(bankType);
			channelTrade.setBankTypeName(bankNo.getBankTypeName());
			channelTrade.setReturnStateName(ChnlTradeState.valueOf(channelTrade.getReturnState()).getName());
			if (StringUtils.isNotEmpty(channelTrade.getResult())) {
				channelTrade.setResultName(TradeResultState.valueOf(channelTrade.getResult()).getName());
			}
		}
		if (isMerchantRoleLogined()) {
			request.setAttribute("mer", Symbol.YES);
		}
		return LIST;
	}
	
	// 明细页面
	public String detail() throws Exception {
		channelTrade = (ChannelTrade)channelTradeDAO.findByPk(channelTrade.getId());
		BankNo bankNo = (BankNo)bankNoDAO.findByPk(channelTrade.getBankType());
		String bankTypeName = bankNo.getBankTypeName();
		channelTrade.setBankTypeName(bankTypeName);
		channelTrade.setTradeTypeName(TradeType.valueOf(channelTrade.getTradeType()).getName());
		String returnState = channelTrade.getReturnState();
		channelTrade.setReturnStateName(ChnlTradeState.valueOf(returnState).getName());
		String result = channelTrade.getResult();
		if (!StringUtils.isEmpty(result)) {
			channelTrade.setResultName(TradeResultState.valueOf(result).getName());
		}
		return DETAIL;
	}
	public String download() throws Exception {
		String id = channelTrade.getId();
		String fileType = channelTrade.getFileType();
		channelTrade = (ChannelTrade)channelTradeDAO.findByPk(id);
		String fileName = "";
		byte[] file = null;
		if ("T".equals(fileType)) {
			file = channelTrade.getTradeFileContent();
			fileName = channelTrade.getTradeFileName();
		} else if ("R".equals(fileType)) {
			file = channelTrade.getReturnFileContent();
			fileName = channelTrade.getReturnFileName();
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(),"iso-8859-1"));
		response.setContentType("application/plain");
		response.getOutputStream().write(file);
		return null;
	}
	
	public String setResult() throws Exception {
		channelTradeService.setResult(channelTrade);
		String msg = LogUtils.r("操作成功,渠道交易id:[{0}]被置为[{1}]", channelTrade.getId(), TradeResultState.valueOf(channelTrade.getResult()).getName());
		this.addActionMessage("/businessSubAccount/chnlTrade/list.do", msg);
		this.log(msg, UserLogType.OTHER);
		return SUCCESS;
	}
	public Collection<?> getTradeTypes(){
		return TradeType.ALL.values();
	}
	public ChannelTrade getChannelTrade() {
		return channelTrade;
	}

	public void setChannelTrade(ChannelTrade channelTrade) {
		this.channelTrade = channelTrade;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public boolean isBranch() {
		if(isCardRoleLogined() || isFenzhiRoleLogined()){
			return true;
		}
		return false;
	}
	
	public Collection<?> getReturnStates(){
		return ChnlTradeState.ALL.values();
	}
}
