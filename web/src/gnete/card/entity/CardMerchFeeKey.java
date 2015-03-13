package gnete.card.entity;

import java.math.BigDecimal;
import gnete.card.entity.type.MerchFeeTransType;

public class CardMerchFeeKey {
    private String branchCode;

    private String merchId;
    
    private String transType;
    
    private String cardBin;

    private BigDecimal ulMoney;
    
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransTypeName(){
		return MerchFeeTransType.ALL.get(this.transType) == null ? "" : MerchFeeTransType
				.valueOf(this.transType).getName();
	}
	
	public BigDecimal getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(BigDecimal ulMoney) {
		this.ulMoney = ulMoney;
	}
}