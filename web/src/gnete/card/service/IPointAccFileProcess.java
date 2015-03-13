package gnete.card.service;

import gnete.card.entity.PointAccReg;
import gnete.etc.BizException;

import java.util.List;

/**
 * <p>
 *   <li>潮州移动文件接口处理类</li> 
 *   <li>内部整合了FTP下载及文件提取</li>
 * </p>
 * @Project: Card
 * @File: IPointAccFileProcess.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-4-14
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public interface IPointAccFileProcess {
	String FILE_SEPARATOR = "/";
    /**
     * 
      * @description：<li>根据传入的机构编号以及日期进行文件的提取</li>
      * @param branchCode
      * @param date
      * @return
      * @throws BizException  
      * @version: 2011-4-18 上午08:59:48
      * @See:
     */
	List<PointAccReg> getPointAccRegList(String branchCode, String date) throws BizException;

	/**
	 * 
	 * @description：<li>路径下的文件名称列表</li>
	 * @return
	 * @throws BizException
	 */
	String[] getFileList() throws BizException;
    
	/**
	 * 
	  * @description：<li>在上述接口的基础上增加针对命令表的批量写入支持</li>
	  * @param branchCode
	  * @param date
	  * @throws BizException  
	  * @version: 2011-4-18 上午08:59:53
	  * @See:
	 */
	void processPointAccRegList(String branchCode, String date) throws BizException;
}
