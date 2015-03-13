package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.entity.DepartmentInfo;

@Repository
public class DepartmentInfoDAOImpl extends BaseDAOIbatisImpl implements DepartmentInfoDAO {

    public String getNamespace() {
        return "DepartmentInfo";
    }
    
    public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("find", params, pageNumber, pageSize);
    }
    
    public List<DepartmentInfo> findByBranch(String branchCode) {
    	return this.queryForList("findByBranch", branchCode);
    }
    
    public List<DepartmentInfo> findDeptList(Map<String, Object> params) {
    	return this.queryForList("find", params);
    }
}