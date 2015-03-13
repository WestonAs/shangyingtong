package gnete.card.web.dayswitch;

import java.net.BindException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import flink.util.DateUtil;
import flink.util.Paginater;
import gnete.card.dao.DaySwitchDAO;
import gnete.card.dao.ProcDAO;
import gnete.card.entity.DaySwitch;
import gnete.card.entity.flag.DaySwitchFlag;
import gnete.card.entity.flag.SwitchFlag;
import gnete.card.entity.state.SetState;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.UserLogType;
import gnete.card.web.BaseAction;
import gnete.etc.BizException;

/**
 * 日切信息查询
 * @author lib
 *
 */
public class DaySwitchQueryAction extends BaseAction{

	@Autowired
	private DaySwitchDAO daySwitchDAO;
	@Autowired
	private ProcDAO procDAO;
	
	private DaySwitch daySwitch;
	private Paginater page;
	private Collection setFlagList; // 结算状态列表
	private Collection switchFlagList; // 日切标志列表
	boolean showButton = false;
	boolean showFeeButton = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		this.setFlagList = SetState.ALL.values();
		this.switchFlagList = SwitchFlag.ALL.values();
		if (daySwitch != null) {
			params.put("switchFlag", daySwitch.getSwitchFlag());
			params.put("setFlag", daySwitch.getSetFlag());
		}
		
		// 只有运营中心、运营中心部门有权限查询
		if (!RoleType.CENTER.getValue().equals(getLoginRoleType())
				& !RoleType.CENTER_DEPT.getValue().equals(getLoginRoleType())) {
			throw new BizException("非营运中心、营运中心部门没有权限查询！");
		}
			
		this.page = this.daySwitchDAO.findDaySwitch(params,this.getPageNumber(), this.getPageSize());

		this.log("查询日切信息成功", UserLogType.SEARCH);
		return LIST;
	}
	
	//取得日切记录的明细
	public String detail() throws Exception {
		this.daySwitch = (DaySwitch) this.daySwitchDAO.findByPk(this.daySwitch.getCurrDate());
		String success = SwitchFlag.SUCCESS.getValue();
		
		// 日切状态不为成功时，显示手动日切
		if(!success.equals(this.daySwitch.getSwitchFlag())){
			showButton = true;
		}
		
		// 手动计费
		String priceSuccess = DaySwitchFlag.SUCCESS.getValue(); 
		boolean feeFlag = !priceSuccess.equals(this.daySwitch.getFeeFlag())||
		            !priceSuccess.equals(this.daySwitch.getRefundFlag())||
					!priceSuccess.equals(this.daySwitch.getCenterFeeFlag());
		
		// 判断日切时间是否为月底
		String currDate = this.daySwitch.getCurrDate();
		boolean eom = DateUtil.isEndOfMonth(currDate);
		
		// 如果是月底，还要检查商户代理分润状态
		if(eom){
			feeFlag = feeFlag || !priceSuccess.equals(this.daySwitch.getMerProxyShareFlag());
		}
		
		if(success.equals(this.daySwitch.getSwitchFlag()) && feeFlag){
			this.showFeeButton = true;
		}
		
		this.log("查询日切["+this.daySwitch.getCurrDate()+"]明细信息成功", UserLogType.SEARCH);
		return DETAIL;
	}
	
	// 手动日切
	public void manualHandle() throws Exception {
		
		Map<String, Object> params = this.procDAO.spCardDayEnd(this.daySwitch.getCurrDate());
		String msg = null;
		params.get("p_errorcode");
		params.get("p_errordesc");
		
		if("0".equals(params.get("p_errorcode").toString())){
			msg = "手动日切工作日期为["+this.daySwitch.getCurrDate()+"]的日切信息成功！ "+params.get("p_errordesc")+"";
			this.addActionMessage("/switchQuery/daySwitchQuery/list.do", msg);
			this.log(msg, UserLogType.UPDATE);
			//return SUCCESS;
		}else{
			msg = "手动日切工作日期为["+this.daySwitch.getCurrDate()+"]的日切信息失败！ "+params.get("p_errordesc")+"";
			throw new BindException(msg);
		}
	}
	
	// 手动计费
	public void manualFee() throws Exception {
		
		Map<String, Object> params = this.procDAO.spCardSettDayEnd(this.daySwitch.getCurrDate());
		String msg = null;
		params.get("p_errorcode");
		params.get("p_errordesc");
		
		if("0".equals(params.get("p_errorcode").toString())){
			msg = "工作日期为["+this.daySwitch.getCurrDate()+"]的手动计费成功！ "+params.get("p_errordesc")+"";
			this.addActionMessage("/switchQuery/daySwitchQuery/list.do", msg);
			this.log(msg, UserLogType.UPDATE);
			//return SUCCESS;
		}else{
			msg = "工作日期为["+this.daySwitch.getCurrDate()+"]的手动计费失败！ "+params.get("p_errordesc")+"";
			throw new BindException(msg);
		}
	}
	
	public DaySwitch getDaySwitch() {
		return daySwitch;
	}

	public void setDaySwitch(DaySwitch daySwitch) {
		this.daySwitch = daySwitch;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public Collection getSetFlagList() {
		return setFlagList;
	}

	public void setSetFlagList(Collection setFlagList) {
		this.setFlagList = setFlagList;
	}

	public Collection getSwitchFlagList() {
		return switchFlagList;
	}

	public void setSwitchFlagList(Collection switchFlagList) {
		this.switchFlagList = switchFlagList;
	}

	public boolean isShowButton() {
		return showButton;
	}

	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}

	public boolean isShowFeeButton() {
		return showFeeButton;
	}

	public void setShowFeeButton(boolean showFeeButton) {
		this.showFeeButton = showFeeButton;
	}

}
