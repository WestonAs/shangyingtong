package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.Terminal;

public interface TerminalDAO extends BaseDAO {
	
	/** 获得下一个新的终端编号 */
	String getNextNewTermId();

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);

	List<Terminal> find(Map<String, Object> params);

}