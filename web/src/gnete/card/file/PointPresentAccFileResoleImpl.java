package gnete.card.file;

import flink.util.StringUtil;
import gnete.card.entity.PointAccReg;
import gnete.etc.BizException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import gnete.card.entity.type.PointAccTransYype;

/**
 * <p>处理"积分赠送"文件的转换</p>
 * @Project: Card
 * @File: PointPresentAccFileResoleImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class PointPresentAccFileResoleImpl extends AbstractPointAccFileResolveImpl {
	@Override
	protected void checkResolve(String fileName, InputStream input) throws BizException {
		// TODO
	}

	@Override
	protected String getFileEncoding() {
		return super.DEFAULT_FILE_ENCODING;
	}

	@Override
	protected String getLineSplit() {
		return super.DEFAULT_LINE_SPLIT;
	}

	@Override
	protected PointAccReg getPointAccRegResolve(String fileName,String branchCode, List<String[]> resolveContentList) throws BizException {
		
		PointAccReg reg = super.getDefaulttPointAccRegResolve(fileName, branchCode, resolveContentList);
		
		// 检查号码格式是否正确，计算总金额
		BigDecimal amt = new BigDecimal("0");
		for(String[] record : resolveContentList){
			if(!StringUtil.isNumeric(record[0])){
				return null;
			}
			amt = amt.add(new BigDecimal(record[2]));
		}
		reg.setAmt(amt);
		return reg;
	}

	@Override
	protected String getPointAccTransType() {
		return PointAccTransYype.POINT_PRESENT.getValue();
	}

	@Override
	protected String getContentListRemark(List<String[]> resolveContentList) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

}
