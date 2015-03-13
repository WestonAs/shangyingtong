package gnete.card.entity;

import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.PtExchgRuleType;
import gnete.card.entity.type.PtInstmMthdType;
import gnete.card.entity.type.PtUsageType;

import java.math.BigDecimal;

public class PointClassTemp {
    private String ptClass;

    private String className;

    private String branchCode;

    private BigDecimal ptDiscntRate;

    private String amtType;

    private String ptUsage;

    private String ptInstmMthd;

    private Short instmPeriod;

    private Short ptValidityCyc;

    private Short ptLatestCyc;

    private BigDecimal ptDeprecRate;

    private Short ptClassParam1;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private String reserved4;

    private String reserved5;

    private String classShortName;

    private String ptExchgRuleType;

    private BigDecimal ptRef;

    private String ptUseLimit;

    private String status;

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
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

    public BigDecimal getPtDiscntRate() {
        return ptDiscntRate;
    }

    public void setPtDiscntRate(BigDecimal ptDiscntRate) {
        this.ptDiscntRate = ptDiscntRate;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }
    public String getAmtTypeName() {
        return AmtType.ALL.get(this.amtType)==null ? "" : AmtType.valueOf(this.amtType).getName();
    }
    public String getPtUsage() {
        return ptUsage;
    }

    public void setPtUsage(String ptUsage) {
        this.ptUsage = ptUsage;
    }
	public String getPtUsageName() {
		return PtUsageType.ALL.get(this.ptUsage) == null ? "" : PtUsageType.valueOf(
				this.ptUsage).getName();
	}
    public String getPtInstmMthd() {
        return ptInstmMthd;
    }
    public String getPtInstmMthdName() {
		return PtInstmMthdType.ALL.get(this.ptInstmMthd) == null ? "" : PtInstmMthdType.valueOf(this.ptInstmMthd).getName();
    }
    public void setPtInstmMthd(String ptInstmMthd) {
        this.ptInstmMthd = ptInstmMthd;
    }

    public Short getInstmPeriod() {
        return instmPeriod;
    }

    public void setInstmPeriod(Short instmPeriod) {
        this.instmPeriod = instmPeriod;
    }

    public Short getPtValidityCyc() {
        return ptValidityCyc;
    }

    public void setPtValidityCyc(Short ptValidityCyc) {
        this.ptValidityCyc = ptValidityCyc;
    }

    public Short getPtLatestCyc() {
        return ptLatestCyc;
    }

    public void setPtLatestCyc(Short ptLatestCyc) {
        this.ptLatestCyc = ptLatestCyc;
    }

    public BigDecimal getPtDeprecRate() {
        return ptDeprecRate;
    }

    public void setPtDeprecRate(BigDecimal ptDeprecRate) {
        this.ptDeprecRate = ptDeprecRate;
    }

    public Short getPtClassParam1() {
        return ptClassParam1;
    }

    public void setPtClassParam1(Short ptClassParam1) {
        this.ptClassParam1 = ptClassParam1;
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

    public String getClassShortName() {
        return classShortName;
    }

    public void setClassShortName(String classShortName) {
        this.classShortName = classShortName;
    }

    public String getPtExchgRuleType() {
        return ptExchgRuleType;
    }
    public String getPtExchgRuleTypeName() {
    	return PtExchgRuleType.ALL.get(this.ptExchgRuleType)==null ? "" :PtExchgRuleType.valueOf(this.ptExchgRuleType).getName();
    }
    public void setPtExchgRuleType(String ptExchgRuleType) {
        this.ptExchgRuleType = ptExchgRuleType;
    }

    public BigDecimal getPtRef() {
        return ptRef;
    }

    public void setPtRef(BigDecimal ptRef) {
        this.ptRef = ptRef;
    }

    public String getPtUseLimit() {
        return ptUseLimit;
    }

    public void setPtUseLimit(String ptUseLimit) {
        this.ptUseLimit = ptUseLimit;
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
}