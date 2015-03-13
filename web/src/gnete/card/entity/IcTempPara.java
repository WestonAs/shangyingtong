package gnete.card.entity;

import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.YesOrNoFlag;

import java.math.BigDecimal;

public class IcTempPara {
    private String cardSubclass;

    private BigDecimal ecashbalance;

    private BigDecimal balanceLimit;

    private BigDecimal amountLimit;

    private String onlineCheck;
    
    // 数据库新增字段
    /** 是否自动圈存*/
    private String autoDepositFlag;
    
    /** 自动圈存金额 */
    private BigDecimal autoDepositAmt;

    public String getCardSubclass() {
        return cardSubclass;
    }

    public void setCardSubclass(String cardSubclass) {
        this.cardSubclass = cardSubclass;
    }

    public BigDecimal getEcashbalance() {
        return ecashbalance;
    }

    public void setEcashbalance(BigDecimal ecashbalance) {
        this.ecashbalance = ecashbalance;
    }

    public BigDecimal getBalanceLimit() {
        return balanceLimit;
    }

    public void setBalanceLimit(BigDecimal balanceLimit) {
        this.balanceLimit = balanceLimit;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public String getOnlineCheck() {
        return onlineCheck;
    }

    public void setOnlineCheck(String onlineCheck) {
        this.onlineCheck = onlineCheck;
    }

	public String getAutoDepositFlag() {
		return autoDepositFlag;
	}

	public void setAutoDepositFlag(String autoDepositFlag) {
		this.autoDepositFlag = autoDepositFlag;
	}

	public BigDecimal getAutoDepositAmt() {
		return autoDepositAmt;
	}

	public void setAutoDepositAmt(BigDecimal autoDepositAmt) {
		this.autoDepositAmt = autoDepositAmt;
	}
	
	/** 
	 *  是否新卡检查
	 * @return
	 */
	public String getOnlineCheckName() {
		return TrueOrFalseFlag.ALL.get(this.onlineCheck) == null ? "" : TrueOrFalseFlag.valueOf(this.onlineCheck).getName();
	}
	
	/**
	 * 是否自动圈存
	 * @return
	 */
	public String getAutoDepositFlagName() {
		return YesOrNoFlag.ALL.get(autoDepositFlag) == null ? "" : YesOrNoFlag.valueOf(autoDepositFlag).getName();
	}
}