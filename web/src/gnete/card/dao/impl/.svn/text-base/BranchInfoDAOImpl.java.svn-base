package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.type.BranchType;
import gnete.card.entity.type.ProxyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BranchInfoDAOImpl extends BaseDAOIbatisImpl implements BranchInfoDAO {

    public String getNamespace() {
        return "BranchInfo";
    }
    
    public String getBranchCode(String sufix) {
    	return (String) this.queryForObject("getBranchCode", "%" + sufix);
    }
    
    public Paginater findBranch(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findBranch", params, pageNumber, pageSize);
    }
    
    public List<BranchInfo> findBranchList(Map<String, Object> params) {
    	return this.queryForList("findBranch", params);
    }
    
    public List<BranchInfo> findFenzhiByManage(String fenzhiCode) {
    	return this.queryForList("findFenzhiByManage", fenzhiCode);
    }
    
    public List<BranchInfo> findByType(String type) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("types", new String[]{type});
    	return this.queryForList("findByTypes", params);
    }
    
    public List<BranchInfo> findAgent(Map<String, Object> params) {
    	return this.queryForList("findAgent", params);
    }
    
    public List<BranchInfo> findByTypes(String[] branchTypes) {
    	if(branchTypes == null || branchTypes.length == 0) {
    		return java.util.Collections.<BranchInfo>emptyList();
    	}
    	
    	Map<String, Object> params = new HashMap<String, Object>(1);
    	params.put("types", branchTypes);
    	
    	return this.queryForList("findByTypes", params);
    }
    
    public List<BranchInfo> findByTypes(List<BranchType> manageBranch) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String[] types = new String[manageBranch.size()];
    	for (int i = 0; i < manageBranch.size(); i++) {
    		types[i] = manageBranch.get(i).getValue();
    	}
    	params.put("types", types);
    	return this.queryForList("findByTypes", params);
    }
    
    public List<BranchInfo> findByManange(String branchNo) {
    	return this.queryForList("findByManange", branchNo);
    }
    
    public List<BranchInfo> findCardByManange(String branchNo) {
    	return this.queryForList("findCardByManange", branchNo);
    }
    
    public List<BranchInfo> findCardProxy(String branchNo, ProxyType proxyType) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchNo", branchNo);
    	params.put("proxyType", proxyType.getValue());
    	
    	return this.queryForList("findCardProxy", params);
    }
    
    public List<BranchInfo> findCardByProxy(String branchNo) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchNo", branchNo);
    	return this.queryForList("findCardByProxy", params);
    }
    
    public List<BranchInfo> findSellByManange(String branchNo) {
    	return this.queryForList("findSellByManange", branchNo);
    }
    
    public List<BranchInfo> findByMerch(String merchNo) {
    	return this.queryForList("findByMerch", merchNo);
    }
    
    public Paginater findProxy(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findProxy", params, pageNumber, pageSize);
    }
    
    public List<BranchInfo> findByTypeAndManage(String roleType, String branchNo) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("roleType", roleType);
    	params.put("manageBranch", branchNo);
    	return this.queryForList("findByTypeAndManage", params);
    }
    
    public BranchInfo findBranchInfo(String branchCode) {
    	return (BranchInfo)this.queryForObject("findByPk", branchCode);
    }
    
    public List<BranchInfo> findProxyByProxy(String proxyId) {
    	return this.queryForList("findProxyByProxy", proxyId);
    }
    
    public List<BranchInfo> findByGroupId(String groupId) {
    	return this.queryForList("findByGroupId", groupId);
    }
    
    public List<BranchInfo> findMakeBranchByCardCode(String branchCode) {
    	return this.queryForList("findMakeBranchByCardCode", branchCode);
    }
    
    public List<BranchInfo> findProxyByBranchCode(String branchCode, ProxyType type) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchCode", branchCode);
    	params.put("proxyType", type.getValue());
    	
    	return this.queryForList("findProxyByBranchCode", params);
    }
    
    public List<BranchInfo> findCardBranchByDevelop(String developBranch,
    		BranchType branchType) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("developBranch", developBranch);
    	params.put("branchType", branchType.getValue());
    	
    	return this.queryForList("findCardBranchByDevelop", params);
    }
    
    public List<BranchInfo> findByLevelAndType(String branchLevel,
    		String branchType) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchLevel", branchLevel);
    	params.put("types", new String[]{branchType});
    	return this.queryForList("findByTypes", params);
    }
    
    public List<BranchInfo> findByLevelTypeAndParent(String branchLevel,
    		String branchType, String parent) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchLevel", branchLevel);
    	params.put("types", new String[]{branchType});
    	params.put("parent", parent);
    	return this.queryForList("findByTypes", params);
    }
    
    @SuppressWarnings("unchecked")
	public List<BranchInfo> findChildrenList(String branchCode) {
    	return this.queryForList("findChildrenList", branchCode);
    }
    
    @SuppressWarnings("unchecked")
	public List<BranchInfo> findTreeBranchList(String branchCode) {
    	return this.queryForList("findTreeBranchList", branchCode);
    }
    
    @SuppressWarnings("unchecked")
	public List<BranchInfo> findRootBranchList(String branchCode) {
    	return this.queryForList("findRootBranchList", branchCode);
    }
    
    @SuppressWarnings("unchecked")
	public List<BranchInfo> findBrotherList(String branchCode) {
    	return this.queryForList("findBrotherList", branchCode);
    }
    
    public BranchInfo findRootByBranch(String branchCode) {
    	return (BranchInfo) this.queryForObject("findRootByBranch", branchCode);
    }
    
    public BranchInfo findParentByBranch(String branchCode) {
    	return (BranchInfo) this.queryForObject("findParentByBranch", branchCode);
    }
    
    @SuppressWarnings("unchecked")
	public List<BranchInfo> findBalanceBranch() {
    	return this.queryForList("findBalanceBranch");
    }
    
    @Override
    public boolean isSuperBranch(String superBranchCode, String subBranchCode){
    	if(superBranchCode!=null && superBranchCode.equals(subBranchCode)){
    		return true;
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("superBranchCode", superBranchCode);
    	params.put("subBranchCode", subBranchCode);
		return (Long) this.queryForObject("isSuperBranch", params) > 0;
    }
    @Override
    public boolean isDirectManagedBy(String branchCode, String manageBranchCode){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("branchCode", branchCode);
    	params.put("manageBranchCode", manageBranchCode);
    	return (Long) this.queryForObject("isDirectManagedBy", params) > 0;
    }
}