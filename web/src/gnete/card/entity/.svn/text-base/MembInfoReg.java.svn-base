package gnete.card.entity;

import gnete.card.entity.type.CertType;
import gnete.card.entity.type.EducationType;
import gnete.card.entity.type.SexType;

import java.util.Date;

public class MembInfoReg {
    private Long membInfoRegId;

    private Long membInfoId;

    private String membClass;

    private String membLevel;

    private String cardIssuer;

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

    private String education;

    private Date updateTime;
    
	private Date insertTime;

    private Short membCardNum;

    private String updateBy;

    private String appointCard;

    private String remark;

    public Long getMembInfoRegId() {
        return membInfoRegId;
    }

    public void setMembInfoRegId(Long membInfoRegId) {
        this.membInfoRegId = membInfoRegId;
    }

    public Long getMembInfoId() {
        return membInfoId;
    }

    public void setMembInfoId(Long membInfoId) {
        this.membInfoId = membInfoId;
    }

    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
    }

    public String getMembLevel() {
        return membLevel;
    }

    public void setMembLevel(String membLevel) {
        this.membLevel = membLevel;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


    public Short getMembCardNum() {
        return membCardNum;
    }

    public void setMembCardNum(Short membCardNum) {
        this.membCardNum = membCardNum;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getAppointCard() {
        return appointCard;
    }

    public void setAppointCard(String appointCard) {
        this.appointCard = appointCard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getCredTypeName(){
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(this.credType).getName();
	}
    
    public String getSexName(){
		return SexType.ALL.get(this.sex) == null ? "" : SexType.valueOf(this.sex).getName();
	}
    
    public String getEducationName(){
		return EducationType.ALL.get(this.education) == null ? "" : EducationType.valueOf(this.education).getName();
	}
}