package gnete.card.service.impl;

import gnete.card.dao.SequenceDAO;
import gnete.card.dao.WashCarActivityDAO;
import gnete.card.entity.UserInfo;
import gnete.card.entity.WashCarActivity;
import gnete.card.service.WashCarActivityService;
import gnete.etc.Assert;
import gnete.etc.BizException;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("washCarActivityService")
public class WashCarActivityServiceImpl implements WashCarActivityService {
	private final Log logger = LogFactory.getLog(getClass());
    @Autowired
	private WashCarActivityDAO washCarActivityDAO;
    @Autowired
	private SequenceDAO sequenceDAO;

	@Override
	public void addWashCarActivity(WashCarActivity washCarActivity,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(washCarActivity,"活动规则对象不能为空");
		
		Long activityId = this.sequenceDAO.getSequence("WASH_ACTIVITY_ID");
		
		washCarActivity.setActivityId(activityId);
		washCarActivity.setUpdateTime(new Date());
		washCarActivity.setUpdateUser(sessionUser.getUserId());
		washCarActivity.setInsertTime(new Date());
	
		washCarActivityDAO.insert(washCarActivity);
	}

	@Override
	public void modifyWashCarActivity(WashCarActivity washCarActivity,
			UserInfo sessionUser) throws BizException {
		Assert.notNull(washCarActivity, "对象不能为空");
		washCarActivity.setUpdateTime(new Date());
		washCarActivity.setUpdateUser(sessionUser.getUserId());
		washCarActivityDAO.update(washCarActivity);
	}
}
