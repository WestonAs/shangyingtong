package gnete.card.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import gnete.card.dao.BranchHasTypeDAO;
import gnete.card.entity.BranchHasType;

@Repository
public class BranchHasTypeDAOImpl extends BaseDAOIbatisImpl implements BranchHasTypeDAO {

    public String getNamespace() {
        return "BranchHasType";
    }
    
    public List<BranchHasType> findByBranch(String branchCode) {
    	return this.queryForList("findByBranch", branchCode);
    }
    
    public int deleteByBranch(String branchCode) {
    	return this.delete("deleteByBranch", branchCode);
    }
}