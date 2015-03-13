package gnete.card.service;

import gnete.card.entity.UserInfo;
import gnete.card.entity.WashCarSvcMbShipDues;
import gnete.etc.BizException;

import java.io.File;

import javax.servlet.http.HttpServletResponse;


public interface WashCarSvcMbShipDuesService {
	 
	 /**生成缴交会费Excel模版*/
     void exportWashCarSvcMbShipExcel(HttpServletResponse response)throws BizException;
     
     /**导入缴交会费Excel*/
     void importWashCarSvcMbShipExcel(File file,WashCarSvcMbShipDues washCarSvcMbShipDues,UserInfo sessionUser)throws BizException;
}
