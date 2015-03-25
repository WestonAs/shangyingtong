package gnete.card.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class EcouponGenReg {
	private BigDecimal id;
	private String branchCode;
	private String cardSubClass;
	private String status;
	private BigDecimal couponNum;
	private String remark;
	private String bgLog;
	private Date insertTime;
	private Date updateTime;
	private String updateBy;
	
	
	
	
	/*public String getExpirMthdName() {
	return CardSubClassExpirMthd.ALL.get(this.expirMthd) == null ? ""
			: CardSubClassExpirMthd.valueOf(this.expirMthd).getName();
}

public boolean isSpecifyDateExpire(){
	return CardSubClassExpirMthd.SPECIFY_DATE.getValue().equals(this.expirMthd);
}

*//**
 * 状态名
 * 
 * @return
 *//*
public String getStatusName() {
	return CheckState.ALL.get(this.status) == null ? "" : CheckState
			.valueOf(this.status).getName();
}

*//** 密码类型名 *//*
public String getPwdTypeName() {
	return PasswordType.ALL.get(pwdType) == null ? "" : PasswordType
			.valueOf(pwdType).getName();
}

*//** 卡片类型名  *//*
public String getIcTypeName() {
	return CardFlag.ALL.get(this.icType) == null ? "" : CardFlag.valueOf(this.icType).getName();
}

public String getChangeFaceValueName() {
	return YesOrNoFlag.ALL.get(changeFaceValue) == null ? "" : YesOrNoFlag.valueOf(changeFaceValue).getName();
}

public String getDepositFlagName() {
	return YesOrNoFlag.ALL.get(depositFlag) == null ? "" : YesOrNoFlag.valueOf(depositFlag).getName();
}
public String getEcouponTypeName(){
	return ecouponTypeFlag.ALL.get(ecouponType)==null ? "": ecouponTypeFlag.valueOf(ecouponType).getName();
}

*//** 是否 纯IC卡或复合卡或M1卡 *//*
public boolean isIcOrCombineOrM1Type(){
	return CardFlag.IC.getValue().equals(getIcType()) || CardFlag.COMBINE.getValue().equals(getIcType())
			|| CardFlag.M1.getValue().equals(getIcType());
}
*/
	
	//setter,getter
	
	public String getBranchCode() {
		return branchCode;
	}
	public BigDecimal getId() {
	return id;
}
public void setId(BigDecimal id) {
	this.id = id;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getBgLog() {
	return bgLog;
}
public void setBgLog(String bgLog) {
	this.bgLog = bgLog;
}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getCardSubClass() {
		return cardSubClass;
	}
	public void setCardSubClass(String cardSubClass) {
		this.cardSubClass = cardSubClass;
	}
	
	public BigDecimal getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(BigDecimal couponNum) {
		this.couponNum = couponNum;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}



	