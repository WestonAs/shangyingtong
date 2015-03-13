package gnete.card.entity;

import gnete.card.entity.type.LimitType;

public class MerchGroupPointCouponLimitKey{
    private String limitId;

    private String limitType;

    private String groupId;

    private String merchId;


    public String getLimitId() {
        return limitId;
    }

    public void setLimitId(String limitId) {
        this.limitId = limitId;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }
    
    public String getLimitTypeName() {
    	return limitType == null?"":LimitType.valueOf(limitType).getName();
    }

}