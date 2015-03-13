package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.GdnhglIssuerCardStatic;

import java.util.List;

public interface GdnhglIssuerCardStaticDAO extends BaseDAO {
	
	Paginater findGdnhglIssuerCardStatic(GdnhglIssuerCardStatic gdnhglIssuerCardStatic, int pageNumber,
			int pageSize);
	
	List<GdnhglIssuerCardStatic> findGdnhglIssuerCardStatic(GdnhglIssuerCardStatic gdnhglIssuerCardStatic);
}