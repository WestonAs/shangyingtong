package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MembClassDiscnt extends MembClassDiscntKey {
    private BigDecimal discnt;

    private Date updateTime;

    private String updateTy;

    public BigDecimal getDiscnt() {
        return discnt;
    }

    public void setDiscnt(BigDecimal discnt) {
        this.discnt = discnt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTy() {
        return updateTy;
    }

    public void setUpdateTy(String updateTy) {
        this.updateTy = updateTy;
    }
}