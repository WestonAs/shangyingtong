package gnete.card.entity;

import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;

import java.math.BigDecimal;
import java.util.Date;

public class IcCardActive {
    private String activeBatchId;

    private String cardId;

    private String cardClass;

    private String cardSubClass;

    private String cardBranch;

    private String activeBranch;

    private BigDecimal lastBalance;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;

    private String signature;

    private String randomSessionid;

    private String cardSn;

    private String arqc;

    private String aqdt;

    private String arpc;

    private String chkRespCode;
    
    //为补登新增字段
    private String writeScript;
    
    private String writeRespCode;
    
    private String writeCardFlag;

    private String reversalFlag;

    public String getActiveBatchId() {
        return activeBatchId;
    }

    public void setActiveBatchId(String activeBatchId) {
        this.activeBatchId = activeBatchId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getCardSubClass() {
        return cardSubClass;
    }

    public void setCardSubClass(String cardSubClass) {
        this.cardSubClass = cardSubClass;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getActiveBranch() {
        return activeBranch;
    }

    public void setActiveBranch(String activeBranch) {
        this.activeBranch = activeBranch;
    }

    public BigDecimal getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(BigDecimal lastBalance) {
        this.lastBalance = lastBalance;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRandomSessionid() {
        return randomSessionid;
    }

    public void setRandomSessionid(String randomSessionid) {
        this.randomSessionid = randomSessionid;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public String getArqc() {
        return arqc;
    }

    public void setArqc(String arqc) {
        this.arqc = arqc;
    }

    public String getArpc() {
        return arpc;
    }

    public void setArpc(String arpc) {
        this.arpc = arpc;
    }

    public String getChkRespCode() {
        return chkRespCode;
    }

    public void setChkRespCode(String chkRespCode) {
        this.chkRespCode = chkRespCode;
    }

	public String getAqdt() {
		return aqdt;
	}

	public void setAqdt(String aqdt) {
		this.aqdt = aqdt;
	}
	
	public String getWriteScript() {
		return writeScript;
	}

	public void setWriteScript(String writeScript) {
		this.writeScript = writeScript;
	}

	public String getWriteRespCode() {
		return writeRespCode;
	}

	public void setWriteRespCode(String writeRespCode) {
		this.writeRespCode = writeRespCode;
	}

	public String getWriteCardFlag() {
		return writeCardFlag;
	}

	public void setWriteCardFlag(String writeCardFlag) {
		this.writeCardFlag = writeCardFlag;
	}

	public String getReversalFlag() {
		return reversalFlag;
	}

	public void setReversalFlag(String reversalFlag) {
		this.reversalFlag = reversalFlag;
	}
	
	/**
	 * 获取状态名
	 * 
	 * @return
	 */
	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}

	/**
	 * 卡种类名
	 * 
	 * @return
	 */
	public String getCardClassName() {
		return CardType.ALL.get(this.cardClass) == null ? "" : CardType
				.valueOf(this.cardClass).getName();
	}

	/**
	 * 是否写卡成功标志名
	 * 
	 * @return
	 */
	public String getWriteCardFlagName() {
		return YesOrNoFlag.ALL.get(this.writeCardFlag) == null ? ""
				: YesOrNoFlag.valueOf(this.writeCardFlag).getName();
	}

	/**
	 * 冲正标志：00-未冲正，01-已冲正，02-冲正失败
	 * 
	 * @return
	 */
	public String getReversalFlagName() {
		return ReversalFlag.ALL.get(this.reversalFlag) == null ? ""
				: ReversalFlag.valueOf(this.reversalFlag).getName();
	}
}