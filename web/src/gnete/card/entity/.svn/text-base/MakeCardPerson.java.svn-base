package gnete.card.entity;

import java.util.Date;
import gnete.card.entity.state.CommonState;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
  * @Project: MyCard
  * @File: MakeCardPerson.java
  * @See:
  * @description：<li>制卡厂商选择人信息Bean</li>
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-21
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class MakeCardPerson extends MakeCardPersonKey implements java.lang.Cloneable {

	private Date updateTime;

	private String updateUser;
	
	private String updateState;

	public MakeCardPerson() {

	}

	public MakeCardPerson(String branchNo, String state) {
		super(branchNo, state);
	}

	public MakeCardPerson(String branchNo, String state, String userId) {
		super(branchNo, state, userId);
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public String getUpdateState() {
		return updateState;
	}

	public void setUpdateState(String updateState) {
		this.updateState = updateState;
	}


	public Object clone() {
		MakeCardPerson cardPerson = null;

		try {
			cardPerson = (MakeCardPerson) super.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return cardPerson;
	}
	
	public String getStateName() {
		return (CommonState.ALL.get(super.getState()) == null) ? "" : CommonState.valueOf(super.getState()).getName();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}

}
