package gnete.card.entity;

import gnete.card.entity.type.IssType;
import gnete.card.entity.type.PrizeType;

import java.util.Date;

public class PrizeDef extends PrizeDefKey {
    private String prizeName;

    private Long prizeCnt;

    private String jinstType;

    private String jinstId;

    private String awdType;

    private String classId;

    private String remark;

    private String defOptr;

    private Date defTime;

    private String shortName;
    
    //新增字段
    /** 奖品总数 */
    private String totalAwdCnt;
    /** 剩余奖品总数 */
    private String leftAwdCnt;

    public String getTotalAwdCnt() {
		return totalAwdCnt;
	}

	public void setTotalAwdCnt(String totalAwdCnt) {
		this.totalAwdCnt = totalAwdCnt;
	}

	public String getLeftAwdCnt() {
		return leftAwdCnt;
	}

	public void setLeftAwdCnt(String leftAwdCnt) {
		this.leftAwdCnt = leftAwdCnt;
	}

	public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Long getPrizeCnt() {
        return prizeCnt;
    }

    public void setPrizeCnt(Long prizeCnt) {
        this.prizeCnt = prizeCnt;
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

    public String getAwdType() {
        return awdType;
    }

    public void setAwdType(String awdType) {
        this.awdType = awdType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    /**
     * 机构类型名
     */
    public String getJinstTypeName() {
		return IssType.ALL.get(jinstType) == null ? "" : IssType.valueOf(
				jinstType).getName();
	}

	/**
	 * 奖品类型名
	 */
	public String getAwdTypeName() {
		return PrizeType.ALL.get(awdType) == null ? "" : PrizeType.valueOf(
				awdType).getName();
	}
}