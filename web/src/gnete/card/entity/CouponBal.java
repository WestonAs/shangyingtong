package gnete.card.entity;

import java.math.BigDecimal;

public class CouponBal extends CouponBalKey {
    private String issType;

    private String jinstId;

    private Integer jinstMercnt;

    private String effDate;

    private String expirDate;

    private BigDecimal balance;
    
    private String className;

	/** 新增属性 */
	private String groupName;
	
    public String getIssType() {
        return issType;
    }

    public void setIssType(String issType) {
        this.issType = issType;
    }

    public String getJinstId() {
        return jinstId;
    }

    public void setJinstId(String jinstId) {
        this.jinstId = jinstId;
    }

    public Integer getJinstMercnt() {
        return jinstMercnt;
    }

    public void setJinstMercnt(Integer jinstMercnt) {
        this.jinstMercnt = jinstMercnt;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}