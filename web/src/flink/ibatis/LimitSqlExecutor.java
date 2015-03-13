//package flink.ibatis;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.BeanUtils;
//
//import flink.dao.Dialect;
//import flink.dao.MySqlDialect;
//import flink.dao.OracleDialect;
//import flink.util.ReflectUtil;
//import com.ibatis.sqlmap.engine.exchange.DataExchange;
//import com.ibatis.sqlmap.engine.exchange.DataExchangeFactory;
//import com.ibatis.sqlmap.engine.execution.SqlExecutor;
//import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
//import com.ibatis.sqlmap.engine.mapping.parameter.BasicParameterMap;
//import com.ibatis.sqlmap.engine.mapping.parameter.BasicParameterMapping;
//import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
//import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
//import com.ibatis.sqlmap.engine.mapping.result.BasicResultMap;
//import com.ibatis.sqlmap.engine.mapping.result.BasicResultMapping;
//import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
//import com.ibatis.sqlmap.engine.mapping.result.ResultMapping;
//import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
//import com.ibatis.sqlmap.engine.scope.RequestScope;
//import com.ibatis.sqlmap.engine.type.LongTypeHandler;
//import com.ibatis.sqlmap.engine.type.TypeHandlerFactory;
//
///**
// * @author haichen.ma
// * @see SqlExecutor version 2.3.0.677
// * @deprecated
// */
//public class LimitSqlExecutor extends SqlExecutor {
//	private static final Log logger = LogFactory.getLog(LimitSqlExecutor.class);
//    
//	private Dialect dialect;
//
//	private boolean enableLimit = false;
//	private boolean enableCount = false;
//	private long offsetIndex;
//	private int pageSize;
//	
//	public long getOffsetIndex() {
//		return offsetIndex;
//	}
//
//	public void setOffsetIndex(long offsetIndex) {
//		this.offsetIndex = offsetIndex;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public Dialect getDialect() {
//		return dialect;
//	}
//
//	public void setDialect(Dialect dialect) {
//		this.dialect = dialect;
//	}
//
//	public boolean isEnableLimit() {
//		return enableLimit;
//	}
//
//	public void setEnableLimit(boolean enableLimit) {
//		this.enableLimit = enableLimit;
//	}
//	
//	public boolean isEnableCount() {
//		return enableCount;
//	}
//
//	public void setEnableCount(boolean enableCount) {
//		this.enableCount = enableCount;
//	}
//	
//	private boolean supportsLimit() {
//		return enableLimit && dialect != null && dialect.supportsLimit();		
//	}
//	
//	private boolean supportsCount() {
//		return enableCount && dialect != null && dialect.supportsLimit();	
//	}
//
//	public void executeQuery(RequestScope request, Connection conn, String sql, Object[] parameters, int skipResults, int maxResults, RowHandlerCallback callback) throws SQLException {
//		if (enableLimit && enableCount) {
//			throw new IllegalArgumentException("enableLimit and enableCount are mutually exclusive");
//		}
//		
//		RequestScope limitRequest = new RequestScope();
//		BeanUtils.copyProperties(request, limitRequest);
//		
//		try {
//			if (supportsLimit()) {
//				boolean hasOffset = getOffsetIndex() > 0;
//				sql = dialect.getLimitString(sql, hasOffset);
//				extendParameterMap(limitRequest, hasOffset);
//				parameters = extendParam(parameters, hasOffset);
//			}
//	
//			if (supportsCount()) {
//				sql = dialect.getRecordCountString(sql);
//				BasicResultMap resultMap = (BasicResultMap) ReflectUtil.getFieldValue(callback, "resultMap");
//				ResultMap map = cloneResultMap(resultMap);
//				
//				limitRequest.setResultMap(map);
//				ReflectUtil.setFieldValue(callback, "resultMap", ResultMap.class, map);
//			}
//			
//			if (logger.isDebugEnabled()) {
//				logger.debug(sql);
//			}
//			
//			super.executeQuery(limitRequest, conn, sql, parameters, skipResults, maxResults, callback);
//		}
//		catch (SQLException e) {
//			throw e;
//		}
//		finally {
//			reset();
//		}
//	}
//
//	private void extendParameterMap(RequestScope request, boolean hasOffset) {
//		ParameterMap map = request.getParameterMap();
//		ParameterMap newMap = new BasicParameterMap((SqlMapExecutorDelegate) ReflectUtil.getFieldValue(map, "delegate"));
//		BeanUtils.copyProperties(map, newMap);
//		ParameterMapping[] oldMappings = map.getParameterMappings();
//		List list = new ArrayList();
//		
//		if (oldMappings != null && oldMappings.length > 0) {
//			for (int i = 0; i < oldMappings.length; i++) {
//				BasicParameterMapping mapping = new BasicParameterMapping();
//				BeanUtils.copyProperties(oldMappings[i], mapping);
//				list.add(mapping);
//			}
//		}
//		
//		int typeHandleCount = hasOffset ? 2 : 1;
//		
//		for (int i = 0; i < typeHandleCount; i++) {
//			BasicParameterMapping mapping = new BasicParameterMapping();
//			mapping.setTypeHandler(new LongTypeHandler());
//			list.add(mapping);
//		}
//		
//		ParameterMapping[] mappings = (ParameterMapping[]) list.toArray(new ParameterMapping[0]);
//		ReflectUtil.setFieldValue(newMap, "parameterMappings", new ParameterMapping[]{}.getClass(), mappings);
//		request.setParameterMap(newMap);
//	}
//
//	/**
//	 * @param parameters
//	 * @return
//	 */
//	private Object[] extendParam(Object[] parameters, boolean hasOffset) {
//		List params = new ArrayList();
//		params.addAll(Arrays.asList(parameters));
//		
//		if (dialect instanceof MySqlDialect) {
//			if (hasOffset) {
//				params.add(new Long(offsetIndex));
//				params.add(new Long(pageSize));
//			}
//			else {
//				params.add(new Long(pageSize));
//			}
//		}
//		else if (dialect instanceof OracleDialect) {
//			if (hasOffset) {
//				params.add(new Long(offsetIndex + pageSize));
//				params.add(new Long(offsetIndex));
//			}
//			else {
//				params.add(new Long(pageSize));
//			}
//		}
//		
//		return params.toArray();
//	}
//
//	private void reset() {
//		enableLimit = false;
//		enableCount = false;
//	}
//
//	/**
//	 * @param resultMap
//	 */
//	private ResultMap cloneResultMap(BasicResultMap map) {
//		BasicResultMap resultMap = new BasicResultMap(map.getDelegate());
//		
//		resultMap.setId(map.getId());
//		resultMap.setResultClass(Long.class);
//		resultMap.setDataExchange(getDataExchange());
//		setResultMapings(resultMap);
//		setRemappableResultMappings(resultMap, map);
//		setNestedResultMappings(resultMap, map.getNestedResultMappings());
//		resultMap.setDiscriminator(map.getDiscriminator());
//		setGroupByPops(resultMap, map);
//		resultMap.setXmlName(map.getXmlName());
//		resultMap.setResource(map.getResource());
//		
//		return resultMap;
//	}
//
//	private void setRemappableResultMappings(BasicResultMap resultMap, BasicResultMap map) {
//		ThreadLocal mappings = (ThreadLocal) ReflectUtil.getFieldValue(map, "remappableResultMappings");
//		ReflectUtil.setFieldValue(resultMap, "remappableResultMappings", ThreadLocal.class, mappings);
//	}
//
//	private void setGroupByPops(BasicResultMap resultMap, BasicResultMap map) {
//		Set groupByPops = (Set) ReflectUtil.getFieldValue(map, "groupByProps");
//		ReflectUtil.setFieldValue(resultMap, "groupByProps", Set.class, groupByPops);
//	}
//
//	private void setNestedResultMappings(BasicResultMap resultMap, List nestedResultMappings) {
//		ReflectUtil.setFieldValue(resultMap, "nestedResultMappings", List.class, nestedResultMappings);
//	}
//
//	private void setResultMapings(BasicResultMap resultMap) {
//		BasicResultMapping mapping = new BasicResultMapping();
//		mapping.setColumnIndex(1);
//		mapping.setTypeHandler(new LongTypeHandler());
//		ReflectUtil.setFieldValue(resultMap, "resultMappings", 
//				new ResultMapping[]{}.getClass(), new ResultMapping[]{mapping});
//	}
//
//	private DataExchange getDataExchange() {
//		DataExchangeFactory dataExchangeFactory = new DataExchangeFactory(new TypeHandlerFactory());
//		
//		return dataExchangeFactory.getDataExchangeForClass(Long.class);
//	}
//}
