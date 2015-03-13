package gnete.card.entity;

import gnete.card.entity.type.IssType;

import java.math.BigDecimal;

public class PointBal extends PointBalKey {
	private String jinstType;

	private String jinstId;

	private String beginDate;

	private String endDate;

	private BigDecimal ptInc;

	private BigDecimal ptAvlb;

	private BigDecimal ptRef;

	private BigDecimal ptDiscntRate;

	private BigDecimal fznPt;
	
	private String jinstName;

	/** 新增属性 */
	private String groupName;
	
	public String getJinstType() {
		return jinstType;
	}

	public String getJinstTypeName() {
		return IssType.ALL.get(jinstType) == null ? "" : IssType.valueOf(
				jinstType).getName();
	}

	public void setJinstType(String jinstType) {
		this.jinstType = jinstType;
	}

	public String getJinstId() {
		return jinstId;
	}

	public void setJinstId(String jinstId) {
		this.jinstId = jinstId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public BigDecimal getPtInc() {
		return ptInc;
	}

	public void setPtInc(BigDecimal ptInc) {
		this.ptInc = ptInc;
	}

	public BigDecimal getPtAvlb() {
		return ptAvlb;
	}

	public void setPtAvlb(BigDecimal ptAvlb) {
		this.ptAvlb = ptAvlb;
	}

	public BigDecimal getPtRef() {
		return ptRef;
	}

	public void setPtRef(BigDecimal ptRef) {
		this.ptRef = ptRef;
	}

	public BigDecimal getPtDiscntRate() {
		return ptDiscntRate;
	}

	public void setPtDiscntRate(BigDecimal ptDiscntRate) {
		this.ptDiscntRate = ptDiscntRate;
	}

	public String getJinstName() {
		return jinstName;
	}

	public void setJinstName(String jinstName) {
		this.jinstName = jinstName;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getFznPt() {
		return fznPt;
	}

	public void setFznPt(BigDecimal fznPt) {
		this.fznPt = fznPt;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}