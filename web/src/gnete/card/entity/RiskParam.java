package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RiskParam extends RiskParamKey {
    private BigDecimal preGuard;

    private BigDecimal resistance;

    private String updateUser;

    private Date updateTime;

    public BigDecimal getPreGuard() {
        return preGuard;
    }

    public void setPreGuard(BigDecimal preGuard) {
        this.preGuard = preGuard;
    }

    public BigDecimal getResistance() {
        return resistance;
    }

    public void setResistance(BigDecimal resistance) {
        this.resistance = resistance;
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
    
}