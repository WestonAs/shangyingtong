package flink.dao;

public abstract class AbstractDialect implements Dialect {
	public boolean supportsLimit() {
		return true;
	}
	
	public boolean supportsCount() {
		return true;
	}
	
	public String getRecordCountString(String sql) {
		return "select count(*) as recordCount from ( " + sql + " ) t_";
	}
}
