package gnete.card.entity;

import gnete.card.entity.state.RmaState;
import gnete.card.entity.type.DSetTransType;

import java.math.BigDecimal;

public class MerchTransDSet extends BaseMSet {

	private Long transNum;

	private String payCode;

	private String recvCode;

	private String setDate;

	private String transType;

	private String chkId;
	
	private String rmaSn;
	
	private String rmaState;

	// 新增
	private String payName;

	private String recvName;

	// 手续费
	private BigDecimal feeAmount;

	public Long getTransNum() {
		return transNum;
	}

	public void setTransNum(Long transNum) {
		this.transNum = transNum;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getRecvCode() {
		return recvCode;
	}

	public void setRecvCode(String recvCode) {
		this.recvCode = recvCode;
	}

	public String getSetDate() {
		return setDate;
	}

	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChkId() {
		return chkId;
	}

	public void setChkId(String chkId) {
		this.chkId = chkId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getRecvName() {
		return recvName;
	}

	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	/**
	 * 交易类型名
	 */
	public String getTransTypeName() {
		return DSetTransType.ALL.get(this.transType) == null ? ""
				: DSetTransType.valueOf(this.transType).getName();
	}

	public String getRmaSn() {
		return rmaSn;
	}

	public void setRmaSn(String rmaSn) {
		this.rmaSn = rmaSn;
	}

	public String getRmaState() {
		return rmaState;
	}

	public void setRmaState(String rmaState) {
		this.rmaState = rmaState;
	}
	
	public String getRmaStateName() {
		return RmaState.ALL.get(this.rmaState) == null ? "" : RmaState.valueOf(this.rmaState).getName();
	}

}