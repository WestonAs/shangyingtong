package flink.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
/**
 * <p>公共文件IO处理类</p>
 * @Project: Card
 * @File: FileHelper.java
 * @See:
 * @description：
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-7
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public final class FileHelper {
	public static final String PATH_SEPRATOR = "/";
	
	private static final int DEFAULT_BFFER_SIZE = 1024;

	private static final int ZIP_BUFFER_SIZE = 512;

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final String DEFAULT_FILEMODE = "rw";

	private static final byte[] EMPTY_ARRAY = new byte[] {};
	
	/**
	 * 
	  * @param remotePath
	  * @param remoteFile
	  * @return  
	  * @version: 2011-7-13 下午02:44:16
	  * @See:
	 */
	public static String getFilePath(String remotePath, String remoteFile) {
		remotePath = remotePath.replace("\\", PATH_SEPRATOR);
		
		if(remotePath.endsWith(PATH_SEPRATOR)) {
			return new StringBuilder().append(remotePath).append(remoteFile).toString();
		}
		
		return new StringBuilder().append(remotePath).append(PATH_SEPRATOR).append(remoteFile).toString();
	}
	
	/**
	 *  <p>将字节数组转换成字符数组</p>
	  * @param bytes
	  * @return  
	  * @version: 2011-7-13 下午02:45:31
	  * @See:
	 */
	public static char[] bytesToChars(byte[] bytes) {
		String s = new String(bytes);
		char chars[] = s.toCharArray();
		return chars;
	}

	//------------------------------------IO CLOSE---------------------------------------------------------------
	/**
	 *  <p>关闭输入流</p>
	  * @param inputStream  
	  * @version: 2011-7-13 下午02:44:19
	  * @See:
	 */
	public static void closeInputStream(InputStream inputStream) {
		IOUtils.closeQuietly(inputStream);
	}

	/**
	 *  <p>关闭输出流</p>
	  * @param outputStream  
	  * @version: 2011-7-13 下午02:44:22
	  * @See:
	 */
	public static void closeOutputStream(OutputStream outputStream) {
		IOUtils.closeQuietly(outputStream);
	}

	/**
	 *  <p>关闭writer</p>
	  * @param writer  
	  * @version: 2011-7-13 下午02:44:26
	  * @See:
	 */
	public static void closeWriter(Writer writer) {
		IOUtils.closeQuietly(writer);
	}
	
	/**
	 *  <p>从路径中获得输入流</p>
	  * @param path
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:29
	  * @See:
	 */
	public static InputStream getResourceStream(String path) throws IOException{
		return new ClassPathResource(path).getInputStream();
	}

	/**
	 *  <p>根据编码从文件中按行读取</p>
	  * @param file
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:35
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static List<String> readLines(File file, String encoding) throws IOException {
		return FileUtils.readLines(file, encoding);
	}

	/**
	 *  <p>根据编码从输入流中按行读取</p>
	  * @param input
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:43
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static List<String> readLines(InputStream input, String encoding) throws IOException {
		return IOUtils.readLines(input, encoding);
	}

	/***
	 *  <p>根据编码从输入流中按行读取(过滤空行)</p>
	  * @param input
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:45
	  * @See:
	 */
	public static List<String> readFilterLines(InputStream input, String encoding) throws IOException {
		return _readLines(input, encoding);
	}

	/**
	 * 
	  * @param input
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:48
	  * @See:
	 */
	private static List<String> _readLines(InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	/**
	 * 
	  * @param input
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:50
	  * @See:
	 */
	private static List<String> readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	/**
	 * 
	  * @param input
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:54
	  * @See:
	 */
	private static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			if (CommonHelper.isNotEmpty(line)) {
				list.add(line);
			}
			line = reader.readLine();
		}
		return list;
	}

	/**
	 * 
	  * @param file
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:44:57
	  * @See:
	 */
	public static String[] readLineInArray(File file, String encoding) throws IOException {
		List<String> readLineList = readLines(file, encoding);

		return readLineList.toArray(new String[readLineList.size()]);
	}

	/**
	 *  <p>根据编码从文件中按行读取(通过RanddomAccess方式)</p>
	  * @param file
	  * @param encoding
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:00
	  * @See:
	 */
	public static List<String> _readLines(File file, String encoding) throws Exception {
		RandomAccessFile accessFile = new RandomAccessFile(file, DEFAULT_FILEMODE);

		List<String> lines = new LinkedList<String>();
		String line = null;

		while ((line = accessFile.readLine()) != null) {
			lines.add(new String(line.getBytes(DEFAULT_CHARSET), encoding));
		}

		accessFile.close();

		return lines;
	}

	/**
	 *  <p>将行集合内容按照编码写入指定文件</p>
	  * @param file         写入文件
	  * @param encoding     文件编码
	  * @param lines        行集合内容
	  * @return  
	  * @version: 2011-7-13 下午02:45:03
	  * @See:
	 */
	public static boolean writeLines(File file, String encoding, Collection lines) {
		boolean result = false;

		try {
			RandomAccessFile accessFile = new RandomAccessFile(file, DEFAULT_FILEMODE);

			for (Iterator iter = lines.iterator(); iter.hasNext();) {
				Object line = iter.next();

				if (line != null) {
					accessFile.write(line.toString().getBytes(encoding));
				}

				accessFile.write(IOUtils.LINE_SEPARATOR.getBytes());
			}

			accessFile.close();

			result = true;
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}

		return result;
	}

	/**
	 *  <p>根据内容写入指定文件</p>
	  * @param content
	  * @param file
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:06
	  * @See:
	 */
	public static boolean persistFile(String content, File file) throws Exception {
		if (CommonHelper.checkIsEmpty(content)) {
			return false;
		}

		OutputStream output = getBufferedOutputStream(file);

		IOUtils.write(content, output);

		closeOutputStream(output);

		return true;
	}

	/**
	 *  <p>根据内容按指定编码写入指定文件</p>
	  * @param content
	  * @param file
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:45:09
	  * @See:
	 */
	public static boolean persistFile(String content, File file, String encoding) throws IOException {
		if (CommonHelper.checkIsEmpty(content)) {
			return false;
		}

		Writer writer = getFileWriter(file, encoding);

		writer.write(content);

		closeWriter(writer);

		return true;
	}

	/**
	 *  <p>根据字节数组写入指定文件</p>
	  * @param content
	  * @param file
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:13
	  * @See:
	 */
	public static boolean persistFile(byte[] content, File file) throws Exception {
		if (CommonHelper.checkIsEmpty(content)) {
			return false;
		}

		OutputStream output = getBufferedOutputStream(file);

		IOUtils.write(content, output);

		output.close();

		return true;
	}

	/**
	 *  <p>根据字节数组按指定编码写入指定文件</p>
	  * @param content
	  * @param file
	  * @param encoding
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:45:21
	  * @See:
	 */
	public static boolean persistFile(byte[] content, File file, String encoding) throws IOException {
		if (CommonHelper.checkIsEmpty(content)) {
			return false;
		}

		Writer writer = getFileWriter(file, encoding);

		writer.write(bytesToChars(content));

		closeWriter(writer);

		return true;
	}
	
	/**
	 * 
	  * @param file
	  * @param encoding
	  * @param lines
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:45:25
	  * @See:
	 */
	public static boolean persistFile(File file, String encoding, Collection lines) throws IOException {
		if (CommonHelper.checkIsEmpty(lines)) {
			return false ;
		}
		
		Writer writer = getFileWriter(file, encoding);
		
		IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, writer);
		
		closeWriter(writer);
		
		return true;
	}
	
	/**
	 *  <p>将行集合内容按照编码写入指定文件(通过Writer方式)</p>
	  * @param output
	  * @param encoding
	  * @param lines
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:28
	  * @See:
	 */
	public static boolean persistFile(OutputStream output,String encoding, Collection lines) throws Exception {
		if (CommonHelper.checkIsEmpty(lines)) {
			return false ;
		}
		
		Writer writer = getFileWriter(output,encoding);
		
		IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, writer);
		
		closeWriter(writer);
		
		return true;
	}

	

	/**
	 *  <p>根据目录名称来查找文件(没有找到则创建该目录)</p>
	  * @param directoryName
	  * @return  
	  * @version: 2011-7-13 下午02:45:35
	  * @See:
	 */
	public static File getDirectoryFile(String directoryName) {
		File dir = new File(directoryName);

		if (dir.exists() && (dir.isDirectory())) {
			return dir;
		}

		dir.mkdirs();

		return dir;
	}

	/**
	 *  <p>返回读取指定目录下文件的字节数组</p>
	  * @param directoryName   目录名称
	  * @param fileName        文件名称
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:38
	  * @See:
	 */
	public static byte[] getFileByteArray(String directoryName, String fileName) throws Exception {
		return getFileByteArray(directoryName, fileName, true);
	}

	/**
	 *  <p>返回读取指定目录下文件的字节数组(如果该文件为空则返回为空)</p>
	  * @param directoryName
	  * @param fileName
	  * @param flag
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:40
	  * @See:
	 */
	public static byte[] getFileByteArray(String directoryName, String fileName, boolean flag) throws Exception {
		InputStream input = getFileInputStream(directoryName, fileName, flag);
		try {
			return (input == null) ? EMPTY_ARRAY : stream2ByteArray(input);
		} finally {
			closeInputStream(input);
		}
	}

	/**
	 *  <p>返回读取指定目录下文件的输入流(如果为空则返回为空)</p>
	  * @param directoryName
	  * @param fileName
	  * @param flag
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:43
	  * @See:
	 */
	public static InputStream getFileInputStream(String directoryName, String fileName, boolean flag) throws Exception {
		File file = getFile(directoryName, fileName, flag);

		return (file == null) ? null : getBufferedInputStream(file);
	}

	/**
	 *  <p>返回读取指定目录下文件的输出流(如果为空则返回为空)</p>
	  * @param directoryName
	  * @param fileName
	  * @param flag
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:45
	  * @See:
	 */
	public static OutputStream getFileOutputStream(String directoryName, String fileName, boolean flag) throws Exception {
		File file = getFile(directoryName, fileName, flag);

		return (file == null) ? null : getBufferedOutputStream(file);
	}

	/**
	 * <p>返回读取指定目录下文件(如果没有该文件且需要创建则创建否则返回为空)</p>
	  * @param directoryName   目录名
	  * @param fileName        文件名
	  * @param flag     是否需创建该文件
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:48
	  * @See:
	 */
	public static File getFile(String directoryName, String fileName, boolean flag) throws Exception {
		File dirFile = getDirectoryFile(directoryName);
		File fileInDir = new File(dirFile, fileName);

		if (fileInDir.exists()) {
			return fileInDir;
		}

		if (flag) {
			if (!fileInDir.createNewFile()) {
				throw new Exception("create File [" + fileName + "] fail");
			}
			return fileInDir;
		} else {
			return null;
		}
	}

	/**
	 *  <p>返回读取指定目录下文件</p>
	  * @param dirFile     目录文件(File)
	  * @param fileName    文件名
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:45:51
	  * @See:
	 */
	public static File getFile(File dirFile, String fileName) throws IOException {
		if (!dirFile.isDirectory()) {
			return null;
		}
		File file = new File(dirFile, fileName);
		if (file.exists()) {
			return file;
		}
		file.createNewFile();
		return file;
	}

	/**
	 *  <p>返回读取指定目录下文件</p>
	  * @param directoryName
	  * @param fileName
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:45:53
	  * @See:
	 */
	public static File getFile(String directoryName, String fileName) throws Exception {
		return getFile(directoryName, fileName, true);
	}

    /*
	public static void writeLineBuffer(File file, String encoding, Collection lineBuffer) throws IOException {
		FileUtils.writeLines(file, encoding, lineBuffer);
	}

	
	public static void writeLineBuffer(File file, String encoding, Collection lineBuffer, String lineEnding) throws IOException {
		FileUtils.writeLines(file, encoding, lineBuffer, lineEnding);
	}*/

	/**
	 *  <p>获得文件访问的FileChannel（NIO方式）</p>
	  * @param file
	  * @return
	  * @throws FileNotFoundException  
	  * @version: 2011-7-13 下午02:46:14
	  * @See:
	 */
	public static FileChannel getDefaultFileChannle(File file) throws FileNotFoundException {
		return getFileChannel(file, DEFAULT_FILEMODE);
	}

	/**
	 *  <p>获得文件前缀内容部分</p>
	  * @param fileName
	  * @param prefix
	  * @return  
	  * @version: 2011-7-13 下午02:46:25
	  * @See:
	 */
	public static String getFilePrefixPart(String fileName, String prefix) {
		String _fileName = getFileName(fileName);

		return _fileName.substring(_fileName.indexOf(prefix) + prefix.length());
	}

	/**
	 *  <p>根据现有文件名称以及添加关联(连接符及添加等)</p>
	  * @param fileName
	  * @param connect
	  * @param append
	  * @param chgsuffix
	  * @return  
	  * @version: 2011-7-13 下午02:46:31
	  * @See:
	 */
	public static String getCreateFileName(String fileName, String connect, Object append, String chgsuffix) {
		int pos = fileName.lastIndexOf(".");
		String suffix = fileName.substring(pos);
		String prefix = fileName.substring(0, pos);

		StringBuilder sb = new StringBuilder(prefix);

		if (CommonHelper.isNotEmpty(connect)) {
			sb.append(connect);
		}

		if (append != null) {
			sb.append(append);
		}

		if (CommonHelper.isNotEmpty(chgsuffix)) {
			sb.append(chgsuffix);
		} else {
			sb.append(suffix);
		}

		return sb.toString();
	}

	// ----------------------------------------------------------------------------------------------
	/**
	 *  <p>根据文件前后缀来创建文件过滤器(commons io)</p>
	  * @param prefixNames
	  * @param suffixNames
	  * @return  
	  * @version: 2011-7-13 下午02:46:37
	  * @See:
	 */
	public static IOFileFilter createFileFilter(String[] prefixNames, String[] suffixNames) {
		return FileFilterUtils.andFileFilter(new PrefixFileFilter(prefixNames), new SuffixFileFilter(suffixNames));
	}

	/**
	 *  <p>根据文件前缀创建文件过滤器(commons io)</p>
	  * @param prefixNames
	  * @return  
	  * @version: 2011-7-13 下午02:46:42
	  * @See:
	 */
	public static IOFileFilter createPrefixFileFilter(String[] prefixNames) {
		return new PrefixFileFilter(prefixNames);
	}

	/**
	 *  <p>根据文件后缀创建文件过滤器(commons io)</p>
	  * @param suffixNames
	  * @return  
	  * @version: 2011-7-13 下午02:46:45
	  * @See:
	 */
	public static IOFileFilter createSuffxiFileFilter(String[] suffixNames) {
		return new SuffixFileFilter(suffixNames);
	}

	/**
	 *   <p>根据文件目录过滤器(commons io)--用于扫描文件中的目录结构</p>
	  * @return  
	  * @version: 2011-7-13 下午02:46:48
	  * @See:
	 */
	public static IOFileFilter createDirFilter() {
		return FileFilterUtils.directoryFileFilter();
	}

	/**
	 *  <p>获得文件的全名结构(包括后缀)--读取文件</p>
	  * @param file
	  * @return  
	  * @version: 2011-7-13 下午02:46:51
	  * @See:
	 */
	public static String getFullFileName(File file) {
		return FilenameUtils.getName(file.getName());
	}

	/**
	 *  <p>获得文件的全名结构(包括后缀)--读取文件名</p>
	  * @param fileName
	  * @return  
	  * @version: 2011-7-13 下午02:46:54
	  * @See:
	 */
	public static String getFullFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	/**
	 * <p>获得文件的名字(舍弃后缀)--读取文件</p>
	  * @param file
	  * @return  
	  * @version: 2011-7-13 下午02:46:57
	  * @See:
	 */
	public static String getFileName(File file) {
		return FilenameUtils.getBaseName(file.getName());
	}

	/**
	 *  <p>获得文件的名字(舍弃后缀)--读取文件名</p>
	  * @param fileName
	  * @return  
	  * @version: 2011-7-13 下午02:47:05
	  * @See:
	 */
	public static String getFileName(String fileName) {
		return FilenameUtils.getBaseName(fileName);
	}

	/**
	 * <p>获得文件的后缀--读取文件</p>
	  * @param file
	  * @return  
	  * @version: 2011-7-13 下午02:47:08
	  * @See:
	 */
	public static String getFileExtension(File file) {
		return getFileExtension(file.getName());
	}

	/**
	 *   <p>获得文件的后缀--读取文件名</p>
	  * @param fileName
	  * @return  
	  * @version: 2011-7-13 下午02:47:10
	  * @See:
	 */
	public static String getFileExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	/**
	 *  <p>根据文件过滤以及目录过滤器从指定目录中提取过滤规则的问价</p>
	  * @param directory
	  * @param fileFilter
	  * @param dirFilter
	  * @return  
	  * @version: 2011-7-13 下午02:47:14
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Collection<File> getDirFilterFileList(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
		return FileUtils.listFiles(directory, fileFilter, dirFilter);
	}

	/**
	 *  <p>根据文件的后缀来保存读取指定文件集合的文件流</p>
	 *  <p>注意这是一个多值MAP结构(后缀,List<InputStream>)</p>
	  * @param fileList
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:47:35
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map getSuffixStreamMap(Collection<File> fileList) throws IOException {
		Map suffixStream = CommonHelper.createDefaultMap();
		for (File file : fileList) {
			String suffix = getFileExtension(file);
			String fileName = getFullFileName(file);
			suffixStream.put(suffix, new Object[] { fileName, FileHelper.getBufferedInputStream(file) });
		}
		return suffixStream;
	}

	/**
	  *  <p>根据文件的后缀来保存读取指定文件集合的文件流</p>
	  * @param filesMap
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:47:38
	  * @See:
	 */
	@SuppressWarnings("unchecked")
	public static Map getSuffixStreamMap(Map<String, File> filesMap) throws IOException {
		Map suffixStream = CommonHelper.createDefaultMultiMap();
		
		for(Iterator<Map.Entry<String, File>> iterator = filesMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, File> entry = iterator.next();
			File file = entry.getValue();
			String fileName = entry.getKey();
			String suffix = getFileExtension(file);
			suffixStream.put(suffix, new Object[] { fileName, FileHelper.getBufferedInputStream(file) });
		}
		
		return suffixStream;
	}

	/**
	 *  <p>读取路径下某文件并将其拷贝到某文件中</p>
	  * @param file
	  * @param fileName
	  * @param localPath
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:47:58
	  * @See:
	 */
	public static boolean persistFile2LocalPath(File file, String fileName, String localPath) throws Exception {
		OutputStream output = null;
		InputStream input = null;
		try {
			output = getBufferedOutputStream(localPath, fileName);
			input = getBufferedInputStream(file);

			copy(input, output);

			return true;
		} finally {
			closeOutputStream(output);
			closeInputStream(input);
		}
	}
	
	public static boolean persistFile2LocalPath(File file, String fileName, File localPath) throws Exception {
		OutputStream output = null;
		InputStream input = null;
		try {
			output = getBufferedOutputStream(localPath, fileName);
			input = getBufferedInputStream(file);

			copy(input, output);

			return true;
		} finally {
			closeOutputStream(output);
			closeInputStream(input);
		}
	}
	
	

	/**
	 *  <p>删除某文件(不抛出异常)</p>
	  * @param file  
	  * @version: 2011-7-13 下午02:48:01
	  * @See:
	 */
	public static void deleteFile(File file) {
		FileUtils.deleteQuietly(file);
	}

	/**
	 *  <p>将某文件拷贝到压缩文件目录中</p>
	  * @param sourceFile
	  * @param zipFile
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:04
	  * @See:
	 */
	public static void copyFile2Zip(File sourceFile, File zipFile) throws Exception {
		InputStream is = getBufferedInputStream(sourceFile);
		ZipOutputStream zos = getZipOutputStream(zipFile);
		ZipEntry ze = getFileZipEntry(sourceFile);
		zos.putNextEntry(ze);

		byte[] buf = new byte[DEFAULT_BFFER_SIZE];
		int readLen = -1;
		while ((readLen = is.read(buf, 0, DEFAULT_BFFER_SIZE)) != -1) {
			zos.write(buf, 0, readLen);
		}

		zos.closeEntry();
		is.close();
		zos.close();
	}

	/**
	 *   <p>将文件集合拷贝到压缩文件目录中</p>
	  * @param files
	  * @param zipFile
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:07
	  * @See:
	 */
	public static void copyFile2Zip(Collection<File> files, File zipFile) throws Exception {
		ZipOutputStream zos = getZipOutputStream(zipFile);
		for (File sourceFile : files) {
			InputStream is = getBufferedInputStream(sourceFile);
			ZipEntry ze = getFileZipEntry(sourceFile);
			zos.putNextEntry(ze);

			byte[] buf = new byte[DEFAULT_BFFER_SIZE];
			int readLen = -1;
			while ((readLen = is.read(buf, 0, DEFAULT_BFFER_SIZE)) != -1) {
				zos.write(buf, 0, readLen);
			}

			zos.closeEntry();
			is.close();
		}
		zos.close();
	}

    /**
     *  
      * @param directory
      * @param fileFilter
      * @param zipFile
      * @throws Exception  
      * @version: 2011-7-13 下午02:48:15
      * @See:
     */
	public static void copyDirectory2Zip(File directory, IOFileFilter fileFilter, File zipFile) throws Exception {
		if (directory.isDirectory()) {
			Collection<File> files = getDirFilterFileList(directory, fileFilter, createDirFilter());
			if (CommonHelper.checkIsNotEmpty(files)) {
				copyFile2Zip(files, zipFile);
			}
		}
	}

	/**
	 * 
	  * @param zipFile
	  * @param outputDir
	  * @param suffix
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:17
	  * @See:
	 */
	public static File[] getUnZipFiles(File zipFile, File outputDir, String suffix) throws Exception {
		// 将 zip文件解压缩到outputDir中
		ZipInputStream in = null;
		try {
			in = getZipInputStream(zipFile);
			ZipEntry z;
			while ((z = in.getNextEntry()) != null) {
				if (z.isDirectory()) {
					String name = z.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDir, name);

					f.mkdir();

				} else {
					File f = new File(outputDir, z.getName());

					f.createNewFile();

					OutputStream out = getBufferedOutputStream(f);

					IOUtils.copy(in, out);

					out.close();
				}
			}
		} finally {
			closeInputStream(in);
		}

		Collection<File> suffixDirFiles = getDirFilterFileList(outputDir, createSuffxiFileFilter(new String[] { suffix }),
				createDirFilter());

		return suffixDirFiles.toArray(new File[suffixDirFiles.size()]);
	}

	/**
	 * 
	  * @param zipFile
	  * @param outputDir
	  * @param suffix
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:21
	  * @See:
	 */
	public static File[] getUnZipFiles(File zipFile, File outputDir, String[] suffix) throws IOException {
		// 将 zip文件解压缩到outputDir中
		ZipInputStream in = null;
		try {
			in = getZipInputStream(zipFile);
			ZipEntry z;
			while ((z = in.getNextEntry()) != null) {
				if (z.isDirectory()) {
					String name = z.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDir, name);

					f.mkdir();

				} else {
					File f = new File(outputDir, z.getName());

					f.createNewFile();

					OutputStream out = getBufferedOutputStream(f);

					IOUtils.copy(in, out);

					out.close();
				}
			}
		} finally {
			closeInputStream(in);
		}

		Collection<File> suffixDirFiles = getDirFilterFileList(outputDir, createSuffxiFileFilter(suffix), createDirFilter());

		return suffixDirFiles.toArray(new File[suffixDirFiles.size()]);
	}

	/**
	 * 
	  * @param colFile
	  * @return  
	  * @version: 2011-7-13 下午02:48:24
	  * @See:
	 */
	public static Collection<String> getCollFileNameList(Collection<File> colFile) {
		List<String> fileNameList = new ArrayList<String>(colFile.size());
		for (File file : colFile) {
			fileNameList.add(FileHelper.getFullFileName(file));
		}
		return fileNameList;
	}

	/**
	 * 
	  * @param zipFile
	  * @param outputDir
	  * @param suffix
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:28
	  * @See:
	 */
	public static Map<String, File> getUnZipFileMap(File zipFile, File outputDir, String[] suffix) throws IOException {
		File[] unzipFiles = getUnZipFiles(zipFile, outputDir, suffix);
		if (unzipFiles == null || unzipFiles.length == 0) {
			return Collections.<String, File> emptyMap();
		}

		Map<String, File> zipFilesMap = new HashMap<String, File>(unzipFiles.length);

		for (File file : unzipFiles) {
			String fileName = FileHelper.getFullFileName(file);
			zipFilesMap.put(fileName, file);
		}

		return zipFilesMap;
	}

	/**
	 * 
	  * @param zipInput
	  * @param outputDir
	  * @param suffix
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:31
	  * @See:
	 */
	public static Map<String, File> getUnZipFileMap(InputStream zipInput, File outputDir, String[] suffix) throws IOException {
		Collection<File> unzipFiles = getUnZipFiles(zipInput, outputDir, suffix);

		if (CommonHelper.checkIsEmpty(unzipFiles)) {
			return Collections.<String, File> emptyMap();
		}

		Map<String, File> zipFilesMap = new HashMap<String, File>(unzipFiles.size());

		for (File file : unzipFiles) {
			String fileName = FileHelper.getFullFileName(file);
			zipFilesMap.put(fileName, file);
		}

		return zipFilesMap;
	}

	/**
	 * 
	  * @param zipInput
	  * @param outputDir
	  * @param suffix
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:34
	  * @See:
	 */
	public static Collection<File> getUnZipFiles(InputStream zipInput, File outputDir, String[] suffix) throws IOException {
		ZipInputStream in = null;
		try {
			in = getZipInputStream(zipInput);
			ZipEntry z;
			while ((z = in.getNextEntry()) != null) {
				if (z.isDirectory()) {
					String name = z.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDir, name);

					f.mkdir();

				} else {
					File f = new File(outputDir, z.getName());

					f.createNewFile();

					OutputStream out = getBufferedOutputStream(f);

					IOUtils.copy(in, out);

					out.close();
				}
			}
		} finally {
			closeInputStream(in);
		}

		return getDirFilterFileList(outputDir, createSuffxiFileFilter(suffix), createDirFilter());
	}

	/**
	 * 
	  * @param zipUpload
	  * @param zipFileName
	  * @param localPath
	  * @param tempPath
	  * @param suffix
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:37
	  * @See:
	 */
	public static Object[] extractZipFiles(File zipUpload, String zipFileName, String localPath, String tempPath, String suffix)
			throws Exception {
		String localZipFileName = getCreateFileName(zipFileName, "_", System.currentTimeMillis(), "");

		if (persistFile2LocalPath(zipUpload, localZipFileName, localPath)) {

			File localZipFile = getFile(localPath, localZipFileName);

			File outputDir = getLocalTempDir(tempPath);

			File[] unzipFiles = getUnZipFiles(localZipFile, outputDir, suffix);

			return new Object[] { unzipFiles, outputDir };
		}

		return new Object[] {};
	}
	
	public static Object[] extractZipFiles(File zipUpload, String zipFileName, String localPath, File tempPath, String suffix)
	       throws Exception {
        String localZipFileName = getCreateFileName(zipFileName, "_", System.currentTimeMillis(), "");

        if (persistFile2LocalPath(zipUpload, localZipFileName, localPath)) {

	        File localZipFile = getFile(localPath, localZipFileName);

	       //File outputDir = getLocalTempDir(tempPath);

	        File[] unzipFiles = getUnZipFiles(localZipFile, tempPath, suffix);

	        return new Object[] { unzipFiles, tempPath };
        }

        return new Object[] {};
    }
	
	

	/**
	 * 
	  * @param upload
	  * @param fileName
	  * @param localPath
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:41
	  * @See:
	 */
	public static File[] getLocalFiles(File upload, String fileName, String localPath) throws Exception {
		if (persistFile2LocalPath(upload, fileName, localPath)) {
			return new File[] { getFile(localPath, fileName) };
		}
		return new File[] {};
	}

	/**
	 * 
	  * @param tempPath
	  * @return  
	  * @version: 2011-7-13 下午02:48:43
	  * @See:
	 */
	public static File getLocalTempDir(String tempPath) {
		return getLocalTempDir(tempPath,String.valueOf(System.currentTimeMillis()));
	}
	
	/**
	 * 
	  * @param tempPath
	  * @param name
	  * @return  
	  * @version: 2011-7-13 下午02:48:47
	  * @See:
	 */
	public static File getLocalTempDir(String tempPath, String name) {
		String dir = new StringBuilder(tempPath).append(File.separator).append(name).toString();
		return getDirectoryFile(dir);
	}

	/**
	 * 
	  * @param file
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:49
	  * @See:
	 */
	public static void forceDeleteFile(File file) throws IOException {
		FileUtils.forceDelete(file);
	}

	/**
	 * 
	  * @param directoryName
	  * @param fileName
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:48:52
	  * @See:
	 */
	public static OutputStream getBufferedOutputStream(String directoryName, String fileName) throws Exception {
		return getBufferedOutputStream(getFile(directoryName, fileName));
	}

	/**
	 * 
	  * @param dirFile
	  * @param fileName
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:55
	  * @See:
	 */
	public static OutputStream getBufferedOutputStream(File dirFile, String fileName) throws IOException {
		return getBufferedOutputStream(getFile(dirFile, fileName));
	}

	/**
	 * 
	  * @param file
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:48:58
	  * @See:
	 */
	public static OutputStream getBufferedOutputStream(File file) throws IOException {
		return new BufferedOutputStream(FileUtils.openOutputStream(file));
	}

	/**
	 * 
	  * @param zipInput
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:00
	  * @See:
	 */
	public static ZipInputStream getZipInputStream(InputStream zipInput) throws IOException {
		return new ZipInputStream(getBufferedInputStream(zipInput));
	}

	/**
	 * 
	  * @param zipFile
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:03
	  * @See:
	 */
	public static ZipInputStream getZipInputStream(File zipFile) throws IOException {
		return new ZipInputStream(getBufferedInputStream(zipFile));
	}

	/**
	 * 
	  * @param zipFile
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:05
	  * @See:
	 */
	public static ZipOutputStream getZipOutputStream(File zipFile) throws IOException {
		return new ZipOutputStream(getBufferedOutputStream(zipFile));
	}

	/**
	 * 
	  * @param sourceFile
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:08
	  * @See:
	 */
	public static InputStream getBufferedInputStream(File sourceFile) throws IOException {
		return new BufferedInputStream(FileUtils.openInputStream(sourceFile));
	}

	/**
	 * 
	  * @param input
	  * @return  
	  * @version: 2011-7-13 下午02:49:11
	  * @See:
	 */
	public static InputStream getBufferedInputStream(InputStream input) {
		return new BufferedInputStream(input, DEFAULT_BFFER_SIZE * 2);
	}

	/**
	 * 
	  * @param sourceFile
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:13
	  * @See:
	 */
	public static Reader getBufferedReader(File sourceFile) throws IOException {
		return new BufferedReader(new FileReader(sourceFile));
	}

	/**
	 * 
	  * @param input
	  * @param output
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:16
	  * @See:
	 */
	public static void copy(InputStream input, OutputStream output) throws IOException {
		IOUtils.copy(input, output);
	}

	/**
	 * 
	  * @param input
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:23
	  * @See:
	 */
	public static byte[] stream2ByteArray(InputStream input) throws IOException {
		return IOUtils.toByteArray(input);
	}

	/**
	 * 
	  * @param input
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:28
	  * @See:
	 */
	public static InputStream getByteArrayInputStream(InputStream input) throws IOException {
		return new ByteArrayInputStream(stream2ByteArray(input));
	}

	/**
	 * 
	  * @param directory
	  * @param fileName
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:49:41
	  * @See:
	 */
	public static Writer getFileWriter(String directory, String fileName) throws Exception {
		File file = getFile(directory, fileName);
		return new FileWriterWithEncoding(file, DEFAULT_CHARSET);
	}

	/**
	 * 
	  * @param file
	  * @param charset
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:44
	  * @See:
	 */
	public static Writer getFileWriter(File file, String charset) throws IOException {
		return new FileWriterWithEncoding(file, charset);
	}
	
	/**
	 * 
	  * @param sourceFile
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:49:46
	  * @See:
	 */
	public static byte[] getFileByteArray(File sourceFile) throws Exception {
		InputStream inputStream = FileUtils.openInputStream(sourceFile);
		try {
			return stream2ByteArray(inputStream);
		} finally {
			closeInputStream(inputStream);
		}
	}

	/**
	 * 
	  * @param sourceFile
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:49:49
	  * @See:
	 */
	public static String getFileContent(File sourceFile) throws Exception {
		InputStream inputStream = FileUtils.openInputStream(sourceFile);
		try {
			return IOUtils.toString(inputStream);
		} finally {
			closeInputStream(inputStream);
		}
	}

	/**
	 * 
	  * @param output
	  * @param charset
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:49:53
	  * @See:
	 */
	public static Writer getFileWriter(OutputStream output, String charset) throws Exception {
		return new OutputStreamWriter(output, charset);
	}

	/**
	 * 
	  * @param output
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:55
	  * @See:
	 */
	public static DeflaterOutputStream getGZipOutputStream(OutputStream output) throws IOException {
		return getGZipOutputStream(output, DEFAULT_BFFER_SIZE);
	}

	/**
	 * 
	  * @param output
	  * @param bufferSize
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:49:58
	  * @See:
	 */
	public static DeflaterOutputStream getGZipOutputStream(OutputStream output, int bufferSize) throws IOException {
		return new GZIPOutputStream(output, bufferSize);
	}

	/**
	 * 
	  * @param input
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:03
	  * @See:
	 */
	public static InflaterInputStream getGZipInputStream(InputStream input) throws IOException {
		return getGZipInputStream(input, DEFAULT_BFFER_SIZE);
	}

	/**
	 * 
	  * @param input
	  * @param bufferSize
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:05
	  * @See:
	 */
	public static InflaterInputStream getGZipInputStream(InputStream input, int bufferSize) throws IOException {
		return new GZIPInputStream(input, bufferSize);
	}

	/**
	 * 
	  * @param array
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:09
	  * @See:
	 */
	public static InflaterInputStream getGZipInputStream(byte[] array) throws IOException {
		return getGZipInputStream(array, DEFAULT_BFFER_SIZE);
	}

	/**
	 *  <p>获得GIZP解压缩流(</p>
	  * @param array
	  * @param bufferSize
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:12
	  * @See:
	 */
	public static InflaterInputStream getGZipInputStream(byte[] array, int bufferSize) throws IOException {
		return new GZIPInputStream(new ByteArrayInputStream(array), bufferSize);
	}

	/**
	 *   <p>获得解压缩流(</p>
	  * @param array
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:15
	  * @See:
	 */
	public static InflaterInputStream getZipInputStream(byte[] array) throws IOException {
		return new ZipInputStream(new ByteArrayInputStream(array));
	}

	/**
	 *  <p>获得GZIP的压缩流(包装)</p>
	  * @param output
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:17
	  * @See:
	 */
	public static DeflaterOutputStream getZipOutputStream(OutputStream output) throws IOException {
		return new ZipOutputStream(output);
	}

	/**
	 *  <p>按照压缩流对源对象进行压缩保存,返回其字节数组</p>
	  * @param obj
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:20
	  * @See:
	 */
	public static byte[] writeZipCompress(Object obj) throws IOException {
		return writeCompress(obj, false);
	}

	/**
	 * <p>按照GZIP对源对象进行压缩保存,返回其字节数组</p>
	  * @param obj
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:23
	  * @See:
	 */
	public static byte[] writeGZipCompress(Object obj) throws IOException {
		return writeCompress(obj, true);
	}

	/**
	 *  <p>按照GZIP或者压缩流对源对象进行压缩保存,返回其字节数组</p>
	  * @param obj
	  * @param flag
	  * @return
	  * @throws IOException  
	  * @version: 2011-7-13 下午02:50:26
	  * @See:
	 */
	private static byte[] writeCompress(Object obj, boolean flag) throws IOException {
		byte[] compress = new byte[] {};

		if (obj == null) {
			return compress;
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream(ZIP_BUFFER_SIZE);

		DeflaterOutputStream dout = (flag) ? getGZipOutputStream(output) : getZipOutputStream(output);

		ObjectOutputStream out = new ObjectOutputStream(dout);
		try {
			out.writeObject(obj);
			out.flush();
			out.close();
			dout.close();

			compress = output.toByteArray();
		} finally {
			closeOutputStream(output);
		}

		return compress;
	}

	/**
	 *  <p>从压缩字节数组中读取源对象(通过解压缩流)</p>
	  * @param compress
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:50:31
	  * @See:
	 */
	public static Object readZipCompress(byte[] compress) throws Exception {
		return readCompress(compress, false);
	}

	/**
	 *  <p>从压缩字节数组中读取源对象(通过GZIP解压缩流)</p>
	  * @param compress
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:50:34
	  * @See:
	 */
	public static Object readGZipCompress(byte[] compress) throws Exception {
		return readCompress(compress, true);
	}

	/**
	 *  <p>从压缩字节数组中读取源对象</p>
	 *  <p>从解压缩流或者GZIP流中读取</p>
	  * @param compress
	  * @param flag
	  * @return
	  * @throws Exception  
	  * @version: 2011-7-13 下午02:50:37
	  * @See:
	 */
	private static Object readCompress(byte[] compress, boolean flag) throws Exception {
		if (CommonHelper.checkIsEmpty(compress)) {
			return null;
		}

		ByteArrayInputStream input = new ByteArrayInputStream(compress);
		InflaterInputStream fin = (flag) ? getGZipInputStream(input) : getZipInputStream(input);
		ObjectInputStream in = new ObjectInputStream(fin);

		Object serial = null;

		try {
			serial = in.readObject();
			in.close();
			fin.close();
		} finally {
			closeInputStream(input);
		}

		return serial;
	}
	
	/**
	 *  <p>检查文件是否符合前后缀约束规范</p>
	  * @param fileName
	  * @param prefixs
	  * @param suffixs
	  * @return  
	  * @version: 2011-7-13 下午02:50:40
	  * @See:
	 */
	public static boolean checkFileName(String fileName, String[] prefixs, String[] suffixs) {
		boolean hasPrefix = false;
		
		boolean prefixFlag = CommonHelper.checkIsNotEmpty(prefixs);
		
		if(prefixFlag) {
			for (String prefix : prefixs) {
				if (fileName.startsWith(prefix)) {
					hasPrefix = true;
				}
			}
		} else {
			hasPrefix = true;
		}

		boolean hasSuffix = false;
		
		boolean suffixFlag = CommonHelper.checkIsNotEmpty(suffixs);
			
		if(suffixFlag) {
			for (String suffix : suffixs) {
				if (fileName.endsWith(suffix)) {
					hasSuffix = true;
				}
			}
		} else {
			hasSuffix = true;
		}

	    return (hasPrefix && hasSuffix);
	}
	
	public static InputStream getInputStream(String url) throws IOException {
		Assert.notNull(url, "Url must not be null");

		return getInputStream(new URL(url));

	}

	/**
	 * 
	 * @description：读取URL资源的文件输入流
	 * @param url
	 * @return
	 * @throws IOException
	 * @version: 2011-1-8 下午01:41:10
	 * @See:
	 */
	public static InputStream getInputStream(URL url) throws IOException {
		URLConnection con = url.openConnection();
		con.setUseCaches(false);
		try {
			return con.getInputStream();
		} catch (IOException ex) {
			// Close the HTTP connection (if applicable).
			if (con instanceof HttpURLConnection) {
				((HttpURLConnection) con).disconnect();
			}
			throw ex;
		}
	}

    /**
     * 
      * @param sourceFile
      * @return  
      * @version: 2011-7-13 下午02:51:06
      * @See:
     */
	private static ZipEntry getFileZipEntry(File sourceFile) {
		ZipEntry ze = new ZipEntry(getFullFileName(sourceFile));
		ze.setSize(sourceFile.length());
		ze.setTime(sourceFile.lastModified());
		return ze;
	}

	/**
	 *  <p>按照读写模式来打开文件通道(NIO方式)</p>
	  * @param file
	  * @param mode
	  * @return
	  * @throws FileNotFoundException  
	  * @version: 2011-7-13 下午02:51:09
	  * @See:
	 */
	private static FileChannel getFileChannel(File file, String mode) throws FileNotFoundException {
		RandomAccessFile accessFile = new RandomAccessFile(file, mode);

		return accessFile.getChannel();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("" + getFilePrefixPart("CommandServer201010.log", "CommandServer"));
		System.out.println("" + getFileName("aa.txt"));
	}

}
