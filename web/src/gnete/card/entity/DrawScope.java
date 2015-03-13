package gnete.card.entity;

public class DrawScope {
    private Long id;

    private String drawId;

    private String scopeType;

    private String merNo;

    private String cardBinScope;

    private String cardSubclass;

    private String membClass;

    private String effDate;

    private String expirDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrawId() {
        return drawId;
    }

    public void setDrawId(String drawId) {
        this.drawId = drawId;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getCardBinScope() {
        return cardBinScope;
    }

    public void setCardBinScope(String cardBinScope) {
        this.cardBinScope = cardBinScope;
    }

    public String getCardSubclass() {
        return cardSubclass;
    }

    public void setCardSubclass(String cardSubclass) {
        this.cardSubclass = cardSubclass;
    }

    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
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
}