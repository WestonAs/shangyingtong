package gnete.card.entity;

import gnete.card.entity.state.MemberCertifyState;
import gnete.card.entity.type.MembDegradeMthdType;
import gnete.card.entity.type.MembUpgradeMthdType;

import java.util.Date;

public class MembClassDef {
    private String membClass;

    private String className;

    private String cardIssuer;

    private String membLevel;

    private String membClassName;

    private String membUpgradeMthd;

    private String membUpgradeThrhd;

    private String membDegradeMthd;

    private String membDegradeThrhd;

    private String mustExpirDate;

    private String status;

    private String updateBy;

    private Date updateTime;

    private String reserved4;

    private String reserved5;
    
    private String reserved1;
    
    private String reserved2;
    
    private String reserved3;
    
    private String jinstId;

	private String jinstType;
	
	private String jinstName;
    
    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMembLevel() {
        return membLevel;
    }

    public void setMembLevel(String membLevel) {
        this.membLevel = membLevel;
    }

    public String getMembClassName() {
        return membClassName;
    }

    public void setMembClassName(String membClassName) {
        this.membClassName = membClassName;
    }

    public String getMembUpgradeMthd() {
        return membUpgradeMthd;
    }

    public void setMembUpgradeMthd(String membUpgradeMthd) {
        this.membUpgradeMthd = membUpgradeMthd;
    }

    public String getMembDegradeMthd() {
        return membDegradeMthd;
    }

    public void setMembDegradeMthd(String membDegradeMthd) {
        this.membDegradeMthd = membDegradeMthd;
    }

    public String getMustExpirDate() {
        return mustExpirDate;
    }

    public void setMustExpirDate(String mustExpirDate) {
        this.mustExpirDate = mustExpirDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved3() {
		return reserved3;
	}
	
	 public String getMembUpgradeMthdName(){
		return MembUpgradeMthdType.ALL.get(this.membUpgradeMthd) == null ? "" : MembUpgradeMthdType.valueOf(this.membUpgradeMthd).getName();
	}

	 public String getMembDegradeMthdName(){
		return MembDegradeMthdType.ALL.get(this.membDegradeMthd) == null ? "" : MembDegradeMthdType.valueOf(this.membDegradeMthd).getName();
	}
	 
	 public String getStatusName(){
		return MemberCertifyState.ALL.get(this.status) == null ? "" : MemberCertifyState.valueOf(this.status).getName();
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}

	public String getJinstType() {
		return jinstType;
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstName() {
		return jinstName;
	}

	public void setJinstName(String jinstName) {
		this.jinstName = jinstName;
	}

	public String getMembUpgradeThrhd() {
		return membUpgradeThrhd;
	}

	public void setMembUpgradeThrhd(String membUpgradeThrhd) {
		this.membUpgradeThrhd = membUpgradeThrhd;
	}

	public String getMembDegradeThrhd() {
		return membDegradeThrhd;
	}

	public void setMembDegradeThrhd(String membDegradeThrhd) {
		this.membDegradeThrhd = membDegradeThrhd;
	}

}