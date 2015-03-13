package gnete.card.service;

import java.io.File;

import flink.security.CertOperateException;
import flink.security.ICertificateKeyStore;
import gnete.card.entity.UserCertificate;
import gnete.card.entity.UserInfo;

import java.util.List;
import java.util.Map;
/**
 * 
 * @Project: MyCard
 * @File: ICardCertificateKeyStore.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public interface ICardCertificateKeyStore extends ICertificateKeyStore {	
	
	/**
	 * 
	 * @description：   将传递的如下参数转换为用户证书表实体类
	 * @param certFile: 上传的证书文件
	 * @param branchCode: 所选中的分支机构
	 * @param deptId: 所选中的部门编号
	 * @param loginUser: 登陆用户
	 * @return
	 * @throws CertOperateException
	 * @version: 2010-12-8 下午01:27:09
	 * @See:
	 */
	UserCertificate getUserCertificate(File certFile, String branchCode, String deptId, UserInfo loginUser)
			throws CertOperateException;

	/**
	 * 
	 * @description：
	 * @param certFiles:上传的批量文件
	 * @param branchCode  分支机构
	 * @param deptId    部门编号
	 * @param loginUser  登陆用户
	 * @return
	 * @throws CertOperateException
	 * @version: 2010-12-8 下午01:32:23
	 * @See:
	 */
	List<UserCertificate> getUserCertificates(File[] certFiles, String branchCode, String deptId,
			UserInfo loginUser) throws CertOperateException;

	/**
	  * @description：传递的certFiles
	  * @param certFiles    上传的批量文件
	  * @param branchCode   分支机构
	  * @param deptId       部门编号
	  * @param loginUser    登陆用户
	  * @return
	  * @throws CertOperateException  
	  * @version: 2010-12-8 下午04:49:50
	  * @See:
	 */
	Map<UserCertificate, File> getUserCertificateMap(File[] certFiles, String branchCode, String deptId,UserInfo loginUser) 
	    throws CertOperateException;
	
	/**
	 * 
	  * @description：
	  * @param certFiles : 传递的certFiles
	  * @param branchCode:  分支机构
	  * @param loginUser:  登陆用户
	  * @return
	  * @throws CertOperateException  
	  * @version: 2011-4-25 下午04:10:44
	  * @See:
	 */
	Map<UserCertificate, File> getUserCertificateMap(File[] certFiles, String branchCode, UserInfo loginUser)
	       throws CertOperateException;

}
