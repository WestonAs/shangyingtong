package gnete.card.dao.impl;

import gnete.card.dao.SequenceDAO;

import org.springframework.stereotype.Repository;

@Repository
public class SequenceDAOImpl extends BaseDAOIbatisImpl implements SequenceDAO {

	protected String getNamespace() {
		return "Sequence";
	}
	
	public Long getSequence(String seqName) {
		return (Long) this.queryForObject("getSequence", seqName);
	}

}
