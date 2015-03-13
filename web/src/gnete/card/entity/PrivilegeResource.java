package gnete.card.entity;

import java.io.Serializable;

import flink.util.WebResource;

public class PrivilegeResource extends WebResource implements Serializable {
    private Long id;

    private String limitId;

    private String url;

    private String param;

    private String isEntry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLimitId() {
        return limitId;
    }

    public void setLimitId(String limitId) {
        this.limitId = limitId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getIsEntry() {
        return isEntry;
    }

    public void setIsEntry(String isEntry) {
        this.isEntry = isEntry;
    }
    
    @Override
	public String getParamValue() {
		return this.param;
	}
}