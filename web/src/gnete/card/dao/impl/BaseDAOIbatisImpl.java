package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BaseDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.StringUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.spring.CustomIbatisDaoSupport;

/**
 * Title: BaseDaoIbatisImpl
 * 
 * <p> 
 * <li>基本的DAO实现CURD的操作</li>
 * <li>用PageDialect的接口实现分页</li>
 * </p>    
 * @copyright: (c) 2008 ylink inc
 * @author aps-mhc
 * @modified by:aps-zbw
 * @version 1.0
 * @since 1.0 2008-07-01
 */
public abstract class BaseDAOIbatisImpl extends CustomIbatisDaoSupport implements BaseDAO {
	private final static String INSERT = "insert";
	private final static String UPDATE = "update";
	private final static String DELETE = "delete";
	private final static String FIND_BY_PK = "findByPk";
	private final static String FIND_ALL = "findAll";
	private final static String FIND_BY_PK_WITHLOCK = "findByPkWithLock";
	private final static int BATCH_COUNT = 50;

	/**
	 * 获取命名空间.
	 * 
	 * @return
	 */
	protected abstract String getNamespace();

	protected String getClassShortName(Class theClass) {
		String className = theClass.getName();

		return className.substring(className.lastIndexOf('.') + 1);
	}

	@Autowired
	// 注入SqlMapClient
	public void setSqlMapClientBase(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	// 是否为只读dao, 只读dao 不允许对数据进行写操�?.
	private boolean readonly = false;

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * 获取statement name.
	 * 
	 * @param operate
	 * @return
	 */
	protected String getStatementName(String operate) {
		String namespace = getNamespace();

		return StringUtils.hasText(namespace) ? (namespace + "." + operate) : namespace;
	}

	protected SqlMapClientTemplate getSqlRunner() {
		return super.getSqlMapClientTemplate();
	}

	public int i(String statementName) {
		return this.getSqlRunner().update(getStatementName(statementName));
	}

	public int delete(Object pk) {
		return this.getSqlRunner().delete(getStatementName(DELETE), pk);
	}

	public List findAll() {
		return this.getSqlRunner().queryForList(getStatementName(FIND_ALL));
	}

	public Object findByPk(Object pk) {
		if (pk == null)
			return null;
		return this.getSqlRunner().queryForObject(getStatementName(FIND_BY_PK), pk);
	}

	public Object findByPkWithLock(Object pk) {
		if (pk == null)
			return null;
		return this.getSqlRunner().queryForObject(getStatementName(FIND_BY_PK_WITHLOCK), pk);
	}

	public Object insert(Object object) {
		return this.getSqlRunner().insert(getStatementName(INSERT), object);
	}

	public int update(Object object) {
		return this.getSqlRunner().update(getStatementName(UPDATE), object);
	}

	public boolean isExist(Object pk) {
		return findByPk(pk) != null;
	}

	protected void insert(String statementName, Object object) {
		this.getSqlRunner().insert(getStatementName(statementName), object);
	}

	public void insertBatch(final List list) {
		this.insertBatch(INSERT, list);
	}

	public void insertBatch(final String operate, final List list) {
		this.getSqlRunner().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				// 批量更新开始
				executor.startBatch();
				int count = 0;
				for (Object object : list) {
					count ++;
					executor.insert(getStatementName(operate), object);
					if (count % BATCH_COUNT == 0) {
						executor.executeBatch();
					}
				}
				// 提交
				executor.executeBatch();
				return null;
			}
		});
	}

	public void updateBatch(final List list) {
		this.updateBatch(UPDATE, list);
	}

	public void updateBatch(final String operate, final List list) {
		this.getSqlRunner().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				// 批量更新开始
				executor.startBatch();
				int count = 0;
				for (Object object : list) {
					count ++;
					executor.update(getStatementName(operate), object);
					if (count % BATCH_COUNT == 0) {
						executor.executeBatch();
					}
				}
				// 提交
				executor.executeBatch();
				return null;
			}
		});
	}

	protected int delete(String statementName, Object parameterObject) {
		return this.getSqlRunner().delete(getStatementName(statementName), parameterObject);
	}

	protected int update(String statementName, Object parameterObject) {
		return this.getSqlRunner().update(getStatementName(statementName), parameterObject);
	}

	protected List queryForList(String statementName) {
		return this.getSqlRunner().queryForList(getStatementName(statementName));
	}

	protected List queryForList(String statementName, Object parameterObject) {
		return this.getSqlRunner().queryForList(getStatementName(statementName), parameterObject);
	}

	protected Object queryForObject(String statementName) {
		return this.getSqlRunner().queryForObject(getStatementName(statementName));
	}

	protected Object queryForObject(String statementName, Object parameterObject) {
		return this.getSqlRunner().queryForObject(getStatementName(statementName), parameterObject);
	}

	/**
	 * 查询分页数据.
	 * <p>通过内置分页查询接口来查询分页信息</p>
	 * @param statementName
	 * @param parameterObject
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	protected Paginater queryForPage(String statementName, Map parameterMap, int pageNumber, int pageSize) {
		if (pageSize <= 0) {
			pageSize = Paginater.PAGE_SIZE;
		}

		/*
		String countStatementName = statementName + "_count";
		Long count = (Long) queryForObject(countStatementName, parameterMap);

		Paginater p = new Paginater(count.longValue(), pageNumber, pageSize);
		parameterMap.put("offsetIndex", new Long(p.getOffsetIndex()));
		parameterMap.put("toIndex", new Long(p.getOffsetIndex() + pageSize));

		String pageStatementName = statementName + "_page";
		p.setData(queryForList(pageStatementName, parameterMap));

		return p;
		*/
		return super.getSqlMapClientTemplate().queryForPageResult(getStatementName(statementName), parameterMap, pageNumber, pageSize);
	}

	/**
	 * 查询分页数据.
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param pageNumber
	 * @return
	 */
	protected Paginater queryForPage(String statementName, Map parameterMap, int pageNumber) {
		return queryForPage(statementName, parameterMap, pageNumber, Paginater.PAGE_SIZE);
	}

	/**
	 * 查询分页数据.
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param pageNumber
	 * @return
	 */
	protected Paginater queryForPage(String statementName, int pageNumber) {
		return queryForPage(statementName, new HashMap(), pageNumber, Paginater.PAGE_SIZE);
	}
}
