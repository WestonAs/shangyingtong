package test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import flink.ibatis.OraclePageSqlPredealer;
import flink.ibatis.PageSqlPredealer;

public class PrepareDeploy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Resource[] res = new Resource[1];
		
		if (args.length == 0) {
			res[0] = new ClassPathResource("sqlMapConfig.xml");
		}
		else {
			res[0] = new ClassPathResource(args[0]);
		}

		try {
			getPageSqlPredealer().prepare(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static PageSqlPredealer getPageSqlPredealer() {
		return new OraclePageSqlPredealer();
	}
}
