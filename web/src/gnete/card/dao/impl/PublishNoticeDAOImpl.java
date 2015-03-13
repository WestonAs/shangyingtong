package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PublishNoticeDAO;
import gnete.card.entity.PublishNotice;

@Repository
public class PublishNoticeDAOImpl extends BaseDAOIbatisImpl implements
		PublishNoticeDAO {

	public String getNamespace() {
		return "PublishNotice";
	}

	public Paginater findPublishNotice(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPublishNotice", params, pageNumber,
				pageSize);
	}
	
	public int updateShowFlag(String showFlag) {
		return this.update("updateShowFlag", showFlag);
	}
	
	public List<PublishNotice> findByShowFlag(String showFlag) {
		return this.queryForList("findByShowFlag", showFlag);
	}
}