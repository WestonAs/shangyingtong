package gnete.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnete.card.dao.DrawSeqPoolDAO;
import gnete.card.entity.DrawSeqPool;

@Repository
public class DrawSeqPoolDAOImpl extends BaseDAOIbatisImpl implements
		DrawSeqPoolDAO {

	public String getNamespace() {
		return "DrawSeqPool";
	}

	public List<DrawSeqPool> findEffectSeq(String status) {
		return queryForList("findEffectSeq", status);
	}
}