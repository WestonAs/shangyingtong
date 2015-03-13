package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.CostRecordState;
import gnete.card.entity.type.CostRecordType;
import gnete.card.entity.type.PayType;

import java.math.BigDecimal;
import java.util.Date;

public class CostRecord {
    private String id;

    private String branchCode;

    private String type;

    private BigDecimal amt;

    private String status;

    private Date genTime;

    private BigDecimal paidAmt;

    private Date payTime;

    private String makeId;

    private String payer;
    
    private String cardBin;
    
    private String cardSubClass;
    
    private BigDecimal cardNum;
    
    private String cardExampleId;
    
    private String remark;

    private String payType;
    
    /** 划账文件路径 */
    private String filePath;
    
    /** 是否已经开发票 */
    private String invoiceStatus;
    
    /** 开发票时间 */
    private Date invoiceTime;
    
    /** 终端编号 */
    private String termId;
    
    /** 周期。按年（表示第几年） */
    private Integer period;
    
    /** 次年缴费时间 */
    private Date nextPayTime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardSubClass() {
		return cardSubClass;
	}

	public void setCardSubClass(String cardSubClass) {
		this.cardSubClass = cardSubClass;
	}

	public BigDecimal getCardNum() {
		return cardNum;
	}

	public void setCardNum(BigDecimal cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardExampleId() {
		return cardExampleId;
	}

	public void setCardExampleId(String cardExampleId) {
		this.cardExampleId = cardExampleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Date getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Date invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getNextPayTime() {
		return nextPayTime;
	}

	public void setNextPayTime(Date nextPayTime) {
		this.nextPayTime = nextPayTime;
	}
	 
    /**
     * 费用类型名
     * @return
     */
    public String getTypeName() {
    	return CostRecordType.ALL.get(this.type) == null ? "" : CostRecordType.valueOf(this.type).getName();
    }
    
    /**
     * 状态名
     * @return
     */
    public String getStatusName() {
    	return CostRecordState.ALL.get(this.status) == null ? "" : CostRecordState.valueOf(this.status).getName();
    }
    

	/**
	 * 付款方式名
	 * 
	 * @return
	 */
	public String getPayTypeName() {
		return PayType.ALL.get(this.payType) == null ? "" : PayType.valueOf(this.payType).getName();
	}

	/**
	 * 是否开票。是或否
	 * @return
	 */
	public String getInvoiceStatusName() {
		return YesOrNoFlag.ALL.get(invoiceStatus) == null ? "" : YesOrNoFlag.valueOf(invoiceStatus).getName();
	}
}