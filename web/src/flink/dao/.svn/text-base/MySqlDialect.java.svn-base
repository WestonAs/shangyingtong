package flink.dao;

/**
 * @author haichen.ma
 *
 */
public class MySqlDialect extends AbstractDialect {

	public String getLimitString(String sql, boolean hasOffset) {
		sql = sql.trim();
		boolean isForUpdate = false;
		String forUpdate = " for update";
		
		if (sql.toLowerCase().endsWith(forUpdate) ) {
			sql = sql.substring(0, sql.length() - forUpdate.length());
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from (");
		pagingSelect.append(sql);
		
		if (hasOffset) {
			pagingSelect.append(" ) t_ limit ?, ?");
		}
		else {
			pagingSelect.append(" ) t_ limit ?");
		}

		if (isForUpdate) {
			pagingSelect.append(forUpdate);
		}

		return pagingSelect.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset > 0);
	}

	public boolean supportsLimit() {
		return true;
	}
}
