package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.SettMthdType;
import java.math.BigDecimal;

public class GiftDef {
	private String giftId;

	private String giftName;
	
	private String giftChain;

	private String jinstType;

	private String jinstId;

	private String settMthd;

	private BigDecimal settAmt;

	private String effDate;

	private String expirDate;

	private String ptClass;

	private BigDecimal ptValue;

	private String giftUse;

	private String status;
	
	private String ptClassName;

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getJinstType() {
		return jinstType;
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}

	public String getSettMthd() {
		return settMthd;
	}

	public void setSettMthd(String settMthd) {
		this.settMthd = settMthd;
	}

	public BigDecimal getSettAmt() {
		return settAmt;
	}

	public void setSettAmt(BigDecimal settAmt) {
		this.settAmt = settAmt;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public BigDecimal getPtValue() {
		return ptValue;
	}

	public void setPtValue(BigDecimal ptValue) {
		this.ptValue = ptValue;
	}

	public String getGiftUse() {
		return giftUse;
	}

	public void setGiftUse(String giftUse) {
		this.giftUse = giftUse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}

	public String getJinstTypeName() {
		return IssType.ALL.get(this.jinstType) == null ? "" : IssType.valueOf(
				this.jinstType).getName();
	}

	public String getSettMthdName() {
		return SettMthdType.ALL.get(this.settMthd) == null ? "" : SettMthdType
				.valueOf(this.settMthd).getName();
	}

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}

	public String getGiftChain() {
		return giftChain;
	}

	public void setGiftChain(String giftChain) {
		this.giftChain = giftChain;
	}

}