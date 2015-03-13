package gnete.card.entity;

import gnete.card.entity.type.BranchLevelType;
import gnete.card.entity.type.SetModeType;

public class BranchHasType extends BranchHasTypeKey {
	private String setMode;

	private String branchLevel;

	public String getSetMode() {
		return setMode;
	}

	public void setSetMode(String setMode) {
		this.setMode = setMode;
	}

	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}

	public String getSetModeName() {
		return SetModeType.ALL.get(this.setMode) == null ? "" : SetModeType
				.valueOf(this.setMode).getName();
	}

	/**
	 * 分支机构级别名
	 * 
	 * @return
	 */
	public String getBranchLevelName() {
		return BranchLevelType.ALL.get(this.branchLevel) == null ? ""
				: BranchLevelType.valueOf(this.branchLevel).getName();
	}
}