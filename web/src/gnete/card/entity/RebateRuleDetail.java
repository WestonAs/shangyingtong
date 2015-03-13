package gnete.card.entity;

import java.math.BigDecimal;

public class RebateRuleDetail extends RebateRuleDetailKey {
    private BigDecimal rebateUlimit;

	private BigDecimal rebateRate;

	public BigDecimal getRebateUlimit() {
		return rebateUlimit;
	}

	public void setRebateUlimit(BigDecimal rebateUlimit) {
		this.rebateUlimit = rebateUlimit;
	}

	public BigDecimal getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(BigDecimal rebateRate) {
		this.rebateRate = rebateRate;
	}

}