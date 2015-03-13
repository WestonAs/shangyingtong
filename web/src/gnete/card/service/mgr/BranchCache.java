package gnete.card.service.mgr;

import flink.util.SpringContext;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.DepartmentInfoDAO;
import gnete.card.dao.MerchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.DepartmentInfo;
import gnete.card.entity.MerchInfo;
import gnete.util.CacheMap;

/**
 * 机构/部门/商户 缓存管理
 */
public class BranchCache {
	private static final BranchCache			instance	= new BranchCache();

	private CacheMap<String, BranchInfo>		branchMap	= new CacheMap<String, BranchInfo>(3 * 60);	// 机构缓存Map

	private CacheMap<String, DepartmentInfo>	deptMap		= new CacheMap<String, DepartmentInfo>(3 * 60); // 部门缓存Map

	private CacheMap<String, MerchInfo>			merchMap	= new CacheMap<String, MerchInfo>(3 * 60);		// 商户缓存Map

	private BranchInfoDAO						branchInfoDAO;

	private DepartmentInfoDAO					departmentInfoDAO;

	private MerchInfoDAO						merchInfoDAO;

	public static BranchCache getInstance() {
		return instance;
	}

	private BranchCache() {
		if (this.branchInfoDAO == null) {
			branchInfoDAO = (BranchInfoDAO) SpringContext.getService("branchInfoDAOImpl");
		}
		if (this.departmentInfoDAO == null) {
			departmentInfoDAO = (DepartmentInfoDAO) SpringContext.getService("departmentInfoDAOImpl");
		}
		if (this.merchInfoDAO == null) {
			merchInfoDAO = (MerchInfoDAO) SpringContext.getService("merchInfoDAOImpl");
		}
	}

	public String getBranchName(String branchCode) {
		BranchInfo obj = this.getBranchInfo(branchCode);
		if (obj == null) {
			return "";
		} else {
			return obj.getBranchName();
		}
	}

	public String getDeptName(String deptId) {
		DepartmentInfo obj = (DepartmentInfo) this.getDept(deptId);
		if (obj == null) {
			return "";
		} else {
			return obj.getDeptName();
		}
	}

	public String getMerchName(String merchId) {
		MerchInfo obj = this.getMerchInfo(merchId);
		if (obj == null) {
			return "";
		} else {
			return obj.getMerchName();
		}
	}

	/**
	 * 获得机构信息
	 */
	public BranchInfo getBranchInfo(String branchCode) {
		CacheMap<String, BranchInfo>.ValueBean vb = branchMap.getValueBean(branchCode);
		if (vb == null) {
			synchronized (branchMap) { // 防止并发
				vb = branchMap.getValueBean(branchCode);
				if (vb == null) {
					BranchInfo obj = (BranchInfo) this.branchInfoDAO.findByPk(branchCode);
					branchMap.put(branchCode, obj);
					return obj;
				} else {
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}

	}

	/**
	 * 获得部门信息
	 */
	public DepartmentInfo getDept(String deptId) {
		CacheMap<String, DepartmentInfo>.ValueBean vb = deptMap.getValueBean(deptId);
		if (vb == null) {
			synchronized (deptMap) {
				vb = deptMap.getValueBean(deptId);
				if (vb == null) {
					DepartmentInfo obj = (DepartmentInfo) this.departmentInfoDAO.findByPk(deptId);
					deptMap.put(deptId, obj);
					return obj;
				} else {
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}
	}

	/**
	 * 获得商户信息信息
	 */
	public MerchInfo getMerchInfo(String merchId) {
		CacheMap<String, MerchInfo>.ValueBean vb = merchMap.getValueBean(merchId);
		if (vb == null) {
			synchronized (merchMap) {
				vb = merchMap.getValueBean(merchId);
				if (vb == null) {
					MerchInfo obj = (MerchInfo) merchInfoDAO.findByPk(merchId);
					merchMap.put(merchId, obj);
					return obj;
				} else {
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}
	}
}
