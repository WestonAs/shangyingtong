package gnete.card.entity;

import java.util.List;

public class CardPrivilegeGroup {
    private Long id;

    private String cardBranch;

    private String name;
    
    // 拥有的	权限点
    private List<PrivilegeResource> privilegeResources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<PrivilegeResource> getPrivilegeResources() {
		return privilegeResources;
	}

	public void setPrivilegeResources(List<PrivilegeResource> privilegeResources) {
		this.privilegeResources = privilegeResources;
	}

}