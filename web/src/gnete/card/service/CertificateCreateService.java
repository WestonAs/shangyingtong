package gnete.card.service;

import gnete.card.entity.BranchCertificate;
import gnete.etc.BizException;
import java.io.File;

/**
 * 
  * @Project: MyCard
  * @File: CertificateCreateService.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-7
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface CertificateCreateService {

	BranchCertificate getCertificateInfo(File certficateFile) throws BizException;

}
