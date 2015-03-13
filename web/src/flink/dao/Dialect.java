package flink.dao;

/**
 * @author haichen.ma
 *
 */
public interface Dialect {
	boolean supportsLimit();
	boolean supportsCount();
	String getLimitString(String sql, boolean hasOffset);
	String getLimitString(String sql, int offset, int limit);
	String getRecordCountString(String sql);
}
