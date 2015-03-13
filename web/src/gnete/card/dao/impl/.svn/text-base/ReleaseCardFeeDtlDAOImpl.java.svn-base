package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ReleaseCardFeeDtlDAO;
import gnete.card.entity.ReleaseCardFeeDtl;

@Repository
public class ReleaseCardFeeDtlDAOImpl extends BaseDAOIbatisImpl implements ReleaseCardFeeDtlDAO {

    public String getNamespace() {
        return "ReleaseCardFeeDtl";
    }

	public List<ReleaseCardFeeDtl> getReleaseCardFeeDtlList(Map<String, Object> params) {
		return this.queryForList("getReleaseCardFeeDtlList", params);
	}

	public Paginater findReleaseCardFeeDtl(Map<String, Object> parma,
			int pageNumber, int pageSize) {
		return this.queryForPage("findReleaseCardFeeDtl", parma, pageNumber, pageSize);
	}
}