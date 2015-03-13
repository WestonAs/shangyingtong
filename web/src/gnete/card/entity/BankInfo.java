package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

/**
 * @File: BankInfo.java
 *
 * @description: 银行表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-5
 */
public class BankInfo {
    private String bankNo;

    private String bankType;

    private String bankName;

    private String addrNo;

    private String status;

    private String updateby;

    private Date updatetime;
    
    // 加入非表中字段
    private String bankTypeName; //行别名
    
    private String areaName; //addrNo对应的地区名
    
    private String cityCode; //城市
    
    private String parent; //省份

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddrNo() {
        return addrNo;
    }

    public void setAddrNo(String addrNo) {
        this.addrNo = addrNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}
	
	/**
	 * 取得状态名
	 * @return
	 */
	public String getStatusName() {
		return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}