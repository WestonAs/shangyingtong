package gnete.card.entity;

import java.math.BigDecimal;

public class PlanSubRule extends PlanSubRuleKey {
    private BigDecimal ruleParam;

    private Integer sendNum;

    public BigDecimal getRuleParam() {
		return ruleParam;
	}

	public void setRuleParam(BigDecimal ruleParam) {
		this.ruleParam = ruleParam;
	}

	public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }
}