package gnete.card.entity;

import gnete.card.entity.type.DSetTransType;

public class RmaTransTypeLimitKey {
    private String insCode;

    private String transType;

    public String getInsCode() {
        return insCode;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public String getTransTypeName() {
		return DSetTransType.ALL.get(this.transType) == null ? "" : DSetTransType
				.valueOf(this.transType).getName();
	}
}