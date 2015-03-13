package gnete.card.entity;

import gnete.etc.Symbol;

public class BranchParaKey {
    private String isBranch;

    private String paraCode;

    private String refCode;
    
    public BranchParaKey() {
	}

    public BranchParaKey(String paraCode, String refCode, String isBranch) {
		super();
		this.isBranch = isBranch;
		this.paraCode = paraCode;
		this.refCode = refCode;
	}

	public String getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(String isBranch) {
        this.isBranch = isBranch;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
    
    @Override
    public String toString() {
    	StringBuffer sb = new StringBuffer(128);
    	sb.append("参数代码").append(paraCode);
    	if (Symbol.YES.equals(isBranch)) {
    		sb.append(",机构代码").append(refCode);
    	} else {
    		sb.append(",商户代码").append(refCode);
    	}
    	return sb.toString();
    }
}