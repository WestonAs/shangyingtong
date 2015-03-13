//package flink.ibatis;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//
//import flink.util.ReflectUtil;
//import com.ibatis.sqlmap.client.SqlMapClient;
//import com.ibatis.sqlmap.engine.execution.SqlExecutor;
//import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
//
///**
// * @author haichen.ma
// * @see SqlExecutor version 2.3.0.677
// * @deprecated
// */
//public class LimitSqlMapClientDaoSupport extends SqlMapClientDaoSupport {
//	private SqlExecutor sqlExecutor;
//
//	public void setSqlExecutor(SqlExecutor sqlExecutor) {
//		this.sqlExecutor = sqlExecutor;
//	}
//	
//	/**
//	 * 启用分页查询.
//	 * 
//	 * @param enableLimit
//	 */
//	public void setEnableLimit(boolean enableLimit, long offsetIndex, int pageSize) {
//		if (sqlExecutor instanceof LimitSqlExecutor) {
//			LimitSqlExecutor executor = (LimitSqlExecutor) sqlExecutor;
//			
//			executor.setEnableLimit(enableLimit);
//			executor.setOffsetIndex(offsetIndex);
//			executor.setPageSize(pageSize);
//		}
//	}
//	
//	/**
//	 * 启用统计笔数查询.
//	 * 
//	 * @param enableCount
//	 */
//	public void setEnableCount(boolean enableCount) {
//		if (sqlExecutor instanceof LimitSqlExecutor) {
//			((LimitSqlExecutor) sqlExecutor).setEnableCount(enableCount);
//		}
//	}
//	
//	public void initialize() {
//		if (sqlExecutor == null) {
//			return;
//		}
//		
//		SqlMapClient client = this.getSqlMapClient();
//		
//		if (client instanceof ExtendedSqlMapClient) {
//			ReflectUtil.setFieldValue(((ExtendedSqlMapClient) client).getDelegate(), 
//					"sqlExecutor", SqlExecutor.class, sqlExecutor);
//		}
//	}
//}
