package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ExternalCardImportResult {
	private Long impId;

	private Integer seqNo;

	private String cardIssuer;

	private String cardId;

	private BigDecimal amount;

	private BigDecimal ptAvlb;

	private String otType;

	private String procStatus;

	private String errMsg;

	private Date impDate;

	public Long getImpId() {
		return impId;
	}

	public void setImpId(Long impId) {
		this.impId = impId;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPtAvlb() {
		return ptAvlb;
	}

	public void setPtAvlb(BigDecimal ptAvlb) {
		this.ptAvlb = ptAvlb;
	}

	public String getOtType() {
		return otType;
	}

	public void setOtType(String otType) {
		this.otType = otType;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Date getImpDate() {
		return impDate;
	}

	public void setImpDate(Date impDate) {
		this.impDate = impDate;
	}

	/** 处理状态描述 */
	public String getProcStatusDesc() {
		if ("01".equals(this.getProcStatus())) {
			return "处理成功";
		} else if ("02".equals(this.getProcStatus())) {
			return "处理失败";
		} else {
			return "";
		}
	}

	/** 操作类型描述 */
	public String getOtTypeDesc() {
		if ("00".equals(this.getOtType())) {
			return "新开卡";
		} else if ("01".equals(this.getOtType())) {
			return "充值";
		} else {
			return "";
		}
	}
}