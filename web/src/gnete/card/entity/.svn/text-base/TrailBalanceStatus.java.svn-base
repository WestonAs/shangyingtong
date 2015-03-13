package gnete.card.entity;

import gnete.card.entity.flag.BalanceFlag;

import java.util.Date;

public class TrailBalanceStatus extends TrailBalanceStatusKey {
    private String balanceFlag;

    private String subacctBalanceFlag;

    private String accuBalanceFlag;

    private String pointBalanceFlag;

    private String couponBalanceFlag;

    private Date updateTime;
    
    private String branchName;

    public String getBalanceFlag() {
        return balanceFlag;
    }

    public void setBalanceFlag(String balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    public String getSubacctBalanceFlag() {
        return subacctBalanceFlag;
    }

    public void setSubacctBalanceFlag(String subacctBalanceFlag) {
        this.subacctBalanceFlag = subacctBalanceFlag;
    }

    public String getPointBalanceFlag() {
        return pointBalanceFlag;
    }

    public void setPointBalanceFlag(String pointBalanceFlag) {
        this.pointBalanceFlag = pointBalanceFlag;
    }

    public String getCouponBalanceFlag() {
        return couponBalanceFlag;
    }

    public void setCouponBalanceFlag(String couponBalanceFlag) {
        this.couponBalanceFlag = couponBalanceFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBalanceFlagName(){
		return BalanceFlag.ALL.get(this.balanceFlag) == null ? "" : BalanceFlag.valueOf(this.balanceFlag).getName();
	}
	
	public String getAccuBalanceFlag() {
		return accuBalanceFlag;
	}

	public void setAccuBalanceFlag(String accuBalanceFlag) {
		this.accuBalanceFlag = accuBalanceFlag;
	}
	
	public String getSubAcctBalanceFlagName(){
		return BalanceFlag.ALL.get(this.subacctBalanceFlag) == null ? "" : BalanceFlag.valueOf(this.subacctBalanceFlag).getName();
	}
	
	public String getAccuBalanceFlagName(){
		return BalanceFlag.ALL.get(this.accuBalanceFlag) == null ? "" : BalanceFlag.valueOf(this.accuBalanceFlag).getName();
	}
	
	public String getPointBalanceFlagName(){
		return BalanceFlag.ALL.get(this.pointBalanceFlag) == null ? "" : BalanceFlag.valueOf(this.pointBalanceFlag).getName();
	}
	
	public String getCouponBalanceFlagName(){
		return BalanceFlag.ALL.get(this.couponBalanceFlag) == null ? "" : BalanceFlag.valueOf(this.couponBalanceFlag).getName();
	}

}