package gnete.card.entity;

import gnete.card.entity.type.RoleType;
import java.util.Date;

public class LargessDef {
    private Long largessId;

    private String largessName;

    private String largessRule;

    private String updateBy;

    private Date updateTime;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private String reserved4;

    private String reserved5;
    
    private String jinstType;

	private String jinstId;
	
	private String branchCode;

    public String getLargessName() {
        return largessName;
    }

    public void setLargessName(String largessName) {
        this.largessName = largessName;
    }

    public String getLargessRule() {
        return largessRule;
    }

    public void setLargessRule(String largessRule) {
        this.largessRule = largessRule;
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

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getReserved5() {
        return reserved5;
    }

    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
    }

	public Long getLargessId() {
		return largessId;
	}

	public void setLargessId(Long largessId) {
		this.largessId = largessId;
	}

	public String getJinstType() {
		return jinstType;
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}
	
	public String getJinstTypeName() {
		return RoleType.ALL.get(this.jinstType) == null ? "" : RoleType.valueOf(
				this.jinstType).getName();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}