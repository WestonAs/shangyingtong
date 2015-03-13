package gnete.service;

import java.util.List;
import gnete.BaseTest;
import gnete.card.entity.PointAccReg;
import gnete.card.service.IPointAccFileProcess;

/**
 * 
 * @Project: Card
 * @File: TestFetchFile.java
 * @See:
 * @description： <li>测试提取文件</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-19
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class TestFetchFile extends BaseTest {

	private IPointAccFileProcess fileAccProcess;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.fileAccProcess = (IPointAccFileProcess) this.getBean("pointAccFileProcess");
	}

	/**
	 * 
	 * @description：<li>测试从远程服务器端获得登记簿
	 * @throws Exception
	 * @version: 2011-4-19 下午04:06:20
	 * @See:
	 */

	public void testGetAccList() throws Exception {
		List<PointAccReg> list = fileAccProcess.getPointAccRegList("10005869", "20110420");

		for (PointAccReg accReg : list) {
			System.out.println("RecordNumber====={" + accReg.toString() + "}");
		}
	}

	/**
	 * 
	 * @description：<li>测试登记簿以及短信写表的整个过程</li>
	 * @throws Exception
	 * @version: 2011-4-19 下午04:06:41
	 * @See:
	 */
	public void testProcessAccList() throws Exception {
		long t1 = System.currentTimeMillis();
		this.fileAccProcess.processPointAccRegList("10005869", "20110420");
		long t2 = System.currentTimeMillis();
		System.out.println("per cost===={" + (t2 - t1) + "}ms");

	}

	/**
	 * 
	  * @description：<li>如上,增加批量处理(大概运行时间在5～7s)</li>
	  * @throws Exception  
	  * @version: 2011-4-21 上午11:02:45
	  * @See:
	 */
	public void testBatchProcessAccList() throws Exception {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 50; i++) {
			this.fileAccProcess.processPointAccRegList("10005869", "20110420");
		}
		long t2 = System.currentTimeMillis();

		System.out.println("batch cost===={" + (t2 - t1) + "}ms");
	}
}
