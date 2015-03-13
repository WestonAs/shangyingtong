package gnete.card.web.makecard;

import flink.etc.MatchMode;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.dao.MakeCardRegDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.MakeCardReg;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.type.UserLogType;
import gnete.card.service.MakeCardService;
import gnete.card.util.BranchUtil;
import gnete.card.web.BaseAction;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 卡样定版处理
 */
public class CardStyleAction extends BaseAction {

	private MakeCardReg makeCardReg;

	@Autowired
	private MakeCardRegDAO makeCardRegDAO;
	@Autowired
	private CardSubClassDefDAO cardSubClassDefDAO;
	@Autowired
	private MakeCardService makeCardService;
	@Autowired
	private BranchInfoDAO branchInfoDAO;
	
	private String makeId;

	// 封装上传文件域的属性
	private File upload;
	// 封装上传文件类型的属性
	private String uploadContentType;
	// 封装上传文件名的属性
	private String uploadFileName;

//	private static final int BUFFER_SIZE = 16 * 1024;
	private static final int MAX_SIZE = 50 * 1024 * 1024;

	private List<CardSubClassDef> cardSubTypeList ;
	private Collection makeTypeList;
	private Collection makeCardRegStateList;

	private Paginater page;

	private List<BranchInfo> makeBranchList;
	@Override
	public String execute() throws Exception {
		
		this.makeCardRegStateList = MakeCardRegState.ALL.values();
		if (makeCardReg != null && StringUtils.isNotBlank(makeCardReg.getBranchCode())) {
			cardSubTypeList = cardSubClassDefDAO.findCardSubClassDefByBranNo(makeCardReg.getBranchCode());
		}
		cardSubTypeList = cardSubTypeList!=null ? cardSubTypeList : new ArrayList<CardSubClassDef>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (makeCardReg != null) {
			params.put("makeId", MatchMode.ANYWHERE.toMatchString(makeCardReg.getMakeId()));
			params.put("makeName", MatchMode.ANYWHERE.toMatchString(makeCardReg.getMakeName()));
			params.put("branchCode", makeCardReg.getBranchCode());
			params.put("cardSubtype", makeCardReg.getCardSubtype());
			params.put("picStatus", makeCardReg.getPicStatus());
		}
		
		if (isCenterOrCenterDeptRoleLogined()) { // 运营中心
			
		} else if (isFenzhiRoleLogined()) { // 分支机构
			params.put("fenzhiList", getMyManageFenzhi());
			
		} else if (isCardOrCardDeptRoleLogined()) {//发卡机构 及 部门
			params.put("cardIssuerList", this.getMyCardBranch());
			
		} else if (isCardMakeRoleLogined()) { // 制卡厂商
			params.put("makeUser", getSessionUser().getBranchNo());
		
		} else {
			throw new BizException("没有权限查看卡样列表");
		}
		
		this.page = makeCardRegDAO.findMakeCardReg(params, getPageNumber(), getPageSize());
		return LIST;
	}

	// 卡片定版审核通过
	public String pass() throws Exception {

		this.makeCardService.passCardPic(makeId, getSessionUser());
		String msg = LogUtils.r("制卡登记Id[{0}]的卡样定版审核通过", makeId);
		log(msg, UserLogType.OTHER);
		this.addActionMessage("/cardStyleFix/list.do", msg);

		return SUCCESS;
	}

	// 明细页面
	public String detail() throws Exception {

		makeCardReg = (MakeCardReg) this.makeCardRegDAO.findByPk(makeCardReg.getMakeId());
		return DETAIL;
	}

	// 打开新增页面的初始化操作
	public String showAdd() throws Exception {
		
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能新增卡样。");
		
		Assert.notBlank(makeCardReg.getBranchCode(), "发卡机构不能为空，请先选择发卡机构！");
		
		// 卡子类型列表
		this.cardSubTypeList = cardSubClassDefDAO.findCardSubClassDefByBranNo(makeCardReg.getBranchCode());
		//制卡机构列表
		this.makeBranchList = this.branchInfoDAO.findMakeBranchByCardCode(makeCardReg.getBranchCode());
		
		return ADD;
	}

	/** 发卡机构上传卡样图案，同时在制卡登记簿里记录相关记录 */
	public String add() throws Exception {
		
		Assert.isTrue(isCardRoleLogined(), "只有发卡机构才能新增卡样！");
		
		Assert.isTrue(BranchUtil.isBelong2SameTopBranch(makeCardReg.getBranchCode(), this
				.getLoginBranchCode()), "发起方与发卡机构不是属于同一顶级机构！");
		
		if (!testImageFix(uploadFileName)) {
			throw new BizException("上传文件的类型只能是压缩文件，请重新选择！");
		}
		if (!testMaxSize(upload)) {
			throw new BizException("上传的文件大小不能超过50M，请重新选择！");
		}
		uploadFileName = DateUtil.getCurrentTimeMillis() + getExtention(uploadFileName);
		
		this.makeCardService.addMakeCardReg(upload, uploadFileName, makeCardReg, getSessionUser());
		
		//makeCardReg.setInitUrl(uploadImage());
		//this.makeCardService.addMakeCardReg(makeCardReg, getSessionUser());
		String msg = LogUtils.r("新增制卡登记[{0}]成功", makeCardReg.getMakeId());
		this.log(msg, UserLogType.ADD);
		this.addActionMessage("/cardStyleFix/list.do", msg);
		return SUCCESS;
	}

	public String showCancel() throws Exception {
		return "cancel";
	}

	// 取消制卡
	public String cancel() throws Exception {

		this.makeCardService.canceCardPic(makeId, makeCardReg.getReason(), getSessionUser());
		String msg = LogUtils.r("取消制卡登记[{0}]成功", makeId);
		log(msg, UserLogType.OTHER);
		this.addActionMessage("/cardStyleFix/list.do", msg);
		return SUCCESS;
	}

	public String showDownload() throws Exception {
		return "download";
	}

	// 下载卡样图案
	public String download() throws Exception {
//		MakeCardReg cardReg = (MakeCardReg) makeCardRegDAO.findByPk(makeId);
//		makeCardService.isFileExist(cardReg);
//		String fileName = cardReg.getInitUrl();
		makeCardService.downloadCardPic(makeId, getSessionUser());
		String msg = LogUtils.r("下载制卡登记[{0}]卡样成功", makeId);
		log(msg, UserLogType.OTHER);
//		try {
//			IOUtil.downloadFile(fileName);
//		} catch (IOException e) {
//			addActionError("文件下载时发生异常。");
//			LOG.error("文件下载时发生异常。", e);
//			throw new BizException("文件下载时发生异常。");
//		}
		return null;
	}

	// // 下载图片
	// private void downloadImage(MakeCardReg cardReg) {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// HttpServletResponse response = ServletActionContext.getResponse();
	// byte[] content = null;
	// String fileName = null;
	// try {
	// fileName = cardReg.getInitUrl();
	// String name = fileName.substring(fileName.lastIndexOf("\\") + 1,
	// fileName.length());
	// content = readFileDoc(fileName);
	//
	// if (request.getHeader("User-Agent").toLowerCase()
	// .indexOf("firefox") > 0)
	// name = new String(name.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
	// else if (request.getHeader("User-Agent").toUpperCase().indexOf(
	// "MSIE") > 0)
	// name = URLEncoder.encode(name, "UTF-8"); // IE浏览器
	// response.reset();
	// response.setContentType("application/octet-stream");
	// response.setHeader("Content-Disposition:attachment",
	// "document;filename=\"" + name + "\"");
	// response.setHeader("Connection", "close");
	// ServletOutputStream out = response.getOutputStream();
	// out.write(content);
	// out.flush();
	// out.close();
	// } catch (Exception e) {
	// LOG.error("文件下载时发生异常", e);
	// }
	// }

	// 上传卡样图案
//	private String uploadImage() {
//		File imageFile = new File(getFilePath());
//		copy(upload, imageFile);
//		return imageFile.getAbsolutePath();
//	}

//	private String getFilePath() {
//		// 通过系统参数获取上传卡样图案保存的路径
//		String pathname = ParaMgr.getInstance().getCardStyleWebSavePath();
//		// String pathname = "D:/uploadCardImg";
//		getDirectoryFile(pathname);
//		String imageFileName = new Date().getTime()
//				+ getExtention(uploadFileName);
//		return (pathname + "/" + imageFileName);
//	}

//	private void copy(File src, File dst) {
//		try {
//			InputStream in = null;
//			OutputStream out = null;
//			try {
//				in = new BufferedInputStream(new FileInputStream(src),
//						BUFFER_SIZE);
//				out = new BufferedOutputStream(new FileOutputStream(dst),
//						BUFFER_SIZE);
//				byte[] buffer = new byte[BUFFER_SIZE];
//				while (in.read(buffer) > 0) {
//					out.write(buffer);
//				}
//			} finally {
//				if (null != in) {
//					in.close();
//				}
//				if (null != out) {
//					out.close();
//				}
//			}
//		} catch (IOException e) {
//			addActionError("文件上传发生异常。");
//			LOG.error("文件上传发生异常,", e);
//		}
//	}

	private String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

//	// 获取上传路径
//	private File getDirectoryFile(String directoryName) {
//		File dir = new File(directoryName);
//		if (dir.exists() && (dir.isDirectory())) {
//			return dir;
//		}
//		dir.mkdirs();
//		return dir;
//	}

	// private byte[] readFileDoc(String path) {
	// byte[] result = null;
	// try {
	// FileInputStream inPut = null;
	// try {
	// File file = new File(path);
	// inPut = new FileInputStream(file);
	// result = new byte[inPut.available()];
	// inPut.read(result);
	// } finally {
	// if (inPut != null) {
	// inPut.close();
	// }
	// }
	// } catch (IOException e) {
	// addActionError("文件下载时发生异常。");
	// LOG.error("文件下载时发生异常,", e);
	// }
	// return result;
	// }

	/**
	 * 验证文件大小
	 */
	private boolean testMaxSize(File file) {
		return file.length() <= MAX_SIZE;
	}

	/**
	 * 验证后缀名
	 */
	private boolean testImageFix(String filename) {
		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		// 图片
		fileTypes.add("zip");
		fileTypes.add("rar");

		// 得到文件尾数并进行小写转换
		String postfix = filename.substring(filename.lastIndexOf(".") + 1)
				.toLowerCase();
		return fileTypes.contains(postfix) ? true : false;
	}

	public List<CardSubClassDef> getCardSubTypeList() {
		return cardSubTypeList;
	}

	public void setCardSubTypeList(List<CardSubClassDef> cardSubTypeList) {
		this.cardSubTypeList = cardSubTypeList;
	}

	public Collection getMakeTypeList() {
		return makeTypeList;
	}

	public void setMakeTypeList(Collection makeTypeList) {
		this.makeTypeList = makeTypeList;
	}

	public MakeCardReg getMakeCardReg() {
		return makeCardReg;
	}

	public void setMakeCardReg(MakeCardReg makeCardReg) {
		this.makeCardReg = makeCardReg;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Collection getMakeCardRegStateList() {
		return makeCardRegStateList;
	}

	public void setMakeCardRegStateList(Collection makeCardRegStateList) {
		this.makeCardRegStateList = makeCardRegStateList;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public String getMakeId() {
		return makeId;
	}

	public void setMakeId(String makeId) {
		this.makeId = makeId;
	}

	public List<BranchInfo> getMakeBranchList() {
		return makeBranchList;
	}

	public void setMakeBranchList(List<BranchInfo> makeBranchList) {
		this.makeBranchList = makeBranchList;
	}
}
