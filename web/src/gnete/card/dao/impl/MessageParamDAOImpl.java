package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MessageParamDAO;
import gnete.card.entity.MessageParam;

@Repository
public class MessageParamDAOImpl extends BaseDAOIbatisImpl implements MessageParamDAO {

    public String getNamespace() {
        return "MessageParam";
    }

	public Paginater findMessageParam(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMessageParam", params, pageNumber, pageSize);
	}

	public List<MessageParam> getMessageParamList(Map<String, Object> params) {
		return this.queryForList("getMessageParamList", params);
	}
	
	public List<MessageParam> findMessageParam(Map params) {
		return this.queryForList("findMessageParam", params);
	}
}