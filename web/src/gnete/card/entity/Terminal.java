package gnete.card.entity;

import gnete.card.entity.flag.CheckIpFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.TerminalState;
import gnete.card.entity.type.CommType;
import gnete.card.entity.type.EntryMode;
import gnete.card.entity.type.ExpenseType;

import java.math.BigDecimal;
import java.util.Date;

public class Terminal {
    private String termId;

    private String merchId;

    private String expenseType;

    private String linkWork;

    private String blackVer;

    private String checkIp;

    private String macAddress;

    private String keyIndex;

    private String pin;

    private String mac;

    private String pinlmk;

    private String maclmk;

    private String sessionKey;

    private String pkgFlag;

    private String posStatus;

    private String batchNo;

    private String userMemo;

    private String shopNo;

    private String pSeq;

    private String commType;

    private String srcTelNo;

    private String manageBranch;

    private String maintenance;

    private String updateBy;

    private Date updateTime;

    private String entryMode;
    
    private BigDecimal rentAmt;

	/** 添加非表字段-商户名称 */
	private String merchName;
	
	// 表中新加字段
	/** 商户地址 */
	private String merchAddress;

	/** POS联系人 */
	private String posContact;

	/** POS联系人电话 */
	private String posContactPhone;
	
	/** 是否单机产品终端 */
	private String singleProduct;
	
	/** 终端添加时间 */
	private Date createTime;
	
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getLinkWork() {
        return linkWork;
    }

    public void setLinkWork(String linkWork) {
        this.linkWork = linkWork;
    }

    public String getBlackVer() {
        return blackVer;
    }

    public void setBlackVer(String blackVer) {
        this.blackVer = blackVer;
    }

    public String getCheckIp() {
        return checkIp;
    }

    public void setCheckIp(String checkIp) {
        this.checkIp = checkIp;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPinlmk() {
        return pinlmk;
    }

    public void setPinlmk(String pinlmk) {
        this.pinlmk = pinlmk;
    }

    public String getMaclmk() {
        return maclmk;
    }

    public void setMaclmk(String maclmk) {
        this.maclmk = maclmk;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getPkgFlag() {
        return pkgFlag;
    }

    public void setPkgFlag(String pkgFlag) {
        this.pkgFlag = pkgFlag;
    }

    public String getPosStatus() {
        return posStatus;
    }

    public void setPosStatus(String posStatus) {
        this.posStatus = posStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getpSeq() {
        return pSeq;
    }

    public void setpSeq(String pSeq) {
        this.pSeq = pSeq;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getSrcTelNo() {
        return srcTelNo;
    }

    public void setSrcTelNo(String srcTelNo) {
        this.srcTelNo = srcTelNo;
    }

    public String getManageBranch() {
        return manageBranch;
    }

    public void setManageBranch(String manageBranch) {
        this.manageBranch = manageBranch;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
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

    public String getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
    }
    
	public String getPosStatusName() {
		return TerminalState.ALL.get(this.posStatus) == null ? ""
				: TerminalState.valueOf(this.posStatus).getName();
	}
	
	public String getExpenseTypeName() {
		return ExpenseType.ALL.get(this.expenseType) == null ? ""
				: ExpenseType.valueOf(this.expenseType).getName();
	}
	
	public String getCheckIpName() {
		return CheckIpFlag.ALL.get(this.checkIp) == null ? ""
				: CheckIpFlag.valueOf(this.checkIp).getName();
	}
	
	public String getEntryModeName() {
		return EntryMode.ALL.get(this.entryMode) == null ? ""
				: EntryMode.valueOf(this.entryMode).getName();
	}
	
	public String getCommTypeName() {
		return CommType.ALL.get(this.commType) == null ? ""
				: CommType.valueOf(this.commType).getName();
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public BigDecimal getRentAmt() {
		return rentAmt;
	}

	public void setRentAmt(BigDecimal rentAmt) {
		this.rentAmt = rentAmt;
	}

	public String getPSeq() {
		return pSeq;
	}

	public void setPSeq(String seq) {
		pSeq = seq;
	}

	public String getMerchAddress() {
		return merchAddress;
	}

	public void setMerchAddress(String merchAddress) {
		this.merchAddress = merchAddress;
	}

	public String getPosContact() {
		return posContact;
	}

	public void setPosContact(String posContact) {
		this.posContact = posContact;
	}

	public String getPosContactPhone() {
		return posContactPhone;
	}

	public void setPosContactPhone(String posContactPhone) {
		this.posContactPhone = posContactPhone;
	}

	public String getSingleProduct() {
		return singleProduct;
	}
	
	/**
	 * 是否单机产品名
	 * @return
	 */
	public String getSingleProductName() {
		return YesOrNoFlag.ALL.get(singleProduct) == null ? "" : YesOrNoFlag.valueOf(singleProduct).getName();
	}

	public void setSingleProduct(String singleProduct) {
		this.singleProduct = singleProduct;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}