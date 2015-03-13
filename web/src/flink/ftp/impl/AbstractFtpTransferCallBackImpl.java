package flink.ftp.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpTransferCallback;
import flink.util.CommonHelper;

/**
 * <p>FTP回调接口的抽象实现</p>
 * @Project: Card
 * @File: AbstractFtpTransferCallBackImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public abstract class AbstractFtpTransferCallBackImpl implements IFtpTransferCallback {
	protected final Log logger = LogFactory.getLog(getClass());

	protected final String remotePath;

	protected AbstractFtpTransferCallBackImpl(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public boolean doTransfer(FTPClient ftpClient) throws CommunicationException {
		checkRemotePath(this.remotePath);

		boolean result = false;

		try {
			String filterRemotePath = filterDirectory(this.remotePath);
			
			boolean chgFlag = chgWorkDirectory(ftpClient, filterRemotePath);

			if (chgFlag) {
				result = ftpProcess(ftpClient);
			} else {
				if (needCreateRemotePath()) {
					try {
						boolean isCreate = ftpClient.makeDirectory(filterRemotePath);
						if(!isCreate){
							isCreate = createDirecroty(filterRemotePath, ftpClient);//在原来的基础上增加支持多级目录创建
						}
						if (isCreate) {
							logger.info("========远程服务目录[" + this.remotePath + "]创建成功(进行切换)======");
							
							if(chgWorkDirectory(ftpClient, filterRemotePath)) {
								result = ftpProcess(ftpClient);
							}							
						}
					} catch (IOException ex) {
						logger.error("远程服务目录[" + this.remotePath + "]创建失败,原因[" + ex.getMessage() + "]");
					}
				}
			}
		} catch (IOException ex) {
			logger.error("远程目录[" + this.remotePath + "]切换失败");
			throw new CommunicationException(ex);
		}

		return result;

	}
	
	/** 
     * 递归创建远程服务器目录   
     * @param remote 远程服务器文件绝对路径   
     * @param ftpClient FTPClient 对象   
     * @return 目录创建是否成功   
     * @throws IOException   
     */    
    public boolean createDirecroty(String remote,FTPClient ftpClient) throws IOException{
    	if(null == remote || null == ftpClient) return false;
    	if(remote.startsWith("/")){//remote为绝对路径
    		String currentPath = ftpClient.printWorkingDirectory();
    		if(remote.startsWith(currentPath)){
    			remote = remote.substring(currentPath.length()+1);
    		}else{
    			if(!ftpClient.changeWorkingDirectory("/")){//尝试从根目录开始创建
    				 throw new IOException("无法跳转到根目录,目录无法创建:"+remote);
    			}
    		}
    	}
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);     
        if(!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)){//如果远程目录不存在，则递归创建远程服务器目录
            int start=0;     
            int end = 0;     
            if(directory.startsWith("/")){     
                start = 1;     
            }else{     
                start = 0;     
            }     
            end = directory.indexOf("/",start);     
            while(true){     
                String subDirectory = new String(remote.substring(start,end));     
                if(!ftpClient.changeWorkingDirectory(subDirectory)){     
                    if(ftpClient.makeDirectory(subDirectory)){     
                        ftpClient.changeWorkingDirectory(subDirectory);     
                    }else {     
                        throw new IOException("创建目录失败:"+subDirectory);     
                    }     
                }     
                start = end + 1;     
                end = directory.indexOf("/",start);     
                     
                //检查所有目录是否创建完毕     
                if(end <= start){     
                    break;     
                }     
            }     
        }
        return true;
    }
	
	/**
	 * 
	 * <p>检查切换工作目录</p>
	 * @param remotePath
	 * @version: 2011-7-8 上午10:42:02
	 * @See:
	 */
	protected static void checkRemotePath(String remotePath) {
		if (CommonHelper.checkIsEmpty(remotePath)) {
			throw new CommunicationException("远程服务器目录不能为空!");
		}

		if (!remotePath.startsWith(PATH_SEPRATOR)) {
			throw new CommunicationException("远程服务目录" + remotePath + "]不合法,需要以[" + PATH_SEPRATOR + "]开始!");
		}
	}
	
	/**
	 * 
	  * <p>对切换目录进行防御式处理(这里用于迭代获取目录的场景)</p>
	  * @param directory
	  * @return  
	  * @version: 2011-7-11 上午11:48:19
	  * @See:
	 */
	protected static String filterDirectory(String directory) {
		if (directory.endsWith(PATH_SEPRATOR)) {
			return directory;
		}

		return directory.concat(PATH_SEPRATOR);
	}

	/**
	 * 
	  * <p>切换工作目录</p>
	  * @param ftpClient
	  * @param remotePath
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-11 上午11:47:14
	  * @See:
	 */
	private static boolean chgWorkDirectory(FTPClient ftpClient, String remotePath) throws IOException {
		return ftpClient.changeWorkingDirectory(remotePath);
	}

	/**
	 * 
	 * <p>检查是否存在远程文件</p>
	 * @param ftpClient
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 * @version: 2010-12-9 上午11:38:05
	 * @See:
	 */
	protected static boolean checkHasRemoteFile(FTPClient ftpClient, String remoteFileName) throws IOException {
		FTPFile[] files = ftpClient.listFiles(remoteFileName);

		return (files != null) && (files.length >= 1);
	}

	/**
	 * 
	 * <p>检查是否没有远程文件</p>
	 * @param ftpClient
	 * @param remoteFileName    文件名 
	 * @return
	 * @throws IOException
	 * @version: 2010-12-13 上午11:49:32
	 * @See:
	 */
	protected static boolean checkNoRemoteFile(FTPClient ftpClient, String remoteFileName) throws IOException {
		FTPFile[] files = ftpClient.listFiles(remoteFileName);

		return (files == null) || (files.length == 0);
	}

	/**
	 * 
	 * <p>获取切换工作目录下的所有文件名称(不包含子目录)</p>
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 * @version: 2011-1-11 下午02:53:16
	 * @See:
	 */
	protected static String[] listPathFileNames(FTPClient ftpClient) throws IOException {
		return ftpClient.listNames();
	}

	/**
	 * 
	 * <p>获取切换目录下某文件夹下的所有文件名称</p>
	 * @param ftpClient
	 * @param dirInPath  目录下的某文件夹名称
	 * @return
	 * @throws IOException
	 * @version: 2011-1-11 下午02:53:59
	 * @See:
	 */
	protected static String[] listPathFileNames(FTPClient ftpClient, String dirInPath) throws IOException {
		return ftpClient.listNames(dirInPath);
	}

	/**
	 * 
	 * <p>ftp上传下载等处理</p>
	 * @param ftpClient
	 * @return
	 * @throws CommunicationException
	 * @version: 2010-12-13 下午01:24:00
	 * @See:
	 */
	protected abstract boolean ftpProcess(FTPClient ftpClient) throws CommunicationException;

	/**
	 * 
	 * <p>是否需要创建远程目录(即切换所在目录不存在时)</p>
	 * @return
	 * @version: 2011-4-16 上午08:14:50
	 * @See:
	 */
	protected abstract boolean needCreateRemotePath();
}
