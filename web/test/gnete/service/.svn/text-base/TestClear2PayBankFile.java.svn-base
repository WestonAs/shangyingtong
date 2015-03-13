package gnete.service;

import gnete.BaseTest;
import gnete.card.clear2Pay.IClear2PayBankFileProcess;

public class TestClear2PayBankFile extends BaseTest {

	private IClear2PayBankFileProcess bankFileProcess;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.bankFileProcess = (IClear2PayBankFileProcess) this.getBean("clear2PayBankFileProcess");
	}

	 public void testPayBankFileGenerate() throws Exception {
//	     this.bankFileProcess.processClear2PayInfoBankBranchFile("20130315");
	     this.bankFileProcess.processClear2PayInfoBankBranchFile("20130319");
	     this.bankFileProcess.processClear2PayInfoBankBranchFile("20130320");
//	     this.bankFileProcess.processClear2PayInfoBankTotalFile("20120829");
	 }
	
	public void testPayBankTotal() throws Exception {
//		String name = this.bankFileProcess.processClear2PayFileArrange("20110703","20110705");
//		System.out.println(name);
	}
}
