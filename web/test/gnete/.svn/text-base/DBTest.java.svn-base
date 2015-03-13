package gnete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

public class DBTest {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@218.168.127.153:1521:orcl";

			String user = "card_dev";
			String password = "card_dev";

			conn = DriverManager.getConnection(url, user, password);

//			Statement stmt = null;
//			stmt = conn.createStatement();
//			stmt.execute(sql);
			
			PreparedStatement pstmt = null;
			String sql = " select * from ( select row_.*, rownum rownum_ from (      select TRANS_SN, POS_SN, POS_DATA, TRANS_DATETIME, SYS_TRACE_NUM, ACQ_INST_ID_CODE,      FWD_INST_ID_CODE, RETRIVL_REF_NUM, AUTHR_ID_RESP, TRANS_TYPE, CARD_ISSUER, PLATFORM, ACCT_ID,      CARD_ID, CARD_BIN, CARD_EXPIR_DATE, SETT_DATE, EIA_CARD_ID, SUBACCT_KIND, CLASS_ID, GIFT_ID,      MER_PARENT_NO, MER_NO, MER_NAME, TERML_ID, TRANS_AMT, CUR_CODE, ACCT_CUR_CODE, MER_PYA_AMT,      MER_PAID_AMT, CHDR_RVA_AMT, CHDR_PDP_AMT, XRATE, DED_COUPON_AMT, DED_OTHER_AMT, SETT_AMT,      OPERATOR_ID, PROC_TIME, RCV_TIME, PROC_STATUS, RESP_CODE, GOODS_AMT, GOODS_STATUS, REMARK,      MSG_AUTHN_CODE, RESERVED_1, RESERVED_2, RESERVED_3, RESERVED_4, RESERVED_5, MER_FEE,      MER_PROXY_FEE, POS_PROV_ID, POS_MANAGE_ID, BRANCH_CODE, MER_PROXY_NO, DED_SUBACCT_AMT, CENT_PROXY_NO     from TRANS    where                                    SETT_DATE >= '20100830'         and    SETT_DATE <= '20100830'                                  order by TRANS_SN DESC   ) row_ where  rownum <= ?) where rownum_ > ? ";
			pstmt = conn.prepareStatement(sql);
			
//			pstmt.setString(1, "20100830");
//			pstmt.setString(2, "20100830");
			pstmt.setInt(1, 20);
			pstmt.setInt(2, 0);
			System.out.println(new Date());
			pstmt.executeQuery();
			System.out.println(new Date());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
