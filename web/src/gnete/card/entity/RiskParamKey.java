package gnete.card.entity;

import gnete.card.entity.type.RiskType;

public class RiskParamKey {
    private String binNo;

    private String riskType;

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
    
    public String getRiskTypeName() {
    	return RiskType.ALL.get(this.riskType) == null ? "" : RiskType
    			.valueOf(this.riskType).getName();
    }
}