package gnete.card.entity;

import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.MembDegradeMthdType;
import gnete.card.entity.type.MembUpgradeMthdType;

import java.util.Date;

public class MembClassTemp {
    private String membClass;

    private String className;

    private String branchCode;

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

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private String reserved4;

    private String reserved5;

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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
    public String getMembUpgradeMthdName() {
    	return MembUpgradeMthdType.ALL.get(this.membUpgradeMthd)==null ? "" : MembUpgradeMthdType.valueOf(this.membUpgradeMthd).getName();
    }
    public void setMembUpgradeMthd(String membUpgradeMthd) {
        this.membUpgradeMthd = membUpgradeMthd;
    }

    public String getMembUpgradeThrhd() {
        return membUpgradeThrhd;
    }

    public void setMembUpgradeThrhd(String membUpgradeThrhd) {
        this.membUpgradeThrhd = membUpgradeThrhd;
    }

    public String getMembDegradeMthd() {
        return membDegradeMthd;
    }
    public String getMembDegradeMthdName() {
        return MembDegradeMthdType.ALL.get(this.membDegradeMthd)==null ? "" : MembDegradeMthdType.valueOf(this.membDegradeMthd).getName();
    }
    public void setMembDegradeMthd(String membDegradeMthd) {
        this.membDegradeMthd = membDegradeMthd;
    }

    public String getMembDegradeThrhd() {
        return membDegradeThrhd;
    }

    public void setMembDegradeThrhd(String membDegradeThrhd) {
        this.membDegradeThrhd = membDegradeThrhd;
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
    public String getStatusName() {
    	return CommonState.ALL.get(this.status)==null ? "" : CommonState.valueOf(this.status).getName();
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
}