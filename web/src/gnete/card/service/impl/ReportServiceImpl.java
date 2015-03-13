package gnete.card.service.impl;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.ReportConfigParaDAO;
import gnete.card.entity.ReportConfigPara;
import gnete.card.entity.ReportConfigParaKey;
import gnete.card.entity.type.report.ReportType;
import gnete.card.service.ReportService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportConfigParaDAO reportConfigParaDAO;
	
	public boolean addReportConfigPara(
			ReportConfigPara reportConfigPara, String[] insCodes, String userId)
			throws BizException {
		Assert.notNull(reportConfigPara, "要添加的报表配置生成参数对象不能为空！");
		Assert.notTrue(ArrayUtils.isEmpty(insCodes), "要添加的机构数组不能为空！");
		
		reportConfigPara.setUpdateBy(userId);
		
		ReportConfigParaKey key = new ReportConfigParaKey();
		key.setInsType(reportConfigPara.getInsType());
		key.setReportType(reportConfigPara.getReportType());
		
		for (String insCode : insCodes) {
			key.setInsId(insCode);
			Assert.isNull(this.reportConfigParaDAO.findByPk(key), 
					"机构[" + insCode + "]、报表类型为[" + ReportType.valueOf(reportConfigPara.getReportType()).getName() + 
					"]的报表配置生成参数已经配置，请勿重复配置！");
			
			reportConfigPara.setInsId(insCode);
			reportConfigPara.setUpdateTime(new Date());
			this.reportConfigParaDAO.insert(reportConfigPara);
		}
		
		return true; 
		
	}

	public boolean deleteReportConfigPara(ReportConfigParaKey key)
			throws BizException {
		Assert.notNull(key, "主键不能为空！");
		ReportConfigPara reportConfigPara = (ReportConfigPara) this.reportConfigParaDAO.findByPk(key);
		Assert.notNull(reportConfigPara, "要删除的报表配置生成参数对象不能为空！");
		return this.reportConfigParaDAO.delete(key) > 0;
		
	}

	public boolean modifyReportConfigPara(ReportConfigPara reportConfigPara,
			String userId) throws BizException {
		Assert.notNull(reportConfigPara, "要修改的报表配置生成参数对象不能为空！");
		
		reportConfigPara.setUpdateBy(userId);
		reportConfigPara.setUpdateTime(new Date());
		return this.reportConfigParaDAO.update(reportConfigPara) > 0;
		
	}

}
