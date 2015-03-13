package gnete.card.entity;

import java.util.Date;

public class LogoConfig {
    private String branchNo;

    private String homeBig;

    private String homeSmall;

    private String loginSmall;

    private String updateBy;

    private Date updateTime;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getHomeBig() {
        return homeBig;
    }

    public void setHomeBig(String homeBig) {
        this.homeBig = homeBig;
    }

    public String getHomeSmall() {
        return homeSmall;
    }

    public void setHomeSmall(String homeSmall) {
        this.homeSmall = homeSmall;
    }

    public String getLoginSmall() {
        return loginSmall;
    }

    public void setLoginSmall(String loginSmall) {
        this.loginSmall = loginSmall;
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