package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.SubacctType;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TransAccReg {
    private Long transAccId;

    private String outCardId;

    private String outAccId;

    /** 转出子账户类型 */
    private String subacctType;

    private BigDecimal avlbBal;

    private BigDecimal fznAmt;

    private String inCardId;

    private String inAccId;
    
    /** 转入子账户类型 */
    private String inSubacctType;

    private BigDecimal amt;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    /** 发卡机构 */
    private String cardBranch;
    
    /** 发起方 */
    private String initiator;

    
	/** 获取“状态”名称 */
	public String getStatusName(){
		return StringUtils.isEmpty(this.status) ? "" : RegisterState.valueOf(this.status).getName();
	}
	
	/** 获取“转出子账户类型”名称 */
	public String getSubacctTypeName(){
		return StringUtils.isEmpty(this.subacctType) ? "" : SubacctType.valueOf(this.subacctType).getName();
	}
	
	/** 获取“转入子账户类型”名称 */
	public String getInSubacctTypeName(){
		return StringUtils.isEmpty(this.inSubacctType) ? "" : SubacctType.valueOf(this.inSubacctType).getName();
	}
    
	/*
	 * ================================= getters and setters following ============================
	 */
	
    public Long getTransAccId() {
        return transAccId;
    }

    public void setTransAccId(Long transAccId) {
        this.transAccId = transAccId;
    }

    public String getOutCardId() {
        return outCardId;
    }

    public void setOutCardId(String outCardId) {
        this.outCardId = outCardId;
    }

    public String getOutAccId() {
        return outAccId;
    }

    public void setOutAccId(String outAccId) {
        this.outAccId = outAccId;
    }

    public String getSubacctType() {
        return subacctType;
    }

    public void setSubacctType(String subacctType) {
        this.subacctType = subacctType;
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

    public String getInCardId() {
        return inCardId;
    }

    public void setInCardId(String inCardId) {
        this.inCardId = inCardId;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getInAccId() {
		return inAccId;
	}

	public void setInAccId(String inAccId) {
		this.inAccId = inAccId;
	}

	public void setInSubacctType(String inSubacctType) {
		this.inSubacctType = inSubacctType;
	}

	public String getInSubacctType() {
		return inSubacctType;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	
}