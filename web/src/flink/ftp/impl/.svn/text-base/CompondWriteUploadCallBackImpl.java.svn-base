package flink.ftp.impl;

import java.io.File;

public class CompondWriteUploadCallBackImpl extends CompondUploadCallBackImpl {

	public CompondWriteUploadCallBackImpl(String remotePath, File uploadFile, String fileName) {
		super(remotePath, uploadFile, fileName);
	}
	
	@Override
	protected boolean needCreateRemotePath() {
		return false;
	}
}
