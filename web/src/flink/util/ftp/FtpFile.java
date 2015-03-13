package flink.util.ftp;

public class FtpFile {
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 是否是目录
	 */
	private boolean directory;
	
	private Long size;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
