package flink.ftp.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.IFtpTransferCallback;

/**
 * <p>FTP回调接口处理类 </p>  
 * @Project: Card
 * @File: FtpCallBackProcessImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class FtpCallBackProcessImpl implements IFtpCallBackProcess {
	protected static final String DEFAULT_CTRL_ENCODING = "UTF-8";

	private FTPClientConfig ftpClientConfig;

	private String server;

	private String user;

	private String pwd;

	private int port;

	public FtpCallBackProcessImpl(String server, String user, String pwd) {
		this.server = server;
		this.user = user;
		this.pwd = pwd;
	}

	public FtpCallBackProcessImpl(String server, int port, String user, String pwd) {
		this.server = server;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

	public boolean processFtpCallBack(IFtpTransferCallback ftpCallBack) throws CommunicationException {
		boolean process = false;

		try {
			FTPClient ftp = getDefaultFtpClient();
			try {
				// 1.1 如果有内部配置则添加
				FTPClientConfig clientConfig = getFtpClientConfig();
				if (clientConfig != null) {
					ftp.configure(clientConfig);
				}
				// 1.2 尝试连接到服务器
				if (this.port != 0) {
					ftp.connect(server, port);
				} else {
					ftp.connect(server);
				}

				// 1.3 获取响应代码
				int reply = ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					// 1.4 如果没有及时响应则断开连接并抛出异常(可选)
					ftp.disconnect();
					throw new IOException("无法连接到指定服务器:" + server);
				}

				// 2.1 尝试用当前的用户和密码登陆
				boolean loginResult = ftp.login(this.getUser(), this.getPwd());
				if (!loginResult) {
					throw new IOException("用当前的用户["+ this.getUser() +"]和密码[" + this.getPwd() + "]登陆FTP服务器失败。");
				}

				// 2.2 进行FTP回调的处理
				process = ftpCallBack.doTransfer(ftp);

				// 2.3 处理完毕后登出
				ftp.logout();

			} finally {
				// 断开FTP连接
				if (ftp.isConnected()) {
					ftp.disconnect();
				}
			}

		} catch (IOException ex) {
			throw new CommunicationException(ex);
		}

		return process;
	}

	public FTPClientConfig getFtpClientConfig() {
		return ftpClientConfig;
	}

	public void setFtpClientConfig(FTPClientConfig ftpClientConfig) {
		this.ftpClientConfig = ftpClientConfig;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	/**
	 * 
	 *<p>构造缺省的FTPClient接口对象</p>
	 * 
	 * @return
	 * @version: 2010-12-8 下午02:41:26
	 * @throws IOException
	 * @See:
	 */
	private FTPClient getDefaultFtpClient() throws IOException {
		FTPClient ftp = new FTPClient();

		ftp.setControlEncoding(DEFAULT_CTRL_ENCODING);

		return ftp;
	}

}
