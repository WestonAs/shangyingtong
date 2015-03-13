package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.math.BigDecimal;

public class DepositPointBatReg {
    private Long depositBatRegId;

    private Long depositBatchId;

    private String cardId;

    private BigDecimal ptPoint;

    private BigDecimal refAmt;

    private String status;

    public Long getDepositBatRegId() {
        return depositBatRegId;
    }

    public void setDepositBatRegId(Long depositBatRegId) {
        this.depositBatRegId = depositBatRegId;
    }

    public Long getDepositBatchId() {
        return depositBatchId;
    }

    public void setDepositBatchId(Long depositBatchId) {
        this.depositBatchId = depositBatchId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getRefAmt() {
        return refAmt;
    }

    public void setRefAmt(BigDecimal refAmt) {
        this.refAmt = refAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public BigDecimal getPtPoint() {
		return ptPoint;
	}

	public void setPtPoint(BigDecimal ptPoint) {
		this.ptPoint = ptPoint;
	}
	
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState.valueOf(status).getName();
	}
}