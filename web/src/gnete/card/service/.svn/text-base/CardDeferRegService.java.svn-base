package gnete.card.service;

import java.io.File;
import java.util.List;

import gnete.card.entity.CardDeferReg;
import gnete.card.entity.CardInfo;
import gnete.card.entity.CardSubClassDef;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

/**  
 * Filename:    CardDeferRegService.java  
 * Description: 卡延期
 * Copyright:   Copyright (c)2010
 * Company:     深圳雁联计算系统有限公司 
 * @author:     aps-zsg  
 * @version:    V1.0  
 * Create at:   2010-9-25 下午06:12:07  
 */

public interface CardDeferRegService {
	
	void addCardDefer(CardDeferReg cardDefer, UserInfo user) throws BizException;
	
	void addCardDeferBat(CardDeferReg cardDefer, UserInfo user) throws BizException;
	
	boolean modifyCardDefer(CardDeferReg cardDefer, UserInfo user) throws BizException;
	
	boolean delete(Long cardDeferId) throws BizException;
	
	boolean checkCardState(CardInfo cardInfo, CardSubClassDef cardSubClassDef);
	
	List<String> addFileCardDeferReg(File upload, String uploadFileName, UserInfo user) throws BizException;

}
