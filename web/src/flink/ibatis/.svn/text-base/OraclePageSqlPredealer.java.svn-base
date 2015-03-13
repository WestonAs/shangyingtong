package flink.ibatis;

public class OraclePageSqlPredealer extends PageSqlPredealer {
	public String getPageSql(String sql) {
		String result = "select * from ( select row_.*, rownum rownum_ from ( ";
		result += sql;
		result += " ) row_ where <![CDATA[ rownum <= #toIndex#) where rownum_ > #offsetIndex# ]]>";
		
		return result;
	}

}
