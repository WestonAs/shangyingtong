package flink;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDirFilesCallBackImpl;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonUploadCallBackImpl;
import flink.ftp.impl.CommonNameDirFilesCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;

public class TestFtp {

	public static void testUpload() throws Exception {
		String server = "172.168.9.20";
		String user = "card";
		String pwd = "card";

		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(server, user, pwd);

		String remotePath = "/home/card/test";
		File file = new File("./conf/card.properties");
		CommonUploadCallBackImpl uploadCallBack = new CommonUploadCallBackImpl(remotePath, file);

		ftpCallBackTemplate.processFtpCallBack(uploadCallBack);
	}

	public static void testDownload() throws Exception {
		String server = "172.168.9.20";
		String user = "card";
		String pwd = "card";

		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(server, user, pwd);

		String remotePath = "/home/card/aaa";
		String remoteFile = "MsgQueue.hpp";

		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(remotePath, remoteFile);

		// 处理下载
		boolean result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);

		if (result) {

			InputStream input = downloadCallBack.getRemoteReferInputStream();

			System.out.println("===" + IOUtils.toString(input));

			IOUtils.closeQuietly(input);

		}
	}

	public static void testDir() throws Exception {
		String server = "218.168.127.153";
		String user = "carddev";
		String pwd = "carddev";

		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(server, user, pwd);

		String remotePath = "/home/carddev/aa";

		String[] prefix = new String[] {};

		String[] suffix = new String[] { ".txt" };

		CommonDirFilesCallBackImpl callBack = new CommonDirFilesCallBackImpl(remotePath, prefix, suffix);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		if (flag) {
			Object[] result = callBack.getLocalFilesRefer();

			List<Object[]> list = (List<Object[]>) result[1];

			for (Object[] objs : list) {
				Object obj = objs[0];

				System.out.println("" + obj.toString());
			}

			System.out.println("" + list.toString());

		}

	}

	public static void testListNames() throws Exception {
		String server = "218.168.127.153";
		String user = "carddev";
		String pwd = "carddev";

		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(server, user, pwd);

		String remotePath = "/home/carddev/oldreport/";

		String[] prefix = new String[] {};

		String[] suffix = new String[] { ".txt" };

		CommonNameDirFilesCallBackImpl callBack = new CommonNameDirFilesCallBackImpl(remotePath, prefix, suffix);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		if (flag) {
			System.out.println("" + callBack.getNameFilesRefer().toString());
		}

	}

	public static void main(String[] args) throws Exception {
		// testUpload();
		// testDownload();
		// testDir();
		testListNames();

	}
}
