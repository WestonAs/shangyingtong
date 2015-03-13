package gnete.card.service.impl;

import flink.ftp.CommunicationException;
import flink.ftp.IFtpCallBackProcess;
import flink.ftp.impl.CommonWriteUploadCallBackImpl;
import flink.ftp.impl.FtpCallBackProcessImpl;
import flink.util.IOUtil;
import flink.util.Paginater;
import gnete.card.dao.CardInfoDAO;
import gnete.card.dao.CardPrevConfigDAO;
import gnete.card.dao.LogoConfigDAO;
import gnete.card.dao.PublishNoticeDAO;
import gnete.card.dao.SaleDepositCheckConfigDAO;
import gnete.card.entity.CardPrevConfig;
import gnete.card.entity.LogoConfig;
import gnete.card.entity.PublishNotice;
import gnete.card.entity.SaleDepositCheckConfig;
import gnete.card.entity.UserInfo;
import gnete.card.entity.flag.ShowFlag;
import gnete.card.entity.state.CurrCodeState;
import gnete.card.service.PublishNoticeService;
import gnete.card.service.mgr.SysparamCache;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publishNoticeService")
public class PublishNoticeServiceImpl implements PublishNoticeService {

	@Autowired
	private PublishNoticeDAO publishNoticeDAO;
	@Autowired
	private LogoConfigDAO logoConfigDAO;
	@Autowired
	private SaleDepositCheckConfigDAO saleDepositCheckConfigDAO;
	@Autowired
	private CardPrevConfigDAO cardPrevConfigDAO;
	@Autowired
	private CardInfoDAO cardInfoDAO;

	private static final Logger logger = LoggerFactory.getLogger(PublishNoticeServiceImpl.class);
	
	public PublishNotice addPublishNotice(PublishNotice publishNotice, String userId)
			throws BizException {
		Assert.notNull(publishNotice, "要发布的通知对象不能为空");
		Assert.notEmpty(publishNotice.getTitle(), "标题不能为空");
		
		// 隐藏其它所有的记录
		publishNoticeDAO.updateShowFlag(ShowFlag.HIDE.getValue());
		
		publishNotice.setShowFlag(ShowFlag.SHOW.getValue());
		publishNotice.setPubUser(userId);
		publishNotice.setPubTime(new Date());

		publishNoticeDAO.insert(publishNotice);
		return publishNotice;
	}

	public boolean deletePublishNotice(String id) throws BizException {
		Assert.notEmpty(id, "传入的ID不能为空");
		return publishNoticeDAO.delete(id) > 0;
	}

	public boolean hidePublishNotice(String id) throws BizException {
		Assert.notEmpty(id, "传入的ID不能为空");
		PublishNotice notice = (PublishNotice) publishNoticeDAO.findByPk(id);
		Assert.notNull(notice, "要显示的通知已经不存在");
		notice.setShowFlag(ShowFlag.HIDE.getValue());

		return publishNoticeDAO.update(notice) > 0;
	}

	public boolean showPublishNotice(String id) throws BizException {
		Assert.notEmpty(id, "传入的ID不能为空");

		PublishNotice notice = (PublishNotice) publishNoticeDAO.findByPk(id);
		Assert.notNull(notice, "要显示的通知已经不存在");
		
		// 隐藏其它所有的记录
		publishNoticeDAO.updateShowFlag(ShowFlag.HIDE.getValue());
		
		// 显示当前的记录
		notice.setShowFlag(ShowFlag.SHOW.getValue());
		
		return publishNoticeDAO.update(notice) > 0;
	}

	public void addCardLogoConfig(LogoConfig logoConfig, File homeBigFile,
			File homeSmallFile, File loginSmallFile, UserInfo userInfo)
			throws BizException {
		Assert.notNull(logoConfig, "发卡机构Logo配置对象不能为空");
		Assert.notEmpty(logoConfig.getBranchNo(), "发卡机构编号不能为空");
		Assert.notNull(homeBigFile, "要上传的首页大图不能为空");
		Assert.notNull(homeSmallFile, "要上传的首页小图不能为空");
		Assert.notNull(loginSmallFile, "要上传的登录后的小图不能为空");
		Assert.notTrue(this.logoConfigDAO.isExist(logoConfig.getBranchNo()), 
				"发卡机构[" + logoConfig.getBranchNo() + "]的logo配置已经存在，请先删除再重新配置");
		
		String homeBigFileName = "login_" + logoConfig.getBranchNo() + ".jpg";
		String homeSmallFileName = "logo_" + logoConfig.getBranchNo() + ".jpg";
		String loginSmallFileName = "logo_" + logoConfig.getBranchNo() + ".gif";

		// 上传图片到web应用的指定目录
		try{
			logger.info("保存机构[{}]的logo图片到web应用路径", logoConfig.getBranchNo());
			// 得到本类的目录，根据此目录取得应用的根目录
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String webLogoPath = path.substring(0, path.indexOf("WEB-INF")) + "images/logo/";
			IOUtil.uploadFile(homeBigFile, homeBigFileName, webLogoPath);
			IOUtil.uploadFile(homeSmallFile, homeSmallFileName, webLogoPath);
			IOUtil.uploadFile(loginSmallFile, loginSmallFileName, webLogoPath);
		}catch (Exception e) {
			logger.warn("保存logo图片异常", e);
			throw new BizException("保存logo图片异常", e.getMessage());
		}
		
		// 上传图片到ftp服务器
		String ftpServer = SysparamCache.getInstance().getFtpServerIP();
		String user = SysparamCache.getInstance().getFtpServerUser();
		String pwd = SysparamCache.getInstance().getFtpServerPwd();
		String ftpPath = SysparamCache.getInstance().getBranchLogoFtpSavePath();
		IFtpCallBackProcess ftpCallBackTemplate = new FtpCallBackProcessImpl(ftpServer, user, pwd);
		try {
			logger.info("上传机构[{}]的logo图片到ftp服务器", logoConfig.getBranchNo());
			ftpCallBackTemplate.processFtpCallBack( new CommonWriteUploadCallBackImpl(ftpPath, homeBigFile, homeBigFileName));
			ftpCallBackTemplate.processFtpCallBack( new CommonWriteUploadCallBackImpl(ftpPath, homeSmallFile, homeSmallFileName));
			ftpCallBackTemplate.processFtpCallBack( new CommonWriteUploadCallBackImpl(ftpPath, loginSmallFile, loginSmallFileName));
		} catch (CommunicationException e) {
			String msg = "FTP上传时发生异常：" + e.getMessage();
			logger.warn(msg);
			throw new BizException(msg);
		}

		// 在数据库中插入数据
		logoConfig.setHomeBig(homeBigFileName);
		logoConfig.setHomeSmall(homeSmallFileName);
		logoConfig.setLoginSmall(loginSmallFileName);
		logoConfig.setUpdateBy(userInfo.getUserId());
		logoConfig.setUpdateTime(new Date());
		this.logoConfigDAO.insert(logoConfig);
	}
	
	
	public boolean deleteCardLogoConfig(String branchCode) throws BizException {
		Assert.notEmpty(branchCode, "发卡机构编号不能为空");
		
		return this.logoConfigDAO.delete(branchCode) > 0;
	}
	
	public SaleDepositCheckConfig findCheckConfig(String cardBranch) {
		return (SaleDepositCheckConfig) this.saleDepositCheckConfigDAO.findByPk(cardBranch);
	}
	
	public void modifyCheckConfig(SaleDepositCheckConfig config, UserInfo user)
			throws BizException {
		Assert.notNull(config, "要修改的发卡机构业务审核配置信息不存在");
		Assert.notEmpty(config.getCardBranch(), "发卡机构编号不能为空");
		Assert.notEmpty(config.getSellCheck(), "售卡是否需要审核不能为空");
		Assert.notEmpty(config.getDepositCheck(), "充值是否需要审核不能为空");
		Assert.notEmpty(config.getLossCardCheck(), "挂失是否需要审核不能为空");
		Assert.notEmpty(config.getTransAccCheck(), "转账是否需要审核不能为空");
		Assert.notEmpty(config.getCancelCardCheck(), "销户是否需要审核不能为空");
		
		config.setUpdateBy(user.getUserId());
		config.setUpdateTime(new Date());
		
		this.saleDepositCheckConfigDAO.update(config);
	}
	
	public Paginater findCardPrevConfigPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.cardPrevConfigDAO.findCardPrevConfigPage(params, pageNumber, pageSize);
	}
	
	public void addCardPrevConfig(CardPrevConfig cardPrevConfig, UserInfo user)
			throws BizException {
		Assert.notNull(cardPrevConfig, "发卡机构卡号前三位配置对象不能为空");
		Assert.notNull(user, "登录用户的信息不能为空");
		Assert.notEmpty(cardPrevConfig.getBranchCode(), "发卡机构编号不能为空");
		Assert.notEmpty(cardPrevConfig.getCardPrev(), "卡号前三位不能为空");
		Assert.isTrue(NumberUtils.isDigits(cardPrevConfig.getCardPrev()), "卡号前三位必须全为数字");
		Assert.notTrue(this.cardPrevConfigDAO.isExist(cardPrevConfig.getBranchCode()), 
				"发卡机构[" + cardPrevConfig.getBranchCode() + "]的卡号前三位已经配置，请勿重复配置！");
		
		Assert.notTrue(this.cardInfoDAO.isExsitByCardIssuer(cardPrevConfig.getBranchCode()), 
				"发卡机构[" + cardPrevConfig.getBranchCode() + "]已经发卡，不能为其配置！");
		
		cardPrevConfig.setUpdateBy(user.getUserId());
		cardPrevConfig.setUpdateTime(new Date());
		cardPrevConfig.setStatus(CurrCodeState.NORMAL.getValue());
		
		this.cardPrevConfigDAO.insert(cardPrevConfig);
	}
	
	public void deleteCardPrevConfig(String branchCode) throws BizException {
		Assert.notEmpty(branchCode, "要删除的发卡机构编号不能为空");
		
		// 如果该卡类型已经发卡，则禁止删除
		Assert.notTrue(this.cardInfoDAO.isExsitByCardIssuer(branchCode), "发卡机构[" + branchCode + "]已经发卡，不能删除！");
		
		this.cardPrevConfigDAO.delete(branchCode);
	}
}
