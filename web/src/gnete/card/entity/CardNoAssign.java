package gnete.card.entity;

import java.util.Date;

public class CardNoAssign extends CardNoAssignKey {
    private Integer endCardNo;

    private Integer useCardNo;

    private String status;

    private String updateBy;

    private Date updateTime;

    public Integer getEndCardNo() {
        return endCardNo;
    }

    public void setEndCardNo(Integer endCardNo) {
        this.endCardNo = endCardNo;
    }

    public Integer getUseCardNo() {
        return useCardNo;
    }

    public void setUseCardNo(Integer useCardNo) {
        this.useCardNo = useCardNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}