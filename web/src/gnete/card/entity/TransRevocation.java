package gnete.card.entity;

import gnete.card.entity.state.RevcState;
import gnete.card.entity.state.RevcType;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.util.Date;

public class TransRevocation {
    private String revcSn;

    private String posSn;

    private String posData;

    private String origPosSn;

    private String origPosData;

    private String transDatetime;

    private String sysTraceNum;

    private String acqInstIdCode;

    private String fwdInstIdCode;

    private String origTransDatetime;

    private String origSysTraceNum;

    private String origAcqInstIdCode;

    private String origFwdInstIdCode;

    private String retrivlRefNum;

    private String authrIdResp;

    private String platform;

    private String oldTransSn;

    private String merParentNo;

    private String initrNo;

    private String termlId;

    private String operator;

    private String revcType;

    private String origTransType;

    private String curCode;

    private BigDecimal revcAmt;

    private String settDate;

    private String respCode;

    private String revcStatus;

    private String remark;

    private Date rcvTime;

    private Date procTime;

    private String merNo;

    private String origCardIssuer;
    
    private BigDecimal merSettAmt;
    
    private BigDecimal issuerSettAmt;
    
    // 表中漏加字段
    private String cardIssuer;
    
    private BigDecimal returnAmt;
    
    private String classId;
    
    private BigDecimal merchPointSettAmt;

    private BigDecimal issuerPointSettAmt;
    
    private BigDecimal issuerCouponAmt;
    
    private BigDecimal merchCouponAmt;
    
    private String cardId;

    public String getRevcSn() {
        return revcSn;
    }

    public void setRevcSn(String revcSn) {
        this.revcSn = revcSn;
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }

    public String getPosData() {
        return posData;
    }

    public void setPosData(String posData) {
        this.posData = posData;
    }

    public String getOrigPosSn() {
        return origPosSn;
    }

    public void setOrigPosSn(String origPosSn) {
        this.origPosSn = origPosSn;
    }

    public String getOrigPosData() {
        return origPosData;
    }

    public void setOrigPosData(String origPosData) {
        this.origPosData = origPosData;
    }

    public String getTransDatetime() {
        return transDatetime;
    }

    public void setTransDatetime(String transDatetime) {
        this.transDatetime = transDatetime;
    }

    public String getSysTraceNum() {
        return sysTraceNum;
    }

    public void setSysTraceNum(String sysTraceNum) {
        this.sysTraceNum = sysTraceNum;
    }

    public String getAcqInstIdCode() {
        return acqInstIdCode;
    }

    public void setAcqInstIdCode(String acqInstIdCode) {
        this.acqInstIdCode = acqInstIdCode;
    }

    public String getFwdInstIdCode() {
        return fwdInstIdCode;
    }

    public void setFwdInstIdCode(String fwdInstIdCode) {
        this.fwdInstIdCode = fwdInstIdCode;
    }

    public String getOrigTransDatetime() {
        return origTransDatetime;
    }

    public void setOrigTransDatetime(String origTransDatetime) {
        this.origTransDatetime = origTransDatetime;
    }

    public String getOrigSysTraceNum() {
        return origSysTraceNum;
    }

    public void setOrigSysTraceNum(String origSysTraceNum) {
        this.origSysTraceNum = origSysTraceNum;
    }

    public String getOrigAcqInstIdCode() {
        return origAcqInstIdCode;
    }

    public void setOrigAcqInstIdCode(String origAcqInstIdCode) {
        this.origAcqInstIdCode = origAcqInstIdCode;
    }

    public String getOrigFwdInstIdCode() {
        return origFwdInstIdCode;
    }

    public void setOrigFwdInstIdCode(String origFwdInstIdCode) {
        this.origFwdInstIdCode = origFwdInstIdCode;
    }

    public String getRetrivlRefNum() {
        return retrivlRefNum;
    }

    public void setRetrivlRefNum(String retrivlRefNum) {
        this.retrivlRefNum = retrivlRefNum;
    }

    public String getAuthrIdResp() {
        return authrIdResp;
    }

    public void setAuthrIdResp(String authrIdResp) {
        this.authrIdResp = authrIdResp;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOldTransSn() {
        return oldTransSn;
    }

    public void setOldTransSn(String oldTransSn) {
        this.oldTransSn = oldTransSn;
    }

    public String getMerParentNo() {
        return merParentNo;
    }

    public void setMerParentNo(String merParentNo) {
        this.merParentNo = merParentNo;
    }

    public String getInitrNo() {
        return initrNo;
    }

    public void setInitrNo(String initrNo) {
        this.initrNo = initrNo;
    }

    public String getTermlId() {
        return termlId;
    }

    public void setTermlId(String termlId) {
        this.termlId = termlId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRevcType() {
        return revcType;
    }

    public void setRevcType(String revcType) {
        this.revcType = revcType;
    }

    public String getOrigTransType() {
        return origTransType;
    }

    public void setOrigTransType(String origTransType) {
        this.origTransType = origTransType;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public BigDecimal getRevcAmt() {
        return revcAmt;
    }

    public void setRevcAmt(BigDecimal revcAmt) {
        this.revcAmt = revcAmt;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRevcStatus() {
        return revcStatus;
    }

    public void setRevcStatus(String revcStatus) {
        this.revcStatus = revcStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(Date rcvTime) {
        this.rcvTime = rcvTime;
    }

    public Date getProcTime() {
        return procTime;
    }

    public void setProcTime(Date procTime) {
        this.procTime = procTime;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getOrigCardIssuer() {
        return origCardIssuer;
    }

    public void setOrigCardIssuer(String origCardIssuer) {
        this.origCardIssuer = origCardIssuer;
    }
    
    public String getRevcStatusName() {
		return RevcState.ALL.get(this.revcStatus) == null ? "" : RevcState
				.valueOf(this.revcStatus).getName();
	}
    
    public String getRevcTypeName() {
    	return RevcType.ALL.get(this.revcType) == null ? "" : RevcType
    			.valueOf(this.revcType).getName();
    }
    
    public String getOrigTransTypeName() {
    	return TransType.ALL.get(this.origTransType) == null ? "" : TransType
    			.valueOf(this.origTransType).getName();
    }

	public BigDecimal getMerSettAmt() {
		return merSettAmt;
	}

	public void setMerSettAmt(BigDecimal merSettAmt) {
		this.merSettAmt = merSettAmt;
	}

	public BigDecimal getIssuerSettAmt() {
		return issuerSettAmt;
	}

	public void setIssuerSettAmt(BigDecimal issuerSettAmt) {
		this.issuerSettAmt = issuerSettAmt;
	}

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

	public BigDecimal getReturnAmt() {
		return returnAmt;
	}

	public void setReturnAmt(BigDecimal returnAmt) {
		this.returnAmt = returnAmt;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public BigDecimal getMerchPointSettAmt() {
		return merchPointSettAmt;
	}

	public void setMerchPointSettAmt(BigDecimal merchPointSettAmt) {
		this.merchPointSettAmt = merchPointSettAmt;
	}

	public BigDecimal getIssuerPointSettAmt() {
		return issuerPointSettAmt;
	}

	public void setIssuerPointSettAmt(BigDecimal issuerPointSettAmt) {
		this.issuerPointSettAmt = issuerPointSettAmt;
	}

	public BigDecimal getIssuerCouponAmt() {
		return issuerCouponAmt;
	}

	public void setIssuerCouponAmt(BigDecimal issuerCouponAmt) {
		this.issuerCouponAmt = issuerCouponAmt;
	}

	public BigDecimal getMerchCouponAmt() {
		return merchCouponAmt;
	}

	public void setMerchCouponAmt(BigDecimal merchCouponAmt) {
		this.merchCouponAmt = merchCouponAmt;
	}
}