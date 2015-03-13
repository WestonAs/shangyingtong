package gnete.card.entity;

import gnete.card.entity.state.ExternalCardImportState;

import java.math.BigDecimal;
import java.util.Date;

public class ExternalCardImportReg {

	private Long id;

	private String binNo;

	private String cardBranch;

	private String cardSubclass;

	private String fileName;

	private BigDecimal totalAmt;

	private Integer totalCount;

	private String status;

	private String updateBy;

	private Date updateTime;

	private String memo;
	
	/** 总通用积分 */
	private BigDecimal totalPoint;
	/** 积分类型 */
	private String ptClass;
	/** 操作机构 */
	private String branchCode;

	/** 上传类型：00 外部卡导入 01 开卡 */
	private String uptype;
	
	/** 页面手工备注 */
	private String remark;
	
	// 加入非表中字段
	private String cardSubclassName;

	/**
	 * 获得状态名
	 */
	public String getStatusName() {
		return ExternalCardImportState.ALL.get(this.status) == null ? ""
				: ExternalCardImportState.valueOf(this.status).getName();
	}
	
	/**
	 * 是否是外部号码开卡
	 */
	public boolean isExternalNumMakeCard(){
		return "01".equals(uptype);
	}
	
	//------------------------------------- getter and setter followed---------------------
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUptype() {
		return uptype;
	}

	public void setUptype(String uptype) {
		this.uptype = uptype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardSubclassName() {
		return cardSubclassName;
	}

	public void setCardSubclassName(String cardSubclassName) {
		this.cardSubclassName = cardSubclassName;
	}
}