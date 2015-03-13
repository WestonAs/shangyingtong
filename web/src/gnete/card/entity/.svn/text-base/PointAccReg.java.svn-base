package gnete.card.entity;

import gnete.card.entity.state.PointAccState;
import gnete.card.entity.type.PointAccTransYype;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
  * @Project: Card
  * @File: PointAccReg.java
  * @See:
  * @description：
  * @author: 
  * @modified: aps-zbw
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-4-14
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
public class PointAccReg  implements java.lang.Cloneable{
    private Long pointAccId;

    private String fileName;

    private String transType;

    private Integer recordNum;

    private BigDecimal amt;

    private Date time;

    private String status;

    private String remark;
    
    private String cardIssuer;
    
    private String eventCode;
    
    private Long messageRegId;
    
    private String importDate;
    
    private Date updateTime;
    
    private String branchName;

    public Long getPointAccId() {
        return pointAccId;
    }

    public void setPointAccId(Long pointAccId) {
        this.pointAccId = pointAccId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getStatusName(){
    	return PointAccState.ALL.get(this.status) == null? "" : PointAccState.valueOf(this.status).getName();
    }
    
    public String getTransTypeName(){
    	return PointAccTransYype.ALL.get(this.transType) == null? "" : PointAccTransYype.valueOf(this.transType).getName();
    }

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public Long getMessageRegId() {
		return messageRegId;
	}

	public void setMessageRegId(Long messageRegId) {
		this.messageRegId = messageRegId;
	}
	
	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * @description:<li>增加CLONE方法</li>
	 */
	public Object clone() {
		PointAccReg pointAccReg = null;
		try {
			pointAccReg = (PointAccReg) super.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return pointAccReg;
	}
	
	/**
	 * @description:<li>改写toString()方法</li>
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}