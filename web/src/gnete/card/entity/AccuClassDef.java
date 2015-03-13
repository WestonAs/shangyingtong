package gnete.card.entity;

import gnete.card.entity.type.IssType;
import gnete.card.entity.type.SettMthdType;

import java.math.BigDecimal;

public class AccuClassDef {
	private String accuClass;

	private String className;

	private String cardIssuer;

	private String jinstType;

	private String jinstId;

	private String effDate;

	private String expirDate;

	private String settMthd;

	private BigDecimal settAmt;

	private String reserved1;

	private String reserved2;

	private String reserved3;

	private String reserved4;

	private String reserved5;

	private String jinstName;

	public String getAccuClass() {
		return accuClass;
	}

	public void setAccuClass(String accuClass) {
		this.accuClass = accuClass;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getJinstType() {
		return jinstType;
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

	public String getSettMthd() {
		return settMthd;
	}

	public void setSettMthd(String settMthd) {
		this.settMthd = settMthd;
	}

	public BigDecimal getSettAmt() {
		return settAmt;
	}

	public void setSettAmt(BigDecimal settAmt) {
		this.settAmt = settAmt;
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

	public String getJinstName() {
		return jinstName;
	}

	public void setJinstName(String jinstName) {
		this.jinstName = jinstName;
	}

	public String getJinstTypeName() {
		return IssType.ALL.get(jinstType) == null ? "" : IssType.valueOf(
				this.jinstType).getName();
	}

	public String getSettMthdName() {
		return SettMthdType.ALL.get(settMthd) == null ? "" : SettMthdType
				.valueOf(this.settMthd).getName();
	}

}