package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonDownloadCallBackImpl;
import flink.ftp.impl.CommonNameDirFilesCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.CommonHelper;
import flink.util.FileHelper;
import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.RmaFileParaDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.BusiReport;
import gnete.card.entity.RmaFilePara;
import gnete.card.entity.state.CardTypeState;
import gnete.card.entity.type.IssType;
import gnete.card.service.RmaService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;
import gnete.etc.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rmaService")
public class RmaServiceImpl implements RmaService {

	static final Logger logger = LoggerFactory.getLogger(RmaServiceImpl.class);
	
	@Autowired
	private RmaFileParaDAO rmaFileParaDAO;
	
	private static final String[] prefix = new String[] {};
	private static final String[] suffix = new String[] { ".txt" };
	
	public boolean addRmaFilePara(RmaFilePara rmaFilePara, String userId)
			throws BizException {
		Assert.notNull(rmaFilePara, "添加的划付文件权限参数不能为空！");
		
		//检查新增对象是否已经存在
		RmaFilePara old = (RmaFilePara)this.rmaFileParaDAO.findByPk(rmaFilePara.getIssCode());
		
		Assert.isNull(old, IssType.valueOf(rmaFilePara.getIssType()).getName() + 
				"["+ rmaFilePara.getIssCode() + "]的划付文件权限参数已经设置，不能重复定义！");
				
		rmaFilePara.setStatus(CardTypeState.NORMAL.getValue());		
		rmaFilePara.setUpdateTime(new Date());
		rmaFilePara.setUpdateBy(userId);
				
		return this.rmaFileParaDAO.insert(rmaFilePara) != null; 
	}

	public boolean deleteRmaFilePara(String issCode) throws BizException {
		Assert.notNull(issCode, "删除的划付文件权限参数不能为空");		
		return this.rmaFileParaDAO.delete(issCode) > 0;	
	}

	public boolean modifyRmaFilePara(RmaFilePara rmaFilePara, String userId)
			throws BizException {
		Assert.notNull(rmaFilePara, "更新的划付文件权限参数不能为空");		
		
		if(CardTypeState.NORMAL.getValue().equals(rmaFilePara.getStatus())){
			rmaFilePara.setStatus(CardTypeState.CANCEL.getValue());
		}else if(CardTypeState.CANCEL.getValue().equals(rmaFilePara.getStatus())){
			rmaFilePara.setStatus(CardTypeState.NORMAL.getValue());
		}
		
		rmaFilePara.setUpdateBy(userId);
		rmaFilePara.setUpdateTime(new Date());
		return this.rmaFileParaDAO.update(rmaFilePara) > 0;
	}

	public Paginater getRmaFileList(Map<String, Object> params, int pageNumber,
			int pageSize) throws BizException {
		
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		//生成的划付文件保存路径
		String rmaFileSavePath = mgr.getRmaFilePath(); 
			
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		CommonNameDirFilesCallBackImpl callBack = new CommonNameDirFilesCallBackImpl(rmaFileSavePath, prefix, suffix);
		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);
		logger.debug("ftp查询划付文件列表结果[{}]", flag);
		List<String> fileList = new ArrayList<String>();
		if (flag) {
			fileList = callBack.getNameFilesRefer();
		}
		
		String fileDate = (String) params.get("fileDate");
		String fileType = (String) params.get("fileType");
		String branchCode = (String) params.get("branchCode");
		List<BranchInfo> branchList = (List<BranchInfo>) params.get("cardBranchList");
		
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		List<String> filterFileList = new ArrayList<String>();
		// 分支机构
		if(branchList!=null){
			for(int i = 0; i < branchList.size(); i++){
				filterFileList.addAll(getFileListResult(fileList, branchList.get(i).getBranchCode(), fileDate, fileType));
			}
			
			fileList = filterFileList;
		}
		
		// 过滤文件列表，筛选出符合条件（branchCode, fileType, fileDate）的文件列表
		filterFileList = getFileListResult(fileList, branchCode, fileDate, fileType);
			
		// 把文件列表转化为BusiReport列表
		busiReportList = getBusiReportList(filterFileList);
		
		// 对文件列表降序排序
		busiReportList = BubbleSort(busiReportList);
		
		return CommonHelper.getListPage(busiReportList, pageNumber, pageSize);

	}
	
	/**
	 * 针对网银通的划付文件列表
	 */
	public Paginater getBranchRmaFileList(Map<String, Object> params, int pageNumber,	int pageSize) throws BizException {
		
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getParamValue(Constants.WYT_REMOTE_SERVER_CODE);
		String user = mgr.getParamValue(Constants.WYT_REMOTE_USER_CODE);
		String pwd = mgr.getParamValue(Constants.WYT_REMOTE_PWD_CODE);

		String fileDate = (String) params.get("fileDate");
		String fileType = (String) params.get("fileType");
		String branchCode = (String) params.get("branchCode");
		List<BranchInfo> branchList = (List<BranchInfo>) params.get("cardBranchList");
		
		//生成的划付文件保存路径
		String rmaFileSavePath = mgr.getClear2PathBankBranchSavePath(); // /home/card/wytBranch
		
		if(CommonHelper.isNotEmpty(branchCode)){//单个机构,减少FTP获取文件数
			rmaFileSavePath = rmaFileSavePath.concat(FileHelper.PATH_SEPRATOR).concat(branchCode);
		}
		
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		List<String> filterFileList = new ArrayList<String>();
			
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);

		CommonNameDirFilesCallBackImpl callBack = new CommonNameDirFilesCallBackImpl(rmaFileSavePath, prefix, suffix);

		boolean flag = ftpCallBackTemplate.processFtpCallBack(callBack);

		List<String> fileList = new ArrayList<String>();
		
		if (flag) {
			fileList = callBack.getNameFilesRefer();
		}
		
		// 分支机构
		if(branchList!=null){
			for(int i = 0; i < branchList.size(); i++){
				filterFileList.addAll(getFileListResult(fileList, branchList.get(i).getBranchCode(), fileDate, fileType));
			}
			
			fileList = filterFileList;
		}
		
		// 过滤文件列表，筛选出符合条件（branchCode, fileType, fileDate）的文件列表
		filterFileList = getFileListResult(fileList, branchCode, fileDate, fileType);
			
		// 把文件列表转化为BusiReport列表
		busiReportList = getBusiReportList(filterFileList);
		
		// 对文件列表降序排序
		busiReportList = BubbleSort(busiReportList);
		
		return CommonHelper.getListPage(busiReportList, pageNumber, pageSize);

	}
	
	/**
	 * 对划付文件过滤
	 * 根据查询条件返回新的list
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<String> getFileListResult(List<String> oldList, String... params) {
		
		if (ArrayUtils.isEmpty(params)) {
			return oldList;
		}
		
		List<String> list = new ArrayList<String>();
		boolean flag;
		
		for (String path : oldList) {
			flag = true;
			path = path.replace("\\", "/");
			String name = path.substring(path.lastIndexOf("/") + 1, path.length()); // 获得文件名
			name = name.split("\\.")[0]; // 去掉后缀
			
			if (StringUtils.isBlank(name)) {
				continue;
			}
			
			// 检查文件名是否以发卡机构开头
			String branchCode = params[0];
			if(StringUtils.isNotEmpty(branchCode)){
				flag = name.startsWith(branchCode) ? true : false;
			}
			
			// 检查文件名是否包含日期
			String fileDate = params[1];
			if(flag && StringUtils.isNotEmpty(fileDate)){
				flag = name.contains(fileDate) ? true : false;
			}
			
			// 检查文件名是否以文件类型结尾
			String fileType = params[2];
			if(flag && StringUtils.isNotEmpty(fileType)){
				flag = name.endsWith(fileType) ? true : false;
			}
			
			if(flag){
				list.add(path);
			}
			
		}
		return list;
	}

	/**
	 * 把文件路径列表转化为BusiReport实体
	 * 
	 * @param oldList
	 * @param params
	 * @return
	 */
	private List<BusiReport> getBusiReportList(List<String> fileList) {
		List<BusiReport> busiReportList = new ArrayList<BusiReport>();
		String[] filePart = new String[] {};
		
		for (String path : fileList) {
			path = path.replace("\\", "/");
			BusiReport busiReport = new BusiReport();

			busiReport.setFilePath(path);
			String name = StringUtils.substringAfterLast(path, "/");
			busiReport.setReportName(name);
			name = name.split("\\.")[0]; // 去掉后缀
			
			filePart = name.split("_"); 
			busiReport.setMerchantNo(filePart[0]); // 发卡机构
			busiReport.setReportDate(filePart[1]); // 日期
			//busiReport.setReportType(filePart[2]); // 类型
			
			
			busiReportList.add(busiReport);
		}
		
		return busiReportList;
	}
	
	// 冒泡排序
	private List<BusiReport> BubbleSort(List<BusiReport> list){ 
		int i;
		int j;
		int n = list.size();
		boolean exchange; //交换标志
		BusiReport temp = null;
		
		for(i=1; i<n; i++){ 
			exchange = false; 
			for(j = n-1; j>=i; j--){ 
				if(list.get(j).getReportDate().compareTo(list.get(j-1).getReportDate())>0){ //交换记录
					temp = list.get(j);
					list.set(j, list.get(j-1));
					list.set(j-1, temp);
					exchange = true; //发生了交换,将交换标志置为真
				}
			}
			if(!exchange){ //本趟排序未发生交换，提前终止算法
				return list;
			}
		}
		return list;
	}

	/**
	 * 针对网银通的划付文件列表
	 */
	public boolean downloadBranchRmaFile(String fullPath) throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getParamValue(Constants.WYT_REMOTE_SERVER_CODE);
		String user = mgr.getParamValue(Constants.WYT_REMOTE_USER_CODE);
		String pwd = mgr.getParamValue(Constants.WYT_REMOTE_PWD_CODE);
		
		String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1); // 文件名
		String ftpPath = fullPath.substring(0, fullPath.indexOf(fileName) - 1); // 文件路径
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到划付文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取划付文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		return result;
	}
	
	public boolean downloadRmaFile(String fullPath) throws BizException {
		SysparamCache mgr = SysparamCache.getInstance();
		String ftpServer = mgr.getFtpServerIP();
		String user = mgr.getFtpServerUser();
		String pwd = mgr.getFtpServerPwd();
		
		String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1); // 文件名
		String ftpPath = fullPath.substring(0, fullPath.indexOf(fileName) - 1); // 文件路径
		
		// 构造模板处理类
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		
		// 构造下载回调处理类
		CommonDownloadCallBackImpl downloadCallBack = new CommonDownloadCallBackImpl(ftpPath, fileName);
		
		// 处理下载
		boolean result = false;
		try {
			result = ftpCallBackTemplate.processFtpCallBack(downloadCallBack);
		} catch (CommunicationException e) {
			throw new BizException(e.getMessage());
		}
		
		if (!result) {
			String msg = "找不到划付文件[" + fileName + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		
		InputStream inputStream = downloadCallBack.getRemoteReferInputStream();
		if (inputStream == null) {
			return false;
		}
		
		try {
			IOUtil.downloadFile(inputStream, fileName);
		} catch (IOException ex) {
			String msg = "提取划付文件[" + fileName + "]异常,原因[" + ex.getMessage() + "]";
			logger.warn(msg);
			throw new BizException(msg);
		}
		return result;
	}

}
