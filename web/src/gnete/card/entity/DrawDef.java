package gnete.card.entity;

import gnete.card.entity.state.DrawDefineState;
import gnete.card.entity.type.AmtType;
import gnete.card.entity.type.DrawType;
import gnete.card.entity.type.IssType;
import gnete.card.entity.type.ProbType;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.util.Date;

public class DrawDef {
	private String drawId;

	private String drawName;

	private String drawShortName;

	private String issType;

	private String issId;

	private String cardBinScope;

	private String drawType;

	private String effDate;

	private String expirDate;

	private String status;

	private String pinstType;

	private String pinstId;

	private String transType;

	private String amtType;

	private BigDecimal refAmt;

	private String probType;

	private BigDecimal probBase;

	private BigDecimal probMax;

	private BigDecimal probMin;

	private String defOptr;

	private Date defTime;

	private String reserved1;

	private String reserved2;

	private String reserved3;

	private String reserved4;

	private String reserved5;
	
	private String sequenceName;

	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public String getDrawName() {
		return drawName;
	}

	public void setDrawName(String drawName) {
		this.drawName = drawName;
	}

	public String getDrawShortName() {
		return drawShortName;
	}

	public void setDrawShortName(String drawShortName) {
		this.drawShortName = drawShortName;
	}

	public String getIssType() {
		return issType;
	}

	public void setIssType(String issType) {
		this.issType = issType;
	}

	public String getIssId() {
		return issId;
	}

	public void setIssId(String issId) {
		this.issId = issId;
	}

	public String getCardBinScope() {
		return cardBinScope;
	}

	public void setCardBinScope(String cardBinScope) {
		this.cardBinScope = cardBinScope;
	}

	public String getDrawType() {
		return drawType;
	}

	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPinstType() {
		return pinstType;
	}

	public void setPinstType(String pinstType) {
		this.pinstType = pinstType;
	}

	public String getPinstId() {
		return pinstId;
	}

	public void setPinstId(String pinstId) {
		this.pinstId = pinstId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public BigDecimal getRefAmt() {
		return refAmt;
	}

	public void setRefAmt(BigDecimal refAmt) {
		this.refAmt = refAmt;
	}

	public String getProbType() {
		return probType;
	}

	public void setProbType(String probType) {
		this.probType = probType;
	}

	public BigDecimal getProbBase() {
		return probBase;
	}

	public void setProbBase(BigDecimal probBase) {
		this.probBase = probBase;
	}

	public BigDecimal getProbMax() {
		return probMax;
	}

	public void setProbMax(BigDecimal probMax) {
		this.probMax = probMax;
	}

	public BigDecimal getProbMin() {
		return probMin;
	}

	public void setProbMin(BigDecimal probMin) {
		this.probMin = probMin;
	}

	public String getDefOptr() {
		return defOptr;
	}

	public void setDefOptr(String defOptr) {
		this.defOptr = defOptr;
	}

	public Date getDefTime() {
		return defTime;
	}

	public void setDefTime(Date defTime) {
		this.defTime = defTime;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getReserved5() {
		return reserved5;
	}

	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}
	
	public String getSequenceName() {
		return sequenceName;
	}
	
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	/**
	 * 状态名
	 */
	public String getStatusName() {
		return status == null ? "": DrawDefineState.valueOf(status).getName();
	}

	/**
	 * 抽奖方法名
	 */
	public String getDrawTypeName() {
		return DrawType.ALL.get(drawType) == null ? "" : DrawType.valueOf(
				drawType).getName();
	}
	
	/**
	 * 概率折算方法名
	 */
	public String getProbTypeName() {
		return ProbType.ALL.get(probType) == null ? "" : ProbType.valueOf(
				probType).getName();
	}

	/**
	 * 交易类型名
	 */
	public String getTransTypeName() {
		return TransType.ALL.get(transType) == null ? "" : TransType.valueOf(
				transType).getName();
	}
	
	/**
	 * 发行机构类型名
	 */
	public String getIssTypeName() {
		return IssType.ALL.get(issType) == null ? "" : IssType.valueOf(
				issType).getName();
	}
	
	/**
	 * 参与机构类型名
	 */
	public String getPinstTypeName() {
		return IssType.ALL.get(pinstType) == null ? "" : IssType.valueOf(
				pinstType).getName();
	}
	
	/**
	 * 金额类型名
	 */
	public String getAmtTypeName() {
		return AmtType.ALL.get(amtType) == null ? "" : AmtType.valueOf(
				amtType).getName();
	}
}