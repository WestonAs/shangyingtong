package gnete.card.dao;

public interface SequenceDAO extends BaseDAO {

	/**
	 * 取Sequence
	 * @param seqName
	 * @return
	 */
	Long getSequence(String seqName);
}

