package flink.ftp;

/**
  * <p>处理面向FTP处理的回调接口模板类</p>
  * @Project: MyCard
  * @File: FtpProcess.java
  * @See:
  * @description：处理面向FTP处理的回调接口模板类
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public interface IFtpCallBackProcess {
    /**
     * 
      * @param ftpCallBack FTP回调接口
      * @return
      * @throws CommunicationException  
      * @version: 2011-7-12 上午11:48:20
      * @See:
     */
	boolean processFtpCallBack(IFtpTransferCallback ftpCallBack) throws CommunicationException;
}
