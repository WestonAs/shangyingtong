package gnete.card.entity;

import gnete.card.entity.type.ReleaseCardFeeTransType;
import java.math.BigDecimal;

public class ReleaseCardFeeKey {
	private String branchCode;

    private String cardBin;

    private String merchId;

    protected String transType;

    private BigDecimal ulMoney;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getTransTypeName() {
		return ReleaseCardFeeTransType.ALL.get(this.transType) == null ? "" : ReleaseCardFeeTransType.valueOf(this.transType).getName();
	}
    
}