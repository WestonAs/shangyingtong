package gnete.card.entity;

import gnete.card.entity.type.InsServiceType;
import gnete.card.entity.type.IssType;

public class InsServiceAuthorityKey {
    private String insId;

    private String insType;

    private String serviceId;

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getInsType() {
        return insType;
    }

    public void setInsType(String insType) {
        this.insType = insType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getInsTypeName() {
    	return IssType.ALL.get(this.insType) == null ? "" : IssType.valueOf(this.insType).getName();
    }

    public String getServiceIdName() {
    	return InsServiceType.ALL.get(this.serviceId) == null ? "" : InsServiceType.valueOf(this.serviceId).getName();
    }
}