package gnete.card.entity;

import java.math.BigDecimal;

public class GdnhglIssuerCardStatic {

	private String		month;

	private String		cardId;

	private BigDecimal	incrAmt;

	private BigDecimal	perAmt;

	private BigDecimal	bal;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public BigDecimal getIncrAmt() {
		return incrAmt;
	}

	public void setIncrAmt(BigDecimal incrAmt) {
		this.incrAmt = incrAmt;
	}

	public BigDecimal getPerAmt() {
		return perAmt;
	}

	public void setPerAmt(BigDecimal perAmt) {
		this.perAmt = perAmt;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}
}