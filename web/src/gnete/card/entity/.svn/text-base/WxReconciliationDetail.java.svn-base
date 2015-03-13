package gnete.card.entity;

import gnete.card.entity.state.WxRecocitionProcState;
import gnete.card.entity.type.WxReconErrorType;

import java.math.BigDecimal;
import java.util.Date;

public class WxReconciliationDetail {
	
    private Long id;

    private String merchNo;

    private String settDate;

    private String errorType;

    private String sytTranSn;

    private Long cfFileSeq;

    private String proStatus;

    private String proUser;

    private Date proTime;

    private Long refRegid;
    
    private BigDecimal sytAmt;

    private BigDecimal zzptAmt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchNo() {
        return merchNo;
    }

    public void setMerchNo(String merchNo) {
        this.merchNo = merchNo;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getSytTranSn() {
        return sytTranSn;
    }

    public void setSytTranSn(String sytTranSn) {
        this.sytTranSn = sytTranSn;
    }

    public Long getCfFileSeq() {
        return cfFileSeq;
    }

    public void setCfFileSeq(Long cfFileSeq) {
        this.cfFileSeq = cfFileSeq;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public String getProUser() {
        return proUser;
    }

    public void setProUser(String proUser) {
        this.proUser = proUser;
    }

    public Date getProTime() {
        return proTime;
    }

    public void setProTime(Date proTime) {
        this.proTime = proTime;
    }

    public Long getRefRegid() {
        return refRegid;
    }

    public void setRefRegid(Long refRegid) {
        this.refRegid = refRegid;
    }
    
    public BigDecimal getSytAmt() {
		return sytAmt;
	}

	public void setSytAmt(BigDecimal sytAmt) {
		this.sytAmt = sytAmt;
	}

	public BigDecimal getZzptAmt() {
		return zzptAmt;
	}

	public void setZzptAmt(BigDecimal zzptAmt) {
		this.zzptAmt = zzptAmt;
	}

	public String getErrorTypeName() {
    	return WxReconErrorType.ALL.get(errorType) == null ? "" : WxReconErrorType.valueOf(errorType).getName();
    }
    
    public String getProStatusName() {
    	return WxRecocitionProcState.ALL.get(proStatus) == null ? "" : WxRecocitionProcState.valueOf(proStatus).getName();
    }
}