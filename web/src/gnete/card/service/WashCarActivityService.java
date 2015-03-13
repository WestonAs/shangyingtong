package gnete.card.service;

import gnete.card.entity.UserInfo;
import gnete.card.entity.WashCarActivity;
import gnete.etc.BizException;


public interface WashCarActivityService {
     
     /**新增活动规则*/
     void addWashCarActivity(WashCarActivity washCarActivity,UserInfo sessionUser)throws BizException;
     
     /**修改活动规则*/
     void modifyWashCarActivity(WashCarActivity washCarActivity,UserInfo sessionUser)throws BizException;
}
