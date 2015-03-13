/**
 * 
 */
package flink.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts.upload.FormFile;

/**
 * <p>Title: 上传文件</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 雁联</p>
 * @author dhs
 * @version 1.0
 */
public class UploadFileService {
	
	/**
	 * 
	 */
	public UploadFileService() {
		
		
	}

	/**
	 * 将上传的文件保存到指定路径（路径+文件名）下。
	 * 
	 * @param formFile
	 *            上传的struts文件对象
	 * @param filePath
	 *            指定保存到的路径（路径+文件名）
	 */
	public void save(FormFile formFile, String filePath) {
		try {
			// //////////////////modified by 071291 on 20071127
			// 解决大文件上传时有时发现内存溢出的问题//////////////////////////////
			// OutputStream bos = new FileOutputStream(filePath);
			OutputStream bos = new BufferedOutputStream(new FileOutputStream(
					filePath));
			// //////////////////end/////////////////////////

			byte[] buffer = this.getBytesData(formFile);
			bos.write(buffer);
			bos.close();
		} catch (FileNotFoundException e) {
			System.out.println("保存上传文件" + formFile.getFileName() + "到" + filePath
					+ "失败，创建文件失败。" + e);
		} catch (IOException e) {
			System.out.println("保存上传文件" + formFile.getFileName() + "到" + filePath
					+ "失败，发生IO异常。" + e);
		}
	}
	/**
	 * 获取上传的二进制文件内容
	 * 
	 * @param formFile
	 *            上传的struts文件对象
	 * @return 二进制字节内容
	 */
	public byte[] getBytesData(FormFile formFile) {
		byte[] data = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream stream = formFile.getInputStream();
			// if (formFile.getFileSize() < (50 * 1024000))
			// {
			byte[] buffer = new byte[8192];
			int bytesRead = 0;
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			data = baos.toByteArray();
			// }
			// else
			// {
			// logger.error("文件" + formFile.getFileName() + "内容大于50MB, "
			// + " 上传失败。" + " 上传文件大小: " + formFile.getFileSize()
			// + " bytes.");
			// }
			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("找不到上传文件" + formFile.getFileName());
		} catch (IOException e) {
			System.out.println("读取上传文件" + formFile.getFileName() + "失败，发生IO异常。");
		}
		return data;
	}

}
