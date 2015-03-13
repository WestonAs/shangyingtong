package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BaodePointExcPara extends BaodePointExcParaKey {
    private BigDecimal expirExcRate;

    private String updateBy;

    private Date updateTime;
    
    private String branchName;
    
    private String merchName;

    private String ptClassName;

    public BigDecimal getExpirExcRate() {
        return expirExcRate;
    }

    public void setExpirExcRate(BigDecimal expirExcRate) {
        this.expirExcRate = expirExcRate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}
}