package gnete.card.entity;

import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.EducationType;
import gnete.card.entity.type.SexType;

import java.math.BigDecimal;
import java.util.Date;

public class MembReg {
    private Long membRegId;

    private String cardId;

    private String cardIssuer;

    private String membClass;

    private String custName;

    private String credType;

    private String credNo;

    private String address;

    private String sex;

    private String birthday;

    private String age;

    private String telNo;

    private String mobileNo;

    private String email;

    private String job;

    private String salary;

    private Date wedTime;

    private String education;

    private Date regTime;

    private String updateBy;

    private Date updateTime;
    
	private String	bankNo;
	private String	bankName;
	private String	accNo;
	private String	accName;
	private String	accAreaCode;
	private String	acctType;
	private String	acctMediaType;
    
    /*  */
	private String		branchName;
	private String		membClassName;
	private BigDecimal	avlbBal;
	private BigDecimal	fznAmt;
	private BigDecimal	ptAvlb;
	
	
    public String getCredTypeName(){
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(this.credType).getName();
	}
    
    public String getSexName(){
		return SexType.ALL.get(this.sex) == null ? "" : SexType.valueOf(this.sex).getName();
	}
    
    public String getEducationName(){
		return EducationType.ALL.get(this.education) == null ? "" : EducationType.valueOf(this.education).getName();
	}
    
	public String getAcctTypeName() {
		return AcctType.ALL.get(this.acctType) == null ? "" : AcctType.valueOf(this.acctType).getName();
	}
	
	/** 账户介质类型名  */
	public String getAcctMediaTypeName() {
		return AcctMediaType.ALL.get(acctMediaType) == null ? "" : AcctMediaType.valueOf(acctMediaType).getName();
	}
	
	/*
	 * =================== getters and setters following ===================
	 */
    
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCredType() {
        return credType;
    }

    public void setCredType(String credType) {
        this.credType = credType;
    }

    public String getCredNo() {
        return credNo;
    }

    public void setCredNo(String credNo) {
        this.credNo = credNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getWedTime() {
        return wedTime;
    }

    public void setWedTime(Date wedTime) {
        this.wedTime = wedTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
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
    
	public Long getMembRegId() {
		return membRegId;
	}

	public void setMembRegId(Long membRegId) {
		this.membRegId = membRegId;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccAreaCode() {
		return accAreaCode;
	}

	public void setAccAreaCode(String accAreaCode) {
		this.accAreaCode = accAreaCode;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctMediaType() {
		return acctMediaType;
	}

	public void setAcctMediaType(String acctMediaType) {
		this.acctMediaType = acctMediaType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMembClassName() {
		return membClassName;
	}

	public void setMembClassName(String membClassName) {
		this.membClassName = membClassName;
	}

	public BigDecimal getAvlbBal() {
		return avlbBal;
	}

	public void setAvlbBal(BigDecimal avlbBal) {
		this.avlbBal = avlbBal;
	}

	public BigDecimal getFznAmt() {
		return fznAmt;
	}

	public void setFznAmt(BigDecimal fznAmt) {
		this.fznAmt = fznAmt;
	}

	public BigDecimal getPtAvlb() {
		return ptAvlb;
	}

	public void setPtAvlb(BigDecimal ptAvlb) {
		this.ptAvlb = ptAvlb;
	}
	
}